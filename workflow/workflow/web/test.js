/** * Created by Administrator on 2014/11/9. */
/** * */
'use strict';/* Controllers */
var controllers = angular.module('Controllers', []);/* Services */
var services = angular.module('Services', [ 'ngResource' ]);
var apps = angular.module('Apps', [ 'ngRoute', 'Controllers', 'Services' ]);
apps.config([ '$routeProvider', function($routeProvider) { /* 设置大小版、个性化、艺考班的报班账户信息 */
	$routeProvider.when('/index', {
		templateUrl : 'views/first.html',
		controller : 'FirstController'
	}).when('/history', {
		templateUrl : 'views/history.html',
		controller : 'HistoryController'
	}).when('/roles', {
		templateUrl : 'views/roles.html',
		controller : 'RolesController'
	}).when('/process', {
		templateUrl : 'views/process.html',
		controller : 'ProcessController'
	}).when('/process_def', {
		templateUrl : 'views/process_def.html',
		controller : 'ProcessControllerDef'
	}).when('/task', {
		templateUrl : 'views/task.html',
		controller : 'TaskController'
	}).otherwise({
		redirectTo : '/index'
	});
} ]);
controllers.controller('MenuController', [ '$scope', function($scope) {
	$scope.menuId = '首页';
	$scope.selectMenu = function(menu) {
		$scope.menuId = menu;
	}
} ]);
var webContext = 'ebusiness_front';