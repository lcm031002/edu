angular.module('ework-ui').controller(
		'report_taskSettingsModalController',
		[ '$scope', '$uibModalInstance', '$uibMsgbox', 'optype', 'taskSetting',
				report_taskSettingsModalController ])

function report_taskSettingsModalController($scope, $uibModalInstance, $uibMsgbox,
		optype, taskSetting) {
	$scope.taskSetting = taskSetting;
	$scope.optype = optype;

	$scope.unitList = [{
		id : 1,
		text : '天'
	}, {
		id : 2,
		text : '时'
	}, {
		id : 3,
		text : '分'
	}, {
		id : 4,
		text : '秒'
	}, {
		id : 5,
		text : '毫秒'
	}];
	
	$scope.typeList = [{
		id : 'common',
		text : '通用'
	}, {
		id : 'bjk',
		text : '班级课'
	}, {
		id : 'ydy',
		text : '一对一'
	}, {
		id : 'wfd',
		text : '晚辅导'
	}];

	$scope.confirm = function() {
		$uibMsgbox.confirm('确认保存？', function(res) {
			if (res == 'yes') {
				$uibModalInstance.close($scope.taskSetting);
			}
		});
	}
}
