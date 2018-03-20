/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_withDrawalController', [
        '$scope',
        '$log',
        '$uibMsgbox', // 消息提示框服务，其他服务按需引入
        '$uibModalInstance',
        'erp_studentAccountService',
        'erp_studentsService',
        'erp_refundService',
        'erp_studentAccountQueryService',
        'erp_studentAccountQueryService',
        'erp_printService',
        erp_withDrawalController]);

function erp_withDrawalController(
    $scope,
    $log,
    $uibMsgbox,
    $uibModalInstance,
    erp_studentAccountService,
    erp_studentsService,
    erp_refundService,
    erp_studentAccountQueryService,
    erp_printService,
    erp_withDrawalController) {
    $scope.accountType_list = [
        { id : 1,name:'储值账户'},
        { id : 2,name:'冻结账户'},
        { id : 3,name:'退费账户'}
    ];
    
    $scope.payMode_list = [
        { id : 1,name:'现金'},
        { id : 3,name:'银行转账'}
    ];
    
    $scope.withDrawalInfo  = {
    		accountType:1,
    		pay_mode:1,
    		fee_amount:0,
    		money:0
    };
    
    $scope.queryParam  = {
            page:1
    };
    
    $scope.withDrawalInfo.id= $scope.$resolve.studentId;
    
    $scope.withDrawalInfo.encoding = $scope.$resolve.changeNo;
    
    $scope.isRefundWithDraw = "false"; // 是否退费单据退款标识
    if ($scope.withDrawalInfo.encoding) {
    	$scope.isRefundWithDraw = "true";
    }
    
    // 打印动户变动情况
//    $scope.printAccountDynamic = function() {
//    	// 取款打印内容
//    	erp_printService.printAccountDynamic({
//    		"dynamicId" : $scope.dynamicId,
//    		"printType" : "03"
//    	}, 'erpStudentAccountPrintPanel');
//    }
    
    function queryStudent(){
        var param = {};
        if($scope.queryParam.search_info){
            param.searchInfo = $scope.queryParam.search_info;
        }
        $scope.queryParam.studentList = [];
        erp_studentsService.query(param,function(resp){
            if(!resp.error){
                $scope.queryParam.studentList = resp.data;
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    }
    

    $scope.payModeFunc = function (){
    	if($scope.withDrawalInfo.pay_mode==3){
    		$scope.isShow = true;  
    	}else{
    		$scope.isShow = false;  
    	}
    }
    
    
    $scope.selectStudent = function(student){
        $scope.student_id = student.id;
        $scope.withDrawalInfo.student_encoding = student.encoding;
        $scope.withDrawalInfo.student_name = student.student_name;
        $scope.withDrawalInfo.student_id=student.id;
        $scope.withDrawalInfo.bu_name=student.bu_name;
        queryStudentAccount();
    };
    
    function queryRefund(){
        var param = {};
        if($scope.withDrawalInfo.encoding){
            param.refundEncoding = $scope.withDrawalInfo.encoding;
        }
        $scope.refundInfo =[];
        erp_refundService.query(param,function(resp){
            if(!resp.error){
                $scope.refundInfo = resp.data;
                if( $scope.refundInfo.length ==1){
	                $scope.withDrawalInfo=$scope.refundInfo[0];
	                $scope.withDrawalInfo.accountType=3; //退费账户
	                $scope.withDrawalInfo.pay_mode=1;
	                if(!$scope.withDrawalInfo.fee_amount){
	                	$scope.withDrawalInfo.fee_amount=0;
	                }
	                queryStudentAccount();
            	}
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    }
    
    function queryStudentAccount(){
        var param = { studentId:$scope.withDrawalInfo.student_id,bu_id:$scope.withDrawalInfo.bu_id,accountType:0};
        $scope.studentAccount ={};
        if(!$scope.withDrawalInfo.student_id){
        	$uibMsgbox.warn("请先选择学生！");
        	return;
        }
        erp_studentAccountQueryService.query(param,function(resp){
            if(!resp.error){
                $scope.studentAccount = resp.data;
                if($scope.withDrawalInfo){
	                if($scope.withDrawalInfo.accountType==3){ //退费账户
	                	$scope.studentAccount.money=($scope.studentAccount.REFUND_ACCOUNT==null?0:$scope.studentAccount.REFUND_ACCOUNT);
	                } else if($scope.withDrawalInfo.accountType==2){ //冻结账户
                        $scope.studentAccount.money=($scope.studentAccount.FROZEN_ACCOUNT==null?0:$scope.studentAccount.FROZEN_ACCOUNT);
                    } else { // 储值账户
	                	$scope.studentAccount.money=$scope.studentAccount.FEE_AMOUNT;
	                }
                }
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.changeSearchInfo = function(){
        queryStudent();
    };
    
    
    $scope.queryRefundInfo = function(){
    	queryRefund();
    };
    
    $scope.changeAccountType= function(){
        // 选择 冻结账户 时，要求 退费单据不可填写 ，故清空
        if($scope.withDrawalInfo && $scope.withDrawalInfo.accountType==2){
            $scope.withDrawalInfo.encoding = null;
        }
    	queryStudentAccount();
    };
    
    // 账户取款按钮提交
    $scope.handleModalConfirm= function () {
    	$scope.withDrawalInfo.accountOperateType='withDrawal';
    	$scope.withDrawalInfo.money=$scope.withDrawalInfo.fee_amount;
        var _modalInstance = $uibMsgbox.waiting('账户取款操作中，请稍候...');
    	erp_studentAccountService.post($scope.withDrawalInfo, function (resp) {
            _modalInstance.close();
            if (!resp.error) {
                $uibModalInstance.close(resp.data);
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    };
    
    if($scope.withDrawalInfo.id){
    	queryStudent();
    }
    if($scope.withDrawalInfo.encoding){
    	queryRefund();
    }
    
    queryStudent();
}