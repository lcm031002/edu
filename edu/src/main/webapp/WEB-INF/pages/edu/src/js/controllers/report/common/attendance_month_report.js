angular.module('ework-ui').controller('report_attendanceMonthController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_attendanceMonthService',
    report_attendanceMonthController
]);

function report_attendanceMonthController($rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    report_attendanceMonthService) {

    $scope.searchParam = {
        p_bu_id: '',
        p_branch_id: '',
        p_yearMonth: new Date().format("yyyy年MM月")
    };

    $scope.dataList = undefined;

    $scope.queryReport = function () {
        if(!$scope.searchParam.p_bu_id) {
            $uibMsgbox.error("请先选择团队！");
            return;
        }
        $scope.loadStatues = true;
        report_attendanceMonthService.query($scope.getQueryParam(), function (resp) {
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
        report_attendanceMonthService.output($scope.getQueryParam(), function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.getQueryParam = function () {
        var queryParam = angular.copy($scope.searchParam);
        queryParam.p_yearMonth = $('#p_yearMonth').val();
        if(queryParam.p_yearMonth) {
            queryParam.p_yearMonth = queryParam.p_yearMonth.replace('年', '-').replace('月', '');
        }
        return queryParam;
    };

}