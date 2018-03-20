/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular.module('ework-ui').controller('erp_orderFrozenMgrController', [
  '$scope',
  '$log',
  '$uibModal',
  '$uibMsgbox',
  'erp_orderFrozenMgrService',
  erp_orderFrozenMgrController
]);

function erp_orderFrozenMgrController(
  $scope,
  $log,
  $uibModal,
  $uibMsgbox,
  erp_orderFrozenMgrService
) {
  // 搜索字段
  $scope.searchParam = {
    status: -1, // 状态
    start_date: getCurrentDate(),
    end_date: getCurrentDate()
  };
  // 列表
  $scope.dataList = undefined;

  // 团队、校区查询条件改变
  $scope.onBranchChange = function() {
    //$scope.dataList = [];
    $scope.onDateChange();
  };

  // 起止日期改变
  $scope.onDateChange = function() {
    if (!$scope.searchParam.bu_id || $scope.searchParam.bu_id == -1) {
      return;
    }
    if (!$scope.searchParam.start_date) {
      return;
    }
    if (!$scope.searchParam.end_date) {
      return;
    }
    if (!checkStartEndTime($scope.searchParam.start_date, $scope.searchParam.end_date)) {
      return;
    }

    $scope.query();
  };

  //批量选中标识
  $scope.selectAllFlag = false;

  //多选监控
  $scope.$watch('selectAllFlag', function(newValue) {
    _.each($scope.dataList, function(item) {
      item.selectItemFlag = newValue == true ? true : false;
    });
  });
  //获取所有选中的id
  $scope.getSelectedOrderIds = function() {
    var orderIds = [];
    _.each($scope.dataList, function(item) {
      if (item.selectItemFlag == true && item.status == 1) {
        orderIds.push(item.orderId);
      }
    });
    return orderIds;
  };

  $scope.beforeQuery = function() {
    if (!$scope.searchParam.bu_id || $scope.searchParam.bu_id == -1) {
      $uibMsgbox.error("请选择团队");
      return false;
    }
    if (!$scope.searchParam.start_date) {
      $uibMsgbox.error("请选择开始日期");
      return false;
    }
    if (!$scope.searchParam.end_date) {
      $uibMsgbox.error("请选择截止日期");
      return false;
    }
    if (!checkStartEndTime($scope.searchParam.start_date, $scope.searchParam.end_date)) {
      $uibMsgbox.alert('截止日期必须大于或等于开始日期');
      return false;
    }
    return true;
  };

  // 查询
  $scope.query = function() {
    if ($scope.beforeQuery()) {
      erp_orderFrozenMgrService.query({
        p_bu_id: $scope.searchParam.bu_id,
        p_branch_id: $scope.searchParam.branch_id,
        p_start_date: $scope.searchParam.start_date,
        p_end_date: $scope.searchParam.end_date,
        p_status: $scope.searchParam.status
      }, function(resp) {
        if (!resp.error) {
          $scope.dataList = resp.data;
        } else {
          $uibMsgbox.error(resp.message);
        }
      });
    }
  };

  $scope.order_ids = null;

  // 选择行 结转
  $scope.selectedRowCarryForward = function() {
    var orderIds = $scope.getSelectedOrderIds();
    if (orderIds.length < 1) {
      $uibMsgbox.alert('请先选定要结转的数据行！');
      return;
    }
    $scope.carryForward(orderIds);
  };

  // 单行 结转
  $scope.oneRowCarryForward = function(data) {
    $scope.carryForward([data.orderId]);
  };

  // 结转
  $scope.carryForward = function(order_ids) {
    $uibModal.open({
      size: 'lg',
      templateUrl: 'templates/block/modal/order_frozen_mgr-carryForward.modal.html',
      controller: 'erp_orderFrozenMgrCarryForwardModalController',
      scope: $scope
    }).result.then(function(resp) {
      var param = {
        order_ids: order_ids,
        remark: resp.remark
      };
      erp_orderFrozenMgrService.carryForward(param, function(resp) {
        if (!resp.error) {
          $uibMsgbox.success('结转成功！');
          $scope.query();
        } else {
          $uibMsgbox.error(resp.message);
        }
      });
    }, function() {});
  };

  // 导出数据
  $scope.exportExcel = function() {
    if ($scope.beforeQuery()) {
      erp_orderFrozenMgrService.exportExcel({
        p_bu_id: $scope.searchParam.bu_id,
        p_branch_id: $scope.searchParam.branch_id,
        p_start_date: $scope.searchParam.start_date,
        p_end_date: $scope.searchParam.end_date,
        p_status: $scope.searchParam.status
      }, function(resp) {
        if (!resp.error) {
          window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
        } else {
          $uibMsgbox.error(resp.message);
        }
      });
    }
  };

  $scope.init = function() {
    // 状态
    $scope.statusList = [
      { "id": -1, "name": "全部" },
      { "id": 1, "name": "锁定中" },
      { "id": 2, "name": "已结转" }
    ];
    if (!$scope.dataList) {
      $scope.onBranchChange();
    }
  };

  $scope.init();
}
