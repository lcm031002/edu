/**
 * Created by Liyong.zhu on 2017/2/18.
 */
"use strict";
angular.module('ework-ui').controller(
		'erp_attendanceBJKMakeupController',
		[ '$scope', 
		  '$log',
			'$uibMsgbox',
		  'erp_attendanceCourseTimesService',
		   erp_attendanceBJKMakeupController ]);

function erp_attendanceBJKMakeupController($scope, $log, $uibMsgbox,
		erp_attendanceCourseTimesService) {
	$scope.searchParam = {
			start_date:"",
			end_date:""
	};
	function queryMakeUp() {
		
		if(!checkStartEndTime($scope.searchParam.start_date,$scope.searchParam.end_date)){
			$uibMsgbox.alert('截止日期必须大于或等于开始日期');
			return;
		}
		var param = {};
		param.startDate = $scope.searchParam.start_date;
		param.endDate = $scope.searchParam.end_date;
		if (!$scope.studentId) {
			// studentId不存在，根据courseId和courseTime查询补课信息
			param.courseId = $scope.courseId;
			param.courseTime = $scope.courseTime;
		} else {
			param.studentId = $scope.studentId;
		}
		$scope.isQueryCourseTimeMakeup = 'isQueryCourseTimeMakeup';// 正在查询标识
		erp_attendanceCourseTimesService.makeup(param, function(resp) {
			$scope.isQueryCourseTimeMakeup = '';// 查询结束
			if (!resp.error) {
				$scope.courseTimeMakeupList = resp.data;
			} else {
				$uibMsgbox.alert(resp.message);
			}
		});
	}

	$scope.queryInfo = function() {
		queryMakeUp();
	}
	
	

	function initial() {
		$scope.courseId = $("#rootIndex_courseId").val();
		$scope.courseTime = $("#rootIndex_courseTime").val();
		$scope.studentId = $("#rootIndex_studentId").val();
		if (!$scope.studentId) {
			// 不存在 studentId
			$('title').text("补课");
		} else {
			$('title').text("我的补课");
		}
	}
	initial();
}