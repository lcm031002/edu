/**
 * 
 */
angular.module('ework-ui')
	.controller('ChangeEvent_HtCtrl',[
  		'$scope',
		'$log',
		'$state',
		'OrgService',
		'ChangeEvent_HtService',
		ChangeEvent_HtCtrl]);

function ChangeEvent_HtCtrl($scope,
		$log,
		$state,
		OrgService,
		ChangeEvent_HtService){
	$scope.changeHtList=[];
	//得分数组
	$scope.scoreList=[];
	//显示模板
	$scope.showModel=false;
	//审核备注
	$scope.remark=[];
	//处理人
	$scope.handler_nameList=[];
	//处理时间
	$scope.last_update_timeList=[];
	//处理结果
	$scope.handler_opinionList=[];
	$scope.showDetail=0;
	/**
     * 查询人事异动历史
     */
    function queryChangEvent_HtPage(){
    	var param = {};
    	
    	if($scope.changeBuId){
    		var bu_id=$scope.changeBuId;
    		}else{
    			var bu_id="";	
    		}
    	if($scope.changeBranchId){
    		var branch_id=$scope.changeBranchId;
    	}else{
    		var branch_id="";
    	}
    	if($scope.changeSeacherinfo){
    		var serach=$scope.changeSeacherinfo;
    	}else{
    		var serach="";
    	}
    	$scope.isLoading = 'loading...';
    	$scope.pageParam = {
    			pageNum :$scope.pageNum,
    			pageSize : 10
    		};
    	param={"pageParam":$scope.pageParam,"bu_id":bu_id,"branch_id":branch_id,"serach":serach};
    	ChangeEvent_HtService.queryChangEvent_HtPage(param,function(resp){
    		$scope.isLoading = '';
    		if(resp.nodata==false){
    			$scope.changeHtList = resp.data;
    			$scope.pageParam = resp.pageParam;
    	    	 if ($scope.pageParam.pageNum > 1 && $scope.pageParam.pageNum < $scope.pageParam.pages) {
    	     	       $scope.paginationBars = [$scope.pageParam.pageNum - 1,$scope.pageParam.pageNum, $scope.pageParam.pageNum + 1];
    	         } else if ($scope.pageParam.pageNum == 1 && $scope.pageParam.pages > 1) {
    	     	       $scope.paginationBars = [ $scope.pageParam.pageNum, $scope.pageParam.pageNum + 1];
    	         } else if ($scope.pageParam.pageNum == $scope.pageParam.pages && $scope.pageParam.pages > 1) {
    	     	       $scope.paginationBars = [ $scope.pageParam.pageNum - 1,$scope.pageParam.pageNum];
    	         }
    		}else{
    			$scope.changeHtList=[];
    		}
    	})
    };
    
    $scope.queryInfo=function(pageIndex){
    	$scope.pageNum=pageIndex;
    	queryChangEvent_HtPage();
    };
	
    
    $scope.toClosePanel = function(){
    	$scope.showModel=false;
        $scope.openPanel = '';
        
        $scope.handler_opinionList=[];
        $scope.remark=[];
        $scope.handler_nameList=[];
        $scope.last_update_timeList=[];
        $scope.selecteditem={};
    }
    
    
    /**
     * 点击详情
     */
     $scope.detail=function(changeeventHt){
     	$scope.selecteditem={};
     	var param={};
     	param.event_id=changeeventHt.event_id;
     	$scope.step=changeeventHt.step//获取当前流程到了哪一步
     	param.step=changeeventHt.step;
     	ChangeEvent_HtService.queryDetail(param,function(resp){
     		if(resp.error==false){
     			$scope.selecteditem=resp.data;
     			
     			switch($scope.selecteditem.processKey){
     			case "hrm.DXB_zhuanz":$scope.stepNum=3,$scope.openPanel='1';break;
     			case "hrm.DXB_school_zhuanz":$scope.stepNum=3,$scope.openPanel='2';break;
     			case "hrm.gxh_school_pingjia":$scope.stepNum=2,$scope.openPanel='3';break;
     			case "hrm.gxh_zhuanz":$scope.stepNum=3,$scope.openPanel='4';break;
     			case "hrm.gxh_manger_360kaoping":$scope.stepNum=2,$scope.openPanel='5';break;
     			case "hrm.gxh_manger_zhuanz":$scope.stepNum=3,$scope.openPanel='6';break;
     			case "hrm.gxh_zixun_360kaoping":$scope.stepNum=2,$scope.openPanel='7';break;
     			case "hrm.gxh_zixun_zhuanz":$scope.stepNum=3,$scope.openPanel='8';break;
     			default :break;
     			}
     			$scope.showModel=true;
     			if($scope.step>1){
     				getRemark(resp.data);
     			}
     			if($scope.selecteditem.scoreList!==null){
     				strToArr($scope.selecteditem.scoreList);
     			}
     		}
     	})
     	
     }
    
  
     /**
      * 获取之前审批的信息
      */
     function getRemark(data){
     	for(var i=1;i<$scope.step;i++){
     		$scope.handler_opinionList[i-1]=data.list[i].handler_opinion;
     		$scope.remark[i-1]=data.list[i].enclosure;
     		$scope.handler_nameList[i-1]=data.list[i].handler_name;
     		$scope.last_update_timeList[i-1]=data.list[i].last_update_time;
     	}
     }
     
     /**
      * 点击详情后获取数组
      */
     function strToArr(scoreList){
     	var ary = eval('(' + scoreList + ')');
     	for (var i = 0 ;i<ary.length;i++){
     		ary[i];
     	}
     	$scope.scoreList=ary;
     }
    
    /**
     * 根据团队查询所属校区
     */
    $scope.queryBranchsWithId= function (buId){
       	var param={};
       	if( buId)
       	param.buId=buId;
       	else{
       		param.buId=bu;
       	}
           OrgService.querySch(param,function(resp){
               if(resp.error == false){
                   $scope.branchsList = resp.data;
               }
           });
       }
       
       /**
        * 查询归属团队
        */
       function queryBu(){
           OrgService.queryBu({},function(resp){
               if(resp.error == false){
                   $scope.buList = resp.data;
               }
           })
       }
    
    $scope.queryInfo(1);
    queryBu();
}