/**
 * Created by hengshan.ou on 2017/1/16.
 */

"use strict";
angular
    .module('ework-ui')
    .controller('erp_OrdersIndexCouponInfoController', [
    '$rootScope',                                                             	
    '$scope',
    'erp_privilegeRuleService',
    'erp_studentBuOrgsService',
    'erp_couponInfoServicePage', 
    'erp_couponInfoService',
    '$state',
    '$log',
    '$uibMsgbox',
    '$cookieStore',
     function(
    		    $rootScope,                                                             	
    		    $scope,
    		    erp_privilegeRuleService,
    		    erp_studentBuOrgsService,
    		    erp_couponInfoServicePage,
    		    erp_couponInfoService,
        	    $state,
        	    $log,
        	    $uibMsgbox,
        	    $cookieStore) {
    			$scope.business_type = 1; 
        		$scope.couponInfoList = [];
        	    $scope.couponInfo = {}; 
        	 	$scope.pageParam = new Object();
        	 	$scope.branchList = {};
       
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
        	 	
        	 	$scope.status =
                    [{key : 1, value : "有效"},
                     {key : 0, value : "失效"}
                ];
  
        	    $scope.selectBusinessType = function(business_type){
        	        $scope.business_type = business_type;
        	        return true;
        	    }

        	    // 处理【添加优惠券】按钮点击事件
        	    $scope.handleAddCouponInfo = function () {
        	    	$scope.couponInfo.branch_ids = "";
        	        $scope.optype = 'add';
        	        $scope.resetForm();
        	        $scope.queryPrivilegeRule();
        	        $scope.queryBuOrgs();
        	        $('#erpCouponInfoPanel').modal('show');
        	    }
        	    
        	    // 处理【修改优惠券】按钮点击事件
        	    $scope.handlePutCouponInfo = function (rowData) {
        	        $scope.optype = 'put';
        	        $scope.queryPrivilegeRule();
        	        $scope.queryBuOrgs();
        	        
        	        $scope.couponInfo = rowData;
        	        
        	        $("#erpCouponInfoPanel").modal('show');
        	    }
        	    
        	    // 处理【删除优惠券】按钮点击事件
        	    $scope.handleDeleteCouponInfo = function (id) {
        			$uibMsgbox.confirm('确定删除选中优惠券？', function(res) {
        				if (res == 'yes') {
        					$scope.remove(id);
        				}
        			});
        	    }

        	    // 处理【查询优惠券】按钮点击事件
        	    $scope.handleQueryCouponInfo = function () {
        	        $scope.query();
        	    }

        	    // 处理优惠券表单【取消】按钮点击事件
        	    $scope.handleModalCancel = function () {
        	        $('#erpCouponInfoPanel').modal('hide');
        	    }

        	    // 处理优惠券表单【确认】按钮点击事件
        	    $scope.handleModalConfirm = function () {
        	        if ($scope.optype == 'add') {
        	    		$scope.add();
        	    	} else {
        	    		$scope.put();
        	    	}
        	        
        	        $('#erpCouponInfoPanel').modal('hide');
        	    }
        	 	
        	    // 重置优惠券表单
        	    $scope.resetForm = function () {
        	        $("#erpCouponInfoPanel form")[0].reset();
        	    }
        	    
        	    /**
        	     * 分页查询优惠规则
        	     */
        	    $scope.query = function(){
					$scope.loadStatues = true;
        	    	erp_couponInfoServicePage.query(
        	            {
        	                pageSize: $scope.paginationConf.itemsPerPage,
        	                currentPage: $scope.paginationConf.currentPage,
        	                productLine: $scope.business_type,
        	                searchInfo: $scope.queryParam
        	            },
        	            function(resp){
							$scope.loadStatues = false;
        	                if (!resp.error) {
        	                    $scope.couponInfoList = resp.data;
        	                    $scope.paginationConf.totalItems = resp.total || 0;
        	                } else {
        	                	$uibMsgbox.error(resp.message)
        	                }
        	            });
        	    }
        	    
        	    // 添加
        	    $scope.add = function () {
        	    	var param = {};
        	    	$scope.genCouponInfo(param);
        	    	
        	    	erp_couponInfoService.post(param, function (resp) {
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
        	    	$scope.genCouponInfo(param);
        	    	
        	    	erp_couponInfoService.put(param, function (resp) {
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
        	    	erp_couponInfoService.remove({"id" : id},function (resp) {
        	    		if(!resp.error) {
        	    			$uibMsgbox.success("删除成功！");
        	    			$scope.query();
        	    		} else {
        	    			$uibMsgbox.error(resp.message);
        	    		}
        	        });
        	    }
        	    
        	    $scope.genCouponInfo = function(param){
        	    	$scope.strBranchIds = "";
        	    	$scope.strBranchNames = "";
        	        angular.forEach($scope.branchList, function (branch) {
        	        	if(branch.Selected){
        	        		$scope.strBranchIds += branch.id +",";
        	        		$scope.strBranchNames += branch.org_name +",";
        	        	}
        	        });
        	        
        	        if($scope.strBranchIds.length > 0){
        	        	$scope.strBranchIds = $scope.strBranchIds.substr(0,$scope.strBranchIds.length - 1);
        	        	$scope.strBranchNames = $scope.strBranchNames.substr(0,$scope.strBranchNames.length - 1);
        	        }
        	    	
        	        param.id = $scope.couponInfo.id;
        	        param.name = $scope.couponInfo.name;
        	        param.rule_id = $scope.couponInfo.rule_id;
        	        param.valid_date = $scope.couponInfo.valid_date;
        	        param.status = $scope.couponInfo.status;
        	        param.product_line = $scope.couponInfo.product_line;
        	        param.branch_ids = $scope.strBranchIds;
        	        param.branch_names = $scope.strBranchNames;
        	    }
        	    
        	    /**
        	     * 查询校区
        	     */
        	    $scope.queryBuOrgs = function(){
        	        erp_studentBuOrgsService.query({},function(resp){
        	            if(!resp.error){
        	                $scope.branchList = resp.data;
        	                $scope.selectedBranch = $scope.branchList[0];
        	                
                	        angular.forEach($scope.branchList, function (branch) {
                	        	if($scope.couponInfo.branch_ids.indexOf(branch.id) > -1){
                	        		branch.Selected = true;
                	        	}
                	        });
        	            }
        	        })
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
        	                $scope.privilegeRuleList.unshift({
        	                    id:-1,
        	                    rule_name:""
        	                });
        	            }else{
        	            	$uibMsgbox.error(resp.message);
        	            }
        	        })
        	    }
        	    
        	    /**
        	     * 选择所有的校区
        	     */
        	    $scope.checkedAllBuOrgs = function(){
        	        if($scope.couponInfo.checkelBranchs){
        	        	$scope.couponInfo.checkelBranchs = true;
        	        }else{
        	        	$scope.couponInfo.checkelBranchs = false;
        	        }
        	        
        	        angular.forEach($scope.branchList, function (branch) {
        	        	branch.Selected = $scope.couponInfo.checkelBranchs;
        	        });
        	    }
        	    
        	    $scope.query();
        }
]);

