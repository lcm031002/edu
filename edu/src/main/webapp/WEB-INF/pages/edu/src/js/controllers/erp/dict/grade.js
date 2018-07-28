"use strict";
angular.module('ework-ui').controller('erp_gradeController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    'erp_gradeService',
    erp_gradeController
    ]);

function erp_gradeController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_gradeService
  ) {
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索年级名称
    $scope.searchParam = {
        grade_Name: ''
    };
    // 年级列表
    $scope.gradeList = [];

    $scope.lastGradeList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.gradeDetail = {
        id: '',
        grade_name: '',
        encoding: '',
        last_id: '',
        last_encoding: '',
        lastGradeName: '',
        sortNum: '',
        description: ''
    };
    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
     * @param  {Function} onChange      [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function(){
            $scope.query();
        }
    };
    
    // 处理【添加年级】按钮点击事件
    $scope.handleAddGrade = function () {
        $scope.optype = 'add';
        $scope.resetForm();
         $scope.gradeDetail={};
        $scope.querySelectDatas('');
        $('#erpSystemDictGradePanel').modal('show');
    };

    // 处理【删除年级】按钮点击事件
    $scope.handleDeleteGrade = function (id) {
    	 $uibMsgbox.confirm('确定删除选中年级？', function (result) {
             if(result != 'yes') {
                 return;
             }
             $scope.del(id);
         });
    };
    
    // 处理【修改年级】按钮点击事件
    $scope.handlePutGrade = function (grade) {
        $scope.optype = 'put';
        $scope.gradeDetail = grade;
        $scope.querySelectDatas('');
        $("#erpSystemDictGradePanel").modal('show');
    };

    // 处理【查询年级】按钮点击事件
    $scope.handleQueryGrade = function () {
        $scope.query();
    };

    // 处理年级表单【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        $('#erpSystemDictGradePanel').modal('hide');
    };

    // 处理年级表单【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
    	if ($scope.optype == 'add') {
    		$scope.add();
    	} else {
    		$scope.put();
    	}
        $('#erpSystemDictGradePanel').modal('hide');
    };

    // 添加
    $scope.add = function () {
    	erp_gradeService.addGrade($scope.gradeDetail, function (resp) {
    		if (!resp.error) {
    			 $uibMsgbox.success(resp.message);
    			 $scope.query();
            } else {
            	$uibMsgbox.error(resp.message);
            }           
        });
    };

    // 修改
    $scope.put = function () {
    	erp_gradeService.updateGrade($scope.gradeDetail, function (resp) {
    		if (!resp.error) {
   			 $uibMsgbox.success(resp.message);
   			 $scope.query();
           } else {
        	   $uibMsgbox.error(resp.message);
           }      
        });
    };

    // 删除
    $scope.del= function (id) {
    	erp_gradeService.delGrade({
    		grade_ids : id
    	}, function (resp) {
    		if (!resp.error) {
      			 $uibMsgbox.success(resp.message);
      			 $scope.query();
              } else {
           	   $uibMsgbox.error(resp.message);
              }      
        });
    };

    // 查询方法
    $scope.query = function () {
    	erp_gradeService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            p_grade_name: $scope.searchParam.p_gradeName
        }, function (resp) {
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.gradeList=resp.data;
            } else {
            	$uibMsgbox.error(resp.message);
            }
            return resp.gradeList
        });
    };
    
    $scope.querySelectDatas = function (id) {
    	erp_gradeService.querySelectDatas({
    		id : id
    	},function(resp) {
            if(!resp.error) {
                $scope.lastGradeList = resp.data;
            }
        });
    };
    
    
    /*
     * 修改状态
     * @param flag true-生效 false-无效
     */
    $scope.changeStatus = function (id, flag) {
    	erp_gradeService.changeStatus({"id" : id, "status" : flag}, function (resp) {
    		if(!resp.error) {
//    			alert("状态修改成功");
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }
    
    $scope.onStatusChange = function (grade) {
    	$scope.changeStatus(grade.id, grade.status);
    }
    
    
    // 重置表单
    $scope.resetForm = function () {
        $("#erpSystemDictGradePanel form")[0].reset();
    };

    $scope.query();
}
