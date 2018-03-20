/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_orderUnpayController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_orderManagerService',
    'erp_studentBuOrgsService',
    'PUBORGSelectedService',
    erp_orderUnpayController
]);

function erp_orderUnpayController($rootScope,
                                  $scope,
                                  $log,
                                  $state,
                                  $uibModal,
                                  $uibMsgbox,
                                  erp_orderManagerService,
                                  erp_studentBuOrgsService,
                                  PUBORGSelectedService) {


    /**
     * 获取组织结构中选中的组织
     */
    function querySelectedOrg() {
        PUBORGSelectedService.query({}, function (resp) {
            if (!resp.error) {
                $scope.selectedOrg = resp.data;
                
                if ($scope.selectedOrg) {
                    $scope.searchParam.p_bu_id = $scope.selectedOrg.buId;
                }
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    $scope.init = function () {
        // 状态
        $scope.statusList = [
            { "id": -1, "name": "全部" },
            { "id": 2, "name": "审核中" },
            { "id": 3, "name": "已通过" }
        ];
        $scope.searchParam = { // 搜索条件
            p_bu_id: null,
            p_branch_id: null,
            p_start_date: null,
            p_end_date: null,
            p_check_status:null
        };
        $scope.unpayList = []; // 欠费订单列表

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
            onChange: function () {
                $scope.query();
            }
        };
        querySelectedOrg();
    };
    // 导出数据
    $scope.exportExcel = function () {
        erp_orderManagerService.exportUnpayExcel({
            p_branch_id: $scope.searchParam.p_branch_id,
            p_bu_id: $scope.searchParam.p_bu_id,
            p_start_date: $scope.searchParam.p_start_date,
            p_end_date: $scope.searchParam.p_end_date,
            p_check_status:$scope.searchParam.status
        }, function (resp) {
            if (!resp.error) {
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    $scope.toSubmitOrder = function(unpay){
        $cookieStore.put("temporaryOrderId",unpay.id);
        return true;
    }
    // 查询方法
    $scope.query = function () {

        erp_orderManagerService.unpay({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage,// 要获取的第几页的数据
            p_branch_id: $scope.searchParam.p_branch_id,
            p_bu_id: $scope.searchParam.p_bu_id,
            p_start_date: $scope.searchParam.p_start_date,
            p_end_date: $scope.searchParam.p_end_date,
            p_check_status:$scope.searchParam.status
        }, function (resp) {
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.unpayList = resp.data;
            } else {
                alert(resp.message);
            }
        });
    };
    $scope.toPay = function () {
        $state.go('attendanceGroup', {
            path: '/teachers/attendanceGroup',
            href: 'templates/erp/tearchers/attendanceGroup.html'
        })
    };

    $scope.init();
}