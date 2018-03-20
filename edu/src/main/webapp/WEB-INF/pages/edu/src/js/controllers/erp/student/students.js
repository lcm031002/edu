angular.module('ework-ui').controller('erp_StudentListController', [
  '$rootScope',
  '$scope',
  'erp_studentsService',
  '$state',
  '$cookieStore',
  '$uibMsgbox',
  erp_StudentListController
]);

function erp_StudentListController(
  $rootScope,
  $scope,
  erp_studentsService,
  $state,
  $cookieStore,
  $uibMsgbox
) {
  $scope.studentDetail = {};
  $scope.viewCtrl = {
    view: 'list',
    optype: 'add'
  };
  $scope.searchParam = {
    searchInfo: '',
    exact: true
  };

  $scope.studentsList = [];

  $scope.pageConf = {
    currentPage: 1,
    totalItems: 0,
    itemsPerPage: 20,
    showInfos: false,
    showFirstAndLast: true,
    onChange: function () {
      $scope.getCurrentPage();
    }
  };

  $scope.getCurrentPage = function () {
    $scope.loadStatues = true;
    erp_studentsService.query({
      pageSize: $scope.pageConf.itemsPerpage,
      row_num: $scope.pageConf.currentPage,
      currentPage: $scope.pageConf.currentPage,
      need_contact: '1',
      searchInfo: $scope.searchParam.searchInfo,
      searchType: $scope.searchParam.exact ? 1 : 0
    }, function (resp) {
      $scope.loadStatues = false;
      $scope.studentsList.splice(0, $scope.studentsList.length);
      for (var i = 0; i < resp.data.length; i++) {
        $scope.studentsList.push(resp.data[i]);
      }
      $scope.pageConf.totalItems = resp.total;
    });
  };

  $scope.viewStudentDetail = function (stu) {
    window.open('?studentId=' + stu.id + '#/studentMgr/studentMgrIndex');
  };

  $scope.doSearch = function () {
    $scope.pageConf.currentPage = 1;
    $scope.getCurrentPage();
  };
  $scope.getCurrentPage();

  $scope.addStudent = function () {
    $state.go('newStudent');
  };
}
