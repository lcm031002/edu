function agPageObject(scope,service,searchParam){
	
    scope.currentPage = 1;
    scope.totalPage = 1;
    scope.pageSize = scope.pageSize?scope.pageSize:10;
    scope.pages = [];
    scope.endPage = 1;
    scope.items = [];
    scope.searchParam =searchParam;
    
    
    scope.dataHandler=function(data){
       scope.isPerformanceLoading='';
   	   scope.items = data.rows;
   	   scope.totalPage = data.totalPage;
   	   scope.total = data.total;
   	   //获取总页数
     	 scope.endPage = scope.totalPage;
     	var length = scope.totalPage - scope.currentPage;
   	    //生成数字链接
       if (scope.currentPage > 1) {
   	       if(length<5) {
   	    	 scope.pages = [scope.currentPage - 1];
   	    	for(var i=0;i<=length;i++) {
   	   	    	scope.pages.push(scope.currentPage+i);
   	   	       }
   	       }else {
   	    	scope.pages = [scope.currentPage - 1,scope.currentPage, scope.currentPage + 1,scope.currentPage + 2,scope.currentPage + 3];
   	       }
       } else if (scope.currentPage == 1) {
    	   if(length<5) {
     	    	for(var i=0;i<=length;i++) {
     	   	    	scope.pages.push(scope.currentPage+i);
     	   	       }
     	       }else {
     	    	  scope.pages = [ scope.currentPage, scope.currentPage + 1,scope.currentPage + 2,scope.currentPage + 3,scope.currentPage + 4];
     	       }
       } 
       scope.pageCallBack();
   }
    
    scope.refresh=function(){
    	scope.currentPage = 1;
        scope.totalPage = 1;
        scope.pages = [];
        scope.endPage = 1;
        scope.items = [];
        scope.isPerformanceLoading='loading...';
        scope.load();
    }
    
    scope.load = function(){
    	scope.searchParam.page = scope.currentPage;
    	scope.searchParam.rows = scope.pageSize;
    	service.page(scope.searchParam,scope.dataHandler,function(e){
    		alert(e.data);
    	});
    };
    
    scope.next = function () {
          if(scope.currentPage < scope.totalPage) {
    	          scope.currentPage++;
    	          scope.load();
    	    }
    };
    	
    scope.prev = function () {
	      if(scope.currentPage > 1) {
	          scope.currentPage--;
	          scope.load();
	      }
    };
    	 
    scope.loadPage = function (page) {
    	        scope.currentPage = page;
    	        scope.load();
    };
}