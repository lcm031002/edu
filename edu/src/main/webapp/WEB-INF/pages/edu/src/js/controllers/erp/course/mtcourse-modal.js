"use strict"
angular.module('ework-ui')
  .controller('erp_mtCourseModalController', [
    '$rootScope',
    '$scope',
    'erp_mtcourseService',
    '$uibModalInstance',
    '$uibMsgbox',
    'uibDateParser',
    erp_mtCourseModalController
  ])

function erp_mtCourseModalController(
  $rootScope,
  $scope,
  erp_mtcourseService,
  $uibModalInstance,
  $uibMsgbox,
  uibDateParser
) {
  $scope.modalCourseList = [];

   if (!$scope.courseDetail.type) {
     $scope.courseDetail.type = 4;
   }

  $scope.$watch('courseDetail.start_time', function () {
    calculateHourLength()
  })
  $scope.insertCourse = function (course) {
    function insertCourse (course) {
      if (!_.some($scope.modalCourseList, {id: course.id})) {
        if ($scope.modalCourseList.length > 0) {
          var _course = $scope.modalCourseList[0]
          if (_course.start_date != course.start_date ||
            _course.end_date != course.end_date ||
            _course.start_time != course.start_time ||
            _course.end_time != course.end_time ||
            _course.course_count != course.course_count ) {
            $uibMsgbox.error('请确保选择的课程的开课日期、结课日期、上课时间、下课时间、课次 均保持一致！')
            return false;
          }
        }
        $scope.modalCourseList.push(course);
          $scope.courseDetail.start_date = new Date(course.start_date);
          $scope.courseDetail.end_date = new Date(course.end_date);
          $scope.courseDetail.start_time = course.start_time;
          $scope.courseDetail.end_time = course.end_time;
          $scope.courseDetail.attend_class_period = course.attend_class_period;

          if (!course.hour_len) {
            calculateHourLength();
          } else {
            $scope.courseDetail.hour_len = course.hour_len;
          }

          $scope.courseDetail.branch_id = course.branch_id;
          $scope.courseDetail.unit_price = course.unit_price;
          $scope.courseDetail.course_count = course.course_count;
          $scope.courseDetail.description = course.description;
      }
    }

    if (course.is_textbooks == 1) {
      $uibMsgbox.error("教材不能加入双师包！");
      return false;
    }

    if (course.more_teacher_courseid && course.more_teacher_courseid != $scope.courseDetail.id) {
      $uibMsgbox.confirm('您添加的班级课程已经在 另外一个双师课程档案('+course.more_teacher_courseName+')已存在，如果继续，对方将消失该班级课，是否继续？', function (res) {
        if (res == 'yes') {
          insertCourse(course)
        }
      })
    } else {
      insertCourse(course)
    }
  }

  $scope.removeCourse = function (course) {
    _.remove($scope.modalCourseList, {id: course.id})
  }

  $scope.$watch('courseDetail.end_time', function() {
    calculateHourLength()
  })
  $scope.handleModalCancel = function() {
    $uibModalInstance.dismiss('cancel')
  }
  $scope.handleModalConfirm = function() {
     var mainCourseCount = 0;
     $.each($scope.modalCourseList, function(idx, course) {
       if (course.teacher_id == $scope.courseDetail.teacher_id) {
         mainCourseCount += 1;
       }
     });

     if (mainCourseCount == 0 || mainCourseCount > 1) {
       $uibMsgbox.error("所选课程老师必须有且只有一个与双师包的老师一致！");
       return false;
     }

	$scope.courseDetail.courseRel=$scope.modalCourseList;
    $uibModalInstance.close($scope.courseDetail);
  }
  $scope.setMtCourseTeacher = function (teacher) {
    $scope.courseDetail.teacher_name = teacher.teacher_name;
    $scope.courseDetail.teacher_id = teacher.id;
    console.log($scope.courseDetail)
  }
  function calculateHourLength() {
    var start = uibDateParser.parse($scope.courseDetail.start_time, 'HH:mm')
    var end = uibDateParser.parse($scope.courseDetail.end_time, 'HH:mm')
    if (start && end && start.getTime && end.getTime) {
      $scope.courseDetail.hour_len = ((end.getTime() - start.getTime()) / 1000 / 60 / 60).toFixed(2)
    } else {
      $scope.courseDetail.hour_len = 0
    }
  }
  
  $scope.selectedCourseList = function () {
      if(!$scope.courseDetail.id) {
        return;
      }
	  erp_mtcourseService.selectedCourseList({"id":$scope.courseDetail.id}, function (resp) {
  		if(!resp.error){
  			$scope.modalCourseList=resp.selectedCourList;
           }else{
           	$uibMsgbox.error(resp.message);
           }
      });
  };
  
  $scope.selectedCourseList();
}
