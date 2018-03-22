/**
 * Created by Liyong.zhu on 2016/5/27.
 */

angular.module('ework-ui').
    directive('block', ['$compile',function($compile) {
        return {
            restrict: 'E',
            template: '',
            transclude: true
        };
    }])
    .controller('CountAccomplishmentEverydayCtrl', ['$scope', '$log','$state','CompanyService','BranchsService','ReportService',CountAccomplishmentEverydayCtrl]);

function CountAccomplishmentEverydayCtrl($scope, $log,$state,CompanyService,BranchsService,ReportService) {
    $scope.companys = [];
    $scope.reportData = [];
    
    $scope.st=new Date();
    $scope.dt=new Date();
    
    $scope.search_info = "";

    /**
     * 查询订单信息
     */
    $scope.queryInfo = function(){
    	$scope.currentPage=1;
        queryReportData();
    }

    /**
     * 获取数组
     * @param n
     * @returns {Array}
     */
    $scope.getNumber = function(n){
        var list = new Array();
        for (var index = 0;index<n;index++){
            list.push(index+1);
        }
        return list;
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
    	 queryReportData();
    }
    
    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
        BranchsService.get({},function(resp){
            if(resp.error == 'false'){
            	resp.data.unshift({id:'-1',name:'全部'});
                $scope.branchsList = resp.data;
                $scope.selectedBranch = $scope.branchsList[0];
            }
        })
    }


    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'){
            	resp.data.unshift({id:'-1',name:'全部'});
                $scope.companys = resp.data;
                $scope.selectedCompany = $scope.companys[0];
            }
        })
    }
    function queryReportData(){
	    if(!$scope.selectedCompany){
	    	alert('请选择公司！');
	    	return;
	    }
	    if(!$scope.selectedBranch){
	    	alert('请选择校区！');
	    	return;
	    }
	    if(!$scope.st){
	    	alert("开始时间必须录入！");
	    	return;
	    }
	    if(!$scope.dt){
	    	alert("截止时间必须录入！");
	    	return;
	    }
        var param = {};
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;
        param.reportType = "queryCountAccomplishmentEveryday";
        param.search_info = $scope.search_info;
        
		param.calcBeginDate = $('#beginDate').val();
		param.calcEndDate = $('#endDate').val();
		
		param.companyId = $scope.selectedCompany.id;
		param.branchId = $scope.selectedBranch.id;
		param.isWfd = $('#isWfd').prop('checked');
		
        $scope.isLoadingOrders = 'loading...';
        $scope.reportData = [];
        ReportService.query(param,function(resp){
            $scope.reportData = resp.data;
            $scope.currentPage = resp.currentPage;
            $scope.totalPage = resp.totalPage;
            $scope.pageSize = resp.pageSize;
            changeNumber();
            $log.log("pageSize is"+$scope.pageSize+",totalPage is "+$scope.totalPage+",currentPage"+$scope.currentPage);
            $scope.isLoadingOrders = '';
        },
        function(err){
        	  alert('异常:' + err.data.message+'请联系管理员！');
        	  $scope.isLoadingOrders = '';
         })
    }
	$scope.exp = function(){
	    if(!$scope.selectedCompany){
	    	alert('请选择公司！');
	    	return;
	    }
	    if(!$scope.selectedBranch){
	    	alert('请选择校区！');
	    	return;
	    }
		
		if(!$scope.st){
			alert("开始时间必须录入！");
			return;
		}
		if(!$scope.dt){
			alert("截止时间必须录入！");
			return;
		}
		if(confirm("确定要导出?")){
			var paramUrl = "?companyId=" + $scope.selectedCompany.id 
						+ "&branchId=" + $scope.selectedBranch.id
						+"&isWfd=" + $('#isWfd').prop('checked')
						+ "&calcBeginDate=" + $('#beginDate').val() 
						+ "&calcEndDate=" + $('#endDate').val() 
			window.open("excels/output/outExcelCountaccomplishmenteveryday" + paramUrl, "_blank");
		}
   }


    queryCompanys();

    queryBranchs();

//    queryReportData();
}