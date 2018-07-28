"use strict";
angular.module('ework-ui').controller('report_teacherAttendReportController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'report_wfdTeacherAttendanceService',
    report_teacherAttendReportController
]);

function report_teacherAttendReportController($rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    report_wfdTeacherAttendanceService) {


    $scope.init = function () {

        $scope.selectorList = [
            {label:'按考勤日期',value:false},
            {label:'按上课日期',value:true}
        ]
        $scope.searchParam = { // 搜索条件
            p_bu_id: null,
            p_branch_id: null,
            p_calcBeginDate: null,
            p_calcEndDate: null,
            p_search_key: null,
            p_isCourseTime: false
        };
        $scope.teacherAttendList = []; // 晚辅导教师考勤列表
    };
    // 导出数据
    $scope.exportExcel = function () {
        if(!$scope.searchParam.p_bu_id) {
            $uibMsgbox.error("请先选择团队！");
            return;
        }
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_wfdTeacherAttendanceService.exportExcel($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    // 查询方法
    $scope.query = function () {
        if(!$scope.searchParam.p_bu_id) {
            $uibMsgbox.error("请先选择团队！");
            return;
        }
        $scope.loadStatues = true;
        report_wfdTeacherAttendanceService.query({
            p_branch_id: $scope.searchParam.p_branch_id,
            p_bu_id: $scope.searchParam.p_bu_id,
            p_calcBeginDate: $scope.searchParam.p_calcBeginDate,
            p_calcEndDate: $scope.searchParam.p_calcEndDate,
            p_search_key: $scope.searchParam.p_search_key,
            p_isCourseTime: $scope.searchParam.p_isCourseTime
        }, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.teacherAttendList = resp.data;
            } else {
                alert(resp.message);
            }
        });
    };

    $scope.init();
}