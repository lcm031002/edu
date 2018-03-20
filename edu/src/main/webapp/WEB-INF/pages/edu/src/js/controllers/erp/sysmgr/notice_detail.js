"use strict"
angular.module('ework-ui').controller('erp_noticeDetailController', [
  '$rootScope',
  '$scope',
  '$uibModalInstance',
  '$uibMsgbox',
  'optype',
  erp_noticeDetailController
])

function erp_noticeDetailController(
  $rootScope,
  $scope,
  $uibModalInstance,
  $uibMsgbox,
  optype
) {
	
	$scope.optype = optype || 'view';
	
	if ($scope.optype == 'add') {
		$scope.notice = {};
	}

  $scope.messageTypeList = [{label:'公告',value:1}];

  $scope.handleModalCancel = function() {
    $uibModalInstance.dismiss('cancel');
  }

  $scope.beforeAdd = function() {
    if (!$scope.notice.subject) {
      $uibMsgbox.error("请输入标题");
      return false;
    }

    if (!$scope.notice.type) {
      $uibMsgbox.error("请选择通知类型");
      return false;
    }

    if (!$scope.notice.start_date) {
      $uibMsgbox.error("请选择开始日期");
      return false;
    }

    if (!$scope.notice.end_date) {
      $uibMsgbox.error("请选择截止日期");
      return false;
    }

    if (!checkStartEndTime($scope.notice.start_date, $scope.notice.end_date)) {
      $uibMsgbox.error("截止日期不能小于开始日期");
      return false;
    }

    if (!$scope.notice.content) {
      $uibMsgbox.error("请输入内容");
      return false;
    }
    return true;
  }

  $scope.handleModalConfirm = function() {
    if ($scope.beforeAdd()) {
      $uibModalInstance.close({
      	optype: $scope.optype,
      	notice:$scope.notice
      });
    }
  }
}
