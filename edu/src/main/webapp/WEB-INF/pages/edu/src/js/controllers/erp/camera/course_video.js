/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_courseVideoController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_dictService',
    'erp_videoService',
    erp_courseVideoController
]);

function erp_courseVideoController($rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_dictService,
    erp_videoService) {
    $scope.searchParam = {
        p_beginDate: moment().startOf('today').add(-7,'day').format('YYYY-MM-DD'),
        p_endDate: moment().endOf('today').format('YYYY-MM-DD'),
        p_orgKind: null
    }
    $scope.videoList = []; // 双师视频列表

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
        onChange: function () {
            $scope.query();
        }
    };

    /**
     * 查询校区
     */
    $scope.dictOrgKindList = []; // 校区类型选择框下拉值
    $scope.queryDictOrgKindList = function() {
        erp_dictService.query({"code" : "orgKind"}, function(resp) {
            if (!resp.error && resp.data && resp.data.length > 0) {
                $scope.dictOrgKindList = resp.data;
            }
        })
    }

    $scope.checkBeforeQuery = function() {
		if(isEmpty($scope.searchParam.p_beginDate)){
			$uibMsgbox.confirm("请填写开始日期");
			return false;
		}
		if(isEmpty($scope.searchParam.p_endDate)){
			$uibMsgbox.confirm("请填写结束日期");
			return false;
		}
    	return true;
    }

    $scope.handleExportExcel = function () {
        if ($scope.checkBeforeQuery()) {
            var params = _.cloneDeep($scope.searchParam);

            var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');

            erp_videoService.outputExcel(params, function (resp) {
                _uibModalInstance.close();
                if (!resp.error) {
                    //下载excel
                    window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    }

    $scope.init = function() {
        $scope.queryDictOrgKindList();
        $scope.query();
    };
    // 查询方法
    $scope.query = function() {
        if ($scope.checkBeforeQuery()) {
            $scope.loadStatues = true;
            erp_videoService.page({
                pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
                currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
                p_beginDate: $scope.searchParam.p_beginDate,
                p_endDate: $scope.searchParam.p_endDate,
                p_orgKind: $scope.searchParam.p_orgKind || ''
            }, function (resp) {
                if (!resp.error) {
                    $scope.loadStatues = false;
                    $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                    $scope.videoList = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    };

    $scope.init();
}
