"use strict";
angular.module('ework-ui').controller('crmGcRptAvgAchieveController',
		[
            '$scope',
            'crm_buService',
            'crm_branchService',
            'crm_avgachieveService',
            crmGcRptAvgAchieveController ]);
function crmGcRptAvgAchieveController(
        $scope,
        crm_buService,
        crm_branchService,
        crm_avgachieveService) {
	$scope.rescRecPage = new PageObj();

	// 搜索参数
	$scope.searchParam = {
		p_grp_company : true,
		p_grp_sch : true
	};
    $scope.selectedCompany = {id:-1};
    $scope.selectedSchool = {id:-1};
	// 转移参数
	$scope.param = {};

	// 报表数据
	$scope.reportData = [];

	$scope.isLoading = '';

	$scope.queryrescRecs = function(data) {
		$scope.reportData = [];
		$scope.isLoading = '';
		if (!$scope.$$phase) {
			$scope.$apply(function() {
				$scope.reportData = data.datalist;
			});
		} else {
			$scope.reportData = data.datalist;
		}
	};

	// 初始化分页
	$scope.initRescRecPage = function() {
		$scope.searchParam = {};
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
		$scope.searchParam.p_grp_company = true;
		$scope.searchParam.p_grp_sch = true;
        crm_avgachieveService.query($scope.searchParam, $scope.queryrescRecs);
	};

//	$scope.initRescRecPage();

	$scope.begin = function() {
		$scope.rescRecPage.begin();
	};

	$scope.up = function() {
		$scope.rescRecPage.up();
	};
	$scope.down = function() {
		$scope.rescRecPage.down();
	};
	$scope.end = function() {
		$scope.rescRecPage.end();
	};

	$scope.cdtSearch = function() {
		$scope.isLoading = 'loading';
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
		$scope.searchParam.p_start_date = $('#cdt_start_date').val();
		$scope.searchParam.p_end_date = $('#cdt_end_date').val();
        crm_avgachieveService.query($scope.searchParam, $scope.queryrescRecs);
	};

	// 导出
	$scope.exp = function() {
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
		$scope.searchParam.p_start_date = $('#cdt_start_date').val();
		$scope.searchParam.p_end_date = $('#cdt_end_date').val();
		var param = "";
		for ( var p in $scope.searchParam) {
			if ($scope.searchParam[p] != '') {
				param += p + "=" + $scope.searchParam[p] + "&";
			}
		}
		if (confirm("确定要导出?")) {
			location.href = "/gxhcrm/report/toExp/avgachieve?"
					+ encodeURI(encodeURI(param));
		}
	};


    $scope.changeCompany = function(){
        queryBranchList();
    };

    function queryBuList(){
        crm_buService.query({},function(resp){
            if(!resp.error){
                $scope.companys = resp.data;
                $scope.selectedCompany = $scope.companys[0];
                crm_branchService.query({
                    buId:$scope.selectedCompany.id?$scope.selectedCompany.id:null
                },function(resp){
                    if(!resp.error){
                        $scope.schs = resp.data;
                        $scope.schs.unshift({'id':-1, 'text':'全部'});
                        $scope.selectedSchool = $scope.schs[0];
                    }
                });
            }
        })
    }

    function queryBranchList(){
        crm_branchService.query({
            buId:$scope.selectedCompany.id?$scope.selectedCompany.id:null
        },function(resp){
            if(!resp.error){
                $scope.schs = resp.data;
                $scope.schs.unshift({'id':-1, 'text':'全部'});
                $scope.selectedSchool = $scope.schs[0];
            }
        })
    }
    queryBuList();
//    queryBranchList();
}