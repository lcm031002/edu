"use strict";
angular.module('ework-ui').controller(
		'crmMasterCtrl',
		[ '$rootScope', '$scope', '$interval', 'crm_ReminderService',
				'crm_ApprovedService', 'crm_MyOrderedsService',
				'crm_NoRegisterCtrlService', 'crm_LoginUserService',
				crmMasterCtrl ]);

function crmMasterCtrl($rootScope, $scope, $interval, crm_ReminderService,
		crm_ApprovedService, crm_MyOrderedsService, crm_NoRegisterCtrlService,
		crm_LoginUserService) {

	$scope.showInputTip = function() {
		$('#resc_input').popover('show');
		setTimeout(function() {
			$("#resc_input").popover('hide');
		}, 3000);
	};

	// $scope.contextPath = contextPath;

	$scope.Master = {
		meminder : 0,
		approved : 0,
		myOrdereds : 0,
		customerBirthday : 0
	};

	$scope.extendPortlet = '';
	$scope.screen = '';
	/**
	 * 放大
	 * 
	 * @param portlet
	 */
	$scope.extend = function(portlet) {
		$scope.extendPortlet = portlet;
	}

	/**
	 * 全屏显示
	 */
	$scope.fullscreen = function() {

		var docElm = document.documentElement;

		// W3C
		if (docElm.requestFullscreen) {
			docElm.requestFullscreen();
		}

		// FireFox
		else if (docElm.mozRequestFullScreen) {
			docElm.mozRequestFullScreen();
		}

		// Chrome等
		else if (docElm.webkitRequestFullScreen) {
			docElm.webkitRequestFullScreen();
		}

		// IE11
		else if (elem.msRequestFullscreen) {
			elem.msRequestFullscreen();
		}
		$scope.screen = 'fullscreen';
	}

	/**
	 * 退出全屏
	 */
	$scope.resetFullscreen = function() {
		$scope.screen = '';
		if (document.exitFullscreen) {
			document.exitFullscreen();
		}

		else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		}

		else if (document.webkitCancelFullScreen) {
			document.webkitCancelFullScreen();
		}

		else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		}
	}
	function queryReminderService() {
		crm_ReminderService.count({}, function(resp) {
			if (!resp.error) {
				$scope.Master.meminder = parseInt(resp.totalCount);
			}
		})
	}

	function queryApprovedService() {
		crm_ApprovedService.count({}, function(resp) {
			if (!resp.error) {
				$scope.Master.approved = parseInt(resp.totalCount);
			}
		})
	}

	function queryMyOrderedsService() {
		crm_MyOrderedsService.count({}, function(resp) {
			if (!resp.error) {
				$scope.Master.myOrdereds = parseInt(resp.totalCount);
			}
		})
	}
	queryReminderService();
	queryApprovedService();
	queryMyOrderedsService();

	$interval(function() {
		queryReminderService();
		queryApprovedService();
		queryMyOrderedsService();
	}, 1000*600*2);

	$interval(function() {
		if ($rootScope.rootMasterRefush == 1) {
			$rootScope.rootMasterRefush = -1;
			queryReminderService();
			queryApprovedService();
			queryMyOrderedsService();
		}
	}, 1000*600*2);
}