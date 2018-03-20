"use strict";
angular.module('ework-ui').controller(
    'crmGcRptGradeResourceController',[
        '$scope',
        'crm_buService',
        'crm_branchService',
        'crm_queryDictDataService',
        '$uibMsgbox',
        crmGcRptGradeResourceController]);

function crmGcRptGradeResourceController(
    $scope,
    crm_buService,
    crm_branchService,
    queryDictDataService,
    $uibMsgbox){
	
	
   function Format(fmt, date) { // author: meizz
       var o = {
           "M+" : date.getMonth() + 1, // 月份
           "d+" : date.getDate(), // 日
           "h+" : date.getHours(), // 小时
           "m+" : date.getMinutes(), // 分
           "s+" : date.getSeconds(), // 秒
           "q+" : Math
               .floor((date.getMonth() + 3) / 3), // 季度
           "S" : date.getMilliseconds()
           // 毫秒
       };
       if (/(y+)/.test(fmt))
           fmt = fmt.replace(RegExp.$1, (date
               .getFullYear() + "")
               .substr(4 - RegExp.$1.length));
       for ( var k in o)
           if (new RegExp("(" + k + ")").test(fmt))
               fmt = fmt
                   .replace(
                   RegExp.$1,
                   (RegExp.$1.length == 1) ? (o[k])
                       : (("00" + o[k])
                       .substr(("" + o[k]).length)));
       return fmt;
   }

	 $scope.url =  "/gxhcrm/report/query/gradeResourceList";
	
   $scope.rescRecPage = new PageObj();
   $scope.schs = {id:-1};
   
   // 搜索参数
   $scope.searchParam = {
   };
   $scope.grade_headers = [];
   $scope.items = [];
   
   $scope.isLoading=false;
   
   $scope.searchParam.p_start_date = Format('yyyy年MM月', new Date());
   
   $scope.queryrescRecs = function(data){
	 $scope.isLoading=false;
  	 $scope.grade_headers = data.datalist[0].headerList;
	 $scope.items = data.datalist[0].lineList;
	 $scope.$apply();
   };
	    
       //查询
		$scope.cdtSearch = function(){
			$scope.isLoading=true;
            $scope.searchParam.branch_id = $scope.branchId;
            $scope.searchParam.p_start_date = $('#cdt_start_date').val();
			toolAjax($scope.url, 'get',$scope.searchParam,$scope.queryrescRecs, 'json');
		}
		
		// 导出
		$scope.exp = function(){
           $scope.searchParam.branch_id = $scope.branchId;
			var param = "";
			for(var p in $scope.searchParam){
			
				if($scope.searchParam[p] != ''){
					param += p+"="+$scope.searchParam[p]+"&";
				}
			}
			if (confirm("确定要导出?")) {
				location.href =   "/gxhcrm/report/toExp/gradeResourceList?" +encodeURI(encodeURI(param));
			}
		};

}

