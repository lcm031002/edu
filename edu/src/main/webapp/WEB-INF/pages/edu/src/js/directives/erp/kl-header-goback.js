angular.module('ework-ui').directive('klHeaderGoBack', [
	'$state',
	klHeaderGoBack
])

function klHeaderGoBack(
	$state
) {
	return {
		restrict: 'EA',
		scope: {},
		template: '<span><span class="cur text-primary" ng-click="goback()">< 返回</span> | </span>',
		link: function (scope, element) {
			scope.goback = function () {
				history.go(-1);
			}
		}
	}
}