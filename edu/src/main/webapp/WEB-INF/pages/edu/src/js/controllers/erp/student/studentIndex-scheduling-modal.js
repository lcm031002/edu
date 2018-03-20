"use strict"
angular.module('ework-ui').controller('erp_studentIndexSheduleModalController', [
    '$rootScope',
    '$scope',
    '$uibModalInstance',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    'erp_studentOrderCourseService',
    'scheduleDetail',
    'studentId',
    'optype',
    erp_studentIndexSheduleModalController
  ])

function erp_studentIndexSheduleModalController(
  $rootScope,
  $scope,
  $uibModalInstance,
  $uibMsgbox,
  erp_studentOrderCourseService,
  scheduleDetail,
  studentId,
  optype
) {
  $scope.studentId = studentId
  $scope.paginationConf = {
      currentPage: 1, //当前页
      totalItems: 0,
      itemsPerPage: 10,
      onChange: function(){
          $scope.queryStudentOrderCourse();
      }
  };
  $scope.optype = optype;
  $scope.scheduleDetail = scheduleDetail;
  $scope.tableConf = {
    checkAllFlag: false
  }
  $scope.orderCourseList=[];
  $scope.attendTime={};
  $scope.attendPeriod={};
  $scope.orderList=[];
  $scope.schedule_type ='time';
  $scope.selectScheduleType = function(schedule_type){
        $scope.schedule_type = schedule_type;
        return true;
    }

  $scope.handleModalCancel = function() {
    $uibModalInstance.dismiss('cancel');
  }
  $scope.handleModalConfirm = function() {
    $scope.scheduleDetail.orderCourseIds=getSelectedIds();
    if($scope.scheduleDetail.orderCourseIds.length==0){
      $uibMsgbox.alert('请选择要操作的课程！');
      return;
    }
    $scope.scheduleDetail.scheduleType=$scope.schedule_type;
    if($scope.schedule_type =='time'){
      $scope.scheduleDetail.startDate=$scope.attendTime.start_date;
      $scope.scheduleDetail.start_time=$scope.attendTime.start_time;
      $scope.scheduleDetail.end_time=$scope.attendTime.end_time;
      $scope.scheduleDetail.subject_id=$scope.attendTime.subject.id;
    }else{
      $scope.scheduleDetail.attendClassPeriod=$scope.attendPeriod.period;
      $scope.scheduleDetail.courseScheduleCount=$scope.attendPeriod.hour_len;
      $scope.scheduleDetail.startDate=$scope.attendPeriod.start_date;
      $scope.scheduleDetail.endDate=$scope.attendPeriod.end_date;
      $scope.scheduleDetail.start_time=$scope.attendPeriod.start_time;
      $scope.scheduleDetail.end_time=$scope.attendPeriod.end_time;
      $scope.scheduleDetail.subject_id=$scope.attendPeriod.subject.id;
    }
    $uibModalInstance.close($scope.scheduleDetail);
  }

  function getSelectedCourseList () {
      var orderList = [];
      _.forEach($scope.orderCourseList ,function(course) {
          if (course.selectFlag) {
            orderList.push(course);
          }
      })
      return orderList;
  }

  //获取所有选中的id
  function getSelectedIds () {
      var ids = "";
      _.forEach($scope.orderCourseList ,function(course) {
          if (course.selectFlag) {
            ids+= "," + course.id;
          }
      })
      return ids.substring(1);
  }

  // 全选事件
  $scope.onCheckAll = function () {
      console.info($scope.tableConf.checkAllFlag)
      _.forEach($scope.orderCourseList ,function(course) {
          course.selectFlag = $scope.tableConf.checkAllFlag;
      })
  }

  // 某一行Checkbox选择事件
  $scope.onCourseChecked = function (course) {
      $scope.tableConf.checkAllFlag= _.every($scope.orderCourseList, {selectFlag: true});
  }

  $scope.insertLeader = function(teacher) {
        $scope.scheduleDetail.teacher_name = teacher.teacher_name;
        $scope.scheduleDetail.teacher_id = teacher.id;
  }
  // 查询方法
  $scope.queryStudentOrderCourse = function () {
    erp_studentOrderCourseService.queryOrderCourse({
          pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
          currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
          studentId: $scope.studentId,
          businessType:2
      }, function (resp) {
          if (!resp.error) {
              $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
              $scope.orderCourseList = resp.data.sort(function (a, b) {
                return a.create_time > b.create_time
              })
              _.forEach($scope.orderCourseList, function (item) {
                item.selectFlag = false
              })
          } else {
            $uibMsgbox.error(resp.message);
          }
      });
  };

  $scope.queryStudentOrderCourse();
}
