angular.module('ework-ui').controller('report_teacherWorkloadController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_teacherWorkloadService',
    report_teacherWorkloadController
]);

function report_teacherWorkloadController($rootScope,
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
        p_teacher_type: '1',
        p_isCourseTime: 'false',
        p_course_name: '',
        default_range: 'lastMonth'
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
        if (!$scope.searchParam.p_teacher_type) {
            $uibMsgbox.error("请选择老师类型:主讲/辅师");
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
        report_teacherWorkloadService.query($scope.searchParam, function (resp) {
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
        report_teacherWorkloadService.output($scope.searchParam, function (resp) {
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