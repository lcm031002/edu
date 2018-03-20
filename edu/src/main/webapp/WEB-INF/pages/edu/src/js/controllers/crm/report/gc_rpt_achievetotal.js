"use strict";
angular.module('ework-ui')
		.controller(
				'crmGcRptAchieveTotalController',
				[
                    '$scope',
                    'crm_buService',
                    'crm_branchService',
                    'crm_achievetotalService',
                    crmGcRptAchieveTotalController ]);
function crmGcRptAchieveTotalController(
        $scope,
        crm_buService,
        crm_branchService,
        crm_achievetotalService
    ) {

	$scope.url = "/gxhcrm/report/query/achievetotal";

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

	// 资源更多信息
	$scope.moreInfo = {};
	// 资源跟踪列表
	$scope.traceInfo = {};

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
        crm_achievetotalService.query($scope.searchParam, $scope.queryrescRecs);
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
        crm_achievetotalService.query($scope.searchParam, $scope.queryrescRecs);
	};

	// 导出
	$scope.exp = function() {
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
		$scope.searchParam.p_start_date = $('#cdt_start_date').val();
		var param = "";
		for ( var p in $scope.searchParam) {
			if ($scope.searchParam[p] != '') {
				param += p + "=" + $scope.searchParam[p] + "&";
			}
		}
		if (confirm("确定要导出?")) {
			location.href = "/gxhcrm/report/toExp/achievetotal?"
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
    };

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