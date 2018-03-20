angular.module('ework-ui').controller('erp_courseSchedulingYdyApplyDetailController', [
  '$scope',
  '$log',
  '$state',
  '$stateParams',
  '$uibMsgbox',
  'erp_stuCourseSchedApplyYdyService',
  erp_courseSchedulingYdyApplyDetailController
])

function erp_courseSchedulingYdyApplyDetailController (
  $scope,
  $log,
  $state,
  $stateParams,
  $uibMsgbox,
  erp_stuCourseSchedApplyYdyService
) {
  $scope.applyBaseReadOnly = true
  $scope.termTypes = [{
    value: "1",
    label: '上学期'
  }, {
    value: "2",
    label: '下学期'
  }]
  $scope.applyTypes = [{
    value: "1",
    label: '新单'
  }, {
    value: "2",
    label: '加课单'
  }, {
    value: "3",
    label: '换单'
  }]
  
  $scope.stuSchedTimeList = [
    {startTime: '08:00', endTime: '10:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '10:15', endTime: '12:15', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '14:00', endTime: '16:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '16:15', endTime: '18:15', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '19:00', endTime: '21:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']}
  ]

  $scope.apply = {}
  $scope.applyId = 0
  
  $scope.getApplyDetail = function (applyId) {
    erp_stuCourseSchedApplyYdyService.getDetail({
      id: applyId
    }, function (resp) {
      if (!resp.error) {
        if (!resp.data) {
          return
        }
        $scope.apply = resp.data
        // console.log(resp.data)stuSchedPlanList
        if (resp.data.schedule) {
          $scope.stuSchedTimeList = JSON.parse(resp.data.schedule)
        }
      } else {
        $uibMsgbox.error(resp.message)
      }
    })
  }

  $scope.init = function () {
    $scope.applyId = $stateParams.id
    $scope.getApplyDetail($scope.applyId)
  }

  $scope.goBack = function() {
    $state.go('classesScheduleYdyApply')
  }
  $scope.init()
}