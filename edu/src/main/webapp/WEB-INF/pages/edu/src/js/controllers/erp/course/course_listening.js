"use strict";
angular.module('ework-ui').controller('erp_courseListeningController', [
    '$scope',
    '$log',
    '$uibModal',
    '$uibMsgbox',
    'erp_courseListeningService',
    erp_courseListeningController
]);

function erp_courseListeningController($scope,
                                       $log,
                                       $uibModal,
                                       $uibMsgbox,
                                       erp_courseListeningService) {
    // 搜索字段
    $scope.searchParam = {
        start_date: getCurrentDate(),
        end_date: getCurrentDate()
    };
    // 列表
    $scope.dataList = undefined;

    // 团队、校区查询条件改变
    $scope.onBranchChange = function () {
        $scope.quietQuery();
    };

    // 起止日期改变
    $scope.onDateChange = function () {
        $scope.quietQuery();
    };

    // 安静的查询，只有条件满足时才触发查询
    $scope.quietQuery = function () {
        if (!$scope.searchParam.bu_id || $scope.searchParam.bu_id == -1) {
            return;
        }
        if (!$scope.searchParam.start_date) {
            return;
        }
        if (!$scope.searchParam.end_date) {
            return;
        }
        if (!checkStartEndTime($scope.searchParam.start_date, $scope.searchParam.end_date)) {
            return;
        }

        $scope.query();
    };

    $scope.beforeQuery = function () {
        if (!$scope.searchParam.bu_id || $scope.searchParam.bu_id == -1) {
            $uibMsgbox.error("请选择团队");
            return false;
        }
        if (!$scope.searchParam.start_date) {
            $uibMsgbox.error("请选择开始日期");
            return false;
        }
        if (!$scope.searchParam.end_date) {
            $uibMsgbox.error("请选择截止日期");
            return false;
        }
        if (!checkStartEndTime($scope.searchParam.start_date, $scope.searchParam.end_date)) {
            $uibMsgbox.alert('截止日期必须大于或等于开始日期');
            return false;
        }
        return true;
    };

    // 查询
    $scope.query = function () {
        if ($scope.beforeQuery()) {
            erp_courseListeningService.queryList({
                p_bu_id: $scope.searchParam.bu_id,
                p_branch_id: $scope.searchParam.branch_id,
                p_start_date: $scope.searchParam.start_date,
                p_end_date: $scope.searchParam.end_date
            }, function (resp) {
                if (!resp.error) {
                    $scope.dataList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    };

    // 导出数据
    $scope.exportExcel = function () {
        if ($scope.beforeQuery()) {
            erp_courseListeningService.exportExcel({
                p_bu_id: $scope.searchParam.bu_id,
                p_branch_id: $scope.searchParam.branch_id,
                p_start_date: $scope.searchParam.start_date,
                p_end_date: $scope.searchParam.end_date
            }, function (resp) {
                if (!resp.error) {
                    window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    };

    $scope.initialize = function () {
        if (!$scope.dataList) {
            $scope.quietQuery();
        }
    };

    $scope.initialize();
}
