"use strict";
angular.module('ework-ui').controller('crmGcRptExcavateGcController',
		[
            '$scope',
            'crm_buService',
            'crm_branchService',
            'crm_excavategcService',
            crmGcRptExcavateGcController ]);
function crmGcRptExcavateGcController(
            $scope,
            crm_buService,
            crm_branchService,
            crm_excavategcService
    ) {

	$scope.url = "/gxhcrm/report/query/excavategc";

	$scope.rescRecPage = new PageObj();

	// 搜索参数
	$scope.searchParam = {};
    $scope.selectedCompany = {id:-1};
    $scope.selectedSchool = {id:-1};

	// 转移参数
	$scope.param = {};

	// 报表数据
	$scope.reportData = [];
	$scope.searchParam.p_start_date = Format('yyyy年MM月', new Date());

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
	}

	// 初始化分页
	$scope.initial = function() {
		$scope.searchParam = {};
		$scope.searchParam.p_start_date = Format('yyyy年MM月', new Date());
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
        crm_excavategcService.query($scope.searchParam, $scope.queryrescRecs);
	};

//	$scope.initial();

	$scope.cdtSearch = function() {
		$scope.isLoading = 'loading';
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
		$scope.searchParam.p_start_date = $('#cdt_start_date').val();
        crm_excavategcService.query($scope.searchParam, $scope.queryrescRecs);
	}

	// 导出
	$scope.exp = function() {
		$scope.searchParam.p_start_date = $('#cdt_start_date').val();
        $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
        $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
		if (confirm("确定要导出?")) {
			location.href = "/gxhcrm/report/toExp/performancegc?p_start_date="
					+ $scope.searchParam.p_start_date + "&p_branch_id="
					+ $scope.searchParam.p_branch_id;
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

	function Format(fmt, date) { // author: meizz
		var o = {
			"M+" : date.getMonth() + 1, // 月份
			"d+" : date.getDate(), // 日
			"h+" : date.getHours(), // 小时
			"m+" : date.getMinutes(), // 分
			"s+" : date.getSeconds(), // 秒
			"q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
			"S" : date.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
}