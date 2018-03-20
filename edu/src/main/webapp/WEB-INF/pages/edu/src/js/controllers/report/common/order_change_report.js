angular.module('ework-ui').controller('report_orderChangeReportController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'erp_dictService',
    'report_orderChangeReportService',
    report_orderChangeReportController
]);

function report_orderChangeReportController($rootScope,
    $scope,
    $uibMsgbox,
    PUBORGService,
    erp_organizationService,
    erp_dictService,
    report_orderChangeReportService) {

    $scope.searchParam = {
        p_business_type:-1
    };
    
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
    		report_orderChangeReportService.query($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.orderChangeList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
    	report_orderChangeReportService.exportExcel($scope.searchParam, function (resp) {
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
    $scope.businessList = [];
    $scope.initPage = function() {
        PUBORGService.queryBu({}, function(resp) {
            if (!resp.error) {
                $scope.buList = resp.data;
            }
        });
        erp_dictService.query({code:"businessType"},function(resp) {
            if (!resp.error) {
                $scope.businessList = resp.data;
                $scope.businessList.unshift({name:'全部',code:-1});
                $.each($scope.businessList,function(i,n) {
                    n.code = parseInt(n.code);
                });
            }
        });
    	$scope.initBranchSelector();
    };
    
    $scope.initPage();
}