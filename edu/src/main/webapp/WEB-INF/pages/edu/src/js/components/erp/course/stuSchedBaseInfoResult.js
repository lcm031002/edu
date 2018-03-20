(function () {
  'use strict';

  // Usage:师生匹配结果
  // 
  // Creates:yans@histudy.com
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedBaseInfoResult', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedBaseInfoResult.html',
      controllerAs: '$ctrl',
      bindings: {
        apply: '=',
        readOnly: '<?'
      },
      controller: [
      '$scope', 
      '$uibModal', 
      '$uibMsgbox', 
      '$state',
      '$stateParams',
      'erp_studentsService',
      'erp_stuCourseSchedApplyYdyService',
      function ($scope,
        $uibModal,
        $uibMsgbox, 
        $state,
        $stateParams,
        erp_studentsService, 
        erp_stuCourseSchedApplyYdyService) {
        var $ctrl = this;
        $scope.apply = $ctrl.apply;

        $scope.SchedCancle = function(schedPlanItem){
          return $uibModal.open({
            templateUrl: 'schedResultDialog.html',
            resolve: {
              schedPlanItem: function () {
                return schedPlanItem
              }
            },
            controller: [
              '$scope',
              'erp_stuCourseSchedApplyYdyService',
              'schedPlanItem',
              function (
                $scope,
                erp_stuCourseSchedApplyYdyService,
                schedPlanItem
              ) {
                $scope.schedPlan = {
                  id: schedPlanItem.id,
                  applyId : schedPlanItem.applyId,
                  status: 4,
                  remark:null
                }

                $scope.onOk = function () {
                  if (!$scope.schedPlan.remark) {
                    return $uibMsgbox.warn('请填写备注！')
                  }
                  var waitingModal = $uibMsgbox.waiting('保存中，请稍候...')
                 
                  erp_stuCourseSchedApplyYdyService.putApplyPlan($scope.schedPlan, function (resp) {
                    waitingModal.close()
                    if (!resp.error) {
                      schedPlanItem.status = 4;
                      schedPlanItem.statusName = '已取消';
                      schedPlanItem.remark = $scope.schedPlan.remark;
                      $uibMsgbox.success('操作成功！', function() {
                        $scope.$close();
                      });
                    } else {
                      $uibMsgbox.error(resp.message)
                    }
                  })
                  
                }
              }
            ]
          })
        }

          $ctrl.$onInit = function () {
            $scope.$watch(function() {return $ctrl.apply}, function () {
              $scope.apply = $ctrl.apply;
            })
          };
          $ctrl.$onChanges = function (changesObj) {
          };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();