"use strict";
angular
  .module('ework-ui')
  .controller('edu_taskController', [
    '$rootScope',
    '$scope',
    '$cookieStore',
    '$log',
    '$uibModal',
    edu_taskController
  ]);

function edu_taskController(
  $rootScope,
  $scope,
  $cookieStore,
  $log,
  $uibModal) {
  $scope.optype = 'view';

  $scope.$watch('$parent.curSystem', function(newValue, oldValue) {
    if (newValue && newValue.menus) {
      $scope.menus = newValue.menus;
      if ($scope.menus) {
        $.each($scope.menus, function(i, menu) {
          menu.href = menu.href + "?_=" + (new Date()).getTime();
        });
      }
    }
  })

};
