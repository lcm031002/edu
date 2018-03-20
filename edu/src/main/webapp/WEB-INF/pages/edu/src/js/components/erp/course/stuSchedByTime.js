(function () {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedByTime', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedByTime.html',
      controllerAs: '$ctrl',
      bindings: {
        orderList: '<?',
        applyItem: '<?',
        studentId: '<',
        studentCounselors: '<?',
        onSchedSuccess: '&?'
      },
      controller: [
        '$scope',
        '$uibModal',
        '$uibMsgbox', 
        'erp_tpScheduleTimeService', 
        'erp_studentCourseSchedulingService',
        function (
          $scope,
          $uibModal,
          $uibMsgbox,
          erp_tpScheduleTimeService,
          erp_studentCourseSchedulingService
        ) {
          var $ctrl = this;
          $scope.apply = {};
          $scope.moment = moment;
          // 时间档期列表
          $scope.tpScheduleTimeList = []
          $scope.checkedDays = [];
          $scope.schedule_type = 'time';
          $scope.schedule = {
            applyId: null,
            schedule_type: 'time',
            subject: null,
            subjectId: null,
            teacher: null,
            period: null,
            startTime: null,
            endTime: null,
            defaultDuration: 0
          }

          $scope.onCheckDays = function (days) {
            $scope.checkedDays = days
          }

          $scope.deleteCheckDay = function (day) {
              var idx = _.findIndex($scope.checkedDays, {key: day.key})
              $scope.checkedDays.splice(idx, 1)
          }

          // 获取时间档期列表
          $scope.getTpScheduleTimeList = function () {
            erp_tpScheduleTimeService.queryList({}, function (resp) {
              if (!resp.error) {
                _.forEach(resp.data, function (item) {
                  item.checked = false
                })
                $scope.tpScheduleTimeList = resp.data
              } else {
                $uibMsgbox.error(resp.message)
              }
            })
          }

          // 时间档期切换事件处理
          $scope.onTpScheduleTimeChange = function (period) {
            _.forEach($scope.tpScheduleTimeList, function (period) {
              period.checked = false
            })
            $scope.schedule.period = period
            $scope.schedule.startTime = period.startTime
            $scope.schedule.endTime = period.endTime
            $scope.schedule.defaultDuration = moment(period.endTime, 'HH:mm')
              - moment(period.startTime, 'HH:mm')
            period.checked = true
          }

          // 监听排课开始时间，改变时，结束时间也相应改变
          $scope.$watch('schedule.startTime', function (newValue, oldValue) {
            $scope.schedule.endTime = moment(moment(newValue, 'HH:mm')
              + $scope.schedule.defaultDuration).format('HH:mm')
          })

          // 选择老师事件处理
          $scope.onSelectTeacher = function (teacher) {
            $scope.schedule.teacher = _.cloneDeep(teacher)
          }
          // 获取已选择的报班单
          function getSelectedCourseList(courseList) {
            var courseIdList = [];
            _.forEach(courseList, function (course) {
              if (course.checked) {
                courseIdList.push(course.id);
              }
            })
            return courseIdList.join(',');
          }

          function validAddScheduleByTime() {
            if (getSelectedCourseList($scope.orderList).length <= 0) {
              $uibMsgbox.error('请先选择报班单！')
              return false
            }
            if (!$scope.schedule.startTime) {
              $uibMsgbox.error('请选择上课时间')
              return false
            }
            if (!$scope.schedule.endTime) {
              $uibMsgbox.error('请选择下课时间')
              return false
            }

            var schedEndTime = $scope.schedule.endTime;
            if (schedEndTime == '00:00') {
              schedEndTime = "24:00";
            }
            if (moment($scope.schedule.startTime, 'HH:mm') - moment(schedEndTime, 'HH:mm') > 0) {
              $uibMsgbox.error('下课时间不能早于上课时间！')
              return false;
            }
            if (!$scope.schedule.subjectId) {
              $uibMsgbox.error('请选择科目')
              return false
            }
            if (!$scope.schedule.teacher) {
              $uibMsgbox.error('请选择老师')
              return false
            }

            if (!_.isArray($scope.checkedDays) || $scope.checkedDays.length <= 0) {
              $uibMsgbox.error('请至少选择一个日期')
              return false
            }
            return true
          }

          // 检测课程是否冲突
          function queryConflictScheduling(
            studentId,
            startTime,
            endTime,
            teacherId,
            checkedDays,
            callback) {
            var waitingModal = $uibMsgbox.waiting('排课校验中，请稍候...')
            var schedulingArry = []
            _.forEach(checkedDays, function (courseDay) {
              schedulingArry.push({
                student_id: studentId,
                start_time: startTime,
                end_time: endTime,
                teacher_id: teacherId,
                course_date: courseDay.key
              })
            })
            return erp_studentCourseSchedulingService.queryConflictScheduling(schedulingArry)
              .$promise.then(function (resp) {
                waitingModal.close()
                if (!resp.error) {
                  callback(resp)
                } else {
                  $uibMsgbox.error(resp.message)
                }
              }, function (resp) {
                $uibMsgbox.error(resp.message)
              });
          }
          $scope.handleAddSched = function () {
            if (!validAddScheduleByTime()) {
              return false;
            }
            queryConflictScheduling(
              $ctrl.studentId,
              $scope.schedule.startTime,
              $scope.schedule.endTime,
              $scope.schedule.teacher.id,
              $scope.checkedDays,
              function (resp) {
                $scope.conflictObject = resp.data
                $scope.conflictList = []
                _.forIn(resp.data, function (value, key) {
                  $scope.conflictList.push(value[0])
                })
                _.forEach($scope.checkedDays, function (day) {
                  day.cls = null;
                  if (!!$scope.conflictObject[day.key]) {
                    day.cls = "danger"
                  }
                })
                $scope.conflictList = _.orderBy($scope.conflictList, 'course_date')
                var courseIdListStr = getSelectedCourseList($scope.orderList);
                $uibModal.open({
                  backdrop: false,
                  templateUrl: 'newScheduleListByDate.html',
                  size: 'xlg',
                  scope: $scope,
                  controller: ['$scope', function ($scope) {
                    $scope.onOk = function () {
                      if ($scope.checkedDays.length <= 0) {
                        return $uibMsgbox.error('排课列表为空，请选择排课日期！', function () {
                          $scope.$dismiss();
                        })
                      }
                      var scheduleList = []
                      _.forEach($scope.checkedDays, function (day) {
                        scheduleList.push({
                          orderCourseIds: courseIdListStr,
                          scheduleType: "time",
                          apply_id: $scope.schedule.applyId,
                          student_id: $ctrl.studentId,
                          start_time: $scope.schedule.startTime,
                          end_time: $scope.schedule.endTime,
                          startDate: day.value,
                          subject_id: $scope.schedule.subjectId,
                          teacher_id: $scope.schedule.teacher.id
                        })
                      })
                      var waitingModal = $uibMsgbox.waiting('排课中，请稍候...')
                      erp_studentCourseSchedulingService.addYdyOrderCourseSchedulingList(
                        scheduleList, function (resp) {
                          waitingModal.close();
                          if (!resp.error) {
                            if (!_.isArray($scope.addedScheduleList)) {
                              $scope.addedScheduleList = []
                            }
                            _.forEach(resp.data, function (item) {
                              $scope.addedScheduleList.push(item)
                            })
                            $uibMsgbox.success(resp.message || '添加排课成功！', function () {
                              $scope.$close();
                            });
                          } else {
                            $uibMsgbox.error(resp.message);
                          }
                        });
                    }
                  }]
                }).result.then(function () {
                  $scope.checkedDays.splice(0, $scope.checkedDays.length)
                  $scope.$broadcast('clearCheckedMultiDate');
                  $scope.schedule.period = null
                  $scope.schedule.startTime = ''
                  $scope.schedule.endTime = ''
                  $ctrl.onSchedSuccess();
                })
              }
            )
          }

          $scope.getTpScheduleTimeList();

          function queryIndexCounselors() {
            erp_studentIndexCounselorsService.query({
              studentId: $scope.studentId
            }).$promise.then(function (resp) {
              if (!resp.error) {
                $scope.studentIndexCounselors = resp.data
              } else {
                $uibMsgbox.error(resp.message)
              }
            }, function (resp) {
              $uibMsgbox.error(resp.message)
            });
          }
          ////////////////
          // 获取时间档期

          $ctrl.$onInit = function () {
            // 监听初步排课计划
            $scope.$watch(function () { return $ctrl.applyItem }, function () {
              $scope.applyItem = _.cloneDeep($ctrl.applyItem);
              $scope.schedule.teacher = {
                id: $scope.applyItem.teacherId,
                teacher_name: $scope.applyItem.teacherName,
                encoding: $scope.applyItem.teacherEncoding
              }
              $scope.schedule.applyId = $scope.applyItem.applyId;
              
              $scope.schedule.defaultDuration = moment($scope.applyItem.endTime, 'HH:mm')
                - moment($scope.applyItem.startTime, 'HH:mm')
              $scope.schedule.startTime = $scope.applyItem.startTime;
              $scope.schedule.endTime = $scope.applyItem.endTime;
              $scope.schedule.subjectId = $scope.applyItem.subjectId;
            })

            // 监听报班单列表
            $scope.$watch(function () { return $ctrl.orderList }, function () {
              $scope.orderList = _.cloneDeep($ctrl.orderList);
            })
          };
          $ctrl.$onChanges = function (changesObj) { };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();