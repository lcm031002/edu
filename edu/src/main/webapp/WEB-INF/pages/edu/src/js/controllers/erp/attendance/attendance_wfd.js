/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_attendanceWfdController', [
        '$scope',
        '$log',
        '$uibMsgbox',
        '$state',
        'erp_attendanceCourseTimesService',
        erp_attendanceWfdController]);

function erp_attendanceWfdController(
    $scope,
    $log,
    $uibMsgbox,
    $state,
    erp_attendanceCourseTimesService
    ) {
    // 搜索字段
    $scope.searchParam = {
        start_date: getCurrentDate(),
        end_date: getCurrentDate(),
        range:"today"
    };
    // 晚辅导考勤统计情况列表
	$scope.wfdList = [];
    
    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，默认]
     * @param  {Function} perPageOptions [description]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        onChange: function() {
        	$scope.query();
        }
    }

    $scope.paginationBars = [];
    
    // 考勤按钮触发
    $scope.handleAttendance = function (wfd) {
    	window.open("?attendance_date=" + wfd.DATE_TIME + "#/orders/attendanceMgr/attendanceMgrWFDDetails");
    }

    // 查询晚辅导考勤统计情况
    $scope.query = function () {
        $scope.loadStatues = true;
		erp_attendanceCourseTimesService.branchinfo({
    		pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            p_start_date: $scope.searchParam.start_date,
            p_end_date: $scope.searchParam.end_date
        },
        function(resp){
            $scope.loadStatues = false;
            if(!resp.error) {
           	 	$scope.wfdList = resp.data;
           	 	$scope.paginationConf.totalItems = resp.total || 0;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    }
    
    $scope.query();
}