"use strict";
angular.module('ework-ui').controller(
  'erp_noticeController', ['$scope', '$log', '$uibMsgbox', '$state', '$uibModal', 'erp_noticeService',
    erp_noticeController
  ]);

function erp_noticeController($scope, $log, $uibMsgbox, $state, $uibModal,
  erp_noticeService) {
  $scope.searchForm = {};
  $scope.notice = {};
  $scope.noticeList = [];
  // $scope.optype = 'add';

  $scope.paginationConf = {
    currentPage: 1, //当前页
    totalItems: 0,
    showFirstAndLast: true,
    showInfos: false,
    onChange: function() {}
  }

  $scope.paginationBars = [];

  $scope.queryPage = function() {
    $scope.searchForm.pageSize = $scope.paginationConf.itemsPerPage;
    $scope.searchForm.currentPage = $scope.paginationConf.currentPage;
    erp_noticeService.queryPage($scope.searchForm, function(resp) {
      if (resp.error) {
        $uibMsgbox.error(resp.message);
      } else {
        $scope.noticeList = resp.data;
        $scope.paginationConf.totalItems = resp.total || 0;
      }
    });
  }

  $scope.handleAddNotice = function() {
    $scope.openModal('add');
  }

  $scope.handlePutNotice = function(notice) {
    $scope.notice = notice;
    $scope.openModal('update');
  }
  
  $scope.handleViewNotice = function (notice) {
  	$scope.notice = notice;
  	$scope.openModal('view');
  }

  $scope.openModal = function(optype) {
    $uibModal.open({
      size: optype == 'view'?'':'lg',
      templateUrl: 'templates/block/modal/notice_detail.modal.html',
      controller: 'erp_noticeDetailController',
      scope: $scope,
      resolve: {
      	optype: function () {
      		return optype;
      	}
      }
    }).result.then(function(detail) {
    	$scope.notice = detail.notice;
      if (detail.optype == 'add') {
        $scope.add();
      } else {
        $scope.update();
      }
    }, function() {});
  }

  $scope.handleDeleteNotice = function(id) {
    $scope.remove(id);
  }

  $scope.add = function() {
    erp_noticeService.post($scope.notice, function(resp) {
      if (resp.error) {
        $uibMsgbox.error(resp.message);
      } else {
        $uibMsgbox.alert("添加成功");
        $scope.queryPage();
      }
    });
  }

  $scope.update = function() {
    erp_noticeService.put($scope.notice, function(resp) {
      if (resp.error) {
        $uibMsgbox.error(resp.message);
      } else {
        $uibMsgbox.alert('修改成功');
        $scope.queryPage();
      }
    });
  }

  $scope.remove = function(id) {
    $uibMsgbox.confirm('确认删除该通知？删除后不可恢复！', function(res) {
      if (res == 'yes') {
        erp_noticeService.remove({ id: id }, function(resp) {
          if (resp.error) {
            $uibMsgbox.error(resp.message);
          } else {
            $uibMsgbox.alert('删除成功');
            $scope.queryPage();
          }
        })
      }
    })
  }

  $scope.queryPage();
}
