/**
 * 1对1排课处理
 * Created By baiqb@klxuexi.org 2017-08-11
 * 
 */
angular.module('ework-ui').controller('erp_courseSchedulingYdyProcessController',  [
  '$rootScope',
  '$scope',
  '$log',
  '$state',
  '$stateParams',
  '$uibMsgbox',
  'erp_stuCourseSchedApplyYdyService',
  erp_courseSchedulingYdyProcessController
])

function erp_courseSchedulingYdyProcessController (
  $rootScope,
  $scope,
  $log,
  $state,
  $stateParams,
  $uibMsgbox,
  erp_stuCourseSchedApplyYdyService
) {
  $scope.selectedApply = {};
  $scope.optype="showList";
  $scope.pageConf = {
    currentPage: 1,
    totalItems: 0,
    itemsPerPage: 30,
    onChange: function () {
      $scope.getApplyList()
    }
  }
  
  if (!$rootScope.curEmployee) {
    $rootScope.curEmployee = {}
  }
  
  $scope.searchParam = {
    beginDateFrom: moment().startOf('month').format('YYYY-MM-DD'),
    beginDateTo: moment().endOf('month').format('YYYY-MM-DD'),
    range: 'curMonth',
    auditStatus: null,
    buId: null,
    type: 'handle',
    status: null,
    branchId: null,
    courseAdmin: null,
    studentName: null,
    encoding: null,
    defaultName:$rootScope.curEmployee.employeeName,
    employeeId:$rootScope.curEmployee.id
  }

  $scope.$watch('curEmployee', function(newValue) {
    if (newValue && newValue.employeeId) {
      $scope.searchParam.defaultName = $rootScope.curEmployee.employeeName
      $scope.searchParam.employeeId = $rootScope.curEmployee.employeeId
    }
  })

  $scope.applyStatus = {
    3: {name: '已完成', cls: 'text-success'},
    4: {name: '有压单', cls: 'text-success'},
  }
  $scope.applyStatusList = [
    {
      code: null, name: '全部'
    }, {
      code: 3, name: '已完成'
    }, {
      code: 4, name: '有压单'
    }
  ]
  $scope.auditStatusList = [
    {value: null, label: '全部'},
    {value: 0, label: '审批不通过'},
    {value: 1, label: '审批通过'},
    // {value: 2, label: '审批中'},
    // {value: 3, label: '未审批'},
  ];

  $scope.applyList= [];

  $scope.getApplyList = function () {
    var params = _.cloneDeep($scope.searchParam)
    params.currentPage = $scope.pageConf.currentPage
    params.pageSize = $scope.pageConf.itemsPerPage
    $scope.loadStatues = true;
    return erp_stuCourseSchedApplyYdyService.get(params)
      .$promise.then(function(resp) {
        $scope.loadStatues = false;
        if (!resp.error) {
          // console.log(resp)
          $scope.applyList = resp.data;
          $scope.pageConf.totalItems = resp.total;
        } else {
          $uibMsgbox.error(resp.message);
        }
      }, function (resp) {
        $uibMsgbox.error(resp);
      })
  }

  $scope.exportReport = function () {
      var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
      erp_stuCourseSchedApplyYdyService.exportScheduleProcessExcel($scope.searchParam, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                window.location.href = '../erp/studentservice/schedulingDeal/downloadExcel?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
      
    };

  $scope.editApplyDetail = function (apply) {
    $scope.selectedApply = _.cloneDeep(apply);
    $scope.optype = 'edit';
  }

  $scope.viewApplyDetail = function (apply) {
    $scope.selectedApply = _.cloneDeep(apply);
    $scope.optype = 'view';
  }

  $scope.showList = function () {
    $scope.optype = 'showList';
    $scope.getApplyList();
  }
  $scope.onAddNewScheduling = function () {
    window.open('?studentId='+$scope.searchParam.studentId+'#/studentMgr/studentMgrYDYSchedule?optype=newScheduling')
  }
  activate();
  ////////////////

  function activate() {
    if ($stateParams.studentName && $stateParams.studentId) {
      $scope.searchParam.studentId = $stateParams.studentId;
      $scope.searchParam.studentName = $stateParams.studentName;
    }
    setTimeout(() => {
      $scope.getApplyList().then();
    }, 0);
  }
} 


 