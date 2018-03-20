/**
 * Created by Administrator on 2014/11/9.
 */
/**
 *
 */
'use strict';

/* Controllers */
var controllers = angular.module('Controllers', []);

/* Services */
var services = angular.module('Services', [ 'ngResource' ]);


var apps = angular.module('Apps', [ 'ngRoute',
    'Controllers', 'Services']);


apps.config(['$routeProvider',
    function($routeProvider) {
        /*设置大小版、个性化、艺考班的报班账户信息*/
        $routeProvider.
            when('/index', {
                templateUrl: 'views/first',
                controller: 'FirstController'
            }).
            when('/history', {
                templateUrl: 'views/history',
                controller: 'HistoryController'
            }).
            when('/roles', {
                templateUrl: 'views/roles',
                controller: 'RolesController'
            }).
            when('/process', {
                templateUrl: 'views/process',
                controller: 'ProcessController'
            }).
            when('/process_def', {
                templateUrl: 'views/process_def',
                controller: 'ProcessControllerDef'
            }).
            when('/task', {
                templateUrl: 'views/task',
                controller: 'TaskController'
            }).
            otherwise({
                redirectTo: '/index'
            });
    }]);
controllers.controller('MenuController', ['$scope',
    function($scope) {
        $scope.menuId = '首页';
        $scope.selectMenu = function(menu){
            $scope.menuId = menu;
        }
    }
]);

/**
 * 获取web上下文
 * @returns {string}
 */
function genWebContext(){
    var  webroot=document.location.href;
    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
    webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
    webroot=webroot.substring(0,webroot.indexOf('/'));
    return webroot=='webERP'||webroot=='ebusiness_front'?'/'+webroot:'';
};
var webContext = genWebContext();
function Format(fmt, date) { // author: meizz
    var o = {
        "M+" : date.getMonth() + 1, // 月份
        "d+" : date.getDate(), // 日
        "h+" : date.getHours(), // 小时
        "m+" : date.getMinutes(), // 分
        "s+" : date.getSeconds(), // 秒
        "q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
        "S" : date.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
/* 将输入字符串转换为数字 */
function genNumByString(inputString) {
	if (!inputString) {
		return null;
	} else {
		var reg = new RegExp("^[0-9]*$");
		if (!reg.test(inputString)) {
			return null;
		}
		var temp = inputString;
		try {
			temp = parseInt(inputString);
			if (temp == NaN) {
				temp = 0;
			}
		} catch (e) {
			temp = 0;
		}

		return temp;
	}
}

