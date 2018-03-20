angular.module('ework-ui')
  .directive('rdDatePickerRange', ['$timeout',rdDatePickerRange]);

function rdDatePickerRange(
    $timeout
  ) {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      scope: {
        start_date: '=?ngStartDate',
        end_date: '=?ngEndDate',
        disabled: '=?',
        disableInit: '=?disableInit',
        onDateChange: '&ngDateChange',
        labelName:'=?',
        dateRange: '=?ngDefaultRange',
        btnTag: '=?ngBtnTag',//hidden为隐藏
        future: '=?ngFuture',
        minDate: '=?ngMinDate',
        maxDate: '=?ngMaxDate',
      },
      template: '<form class="form-inline"> '
          +'<div class="form-group"> '
            +'<span class="input-label">{{labelName || \'起止日期\'}}：</span>	<rd-date-picker-popup ng-model="start_date" ng-min-date="minDate" ng-max-date="maxDate" disabled="disabled"></rd-date-picker-popup> '
            +'&nbsp;-&nbsp;<rd-date-picker-popup ng-model="end_date" ng-min-date="minDate" ng-max-date="maxDate" disabled="disabled"></rd-date-picker-popup> '
            +'<button class="btn {{btnTag}}" '
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'today\']" '
              +'ng-click="handleChangeDateRange(\'today\')">今天</button> '
            +'<button class="btn {{btnTag}}" '
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'curMonth\']" '
              +'ng-click="handleChangeDateRange(\'curMonth\')">本月</button> '
            +'<button class="btn {{btnTag}}" '
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'lastWeek\']" '
              +'ng-click="handleChangeDateRange(\'lastWeek\')">最近一周</button> '
            +'<button class="btn {{btnTag}}"'
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'lastMonth\']" '
              +'ng-click="handleChangeDateRange(\'lastMonth\')">最近一个月</button> '
            +'<button class="btn {{btnTag}}" '
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'lastYear\']" '
              +'ng-click="handleChangeDateRange(\'lastYear\')">最近一年</button> '
            +'<button ng-if="future" class="btn" '
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'today\']" '
              +'ng-click="handleChangeDateRange(\'today\')">今天</button> '
            +'<button ng-if="future" class="btn"'
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'nextTwoDays\']" '
              +'ng-click="handleChangeDateRange(\'nextTwoDays\')">今明天</button> '
            +'<button ng-if="future" class="btn" '
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'nextWeek\']" '
              +'ng-click="handleChangeDateRange(\'nextWeek\')">未来一周</button> '
            +'<button ng-if="future" class="btn"'
              +'ng-class="{true: \'btn-primary\', false: \'\'}[dateRange==\'nextMonth\']" '
              +'ng-click="handleChangeDateRange(\'nextMonth\')">未来一个月</button> '
          +'</div> '
          + '<div class="form-group" ng-transclude></div>'
        +'</form>',
      link: function (scope, element, attrs/*, ngModel*/) {
        if (!scope.start_date && !scope.disableInit) {
          scope.dateRange = scope.dateRange || 'lastMonth';
          if (scope.dateRange == 'curMonth') {
            scope.start_date = moment().startOf('month').format('YYYY-MM-DD');
            scope.end_date = moment().endOf('month').format('YYYY-MM-DD');
          } else {
            scope.start_date = getDateByType(scope.dateRange);
            scope.end_date = scope.end_date || getCurrentDate();
          }
        }
        scope.dateRangeObj = {
          start_date: scope.start_date,
          end_date: scope.end_date
        }

        scope.handleChangeDateRange = function (dateType) {
          scope.dateRange = dateType;
          if (dateType == 'curMonth') {
            scope.start_date = moment().startOf('month').format('YYYY-MM-DD');
            scope.end_date = moment().endOf('month').format('YYYY-MM-DD');
          } else if(scope.future==true){
            scope.end_date = getDateByType(dateType);
            scope.start_date = getCurrentDate();
          }else {
            scope.end_date = getCurrentDate();
            scope.start_date = getDateByType(dateType);
          }
        }
        
        scope.$watch('start_date', function () {
          scope.dateRangeObj.start_date = scope.start_date
        })

        scope.$watch('end_date', function () {
          scope.dateRangeObj.end_date = scope.end_date
        })

        scope.$watch('dateRangeObj', function(newVal, oldVal) {
          if (newVal.start_date != oldVal.start_date 
            || newVal.end_date != oldVal.end_date) {
            scope.onDateChange();
          }
        }, true)

        function getDateByType(dateType) {
          if (dateType == 'today') {
            return getCurrentDate();
          } else if (dateType == 'lastWeek') {
            return getLatestWeek();
          } else if (dateType == 'lastMonth') {
            return getLatestMonth();
          } else if (dateType == 'lastYear') {
            return getLatestYear();
          } else if (dateType == 'nextTwoDays') {
            return getNextTwoDays();
          } else if (dateType == 'nextWeek') {
            return getNextWeek();
          } else if (dateType == 'nextMonth') {
            return getNextMonth();
          } else {
            scope.dateRange = 'lastMonth';
            return getLatestMonth();
          }
        }
      }
    }
}