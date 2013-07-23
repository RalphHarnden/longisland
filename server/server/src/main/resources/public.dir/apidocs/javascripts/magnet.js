var magnet = {}
magnet.global = {
    doProxy   : false,
    proxyPath : '/proxy',
    host      : 'localhost',
    port      : 8080
}
magnet.tabs = {
    loadTabmenu : function(id, isConfigToggler){
        var me = this;
        $('#'+id+' .tab_content').hide();
        $('#'+id+' ul.tabs li:first').addClass('active').show();
        $('#'+id+' .tab_content:first').show();
        $('#'+id+' ul.tabs li').click(function(){
            if(!$(this).hasClass('invalid')){
                $('#'+id+' ul.tabs li').removeClass('active');
                $(this).addClass('active');
                $('#'+id+' .tab_content').hide();
                if(isConfigToggler){
                    $('#'+id+' '+$(this).find('a').attr('href')+'.'+me.getPanelSetting(id)).show();
                }else{
                    $('#'+id+' '+$(this).find('a').attr('href')).show();
                }
            }
            return false;
        });
        if(isConfigToggler){
            me.loadConfigToggler(id, function(){
                $('#'+id+' .tab_content').hide();
                $($('#'+id+' ul.tabs li.active').find('a').attr('href')+'.'+me.getPanelSetting(id)).show();
            });
        }
    },
    getActiveTab : function(id){
        return $('#'+id+' ul.tabs li[class="active"] a').attr('href');
    },
    setDefaultTab : function(id){
        if(this.getActiveTab(id) != '.preview'){
            $('#'+id+' ul.tabs li').removeClass('active');
            $('#'+id+' .tab_content').hide();
            $('#'+id+' ul.tabs li:first').addClass('active').show();
            $('#'+id+' .tab_content:first').show();
        }
    },
    loadConfigToggler : function(id, callback){
        $('#'+id).closest('.container').find('.panelConfig a.tog').click(function(e){
            e.preventDefault();
            if(!$(this).hasClass('invalid')){
                $('#'+id).closest('.container').find('.panelConfig a.tog').removeClass('active'); 
                $(this).addClass('active'); 
                callback();
            }
        });
    },
    getPanelSetting : function(id){
        return $('#'+id).closest('.container').find('.panelConfig a.active').attr('id');
    }
}
magnet.dataMgr = {
    urlDomId  : 'apiUrl',
    typeDomId : 'reqType',
    initPoll  : true,
    call : function(loc, method, dataType, data, callback, doLogging, failFn, isPolling, pollDelay){
        var me = this;
        var dataStr = null;
        if(!$.isEmptyObject(data)){
            dataStr = JSON.stringify(data);
        }
        var reqObj = {
            hostname : magnet.global.host,
            port     : magnet.global.port,
            dataType : dataType, 
            url      : loc,  
            method   : method,
            data     : dataStr
        }
        if(doLogging){
            $('#'+me.urlDomId).val(reqObj.url); 
            $('#'+me.typeDomId).val(reqObj.method); 
        }
        if(doLogging && magnet.global.doProxy){
            reqObj = {
                dataType : dataType, 
                url      : magnet.global.proxyPath,  
                method   : 'POST', 
                data     : reqObj
            }
        }
        $.ajax({  
            type        : reqObj.method,  
            url         : reqObj.url,  
            dataType    : reqObj.dataType,
            contentType : 'application/json',
            data        : reqObj.data,  
            error : function(xhr, ajaxOptions, thrownError){
                console.log(thrownError);
                if(doLogging){
                    var resObj = magnet.utils.convertHeaderStrToObj(xhr);
                    var reqObj = magnet.utils.getInfoHeader(resObj['-Console-Request']);
                    delete resObj['-Console-Request'];
                    me.updateTables('request_header', reqObj);
                    me.updateTables('request_raw_header', magnet.utils.highLightJSON(reqObj));
                    me.updateTables('request_data', '');
                    me.updateTables('request_raw_data', '');
                    me.updateTables('response_header', resObj);
                    me.updateTables('response_raw_header', magnet.utils.highLightJSON(resObj));
                    me.updateTables('response_data', thrownError);
                    me.updateTables('response_raw_data', thrownError);
                }
                if(failFn){
                    failFn(xhr, ajaxOptions, thrownError);
                }
            },
            success : function(result, status, xhr){
                if(doLogging){
                    if(data !== undefined){
                        me.updateTables('request_data', data);
                        me.updateTables('request_raw_data', magnet.utils.highLightJSON(data));
                    }
                    if(xhr !== undefined){
                        var resObj = magnet.utils.convertHeaderStrToObj(xhr);
                        if(typeof resObj['-Console-Request'] != 'undefined'){
                            var reqObj = magnet.utils.getInfoHeader(resObj['-Console-Request']);
                            delete resObj['-Console-Request'];
                        }else{
                            var reqObj = '';
                        }
                        me.updateTables('request_header', reqObj);
                        me.updateTables('request_raw_header', magnet.utils.highLightJSON(reqObj));
                        me.updateTables('response_header', resObj);
                        me.updateTables('response_raw_header', magnet.utils.highLightJSON(resObj));
                    }
                    if(result !== undefined){
                        me.updateTables('response_data', result);
                        me.updateTables('response_raw_data', magnet.utils.highLightJSON(result));
                    }
                }
                me.loadTableStyles();
                if(isPolling && result != '1' && me.initPoll){
                    console.log('..polling again in '+pollDelay+' milliseconds..');
                    setTimeout(function(){
                        me.sendData(loc, method, dataType, data, callback, doLogging, failFn, isPolling, pollDelay);
                    }, pollDelay);
                }else{
                    return callback(result, status, xhr);
                }
                return callback(result, status, xhr);
            }
        });
        return false;
    },
    updateTables : function(domId, data){
        $('#'+domId).html('');
        if(data !== undefined && data !== null){
            if(typeof data === 'string'){
                $('#'+domId).html('<pre>'+data+'</pre>');
            }else if(typeof data === 'object'){
                $('#'+domId).html('<table id="table_'+domId+'"><thead><tr><th>Name</th><th>Value</th></tr></thead><tbody></tbody></table>');
                $.each(data, function(id, val){
                    if(typeof val === 'object' || typeof val === 'array'){
                        var uniqueId = magnet.localStore.getID();
                        $('#'+domId+' tbody').append('<tr><td>'+id+'</td><td><div id="'+'tree'+uniqueId+'">'+magnet.tree.toTreeObj(val)+'</div></td></tr>');
                        magnet.tree.create('tree'+uniqueId);
                    }else{
                        $('#'+domId+' tbody').append('<tr><td>'+id+'</td><td>'+val+'</td></tr>');
                    }
                });
            }
        }
    },  
    loadTableStyles : function(){
        var tblAry = ['request_header', 'request_data', 'response_header', 'response_data'];
        $.each(tblAry, function(i, val){
            $('#'+val+' tr:even').removeClass().addClass('tr_even');
            $('#'+val+' tr:odd').removeClass().addClass('tr_odd');
        });
    }
}
magnet.propertyMenu = {
    domId : 'propertyMenu',
    blist : ['magnetVersion', 'magnetActive', 'id', 'magnet-type'],
    init : function(choices, schema, cbComplete, cbCancel){
        var me = this, str = '', req = '', reqTag = '0', inputHTML = '', reqVal = '', uri = '', state = '', propertyType = '', hasDate = false;
        $('#'+me.domId+' p').html('');
        if(!$.isEmptyObject(schema)){
            me.show();
            str = '';
            if(choices !== null){
                var opts = '<option value="" selected="selected"></option>';
                $.each(choices, function(i, val){
                    opts += '<option value="'+val.name+'">'+val.name+'</option>';
                });
                $.each(schema, function(i, val){
                    if(opts.indexOf('value="'+val+'"') != -1){
                        opts = opts.replace(' selected="selected"', '').replace('value="'+val+'"', 'value="'+val+'" selected="selected"');
                    }else{
                        opts = opts.replace(' selected="selected"', '').replace('value=""', 'value="" selected="selected"');
                    }
                    str += '<div class="attrItem"><input type="text" name="req_'+val+'" value="'+val+'" readonly="readonly" req="1" class="readonly" /><select class="req_'+val+'">'+opts+'</select><div class="attrError"></div></div>';
                });
            }else{
                $.each(schema, function(i, obj){
                    uri = '0';
                    propertyType = 'DATA OBJECT';
                    if(obj.uri !== undefined){
                        uri = '1';
                        propertyType = 'QUERYSTRING';
                    }
                    if(obj.req != "0"){
                       req = ' <span style="color:red">*</span>';
                       reqTag = '1';
                    }else{
                        req = '';
                        reqTag = '0';
                    }
                    if(obj.type == 'boolean'){
                        inputHTML = '<select><option value="true">True</option><option value="false">False</option></select>';
                    }else{
                        reqVal = obj.val !== undefined ? obj.val : '';
                        if(obj.type == 'date'){
                            hasDate = true;
                            inputHTML = '<input type="text" name="req_'+obj.name+'" class="datePicker" value="'+reqVal+'" />';
                        }else{
                            inputHTML = '<input type="text" name="req_'+obj.name+'" value="'+reqVal+'" />';
                        }
                    }
                    state = '';
                    $.each(me.blist, function(index, val){
                        if(obj.name == val && uri == '0'){
                            state = ' hidden';
                        }
                    });
                    str += '<div class="attrItem"'+state+'>'+req+'<input type="text" name="req_'+obj.name+'" value="'+obj.name+'" req="'+obj.req+'" class="readonly" uri="'+uri+'" readonly="readonly" />'+inputHTML+'<div class="attrDescription">'+obj.doc+'</div><div class="attrError"></div><div class="details type">'+propertyType+'</div><div class="details">'+obj.type.toUpperCase()+'</div></div>';
                });
            }
            $('#'+me.domId+' p').html(str);
            if(hasDate){
                $('.datePicker').each(function(){
                    var dtDom = $(this);
                    $(this).Zebra_DatePicker({
                        onSelect: function(date_formatted, date_as_yyyymmdd, date_as_js_date){
                            dtDom.val(date_formatted+' 12:00:00 PDT');
                        },
                        readonly_element:false
                    });
                });
            }
            $('#'+me.domId+' .attrContinue').unbind('click').click(function(){
                me.validate(choices, function(b){
                    me.hide();
                    cbComplete(b.properties, b.uriProperties);
                });
            });
            $('#'+me.domId+' .attrCancel').unbind('click').click(function(){
                me.hide();
                if(typeof(cbCancel) == typeof(Function)){
                    cbCancel();
                }
            });
        }else{
            me.hide();
            cbComplete({}, {});
        }
    },
    hide : function(){
        $('#'+this.domId).stop(true, true).fadeOut('fast');
        $('#properties').hide('fast').animate({width:'0%', left:'0%'}, 'fast');
        $('#data2').animate({width:'75%', left:'25%'}, 'fast');
    },
    show : function(){
        $('#'+this.domId).fadeIn('fast');
        $('#properties').show('fast').animate({width:'25%', left:'25%'}, 'fast');
        $('#data2').animate({width:'50%', left:'50%'}, 'fast');
    },
    validate : function(choices, callback){
        var properties = {}, uriProperties = {};
        var isValid = true;
        $('.attrItem').each(function(i, val){
            var property = $(this).find('input[class="readonly"]');
            var propertyValue = $(this).find('input:not([class="readonly"])');
            var entryLength = choices !== null ? $.trim($(this).find('select').val()).length : $.trim(propertyValue.val()).length;
            if(entryLength < 1 && property.attr('req') == "1"){
                $(this).css('background-color', '#F7D7D7').css('border', 'solid 1px #C65D5D').find('.attrError').html('*field empty');
                isValid = false;
            }else{
                var pVal = $(this).find('select').val() || propertyValue.val();
                if(pVal == 'true'){
                    pVal = true;
                }
                if(pVal == 'false'){
                    pVal = false;
                }
                if(!entryLength < 1 || typeof pVal === 'boolean'){
                    $(this).css('background-color', '#C8D8F0').css('border', 'solid 1px #668FC3').find('.attrError').html('');
                    if(property.attr('uri') == '1'){
                        uriProperties[property.val()] = pVal;
                    }else{
                        properties[property.val()] = pVal;
                    }
                }
            }
        });
        if(isValid){
            callback({properties:properties,uriProperties:uriProperties});
        }else{
            magnet.messages.create({
                "title"   : "Blank Fields Detected",
                "message" : "To view the control, you must bind all required properties to the available properties under the drop down menus.",
                "buttons" : [{
                    "title"  : "OK", 
                    "action" : function(){ 
                        magnet.messages.hide();
                    }
                }]
            });
        }
        return false;
    }
}
magnet.localStore = {
    uniqueId : 500,
    init : function(host, port){
        this.uniqueId == this.getUniqueId();
        if(host && port){
            magnet.localStore.store('serverhost', host, 'general');
            magnet.localStore.store('serverport', port, 'general');
        }
    },
    store : function(key, data, groupId){
        if(groupId){
            var store = amplify.store(groupId) || {};
            store[key] = data;
            amplify.store(groupId, store);
        }else{
            amplify.store(key, data);
        }
    },
    retrieve : function(key, groupId){
        if(key && groupId){
            try{
                return amplify.store(groupId)[key];
            }catch(e){
                return null;
            };
        }else if(key){
            return amplify.store(key);
        }else{
            return amplify.store();
        }
    },
    remove : function(key, groupId){
        if(key && groupId){
            var store = amplify.store(groupId);
            delete store[key];
            amplify.store(groupId, store);
        }else if(key){
            amplify.store(key, null);
        }else{
            $.each(amplify.store(), function(key, obj){
                amplify.store(key, null);
            });
        }
    },
    getUniqueId : function(){
        var me = this;
        if(me.retrieve('displayPanel') !== undefined){
            $.each(me.retrieve('displayPanel'), function(key, obj){
                if(magnet.utils.isNumeric(key) && key > me.uniqueId){
                    me.uniqueId = key;
                }
            });
        }
        ++me.uniqueId;
    },
    getUniquePage : function(){
        var me = this;
        if(me.retrieve('builderPanel') !== undefined){
            $.each(me.retrieve('builderPanel'), function(key, obj){
                if(magnet.utils.isNumeric(key) && key > me.uniqueId){
                    me.uniqueId = key;
                }
            });
        }
        return ++me.uniqueId;
    },
    getID : function(){
        return ++this.uniqueId;
    }
}
magnet.tree = {
    domId   : 'controlTree',
    enabled : true,
    init : function(clickback, callback, showChild){
        var me = this;
        magnet.dataMgr.call('/rest/entities.json', 'GET', 'json', {}, function(result){
            $('#data').removeClass('hidden');
            magnet.tree.create('apiTree', me.formatJSON(result, showChild), function(selNode, parNode){
                if(parNode != -1){
                    if(parNode.attr('url') !== undefined || parNode.attr('uri') !== undefined){
                        me.enable();
                        clickback(selNode, parNode);
                    }
                }else{
                    me.enable();
                    clickback(selNode);
                }
            }, callback(result));
        }, false, function(){
            magnet.messages.create({
                "title"   : "Connection Failure - entities.json",
                "message" : "The Developer Suite has no access to the APIs and cannot continue. Please check your connection and refresh the page again."
            });
        });
    },
    formatJSON : function(data, showChild){
        var base = [], entityObj = {};
        $.each(data, function(i, obj){
            var currId = obj.data.split('/')[3];
            if(typeof currId === 'undefined'){
                if(!$.isEmptyObject(entityObj)){
                    base.push(entityObj);
                }
                entityObj = {};
                entityObj.data = obj.attr.entity.slice(0,1).toUpperCase()+obj.attr.entity.slice(1);
            }
            if(!showChild){
                entityObj.children.push(obj);
            }
        });
        base.push(entityObj);
        return base;
    },
    disable : function(){
        $('#'+this.domId).addClass('invalid');
        this.enabled = false;
    },
    enable : function(){
        $('#'+this.domId).removeClass('invalid');
        this.enabled = true;
    },
    create : function(id, nodes, clickback, callback){
        var me = this;
        var tree = $('#'+id);
        if(nodes){
            tree.jstree({
                "core"      : {"animation"    : 200},
                "json_data" : {"data"         : nodes},
                "ui"        : {"select_limit" : 1},
                "plugins"   : ["themes", "json_data", "ui"]
            }).bind("select_node.jstree", function(event, data){ 
                if(data.rslt.obj.hasClass('jstree-leaf')){
                    clickback(data.rslt.obj, data.inst._get_parent(data.rslt.obj));
                }
            }).bind("loaded.jstree", function(event, data){
                if(id != 'apiTree'){
                    tree.jstree("open_all");
                }
                if(typeof(callback) == typeof(Function)){
                    callback();
                }
            }).delegate("a", "click", function(event, data){
                event.preventDefault();
            });
        }else{
            tree.jstree({
                "core"    : {"animation"    : 200},
                "ui"      : {"select_limit" : 1},
                "themes"  : {"icons"        : false},
                "plugins" : ["themes", "html_data", "ui"]
            });
        }
    },
    insert : function(obj){
        $('#'+this.domId).jstree("create_node", $("#JQueryMobile"), "inside", {"data" : obj.title, "attr" : { "dir" : "jquery_mobile", "lsId" : obj.id, "file" : 'ct'+obj.id}});
        magnet.controls.setDeleteState();
    },
    remove : function(id){
        $('#'+this.domId+' li[lsid='+id+']').remove();
        magnet.controls.setDeleteState();
    },
    toTreeObj : function(data){
        var me = this;
        var str = '';
        switch ($.type(data)){
        case 'string':
            str += '<ul><li><a href="#">'+data+'</a></li></ul>';
            break;
        case 'array':
            $.each(data, function(){
                str += me.toTreeObj(this);
            });
            break;
        default:
            $.each(data, function(i, val){
                if(typeof val == 'string' || typeof val == 'number'){
                    i += ': <span class="SYNstring">'+val+'</span>';
                }
                str += '<ul><li><a href="#">'+i+'</a>';
                if(typeof val == 'array' || typeof val == 'object'){
                    str += me.toTreeObj(this);
                }
                str += '</li></ul>';
            });
        } 
        return str.replace('undefined', '');
    }
}
magnet.schema = {
    cache : {},
    load : function(type, cb, host){
        var me = this;
        if(me.cache[type] === undefined){
            $.support.cors = true;
            $.ajax({
                url      : host+'/rest/schemas/'+type,
                type     : 'GET',
                timeout  : 30000,
                dataType : 'xml', 
                success : function(xml){
                    me.cache[type] = me.parse(type, xml);
                    cb(me.cache[type].slice(0));
                },
                error: function(xhr, status, options){
                    alert('Connection Failure');
                }
            });
        }else{
            cb(me.cache[type].slice(0));
        }
    },
    parse : function(type, xml){
        var a = [], o = {};
        /* no more magnet-uri?
        o = {
            name : 'magnet-type',
            type : 'string',
            req  : '1',
            doc  : 'type of magnet entity',
            val  : type
        };
        a.push(o);
        */
        $(xml).find('xs\\:element,element').each(function(){
            o = {};
            o['name'] = $(this).attr('name');
            o['type'] = $(this).attr('type').replace('xs:', '');
            o['req'] = $(this).attr('minOccurs');
            o['doc'] = $(this).find('xs\\:documentation, documentation').text();
            a.push(o);
        });
        return a;
    }
}
magnet.resizable = {
    active   : false,
    firstEx  : false,
    minWidth : 200,
    init : function(id, id2, sensitivity){
        var barId = 'resizable'+magnet.localStore.getID();
        $(id).append('<div id="'+barId+'" class="resizablePanel"></div>');
        this.bindClick(barId, id, id2, sensitivity);
    },
    bindClick : function(barId, id, id2, sensitivity){
        var me = this;
        var xCoord = '';
        $('#'+barId).click(function(){
            me.active = true;
            $('.container > div:not(.heading)').css('opacity', '.3');
            $('iframe').hide();
            var divWidth = $(id).width();
            var div2Width = $(id2).width();
            if(!me.firstEx){ // FF bug fix - FF is adding a pixel when resizing panels which causes layout issues
                divWidth -= 2;
            }
            $(document).mousemove(function(e){
                if(e.pageX > xCoord){
                    if(div2Width > me.minWidth){
                        divWidth += sensitivity;
                        div2Width -= sensitivity;
                        $(id2).css('width', div2Width+'px');
                        $(id).css('width', divWidth+'px');
                    }
                }else if(e.pageX < xCoord){
                    if(divWidth > me.minWidth){
                        divWidth -= sensitivity;
                        div2Width += sensitivity;
                        $(id).css('width', divWidth+'px');
                        $(id2).css('width', div2Width+'px');
                    }
                }
                xCoord = e.pageX;
            });
            me.bindStop(id);
            me.firstEx = true;
        });
    },
    bindStop : function(id){
        var me = this;
        $(document).unbind('mouseup').mouseup(function(){
            if(magnet.resizable.active){
                $('.container > div:not(".header")').css('opacity', '');
                $('iframe').show();
                magnet.resizable.active = false;
                $(document).unbind('mousemove');
            }
        });
    }
}
magnet.sampleData = {
    data  : {},
    lines : 11,
    generate : function(schemas){
        var me = this, ary = [];
        for(i=1;i<this.lines;i++){
            var obj = {};
            $.each(schemas, function(j, schema){
                obj[schema.name] = me.create(schema.name, i, schema.type);
            });
            ary.push(obj);
        }
        return ary;
    },
    create : function(name, i, type){
        var item = '';
        var rnum = Math.floor(Math.random()*(99999-10000 + 1)+10000);
        switch(type){
        case 'integer': case 'double': case 'long': 
            item = rnum;
            break;
        case 'decimal': 
            item = rnum+parseFloat('.'+i);
            break;
        case 'boolean':
            item = (Math.floor(Math.random()*(2-1 + 1)+1))%2 == 0 ? 'true' : 'false';
            break;
        default:
            item = name+Math.round((rnum/10));
        }
        return item;
    }
}
magnet.generalMenu = {
    defaults : {
        configMenu : {
            SYNkey     : "gray", 
            SYNstring  : "blue", 
            SYNnumber  : "red", 
            SYNboolean : "green", 
            SYNnull    : "black",
            serverhost : "localhost",
            serverport : 8080
        }
    },
    init : function(id){
        this.load(id);
        this.bindClick(id);
    },
    load : function(id){
        if(magnet.localStore.retrieve(id) !== undefined){
            this.setMenu(magnet.localStore.retrieve(id));
        }else{
            this.reset(id);
        }
    },
    bindClick : function(id){
        var me = this;
        $('#'+id+' .save').click(function(){
            $('#'+id).hide('fast').animate({width:'0', height:'0'}, 'fast');
            var obj = me.getMenu(id);
            magnet.localStore.store(id, obj);
            me.setMenu(obj);
        });
        $('#'+id+' .reset').click(function(){
            $('#'+id).hide('fast').animate({width:'0', height:'0'}, 'fast');
            me.reset(id);
        });
        $('#'+id+' .cancel').click(function(){
            $('#'+id).hide('fast').animate({width:'0', height:'0'}, 'fast');
            me.load(id);
        });
    },
    setMenu : function(obj){
        $.each(obj, function(i, val){
            $('#'+i).val(val);
            $('.'+i).css('color', val);
        });
        return obj;
    },
    getMenu : function(id){
        var obj = {};
        $('#'+id+' .menuOpt').each(function(){
            obj[$(this).attr('id')] = $(this).val();
        });
        return obj;
    },
    reset : function(id){
        magnet.localStore.store(id, this.defaults[id]);
        return this.setMenu(this.defaults[id]);
    }
}
magnet.messages = {
    msgSel   : 'messages',
    msgTmpl  : 'messagesTmpl',
    bgk      : 'msgBGK',
    create : function(message){
        var me = this;
        $('#'+me.bgk).fadeIn('fast');
        var uniqueID = magnet.localStore.getID();
        if(!message.buttons){
            message.buttons = [];
        }
        $.each(message.buttons, function(i, obj){
            message.buttons[i].id = magnet.utils.cleanChar(obj.title)+''+uniqueID;
        });
        $('#'+me.msgSel).html(
            $('#'+me.msgTmpl).render({
                "title"   : message.title, 
                "message" : message.message, 
                "buttons" : message.buttons
            })
        ).fadeIn('fast');
        $.each(message.buttons, function(i, button){
            $('div#'+button.id).click(function(){
                button.action();
            });
        });
    },
    hide : function(){
        $('#'+this.msgSel+', #'+this.bgk).fadeOut('fast');
    }
}
magnet.utils = {
    txtDefaults : function(sel){
        $(sel).focus(function(){
            if(this.value == this.defaultValue){
                this.value = '';
                $(this).css('color', '#000');
            }
        }).blur(function(){
            if(this.value == ''){
                this.value = this.defaultValue;
                $(this).css('color', '#555');
            }
        })
    },
    strToObj : function(str){
        var obj = {};
        if(str !== undefined){
            var ary = str.split(' ');
            $.each(ary, function(i, val){
                obj[val] = val;
            });
        }
        return obj;
    },
    getAttributes : function(obj){
        var attributes = {}; 
        if(!$.isEmptyObject(obj) && obj[0] !== undefined){
            $.each(obj[0], function(name, val){
                attributes[name] = name;
            }); 
        }
        return attributes;
    },
    getvalidJSON : function(str){
        try{
            str = $.parseJSON(str);
        }catch(e){
            return false;
        };
        return str;
    },
    getvalidXML : function(str){
        try{
            str = $.parseXML(str);
        }catch(e){
            console.log(e);
            return false;
        };
        return str;
    },
    convertHeaderStrToObj : function(xhr){
        var dataObj = {};
        $.each(xhr, function(i, val){
            if(($.type(val) == 'string' || $.type(val) == 'number')  && i != 'responseText'){
                dataObj[i] = val;
            }
        });
        $.each(xhr.getAllResponseHeaders().split('\n'), function(i, line){
            var ary = $.trim(line).split(': ');
            if(ary.length > 1){
                dataObj[ary[0]] = ary[1];
            }
        });
        return dataObj;
    },
    getInfoHeader : function(str){
        var hdrAry = str.split(','), obj = {};
        $.each(hdrAry, function(i, val){
            var ary = Base64.decode($.trim(val)).match(/([^ ]*) (.*)/);
            if(ary != null){
                obj[ary[1]] = ary[2];
            }
        });
        return obj;
    },
    getCurrentTime : function(){
        var now    = new Date();
        var hour   = now.getHours();
        var minute = now.getMinutes();
        var second = now.getSeconds();
        var ap = 'AM';
        if (hour   > 11) { ap = 'PM';             }
        if (hour   > 12) { hour = hour - 12;      }
        if (hour   == 0) { hour = 12;             }
        if (hour   < 10) { hour   = '0' + hour;   }
        if (minute < 10) { minute = '0' + minute; }
        if (second < 10) { second = '0' + second; }
        return hour+':'+minute+':'+second+' '+ap;
    },
    cleanChar : function(str){
        return str.replace(/[^a-zA-Z0-9]+/g, '');
    },
    htmlEncode : function(str){
        if(str){
            return $('<div />').text(str).html().replace(/&nbsp;/g, " ");
        }else{
            return '';
        }
    },
    htmlDecode : function(str){
        if(str){
            return $('<div />').html(str).text();
        }else{
            return '';
        }
    },
    isNumeric : function(n){
        return !isNaN(parseFloat(n)) && isFinite(n);
    },
    highLightJSON : function(str){
        if(str === undefined || str === null){
            return '';
        }
        if(typeof str != 'string'){
             str = JSON.stringify(str, null, 6);
        }
        str = str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        return str.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match){
            var cls = 'number';
            if(/^"/.test(match)){
                cls = /:$/.test(match) ? 'key' : 'string';
            }else if(/true|false/.test(match)){
                cls = 'boolean';
            }else if(/null/.test(match)){
                cls = 'null';
            }
            return '<span class="SYN'+cls+'">'+match+'</span>';
        });
    }
}