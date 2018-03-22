/**
 * 
 */
angular.module('ework-ui')
	.controller('ChangeEventCtrl',[
		'$scope',
		'$log',
		'$state',
		'OrgService',
		'workflowComplete',
		'workflowQuery',
		'EmployeeManageService',
		'ChangeEventService',
		ChangeEventCtrl]);

function ChangeEventCtrl($scope,
		$log,
		$state,
		OrgService,
		workflowComplete,
		workflowQuery,
		EmployeeManageService,
		ChangeEventService){
	//异动列表
	$scope.changeList = [];
	$scope.selecteditem={};
	//得分数组
	$scope.scoreList=[];
	//显示模板
	$scope.showModel=false;
	//流程当前的处理分支查询结果被封装在数组中，如"data":["审核同意，去教学总监审核","审核不同意"],"
	$scope.outcomeList=[];

	//审核备注
	$scope.remark=[];
	//处理人
	$scope.handler_nameList=[];
	//处理时间
	$scope.last_update_timeList=[];
	//处理结果
	$scope.handler_opinionList=[];
	$scope.showDetail=1;
	 /**
     * 查询人事异动列表
     */
    function queryChangEventPage(){
    	var param = {};
    	
    	if($scope.changeBuId){
    		var bu_id=$scope.changeBuId;
    		}else{
    			var bu_id="";	
    		}
    	if($scope.changeBranchId){
    		var branch_id=$scope.changeBranchId;
    	}else{
    		var branch_id="";
    	}
    	if($scope.changeType){
    		var type_id=$scope.changeType;
    	}else{
    		var type_id="";
    	}
    	if($scope.changeSeacherinfo){
    		var serach=$scope.changeSeacherinfo;
    	}else{
    		var serach="";
    	}
    	$scope.isLoading = 'loading...';
    	$scope.pageParam = {
    			pageNum :$scope.pageNum,
    			pageSize : 10
    		};
    	param={"pageParam":$scope.pageParam,"bu_id":bu_id,"branch_id":branch_id,"serach":serach,"type_id":type_id};
    	ChangeEventService.queryChangEventPage(param,function(resp){
    		$scope.isLoading = '';
    		if(resp.nodata==false){
    			$scope.changeList = resp.data;
    			$scope.pageParam = resp.pageParam;
    	    	 if ($scope.pageParam.pageNum > 1 && $scope.pageParam.pageNum < $scope.pageParam.pages) {
    	     	       $scope.paginationBars = [$scope.pageParam.pageNum - 1,$scope.pageParam.pageNum, $scope.pageParam.pageNum + 1];
    	         } else if ($scope.pageParam.pageNum == 1 && $scope.pageParam.pages > 1) {
    	     	       $scope.paginationBars = [ $scope.pageParam.pageNum, $scope.pageParam.pageNum + 1];
    	         } else if ($scope.pageParam.pageNum == $scope.pageParam.pages && $scope.pageParam.pages > 1) {
    	     	       $scope.paginationBars = [ $scope.pageParam.pageNum - 1,$scope.pageParam.pageNum];
    	         }
    		}else{
    			$scope.changeList=[];
    		}
    	})
    };
    
    $scope.queryInfo=function(pageIndex){
    	$scope.pageNum=pageIndex;
    	queryChangEventPage();
    };
    
    /**
     * 关注与取消关注
     */
   
    $scope.updateFollow=function(changeevent){ 
    	var param={};
    	var IS_EFFECT=null;
    	if(changeevent.tha_is_effect==1){
    		 IS_EFFECT=0;
    	}else{
    		IS_EFFECT=1;
    	}
    	param.event_id=changeevent.id;
    	param.tha_is_effect=IS_EFFECT;
    	param.employee_id=changeevent.employee_id;
    	ChangeEventService.updateFollow(param,function(resp){
    		if(resp.error==false){
    			queryChangEventPage();
    		}
    	})
    }
    
    $scope.toClosePanel = function(){
    	$scope.showModel=false;
        $scope.openPanel = '';
    }
    
    
    /**
     * 选择工作流类型模态框打开
     */
    
    $scope.toAddChangeEventPanel=function(){
    	$("#chooseChange").modal("show");
    	$scope.selecteditem={};
    	$scope.choosed_type_name=null;
    	$scope.model_num="";
    }
    
    /**
     * 选择工作流类型之后跳转到指定页面
     */
   $scope.chooseChangeEvent=function(){
    	$("#chooseChange").modal("hide");
    
    		return true;
   
    }
    

   
   /**
    * 点击详情
    */
    $scope.detail=function(changeevent){
    	$scope.showDetail=0;
    	$scope.task_id=changeevent.task_id;//将任务id保留，后面提交处理时需要调用
    	$scope.EVENT_ID=changeevent.id;//每一个移动事务都有一个id即异动id，保留后面存至数据库
    	$scope.selecteditem={};
    	var param={};
    	param.event_id=changeevent.id;
    	outcome(changeevent.task_id);
    	ChangeEventService.queryDetail(param,function(resp){
    		if(resp.error==false){
    			if(resp.data.post){
    				resp.data.post=parseInt(resp.data.post);
    			}
    			if(resp.data.poslevel){
    				resp.data.poslevel=parseInt(resp.data.poslevel);
    			}
    			$scope.selecteditem=resp.data;
    			$scope.step=resp.data.steps;//获取当前流程到了哪一步
    
    			switch($scope.selecteditem.processKey){
    			case "hrm.DXB_zhuanz":$scope.stepNum=3,$scope.openPanel='1';break;
    			case "hrm.DXB_school_zhuanz":$scope.stepNum=3,$scope.openPanel='2';break;
    			case "hrm.gxh_school_pingjia":$scope.stepNum=2,$scope.openPanel='3';break;
    			case "hrm.gxh_zhuanz":$scope.stepNum=3,$scope.openPanel='4';break;
    			case "hrm.gxh_manger_360kaoping":$scope.stepNum=2,$scope.openPanel='5';break;
    			case "hrm.gxh_manger_zhuanz":$scope.stepNum=3,$scope.openPanel='6';break;
    			case "hrm.gxh_zixun_360kaoping":$scope.stepNum=2,$scope.openPanel='7';break;
    			case "hrm.gxh_zixun_zhuanz":$scope.stepNum=3,$scope.openPanel='8';break;
    			default :break;
    			}
    			$scope.showModel=true;
    			if($scope.step>1){
    				getRemark(resp.data);
    			}
    			if($scope.selecteditem.scoreList!==null){
    				strToArr($scope.selecteditem.scoreList);
    			}
    		}
    	})
    	
    }
   
 
    /**
     * 获取之前审批的信息
     */
    function getRemark(data){
    	for(var i=1;i<$scope.step;i++){
    		$scope.handler_opinionList[i-1]=data.list[i].handler_opinion;
    		$scope.remark[i-1]=data.list[i].enclosure;
    		$scope.handler_nameList[i-1]=data.list[i].handler_name;
    		$scope.last_update_timeList[i-1]=data.list[i].last_update_time;
    	}
    }
    
    /**
     * 点击详情后获取数组
     */
    function strToArr(scoreList){
    	var ary = eval('(' + scoreList + ')');
    	for (var i = 0 ;i<ary.length;i++){
    		ary[i];
    	}
    	$scope.scoreList=ary;
    }
    
    
    /**
     * 流程当前的处理分支查询
     */
    function outcome(task_id){
    	var param = {};
    	param.taskId=task_id;
    	workflowQuery.query(param,function(resp){
    		if(resp.error==false){
    			$scope.outcomeList=resp.data;
    		}else{
    			alert(resp.message);return;
    		}
    	})
    }
    
    /**
     * 点击同意或不同意，流程当前分支选择
     */
    $scope.processTask = function(outcome){
        var param = {};
        param.remark=$scope.remark[$scope.step];
        param.taskId = $scope.task_id;
        param.outcome = outcome;
        workflowComplete.post(param,function(resp){
            if(resp.error==false){
                alert("审批成功！");
                updateApply(outcome);
                $scope.showModel=false;
                $scope.openPanel='';
                $scope.queryInfo(1);
            }else{
                alert(resp.message);
            }
        })
    }
    
    /**
     * 审批完成后保存到数据库
     */
    function updateApply(outcome){
    	var param={};
    	param.event_id=$scope.EVENT_ID;
    	param.outcome=outcome;
    	param.remark=$scope.remark[$scope.step-1];
    	param.step=$scope.step+1;
    	var temp=$.trim(outcome);
    	if(temp=='审核不同意'){
    		param.is_effect=0;
    		param.approval_status=3;
    	}else{
    		if($scope.step==$scope.stepNum){
    		param.approval_status=2;
    		param.is_effect=0;
    		}else{
    			param.is_effect=1;
	    		param.approval_status=1;
	    		}
    	}	
    	ChangeEventService.post(param,function(resp){
    		if(resp.error==false){
    			alert("保存成功");
    			$scope.showModel=false;
    			$scope.openPanel='';
    		}
    	})
    	$scope.remark[$scope.step-1]="";
    	$scope.queryInfo(1);
    }
    
 
    
    /**
     * 查询归属团队
     */
    $scope.showEmployeeQuery=function(){
    	$scope.showQuery=true;
    }
    
	/**
     * 选择员工时绑定员工
     */
    $scope.queryEmployeeInfo= function (){
    	var searchInfo=$("#employee_search_info").val();
    	 $scope.onGoingQuery=true;
    	   var param = {};
    	   param.searchInfo=searchInfo;
    	EmployeeManageService.queryEmployeeInfo(param,function(resp){
             if(resp.error == false){
                 $scope.employeeList = resp.data;
             }
         });
    }
    
    /**
     * 选择一个员工
     */
    $scope.selectedEmployee=function(employee){
    	$scope.selecteditem.employeeId=employee.ID;
    	$scope.selecteditem.employeeName=employee.EMPLOYEE_NAME;
    	$scope.onGoingQuery=false;
    	$scope.showQuery=false;
    }
    
   
    /**
     * 根据团队查询所属校区
     */
    $scope.queryBranchsWithId= function (buId){
       	var param={};
       	if( buId)
       	param.buId=buId;
       	else{
       		param.buId=bu;
       	}
           OrgService.querySch(param,function(resp){
               if(resp.error == false){
                   $scope.branchsList = resp.data;
               }
           });
       }
  
       /**
        * 查询归属团队
        */
       function queryBu(){
           OrgService.queryBu({},function(resp){
               if(resp.error == false){
                   $scope.buList = resp.data;
               }
           })
       }
       
       /**
        * 查询异动类型
        */
       function queryChangeEventType(){
    	   ChangeEventService.queryChangeEventType({},function(resp){
    		   if(resp.error==false){
    			   $scope.applicationList=resp.data;
    		   }
    	   })
       }
    
       
       
    $scope.queryInfo(1);
    queryBu();
    queryChangeEventType();
}