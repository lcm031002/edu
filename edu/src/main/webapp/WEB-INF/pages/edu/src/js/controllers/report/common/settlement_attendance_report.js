angular.module('ework-ui').controller('report_settlementAttendanceReportController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_attendanceReportService',
  report_settlementAttendanceReportController
]);

function report_settlementAttendanceReportController($rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    report_attendanceReportService) {

    $scope.searchParam = {
        p_bu_id: '',
        p_start_date: '',
        p_end_date: '',
        p_branch_id: '',
        p_isCourseTime: 'false',
        p_business_type: '1',
        default_range: 'lastWeek'
    };

    $scope.dataList = undefined;

    $scope.beforeQuery = function() {
        if (!$scope.searchParam.p_bu_id || $scope.searchParam.p_bu_id == -1) {
            $uibMsgbox.error("请选择团队");
            return false;
        }
        if (!$scope.searchParam.p_start_date) {
            $uibMsgbox.error("请选择开始日期");
            return false;
        }
        if (!$scope.searchParam.p_end_date) {
            $uibMsgbox.error("请选择截止日期");
            return false;
        }
        if (!checkStartEndTime($scope.searchParam.p_start_date, $scope.searchParam.p_end_date)) {
            $uibMsgbox.alert('截止日期必须大于或等于开始日期');
            return false;
        }
        return true;
    };

    $scope.queryReport = function () {
        if ($scope.beforeQuery() == false) {
            return;
        }
        $scope.loadStatues = true;
        report_attendanceReportService.querySettlement($scope.searchParam, function (resp) {
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
        var selected_branch = $("#branch_name").find("option:selected").text()
        var branch_name = null;

        if(selected_branch.indexOf("个性化") > 0){
            branch_name = "个性化";
        }else {
            branch_name = "其他";
        }
        $scope.searchParam.p_branch_name = branch_name;

        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_attendanceReportService.outputSettlement($scope.searchParam, function (resp) {
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