/**
 * 
 */
angular.module('ework-ui')
    .controller('PostDutyCtrl', [
        '$scope',
        '$log',
		'$state',
		'$uibMsgbox',
        'PostService',
        'PostRankService',
        'PostDutyService', 
        PostDutyCtrl]);

function PostDutyCtrl($scope,
        $log,
		$state,
		$uibMsgbox,
        PostService,
        PostRankService,
        PostDutyService){
	$scope.postDutyList = [];
	$scope.itemOperateType = '';
	$scope.selectedItem = {};
	
	/**
	 * 查询列表
	 */
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
	
//	function queryPostRank(){
//		var param = {};
//		
//		$scope.isLoading = 'loading...';
//		$scope.postRankList = [];
//		
//		PostRankService.querySimplePostRank(param,function(resp){
//			if(resp.error == false){
//				$scope.postRankList = resp.data;
//				$scope.isLoading = '';
//			}
//		})
//	};
//	queryPostRank();
//	
//	function queryPost(){
//		var param = {};
//		
//		$scope.isLoading = 'loading...';
//		$scope.postList = [];
//		
//		PostService.queryPost(param,function(resp){
//			if(resp.error == false){
//				$scope.postList = resp.data;
//				$scope.isLoading = '';
//			}
//		})
//	};
//	queryPost();
	
	/**
	 * 打开添加面板
	 */
	
	$scope.toAddPostDutyPanel = function(){
		$scope.selectedItem = {};
		$scope.itemOperateType = 'add';
	    $('#PostDutyPanel').modal({'show':'true','backdrop':'static'});
	};
	
	$scope.toUpdateItem = function(item){
	    $scope.selectedItem = item;
	    $scope.itemOperateType = 'update';
	    $('#PostDutyPanel').modal({'show':'true','backdrop':'static'});
	};
	
	/**
	 * 添加
	 */
	$scope.savePostDuty = function(){
	    if($scope.itemOperateType=='add'){
	        $scope.addPostDuty();
	    }else if($scope.itemOperateType=='update'){
	        $scope.updatePostDuty();
	    }
	};
	
	$scope.addPostDuty = function(){
		var item={};
		item.remark=$scope.selectedItem.remark;
		if($scope.selectedItem.duty_name==""||$scope.selectedItem.duty_name==null){
			    $uibMsgbox.confirm("岗位名称不能为空");
				return;
		}else{
			item.duty_name=$scope.selectedItem.duty_name;
		}
		
//		if($scope.selectedItem.post_id==""||$scope.selectedItem.post_id==null){
//			$uibMsgbox.confirm("对应岗位不能为空");
//			return;
//		}else{
//			item.post_id=$scope.selectedItem.post_id;
//		}
//		
//		if($scope.selectedItem.rank_id==""||$scope.selectedItem.rank_id==null){
//			$uibMsgbox.confirm("对应职级不能为空");
//			return;
//		}else{
//			item.rank_id=$scope.selectedItem.rank_id;
//		}
		
		PostDutyService.add(item,function(resp){
			 if(resp.error == false){
				$('#PostDutyPanel').modal('hide');
				$uibMsgbox.success("添加成功");
				queryPostDuty();
			}else{
				$uibMsgbox.error("添加失败！失败信息："+resp.message);
			}
		});
	};
	
	$scope.updatePostDuty = function(){
		var item={};
		item.id=$scope.selectedItem.id;
		item.remark=$scope.selectedItem.remark;
		if($scope.selectedItem.duty_name==""||$scope.selectedItem.duty_name==null){
			$uibMsgbox.confirm("岗位名称不能为空");
			return;
		}else{
			item.duty_name=$scope.selectedItem.duty_name;
		}
		
//		if($scope.selectedItem.post_id==""||$scope.selectedItem.post_id==null){
//			$uibMsgbox.confirm("对应岗位不能为空");
//			return;
//		}else{
//			item.post_id=$scope.selectedItem.post_id;
//		}
//		
//		if($scope.selectedItem.rank_id==""||$scope.selectedItem.rank_id==null){
//			$uibMsgbox.confirm("对应职级不能为空");
//			return;
//		}else{
//			item.rank_id=$scope.selectedItem.rank_id;
//		}
		
		PostDutyService.update(item,function(resp){
			 if(resp.error==false){
				$('#PostDutyPanel').modal('hide');
				$uibMsgbox.success("修改成功");
				queryPostDuty();
			}else{
				$uibMsgbox.error("修改失败，失败信息："+resp.message);
			}
		});
	};
	
}