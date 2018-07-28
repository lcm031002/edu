"use strict";
angular.module('ework-ui').controller('erp_doubleTeacherSyncCtrl', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    'erp_doubleTeacherSyncService',
    erp_doubleTeacherSyncCtrl
    ]);

function erp_doubleTeacherSyncCtrl(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_doubleTeacherSyncService
  ) {
    $scope.searchType = "0" ;
    // 搜索双师数据名称
    $scope.searchParam = {
        mainCourseCdtn: '',
        subCourseCdtn:'',
        orderNo:''
    };

    // 双师数据列表
    $scope.MTSyncList = [];

    // 查询方法
    $scope.query = function () {
        $scope.loadStatues = true;
    	erp_doubleTeacherSyncService.query({
            mainCourseCdtn: $scope.searchParam.mainCourseCdtn,
            subCourseCdtn:$scope.searchParam.subCourseCdtn,
            orderNo:$scope.searchParam.orderNo
        }, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.MTSyncList=resp.data;
            } else {
            	$uibMsgbox.error(resp.message);
            }
            return resp.MTSyncList;
        });
    };

    
    $scope.handleExportExcel = function (exp) {
        var params = _.cloneDeep($scope.searchParam);
        params.exportAll = exp;
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');

        erp_doubleTeacherSyncService.outputExcel(params, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载excel
                if(!resp.data){
                    $uibMsgbox.confirm('数据为空');
                }else{
                    window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
                }
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }
}
