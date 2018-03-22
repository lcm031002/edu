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
    	.controller('AccomplishmentDetailCtrl', ['$scope', '$log','$state','CompanyService','BranchsService','ReportService',AccomplishmentDetailCtrl]);
    
    function AccomplishmentDetailCtrl($scope, $log,$state,CompanyService,BranchsService,ReportService){
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
    	 $scope.queryInfo();
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
        param.rows = 9; //页数
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;
        param.search_info = $scope.search_info;
		param.companyId = $scope.selectedCompany.id;
		param.branchId = $scope.selectedBranch.id;
		param.calcBeginDate = $('#beginDate').val();
		param.calcEndDate = $('#endDate').val();
		
		param.isWfd = $('#isWfd').prop('checked');
        param.reportType = "queryAccomplishmentDetais";
        $scope.isLoadingOrders = 'loading...';
        $scope.reportData = [];
        ReportService.query(param,function(resp){
            if(resp.error == 'false'){
                $scope.reportData = resp.data;
                setRowSpan($scope.reportData);
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                changeNumber();
                $log.log("pageSize is"+$scope.pageSize+",totalPage is "+$scope.totalPage+",currentPage"+$scope.currentPage);
            }
            $scope.isLoadingOrders = '';
        },
        function(err){
      	  alert('异常:' + err.data.message+'请联系管理员！');
      	  $scope.isLoadingOrders = '';
       })
    }

	function setRowSpan(data){
		var dataMap = {};
		var countSub = 0;
		var preOptEncoding = null;
		var preModel = null;
	    var curOptEncoding = null;
	    var groupIndex = 0;
		$.each(data,function(i,model){
			model.first = false;
			if(isEmpty(preOptEncoding)){
				preOptEncoding = model.optEncoding;
				preModel = model;
				model.first = true;
				groupIndex = i;
			}
			curOptEncoding = model.optEncoding;
//			去除报班作废中金额为0并且校区不同的
			if(preOptEncoding == curOptEncoding && !(preOptEncoding == curOptEncoding && model.optType ==6
					&& model.feeAmount == 0 && model.courseBranchName != preModel.courseBranchName)){
				countSub++;
			}
			
			if(preOptEncoding != curOptEncoding || (preOptEncoding == curOptEncoding && model.optType ==6
					&& model.feeAmount == 0 && model.courseBranchName != preModel.courseBranchName)){
				preOptEncoding = curOptEncoding;
				preModel = model;
				data[groupIndex].countSub = countSub;
				
				groupIndex = i;
				model.first = true;
				countSub = 1;
			}
		});
		if(countSub > 1){
			data[groupIndex].countSub = countSub;
		}
		
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
							+ "&isWfd=" + $('#isWfd').prop('checked')
							+ "&calcBeginDate=" + $('#beginDate').val()
							+ "&calcEndDate=" + $('#endDate').val() 
			window.open("excels/output/outExcelAccomplishmentdetail" + paramUrl, "_blank");
		}
    }

    queryCompanys();

    queryBranchs();

//    queryReportData();
}