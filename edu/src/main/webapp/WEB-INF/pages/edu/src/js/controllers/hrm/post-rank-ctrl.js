/**
 * 
 */
angular.module('ework-ui')
    .controller('PostRankCtrl', [
        '$scope',
        '$log',
		'$state',
		'$uibMsgbox',
        'PostLevelService',
        'PostRankService',
        PostRankCtrl]);

function PostRankCtrl($scope,
        $log,
		$state,
		$uibMsgbox,
        PostLevelService,
        PostRankService){
	$scope.postRankList = [];
	$scope.itemOperateType = '';
	$scope.selectedItem = {};
	
	/**
	 * 查询列表
	 */
	function queryPostRank(){
		var param = {};
		
		$scope.isLoading = 'loading...';
		$scope.postRankList = [];
		
		PostRankService.queryPostRank(param,function(resp){
			if(resp.error == false){
				$scope.postRankList = resp.data;
				$scope.isLoading = '';
			}
		})
	};
	queryPostRank();
	
	$scope.postLevelList = [];
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
	
	$scope.selectedList = [];
	$scope.removeSelected = function (select) {
		select.checked = false;
		_.remove($scope.selectedList, select);
    }

	$scope.handleSelectedListChange = function (select) {
		if (select.checked) {
			$scope.selectedList.push(select)
		} else {
			_.remove($scope.selectedList, select);
		}
	}
	
	/**
	 * 打开添加面板
	 */
	
	$scope.toAddPostRankPanel = function(){
		$scope.selectedItem = {};
		// 已选择的层级
		$scope.selectedList = [];
		// 清空选择
		_.forEach($scope.postLevelList, function(item) {
	    	item.checked = false;
	    });
		
		$scope.itemOperateType = 'add';
	    $('#PostRankPanel').modal('show');
	};
	
	$scope.toUpdateItem = function(item){
	    $scope.selectedItem = item;
	    // 已选择的层级
		$scope.selectedList = [];
	    // 层级Id
	    var level_ids = item.level_ids.split(/[,]/);
	    _.forEach($scope.postLevelList, function(item) {
	    	if(_.indexOf(level_ids, item.id) > -1) {
	    		item.checked = true;
	    		$scope.selectedList.push(item);
	    	} else {
	    		item.checked = false;
	    	}
	    });
	    
	    $scope.itemOperateType = 'update';
	    $('#PostRankPanel').modal('show');
	};
	
	/**
	 * 添加
	 */
	$scope.savePostRank = function(){
	    if($scope.itemOperateType=='add'){
	        $scope.addPostRank();
	    }else if($scope.itemOperateType=='update'){
	        $scope.updatePostRank();
	    }
	};
	
	$scope.addPostRank = function(){
		var item={};
		if($scope.selectedItem.rank_code==""||$scope.selectedItem.rank_code==null){
			$uibMsgbox.confirm("职级名称不能为空");
			return;
		} else {
			item.rank_code=$scope.selectedItem.rank_code;
		}
		
		if(!$scope.selectedList || $scope.selectedList.length < 1){
			$uibMsgbox.confirm("层级不能为空");
			return;
		} else {
			var level_ids = [];
		    _.forEach($scope.selectedList, function(item) {
		    	level_ids.push(item.id);
		    });
		    item.level_ids = level_ids.join(",");
		}
		PostRankService.add(item,function(resp){
			 if(resp.error == false){
				$('#PostRankPanel').modal('hide');
				$uibMsgbox.success("添加成功");
				queryPostRank();
			}else{
				$uibMsgbox.error("添加失败！失败信息："+resp.message);
			}
		});
	};
	
	$scope.updatePostRank = function(){
		var item={};
		item.id=$scope.selectedItem.id;
		
		if($scope.selectedItem.rank_code==""||$scope.selectedItem.rank_code==null){
			$uibMsgbox.confirm("职级名称不能为空");
			return;
		} else {
			item.rank_code=$scope.selectedItem.rank_code;
		}
		
		if(!$scope.selectedList || $scope.selectedList.length < 1){
			$uibMsgbox.confirm("层级不能为空");
			return;
		} else {
			var level_ids = [];
		    _.forEach($scope.selectedList, function(item) {
		    	level_ids.push(item.id);
		    });
		    item.level_ids = level_ids.join(",");
		}
		
		PostRankService.update(item,function(resp){
			 if(resp.error==false){
				$('#PostRankPanel').modal('hide');
				$uibMsgbox.success("修改成功");
				queryPostRank();
			}else{
				$uibMsgbox.error("修改失败，失败信息："+resp.message);
			}
		});
	};
	
}