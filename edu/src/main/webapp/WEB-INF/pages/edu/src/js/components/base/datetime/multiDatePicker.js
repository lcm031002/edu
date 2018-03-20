(function () {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcbDatetimeMultiDatePicker', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/base/datetime/multiDatePicker.html',
      controllerAs: '$ctrl',
      bindings: {
        checkedDays: '=?',
        onCheckDays: '&?',
      },
      controller: ['$scope',
        function ($scope) {
          var $ctrl = this;
          
          $scope.calendar = {
            yearMonth: moment().format('YYYY年MM月'),
            weeks: getCalendar(moment()),
            currentDate: moment(),
            checkedDays: []
          }

          $scope.calendarToggleCheck = function (day) {
            day.isChecked = !day.isChecked;
            var dayStr = day.time.format('YYYY-MM-DD')
            var checkedDays = $scope.calendar.checkedDays
            if (day.isChecked && !_.some(checkedDays, { value: dayStr })) {
              checkedDays.push({
                key: moment(dayStr, 'YYYY-MM-DD').format('YYYYMMDD'),
                value: dayStr
              })
            }
            if (!day.isChecked) {
              checkedDays.splice(_.findIndex(checkedDays, { value: dayStr }), 1)
            }
            if (_.isFunction($ctrl.onCheckDays)) {
              $ctrl.onCheckDays({
                days: $scope.calendar.checkedDays
              });
            }
          }

          $scope.calendarDecrement = function (calendar, type) {
            calendar.currentDate.subtract(1, type)
            reloadCalendar(calendar)
          }
          $scope.calendarIncrement = function (calendar, type) {
            calendar.currentDate.add(1, type)
            reloadCalendar(calendar)
          }
          $scope.clearAllCalendarChecked = function (calendar) {
            calendar.checkedDays.splice(0, calendar.checkedDays.length)
            reloadCalendar(calendar)
          }
          $scope.setCalendarToCurrenDay = function (calendar) {
            calendar.currentDate = moment()
            reloadCalendar(calendar)
          }
          $scope.deleteCheckDay = function (day) {
            var idx = _.findIndex($scope.calendar.checkedDays, { key: day.key })
            $scope.calendar.checkedDays.splice(idx, 1)
            reloadCalendar($scope.calendar)
          }

          function getCalendar(curDate, checkedList) {
            // 已经选择的日期
            checkedList = checkedList || []
            // 当前日期
            var curMoment = moment(curDate)
            // 本月第一天
            var curMonthFirstDay = moment(curMoment.startOf('month'))
            // 本月最后一天
            var curMonthLastDay = moment(curMoment.endOf('month'))
            // 本月第一天是一周中的第几天（周日第0天，周一第1天...周六第6天）
            var curMonthFirstDayWeek = curMonthFirstDay.day()
            // 本月最后一天是一周中的第几天
            var curMonthLastDayWeek = curMonthLastDay.day()
            // 当前月日历的第一天（例如本月第一天是周三，那就需要再填充三天）
            var calFirstDay = moment(curMonthFirstDay).subtract((curMonthFirstDayWeek + 6) % 7, 'day')
            // 当前月日历的最后一天
            var calLastDay = moment(curMonthLastDay).add((6 - (curMonthLastDayWeek + 6) % 7), 'day')
            // 当前月日历的所有天数
            var daysArray = []
            var dayPointer = moment(calFirstDay)
            for (; dayPointer < calLastDay; dayPointer.add('day', 1)) {
              // 当前日期是否已经选择
              var isChecked = false
              // 查看当前日期是否在已经选择的日期列表中
              _.forEach(checkedList, function (item) {
                var d = moment(item.value)
                if (d.format('YYYYMMDD') == dayPointer.format('YYYYMMDD')) {
                  isChecked = true
                }
              })
              daysArray.push({
                time: moment(dayPointer),
                date: dayPointer.date(),
                month: dayPointer.month(),
                isChecked: isChecked,
                isCurrentMonth: dayPointer.month() == curDate.month()
              })
            }
            return _.chunk(daysArray, 7)
          }
          function reloadCalendar(calendar) {
            calendar.yearMonth = calendar.currentDate.format('YYYY年MM月')
            calendar.weeks = getCalendar(calendar.currentDate, calendar.checkedDays)
          }

          $scope.$on('clearCheckedMultiDate', function () {
            $scope.checkedDays = $ctrl.checkedDays;
            reloadCalendar($scope.calendar);
          })
          ////////////////

          $ctrl.$onInit = function () {
            $scope.$watch(function () { return $ctrl.checkedDays }, function () {
              $scope.checkedDays = $ctrl.checkedDays;
              reloadCalendar($scope.calendar)
            },true)
          };
          $ctrl.$onChanges = function (changesObj) { };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();