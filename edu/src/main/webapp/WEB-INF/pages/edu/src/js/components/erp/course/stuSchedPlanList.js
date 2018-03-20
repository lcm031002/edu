(function () {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedPlanList', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedPlanList.html',
      controllerAs: '$ctrl',
      bindings: {
        applyId: '=',
        showOperatorBtn: '=?',
        onItemClick: '&?'
      },
      controller: ['$scope', '$uibMsgbox', 'erp_stuCourseSchedApplyYdyService',
        function ($scope, $uibMsgbox, erp_stuCourseSchedApplyYdyService) {
          var $ctrl = this;
          $scope.applyPlanList = [];



          // 获取初步排课意向表
          $scope.getApplyPlanList = function (applyId) {
            applyId = applyId || $scope.applyId;
            return erp_stuCourseSchedApplyYdyService.getApplyPlanList({
              applyId: applyId
            }).$promise.then(function (resp) {
              if (!resp.error) {
                $scope.applyPlanList = resp.data
              } else {
                $uibMsgbox.error(resp.message)
              }
            }, function (resp) { })
          }


          $scope.onItemClick = function (item) {
            _.forEach($scope.applyPlanList, function (apply) {
              apply.checked = apply.id === item.id
            })

            if (typeof $ctrl.onItemClick === 'function') {
              $ctrl.onItemClick({
                item: item
              })
            }
          }
          ////////////////

          $ctrl.$onInit = function () {
            $scope.$watch(function () { return $ctrl.applyId }, function () {
              if ($ctrl.applyId) {
                $scope.getApplyPlanList($ctrl.applyId);
              }
            })
          };
          $ctrl.$onChanges = function (changesObj) { };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();