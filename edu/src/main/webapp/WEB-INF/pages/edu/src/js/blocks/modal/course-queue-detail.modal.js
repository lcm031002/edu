angular.module('ework-ui').controller('modal_courseQueueDetailController', [
	'$rootScope',
	'$scope',
	'$uibMsgbox',
	'erp_sortNumService',
	'course',
	'courseSeqDetail',
	modal_courseQueueDetailController
]);

function modal_courseQueueDetailController(
	$rootScope,
	$scope,
	$uibMsgbox,
	erp_sortNumService,
	course,
	courseSeqDetail
) {
	// 课程信息
	$scope.course = course;
	// 课次信息
	$scope.courseSeqDetail = courseSeqDetail;
	// 课次排号列表
	$scope.courseQueueList = []

	$scope.goToOrders = function (studentId) {
		window.open("?studentId=" + studentId + "#/orders/ordersMgr/ordersMgrOrders");
	}
	function getDetailList() {
		erp_sortNumService.query({
			courseId: course.id,
			seq: courseSeqDetail.seq
		}, function (resp) {
			if (!resp.error) {
				$scope.courseQueueList = resp.data;
			}
		})
	}

	$scope.cancelQueue = function(detail) {
		$uibMsgbox.confirm('确认取消排号？', function(res) {
			if (res == 'yes') {
				erp_sortNumService.cancelSortNum({
					courseId: $scope.course.id,
					studentId: detail.studentId,
					seq: detail.seq
				}, function (resp) {
					if (!resp.error) {
						getDetailList();
					} else {
						$uibMsgbox.error(resp.message);
					}
				}, function (resp) {
					$uibMsgbox.error('请求失败！错误码：' + resp.status);
				})
			}
		})
	}
	getDetailList();
}
