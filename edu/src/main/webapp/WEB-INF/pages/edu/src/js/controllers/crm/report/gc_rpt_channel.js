"use strict";
angular.module('ework-ui').controller(
    'crmGcRptChannelController',[
        '$scope',
        'crm_buService',
        'crm_branchService',
        'crm_queryDictDataService',
        'crm_channelService',
        crmGcRptChannelController]);

function crmGcRptChannelController(
    $scope,
    crm_buService,
    crm_branchService,
    crm_queryDictDataService,
    crm_channelService){
	
	 $scope.paginationConf = {
	          currentPage: 1, // 当前页
	          totalItems: 0,
	          itemsPerPage: 10,
	          onChange: function() {
	            $scope.cdtSearch();
	          }
	        };
	
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

	 $scope.url =  "/gxhcrm/report/queryPage/channelPage";
	
    $scope.rescRecPage = new PageObj();
    $scope.schs = {id:-1};
    
    // 搜索参数
    $scope.searchParam = {
   		   p_grp_company:true,
   		   p_grp_sch:true
    };
    $scope.selectedCompany = {id:-1};
    $scope.selectedSchool = {id:-1};
    $scope.selectedResType = {id:-1};
    $scope.selectedChannel = {code:''}
    // 转移参数
    $scope.param = {};
    
    // 报表数据
    $scope.reportData = [];
    
    $scope.isLoading='';
    
    $scope.queryrescRecs = function(data){
   	 $scope.reportData = [];
   	$scope.paginationConf.totalItems = data.total; // 设置总条数
   	$scope.isLoading='';
   	 if(!$scope.$$phase){
   		 $scope.$apply(function(){
   			 $scope.reportData =data.rows;
   		 });
   	 }else{
   		 $scope.reportData =data.rows;
   	 }
    }
	    
	 	// 初始化分页
		$scope.initRescRecPage = function(){
			$scope.searchParam= {};
            $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
            $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
            $scope.searchParam.p_channel_code = $scope.selectedChannel.code;
			$scope.searchParam.p_grp_company =true;
			$scope.searchParam.p_grp_sch =true;
			$scope.searchParam.rows =$scope.paginationConf.itemsPerPage, // 每页显示条数;
			$scope.searchParam.page =$scope.paginationConf.currentPage, // 要获取的第几页的数据;
            crm_channelService.query($scope.searchParam,$scope.queryrescRecs);
		};
	    
//		$scope.initRescRecPage();
	    
		$scope.begin = function(){
			$scope.rescRecPage.begin();
		};
		
		$scope.up = function(){
			$scope.rescRecPage.up();
		};
		$scope.down = function(){
			$scope.rescRecPage.down();
		};
		$scope.end = function(){
			$scope.rescRecPage.end();
		};
        //查询
		$scope.cdtSearch = function(){
			$scope.isLoading='loading';
            $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
            $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
            $scope.searchParam.p_channel_code = $scope.selectedChannel.code;
			$scope.searchParam.p_start_date=$('#cdt_start_date').val();
			$scope.searchParam.p_end_date=$('#cdt_end_date').val();
			$scope.searchParam.rows =$scope.paginationConf.itemsPerPage, // 每页显示条数;
			$scope.searchParam.page =$scope.paginationConf.currentPage, // 要获取的第几页的数据;
			toolAjax($scope.url, 'get',$scope.searchParam,$scope.queryrescRecs, 'json');
		}
		

		// 导出
		$scope.exp = function(){
            $scope.searchParam.p_bu_id = $scope.selectedCompany.id;
            $scope.searchParam.p_branch_id = $scope.selectedSchool.id;
            $scope.searchParam.p_channel_code = $scope.selectedChannel.code;
			$scope.searchParam.p_start_date=$('#cdt_start_date').val();
			$scope.searchParam.p_end_date=$('#cdt_end_date').val();
			var param = "";
			for(var p in $scope.searchParam){
				console.log($scope.searchParam[p]);
				if($scope.searchParam[p] != ''){
					param += p+"="+$scope.searchParam[p]+"&";
				}
			}
			if(confirm("确定要导出?")){
				location.href =   "/gxhcrm/report/toExp/channel?" +encodeURI(encodeURI(param)) ;
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
        
        $scope.changeResType = function(parentDataId, initSelectorValue) {
            // 查询渠道列表
            crm_queryDictDataService.query({
              dictTypeCode : 'Channel',
			  parentDataId : parentDataId
            }, function(data) {
            	$scope.setChannelList(data, initSelectorValue);
            });
          };
            
            $scope.setChannelList = function(data, initSelectorValue) {
				$scope.channels = [];
				$scope.channels = data;
				$scope.channels.unshift({'code':'', 'name':'全部'});
                $scope.selectedChannel = $scope.channels[0];
			};
			
        function queryResType() {
        	crm_queryDictDataService.query({
				dictTypeCode : 'ResType'
			}, $scope.setResTypeList);
        };
        $scope.setResTypeList = function(data) {
			$scope.resTypes = [];
			$scope.resTypes = data;
			$scope.resTypes.unshift({'code':'', 'name':'全部'});
            $scope.selectedResType = $scope.resTypes[0];
        }
        queryBuList();
        queryResType();
        $scope.changeResType();

}

