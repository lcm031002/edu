(function() {
  'use strict';

  // Usage:
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedResult', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedResult.html',
      controllerAs: '$ctrl',
      bindings: {
        data: '=',
      },
      controller: ['$scope',
        function ($scope) {
          var $ctrl = this;
          console.log($ctrl.data)
          ////////////////

          $ctrl.$onInit = function() { };
          $ctrl.$onChanges = function(changesObj) { };
          $ctrl.$onDestroy = function() { };
        }
      ]
    });

})();