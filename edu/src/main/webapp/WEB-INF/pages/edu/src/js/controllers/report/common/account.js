angular.module('ework-ui').controller('report_accountController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'PUBORGService',
    'erp_organizationService',
    'report_accountService',
    report_accountController
]);

function report_accountController($rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    PUBORGService,
    erp_organizationService,
    report_accountService) {

    $scope.searchParam = {
        selectedBuId: ''
    };

    $scope.buList = [];

    $scope.branchList = [];

    $scope.dataList = undefined;

    $scope.queryReport = function () {
        if (!$scope.searchParam.p_bu_id) {
            $uibMsgbox.error("请先选择团队！");
            return;
        }
        $scope.loadStatues = true;
        report_accountService.query($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.outputReport = function () {
        if (!$scope.searchParam.p_bu_id) {
            $uibMsgbox.error("请先选择团队！");
            return;
        }
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_accountService.output($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                // window.open('../report/common/downloadTempFile?fileName=' + resp.data);
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

    $scope.initialize = function () {
        PUBORGService.queryBu({}, function(resp) {
            if (!resp.error) {
                $scope.buList = resp.data;
            }
        });

        $scope.initBranchSelector();
    };

    $scope.initialize();
}