/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_StudentOrdersController', [
        '$rootScope',
        '$scope',
        '$log',
        '$uibMsgbox',
        '$cookieStore',
        'erp_studentOrdersService',
        'erp_orderManagerService',
        'erp_studentsService',
        'erp_InvoiceManagerService',
        erp_StudentOrdersController]);

function erp_StudentOrdersController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $cookieStore,
    erp_studentOrdersService,
    erp_orderManagerService,
    erp_studentsService,
    erp_InvoiceManagerService) {
    //学员信息
    $scope.student = {};
    $scope.searchParam = {
        selectedMonth: '-1'
    }
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
    
    // add by lincm 20170309 发票抬头选择框下拉值
    $scope.headerList = [{
    	"key" : "1", "value" : "个人"
    }, {
    	"key" : "2", "value" : "公司"
    }];


    $scope.isLoading = '';
    $scope.selectMonth = function(){
        queryStudentOrders();
    }

    function queryStudentOrders(){
        $scope.isLoading = 'isLoading';
        erp_studentOrdersService.query({
            studentId:$scope.studentId,
            month:$scope.searchParam.selectedMonth
        },function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.orderInfoList = resp.data;
            }
        });
    }

    /**
     * 显示
     *
     * @param targetId
     */
    $scope.toShow = function(targetId) {
        if ($("#" + targetId).hasClass("showed")) {
            $("#" + targetId).hide();
            $("#" + targetId).removeClass("showed");
        } else {
            $("#" + targetId).show();
            $("#" + targetId).addClass("showed");
        }
    };
    
    /**
     * 隐藏
     *
     * @param targetId
     */
    $scope.toHide = function(targetId) {
        $("#" + targetId).hide();
        $("#" + targetId).removeClass("showed");
    };

    $scope.toSubmitOrder = function(order){
        $cookieStore.put("temporaryOrderId",order.ID);
        return true;
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
        var href = '?studentId=' + orderInfo.STUDENT_ID + '&orderId=' + orderInfo.ID;
        href += isTemporaryOrder == true ? '&orderType=temporaryOrder' : '';
        href += '#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
        $scope.detailOrderHref = href;

        $cookieStore.put("currentOrderId",orderInfo.ID);
        return true;
    };
    $scope.openPanel = '';
    $scope.deleteOrder = function(order){
        $scope.openPanel = 'deleteOrder';
        console.log($scope.$parent)
        var param = {};
        param.orderId = order.ID;
        erp_orderManagerService.delete(param,function(resp){
            $scope.openPanel = '';
            if(!resp.error){
            	$uibMsgbox.success("已成功删除！");
                queryStudentOrders();
            }else{
            	$uibMsgbox.error(resp.message);
            }
        })
    };
    $scope.printPage = '';
    $scope.printOrder = function(order){
        $scope.printPage = 'beginPrint';

        erp_orderManagerService.query({
            order_id:order.ID,
            orderType:'temporaryOrder'
        },function(resp){
            $scope.openPanel = '';
            
            if(!resp.error){
                $scope.temporaryOrder = resp.data;
                if (!$scope.temporaryOrder.details) {
                    $scope.temporaryOrder = $scope.temporaryOrder.temporaryOrder;
                }
                var printDataStirng = JSON.stringify(resp.data);
                if($scope.temporaryOrder.business_type == 2) {//1对1
                    var useragent = navigator.userAgent;
                    if(useragent.indexOf('Android') > -1 || useragent.indexOf('Adr') > -1){
                        window.localStorage.setItem('baobanData', printDataStirng)
                        if(location.href.indexOf('klxuexi.org')>0){
                            window.open("/printhtml/print_classlist.html");
                        }else{
                        window.open("/edu/printhtml/print_classlist.html");
                        }
                    }else{
                        CreatePrintPageGxhYdyBb($scope.temporaryOrder);
                    }
                } else if($scope.temporaryOrder.business_type == 1) {//班级课
                    var useragent = navigator.userAgent;
                    if(useragent.indexOf('Android') > -1 || useragent.indexOf('Adr') > -1){
                        window.localStorage.setItem('BJKData', printDataStirng)
                        if(location.href.indexOf('klxuexi.org')>0){
                            window.open("/printhtml/print_BJKclasslist.html");
                        }else{
                        window.open("/edu/printhtml/print_BJKclasslist.html");
                        }
                    }else{
                    if ($scope.temporaryOrder.city_id == 3) {
                        CreatePrintPageXiamen($scope.temporaryOrder);
                    } else {
                        CreatePrintPage($scope.temporaryOrder);
                    }
                } }else {
                    CreatePrintPage($scope.temporaryOrder);
                }
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.closePrintWindow = function(){
        $scope.printPage = '';
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
                	$uibMsgbox.error(resp.message);
                }
            });
    }
    function initial(){
        queryStudentOrders();

        $('title').text('学员|'+ $scope.student.student_name);
    }
    
    // add by lincm 20170309 发票申请处理
    $scope.invoice = {
    	orderId : '',
    	branchId : '',
    	studentId : '',
    	orderMoney : 0,
    	heading : '1',
    	money : 0,
    	requiredMoney : 0,
    	companyName : '',
    	taxNum : '',
    	invoiceExplain : ''
    };
    
    $scope.invoiceApply = function(orderId) {
    	erp_InvoiceManagerService.queryForApply({
    		"orderId" : orderId
    	}, function(resp) {
            if(!resp.error) {
            	$scope.invoice = resp.data;
            	$scope.invoice.heading = "1";
            	$('#erpStudentMgrInvoiceApplyPanel').modal('show');
            } else{
            	$uibMsgbox.error(resp.message);
            }
    	});
    }
    
    $scope.setTaxNum = function() {
    	if ($scope.invoice.heading == "1") {
    		$scope.invoice.taxNum = $scope.invoice.companyName;
    	}    
    }
    
    $scope.clearTaxNumAndCompName = function() {
		$scope.invoice.taxNum = null;
		$scope.invoice.companyName = null;
    }
    
    $scope.handleInvoiceApply = function() {
        var _modalInstance = $uibMsgbox.waiting('发票申请中，请稍候...');
    	erp_InvoiceManagerService.post($scope.invoice, function(resp) {
            _modalInstance.close();
            if(!resp.error) {
            	$uibMsgbox.success("发票申请成功");
            	$('#erpStudentMgrInvoiceApplyPanel').modal('hide');
            } else{
            	$uibMsgbox.error(resp.message);
            }
    	});
    }
    
    $scope.handleInvoiceApplyCancel = function() {
    	$('#erpStudentMgrInvoiceApplyPanel').modal('hide');
    }

    queryStudentInfo();
}