angular.module('ework-ui')
  .directive('klSpanRequired', ['$scope', klSpanRequired])

function klSpanRequired($scope) {
  return {
    restrict: 'E',
    replace: true,
    template: '<span class="text-danger">*</span>',
    link: function (scope, element, attrs){

    }
  }
}