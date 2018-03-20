angular.module('ework-ui').controller('report_businessStatisticsController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'erp_timeSeasonService',
    'erp_gradeService',
    'report_bizStatisticsService',
    report_businessStatisticsController
]);

function report_businessStatisticsController($rootScope,
    $scope,
    $uibMsgbox,
    PUBORGService,
    erp_organizationService,
    erp_timeSeasonService,
    erp_gradeService,
    report_bizStatisticsService) {
    
    $scope.searchParam = {
	};
    
    $scope.buList = [];
    
    $scope.timeSeasonList = [];
    
    $scope.gradeList = [];
    
    $scope.checkBeforeQuery = function() {
    	if (!$scope.searchParam.p_bu_id) {
    		$uibMsgbox.error("请选择团队");
    		return false;
    	}
    	if (!$scope.searchParam.p_season_id) {
    		$uibMsgbox.error("请选择课程季");
    		return false;
    	}
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		$scope.loadStatues = true;

            report_bizStatisticsService.query($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.busStatisticss = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	if ($scope.checkBeforeQuery()) {
        	var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');

            report_bizStatisticsService.exportExcel($scope.searchParam, function (resp) {
                _uibModalInstance.close();
                if (!resp.error) {
                    window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };
    
    $scope.buChange = function() {
    	$scope.initTimeSeasonSelector();
    	$scope.initGradeSelector();
    }
    
    $scope.initTimeSeasonSelector = function() {
    	var param = {};
    	if ($scope.searchParam.p_bu_id) {
    		param.bu_id = $scope.searchParam.p_bu_id;
    	}
    	erp_timeSeasonService.list(param, function(resp) {
    		if (!resp.error) {
    			$scope.timeSeasonList = resp.data;
    		}
    	});
    }
    
    $scope.initGradeSelector = function() {
    	var param = {};
    	if ($scope.searchParam.p_bu_id) {
    		param.bu_id = $scope.searchParam.p_bu_id;
    	}
    	erp_gradeService.querySelectDatas(param, function(resp) {
    		if (!resp.error) {
    			$scope.gradeList = resp.data;
    		}
    	});
    }
    
    $scope.initPage = function() {
        PUBORGService.queryBu({}, function(resp) {
            if (!resp.error) {
                $scope.buList = resp.data;
				$scope.searchParam.p_bu_id = $scope.buList[0].buId;
            }
        });
    	
    	$scope.initTimeSeasonSelector();
    	$scope.initGradeSelector();
    };
    
    $scope.initPage();
}