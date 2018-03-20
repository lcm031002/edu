angular.module('ework-ui').directive('klTable', [
	klTable
])

function klTable() {
	return {
		restrict: 'EA',
		replace: true,
		transclude: true,
		template: '<table class="table table-striped table-hover" ng-transclude></table>'
	}	
}