/**
 * Created by Liyong.zhu on 2016/11/9.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_workflowMgrmentController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        'erp_workflowMgrmentService',
        erp_workflowMgrmentController]);

function erp_workflowMgrmentController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    erp_workflowMgrmentService) {
    $scope.processInfoList = [];
    $scope.isLoading = '';
    $scope.openPanel = '';
    $scope.selectedProcess = null;

    $scope.toOpenPanel = function(process){
        $scope.selectedProcess = process;
        $scope.openPanel = 'showWorkflowPng';
    }

    $scope.toClosePanel = function(){
        $scope.openPanel = '';
        $scope.selectedProcess = null;
    }

    $scope.refresh = function(){
        queryWorkflowDefined();
    }
    /**
     * 查询流程定义
     */
    function queryWorkflowDefined(){
        $scope.isLoading = 'isLoading';
        $scope.processInfoList = [];
        erp_workflowMgrmentService.query({},function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.processInfoList = resp.fileList;
                $scope.path = resp.path;
            }else{
                alert("流程信息获取不正确！");
            }
        })
    }

    queryWorkflowDefined();

    /**
     * 发布流程
     * @param processInfo
     */
    $scope.deployProcess = function(processInfo){
        var param = {};
        param.processFile = processInfo.file;
        $scope.openPanel = 'deployProcess';
        erp_workflowMgrmentService.post(param,function(resp){
            $scope.openPanel = '';
            if(!resp.error){
                alert("发布成功！");
                queryWorkflowDefined();
            }else{
                alert(resp.message);
            }
        })
    }

    /**
     * 更新流程
     * @param processInfo
     */
    $scope.updateProcess = function(processInfo){
        var param = {};
        param.processDeployId = processInfo.deployId;
        param.processZip = processInfo.file;
        $scope.openPanel = 'updateProcess';
        erp_workflowMgrmentService.update(param,function(resp){
            $scope.openPanel = '';
            if(!resp.error){
                alert("更新成功！");
                queryWorkflowDefined();
            }else{
                alert(resp.message);
            }
        })
    }


};