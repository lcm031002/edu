angular.module('ework-ui')
    .controller('erp_wfdComboInputModalController', [
        '$rootScope',
        '$scope',
        '$uibModalInstance',
        '$uibMsgbox',
        '$filter',
        'uibDateParser',
        'erp_gradeService',
        'erp_studentBuOrgsService',
        'PUBORGSelectedService',
        'data',
        erp_wfdComboInputModalController
    ]);

function erp_wfdComboInputModalController($rootScope,
                                          $scope,
                                          $uibModalInstance,
                                          $uibMsgbox,
                                          $filter,
                                          uibDateParser,
                                          erp_gradeService,
                                          erp_studentBuOrgsService,
                                          PUBORGSelectedService,
                                          data) {
    // 初始化信息
    $scope.modalData = angular.copy(data);

    $scope.handleModalCancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    $scope.handleModalConfirm = function () {
        if(!$scope.modalData.combo_name) {
            $uibMsgbox.error("套餐名称必填！");
            return;
        }
        if(!$scope.modalData.grade_id) {
            $uibMsgbox.error("年级必填！");
            return;
        }
        if(!$scope.modalData.combo_type || $scope.modalData.combo_type < 1) {
            $uibMsgbox.error("套餐类型必填！");
            return;
        }
        if(!$scope.modalData.branch_id) {
            $uibMsgbox.error("校区必填！");
            return;
        }
        if(!$scope.modalData.price) {
            $uibMsgbox.error("价格必填！");
            return;
        }
        if(!$scope.modalData.course_count) {
            $uibMsgbox.error("课次必填！");
            return;
        }
        if(!$scope.modalData.status) {
            $uibMsgbox.error("状态必填！");
            return;
        }

        angular.copy($scope.modalData, data);
        data.validateStatus = 2;
        data.errorMsgObj = {};
        data.errorList = [];
        $uibModalInstance.close();
    };

    // 查询年级
    $scope.queryGrade = function(){
        erp_gradeService.querySelectDatas({},function(resp){
            if(!resp.error){
                $scope.gradeList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 查询校区
    $scope.queryBuOrgs = function(){
        erp_studentBuOrgsService.query({},function(resp){
            if(!resp.error){
                $scope.branchList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    };

    $scope.init = function () {
        // 套餐类型
        $scope.comboTypeList = [{"id" : 1, "name" : "月卡"},{"id" : 2, "name" : "次卡"}];
        // 查询年级
        $scope.queryGrade();
        // 查询校区
        $scope.queryBuOrgs();
    };
    $scope.init();

}
