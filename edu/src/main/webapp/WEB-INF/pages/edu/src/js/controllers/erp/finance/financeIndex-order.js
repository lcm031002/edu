"use strict";
angular
    .module('ework-ui')
    .controller('erp_FinanceOrderController', [
        '$rootScope',                                                             	
		'$scope',
		'erp_FinanceOrderService',
        '$state',
        '$log',
        '$cookieStore',
		function(
				 $rootScope,
				 $scope,
				 erp_FinanceOrderService,
                 $state,
                 $log,
                 $cookieStore
            ) {
        	 $scope.orderList = [];
        	 $scope.pageParam = new Object();
        	 $scope.searchOrder = function(){
                 $scope.orderList = [];
                 $scope.isDown = 'loading';
                 erp_FinanceOrderService.query(
                     {
                         pageSize: 10,
                         order_no: $scope.order_no,
                         business_type: $scope.business_type,
                         order_type: $scope.order_type,
                         currentPage:1
                     },
                     function(resp){
                         $scope.isDown = '';
                         if(!resp.error){
                        	 $scope.orderList = resp.data;
                        	 $scope.pageParam.totalPage = resp.totalPage;
                        	 $scope.pageParam.pageSize = resp.pageSize;
                        	 $scope.pageParam.currentPage = resp.currentPage;
                        	 if ($scope.pageParam.currentPage > 1 && $scope.pageParam.currentPage < $scope.pageParam.totalPage) {
                	     	       $scope.paginationBars = [$scope.pageParam.currentPage - 1,$scope.pageParam.currentPage, $scope.pageParam.currentPage + 1];
                          	 } else if ($scope.pageParam.currentPage == 1 && $scope.pageParam.totalPage > 1) {
                	     	       $scope.paginationBars = [ $scope.pageParam.currentPage, $scope.pageParam.currentPage + 1];
                          	 } else if ($scope.pageParam.currentPage == $scope.pageParam.totalPage && $scope.pageParam.totalPage > 1) {
                	     	       $scope.paginationBars = [ $scope.pageParam.currentPage - 1,$scope.pageParam.currentPage];
                          	 }
                         }else{
                             alert(resp.message);
                         }
                     });
 			};
 			 $scope.queryOrder = function(pageNum){
                 $scope.orderList = [];
                 $scope.isDown = 'loading';
                 erp_FinanceOrderService.query(
                     {
                         row_num: 10,
                         order_no: $scope.order_no,
                         business_type: $scope.business_type,
                         order_type: $scope.order_type,
                         currentPage:pageNum
                     },
                     function(resp){
                         $scope.isDown = '';
                         if(!resp.error){
                        	 $scope.orderList = resp.data;
                        	 $scope.pageParam.totalPage = resp.totalPage;
                        	 $scope.pageParam.pageSize = resp.pageSize;
                        	 $scope.pageParam.currentPage = resp.currentPage;
                        	 if ($scope.pageParam.currentPage > 1 && $scope.pageParam.currentPage < $scope.pageParam.totalPage) {
              	     	       $scope.paginationBars = [$scope.pageParam.currentPage - 1,$scope.pageParam.currentPage, $scope.pageParam.currentPage + 1];
                        	 } else if ($scope.pageParam.currentPage == 1 && $scope.pageParam.totalPage > 1) {
              	     	       $scope.paginationBars = [ $scope.pageParam.currentPage, $scope.pageParam.currentPage + 1];
                        	 } else if ($scope.pageParam.currentPage == $scope.pageParam.totalPage && $scope.pageParam.totalPage > 1) {
              	     	       $scope.paginationBars = [ $scope.pageParam.currentPage - 1,$scope.pageParam.currentPage];
                        	 }
                         }else{
                             alert(resp.message);
                         }
                     });
 			};
 			$scope.searchOrder();
        }
]);
