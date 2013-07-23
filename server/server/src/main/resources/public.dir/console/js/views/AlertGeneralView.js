define(['jquery', 'backbone'], function($, Backbone){
    var View = Backbone.View.extend({
        initialize: function(){
            this.setElement('#general-alert');
            $(this.el).modal({
                show     : false, 
                keyboard : true, 
                backdrop : true
            });
        },
        display: function(vars, url, timeout){
            if(vars){   
                $(this.el).modal('show');
                $(this.el).find('.modal-header h3').text(vars.title);
                $(this.el).find(' .modal-body p').text(vars.content);
                if(url){
                    $('.modal-alert button').click(function(){
                        window.location.href = vars.url;
                    });
                    setTimeout(function(){
                        window.location.href = vars.url;
                    }, vars.timeout || 3000);
                }
            }
        }
    });
    return View;
});