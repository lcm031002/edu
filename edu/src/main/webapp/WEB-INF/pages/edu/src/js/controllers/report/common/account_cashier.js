angular.module('ework-ui').controller('report_accountCashierController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'erp_printService',
    'report_accountCashierService',
    report_accountCashierController
]);

function report_accountCashierController($rootScope,
    $scope,
    $uibMsgbox,
    PUBORGService,
    erp_organizationService,
    erp_printService,
    report_accountCashierService) {

    $scope.searchParam = {};
    
    $scope.buList = [];
    
    $scope.branchList = [];
    
	$scope.searchParam.p_start_date = Format("yyyy-MM-dd",new Date());
	$scope.searchParam.p_end_date = Format("yyyy-MM-dd",new Date());
	$scope.endDate = Format("yyyy-MM-dd",new Date());

    $scope.checkBeforeQuery = function() {
    	if (!$scope.searchParam.p_bu_id) {
    		$uibMsgbox.confirm("请选择团队");
    		return false;
    	}
		if(isEmpty($scope.searchParam.p_start_date)){
			$uibMsgbox.confirm("请填写开始日期");
			return false;
		}
		if(isEmpty($scope.searchParam.p_end_date)){
			$uibMsgbox.confirm("请填写结束日期");
			return false;
		}
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		$scope.loadStatues = true;
    		report_accountCashierService.query($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.accountCashierList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
    	report_accountCashierService.exportExcel($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    	
    	
    };

    $scope.print = function (data) {
        switch (data.opt_type_name) {
            case "充值":
                erp_printService.printAccountDynamic({"dynamicId": data.opt_id, "printType": "03"});
                break;
            case "报班":
                erp_printService.printAccountDynamic({"encoding": data.opt_encoding, "printType": "01"});
                break;
            case "退费":
                var refund = {
                    branch_name:data.branch_name,
                    create_time:data.input_time,
                    student_name:data.student_name,
                    student_encoding:data.student_encoding,
                    encoding:data.opt_encoding,
                    fee_amount:data.pay_amount
                }
                CreatePrintPageForRefund (refund);
                break;
            case "取款":
                erp_printService.printAccountDynamic({"dynamicId": data.opt_id, "printType": "04"});
                break;
            default:
                $uibMsgbox.alert("该类型的账户单据不支持打印");
        }
    };
}