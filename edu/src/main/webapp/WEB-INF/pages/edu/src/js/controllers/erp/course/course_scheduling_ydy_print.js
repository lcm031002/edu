/**
 * 1对1讲义打印
 * Created By yans@klxuexi.org 2017-12-25
 * 
 */
angular.module('ework-ui').controller('erp_courseSchedulingYdyPrintController', [
  '$rootScope',
  '$scope',
  '$log',
  '$state',
  '$uibMsgbox',
  'erp_studentBuOrgsService',
  'erp_stuCourseSchedYdyPrintService',
  'PUBORGSelectedService',
  erp_courseSchedulingYdyPrintController
])

function erp_courseSchedulingYdyPrintController(
  $rootScope,
  $scope,
  $log,
  $state,
  $uibMsgbox,
  erp_studentBuOrgsService,
  erp_stuCourseSchedYdyPrintService,
  PUBORGSelectedService
) {
  $scope.printStatus = 'TO_PRINT';
  /**
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
    $scope.showId=null;
    $scope.getList();
  }

  $scope.searchParam = {
    minDate: moment().startOf('day').format('YYYY-MM-DD'),
    startDate: moment().startOf('day').format('YYYY-MM-DD'),
    endDate: moment().endOf('day').format('YYYY-MM-DD'),
    range: 'today',
  }

  //切换打印状态
  $scope.changeStatus = function(arr){
    $scope.showId=null;
    $scope.searchParam = {
      minDate: moment().startOf('day').format('YYYY-MM-DD'),
      startDate: moment().startOf('day').format('YYYY-MM-DD'),
      endDate: moment().endOf('day').format('YYYY-MM-DD'),
      range: 'today',
      branchId: $scope.defaultBranchId
    }
    if(arr=='0'){
      $scope.printFlag = 'TO_PRINT'
    }else{
      $scope.printFlag = 'PRINT'
    }
    $scope.getList();
  }

  //获取讲义内容
  $scope.applyList = []
  $scope.getList = function () {
    $scope.loadStatues = true;   
    var params = _.cloneDeep($scope.searchParam)
    params.printFlag = $scope.printFlag
    params.pageSize = $scope.pageConf.itemsPerPage
    params.currentPage = $scope.pageConf.currentPage 
    erp_stuCourseSchedYdyPrintService.post(params, function (resp) {
      $scope.loadStatues = false;
      if(!resp.error){
        $scope.applyList = resp.data
        $scope.pageConf.totalItems = resp.total || 0;
      } else{
        alert(resp.message);
      }
    })
  }

  //打印跳转新页面
  $scope.printOrder = function(arr){
    var params = arr
    erp_stuCourseSchedYdyPrintService.printpost(params, function (resp) {
      $scope.printList = resp.data
      window.open($scope.printList,'_blank');
      $scope.getList();
      $scope.pageConf.totalItems = resp.total || 0;
    })
  }
  //显示下拉详情
  $scope.showDetail = function(arr){
    if($scope.showId != arr.id){
      $scope.isCollapsed = true;
    }
    $scope.isCollapsed = !$scope.isCollapsed
    $scope.showId = arr.id;
  }
  function querySelectedOrg() {
    PUBORGSelectedService.query({}, function (resp) {
      if (!resp.error) {
        $scope.selectedOrg = resp.data;
        if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {
          $.each($scope.branchList, function (i, b) {
            if (b.id == $scope.selectedOrg.id) {
              $scope.searchParam.branchId = b.id;
              $scope.defaultBranchId = b.id;
              $scope.getList();              
            }
          });
        } else {
          $uibMsgbox.warn('您还没选择校区，请选择校区！', function() {
            setTimeout(function() {
              $('.btn-group.sel-org.pull-left').addClass('open');
            }, 300);
          })
        }
      } else {
        alert(resp.message);
      }
    })
  }
  /**
    * 查询校区
    */
  function queryBuOrgs() {
    erp_studentBuOrgsService.query({}, function (resp) {
      if (!resp.error) {
        $scope.branchList = resp.data;
        querySelectedOrg() 
      }
    })
  }

  $scope.init = function () {
    queryBuOrgs();
  }
  
  $scope.init()
}