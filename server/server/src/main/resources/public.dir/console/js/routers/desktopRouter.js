define(["jquery", "backbone","views/PageView"], function($, Backbone, PageView){
    // bind alerts
    //Alerts.General = new AlertGeneralView();
    //Alerts.Confirm = new AlertConfirmView();
    //Alerts.Error = new AlertErrorView();
    // main router
    var Router = Backbone.Router.extend({
        initialize: function(){
            Backbone.history.start();
        },
        routes: {
            "" : "page"
        },
        page: function(){
            var pv = new PageView();
        }
    });
    return Router;
});
var Alerts = {};