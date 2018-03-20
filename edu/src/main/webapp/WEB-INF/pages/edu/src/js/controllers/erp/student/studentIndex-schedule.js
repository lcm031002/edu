angular.module('ework-ui').controller('erp_studentIndexScheduleController', [
  '$rootScope',
  '$scope',
  '$log',
  '$uibMsgbox',
  'calendarConfig',
  'erp_studentScheduleService',
  erp_studentIndexScheduleController
]);

function erp_studentIndexScheduleController(
  $rootScope,
  $scope,
  $log,
  $uibMsgbox,
  calendarConfig,
  erp_studentScheduleService
) {
  moment.locale('zh-cn');
  $scope.monthList = [{
      name: '1月',
      value: 0
    }, {
      name: '2月',
      value: 1
    }, {
      name: '3月',
      value: 2
    }, {
      name: '4月',
      value: 3
    }, {
      name: '5月',
      value: 4
    }, {
      name: '6月',
      value: 5
    }, {
      name: '7月',
      value: 6
    }, {
      name: '8月',
      value: 7
    }, {
      name: '9月',
      value: 8
    }, {
      name: '10月',
      value: 9
    }, {
      name: '11月',
      value: 10
    }, {
      name: '12月',
      value: 11
    }]
  $scope.yearList = []
  var startYear = new Date().getFullYear() + 5;
  for (var i = 0; i < 10; i++) {
    $scope.yearList.push(startYear - i);
  }
    // 日历配置
  $scope.calendar = {
    view: 'month',
    viewDate: new Date(),
    events: [{
      title: '语文',
      startsAt: moment().startOf('day').add(8, 'hours').toDate(),
      endsAt: moment().startOf('day').add(9, 'hours').toDate()
    }]
  };

  $scope.$watch('calendar.view', function (newValue, oldValue) {

  })
  $scope.detail = {
    dateStr: moment().format('YYYY-MM-DD dddd'),
    dayStr: moment().format('DD'),
    courseList: []
  };

  $scope.dateRange = {
    year: moment().year(),
    month: moment().month()
  }
  $scope.searchParam = {
    p_student_id: $("#rootIndex_studentId").val(),
    p_attend_type: '21,28', //正常上课、正常排课
    p_start_date: moment().startOf('month').format('YYYY-MM-DD'),
    p_end_date: moment().endOf('month').format('YYYY-MM-DD')
  };

  $scope.onViewChangeClick = function(calendarDate,calendarNextView) {
    $scope.timeSpanClick(calendarDate);
  }
  $scope.getSearchParam = function () {
    var m = moment({
      y: $scope.dateRange.year,
      M: $scope.dateRange.month
    });
    $scope.searchParam.p_start_date = m.startOf('month').format('YYYY-MM-DD');
    $scope.searchParam.p_end_date = m.endOf('month').format('YYYY-MM-DD');
  }
  $scope.timeSpanClick = function(calendarDate) {
    var date = moment(calendarDate);
    $scope.detail.dateStr = date.format('YYYY-MM-DD dddd');
    $scope.detail.dayStr = date.format('DD');
    $scope.detail.courseList.splice(0, $scope.detail.courseList);
    var list = _.filter($scope.calendar.events, { course_date: Number(date.format('YYYYMMDD')) });
    $scope.detail.courseList = list;
  };


  $scope.addCourse = function(courseList) {
    _.forEach(courseList, function(course) {
      course.title = (course.course_name || ' ') + (course.subject_name || ' ');
      var startTimeStr = '' + course.course_date + ' ' + course.start_time;
      var endTimeStr = '' + course.course_date + ' ' + course.end_time;
      course.startsAt = moment(startTimeStr, 'YYYYMMDD HH:mm').toDate();
      course.endsAt = moment(endTimeStr, 'YYYYMMDD HH:mm').toDate();
      $scope.calendar.events.push(course);
    });
    var m = moment({
      y: $scope.dateRange.year,
      M: $scope.dateRange.month
    });
    var newDate = m.startOf('month').toDate();
    $scope.timeSpanClick(newDate);
  };

  $scope.removeAllCourse = function() {
    $scope.calendar.events.splice(0, $scope.calendar.events.length)
  };

  $scope.prevYear = function () {
    if ($scope.dateRange.year > _.last($scope.yearList)) {
      $scope.dateRange.year --;
      $scope.getCourseList();
    }
  }

  $scope.nextYear = function () {
    if ($scope.dateRange.year < _.head($scope.yearList)) {
      $scope.dateRange.year ++;
      $scope.getCourseList();
    }
  }

  $scope.prevMonth = function () {
    if ($scope.dateRange.month > 0) {
      $scope.dateRange.month--;
      $scope.getCourseList();
    }
  }

  $scope.nextMonth = function () {
    if ($scope.dateRange.month < 11) {
      $scope.dateRange.month++;
      $scope.getCourseList();
    }
  }

  $scope.queryYDYSchedule = function() {
    // 个性化课程
    erp_studentScheduleService.queryYDY($scope.searchParam, function(resp) {
      if (!resp.error) {
        $scope.addCourse(resp.data)
      } else {
        $uibMsgbox.error(resp.message);
        return;
      }
    });
  };

  $scope.queryBJKSchedule = function() {
    // 班级课
    erp_studentScheduleService.queryBJK($scope.searchParam, function(resp) {
      if (!resp.error) {
        $scope.addCourse(resp.data)
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  };
  $scope.getCourseList = function() {
    $scope.getSearchParam();
    var m = moment({
      y: $scope.dateRange.year,
      M: $scope.dateRange.month
    });
    var newDate = m.startOf('month').toDate();
    $scope.calendar.viewDate = newDate;
    $scope.removeAllCourse();
    $scope.queryBJKSchedule();
    $scope.queryYDYSchedule();
  };

  // 初始化
  $scope.initialize = function() {
    $scope.getCourseList();
  };

  $scope.initialize();
}
