/**
 * 
 */
"use strict";
angular
  .module('ework-ui')
  .controller('klxxedu_indexController', [
    '$rootScope',
    '$scope',
    '$cookieStore',
    '$log',
    '$uibModal',
    'erp_noticeService',
    klxxedu_indexController
  ]);

function klxxedu_indexController(
  $rootScope,
  $scope,
  $cookieStore,
  $log,
  $uibModal,
  erp_noticeService) {
  $scope.optype = 'view';
  $scope.notice = {};
  $scope.noticeList = [];

  $scope.totalNotice = 5;
  $scope.$watch('$parent.curSystem', function(newValue, oldValue) {
    if (newValue && newValue.menus) {
      $scope.menus = newValue.menus;
      if ($scope.menus) {
        $.each($scope.menus, function(i, menu) {
          menu.href = menu.href + "?_=" + (new Date()).getTime();
        });
      }
    }
  })
  
  $scope.queryNotice = function() {
    erp_noticeService.queryPage({
      pageSize: 5,
      currentPage: 1,
      p_status: 1
    }, function(resp) {
      if (resp.error) {
        $uibMsgbox.error(resp.message);
      } else {
        $scope.noticeList = resp.data;
        $scope.totalNotice = resp.total;
      }
    });
  }

  $scope.viewNotice = function(notice) {
    $scope.notice = notice;
    $uibModal.open({
      templateUrl: 'templates/block/modal/notice_detail.modal.html',
      controller: 'erp_noticeDetailController',
      resolve: {
        optype: function() {
          return 'view'
        }
      },
      scope: $scope
    }).result.then(function(detail) {}, function() {});

    erp_noticeService.addUserNoticeRel({
      notice_id: notice.id
    }, function(resp) {
        $scope.queryNotice()
    });
  }

  $scope.viewNoticeList = function() {
    $uibModal.open({
      size: 'lg',
      templateUrl: 'templates/block/modal/system-notice-list.modal.html',
      controller: 'erp_noticeListModalController',
      resolve: {
        optype: function() {
          return 'view';
        }
      }
    })
  }

  $('title').text('首页 | 厝边素高');

  $scope.queryNotice();
};
