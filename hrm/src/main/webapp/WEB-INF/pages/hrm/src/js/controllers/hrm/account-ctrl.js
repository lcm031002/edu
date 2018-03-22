/**
 * 
 */
angular.module('ework-ui')
.controller('AccountCtrl',[
	'$scope',
	'$log',
	'$state',
	'$document',
	'RoleService',
	'OrgService',
	'EmployeeManageService',
	'AccountService',
    AccountCtrl]);

function AccountCtrl($scope,
		$log,
		$state,
		$document,
		RoleService,
		OrgService,
		EmployeeManageService,
		AccountService){
	$scope.AccountList = {};
	$scope.employeeList = [];
	$scope.operateType = '';
	$scope.role_search_info='';
	$scope.curAccountRole = {};
	//团队列表
	$scope.buList = [];
	$scope.paginationBars=[];
	//校区列表
	$scope.branchsList = [];
	
	  $scope.rolePage = {
	            roleSearchInfo:''
	        }
	/**
	 * 查询账户列表
	 */
	function queryAccount(){
		var param = {};
		/*if($scope.selectedAreas){
		param.orgId=$scope.selectedAreas;
		}*/
		if($scope.accountName){
		param.accountName=$scope.accountName;
		}
		if($scope.employeeId){
			param.employeeId=$scope.employeeId;
		}
		if($scope.employeeName){
			param.employeeName=$scope.employeeName;
		}
		
		$scope.isLoading = 'loading...';
		$scope.accountList = {};
		$scope.pageParam = {
				pageNum:$scope.pageNum,
				pageSize:10
		};
		param.pageParam = $scope.pageParam;
		AccountService.queryAccountForPage(param,function(resp){
			if(resp.error == false){
				$scope.accountList = resp.data;
				$scope.pageParam = resp.pageParam;
				$scope.isLoading = '';
				 if ($scope.pageParam.pageNum > 1 && $scope.pageParam.pageNum < $scope.pageParam.pages) {
  	     	       $scope.paginationBars = [$scope.pageParam.pageNum - 1,$scope.pageParam.pageNum, $scope.pageParam.pageNum + 1];
  	         } else if ($scope.pageParam.pageNum == 1 && $scope.pageParam.pages > 1) {
  	     	       $scope.paginationBars = [ $scope.pageParam.pageNum, $scope.pageParam.pageNum + 1];
  	         } else if ($scope.pageParam.pageNum == $scope.pageParam.pages && $scope.pageParam.pages > 1) {
  	     	       $scope.paginationBars = [ $scope.pageParam.pageNum - 1,$scope.pageParam.pageNum];
  	         }
			}
		})
	};
	
	$scope.queryInfo=function(pageIndex){
		$scope.pageNum=pageIndex;
		queryAccount();
	};
	
	
    /**
     * 去添加账户
     */
    $scope.toAddAccountPanel = function(){
        $scope.operateType = 'addAccount';
        $scope.curAccount = {};
        $("#accountMgrPanel").modal('show');
        querySch();
    }
    
    /**
     * 去修改账户
     * @param account
     */
    $scope.toUpdateAccountPanel = function(account){
    	//$scope.queryBranchsWithId(account.companyId);alert("aaa");
        $scope.curAccount = account;
        $scope.comparePassword=account.password;//比较密码是否发生变化
        $scope.origalAccountName=account.accountName;//比较账户名是否发生变化
        $scope.operateType = 'updateAccount';
        $("#accountMgrPanel").modal('show');
    }
    
    /**
     * 去禁用/启用账户
     * @param account
     */
    $scope.toDeleteAccountPanel = function(account){
    	$scope.operateType = 'deleteAccount';
    	$scope.curAccount = account;
    	$scope.title=account.status==1?"禁用":"启用"+"账户";
    	$scope.modalBody="确认"+$scope.title+"当前【"+account.accountName+"】账户吗？";
    	$("#confirm_dialog").modal("show");
    }
    
    /**
     * 账户确认
     */
    $scope.comfirmAccount = function(){
        if($scope.operateType == 'addAccount'){
        	$scope.isSbumitting=true;
        	if(checkAccountIsPassed($scope.curAccount)==false)
        		return;
            addAccount($scope.curAccount);
        } else if ($scope.operateType == 'updateAccount'){
        	$scope.isSbumitting=true;
        	$log.log($scope.curAccount);
        	if(checkAccountIsPassed($scope.curAccount)==false)
        		return;
            updateAccount($scope.curAccount);
        } else if ($scope.operateType == 'deleteAccount'){
            deleteAccount($scope.curAccount);
        }
    }
    
    
    function checkAccountIsPassed(account){
    	if(!account.accountName||account.accountName==''){
    		return false;
    	}
    	if(!account.employeeId||account.employeeId==''){
    		return false;
    	}
    	if(!account.password||account.password==''){
    		return false;
    	}
    	if(!account.buId||account.buId==''){
    		return false;
    	}
    	if(!account.branchId||account.branchId==''){
    		return false;
    	}
    	return true;
    }
    
    /**
     * 去添加角色
     */
    $scope.toAddRolePanel = function(){
        if(!$scope.curAccount){
            alert("请选择账户！");
            return;
        }
        $scope.rolePage = {};
        $("#accountMgrRolePanel").modal('show');
        $scope.queryAllRole();
        
    }
    
    /**
     * 移除角色
     * @param role
     */
    $scope.removeRole = function(role){
        if(!$scope.curAccount){
            alert("请选择账户！");
            return;
        }
        if($scope.curAccount.roleList){
            var roleList = [];
            $.each($scope.curAccount.roleList,function(i,r){
                if(r.id != role.id){
                    roleList.push(r);
                }
            });
            $scope.curAccount.roleList = roleList;
        }

    }
    
    /**
     * 选择行
     * @param row
     */
    $scope.checked = function(row){
    	if($scope.curAccount&& $scope.curAccount.hasOwnProperty('checked'))
        $scope.curAccount.checked=false;
        if(row.checked){
            row.checked = false;
        }else{
            row.checked = true;
            $scope.curAccount = row;
            //查询角色
            queryRole(row);
            //查询校区权限树
            queryBranchTree(row);
        }
    }
    
    /**
     * 选择角色
     * @param role
     */
    $scope.selectedRole = function(role){
        if(!$scope.curAccount){
            alert("请选择账户！");
        }
        if(!$scope.curAccount.roleList){
            $scope.curAccount.roleList = [];
        }
        $scope.curAccount.roleList.push(role);

        //查询角色
        $scope.queryAllRole();
    }
    
    /**
     * 更新账户角色关系
     */
    $scope.updateAccountRole = function(){
        if(!$scope.curAccount){
           alert("请选择账户！");
            return;
        }
        updateAccountRole($scope.curAccount);
    }
    
    function updateAccountRole(account){
        var param = account;
        AccountService.updateAccountRole(param,function(resp){
        	if(resp.error==false){
        		alert(account.accountName+"  账户角色关系修改成功！");
        		queryRole(account);
        	}else{
        		 alert("修改失败："+resp.message+",请截图反馈给客服！");
        	}
        })
    }
    
    /**
     * 更新账户组织关系
     */
    $scope.updateAccountOrg = function(){
        updateAccountOrg($scope.curAccount);
    }
    
    function updateAccountOrg(account){
    	var param = account; 
    	var selected = [];
    	param.selectedBranch = genSelectedMenus();
    	AccountService.updateAccountOrg(param,function(resp){
    		if(resp.error==false){
    			alert(account.accountName+"  账户组织关系修改成功！");
    			queryBranchTree(account);
    		}else{
    			alert("修改失败："+resp.message+",请截图反馈给客服！");
    		}
    	})
    }
    
    /**
     * 修改账户
     */
    function updateAccount(account){
        var param = account;
        //var selected = [];
        //param.selectedBranch = genSelectedMenus();
        account.oldpassword=$scope.comparePassword;
        //判断新旧密码是否一样,如果一样将新密码传递为空
        if(account.oldpassword==account.password||$scope.comparePassword==null){
        	account.password='';
        }
        account.origalAccountName=$scope.origalAccountName;//修改之前的账户名称
        AccountService.update(param,function(resp){
            if(resp.error ==  false){
                alert("修改成功！");
                $("#accountMgrPanel").modal("hide");
                $scope.queryInfo(1);
                $scope.comparePassword=null;
                $scope.origalAccountName=null;
            }else{
                alert("修改失败："+resp.message+",请截图反馈给ERP客服！");
            }
            
            
        })
    }
    
    /**
     * 添加账户
     */
    function addAccount(account){
        var param = account;
        AccountService.addAccount(param,function(resp){
            if(resp.error==false){
                alert("添加成功！");
                $('#accountMgrPanel').modal('hide');
                $scope.queryInfo(1);
            }else{
                alert("添加失败："+resp.message+",请截图反馈给客服！");
                
            }
        })
    }
    
    /**
     * 禁用/启用账户
     */
    function deleteAccount(account){
    	$("#confirm_dialog").modal("hide");
    	var param = {};
    	param.accountId=$scope.curAccount.accountId;
    	AccountService.remove(param,function(resp){
    		if(resp.error==false){
    			alert("操作成功");
    			$scope.queryInfo($scope.pageNum);
    		}
    	})
    }
    
    /**
     *根据账户id查询角色信息
     */
    function queryRole(account){
        var param = {};
        param.accountId = account.accountId;
        $scope.isLoadingRole = 'loading...';
        AccountService.queryRoleWithAccount(param,function(resp){
            $scope.isLoadingRole = '';
           if(resp.error ==  false){
               account.roleList = resp.data;
           }
        });
    }
    
    /**
     * 查询除已有角色外的所有角色，给账户添加角色时使用
     */
    $scope.queryAllRole = function(){
        var param = {};
        $scope.isLoadingRoleList = 'loading...';
        param.roleName = $scope.rolePage.roleSearchInfo;
        param.accountId=$scope.curAccount.accountId;//当前选中的账户编码传递至后台过滤出选中账户拥有过的角色
        $scope.pageParam = {
				pageNum:$scope.pageNum,
				pageSize:10
		};
        param.pageParam = $scope.pageParam;
        RoleService.queryRoleForPage(param,function(resp){
            $scope.isLoadingRoleList = '';
            if(resp.error ==  false){
                var roleList = [];
                $.each(resp.data,function(i,role){
                    var exist = false;
                    if($scope.curAccount&&$scope.curAccount.roleList){
                    $.each($scope.curAccount.roleList,function(ii,r){
                        if(role.id == r.id){
                            exist = true;
                        }
                    });
                    }
                    if(!exist){
                        roleList.push(role);
                    }
                });
                $scope.roleList = roleList;
				$scope.pageParam = resp.pageParam;

            }
        });
    }
    
    /**
     * 根据账户id查询账户校区列表树
     */
    function queryBranchTree(account){
    	$('#branchTree').jstree("destroy").empty();
        var param = {};
        param.accountId = account.accountId;
        OrgService.queryOrgWithAccount(param,function(resp){
                account.branchTree = resp.data;
                $('#branchTree').jstree({
                    "plugins" : ["types","checkbox"],
                    "types" : {
                        "menus" : {
                            "icon" : "fa fa-folder-o"
                        },
                        "leaf" : {
                            "icon" : "fa fa-file-text-o"
                        }
                    },
                    'core' : {
                        'data' : account.branchTree,
                        "check_callback" : function (operation, node, parent, position, more) {
                            //TODO
                        	 $(this).jstree("close_all");
                        }
                    }
                });
        })
    }
    
    function genSelectedMenus(){
        var instance = $('#branchTree').jstree(true);
        var selectedMenus = instance.get_selected(true);
        var selected = [];
        for(var key in selectedMenus){
        	var type=selectedMenus[key].type;
        	if(type=='leaf')
            selected.push(selectedMenus[key].id);
        }
        return selected;
    }
    
    /**
     * 查询角色列表
     */
    $scope.queryRole=function(pageIndex){
		$scope.pageNum=pageIndex;
		$scope.queryAllRole();
	};
	
	/**
     * 添加账户时绑定员工
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
    	$scope.curAccount.employeeId=employee.ID;
    	$scope.curAccount.employeeName=employee.EMPLOYEE_NAME;
    	$scope.onGoingQuery=false;
    	$scope.showQuery=false;
    }	
	
	$scope.showEmployeeQuery=function(){
    	$scope.showQuery=true;
    }
	
   $document.on('click', function(event){
	      var element=angular.element(event.target).attr("id");
	    	  $scope.$apply(function() {
	    		  if(element!='showSpan'
	    			  &&element!='selectSpan'
	    		      &&element!='employee_search_info'){
	    	       $scope.onGoingQuery = false;
	    	       $scope.showQuery=false;
	    		  }
	    		  
	    	 });
	      return $document.off('click', event);
	});
   
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
    * 查询归属校区
    */
 /*  function querySch(){
       OrgService.querySch({},function(resp){
           if(resp.error == false){
               $scope.branchsList = resp.data;
           }
       })
   }*/

	$scope.queryInfo(1);
	
	queryBu();
	
	
}