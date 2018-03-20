angular.module('ework-ui').controller('report_gxhStudentStatusController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_gxhStudentStatusService',
    report_gxhStudentStatusController
]);

function report_gxhStudentStatusController($rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    report_gxhStudentStatusService) {

    $scope.searchParam = {
        p_bu_id: '',
        p_year_month: '',
        p_branch_id: ''
    };

    $scope.dataList = undefined;

    $scope.beforeQuery = function() {
        if (!$scope.searchParam.p_bu_id || $scope.searchParam.p_bu_id == -1) {
            $uibMsgbox.error("请选择团队");
            return false;
        }
        return true;
    };

    $scope.queryReport = function () {
        if ($scope.beforeQuery() == false) {
            return;
        }

        $scope.searchParam.p_year_month = $('#p_year_month').val();
        $scope.loadStatues = true;
        report_gxhStudentStatusService.query($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.outputReport = function () {
        if ($scope.beforeQuery() == false) {
            return;
        }
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_gxhStudentStatusService.output($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

}