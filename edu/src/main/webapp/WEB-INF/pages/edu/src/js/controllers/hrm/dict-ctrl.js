/**
 * Created by Liyong.zhu on 2016/6/6.
 */
angular.module('ework-ui')
    .controller('DictCtrl', [
        '$scope',
        '$log',
        '$state',
        '$uibMsgbox',
        'DictsService',
        'DictTypeItemService',
        'DictItemService',
        DictCtrl]);

function DictCtrl($scope,
                  $log,
                  $state,
                  $uibMsgbox,
                  DictsService,
                  DictTypeItemService,
                  DictItemService){

    $scope.dictDatas = [];
    $scope.dictSubDatas = [];
    $scope.selectedRow = [];
    $scope.itemOperateType = '';
    $scope.selectedTypeItem = {};
    $scope.selectedItem = {};
    /**
     * 单选一个字典定义
     * @param row
     */
    $scope.singleCheckRow = function(row){ 
        $.each($scope.dictDatas,function(i,m){
            m.checked = false;
        });
        if(row.checked){
            row.checked = false;
        }else{
            row.checked = true;
         
            $scope.dictSubDatas = row.items;
            if( !row.items)
                $scope.dictSubDatas=[];
        }
    }

    /**
     * 打开添加字典类型的面板
     */
    $scope.toAddDicTypeItem = function(){
    	$scope.itemOperateDicType = 'add';
        $('#dictTypeItemPanel').modal({"show":true,"backdrop":'static'});	
    }
    
    /**
     * 打开添加子项的面板
     */
    $scope.toAddItem = function(){
    	var row = null;
        $.each($scope.dictDatas,function(i,m){
            if(m.checked){
                row = m;
            }
        });

        if(!row){
            $uibMsgbox.confirm("请选择左侧字典类型！");
        }else{
            $scope.selectedRow = row;
            $scope.selectedItem = {};
            $scope.itemOperateType = 'add';
            $('#dictItemPanel').modal({"show":true,"backdrop":'static'});
        }
    }

    $scope.toUpdateItem = function(item){
        var row = null;
        $.each($scope.dictDatas,function(i,m){
            if(m.checked){
                row = m;
            }
        });

        if(!row){
            $uibMsgbox.confirm("请选择左侧字典类型！");
        }else{
            $scope.selectedRow = row;
            $scope.selectedItem = item;
            $scope.itemOperateType = 'update';
            $('#dictItemPanel').modal({"show":true,"backdrop":'static'});
        }
    }

    $scope.toDeleteItem = function(item){
    	var r=confirm("确认删除吗?");
    	if(r){
    		$scope.deleteSubItem(item);
    	}
    	
       /* var row = null;
        $.each($scope.dictDatas,function(i,m){
            if(m.checked){
                row = m;
            }
        });

        if(!row){
            alert("请选择左侧字典类型！");
        }else{
            $scope.selectedRow = row;
            $scope.selectedItem = item;
            $scope.itemOperateType = 'delete';
            $('#dictItemPanel').modal('show');
        }*/
    }

    /**
     * 添加字典类型定义
     */
  $scope.addDicTypeItem = function(){
	  var item={};
       $log.log("item name is " + $scope.selectedTypeItem.name + ",item id is " + $scope.selectedTypeItem.id + ",remark is " + $scope.selectedTypeItem.remark);
       //$scope.selectedTypeItem.id = $scope.selectedRow.id;
       $scope.selectedTypeItem.mod = "HRM";
       $scope.selectedTypeItem.status = "1";
       if($scope.selectedTypeItem.name == "" || $scope.selectedTypeItem.name == null){
                $uibMsgbox.error("字典名称不能为空");
   				return;
    	   }
       if($scope.selectedTypeItem.key == "" || $scope.selectedTypeItem.key == null){
           $uibMsgbox.error("字典关键字不能为空");
    	   return;
       }
       DictTypeItemService.add( $scope.selectedTypeItem,function(resp){
    	   if(resp.error == false){
               $('#dictTypeItemPanel').modal('hide');
               $uibMsgbox.success("添加成功！");
               $scope.selectedTypeItem.id=resp.id;
               $scope.dictDatas[ $scope.dictDatas.length]= $scope.selectedTypeItem;
               queryDicts();
           }else{
               $uibMsgbox.error("添加失败！失败信息："+resp.message);
           }
       })
   }
    
    $scope.saveDicTypeItem = function(){
            $scope.addDicTypeItem();
   }
    	
    /**
     * 添加子项定义
     */
    $scope.addSubItem = function(){
    	 var item={};
        $log.log("item name is " + $scope.selectedItem.name + ",item id is " + $scope.selectedRow.id + ",remark is " + $scope.selectedItem.remark);
        $scope.selectedItem.dictTypeId = $scope.selectedRow.id;
        if($scope.selectedItem.name == "" || $scope.selectedItem.name == null){
                    $uibMsgbox.error("子项名称不能为空");
    				return;
    			}
        if($scope.selectedItem.code == "" || $scope.selectedItem.code == null){
        	$uibMsgbox.error("子项编码不能为空");
        	return;
        }
        DictItemService.add( $scope.selectedItem,function(resp){
        	 if(resp.error == false){
                $('#dictItemPanel').modal('hide');
                $uibMsgbox.success("添加成功！");
                $scope.selectedItem.id=resp.data;
                $scope.dictSubDatas[ $scope.dictSubDatas.length]= $scope.selectedItem;
            }else{
                $uibMsgbox.error("添加失败！失败信息："+resp.message);
            }
        })
    }

    $scope.updateSubItem = function(){
        $log.log("item name is " + $scope.selectedItem.name + ",remark is " + $scope.selectedItem.remark);
        $scope.selectedItem.type = $scope.selectedRow.id;
        DictItemService.update($scope.selectedItem,function(resp){
            if(resp.error == false){
                $('#dictItemPanel').modal('hide');
                $uibMsgbox.confirm("修改成功！");
            }else{
                $uibMsgbox.error("修改失败！失败信息："+resp.message);
            }
        })
    }

    $scope.deleteSubItem = function(dicData){
    	$log.log(dicData.id);
        $scope.selectedItem.id = dicData.id;
        DictItemService.remove($scope.selectedItem,function(resp){
            if(resp.error == false){
                $('#dictItemPanel').modal('hide');
                $uibMsgbox.success("删除成功！");
                removeDate(dicData.id);
            }else{
                $uibMsgbox.error("删除失败！失败信息："+resp.message);
            }
        });
    }

    $scope.saveItem = function(){
        if($scope.itemOperateType=='add'){
            $scope.addSubItem();
        }else if($scope.itemOperateType=='update'){
            $scope.updateSubItem();
        }else if($scope.itemOperateType=='delete'){
            $scope.deleteSubItem();
        }
    }
    
    function removeDate(id){
    	 $.each($scope.dictSubDatas,function(i,model){
    		 if(model.id==id){
    			 $scope.dictSubDatas.splice(i,1);//溢出元素
    		 }
    		 
    	 });
    	
    }

    /**
     * 查询字典
     */
    function queryDicts(){
        var param = {};
        DictsService.get(param,function(resp){
            if(resp.error == 'false'||resp.error ==false){
            	$log.log( resp.data[0].items);
                $scope.dictDatas = resp.data;
            }
        })
    }
    
     queryDicts();
}