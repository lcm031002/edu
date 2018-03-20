(function () {
  'use strict';

  // Usage:2017.01.03
  // 教室日历表
  // Creates:yans@histudy.com
  // 

  angular
    .module('ework-ui')
    .directive('onSizeChanged', ['$window', function ($window) {
      return {
        restrict: 'A',
        scope: { onSizeChanged: '&' },
        link: function (scope, $element, attr) {
          $window.addEventListener('resize', onWindowResize);
          function onWindowResize() {
            var expression = scope.onSizeChanged();
            expression();
          };
        }
      }
    }])
    .component('klcTeacherSchedule', {
      templateUrl: 'templates/components/erp/teacher/teacherSchedule.html',
      controllerAs: '$ctrl',
      transclude: true,
      bindings: {
        groupId: '=?',
        groupAll: '=?',
        searchSeg: '=?',

      },
      controller: [
        '$scope',
        '$log',
        '$state',
        '$uibMsgbox',
        '$uibModal',
        'erp_TeacherListService',
        function (
          $scope,
          $log,
          $state,
          $uibMsgbox,
          $uibModal,
          erp_TeacherListService) {
          var $ctrl = this;
          $scope.groupAll = $ctrl.groupAll;
          // $scope.pageSize = 22;
          // if( $scope.groupAll==true){
          //   $scope.pageSize = 2200;
          // }
          $scope.courseAllList = [] //教师所有的课程列表
          $scope.courseList = []; //教师课程列表
          $scope.teacherDetail = []; //教师详情列表
          $scope.paginationConf = {
            currentPage: 1, // 当前页
            totalItems: 0,
            itemsPerPage: 8,
            onChange: function () {
              $scope.queryTeacher(); //教师搜索
            }
          };

          //悬浮框添加
          $("[data-toggle=tooltip]").tooltip({html : true });

          //获取课程
          $scope.getCourse = function () {
            erp_TeacherListService
              .queryTeacherSched({
                startDate: $scope.curFroms,
                endDate: $scope.curTos,
                teacherId: $scope.teacherDetail.id
              },
              function (resp) {
                if (!resp.error) {
                  var item =[];
                  var lists =[];
                  $scope.space = ($('.teacher-week_title').width() -60 )/ 7; //获取兼容不同屏幕的课程间距
                  _.forEach(resp.data, function (course) { 
                    var i = 0;
                    _.forEach(course.showList,function(arr){
                      arr.xAxis = arr.xAxis * $scope.space;
                      if(i==1){
                        arr.xAxis += 28; //同一时间段的第二个课程加一个间距
                      }
                      i = i + 1;
                      item.push(arr)
                    })
                    course.x = course.x * $scope.space +$scope.space -35; //更多标签的x轴
                    lists.push(course)
                  })

                  //同一天的课程错开
                  for (var i = 0; i < item.length; i = i + 1) {
                    for (var j = i + 1; j < item.length; j ++) {
                      var day = item[i].courseDate == item[j].courseDate;
                      var tag = item[i].yAxis + item[i].courseLen;
                      var jtag = item[j].yAxis + item[j].courseLen;
                      if (day) {
                        if ((item[j].yAxis < tag && item[j].yAxis > item[i].yAxis)
                          || (item[j].yAxis != item[i].yAxis &&jtag < tag && jtag > item[i].yAxis)) {
                          item[j].xAxis = item[j].xAxis + 28;
                        }
                      }
                    }
                  }
                  // console.log(resp.data)
                  $scope.courseList = item;
                  $scope.courseAllList = lists;
                }
              });
          }
          
          //屏幕自适应
          $scope.logResize = function () {
            $scope.getCourse();
            $scope.topCourse = $('.teacher-week_title').height();
            // console.log($scope.topCourse)
            // console.log($scope.space)
          };

          //搜索教师
          $scope.queryTeacher = function () {
            erp_TeacherListService
              .query({
                pageSize:  $scope.paginationConf.itemsPerPage,
                currentPage: $scope.paginationConf.currentPage,
                teach_group_id: $scope.groupId,
                search_info: $scope.teacher_name,
                courseDate: $scope.courseDate,
                startTime: $scope.startTime,
                endTime: $scope.endTime
              },
              function (resp) {
                if (!resp.error) {
                  $scope.paginationConf.totalItems = resp.total || 0;
                  $scope.groupteacherList = resp.data;
                  if ($scope.groupId != null || $scope.groupAll== true) {
                    $scope.teacherListed = $scope.groupteacherList;
                    // if($scope.groupAll == true){
                    //   $scope.teacherListed = $scope.groupteacherList.slice(0,13)
                    // }
                    if ($scope.teacherListed == '') {
                      $scope.teacherDetail = [];
                    } else {
                      $scope.getTeacher($scope.teacherListed[0])
                      // $scope.teacherDetail = $scope.teacherListed[0];                       
                    }
                    $scope.getCourse();
                  }
                  $scope.getCourse();
                }
              });
          };
          
          //选择老师
          $scope.getTeacher = function (arr) {
            // $scope.curWeek();//切换老师重置时间到本周
            $scope.teacherDetail = arr;
            _.forEach($scope.teacherListed, function (apply) {
              apply.checked = apply.id === arr.id
            });
            $scope.getCourse();
          }

          //日历
          $scope.curFirst;
          $scope.weekList = [];
          $scope.timeList = [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
          $scope.year = new Date().getFullYear();
          $scope.month = (new Date().getMonth() + 1);
          $scope.day = new Date().getDate();
          $scope.weekDay = [];
          $scope.formatDate = function (date) {
            $scope.year = date.getFullYear() + '年';
            $scope.month = (date.getMonth() + 1) + '月';
            $scope.day = date.getDate() + '日';
            $scope.weekDay = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'][date.getDay()];

            return $scope.weekDay + '(' + $scope.month + $scope.day + ')';
          };
          $scope.changeDate = function (date, n) {
            date.setDate(date.getDate() + n);
            return date;
          };

          $scope.getWeek = function (date) {
            var week = date.getDay() + 1;
            date = $scope.changeDate(date, week * -1);
            $scope.curFirst = new Date(date);
            var curdata = $scope.curFirst;
            $scope.curFrom = Format('yyyy年MM月dd日', curdata)
            $scope.curTo = Format('yyyy年MM月dd日', new Date(curdata.getTime() + 24 * 6 * 60 * 60 * 1000))
            $scope.curFroms = Format('yyyy-MM-dd', curdata)
            $scope.curTos = Format('yyyy-MM-dd', new Date(curdata.getTime() + 24 * 6 * 60 * 60 * 1000))
            for (var i = 0; i < 7; i++) {
              $scope.weekList[i] = $scope.formatDate(i == 0 ? date : $scope.changeDate(date, 1));
            }
            $scope.getCourse();
          };
          $scope.prevWeek = function () {
            $scope.getWeek($scope.changeDate($scope.curFirst, -5));
          };
          $scope.nextWeek = function () {
            $scope.getWeek($scope.changeDate($scope.curFirst, 10));
          };
          $scope.curWeek = function () {
            $scope.getWeek(new Date());
          };
          $scope.getWeek(new Date());

          $ctrl.$onInit = function () {
            $scope.$watch(function () { return $ctrl.groupId }, function () {
              $scope.groupId = $ctrl.groupId;
              $scope.queryTeacher()
            })
            $scope.$watch(function () { return $ctrl.searchSeg }, function () {
              var search_seg = $ctrl.searchSeg || [];
              $scope.courseDate = search_seg[0];
              $scope.startTime = search_seg[1];
              $scope.endTime = search_seg[2];

              $scope.queryTeacher()
            })
          };
          $ctrl.$onChanges = function (changesObj) {
          };
          $ctrl.$onDestroy = function () {
          };
        }
      ]
    });
})();