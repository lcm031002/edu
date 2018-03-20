(function() {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedByPeriod', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedByPeriod.html',
      controllerAs: '$ctrl',
      bindings: {
        schedConfig: '=',
      },
      controller: ['$scope',
        function ($scope) {
          var $ctrl = this;
          console.log('klcErpCourseStuSchedByPeriod');

          ////////////////

          $ctrl.$onInit = function() { };
          $ctrl.$onChanges = function(changesObj) { };
          $ctrl.$onDestroy = function() { };
        }
      ]
    });

})();