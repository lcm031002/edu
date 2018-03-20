"use strict";
angular.module('ework-ui').controller(
    'erp_StudentManagerController',
    ['$rootScope',
        '$scope',
        '$state',
        '$stateParams',
        '$uibMsgbox',
        'erp_StudentManagerService',
        erp_StudentManagerController
    ]);

function erp_StudentManagerController($rootScope, $scope,
    $state, $stateParams, $uibMsgbox, erp_StudentManagerService) {
    $scope.stuMgrAnalysisList = [];

    $scope.buId = $stateParams.buId ? +$stateParams.buId : null;
    $scope.branchId = $stateParams.branchId ? +$stateParams.branchId : null;

    $scope.searchParam = {
      range: $stateParams.dateRange || 'curMonth',
      p_start_date: $stateParams.startDate || moment().startOf('month').format('YYYY-MM-DD'),
      p_end_date: $stateParams.endDate || moment().endOf('month').format('YYYY-MM-DD'),
      p_btn_tag: 'hidden',
      p_min_date: moment().startOf('month').format('YYYY-MM-DD'),
      p_max_date: moment().endOf('month').format('YYYY-MM-DD'),
    };
    $scope.gotoTeacherStudentReport = function (buId, branchId, studentManagerId, employeeName) {
        window.location.href = "?#/students/managerReport/student?buId=" + buId +
            '&branchId=' + branchId + '&studentManagerId=' + studentManagerId +
            '&employeeName=' + employeeName +
            '&startDate=' + $scope.searchParam.p_start_date +
            '&endDate=' + $scope.searchParam.p_end_date +
            '&dateRange=' + $scope.searchParam.range;
    }
    $scope.queryStuMgrAnalysis = function () {
        $scope.loadStatues = true;
        if ($scope.buId) {
            $scope.searchParam.p_bu_id = $scope.buId;
        }
        if ($scope.branchId) {
            $scope.searchParam.p_branch_id = $scope.branchId;
        }
        erp_StudentManagerService.queryStuMgr($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.stuMgrAnalysisList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }

        });
    };

    $scope.queryStuMgrAnalysis();

}