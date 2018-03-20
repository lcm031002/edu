"use strict"
angular.module('ework-ui').controller('erp_studentIndexSheduleEditModalController', [
    '$rootScope',
    '$scope',
    '$uibModalInstance',
    'scheduleDetail',
    'studentId',
    'disabled',
    '$uibMsgbox',
    'erp_studentCourseSchedulingService',
    erp_studentIndexSheduleEditModalController
  ])

function erp_studentIndexSheduleEditModalController(
  $rootScope,
  $scope,
  $uibModalInstance,
  scheduleDetail,
  studentId,
  disabled,
  $uibMsgbox,
  erp_studentCourseSchedulingService
) {
  var deltaTime = moment(scheduleDetail.end_time, 'hh:mm') - moment(scheduleDetail.start_time, 'hh:mm')
  $scope.scheduleDetail = scheduleDetail;
  $scope.disabled = disabled;
  scheduleDetail.teacher_name=scheduleDetail.attTeacherName;
  $scope.handleModalCancel = function() {
    $uibModalInstance.dismiss('cancel')
  }
  $scope.$watch('scheduleDetail.start_time', function (newValue, oldValue) {
    if (newValue != oldValue) {
      $scope.scheduleDetail.end_time = moment(newValue, 'HH:mm').add(deltaTime).format('HH:mm')
    }
  })
  $scope.$watch('scheduleDetail.end_time', function (newValue, oldValue) {
    if (newValue != oldValue) {
      $scope.scheduleDetail.start_time = moment(newValue, 'HH:mm').subtract(deltaTime).format('HH:mm')
    }
  })
  $scope.handleModalConfirm = function() {
    var waitingModal = $uibMsgbox.waiting('保存中，请稍候...')
    erp_studentCourseSchedulingService.updateYdyOrderCourseScheduling({
      "attend_type": $scope.scheduleDetail.attend_type,
      "id": $scope.scheduleDetail.id,
      "attTeacherName": $scope.scheduleDetail.attTeacherName,
      "teacher_id": $scope.scheduleDetail.teacher_id,
      "subject_id": $scope.scheduleDetail.subject_id,
      "course_date": moment($scope.scheduleDetail.course_date).format('YYYYMMDD'),
      "start_time": $scope.scheduleDetail.start_time,
      "student_id": studentId,
      "end_time": $scope.scheduleDetail.end_time
    }, function (resp) {
      waitingModal.close()
      if (!resp.error) {
          $uibMsgbox.success(resp.message);
          $uibModalInstance.close()
      } else {
          $uibMsgbox.error(resp.message);
      }
    });
  }
  $scope.insertLeader = function(teacher) {
        $scope.scheduleDetail.teacher_name = teacher.teacher_name;
        $scope.scheduleDetail.teacher_id = teacher.id;
  }
}
