angular.module('ework-ui')
  .controller('erp_courseSchedulingYdyMatchEditController', [
    '$scope',
    '$log',
    '$state',
    '$stateParams',
    '$uibModal',
    '$uibMsgbox',
    'erp_stuCourseSchedApplyYdyService',
    erp_courseSchedulingYdyMatchEditController
  ])

function erp_courseSchedulingYdyMatchEditController (
  $scope,
  $log,
  $state,
  $stateParams,
  $uibModal,
  $uibMsgbox,
  erp_stuCourseSchedApplyYdyService
) {
  // 申请单
  $scope.apply = {}
  // 申请单ID
  $scope.applyId = 0

  // 获取申请单详情
  $scope.getApplyDetail = function (applyId) {
    return erp_stuCourseSchedApplyYdyService.getDetail({
      id: applyId
    }).$promise.then(function (resp) {
      if (!resp.error) {
        $scope.apply = resp.data
      } else {
        $uibMsgbox.error(resp.message)
      }
    }, function (resp) {})
  }
  // 获取初步排课意向表
  $scope.getApplyPlanList = function (applyId) {
    applyId = applyId || $scope.applyId;
    return erp_stuCourseSchedApplyYdyService.getApplyPlanList({
      applyId: applyId
    }).$promise.then(function (resp) {
      if (!resp.error) {
        $scope.applyPlanList = resp.data
      } else {
        $uibMsgbox.error(resp.message)
      }
    }, function (resp) { })
  }
  // 初始化页面
  $scope.init = function () {
    $scope.applyId = $stateParams.id
    if ($state.current.name == "classesScheduleYdyMatchDetail") {
      $scope.optype = 'view'
    } else {
      $scope.optype = 'edit'
    }
    $scope.getApplyDetail($scope.applyId).then(function () {
      return $scope.getApplyPlanList($scope.applyId)
    })
  }
  // 返回
  $scope.goBack = function() {
    $state.go('classesScheduleYdyMatch')
  }
  // 调用初始化页面方法
  $scope.init()
}