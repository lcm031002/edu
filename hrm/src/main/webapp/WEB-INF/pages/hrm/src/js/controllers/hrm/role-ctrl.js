/**
 * 
 */
angular.module('ework-ui')
	.controller('RoleCtrl', [
		'$scope',
		'$log',
		'$state',
		'MenusService',
		'RoleService',
	    RoleCtrl]);

function RoleCtrl($scope,
					$log,
					$state,
					MenusService,
					RoleService){
	$scope.RoleList = {};
	
    //当前角色的权限列表
    $scope.privileges=[];
    $scope.paginationBars=[];
	$scope.operateType = '';
    $scope.dialog={
        title:"角色管理",
    	confirmMsg:"确认禁用当前选中的角色吗?",
    }
    $scope.confirmMsg="确认禁用当前选中的角色吗?";
	/**
	 * 查询角色列表
	 */
	function queryRole(){
		var param = {};
		
		if($scope.roleName){
		param.roleName=$scope.roleName;
		}
		if($scope.selectedStatus!=null && $scope.selectedStatus!=''){
			param.status=$scope.selectedStatus;
		}
		$scope.isLoading = 'loading...';
		$scope.roleList = {};
		$scope.pageParam = {
				pageNum:$scope.pageNum,
				pageSize:10
		};
		param.pageParam = $scope.pageParam;
		RoleService.queryRoleForPage(param,function(resp){
			if(resp.error == false){
				$scope.roleList = resp.data;
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
		queryRole();
	};
	
    /**
     * 增加角色
     */
    $scope.toAddRolePanel = function(){
        $scope.operateType = 'add';
        $scope.selectedRole = {};
        initialRoleTree(null);
        $("#roleMgrPanel").modal('show');
    }

    /**
     * 修改角色
     * @param role
     */
    $scope.toUpdateRolePanel = function(role){
        $scope.selectedRole = role;
        queryRoleMenu();
        $scope.operateType = 'update';
        $("#roleMgrPanel").modal('show');
    }

    /**
     * 禁用角色
     */
    $scope.toForbiddenRolePanel = function(role){
    	 $scope.title=role.status==1?"禁用":"启用"+"角色";
      	  $scope.modalBody="确认"+$scope.title+"当前【"+role.roleName+"】角色吗?";
      	  $scope.operateType = 'forbidden';
      	  $("#confirm_dialog").modal("show");
      		 
             $scope.selectedRole = role;
    }

    /**
     * 操作确认
     */
    $scope.saveRole = function(){
        if($scope.operateType == 'add'){
            addRole();
        }else if($scope.operateType == 'update'){
            updateRoleMenu();
        	updateRole();
        }else if($scope.operateType == 'forbidden'){
            forbiddenRole();
        }
    }


    /**
     * 添加角色
     */
    function addRole(){
        var selectedMenus = genSelectedMenus();
        $scope.isSbumitting=true;
        $scope.selectedRole.selectedPrivileges = selectedMenus;
        if(!$scope.selectedRole.roleName)
        	return;
      RoleService.add($scope.selectedRole,function(resp){
    	  $scope.isSbumitting=false;
            if(resp.error == false){
                 alert("添加成功!");
                 $scope.queryInfo($scope.pageNum);
                 $("#roleMgrPanel").modal("hide");
            }
            else if(resp.message){
            	alert(resp.message);
            }
        })
    }

    /**
     * 禁用角色
     */
    function forbiddenRole(){
    	var param={}; 
    	$("#confirm_dialog").modal("hide");
    	param.roleId=$scope.selectedRole.id;
        RoleService.remove(param,function(resp){
            if(resp.error ==  false){
                alert("操作成功!");
                $scope.queryInfo($scope.pageNum);
            }
        })
    }
    
    /**
     * 查询角色菜单权限
     */
    function queryRoleMenu(){
    	var param = {};
        param.id = $scope.selectedRole.id;
    	RoleService.queryRoleMenu(param,function(resp){
    		if(resp.error==false){
    			roleMenu=resp.data;
    			initialRoleTree(roleMenu);
    		}
    	})
    }

    /**
     * 修改角色
     */
    function updateRole(){
        RoleService.update($scope.selectedRole,function(resp){
            if(resp.error == false){
                alert("修改成功!");
                $("#roleMgrPanel").modal("hide");
               $scope.queryInfo($scope.pageNum);
            }
            else if(resp.message){
            	alert(resp.message);
            }
        })
    }
    
    /**
     * 修改角色菜单权限关系
     */
    function updateRoleMenu(){
    	var param={};
    	param.roleId=$scope.selectedRole.id;
    	param.selectedPrivileges=genSelectedMenus();
    	RoleService.updateRoleMenu(param,function(resp){
    		if(resp.error==false){
    			//alert("修改角色菜单成功");
    		}
    	})
    }
    
    /**
     * 菜单数据转换成tree数据
     * @param treeNodeList
     * @param menus
     * @param root
     */
    function addMenu(treeNodeList,menus,root,privileges){
    $('#treeRole').jstree("destroy").empty();
        if(menus&&menus.length){
            $.each(menus,function(i,m){
                if(m.type == 'line'){
                    return;
                }
                var menuId=m.index;
                m.selected=false;
                for (var i in privileges) {
                  	if(privileges[i]==menuId){
                  		m['selected']=true;
                  		break;
                  }
                  }
                var node = {
                    "text": m.name,
                    "id": menuId,
                    "parent_id":root.id,
                    "type": m.menus&&m.menus.length?'menus':'leaf',
                    "state" : {
                        "selected" :  m.selected
                    }
                };
                treeNodeList.push(node);
                if(m.menus&&m.menus.length){
                    var subMenu = [];
                    node.children = subMenu;
                    addMenu(node.children, m.menus,node,privileges);
                }
            });
        }
    }
    $scope.allMenus = [];

    /**
     * 权限树初始化
     */
    function initialRoleTree(privileges){
        MenusService.get({},function(resp){
               var menus = resp.menus;
               $scope.allMenus = menus;
               var treeNode = [];
               var root = {id:"root"};
               addMenu(treeNode,menus,root,privileges);
               $('#treeRole').jstree({
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
                       'data' : treeNode,
                       "check_callback" : function (operation, node, parent, position, more) {
                           //TODO
                       }
                   }
               });

        });
    }

    function genSelectedMenus(){
        var instance = $('#treeRole').jstree(true);
        var selectedMenus = instance.get_selected(true);
        var selected = [];
        for(var key in selectedMenus){
        	var type=selectedMenus[key].type;
        	if(type=='leaf')
            selected.push(selectedMenus[key].id);
        }
        $log.log(selected.join(","));
        return selected;
    }

	$scope.queryInfo(1);
}