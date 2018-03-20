"use strict";
angular.module('ework-ui').controller('erp_InvoiceTraceController', [
  '$rootScope',
  '$scope',
  '$uibMsgbox',
  'erp_InvoiceManagerService',
  'erp_invoiceTraceService',
  erp_InvoiceTraceController
]);

function erp_InvoiceTraceController(
   $rootScope,
   $scope,
    $uibMsgbox,
    erp_InvoiceManagerService,
    erp_invoiceTraceService
) {
  // 发票信息
  $scope.invoiceList = [];

  $scope.paginationConf = {
    currentPage: 1, // 当前页
    totalItems: 0,
    onChange: function() {
      $scope.query();
    }
  };
  $scope.paginationBars = [];

  $scope.searchParam = {
    p_receiveStatus: 0
  }

  $scope.operateType = "receive";

  $scope.studentId = $("#rootIndex_studentId").val();

  $scope.handleChangeStatus = function(receiveStatus) {
    $scope.searchParam.p_receiveStatus = receiveStatus;
    $scope.query();
  }

  $scope.query = function() {
    $scope.isDown = 'loading';

    $scope.searchParam.pageSize = $scope.paginationConf.itemsPerPage;
    $scope.searchParam.currentPage = $scope.paginationConf.currentPage;
    $scope.searchParam.p_studentId = $scope.studentId;
    $scope.searchParam.p_receiveStatus = $scope.searchParam.p_receiveStatus == 0 ? '' : $scope.searchParam.p_receiveStatus;

    erp_InvoiceManagerService.query($scope.searchParam, function(resp) {
      $scope.isDown = '';
      if (!resp.error) {
        $scope.invoiceList = resp.data;
        $scope.paginationConf.totalItems = resp.total || 0;
      } else {
          $uibMsgbox.error(resp.message);
      }
    });
  }

  /**
   * @param invoiceReceiveLog 发票领取记录对象
   * 参数invoiceId ：发票ID
   * 参数status ：领取状态 2-已领取 3-领取作废
   * 参数remark ：备注
   */
  $scope.invoiceReceiveLog = {};
  $scope.receiveInvoice = function() {
    console.log($scope.invoiceReceiveLog.receiverCode)
    if (!$scope.invoiceReceiveLog.receiverCode && $scope.invoiceReceiveLog.status == 2) {
      $uibMsgbox.error('请选择领取人！');
      return false;
    }
    erp_invoiceTraceService.receiveInvoice($scope.invoiceReceiveLog, function(resp) {
      if (!resp.error) {
        $("#invoiceInvalid").modal('hide');
        $uibMsgbox.alert($scope.invoiceReceiveLog.status == 3 ? "领取作废成功" : "领取成功");
        $scope.query();
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  $scope.invoiceReceiveList = [];
  $scope.viewReceiveLog = function(invoiceId) {
    erp_invoiceTraceService.queryList({invoiceId : invoiceId}, function(resp) {
      console.log(resp)
      if (!resp.error) {
        $scope.invoiceReceiveList = resp.data;
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  $scope.query();

  //显示领取状态记录框
  $scope.viewLog = function (item) {
    $("#viewInvoice").modal('show');
    $scope.viewReceiveLog(item);
  };
  //显示领取状态框
  $scope.openDialog = function (operateType, invoiceId) {
    $scope.operateType = operateType;
    $scope.invoiceReceiveLog.invoiceId = invoiceId;
    $scope.invoiceReceiveLog.status = operateType == 'receive' ? 2 : 3;
    $("#invoiceInvalid").modal('show');
  };
}
