/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_invoiceController', [
        '$scope',
        '$log',
        '$uibMsgbox',
        'erp_InvoiceManagerService',
        'erp_invoiceCompanyService',
        erp_invoiceController]);

function erp_invoiceController(
    $scope,
    $log,
    $uibMsgbox,
    erp_InvoiceManagerService,
    erp_invoiceCompanyService
    ) {
	// 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    
    // 搜索字段
    $scope.searchParam = {
        status: 0, // 发票状态
        start_date: getCurrentDate(),
        end_date: getCurrentDate()
    };
    // 发票列表
	$scope.invoiceList = [];
	
	// 开票公司列表
	$scope.invoiceCmpList = [];

    // 业务类型列表
	$scope.headingList = [{"key" : "1", "value" : "个人"},
	                   	  {"key" : "2", "value" : "公司"}];
	
	$scope.invoiceStatus = 0;

    // 与表单绑定的数据，用于添加和修改
    $scope.invoice = {
    }
    
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
        	if ($scope.searchParam.bu_id) {
        		$scope.query();
        	}
        }
    }

    $scope.paginationBars = [];
    
    // 处理【修改发票】按钮点击事件
    $scope.handleInvoice = function (invoice) {
        $scope.optype = 'put';
        $scope.invoiceStatus = invoice.status;
        erp_invoiceCompanyService.list({}, function(resp) {
        	if (!resp.error) {
        		$scope.invoiceCmpList = resp.data;
        	}
        });
        if (invoice.status == 4) { // 申请中
        	invoice.actualMoney = invoice.requiredMoney;
        }
        $scope.invoice = invoice;
        $("#erpFinanceInvoicePanel").modal('show');
    }
    
    // 发票状态改变，查询相应状态的数据
    $scope.handleChangeStatus = function(status) {
    	$scope.searchParam.status = status;
    	$scope.handleQueryInvoice();
    }

    // 处理【查询发票】按钮点击事件
    $scope.handleQueryInvoice = function () {
        $scope.query();
    }
    
    $scope.handlePutInvoice = function(status) {
    	$scope.put(status);
    }
    
    $scope.beforeQuery = function() {
    	if (!$scope.searchParam.bu_id) {
    		$uibMsgbox.error("请选择团队");
    		return false;
    	}
    	return true;
    }

    // 查询发票
    $scope.query = function () {
    	if ($scope.beforeQuery()) {
    		erp_InvoiceManagerService.query({
        		pageSize: $scope.paginationConf.itemsPerPage,
                currentPage: $scope.paginationConf.currentPage,
                p_bu_id: $scope.searchParam.bu_id,
                p_branch_id: $scope.searchParam.branch_id,
                p_start_date: $scope.searchParam.start_date,
                p_end_date: $scope.searchParam.end_date,
                p_status: $scope.searchParam.status,
                p_invoice_code: $scope.searchParam.invoice_code
            },
            function(resp){
                if(!resp.error) {
               	 	$scope.invoiceList = resp.data;
               	 	$scope.paginationConf.totalItems = resp.total || 0;
                } else {
                	$uibMsgbox.error(resp.message);
                }
            });
    	}
    }
    
    $scope.checkBeforePut = function(status) {
    	if (status == 1) {
    		if (!$scope.invoice.actualMoney) {
    			$uibMsgbox.error("请输入实开金额！");
    			return false;
    		}
    		
    		if (!$scope.invoice.invoiceCode) {
    			$uibMsgbox.error("请输入发票号码！");
    			return false;
    		}
    		
    		if (!$scope.invoice.invoiceCompamy) {
    			$uibMsgbox.error("请选择发票单位！");
    			return false;
    		}
    	}
    	return true;
    }

    // 修改发票
    $scope.put = function (status) {
    	var oriStatus = $scope.invoice.status;
    	if (status) {
    		$scope.invoice.status = status;
    	}
    	
    	if (!$scope.checkBeforePut(status)) {
    		$scope.invoice.status = oriStatus;
    		return;
    	}
    	
    	erp_InvoiceManagerService.put($scope.invoice, function (resp) {
    		if(!resp.error) {
    			var msg = (status == 5) ? "拒绝成功" : ((status == 3) ? "未回收作废成功" : ((status == 2) ? "已回收作废成功" : "开票成功"));
    			$uibMsgbox.success(msg);
    			$scope.query();
    			$('#erpFinanceInvoicePanel').modal('hide');
    		} else {
    			$scope.invoice.status = oriStatus;
    			$uibMsgbox.error(resp.message);
    		}
        });
    }
    
    // 导出数据
    $scope.exportInv = function() {
    	if ($scope.beforeQuery()) {
    		erp_InvoiceManagerService.exportInv({
                p_bu_id: $scope.searchParam.bu_id,
                p_branch_id: $scope.searchParam.branch_id,
                p_start_date: $scope.searchParam.start_date,
                p_end_date: $scope.searchParam.end_date,
                p_status: $scope.searchParam.status,
                p_invoice_code: $scope.searchParam.invoice_code
            }, function(resp) {
            	if (!resp.error) {
        			window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
        		} else {
        			$uibMsgbox.error(resp.message);
        		}
            });
    	}
    }
}