define(['jquery', 'backbone'], function($, Backbone){
    var View = Backbone.View.extend({
        el: "#panels",
        initialize: function(){
            var me = this;
            this.initNavMenu('navigate', this.options.nav);
            // subscribe to call API event
            me.options.eventPubSub.bind("callAPI", function(params){
                me.$el.find('h2 span').html(': '+params.req.url);
                me.showTab($('ul.tabs li:nth-child(2)'));
                me.$el.find('.tab_content').scrollTo(0, 100);
            });
        },
        events: {
            "click .tabs li a": "doTabClick",
            "click .panelConfig a": "doPanelClick"
        },
        doTabClick: function(e){
            e.preventDefault();
            this.showTab($(e.currentTarget).closest('li'));
        },
        // show and hide active tab
        showTab: function(li){
            if(!li.hasClass('active')){
                $(this.el).find('.tabs li').each(function(){
                    if(!$(this).hasClass('hidden')){
                        $(this).removeClass('active');
                        var sel = $(this).find('a').attr('href');
                        $(sel).addClass('hidden');
                    }
                });
                li.addClass('active');
                $(li.find('a').attr('href')).removeClass('hidden');
            }
        },
        // create navigation menu options and bindings
        initNavMenu: function(id, links){
            var html = '';
            $.each(links, function(i, obj){
                html += '<li><a href="/'+obj.url+'/">'+obj.title+'</a></li>';
            });
            $('#'+id+' ul').html(html+'<div class="last"></div>');
            this.bindToggle([id], ['#'+id+' .dropdown']);
        },
        // toggle display of navigation menu
        bindToggle : function(ary, aryT){
            $.each(ary, function(i, val){
                var dom = typeof aryT !== 'undefined' ? $(aryT[i]) : $('#'+val);
                $('#'+val).click(function(){
                    if(dom.hasClass('hidden')){
                        dom.removeClass('hidden');
                    }else{
                        dom.addClass('hidden');
                    }
                });
            });
        },
        // handle panel menu click
        doPanelClick: function(e){
            e.preventDefault();
            var link = $(e.currentTarget);
            if(!link.hasClass('active')){
                var parent = link.parent('div');
                parent.find('a').removeClass('active');
                link.addClass('active');
                $(this.el).find('.tab_section').each(function(){
                    $(this).addClass('hidden');
                });
                $('.'+link.attr('id')).removeClass('hidden');
            }
        }
    });
    return View;
});