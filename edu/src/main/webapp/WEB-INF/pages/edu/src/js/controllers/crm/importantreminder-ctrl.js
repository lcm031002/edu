/**
 * Created by Liyong.zhu on 2015/12/16.
 */
"use strict";
angular.module('ework-ui').controller('crmImportantReminderCtrl',
		[ '$scope', 'crm_ReminderService', crmImportantReminderCtrl ]);

function crmImportantReminderCtrl($scope, crm_ReminderService) {

	$scope.reminderResult = [];
	$scope.queryReminders = function() {
		if ('loading' == $scope.dataLoad)
			return;

		$scope.dataLoad = 'loading';
		$scope.reminderResult = [];
		crm_ReminderService.query({}, function(Res) {
			$scope.dataLoad = '';
			if (Res.error == false) {
				if (Res.resultList) {
					for (var i = 0; i < Res.resultList.length; i++) {
						$scope.reminderResult.push(Res.resultList[i]);
					}
				}
			}
		});
	}

	$scope.fresh = function() {
		$scope.queryReminders();
	}
	$scope.queryReminders();
}