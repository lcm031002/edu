/**
 * Created by Liyong.zhu on 2016/11/9.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_workflowProcessDefinitionController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        'erp_workflowDeployedProcessDefinitionService',
        'erp_workflowProcessInstanceService',
        erp_workflowProcessDefinitionController]);

function erp_workflowProcessDefinitionController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    erp_workflowDeployedProcessDefinitionService,
    erp_workflowProcessInstanceService) {
    $scope.processInfoList = [];
    $scope.isLoading = '';
    $scope.openPanel = '';
    $scope.selectedProcess = null;
    $scope.selectedProcess = null;

    $scope.toOpenPanel = function(process){
        $scope.selectedProcess = process;
        $scope.openPanel = 'showWorkflowPng';
    }

    $scope.toClosePanel = function(){
        $scope.openPanel = '';
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
        erp_workflowDeployedProcessDefinitionService.query({},function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.processInfoList = resp.data;
            }else{
                alert("流程信息获取不正确！");
            }
        })
    }

    queryWorkflowDefined();

    $scope.deleteDefinition = function(processInfo){
        $scope.openPanel = 'deleteProcessDefinition';
        var param = {};
        param.deploymentid = processInfo.deploymentid;
        erp_workflowDeployedProcessDefinitionService.delete(param,function(resp){
            $scope.openPanel = '';
            if(!resp.error){
                alert("删除成功！");
                queryWorkflowDefined();
            }else{
                alert(resp.message);
            }
        });
    }

    $scope.startProcess = function(processInfo){
        var param = {};
        param.id = processInfo.id;
        $scope.openPanel = 'startProcess';
        erp_workflowDeployedProcessDefinitionService.post(param,function(resp){
            $scope.openPanel = '';
            if(!resp.error){
                alert("流程启动成功！");
            }else{
                alert(resp.message);
            }
        })
    }

    $scope.deleteDefinitionForse = function(processInfo){
        $scope.openPanel = 'deleteProcessDefinition';
        var param = {};
        param.deploymentid = processInfo.deploymentid;
        param.deleteType = 'force';
        erp_workflowDeployedProcessDefinitionService.delete(param,function(resp){
            $scope.openPanel = '';
            if(!resp.error){
                alert("删除成功！");
                queryWorkflowDefined();
            }else{
                alert(resp.message);
            }
        })
    }

    /**
     * 查询工作流的流程实例
     * @param process
     */
    $scope.queryWorkflowProcessInstance = function(process){
        $scope.selectedProcess = process;
        queryWorkflowProcessInstanceDetail();
    }


    function queryWorkflowProcessInstanceDetail(){
        $scope.openPanel = 'queryProcessDefinitionDetail';
        var param = {};
        $scope.isQueryProcessDefinitionDetail = true;
        $scope.selectedProcess.instanceList = [];
        param.deploymentid = $scope.selectedProcess.deploymentid;
        erp_workflowProcessInstanceService.query(param,function(resp){
            $scope.isQueryProcessDefinitionDetail = false;
            if(!resp.error){
                $scope.selectedProcess.instanceList = resp.data;
            }
        })
    }






};