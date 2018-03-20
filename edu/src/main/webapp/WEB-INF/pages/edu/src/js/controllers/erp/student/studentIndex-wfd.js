/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_StudentOrdersWfdController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        'erp_studentOrdersWFDService',
        'erp_studentsService',
        erp_StudentOrdersWfdController]);

function erp_StudentOrdersWfdController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    erp_studentOrdersWFDService,
    erp_studentsService) {
    //学员信息
    $scope.student = {};

    var date = new Date();
    var mm = date.getMonth();
    var yy = date.getFullYear();
    $scope.selectMonthModel = [
        {
            value : -1,
            label : '全部'
        },
        {
            value : 1,
            label : '最近1个月'
        },
        {
            value : 2,
            label : (mm - 1 >= 0 ? yy : yy - 1)
                + '年'
                + ((mm - 1 >= 0 ? mm
                    : mm + 1 + 12 - 1))
                + '月'
        },
        {
            value : 3,
            label : (mm - 2 >= 0 ? yy : yy - 1)
                + '年'
                + ((mm - 2 >= 0 ? mm - 1
                    : mm + 1 + 12 - 2))
                + '月'
        },
        {
            value : 4,
            label : (mm - 3 >= 0 ? yy : yy - 1)
                + '年'
                + ((mm - 3 >= 0 ? mm - 2
                    : mm + 1 + 12 - 3))
                + '月'
        },
        {
            value : 5,
            label : (mm - 4 >= 0 ? yy : yy - 1)
                + '年'
                + ((mm - 4 >= 0 ? mm - 3
                    : mm + 1 + 12 - 4))
                + '月'
        } ];

    $scope.monthOrder = $scope.selectMonthModel[0];


    $scope.isLoading = '';
    $scope.selectMonth = function(month){
        $scope.monthOrder = month;
        queryStudentOrdersWfd();
    }

    function queryStudentOrdersWfd(){
        $scope.isLoading = 'isLoading';
        $log.log($scope.monthOrder.value);
        erp_studentOrdersWFDService.query({
            studentId:$scope.studentId,
            month:$scope.monthOrder.value
        },function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.orderInfoList = resp.data;
            }
        });
    }



    $scope.studentId = $("#rootIndex_studentId").val();

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
        queryStudentOrdersWfd();
        $('title').text('学员|'+ $scope.student.student_name);
    }

    $scope.detailOrderHref = ' ';
    $scope.toOrderDetail = function(orderInfo){
        var isTemporaryOrder = false; //是否临时订单
        if(orderInfo.CHECK_STATUS==1 && orderInfo.VALID_STATUS != 0 ||   //暂存
            orderInfo.VALID_STATUS == 0 && !orderInfo.PAY_STATUS ||       //已删除
            orderInfo.CHECK_STATUS==4 && orderInfo.VALID_STATUS == 1 && !orderInfo.PAY_STATUS ||  //未通过
            orderInfo.CHECK_STATUS==3 && orderInfo.VALID_STATUS == 1 && !orderInfo.PAY_STATUS  ||  //待缴费
            orderInfo.CHECK_STATUS==2 && orderInfo.VALID_STATUS == 1                           //审核中
        ) {
            isTemporaryOrder = true;
        }
        var href = '?studentId=' + orderInfo.STUDENT_ID + '&orderId=' + orderInfo.ORDER_ID;
        href += isTemporaryOrder == true ? '&orderType=temporaryOrder' : '';
        href += '#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
        $scope.detailOrderHref = href;

        $cookieStore.put("currentOrderId",orderInfo.ORDER_ID);
        return true;
    };

    queryStudentInfo();
}