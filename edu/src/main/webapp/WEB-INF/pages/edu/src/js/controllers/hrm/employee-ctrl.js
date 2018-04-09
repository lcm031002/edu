/**
 * 
 */
angular.module('ework-ui')
    .controller('EmployeeCtrl', [
        '$scope',
        '$log',
        '$state',
        '$cookieStore',
        'OrgService',
        'PostService',
        'DictItemService',
        'EmployeeExtService',
        'erp_employeeMgrService',
        EmployeeCtrl]);

function EmployeeCtrl($scope,
                  $log,
                  $state,
                  $cookieStore,
                  OrgService,
                  PostService,
                  DictItemService,
                  EmployeeExtService,
                  erp_employeeMgrService){
	//员工列表
	$scope.employeeList = [];
	$scope.itemOperateType = '';
    $scope.selectedItem = {};
    $scope.employee = [];
    $scope.edu={};
    $scope.paginationBars = [];
    $scope.enterTypeList=[];
    //选中的查询条件
    $scope.selected={};
    
    //选中的员工岗位信息
    $scope.selectedPost={};
    
    $scope.setPostPanel=false;
    
    //岗位查询列表
    $scope.postList=[];
    $scope.detailPage = 'templates/hrm/employee/employee_info.html?_='+(new Date()).getTime();
    $('title').text('员工管理 | 人力');

    $scope.test=1;

    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function(){
            queryEmployee();
        }
    };

    /**
     * 查询员工列表
     */
    function queryEmployee(){
    	var param = {};
    	$scope.employeeList=[];

    	/*if($scope.selectedOrgId){
    		param.org_id=$scope.selectedOrgId;
    		}*/
    	
    	if($scope.selected.Dept!=null && $scope.selected.Dept!=''){
    		param.dept=$scope.selected.Dept;
    		}else{
    			param.dept='';
    		}
    	if($scope.selected.PostId!=null){
    		param.post=$scope.selected.PostId;
    	}else{
    		param.post=-1;
    	}
    	if($scope.selected.EnterType!=null){
    		param.enterType=$scope.selected.EnterType;
    	}else{
    		param.enterType=-1;
    	}
    	if($scope.selected.Name!=null && $scope.selected.Name!=''){
    		param.employee_name=$scope.selected.Name;
    	}else{
    		param.employee_name='';
    	}
    	$scope.isLoading = 'loading...';

    	erp_employeeMgrService.queryEmployeeForPage(param,function(resp){
    		$scope.isLoading = '';
    		if(!resp.error) {
    			$scope.employeeList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
    		}
    	})
    };
    
    $scope.queryInfo=function(pageIndex){
    	$scope.pageNum=pageIndex;
    	queryEmployee();
    };
    
    /**
	 * 查询数据字典子典所有数据
	 */
    function queryDictSub(){
    	var param={};
    	DictItemService.queryDictSubAll(param,function(resp){
    		if(resp.error==false){
    			$scope.cache=resp.data;
    			$scope.enterTypeList=$scope.cache.ENTERTYPE;
    			$scope.poslevelList=$scope.cache.POSLEVEL;
    		}
    	})
    }
    
    
    /**
	 * 打开员工档案管理的面板
	 */    
    $scope.toQueryItem = function(emp){
    	/*$scope.addEmp=false;
    	$('#EmpPanel').modal({"show":true,"backdrop":'static'});
    	$scope.selectedEmp=emp;		//提取选中的员工id方便查询教育经历 ，工作经历等信息
    	//param.id = emp.id;
    	
    	getRet(emp);
    	
        $scope.itemOperateType = 'query';
	    queryEmployeeEdu();
	    queryEmployeeExp();
	    queryEmployeeSum();
	    queryEmployeeRew();*/
    	
        return true;
	};
	
	/**
	 * 查询选中的员工定义表中的动态字段
	 */
	function getRet(emp){
		var param={};
    	EmployeeExtService.queryField(param,function(resp){
    		if(resp.error==false){
    			$scope.ret=resp.data;
    		}
    		getEmployeeInfo(emp);
    	});
	}
	
	/**
	 * 查询员工信息表中的基础信息
	 */
	function getEmployeeInfo(emp){
		var param={};
    	param.id = emp.id;
		erp_employeeMgrService.queryField(param,function(resp){
            if(resp.error == false){
                $scope.employee = resp.data;
            }
            getSelectedEmp($scope.ret,$scope.employee);
        })
	}
	
	/**
	 * 查询动态的员工基础信息
	 */
	function getSelectedEmp(ret,employee){
		$scope.temp=[];
        if(ret && ret.length){
            for(var i=0;i<ret.length;i++){
                var str=ret[i].fieldKey;
                ret[i].fieldKey=employee[0][str];
            };
            //再次进行遍历将数据字典子项加入集合
            for(var i=0;i<ret.length;i++){
                if(ret[i].dictTypeName!=null){
                    ret[i].selected=$scope.cache[ret[i].dictTypeName];
                }
            }
            $scope.temp=ret;
        }


	}
	
	/**
	 * 准备添加员工基本信息
	 */
	$scope.toAddEmpPanel = function(){
		$scope.temp=[];
		var param={};
		$scope.selectedEmp = {};
		$scope.addEmp=true;
//	    $('#EmpPanel').modal('show');
	    EmployeeExtService.queryField(param,function(resp){
    		if(resp.error==false){
    			$scope.extField=resp.data;
    		}
    		for(var i=0;i<$scope.extField.length;i++){
    			if($scope.extField[i].dictTypeName!=null){
    				$scope.extField[i].fieldKey=null;
    				$scope.extField[i].selected=$scope.cache[$scope.extField[i].dictTypeName];
    			}else{
    				$scope.extField[i].fieldKey=null;
    			}
    		}
    		$scope.temp=$scope.extField;
    	});
	};
	

	
	

	
	/**
	 * 去添加教育经历
	 */
	$scope.toAddEdu = function(){
		$scope.eduUpdate=false;
		$scope.selectedEdu={};
		$("#eduModal").modal({"show":true,"backdrop":'static'});
	}
	
	/**
	 * 去修改教育经历
	 */
	$scope.toUpdateEdu = function(edu){
		$scope.eduUpdate=true;
		$("#eduModal").modal({"show":true,"backdrop":'static'});
		$scope.selectedEdu=edu;
	}
	
	/**
	 * 去删除教育经历
	 */
	$scope.toDeleteEdu = function(edu){
		$scope.eduDelete=true;
		$scope.selectedEdu=edu;
	}
	
	
	/**
	 * 去添加工作经历
	 */
	$scope.toAddExp = function(){
		$scope.expUpdate=false;
		$scope.selectedExp={};
		$("#expModal").modal({"show":true,"backdrop":'static'});
	}
	
	/**
	 * 去修改工作经历
	 */
	$scope.toUpdateExp = function(exp){
		$scope.expUpdate=true;
		$("#expModal").modal({"show":true,"backdrop":'static'});
		$scope.selectedExp=exp;
	}
	
	/**
	 * 去删除工作经历
	 */
	$scope.toDeleteExp = function(exp){
		$scope.expDelete=true;
		$scope.selectedExp=exp;
	}
	
	
	/**
	 * 去添加工作总结
	 */
	$scope.toAddSum = function(){
		$scope.sumUpdate=false;
		$scope.selectedSum={};
		$("#sumModal").modal({"show":true,"backdrop":'static'});
	}
	
	/**
	 * 去修改工作总结
	 */
	$scope.toUpdateSum = function(sum){
		$scope.sumUpdate=true;
		$("#sumModal").modal({"show":true,"backdrop":'static'});
		$scope.selectedSum=sum;
	}
	
	/**
	 * 去删除工作总结
	 */
	$scope.toDeleteSum = function(sum){
		$scope.sumDelete=true;
		$scope.selectedSum=sum;
	}
	
	
	/**
	 * 去添加奖惩信息
	 */
	$scope.toAddRew = function(){
		$scope.rewUpdate=false;
		$scope.selectedRew={};
		$("#rewModal").modal({"show":true,"backdrop":'static'});
	}
	
	/**
	 * 去修改奖惩信息
	 */
	$scope.toUpdateRew = function(rew){
		$scope.rewUpdate=true;
		$("#rewModal").modal({"show":true,"backdrop":'static'});
		$scope.selectedRew=rew;
	}
	
	/**
	 * 去删除奖惩信息
	 */
	$scope.toDeleteRew = function(rew){
		$scope.rewDelete=true;
		$scope.selectedRew=rew;
	}
	
	
	/**
	 * 添加员工基础信息
	 */
	$scope.addEmployee = function(){
		var param={};		
		/*if($scope.selectedItem.EMPLOYEE_NAME==""||$scope.selectedItem.EMPLOYEE_NAME==null){
				alert("姓名不能为空");
				return;
			}
		if($scope.selectedItem.ENCODING==""||$scope.selectedItem.ENCODING==null){
			alert("工号不能为空");
			return;
		}*/
		queryExtInfo();
		var retEmp=$scope.retEmp;
		for(var i=0;i<retEmp.length;i++){
			//过滤，获取填写过的信息，若字段值为null，MyBatis 插入空值时，需要指定JdbcType，否则报错
			if($scope.temp[i].fieldKey!=null){
				var str=retEmp[i].fieldKey;
				param[str]=$scope.temp[i].fieldKey;
				}
		}
		//添加任职信息
		param.work=$scope.selectedEmp;
		erp_employeeMgrService.add(param,function(resp){
			 if(resp.error == false){
//				$('#EmpPanel').modal('hide');
				alert("添加成功");
				$scope.addEmp=false;
				$scope.employeeList[$scope.employeeList.length]=param;
				queryEmployee();
			}else{
				alert("添加失败！失败信息："+resp.message);
			}
		})
	};
	
	/**
	 * 修改员工档案
	 */
	$scope.updateEmployee = function(){
		/*if($scope.selectedEmp.EMPLOYEE_NAME==""||$scope.selectedEmp.EMPLOYEE_NAME==null){
				alert("姓名不能为空");
				return;
			}
		if($scope.selectedEmp.ENCODING==""||$scope.selectedEmp.ENCODING==null){
			alert("员工号不能为空");
			return;
		}*/
		var param={};
		$scope.addEmp=false;
		queryExtInfo();
		var retEmp=$scope.retEmp;
		for(var i=0;i<retEmp.length;i++){
			var str=retEmp[i].fieldKey;
			if($scope.temp[i].fieldKey!=null ){
				$scope.employee[0][str]=$scope.temp[i].fieldKey;
			}
		}
		
		$scope.employee[0].id=$scope.selectedEmp.id;
		param=$scope.employee[0];
		erp_employeeMgrService.updateEmployeeStatic($scope.selectedEmp,function(resp){
			if(resp.error==false){
			
			}
		})
		erp_employeeMgrService.update(param,function(resp){
			 if(resp.error==false){
//				$('#EmpPanel').modal('hide');
				alert("修改成功");
				queryEmployee();
			}else{
				alert("修改失败，失败信息："+resp.message);
			}
		});
	};
	
	
	
	/**
	 * 再次查询员工定义表中启用的字段信息，获取员工信息的key
	 */
	function queryExtInfo(){
		var param={};
		EmployeeExtService.queryField(param,function(resp){
    		if(resp.error==false){
    			$scope.retEmp=resp.data;
    		}
    	});
	}
	
	/**
	 * 添加员工教育经历
	 */
	$scope.addEdu = function(selectedEdu){
		var param = {};
		selectedEdu.employee_id=$scope.selectedEmp.id;
		param=selectedEdu;
		erp_employeeMgrService.addEmployeeEdu(param,function(resp){
			if(resp.error==false){
				alert("添加员工教育经历成功");
				$("#eduModal").modal("hide");
				queryEmployeeEdu();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 修改员工教育经历
	 */
	$scope.updateEdu = function(selectedEdu){
		var param = {};
		param = selectedEdu;
		erp_employeeMgrService.updateEmployeeEdu(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#eduModal").modal("hide");
				queryEmployeeEdu();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 删除员工教育经历
	 */
	$scope.deleteEdu = function(){
		var param = {};
		param.id=$scope.selectedEdu.id;
		erp_employeeMgrService.deleteEmployeeEdu(param,function(resp){
			if(resp.error==false){
				alert("删除员工教育经历成功");
				$("#DelModal").modal("hide");
				queryEmployeeEdu();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.eduDelete=false;
		});
	}
	
	/**
	 * 查询员工教育经历
	 */
	function queryEmployeeEdu(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		erp_employeeMgrService.queryEmployeeEdu(param,function(resp){
			if(resp.error==false){
				$scope.employeeEdu=resp.data;
			}
		});
	}
	
	/**
	 * 查询员工工作经历
	 */
	function queryEmployeeExp(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		erp_employeeMgrService.queryEmployeeExp(param,function(resp){
			if(resp.error==false){
				$scope.employeeExp=resp.data;
			}
		});
	}
	
	/**
	 * 添加员工工作经历
	 */
	$scope.addExp = function(selectedExp){
		var param = {};
		selectedExp.employee_id=$scope.selectedEmp.id;
		param=selectedExp;
		erp_employeeMgrService.addEmployeeExp(param,function(resp){
			if(resp.error==false){
				alert("添加员工工作经历成功");
				$("#expModal").modal("hide");
				queryEmployeeExp();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 修改员工工作经历
	 */
	$scope.updateExp = function(selectedExp){
		var param = {};
		param = selectedExp;
		erp_employeeMgrService.updateEmployeeExp(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#expModal").modal("hide");
				queryEmployeeExp();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 删除员工工作经历
	 */
	$scope.deleteExp = function(){
		var param = {};
		param.id=$scope.selectedExp.id;
		erp_employeeMgrService.deleteEmployeeExp(param,function(resp){
			if(resp.error==false){
				alert("删除员工工作经历成功");
				$("#DelModal").modal("hide");
				queryEmployeeExp();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.expDelete=false;
		});
	}
	
	/**
	 * 查询员工工作总结
	 */
	function queryEmployeeSum(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		erp_employeeMgrService.queryEmployeeSum(param,function(resp){
			if(resp.error==false){
				$scope.employeeSum=resp.data;
			}
		});
	}
	
	/**
	 * 添加员工工作总结
	 */
	$scope.addSum = function(selectedSum){
		var param = {};
		selectedSum.employee_id=$scope.selectedEmp.id;
		param=selectedSum;
		erp_employeeMgrService.addEmployeeSum(param,function(resp){
			if(resp.error==false){
				alert("添加员工工作总结成功");
				$("#sumModal").modal("hide");
				queryEmployeeSum();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 修改员工工作总结
	 */
	$scope.updateSum = function(selectedSum){
		var param = {};
		param = selectedSum;
		erp_employeeMgrService.updateEmployeeSum(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#sumModal").modal("hide");
				queryEmployeeSum();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 删除员工工作总结
	 */
	$scope.deleteSum = function(){
		var param = {};
		param.id=$scope.selectedSum.id;
		erp_employeeMgrService.deleteEmployeeSum(param,function(resp){
			if(resp.error==false){
				alert("删除员工工作总结成功");
				$("#DelModal").modal("hide");
				queryEmployeeSum();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.sumDelete=false;
		});
	}
	
	/**
	 * 查询员工奖惩信息
	 */
	function queryEmployeeRew(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		erp_employeeMgrService.queryEmployeeRew(param,function(resp){
			if(resp.error==false){
				$scope.employeeRew=resp.data;
			}
		});
	}
	
	/**
	 * 添加员工奖惩信息
	 */
	$scope.addRew = function(selectedRew){
		var param = {};
		selectedRew.employee_id=$scope.selectedEmp.id;
		param=selectedRew;
		erp_employeeMgrService.addEmployeeRew(param,function(resp){
			if(resp.error==false){
				alert("添加员工奖惩信息成功");
				$("#rewModal").modal("hide");
				queryEmployeeRew();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 修改员工奖惩信息
	 */
	$scope.updateRew = function(selectedRew){
		var param = {};
		param = selectedRew;
		erp_employeeMgrService.updateEmployeeRew(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#rewModal").modal("hide");
				queryEmployeeRew();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		});
	}
	
	/**
	 * 删除员工奖惩信息
	 */
	$scope.deleteRew = function(){
		var param = {};
		param.id=$scope.selectedRew.id;
		erp_employeeMgrService.deleteEmployeeRew(param,function(resp){
			if(resp.error==false){
				alert("删除员工奖惩信息成功");
				$("#DelModal").modal("hide");
				queryEmployeeRew();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.rewDelete=false;
		});
	}
	
	/**
     * 上传头像
     */
	$scope.headPic=function(){
		//var selectedEmpId=$scope.selectedEmp.id;
		window.location.href='templates/hrm/uploadImage/headPic?selectedEmpId='+$scope.selectedEmp.id;
		
	}
	
	
	 /**
     * 查询归属组织
     */
   function queryOrg(){
    	var param={};
        OrgService.get(param,function(resp){
            if(resp.error == false){
                $scope.orgs = resp.data;
            }
        });
    };
   
   
   /**
    * 查询岗位
    */
   function queryPost(){
	   var param={};
	   PostService.queryPost(param,function(resp){
		   if(resp.error == false){
			   $scope.postList = resp.data;
		   }
	   });
   };

    $scope.cancelPanel = function(){
        $scope.addEmp = false;
    }

    $scope.cancelSetPostPanel=function(){
    	$scope.addEmp = false;
    	$scope.empId='';
    	$scope.queryInfo(1);
    }
    
    $scope.setPostPanel=function(emp){
    	$scope.addEmp = true;
    	$scope.empId=emp.id;
    	queryPostByEmpId(emp.id);
    }
    
    
    //按照员工id查询岗位信息
    function queryPostByEmpId(emp_id){
    	var param={};
    	param.employee_id=emp_id;
    	erp_employeeMgrService.queryPostByEmpId(param,function(resp){
    		if(resp.error==false){
    			$scope.postByEmpList=resp.data;
    		}
    	});
    }
   
    
    //点击保存，提交
    $scope.savePost=function(){
    	console.log($scope.selectedPost);
    	var param={};
    	if($scope.empId!=null&&$scope.empId!=''){
    		param.emp_id=$scope.empId;
    	}else{
    		alert("请选择员工");
    		return;
    	}
    	if($scope.selectedPost.bu_Id!=null && $scope.selectedPost.bu_Id!=''){
    		param.bu_Id=$scope.selectedPost.bu_Id;
    	}else{
    		alert("请选择团队");
    		return;
    	}
    	if($scope.selectedPost.branch_Id!=null && $scope.selectedPost.branch_Id!=''){
    		param.branch_Id=$scope.selectedPost.branch_Id;
    	}else{
    		alert("请选择校区");
    		return;
    	}
    	if($scope.selectedPost.post_Id!=null && $scope.selectedPost.post_Id!=''){
    		param.post_Id=$scope.selectedPost.post_Id;
    	} else{
    		alert("请选择岗位");
    		return;
    	}
    
    	erp_employeeMgrService.addPost(param,function(resp){
    		if(resp.error==false){
    			alert("添加成功");
    			queryPostByEmpId(param.emp_id);
    			$scope.bu_Id='';
    	    	$scope.branch_Id='';
    	    	$scope.post_Id='';
    		}
    	});
    }
   
    //删除员工岗位
    $scope.removePost=function(d){
    	var param={};
    	if(d.id!=null && d.id!=''){
    		param.id=d.id;
    	}else{
    		alert("请选择要删除的岗位");
    	}
    	erp_employeeMgrService.removePost(param,function(resp){
    		if(resp.error==false){
    			alert("操作成功");
    			queryPostByEmpId(d.emp_id);
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
        });
    }
    
    //根据团队id查询校区
    $scope.changeBuId=function(buId){
    	var param={};
    	param.buId=buId;
    	OrgService.querySch(param,function(resp){
               if(resp.error == false){
                   $scope.branchsList = resp.data;
                   $scope.branchsList.unshift(
                	{
                		id:'-1',
                		org_name:'全部校区'
                	}	   
                   );
               }
           });
    }
    
    /**
     * 启用/禁用员工
     */
    $scope.setStatus=function(emp){
    	var param={};
    	param.id=emp.id;
    	erp_employeeMgrService.setStatus(param,function(resp){
    		if(resp.error==false){
    			alert("操作成功");
    			$scope.queryInfo($scope.pageNum);
    		}
    	});
    }
    
   
    $scope.queryInfo(1);
    queryPost();
  //查询数据字典所有子项
	queryDictSub();
	queryBu();

}


