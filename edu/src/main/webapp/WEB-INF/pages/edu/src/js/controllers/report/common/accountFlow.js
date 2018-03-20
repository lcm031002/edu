angular.module('ework-ui').controller('report_accountFlowController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'erp_organizationService',
    'report_accountFlowService',
    report_accountFlowController
]);

function report_accountFlowController($rootScope,
    $scope,
    $uibMsgbox,
    erp_organizationService,
    report_accountFlowService) {

    $scope.searchParam = {
        p_branch_id :null
    };
    
    $scope.branchList = [];

    $scope.checkBeforeQuery = function() {
    	//if (!$scope.searchParam.p_branch_id) {
    	//	$uibMsgbox.error("请选择校区");
    	//	return false;
    	//}
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		$scope.loadStatues = true;
            report_accountFlowService.queryList($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.accountFlowList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	if ($scope.checkBeforeQuery()) {
	    	var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
	    	report_accountFlowService.exportExcel($scope.searchParam, function (resp) {
	            _uibModalInstance.close();
	            if (!resp.error) {
	                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
	            } else {
	                $uibMsgbox.error(resp.message);
	            }
	        });
    	}
    };
    
    $scope.initPage = function() {
    	erp_organizationService.branchList({}, function(resp) {
    		if (!resp.error) {
    			$scope.branchList = resp.data;
                $scope.branchList.unshift({id:null,org_name:'全部'});
    		}
    	});
    }
    
    $scope.initPage();
}