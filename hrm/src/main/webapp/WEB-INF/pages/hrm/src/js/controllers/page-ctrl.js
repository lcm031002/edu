
/**
 * 分页组件
 */

angular.module('ework-ui')
    .controller('PaginationDemoCtrl', ['$scope', '$log',PaginationDemoCtrl]);
function PaginationDemoCtrl($scope, $log){
	  $scope.$parent.$watch('loaded', function(value){loadData()});
	  $scope.viewby = 5;
	  $scope.currentPage = 4;
	  $scope.itemsPerPage = $scope.viewby;
	  $scope.maxSize = 50; 
	  $scope.setPage = function (pageNo) {
	    $scope.currentPage = pageNo;
	  };
	  $scope.pageChanged = function() {
		  if($scope.$parent.datas)
			  $scope.child.currentPage= $scope.currentPage;
		      $scope.child.itemsPerPage= $scope.itemsPerPage;
	  };
	  $scope.selectPage=function(){
			 $scope.child.itemsPerPage= $scope.itemsPerPage;
	  }
	  $scope.setItemsPerPage = function(num) {
		  $scope.itemsPerPage = num;
		  $scope.currentPage = 1;
		}
	  function loadData(){
			//加载完之后执行赋值
		    if($scope.loaded==true){
		    	 $scope.data=$scope.$parent.datas;
			     $scope.totalItems = $scope.data.length;
				 $scope.child.currentPage= $scope.currentPage;
				 $scope.child.itemsPerPage= $scope.itemsPerPage;
		    }
		}
	
}
