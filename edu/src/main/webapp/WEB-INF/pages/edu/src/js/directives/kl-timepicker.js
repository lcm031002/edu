angular.module('ework-ui')
  .directive('klTimepicker', [
    '$filter',
    'uibDateParser',
    klTimepicker
  ])

function klTimepicker(
    $filter,
    uibDateParser
  ) {
  return {
    restrict: 'EA',
    replace: false,
    transclude: true,
    require:  '^ngModel',
    scope: {
      ngModel: '=',
      disabled: '=',
      width: '=?'
    },
    template: '<div class="input-group" style="position: relative;" ng-style="{width: width}">'
      +'<input type="text" readonly="readonly" ng-disabled="disabled" class="form-control"'
        +'ng-click="toggle()" value="{{time | date: \'HH:mm\'}}" style="background:#fff;">'
      +'<div class="kl-timepicker-wrapper" ng-show="showEndTime" '
        +'style="width: 200px;">'
        +'<div uib-timepicker ng-model="time" minute-step="5" show-meridian="showMeridian"></div>'
        +'<div class="btn-group pull-left">'
          +'<button class="btn btn-primary btn-sm" ng-click="now()">现在</button>'
          +'<button class="btn btn-danger btn-sm" ng-click="clear()">清除</button>'
        +'</div>'
        +'<button class="btn btn-success btn-sm pull-right" ng-click="close()">确定</button>'
        +'<div class="clearfix"></div>'
      +'</div>'
      +'<span class="input-group-btn">'
        +'<button class="btn btn-default" ng-click="toggle()" ng-disabled="disabled"><i class="glyphicon glyphicon-time"></i></button>'
      +'</span>'
    +'</div>',
    link: function(scope, element, attrs, ngModel) {
      scope.showMeridian = false;
      ngModel.$render = function () {
        scope.time = uibDateParser.parse(ngModel.$viewValue, 'HH:mm')
      }

      scope.$watch('time', function () {
        ngModel.$setViewValue($filter('date')(scope.time, 'HH:mm'))
      })
      
      scope.now = function () {
        scope.time = new Date()
      }

      scope.clear = function () {
        scope.time = null
      }

      scope.toggle = function () {
        scope.showEndTime = !scope.showEndTime
      }

      scope.close = function () {
        scope.showEndTime = false;
      }
    }
  }
}