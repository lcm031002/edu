/**
 * Created by hengshan.ou on 2017/1/16.
 */

"use strict";
angular
    .module('ework-ui')
    .controller('erp_OrdersIndexCouponRuleRelController', [
    '$rootScope',                                                             	
    '$scope',
    'erp_studentsService',
    'erp_couponInfoServices',
    'erp_couponRuleRelService',
    '$state',
    '$log',
    '$cookieStore',
     function(
    		    $rootScope,                                                             	
    		    $scope,
    		    erp_studentsService,
    		    erp_couponInfoServices,
    		    erp_couponRuleRelService,
        	    $state,
        	    $log,
        	    $cookieStore) {
    			$scope.business_type = 1; 
        		$scope.studentInfoList = [];
        	    $scope.studentInfo = {}; 
        	 	$scope.pageParam = new Object();
        	 	$scope.couponInfoList = {};
        	 	$scope.currStudentInfo = {};
        	 	$scope.currPrivilegeId = "";
        	 	
        	    $scope.selectBusinessType = function(business_type){
        	        $scope.business_type = business_type;
        	        return true;
        	    }
        	    
        	    /**
        	     * 分页查询学生信息
        	     */
        	    $scope.queryStudentInfo = function(pageNum){
        	        $scope.isQueryStudentInfo = true;
        	        $scope.studentInfoList = [];
        	        erp_studentsService.query(
        	            {
        	            	row_num: 20,
        	                currentPage:pageNum,
        	                studentId: $scope.studentInfoQueryInfo
        	            },
        	            function(resp){
        	                $scope.isQueryStudentInfo = false;
        	                if(!resp.error){
        	                	$scope.studentInfoList = resp.data;
        		               	$scope.pageParam.totalPage = resp.totalPage;
        		            	$scope.pageParam.pageSize = resp.pageSize;
        		            	$scope.pageParam.currentPage = resp.currentPage;
        		            	if ($scope.pageParam.currentPage > 1 && $scope.pageParam.currentPage < $scope.pageParam.totalPage) {
        		    	     	      $scope.paginationBars = [$scope.pageParam.currentPage - 1,$scope.pageParam.currentPage, $scope.pageParam.currentPage + 1];
        		              	} else if ($scope.pageParam.currentPage == 1 && $scope.pageParam.totalPage > 1) {
        		    	     	      $scope.paginationBars = [ $scope.pageParam.currentPage, $scope.pageParam.currentPage + 1];
        		              	} else if ($scope.pageParam.currentPage == $scope.pageParam.totalPage && $scope.pageParam.totalPage > 1) {
        		    	     	      $scope.paginationBars = [ $scope.pageParam.currentPage - 1,$scope.pageParam.currentPage];
        		              	} else if ($scope.pageParam.currentPage == $scope.pageParam.totalPage && $scope.pageParam.totalPage == 1) {
        		              		  $scope.paginationBars = [ $scope.pageParam.currentPage];
        		              	}
        	                }else{
        	                    alert(resp.message);
        	                }
        	            });
        	    }
        	    
        	    /**
        	     * 展开和关闭课程
        	     */
        	    $scope.toAddCouponRuleRel = function(){
        	    	$scope.selectedStudentInfoList = [];
        	    	queryCouponInfo();
        	    	
        	        angular.forEach($scope.studentInfoList, function (studentInfo) {
        	        	if(studentInfo.Selected){
        	        		var param ={};
        	        		param.id = studentInfo.id;
        	        		param.encoding = studentInfo.encoding;
        	        		param.student_name = studentInfo.student_name;

        	        		$scope.selectedStudentInfoList.push(param);
        	        	}
        	        });
        	        
        	        angular.forEach($scope.selectedStudentInfoList, function (selectedStudent) {
        	        	selectedStudent.Selected = true;
        	        });
        	        
        	        $scope.openPanel = 'toManageCouponRuleRel';
        	    }
        	    
        	    /**
        	     * 关闭窗口
        	     */
        	    $scope.closePanel = function(){
        	        $scope.openPanel = '';
        	    }
        	    
        	    /**
        	     * 保存优惠券
        	     */
        	    $scope.saveCouponRuleRel = function(){
        	        var param = {};
        	        $scope.openPanel = 'saveCouponRuleRel';
        	        genCouponRuleRel(param);
        	        erp_couponRuleRelService.post(param,function(resp){

        	            if(!resp.error){
        	                $scope.openPanel = 'saveCouponRuleRelOK';
        	                $scope.temporaryOrder = resp.data;
        	                $cookieStore.remove("temporaryOrderId");
        	            }else{
        	                $scope.openPanel = '';
        	                alert(resp.message);
        	            }
        	        },function(resp){
        	            $scope.openPanel = '';
        	            alert("保存失败！");
        	        }) 
        	    }
        	    
        	    function genCouponRuleRel(param){
        	    	$scope.strStudentIds = "";
        	        angular.forEach($scope.selectedStudentInfoList, function (selectedStudent) {
        	        	if(selectedStudent.Selected){
        	        		$scope.strStudentIds += selectedStudent.id +",";
        	        	}
        	        });
        	        
        	        if($scope.strStudentIds.length > 0){
        	        	$scope.strStudentIds = $scope.strStudentIds.substr(0,$scope.strStudentIds.length - 1);
        	        }
        	    	
        	        param.encoding = '100005104';
        	        param.study_ids = $scope.strStudentIds;
        	    }
        	    
        	    /**
        	     * 查询优惠券
        	     */
        	    function queryCouponInfo(){
        	    	erp_couponInfoServices.query({},function(resp){
        	            if(!resp.error){
        	                $scope.couponInfoList = resp.data;
        	            }
        	        })
        	    }
        	    
        	    /**
        	     * 选择所有的学生
        	     */
        	    $scope.checkedAllStudents = function(){
        	        if($scope.studentInfo.checkelStudents){
        	        	$scope.studentInfo.checkelStudents = true;
        	        }else{
        	        	$scope.studentInfo.checkelStudents = false;
        	        }
        	        
        	        angular.forEach($scope.studentInfoList, function (student) {
        	        	student.Selected = $scope.studentInfo.checkelStudents;
        	        });
        	    }
        	    
        	    $scope.queryStudentInfo(1);
        }
]);

