(function () {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedProcess', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedProcess.html',
      controllerAs: '$ctrl',
      bindings: {
        apply: '<',
        optype: '<',
        onGoBack: '&'
      },
      controller: [
        '$scope',
        '$uibMsgbox',
        'erp_stuCourseSchedApplyYdyService',
        'erp_studentCourseSchedulingService',
        function (
          $scope,
          $uibMsgbox,
          erp_stuCourseSchedApplyYdyService,
          erp_studentCourseSchedulingService
        ) {
          var $ctrl = this;

          // 1对1排课申请单详情
          $scope.apply = {};
          $scope.moment = moment;
          // 已选择的学员报班单
          $scope.checkedOrders = [];
          $scope.checkApplyPlanItem = {};
          $scope.studentCounselors = {};
          $scope.scheduledList = []
          // 获取订单详情
          $scope.getApplyDetail = function (applyId) {
            erp_stuCourseSchedApplyYdyService.getDetail({
              id: applyId
            }, function (resp) {
              if (!resp.error) {
                if (!resp.data) {
                  return
                }
                $scope.apply = resp.data
              } else {
                $uibMsgbox.error(resp.message)
              }
            })
          }

          $scope.onSchedSuccess = function () {
            $scope.$broadcast('getOrderList');
            $scope.getSchedListByApplyId();
          }

          $scope.getSchedListByApplyId = function () {
            erp_studentCourseSchedulingService.get({
              apply_id: $scope.apply.id
            }).$promise.then(function (resp) {
              if (!resp.error) {
                $scope.scheduledList = resp.data;
              } else {
                $uibMsgbox.error(resp.message);
              }
            })
          }

          $scope.hanldeSchedPlanListClick = function (item) {
            $scope.checkApplyPlanItem = item
          }

          ////////////////

          $ctrl.$onInit = function () {
            $scope.$watch(function () { return $ctrl.apply }, function () {
              $scope.apply = $ctrl.apply;
              $scope.studentId = $ctrl.apply.studentId;
              if ($ctrl.apply.id) {
                $scope.getApplyDetail($ctrl.apply.id);
                $scope.getSchedListByApplyId()
              }
              $scope.studentCounselors = _.pick($scope.apply, [
                'counselor',
                'counselorId',
                'courseAdmin',
                'courseAdminId'
              ])
            })

            $scope.$watch(function () { return $ctrl.optype; }, function () {
              $scope.optype = $ctrl.optype;
            })
          };

          $ctrl.$onChanges = function (changesObj) { };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();