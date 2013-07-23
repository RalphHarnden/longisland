define(['jquery', 'backbone'], function($, Backbone){
    var View = Backbone.View.extend({
        initialize: function(){
            this.setElement('#error-alert');
            $(this.el).modal({
                show     : false, 
                keyboard : true, 
                backdrop : true
            });
        },
        display: function(vars){
            if(vars){   
                $(this.el).modal('show');
                $(this.el).find('.modal-header h3').text(vars.title);
                $(this.el).find(' .modal-body p').text(vars.content);
            }
        }
    });
    return View;
});