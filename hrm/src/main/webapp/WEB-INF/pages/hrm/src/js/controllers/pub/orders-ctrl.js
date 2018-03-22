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
    .controller('OrdersCtrl', ['$scope', '$log','$state','CompanyService','OrdersService', 'OrderCoursesService',OrdersCtrl]);

function OrdersCtrl($scope, $log,$state,CompanyService,OrdersService,OrderCoursesService) {
    $scope.companys = [];
    $scope.orderData = [];

    $scope.checkAll = false;
    $scope.search_info = "";

    $scope.currentCourse = {};
    $scope.paginationBars = [];
    $scope.initPageNumber=false;
 
    /**
     * 选择订单
     * @param company
     */
    $scope.selectCompany = function(company){
        var selectedOrder =  new Array();
        var updateOrders=[];
        $.each($scope.orderData,function(i,model){
        	//提交
            if(model.checked){
            	if(model.company_name){
            		updateOrders.push(model.id);
            	}
            	else{
                selectedOrder.push(
                   model.id
                );
            	}
             
            }
        });

        if(!selectedOrder.length&&!updateOrders.length){
            alert("请选择订单！");
        }else{
            var param = {};
            param.orders =selectedOrder;
            param.updateOrders=updateOrders;
            param.companyId=company.id;
            //提交后台
            OrdersService.comments(param,function(resp){
                if(resp.error == 'false'||resp.error == false){
                    alert("批注成功！");
                	$scope.queryIndex($scope.currentPage);//刷新页面
                }
            },function(resp){
                alert("error found!");
                $log.log(resp);
            });
        }
    }

    /**
     * 选择全部订单
     */
    $scope.checkAllOrder = function(){
        if($scope.checkAll){
            $.each($scope.orderData,function(i,model){
                model.checked = false;
            });
            $scope.checkAll = false;
        }else{
            $.each($scope.orderData,function(i,model){
                model.checked = true;
            });
            $scope.checkAll = true;
        }

    }

    /**
     * 订单选择
     * @param order
     */
    $scope.checkOrder = function(order){
        if(order.checked){
            order.checked = false;
        }else{
            order.checked = true;
        }
        var checkedAll = true;
        $.each($scope.orderData,function(i,model){
            if(!model.checked){
                checkedAll = false;
            }
        });
        $scope.checkAll = checkedAll;

    }

    /**
     * 查询订单信息
     */
    $scope.queryInfo = function(){
        queryOrders();
    }

    /**
     * 展示详情
     * @param order
     */
    $scope.showDetail = function(order){
    	$("#orderDetailsCapacity").modal("show");
        /*if(order.showDetail){
            order.showDetail = false;
        }else{
            order.showDetail = true;
            queryOrderCourses(order);
        }*/
    }
    
   
   $scope.queryERPStudentList = function(course){
       $scope.currentCourse.courseId =course.courseId;
       $('#myModal').modal('show');
   }

   $scope.queryPUBStudentList = function(course){
       $scope.currentCourse.courseId =course.courseId;
       $('#myModal2').modal('show');

   }
    //订单同步/取消操作
    $scope.orderSync=function(order){
    	orderSync(order);
    	
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
    	 $scope.queryInfo();
    }
    
    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get(function(resp){
            if(resp.error == 'false'||resp.error == false){
                $scope.companys = resp.data;
            }
        })
    }
    
    function orderSync(order){
    	  var param = {};
    	  param.status=order.sync==0?1:0;
    	  if(order.id)
    	  param.orderId=order.id;
    	  if(order.orderCourseId)
    	  param.orderCourseId=order.orderCourseId;
    	  OrdersService.sync(param,function(resp){
    		order.orderId=null;
    		order.sync=param.status;
      	   if(resp.error==false||typeof(resp.error)=='undefined'){
      		   alert("操作成功!");
      	   }
      	   else{
      		 alert("当前操作的订单未找到归属公司!");
      	   }
      })
    }

    function queryOrders(){
        var param = {};
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.page=$scope.currentPage;
        param.totalPage = $scope.totalPage;
        param.p_searchInfo = $scope.search_info;
        $scope.isLoadingOrders = 'loading...';
        $scope.orderData = [];
        OrdersService.get(param,function(resp){
                $scope.orderData = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                changeNumber();
                $scope.isLoadingOrders = '';
        })
    }
    
 
   
    function queryOrderCourses(order){
        var param = {};
        param.orderId =  order.id;
        order.isLoadingCourses = 'loading...';
        order.orderCourses = [];
        OrderCoursesService.get(param,function(resp){
          if(resp.error == 'false'||resp.error==false){
               order.orderCourses =  resp.data;
               order.isLoadingCourses = '';
           }
        });
    }
    
    
	  /**
     * 导出数据
     */
	$scope.exports=function(){
		exportData();
	}
		
    //导出数据
    function exportData(){
    	var paramUrl="";
    	if($scope.search_info){
    		paramUrl += "?searchInfo=" + $scope.search_info;
    	}
        window.open("excels/output/orders_01" + paramUrl, "_blank");
    }
    


    queryCompanys();


    queryOrders();
    

}