angular.module('ework-ui')
.controller('erp_AccountCtrl',[
	'$scope',
	'$log',
	'$state',
	'$document',
	'$uibMsgbox',
	'erp_RoleService',
	'erp_organizationService',
	'PostService',
	'erp_employeeMgrService',
	'erp_AccountService',
    erp_AccountCtrl]);

function erp_AccountCtrl($scope,
		$log,
		$state,
		$document,
		$uibMsgbox,
		erp_RoleService,
		erp_organizationService,
        PostService,
		erp_employeeMgrService,
		erp_AccountService){
	$scope.AccountList = {};
	$scope.employeeList = [];
	$scope.operateType = '';
	$scope.role_search_info='';
	$scope.curAccountRole = {};
	//团队列表
	$scope.buList = [];
	//校区列表
	$scope.branchsList = [];
	
	  $scope.rolePage = {
	            roleSearchInfo:''
	        }

    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function(){
            $scope.queryAccount();
        }
    };
	/**
	 * 查询账户列表
	 */
	$scope.queryAccount = function(){
		var param = {};
		if($scope.accountName){
		param.accountName=$scope.accountName;
		}
		if($scope.employeeId){
			param.employeeId=$scope.employeeId;
		}
		if($scope.employeeName){
			param.employeeName=$scope.employeeName;
		}

        param.pageSize=$scope.paginationConf.itemsPerPage; // 每页显示条数
        param.currentPage=$scope.paginationConf.currentPage; // 要获取的第几页的数据

		$scope.isLoading = 'loading...';
		$scope.accountList = [];
		erp_AccountService.queryAccountForPage(param,function(resp){
            $scope.isLoading = '';
			if(!resp.error){
				$scope.accountList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
			}
		})
	};

    /**
     * 去添加账户
     */
    $scope.toAddAccountPanel = function(){
        $scope.operateType = 'addAccount';
        $scope.curAccount = {};
        $("#accountMgrPanel").modal('show');
//        querySch();
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
        // debugger;
    	$scope.title=account.status==1?"禁用":"启用"+"账户";
    	$scope.modalBody="确认"+$scope.title+"当前【"+account.accountName+"】账户吗？";
    	$("#account_remove_confirm_dialog").modal("show");
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
        	if(checkAccountIsPassed($scope.curAccount)==false)
        		return;
            updateAccount($scope.curAccount);
        } else if ($scope.operateType == 'deleteAccount'){
            $scope.deleteAccount($scope.curAccount);
        }
    }


    function checkAccountIsPassed(account){
    	if(!account.accountName||account.accountName==''){
    		return false;
    	}
    	if(account.employeeId==undefined||account.employeeId==null){
    		return false;
    	}
    	if(!account.password||account.password==''){
    		return false;
    	}
    	/*if(!account.buId||account.buId==''){
    		return false;
    	}
    	if(!account.branchId||account.branchId==''){
    		return false;
    	}*/
    	return true;
    }

    /**
     * 去添加角色
     */
    $scope.toAddRolePanel = function(){
        if(!$scope.curAccount.checked){
        	$uibMsgbox.confirm("请选择账户！");
            return;
        }
        $scope.rolePage = {};
        $scope.roleListCopy = [];
        angular.copy($scope.curAccount.roleList,$scope.roleListCopy);
        $("#accountMgrRolePanel").modal('show');
        $scope.queryAllRole();
    }

    /**
     * 移除角色
     * @param role
     */
    $scope.removeRole = function(role){
        if(!$scope.curAccount){
        	$uibMsgbox.confirm("请选择账户！");
            return;
        }

        $uibMsgbox.confirm("是否确认删除？", function (result) {
            if(result == 'yes') {
            	erp_AccountService.removeAccountRoleById({accountRoleId : role.accountRoleId}, function(resp) {
        			if (!resp.error) {
        				$uibMsgbox.success("删除成功");
        				var tmpRoleList = [];
        				if ($scope.curAccount&& $scope.curAccount.roleList) {
        					$.each($scope.curAccount.roleList, function(idx, r) {
        						if (r.id != role.id) {
        							tmpRoleList.push(r);
        						}
        					});
        					$scope.curAccount.roleList = tmpRoleList;
        					$scope.queryAllRole();
        				}
        			} else {
        				$uibMsgbox.error(resp.message);
        			}
        		});
        	}
        });
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
        	$uibMsgbox.confirm("请选择账户！");
        }
        if(!$scope.roleListCopy){
            $scope.roleListCopy = [];
        }
       if(!_.some($scope.roleListCopy,{id:role.id})) {
           $scope.roleListCopy.push({id:role.id, roleName:role.roleName});
       }
        //查询角色
        //$scope.queryAllRole();
    }
    /**
     * 选择角色
     * @param role
     */
    $scope.disSelectedRole = function(role){
        if(!$scope.curAccount){
        	$uibMsgbox.confirm("请选择账户！");
        }
        _.remove($scope.roleListCopy,role);
    }

    /**
     * 更新账户角色关系
     */
    $scope.updateAccountRole = function(){
        if(!$scope.curAccount.checked){
        	$uibMsgbox.confirm("请选择账户！");
            return;
        }
        var saveData = {};
        angular.copy($scope.curAccount,saveData);
        saveData.roleList = $scope.roleListCopy;
        updateAccountRole(saveData);
    }

    function updateAccountRole(account){
        var param = account;
        erp_AccountService.updateAccountRole(param,function(resp){
        	if(resp.error==false){
        		$uibMsgbox.success(account.accountName+"  账户角色关系修改成功！");
        		queryRole(account);
        	}else{
        		$uibMsgbox.error("修改失败："+resp.message+",请截图反馈给客服！");
        	}
        })
    }

    /**
     * 更新账户组织关系
     */
    $scope.updateAccountOrg = function(){
    	if(!$scope.curAccount.checked){
    		$uibMsgbox.confirm("请选择账户！");
        }
        updateAccountOrg($scope.curAccount);
    }

    function updateAccountOrg(account){

    	var param = {};
    	var selected = [];
    	param.selectedBranch = genSelectedMenus();
    	param.accountId=account.id;
    	erp_AccountService.updateAccountOrg(param,function(resp){
    		if(resp.error==false){
    			$uibMsgbox.success(account.accountName+"  账户组织关系修改成功！");
    			queryBranchTree(account);
    		}else{
    			$uibMsgbox.error("修改失败："+resp.message+",请截图反馈给客服！");
    		}
    	})
    }

    /**
     * 修改账户
     */
    function updateAccount(account){
        var param = {};
        param.id=account.id;
        param.accountName=account.accountName;
        param.employeeId=account.employeeId;
        param.password=account.password;
        //var selected = [];
        //param.selectedBranch = genSelectedMenus();
        param.oldPassword=$scope.comparePassword;
        //判断新旧密码是否一样,如果一样将新密码传递为空
        if(account.oldpassword==account.password||$scope.comparePassword==null){
        	param.password='';
        }
       // account.origalAccountName=$scope.origalAccountName;//修改之前的账户名称
        erp_AccountService.update(param,function(resp){
            if(resp.error ==  false){
            	$uibMsgbox.success("修改成功！");
                $("#accountMgrPanel").modal("hide");
                $scope.queryInfo(1);
                $scope.comparePassword=null;
                $scope.origalAccountName=null;
            }else{
            	$uibMsgbox.error("修改失败："+resp.message+",请截图反馈给ERP客服！");
            }


        })
    }

    /**
     * 添加账户
     */
    function addAccount(account){
        var param = account;
        erp_AccountService.addAccount(param,function(resp){
            if(resp.error==false){
            	$uibMsgbox.success("添加成功！");
                $('#accountMgrPanel').modal('hide');
                $scope.queryInfo(1);
            }else{
            	$uibMsgbox.error("添加失败："+resp.message+",请截图反馈给客服！");

            }
        })
    }

    /**
     * 禁用/启用账户
     */
    $scope.deleteAccount = function (account){
        // debugger;
    	$("#account_remove_confirm_dialog").modal("hide");
    	var param = {};
    	param.accountId=$scope.curAccount.id;
        param.status=$scope.curAccount.status==1?2:1;
    	erp_AccountService.remove(param,function(resp){
    		if(resp.error==false){
    			$uibMsgbox.success("操作成功");
    			$scope.queryInfo($scope.pageNum);
    		} else {
                $uibMsgbox.error(resp.message);
            }
    	})
    }

    /**
     *根据账户id查询角色信息
     */
    function queryRole(account){
        var param = {};
        param.user_id = account.id;
        $scope.isLoadingRole = 'loading...';
        erp_AccountService.queryRoleWithAccount(param,function(resp){
            $scope.isLoadingRole = '';
           if(resp.error ==  false){
               $scope.curAccount.roleList = resp.data;
           }
        });
    }

    /**
     * 查询除已有角色外的所有角色，给账户添加角色时使用
     */
    $scope.queryAllRole = function(){
        var param = {};
        param.status=1;
        $scope.isLoadingRoleList = 'loading...';
        param.roleName = $scope.rolePage.roleSearchInfo;
        param.accountId=$scope.curAccount.accountId;//当前选中的账户编码传递至后台过滤出选中账户拥有过的角色
        param.pageNum=$scope.pageNum;
        param.pageSize=10;

        erp_RoleService.queryRoleForPage(param,function(resp){
            $scope.isLoadingRoleList = '';
            if(resp.error ==  false){
                //不过滤角色
                $scope.roleList = resp.data;
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
        param.accountId = account.id;
        erp_organizationService.queryOrgWithAccount(param,function(resp){
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
    	erp_employeeMgrService.queryEmployeeInfo(param,function(resp){
             if(resp.error == false){
                 $scope.employeeList = resp.data;
             }
         });
    }


    /**
     * 选择一个员工
     */
    $scope.selectedEmployee=function(employee){
    	$scope.curAccount.employeeId=employee.id;
    	$scope.curAccount.employeeName=employee.employee_name;
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
       erp_organizationService.querySch(param,function(resp){
           if(resp.error == false){
               $scope.branchsList = resp.data;
           }
       });
   }

   /**
    * 查询归属团队
    */
   function queryBu() {
       erp_organizationService.queryBu({},function(resp){
           if(!resp.error){
               $scope.buList = resp.data;
           }
       })
   }

    /**
     * 查询岗位
     */
    function queryPost(){
        var param={};
        PostService.queryPost(param,function(resp) {
            if(!resp.error){
                $scope.postList = resp.data;
            }
        });
    };

    $scope.selectedPost = {};
    $scope.setPostPanel = function(emp) {
        $scope.empId = emp.employeeId;
        queryPostByEmpId(emp.employeeId);
        $scope.selectedPost.bu_Id = '';
        $scope.selectedPost.post_Id = '';
        $('#setEmpPostModal').modal('show');
    }

    //按照员工id查询岗位信息
    function queryPostByEmpId(emp_id) {
        erp_employeeMgrService.queryPostByEmpId({
            employee_id : emp_id
        }, function(resp) {
            if(!resp.error) {
                $scope.postByEmpList=resp.data;
            }
        });
    }

    //点击保存，提交
    $scope.savePost = function() {
        var param = {};
        if(isEmpty($scope.empId)){
            $uibMsgbox.confirm("请选择员工");
            return;
        } else {
            param.emp_id = $scope.empId;
        }
        if(isEmpty($scope.selectedPost.bu_Id)){
            $uibMsgbox.confirm("请选择团队");
            return;
        }else{
            param.bu_Id = $scope.selectedPost.bu_Id;
        }
        if(isEmpty($scope.selectedPost.post_Id)){
            $uibMsgbox.confirm("请选择岗位");
            return;
        } else{
            param.post_Id = $scope.selectedPost.post_Id;
        }

        erp_employeeMgrService.addPost(param, function(resp){
            if(resp.error==false){
                $uibMsgbox.success("添加成功");
                queryPostByEmpId(param.emp_id);
                $scope.selectedPost.bu_Id = '';
                $scope.selectedPost.post_Id = '';
            }
        });
    }

    //删除员工岗位
    $scope.removePost=function(post) {
        var param={};
        if(post.id) {
            param.id = post.id;
        } else {
            $uibMsgbox.confirm("请选择要删除的岗位");
        }
        erp_employeeMgrService.removePost(param, function(resp) {
            if(!resp.error) {
                $uibMsgbox.success("操作成功");
                queryPostByEmpId(post.emp_id);
            }
        });
    }

    /**
     * 启用/禁用员工
     */
    $scope.setStatus = function(emp){
        erp_employeeMgrService.setStatus({
            id : emp.id
        }, function(resp) {
            if(!resp.error){
                $uibMsgbox.success("操作成功");
                $scope.queryInfo($scope.pageNum);
            }
        });
    }

    $scope.queryAccount();
    //queryPost();
    //queryBu();
}