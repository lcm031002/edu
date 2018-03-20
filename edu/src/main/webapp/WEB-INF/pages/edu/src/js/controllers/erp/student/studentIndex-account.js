/**
 * Created by Liyong.zhu on 2016/9/30.
 */
/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_StudentIndexAccountController', [
        '$rootScope',
        '$scope',
        '$log',
        'erp_studentIndexAccountService',
        'erp_studentAccountService',
        'erp_studentPrintService',
        'erp_studentsService',
        erp_StudentIndexAccountController]);

function erp_StudentIndexAccountController(
    $rootScope,
    $scope,
    $log,
    erp_studentIndexAccountService,
    erp_studentAccountService,
    erp_studentPrintService,
    erp_studentsService) {
    //学员信息
    $scope.student = {};
    $scope.dateRange = {
        start_date: '',
        end_date: '',
        defaultRange: 'lastYear'
    }
    $scope.studentId = $("#rootIndex_studentId").val();
    $scope.tabsCtrl = {
        value: -1 
    }
    $scope.changeAccountTypeList = [
        // {value : -1, label : '全部'},
        {value : 1, label : '储值账户'},
        {value : 2, label : '冻结账户'},
        {value : 3, label : '退费账户'}
    ];

    function queryStudentInfo(){
        erp_studentsService.query(
            {
                row_num: 20,
                studentId: $scope.studentId
            },
            function(resp){
                if(!resp.error && resp.data.length){
                    $scope.student = resp.data[0];
                    initial();
                }else{
                    alert(resp.message);
                }
            });
    }
    function initial(){
        queryIndexAccount();
        $('title').text('学员|'+ $scope.student.student_name);

        /*时间轴_start*/
        var callBack = function() {
            $scope.queryAccountList();
        }
        TimeLine($scope, {
            width : 600
        }, callBack);
    }

    queryStudentInfo();

    $scope.Page = {};
    // 变动 的 账户类型
    $scope.Page.change_account_type = 1;
    $scope.Page.rows = 10;
    $scope.Page.total = 0;
    $scope.change_types = '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14';
    $scope.change_types_bak = '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14';
    $scope.changeAccountChangePage = function(change_types){
        if($scope.change_types==change_types){
            $scope.change_types = $scope.change_types_bak;
        }else{
            $scope.change_types = change_types;
        }
        $scope.queryAccountList();
    }

    $scope.queryAccountList = function(account_type){
        if (account_type) {
            $scope.Page.change_account_type = account_type;
        }
        // else {
        //     account_type = $scope.Page.change_account_type;
        // }
        // if(account_type == -1) {
        //     $scope.change_types = $scope.change_types_bak;
        // } else if(account_type == 1) {
        //     $scope.change_types = '0,1,3,4,5,6,7,8,9,10,11';
        // } else if(account_type == 2) {
        //     $scope.change_types = '13,14';
        // } else if(account_type == 3) {
        //     $scope.change_types = '2,12';
        // }
        var param = {
            start_date:$scope.dateRange.start_date,
            end_date:$scope.dateRange.end_date,
            student_id:$scope.studentId,
            account_type:0,
            //change_types:$scope.change_types,
            change_account_type:$scope.Page.change_account_type,
            currentPage:$scope.Page.page,
            pageSize:$scope.Page.rows
        }
        $scope.isLoading = 'isLoading';
        $scope.accountDataList = [];
        erp_studentAccountService.query(param,function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.accountDataList = resp.data;

                $scope.Page.total = resp.total;
                $scope.Page.totalPage = resp.totalPage;
                $scope.Page.currentPage = resp.currentPage;
                $scope.Page.page = resp.currentPage;
                $scope.Page.pageSize = resp.pageSize;

                var _rows = $scope.Page.rows;
                $scope._total = $scope.Page.total;
                if($scope._total == 0){
                    $scope._begin = 0;
                    $scope._end = 0;
                }else{
                    $scope._begin = ($scope.Page.page - 1) * _rows + 1;
                    if($scope._total <= _rows || (($scope._begin - 1) + _rows) >= $scope._total){
                        $scope._end = $scope._total;
                    }else{
                        $scope._end = ($scope.Page.page - 1) * _rows + _rows;
                    }
                }
            }else{
                alert(resp.message);
            }
        })
    }

    // 账户详情
    $scope.studentIndexAccount = {
        STORE_ACCOUNT : 0,
        FROZEN_ACCOUNT : 0,
        REFUND_ACCOUNT : 0
    };
    // 账户余额
    $scope._total = 0;
    $scope._begin = 0;
    $scope._end = 0;
    var param = {};
    $scope.accountDataList = [];

    $scope.print = function(dynamic_id, print_type){
        if(print_type == 0){
            //充值
            erp_studentPrintService.query({dynamicId: dynamic_id,printType:'03'},function(resp){

                if(!resp.error){
                    var printDate = [];
                    printDate.push(resp.data);
                    if(resp.data.rechargeInfo.CITY_ID == 3) {
                        CreatePrintPage04Xiamen(printDate);
                    } else {
                        CreatePrintPage04(printDate);
                    }
                }else{
                    alert(resp.message);
                }

            });
        }
        if(print_type == 6){
            // 理赔
            erp_studentPrintService.query({dynamicId: dynamic_id,printType:'06'},function(resp){
                if(!resp.error){
                    if(resp.data.claimInfo.CITY_ID == 3) {
                        CreatePrintPage06Xiamen(resp.data);
                    } else {
                        CreatePrintPage06(resp.data);
                    }
                }else{
                    alert(resp.message);
                }
            });
        }
    }

    function queryIndexAccount(){
        erp_studentIndexAccountService.query({
            studentId:$scope.student.id
        },function(resp){
            if(!resp.error){
                $scope.studentIndexAccount = resp.data;
            }
        })
    }



    /**
     * 首页
     */
    $scope.begin = function(){
        $scope.Page.page = 1;
        $scope.queryAccountList();
    }

    /**
     * 上一页
     */
    $scope.up = function(){
        if($scope.Page.page <= 1){
            return;
        }
        $scope.Page.page = $scope.Page.page - 1;
        $scope.queryAccountList();
    }

    /**
     * 下一页
     */
    $scope.down = function(){
        if($scope.Page.page >= $scope.Page.totalPage){
            return;
        }
        $scope.Page.page = $scope.Page.page + 1;
        $scope.queryAccountList();
    }

    /**
     * 最后一页
     */
    $scope.end = function(){
        $scope.Page.page = $scope.Page.totalPage;
        $scope.queryAccountList();
    }
}