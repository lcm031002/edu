angular.module('ework-ui').controller('report_taskResultController', [
    '$rootScope',
    '$scope',
    '$uibMsgbox',
    '$uibModal',
    'report_taskResultService',
    report_taskResultController
]);

function report_taskResultController($rootScope,
    $scope,
    $uibMsgbox,
    $uibModal,
    report_taskResultService) {

    $scope.searchParam = {};
    
    $scope.taskResult = {};
    
    $scope.taskResultList = [];
    
    $scope.typeList = [{
		id : 'common',
		text : '通用'
	}, {
		id : 'bjk',
		text : '班级课'
	}, {
		id : 'ydy',
		text : '一对一'
	}, {
		id : 'wfd',
		text : '晚辅导'
	}];
    
    $scope.runResultList = [{
		id : 1,
		text : '成功'
	}, {
		id : 0,
		text : '失败'
	}];
    
    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，默认]
     * @param  {Function} perPageOptions [description]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function() {
            $scope.queryTaskResult();
        }
    };

    $scope.paginationBars = [];
    
    $scope.queryTaskResult = function () {
		$scope.loadStatues = true;
		$scope.searchParam.pageSize = $scope.paginationConf.itemsPerPage;
		$scope.searchParam.currentPage = $scope.paginationConf.currentPage;
		report_taskResultService.query($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.taskResultList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    
    $scope.executeTask = function(taskResult) {
    	var _uibModalInstance = $uibMsgbox.waiting('运行中，请稍候...');
    	report_taskResultService.execute({
    		taskFlow : taskResult.taskFlow,
    		taskId : taskResult.taskId
    	}, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
            	$scope.queryTaskResult();
            	$uibMsgbox.alert("运行成功！");
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }    
    
    $scope.initPage = function() {
    	$scope.queryTaskResult();
    }
    
    $scope.initPage();
}