define(["jquery", "backbone"], function($, Backbone){
    var View = Backbone.Model.extend({
        initialize: function(){
        },
        idAttribute: 'magnetId',
        url: ''
    });
    return View;
});