/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_delayRecordController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    '$state',
    '$uibModal',
    'erp_employeeService',
    'erp_delayCourseService',
    erp_delayRecordController
    ]);

function erp_delayRecordController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $state,
    $uibModal,
    erp_employeeService,
    erp_delayCourseService
  ) {

    $scope.searchParam = {};

    $scope.delayRecordList = [];
    $scope.employee = {};


    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
     * @param  {Function} onChange      [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function(){
            $scope.query();
        }
    };


    $scope.queryDelayReocedCourseDetail = function (id) {
    	window.location.href="?recordId="+id+"#/orders/delayrecord/detail";
    };


    // 查询方法
    $scope.query = function () {
        $scope.loadStatues = true;
    	erp_delayCourseService.listDelayRecord({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage,// 要获取的第几页的数据
			createUser:$scope.employee.employee_id,
            createDate:$scope.searchParam.createDate,
			delayCourseDate:$scope.searchParam.delayCourseDate
        }, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.delayRecordList=resp.data;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    };


    // 选中关联员工输入框，清空输入框内容
    $scope.onEmployeeNameFocus = function() {

    };

    // 鼠标离开关联员工控件处理事件
    $scope.onEmployeeNameBlur = function() {
        if(!$scope.employee.employee_id) {
            $scope.employee.employee_name = '';
        }
    };


    // 关联员工输入框，名称变化则重新查询员工信息
    $scope.onEmployeeNameChange = function() {
        $scope.isDown = 'loading';
        $scope.searchResult = [];
        $scope.employee.employee_id = null;
        erp_employeeService.query({
                row_num: 10,
                currentPage: 1,
                pageSize: 10,
                employee_name: $scope.employee.employee_name
            },
            function(resp) {
                $scope.isDown = '';
                if (!resp.error) {
                    $scope.searchResult = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    }

    // 从转入学员控件的查询结果中选择一条数据触发该事件
    $scope.selectEmployee = function(employee) {
        debugger;
        $scope.employee.employee_id = employee.id;
        $scope.employee.teacher_name
            = $scope.employee.nickname
            = $scope.employee.employee_name
            = employee.employee_name;

        $scope.searchResult = [];
    };
}
