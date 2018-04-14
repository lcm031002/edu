/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_invoiceCompanyController', [
        '$scope',
        '$log',
        '$uibMsgbox',
        'erp_invoiceCompanyService',
        erp_invoiceCompanyController]);

function erp_invoiceCompanyController(
    $scope,
    $log,
    $uibMsgbox,
    erp_invoiceCompanyService
    ) {
	// 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    
    // 搜索的开票单位名称
    $scope.searchParam = {
    	company_name: ''
    };

	$scope.invoiceCompanyList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.invoiceCompany = {
    		"id" : '',
    		"company_name" : '',
    		"description" : ''
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
            $scope.query();
        }
    }

    $scope.paginationBars = [];

    // 处理【添加开票单位】按钮点击事件
    $scope.handleAddInvoiceCompany = function () {
        $scope.optype = 'add';
        $scope.resetForm();
        $('#erpSystemCommonInvoiceCompanyPanel').modal('show');
    }
    
    // 处理【修改开票单位】按钮点击事件
    $scope.handlePutInvoiceCompany = function (invoiceCompany) {
        $scope.optype = 'put';
        $scope.invoiceCompany = invoiceCompany;
        $("#erpSystemCommonInvoiceCompanyPanel").modal('show');
    }
    
    // 处理【删除开票单位】按钮点击事件
    $scope.handleDeleteInvoiceCompany = function (id) {
        if (window.confirm('确定删除选中开票公司？')) {
            $scope.changeStatus(id, "delete");
        }
    }

    // 处理【查询课程季】按钮点击事件
    $scope.handleQueryInvoiceCompany = function () {
        $scope.query();
    }

    // 处理课程季表单【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        $('#erpSystemCommonInvoiceCompanyPanel').modal('hide');
    }

    // 处理开票单位表单【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
    	if ($scope.optype == 'add') {
    		$scope.add();
    	} else {
    		$scope.put();
    	}
    }
    $scope.onStatusChange = function (invoiceCompany) {
    	var flag = (invoiceCompany.status == '1') ? 'valid' : 'invalid';
    	$scope.changeStatus(invoiceCompany.id, flag);
    }
    // 查询开票单位
    $scope.query = function () {
    	erp_invoiceCompanyService.query({
    		pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            p_company_name: $scope.searchParam.company_name,
        },
        function(resp){
            if(!resp.error) {
           	 	$scope.invoiceCompanyList = resp.data;
           	 	$scope.paginationConf.totalItems = resp.total || 0;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    }

    // 添加开票单位
    $scope.add = function () {
    	erp_invoiceCompanyService.post($scope.invoiceCompany, function (resp) {
    		if (!resp.error) {
    			$uibMsgbox.success("添加成功");
    			$('#erpSystemCommonInvoiceCompanyPanel').modal('hide');
    			$scope.query();
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }

    // 修改开票单位
    $scope.put = function () {
    	erp_invoiceCompanyService.put($scope.invoiceCompany, function (resp) {
    		if (!resp.error) {
    			$uibMsgbox.success("修改成功");
    			$('#erpSystemCommonInvoiceCompanyPanel').modal('hide');
    			$scope.query();
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }

    /*
     * 修改开票公司状态
     * @param flag true-生效 false-无效
     */
    $scope.changeStatus = function (id, flag) {
    	erp_invoiceCompanyService.changeStatus({"id" : id, "flag" : flag}, function (resp) {
    		if(!resp.error) {
    			if (flag == 'delete') {
    				$uibMsgbox.success("删除成功");
        			$scope.query();
    			} else {
    				$uibMsgbox.success("状态修改成功");
    			}    			
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }

    // 重置开票单位表单
    $scope.resetForm = function () {
        $("#erpSystemCommonInvoiceCompanyPanel form")[0].reset();
    }
    
    $scope.query();
}