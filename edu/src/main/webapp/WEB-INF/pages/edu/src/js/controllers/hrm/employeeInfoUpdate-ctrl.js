/**
 * 
 */
angular.module('ework-ui')
    .controller('hrmEmployeeInfoUpdateCtrl', [
        '$scope',
        '$log',
        '$state',
        '$cookieStore',
        '$rootScope',
        'OrgService',
        'PostService',
        'DictItemService',
        'EmployeeExtService',
        'EmployeeManageService',
        'hrmEmployeeService',
        hrmEmployeeInfoUpdateCtrl]);

function hrmEmployeeInfoUpdateCtrl($scope,
                  $log,
                  $state,
                  $cookieStore,
                  $rootScope,
                  OrgService,
                  PostService,
                  DictItemService,
                  EmployeeExtService,
                  EmployeeManageService,
                  hrmEmployeeService
    ){
	//员工列表
	$scope.employeeList = {};
	$scope.itemOperateType = '';
    $scope.selectedItem = {};
    $scope.employee = [];
    $scope.edu={};
    $scope.paginationBars = [];
    $scope.enterTypeList=[];
    //岗位查询列表
    $scope.postList=[];
    $scope.detailPage = 'templates/hrm/employee/employee_info.html?_='+(new Date()).getTime();

    $scope.employeeId = null;
    $scope.queryType = '';
    //暂存的订单
    $scope.employeeId = $("#rootIndex_employeeId").val();


    function queryEmployeeInfo(){
        var param = {};
        param.employeeId = $scope.employeeId;
        hrmEmployeeService.query(param,function(resp){
            if(!resp.error){
                $scope.selectedEmp = resp.data;
                $('title').text(''+$scope.selectedEmp.employee_name);
                $scope.toQueryItem($scope.selectedEmp);
            }else{
                alert(resp.message);
            }
        })
    }

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
    	$scope.addEmp=false;
    	//$('#EmpPanel').modal({"show":true,"backdrop":'static'});
    	$scope.selectedEmp=emp;		//提取选中的员工id方便查询教育经历 ，工作经历等信息
    	//param.id = emp.id;
    	
    	getRet(emp);
    	
        $scope.itemOperateType = 'query';
	    queryEmployeeEdu();
	    queryEmployeeExp();
	    queryEmployeeSum();
	    queryEmployeeRew();
	};
	
	/**
	 * 查询选中的员工定义表中的动态字段
	 */
	function getRet(emp){
		var param={};
    	EmployeeExtService.queryField(param,function(resp){
    		if(resp.error==false){
    			$scope.ret = resp.data;
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
		EmployeeManageService.query(param,function(resp){
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
                var str= ret[i].fieldKey;
                if(employee[0]){
                    ret[i].fieldKey=employee[0][str];
                }else{
                    ret[i].fieldKey = null;
                }
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
	    $('#EmpPanel').modal('show');
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
		$("#eduModal").modal("show");
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
		EmployeeManageService.add(param,function(resp){
			 if(resp.error == false){
				$('#EmpPanel').modal('hide');
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
		EmployeeManageService.updateEmployeeStatic($scope.selectedEmp,function(resp){
			if(resp.error==false){
			
			}
		})
		EmployeeManageService.update(param,function(resp){
			 if(resp.error==false){
				$('#EmpPanel').modal('hide');
				alert("修改成功");
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
		EmployeeManageService.addEmployeeEdu(param,function(resp){
			if(resp.error==false){
				alert("添加员工教育经历成功");
				$("#eduModal").modal("hide");
				queryEmployeeEdu();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 修改员工教育经历
	 */
	$scope.updateEdu = function(selectedEdu){
		var param = {};
		param = selectedEdu;
		EmployeeManageService.updateEmployeeEdu(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#eduModal").modal("hide");
				queryEmployeeEdu();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 删除员工教育经历
	 */
	$scope.deleteEdu = function(){
		var param = {};
		param.id=$scope.selectedEdu.id;
		EmployeeManageService.deleteEmployeeEdu(param,function(resp){
			if(resp.error==false){
				alert("删除员工教育经历成功");
				$("#DelModal").modal("hide");
				queryEmployeeEdu();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.eduDelete=false;
		})
	}
	
	/**
	 * 查询员工教育经历
	 */
	function queryEmployeeEdu(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		EmployeeManageService.queryEmployeeEdu(param,function(resp){
			if(resp.error==false){
				$scope.employeeEdu=resp.data;
			}
		})
	}
	
	/**
	 * 查询员工工作经历
	 */
	function queryEmployeeExp(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		EmployeeManageService.queryEmployeeExp(param,function(resp){
			if(resp.error==false){
				$scope.employeeExp=resp.data;
			}
		})
	}
	
	/**
	 * 添加员工工作经历
	 */
	$scope.addExp = function(selectedExp){
		var param = {};
		selectedExp.employee_id=$scope.selectedEmp.id;
		param=selectedExp;
		EmployeeManageService.addEmployeeExp(param,function(resp){
			if(resp.error==false){
				alert("添加员工工作经历成功");
				$("#expModal").modal("hide");
				queryEmployeeExp();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 修改员工工作经历
	 */
	$scope.updateExp = function(selectedExp){
		var param = {};
		param = selectedExp;
		EmployeeManageService.updateEmployeeExp(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#expModal").modal("hide");
				queryEmployeeExp();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 删除员工工作经历
	 */
	$scope.deleteExp = function(){
		var param = {};
		param.id=$scope.selectedExp.id;
		EmployeeManageService.deleteEmployeeExp(param,function(resp){
			if(resp.error==false){
				alert("删除员工工作经历成功");
				$("#DelModal").modal("hide");
				queryEmployeeExp();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.expDelete=false;
		})
	}
	
	/**
	 * 查询员工工作总结
	 */
	function queryEmployeeSum(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		EmployeeManageService.queryEmployeeSum(param,function(resp){
			if(resp.error==false){
				$scope.employeeSum=resp.data;
			}
		})
	}
	
	/**
	 * 添加员工工作总结
	 */
	$scope.addSum = function(selectedSum){
		var param = {};
		selectedSum.employee_id=$scope.selectedEmp.id;
		param=selectedSum;
		EmployeeManageService.addEmployeeSum(param,function(resp){
			if(resp.error==false){
				alert("添加员工工作总结成功");
				$("#sumModal").modal("hide");
				queryEmployeeSum();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 修改员工工作总结
	 */
	$scope.updateSum = function(selectedSum){
		var param = {};
		param = selectedSum;
		EmployeeManageService.updateEmployeeSum(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#sumModal").modal("hide");
				queryEmployeeSum();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 删除员工工作总结
	 */
	$scope.deleteSum = function(){
		var param = {};
		param.id=$scope.selectedSum.id;
		EmployeeManageService.deleteEmployeeSum(param,function(resp){
			if(resp.error==false){
				alert("删除员工工作总结成功");
				$("#DelModal").modal("hide");
				queryEmployeeSum();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.sumDelete=false;
		})
	}
	
	/**
	 * 查询员工奖惩信息
	 */
	function queryEmployeeRew(){
		var param = {};
		param.employee_id=$scope.selectedEmp.id;
		EmployeeManageService.queryEmployeeRew(param,function(resp){
			if(resp.error==false){
				$scope.employeeRew=resp.data;
			}
		})
	}
	
	/**
	 * 添加员工奖惩信息
	 */
	$scope.addRew = function(selectedRew){
		var param = {};
		selectedRew.employee_id=$scope.selectedEmp.id;
		param=selectedRew;
		EmployeeManageService.addEmployeeRew(param,function(resp){
			if(resp.error==false){
				alert("添加员工奖惩信息成功");
				$("#rewModal").modal("hide");
				queryEmployeeRew();
			}else{
				alert("添加失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 修改员工奖惩信息
	 */
	$scope.updateRew = function(selectedRew){
		var param = {};
		param = selectedRew;
		EmployeeManageService.updateEmployeeRew(param,function(resp){
			if(resp.error==false){
				alert("修改成功");
				$("#rewModal").modal("hide");
				queryEmployeeRew();
			}else{
				alert("修改失败："+resp.message+",请截图反馈给客服");
			}
		})
	}
	
	/**
	 * 删除员工奖惩信息
	 */
	$scope.deleteRew = function(){
		var param = {};
		param.id=$scope.selectedRew.id;
		EmployeeManageService.deleteEmployeeRew(param,function(resp){
			if(resp.error==false){
				alert("删除员工奖惩信息成功");
				$("#DelModal").modal("hide");
				queryEmployeeRew();
			}else{
				alert("删除失败："+resp.message+",请截图反馈给客服");
			}
			$scope.rewDelete=false;
		})
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
        })
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
	   })
   };
    if($scope.employeeId){
        //查询当前员工的基本信息
        queryEmployeeInfo();
    }

    //queryOrg();
    queryPost();
  //查询数据字典所有子项
	queryDictSub();

}


