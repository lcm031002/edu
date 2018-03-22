/**
 * Created by Liyong.zhu on 2016/6/5.
 */
angular.module('ework-ui')
    .controller('AuditLogCtrl', [
        '$scope',
        '$log',
        '$state',
        'AuditLogService',
        AuditLogCtrl]);


function AuditLogCtrl(
    $scope,
    $log,
    $state,
    AuditLogService
    ){

    $scope.auditLogList = [];
    $scope.downUpState = true;

    $scope.downUp = function(){
        if($scope.downUpState){
            $scope.downUpState = false;
        }else{
            $scope.downUpState = true;
        }
    }

    $scope.queryLog = function(){
        queryAuditLog();
    }

    function queryAuditLog(){
        $log.log("begin to query logs.pageSize is "+ $scope.pageSize+",currentPage is "
            + $scope.currentPage+",totalPage is "+$scope.totalPage+",beginDate is "
            + $("#beginDate").val()+",endDate is "+ $("#endDate").val()+",search_info is " + $scope.search_info);
        var param = {};
        $scope.auditLogList = [];
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;
        $scope.isLoading = 'loading...';
        AuditLogService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.auditLogList = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                if(resp.data&&resp.data.length){
                    $log.log("found data length is "+resp.data.length);
                }else{
                    $log.log("request return ,but data length is 0");
                }
            }
            $scope.isLoading = '';
        })
    }
    queryAuditLog();


    /**
     * 获取数组
     * @param n
     * @returns {Array}
     */
    $scope.getNumber = function(n){
        var list = new Array();
        for (var index = 0;index<n;index++){
            list.push(index+1);
        }
        return list;
    }
}