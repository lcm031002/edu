(function () {
  'use strict';

  // Usage:2017.12.1
  // 一对一师生匹配的匹配操作页面
  // Creates:yans@histudy.com
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedByMatch', {
      templateUrl: 'templates/components/erp/course/stuSchedByMatch.html',
      controllerAs: '$ctrl',
      bindings: {
        matchList: '<?',
        onMatchSuccess: '&?',
        apply: '<?'
      },
      controller: [
        '$scope',
        '$log',
        '$state',
        '$stateParams',
        '$uibModal',
        '$uibMsgbox',
        'erp_stuCourseSchedApplyYdyService',
        'erp_tpScheduleTimeService',
        'erp_teacherGroupService',
        'erp_teacherService',
        function (
          $scope,
          $log,
          $state,
          $stateParams,
          $uibModal,
          $uibMsgbox,
          erp_stuCourseSchedApplyYdyService,
          erp_tpScheduleTimeService,
          erp_teacherGroupService,
          erp_teacherService
        ) {
          var $ctrl = this;
          $scope.detail=$ctrl.matchList;
          $scope.teachGroupList = [];
          $scope.moment = moment;
          $scope.tpScheduleTimeList = [];
          $scope.teacher = $ctrl.matchList;
          // 匹配取消
          $scope.onCancle = function (period) {
            $scope.onTpScheduleTimeChange({});
            $scope.detail.courseDate = null;
            $scope.detail.teacher_name = null;
            $scope.detail.teacherEncoding = null;
            $scope.detail.remark = null;
          }
          // 匹配确认验证
          $scope.valid = function () {
            if (!$scope.detail.subjectName) {
              $uibMsgbox.error('请选择科目！');
              return false;
            }
            if (!$scope.detail.teachGroupId) {
              $uibMsgbox.error('请选择教研组！');
              return false;
            }
            if (!$scope.detail.courseDate) {
              $uibMsgbox.error('请选择上课日期！');
              return false;
            }
            if (!$scope.detail.startTime) {
              $uibMsgbox.error('请选择上课时间！');
              return false;
            }
            if (!$scope.detail.endTime) {
              $uibMsgbox.error('请选择下课时间！');
              return false;
            }
            if (!$scope.detail.teacher_name && !$scope.detail.teacherEncoding) {
              $uibMsgbox.error('请选择上课的老师');
              return false;
            }
            // if (!$scope.detail.remark) {
            //   $uibMsgbox.error('请输入备注！');
            //   return false;
            // }
            var detailEndTime = $scope.detail.endTime;
            if (detailEndTime == '00:00') {
              detailEndTime = "24:00";
            }
            if (moment($scope.detail.startTime, 'HH:mm') - moment(detailEndTime, 'HH:mm') > 0) {
              $uibMsgbox.error('下课时间不能早于上课时间！')
              return false;
            }
            return true;
          }

          // 匹配确认
          $scope.onOk = function () {
            if($scope.detail.courseDate){
              $scope.detail.weekday = moment($scope.detail.courseDate, 'YYYY-MM-DD').format('dddd')              
            }
            if (!$scope.valid()) {
              return false;
            }
            var waitingModal = $uibMsgbox.waiting('保存中，请稍候...')
            erp_stuCourseSchedApplyYdyService.putApplyPlan(
              _.pick($scope.detail, ['id', 'applyId', 'courseDate', 'startTime',
                'endTime', 'weekday', 'teacherId', 'teacherName', 'subjectId', 'subjectName',
                'remark', 'status', 'courseSpId'])
            ).$promise.then(function (resp) {
              waitingModal.close();
              if (!resp.error) {
                if (typeof $ctrl.onMatchSuccess == 'function') {
                  $ctrl.onMatchSuccess()
                }
              } else {
                $uibMsgbox.error(resp.message)
              }
            })
          }
          // 获取教研组
          $scope.getGroup = function () {
            erp_teacherGroupService.queryList({}, function (resp) {
              if (!resp.error) {
                $scope.teachGroupList = resp.data;
              } else {
                $uibMsgbox.error(resp.message)
              }
            })
          }
          $scope.getGroup();

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
            $scope.detail.period = period
            $scope.detail.startTime = period.startTime
            $scope.detail.endTime = period.endTime
            $scope.detail.defaultDuration = moment(period.endTime, 'HH:mm')
              - moment(period.startTime, 'HH:mm')
            period.checked = true
          }
          $scope.getTpScheduleTimeList();
          // 选择老师
          $scope.onSearchTearcher = function (search_info) {
            return erp_teacherService.page({
              search_info: search_info,
              subjectId: $scope.detail.subjectId
            }).$promise;
          }
          $scope.onSelectTeacher = function (teacher) {
            $scope.detail.teacherName = teacher.teacher_name;
            $scope.detail.teacherEncoding = teacher.encoding;
            $scope.detail.teacherId = teacher.id;
          }

          $scope.setWeekday = function() {
            if ($scope.detail.courseDate) {
              $scope.detail.weekday = "星期"+"日一二三四五六".charAt(new Date($scope.detail.courseDate).getDay());
            }
          }

          $scope.viewSchedule = function (group) {
            if (!group.subjectName) {
              $uibMsgbox.error('科目不能为空，请选择初步排课意向！');
              return false;
            }else if(!group.teachGroupId){
              $uibMsgbox.error('请选择教研组！');
              return false;
            }
            else{
              window.open('?groupId='+group.teachGroupId+'#/teachers/courseSchedule');
            }
          }
          //查询老师
          $scope.detail.seg=[];
          $scope.searchSchedule = function(teacher){
            $scope.detail.seg = [$scope.detail.courseDate,$scope.detail.startTime,$scope.detail.endTime];
          };
          $ctrl.$onInit = function () {
            $scope.$watch(function () { return $ctrl.matchList }, function () {
              $scope.detail = _.cloneDeep($ctrl.matchList);
              $scope.onTpScheduleTimeChange({})
              $scope.onSearchTearcher();
            })
            
            $scope.$watch(function () { return $ctrl.apply }, function () {
              $scope.apply = $ctrl.apply;
            })

            $scope.$watch(function () { return $scope.detail.courseDate }, function () {
              if($scope.detail.courseDate){
                $scope.setWeekday();
              }else{
                $scope.detail.weekday = '';
              }
            })
            $scope.$watch(function () { return $scope.detail.teachGroupId }, function () {
              if($scope.detail.teachGroupId !=null){
                setTimeout(function(){
                  document.getElementById('applyPlanDialog.html').scrollIntoView();
                 },0)
              }
            })
          };
          $ctrl.$onChanges = function (changesObj) {
          };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();