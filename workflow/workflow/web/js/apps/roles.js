/**
 * Created by Administrator on 2014/11/9.
 */

controllers.controller('RolesController', [
    '$scope',
    '$resource',
    'ProcessDefService',
    'ProcessRoleService',
    'ProcessRolemappingService',
    function($scope,$resource,ProcessDefService,ProcessRoleService,ProcessRolemappingService) {
    	$scope.menuId = '角色分配';
    	$scope.drocessDefList = [];
    	$scope.ext_query_business_role_url = "";
		ProcessDefService.query({}, function(response) {
			if (!response.error) {
				$scope.drocessDefList = response.data;
			}
		});
		
		$scope.deleteRole=function(mRole){
			ProcessRolemappingService.del(mRole,function(response){
				if(!response.error){
					queryProcess()
				}
			})
		}
       
		$scope.queryProcessRoles = function(process){
			$scope.selectedProcess = process;
			queryProcess()
		}
		
		function queryProcess(){
			ProcessRoleService.query({
				processKey:$scope.selectedProcess.key
			},function(response){
				if(!response.error){
					$scope.roles = response.data;
					$scope.ext_query_business_role_url = response.ext_query_business_role_url;
				}
			})
		}
		
		$scope.selectedProcessClosed = function(){
			$scope.selectedProcess = undefined;
			$scope.roles = undefined;
		}
		
		$scope.seacherRoleFocus = function(role){
			role.seacherRoleStr = "";
			role.seacherRoleResult = [];
			$scope.seacherRoleChange(role);
		}
		$scope.seacherRoleBlur = function(role){
			role.seacherRoleStr = "请输入搜索信息";
			//role.seacherRoleResult = undefined;
		}
		$scope.seacherRoleChange = function(role){
			var queryService = $resource(genWebContext()+$scope.ext_query_business_role_url,{},{
	            query: {method:'GET', params:{}, isArray:false}
	        });
			queryService.query({
				queryString:role.seacherRoleStr
			},function(response){
				if(!response.error){
					role.seacherRoleResult = response.data.resultList;
					$scope.settingsRole = settingsRole;
				}
			})
		}
		
		function settingsRole(role,seacherRole){
			var roleDef = {};
			roleDef.id = role.id;
			roleDef.processKey = role.processKey;
			roleDef.processTask = role.processTask;
			roleDef.remark = role.remark;
			var mapping = {};
			mapping.processRoleDefId = role.id;
			mapping.businessRole = seacherRole.NAME;
			mapping.businessRoleId = seacherRole.ID;
			mapping.processRoleDef = roleDef;
			ProcessRolemappingService.post(mapping,function(response){
				if(!response.error){
					queryProcess();
				}else{
					alert("错误：添加失败！");
				}
			})
		}
    }
]);

services.factory('RolesService', ['$resource',
    function($resource){
        return $resource('/apps/loginuser', {}, {
            query: {method:'GET', params:{}, isArray:false}
        });
    }
]);
services.factory('ProcessRoleService', ['$resource',
    function($resource){
        return $resource(webContext+'/rest/processdef/queryrole',{},{
            query: {method:'GET', params:{}, isArray:false}
        })
    }
]);
services.factory('ProcessRolemappingService', ['$resource',
    function($resource){
        return $resource(webContext+'/rest/processdef/rolemapping',{},{
            query: {method:'GET', params:{}, isArray:false},
            post: {method:'POST', params:{}, isArray:false},
            del: {method:'DELETE', params:{}, isArray:false}
        })
    }
]);
