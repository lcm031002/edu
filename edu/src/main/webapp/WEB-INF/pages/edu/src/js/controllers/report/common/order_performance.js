angular.module('ework-ui').controller('report_orderPerformaceController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'erp_organizationService',
    'report_orderPerformaceService',
    'erp_timeSeasonService',
    report_orderPerformaceController
]);

function report_orderPerformaceController($rootScope,
    $scope,
    $uibMsgbox,
    erp_organizationService,
    report_orderPerformaceService,
    erp_timeSeasonService) {

    $scope.searchParam = {
        p_start_date: moment.now(),
        p_end_date: moment.now()
    };

    $scope.performance ={isWfd:false};
    
    $scope.buList = [];
    
    $scope.branchList = [];

    $scope.checkBeforeQuery = function() {
    	if (!$scope.searchParam.p_bu_id) {
    		$uibMsgbox.error("请选择团队");
    		return false;
    	}
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		$scope.loadStatues = true;
    		report_orderPerformaceService.queryList($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.perfDetails = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
    		report_orderPerformaceService.exportExcel($scope.searchParam, function (resp) {
                _uibModalInstance.close();
                if (!resp.error) {
                    window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };
   /**
     * 查询课程季
     */
    function queryTimeSeason(){
        erp_timeSeasonService.list({
        },function(resp){
            if(!resp.error){
                $scope.timeSeasonList = resp.data;
            }else{
            	$uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.initPage = function() {
    	erp_organizationService.query({org_type : 3}, function(resp) {
    		if (!resp.error) {
    			$scope.buList = resp.data;
    		}
    	});
    	queryTimeSeason();
    }

    $scope.checkedWfd = function() {
        if (performance.isWfd) {
            performance.isWfd  = false;
            $scope.searchParam.p_isWfd=0;
        } else {
            performance.isWfd  = true;
            $scope.searchParam.p_isWfd=1;
        }
    }

    $scope.initPage();
    queryTimeSeason();
}