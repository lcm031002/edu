/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_StudentOrderDetailController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        '$state',
        '$uibModal',
        '$uibMsgbox', // 消息提示框服务，其他服务按需引入
        'erp_FinanceOrderService',
        'erp_orderManagerService',
        'erp_InvoiceManagerService',
        'PUBORGSelectedService',
        'erp_orderChangeService',
        erp_StudentOrderDetailController]);

function erp_StudentOrderDetailController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_FinanceOrderService,
    erp_orderManagerService,
    erp_InvoiceManagerService,
    PUBORGSelectedService,
    erp_orderChangeService) {
    $scope.currentOrderId = $("#rootIndex_orderId").val();
    $scope.orderType = $("#rootIndex_orderType").val();

    $scope.studentId = $("#rootIndex_studentId").val();
    $scope.temporaryOrderRemarkDisabled = true;
    $scope.temporaryOrderRemarkCopy = '';
    $scope.isLoading = 'loadOrderInfo';
    $scope.selectedOrg = {};
    $scope.remark="1111";
    function querySelectedOrg(){
        var param = {};
        PUBORGSelectedService.query(param,function(resp){
           if(!resp.error){
               $scope.selectedOrg = resp.data;
           }
        });
    }

    // 编辑备注
    $scope.editTemporaryOrderRemark = function (remark) {
        $scope.temporaryOrderRemarkCopy = _.cloneDeep(remark);
        $scope.temporaryOrderRemarkDisabled = false;
    }

    // 保存备注
    $scope.saveTemporaryOrderRemark = function (remark) {
        var waitingModal = $uibMsgbox.waiting('保存备注中，请稍候...')
        // save Remark
        // After Request
        erp_orderManagerService.updateOrderInfo({
            id:  $scope.currentOrderId,
            remark: remark
        }, function (resp) {
            waitingModal.close()
            if (!resp.error) {
                $uibMsgbox.alert('保存成功!')
                $scope.temporaryOrderRemarkCopy = '';
                $scope.temporaryOrderRemarkDisabled = true;
            } else {
                $uibMsgbox.error(resp.message)
            }
        })
    }

    // 取消备注修改
    $scope.cancelTemporaryOrderRemark = function  () {
        $scope.order.temporaryOrder.remark = _.cloneDeep($scope.temporaryOrderRemarkCopy);
        $scope.temporaryOrderRemarkCopy = '';
        $scope.temporaryOrderRemarkDisabled = true;
    }
    // 处理【订单作废】按钮点击事件
    $scope.handleCancelOrder = function () {
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/student-order-detail-del.modal.html',
            controller: 'erp_studentIndexOrderDetailDelModalController'
        }).result.then(function (resp) {
        	console.info(resp);
        	 $uibMsgbox.confirm('确定作废当前订单？', function (result) {
                 if(result != 'yes') {
                     return;
                 }
                 $scope.del(resp);
             });
        	
        }, function () { 
        	
        });
    };
    
    // 处理【订单锁定】按钮点击事件
    $scope.handleLockOrder = function () {
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/student-order-detail-lock.modal.html',
            controller: 'erp_studentIndexOrderDetailLockModalController',
            scope: $scope
        }).result.then(function (resp) {
        	$scope.lock(resp);
        }, function () { 
        	
        });
    };
    
    // 处理【订单解锁】按钮点击事件
    $scope.handleUnLockOrder = function () {
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/student-order-detail-unlock.modal.html',
            controller: 'erp_studentIndexOrderDetailUnlockModalController',
            scope: $scope
        }).result.then(function (resp) {
        	$scope.unLock(resp);
        }, function () { 
        	
        });
    };
    
    // 锁定
    $scope.lock= function (resp) {
    	erp_FinanceOrderService.lock({
    		remark:resp.remark ,
    		orderId : $scope.currentOrderId,
    		status:1
    	}, function (resp) {
    		if (!resp.error) {
    			$state.reload();
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    };
    
    // 解锁
    $scope.unLock= function (resp) {
    	erp_FinanceOrderService.lock({
    		remark:resp.remark ,
    		orderId : $scope.currentOrderId,
    		status:3
    	}, function (resp) {
    		if (!resp.error) {
    			$state.reload();
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    };
    
    // 删除
    $scope.del= function (resp) {
    	erp_FinanceOrderService.remove({
    		remark:resp.remark ,
    		orderId : $scope.currentOrderId
    	}, function (resp) {
    		if (!resp.error) {
    			$state.reload();
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    };
    
    function initialOrderInfo(){
        erp_orderManagerService.query({
            order_id:$scope.currentOrderId,
            orderType: $scope.orderType
        },function(resp){
            if(!resp.error){
                if($scope.orderType && $scope.orderType == 'temporaryOrder' && resp.data && resp.data.order_status != 1) { //临时订单

                    $scope.order = {};
                    $scope.order.temporaryOrder = resp.data;
                    $scope.order.studentInfo = $scope.order.temporaryOrder.studentInfo;
                    $scope.order.order_no = $scope.order.temporaryOrder.encoding; //报班单号
                    $scope.order.order_status = $scope.order.temporaryOrder.order_status;
                    $scope.order.actural_amount = $scope.order.temporaryOrder.actual_price; //订单现价
                    $scope.order.fee_amount = $scope.order.temporaryOrder.sum_price; //订单原价
                    $scope.order.orderDetails = $scope.order.temporaryOrder.details; //订单明细
                    _.forEach($scope.order.orderDetails, function (detail, index) {
                        detail.course = detail.tCourseInfo;
                        detail.course_surplus_count = 0; //临时订单，不显示置空课时
                    });
                } else { //正式订单
                    $scope.order = resp.data;
                    console.log($scope.order)
                }
                
                if( $scope.order.studentInfo && $scope.order.studentInfo.phone ){
                    $scope.order.studentInfo.phone = parseInt($scope.order.studentInfo.phone);
                }
                if($scope.order&&$scope.order.temporaryOrder&&$scope.order.temporaryOrder.create_time){
                    var date = new Date();
                    date.setTime($scope.order.temporaryOrder.create_time);
                    $scope.order.temporaryOrder.create_time = Format('yyyy-MM-dd hh:mm',date);
                }

                if($scope.order&&$scope.order.fin_confirm_date){
                    var date = new Date();
                    date.setTime($scope.order.fin_confirm_date);
                    $scope.order.fin_confirm_date = Format('yyyy-MM-dd hh:mm',date);
                }

                if($scope.order.orderDetails){
                    $.each($scope.order.orderDetails,function(j,detail){
                        detail.kaoqinCount = 0;
                        detail.guaqiCount = 0;
                        detail.tuifeiCount = 0;
                        detail.zhuanbanCount = 0;
                        detail.dongjieCount = 0;
                        detail.tuifeiAuditCount = 0;
                        //考勤
                        if(detail && detail.tAttendanceList && detail.tAttendanceList.length){
                            $.each(detail.tAttendanceList,function(i,att){
                                if(att.attend_date){
                                    var date = new Date();
                                    date.setTime(att.attend_date);
                                    att.attend_date = Format('yyyy-MM-dd', date);
                                }
                                if(att.course_time){
                                    var date = new Date();
                                    date.setTime(att.course_time);
                                    att.course_time = Format('yyyy-MM-dd',date);
                                }
                                if(att.attend_type&&att.attend_type != 10&&att.attend_type != 20&&att.attend_type != 30){
                                    if (att.attend_type == 11 ) { //培英班挂起
                                    	detail.guaqiCount++;
                                    } else if(att.attend_type == 12 || att.attend_type == 31  ){//培英班晚辅导考勤
                                        detail.kaoqinCount++;
                                    } else if(att.attend_type ==21 || att.attend_type ==22) {//一对一考勤
                                            detail.kaoqinCount += att.course_times;
                                    }
                                }
                            });
                        }
                        //退费
                        if(detail && detail.orderCourseChange && detail.orderCourseChange.length){
                            $.each(detail.orderCourseChange,function(i,ocg){
                                if(ocg.change_type==1&&ocg.change_status==5){
                                    detail.tuifeiCount=detail.tuifeiCount+parseInt(ocg.course_times);
                                }
                            });
                        }
                        //转班
                        if(detail && detail.orderCourseChange && detail.orderCourseChange.length){
                            $.each(detail.orderCourseChange,function(i,ocg){
                                if(ocg.change_type==2&&ocg.change_status==5&&ocg.order_course_id == detail.id && ocg.transfer_flag==0){
                                    detail.zhuanbanCount=detail.zhuanbanCount+parseInt(ocg.course_times);
                                }
                            });
                        }
                        //冻结
                        if(detail && detail.orderCourseChange && detail.orderCourseChange.length){
                            $.each(detail.orderCourseChange,function(i,ocg){
                                if(ocg.change_type==5&&ocg.change_status==5){
                                    detail.dongjieCount=detail.dongjieCount+parseInt(ocg.course_times);
                                }
                            });
                        }

                        $.each($scope.order.temporaryOrder.details,function(hh,tDetail){
                            if(tDetail.id == detail.id){
                                detail.temporaryOrderDetail = tDetail;
                            }
                        });
                        // 退费审批
                        if(detail && $scope.order.premiumAuditList && $scope.order.premiumAuditList.length){
                            detail.premiumAuditList = [];
                            $.each($scope.order.premiumAuditList,function(i,premiumAudit){
                                if(detail.id == premiumAudit.ORDER_COURSE_ID){
                                    detail.tuifeiAuditCount += parseInt(premiumAudit.COURSE_TIMES);
                                    detail.premiumAuditList.push(premiumAudit);
                                }
                            });
                        }
                    });
                }

                $scope.isLoading = '';
            }else{
                alert(resp.message);
                $scope.isLoading = 'loadOrderInfoFailed';
                $scope.isLoadingFailedInfo = resp.message;
            }
        });
    }

    initialOrderInfo();
    $scope.showDetailTimesPanel = '';
    $('#detailTimesPanel').modal('hide');
    $scope.showDetailTimes = function(detail,type){
        if($scope.selectedOrg.productLine == 2) {
            return
        }
        if(type == 'shenyu'){
            $scope.showDetailTimesPanel = 'showDetailTimesPanel.shenyu';
            $scope.showDetailTimesPanelLoading = 'loading';
            $scope.showDetailTimesPanelSelectedDetail = detail;
            queryOrderCourseSurplusCount(detail);
        } else if(type == 'kaoqin'){
            $scope.showDetailTimesPanel = 'showDetailTimesPanel.kaoqin';
            $scope.showDetailTimesPanelSelectedDetail = detail;
        } else if(type == 'guaqi'){
            $scope.showDetailTimesPanel = 'showDetailTimesPanel.guaqi';
            $scope.showDetailTimesPanelSelectedDetail = detail;
        }  else if(type == 'zhuanban'){
            $scope.showDetailTimesPanel = 'showDetailTimesPanel.zhuanban';
            $scope.showDetailTimesPanelLoading = 'loading';
            $scope.showDetailTimesPanelSelectedDetail = detail;
            queryOrderChangeTimesInfo(detail);
        } else if(type == 'tuifei'){
            $scope.showDetailTimesPanel = 'showDetailTimesPanel.tuifei';
            $scope.showDetailTimesPanelLoading = 'loading';
            $scope.showDetailTimesPanelSelectedDetail = detail;
            queryOrderChangeTimesInfo(detail);
        } else if(type == 'dongjie'){
            $scope.showDetailTimesPanel = 'showDetailTimesPanel.dongjie';
            $scope.showDetailTimesPanelSelectedDetail = detail;
            queryOrderChangeTimesInfo(detail);
        }  else if(type == 'tuifeiAudit'){
            $scope.showDetailTimesPanel = 'showDetailTimesPanel.tuifeiAudit';
            $scope.showDetailTimesPanelSelectedDetail = detail;
        }
        $('#detailTimesPanel').modal('show');
    }

    function queryOrderCourseSurplusCount(detail){
        var param = {};
        param.orderDetailId = detail.id;
        erp_orderManagerService.orderCourseSurplusCount(param,function(resp){
            $scope.showDetailTimesPanelLoading = '';
            if(!resp.error){
                $scope.showDetailTimesPanelSelectedDetail.orderCourseSurplusCount = resp.data;
            }else{
                alert(resp.message);
            }
        });
    }

    function queryOrderChangeTimesInfo(detail){
        var param = {};
        param.orderDetailId = detail.id;
        erp_orderManagerService.queryOrderChangeCourseTimes(param,function(resp){
            $scope.showDetailTimesPanelLoading = '';
            if(!resp.error){
                $scope.showDetailTimesPanelSelectedDetail.orderChangeCourseTimes = resp.data;
                if($scope.showDetailTimesPanelSelectedDetail.orderChangeCourseTimes){
                    $.each($scope.showDetailTimesPanelSelectedDetail.orderChangeCourseTimes,function(i,ch){
                        if(ch.courseDate){
                            var date = new Date();
                            date.setTime(ch.courseDate);
                            ch.courseDate = Format('yyyy-MM-dd',date);
                        }
                        if(ch.finConfirmTime){
                            var date = new Date();
                            date.setTime(ch.finConfirmTime);
                            ch.finConfirmTime = Format('yyyy-MM-dd hh:mm:ss',date);
                        }
                        if(ch.createTime){
                            var date = new Date();
                            date.setTime(ch.createTime);
                            ch.createTime = Format('yyyy-MM-dd hh:mm:ss',date);
                        }
                    });
                }
            }else{
                alert(resp.message);
            }
        });
    }

    $scope.closeShowDetailTimes = function(){
        $scope.showDetailTimesPanel = '';
        $('#detailTimesPanel').modal('hide');
    }

    $scope.isQueryInvoiceData = '';
    function queryInvoiceInfo(){
        var param = {};
        param.orderId = $scope.currentOrderId;
        $scope.isQueryInvoiceData = 'isLoading';
        erp_InvoiceManagerService.queryByOrder(param,function(resp){
            $scope.isQueryInvoiceData = '';
           if(!resp.error){
               $scope.invoiceData =  resp.data;
               if($scope.invoiceData){
                   $.each($scope.invoiceData,function(i,invoice){
                       if(invoice && invoice.invoiceDate){
                           var date = new Date();
                           date.setTime(invoice.invoiceDate);
                           invoice.invoiceDate = Format('yyyy-MM-dd',date);
                       }
                   });

               }
           }
        });
    }
    $scope.orderTuifeiInfo = [];
    $scope.orderZhuanbanInfo = [];

    function queryOrderChange(){
        var param = {};
        param.orderId = $scope.currentOrderId;
        $scope.orderTuifeiInfo = [];
        erp_orderManagerService.orderChangeInfo(param,function(resp){
            if(!resp.error){
                var orderChangeInfo =  resp.data;
                if(orderChangeInfo){
                    $.each(orderChangeInfo,function(i,change){
                        if((change.change_type == 1|| change.change_type == 4) && change.change_status == 5){
                            $scope.orderTuifeiInfo.push(change);
                        }

                        if(change.change_type == 2){
                            $scope.orderZhuanbanInfo.push(change);
                        }
                    });

                }
            }
        });
    }

    $scope.printPremium = function(premium){
        PrintPage02($scope.student,premium);
    }

    function PrintPage02(student,premium){
        //02冻结
        var strHtml = '<style type="text/css">';
        strHtml += 'table { width:660px; font:12px Arial;font-weight: 400; margin-left:10px;}';
        strHtml += 'tr.Line td{ border-top:1px dashed #000;}';
        strHtml += 'tr td{ height:15px; line-height:15px;}';
        strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
        strHtml += '</style>';
        strHtml += '<table width="660px" style="margin:103px 0 0 95px;" height="600px" border="0" cellspacing="0" cellpadding="0">';
        strHtml += '<tr height="300px"><td ></td></tr>';
        strHtml += '<tr height="330px"><td>';
        strHtml += '<table border="0" width="660px" height="48" align="center">';
        strHtml += '<tr align="left" style="font-weight:900;">';
        strHtml += '<td colspan="5">业务校区:' + premium.branch_name + '	经办人:' + premium.createUserName + '</td>';
        strHtml += '</tr>';
        strHtml += '<tr align="center" style="font-weight:900;">';
        strHtml += '<td width="150">学员编号</td>';
        strHtml += '<td width="66">学员姓名</td>';
        strHtml += '<td width="155">单据编号</td>';
        strHtml += '<td width="68">业务类型</td>';
        strHtml += '<td width="76" align="center">业务日期</td>';
        strHtml += '</tr>';
        strHtml += '<tr align="center">';
        strHtml += '<td>'+premium.student_encoding+'</td>';
        strHtml += '<td>'+premium.student_name+'</td>';
        strHtml += '<td>'+premium.order_no+'</td>';
        strHtml += '<td>退费</td>';
        strHtml += '<td>'+premium.apply_time_string+'</td>';
        strHtml += '</tr>';
        strHtml += '<tr align="left">';
        strHtml += '<td colspan="6">冻结信息：'+(premium.remark?premium.remark:'-')+'</td>';
        strHtml += '</tr>';
        strHtml += '</table>';
        //
        strHtml += '<table border="0" width="660px" height="48">';
        strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
        strHtml += '<td>编号</td>';
        strHtml += '<td>课程商品名称</td>';
        strHtml += '<td>冻结课时</td>';
        strHtml += '<td>冻结金额</td>';
        strHtml += '<td>冻结类型</td>';
        if(premium.fee_deduction_amount){
            strHtml += '<td>冻结补扣</td>';
        }
        strHtml += '</tr>';
        strHtml += '<tr align="center">';
        strHtml += '<td>'+premium.courseNo+'</td>';
        strHtml += '<td>'+premium.courseName+'</td>';
        strHtml += '<td>'+premium.courseTimes+'</td>';
        strHtml += '<td>'+premium.fee_amount+'</td>';
        strHtml += '<td>退费冻结</td>';
        if(premium.fee_deduction_amount){
            strHtml += '<td>'+premium.fee_deduction_amount+'</td>';
        }
        strHtml += '</tr>';
        strHtml += '</table>';

        KlxxPrint(strHtml);
    }

    $scope.printOrder = function(order){
        erp_orderManagerService.query({
            order_id:order.id,
            orderType:'temporaryOrder'
        },function(resp){
            $scope.openPanel = '';

            if(!resp.error){
                $scope.temporaryOrder = resp.data;
                if (!$scope.temporaryOrder.details) {
                    $scope.temporaryOrder = $scope.temporaryOrder.temporaryOrder;
                }
                if($scope.temporaryOrder.business_type == 2) {//1对1
                    CreatePrintPageGxhYdyBb($scope.temporaryOrder);
                } else if($scope.temporaryOrder.business_type == 1) {//班级课
                    if ($scope.temporaryOrder.city_id == 3) {
                        CreatePrintPageXiamen($scope.temporaryOrder);
                    } else {
                        CreatePrintPage($scope.temporaryOrder);
                    }
                } else {
                    CreatePrintPage($scope.temporaryOrder);
                }
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }
    
    $scope.openModal = function() {
    	$uibModal.open({
			resolve : {
				orderId : function() {
					return $scope.order.temporaryOrder ? $scope.order.temporaryOrder.id : $scope.order.id;
				}
			},
			templateUrl : 'templates/block/modal/order_workflow.modal.html',
			controller : 'orderWorkflowModalController'
		}).result.then(function() {
		}, function() {
		});
    }

    $scope.checkOrderChange = function(order, orderDetailId, changeType) {
        erp_orderChangeService.changeCheck({
            orderCourseId : orderDetailId
        }, function(resp) {
            if(!resp.error){
                var url = "?studentId=" + order.student_id + "&orderDetailId=" + orderDetailId;
                if (changeType == 'transfer') {
                    url += "#/orders/orderChangeTransfer";
                } else if (changeType == 'frozen') {
                    url += "&bizType=" + order.business_type + "#/orders/classesFrozen";
                } else {
                    url +=  "&bizType=" + order.business_type + "#/orders/classesRefund";
                }
                var newTab = window.open();
                newTab.location.href = url;
            }else{
                var message = resp.message;
                message = message.replace(new RegExp('退费', 'g'), changeType == 'transfer' ? "转班" : (changeType == 'frozen' ? "冻结" : "退费"));
                $uibMsgbox.error(message);
            }
        });
    }

    queryInvoiceInfo();
    querySelectedOrg();
    queryOrderChange();
}