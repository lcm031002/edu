/**
 * @author liufq@klxuexi.org 2017/04/12
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_StudentOrdersYdyController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        '$uibMsgbox',
        'erp_studentsService',
        'erp_studentOrdersYdyService',
        erp_StudentOrdersYdyController]);

function erp_StudentOrdersYdyController(
	$rootScope,
	$scope,
	$cookieStore,
    $log,
    $uibMsgbox,
    erp_studentsService,
    erp_studentOrdersYdyService) {
    //学员信息
    $scope.student = {};

    $scope.selectMonthModel = [
        {
            value : -1,
            label : '全部'
        },
        {
            value : 1,
            label : '最近1个月'
        },
        {
        	value : 2,
            label : '最近2个月'
        },
        {
        	value : 3,
            label : '最近3个月'
        },
        {
        	value : 4,
            label : '最近4个月'
        },
        {
        	value : 5,
            label : '最近5个月'
        } ];

    $scope.monthOrder = $scope.selectMonthModel[0];
    
    /**
	 * 分页配置
	 * 
	 * @param {Number}
	 *            currentPage [当前页面，初始化时默认为1]
	 * @param {Number}
	 *            totalItems [数据总条数，每次查询时赋值]
	 * @param {Number}
	 *            itemsPerPage [每页显示条数]
	 * @param {Number}
	 *            pagesLength [可选，分页栏长度,默认为9]
	 * @param {Array}
	 *            perPageOptions [可选，默认]
	 * @param {Function}
	 *            perPageOptions [description]
	 */
	$scope.paginationConf = {
		currentPage : 1, // 当前页
		totalItems : 0,
		onChange : function() {
			queryStudentOrdersYdy();
		}
	}
    
    $scope.isLoading = '';
    $scope.selectMonth = function(month){
        $scope.monthOrder = month;
        queryStudentOrdersYdy();
    }

    function queryStudentOrdersYdy(){
        $scope.isLoading = 'isLoading';
        $log.log($scope.monthOrder.value);
        erp_studentOrdersYdyService.query({
        	pageSize : $scope.paginationConf.itemsPerPage,
			currentPage : $scope.paginationConf.currentPage,
            studentId:$scope.studentId,
            month:$scope.monthOrder.value,
            businessType:2
        },function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.courseYDYInfoList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
				$uibMsgbox.error(resp.message);
			}
        });
    }

    $scope.studentId = $("#rootIndex_studentId").val();
    
    function queryStudentInfo(){
        erp_studentsService.query(
            {
                row_num: 20,
                studentId: $scope.studentId
            },
            function(resp){
                if(!resp.error && resp.data.length){
                    $scope.student = resp.data[0];
                    initial();
                }else{
                    alert(resp.message);
                }
            });
    }
    
    function initial(){
        queryStudentOrdersYdy();
        $('title').text('学员|'+ $scope.student.student_name);
    }

    queryStudentInfo();
}