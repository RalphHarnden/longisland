define(['jquery', 'backbone', "models/ResourceModel"], function($, Backbone, ResourceModel){
    var View = Backbone.View.extend({
        el: "#editor",
        initialize: function(){
            var me = this;
            // subscribe to editor reset event
            me.options.eventPubSub.bind("resetEditor", function(){
                me.reset();
            });
            // subscribe to editor initialization event
            me.options.eventPubSub.bind("initEditor", function(params){
                me.slideIn();
                me.initEditor(params);
            });
            // subscribe to field selection event
            me.options.eventPubSub.bind("selectField", function(index){
                if($(me.el).hasClass('hidden')){
                    me.slideIn();
                }
                me.doHighlight(index);
            });
            // subscribe to call API event
            me.options.eventPubSub.bind("callAPI", function(params){
                me.slideOut();
            });
            // reduce adapt font size to match viewport resolution
            me.reduceSize();
            $(window).resize(function(){
                me.reduceSize();
            });
            this.blist = [];
            //this.blist = ['magnetVersion', 'magnetActive', 'id', 'magnet-type'];
        },
        events: {
            "click .close": "slideOut",
            "keyup input": "validate",
            "click #undoChanges": "undoChanges",
            "click #saveChanges": "saveChanges",
            "click table tbody tr": "selectField"
        },
        // validate data and check for changes
        validate: function(){
            var me = this, hasChanged = false, properties = {}, qsparams = {}, isInvalid = false;
            $(this.el).find('table tbody input, table tbody select').each(function(){
                var property = $(this).attr('name');
                var isURI = $(this).attr('uri');
                var val = $.trim($(this).val());
                if($(this).attr('req').indexOf('*') != -1 && val == ''){
                    isInvalid = true;
                }
                if(me.model.attributes[property] !== stringToBool(val)){
                    hasChanged = true;
                }
                if(val != ''){
                    if(me.blist.length){
                        $.each(me.blist, function(index, item){
                            if(property != item){
                                if(isURI == '1'){
                                    qsparams[property] = stringToBool(val);
                                }else{
                                    properties[property] = stringToBool(val);
                                }
                            }
                        });
                    }else{
                        if(isURI == '1'){
                            qsparams[property] = stringToBool(val);
                        }else{
                            properties[property] = stringToBool(val);
                        }
                    }
                }
            });
            this.toggleEdits(hasChanged);
            return {
                values     : properties, 
                qsparams   : qsparams, 
                hasChanged : hasChanged, 
                isInvalid  : isInvalid
            };
        },
        // Todo: refactor with underscore templates
        render: function(model, schema){
            var str = '', req = '', uri = '', inputHTML = '';
            $.each(schema, function(i, obj){
                if(typeof model[obj.name] == 'undefined'){
                    model[obj.name] = '';
                }
                req = obj.req != "0" ? ' *' : '';
                uri = obj.uri != "1" ? '0' : '1';
                if(obj.type == 'boolean'){
                    inputHTML = selectBuilder(model[obj.name] || '', ' req="'+req+'" uri="'+uri+'" name="'+obj.name+'"');
                }else{
                    if(obj.type == 'date'){
                        inputHTML = '<input type="text" name="'+obj.name+'" req="'+req+'" uri="'+uri+'" class="datePicker" value="'+model[obj.name]+'" />';
                    }else{
                        inputHTML = '<input type="text" name="'+obj.name+'" req="'+req+'" uri="'+uri+'" value="'+model[obj.name]+'" />';
                    }
                }
                str += '<tr>\
                    <td width="20%"><h3>'+obj.name+' <a rel="tooltip" cap="'+obj.doc+'"><img src="images/info_icon.png" align="absmiddle" /></a></h3></td>\
                    <td width="5%"><span class="redtxt">'+req+'</span></td>\
                    <td width="15%">'+obj.type.toUpperCase()+'<br />'+(obj.uri != "1" ? 'DATA PROPERTY' : 'URL PARAMETER')+'</td>\
                    <td>'+inputHTML+'</td>\
                </tr>';
            });
            $(this.el).find('table tbody').html(str);
            $(this.el).find('table tbody tr:odd td').addClass('odd');
            $('.datePicker').each(function(){
                var dtDom = $(this);
                dtDom.Zebra_DatePicker({
                    offset : [-236, -20],
                    onSelect : function(date_formatted, date_as_yyyymmdd, date_as_js_date){
                        dtDom.val(date_formatted+' 12:00:00 PDT');
                    },
                    readonly_element : false
                });
                dtDom.data('Zebra_DatePicker').show();
            });
            this.bindTooltips();
        },
        // initialize editor
        initEditor: function(params){
            $(this.el).find('h4').html(params.req.entity.substr(0, 1).toUpperCase()+params.req.entity.substr(1));
            this.model = new ResourceModel();
            this.req = params.req;
            this.render(this.model.attributes, params.schema);
        },
        // close and reset editor
        reset: function(){
            $(this.el).find('table tbody').html('');
        },
        // slide in/out animations
        slideIn: function(){
            var me = this;
            me.toggleEdits();
            $(me.el).removeClass('hidden').stop().animate({
                opacity : 1,
                width   : '70%'
            }, 400, function(){
            });
        },
        slideOut: function(){
            var me = this;
            $(me.el).stop().animate({
                opacity : 0,
                width   : '0%'
            }, 400, function(){
                $(me.el).addClass('hidden');
            });
        },
        // select a field to trigger highlight events
        selectField: function(e){
            var field = $(e.currentTarget);
            this.options.eventPubSub.trigger("selectField", field.index());
        },
        doHighlight: function(index){
            var row = $(this.el).find('tbody tr').eq(index);
            if(!row.hasClass('sel')){
                $(this.el).find('tbody tr').removeClass('sel');
                row.addClass('sel');
                $(this.el).find('.table-container').scrollTo(row, 500, {onAfter:function(){ 
                    row.find('input, select').focus();
                }}); 
            }
        },
        // revert changes made to the edited data
        undoChanges: function(){
            var me = this;
            if($('#undoChanges').hasClass('red')){
                $(this.el).find('table tbody input, table tbody select').each(function(){
                    $(this).val(boolToString(me.model.attributes[$(this).attr('name')]));
                });
                me.toggleEdits();
            }
        },
        // save changes made to the edited data
        saveChanges: function(){
            var me = this;
            if($('#saveChanges').hasClass('red')){
                var object = this.validate();
                if(object.isInvalid){
                    magnet.messages.create({
                        "title"   : "Required Fields Left Blank",
                        "message" : "To save the data, all the required fields specified by <span style='color:red'>*</span> must have a value.",
                        "buttons" : [{
                            "title"  : "OK", 
                            "action" : function(){ 
                                magnet.messages.hide();
                            }
                        }]
                    });
                }else if(!object.hasChanged){
                    magnet.messages.create({
                        "title"   : "No Data Has Changed",
                        "message" : "No data has been changed. Please check your input and try again.",
                        "buttons" : [{
                            "title"  : "OK", 
                            "action" : function(){ 
                                magnet.messages.hide();
                            }
                        }]
                    });
                }else{
                    me.req.url = me.addQSParams(me.req.url, object.qsparams);
                    me.options.eventPubSub.trigger("callAPI", {req:me.req, values:object.values, qsparams:object.qsparams});
                }
            }
        },
        // toggle undo UI
        toggleEdits: function(hasChanged){
            if(hasChanged){
                $('#undoChanges').removeClass('gray').addClass('red');
            }else{
                $('#undoChanges').addClass('gray').removeClass('red');
            }
        },
        // bind tooltips
        bindTooltips: function(){
            $('a[rel=tooltip]').hover(function(e){
                var toolTip = $(this).attr('cap');
                $('<span class="tooltip"></span>').text(toolTip)
                    .appendTo('body')
                    .css('top', (e.pageY - 10) + 'px')
                    .css('left', (e.pageX + 20) + 'px')
                    .fadeIn('slow');
            }, function(){
                $('.tooltip').remove();
            }).mousemove(function(e){
                $('.tooltip')
                .css('top', (e.pageY - 10) + 'px')
                .css('left', (e.pageX + 20) + 'px');
            });
        },
        reduceSize: function(){
            if($(window).width() < 970){
                $(this.el).find('h3').addClass('reduced');
            }else{
                $(this.el).find('h3').removeClass('reduced');
            }
        },
        addQSParams : function(url, params){
            if(params !== undefined){
                $.each(params, function(name, val){
                    url = url.replace('{'+name+'}', val);
                });
            }
            return url;
        }
    });
    return View;
});