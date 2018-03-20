angular.module('ework-ui').controller('onLineOrderReportController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'erp_organizationService',
    'report_onLineOrderService',
    'erp_timeSeasonService',
    onLineOrderReportController
]);

function onLineOrderReportController($rootScope,
    $scope,
    $uibMsgbox,
    erp_organizationService,
    report_onLineOrderService,
    erp_timeSeasonService) {

    $scope.searchParam = {
        p_season_id: null,
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
    		report_onLineOrderService.queryList($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.perfDetails = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.dateChanged = function(){
        if(!$scope.searchParam.p_bu_id){
            return false;
        }else{
            $scope.queryReport()
        }
        
    }
    $scope.exportReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
    		report_onLineOrderService.exportExcel($scope.searchParam, function (resp) {
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
                $scope.timeSeasonList.unshift({
                    id: null,
                    course_season_name: '全部'
                })
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

    $scope.initPage();
}