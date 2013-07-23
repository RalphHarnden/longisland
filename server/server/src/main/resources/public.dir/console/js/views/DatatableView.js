define(['jquery', 'backbone', "models/ResourceModel"], function($, Backbone, ResourceModel){
    var View = Backbone.View.extend({
        el: "#datatable",
        initialize: function(){
            var me = this;
            me.stores = {};
            // subscribe to reset datatable event
            me.options.eventPubSub.bind("resetDatatable", function(params){
                me.stores.config = params;
                $(me.el).find('tbody').html('');
                me.refreshHeaders(params);
                me.toggleAddBtn();
                $(me.el).removeClass('hidden');
            });
            // when a model is added to magnet entity collection, add new row
            me.options.oec.on('add', function(model){
                me.append(model.attributes);
            });
            // subscribe to field select event
            me.options.eventPubSub.bind("selectField", function(index){
                me.selectField(index);
            });
            // subscribe to row update event
            me.options.eventPubSub.bind("updateRow", function(params){
                me.options.eventPubSub.trigger("resetEditor");
                if(params.rowId == 'new'){
                    me.deleteRow('new');
                    me.options.oec.add(new ResourceModel(params.model));
                }else{
                    me.append(params.model.attributes, params.rowId);
                }
            });
            // fix for ie < 10 min-width incompatibilities
            if($.browser.msie){
                $('table.thead').remove();
                $(me.el).find('table').prepend('<thead />');
            }
        },
        events: {
            "click #addRow": "insertRow",
            "click #deleteRows": "deleteRows",
            "click tbody td": "select",
            "change input[type='checkbox']": "toggleDeleteBtn",
            "change #selectMultiple": "selectMultiple"
        },
        // bind event actions
        insertRow: function(){
            if($(this.el).find('tbody tr[o="new"]').length){
                this.select(null, $(this.el).find('tbody tr:last'));
            }else{
                this.append({
                    magnetId : 'new'
                });
                this.select(null, $(this.el).find('tbody tr:last'));
                this.toggleAddBtn();
            }
        },
        deleteRow: function(rowId){
            $(this.el).find('tbody tr[o="'+rowId+'"]').remove();
        },
        // delete all selected rows
        deleteRows: function(){
            var me = this;
            if($('#deleteRows').hasClass('red')){
                $(this.el).find('tbody tr').each(function(){
                    if($(this).find('input[type="checkbox"]').prop('checked')){
                        var rowId = $(this).attr('o');
                        if(rowId != 'new'){
                            var model = me.options.oec.get(rowId);
                            model.destroy();
                            me.deleteRow(rowId);
                        }
                    }
                });
            }
        },
        // bind multiple checkbox selection UI
        selectMultiple: function(e){
            $(this.el).find('tbody tr input[type="checkbox"]').prop('checked', $(e.currentTarget).prop('checked'));
            this.toggleDeleteBtn();
        },
        // toggle delete row button
        toggleDeleteBtn: function(){
            var hasSelections = false;
            $(this.el).find('tbody tr input[type="checkbox"]').each(function(){
                if($(this).prop('checked')){
                    hasSelections = true;
                }
            });
            if(hasSelections){
                $('#deleteRows').removeClass('gray').addClass('red');
            }else{
                $('#deleteRows').addClass('gray').removeClass('red');
            }
        },
        // toggle add new row button
        toggleAddBtn: function(){
            if($(this.el).find('tbody tr[o="new"]').length){
                $('#addRow').addClass('gray').removeClass('red');
            }else{
                $('#addRow').removeClass('gray').addClass('red');
            }
        },
        // select a field/row and initialize Editor
        select: function(e, dom){
            var field, row;
            if(dom){
                field = dom.find('td:first');
                row = dom;
            }else{
                field = $(e.currentTarget);
                row = $(e.currentTarget).closest('tr');
            }
            // handle row events first
            if(!row.hasClass('sel')){
                $(this.el).find('tbody tr').removeClass('sel');
                row.addClass('sel');
                this.options.eventPubSub.trigger("initEditor", row.attr('o'));
            }
            // handle field events after Editor has been initialized
            this.options.eventPubSub.trigger("selectField", field.index()-1);
        },
        // highlight and focus on a field within selected row
        selectField: function(index){
            var field = $(this.el).find('tbody tr.sel td').eq(index+1);
            if(!field.hasClass('sel')){
                field.closest('tr').find('td').removeClass('sel');
                field.addClass('sel');
                field.focus();
            }
        },
        // refresh datatable headers
        refreshHeaders: function(params){
            var headers = '';
            headers += '<th class="chk"><input type="checkbox" name="selectMultiple" id="selectMultiple" /></th>';
            $.each(params.headers, function(i, obj){
                headers += '<th>'+obj.name+'</th>';
            });
            $(this.el).find('thead').html('<tr>'+headers+'</tr>');
        },
        // append a datatable row with provided data
        append: function(data, rowId){
            var str = '', pairs = {};
            str += '<td class="chk"><input type="checkbox" class="selector" name="selector" /></input></td>';
            $.each(this.stores.config.headers, function(i, obj){
                if(typeof data[obj.name] === 'undefined'){
                    data[obj.name] = '';
                }
                str += '<td><input type="text" value="'+data[obj.name]+'" readonly="readonly" /></td>';
            });
            if(rowId){
                $(this.el).find('tbody tr[o="'+rowId+'"]').replaceWith('<tr o="'+data.magnetId+'">'+str+'</tr>');
            }else{
                $(this.el).find('tbody').append('<tr o="'+data.magnetId+'">'+str+'</tr>');
            }
        }
    });
    return View;
});