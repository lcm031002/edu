/**
 * 
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_workflowTaskAppication', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        'erp_workflowMyAppicationService',
        'erp_workflowTaskService',
        erp_workflowMgrmentController]);

function erp_workflowMgrmentController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    erp_workflowMyAppicationService,
    erp_workflowTaskService) {
	$scope.page = {
				currentPage:1,
				pageSize:5,
				totalCount:0
			};
	$scope.isLoading = '';
	$scope.app_info = '';
	$scope.isDetailLoading='';
	$scope.showDetail = false;
	$scope.queryModuleList = [
	    {
	    	"name":"全部",
	    	"value":"-1"
	    },{
	    	"name":"erp",
	    	"value":"erp"
	    },{
	    	"name":"hrm",
	    	"value":"hrm"
	    }
	];
	$scope.selectedModule = $scope.queryModuleList[0];
	$scope.queryWorkFlowStateList = [
  	    {
  	    	"name":"全部",
  	    	"value":"-1"
  	    },{
  	    	"name":"申请已提交",
  	    	"value":"申请已提交"
  	    },{
  	    	"name":"待审核",
  	    	"value":"待审核"
  	    },{
  	    	"name":"已通过",
  	    	"value":"已通过"
  	    },{
  	    	"name":"审核通过，订单生效",
  	    	"value":"审核通过，订单生效"
  	    },{
  	    	"name":"审核通过，订单作废处理",
  	    	"value":"审核通过，订单作废处理"
  	    },{
  	    	"name":"审核通过，退费单生效",
  	    	"value":"审核通过，退费单生效"
  	    },{
  	    	"name":"订单审批不通过",
  	    	"value":"订单审批不通过"
  	    },{
  	    	"name":"审批不通过，退费单作废",
  	    	"value":"审批不通过，退费单作废"
  	    }
  	];
	$scope.selectedWorkFlowState = $scope.queryWorkFlowStateList[2];
	
	$scope.queryMyAppications = function (){
		var param = {
				currentPage:$scope.page.currentPage,
				pageSize:$scope.page.pageSize
		};
		param.app_info = $scope.app_info;
		param.module = $scope.selectedModule.value;
		param.start_date = $("#cdt_start_date_02").val();
		param.end_date = $("#cdt_end_date_02").val();
		param.auditStatus = $scope.selectedWorkFlowState.value;
		$scope.isLoading = 'isLoading';
		erp_workflowMyAppicationService.query(param,function(resp){
			
			$scope.isLoading = '';
			if(!resp.error){
				$.each(resp.data.resultList,function(i,n) {
					n.APPLICATION = n.APPLICATION.replace(/\[(.+?)\]/g,"【<span style='color:#1b8cf2'>$1</span>】");
				})
				$scope.page = resp.data;
			}else{
				alert(resp.message);
			}
		});
	};
	
	$scope.pageQuery =function(currentPage){
		$scope.page.currentPage = currentPage;
		if($scope.page.currentPage<1){
			$scope.page.currentPage = 1;
		}
		if($scope.page.currentPage>$scope.page.totalPage){
			$scope.page.currentPage=$scope.page.totalPage;
		}
		$scope.queryMyAppications();
	};
	
	$scope.deleteApplication = function(row){
		var param = {
				applicationId:row.ID
		};
		$scope.isLoading = 'isDeleting';
		erp_workflowMyAppicationService.del(param,function(resp){
			$scope.isLoading = '';
			if(!resp.error){
				alert("删除成功！");
				$scope.queryMyAppications();
			}else{
				alert(resp.message);
			}
		});
	};
	
	$scope.queryMore = function(){
		$scope.page.pageSize = $scope.page.pageSize + 5;
		$scope.pageQuery(1);
	};
	$scope.queryInitial = function(){
		$scope.page.pageSize = 5;
		$scope.pageQuery(1);
	};
	
	$scope.showDetailInfo = function(row){
        if(row.showDetail ){
            row.showDetail = false;
            return true;
        }
		row.showDetail = true;
		$scope.selectedRow = row;
		$scope.isDetailLoading='isDetailLoading';
		var param = {};
		$scope.selectedRow.task = undefined;
		param.taskId = row.taskId;
		erp_workflowTaskService.query(param,function(resp){
			$scope.isDetailLoading='';
			if(!resp.error){
                var taskDetailInfo = resp;
                if(taskDetailInfo && taskDetailInfo.task &&  taskDetailInfo.task.createTime){
                    var dt = new Date();
                    dt.setTime(taskDetailInfo.task.createTime);
                    taskDetailInfo.task.createTime = Format('yyyy-MM-dd hh:mm:ss',dt);
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
                
                $scope.selectedRow.task = taskDetailInfo;
                if($scope.selectedRow.task&&$scope.selectedRow.task.task&&$scope.selectedRow.task.task.extData){
                	$scope.selectedRow.extData = $scope.selectedRow.task.task.extData;
                }

            }else{
                alert(resp.message);
            }
		});
	};
	$scope.closeDetailInfo = function(row){
        row.showDetail = false;
	};
	$scope.closeDeletingInfo = function(){
		$scope.isLoading = '';
	};


    $scope.query = function(){
        $scope.page.resultList = [];
        $scope.page.pageSize = 5;
        $scope.queryMyAppications();
    }

    $scope.queryMyAppications();
};