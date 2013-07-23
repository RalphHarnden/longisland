/*! JsRender v1.0pre: http://github.com/BorisMoore/jsrender */
/*
 * Optimized version of jQuery Templates, for rendering to string.
 * Does not require jQuery, or HTML DOM
 * Integrates with JsViews (http://github.com/BorisMoore/jsviews)
 * Copyright 2012, Boris Moore
 * Released under the MIT License.
 */
this.jsviews||this.jQuery&&jQuery.views||function(P,j){function Q(b,a){var d="\\"+b.charAt(0),f="\\"+b.charAt(1),e="\\"+a.charAt(0),c="\\"+a.charAt(1);n.rTag=x=f+"(?:(?:(\\w+(?=[\\/\\s"+e+"]))|(?:(\\w+)?(:)|(>)|(\\*)))\\s*((?:[^"+e+"]|"+e+"(?!"+c+"))*?)";x=RegExp(d+x+"(\\/)?|(?:\\/(\\w+)))"+e+c,"g");R=RegExp("<.*>|"+b+".*"+a);return this}function S(b){var a=this,d=a.tmpl.helpers||{},b=(a.ctx[b]!==j?a.ctx:d[b]!==j?d:v[b]!==j?v:{})[b];return"function"!==typeof b?b:function(){return b.apply(a,arguments)}} function F(b,a,d,f,e,c){var g=d.views,b={tmpl:e,path:a,parent:d,data:f,ctx:b,views:o.isArray(f)?[]:{},hlp:S};o.isArray(g)?g.splice(b.key=b.index=c!==j?c:g.length,0,b):(g[b.key="_"+X++]=b,b.index=d.index);return b}function G(b,a,d,f,e){var c,g;if(d&&"object"===typeof d&&!d.nodeType){for(c in d)a(c,d[c]);return b}if(!d||f===j)e&&(f=e(j,f||d));else if(""+d===d)if(null===f)delete a[d];else if(f=e?e(d,f):f)a[d]=f;(g=T.onStoreItem)&&g(a,d,f,e);return f}function p(b,a){return G(this,p,b,a,K)}function H(b, a){return G(this,H,b,a)}function v(b,a){return G(this,v,b,a)}function I(b,a){return G(this,I,b,a)}function L(b,a,d,f,e){var c,g,i,h,l,k,M;h=f===q;var y="";this.isTag?(k=this.tmpl,this.props&&this.ctx&&m(this.ctx,this.props),this.ctx&&a&&(a=m(this.ctx,a)),a=this.ctx||a,e=e||this.view,d=d||this.path,f=f||this.key,M=this.props):k=this.jquery&&this[0]||this;e=e||n.topView;l=e.ctx;if(k&&(c=k.layout,b===e&&(b=e.data,c=q),a=a&&a===l?l:a?m(m({},l),a):l,a.link===t&&(a.onRender=t),k.fn||(k=p[k]||p(k)),l=a.onRender, k)){if(o.isArray(b)&&!c){h=h?e:f!==j&&e||F(a,d,e,b,k,f);c=0;for(g=b.length;c<g;c++)i=b[c],i=k.fn(i,F(a,d,h,i,k,(f||0)+c),n),y+=l?l(i,k,M):i}else h=h?e:F(a,d,e,b,k,f),y+=b||c?k.fn(b,h,n):"";e.topKey=h.key;return l?l(y,k,M,h.key,d):y}return""}function J(b,a){throw(a?a.name+': "'+a.message+'"':"Syntax error")+(b?" \n"+b:"");}function N(b,a,d){function f(a){(a-=z)&&s.push(b.substr(z,a).replace(Y,"\\n"))}var e,c,g,i,h,l,k,j,o,m,n,C,p,t,w,u=a?{allowCode:w=a.allowCode,debug:a.debug}:{},D=a&&a.tmpls,A=[], z=0,E=[],s=A,r=[,,,A],B=0,b=b.replace(Z,"\\$1");b.replace(x,function(a,c,g,h,l,k,i,o,p,m){l&&(h=":",g="html");var j="",B="",l=!o&&!h&&!d,c=c||h;f(m);z=m+a.length;k?w&&s.push(["*",i.replace($,"$1")]):c?("else"===c&&(r[5]=b.substring(r[5],m),r=E.pop(),s=r[3],l=q),i=i?aa(i,d).replace(ba,function(b,a,c){a?B=B+(c+","):j=j+(c+",");return""}):"",j=j.slice(0,-1),i=i.slice(0,-1),e=[c,g||"",i,l&&[],"{"+(j?"props:{"+j+"},":"")+"path:'"+i+"'"+(B?",ctx:{"+B.slice(0,-1)+"}":"")+"}"],l&&(E.push(r),r=e,r[5]=z),s.push(e)): p&&(r[5]=b.substring(r[5],m),r=E.pop());if(!r)throw"Expected block tag";s=r[3]});f(b.length);h=(i=A.length)?"":'"";';for(g=0;g<i;g++)c=A[g],""+c===c?h+='"'+c+'"+':"*"===c[0]?h=h.slice(0,g?-1:-3)+";"+c[1]+(g+1<i?"ret+=":""):(n=c[0],C=c[1],p=c[2],s=c[3],t=c[4],b=c[5],s&&(c=U(b,u,a,B++),N(b,c),D.push(c)),m=m||-1<t.indexOf("view"),h+=(":"===n?"html"===C?(k=q,"e("+p):C?(o=q,'c("'+C+'",view,'+p):(j=q,"((v="+p+')!=u?v:""'):(l=q,'t("'+n+'",view,"'+(C||"")+'",'+(s?D.length:'""')+","+t+(p?",":"")+p))+")+"); h=ca+(j?"v,":"")+(l?"t=j.tag,":"")+(o?"c=j.convert,":"")+(k?"e=j.converters.html,":"")+"ret; try{\n\n"+(u.debug?"debugger;":"")+(w?"ret=":"return ")+h.slice(0,-1)+";\n\n"+(w?"return ret;":"")+"}catch(e){return j.err(e);}";try{h=new Function("data, view, j, b, u",h)}catch(da){J("Error in compiled template code:\n"+h,da)}a&&(a.fn=h,a.useVw=o||m||l);return h}function aa(b,a){var d,f={},e=0,c=t,g=t;return b=(b+" ").replace(ea,function(b,h,l,k,j,m,o,p,n,u,x,w,v,D,A,z){function E(b,a,c,d,e,f,g){return a? (a=(c?'view.hlp("'+c+'")':d?"view":"data")+(g?(e?"."+e:c?"":d?"":"."+a)+(f||""):(g=c?"":d?e||"":a,"")),b=g?"."+g:"",s||(a+=b),a="view.data"===a.slice(0,9)?a.slice(5):a,s&&(a="b("+a+',"'+g+'")'+b),a):b}var l=l||h||x,k=k||p,n=n||A||"",j=j||""||"",s=a&&"("!==n;if(m)J();else return g?(g=!w,g?b:'"'):c?(c=!v,c?b:'"'):(l?(e++,l):"")+(z?e?"":d?(d=t,"\u0008"):",":o?(e&&J(),d=q,"\u0008"+k+":"):k?k.replace(fa,E)+(n?(f[++e]=q,n):j):j?b:D?(f[e--]=t,D)+(n?(f[++e]=q,n):""):u?(f[e]||J(),","):h?"":(g=w,c=v,'"'))})} function K(b,a,d,f){function e(a){if(""+a===a||0<a.nodeType){try{g=0<a.nodeType?a:!R.test(a)&&u&&u(a)[0]}catch(c){}g&&g.type&&(a=p[g.getAttribute(V)],a||(b=b||"_"+ga++,g.setAttribute(V,b),a=K(b,g.innerHTML,d,f),p[b]=a));return a}}var c,g,i,h,a=a||"";c=e(a);f=f||(a.markup?a:{});f.name=b;h=f.templates;if(!c&&a.markup&&(c=e(a.markup)))if(c.fn&&(c.debug!==a.debug||c.allowCode!==a.allowCode))c=c.markup;if(c!==j){b&&!d&&(O[b]=function(){return a.render.apply(a,arguments)});c.fn||a.fn?c.fn&&(a=b&&b!==c.name? m(m({},c),f):c):(a=U(c,f,d,0),N(c,a));for(i in h)c=h[i],c.name!==i&&(h[i]=K(i,c,a));return a}}function U(b,a,d,f){function e(b){d[b]&&(c[b]=m(m({},d[b]),a[b]))}var a=a||{},c={markup:b,tmpls:[],links:[],render:L};d&&(d.templates&&(c.templates=m(m({},d.templates),a.templates)),c.parent=d,c.name=d.name+"["+f+"]",c.key=f);m(c,a);d&&(e("templates"),e("tags"),e("helpers"),e("converters"));return c}function ha(b){return W[b]||(W[b]="&#"+b.charCodeAt(0)+";")}var o,x,R,m,T={},t=!1,q=!0,u=P.jQuery,fa=/^(?:null|true|false|\d[\d.]*|([\w$]+|~([\w$]+)|#(view|([\w$]+))?)([\w$.]*?)(?:[.[]([\w$]+)\]?)?|(['"]).*\8)$/g, ea=/(\()(?=|\s*\()|(?:([([])\s*)?(?:([#~]?[\w$.]+)?\s*((\+\+|--)|\+|-|&&|\|\||===|!==|==|!=|<=|>=|[<>%*!:?\/]|(=))\s*|([#~]?[\w$.]+)([([])?)|(,\s*)|(\(?)\\?(?:(')|("))|(?:\s*([)\]])([([]?))|(\s+)/g,Y=/\r?\n/g,$=/\\(['"])/g,Z=/\\?(['"])/g,ba=/\x08(~)?([^\x08]+)\x08/g,X=0,ga=0,W={"&":"&amp;","<":"&lt;",">":"&gt;"},V="data-jsv-tmpl",ca="var j=j||"+(u?"jQuery.":"js")+"views,",ia=/[\x00"&'<>]/g,ja=Array.prototype.slice,O={},n={jsviews:"v1.0pre",sub:T,debugMode:q,err:function(b){return n.debugMode?"<br/><b>Error:</b> <em> "+ (b.message||b)+". </em>":'""'},tmplFn:N,render:O,templates:p,tags:H,helpers:v,converters:I,View:F,convert:function(b,a,d){var f=a.tmpl.converters;return(b=f&&f[b]||I[b])?b.call(a,d):d},delimiters:Q,tag:function(b,a,d,f,e){var c;c=e.props&&e.props.tmpl;var g=a.tmpl.tags,i=a.tmpl.templates,h=arguments,g=g&&g[b]||H[b];if(!g)return"";f=f&&a.tmpl.tmpls[f-1];c=c||f||j;e.tmpl=""+c===c?i&&i[c]||p[c]||p(c):c;e.isTag=q;e.converter=d;e.view=a;e.renderContent=L;return(c=g.apply(e,5<h.length?ja.call(h,5):[]))|| (c==j?"":c.toString())}};u?(o=u,o.templates=p,o.render=O,o.views=n,o.fn.render=L):(o=P.jsviews=n,o.extend=function(b,a){var d,b=b||{};for(d in a)b[d]=a[d];return b},o.isArray=Array&&Array.isArray||function(b){return"[object Array]"===Object.prototype.toString.call(b)});m=o.extend;n.topView={views:{},tmpl:{},hlp:S,ctx:n.helpers};H({"if":function(){var b=this.view;b.onElse=function(a,d){for(var f=0,e=d.length;e&&!d[f++];)if(f===e)return"";b.onElse=j;a.path="";return a.renderContent(b)};return b.onElse(this, arguments)},"else":function(){var b=this.view;return b.onElse?b.onElse(this,arguments):""},"for":function(){var b,a="",d=arguments,f=d.length;if(this.props&&this.props.layout)this.tmpl.layout=q;for(b=0;b<f;b++)a=a+this.renderContent(d[b]);return a},"=":function(b){return b},"*":function(b){return b}});I({html:function(b){return b!=j?(""+b).replace(ia,ha):""}});Q("{{","}}")}(this);