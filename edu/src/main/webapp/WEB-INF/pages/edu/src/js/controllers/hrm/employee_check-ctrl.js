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
	$scope.processKeyModel="hrm.DXB_zhuanz";
	//
	$scope.processKey="hrm_apply_shenpi";
	
	$scope.openPanel='1';
	//当前审核状态（0 新申请 1审批中 2 审批通过 3 审批不通过）
	$scope.approval_status="1";
	//岗位默认值
	$scope.post=0;
	//转正后等级默认值
	$scope.poslevel = 0;
	//转正考核有四张模板中有等级字段，当点击详情查询时，仅显示
	$scope.showDetail = 1;
    var _ = (new Date()).getTime();
    $scope.modelDefineList = [
        {
            "name":"快乐学习大小班教师转正考核表",
            "value":"1",
            "url":"templates/hrm/model/change_post/DXB_teacher_post.html?_="+_,
            "processKeyModel":"hrm.DXB_zhuanz"
        },{
            "name":"快乐学习大小班校区部转正考核表",
            "value":"2",
            "url":"templates/hrm/model/change_post/DXB_school_post.html?_="+_,
            "processKeyModel":"hrm.DXB_school_zhuanz"
        },/*{
            "name":"快乐学习个性化教学部试讲评价表",
            "value":"3",
            "url":"templates/hrm/model/change_post/GXH_teaching_dept_lecture.html?_="+_,
            "processKeyModel":"hrm.gxh_school_pingjia"
        },*/{
            "name":"快乐学习个性化教师转正考核表",
            "value":"4",
            "url":"templates/hrm/model/change_post/GXH_teacher_post.html?_="+_,
            "processKeyModel":"hrm.gxh_zhuanz"
        },/*{
            "name":"快乐学习个性化学管部 360 度考评问卷",
            "value":"5",
            "url":"templates/hrm/model/change_post/GXH_teaching_dept_360.html?_="+_,
            "processKeyModel":"hrm.gxh_manger_360kaoping"
        },*/{
            "name":"快乐学习个性化学管部转正考核表",
            "value":"6",
            "url":"templates/hrm/model/change_post/GXH_teaching_dept_post.html?_="+_,
            "processKeyModel":"hrm.gxh_manger_zhuanz"
        },/*{
            "name":"快乐学习个性化咨询部 360 度考评问卷",
            "value":"7",
            "url":"templates/hrm/model/change_post/GXH_consulting_dept_360.html?_="+_,
            "processKeyModel":"hrm.gxh_zixun_360kaoping"
        },*/{
            "name":"快乐学习个性化咨询部转正考核表",
            "value":"8",
            "url":"templates/hrm/model/change_post/GXH_consulting_dept_post.html?_="+_,
            "processKeyModel":"hrm.gxh_zixun_zhuanz"
        },{
            "name":"快乐学习行政部职能部转正考核表",
            "value":"9",
            "url":"templates/hrm/model/change_post/tobecontinue.html?_="+_,
            "processKeyModel":"hrm.gxh_zixun_zhuanz"
        },{
            "name":"佳音英语（厦门）教师岗位转正考核表",
            "value":"10",
            "url":"templates/hrm/model/change_post/tobecontinue.html?_="+_,
            "processKeyModel":"hrm.gxh_zixun_zhuanz"
        },{
            "name":"佳音英语（厦门）行政教务岗位转正考核表",
            "value":"11",
            "url":"templates/hrm/model/change_post/tobecontinue.html?_="+_,
            "processKeyModel":"hrm.gxh_zixun_zhuanz"
        },{
            "name":"佳音英语（厦门）课程顾问岗位转正考核表",
            "value":"12",
            "url":"templates/hrm/model/change_post/tobecontinue.html?_="+_,
            "processKeyModel":"hrm.gxh_zixun_zhuanz"
        },{
            "name":"佳音英语（厦门）学管师岗位转正考核表",
            "value":"13",
            "url":"templates/hrm/model/change_post/tobecontinue.html?_="+_,
            "processKeyModel":"hrm.gxh_zixun_zhuanz"
        }
    ];

    $scope.selected = $scope.modelDefineList[0];
    
   /**
    * 选择工作流类型之后跳转到指定页面
    */
   $scope.selectModel=function(){
       $scope.openPanel = $scope.selected.value;
       $scope.processKeyModel = $scope.selected.processKeyModel;

       if($scope.selected.value == '2'){
           queryAllPost();
           queryDictSub();
       }
       if($scope.selected.value == '4'){
           queryDictSub();
       }

       if($scope.selected.value == '6'){
           queryDictSub();
       }

       if($scope.selected.value == '8'){
           queryDictSub();
       }
       /*$scope.selecteditem={};
	   switch(selected){
	   case "1":$scope.openPanel='1',$scope.processKeyModel="hrm.DXB_zhuanz";break;
	   case "2":$scope.openPanel='2',$scope.processKeyModel="hrm.DXB_school_zhuanz",queryAllPost(),queryDictSub();break;
	   case "3":$scope.openPanel='3',$scope.processKeyModel="hrm.gxh_school_pingjia";break;
	   case "4":$scope.openPanel='4',$scope.processKeyModel="hrm.gxh_zhuanz",queryDictSub();break;
	   case "5":$scope.openPanel='5',$scope.processKeyModel="hrm.gxh_manger_360kaoping";break;
	   case "6":$scope.openPanel='6',$scope.processKeyModel="hrm.gxh_manger_zhuanz",queryDictSub();break;
	   case "7":$scope.openPanel='7',$scope.processKeyModel="hrm.gxh_zixun_360kaoping";break;
	   case "8":$scope.openPanel='8',$scope.processKeyModel="hrm.gxh_zixun_zhuanz",queryDictSub();break;
	   default:alert("请选择模板");break;
	   }*/
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
    
    
    //360考评问卷选择后统计总分
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
    
    /*--------先提交再保存start-------*/
    
    
    //将所有数据提交到工作流，发起流程并保存到数据库，保证不留脏数据和功能的一致性
    /**
     * 转正确定提交模板
     */
    $scope.save=function(selecteditem){
    	var param ={};
    	if(selecteditem.employeeId==null){
    		alert("员工不能为空");return;
    	}
		 if(selecteditem.entryDate==null||selecteditem.toPostDate==null){
	    		alert("日期不能为空");return;
	    	}
    	if($scope.processKeyModel=='hrm.DXB_school_zhuanz'||$scope.processKeyModel=='hrm.gxh_zhuanz'||
    			$scope.processKeyModel=='hrm.gxh_manger_zhuanz'||$scope.processKeyModel=='hrm.gxh_zixun_zhuanz'){
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
    	
    	 if($scope.scoreList.length!=0){
    		$scope.toSum($scope.scoreList);
    		param.scoreList=$scope.scoreList;
    	}
    	 param.approval_status=$scope.approval_status;
    	//选择的模板
    	param.processKeyModel=$scope.processKeyModel;
    	//流程类型,数据库中转正考核类型id为9
    	param.application_type=9;
    	param.step=1;
    	param.is_effect=1;
    	if(selecteditem.post!=null&&selecteditem.post!=''){
    		param.postshow=selecteditem.post.post_name;
    	}
    	if(selecteditem.poslevel!=null&&selecteditem.poslevel!=''){
    		param.poslevelshow=selecteditem.poslevel.name;
    	}
    	param.processKey=$scope.processKey;
    	param.employee_id=selecteditem.employeeId;
    	workflowNew.query({},function(resp){
    		if(!resp.error){
    			workflowNew.post(param,function(resp){
    	    		if(resp.error==false){
    	    			//selecteditem.processInstanceId=resp.data.processInstanceId;
    	    			//modelNew(selecteditem);
    	    			alert("申请成功");
    	    			$scope.selecteditem={};
    		    		$scope.scoreList=[];
    		    		$scope.processKeyModel="";
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
   /* function modelNew(selecteditem){
    	if($scope.processKeyModel=='hrm.gxh_manger_360kaoping'||$scope.processKeyModel=='hrm.gxh_zixun_360kaoping'){
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
    	selecteditem.processKeyModel=$scope.processKeyModel;
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
	    		$scope.processKeyModel="";
    			
    		}else{
    			alert(resp.message);
    		}
    		
    	});
    };*/
    
    /*--------先提交再保存end-------*/

    
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