angular.module('ework-ui').controller(
		'orderWorkflowModalController',
		[ '$scope',
		  '$uibModalInstance',
		  '$uibMsgbox',
		  'erp_workflowTaskTodoService',
	      'erp_workflowTaskService',
	      'orderId',
	      orderWorkflowModalController ])

function orderWorkflowModalController($scope, $uibModalInstance, $uibMsgbox,
		erp_workflowTaskTodoService, erp_workflowTaskService, orderId) {
	$scope.orderId = orderId;
	$scope.taskDetail = null;
	
	$scope.queryWorkflowInfo = function() {
		var param = {orderId : orderId};
		erp_workflowTaskTodoService.queryByOrderId(param, function(resp) {
			if (!resp.error && resp.data) {
				param.taskId = resp.data.id;
				erp_workflowTaskService.query(param, function(resp) {
		            if(!resp.error) {
		                var taskDetailInfo = resp;
		                if(taskDetailInfo && taskDetailInfo.task &&  taskDetailInfo.task.createTime){
		                    var dt = new Date();
		                    dt.setTime(taskDetailInfo.task.createTime);
		                    taskDetailInfo.task.createTime = Format('yyyy-MM-dd hh:mm:ss', dt);
		                }
		                if(taskDetailInfo && taskDetailInfo.task && taskDetailInfo.task.extData && taskDetailInfo.task.extData.businessDetailInfo){
		                    taskDetailInfo.task.extData.businessDetailInfo = taskDetailInfo.task.extData.businessDetailInfo.split("$$$$").join("");
		                }

		                if(taskDetailInfo && taskDetailInfo.historyTasks){
		                    $.each( taskDetailInfo.historyTasks,function(i,historyTask){
		                        if(historyTask.createTime){
		                            var dt = new Date();
		                            dt.setTime(historyTask.createTime);
		                            historyTask.createTime = Format('yyyy-MM-dd hh:mm:ss',dt);
		                        }
		                    });
		                }

		                $scope.taskDetail = taskDetailInfo;
		            }else{
		            	$uibMsgbox.alert(resp.message);
		            }
		        });
			} else {
				if (resp.message) {
					$uibMsgbox.alert(resp.message);
				} else if (!resp.data) {
					$uibMsgbox.alert("该订单没有审批流程信息");
					$uibModalInstance.close();
				}
			}
		});
	}
	
	$scope.queryWorkflowInfo();
}
