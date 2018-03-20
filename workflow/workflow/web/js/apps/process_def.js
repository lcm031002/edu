/**
 * Created by Administrator on 2014/11/9.
 */
controllers.controller('ProcessControllerDef', [ 
        '$scope', 
        'ProcessDefService',
        'ProcessDefERRORsService',
        'ProcessInstanceService',
		function($scope, ProcessDefService,ProcessDefERRORsService,ProcessInstanceService) {
			$scope.menuId = '流程定义';
			$scope.drocessDefList = [];

			function query() {
				ProcessDefService.query({}, function(response) {
					if (!response.error) {
						$scope.drocessDefList = response.data;
					}
				});
			}
			query()

			$scope.startProcessDialog = "";
			$scope.startProcessSelected = null;
			$scope.startProcess = function(process) {
				ProcessDefService.post(process, function(response) {
					if (!response.error) {
						alert("已成功发起任务!");
					}
				})
			};

			$scope.delProcess = function(process) {
				ProcessDefService.del({
					deploymentid : process.deploymentid
				}, function(response) {
					if (!response.error) {
						query()
					}else{
						alert("删除失败！可能原因，该流程有尚未结束的任务实例！");
					}
				},function(response){
					alert("删除失败！可能原因，该流程有尚未结束的任务实例！");
				})
			}
			
			$scope.delProcessForce = function(process) {
				ProcessDefERRORsService.del({
					deploymentid : process.deploymentid
				}, function(response) {
					if (!response.error) {
						query()
					}else{
						alert("删除失败！可能原因，该流程有尚未结束的任务实例！");
					}
				},function(response){
					alert("删除失败！可能原因，该流程有尚未结束的任务实例！");
				})
			}
			
			$scope.processInstanceSub = function(process){
				if(process.instanceList){
					process.instanceList = null;
				}else{
					ProcessInstanceService.query({
						deploymentid : process.deploymentid
					},function(response){
						if(!response.error){
							process.instanceList = response.data;
						}
					})
				}
			}
			
			$scope.processInstanceDelete = function(process,subInstance){
				ProcessInstanceService.del({
					processInstanceId : subInstance.id
				},function(response){
					if(!response.error){
						$scope.processInstanceSub(process);
					}
				})
			}
			
			$scope.processInstanceRedo = function(process,subInstance){
				ProcessInstanceService.put({
					processInstanceId : subInstance.id
				},function(response){
					if(!response.error){
						$scope.processInstanceSub(process);
					}
				})
			}

		} ]);

services.factory('ProcessDefService', [ '$resource', function($resource) {
	return $resource(webContext + '/rest/processdef', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		post : {
			method : 'POST',
			params : {},
			isArray : false
		},
		del : {
			method : 'DELETE',
			params : {},
			isArray : false
		}
	});
} ]);
services.factory('ProcessDefERRORsService', [ '$resource', function($resource) {
	return $resource(webContext + '/rest/processdef_forse', {}, {
		del : {
			method : 'DELETE',
			params : {},
			isArray : false
		}
	});
} ]);
services.factory('ProcessInstanceService', [ '$resource', function($resource) {
	return $resource(webContext + '/rest/processinstances', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		del : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		put : {
			method : 'PUT',
			params : {},
			isArray : false
		}
	});
} ]);
