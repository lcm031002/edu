/**
 * Created by Liyong.zhu on 2016/6/23.
 */
angular.module('ework-ui')
    .controller('BStransferClassMgrCtrl', [
        '$scope',
        '$log',
        '$state',
        'TransferClassService',
        'BranchsService',
        'CompanyService',
        BStransferClassMgrCtrl]);


function BStransferClassMgrCtrl(
    $scope,
    $log,
    $state,
    TransferClassService,
    BranchsService,
    CompanyService
    ){
    //转班服务
    $scope.transferClassList = [];
    //公司列表
    $scope.companyList = [];
    //校区列表
    $scope.branchsList = [];
    
    $scope.paginationBars = [];
    /**
     * 转班服务查询
     */
    $scope.queryInfo = function(i){
        if(i){
            $scope.currentPage = i;
        }
        queryTransferClass();
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
    
    $scope.queryBranchsWithCompany= function (company){
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
     * 转班服务
     */
    function queryTransferClass(){
    	$scope.transferClassList=[];
        var param = {};
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;

        param.p_searchInfo = $scope.search_info;
        if($scope.selectedCompany){
            param.p_selectedCompany =  $scope.selectedCompany;
        }
        if($scope.selectedBranch){
            param.p_selectedBranch =  $scope.selectedBranch;
        }
        param.p_startDate = $("#beginDate").val();
        param.p_endDate= $("#endDate").val();
        $scope.isLoadingOrders = 'loading...';
        TransferClassService.get(param,function(resp){
            $scope.isLoadingOrders = '';
            if(resp.error == 'false'){
                $scope.transferClassList = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                changeNumber();
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
         } else{ //如果当前页码不在尾页
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
    	 queryTransferClass();
    }
    
    

    queryTransferClass();
    queryCompanys();
    queryBranchs();
}