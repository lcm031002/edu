/**
 * Created by Liyong.zhu on 2017/1/12.
 */
/**
 *
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_workflowTaskTodoController', [
        '$rootScope',
        '$scope',
        '$uibMsgbox',
        '$cookieStore',
        '$log',
        'erp_studentBuOrgsService',
        'erp_workflowTaskTodoService',
        'erp_workflowTaskService',
        'erp_workflowTaskExamineAndApproveService',
        'edu_EmployeeService',
        'erp_workflowTaskTodoChangeService',
        erp_workflowTaskTodoController]);

function erp_workflowTaskTodoController(
    $rootScope,
    $scope,
    $uibMsgbox,
    $cookieStore,
    $log,
    erp_studentBuOrgsService,
    erp_workflowTaskTodoService,
    erp_workflowTaskService,
    erp_workflowTaskExamineAndApproveService,
    edu_EmployeeService,
    erp_workflowTaskTodoChangeService
    ) {
    $scope.page = {
        currentPage:1,
        pageSize:5,
        totalCount:0
    };
    $scope.isLoading = '';
    $scope.app_info = '';
    $scope.isDetailLoading='';
    $scope.showDetail = false;
    $scope.queryModuleList = [
        {
            "name":"全部",
            "value":"-1"
        },{
            "name":"erp",
            "value":"erp"
        },{
            "name":"hrm",
            "value":"hrm"
        }
    ];
    $scope.selectedModule = $scope.queryModuleList[0];
    $scope.queryWorkFlowStateList = [
        {
            "name":"全部",
            "value":"-1"
        },{
            "name":"申请已提交",
            "value":"申请已提交"
        },{
            "name":"待审核",
            "value":"待审核"
        },{
            "name":"已通过",
            "value":"已通过"
        },{
            "name":"审核通过，订单生效",
            "value":"审核通过，订单生效"
        },{
            "name":"审核通过，订单作废处理",
            "value":"审核通过，订单作废处理"
        },{
            "name":"审核通过，退费单生效",
            "value":"审核通过，退费单生效"
        },{
            "name":"订单审批不通过",
            "value":"订单审批不通过"
        },{
            "name":"审批不通过，退费单作废",
            "value":"审批不通过，退费单作废"
        }
    ];
    $scope.selectedWorkFlowState = $scope.queryWorkFlowStateList[2];
    $scope.todoTaskPage = {
        pageSize:5
    };
    $scope.isLoading='';
    function queryTodoTaskList(){
        var param = {};
        $scope.isLoading='isLoading';
        param.pageSize = $scope.todoTaskPage.pageSize;
        param.queryString = $scope.app_info;
        param.branchId = $scope.selectedBranch;
        param.startDate = $("#cdt_start_date").val();
        param.endDate = $("#cdt_end_date").val();
        erp_workflowTaskTodoService.query(param,function(resp){
            $scope.isLoading='';
            if(!resp.error){
                $scope.todoTaskPage = resp.data;
                if( $scope.todoTaskPage&& $scope.todoTaskPage.resultList){
                    $.each($scope.todoTaskPage.resultList,function(i,row){
                        if(row.createTime){
                           var date =  new Date();
                            date.setTime(row.createTime);
                            row.createTime = Format('yyyy/MM/dd hh:mm',date);
                        }
                    })
                }
            }
        });
    }

    queryTodoTaskList();
    queryBuOrgs();

    $scope.pageQuery =function(currentPage){
        $scope.todoTaskPage.currentPage = currentPage;
        if($scope.todoTaskPage.currentPage<1){
            $scope.todoTaskPage.currentPage = 1;
        }
        if($scope.todoTaskPage.currentPage>$scope.todoTaskPage.totalPage){
            $scope.todoTaskPage.currentPage=$scope.todoTaskPage.totalPage;
        }
        queryTodoTaskList();
    };

    $scope.queryMore = function(){
        $scope.todoTaskPage.pageSize = $scope.todoTaskPage.pageSize + 5;
        $scope.pageQuery(1);
    };

    $scope.queryTask = function(){
        $scope.todoTaskPage.resultList = [];
        $scope.todoTaskPage.pageSize = 5;
        queryTodoTaskList();
    }

    $scope.showDetailInfo = function(row){
        if(row.showDetail ){
            row.showDetail = false;
            return true;
        }
        row.showDetail = true;
        $scope.selectedRow = row;
        row.isDetailLoading='isDetailLoading';
        var param = {};
        $scope.selectedRow.task = undefined;
        param.taskId = row.id;
        erp_workflowTaskService.query(param,function(resp){
            row.isDetailLoading=undefined;
            if(!resp.error){
                var taskDetailInfo = resp;
                if(taskDetailInfo && taskDetailInfo.task &&  taskDetailInfo.task.createTime){
                    var dt = new Date();
                    dt.setTime(taskDetailInfo.task.createTime);
                    taskDetailInfo.task.createTime = Format('yyyy-MM-dd hh:mm:ss',dt);
                }
                if(taskDetailInfo && taskDetailInfo.task && taskDetailInfo.task.extData && taskDetailInfo.task.extData.businessDetailInfo){
                    taskDetailInfo.task.extData.businessDetailInfo = taskDetailInfo.task.extData.businessDetailInfo.split("$$$$").join("");
                }

                if(taskDetailInfo && taskDetailInfo.historyTasks){
                    $.each( taskDetailInfo.historyTasks,function(i,historyTask){
                        if(historyTask.createTime){
                            var dt = new Date();
                            dt.setTime(historyTask.createTime);
                            historyTask.createTime = Format('yyyy-MM-dd hh:mm:ss',dt);
                        }
                    });
                }

                $scope.selectedRow.task = taskDetailInfo;
                if($scope.selectedRow.task&&$scope.selectedRow.task.task&&$scope.selectedRow.task.task.extData){
                    $scope.selectedRow.extData = $scope.selectedRow.task.task.extData;
                }

            }else{
                $uibMsgbox.error(resp.message);
            }
        });
        queryTaskTodoChange(row);
    };
    $scope.closeDetailInfo = function(row){
        row.showDetail = false;
    };


    $scope.submitTask = function(task,outcome){
        if (outcome.indexOf("不通过") > 0) {
            $uibMsgbox.confirm('确认驳回该审批?',function(res){
                if(res == 'yes'){
                    $scope.approveTask(task,outcome)
                }
            });
        }else{
            $scope.approveTask(task,outcome)
        }
        
    }

    $scope.approveTask = function(task,outcome){
        var param = {};
        param.taskId = task.id;
        param.remark = task.submitRemark;
        param.outcome = outcome;
        $scope.isSubmitTask = 'isSubmitTask';
        erp_workflowTaskExamineAndApproveService.post(param,function(resp){
            $scope.isSubmitTask = '';
            if(!resp.error){
                $uibMsgbox.success("审批已处理！");
                $scope.queryTask();
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }
    $scope.employeeList = [];
    $scope.toEntrust = function(todoTask){
        todoTask.entrust = true;
        $scope.queryEmployeeList(todoTask);
    }

    $scope.queryEmployeeList = function(todoTask){
        todoTask.entrustIsLoadingDetail = 'entrustIsLoadingDetail';
        var param = {};
        $scope.employeeList = [];
        param.searchName = todoTask.searchName;
        edu_EmployeeService.query(param,function(resp){
            todoTask.entrustIsLoadingDetail='';
            if(!resp.error){
                $scope.employeeList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }



    $scope.returnDetail = function(todoTask){
        todoTask.entrust = undefined;
    }

    $scope.checkEmployee =function(employee){
        if(employee.checked){
            employee.checked = false;
        }else{
            employee.checked = true;
        }
    }
    $scope.changeTask = function(todoTask){
        if(!$rootScope.curAccount){
            $uibMsgbox.error("服务端异常，未查询到当前登录用户,提交失败！");
            return ;
        }
        var param = {};
        param.beginDate = $("#cdt_start_date_0201").val();
        param.endDate = $("#cdt_end_date_0201").val();
        param.consigneeTaskId = todoTask.id+"";
        param.consignorRole = $rootScope.curAccount.id+"";
        param.employees = [];
        param.employeesName = [];
        $.each($scope.employeeList,function(i,empl){
            if(empl.checked){
                param.employees.push(empl.id);
                param.employeesName.push(empl.employeeName);
            }
        });
        if(param.employees.length==0){
            $uibMsgbox.confirm("请选择委托人！");
            return;
        }

        if(!param.beginDate){
            $uibMsgbox.confirm("请设置委托开始时间！");
            return;
        }
        if(!param.endDate){
            $uibMsgbox.confirm("请设置委托结束时间！");
            return;
        }

        todoTask.entrustIsLoadingDetail = 'entrustIsPosting';
        erp_workflowTaskTodoChangeService.post(param,function(response){
            todoTask.entrustIsLoadingDetail = '';
            if(!response.error){
                $uibMsgbox.success("委托成功！");
            }else{
                $uibMsgbox.error(response.message);
            }
        });
    };


    function queryTaskTodoChange(todoTask){
        var param = {};
        param.consignorRole = $rootScope.curAccount.id+"";
        param.consigneeTaskId = todoTask.id+"";
        erp_workflowTaskTodoChangeService.query(param,function(resp){
            if(!resp.error){
                todoTask.historyChangeTaskList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }

    /**
     * 查询校区
     */
    function queryBuOrgs() {
        erp_studentBuOrgsService.query({}, function (resp) {
            if (!resp.error) {
                var data = resp.data;
                if (data && data.length) {
                    $scope.branchList = resp.data;
                    //将全部option添加到branchList
                    $scope.branchList.unshift(
                        {
                            id : -1,
                            org_name:"-- 全部 --"
                        }
                    );
                }
            }
        })
    }
};