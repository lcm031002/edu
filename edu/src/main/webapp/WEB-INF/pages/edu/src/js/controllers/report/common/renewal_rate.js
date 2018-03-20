angular.module('ework-ui').controller('report_renewalRateController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'erp_timeSeasonService',
    'erp_gradeService',
    'report_renewalRateService',
    report_renewalRateController
]);

function report_renewalRateController($rootScope,
	$scope,
	$uibMsgbox,
	PUBORGService,
	erp_organizationService,
	erp_timeSeasonService,
	erp_gradeService,
	report_renewalRateService) {
    
    $scope.searchParam = {
		p_teacher_type : '1'
	};
    
    $scope.buList = [];
    
    $scope.timeSeasonList = [];
    
    $scope.gradeList = [];
    
	$scope.searchParam.p_start_date = Format("yyyy-MM-dd",new Date());
	$scope.searchParam.p_end_date = Format("yyyy-MM-dd",new Date());
	$scope.endDate = Format("yyyy-MM-dd",new Date());

    $scope.checkBeforeQuery = function() {
    	if (!$scope.searchParam.p_bu_id) {
    		$uibMsgbox.error("请选择团队");
    		return false;
    	}
    	if (!$scope.searchParam.p_season_id) {
    		$uibMsgbox.error("请选择课程季");
    		return false;
    	}
        if (!$scope.searchParam.p_teacher_type) {
            $uibMsgbox.error("请选择老师类型:主讲/辅师");
            return false;
        }
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		$scope.loadStatues = true;
    		var selected_season = $("#season_name").find("option:selected").text()
    		var season_name = null;
    		
    		if(selected_season.indexOf("春季班") > 0){
    			season_name = "春季班";
    		}else if(selected_season.indexOf("暑假班") > 0){
    			season_name = "暑假班";
    		}else if(selected_season.indexOf("秋季班") > 0){
    			season_name = "秋季班";
    		}else if(selected_season.indexOf("寒假班") > 0){
    			season_name = "寒假班";
    		}
    		$scope.searchParam.p_season_name = season_name;
    		report_renewalRateService.queryForSum($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.renewalRateList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	if ($scope.checkBeforeQuery()) {
        	var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
    		var selected_season = $("#season_name").find("option:selected").text()
    		var season_name = null;
    		
    		if(selected_season.indexOf("春季班") > 0){
    			season_name = "春季班";
    		}else if(selected_season.indexOf("暑假班") > 0){
    			season_name = "暑假班";
    		}else if(selected_season.indexOf("秋季班") > 0){
    			season_name = "秋季班";
    		}else if(selected_season.indexOf("寒假班") > 0){
    			season_name = "寒假班";
    		}
    		$scope.searchParam.p_season_name = season_name;
        	report_renewalRateService.exportExcelForSum($scope.searchParam, function (resp) {
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