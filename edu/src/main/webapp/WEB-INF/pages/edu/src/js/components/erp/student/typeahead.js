(function() {
  'use strict';

  // Usage:
  // Output:
  //    - studentId[optional]   教师ID
  //    - onSelect[optional]   选中后回调事件
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcStudentTypeahead', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/student/typeahead.html',
      controllerAs: '$ctrl',
      transclude: true,
      bindings: {
        onChange: '@',
        studentId: '=?',
        studentName: '=?',
        onSelect: '&?'
      },
      controller: ['$scope', 'erp_studentsService',
        function ($scope, erp_studentsService) {
          var $ctrl = this;
          var _selected = null;
          $scope.student = null;
          if ($ctrl.studentId && $ctrl.studentName) {
            $scope.student = {
              id: $ctrl.studentId,
              fullName: $ctrl.studentName
            }
          }
          $scope.ngModelOptionsSelected = function(value) {
            if (arguments.length) {
              _selected = value;
            } else {
              return _selected;
            }
          };
          
          $scope.onTypeaheadSelect = function($item, $model, $label, $event) {
            $ctrl.studentId = $item.id
            setTimeout(() => {
              if (typeof $ctrl.onSelect == 'function') {
                $ctrl.onSelect({'$item': $item})
              }
            }, 0);
            return false
          }

          $scope.onClearTypeahead = function() {
            $ctrl.studentId = null;
            $ctrl.studentName = null;
            $scope.student = null;
            setTimeout(() => {
              if (typeof $ctrl.onSelect == 'function') {
                $ctrl.onSelect()
              }
            }, 0);
          }

          $scope.getStudent = function (value) {
            return erp_studentsService.query({
              pageSize: 30,
              currentPage: 1,
              need_contact: '1',
              searchInfo: value,
              searchType: 0
            }).$promise.then(function (resp) {
              _.forEach(resp.data, function (item) {
                item.fullName=item.student_name +
                  ' (' + (item.grade_name || '未知年级') + ') 【' +
                  (item.branch_name || '未知校区')  +'】'
              })
              return resp.data
            })
          }
          ////////////////

          $ctrl.$onInit = function() { };
          $ctrl.$onChanges = function(changesObj) { };
          $ctrl.$onDestroy = function() { };
        }
      ]
    });

})();