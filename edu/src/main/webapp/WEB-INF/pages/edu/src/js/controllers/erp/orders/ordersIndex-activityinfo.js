/**
 * Created by hengshan.ou on 2017/1/16.
 */

"use strict";
angular
    .module('ework-ui')
    .controller('erp_OrdersIndexActivityInfoController', [
    '$rootScope',                                                             	
    '$scope',
    'erp_activityInfoService',
    'erp_studentBuOrgsService',
    'erp_privilegeRuleService',
    'erp_activityGenerateCouponDepotService',
    '$state',
    '$log',
    '$uibMsgbox',
    '$cookieStore',
     function(
    		    $rootScope,                                                             	
    		    $scope,
    		    erp_activityInfoService,
    		    erp_studentBuOrgsService,
    		    erp_privilegeRuleService,
    		    erp_activityGenerateCouponDepotService,
        	    $state,
        	    $log,
        	    $uibMsgbox,
        	    $cookieStore) {
    			$scope.business_type = 1; 
        		$scope.activityInfoList = [];
        	    $scope.activityInfo = {}; 
        	    $scope.activityInfoDepot = {}; 
        		$scope.privilegeRuleList = [];
        	 	$scope.pageParam = new Object();
        	 	
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
        	 	
        	 	$scope.is_pub =
                    [{key : 1, value : "是"},
                     {key : 0, value : "否"}
                ];
        	 	
        	    $scope.selectBusinessType = function(business_type){
        	        $scope.business_type = business_type;
        	        return true;
        	    }

        	    // 处理【添加优惠活动】按钮点击事件
        	    $scope.handleAddActivityInfo = function () {
        	    	$scope.activityInfo.branch_ids = "";
        	        $scope.optype = 'add';
        	        $scope.resetForm();
        	        $scope.queryPrivilegeRule();
        	        $scope.queryBuOrgs();
        	        $('#erpActivityInfoPanel').modal('show');
        	    }
        	  
        	    // 处理【修改优惠活动】按钮点击事件
        	    $scope.handlePutActivityInfo = function (rowData) {
        	        $scope.optype = 'put';
        	        $scope.queryPrivilegeRule();
        	        $scope.queryBuOrgs();
        	        
        	        $scope.activityInfo = rowData;
        	        
        	        $("#erpActivityInfoPanel").modal('show');
        	    }
 
        	    // 处理【券仓库】按钮点击事件
        	    $scope.handleActivityDepot = function (rowData) {
        	        $scope.activityInfoDepot = rowData;
        	        $("#erpCouponDepotPanel").modal('show');
        	    }
        	        
        	    // 处理【删除优惠活动】按钮点击事件
        	    $scope.handleDeleteActivityInfo = function (id) {
        			$uibMsgbox.confirm('确定删除选中优惠活动？', function(res) {
        				if (res == 'yes') {
        					$scope.remove(id);
        				}
        			});
        	    }

        	    // 处理【查询优惠活动】按钮点击事件
        	    $scope.handleQueryActivityInfo = function () {
        	        $scope.query();
        	    }

        	    // 处理优惠活动表单【取消】按钮点击事件
        	    $scope.handleModalCancel = function () {
        	        $('#erpActivityInfoPanel').modal('hide');
        	    }

        	    // 处理优惠活动券仓库表单【取消】按钮点击事件
        	    $scope.handleModalDepotCancel = function () {
        	        $('#erpCouponDepotPanel').modal('hide');
        	    }
        	    
        	    // 处理优惠活动券仓库【确认】按钮点击事件
        	    $scope.handleModalGenerate = function () {
        	    	var param = {};
        	        param.activity_id = $scope.activityInfoDepot.activity_id;
        	        param.activity_name = $scope.activityInfoDepot.activity_name;
        	        param.rule_id = $scope.activityInfoDepot.rule_id;
        	        param.rule_name = $scope.activityInfoDepot.rule_name;
        	        param.branch_ids = $scope.activityInfoDepot.branch_ids;
        	        param.branch_names = $scope.activityInfoDepot.branch_names;
        	        param.couponCount = $scope.activityInfoDepot.couponCount;
                    param.coupon_prefix = $scope.activityInfoDepot.coupon_prefix;

        	        erp_activityGenerateCouponDepotService.post(param,function(resp){

        	            if(!resp.error){
        	            	$uibMsgbox.success('生成券成功！');
        	            }else{
        	                $scope.openPanel = '';
        	                $uibMsgbox.error(resp.message);
        	            }
        	        },function(resp){
        	            $scope.openPanel = '';
        	            $uibMsgbox.error("保存失败！");
        	        });
        	        
        	        $('#erpCouponDepotPanel').modal('hide');
        	    }
        	    
        	    // 处理优惠活动表单【确认】按钮点击事件
        	    $scope.handleModalConfirm = function () {
        	        if ($scope.optype == 'add') {
        	    		$scope.add();
        	    	} else {
        	    		$scope.put();
        	    	}
        	        
        	        $('#erpActivityInfoPanel').modal('hide');
        	    }
        	 	
        	    // 重置优惠活动表单
        	    $scope.resetForm = function () {
        	        $("#erpActivityInfoPanel form")[0].reset();
        	    }       	    
        	    
        	    /**
        	     * 分页查询优惠活动
        	     */
        	    $scope.query = function(){
					$scope.loadStatues = true;
        	    	erp_activityInfoService.query(
        	            {
        	                pageSize: $scope.paginationConf.itemsPerPage,
        	                currentPage: $scope.paginationConf.currentPage,
        	                searchInfo: $scope.queryParam
        	            },
        	            function(resp){
							$scope.loadStatues = false;
        	                if (!resp.error) {
        	                    $scope.activityInfoList = resp.data;
        	                    $scope.paginationConf.totalItems = resp.total || 0;
        	                } else {
        	                	$uibMsgbox.error(resp.message)
        	                }
        	            });
        	    }
        	    
        	    // 添加
        	    $scope.add = function () {
        	    	var param = {};
        	    	$scope.genActivityInfo(param);
        	    	
        	    	erp_activityInfoService.post(param, function (resp) {
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
        	    	$scope.genActivityInfo(param);
        	    	
        	    	erp_activityInfoService.put(param, function (resp) {
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
        	    	erp_activityInfoService.remove({"id" : id},function (resp) {
        	    		if(!resp.error) {
        	    			$uibMsgbox.success("删除成功！");
        	    			$scope.query();
        	    		} else {
        	    			$uibMsgbox.error(resp.message);
        	    		}
        	        });
        	    }
        	    
        	    /**
        	     * 查询校区
        	     */
        	    $scope.queryBuOrgs = function (){
        	        erp_studentBuOrgsService.query({},function(resp){
        	            if(!resp.error){
        	                $scope.branchList = resp.data;
        	                $scope.selectedBranch = $scope.branchList[0];
        	                
                	        angular.forEach($scope.branchList, function (branch) {
                	        	if($scope.activityInfo.branch_ids.indexOf(branch.id) > -1){
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
       	    
        	    $scope.genActivityInfo = function(param){
        	    	$scope.strBranch = "";
        	    	$scope.strBranchName = "";
        	        angular.forEach($scope.branchList, function (branch) {
        	        	if(branch.Selected){
        	        		$scope.strBranch += branch.id +",";
        	        		$scope.strBranchName += branch.org_name +",";
        	        	}
        	        });
        	        
        	        if($scope.strBranch.length > 0){
        	        	$scope.strBranch = $scope.strBranch.substr(0,$scope.strBranch.length - 1);
        	        	$scope.strBranchName = $scope.strBranchName.substr(0,$scope.strBranchName.length - 1);
        	        }
    
        	        param.id = $scope.activityInfo.id;
        	        param.activity_name = $scope.activityInfo.activity_name;
        	        param.activity_date = $scope.activityInfo.activity_date;
        	        param.people_count = $scope.activityInfo.people_count;
        	        param.rule_id = $scope.activityInfo.rule_id;
        	        param.is_pub = $scope.activityInfo.is_pub;
        	        param.coupon_prefix = $scope.activityInfo.coupon_prefix;
        	        param.branch_ids = $scope.strBranch;
        	        param.branch_names = $scope.strBranchName;
        	    }
        	    
        	    /**
        	     * 选择所有的校区
        	     */
        	    $scope.checkedAllBuOrgs = function(){
        	        if($scope.activityInfo.checkelBranchs){
        	        	$scope.activityInfo.checkelBranchs = true;
        	        }else{
        	        	$scope.activityInfo.checkelBranchs = false;
        	        }
        	        
        	        angular.forEach($scope.branchList, function (branch) {
        	        	branch.Selected = $scope.activityInfo.checkelBranchs;
        	        });
        	    }
        	    
        	    $scope.query();
        }
]);

