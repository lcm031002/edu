angular.module('ework-ui').controller('report_courseAnalysisController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    'erp_organizationService',
    'report_courseAnalysisService',
  report_courseAnalysisController
]);

function report_courseAnalysisController($rootScope,
    $scope,
    $uibMsgbox,
    erp_organizationService,
    report_courseAnalysisService) {
    $scope.searchParam = {
        startDate: '',
        endDate: '',
        branchId: '',
        courseInfo: '',
        dateType: '1',
        teacherId: '',
        buId: '',
        default_range: 'curMonth',
        btnTag: 'hidden'
    };

  $scope.paginationConf = {
    currentPage: 1, //当前页
    totalItems: 0,
    onChange: function() {
      $scope.queryReport();
    }
  };

    $scope.dataList = [];

    $scope.beforeQuery = function() {
          if (!$scope.searchParam.branchId) {
            $uibMsgbox.warn('您还没选择校区，请选择校区！', function() {
              setTimeout(function() {
                $('.btn-group.sel-org.pull-left').addClass('open');
              }, 300);
            })
            return false;
          }
        if (!$scope.searchParam.dateType) {
            $uibMsgbox.error("请选择日期类型");
            return false;
        }
        if (!$scope.searchParam.startDate) {
            $uibMsgbox.error("请选择开始日期");
            return false;
        }
        if (!$scope.searchParam.endDate) {
            $uibMsgbox.error("请选择截止日期");
            return false;
        }
        if (!checkStartEndTime($scope.searchParam.startDate, $scope.searchParam.endDate)) {
            $uibMsgbox.alert('截止日期必须大于或等于开始日期');
            return false;
        }

        if (!checkStartEndDateIntervalDays($scope.searchParam.startDate, $scope.searchParam.endDate, 30)) {
          $uibMsgbox.alert('起止日期间隔不能超过30天');
          return false;
        }
        return true;
    };

    $scope.queryReport = function () {
        if ($scope.beforeQuery() == false) {
            return;
        }

        $scope.loadStatues = true;

        $scope.searchParam.pageSize = $scope.paginationConf.itemsPerPage;
        $scope.searchParam.currentPage = $scope.paginationConf.currentPage;

        report_courseAnalysisService.query($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.exportReport = function () {
        if ($scope.beforeQuery() == false) {
            return;
        }
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_courseAnalysisService.exportExcel($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }
    activate ();

    function activate() {
      if ($rootScope.selectedOrg && $rootScope.selectedOrg.buId && $rootScope.selectedOrg.id) {
        $scope.searchParam.buId = $rootScope.selectedOrg.buId;
        $scope.searchParam.branchId = $rootScope.selectedOrg.id;
      }
      $scope.$watch('selectedOrg', function (newValue, oldValue) {
        if (newValue){
          $scope.searchParam.buId = $rootScope.selectedOrg.buId;
          $scope.searchParam.branchId = $rootScope.selectedOrg.id;
        }
      });
    }
}