/**
 * Created by Liyong.zhu on 2016/11/25.
 */
/**
 * Created by Liyong.zhu on 2016/10/27.
 */

"use strict";
angular
    .module('ework-ui')
    .controller('erp_todoTaskListController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        'erp_workflowUserTaskService',
        erp_todoTaskListController]);

function erp_todoTaskListController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    erp_workflowUserTaskService) {
    $scope.todoTaskList = {};
    $scope.today = Format('yyyy-MM-dd',new Date());

    function queryTodoTaskList(){
        var param = {};
        erp_workflowUserTaskService.query(param,function(resp){
            if(!resp.error){
                $scope.todoTaskList = resp.data;
                if($scope.todoTaskList.resultList){
                    $.each($scope.todoTaskList.resultList,function(i,model){
                        if(model.createTime){
                            var dt = new Date();
                            dt.setTime(model.createTime);
                            model.createTimeStr = Format('yyyy-MM-dd hh:mm:ss',dt);
                            model.createDate = Format('yyyy-MM-dd',dt);
                        }

                        if(model.extData&&model.extData.businessDetailInfo){
                            model.extData.businessDetails = model.extData.businessDetailInfo.split("$$$$");
                        }
                    });
                }
            }
        })
    }

    queryTodoTaskList();
}