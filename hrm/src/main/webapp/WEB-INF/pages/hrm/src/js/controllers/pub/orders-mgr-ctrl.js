/**
 * Created by Liyong.zhu on 2016/6/16.
 */
angular.module('ework-ui')
    .controller('EBOrdersCtrl', ['$scope', '$log','$state','CompanyService','BranchsService','OrdersPubService',EBOrdersCtrl]);

function EBOrdersCtrl(
                      $scope,
                      $log,
                      $state,
                      CompanyService,
                      BranchsService,
                      OrdersPubService
    ){
    //公司列表
    $scope.companyList = [];
    //校区列表
    $scope.branchsList = [];
    //订单列表
    $scope.orderList = [];
    //订单详情列表
    //课程列表当前页码
    $scope.currentPage = 1;
    //课程列表当前总页码
    $scope.totalPage = 1;
    //课程列表当前页行数
    $scope.pageSize = 20;
    //选择的校区
    $scope.searchPage = {
    		selectedCompany:null,
    		selectedBranch:null,
    		startDate:null,
    		endDate:null
        }
    /**
     *  查询订单信息
     */
    $scope.queryOrderInfo = function(){
        queryOrdersPub();
    }

    /**
     * 订单作废
     */
    $scope.orderCancel = function(order){
    	var param={};
    	param.orderId=order.id;
        OrdersPubService.delete(param,function(resp){
            if(resp.error == 'false'){
                alert("已成功作废！");
            }
            else{
            	alert("作废失败:"+resp.message);
            }
        });
    }

    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.companyList = resp.data;
            }
        })
    }

    $scope.queryBranchs=function(){
    	
    	queryBranchs();
    }
    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
    	var param={};
    	if($scope.searchPage.selectedCompany){
    		param.companyId=$scope.searchPage.selectedCompany;
    	}
        BranchsService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.branchsList = resp.data;
            }
        })
    }
		$scope.queryInfo=function(){
			queryOrdersPub();
			
		}
    function queryOrdersPub(){
        var param = {};
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;

        param.p_searchInfo = $scope.search_info;
        if($scope.searchPage.selectedCompany){
            param.p_selectedCompany =  $scope.searchPage.selectedCompany;
        }
        if($scope.searchPage.selectedBranch){
            param.p_selectedBranch =  $scope.searchPage.selectedBranch;
        }
        if($scope.searchPage.startDate){
            param.p_startDate = $("#startDate").val();
        }
        if($scope.searchPage.endDate){
            param.p_endDate= $("#endDate").val();
        }
        $scope.isLoadingOrders = 'loading...';
        $scope.orderData = [];
        $log.log("param="+$scope.searchPage.selectedBranch)
        OrdersPubService.get(param,function(resp){
        	if(resp.message){
        		alert(resp.message)
        	}
        	else if(resp.error == 'false'){
            	$log.log(resp.data[0]['actural_amount'])
                $scope.orderList = resp.data;
                getCourseTimesTotal($scope.orderList);
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
            }
            changeNumber();
            $scope.isLoadingOrders = '';
        })
    }
    
    //获取课时总和
    function getCourseTimesTotal(orderList){
    	angular.forEach(orderList, function(order){
    		var count=0;
    		angular.forEach(order.orderCourseList, function(course){
    			count+=course.course_total_count;
    			count=parseInt(count);
    		});
    		$.extend(order,{'courseTimes':count});//求出总课时
    	});
    	
    }
    
    function queryOrderDetails(order){
    	var param={};
    	param.orderId=order.id;
        OrdersPubService.queryDetails(param,function(resp){
    	   if(resp.error=='false'){
    		   $scope.currentOrder.payFees=resp.payFees;//缴费信息
    		   $scope.currentOrder.transfers=resp.transfers;//转班信息
    		   $scope.currentOrder.attendances=resp.attendances;//考勤信息
    	   }
    		   
    	   });
    }
   /* 
    *//**
     * 初始化分页数组
     * @param n
     * @returns {Array}
     */
   $scope.initNumber = function(n){
        var list = new Array();
        for (var index = 1;index<=10&&index<=n;index++){
            list.push(index);
        }
        $scope.paginationBars=list;
    }
    

    /**
     * 获取分页导航条
     */
    function changeNumber(){
    	 var list = new Array();
         //如果当前页码跳到了尾页
         if($scope.currentPage==$scope.totalPage){
        	 var $i=$scope.totalPage-9;
        	 var $index=$i<0?1:$i;
        	 for (var index = $index;index<=$scope.totalPage;index++){
                 list.push(index);
             }
        	 $scope.paginationBars=list;
         }
         //首页
         else if($scope.currentPage==1){
        	 $scope.initNumber($scope.totalPage);
         }
       //如果当前页码不在尾页
         else{
        	 var len=$scope.paginationBars.length;
        	 var lastIndex=$scope.paginationBars[len-1];
        	 if($scope.currentPage< $scope.paginationBars[0]){
        		 $scope.paginationBars.splice(0,0,$scope.paginationBars[0]-1);
        		 $scope.paginationBars.splice(len,1); 
        	 }
        	 else if($scope.currentPage>lastIndex &&$scope.currentPage<$scope.totalPage){
        		 $scope.paginationBars.splice(0,1); 
        		 $scope.paginationBars.splice(len-1,0,lastIndex+1);
        	 }
         }
    }

    $scope.queryIndex=function($index){
    	if(($index<=0||$index>$scope.totalPage)&&$scope.totalPage>0)
    		return;
    	 $scope.currentPage=$index;
    	 $scope.queryInfo();
    }



    /**
     * 展示详情
     * @param order
     */
    $scope.showDetails= function(order){
    	$scope.currentOrder=order;
    	queryOrderDetails(order);
    	$scope.orderDetails=order.orderCourseList;
    	$("#orderDetailsCapacity").modal("show");
    }

    queryCompanys();

    queryBranchs();

    queryOrdersPub();


}