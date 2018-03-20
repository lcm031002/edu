/**
 * Created by Administrator on 2014/11/9.
 */

controllers.controller('TaskController', [
    '$scope',
    'TaskService',
    'TaskOutcomesService',
    'TaskOperateService',
    'ProcessRoleService',
    function($scope,TaskService,TaskOutcomesService,TaskOperateService,ProcessRoleService) {
    	$scope.tasks = [];
    	$scope.webContext = genWebContext();
    	var dat = new Date();
    	function query(param){
    	
    		TaskService.query(param,function(response){
                if(!response.error){
                    $scope.tasks = [];
                    $.each(response.data,function(i,model){
                    	if(model.createTime){
                    		var d = new Date();
                    		d.setTime(model.createTime);
                    		model.createTimeString = Format('yyyy-MM-dd hh:ss',d);
                    	}
                        $scope.tasks.push(model);
                    })
                }
            })
    	}
    	
    	query({_:dat.getTime()});
        
        $scope.queryDetails = function(task){
        	if(task.outcomes){
        		task.outcomes = undefined;
        		return ;
        	}
        	var dat = new Date();
        	TaskOutcomesService.query({
        		taskId:task.id,
        		_:dat.getTime()
        	},function(response){
        		if(!response.error){
        			task.outcomes = response.data;
        			task.task_png = response.task_png;
        			task.activityCoordinates = response.activityCoordinates;
        			task.historyTasks = response.historyTasks;
        			$.each(task.historyTasks,function(i,hst){
        				if(hst&&hst.createTime){
        					var dt = new Date();
        					dt.setTime(hst.createTime);
        					hst.createTime = Format('yyyy/MM/dd hh:ss',dt) 
        				}
        			})
        			
        			ProcessRoleService.query({
        				processKey:task.processKey
        			},function(response){
        				if(!response.error){
        					var roles = response.data;
        					
        					var businessRoles = [];
        					if(roles){
        						$.each(roles,function(i,role){
        							if(!role){
        								return;
        							}
            						$.each(role.mappings,function(ii,map){
            							businessRoles.push({
            								businessRole:map.businessRole,
            								businessRoleId:map.businessRoleId
            							});
            						})
            					})
        					}
        					
        					if(businessRoles.length){
        						$.each(task.historyTasks,function(i,historyTask){
        							$.each(businessRoles,function(ii,businessRole){
        								if(businessRole.businessRoleId==historyTask.assignee+""){
        									historyTask.assignee = businessRole.businessRole;
        								}
        							})
        						})
        					}
        					
        				}
        			})
        		}
        	})
        }
        
        $scope.operateTask = function(task,outcome){
        	var remark = task.taskRemak;
        	var outcome = outcome;
        	var taskId = task.id;
        	TaskOperateService.post({
        		taskId:taskId,
        		outcome:outcome,
        		remark:remark
        	},function(response){
        		if(!response.error){
        			alert("操作成功！");
        			query({_:dat.getTime()})
        		}
        	})
        }
        
        $scope.seacherName = '请输入任意关键字';
		$scope.inputing = false;
		$scope.onSeacherNameBlur = function(){
			if($scope.seacherName==''){
				$scope.seacherName = '请输入任意关键字';
			}else{
				
			}
			$scope.inputing = false;
//			$(".sub-search-input").animate({width:'240px'},'slow');
		};
		
		$scope.onSeacherNameFocus=function(){
			if($scope.seacherName=='请输入任意关键字'){
				$scope.seacherName = '';
			}
			$scope.inputing = true;
//			$(".sub-search-input").animate({width:'350px'},'slow');
		};
		
		$scope.seacher=function(){
			var param = {};
			var time = new Date();
			param._ = time.getTime();
			if($scope.seacherName=='请输入任意关键字'){
				$scope.seacherName = '';
			}
			param.seacherName = encodeURIComponent($.trim($scope.seacherName));
			query(param);
		};
        
    }
]);

services.factory('TaskService', ['$resource',
    function($resource){
        return $resource(webContext + '/rest/taskinfos', {}, {
            query: {method:'GET', params:{}, isArray:false}
        });
    }
]);
services.factory('TaskOutcomesService', ['$resource',
     function($resource){
         return $resource(webContext + '/rest/task/outcomes', {}, {
             query: {method:'GET', params:{}, isArray:false}
         });
     }
 ]);
services.factory('TaskOperateService', ['$resource',
     function($resource){
         return $resource(webContext + '/rest/task/complete', {}, {
             post: {method:'POST', params:{}, isArray:false}
         });
     }
 ]);