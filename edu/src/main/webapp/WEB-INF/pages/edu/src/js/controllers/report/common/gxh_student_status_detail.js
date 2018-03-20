angular.module('ework-ui').controller('report_gxhStudentStatusDetailController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_gxhStudentStatusService',
    report_gxhStudentStatusDetailController
]);

function report_gxhStudentStatusDetailController($rootScope,
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
        var _uibModalInstance = $uibMsgbox.waiting('数据加载中，请稍候...');
        report_gxhStudentStatusService.queryDetail($scope.searchParam, function (resp) {
            _uibModalInstance.close();
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
        report_gxhStudentStatusService.outputDetailExcel($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.GetQueryString = function(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  decodeURI(r[2]); return null;
    };

    $scope.init = function () {
        $scope.searchParam.p_bu_id = $scope.GetQueryString("buId");
        $scope.searchParam.p_branch_id = $scope.GetQueryString("branchId");
        $scope.searchParam.p_year_month = $scope.GetQueryString("yearMonth");
        $scope.searchParam.p_counselor_name = $scope.GetQueryString("counselorName");
        $scope.queryReport();
    }

    $scope.init();

}