angular.module('ework-ui').controller('erp_noticeListModalController', [
  '$rootScope',
  '$scope',
  '$log',
  'optype',
  erp_noticeListModalController
])

function erp_noticeListModalController(
	$rootScope,
	$scope,
	$log,
	optype
) {
	$scope.optype = optype || 'view';
}
