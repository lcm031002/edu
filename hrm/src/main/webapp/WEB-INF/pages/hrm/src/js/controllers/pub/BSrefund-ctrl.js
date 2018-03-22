/**
 * Created by Liyong.zhu on 2016/6/22.
 */
angular.module('ework-ui')
    .controller('BSrefundCtrl', [
        '$scope',
        '$log',
        '$state',
        'RefundService',
        'BranchsService',
        'CompanyService',
        BSrefundCtrl]);


function BSrefundCtrl(
    $scope,
    $log,
    $state,
    RefundService,
    BranchsService,
    CompanyService
    ){
    //退费单据列表
    $scope.refundList = [];
    //课程列表当前页码
    $scope.currentPage = 1;
    //课程列表当前总页码
    $scope.totalPage = 1;
    //课程列表当前页行数
    $scope.pageSize = 20;
    //选择的校区
    $scope.selectedBranch = null;
    //选择的公司
    $scope.selectedCompany = null;
    //选择的退费单据
    $scope.selectedRefund = null;

    /**
     * 查询退费单据信息
     */
    $scope.queryInfo = function(){
        queryRefund();
    }

    /**
     * 去作废退费单据
     */
    $scope.toRefundCancel = function(refund){
        $scope.selectedRefund = refund;
        $("#refundCancelPanel").modal("show");
    }

    /**
     * 确认作废退费单据
     */
    $scope.confirmDelete = function(){
        var param = {};
        $.extend(param,$scope.selectedRefund);
        RefundService.delete(param,function(resp){
            if(resp.error == 'false'){
                $("#refundCancelPanel").modal("hide");
                alert("删除成功！");
            }
        });
    }

    /**
     * 取消作废退费单据
     */
    $scope.cancelDelete = function(){
        $("#refundCancelPanel").modal("hide");
    }

    /**
     * 查询退费单据的详细信息
     * @param refund
     */
    $scope.toRefundDetail = function(refund){
        $scope.selectedRefund = refund;
        $("#refundDetailPanel").modal("show");
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
    

    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
        BranchsService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.branchsList = resp.data;
            }
        })
    }
    
    $scope.queryBranchsWithId= function (company){
    	var param={};
    	if( company.id)
    	param.companyId=company.id;
    	else{
    		param.companyId=company;
    	}
        BranchsService.get(param,function(resp){
            if(resp.error == 'false'||resp.error==false){
                $scope.branchsList = resp.data;
            }
        });
    }
    
    /**
     * 查询退费单据
     */
    function queryRefund(){
        var param = {};
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;
        param.p_startDate=$("#beginDate").val();
        param.p_endDate=$("#endDate").val();
        param.p_searchInfo = $scope.search_info;
        if($scope.selectedCompany){
            param.p_selectedCompany =  $scope.selectedCompany;
        }
        if($scope.selectedBranch){
            param.p_selectedBranch =  $scope.selectedBranch;
        }
        $scope.isLoadingOrders = 'loading...';
        RefundService.get(param,function(resp){
            $scope.isLoadingOrders = '';
            if(resp.error ==  'false'){
                $scope.refundList = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                changeNumber();
            }
            else if(resp.message){
            	alert("系统出现错误"+resp.message+":请截图给管理员!");
            }
        })
    }

    /**
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
   	queryRefund();
   }
   

    queryCompanys();

    queryBranchs();

    queryRefund();


}