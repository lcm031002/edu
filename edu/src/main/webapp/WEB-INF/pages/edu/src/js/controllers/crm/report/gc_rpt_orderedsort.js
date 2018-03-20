"use strict";
angular.module('ework-ui')
		.controller(
				'crmGcRptOrderedSortController',
				[
                    '$scope',
                    'crm_buService',
                    'crm_orderedsortService',
                    crmGcRptOrderedSortController ]);

function crmGcRptOrderedSortController(
    $scope,
    crm_buService,
    crm_orderedsortService) {
    $scope.selectedCompany = {id:-1};
	/* 分页_start */
	$scope.reportPageObj = new PageObj();
    $scope.reportPageObj.page = 1;
    $scope.reportPageObj.rows = 20;
    $scope.reportPageObj.totalPage = 0;

	$scope.reportPageLib = {};
    // 搜索参数
    $scope.searchParam = {
        p_grp_company : true,
        p_grp_sch : true
    };
	$scope.reportPageLib.dataLoad = '';
	$scope.reportPageLib.pageCallBack = function(ResMapper) {
		$scope.reportData = [];
		$scope.reportPageLib.dataLoad = '';
		try {
			for (var i = 0; i < ResMapper.rows.length; i++)
				$scope.reportData.push(ResMapper.rows[i]);
			$scope.reportPageObj.totalPage = ResMapper.totalPage;
			$scope.reportPageLib.total = ResMapper.total;

		} catch (e) {
			alert('查询异常，请联系系统管理员！');
		}
		$scope.reportPageLib.dataLoad = '';
	}

	$scope.searchPage = function() {
        $scope.searchParam.bu_id = $scope.selectedCompany.id;
		/* 配置时间参数_start */
        $scope.searchParam.moth = $('#cdt_start_date').val();
		/* 配置时间参数_end */
        $scope.searchParam.dataLoad = 'loading';
        $scope.reportPageLib.dataLoad = 'loading';
        $scope.searchParam.page = $scope.reportPageObj.page;
        $scope.searchParam.rows = $scope.reportPageObj.rows;

        crm_orderedsortService.query($scope.searchParam,$scope.reportPageLib.pageCallBack);
	}

    $scope.foundPage = function(page){
        $scope.reportPageObj.page = page;
        $scope.searchPage();
    }

    /* 分页_end */
	$scope.searchParam = {};
	/* 导出_start */
	$scope.exp = function() {
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_moth = $('#cdt_start_date').val();
		var param = "";
		for ( var p in $scope.searchParam) {
			if ($scope.searchParam[p] != '') {
				param += p + "=" + $scope.searchParam[p] + "&";
			}
		}
		if (confirm("确定要导出?")) {
			location.href = "/gxhcrm/report/toExp/expOrderedsort?"
					+ encodeURI(encodeURI(param));
		}
	};
	/* 导出_end */

    function queryBuList(){
        crm_buService.query({},function(resp){
            if(!resp.error){
                $scope.companys = resp.data;
                $scope.selectedCompany = $scope.companys[0];
            }
        })
    }
    queryBuList();
//    $scope.searchPage();

}