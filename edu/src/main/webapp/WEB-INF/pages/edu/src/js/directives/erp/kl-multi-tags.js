angular.module('ework-ui').directive('klMultiTags', [
	klMultiTags
])

function klMultiTags(
) {
	return {
		restrict: 'EA',
		transclude: true,
		scope:{
		},
		template: '<div class="multi-tags">'
			+ 	'<div class="tags-container" ng-transclude>'
			+ 	'</div>'
			+		'<a ng-hide="open" href="javascript:void(0);" class="show-more" ng-click="showMore()">更多</a>'
			+		'<a ng-show="open" href="javascript:void(0);" class="show-more" ng-click="hideMore()">收起</a>'
			+	'</div>',
		link: function (scope, element, attrs) {
			scope.open = true;
			var $el = $('.multi-tags',element[0]);			
			var $c = $('.tags-container', $el);

			scope.showMore = function() {
				scope.open = true;
				$el.css('height', 'auto')
			}

			scope.hideMore = function() {
				scope.open = false;
				$el.css('height', '30px')
			}

			scope.showMore();
		}
	}
}