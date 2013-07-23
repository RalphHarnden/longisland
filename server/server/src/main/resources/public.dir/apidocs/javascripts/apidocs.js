magnet.init = function(){
    var page = new Page();
}
function Page(){
    var nav = new Nav('docList');
    var host = getQuerystring('url') || '';
    getData(host+'/rest/entities.json', function(data){
        if(data){
            var edb = new EntityDocBuilder('entityDocs', nav, data, host);
        }  
    });
    getData(host+'/rest/controllers.json', function(data){
        if(data){
            var cdb = new ControllerDocBuilder('controllerDocs', nav, data);
        }
    });
}
function Nav(domId){
    this.domId = domId;
}
Nav.prototype.add = function(domId, name){
    var me = this;
    $('#'+domId).append('<li><a href=".'+name+'"><i class="icon-chevron-right"></i> '+name+'</a></li>');
    $('#'+me.domId+' li a[href=".'+name+'"]').click(function(e){
        e.preventDefault();
        $('.content_container').animate({scrollTop:0}, 'fast');
        $('#'+me.domId+' li').removeClass('active');
        $(this).closest('li').addClass('active');
        $('.item').hide();
        $('#'+name).show();
    });
}

function getData(url, callback){
    $.support.cors = true;
    $.ajax({
        url      : url,
        type     : 'GET',
        timeout  : 10000,
        dataType : 'json', 
        success : function(data){
            callback(data);
        },
        error: function(xhr, status, options){
            alert('Connection Failure');
            console.log(xhr, status, options);
        }
    });
}
function xDomainAJAX(url, domId, callback){
    var script = document.createElement("script");
    script.type = "text/javascript";
    if(script.readyState){ //for IE
        script.onreadystatechange = function(){
            if(script.readyState == "loaded" || script.readyState == "complete"){
                script.onreadystatechange = null;
                callback();
            }
        }    
    }
    else{ //other browsers
        script.onload = function(){
            callback();
        }
    }
    script.src = url;
    //$('#ajax-content').append('<div id="'+domId+'"></div>');
    //$('#'+domId).html(script);
    document.getElementById(domId).appendChild(script);
}
// translate controllers.json to a better structure for insert into jsrender tmpls
function ControllerDocBuilder(domId, nav, data){
    var me = this;
    $.each(data.controllers, function(i, obj){ 
        var item = {};
        item.name = obj.interfaceClassName.slice(obj.interfaceClassName.lastIndexOf('.')+1);
        item.apis = [];
        var methods  = [];
        $.each(obj.methods, function(i, api){  
            var urlparams = [];
            var apiparams = [];
            $.each(api.parameters, function(i, param){
                if(api.path.indexOf('{'+param.name+'}') != -1){
                    urlparams.push(param);
                }else{
                    apiparams.push(param);
                }
            });
            item.apis.push({
                api     : api.name,
                url     : '/rest'+api.path,
                methods : [{
                    method       : api.method,
                    isController : true,
                    description  : 'Invokes the Java class: "' + api.returnType + '" on the server',
                    properties   : apiparams
                }],
                params  : urlparams
            });
        });
        nav.add('controllersList', item.name);
        $('#'+domId).append(
            $('#item-tmpl').render(item)
        );
    });
}
// translate entities.json to a better structure for insert into jsrender tmpls
function EntityDocBuilder(domId, nav, data, host){
    var me = this;
    me.host = host;
    var entity = {};
    var params = [];
    var timeout = 0;
    $.each(data, function(i, api){
        // entity level
        var plural = api.data.split('/')[2];
        if(entity.plural != plural){
            if(!$.isEmptyObject(entity)){
                // render entity template
                $('#'+domId).append(
                    $('#item-tmpl').render(entity)
                );
                // add to navigation menu
                nav.add('entitiesList', entity.name);
                // schema loading! what a chore. good thing we can do other things at the same time
                timeout += 200;
                me.buildProperties(entity.name, timeout);
            }
            entity = {};
            entity.name = api.attr.entity;
            entity.plural = plural;
            entity.apis = [];
        }
        // entity relationship apis do not provide url parameter metadata..
        if(api.attr.params){
            params = api.attr.params;
        }
        if(api.data.indexOf('{') == -1){
            params = undefined;
        }
        // method level
        var methods  = [];
        $.each(api.children, function(i, method){
            methods.push({
                method      : method.attr.name,
                description : method.attr.description,
            });
        });
        // api level
        entity.apis.push({
            api     : api.attr.entity,
            url     : api.data,
            methods : methods,
            params  : params
        });
    });
}
EntityDocBuilder.prototype.buildProperties = function(entity, timeout){
    var me = this;
    if(entity != 'Resource'){ // base  entity should not show up in schema. 
        setTimeout(function(){
            magnet.schema.load(entity, function(schema){
                $('.'+entity+' .POST .api-property tbody, .'+entity+' .PUT .api-property tbody').html(
                    $('#properties-tmpl').render(schema)
                );
                $('.'+entity+' .POST .api-sample tbody, .'+entity+' .PUT .api-sample tbody').html(
                    $('#samplerequest-tmpl').render({sample:buildSample(schema)})
                );
            }, me.host);
        }, timeout);
    }
}
function buildSample(schema){
    var sample = {};
    $.each(schema, function(i, obj){
        if(obj.req == '1'){
            switch(obj.type){
                case "boolean": sample[obj.name] = true; break;
                case "date": sample[obj.name] = getDate()+" 12:00:00 PDT"; break;
                case "anyURI": sample[obj.name] = "entity:service:533927f7-f74c-4bb1-99ef-d631916afda9"; break;
                case "double": sample[obj.name] = .25; break;
                case "decimal": sample[obj.name] = 10.52; break;
                case "float": sample[obj.name] = 1000; break;
                case "long": sample[obj.name] = 1000; break;
                case "integer": sample[obj.name] = 1000; break;
                default: sample[obj.name] = "my"+obj.name; break;
            }
        }
    });
    return JSON.stringify(sample, null, 4);
}
function getDate(){
    var date = new Date();
    var year = date.getYear();
    if(year < 1000){
        year += 1900;
    }
    var month = date.getMonth()+1;
    if(month < 10){
        month = '0' + month;
    }
    var day = date.getDate();
    if(day < 10){
        day = '0' + day;
    }
    return year+'-'+month+'-'+day;
}
function getQuerystring(key){
    key = key.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regex = new RegExp("[\\?&]"+key+"=([^&#]*)");
    var qs = regex.exec(window.location.href);
    if(qs == null){
        return false;
    }else{
        return qs[1];
    }
}