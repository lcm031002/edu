"use strict"
angular.module('ework-ui').controller('erp_studentIndexOrderDetailUnlockModalController', [
    '$rootScope',
    '$scope',
    '$uibModalInstance',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    erp_studentIndexOrderDetailUnlockModalController
  ])

function erp_studentIndexOrderDetailUnlockModalController(
  $rootScope,
  $scope,
  $uibModalInstance,
  $uibMsgbox
) {
	 $scope.conf = {
			 remark: ''
	 };
	  $scope.handleModalCancel = function() {
	    $uibModalInstance.dismiss('cancel');
	  }
	  
	  $scope.handleModalConfirm = function() {
		  $uibModalInstance.close({
			  remark: $scope.conf.remark
		  });
	  }
  
}
