/**
 * Alerts Controller
 */

angular.module('ework-ui').controller(
		'crmAlertsCtrl',
		[ '$rootScope', '$scope', '$sce', '$interval', 'crm_AlertService',
				crmAlertsCtrl ]);

function crmAlertsCtrl($rootScope, $scope, $sce, $interval, crm_AlertService) {
	$scope.operWait = false;
	function queryReminders() {
		if ($scope.operWait)
			return;
		$scope.operWait = true;
		crm_AlertService.query({
			action : 'queryReminders'
		}, function(Res) {
			$scope.noticeResult = [];
			$scope.reminderResult = [];
			if (Res.error == false) {
				if (Res.data) {
					for (var i = 0; i < Res.data.length; i++) {
						if (1 == Res.data[i].remind_type) {
							$scope.noticeResult.push(Res.data[i]);
						}
						if (2 == Res.data[i].remind_type) {
							$scope.reminderResult.push(Res.data[i]);
						}
					}
				}
			}
			$scope.operWait = false;
		});
	}
	queryReminders();
//	$interval(queryReminders, 1000 * 60);

	$scope.closeNotice = function() {
		$scope.noticeResult = [];
	}
	$scope.closeReminder = function() {
		$scope.reminderResult = [];
	}

}