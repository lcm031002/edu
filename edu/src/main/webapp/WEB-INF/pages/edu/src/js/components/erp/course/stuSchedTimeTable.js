(function() {
  'use strict';

  // Usage: 学生档期表格
  // <sched-time-table time-list="stuSchedTimeListStr" read-only="true">
  // 数据格式
  /* [{startTime: '08:00', endTime: '10:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '10:15', endTime: '12:15', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '14:00', endTime: '16:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '16:15', endTime: '18:15', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
    {startTime: '19:00', endTime: '21:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']}]
  */
  // 
  // Creates:baiqb@klxuexi.org
  // 说明：组件均以Klc开头（klxuexi）
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedTimeTable', {
      templateUrl:'templates/components/erp/course/stuSchedTimeTable.html',
      controllerAs: '$ctrl',
      bindings: {
        timeList: '=',
        readOnly: '<?',
        onUpdate: '&?'
      },
      controller: ['$scope',
        function (
          $scope
        ) {
          var $ctrl = this;
          $scope.readOnly = !!$ctrl.readOnly;
          $scope.stuSchedTimeList = [
            {startTime: '08:00', endTime: '10:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
            {startTime: '10:15', endTime: '12:15', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
            {startTime: '14:00', endTime: '16:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
            {startTime: '16:15', endTime: '18:15', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']},
            {startTime: '19:00', endTime: '21:00', idles: ['Y', 'Y', 'N', 'N', 'N', 'N', 'N']}
          ]
          $scope.reverseIdle = function(time, idx) {
            if ($scope.readOnly) {
              return
            }
            time.idles[idx] = time.idles[idx] == 'Y' ? 'N':'Y'
            if(_.isFunction($ctrl.onUpdate)) {
              $ctrl.onUpdate({
                schedule: JSON.stringify($scope.stuSchedTimeList)
              })
            }
          }
          $scope.setAllTimesIdle = function(index) {
            if ($scope.readOnly) {
              return
            }
            var allIdle = 'N'
            _.forEach($scope.stuSchedTimeList, function (item) {
              if (item.idles[index] == 'N') {
                allIdle = 'Y'
              }
            })
            _.forEach($scope.stuSchedTimeList, function (item) {
              item.idles[index] = allIdle
            })
            if(_.isFunction($ctrl.onUpdate)) {
              $ctrl.onUpdate({
                schedule: JSON.stringify($scope.stuSchedTimeList)
              })
            }
          }
          ////////////////
      
          $ctrl.$onInit = function() { 
            $scope.$watch(function(){return $ctrl.timeList}, function () {
              if ($ctrl.timeList) {
                $scope.stuSchedTimeList = JSON.parse($ctrl.timeList)
              }
            })
            if (typeof $ctrl.onUpdate == 'function') {
              $ctrl.onUpdate({
                schedule: JSON.stringify($scope.stuSchedTimeList)
              })
            }
          };
          $ctrl.$onChanges = function(changesObj) {
          };
          $ctrl.$onDestroy = function() { };
        }
      ],
    });
})();