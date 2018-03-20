/**
 * DatePickerPopup Directive
 */
angular.module('ework-ui')
  .directive('rdDatePickerPopup', ['$filter','uibDateParser',rdDatePickerPopup]);

function rdDatePickerPopup(
    $filter,
    uibDateParser
  ) {
  return {
    restrict: 'EA',
    replace: false,
    transclue: true,
    require: '?ngModel',
    scope: {
      ngModel: '=',
      required: '=required',
      disabled: '=?',
      ngMinDate: '=?',
      ngMaxDate: '=?',
    },
    template: 
        '<div class="input-group">'
      +   '<input type="text" ng-disabled="disabled" class="form-control" readonly="true"'
      +       'uib-datepicker-popup '
      +       'datepicker-options="datepickerOptions"'
      +       'ng-model="date" is-open="isopened" '
      +       'datepicker-options = "dateOptions"' 
      +       'ng-required="required" '
      +       'clear-text="清空"'
      +       'current-text="今天"'
      +       'on-open-focus="true" '
      +        'close-text="关闭" style="background:#fff;">'
      +   '<span class="input-group-btn">'
      +     '<button class="btn btn-default" ng-click="open()" ng-disabled="disabled">'
      +       '<i class="glyphicon glyphicon-calendar"></i>'
      +     '</button>'
      +   '</span>'
      + '</div>',
    link: function (scope, element, attrs, ngModel) {
      scope.isopened = false;
      scope.datepickerOptions = {
        showWeeks: false,
        maxDate: new Date(scope.ngMaxDate),
        minDate: new Date(scope.ngMinDate),
      }
      // Specify how UI should be updated
      ngModel.$render = function () {
        scope.date = uibDateParser.parse(ngModel.$viewValue, 'yyyy-MM-dd')
      }

      scope.open = function () {
        scope.isopened = true;
      }

      scope.$watch('date', function () {
        ngModel.$setViewValue($filter('date')(scope.date, 'yyyy-MM-dd'))
      })
    }
  }
}