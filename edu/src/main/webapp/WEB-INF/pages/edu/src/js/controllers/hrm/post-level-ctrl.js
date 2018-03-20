/**
 * 
 */
angular.module('ework-ui')
    .controller('PostLevelCtrl', [
        '$scope',
		'$log',
		'$state',
		'$uibMsgbox',
        'PostLevelService',
        PostLevelCtrl]);

function PostLevelCtrl($scope,
        $log,
		$state,
		$uibMsgbox,
        PostLevelService){
	$scope.postLevelList = [];
	$scope.itemOperateType = '';
	$scope.selectedItem = {};
	
	/**
	 * 查询列表
	 */
	function queryPostLevel(){
		var param = {};
		
		$scope.isLoading = 'loading...';
		$scope.postLevelList = [];
		
		PostLevelService.queryPostLevel(param,function(resp){
			if(resp.error == false){
				$scope.postLevelList = resp.data;
				$scope.isLoading = '';
			}
		})
	};
	
	queryPostLevel();
	
	$scope.levelTypeList = [
	    { "id": "PROFESSIONAL", "text": "专业通道(P)"},
	    { "id": "TECHNOLOGY", "text": "技术通道(T)"},
	    { "id": "MANAGEMENT", "text": "管理通道(M)"}
	];
	
	/**
	 * 打开添加面板
	 */
	
	$scope.toAddPostLevelPanel = function(){
		$scope.selectedItem = {};
		$scope.itemOperateType = 'add';
	    $('#PostLevelPanel').modal({'show':'true','backdrop':'static'});
	};
	
	$scope.toUpdateItem = function(item){
	    $scope.selectedItem = item;
	    $scope.itemOperateType = 'update';
	    $('#PostLevelPanel').modal({'show':'true','backdrop':'static'});
	};
	
	/**
	 * 添加
	 */
	$scope.savePostLevel = function(){
	    if($scope.itemOperateType=='add'){
	        $scope.addPostLevel();
	    }else if($scope.itemOperateType=='update'){
	        $scope.updatePostLevel();
	    }
	};
	
	$scope.addPostLevel = function(){
		var item={};
		if($scope.selectedItem.level_code==""||$scope.selectedItem.level_code==null){
			    $uibMsgbox.confirm("层级不能为空");
				return;
			}else{item.level_code=$scope.selectedItem.level_code}
		if($scope.selectedItem.level_type==""||$scope.selectedItem.level_type==null){
			$uibMsgbox.confirm("类别不能为空");
			return;
		}else{item.level_type=$scope.selectedItem.level_type}
		PostLevelService.add(item,function(resp){
			 if(resp.error == false){
				$('#PostLevelPanel').modal('hide');
				$uibMsgbox.success("添加成功");
				queryPostLevel();
			}else{
				$uibMsgbox.error("添加失败！失败信息："+resp.message);
			}
		})
	};
	
	$scope.updatePostLevel = function(){
		var item={};
		item.id=$scope.selectedItem.id;
		if($scope.selectedItem.level_code==""||$scope.selectedItem.level_code==null){
			$uibMsgbox.confirm("层级不能为空");
			return;
		}else{item.level_code=$scope.selectedItem.level_code}
	if($scope.selectedItem.level_type==""||$scope.selectedItem.level_type==null){
		$uibMsgbox.confirm("类别不能为空");
		return;
	}else{item.level_type=$scope.selectedItem.level_type}
	PostLevelService.update(item,function(resp){
			 if(resp.error==false){
				$('#PostLevelPanel').modal('hide');
				$uibMsgbox.success("修改成功");
				queryPostLevel();
			}else{
				$uibMsgbox.error("修改失败，失败信息："+resp.message);
			}
		})
	};
	
}