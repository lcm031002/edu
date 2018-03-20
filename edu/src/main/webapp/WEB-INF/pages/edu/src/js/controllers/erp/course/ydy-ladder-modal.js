"use strict"
angular.module('ework-ui').controller('erp_ydyLadderModalController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    '$uibModalInstance',
    function ($rootScope,
              $scope,
              $uibMsgbox,
              $uibModalInstance,
              erp_ydyLadderModalController) {
        $scope.handleModalCancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.handleModalConfirm = function () {
            if(!$scope.ladderModelData.ladder_level_name) {
                $uibMsgbox.error("阶梯名称必填！");
                return;
            }
            if(!$scope.ladderModelData.level_condition) {
                $uibMsgbox.error("达成条件必填！");
                return;
            }
            if(!$scope.ladderModelData.level_price) {
                $uibMsgbox.error("达成单价必填！");
                return;
            }

            $uibModalInstance.close();
        };
    }
]);
