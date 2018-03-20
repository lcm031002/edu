/**
 * 
 */
angular.module('ework-ui')
    .controller('EmployeeExtCtrl', [
        '$scope',
        '$log',
        '$state',
        '$uibMsgbox',
        'EmployeeExtService',
        'DictsService',
        EmployeeExtCtrl]);

function EmployeeExtCtrl($scope,
                  $log,
                  $state,
                  $uibMsgbox,
                  EmployeeExtService,
                  DictsService){
	$scope.employeeExtDatas = [];
	$scope.dictDatas = [];
	$scope.itemOperateType = '';
    $scope.selectedItem = {};

    /**
     * 打开添加员工档案定义的面板
     */

    $scope.toAddEmployeeExt = function(){
    	$scope.selectedItem = {};
    	$scope.itemOperateType = 'add';
        $('#EmployeeExtPanel').modal({"show":true,"backdrop":'static'});
        queryDicts();
    }
    
    $scope.toUpdateItem = function(item){
        $scope.selectedItem = item;
        $scope.itemOperateType = 'update';
        $('#EmployeeExtPanel').modal({"show":true,"backdrop":'static'});
        queryDicts();
    }
    
    $scope.toDeleteItem = function(item){
        $scope.selectedItem = item;
    	$scope.title=$scope.selectedItem.fieldStatus==1?"禁用":"启用";
    	$scope.modalBody="确认"+$scope.title+"当前【"+$scope.selectedItem.fieldName+"】员工档案定义吗?";
        $scope.itemOperateType = 'remove';
        $('#DeleteEmployeeExtPanel').modal({"show":true,"backdrop":'static'});
    }
    
    
    /**
     * 添加员工档案定义
     */
    $scope.saveEmployeeExt = function(){
        if($scope.itemOperateType=='add'){
            $scope.addEmployeeExt();
        }else if($scope.itemOperateType=='update'){
            $scope.updateEmployeeExt();
        }else if($scope.itemOperateType=='remove'){
            $scope.removeEmployeeExt();
        }
    }

    $scope.addEmployeeExt = function(){
    	 var item={};
        $scope.selectedItem.fieldStatus = "1";
        $log.log("item fieldDictType is " + $scope.selectedItem.fieldDictType + ",item fieldName is " + $scope.selectedItem.fieldName + ",item fieldKey is " + $scope.selectedItem.fieldKey);
        if($scope.selectedItem.fieldName == "" || $scope.selectedItem.fieldName == null){
                $uibMsgbox.error("字段名称不能为空");
    				return;
    			}
        EmployeeExtService.add($scope.selectedItem,function(resp){
            if (resp.error == false) {
                $('#EmployeeExtPanel').modal('hide');
                $uibMsgbox.success("添加成功！");
                $scope.employeeExtDatas[$scope.employeeExtDatas.length] = $scope.selectedItem;
                queryEmployeeExt();
            } else {
                $uibMsgbox.error("添加失败！失败信息：" + resp.message);
            }
        })
    }

    $scope.updateEmployeeExt = function(){
    	if($scope.selectedItem.fieldType != "数据字典"){
    		$scope.selectedItem.fieldDictType = "";
    	}
    	if($scope.selectedItem.fieldName == "" || $scope.selectedItem.fieldName == null){
                $uibMsgbox.error("字段名称不能为空");
    				return;
    			}
    	EmployeeExtService.update($scope.selectedItem,function(resp){
    		 if(resp.error == false){
                $('#EmployeeExtPanel').modal('hide');
                $uibMsgbox.success("修改成功！");
                queryEmployeeExt();
            }else{
                $uibMsgbox.error("修改失败！失败信息："+resp.message);
            }
        })
    }

    $scope.removeEmployeeExt = function(){
    	var item={};
    	$log.log("item id is " + $scope.selectedItem.id);
    	EmployeeExtService.remove($scope.selectedItem,function(resp){
            if(resp.error == false){
                $('#DeleteEmployeeExtPanel').modal('hide');
                alert("操作成功！");
                queryEmployeeExt();
            }else{
                alert("禁用失败！失败信息："+resp.message);
            }
        })
    }


    /**
     * 字段类型下拉框
     */   
    	$scope.fieldSelectType = '字符串';
     
        $scope.fieldType =
        [
            '字符串',
            '日期',
            '数据字典'
        ];
    
        
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
        
        /**
         * 查询员工档案定义
         */
        function queryEmployeeExt(){
            var param = {};
            EmployeeExtService.get(param,function(resp){
                if(resp.error == false){
                	$log.log( resp.data[0].items);
                    $scope.employeeExtDatas = resp.data;
                }
            })
        };
            
        
       
        
        queryEmployeeExt();
}