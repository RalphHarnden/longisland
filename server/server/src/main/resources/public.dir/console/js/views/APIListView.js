define(['jquery', 'backbone', "models/ResourceModel"], function($, Backbone, ResourceModel){
    var View = Backbone.View.extend({
        el: "#apiTree",
        initialize: function(){
            var me = this;
            // when api list model changes, rerender the api list view. this allows us to easily add/remove data from the view
            me.options.alm.bind('change', function(){
                me.render();
            });
            // retrieve api metadata from running magnet platform, then store to api list model
            me.getFromAPI('Entities', '/rest/entities.json', function(folderData){
                me.options.alm.set({
                    "data"     : 'Entities',
                    "children" : magnet.tree.formatJSON(folderData)
                });
            });
            // subscribe to call API event
            me.options.eventPubSub.bind("callAPI", function(params){
                me.call(params, function(){
                    // failback
                });
            });
        },
        events: {
            "click .jstree-leaf a": "doClick"
        },
        render: function(){
            // using latest api list model data, build jsTree tree. Todo: create a lightweight tree view. Todo: build list from freemarker template on server side to further increase speed
            magnet.tree.create(this.$el.attr('id'), this.options.alm.attributes);
        },
        // handle click of api list item
        doClick: function(e){
            var me = this;
            var req = me.getHierarchy($(e.currentTarget));
            magnet.schema.load(req.entity, function(schema){
                if(req.method == 'GET' || req.method == 'DELETE'){
                    schema = [];
                }
                if(req.url.indexOf('{id}') != -1){
                    schema.push({"name":"id", "req":"1", "type":"string", "doc":"Unique identifier for the entity, formatted as entity:service:id", "uri":"1"});
                }
                // if schema is empty, call api directly. otherwise initiate editor
                if(schema.length){
                    me.options.eventPubSub.trigger("initEditor", {req:req, schema:schema});
                }else{
                    me.options.eventPubSub.trigger("callAPI", {req:req});
                }
            });
        },
        // get api details by navigating dom hierarchy
        getHierarchy: function(dom){
            var child = dom.parent('li');
            var parent = child.parent('ul').parent('li');
            var method = child.attr('name');
            var url = parent.attr('uri');
            var entity = parent.attr('entity');
            return {entity:entity, url:url, method:method};
        },
        // retrieve explorer metadata from API
        getFromAPI: function(folderName, url, callback){
            var me = this;
            /*
            var folderData = magnet.localStore.retrieve(folderName, 'apis');
            if(folderData){
                callback(folderData);
            }else{
            */
            magnet.dataMgr.call(url, 'GET', 'json', 'application/json', {}, function(folderData, status, xhr){
                //magnet.localStore.store(folderName, folderData, 'apis');
                callback(folderData);
            }, false, function(xhr, ajaxOptions, thrownError){
                //magnet.dataMgr.logHTTP(data, xhr, thrownError, me.hl.insert(uri, 'GET'))
                magnet.messages.create({
                    "title"   : "Connection Failure - " + url,
                    "message" : "The application has no access to the API and cannot continue. Please check your connection and try again.",
                        "buttons" : [{
                            "title"  : "OK", 
                            "action" : function(){ 
                                magnet.messages.hide();
                            }
                        }]
                });
            });
        },
        call: function(params, failback){
            magnet.dataMgr.call(params.req.url, params.req.method, 'json', 'application/json', params.values || undefined, function(data){
                //magnet.generalMenu.load('configMenu');
            }, true, function(){
                magnet.messages.create({
                    "title"   : "Request Failure - MAGNET API: "+params.req.url,
                    "message" : " Console experienced an error while interacting with the platform.",
                    "buttons" : [{
                        "title"  : "OK", 
                        "action" : function(){ 
                            magnet.messages.hide();
                            if(typeof(failback) == typeof(Function)){
                                failback();
                            }
                        }
                    }]
                });
            });
        }
    });
    return View;
});