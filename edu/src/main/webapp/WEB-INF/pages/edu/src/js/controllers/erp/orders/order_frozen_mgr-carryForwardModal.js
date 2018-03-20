"use strict"
angular.module('ework-ui').controller('erp_orderFrozenMgrCarryForwardModalController', [
    '$rootScope',
    '$scope',
    '$uibModalInstance',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    erp_orderFrozenMgrCarryForwardModalController
]);

function erp_orderFrozenMgrCarryForwardModalController($rootScope,
                                                       $scope,
                                                       $uibModalInstance,
                                                       $uibMsgbox) {
    $scope.conf = {
        remark: '锁定订单到期，财务手工结转。'
    };

    $scope.handleModalCancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.handleModalConfirm = function () {
        if(!$scope.conf.remark) {
            $uibMsgbox.alert("备注不能为空！");
            return;
        }
        $uibModalInstance.close({
            remark: $scope.conf.remark
        });
    };
}
