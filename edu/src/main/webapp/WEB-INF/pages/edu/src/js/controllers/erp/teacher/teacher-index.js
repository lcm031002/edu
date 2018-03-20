"use strict";
angular.module('ework-ui').controller(
		'erp_TeacherIndexController',
		[ '$rootScope', '$scope', 'erp_TeacherIndexService', '$state', '$log',
				 erp_TeacherIndexController ]);
function erp_TeacherIndexController($rootScope, $scope,
		erp_TeacherIndexService, $state, $log) {
	$scope.teacherDetail = {};
	
	$scope.editStatus = true;
	
	$scope.statusSelect = [
	                {desc : "正式员工", status : 1},
	                {desc : "试用员工", status : 2},
	                {desc : "返聘", status : 3},
	                {desc : "辞职", status : 4},
	                {desc : "解聘", status : 5}
	            ];
	$scope.sexSelect = [
	   	                {desc : "男", sex : 1},
	   	                {desc : "女", sex : 2}
	   	            ];
    $scope.teacherId = $("#rootIndex_teacherId").val();

	
	$scope.editTeacherInfo = function() {
		$scope.editStatus = false;
	};
	
	$scope.updateTeacherInfo = function() {
		$scope.editStatus = true;
		$log.log($scope.teacherDetail);
		$scope.teacherDetail.create_time = null;
		$scope.teacherDetail.update_time = null;
		erp_TeacherIndexService.put($scope.teacherDetail,
                function(resp){
					if(!resp.error){
							$scope.teacherIndex();
					}
                }
		);
	};

	$scope.teacherIndex = function() {
		$scope.teacherDetail = {};
		$scope.isDown = 'loading';
		erp_TeacherIndexService.query({
			teacherId : $scope.teacherId
		}, function(resp) {
			$scope.isDown = '';
			if (!resp.error) {
				$scope.teacherDetail = resp.data;
			} else {
				alert(resp.message);
			}
		});
	};
	$scope.teacherIndex();
}
