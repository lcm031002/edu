angular.module('ework-ui').controller('report_alStudentStatusController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'report_alStudentStatusReportService',
    report_alStudentStatusController
]);

function report_alStudentStatusController($rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    report_alStudentStatusReportService) {

    $scope.searchParam = {
        p_bu_id: '',
        p_year_month:  moment().startOf('month').add('-1').format('YYYY-MM'),
        p_branch_id: ''
    }; 
   

    $scope.beforeQuery = function() {
        $scope.dataList = undefined;
        if (!$scope.searchParam.p_bu_id || $scope.searchParam.p_bu_id == -1) {
            $uibMsgbox.confirm("请选择团队");
            return false;
        }
        $scope.searchParam.p_year_month = $('#p_year_month').val();
        if (!$scope.searchParam.p_year_month) {
            $uibMsgbox.confirm("请选择月份");
            return false;
        }
        if ($scope.searchParam.p_year_month >  moment().startOf('month').add('-1').format('YYYY-MM')) {
            $uibMsgbox.confirm("所选月份应小于当前月份");
            return false;
        }
        return true;
    };

    $scope.queryReport = function () {
        if ($scope.beforeQuery() == false) {
            return;
        }
        $scope.loadStatues = true;
        report_alStudentStatusReportService.query($scope.searchParam, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.outputReport = function () {
        if ($scope.beforeQuery() == false) {
            return;
        }
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        report_alStudentStatusReportService.outputExcel($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                window.location.href = '../report/common/downloadTempFile?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    $scope.alSub = true; //默认显示总表
    $scope.detailsList = []; 
    $scope.openDetails = function (details,status) {
        if(details.branchName == '合计'){
            return false;
        }
        $scope.alSub = false;  //显示子表
        var studentStatus = [
            '',
            '预报名',
            '重复',
            '在读',
            '沉睡',
            '有来未缴',
            '搬家',
            '新生',
            '流失']
        $scope.sutdentStatus = studentStatus[status]
        var params = {
            p_bu_id : $scope.searchParam.p_bu_id,
            p_branch_id : details.branchId,
            p_year_month : details.changeTime,
            p_student_status : status
        }
        $scope.loadStatues = true;
        report_alStudentStatusReportService.queryDetails(params,function(resp){
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.detailsList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.showBack = function(){
        $scope.alSub = true;
    }
}