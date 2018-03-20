angular.module('ework-ui').controller('erp_employeeListModalController', [
  '$rootScope',
  '$scope',
  '$log',
  '$uibMsgbox',
  '$uibModalInstance',
  'counselorType',
  'modalTitle',
  'erp_employeeService',
  erp_employeeListModalController
])

function erp_employeeListModalController(
  $rootScope,
  $scope,
  $log,
  $uibMsgbox,
  $uibModalInstance,
  counselorType,
  modalTitle,
  erp_employeeService
) {
  $scope.relationList = [];
  $scope.searchInfo = {
    employeeName: ''
  }
  $scope.pageConf = {
    currentPage: 1,
    totalItems: 0,
    itemsPerPage: 8,
    // showInfos: false,
    onChange: function () {
      $scope.getEmployeeList();
    }
  }
  $scope.modalTitle = modalTitle || '员工列表';
  $scope.counselorType = counselorType || 1;
  $scope.employeeList = []
  $scope.selectedemployee = null;
  $scope.onEmployeeSelected = function(employee) {
    if ($scope.selectedemployee && $scope.selectedemployee.id == employee.id) {
      $scope.selectedemployee = null;
    } else {
      $scope.selectedemployee = employee;
    }
  }
  $scope.getEmployeeList = function() {
    // var _modalInstance= $uibMsgbox.waiting('加载中，请稍候...');
    erp_employeeService.query({
      currentPage: $scope.pageConf.currentPage,
      pageSize: $scope.pageConf.itemsPerPage,
      counselor_type: $scope.counselorType,
      employee_name: $scope.searchInfo.employeeName
    }, function(resp) {
      // _modalInstance.close();
      $scope.employeeList = resp.data;
      $scope.pageConf.totalItems = resp.total || 0;
    })
  }
  $scope.confirm = function () {
    if ($scope.selectedemployee) {
      $uibModalInstance.close($scope.selectedemployee);
    } else {
      $uibMsgbox.alert('请选择老师！');
    }
  }
  $scope.getEmployeeList();
}
