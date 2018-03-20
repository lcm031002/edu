/**
 * 1对1师生匹配
 * Created By baiqb@klxuexi.org 2017-08-11
 * 
 */
angular.module('ework-ui').controller('erp_courseSchedulingYdyMatchController',  [
    '$rootScope',
  '$scope',
  '$log',
  '$state',
  '$uibMsgbox',
  'erp_dictService',
  'erp_arrangerService',
  'erp_stuCourseSchedApplyYdyService',
  erp_courseSchedulingYdyMatchController
])

function erp_courseSchedulingYdyMatchController (
    $rootScope,
  $scope,
  $log,
  $state,
  $uibMsgbox,
  erp_dictService,
  erp_arrangerService,
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
    1: {name: '待匹配', cls: 'text-primary'},
    2: {name: '匹配中', cls: 'text-danger'},
    3: {name: '已完成', cls: 'text-success'},
    4: {name: '有压单', cls: 'text-success'},
  }
  $scope.applyStatusList = [
    {
      code: null, name: '全部'
    }, {
      code: 1, name: '待匹配'
    }, {
      code: 2, name: '匹配中'
    }, {
      code: 3, name: '已完成'
    }, {
      code: 4, name: '有压单'
    }
  ]

  $scope.searchParam = {
    beginDateFrom: moment().startOf('month').format('YYYY-MM-DD'),
    beginDateTo: moment().endOf('month').format('YYYY-MM-DD'),
    type: 'match',
    buId: null,
    status: null,
    branchId: null,
    courseArrangerId: null
  }
  $scope.auditStatusList = []
  $scope.applyList = []

  $scope.getList = function () {
    $scope.loadStatues = true;
    var params = _.cloneDeep($scope.searchParam)
    params.currentPage = $scope.pageConf.currentPage
    params.pageSize = $scope.pageConf.itemsPerPage
    erp_stuCourseSchedApplyYdyService.get(params, function (resp) {
      $scope.loadStatues = false;
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

  // 获取排课专员信息
  $scope.arrangerList = [];
  $scope.queryCourseArranger = function() {
    erp_arrangerService.query({}, function(resp) {
      if (!resp.error && resp.data) {
        $scope.arrangerList = resp.data;
        $.each($scope.arrangerList, function(idx, arranger) {
          if ($rootScope.curEmployee && $rootScope.curEmployee.id && arranger.employeeId == $rootScope.curEmployee.id) {
            $scope.searchParam.curEmployeeId = +$rootScope.curEmployee.id;
            $scope.curEmployeeId = $rootScope.curEmployee.id;
          }
        });
      }
      $scope.getList();
    });
  }

  $scope.init = function () {
    $scope.getDicts();
    $scope.queryCourseArranger();
  }

  $scope.doMatch = function (item) {
    $state.go('classesScheduleYdyMatchEdit', {
      id: item.id,
      schedulableSubjectId: item.schedulableSubjectId,
      curCourseArrangerId: item.courseArrangerEmpId
    })
  }

  $scope.viewDetail = function (item) {
    $state.go('classesScheduleYdyMatchDetail', {
      id: item.id,
      schedulableSubjectId: item.schedulableSubjectId,
      curCourseArrangerId: item.courseArrangerEmpId
    })
  }

  $scope.init()
}