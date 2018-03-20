/**
 * Created by hengshan.ou on 2017/1/11.
 */

"use strict";
angular
    .module('ework-ui')
    .controller('erp_privilegeRuleController', [
    '$rootScope',                                                             	
    '$scope',
    'erp_privilegeRuleServicePage', 
    'erp_privilegeRuleService',
    'erp_studentBuOrgsService',
	'erp_organizationService',
    '$state',
    '$log',
    '$uibMsgbox',
    '$cookieStore',
     function(
	    $rootScope,                                                             	
	    $scope,
	    erp_privilegeRuleServicePage,
	    erp_privilegeRuleService,
	    erp_studentBuOrgsService,
		erp_organizationService,
	    $state,
	    $log,
	    $uibMsgbox,
	    $cookieStore) {
        // 表单操作类型，添加： add，修改：put
        $scope.optype = 'add'; //

    	$scope.business_type = 1;     	
		$scope.privilegeRuleList = [];
	 	$scope.branchList = {};
		 
	    // 与表单绑定的数据，用于添加和修改
	    $scope.privilegeRule = {

	    	coupon_type:1,
	    	use_scope:1
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
	        // itemsPerPage: 10,
	        // pagesLength: 9,
	        // perPageOptions: [10, 20, 30, 40, 50],
	        onChange: function(){
	            $scope.query()
	        }
	    };
	 	
	 	$scope.useScopes =
            [{key : 1, value : "通用"},
             {key : 2, value : "老学员"},
             {key : 3, value : "新学员"},
             {key : 4, value : "推荐人"},
             {key : 5, value : "被推荐人"}
        ];

	 	$scope.couponTypes =
            [{key : 1, value : "折扣优惠"},
             {key : 2, value : "优惠金额"},
             {key : 3, value : "每课时优惠"}
        ];

	 	$scope.productLines =[];
	 	
	 	$scope.status =
            [{key : 1, value : "有效"},
             {key : 2, value : "失效"}
                ];

     	$scope.selectBusinessType = function(business_type){
        	$scope.business_type = business_type;
        	return true;
        }

	    // 处理【添加优惠规则】按钮点击事件
	    $scope.handleAddPrivilegeRule = function () {
			$scope.privilegeRule = [];
	        $scope.optype = 'add';
	        $scope.resetForm();
	        $scope.queryBuOrgs();
	        $('#erpPrivilegeRulePanel').modal('show');
	    }
	    
	    // 处理【修改优惠规则】按钮点击事件
	    $scope.handlePutPrivilegeRule = function (rowData) {
	        $scope.optype = 'put';
	        $scope.queryBuOrgs();
	        
	        $scope.privilegeRule = rowData;
	        
	        $("#erpPrivilegeRulePanel").modal('show');
	    }
	    
	    // 处理【删除优惠规则】按钮点击事件
	    $scope.handleDeletePrivilegeRule = function (id) {
			$uibMsgbox.confirm('确定删除选中优惠规则？', function(res) {
				if (res == 'yes') {
					$scope.remove(id);
				}
			});
	    }

	    // 处理【查询优惠规则】按钮点击事件
	    $scope.handleQueryPrivilegeRule = function () {
	        $scope.query();
	    }

	    // 处理优惠规则表单【取消】按钮点击事件
	    $scope.handleModalCancel = function () {
	        $('#erpPrivilegeRulePanel').modal('hide');
	    }
		//验证
		$scope.valid = function(){
			if(!$scope.privilegeRule.rule_name){
				$uibMsgbox.confirm('请输入规则名称！');
				return false;
			}
			if(!$scope.privilegeRule.coupon_type){
				$uibMsgbox.confirm('请输入优惠类型！');
				return false;
			}
			if(!$scope.privilegeRule.coupon_content){
				$uibMsgbox.confirm('请输入优惠！');
				return false;
			}
			if(!$scope.privilegeRule.start_date){
				$uibMsgbox.confirm('请输入开始日期！');
				return false;
			}
			if(!$scope.privilegeRule.end_date){
				$uibMsgbox.confirm('请输入结束日期！');
				return false;
			}
			if(!$scope.privilegeRule.use_scope){
				$uibMsgbox.confirm('请输入适用范围！');
				return false;
			}
			if(!$scope.privilegeRule.product_line){
				$uibMsgbox.confirm('请输入产品线！');
				return false;
			}
			if(!$scope.privilegeRule.status){
				$uibMsgbox.confirm('请输入状态！');
				return false;
			}
			var arr =[];
			angular.forEach($scope.branchList, function (branch) {
	        	arr.push(branch.Selected)
	        });
			if($.inArray(true, arr)== -1){
				$uibMsgbox.confirm('请输入校区！');
				 return false;
			}
			return true;
		}
	    // 处理优惠规则表单【确认】按钮点击事件
	    $scope.handleModalConfirm = function () {
			if (!$scope.valid()) {
				return false;
			}
			if ($scope.optype == 'add') {
	    		$scope.add();
	    	} else {
	    		$scope.put();
	    	}
	        $('#erpPrivilegeRulePanel').modal('hide');
	    }
	 	
	    // 重置优惠规则表单
	    $scope.resetForm = function () {
	        $("#erpPrivilegeRulePanel form")[0].reset();
	        $scope.privilegeRule.coupon_type=1;
	        $scope.privilegeRule.use_scope=1;
	        $scope.privilegeRule.product_line=1;
	        $scope.privilegeRule.status=1;
	    }
	    
	    /**
	     * 分页查询优惠规则
	     */
	    $scope.query = function(){
			$scope.loadStatues = true;
	        erp_privilegeRuleServicePage.query(
	            {
	                pageSize: $scope.paginationConf.itemsPerPage,
	                currentPage: $scope.paginationConf.currentPage,
	                productLine: $scope.business_type,
	                searchInfo: $scope.queryParam
	            },
	            function(resp){
					$scope.loadStatues = false;
	                if (!resp.error) {
	                    $scope.privilegeRuleList = resp.data;
	                    $scope.paginationConf.totalItems = resp.total || 0;
	                } else {
	                	$uibMsgbox.error(resp.message)
	                }
	            });
	    }
		 

	    // 添加
	    $scope.add = function () {
	    	var param = {
	    	};
	    	$scope.genPrivilegeRule(param);
    	 	erp_privilegeRuleService.post(param, function (resp) {
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
	    	$scope.genPrivilegeRule(param);
	    	
    	 	erp_privilegeRuleService.put(param, function (resp) {
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
	    	erp_privilegeRuleService.remove({"id" : id},function (resp) {
	    		if(!resp.error) {
	    			$uibMsgbox.success("删除成功！");
	    			$scope.query();
	    		} else {
	    			$uibMsgbox.error(resp.message);
	    		}
	        });
	    }
		
		//获取优惠规则
	    $scope.genPrivilegeRule = function(param){
	    	$scope.strBranch = "";
	        angular.forEach($scope.branchList, function (branch) {
	        	if(branch.Selected){
	        		$scope.strBranch += branch.id +",";
	        	}
	        });
	        
	        if($scope.strBranch.length > 0){
	        	$scope.strBranch = $scope.strBranch.substr(0,$scope.strBranch.length - 1);
	        }
	    	
	        param.id = $scope.privilegeRule.id;
	        param.rule_name = $scope.privilegeRule.rule_name;
	        param.coupon_type = $scope.privilegeRule.coupon_type;
	        param.coupon_content = $scope.privilegeRule.coupon_content;
	        param.start_date = $scope.privilegeRule.start_date;
	        param.end_date = $scope.privilegeRule.end_date;
	        param.use_scope = $scope.privilegeRule.use_scope;
	        param.product_line = $scope.privilegeRule.product_line;
	        param.status = $scope.privilegeRule.status;
	        param.branch_ids = $scope.strBranch;
	    }
	    
	    /**
	     * 查询校区
	     */
	    $scope.queryBuOrgs = function(){
	        erp_studentBuOrgsService.query({},function(resp){
	            if(!resp.error){
					$scope.branchList = resp.data;
	                // $scope.selectedBranch = $scope.branchList[0];
					if($scope.optype == "put"){
						angular.forEach($scope.branchList, function (branch) {
							if($scope.privilegeRule.branch_ids.indexOf(branch.id) > -1){
								branch.Selected = true;
							}
						});
					}
	            }
	        })
	    }
	    
	    /**
	     * 选择所有的校区
	     */
	    $scope.checkedAllBuOrgs = function(){
	        angular.forEach($scope.branchList, function (branch) {
	        	branch.Selected = $scope.privilegeRule.checkelBranchs;
	        });
	    }
	    $scope.initPage = function() {
			erp_organizationService.queryProductLine({},function(resp) {
				if(!resp.error) {
					$scope.productLines = resp.data;
				} else {
					$uibMsgbox.error(resp.message);
				}
			})
			$scope.query();
		}
		 $scope.initPage();
    }
]);

