"use strict"
angular.module('ework-ui')
  .controller('erp_courseModalController', [
    '$rootScope',
    '$scope',
    '$uibModalInstance',
    '$uibMsgbox',
    'saveCourse',
    'erp_courseService',
    erp_courseModalController
  ])

function erp_courseModalController(
  $rootScope,
  $scope,
  $uibModalInstance,
  $uibMsgbox,
  saveCourse,
  erp_courseService
) {

  $scope.isMtCourse = $scope.courseDetail.lecturer_id && ($scope.courseDetail.more_teacher_course_type == '4');
  $scope.isMainMtCourse = $scope.isMtCourse && ($scope.courseDetail.teacher_id == $scope.courseDetail.lecturer_id);

  $scope.handleModalCancel = function() {
    $uibModalInstance.dismiss('cancel')
  }

$scope.setCourseTeacher = function (teacher) {
    $scope.courseDetail.assteacher_name = teacher.teacher_name;
    $scope.courseDetail.assteacher_id = teacher.id;
  };

    // 移除辅导老师-培英
   $scope.removeAssTeacher = function () {
       $scope.courseDetail.assteacher_name = null;
       $scope.courseDetail.assteacher_id = null;
   };

    //教材选择是后选择自动添加计划人数999999
    $scope.changepeoplecount=function(){
         if($scope.courseDetail.is_textbooks==1) {
             $scope.courseDetail.people_count = 999999;
    }else if($scope.courseDetail.is_textbooks==0){
             $scope.courseDetail.people_count = $scope.courseDetail.people_count;
         }
    };

     $scope.queryEndDate=function(){
         if($scope.courseDetail.start_date&&$scope.courseDetail.attend_class_period
          &&$scope.courseDetail.course_count) {
            erp_courseService.queryEndTimesByPeriod({
              start_date : $scope.courseDetail.start_date,
              attend_class_period:$scope.courseDetail.attend_class_period,
              course_count:$scope.courseDetail.course_count
            }, function (resp) {
                if (!resp.error) {
                    $scope.courseDetail.end_date=moment(resp.end_date).format('YYYY-MM-DD');
                }else{
                    $uibMsgbox.error(resp.message);
                }
            });
         }
    };

    $scope.queryHourLen=function(){
        if($scope.courseDetail.start_time && $scope.courseDetail.end_time){
            erp_courseService.queryHourLen({
                start_time: $scope.courseDetail.start_time,
                end_time: $scope.courseDetail.end_time
            },function(resp){
                if (!resp.error) {
                    $scope.courseDetail.hour_len=resp.hour_len;
                }else{
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    };

  $scope.handleModalConfirm = function() {
      if(!$scope.courseDetail.attend_class_period) {
          $uibMsgbox.error("上课周期必填！");
          return;
      }
      else if(!$scope.courseDetail.people_count){
          $uibMsgbox.error("计划人数必填！");
          return;
      }
      else if($scope.courseDetail.hour_len<0){
          $uibMsgbox.error("课时长度必须大于0！");
          return;
      }
      else if($scope.courseDetail.unit_price < 0){
          $uibMsgbox.error("课程单价不允许为负数");
          return;
      }
      else {
          var rule =/^([1-7](~\d{2}:\d{2}-\d{2}:\d{2})?,)*([1-7](~\d{2}:\d{2}-\d{2}:\d{2})?)$/;
          var re = new RegExp(rule);
          if (!re.test($scope.courseDetail.attend_class_period)) {
              $uibMsgbox.error("上课周期格式错误！<br>例1：周一、周三，请输入： 1,3; <br>例2：周一 08:00-12:00、周三，请输入：1~08:00-12:00,3");
              return;
          }
      }
    var _watingModal = $uibMsgbox.waiting('保存中，请稍候...')
    saveCourse($scope.courseDetail).$promise.then(function(resp) {
      _watingModal.close();
      if (!resp.error) {
        $uibModalInstance.close($scope.courseDetail)
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }
}
