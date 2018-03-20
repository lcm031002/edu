angular.module('ework-ui').directive('ngEnter', function () {
	return {
		restrict: 'A',
		require: '?ngModel',
		link: function(scope, element, attrs) {
			element.bind("keydown keypress", function (event) {
          if (event.which === 13) {
              scope.$apply(function () {
                  scope.$eval(attrs.ngEnter);
              });
              event.preventDefault();
          }
      });
		}
	}
})