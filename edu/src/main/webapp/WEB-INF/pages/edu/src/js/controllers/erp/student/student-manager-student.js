"use strict";
angular.module('ework-ui').controller(
    'erp_StudentManagerStudentController',
    ['$rootScope', '$scope', 
        '$state', '$stateParams', '$uibMsgbox',
        'erp_StudentManagerService', erp_StudentManagerStudentController]);

function erp_StudentManagerStudentController($rootScope, $scope,
    $state, $stateParams, $uibMsgbox, erp_StudentManagerService) {
    $scope.stuMgrAnalysisList = [];

    $scope.buId = $stateParams.buId ? +$stateParams.buId : null;
    $scope.branchId = $stateParams.branchId ? +$stateParams.branchId : null;
    $scope.studentManagerId = $stateParams.studentManagerId ? +$stateParams.studentManagerId : null;
    $scope.employeeName  = $stateParams.employeeName || null;
    console.log($scope.employeeName)
    $scope.searchParam = {
        range: $stateParams.dateRange || 'curMonth',
        p_start_date: $stateParams.startDate || moment().startOf('month').format('YYYY-MM-DD'),
        p_bu_id: $scope.buId,
        p_branch_id: $scope.branchId,
        p_studentManagerId: $scope.studentManagerId,
        p_end_date: $stateParams.endDate || moment().endOf('month').format('YYYY-MM-DD'),
        p_btn_tag: 'hidden',
        p_min_date: moment().startOf('month').format('YYYY-MM-DD'),
        p_max_date: moment().endOf('month').format('YYYY-MM-DD'),
    };

    $scope.queryStuMgrAnalysis = function () {
        $scope.loadStatues = true;
        // if ($scope.buId) {
        //     $scope.searchParam.p_bu_id = $scope.buId;
        // }
        // if ($scope.branchId) {
        //     $scope.searchParam.p_branch_id = $scope.branchId;
        // }
        // if ($scope.studentManagerId) {
        //     $scope.searchParam.p_studentManagerId = $scope.studentManagerId;
        // }
        erp_StudentManagerService.queryStuMgrStudent($scope.searchParam, function (resp) {
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