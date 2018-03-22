/**
 * 
 */
angular.module('ework-ui')
    .controller('PostCtrl', [
        '$scope',
        '$log',
        '$state',
        'OrgService',
        'PostService',
        PostCtrl]);

function PostCtrl($scope,
        $log,
        $state,
        OrgService,
        PostService){
	$scope.postList = {};
	$scope.itemOperateType = '';
	$scope.selectedItem = {};
	
	/**
	 * 查询岗位列表
	 */
	function queryPost(){
		var param = {};
		if($scope.selectedOrgId){
		param.orgId=$scope.selectedOrgId.id;
		}
		if($scope.selectedName){
		param.name=$scope.selectedName;
		}
		$scope.isLoading = 'loading...';
		$scope.postList = {};
		$scope.pageParam = {
				pageNum:$scope.pageNum,
				pageSize:10
		};
		param.pageParam = $scope.pageParam;
		PostService.queryPostForPage(param,function(resp){
			if(resp.error == false){
				$scope.postList = resp.data;
				$scope.pageParam = resp.pageParam;
				$scope.isLoading = '';
			}
		})
	};
	
	$scope.queryInfo=function(pageIndex){
		$scope.pageNum=pageIndex;
		queryPost();
	};
	
	
	/**
	 * 打开添加岗位管理的面板
	 */
	
	$scope.toAddPostPanel = function(){
		$scope.selectedItem = {};
		$scope.itemOperateType = 'add';
	    $('#PostPanel').modal('show');
	};
	
	$scope.toUpdateItem = function(item){
	    $scope.selectedItem = item;
	    $scope.itemOperateType = 'update';
	    $('#PostPanel').modal('show');
	};
	
	$scope.toDeleteItem = function(post){
	    $scope.selectedItem = post;
		$scope.title=$scope.selectedItem.status==1?"禁用":"启用";
		$scope.modalBody="确认"+$scope.title+"当前【"+$scope.selectedItem.name+"】岗位吗?";
	    $scope.itemOperateType = 'remove';
	    $("#DeletePostPanel").modal("show");
	}
	
	/**
	 * 添加员工档案定义
	 */
	$scope.savePost = function(){
	    if($scope.itemOperateType=='add'){
	        $scope.addPost();
	    }else if($scope.itemOperateType=='update'){
	        $scope.updatePost();
	    }else if($scope.itemOperateType=='remove'){
	        $scope.removePost();
	    }
	};
	
	$scope.addPost = function(){
		var item={};
		if($scope.selectedItem.name==""||$scope.selectedItem.name==null){
				alert("名称不能为空");
				return;
			}
		if($scope.selectedItem.orgId==""||$scope.selectedItem.orgId==null){
			alert("所属组织不能为空");
			return;
		}
		
		PostService.add($scope.selectedItem,function(resp){
			 if(resp.error == false){
				$('#PostPanel').modal('hide');
				alert("添加成功");
				$scope.postList[$scope.postList.length]=$scope.selectedItem;
				queryPost();
			}else{
				alert("添加失败！失败信息："+resp.message);
			}
		})
	};
	
	$scope.updatePost = function(){
		if($scope.selectedItem.name==""||$scope.selectedItem.name==null){
				alert("名称不能为空");
				return;
			}
		if($scope.selectedItem.orgId==""||$scope.selectedItem.orgId==null){
			alert("所属组织不能为空");
			return;
		}
		PostService.update($scope.selectedItem,function(resp){
			 if(resp.error==false){
				$('#PostPanel').modal('hide');
				alert("修改成功");
				queryPost();
			}else{
				alert("修改失败，失败信息："+resp.message);
			}
		})
	};
	
	$scope.removePost = function(){
		var param={};
		param.id=$scope.selectedItem.id;
		$("#DeletePostPanel").modal("hide");
		PostService.remove(param,function(resp){
			if(resp.error==false){
				alert("操作成功");
				$scope.queryInfo($scope.pageNum);
			}
		})
	};
	
    /**
     * 查询归属组织
     */
   function queryBus(){
    	var param={};
        OrgService.queryBu(param,function(resp){
            if(resp.error == false){
                $scope.orgs = resp.data;
            }
        })
    }
	
	
	$scope.queryInfo(1);
	queryBus();

}