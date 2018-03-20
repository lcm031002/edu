angular.module('ework-ui').controller('report_renewalRateLastBaseController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'erp_timeSeasonService',
    'erp_gradeService',
    'report_renewalRateService',
    report_renewalRateLastBaseController
]);

function report_renewalRateLastBaseController($rootScope,
    $scope,
    $uibMsgbox,
    PUBORGService,
    erp_organizationService,
    erp_timeSeasonService,
    erp_gradeService,
    report_renewalRateService) {
    
    $scope.searchParam = {};
    
    $scope.buList = [];
    
    $scope.timeSeasonList = [];
    
    $scope.gradeList = [];
    
	$scope.searchParam.p_start_date = Format("yyyy-MM-dd",new Date());
	$scope.searchParam.p_end_date = Format("yyyy-MM-dd",new Date());
	$scope.endDate = Format("yyyy-MM-dd",new Date());

    $scope.checkBeforeQuery = function() {
    	//if (!$scope.searchParam.p_bu_id) {
    	//	$uibMsgbox.error("请选择团队");
    	//	return false;
    	//}
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		$scope.loadStatues = true;
    		report_renewalRateService.queryForLastBase($scope.searchParam, function (resp) {
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
    	var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
    	report_renewalRateService.exportExcelForLastBase($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    	
    	
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
            }
        });
    	
    	$scope.initTimeSeasonSelector();
    	$scope.initGradeSelector();
    }
    
	$scope.GetQueryString = function(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  decodeURI(r[2]); return null;
	};
    
    $scope.initPage();
    
    $scope.searchParam.p_bu_id = $scope.GetQueryString("buId");
	$scope.searchParam.p_grade_id = $scope.GetQueryString("gradeId");
	$scope.searchParam.p_season_id = $scope.GetQueryString("seasonId");
    $scope.searchParam.p_teacher_type = $scope.GetQueryString("teacherType");
    $scope.searchParam.p_teacher_name = $scope.GetQueryString("teacherName");

	$scope.queryReport();
}