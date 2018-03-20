"use strict"
angular.module('ework-ui').controller('erp_wfdComboModalController', [
    '$rootScope',
    '$scope',
    '$uibModalInstance',
    '$uibMsgbox',
    erp_wfdComboModalController
]);

function erp_wfdComboModalController($rootScope,
                                     $scope,
                                     $uibModalInstance,
                                     $uibMsgbox) {
    $scope.handleModalCancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    $scope.handleModalConfirm = function () {
        if(!$scope.modalData.combo_name) {
            $uibMsgbox.error("套餐名称必填！");
            return;
        }
        if(!$scope.modalData.grade_id) {
            $uibMsgbox.error("年级必填！");
            return;
        }
        if(!$scope.modalData.combo_type || $scope.modalData.combo_type < 1) {
            $uibMsgbox.error("套餐类型必填！");
            return;
        }
        if(!$scope.modalData.branch_id) {
            $uibMsgbox.error("校区必填！");
            return;
        }
        if(!$scope.modalData.price) {
            $uibMsgbox.error("价格必填！");
            return;
        }
        if(!$scope.modalData.course_count) {
            $uibMsgbox.error("课次必填！");
            return;
        }

        $uibModalInstance.close();
    };
}
