/**
 * Created by hengshan.ou on 2017/1/11.
 */

"use strict";
angular
    .module('ework-ui')
    .controller('erp_OrdersIndexPrivilegeCriteriaController', [
    '$rootScope',                                                             	
    '$scope',
    'erp_privilegeCriteriaServicePage',
    'erp_privilegeCriteriaService',
    'erp_privilegeRuleService',
    'erp_gradeService',
    '$state',
    '$log',
    '$uibMsgbox',
    '$cookieStore',
     function(
    		    $rootScope,                                                             	
    		    $scope,
        	    erp_privilegeCriteriaServicePage,
        	    erp_privilegeCriteriaService,
        	    erp_privilegeRuleService,
        	    erp_gradeService,
        	    $state,
        	    $log,
        	    $uibMsgbox,
        	    $cookieStore) {
		        // 表单操作类型，添加： add，修改：put
		        $scope.optype = 'add'; //
    	
    			$scope.business_type = 1; 
        		$scope.privilegeCriteriaList = [];
        		$scope.privilegeRuleList = [];
        	 	$scope.pageParam = new Object();
        	    // 与表单绑定的数据，用于添加和修改
        	    $scope.privilegeCriteria = {};
        	 	
        	 	$scope.status =
                    [{key : 1, value : "有效"},
                     {key : 0, value : "失效"}
                ];
        	 	
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
        	        // itemsPerPage: 10,
        	        // pagesLength: 9,
        	        // perPageOptions: [10, 20, 30, 40, 50],
        	        onChange: function(){
        	            $scope.query()
        	        }
        	    };
        	 	
        	 	$scope.selectBusinessType = function(business_type){
        	        $scope.business_type = business_type;
        	        return true;
        	    }

        	    // 处理【添加优惠前置】按钮点击事件
        	    $scope.handleAddPrivilegeCriteria = function () {
        	    	$scope.privilegeCriteria.branch_ids = "";
        	        $scope.optype = 'add';
        	        $scope.resetForm();
        	        $scope.queryPrivilegeRule();
        	        $scope.queryGrade();
        	        $('#erpPrivilegeCriteriaPanel').modal('show');
        	    }
        	    
        	    // 处理【修改优惠前置】按钮点击事件
        	    $scope.handlePutPrivilegeCriteria = function (rowData) {
        	        $scope.optype = 'put';
        	        $scope.queryPrivilegeRule();
        	        $scope.queryGrade();
        	        
        	        $scope.privilegeCriteria = rowData;
        	        
        	        $("#erpPrivilegeCriteriaPanel").modal('show');
        	    }
        	    
        	    // 处理【删除优惠前置】按钮点击事件
        	    $scope.handleDeletePrivilegeCriteria = function (id) {
        			$uibMsgbox.confirm('确定删除选中优惠前置？', function(res) {
        				if (res == 'yes') {
        					$scope.remove(id);
        				}
        			});
        	    }

        	    // 处理【查询优惠前置】按钮点击事件
        	    $scope.handleQueryPrivilegeCriteria = function () {
        	        $scope.query();
        	    }

        	    // 处理优惠前置表单【取消】按钮点击事件
        	    $scope.handleModalCancel = function () {
        	        $('#erpPrivilegeCriteriaPanel').modal('hide');
        	    }

        	    // 处理优惠前置表单【确认】按钮点击事件
        	    $scope.handleModalConfirm = function () {
        	        if ($scope.optype == 'add') {
        	    		$scope.add();
        	    	} else {
        	    		$scope.put();
        	    	}
        	        
        	        $('#erpPrivilegeCriteriaPanel').modal('hide');
        	    }
        	 	
        	    // 重置优惠前置表单
        	    $scope.resetForm = function () {
        	        $("#erpPrivilegeCriteriaPanel form")[0].reset();
        	    }
        	    
        	    /**
        	     * 分页查询优惠前置
        	     */
        	    $scope.query = function(){
					$scope.loadStatues = true;
        	    	erp_privilegeCriteriaServicePage.query(
        	            {
        	                pageSize: $scope.paginationConf.itemsPerPage,
        	                currentPage: $scope.paginationConf.currentPage,
        	                productLine: $scope.business_type,
        	                searchInfo: $scope.queryParam
        	            },
        	            function(resp){
							$scope.loadStatues = false;
        	                if (!resp.error) {
        	                    $scope.privilegeCriteriaList = resp.data;
        	                    $scope.paginationConf.totalItems = resp.total || 0;
        	                } else {
        	                    $uibMsgbox.error(resp.message);
        	                }
        	            });
        	    }
        	 	
        	    // 添加
        	    $scope.add = function () {
        	    	var param = {};
        	    	$scope.genPrivilegeCriteria(param);
        	    	
        	    	erp_privilegeCriteriaService.post(param, function (resp) {
        	            if (!resp.error) {
        	                $uibMsgbox.success('添加成功！');
        	                $scope.query();
        	            } else {
        	            	$uibMsgbox.error(resp.message);
        	            }
        	        })
        	    };
        	    
        	    // 修改
        	    $scope.put = function () {
        	    	var param = {};
        	    	$scope.genPrivilegeCriteria(param);
        	    	
        	    	erp_privilegeCriteriaService.put(param, function (resp) {
        	            if (!resp.error) {
        	                $uibMsgbox.success('修改成功！');
        	                $scope.query();
        	            } else {
        	                $uibMsgbox.error(resp.message);
        	            }
        	        })
        	    };	    

        	    // 删除优惠规则
        	    $scope.remove = function (id) {
        	    	erp_privilegeCriteriaService.remove({"id" : id},function (resp) {
        	    		if(!resp.error) {
        	                $uibMsgbox.success('删除成功！');
        	    			$scope.query();
        	    		} else {
        	    			$uibMsgbox.error(resp.message);
        	    		}
        	        });
        	    }
        	    
        	    $scope.genPrivilegeCriteria = function(param){
        	    	param.id = $scope.privilegeCriteria.id;
        	        param.name = $scope.privilegeCriteria.name;
        	        param.rule_id = $scope.privilegeCriteria.rule_id;
        	        param.sum_price = $scope.privilegeCriteria.sum_price;
        	        param.sum_hour = $scope.privilegeCriteria.sum_hour;
        	        param.sum_integral = $scope.privilegeCriteria.sum_integral;
        	        param.grade_id = $scope.privilegeCriteria.grade_id;
        	        param.status = $scope.privilegeCriteria.status;
        	        param.description = $scope.privilegeCriteria.description;
        	    }
        	    
        	    /**
        	     * 查询优惠规则
        	     */
        	    $scope.queryPrivilegeRule = function(){
        	        $scope.privilegeRuleList = [];
        	        erp_privilegeRuleService.query({
        	            productLine:$scope.business_type
        	        },function(resp){
        	            if(!resp.error){
        	                $scope.privilegeRuleList = resp.data;
        	            }else{
        	            	$uibMsgbox.error(resp.message);
        	            }
        	        })
        	    }
        	    
        	    /**
        	     * 查询年级
        	     */
        	    $scope.queryGrade = function(){
        	        erp_gradeService.querySelectDatas({
        	            branch_id:$scope.selectedBranch?$scope.selectedBranch.id:-1,
        	            season_id:$scope.selectedTimeSeason?$scope.selectedTimeSeason.id:-1
        	        },function(resp){
        	            if(!resp.error){
        	                $scope.gradeList = resp.data;
        	                if($scope.student && $scope.student.grade_id){
        	                    $.each($scope.gradeList,function(i,grade){
        	                        if(grade.id == $scope.student.grade_id){
        	                            $scope.selectedGrade = grade;
        	                        }
        	                    })
        	                }
        	            }
        	        })
        	    }
        	    
        	    $scope.query();        	    
        }
]);

