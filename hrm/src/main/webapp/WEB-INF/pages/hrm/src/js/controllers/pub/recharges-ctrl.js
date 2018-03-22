/**
 * Created by Liyong.zhu on 2016/5/31.
 */
angular.module('ework-ui')
    .controller('RechargesCtrl', ['$scope', '$log','$state','CompanyService', 'RechargesService',RechargesCtrl]);

function RechargesCtrl($scope, $log,$state,CompanyService,RechargesService){
    $scope.companys = [];
    $scope.initPageNumber=false;
    $scope.paginationBars = [];
    /**
     * 选择订单
     * @param company
     */
    $scope.selectCompany = function(company){
        var selected = [];
        var updateRecharges=[];
        $.each($scope.rechargesData,function(i,model){
            if(model.checked){
            	//批注过不做添加而做覆盖处理
            	if(model.company_name){
            		updateRecharges.push(model.id);
            	}
            	else{
            	selected.push(
                   model.id
                );
            	}
            	
            }
        });

        if(!selected.length&&!updateRecharges.length){
            alert("请选择订单！");
        }else{
            var param = {};
            param.recharges = selected;
            param.companyId=company.id;
            param.updateRecharges=updateRecharges;
            //提交后台
            RechargesService.post(param,function(resp){
                if(resp.error == 'false'||resp.error == false){
                    alert("提交成功！");
                    $scope.queryIndex($scope.currentPage);//刷新页面
                }
            },function(resp){
                alert("error found!");
                $log.log(resp);
            });
        }
    }


    /**
     * 选择全部
     */
    $scope.checkAllRows = function(){
        if($scope.checkAll){
            $.each($scope.rechargesData,function(i,model){
                model.checked = false;
            });
            $scope.checkAll = false;
        }else{
            $.each($scope.rechargesData,function(i,model){
                model.checked = true;
            });
            $scope.checkAll = true;
        }

    }

    /**
     * 选择行
     * @param row
     */
    $scope.check = function(row){
        if(row.checked){
            row.checked = false;
        }else{
            row.checked = true;
        }
        var checkedAll = true;
        $.each($scope.rechargesData,function(i,model){
            if(!model.checked){
                checkedAll = false;
            }
        });
        $scope.checkAll = checkedAll;

    }
    
    //充值单定时
    $scope.timing=function(recharge){
    	var param = {};
    	param.rechargeId=recharge.id;
    $log.log("rechargeId="+param.rechargeId)
    	RechargesService.rechargeTiming(param,function(resp){
            if(resp.error == 'false'||resp.error == false){
                alert("操作成功！");
                $scope.queryIndex($scope.currentPage);//刷新页面
            }
        },function(resp){
            alert("error found!");
            $log.log(resp);
        });
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
    /**
     * 查询单据信息
     */
    $scope.queryInfo = function(){
        queryRecharges();
    }

    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get({},function(resp){
        	 if(resp.error == 'false'||resp.error == false){
                 $scope.companys = resp.data;
             }
        })
    }

    /**
     * 展示详情
     * @param row
     */
    $scope.showDetail = function(row){
        if(row.showDetail){
            row.showDetail = false;
        }else{
            row.showDetail = true;
        }
    }

    function queryRecharges(){
        var param = {};
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.page= $scope.currentPage;
        param.totalPage = $scope.totalPage;
        param.p_searchInfo = $scope.search_info;
        $scope.isLoadingRechargesData = 'loading...';
        $scope.rechargesData = [];
        RechargesService.get(param,function(resp){
            if(resp.error == 'false'||resp.error == false){
                $scope.rechargesData = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.page= resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                changeNumber();
            }
           
            $scope.isLoadingRechargesData = '';
        })
    }

    
    $scope.queryIndex=function($index){
    	if(($index<=0||$index>$scope.totalPage)&&$scope.totalPage>0)
    		return;
    	 $scope.currentPage=$index;
    	 $scope.queryInfo();
    }
    
    /**
     * 导出数据
     */
	$scope.exports=function(){
		exportData();
	}
		
    //导出数据
    function exportData(){
    	//导出数据
    	var paramUrl="";
    	if($scope.search_info){
    		paramUrl += "?searchInfo=" + $scope.search_info;
    	}
        window.open("excels/output/recharges_01" + paramUrl, "_blank");
    }
    

    queryCompanys();


    queryRecharges();
}