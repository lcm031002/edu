/**
 * @author liufq@klxuexi.org 2017/04/19
 */
angular.module('ework-ui')
    .controller('erp_epayWapController', [
        '$rootScope',
        '$scope',
        '$uibMsgbox',
        'erp_epayWapService',
        erp_epayWapController
    ]);

function erp_epayWapController(
		$rootScope,
		$scope,
		$uibMsgbox,
		erp_epayWapService) {
	
	//学员信息
    $scope.payFlow = {};
    
    $scope.allDataArray = [];
    
    //团队
    $scope.teamList = [];
    
    // 搜索字段
    $scope.searchParam = {
        beginDate: '',
        endDate: '',
        beginDate: getCurrentDate(),
        endDate: getCurrentDate()
    };
    $scope.isLoading = '';
    
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
			queryPayFlow();
		}
	}
	
	// 处理【搜索】按钮点击事件
    $scope.handleQueryInvoice = function () {
		queryPayFlow();
    }
	
	//查询支付团队
	function queryPayAcountTeam() {
		erp_epayWapService.queryTeam({}, function(resp) {
			if (!resp.error) {
				$scope.teamList = resp.data;
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	}
	
	// 分页查询
	function queryPayFlow(){
        $scope.isLoading = 'isLoading';
    	erp_epayWapService.query({
        	pageSize : $scope.paginationConf.itemsPerPage,
			currentPage : $scope.paginationConf.currentPage,
            buId:$scope.searchParam.buId,
            keyWord:$scope.searchParam.keyWord,
            beginDate:$scope.searchParam.beginDate,
            endDate:$scope.searchParam.endDate
        },function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.payFlowList = resp.data;
                $scope.totalAmount = resp.totalAmount;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $scope.isLoading = '';
				$uibMsgbox.error(resp.message);
			}
        });
        
    }
	
	//查询list
	function queryPayFlowList(){
		
		erp_epayWapService.list({
            buId:$scope.searchParam.buId,
            keyWord:$scope.searchParam.keyWord,
            beginDate:$scope.searchParam.beginDate,
            endDate:$scope.searchParam.endDate
        },function(resp){
            if(!resp.error){
                $scope.allDataArray = resp.data;
              //导出excel
                outputExcel($scope.allDataArray);
            } else {
				$uibMsgbox.error(resp.message);
			}
        });
	}
	
	//导出excel
	function outputExcel(dataList) {
        var param = {
            dataList: dataList
        };
        erp_epayWapService.output(param, function (resp) {
            if (!resp.error) {
                //下载
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }
	
	// 全部导出
    $scope.exportAll = function () {
    	//先根据当前条件查询list
    	queryPayFlowList();
    };
    
    //默认查询团队
    queryPayAcountTeam();
	
}
