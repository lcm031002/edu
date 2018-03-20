/**
 * 
 */
angular.module('ework-ui')
    .controller('PostCtrl', [
        '$scope',
        '$log',
		'$state',
		'$uibMsgbox',
        'OrgService',
        'PostRankService',
        'PostDutyService',
        'PostService',
        PostCtrl]);

function PostCtrl($scope,
        $log,
		$state,
		$uibMsgbox,
        OrgService,
        PostRankService,
        PostDutyService,
        PostService){
	$scope.postList = {};
	$scope.itemOperateType = '';
	$scope.selectedItem = {};
	
	/**
	 * 查询岗位列表
	 */
	function queryPost(){
		var param = {};
		
		$scope.isLoading = 'loading...';
		$scope.postList = [];
		
		/*$scope.pageParam = {
				pageNum:$scope.pageNum,
				pageSize:10
		};
		param.pageParam = $scope.pageParam;*/
		PostService.queryPost(param,function(resp){
			if(resp.error == false){
				$scope.postList = resp.data;
				//$scope.pageParam = resp.pageParam;
				$scope.isLoading = '';
			}
		})
	};
	
	
		queryPost();
	
	function queryPostRank(){
		var param = {};
		
		$scope.isLoading = 'loading...';
		$scope.postRankList = [];
		
		PostRankService.querySimplePostRank(param,function(resp){
			if(resp.error == false){
				$scope.postRankList = resp.data;
				$scope.isLoading = '';
			}
		})
	};
	queryPostRank();
	
	function queryPostDuty(){
		var param = {};
		
		$scope.isLoading = 'loading...';
		$scope.postDutyList = [];
		
		PostDutyService.queryPostDuty(param,function(resp){
			if(resp.error == false){
				$scope.postDutyList = resp.data;
				$scope.isLoading = '';
			}
		})
	};
	queryPostDuty();
	
	/**
	 * 打开添加岗位管理的面板
	 */
	
	$scope.toAddPostPanel = function(){
		//查询岗位类型
		//queryPostTypeName();
		$scope.selectedItem = {};
		$scope.itemOperateType = 'add';
	    $('#PostPanel').modal({'show':'true','backdrop':'static'});
	};
	
	$scope.toUpdateItem = function(item){
		//查询岗位类型
		//queryPostTypeName();
	    $scope.selectedItem = item;
	    $scope.itemOperateType = 'update';
	    $('#PostPanel').modal({'show':'true','backdrop':'static'});
	};
	
	/*$scope.toDeleteItem = function(post){
	    $scope.selectedItem = post;
		$scope.title=$scope.selectedItem.status==1?"禁用":"启用";
		$scope.modalBody="确认"+$scope.title+"当前【"+$scope.selectedItem.name+"】岗位吗?";
	    $scope.itemOperateType = 'remove';
	    $("#DeletePostPanel").modal("show");
	}*/
	
	/**
	 * 添加员工档案定义
	 */
	$scope.savePost = function(){
	    if($scope.itemOperateType=='add'){
	        $scope.addPost();
	    }else if($scope.itemOperateType=='update'){
	        $scope.updatePost();
	    }
	    /*else if($scope.itemOperateType=='remove'){
	        $scope.removePost();
	    }*/
	};
	
	//查询岗位类型名称
	function queryPostTypeName(){
		var param={};
		PostService.queryPostTypeName(param,function(resp){
			if(resp.error==false){
				$scope.post_typeList=resp.data;
			}
		})
	}
	
	$scope.addPost = function(){
		var item={};
		if($scope.selectedItem.post_name==""||$scope.selectedItem.post_name==null){
			$uibMsgbox.confirm("职务名称不能为空");
			return;
		}else{
			item.post_name=$scope.selectedItem.post_name;
		}
		if($scope.selectedItem.post_code==""||$scope.selectedItem.post_code==null){
			$uibMsgbox.confirm("职务编码不能为空");
			return;
		}else{
			item.post_code=$scope.selectedItem.post_code;
		}
		if($scope.selectedItem.post_type_id==""||$scope.selectedItem.post_type_id==null){
			$uibMsgbox.confirm("岗位不能为空");
			return;
		}else{
			item.post_type_id=$scope.selectedItem.post_type_id;
		}
		if($scope.selectedItem.rank_id==""||$scope.selectedItem.rank_id==null){
			$uibMsgbox.confirm("职级不能为空");
			return;
		}else{
			item.rank_id=$scope.selectedItem.rank_id;
		}
		/*if($scope.selectedItem.post_type==""||$scope.selectedItem.post_type==null){
			$uibMsgbox.error("类型不能为空");
			return;
		}else{item.post_type=$scope.selectedItem.post_type}*/
		PostService.add(item,function(resp){
			 if(resp.error == false){
				$('#PostPanel').modal('hide');
				$uibMsgbox.success("添加成功");
				//$scope.postList[$scope.postList.length]=$scope.selectedItem;
				queryPost();
			}else{
				$uibMsgbox.error("添加失败！失败信息："+resp.message);
			}
		})
	};
	
	$scope.updatePost = function(){
		var item={};
		item.id=$scope.selectedItem.id;
		if($scope.selectedItem.post_name==""||$scope.selectedItem.post_name==null){
			$uibMsgbox.confirm("职务名称不能为空");
			return;
		}else{
			item.post_name=$scope.selectedItem.post_name;
		}
		if($scope.selectedItem.post_type_id==""||$scope.selectedItem.post_type_id==null){
			$uibMsgbox.confirm("岗位不能为空");
			return;
		}else{
			item.post_type_id=$scope.selectedItem.post_type_id;
		}
		if($scope.selectedItem.rank_id==""||$scope.selectedItem.rank_id==null){
			$uibMsgbox.confirm("职级不能为空");
			return;
		}else{
			item.rank_id=$scope.selectedItem.rank_id;
		}
		/*if($scope.selectedItem.post_type==""||$scope.selectedItem.post_type==null){
			$uibMsgbox.confirm("类型不能为空");
			return;
		}else{item.post_type=$scope.selectedItem.post_type}*/
		PostService.update(item,function(resp){
			 if(resp.error==false){
				$('#PostPanel').modal('hide');
				$uibMsgbox.success("修改成功");
				queryPost();
			}else{
				$uibMsgbox.error("修改失败，失败信息："+resp.message);
			}
		})
	};
	
	/*$scope.removePost = function(){
		var param={};
		param.id=$scope.selectedItem.id;
		$("#DeletePostPanel").modal("hide");
		PostService.remove(param,function(resp){
			if(resp.error==false){
				$uibMsgbox.success("操作成功");
				$scope.queryInfo($scope.pageNum);
			}
		})
	};*/
	
    /**
     * 查询归属组织
     */
   /*function queryOrg(){
    	var param={};
        OrgService.get(param,function(resp){
            if(resp.error == false){
                $scope.orgs = resp.data;
            }
        })
    }*/
	
	


}