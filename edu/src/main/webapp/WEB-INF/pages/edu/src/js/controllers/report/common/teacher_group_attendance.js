angular.module('ework-ui').controller('report_teacherGroupAttendanceController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'erp_timeSeasonService',
    'erp_gradeService',
    'report_teacherGroupAttendanceService',

    report_teacherGroupAttendanceController
]);

function report_teacherGroupAttendanceController($rootScope,
    $scope,
    $uibMsgbox,
    PUBORGService,
    erp_organizationService,
    erp_timeSeasonService,
    erp_gradeService,
    report_teacherGroupAttendanceService
) {

    $scope.searchParam = {
    };

    $scope.buList = [];

    $scope.gradeList = [];

    $scope.timeSeasonList = [];

    $scope.searchParam = {
        p_course_date: moment.now()
    };

    $scope.checkBeforeQuery = function() {
    	if (!$scope.searchParam.p_bu_id) {
    		$uibMsgbox.error("请选择团队");
    		return false;
    	}

        if (!$scope.searchParam.p_course_date) {
            $uibMsgbox.error("请选择查询日期");
            return false;
        }
    	return true;
    }
    
    $scope.queryReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		$scope.loadStatues = true;
            report_teacherGroupAttendanceService.query($scope.searchParam, function (resp) {
                $scope.loadStatues = false;
                if (!resp.error) {
                    $scope.dataList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    	}
    };

    $scope.exportReport = function () {
    	if ($scope.checkBeforeQuery()) {
    		var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
            report_teacherGroupAttendanceService.exportExcel($scope.searchParam, function (resp) {
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