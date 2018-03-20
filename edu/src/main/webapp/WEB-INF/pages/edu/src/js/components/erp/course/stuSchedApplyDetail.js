(function() {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedApplyDetail', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedApplyDetail.html',
      controllerAs: '$ctrl',
      bindings: {
        apply: '=',
        type: '=',        
      },
      controller: ['$scope',
        function ($scope) {
          var $ctrl = this;
          $scope.match=false;
          if ($ctrl.type == 1) {
            $scope.match = true;
          }


          ////////////////

          $ctrl.$onInit = function() { };
          $ctrl.$onChanges = function(changesObj) { };
          $ctrl.$onDestroy = function() { };
        }
      ]
    });

})();