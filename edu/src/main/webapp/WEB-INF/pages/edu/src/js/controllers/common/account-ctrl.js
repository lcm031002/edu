/**
 * Created by Liyong.zhu on 2016/6/12.
 */
angular.module('ework-ui')
    .controller('AccountCtrl', [
        '$scope',
        '$log',
        '$state',
        'RoleService',
        'CompanyService',
        'BranchsService',
        'AccountService',
        'BranchTreeService',
        AccountCtrl]);


function AccountCtrl(
    $scope,
    $log,
    $state,
    RoleService,
    CompanyService,
    BranchsService,
    AccountService,
    BranchTreeService
    ){

    //列表当前页码
    $scope.currentPage = 1;
    //列表当前总页码
    $scope.totalPage = 1;
    //列表当前页行数
    $scope.pageSize = 50;

    //账户列表
    $scope. accountList = [];
    //公司列表
    $scope. companyList = [];
    //校区列表
    $scope.branchsList = [];
    //操作类型
    $scope.operateType = '';
    //当前选中的角色
    $scope.curAccountRole = {};
    $scope.role_search_info='';
    $scope.rolePage = {
    		roleCompany:null,
    		roleBranch:null,
            roleSearchInfo:''
        }

    /**
     * 查询账户
     */
    $scope.queryAccount = function(){
        var param = {};
        if($scope.selectedCompany){
            param.p_selectedCompany = $scope.selectedCompany.id;
        }
        if($scope.branch){
            param.p_selectedBranch = $scope.branch.id;
        }
      $log.log("selectedBranch="+ param.p_selectedBranch);  
        if($scope.search_info){
            param.p_searchInfo = $scope.search_info;
        }
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;
        $scope.isLoading = 'loading...';
        $scope.curAccount = null;

        AccountService.get(param,function(resp){
            $scope.isLoading = '';
            if(resp.error ==  'false'){
                $scope. accountList = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
            }
        });
    }

    /**
     * 去添加账户
     */
    $scope.toAddAccountPanel = function(){
        $scope.operateType = 'addAccount';
        $scope.curAccount = {};
        $("#accountMgrPanel").modal('show');
    }

    /**
     * 去修改账户
     * @param account
     */
    $scope.toUpdateAccountPanel = function(account){
        $scope.curAccount = account;
        $scope.operateType = 'updateAccount';
        $("#accountMgrPanel").modal('show');
    }

    /**
     * 去删除账户
     * @param account
     */
    $scope.toDeleteAccountPanel = function(account){
        $scope.curAccount = account;
        $scope.operateType = 'deleteAccount';
        $scope.comfirmAccount();
    }

    /**
     * 去添加角色
     */
    $scope.toAddRolePanel = function(){
        if(!$scope.curAccount){
            alert("请选择账户！");
            return;
        }
        $scope.curAccountRole = {};
        $("#accountMgrRolePanel").modal('show');
        queryAllRole();
    }

    /**
     * 查询角色
     */
    $scope.queryAllRole = function(){
        queryAllRole();
    }

    /**
     * 账户确认
     */
    $scope.comfirmAccount = function(){
        if($scope.operateType == 'addAccount'){
            addAccount($scope.curAccount);
        } else if ($scope.operateType == 'updateAccount'){
            updateAccount($scope.curAccount);
        } else if ($scope.operateType == 'deleteAccount'){
            deleteAccount($scope.curAccount);
        }
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
        queryAllRole();
    }

    /**
     * 更新账户
     */
    $scope.updateAccount = function(){
        if(!$scope.curAccount){
           alert("请选择账户！");
            return;
        }
        updateAccount($scope.curAccount);
    }
    
    
    $scope.queryBranchsWithId= function (company){
    	var param={};
    	if( company.id)
    	param.companyId=company.id;
    	else{
    		param.companyId=company;
    	}
        BranchsService.get(param,function(resp){
            if(resp.error == 'false'||resp.error==false){
                $scope.branchsList = resp.data;
            }
        });
    }

    function deleteAccount(account){
        var param = {};
        param.accountId=account.id;
        AccountService.delete(param,function(resp){
            if(resp.error ==  'false'){
                alert("禁用成功！");
                $scope.queryAccount();
            }else{
                alert("删除失败："+resp.message+",请截图反馈给ERP客服！");
            }
        })
    }

    function updateAccount(account){
        var param = account;
        var selected = [];

        param.selectedBranch = genSelectedMenus();
        if(!param.selectedBranch||  param.selectedBranch.length==0)
        	{
        	 alert("未选择任何的账户校区!");
        	 return;
        	}
        AccountService.update(param,function(resp){
            if(resp.error ==  'false'){
                alert("修改成功！");
            }else{
                alert("修改失败："+resp.message+",请截图反馈给ERP客服！");
            }
        })
    }

    function addAccount(account){
        var param = account;
        if($scope.curAccount.selectedCompany)
        	param.companyId=$scope.curAccount.selectedCompany.id;
        AccountService.add(param,function(resp){
            if(resp.error ==  'false'){
                alert("添加成功！");
                $scope. accountList.push(account);
            }else{
                alert("添加失败："+resp.message+",请截图反馈给ERP客服！");
            }
        })
    }

    function queryRole(account){
        var param = {};
        param.accountId = account.id;
        $scope.isLoadingRole = 'loading...';
        RoleService.queryRoleWithAccount(param,function(resp){
            $scope.isLoadingRole = '';
           if(resp.error ==  'false'){
               account.roleList = resp.data;
           }
        });
    }

    function queryAllRole(){
        var param = {};
        $scope.isLoadingRoleList = 'loading...';
        if($scope.rolePage.roleCompany)
        param.p_selectedCompany= $scope.rolePage.roleCompany;
        if($scope.rolePage.roleBranch)
        param.p_selectedBranch= $scope.rolePage.roleBranch;
        param.p_searchInfo = $scope.rolePage.roleSearchInfo;
        param.p_accountId=$scope.curAccount.id;//当前选中的账户编码传递至后台过滤出选中账户拥有过的角色
        RoleService.get(param,function(resp){
            $scope.isLoadingRoleList = '';
            if(resp.error ==  'false'){
                var roleList = [];
                $.each(resp.data,function(i,role){
                    var exist = false;
                    $.each($scope.curAccount.roleList,function(ii,r){
                        if(role.id == r.id){
                            exist = true;
                        }
                    });
                    if(!exist){
                        roleList.push(role);
                    }
                });
                $scope.roleList = roleList;
            }
        });
    }

    function queryBranchTree(account){
    	$('#branchTree').jstree("destroy").empty();
        var param = {};
        param.accountId = account.id;
        BranchTreeService.queryTree(param,function(resp){
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
     * 查询归属公司
     */
    function queryCompany(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.companyList = resp.data;
            }
        })
    }

    /**
     * 查询归属校区
     */
    function queryBranchs(){
        BranchsService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.branchsList = resp.data;
            }
        })
    }

    $scope.queryAccount();

    queryCompany();

    queryBranchs();

}