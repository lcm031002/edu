/**
 * 1对1排课审批
 * Created By baiqb@klxuexi.org 2017-08-11
 * 
 */
angular.module('ework-ui').controller('erp_courseSchedulingYdyApprovalController',  [
  '$scope',
  '$log',
  '$state',
  '$uibModal',
  '$uibMsgbox',
  'erp_dictService',
  'erp_stuCourseSchedApplyYdyService',
  erp_courseSchedulingYdyApprovalController
])

function erp_courseSchedulingYdyApprovalController (
  $scope,
  $log,
  $state,
  $uibModal,
  $uibMsgbox,
  erp_dictService,
  erp_stuCourseSchedApplyYdyService
) {
  /**
   * 个性化排课申请类别 schedApplyType
   * 个性化排课申请状态 schedApplyStatus
   * 个性化排课申请审批状态 schedApplyAuditStatus
   * 学期 term
   * 个性化排课申请课程规划状态 schedApplyCourseReqStatus
   * 校区类型 orgKind
   */
  $scope.optype = 'showList'
  $scope.selectedApply = {}
  $scope.pageConf = {
    currentPage: 1,
    totalItems: 0,
    itemsPerPage: 30,
    onChange: function () {
      $scope.getList()
    }
  }
  $scope.onDateRangeChange = function () {
    $scope.getList();
  }
  $scope.applyStatus = {
    1: {name: '已提交', cls: 'text-primary'},
    2: {name: '匹配中', cls: 'text-danger'},
    3: {name: '已匹配', cls: 'text-success'},
    4: {name: '已排课', cls: 'text-warning'},
    5: {name: '草稿', cls: 'text-default'},
    0: {name: '取消', cls: 'text-muted'}
  }
  $scope.applyStatusList = [
    {
      code: null, name: '全部'
    }, {
      code: 1, name: '已提交'
    }, {
      code: 2, name: '匹配中'
    }, {
      code: 3, name: '已匹配'
    }, {
      code: 4, name: '已排课'
    }, {
      code: 5, name: '草稿'
    }, {
      code: 0, name: '已取消'
    }
  ]
  $scope.searchParam = {
    beginDateFrom: moment().startOf('month').format('YYYY-MM-DD'),
    beginDateTo: moment().endOf('month').format('YYYY-MM-DD'),
    type: 'audit',
    buId: null,
    status: null,
    branchId: null,
    courseAdmin: null,
    studentName: null,
    encoding: null
  }
  $scope.auditStatusList = []
  $scope.applyList = []

  $scope.getList = function () {
    var params = _.cloneDeep($scope.searchParam)
    params.currentPage = $scope.pageConf.currentPage
    params.pageSize = $scope.pageConf.itemsPerPage
    erp_stuCourseSchedApplyYdyService.get(params, function (resp) {
      $scope.applyList = resp.data
      $scope.pageConf.totalItems = resp.total
    })
  }
  $scope.getDicts = function () {
    erp_dictService.get({
      code: 'schedApplyAuditStatus'
    }).$promise.then(function (resp) {
      resp.data.unshift({
        code: null,
        name: '全部'
      })
      $scope.auditStatusList = resp.data
    })
  }
  $scope.init = function () {
    $scope.getDicts()
    $scope.getList()
  }
  
  $scope.onAsignArranger = function (item) {
    openAuditDialog('asign', item)
  }

  $scope.onRefuseArranger = function (item) {
    openAuditDialog('reject', item)
  }

  $scope.onViewDetail = function (item) {
    // $scope.selectedApply = item;
    erp_stuCourseSchedApplyYdyService.getDetail({
      id: item.id
    }, function (resp) {
      if (!resp.error) {
        if (!resp.data) {
          return
        }
        $scope.selectedApply = resp.data
      } else {
        $uibMsgbox.error(resp.message)
      }
    })
    $scope.optype = 'viewDetail';
  }

  $scope.init()

  /////////
  /**
   * 打开排课审批对话框
   * @param {string} optype 可选值：'asign'-指派排课专员， 'reject'-拒绝
   * @param {object} apply 申请单
   */
  function openAuditDialog (optype, apply) {
    return $uibModal.open({
      templateUrl: 'approvalModal.html',
      backdrop: 'static',
      resolve: {
        optype: function () {
          return optype
        },
        apply: function () {
          return apply
        }
      },
      controller: [
        '$scope', 
        '$uibMsgbox',
        'optype',
        'apply',
        'erp_arrangerService',
        'erp_stuCourseSchedApplyYdyService',
        function (
          $scope,
          $uibMsgbox,
          optype,
          apply,
          erp_arrangerService,
          erp_stuCourseSchedApplyYdyService
        ) {
          var auditStatusMap = {
            asign: 1,
            reject: 0
          }
          $scope.optype = optype
          $scope.arrangerList = []
          $scope.audit = {
            id: apply.id,
            auditStatus: auditStatusMap[optype],
            courseArrangerId: null,
            rejectReason: null
          }

          activate(apply);

          $scope.onSave = function () {
            if (!arrangerValid()) {
              return false;
            }
            var waitModal = $uibMsgbox.waiting('保存中，请稍候...')
            erp_stuCourseSchedApplyYdyService.putAudit($scope.audit)
              .$promise.then(function(resp) {
                waitModal.close();
                if (!resp.error) {
                  $uibMsgbox.success('操作成功！', function() {
                    $scope.$close();
                  })
                } else {
                  $uibMsgbox.error(resp.message)
                }
              }, function(resp) {
                waitModal.close();
                $uibMsgbox.error('请求失败！' + resp.statusText);
                console.error(resp)
              })
          }
          //////////
          function activate(apply) {
            erp_arrangerService.query({
              "p_branchId" : apply.branchId,
              "p_gradeId" : apply.gradeId,
              "p_subjectId" : apply.schedulableSubjectId
            }).$promise.then(function(resp) {
              $scope.arrangerList = resp.data;
            })
          }

          function arrangerValid() {
            if (optype == 'asign' && !$scope.audit.courseArrangerId) {
              $uibMsgbox.error('请选择排课专员！');
              return false;
            }
            if (optype == 'reject' && !$scope.audit.rejectReason) {
              $uibMsgbox.error('请输入拒绝原因！');
              return false;
            }
            return true;
          }
        }
      ]
    }).result.then(function(){
      $scope.getList(); 
    })
  }
}