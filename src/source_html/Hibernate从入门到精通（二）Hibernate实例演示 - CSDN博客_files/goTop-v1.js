!function(){function t(){var t=$(window).scrollTop();t>=n?l.find("a#backtop").fadeIn(200).css("display","flex"):l.find("a#backtop").fadeOut(200)}var o=!1,e="http"===location.protocol.substr(0,4)?"":"http:";$("<link/>",{rel:"stylesheet",type:"text/css",href:e+"//csdnimg.cn/public/common/gotop/css/goTop.min.css?v1520242039"}).appendTo("head");var n=$(window).height();$(window).resize(function(){n=$(window).height(),t()});var i={parentBox:$("body"),right:8,bottom:40,zindex:110,hasReport:!1,reportFun:null},l=$('<div class="meau-gotop-box">    <a href="#" id="backtop" class="btn-meau" title="返回顶部">      <svg xmlns="http://www.w3.org/2000/svg" width="19" height="12">        <path d="M9.314 0l9.313 9.314-2.12 2.121-7.193-7.192-7.193 7.192L0 9.314z" fill="#FFF" fill-rule="evenodd" />      </svg>    </a>  </div>');l.find("a#backtop").click(function(t){t.preventDefault(),$("html,body").animate({scrollTop:0},200)});var a=function(e){if(void 0!==e&&"object"==typeof e&&$.extend(i,e),i.hasReport)if(null===i.reportFun)console.warn("请设置内容举报方法");else{var n=$('<a href="#" id="reportContent" class="btn-meau" title="返回顶部">                            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="22"><path d="M0 13.028V3.365L9 0l9 3.365v9.663C18 17.983 13.97 22 9 22s-9-4.017-9-8.972zm2-8.282v8.282c0 3.854 3.134 6.978 7 6.978s7-3.124 7-6.978V4.746L9 2.13 2 4.746zm6 10.276h2v1.993H8v-1.993zm0-8.973h2v6.979H8V6.049z" fill="#FFF" fill-rule="nonzero"/></svg>                          </a>');l.append(n),n.click(function(t){t.preventDefault(),i.reportFun()})}i.parentBox.append(l),l.css({right:i.right+"px",bottom:i.bottom+"px","z-index":i.zindex}),$(window).scroll(t),o=!0};window.GoTop=a,setTimeout(function(){o||a()},200)}();