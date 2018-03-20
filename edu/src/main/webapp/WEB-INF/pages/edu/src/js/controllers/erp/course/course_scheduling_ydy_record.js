/**
 * 1对1排课记录
 * Created By baiqb@klxuexi.org 2017-08-11
 *
 */
angular.module('ework-ui').controller('erp_courseSchedulingYdyRecordController',
    [
      '$scope',
      '$state',
      '$uibMsgbox',
      'erp_dictService',
      'erp_StudentManagerService',
      erp_courseSchedulingYdyRecordController
    ])

function erp_courseSchedulingYdyRecordController($scope,
    $state,
    $uibMsgbox,
    erp_dictService,
    erp_StudentManagerService) {

  $scope.courseSchedInfoList = [];

  $scope.pageConf = {
    currentPage: 1,
    totalItems: 0,
    itemsPerPage: 10,
    onChange: function () {
      $scope.queryCourseSchedInfo();
    }
  }

  $scope.searchParam = {
    range: 'curMonth',
    p_startDate: moment().startOf('month').format('YYYY-MM-DD'),
    p_endDate: moment().endOf('month').format('YYYY-MM-DD'),
    labelName : '上课日期'
  };

  $scope.subAttendTypeList = [];
  $scope.subAttendTypeAll = [];
  $scope.attendTypeList = [{
    "code" : 28,
    "name" : "未考勤"
  }, {
    "code" : 21,
    "name" : "正常上课"
  }, {
    "code" : 23,
    "name" : "排课取消"
  }, {
    "code" : 29,
    "name" : "考勤作废"
  }];

  $scope.querySubAttendTypeList = function() {
    erp_dictService.query({"code" : "ydyAttendType"}, function(resp) {
      if (!resp.error) {
        $scope.subAttendTypeAll = resp.data;
        $scope.subAttendTypeList = $scope.subAttendTypeAll;
      }
    });
  }
  //考勤状态和考勤类型联动
  $scope.changeType = function(){
    var type =$scope.searchParam.p_attendType;
    var arr=$scope.subAttendTypeAll;
      if(type==null){
        $scope.subAttendTypeList = arr;
      }else if(type==28){
        $scope.subAttendTypeList = []
      }else if(type==21){
        $scope.subAttendTypeList = [arr[0],arr[1]]
      }else{
        $scope.subAttendTypeList = arr.filter(obj => obj.code !== "21").filter(obj => obj.code !== "22");
      }
  }
  $scope.queryCourseSchedInfo = function () {
    $scope.loadStatues = true;
    $scope.searchParam.pageSize = $scope.pageConf.itemsPerPage;
    $scope.searchParam.currentPage = $scope.pageConf.currentPage;
    erp_StudentManagerService.queryCourseSchedInfo($scope.searchParam, function(resp) {
      $scope.loadStatues = false;
      if (!resp.error) {
        $scope.courseSchedInfoList = resp.data;
        $scope.pageConf.totalItems = resp.total || 0;
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  $scope.exportCourseSchedInfo = function() {
        erp_StudentManagerService.exportCourseSchedInfo($scope.searchParam, function(resp) {
          if (!resp.error) {
            window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
          } else {
            $uibMsgbox.error(resp.message);
          }
        });
    }

  $scope.querySubAttendTypeList();
  $scope.queryCourseSchedInfo();
}