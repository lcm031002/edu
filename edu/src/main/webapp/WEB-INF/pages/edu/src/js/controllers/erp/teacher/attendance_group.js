/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
  .module('ework-ui')
  .controller('erp_attendanceGroupController', [
    '$scope',
    '$log',
    '$uibMsgbox',
    '$uibModal',
    'erp_studentBuOrgsService',
    'erp_teacherGroupService',
    'erp_attendanceTeacherGroupService',
    erp_attendanceGroupController
  ]);

function erp_attendanceGroupController(
  $scope,
  $log,
  $uibMsgbox,
  $uibModal,
  erp_studentBuOrgsService,
  erp_teacherGroupService,
  erp_attendanceTeacherGroupService
) {

  // 搜索字段
  $scope.searchParam = {
    teach_group_name: ''
  };
  //弹出框操作类型
  $scope.optype = 'add';
  // 考勤教师组列表
  $scope.attendTeacherGroupList = [];

  // 与表单绑定的数据，用于添加和修改
  $scope.attendTeacherGroup = {};

  // 校区列表
  $scope.branchList = [];

  /**
   * 分页配置
   * @param  {Number} currentPage     [当前页面，初始化时默认为1]
   * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
   * @param  {Number} itemsPerPage    [每页显示条数]
   * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
   * @param  {Array}  perPageOptions  [可选，默认]
   * @param  {Function} perPageOptions [description]
   */
  $scope.paginationConf = {
    currentPage: 1, //当前页
    totalItems: 0,
    onChange: function() {
      $scope.query();
    }
  }

  $scope.paginationBars = [];

  // 处理【添加考勤教师组】按钮点击事件
  $scope.handleAddAttendTeacherGroup = function() {
    $scope.optype = 'add';
    openAttendTeacherGroup("添加");
  }

  // 处理【修改考勤教师组】按钮点击事件
  $scope.handlePutAttendTeacherGroup = function(rowData) {
    $scope.optype = 'put';
    erp_attendanceTeacherGroupService.queryDetail({"p_group_id":rowData.id},//通过教师组id查询教师组详细信息
        function(resp) {
          if (!resp.error) {
            angular.copy(resp.data,$scope.attendTeacherGroup);
            openAttendTeacherGroup("修改");
          } else {
            $uibMsgbox.error(resp.message);
          }
        });
  }

  // 打开对话框
  function openAttendTeacherGroup(optype) {
    $uibModal.open({
      resolve: {
        optype: function() {return optype},
        attendTeacherGroup: function() {return $scope.attendTeacherGroup},
        branchList: function () {return $scope.branchList}
      },
      backdrop: 'static',
      templateUrl: 'templates/block/modal/teacher-attendance-group.modal.html',
      controller: 'erp_attendTeacherGroupModalController'
    }).result.then(function(attendTeacherGroup) {
      if ($scope.optype == 'add') {
        $scope.add(attendTeacherGroup);
      } else {
        $scope.put(attendTeacherGroup);
      }
    }, function() {})
  }

  // 处理【删除考勤教师组】按钮点击事件
  $scope.handleDeleteAttendTeacherGroup = function(id) {
    if (window.confirm('确定删除选中考勤教师组？')) {
      $scope.remove(id);
    }
  }

  // 处理【查询考勤教师组】按钮点击事件
  $scope.handleQueryAttendTeacherGroup = function() {
    $scope.query();
  }

  $scope.checkBeforeSave = function() {
    if (!$scope.attendTeacherGroup.teach_group_name) {
      $uibMsgbox.error("考勤教师组名称必填");
      return false;
    }

    if (!$scope.attendTeacherGroup.bu_id) {
      $uibMsgbox.error("团队必填");
      return false;
    }
  }

  // 查询考勤教师组
  $scope.query = function() {
    
    erp_attendanceTeacherGroupService.query({
        pageSize: $scope.paginationConf.itemsPerPage,
        currentPage: $scope.paginationConf.currentPage,
        p_teach_group_name: $scope.searchParam.teach_group_name,
      },
      function(resp) {
        if (!resp.error) {
          $scope.attendTeacherGroupList = resp.data;
          $scope.paginationConf.totalItems = resp.total || 0;
        } else {
          $uibMsgbox.error(resp.message);
        }
      });
  }

  // 添加考勤教师组
  $scope.add = function(attendTeacherGroup) {
    $scope.attendTeacherGroup = {};
    erp_attendanceTeacherGroupService.post(attendTeacherGroup, function(resp) {
      if (!resp.error) {
        $uibMsgbox.success("添加成功");
        $scope.query();
        $('#erpAttendTeacherGroupPanel').modal('hide');
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  // 修改考勤教师组
  $scope.put = function(attendTeacherGroup) {
    erp_attendanceTeacherGroupService.put(attendTeacherGroup , function(resp) {
      $scope.attendTeacherGroup = {};
      if (!resp.error) {
        $uibMsgbox.success("修改成功");
        $scope.query();
        $('#erpAttendTeacherGroupPanel').modal('hide');
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  // 删除考勤教师组
  $scope.remove = function(id) {
    erp_attendanceTeacherGroupService.remove({ "ids": id }, function(resp) {
      if (!resp.error) {
        $uibMsgbox.success("删除成功");
        $scope.query();
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  $scope.initialize = function() {
    $scope.query();
    
    erp_studentBuOrgsService.query({}, function(resp) {
      if (!resp.error) {
        $scope.branchList = resp.data;
      }
    });
  }

  $scope.initialize();
}
