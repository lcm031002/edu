/**
 * Created by Liyong.zhu on 2016/11/9.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_workflowProcessNodeController', [
        '$rootScope',
        '$scope',
        '$uibMsgbox',
        '$cookieStore',
        '$log',
        '$resource',
        'erp_workflowDeployedProcessDefinitionService',
        'erp_workflowProcessNodeRoleService',
        'erp_workflowProcessNodeRoleMappingService',
        erp_workflowProcessNodeController]);

function erp_workflowProcessNodeController(
    $rootScope,
    $scope,
    $uibMsgbox,
    $cookieStore,
    $log,
    $resource,
    erp_workflowDeployedProcessDefinitionService,
    erp_workflowProcessNodeRoleService,
    erp_workflowProcessNodeRoleMappingService) {
    $scope.processInfoList = [];
    $scope.isLoading = '';
    $scope.selectedProcess = null;
    $scope.selectedProcess = null;
    $scope.ext_query_business_role_url = null;


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
                $uibMsgbox.error(resp.message);
            }
        })
    }

    queryWorkflowDefined();


    /**
     * 查询工作流的流程实例
     * @param process
     */
    $scope.queryProcessNodeRight = function(process){
        $scope.selectedProcess = process;
        queryProcessNodeRightDetail();
    }


    function queryProcessNodeRightDetail(){
        $('#erpWorkflowProcessNodePanel').modal('show');
        var param = {};
        $scope.isQueryProcessDefinitionDetail = true;
        $scope.selectedProcess.roleList = [];
        $scope.selectedRole = null;
        param.processKey = $scope.selectedProcess.key;
        erp_workflowProcessNodeRoleService.query(param,function(resp){
            $scope.isQueryProcessDefinitionDetail = false;
            if(!resp.error){
                $scope.selectedProcess.roleList = resp.data;
                $scope.ext_query_business_role_url = resp.ext_query_business_role_url;
                if($scope.selectedProcess.roleList && $scope.selectedProcess.roleList.length ){
                    if($scope.selectedRole){
                        $scope.selectProcessNode($scope.selectedRole);
                    }else{
                        $scope.selectProcessNode($scope.selectedProcess.roleList[0]);
                    }
                }

            }
        });
    }

    $scope.selectProcessNode = function(processRole){
        $scope.selectedRole = processRole;
    }

    $scope.removeRoleMapping = function(mapping){
        var waitingModal =  $uibMsgbox.waiting("正在删除任务");
        var param = {};
        param.id = mapping.id;
        erp_workflowProcessNodeRoleMappingService.delete(param,function(resp){
            waitingModal.close();
            if(!resp.error){
                $uibMsgbox.success("删除成功！");
                queryProcessNodeRightDetail();
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.queryBusinessRoleParam = {
        searchName:'',
        isSearch:false
    };
    $scope.searchRoleList = [];
    /**
     * 查询业务角色，ext_query_business_role_url为员工查询服务扩展点。返回值为员工列表，参数为queryString
     * @returns {boolean}
     */
    $scope.queryBusinessRole = function(){
        if($scope.openSearchRoleList){
            $scope.openSearchRoleList = false;
            return false;
        }
        if(!$scope.ext_query_business_role_url){
            $uibMsgbox.confirm("没有角色查询服务！ext_query_business_role_url is null.");
            return false;
        }

        var queryService = $resource($scope.ext_query_business_role_url,{},{
            query: {method:'GET', params:{}, isArray:false}
        });

        $scope.queryBusinessRoleParam.isSearch = true;
        $scope.searchRoleList = [];
        queryService.query({
            searchName:$scope.queryBusinessRoleParam.searchName
        },function(resp){
            $scope.queryBusinessRoleParam.isSearch = false;
            if(!resp.error){
                $scope.searchRoleList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
        $scope.openSearchRoleList = true;

        return true;
    }



    $scope.selectSearchRole = function(employee){
        $scope.queryBusinessRoleParam.searchName = employee.employeeName;
        $scope.queryBusinessRoleParam.employee = employee;
        if($scope.openSearchRoleList){
            $scope.openSearchRoleList = false;
            $scope.searchRoleList = [];
        }
    }

    $scope.addRoleMapping = function(){
        if(!$scope.queryBusinessRoleParam.employee){
            $uibMsgbox.confirm("请搜索并选择员工！");
            return;
        }

        if(!$scope.selectedRole){
            $uibMsgbox.confirm("请在左侧选择流程审批节点！");
            return;
        }

        var roleDef = {};
        roleDef.id = $scope.selectedRole.id;
        roleDef.processKey = $scope.selectedRole.processKey;
        roleDef.processTask = $scope.selectedRole.processTask;
        roleDef.remark = $scope.selectedRole.remark;
        var mapping = {};
        mapping.processRoleDefId = $scope.selectedRole.id;
        mapping.businessRole = $scope.queryBusinessRoleParam.employee.employeeName+"-"+$scope.queryBusinessRoleParam.employee.encoding+","+$scope.queryBusinessRoleParam.employee.orgName;
        mapping.businessRoleId = $scope.queryBusinessRoleParam.employee.id;
        mapping.processRoleDef = roleDef;
        var waitingModal =  $uibMsgbox.waiting("正在添加角色...");
        erp_workflowProcessNodeRoleMappingService.post(mapping,function(resp){
            waitingModal.close();
            if(!resp.error){
                $uibMsgbox.success("添加成功！");
                queryProcessNodeRightDetail();
            }else{
                $uibMsgbox.error(resp.message);
            }
        });

    }






};