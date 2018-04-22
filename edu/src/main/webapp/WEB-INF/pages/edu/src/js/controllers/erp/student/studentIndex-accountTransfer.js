/**
 * Created by Liyong.zhu on 2016/10/24.studentIndex-accountTransfer.js
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_studentAccountTransferController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        '$uibMsgbox',
        'erp_studentsService',
        'erp_studentAccountQueryService',
        'erp_studentAccountService',
        'erp_printService',
        erp_studentAccountTransferController]);

function erp_studentAccountTransferController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    $uibMsgbox,
    erp_studentsService,
    erp_studentAccountQueryService,
    erp_studentAccountService,
    erp_printService) {
    // 学员信息
    $scope.student = {};
    //转出学员账户详情
    $scope.accountOutputDetail = {};
    //转入学员账户详情
    $scope.accountInputDetail = {};
    // 转出学员编号
    if ($scope.$resolve && $scope.$resolve.studentId) {
        $scope.studentId = $scope.$resolve.studentId;
        $scope.buId = $scope.$resolve.buId;
    } else {
        $scope.studentId = $("#rootIndex_studentId").val();
        $scope.buId = null;
    }
    // 转出学员信息
    $scope.transfer_out_student = {};
    // 转入学员信息
    $scope.transfer_in_student = {
    	account_type : 1
    };
    // 转出账户类型
    $scope.accountTypeOutList = [{
    	"key" : 1, "value" : "储蓄账户"
    }, {
    	"key" : 2, "value" : "冻结账户"
    }, {
    	"key" : 3, "value" : "退费账户"
    }];
    // 转进账户类型
    $scope.accountTypeInList = [{
    	"key" : 1, "value" : "储蓄账户"
    }, {
    	"key" : 2, "value" : "冻结账户"
    }, {
    	"key" : 3, "value" : "退费账户"
    }];
    
    // 动户变动记录编号 用于打印
    $scope.dynamicId = 0;
    
    // 查询学员账户信息
    $scope.queryStudentAccount = function(studentId, studentType) {
        var param = {
        	accountType : 0,
        	studentId : studentId
        };
        erp_studentAccountQueryService.query(param, function(resp) {
            if(!resp.error) {
            	if (studentType == 'out') {
            		if (resp.data) {
                        $scope.accountOutputDetail = resp.data;
                        if ($scope.transfer_out_student.account_type && $scope.transfer_out_student.account_type == 1) {//储值账户
                            $scope.transfer_out_student.fee_amount = resp.data.fee_amount ? resp.data.fee_amount : 0;
                        } else if ($scope.transfer_out_student.account_type && $scope.transfer_out_student.account_type == 2) {//冻结账户
                            $scope.transfer_out_student.fee_amount = resp.data.FROZEN_ACCOUNT ? resp.data.FROZEN_ACCOUNT : 0;
                        } else if ($scope.transfer_out_student.account_type && $scope.transfer_out_student.account_type == 3) {//冻结账户
                            $scope.transfer_out_student.fee_amount = resp.data.REFUND_ACCOUNT ? resp.data.REFUND_ACCOUNT : 0;
                        }
                    } else {
                    	$scope.transfer_out_student.fee_amount = 0;
                    }
            	} else {
            		if (resp.data) {
                        $scope.accountInputDetail = resp.data;
                        $scope.setTransInStuAccountType();
                        if ($scope.transfer_out_student.account_type && $scope.transfer_out_student.account_type == 1) {//储值账户
                            $scope.transfer_in_student.fee_amount = resp.data.fee_amount ? resp.data.fee_amount : 0;
                        } else if ($scope.transfer_out_student.account_type && ($scope.transfer_out_student.account_type == 2
                        		|| $scope.transfer_out_student.account_type == 3)) {//冻结账户
                            $scope.transfer_in_student.fee_amount = resp.data.FROZEN_ACCOUNT ? resp.data.FROZEN_ACCOUNT : 0;
                        }
                    } else {
                    	$scope.transfer_in_student.fee_amount = 0;
                    }
            	}
                //将转账金额清空
                $scope.transfer_money = 0;
            }
        });
    }
    
    $scope.setTransInStuAccountType = function () {
    	if ($scope.transfer_out_student.account_type == 3) {
        	$scope.transfer_in_student.account_type = 2;
        } else {
        	$scope.transfer_in_student.account_type = $scope.transfer_out_student.account_type;
        }
    }

    //切换转账账户类型
    $scope.changeTransferType = function() {
        $scope.transfer_money = 0;
        $scope.setTransInStuAccountType();
        
        if($scope.transfer_out_student.account_type == 1) {//储蓄账户
            $scope.transfer_out_student.fee_amount = $scope.accountOutputDetail.FEE_AMOUNT ? $scope.accountOutputDetail.FEE_AMOUNT : 0;
            $scope.transfer_in_student.fee_amount = $scope.accountInputDetail.FEE_AMOUNT ? $scope.accountInputDetail.FEE_AMOUNT : 0;
        } else if($scope.transfer_out_student.account_type == 2) {//冻结账户
            $scope.transfer_out_student.fee_amount = $scope.accountOutputDetail.FROZEN_ACCOUNT ? $scope.accountOutputDetail.FROZEN_ACCOUNT : 0;
            $scope.transfer_in_student.fee_amount = $scope.accountInputDetail.FROZEN_ACCOUNT ? $scope.accountInputDetail.FROZEN_ACCOUNT : 0;
        } else if($scope.transfer_out_student.account_type == 3) {//退费账户
            $scope.transfer_out_student.fee_amount = $scope.accountOutputDetail.REFUND_ACCOUNT ? $scope.accountOutputDetail.REFUND_ACCOUNT : 0;
            $scope.transfer_in_student.fee_amount = $scope.accountInputDetail.FROZEN_ACCOUNT ? $scope.accountInputDetail.FROZEN_ACCOUNT : 0;
        }
        
        if ($scope.transfer_out_student.account_type == 3) {
        	$scope.transfer_in_student.id = $scope.transfer_out_student.id;
            $scope.transfer_in_student.bu_id = $scope.transfer_out_student.bu_id;
            $scope.transfer_in_student.student_name = $scope.transfer_out_student.student_name;
            $scope.transfer_in_student.encoding = $scope.transfer_out_student.encoding;
            $scope.queryStudentAccount($scope.transfer_in_student.id, 'in');
            $('#txtTransferInStudentName').attr('readonly', 'readonly');
        } else {
        	$scope.transfer_in_student.id = null;
            $scope.transfer_in_student.bu_id = null;
            $scope.transfer_in_student.student_name = null;
            $scope.transfer_in_student.encoding = null;
            $('#txtTransferInStudentName').removeAttr('readonly');
        }
        //将转账金额清空
        $scope.transfer_money = 0;
    }

    // 转入学员输入框，名称变化则重新查询学员信息
    $scope.onTransferInStudentNameChange = function() {
        
        $scope.isDown = 'loading';
        $scope.searchResult = [];
        erp_studentsService.query({
            row_num: 10,
            need_contact: '1',
            searchInfo: $scope.transfer_in_student.student_name
        },
        function (resp) {
            
            $scope.isDown = '';
            if(!resp.error) {
                $scope.searchResult = resp.data;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    }

    // 选中转入学员输入框，清空输入框内容
    $scope.onTransferInStudentNameFocus = function() {
        if($scope.transfer_in_student.student_name == '请输入学员姓名') {
            $scope.transfer_in_student.student_name = '';
        }
        $scope.inputing = true;
    };
    
    // 鼠标离开转入学员控件处理事件
    $scope.onTransferInStudentNameBlur = function() {
        if(!$scope.transfer_in_student.student_name) {
            $scope.transfer_in_student.student_name = '请输入学员姓名';
        }
        $scope.inputing = false;
    };

    // 从转入学员控件的查询结果中选择一条数据触发该事件
    $scope.selectTransferInStudent = function(student) {
    	if (student.bu_id != $scope.transfer_out_student.bu_id) {
    		$uibMsgbox.error("转入学员和转出学员不属于一个团队，不能转出");
    		return;
    	}
        $scope.transfer_in_student = student;
        $scope.searchResult = [];
        $scope.queryStudentAccount($scope.transfer_in_student.id, 'in');
    };

    // 鼠标离开转出金额控件触发该事件
    $scope.onTransferOutBlur = function() {
        if (!$scope.transfer_money) {
            $scope.transfer_money = 0;
        } else {
        	$scope.checkTransferMoney();
        }
        return true;
    }
    $scope.checkTransferMoney = function() {
    	var transferMoney = genFloatByString($scope.transfer_money);
        if (!transferMoney || transferMoney <= 0 || transferMoney > 1000000) {
        	$uibMsgbox.error('转账金额必须在0到1000000之间！');
            $scope.transfer_money = 0;
            return false;
        }
        
        var feeAmount = genFloatByString($scope.transfer_out_student.fee_amount);
        feeAmount = feeAmount ? feeAmount : 0;
        if (feeAmount < transferMoney) {
        	$uibMsgbox.error("转账金额不能大于转出账户余额");
        	$scope.transfer_money = 0;
        	return false;
        }
        return true;
    }
    
    // 转账前校验处理
    $scope.checkBeforeSubmit = function() {
    	if ($scope.checkTransferMoney()) {
    		if (!$scope.transfer_in_student.id || !$scope.transfer_in_student.student_name) {
    			$uibMsgbox.error("请选择转入学员");
    			return false;
    		}
    		
    		if (!$scope.transfer_in_student.account_type || !$scope.transfer_out_student.account_type) {
    			$uibMsgbox.error("请选择转出和转入账户类型");
    			return false;
    		}
    		
    		if ($scope.transfer_in_student.id == $scope.transfer_out_student.id) {
    			if ($scope.transfer_in_student.account_type != 2 || $scope.transfer_out_student.account_type != 3) {
    				$uibMsgbox.error("本学员转账，只能从退费账户转到冻结账户");
        			return false;
    			}
    			
    		} else if ($scope.transfer_in_student.account_type != $scope.transfer_out_student.account_type) {
    			$uibMsgbox.error("学员互转，转入和转出账户类型必须一致");
    			return false;
    		}
    		return true;
    	}
    	return false;
    }
    
    // 打印动户变动情况
    $scope.printAccountDynamic = function() {
    	// 转账打印内容
    	erp_printService.printAccountDynamic({
    		"dynamicId" : $scope.dynamicId,
    		"printType" : "05"
    	}, 'erpStudentAccountPrintPanel');
    }

    // 转账处理
    $scope.ZZSubmit = function() {
        
    	if ($scope.checkBeforeSubmit()) {
    		var postParam = {
    			p_input_student_id : $scope.transfer_in_student.id,
    			p_output_student_id : $scope.transfer_out_student.id,
    			p_input_account_type : $scope.transfer_in_student.account_type,
    			p_output_account_type : $scope.transfer_out_student.account_type,
    			p_transfer_money : $scope.transfer_money,
    			p_remark : $scope.remark
	        };

	        $scope.delMessage = 'isdoing...';
	        postParam.accountOperateType = 'transfer';
            var _waitingModal = $uibMsgbox.waiting('转账中，请稍候...');
	        erp_studentAccountService.post(postParam, function(resp) {
                _waitingModal.close();
	            $scope.delMessage = '';
	            if (!resp.error) {
	            	$("#erpStudentAccountPrintPanel").modal('show');
	                $scope.transfer_money = 0;
	                $scope.remark = '';
	                $scope.queryStudentAccount($scope.transfer_in_student.id, 'in');
	                $scope.queryStudentAccount($scope.transfer_out_student.id, 'out');

	                $scope.dynamicId = resp.data.dynamic_id;
	            } else {
	            	$uibMsgbox.error(resp.message);
	            }
	        });
    	}   
    }

    function queryStudentInfo() {
        erp_studentsService.query({
                row_num: 20,
                studentId: $scope.studentId,
                buId: $scope.buId
            },
            function (resp) {
                if (!resp.error && resp.data.length) {
                    $scope.student = resp.data[0];
                    initial();
                } else {
                	$uibMsgbox.error(resp.message);
                }
                //将学生所属团队置空
                $scope.buId = null;
            });
    }
    
    function initial() {
        $scope.transfer_out_student = $scope.student;
        $scope.transfer_out_student.account_type = 1;
        
        $scope.transfer_in_student.student_name='请输入学员姓名';
        $scope.queryStudentAccount($scope.student.id, 'out');

        $('title').text('学员|'+ $scope.student.student_name);
    }

    queryStudentInfo();
}