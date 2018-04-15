(function() {
  'use strict';

  // Usage:
  //  teacherType: 
  //      1 咨询师
  //      2 学管师
  // Input:
  //    - teacherType[require] 教师类型
  // Output:
  //    - teacherId[optional]   教师ID
  //    - onSelect[optional]   选中后回调事件
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcTeacherTypeahead', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/teacher/typeahead.html',
      controllerAs: '$ctrl',
      transclude: true,
      bindings: {
        defaultName:'=?',
        teacherType: '<',
        teacherId: '=?',
        teacherName: '=?',
        employeeId: '=?',
        employeeName: '=?',
        encoding: '=?',
        onSelect: '&?'
      },
      controller: ['$scope', 'erp_employeeMgrService', 'erp_TeacherSearchService',
        function ($scope, erp_employeeMgrService, erp_TeacherSearchService) {
          var $ctrl = this;
          var _selected;
          
          $scope.teacher = null;
          
          $scope.teacherTypeNameMap = {
            1 : '咨询师',
            2 : '学管师',
            3: '教师',
            4: '员工'
          }
          
          $scope.teacherType = $ctrl.teacherType || 1

          $scope.onTypeaheadSelect = function($item, $model, $label, $event) {
            $ctrl.employeeId = $item.employee_id || $item.id
            $ctrl.teacherId = $item.id
            $ctrl.employeeName = $item.employee_name
            $ctrl.encoding = $item.encoding;
            if (typeof $ctrl.onSelect == 'function') {
              $ctrl.onSelect({'$item': $item})
            }
          }
          
          $scope.onClearTypeahead = function() {
            $ctrl.teacherId = null
            $ctrl.employeeId = null
            $ctrl.employeeName = null
            $ctrl.encoding = null
            $scope.teacher = null
          }

          $scope.ngModelOptionsSelected = function(value) {
            if (arguments.length) {
              _selected = value;
            } else {
              return _selected;
            }
          };

          // 查询咨询师或学管师
          function getCounselorOrManager (employee_name) {
            return erp_employeeMgrService.queryEmployeeForPage({
              currentPage: 1,
              pageSize: 30,
              counselor_type: $scope.teacherType,
              employee_name: employee_name
            }).$promise.then(function (resp) {
              if (!resp.error) {
                _.forEach(resp.data, function (item) {
                  item.fullName = item.employee_name
                })
                return resp.data
              }
              return []
            })
          }
          
          // 获取教师列表
          function getTeacher (searchString) {
            return erp_TeacherSearchService.query({
              currentPage: 1,
              pageSize: 30,
              search_info: searchString
            }).$promise.then(function(resp) {
              if (!resp.error) {
                _.forEach(resp.data, function (item) {
                  item.fullName = (item.teacher_name || '') +
                    '(' + (item.encoding || '暂无编码') + ')' +
                    '【' + (item.bu_name || item.city_name || '暂无地区/团队信息') + '】'
                })
                return resp.data
              } else {
                return []
              }
            })
          }

          $scope.getTeacher = function (searchString) {
            if ($scope.teacherType == 1 || $scope.teacherType == 2 || $scope.teacherType == 4) {
              return getCounselorOrManager(searchString)
            } else if ($scope.teacherType == 3) {
              return getTeacher(searchString)
            }
          }

          ////////////////
          $ctrl.$onInit = function() {
            if($ctrl.employeeName==null && $ctrl.teacherType == 2){
              $ctrl.employeeName=$ctrl.defaultName;
              getCounselorOrManager ($ctrl.employeeName)
            }

            $scope.$watch(function() {return $ctrl.teacherType}, function () {
              $scope.teacherType = $ctrl.teacherType
            })

            $scope.$watch(function() {
              return $ctrl.employeeName
            }, function () {
              if ($ctrl.employeeName || $ctrl.teacherName) {
                $scope.teacher = {
                  fullName: ($ctrl.employeeName || $ctrl.teacherName) +  ($ctrl.encoding ? ('(' + $ctrl.encoding + ')') : '')
                }
              }
            })
          };
          $ctrl.$onChanges = function(changesObj) {
          };
          $ctrl.$onDestroy = function() {
          };
        }
      ]
    });

})();