angular.module('ework-ui').controller('report_teacherWorkloadOrderController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_teacherWorkloadService',
    report_teacherWorkloadOrderController
]);

function report_teacherWorkloadOrderController($rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    report_teacherWorkloadService) {

    $scope.searchParam = {
        p_bu_id: '',
        p_start_date: '',
        p_end_date: '',
        p_branch_id: '',
        p_isCourseTime: 'false',
        p_course_name: '',
        default_range: 'lastMonth'
    };

    $scope.dataList = undefined;

    $scope.queryReport = function () {
        $scope.loadStatues = true;
        report_teacherWorkloadService.queryOrderStudents($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.outputReport = function () {
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_teacherWorkloadService.outputOrderStudents($scope.searchParam, function (resp) {
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

    $scope.searchParam.p_course_id = $scope.GetQueryString("courseId");
    $scope.searchParam.p_course_times = $scope.GetQueryString("courseTime");

    $scope.queryReport();
}