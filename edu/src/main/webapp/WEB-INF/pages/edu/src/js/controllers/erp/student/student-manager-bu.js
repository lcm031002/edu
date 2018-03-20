"use strict";
angular.module('ework-ui').controller(
    'erp_StudentManagerBuController',
    ['$rootScope', '$scope', '$state', '$uibMsgbox',
        'erp_StudentManagerService', erp_StudentManagerBuController]);

function erp_StudentManagerBuController($rootScope, $scope, $state, $uibMsgbox, erp_StudentManagerService) {
    $scope.stuMgrAnalysisList = [];
    $scope.searchParam = {
        range: 'curMonth',
        p_start_date: moment().startOf('month').format('YYYY-MM-DD'),
        p_end_date: moment().endOf('month').format('YYYY-MM-DD'),
        p_btn_tag: 'hidden',
        p_min_date: moment().startOf('month').format('YYYY-MM-DD'),
        p_max_date: moment().endOf('month').format('YYYY-MM-DD'),
    };
    $scope.gotoBranchReport = function (buId) {
        window.location.href = "?#/students/managerReport/Branch?buId=" + buId +
            '&startDate=' + $scope.searchParam.p_start_date +
            '&endDate=' + $scope.searchParam.p_end_date +
            '&dateRange=' + $scope.searchParam.range
    }
    $scope.queryStuMgrAnalysis = function () {
        $scope.loadStatues = true;
        if ($scope.buId) {
            $scope.searchParam.p_bu_id = $scope.buId;
        }
        erp_StudentManagerService.queryStuMgrBu($scope.searchParam, function (resp) {
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