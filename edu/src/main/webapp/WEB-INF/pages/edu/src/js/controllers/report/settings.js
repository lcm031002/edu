angular.module('ework-ui').controller('report_taskSettingsController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    '$uibModal',
    'report_taskSettingsService',
    report_taskSettingsController
]);

function report_taskSettingsController($rootScope,
	$scope,
	$uibMsgbox,
	$uibModal,
	report_taskSettingsService) {

    $scope.searchParam = {};
    
    $scope.taskSetting = {};
    
    $scope.taskSettings = [];
    
    $scope.optype = 'add';
    
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
    
    $scope.queryTaskSettings = function () {
		$scope.loadStatues = true;
		report_taskSettingsService.queryList($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.taskSettings = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    
    $scope.handleAddTaskSetting = function() {
    	$scope.optype = 'add';
    	$scope.openModal();
    }
    
    $scope.handlePutTaskSetting = function(taskSetting) {
    	$scope.optype = 'update';
    	$scope.taskSetting = taskSetting;
    	$scope.openModal();
    }
    
    $scope.openModal = function() {
    	$uibModal.open({
			resolve : {
				optype : function() {
					return $scope.optype;
				},
				taskSetting : function() {
					return ($scope.optype == 'add') ? {} : $scope.taskSetting;
				}
			},
			templateUrl : 'templates/block/modal/settings.modal.html',
			controller : 'report_taskSettingsModalController'
		}).result.then(function(taskSetting) {
			$scope.taskSetting = taskSetting;
			if ($scope.optype == 'add') {
				$scope.add();
			} else {
				$scope.update();
			}
		}, function() {
		});
    }
    
    $scope.check = function() {
    	if (!checkStartEndTime($scope.taskSetting.startDate, $scope.taskSetting.endDate)) {
    		$uibMsgbox.error("截止日期不能小于开始日期");
    		return false;
    	}
    	return true;
    }
    
    $scope.add = function() {
    	if ($scope.check()) {
    		report_taskSettingsService.add($scope.taskSetting, function(resp) {
        		if (!resp.error) {
        			$uibMsgbox.alert("添加成功！");
        		} else {
        			$uibMsgbox.error(resp.message);
        		}
        	});
    	}
    }

    $scope.update = function() {
    	if ($scope.check()) {
    		report_taskSettingsService.update($scope.taskSetting, function(resp) {
        		if (!resp.error) {
        			$uibMsgbox.alert("修改成功！");
        		} else {
        			$uibMsgbox.error(resp.message);
        		}
        	});
    	}
    }
    
    $scope.onStatusChange = function(taskSetting) {
		report_taskSettingsService.changeStatus(taskSetting, function(resp) {
    		if (!resp.error) {
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
    	});
    }
    
    $scope.initPage = function() {
    	$scope.queryTaskSettings();
    }
    
    $scope.initPage();
}