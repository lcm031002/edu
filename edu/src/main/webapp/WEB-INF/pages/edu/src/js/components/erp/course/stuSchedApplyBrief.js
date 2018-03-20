(function() {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedApplyBrief', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedApplyBrief.html',
      controllerAs: '$ctrl',
      bindings: {
        apply: '=',
      },
      controller: ['$scope',
        function ($scope) {
          var $ctrl = this;


          ////////////////

          $ctrl.$onInit = function() { };
          $ctrl.$onChanges = function(changesObj) { };
          $ctrl.$onDestroy = function() { };
        }
      ]
    });

})();