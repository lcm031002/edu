/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular.module('ework-ui').controller(
    'erp_StudentMineController',
    ['$rootScope', '$scope', '$uibMsgbox',
        'erp_studentMineService', erp_StudentMineController]);

function erp_StudentMineController($rootScope, $scope, $uibMsgbox,
    erp_studentMineService) {
    // 学员信息
    $scope.studentList = [];
    $scope.pageParam = new Object();
    $scope.statusBtnNames = [
        { status: null, name: '全部' },
        { status: 1, name: '正常' },
        { status: 6, name: '结课' },
        { status: 4, name: '沉睡' },
        { status: 7, name: '高中毕业' }
    ]

    $scope.paginationConf = {
        currentPage: 1, // 当前页
        totalItems: 0,
        itemsPerPage: 50,
        onChange: function () {
            $scope.queryMineStudent();
        }
    };
    $scope.searchParam = {
        student_status: null,
        student_name: null
    };
    $scope.queryMineStudentByStatus = function (status) {
        $scope.searchParam.student_status = status;
        $scope.queryMineStudent();
    }
    $scope.queryMineStudent = function () {
        $scope.loadStatues = true;
        erp_studentMineService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            p_student_name: $scope.searchParam.student_name,
            p_student_status: $scope.searchParam.student_status //学员状态
        }, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.studentList.splice(0, $scope.studentList.length);
                $scope.studentList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    $scope.openStudentTraceInfo = function (student) {

    };

    $scope.exportExcel = function () {
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        erp_studentMineService.exportExcel({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            p_student_name: $scope.searchParam.student_name,
            p_student_status: status //学员状态
        }, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.queryMineStudent();

}