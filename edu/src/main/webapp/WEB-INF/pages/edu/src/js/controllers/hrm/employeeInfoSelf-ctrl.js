/**
 * 
 */
angular.module('ework-ui')
    .controller('hrmEmployeeInfoSelfCtrl', [
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
        'edu_LoginEmployeeService',
        hrmEmployeeInfoSelfCtrl]);

function hrmEmployeeInfoSelfCtrl($scope,
                  $log,
                  $state,
                  $cookieStore,
                  $rootScope,
                  OrgService,
                  PostService,
                  DictItemService,
                  EmployeeExtService,
                  EmployeeManageService,
                  hrmEmployeeService,
                  edu_LoginEmployeeService
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

    /**
     * 去添加工作总结
     */
    $scope.toAddSum = function(){
        $scope.sumUpdate=false;
        $scope.selectedSum={};
        $("#sumModal").modal({"show":true,"backdrop":'static'});
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


    if($rootScope.curEmployee){
        $log.log("is $rootScope.curEmployee "+$rootScope.curEmployee+",$rootScope.curEmployee.id is "+$rootScope.curEmployee.id);
        //暂存的订单
        $scope.employeeId = $rootScope.curEmployee.id;
        $scope.queryType = 'self';
        //查询当前员工的基本信息
        queryEmployeeInfo();
    }else{
        edu_LoginEmployeeService.query({},function(resp){
            if(!resp.error && resp.data){
                $scope.employeeId = resp.data.id;
                $scope.queryType = 'self';
                //查询当前员工的基本信息
                queryEmployeeInfo();
            }else{
                alert("message is" + resp.error);
            }
        });
    }




    //queryOrg();
    queryPost();
    //查询数据字典所有子项
	queryDictSub();

}


