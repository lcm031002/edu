/**
 * Created by Liyong.zhu on 2016/11/9.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_workflowProcessTaskController', [
        '$rootScope',
        '$scope',
        '$uibMsgbox',
        '$cookieStore',
        '$log',
        '$resource',
        'erp_workflowProcessTaskService',
        'erp_workflowTaskExamineAndApproveService',
        'erp_workflowTaskOutcomesService',
        erp_workflowProcessTaskController]);

function erp_workflowProcessTaskController(
    $rootScope,
    $scope,
    $uibMsgbox,
    $cookieStore,
    $log,
    $resource,
    erp_workflowProcessTaskService,
    erp_workflowTaskExamineAndApproveService,
    erp_workflowTaskOutcomesService) {
    $scope.taskInfoList = [];
    $scope.isLoading = '';
    $scope.openPanel = '';
    $scope.selectedProcess = null;
    $scope.selectedProcess = null;
    $scope.queryTaskParam = {
        beginNo:0,
        endNo:19,
        totalCount:0,
        currentPage:1,
        rowNo:20,
        searcherName:null,
        remark:''
    };
    $scope.taskDetailInfo = {};

    $scope.toOpenPanel = function(process){
        $scope.selectedProcess = process;
        $scope.openPanel = 'showWorkflowPng';
    }

    $scope.toClosePanel = function(){
        $scope.openPanel = '';
    }

    $scope.refresh = function(){
        queryWorkflowTaskList();
    }

    $scope.selectPage = function(next){
        $scope.queryTaskParam.currentPage = next;
        $scope.queryTaskParam.endNo =  $scope.queryTaskParam.currentPage * $scope.queryTaskParam.rowNo-1;
        $scope.queryTaskParam.beginNo =  ($scope.queryTaskParam.currentPage-1) * $scope.queryTaskParam.rowNo;
        queryWorkflowTaskList();
    }

    /**
     * 查询流程任务列表
     */
    function queryWorkflowTaskList(){
        $scope.isLoading = 'isLoading';
        $scope.taskInfoList = [];
        var param = $scope.queryTaskParam;
        erp_workflowProcessTaskService.query(param,function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.taskInfoList = resp.data;
                $scope.queryTaskParam.totalCount = resp.totalCount;
                $scope.queryTaskParam.beginNo = resp.beginNo;
                $scope.queryTaskParam.endNo = resp.endNo;
                if($scope.queryTaskParam.totalCount<$scope.queryTaskParam.beginNo){
                    $scope.queryTaskParam.beginNo = $scope.queryTaskParam.totalCount;
                }
                if($scope.queryTaskParam.totalCount<$scope.queryTaskParam.endNo){
                    $scope.queryTaskParam.endNo = $scope.queryTaskParam.totalCount;
                }

                if( $scope.taskInfoList){
                    $.each( $scope.taskInfoList,function(i,model){
                        if(model.createTime){
                            var d = new Date();
                            d.setTime(model.createTime);
                            model.createTimeString = Format('yyyy-MM-dd hh:ss',d);
                        }
                        if(model.variables){
                            var paramPbj = {};
                            $.each(model.variables,function(j,v){
                                v.val_ = genStringValue(v.date_value_)
                                    +""+genStringValue(v.double_value_)
                                    +""+genStringValue(v.long_value_)
                                    +""+genStringValue(v.string_value_)
                                    +""+genStringValue(v.text_value_);
                                paramPbj[v.key_] =  v.val_;
                            });
                            model.paramPbj = paramPbj;
                        }
                    });
                }
            }else{
                alert(resp.message);
            }
        })
    }

    function genStringValue(v){
        return v?v+"":"";
    }

    queryWorkflowTaskList();

    $scope.queryWorkflowTaskList = function(){
        queryWorkflowTaskList();
    }
    $scope.selectedTask = null;
    $scope.detailPanel = function(taskInfo){
        $scope.selectedTask = taskInfo;
        $('#erpWorkflowProcessTaskPanel').modal('show');
        $scope.queryTaskParam.remark = '';
        var param = {};
        param.taskId = taskInfo.dbid_;
        $scope.taskDetailInfo = {};
        $scope.openPanel2 = 'detailPanel';
        erp_workflowTaskOutcomesService.query(param,function(resp){
            $scope.openPanel2 = '';
            if(!resp.error){
                $scope.taskDetailInfo = resp;
                if($scope.taskDetailInfo && $scope.taskDetailInfo.task &&  $scope.taskDetailInfo.task.createTime){
                    var dt = new Date();
                    dt.setTime($scope.taskDetailInfo.task.createTime);
                    $scope.taskDetailInfo.task.createTime = Format('yyyy-MM-dd hh:mm:ss',dt);
                }

                if($scope.taskDetailInfo && $scope.taskDetailInfo.historyTasks){
                    $.each( $scope.taskDetailInfo.historyTasks,function(i,historyTask){
                        if(historyTask.createTime){
                            var dt = new Date();
                            dt.setTime(historyTask.createTime);
                            historyTask.createTime = Format('yyyy-MM-dd hh:mm:ss',dt);
                        }
                    })
                }

            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }

    $scope.processTask = function(outcome){
        if(!$scope.selectedTask){
            $uibMsgbox.confirm("请选择要审批的任务！");
            $scope.openPanel = '';
            return;
        }
        var waitingModal =  $uibMsgbox.waiting("正在提交任务");
        var param = {};
        param.taskId = $scope.selectedTask.dbid_;
        param.remark = $scope.queryTaskParam.remark;
        param.outcome = outcome;
        erp_workflowTaskExamineAndApproveService.post(param,function(resp){
            waitingModal.close();
            $('#erpWorkflowProcessTaskPanel').modal('hide');
            $scope.openPanel = '';
            if(!resp.error){
                $uibMsgbox.success("审批成功！");
                queryWorkflowTaskList();
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }

};