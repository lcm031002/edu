angular.module('ework-ui').controller('erp_ordersQueueController', [
	'$rootScope',
	'$scope',
	'$log',
    '$uibModal',
    '$uibMsgbox',
    'erp_courseService',
    'erp_sortNumService',
	erp_ordersQueueController
])

function erp_ordersQueueController(
	$rootScope,
	$scope,
	$log,
    $uibModal,
    $uibMsgbox,
    erp_courseService,
	erp_sortNumService
) {
	// 界面显示配置
	$scope.view = {
		showAdvanceSearch: false
	}
    $scope.pageConf = {
        totalItems: 0,
        currentPage: 1,
        itemsPerPage: 10,
        onChange: function () {
            $scope.queryOrderQueue();
        }
    }
    $scope.course = {};
    $scope.selectedTimeSeason = {};
    $scope.selectedGrade = {};
    $scope.selectedSubject = {};
    $scope.searchParam = {
        buId: 0,
        branchId: 0,
        courseId:''
    }
	// 课程列表
	$scope.courseList = []
	$scope.queryOrderQueue = function () {
        $scope.loadStatues = true;
		erp_sortNumService.countCourseSortNum({
            pageSize: $scope.pageConf.itemsPerPage,
            currentPage: $scope.pageConf.currentPage,
            branchId: $scope.searchParam.branchId || null,
            gradeId: $scope.selectedGrade.id || null,
            subjectId: $scope.selectedSubject.id || null,
            seasonId: $scope.selectedTimeSeason.id || null,
            courseId:$scope.searchParam.courseId || null
        }, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.courseList = resp.data;
                $scope.pageConf.totalItems = resp.total;
            } else {
                $uibMsgbox.alert(resp.message)
            }
        })
    };
    
    $scope.queryOrderQueueByCourseId = function (course) {
    	erp_sortNumService.countSortNumDetail({
            courseId: course.id
        }, function (resp) {
            if (!resp.error) {
            	course.detailList = resp.data;
            } else {
                $uibMsgbox.error(resp.message)
            }
        })
    };
    
    $scope.queryOrderQueueDetail = function (course, detail) {
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/course-queue-detail.modal.html',
            controller: 'modal_courseQueueDetailController',
            resolve: {
                course: function () {
                    return course;
                },
                courseSeqDetail: function() {
                    return detail;
                }
            }
        }).result.then(function() {
            $scope.queryOrderQueueByCourseId(course);
        }, function() {
            $scope.queryOrderQueueByCourseId(course);
        })
    };
    $scope.queryCheckPeopleList = function (course, detail, orderType) {
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/course-check-detail.modal.html',
            resolve: {
                course: function () {
                    return course;
                },
                courseSeqDetail: function() {
                    return detail;
                },
                type: function () {
                  return orderType
                }
            },
            controller: ['$scope', 
              'erp_sortNumService',
              'course',
              'courseSeqDetail',
              'type',
              function (
                $scope,
                erp_sortNumService,
                course,
                courseSeqDetail,
                type
              ) {
                $scope.detailList = [];
                $scope.type = type;
                erp_sortNumService.queryCheckPeople({
                  courseId: course.id,
                  seq: courseSeqDetail.seq,
                  type: type
                }, function (resp) {
                    console.log('srs'+resp.detailList)
                  if (!resp.error) {
                    $scope.detailList = resp.data
                    console.log(resp.detailList)
                  }
                })
              }]
        }).result.then(function(){
         console.log('ss'+course)
          $scope.queryOrderQueueByCourseId(course);
        }, function() {
            console.log('sses'+course)
          $scope.queryOrderQueueByCourseId(course);
        })
    }
    $scope.showDetail = function (course) {
        course.showDetail = true;
        $scope.queryOrderQueueByCourseId(course)
    }

    $scope.hideDetail = function (course) {
        course.showDetail = false;
    }
    $scope.queryOrderQueue();
}