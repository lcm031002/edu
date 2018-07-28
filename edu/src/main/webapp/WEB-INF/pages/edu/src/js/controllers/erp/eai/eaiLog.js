"use strict";
angular.module('ework-ui').controller('erp_eaiLogController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    'erp_eaiLogService',
    erp_eaiLogController
    ]);

function erp_eaiLogController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_eaiLogService
  ) {
	
	 //日志类型列表
    $scope.logTypeList =  [{"key" : 1, "value" : "订单消息提醒"}, {"key" : 3, "value" : "双师接口"}];
    // 双师接口
    $scope.mtMethodList =  [{"key" : "syncErpOrder", "value" : "订单同步双师"}, {"key" : "syncErpOrderPay", "value" : "订单支付同步双师"},
        {"key" : "syncErpOrderCancel", "value" : "订单作废同步双师"}, {"key" : "syncErpTransferClass", "value" : "转班同步双师"},
        {"key" : "syncErpCourseRefund", "value" : "课次冻结、退费同步双师"}, {"key" : "syncErpAttendance", "value" : "考勤同步双师"},
        {"key" : "syncErpCourseRefundCancel", "value" : "课次冻结、退费作废同步双师"}];
    //状态列表
    $scope.statusList =  [{"key" : 0, "value" : "同步失败"},{"key" : 1, "value" : "同步成功"}];
    //系统列表
    $scope.sysList =  [{"key" : "ERP", "value" : "ERP系统"},{"key" : "OPENAPI", "value" : "OPENAPI接口服务"},{"key" : "GATEWAY", "value" : "GATEWAY"}];
    // 搜索日志名称
    $scope.searchParam = {
        log_type: '',
        soruce_sys:'',
        in_sys:'',
        status:''
    };
    // 日志列表
    $scope.eaiLogList = [];
    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
     * @param  {Function} onChange      [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function(){
            $scope.query();
        }
    };
    
    // 全选事件
    $scope.onCheckAll = function () {
        _.forEach($scope.eaiLogList ,function(eaiLog) {
        	eaiLog.selectFlag = $scope.checkAllFlag;
        })
    }

    // 某一行Checkbox选择事件
    $scope.onLogChecked = function (eaiLog) {
        $scope.checkAllFlag = _.every($scope.eaiLogList, {selectFlag: true});
    }
   
    function getSelectedCourseList () {
        var eaiLogList = [];
        _.forEach($scope.eaiLogList ,function(eaiLog) {
            if (eaiLog.selectFlag) {
            	eaiLogList.push(eaiLog)
            }
        })
        return eaiLogList;
    }
    
    //获取所有选中的id
    function getSelectedIds () {
        var ids = "";
        _.forEach($scope.eaiLogList ,function(eaiLog) {
            if (eaiLog.selectFlag) {
            	ids+= "," + eaiLog.id;
            }
        })
        return ids.substring(1);
    }
    
    // 处理【查询日志】按钮点击事件
    $scope.handleQueryLog= function () {
        $scope.query();
    };
    
    $scope.handleSendData = function () {
    	var ids = getSelectedIds();
    	erp_eaiLogService.repeatSendData({"ids" : ids}, function (resp) {
    		if(!resp.error) {
    			$uibMsgbox.alert("操作成功!");
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }

    // 查询方法
    $scope.query = function () {
    	erp_eaiLogService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            p_log_type: $scope.searchParam.log_type,
            p_source_sys:$scope.searchParam.source_sys,
            p_in_sys:$scope.searchParam.in_sys,
            p_status:$scope.searchParam.status,
            p_method:$scope.searchParam.method,
            p_search_info:$scope.searchParam.search_info
        }, function (resp) {
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.eaiLogList=resp.data;
            } else {
            	$uibMsgbox.error(resp.message);
            }
            return resp.eaiLogList;
        });
    };
    
    $scope.query();
}
