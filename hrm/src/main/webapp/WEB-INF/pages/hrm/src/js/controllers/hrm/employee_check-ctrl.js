/**
 * 
 */
angular.module('ework-ui')
	.controller('EmployeeCheckCtrl',[
		'$scope',
		'$log',
		'$state',
		'DictItemService',
		'EmployeeManageService',
		'EmployeeCheckService',
		'PostService',
		'workflowNew',
		EmployeeCheckCtrl]);

function EmployeeCheckCtrl($scope,
		$log,
		$state,
		DictItemService,
		EmployeeManageService,
		EmployeeCheckService,
		PostService,
		workflowNew){
	//异动列表
	$scope.changeList = [];
	//填写好的模板数据
	$scope.selecteditem={};
	//得分数组
	$scope.scoreList=[];
	//流程类型初始值
	$scope.processKey="hrm.DXB_zhuanz";
	
	$scope.openPanel='1';
	//当前审核状态（0 新申请 1审批中 2 审批通过 3 审批不通过）
	$scope.approval_status="1";
	//岗位默认值
	$scope.post=0;
	//转正后等级默认值
	$scope.poslevel=0;
	//转正考核有四张模板中有等级字段，当点击详情查询时，仅显示
	$scope.showDetail=1;


    
   /**
    * 选择工作流类型之后跳转到指定页面
    */
   $scope.selectModel=function(selected){
	   $scope.selecteditem={};
	   switch(selected){
	   case "1":$scope.openPanel='1',$scope.processKey="hrm.DXB_zhuanz";break;
	   case "2":$scope.openPanel='2',$scope.processKey="hrm.DXB_school_zhuanz",queryAllPost(),queryDictSub();break;
	   case "3":$scope.openPanel='3',$scope.processKey="hrm.gxh_school_pingjia";break;
	   case "4":$scope.openPanel='4',$scope.processKey="hrm.gxh_zhuanz",queryDictSub();break;
	   case "5":$scope.openPanel='5',$scope.processKey="hrm.gxh_manger_360kaoping";break;
	   case "6":$scope.openPanel='6',$scope.processKey="hrm.gxh_manger_zhuanz",queryDictSub();break;
	   case "7":$scope.openPanel='7',$scope.processKey="hrm.gxh_zixun_360kaoping";break;
	   case "8":$scope.openPanel='8',$scope.processKey="hrm.gxh_zixun_zhuanz",queryDictSub();break;
	   default:alert("请选择模板");break;
	   }
   };
   

    /**
     * 查询归属团队
     */	
    $scope.showEmployeeQuery=function(){
    	$scope.showQuery=true;
    };
    
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
    };
    
    /**
     * 选择一个员工
     */
    $scope.selectedEmployee=function(employee){
    	$scope.selecteditem.employeeId=employee.ID;
    	$scope.selecteditem.employeeName=employee.EMPLOYEE_NAME;
    	$scope.onGoingQuery=false;
    	$scope.showQuery=false;
    };
    
    /**
     * 确定提交模板
     */
    $scope.toSum=function(scoreList){
    	//初始化总分为0
    	var total_score=0;
    	//取得数组的长度，将得分设为数组，由于每个模板的得分个数不同，数组长度不定，便于该方法适用于每个模板
    	var size=scoreList.length;
    	for(var i=0;i<size;i++){
    		total_score+=scoreList[i];//parseInt() 函数可解析一个字符串，并返回一个整数。
    	}
    	$scope.selecteditem.total_score=total_score;
    };
    
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
    
    $scope.toScore=function(scoreList){
    	var total_score=0;
    	var size=scoreList.length;
    	for(var i=0;i<size;i++){
    		var a='';
    		if(scoreList[i]!=null&&scoreList[i]!=''){
    			 a=scoreList[i];
    		}else{
    		  a=scoreList[i]=0;
    		}
    		total_score+=parseInt(a);
    	}
    	$scope.selecteditem.total_score=total_score;
    	   
    }
    
    
    $scope.cancel=function(){
    	$scope.openPanel='';
    	window.close();
    };
    
    
    /**
     * 转正确定提交模板
     */
    $scope.save=function(selecteditem){
    	var param ={};
    	if(selecteditem.employeeId==null){
    		alert("员工不能为空");return;
    	}
    	if($scope.processKey=='hrm.gxh_school_pingjia'){
    		if(selecteditem.class_date==null){
    			alert("日期不能为空");return;
    		}
    	}else if($scope.processKey=='hrm.gxh_zixun_360kaoping'||$scope.processKey=='hrm.gxh_manger_360kaoping'){
    		
    	}
    	else if(selecteditem.entryDate==null||selecteditem.toPostDate==null){
	    		alert("日期不能为空");return;
	    	}
    	if($scope.processKey=='hrm.DXB_school_zhuanz'||$scope.processKey=='hrm.gxh_zhuanz'||
    			$scope.processKey=='hrm.gxh_manger_zhuanz'||$scope.processKey=='hrm.gxh_zixun_zhuanz'){
    		if(selecteditem.post!=null&&selecteditem.post!=''){
    			var temp={};
    			temp=selecteditem.post;
    			param.post=temp.ID;
    		}else{
    			param.post=0;
    		}
    		if(selecteditem.poslevel!=null&&selecteditem.poslevel!=''){
    			var temp={};
    			temp=selecteditem.poslevel;
    			param.poslevel=temp.id;
    		}else{
    			param.poslevel=0;
    		}
    	}
    	param.processKey=$scope.processKey;
    	param.employee_id=selecteditem.employeeId;
    	workflowNew.query({},function(resp){
    		if(!resp.error){
    			workflowNew.post(param,function(resp){
    	    		if(resp.error==false){
    	    			selecteditem.processInstanceId=resp.data.processInstanceId;
    	    			modelNew(selecteditem);
    	    		}else{
    	    			alert(resp.message);
    	    		}
    	    	});
    		}else{
    			alert("流程尚未启动，请联系管理员！");
    		}
    	})
    	
    };
    
    
    /**
     * 发起工作流成功后存到数据库
     */
    function modelNew(selecteditem){
    	if($scope.processKey=='hrm.gxh_manger_360kaoping'||$scope.processKey=='hrm.gxh_zixun_360kaoping'){
    		if($scope.scoreList.length!=0){
    			$scope.toScore($scope.scoreList);
    		selecteditem.scoreList=$scope.scoreList;
    		}
    	}//如果该模板 有得分数组，则获取每一项和总分
    	else if($scope.scoreList.length!=0){
    		$scope.toSum($scope.scoreList);
    		selecteditem.scoreList=$scope.scoreList;
    	}
    	selecteditem.approval_status=$scope.approval_status;
    	//选择的模板
    	selecteditem.processKey=$scope.processKey;
    	//流程类型,数据库中转正考核类型id为9
    	selecteditem.application_type=9;
    	selecteditem.step=1;
    	selecteditem.is_effect=1;
    	if(selecteditem.post!=null&&selecteditem.post!=''){
    	selecteditem.postshow=selecteditem.post.NAME;
    	}
    	if(selecteditem.poslevel!=null&&selecteditem.poslevel!=''){
    		selecteditem.poslevelshow=selecteditem.poslevel.name;
    	}
    	EmployeeCheckService.newModel(selecteditem,function(resp){
    		if(resp.error==false){
    			alert("提交成功");
    			$scope.selecteditem={};
	    		$scope.scoreList=[];
	    		$scope.processKey="";
    			
    		}else{
    			alert(resp.message);
    		}
    		
    	});
    };
    
    
    /**
	 * 查询数据字典子典所有数据
	 */
    function queryDictSub(){
    	var param={};
    	DictItemService.queryDictSubAll(param,function(resp){
    		if(resp.error==false){
    			$scope.poslevelList=resp.data.POSLEVEL;
    		}
    	})
    }
    
    
    /**
     * 查询岗位
     */
   function queryAllPost(){
    	var param={};
    	PostService.queryPost(param,function(resp){
    		if(resp.error==false){
    			$scope.posts=resp.data;
    		}
    	})
    }
    
}