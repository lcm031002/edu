/**
 * Created by Liyong.zhu on 2017/1/15.
 */

"use strict";
angular
    .module('ework-ui')
    .controller('erp_UserOrdersController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        'erp_orderManagerUserOrdersService',
        erp_UserOrdersController]);

function erp_UserOrdersController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    erp_orderManagerUserOrdersService) {

    $scope.isLoading = '';
    $scope.app_info = '';

    $scope.stateList = [
        {
            "name":"全部",
            "value":"-1"
        },{
            "name":"暂存",
            "value":"暂存"
        },{
            "name":"审核中",
            "value":"审核中"
        },{
            "name":"已通过",
            "value":"已通过"
        },{
            "name":"异常订单",
            "value":"异常订单"
        },{
            "name":"欠费",
            "value":"欠费"
        },{
            "name":"审核未通过",
            "value":"审核未通过"
        }
    ];
    $scope.selectedState = $scope.stateList[5];
    $scope.orderPage = {
        currentPage:1,
        pageSize:5,
        totalCount:0
    };
    $scope.isLoading='';
    function queryOrderList(){
        var param = {};
        $scope.isLoading='isLoading';
        param.pageSize = $scope.orderPage.pageSize;
        param.app_info = $scope.app_info;
        param.auditStatus = $scope.selectedState.value;
        param.startDate = $("#cdt_start_date_03").val();
        param.endDate = $("#cdt_end_date_03").val();
        erp_orderManagerUserOrdersService.query(param,function(resp){
            $scope.isLoading='';
            if(!resp.error){
                $scope.orderPage = resp.data;
            }
        });
    }

    queryOrderList();

    $scope.pageQuery =function(currentPage){
        $scope.orderPage.currentPage = currentPage;
        if($scope.orderPage.currentPage<1){
            $scope.orderPage.currentPage = 1;
        }
        if($scope.orderPage.currentPage>$scope.orderPage.totalPage){
            $scope.orderPage.currentPage=$scope.orderPage.totalPage;
        }
        queryOrderList();
    };

    $scope.queryMore = function(){
        $scope.orderPage.pageSize = $scope.orderPage.pageSize + 5;
        $scope.pageQuery(1);
    };

    $scope.queryTask = function(){
        $scope.orderPage.resultList = [];
        $scope.orderPage.pageSize = 5;
        queryOrderList();
    }

    $scope.toCall = function(order){
        if(order.STUID){
            return true;
        }else{
            alert("异常订单，该订单未查到对应的学员，请联系管理员！");
            return false;
        }
    }

}