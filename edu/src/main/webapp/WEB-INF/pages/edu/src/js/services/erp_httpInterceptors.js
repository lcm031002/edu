/**
 * Created by jimboi on 2017/5/6.
 * erp http请求拦截器服务
 * 使用方式：在请求参数中添加modal:true
 */
/**
 * 模态框显示个数，如果modalCount=0则允许新建modal，modal=1允许销毁
 * @type {number}
 */
var modalCount = 0;
var modalDiv = "<div id='erp_grobal_ajax_modal' style='position:fixed;left:0;top:0;bottom:0;right:0;background:#ccc;opacity: 0.5;z-index:1000;margin:0 auto;text-align:center;padding-top:20%'>" +
    "<div style='display:inline-block;width:300px;height:50px;font-size:16px'>数据加载中...</div>" +
    "</div>";
/**
 * 处理http拦截器响应操作
 * @param response http拦截器响应数据
 * @param modalCount 模态框显示个数
 * @returns {*}
 */
function clearCookie(){
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = keys.length; i--;)
            document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()
    }
}
var httpInterceptorHandler = function(response,modalCount) {
    //判断响应的数据是否是登录页面
    if(response.data) {
        var temp = response.data.toString();
        var re = /body ng-controller="LoginCtrl" style="height: 100%;"/i;
        if(re.test(temp)) {
            clearCookie();
            location.href = location.hostname;
        }
    }
    if(response.config.params && response.config.params.modal && response.config.params.modal == true) {
        if(1 >= modalCount) {
            $("#erp_grobal_ajax_modal").remove();
            modalCount = 0 ;
        } else {
            modalCount -- ;
        }
    }
}

angular.module("ework-ui").factory('httpInterceptor', ['$q', '$injector', function ($q, $injector) {
        var httpInterceptor = {
            'responseError': function (response) {
                httpInterceptorHandler(response,modalCount);
                return $q.reject(response);
            },
            'response': function (response) {
                httpInterceptorHandler(response,modalCount)
                return response;
            },
            'request': function (config) {
                if(config.params && config.params.modal && config.params.modal == true) {
                    if(modalCount <= 0) {
                        $(modalDiv).prependTo($(document.body));
                        modalCount =1 ;
                    } else {
                        modalCount ++ ;
                    }
                }
                return config;
            },
            'requestError': function (config) {
                return $q.reject(config);
            }
        }
        return httpInterceptor;
    }]);

angular.module("ework-ui")
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('httpInterceptor');
    }]);