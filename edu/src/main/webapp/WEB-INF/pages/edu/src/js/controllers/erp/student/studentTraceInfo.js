/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular.module('ework-ui').controller('erp_StudentTraceInfoController', [
  '$rootScope',
  '$scope',
  '$uibMsgbox',
  'erp_studentTraceInfoService',
  erp_StudentTraceInfoController
]);

function erp_StudentTraceInfoController(
  $rootScope,
  $scope,
  $uibMsgbox,
  erp_studentTraceInfoService
) {
  // 学员信息
  $scope.studentTraceInfoList = [];
  /**
   * 分页配置
   * 
   * @param {Number}
   *            currentPage [当前页面，初始化时默认为1]
   * @param {Number}
   *            totalItems [数据总条数，每次查询时赋值]
   * @param {Number}
   *            itemsPerPage [每页显示条数]
   * @param {Number}
   *            pagesLength [可选，分页栏长度,默认为9]
   * @param {Array}
   *            perPageOptions [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
   * @param {Function}
   *            onChange [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
   */
  $scope.paginationConf = {
    currentPage: 1, // 当前页
    totalItems: 0,
    onChange: function() {
      $scope.query()
    }
  };

  $scope.studentId = $("#rootIndex_studentId").val();

  $scope.paginationBars = [];
  // 表单操作类型，添加： add，修改：put
  $scope.optype = 'add';

  $scope.query = function() {
    $scope.studentTraceInfoList = [];
    $scope.isDown = 'loading';
    erp_studentTraceInfoService.query({
        pageSize: $scope.paginationConf.itemsPerPage,
        currentPage: $scope.paginationConf.currentPage,
        p_student_id: $scope.studentId
    }, function(resp) {
      $scope.isDown = '';
      if (!resp.error) {
        $scope.studentTraceInfoList = resp.data;
        $scope.paginationConf.totalItems = resp.total || 0;
      } else {
          $uibMsgbox.error(resp.message);
      }
    });
  }

  // 处理【添加】按钮点击事件
  $scope.handleAddTraceInfo = function() {
    $scope.optype = 'add';
    $('#erpStudentTraceInfoPanel').modal('show');
    $scope.newStudentTraceInfo = {
      student_id: $scope.studentId
    };
  }

  // 处理【取消】按钮点击事件
  $scope.handleModalCancel = function() {
    $('#erpStudentTraceInfoPanel').modal('hide');
  }

  // 处理【确认】按钮点击事件
  $scope.handleModalConfirm = function() {
    if ($scope.optype == 'add') {
      // 添加的内容
      $scope.add();
      $scope.query();
    } else if ($scope.optype == 'put') {
      $scope.put();
    }
    $('#erpStudentTraceInfoPanel').modal('hide');
  }

  // 添加
  $scope.add = function() {
    erp_studentTraceInfoService.add($scope.newStudentTraceInfo, function(resp) {
      if (!resp.error) {
        $uibMsgbox.success("添加成功");
        $scope.query();
        $('#erpSystemDictTimeSeasonPanel').modal('hide');
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  $scope.addStudentTrace = function() {
    location.href = "?studentId=" + $scope.studentId + "#/studentMgr/studentTraceInfoAdd";
  }

    $scope.searchParam = {
        p_studentId : $scope.studentId
    }
  $scope.exportCourseInfo = function() {
      var params = _.cloneDeep($scope.searchParam);
      var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
      erp_studentTraceInfoService.exportCourseInfo(params, function(resp) {
          _uibModalInstance.close();
          if (!resp.error) {
              if(resp.data){
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
              }else{
                $uibMsgbox.confirm("课堂反馈详情为空");
              }
          } else {
              $uibMsgbox.error(resp.message);
          }
      });
  }

  $scope.query();
}
