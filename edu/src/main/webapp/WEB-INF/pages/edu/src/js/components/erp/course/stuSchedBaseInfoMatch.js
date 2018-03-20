(function () {
  'use strict';

  // Usage:师生匹配结果
  // 
  // Creates:yans@histudy.com
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedBaseInfoMatch', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedBaseInfoMatch.html',
      controllerAs: '$ctrl',
      bindings: {
        apply: '=',
        readOnly: '<?'
      },
      controller: [
        '$rootScope',
        '$scope',
        '$log',
        '$state',
        '$stateParams',
        '$uibModal',
        '$uibMsgbox',
        'erp_stuCourseSchedApplyYdyService',
      function (
        $rootScope,
        $scope,
        $log,
        $state,
        $stateParams,
        $uibModal,
        $uibMsgbox,
        erp_stuCourseSchedApplyYdyService) {
        var $ctrl = this;
        $scope.apply = $ctrl.apply;
        $scope.matchList = {};
        $scope.optype = 'edit'
        $scope.checkAll = false;
        // 申请单基础信息是否只读
        $scope.applyBaseReadOnly = true
        // 初步排课意向表
        $scope.applyPlanList = []
        // 学期类型
        $scope.termTypes = [
          {
            value: "1",
            label: '上学期'
          }, {
            value: "2",
            label: '下学期'
          }
        ]
        // 申请单类型
        $scope.applyTypes = [
          {
            value: "1",
            label: '新单'
          }, {
            value: "2",
            label: '加课单'
          }, {
            value: "3",
            label: '换单'
          }
        ]
        // 申请档期
        $scope.stuSchedTimeList = ''
        // 申请单
        $scope.apply = {}
        // 申请单ID
        $scope.applyId = 0
      
        // 获取申请单详情
        $scope.getApplyDetail = function (applyId) {
          return erp_stuCourseSchedApplyYdyService.getDetail({
            id: applyId
          }).$promise.then(function (resp) {
            if (!resp.error) {
              $scope.apply = resp.data
            } else {
              $uibMsgbox.error(resp.message)
            }
          }, function (resp) {})
        }
      
        // 获取初步排课意向表
        $scope.getApplyPlanList =  function (applyId) {
          applyId = applyId || $scope.applyId;
          return erp_stuCourseSchedApplyYdyService.getApplyPlanList({
            applyId: applyId
          }).$promise.then(function (resp) {
            if (!resp.error) {
              $scope.applyPlanList = resp.data
            } else {
              $uibMsgbox.error(resp.message)
            }
          }, function (resp) {})
        }
      
        // 初步排课意向操作 （status: 1:未排课，2：正常排课,3:压单，4：取消压单）
        /**
         * item: 排课意向单
         * status: 状态
         */
        $scope.handlePutSchedPlanSatus = function (item, status) {
          $scope.PlanSatus = status;
          item.status = status;
          if (status == 2) {
            // 匹配
            $scope.matchList = item;
             _.forEach($scope.applyPlanList, function (apply) {
                apply.checked = apply.id === item.id
             });
             setTimeout(function(){
              document.getElementById('applyPlanDialog.html').scrollIntoView();
             },0)
             
          } else if (status == 3) {
            // 压单
            openOverstockDialog(item);
          } else if (status == 4) {
            // 取消压单
            $uibMsgbox.confirm('确认取消压单？', function (res) {
              if (res == 'yes') {
                putSchedPlanSatus(item);
              }
            })
          } else if (status == 5) {
            openOverstockDialog(item); // 课单事故
          } else {
            $uibMsgbox.error('未知的初步排课意向状态：' + status + '，请联系管理员！')
          }
        }
      
        $scope.onMatchSuccess  = function () {
          $scope.getApplyPlanList();
        }

        //全选
        $scope.checkAllApplyPlan = function() {
          $scope.checkAll = !$scope.checkAll;
	        if ($scope.applyPlanList && $scope.applyPlanList.length > 0) {
            $.each($scope.applyPlanList, function (idx, item) {
              item.checkFlag = $scope.checkAll;
            });
          }
        }

        //批量课单事故
        $scope.batchOperation = function() {
          var arr = [];
          if ($scope.applyPlanList && $scope.applyPlanList.length > 0) {
            $.each($scope.applyPlanList, function (idx, item) {
                if (item.checkFlag) {
                  let batchItem = { "id": item.id, "applyId": item.applyId, "status": 5, "remark": item.remark, "courseSpId": item.courseSpId }
                  if (!(item.status == 4 || item.subjectId != $scope.schedulableSubjectId)==true) {
                    arr.push(batchItem);
                  } 
                }
            });
          }
          $scope.PlanSatus = 8;
          if(arr.length==0){
            $uibMsgbox.confirm('请选择初步排课意向！');
          }else{
            openOverstockDialog (arr) 
          }
        }
      
        // 课单事故
        function openOverstockDialog (item) {
          return $uibModal.open({
            templateUrl: 'applyPlanOverstock.html',
            resolve: {
              detail: function (){
                return item
              },
              PlanSatus: function(){
                return $scope.PlanSatus
              }
            },
            controller: ['$scope', 'detail', 'PlanSatus','$uibMsgbox', 'erp_stuCourseSchedApplyYdyService',
              function ($scope, detail,PlanSatus,$uibMsgbox, erp_stuCourseSchedApplyYdyService) {
                $scope.detail = _.clone(detail)
                $scope.PlanSatus = PlanSatus;
                if(PlanSatus==8){
                  $scope.detail = {};
                  $scope.batchList = _.clone(detail);
                }
                $scope.valid = function () {
                  if (!$scope.detail.remark) {
                    $uibMsgbox.error('请输入备注！');
                    return false;
                  }
                  return true;
                }
                $scope.onOk = function () {
                  if (!$scope.valid()) {
                    return false;
                  }
                  if(PlanSatus==8){
                    angular.forEach($scope.batchList, function (item) {
                      item.remark = $scope.detail.remark;
                    });
                    erp_stuCourseSchedApplyYdyService.putApplyPlanStatus($scope.batchList, function (resp) {
                      if (!resp.error) {
                        $scope.$close();
                        $uibMsgbox.confirm('操作成功')
                      } else {
                        $uibMsgbox.error(resp.message)
                      }
                    }, function (resp) {})
                  }else{
                    erp_stuCourseSchedApplyYdyService.putApplyPlan(
                      _.pick($scope.detail, ['id', 'applyId', 'remark', 'status', 'courseSpId'])).$promise.then(function(resp) {
                      if (!resp.error) {
                        $scope.$close();
                      } else {
                        $uibMsgbox.error(resp.message)
                      }
                    }, function (resp) {})
                  }
                 
                }
              }
            ]
          }).result.then(function () {
            $scope.getApplyPlanList();
          })
        }
      
        function putSchedPlanSatus (item) {
          erp_stuCourseSchedApplyYdyService.putApplyPlan(item)
            .$promise.then(function (resp) {
              if (!resp.error) {
                $scope.getApplyPlanList()
              } else {
                $uibMsgbox.error(resp.message)
              }
            }, function (resp) {
              $uibMsgbox.error('请求失败!' + resp);
            })
        }

          $ctrl.$onInit = function () {
            $scope.applyId = $stateParams.id;
            $scope.schedulableSubjectId = $stateParams.schedulableSubjectId;
            $scope.curCourseArrangerId = $stateParams.curCourseArrangerId;
            if ($state.current.name == "classesScheduleYdyMatchDetail") {
              $scope.optype = 'view'
            } else {
              $scope.optype = 'edit'
            }
            $scope.getApplyDetail($scope.applyId).then(function () {
              return $scope.getApplyPlanList($scope.applyId)
            })
            $scope.$watch(function() {return $ctrl.apply}, function () {
              $scope.apply = $ctrl.apply;
              $scope.matchList = $scope.matchList;
            })
          };
          $ctrl.$onChanges = function (changesObj) {
          };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();