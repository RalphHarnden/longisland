define(['jquery', 'backbone', 'models/APIListModel', 'views/APIListView', 'views/PanelView', 'views/PropertyEditorView'], function($, Backbone, APIListModel, APIListView, PanelView, PropertyEditorView){
    var View = Backbone.View.extend({
        el: "body",
        initialize: function(){
            // establish event pub/sub 
            this.eventPubSub = _.extend({}, Backbone.Events);
            // initialize base models and collections
            this.alm = new APIListModel();
            // initialize base views
            this.alv = new APIListView({alm:this.alm, eventPubSub:this.eventPubSub});
            this.pv = new PanelView({nav:[
                {title : ' Studio', url : 'studio'},
                {title : ' Graph', url : 'graph'},
                {title : ' Appbuilder', url : 'appbuilder'}
            ], eventPubSub:this.eventPubSub});
            this.ev = new PropertyEditorView({alm:this.alm, eventPubSub:this.eventPubSub});
        }
    });
    return View;
});