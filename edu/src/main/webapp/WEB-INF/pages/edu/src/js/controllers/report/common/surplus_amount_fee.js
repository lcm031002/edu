angular.module('ework-ui').controller('report_surplusAmountFeeController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_surplusAmountFeeService',
    report_surplusAmountFeeController
]);

function report_surplusAmountFeeController($rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    report_surplusAmountFeeService) {

    $scope.searchParam = {
        p_bu_id: '',
        p_branch_id: '',
        p_student_info: '',
        p_business_type: '1'
    };

    $scope.dataList = undefined;

    $scope.queryReport = function () {
        if(!$scope.searchParam.p_bu_id) {
            $uibMsgbox.error("请先选择团队！");
            return;
        }
        $scope.loadStatues = true;
        report_surplusAmountFeeService.query($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.outputReport = function () {
        if(!$scope.searchParam.p_bu_id) {
            $uibMsgbox.error("请先选择团队！");
            return;
        }
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_surplusAmountFeeService.output($scope.searchParam, function (resp) {
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