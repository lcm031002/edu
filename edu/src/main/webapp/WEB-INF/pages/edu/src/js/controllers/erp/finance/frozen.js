angular.module('ework-ui').controller('erp_financeFrozenController', [
  '$rootScope',
  '$scope',
  '$uibMsgbox',
  '$uibModal',
  'erp_frozenService',
  erp_financeFrozenController
])

function erp_financeFrozenController(
  $rootScope,
  $scope,
  $uibMsgbox,
  $uibModal,
  erp_frozenService
) {
  $scope.searchParams = {
    queryOrderString: '',
    frozenEncoding: ''
  }
  $scope.pageConf = {
    totalItems: 0,
    itemsPerPage: 10,
    currentPage: 1,
    onChange: function () {
      $scope.getList();
    }
  }
  $scope.frozenList = []

  $scope.getList = function () {
    var params = _.extend({
      currentPage: $scope.pageConf.currentPage,
      pageSize: $scope.pageConf.itemsPerPage
    }, $scope.searchParams)

    erp_frozenService.query(params).$promise
      .then(function (resp) {
        if (!resp.error) {
          $scope.frozenList = resp.data
          $scope.pageConf.totalItems = resp.total
        } else {
          $uibMsgbox.error(resp.message)
        }
      }, function (resp) {
        $uibMsgbox.error('请示数据失败，错误码：' + resp.status)
      })
  }

  $scope.viewDetail = function (change) {
    erp_frozenService.queryDetail({
      change_id: change.id
    }).$promise.then(function (resp) {
      if (!resp.error) {
        showDetail(resp.data)
      } else {
        $uibMsgbox.error(resp.message)
      }
    }, function (resp) {
      $uibMsgbox.error('请求数据失败，错误码：' + resp.status)
    })
  }

  $scope.deleteChange = function (change) {
    $uibMsgbox.confirm('确定作废该订单？', function (res) {
      if (res == 'yes') {
        erp_frozenService.delete({
          change_id: change.id
        }).$promise.then(function (resp) {
          if (!resp.error) {
            $uibMsgbox.success('操作成功！')
            $scope.getList();
          } else {
            $uibMsgbox.error(resp.message)
          }
        }, function (resp) {
          $uibMsgbox.error('操作失败，错误码：' + resp.status)
        })
      }
    })
  }

  function showDetail(change) {
    $uibModal.open({
      size: 'lg',
      templateUrl: 'frozenDetailModal.html',
      resolve: {
        detail: function () {
          return change
        }
      },
      controller: ['$scope', 'detail', function ($scope, detail) {
        $scope.frozenDetail = detail
      }]
    })
  }

  $scope.getList();
}