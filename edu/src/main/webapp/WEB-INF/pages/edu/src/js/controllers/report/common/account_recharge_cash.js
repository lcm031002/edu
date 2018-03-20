angular.module('ework-ui').controller('report_rechargeCashController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'report_rechargeCashService',
    report_rechargeCashController
]);

function report_rechargeCashController($rootScope,
    $scope,
    $uibMsgbox,
    PUBORGService,
    erp_organizationService,
    report_rechargeCashService) {
    
    $scope.searchParam = {};
    
    $scope.buList = [];
    
    $scope.branchList = [];
    
	$scope.searchParam.p_start_date = Format("yyyy-MM-dd",new Date());
	$scope.searchParam.p_end_date = Format("yyyy-MM-dd",new Date());
	$scope.endDate = Format("yyyy-MM-dd",new Date());

    $scope.checkBeforeQuery = function() {
    	if (!$scope.searchParam.p_bu_id) {
    		$uibMsgbox.error("请选择团队");
    		return false;
    	}
		if(isEmpty($scope.searchParam.p_start_date)){
			alert("请填写开始日期");
			return false;
		}
		if(isEmpty($scope.searchParam.p_end_date)){
			alert("请填写结束日期");
			return false;
		}
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
            $scope.loadStatues = true;
    		report_rechargeCashService.query($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.rechargeCashList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
    	report_rechargeCashService.exportExcel($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    	
    	
    };
    
    $scope.buChange = function() {
    	$scope.initBranchSelector();
    }
    
    $scope.initBranchSelector = function() {
    	var param = {};
    	if ($scope.searchParam.p_bu_id) {
    		param.bu_id = $scope.searchParam.p_bu_id;
    	}
    	erp_organizationService.branchList(param, function(resp) {
    		if (!resp.error) {
    			$scope.branchList = resp.data;
    		}
    	});
    }
    
    $scope.initPage = function() {
        PUBORGService.queryBu({}, function(resp) {
            if (!resp.error) {
                $scope.buList = resp.data;
            }
        });
    	
    	$scope.initBranchSelector();
    }
    
    $scope.initPage();
}