/**
 * 
 */
angular.module('ework-ui')
    .controller('OrgCtrl', [
        '$scope',
        '$log',
        '$state',
        '$timeout',
        'OrgService',
        OrgCtrl]);

function OrgCtrl($scope,
                  $log,
                  $state,
                  $timeout,
                  OrgService){
	$scope.orgDatas = [];
	$scope.itemOperateType = '';
    $scope.selectedItem = {};
    $scope.orgItem = {};

    $scope.reloadPage = function(){window.location.reload();}

    /**
     * 打开添加组织机构的面板
     */

    $scope.toAddItem = function(item){
        $scope.selectedItem = item;
    	$scope.itemOperateType = 'add';
        $('#AddOrgItemPanel').modal('show');	
    }
    
    $scope.toUpdateItem = function(item){
        $scope.selectedItem = item;
        $scope.itemOperateType = 'update';
        $('#UpdateOrgItemPanel').modal('show');
    }
    
    $scope.toDeleteItem = function(item){
        $scope.selectedItem = item;
    	$scope.title=$scope.selectedItem.status==1?"禁用":"启用";
    	$scope.modalBody="确认"+$scope.title+"当前【"+$scope.selectedItem.org_name+"】组织机构吗?";
        $scope.itemOperateType = 'remove';
        $('#DeleteOrgItemPanel').modal('show');
    }
    
    
    /**
     * 添加组织机构
     */
    $scope.saveOrgItem = function(){
        if($scope.itemOperateType=='add'){
            $scope.addOrgItem();
        }else if($scope.itemOperateType=='update'){
            $scope.updateOrgItem();
        }else if($scope.itemOperateType=='remove'){
            $scope.removeOrgItem();
        }
    }

    $scope.addOrgItem = function(){
    	 var item={};
        $scope.orgItem.status = "1";
		$scope.orgItem.parent_id = $scope.selectedItem.id;
		$scope.orgItem.org_name = $scope.selectedItem.name;
		$scope.orgItem.org_type = $scope.orgSelectType.type;
        $log.log("item name is " + $scope.orgItem.org_name + ",item parent_id is " + $scope.orgItem.parent_id + ",item type is " + $scope.orgItem.org_type);
        OrgService.add($scope.orgItem,function(resp){
        	if($scope.orgItem.org_name == "" || $scope.orgItem.org_name == null){
         	   alert("组织机构名称不能为空");
    				return;
    			}else if(resp.error == false){
	                $('#AddOrgItemPanel').modal('hide');
	                alert("添加成功！");
	                $scope.orgDatas[ $scope.orgDatas.length]= $scope.orgItem;
	                $scope.reloadPage();
            }else{
                alert("添加失败！失败信息："+resp.message);
            }
        })
    }

    $scope.updateOrgItem = function(){
        OrgService.update($scope.selectedItem,function(resp){
            if(resp.error == false){
                $('#UpdateOrgItemPanel').modal('hide');
                alert("修改成功！");
                $scope.reloadPage();
            }else{
                alert("修改失败！失败信息："+resp.message);
            }
        })
    }

    $scope.removeOrgItem = function(){
    	var item={};
    	$log.log("item id is " + $scope.selectedItem.id);
        OrgService.remove($scope.selectedItem,function(resp){
            if(resp.error == false){
                $('#DeleteOrgItemPanel').modal('hide');
                alert("禁用成功！");
                $scope.reloadPage();
            }else{
                alert("禁用失败！失败信息："+resp.message);
            }
        })
    }


    /**
     * 组织类型下拉框
     */   
    	$scope.orgSelectType = {type: '1'};
     
        $scope.orgType =
        [
            {type: '1', name: '地区级别'},
            {type: '3', name: '团队级别'},
            {type: '4', name: '校区级别'}
        ];
        
        
        /**
         * 查询组织机构
         */
        function queryOrg(){
            var param = {};
            OrgService.get(param,function(resp){
                if(resp.error == false){
                	$log.log( resp.data[0].items);
                    $scope.orgDatas = resp.data;
                    $scope.delayTree();
                }
            })
        };
            
/*        $(function(){ 
            //上移 
            var $up = $(".upOrg") 
            $up.click(function() { 
                var $tr = $(this).parents("tr"); 
                if ($tr.index() != 0) { 
                    $tr.fadeOut().fadeIn(); 
                    $tr.prev().before($tr); 
                     
                } 
            }); 
            //下移 
            var $down = $(".downOrg"); 
            var len = $down.length; 
            $down.click(function() { 
                var $tr = $(this).parents("tr"); 
                if ($tr.index() != len - 1) { 
                    $tr.fadeOut().fadeIn(); 
                    $tr.next().after($tr); 
                } 
            }); 
        }); */

        //延迟生成树结构  
        $scope.delayTree = function(){        
        	var todo = function() {
        		$scope.toTree();
        };
        	$timeout(todo,0);
        }

        $scope.toTree=function(){
        	   $("#tree_table_id").treetable({ expandable: true });
        	
			     // Highlight selected row
			     $("#tree_table_id tbody").on("mousedown", "tr", function() {
			       $(".selected").not(this).removeClass("selected");
			       $(this).toggleClass("selected");
			     });
			
			     // Drag & Drop Example Code
			     $("#tree_table_id .file, #tree_table_id .folder").draggable({
			       helper: "clone",
			       opacity: .75,
			       refreshPositions: true,
			       revert: "invalid",
			       revertDuration: 300,
			       scroll: true
			     });
			
		     $("#tree_table_id .folder").each(function() {
		       $(this).parents("#tree_table_id tr").droppable({
		         accept: ".file, .folder",
		         drop: function(e, ui) {
		           var droppedEl = ui.draggable.parents("tr");
		           $("#tree_table_id").treetable("move", droppedEl.data("ttId"), $(this).data("ttId"));
		         },
		         hoverClass: "accept",
		         over: function(e, ui) {
		           var droppedEl = ui.draggable.parents("tr");
		           if(this != droppedEl[0] && !$(this).is(".expanded")) {
		             $("#tree_table_id").treetable("expandNode", $(this).data("ttId"));
		           }
		         }
		       });
		     });
		     
        }
    	
        queryOrg();

}