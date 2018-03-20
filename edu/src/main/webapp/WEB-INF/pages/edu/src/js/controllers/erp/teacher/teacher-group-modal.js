angular.module('ework-ui').controller(
		'erp_teacherGroupModalController',
		[ '$scope', '$uibModalInstance', '$uibMsgbox', 'optype', 'buList',
				'teacherGroup', erp_teacherGroupModalController ])

function erp_teacherGroupModalController($scope, $uibModalInstance, $uibMsgbox,
		optype, buList, teacherGroup) {
	$scope.teacherGroup = teacherGroup;
	$scope.optype = optype;
	$scope.buList = buList;
	$scope.showLeaderSearchModal = false;
	$scope.leaderList = [];
	$scope.teacherList = [];
	
	if (teacherGroup && teacherGroup.leaderList) {
		$scope.leaderList = teacherGroup.leaderList;
	}
	
	if (teacherGroup && teacherGroup.teacherList) {
		$scope.teacherList = teacherGroup.teacherList;
	}
	
	$scope.onLeaderSearchModalChange = function() {
		console.log('onLeaderSearchModalChange');
	}

	$scope.insertLeader = function(teacher) {
		var idx = _.findIndex($scope.leaderList, function(o) {
			return o.id == teacher.id;
		});
		if (idx == -1) {
			$scope.leaderList.push(teacher);
		}
	}

	$scope.insertTeacher = function(teacher) {
		var idx = _.findIndex($scope.teacherList, function(o) {
			return o.id == teacher.id;
		})
		if (idx == -1) {
			$scope.teacherList.push(teacher);
		}
		console.log($scope.teacherList);
	}

	$scope.removeTeacher = function(teacher) {
		_.remove($scope.teacherList, function(o) {
			return o.id == teacher.id;
		});
	}

	$scope.removeLeader = function(teacher) {
		_.remove($scope.leaderList, function(o) {
			return o.id == teacher.id;
		});
	}

	$scope.ok = function() {
		$uibMsgbox.confirm('确认保存？', function(res) {
			if (res == 'yes') {
				if ($scope.leaderList && $scope.leaderList.length > 0) {
					$scope.teacherGroup.leaderList = [];
					$.each($scope.leaderList, function(idx, leader) {
						$scope.teacherGroup.leaderList.push({
							employee_id : leader.employee_id,
							teacher_id : leader.id
						});
					});
				}

				if ($scope.teacherList && $scope.teacherList.length > 0) {
					$scope.teacherGroup.teacherList = [];
					$.each($scope.teacherList, function(idx, teacher) {
						$scope.teacherGroup.teacherList.push({
							teacher_id : teacher.id
						});
					});
				}

				$uibModalInstance.close($scope.teacherGroup);
			}
		});
	}
}
