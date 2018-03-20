(function () {
  'use strict';

  // Usage: 学员课程规划
  // 
  // Creates:
  // 

  angular
    .module('ework-ui')
    .component('klcErpCourseStuSchedBaseInfoPlan', {
      //template:'htmlTemplate',
      templateUrl: 'templates/components/erp/course/stuSchedBaseInfoPlan.html',
      controllerAs: '$ctrl',
      bindings: {
        apply: '=',
        readOnly: '<?'
      },
      controller: ['$scope', '$uibModal', '$uibMsgbox', 
        'erp_studentsService',
        'erp_teacherGroupService',
        'erp_stuCourseSchedApplyYdyService',
        function ($scope, $uibModal, $uibMsgbox, erp_studentsService, erp_teacherGroupService, erp_stuCourseSchedApplyYdyService) {
          var $ctrl = this;
          $scope.apply = $ctrl.apply;
          $scope.pageData = {
            totalRequirement:0
          };
          // 计算每周总课程规划节数
          $scope.calcReqSum = function () {
            var sum = 0
            _.forEach($scope.apply.stuReqList, function (req) {
              sum = sum + req.requirement
            })
            $scope.pageData.totalRequirement = sum
          }

          $scope.$on('recalcReqSum', function () {
            $scope.calcReqSum()
          })
          
          $scope.onUpdateSched = function (schedule) {
            $scope.apply.schedule = schedule
          }

          $scope.queryStudentInfo = function () {
            erp_studentsService.query({
              row_num: 20,
              studentId: $scope.apply.studentId
            },
              function (resp) {
                if (!resp.error && resp.data.length) {
                  $scope.apply.student = resp.data[0];
                } else {
                  $uibMsgbox.error(resp.message);
                }
              });
          }
          
          $scope.$watch('apply.studentId', function (newValue, oldValue) {
            if (newValue) {
              $scope.queryStudentInfo();
            }
          });

          $scope.handleBranchChange = function (branch) {
            $scope.apply.branchEmail = branch.email
          }
          $scope.onUpdateSched = function (schedule) {
            $scope.apply.schedule = schedule;
          }
          // 添加课程规划
          $scope.addReq = function () {
            openReqModal($scope.apply.id, $scope.apply.applyType).result.then(function (req) {
              $scope.apply.stuReqList.push(req)
              $scope.apply.stuReqList = _.sortBy($scope.apply.stuReqList, 'seq')
              $scope.calcReqSum()
            }, function () { })
          }
          // 编辑课程规划
          $scope.editReq = function (req) {
            openReqModal($scope.apply.id, $scope.apply.applyType, req, 'edit').result.then(function (reqRes) {
              req = _.assign(req, reqRes)
              $scope.apply.stuReqList = _.sortBy($scope.apply.stuReqList, 'seq')
              $scope.calcReqSum()
            }, function () { })
          }
          // 删除课程规划
          $scope.delReq = function (req) {
            $uibMsgbox.confirm('确定删除该课程规划？', function (res) {
              if (res == 'yes') {
                erp_stuCourseSchedApplyYdyService.delReq({
                  id: req.id
                }, function (resp) {
                  if (!resp.error) {
                    $scope.apply.stuReqList.splice(
                      _.findIndex($scope.apply.stuReqList, req), 1
                    )
                    $scope.calcReqSum()
                  } else {
                    $uibMsgbox.error(resp.message)
                  }
                })
              }
            })
          }

          // 打开课程规划对话框
          function openReqModal(applyId, applyType, req, optype) {
            var reqCopy = _.cloneDeep(req)
            return $uibModal.open({
              templateUrl: 'erp_courseSchedApplyReqModal.html',
              resolve: {
                applyId: function () {
                  return applyId
                },
                applyType: function () {
                  return applyType
                },
                req: function () {
                  return reqCopy || {}
                },
                optype: function () {
                  return optype || 'add'
                }
              },
              controller: [
                '$scope',
                'erp_stuCourseSchedApplyYdyService',
                'applyId',
                'applyType',
                'req',
                'optype',
                function (
                  $scope,
                  erp_stuCourseSchedApplyYdyService,
                  applyId,
                  applyType,
                  req,
                  optype
                ) {
                  $scope.req = req || {}
                  if (optype == 'add') {
                    $scope.req.seq = $scope.req.seq || 1
                  }
                  $scope.saveReq = function () {
                    $scope.req.applyId = applyId;
                    $scope.req.subjectId = parseInt($scope.req.subjectId, 10);
                    $scope.req.teachGroupId = parseInt($scope.req.teachGroupId, 10);                    
                    $scope.req.requirement = parseInt($scope.req.requirement, 10);
                    $scope.req.seq = parseInt($scope.req.seq, 10);
                    $scope.req.applyType = applyType;
                    if (!$scope.req.subjectId) {
                      return $uibMsgbox.warn('请选择科目！')
                    }
                    if (!$scope.req.teachGroupId) {
                      return $uibMsgbox.warn('请选择教研组！')
                    }
                    if (!$scope.req.requirement || isNaN($scope.req.requirement)) {
                      return $uibMsgbox.warn('请输入每周上课节数!')
                    }
                    if (!$scope.req.seq || isNaN($scope.req.seq)) {
                      return $uibMsgbox.warn('请输入排序序号')
                    }
                    var waitingModal = $uibMsgbox.waiting('保存中，请稍候...')
                    if (optype == 'add') {
                      erp_stuCourseSchedApplyYdyService.postReq($scope.req)
                        .$promise.then(function (resp) {
                          waitingModal.close()
                          if (!resp.error) {
                            $scope.req.id = resp.data.id
                            $scope.$close($scope.req)
                          } else {
                            $uibMsgbox.error(resp.message)
                          }
                        }, function (resp) {
                          waitingModal.close()
                          $uibMsgbox.error('数据请求失败，请联系管理员！' + resp.message)
                        })
                    } else {
                      erp_stuCourseSchedApplyYdyService.putReq($scope.req)
                        .$promise.then(function (resp) {
                          waitingModal.close()
                          if (!resp.error) {
                            $scope.$close($scope.req)
                          } else {
                            $uibMsgbox.error(resp.message)
                          }
                        }, function () {
                          waitingModal.close()
                          $uibMsgbox.error('数据请求失败，请联系管理员！' + resp.message)
                        })
                    }
                  }

                  // 获取教研组
                  $scope.teachGroupList = [];
                  $scope.getGroup = function () {
                    erp_teacherGroupService.queryList({}, function (resp) {
                      if (!resp.error) {
                        $scope.teachGroupList = resp.data;
                      } else {
                        $uibMsgbox.error(resp.message)
                      }
                    })
                  }
                  $scope.getGroup();
                }
              ]
            })
          }

          $ctrl.$onInit = function () {
            $scope.$watch(function() {return $ctrl.apply}, function () {
              $scope.apply = $ctrl.apply;
              // 等待渲染完成，nexttick的时候再渲染每周总节数
              setTimeout(function () {
                $scope.calcReqSum();
              }, 0)
            });
          };
          $ctrl.$onChanges = function (changesObj) {
          };
          $ctrl.$onDestroy = function () { };
        }
      ]
    });

})();