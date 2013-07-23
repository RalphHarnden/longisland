require.config({
  paths : {
      "modernizr"  : "libs/modernizr",
      "jquery"     : "libs/jquery",
      "json2"      : "libs/json2",
      "underscore" : "libs/underscore",
      "backbone"   : "libs/backbone",
      "ampstore"   : "libs/ampstore",
      "jsrender"   : "libs/jsrender",
      "jstree"     : "libs/jstree",
      "magnet"     : "libs/magnet",
      "base64"     : "libs/base64",
      "datepicker" : "libs/datepicker",
      "scrollto"   : "libs/scrollto"
  },
  shim : {
      "backbone"  : {
          "deps"    : ["underscore", "jquery", "magnet"],
          "exports" : "Backbone" 
      },
      "scrollto"  : {
          "deps"    : ["jquery"]
      },
      "datepicker"  : {
          "deps"    : ["jquery"]
      },
      "jstree"  : {
          "deps"    : ["jquery"]
      },
      "magnet"  : {
          "deps"    : ["jquery", "ampstore", "jsrender", "jstree", "datepicker", "scrollto"],
          "exports" : "magnet" 
      }
  } 
});
require(['modernizr','jquery','backbone','routers/desktopRouter', 'magnet', 'json2', 'base64', 'datepicker', 'scrollto'], function(Modernizr, $, Backbone, Desktop, magnet){
    this.router = new Desktop();
});

/* HELPERS */

function selectBuilder(state, i){
    var e = '', t = '', f = '', s = ' selected="selected"';
    switch(state){
        case '':
            e = s;
            break;
        case true:
            t = s;
            break;
        case false:
            f = s;
            break;
    }
    return '<select '+((typeof i != 'undefined') ? i : '')+'><option value=""'+e+'></option><option value="true"'+t+'>True</option><option value="false"'+f+'>False</option></select>';
}
function stringToBool(str){
    switch(str){
        case 'true':
            return true;
        case 'false':
            return false;
        default:
            return str;
    }
}
function boolToString(bool){
    switch(bool){
        case true:
            return 'true';
        case false:
            return 'false';
        default:
            return bool;
    }
}