(function () {
  'use strict';

  // Usage: 获取学员的订单
  // input:
  //    studentId: Number               #学员id
  // events: 
  //    使用方式：父组件调用 $scope.$broadcast('getOrderList')方法
  //    getOrderList:                   # 更新学员报班单信息
  //--TODO---
  //    [multi-select]: Boolean         #是否可多选
  //    [branch-id]: Number             #有传值时，只能选择指定校区，没有值时可以选择所有校区
  //    [select-in-order]: Boolean      #是否必须按顺序选择，默认为否
  //    [select-first-default]: Boolean #默认选择第一条可选的记录
  //--End TODO---
  // output:
  //    onChecked: Array  #
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuOrderList', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuOrderList.html',
      controllerAs: '$ctrl',
      bindings: {
        studentId: '=',
        isAllBranch: '=?',
        onChecked: '&?'
      },
      controller: ['$scope', '$rootScope', '$uibMsgbox', 'erp_studentOrderCourseService',
        function ($scope, $rootScope, $uibMsgbox, erp_studentOrderCourseService) {
          var $ctrl = this;
          $scope.orderCourseList = [];
          $scope.curBranchCourseList = [];
          $scope.orderCourseListCheckedAllFlag = false;
          $scope.$on('getOrderList', function () {
            $scope.getOrderCourseList();
          })
          $scope.getOrderCourseList = function () {
            var waitingModal = $uibMsgbox.waiting('加载学员报班单中，请稍候...')
            erp_studentOrderCourseService.queryOrderCourse({
              pageSize: 999, // 每页显示条数
              currentPage: 1, // 要获取的第几页的数据
              studentId: $scope.studentId,
              isAllBranch: $ctrl.isAllBranch || null,
              businessType: 2
            }).$promise.then(function (resp) {
              waitingModal.close()
              if (!resp.error) {
                var arry = _.filter(_.sortBy(resp.data, 'create_time'), function (o) {
                  return o.course_schedule_count > 0;
                })
                _.forEach(arry, function (item) {
                  item.available = item.branch_id == $rootScope.selectedOrg.id
                  // item.disabled = true
                  item.checked = false
                })
                $scope.orderCourseList = arry;
                $scope.curBranchCourseList = _.filter(arry, { branch_id: $rootScope.selectedOrg.id })
                if (_.isArray($scope.curBranchCourseList) && $scope.curBranchCourseList.length > 0) {
                  $scope.curBranchCourseList[0].checked = true;
                  $scope.onCourseChecked($scope.curBranchCourseList[0])
                }
              } else {
                $uibMsgbox.error(resp.message);
              }
            }, function (resp) {
              waitingModal.close()
            });
          }

          // 学员报班单勾选/取消事件处理
          $scope.onCourseChecked = function (course) {
            if (!_.isArray($scope.curBranchCourseList)) {
              return false
            }
            // for(var i = 0; i < $scope.curBranchCourseList.length; i ++ ) {
            //     var preCourse = $scope.curBranchCourseList[i-1]
            //     var curCourse = $scope.curBranchCourseList[i]
            //     var nextCourse = $scope.curBranchCourseList[i + 1]
            //     if (curCourse.checked) {
            //         curCourse.disabled = !!(nextCourse && nextCourse.checked)
            //     } else {
            //         curCourse.disabled = !!(preCourse && !preCourse.checked)
            //     }
            // }
            $scope.orderCourseListCheckedAllFlag = _.every($scope.curBranchCourseList, { checked: true })
            if (_.isFunction($ctrl.onChecked)) {
              var checkedOrders = _.filter($scope.curBranchCourseList, {
                'checked': true
              });
              $ctrl.onChecked({
                orders: checkedOrders
              })
            }
          }

          // 全选/反选课程
          $scope.onCheckAllOrderCourse = function () {
            _.forEach($scope.curBranchCourseList, function (item) {
              item.checked = $scope.orderCourseListCheckedAllFlag
            })
            $scope.onCourseChecked()
          }

          ////////////////
          $ctrl.$onInit = function () {
            $scope.$watch(function () { return $ctrl.studentId }, function () {
              if ($ctrl.studentId) {
                $scope.studentId = $ctrl.studentId;
                $scope.getOrderCourseList();
              }
            })
          };
          
          $ctrl.$onChanges = function (changesObj) { };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();