/**
 * 1对1排课申请
 * Created By baiqb@klxuexi.org 2017-08-11
 * 
 */
angular.module('ework-ui').controller('erp_courseSchedulingYdyApplyController',  [
  '$rootScope',
  '$scope',
  '$log',
  '$state',
  '$uibMsgbox',
  'erp_stuCourseSchedApplyYdyService',
  erp_courseSchedulingYdyApplyController
])

function erp_courseSchedulingYdyApplyController (
  $rootScope,
  $scope,
  $log,
  $state,
  $uibMsgbox,
  erp_stuCourseSchedApplyYdyService
) {
  // $scope.applyStatus = {
  //   1: {name: '已提交', cls: 'text-primary'},
  //   2: {name: '匹配中', cls: 'text-danger'},
  //   3: {name: '已匹配', cls: 'text-success'},
  //   4: {name: '已排课', cls: 'text-warning'},
  //   5: {name: '草稿', cls: 'text-default'},
  //   0: {name: '取消', cls: 'text-muted'}
  // }
  // $scope.applyStatusList = [
  //   {
  //     code: null, name: '全部'
  //   }, {
  //     code: 1, name: '已提交'
  //   }, {
  //     code: 2, name: '匹配中'
  //   }, {
  //     code: 3, name: '已匹配'
  //   }, {
  //     code: 4, name: '已排课'
  //   }, {
  //     code: 5, name: '草稿'
  //   }, {
  //     code: 0, name: '已取消'
  //   }
  // ]
  $scope.applyStatus = {
    1: {name: '待匹配', cls: 'text-primary'},
    2: {name: '匹配中', cls: 'text-danger'},
    3: {name: '已完成', cls: 'text-success'},
    4: {name: '有压单', cls: 'text-success'},
    5: {name: '草稿', cls: 'text-default'},
    0: {name: '取消', cls: 'text-muted'}
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
    }, {
      code: 5, name: '草稿'
    }, {
      code: 0, name: '取消'
    }
  ]

  $scope.pageConf = {
    currentPage: 1,
    totalItems: 0,
    itemsPerPage: 30,
    onChange: function () {
      $scope.getList()
    }
  }
  $scope.searchParam = {
    beginDateFrom: moment().startOf('month').format('YYYY-MM-DD'),
    beginDateTo: moment().endOf('month').format('YYYY-MM-DD'),
    defaultName: $rootScope.curEmployee.employeeName,
    employeeId: $rootScope.curEmployee.id,    
    range: 'curMonth',
    buId: null,
    type: 'apply',
    status: null,
    auditStatus: null,
    branchId: null,
    create_user_name: null,
  }
  
  $scope.auditStatusList = [
    {value: null, label: '全部'},
    {value: 0, label: '审批不通过'},
    {value: 1, label: '审批通过'},
    // {value: 2, label: '审批中'},
    {value: 3, label: '待审批'},
  ];

  $scope.applyList = []

  $scope.newApply = function () {
    $state.go('classesScheduleYdyApplyAdd')
  }
  $scope.editApplyDetail = function (item) {
    $state.go('classesScheduleYdyApplyEdit', {
      id: item.id
    })
  }
  $scope.viewApplyDetail = function (item) {
    $state.go('classesScheduleYdyApplyDetail', {
      id: item.id
    })
  }
  $scope.onDateRangeChange = function () {
    $scope.getList()
  }
  /* 获取申请列表 */
  $scope.getList = function () {
    $scope.loadStatue = true;
    var params = _.cloneDeep($scope.searchParam)
    params.currentPage = $scope.pageConf.currentPage
    params.pageSize = $scope.pageConf.itemsPerPage
    erp_stuCourseSchedApplyYdyService.query(params, function (resp) {
      $scope.loadStatue = false;
      if (!resp.error) {
        $scope.applyList = resp.data
        $scope.pageConf.totalItems = resp.total
      } else {
        $uibMsgbox.alert(resp.message)
      }
    })
  }
  $scope.cancelApply = function (item) {
    $uibMsgbox.confirm('确认取消该申请单？', function (res) {
      if (res == 'yes') {
        erp_stuCourseSchedApplyYdyService.putStatus({
          id: item.id,
          status: 0
        }).$promise.then(function (resp) {
          if (!resp.error) {
            $scope.getList()
          } else {
            $uibMsgbox.error(resp.message)
          }
        }, function (resp) {
          $uibMsgbox.error('请求失败!' + resp)
        })
      }
    })
  }
  $scope.deleteApply = function (item) {
    $uibMsgbox.confirm('确定删除该申请单？', function (res) {
      if (res == 'yes') {
        erp_stuCourseSchedApplyYdyService.delete({
          id: item.id
        }).$promise.then(function (resp) {
          if (!resp.error) {
            $uibMsgbox.alert('删除成功！')
            $scope.getList()
          } else {
            $uibMsgbox.error(resp.message)
          }
        }, function (resp) {
          $uibMsgbox.error(resp.message)
        })
      }
    })
  }
  $scope.init = function () {
    $scope.getList()
  }
  $scope.init()
}