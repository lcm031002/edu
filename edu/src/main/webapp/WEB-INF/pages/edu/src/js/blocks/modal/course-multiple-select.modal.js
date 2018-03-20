angular.module('ework-ui').controller('modal_courseMultiSelectController', [
  '$rootScope',
  '$scope',
  '$uibMsgbox',
  'erp_studentBuOrgsService',
  'PUBORGSelectedService',
  'erp_timeSeasonService',
  'erp_subjectService',
  'erp_gradeService',
  'erp_courseService',
  'selectedCourseList',
  modal_courseMultiSelectController
]);

function modal_courseMultiSelectController(
  $rootScope,
  $scope,
  $uibMsgbox,
  erp_studentBuOrgsService,
  PUBORGSelectedService,
  erp_timeSeasonService,
  erp_subjectService,
  erp_gradeService,
  erp_courseService,
  selectedCourseList
) {
  $scope.business_type = 1
  $scope.tableConf = {
    selectAll: false
  }
  $scope.selectedTimeSeason = null
  $scope.selectedGrade = null
  $scope.selectedSubject = null
  $scope.branchList = []
  $scope.timeSeasonList = []
  $scope.gradeList = []
  $scope.subjectList = []
  $scope.selectedCourseList = [];
  _.forEach(selectedCourseList, function (item) {
    $scope.selectedCourseList.push(item.course)
  })
  $scope.business_types = [{
    value: 1,
    label: '培英班课程'
  }, {
    value: 2,
    label: '一对一课程'
  }, {
    value: 3,
    label: '晚辅导课程'
  }]
  init();
  $scope.onCheckAllChange = function () {
    _.forEach($scope.toSelectingCourseList, function (item) {
      item.checked = $scope.tableConf.selectAll
    })
  }

  $scope.onCourseCheckedChange = function (course) {
    $scope.tableConf.selectAll = _.every($scope.toSelectingCourseList, {checked: true})
  }
  /**
   * 选择校区
   * @param branch
   */
  $scope.selectBranch = function(branch) {
    $scope.selectedBranch = branch;
    querySubject();
    queryGrade();
    $scope.querySelectingCourse();
  }

  /**
   * 选择课程季
   * @param season
   */
  $scope.selectSeason = function(season) {
    $scope.selectedTimeSeason = season;
    querySubject();
    queryGrade();
    $scope.querySelectingCourse();
  }

  /**
   * 选择年级
   * @param grade
   */
  $scope.selectGrade = function(grade) {
    $scope.selectedGrade = grade;
    querySubject();
    $scope.querySelectingCourse();
  }

  /**
   * 选择科目
   * @param subject
   */
  $scope.selectSubject = function(subject) {
    $scope.selectedSubject = subject;
    $scope.querySelectingCourse();
  }

  $scope.selectBusinessType = function (business_type) {
    $scope.business_type = business_type
    $scope.querySelectingCourse();
  }

  $scope.onOk = function () {
    let checkedCourseList = _.filter($scope.toSelectingCourseList, {checked: true})
    if (!checkedCourseList || checkedCourseList.length <= 0) {
      $uibMsgbox.alert('请至少选择一门课程！')
      return
    }
    $scope.$close(checkedCourseList)
  }
  /**
   * 查询课程
   */
  $scope.querySelectingCourse = function() {
    var param = {
      branch_id: $scope.selectedBranch.id,
      season_id: $scope.selectedTimeSeason ? $scope.selectedTimeSeason.id : -1,
      grade_id: $scope.selectedGrade ? $scope.selectedGrade.id : -1,
      subject_id: $scope.selectedSubject ? $scope.selectedSubject.id : -1,
      business_type: $scope.business_type,
      status : 1, //只查询上架课程
      search_info: $("#courseSearchInfo").val()
    };
    $scope.isQuerySelectingCourse = 'isQuerySelectingCourse';
    erp_courseService.query(param, function(resp) {
      $scope.toSelectingCourseList = [];
      $scope.isQuerySelectingCourse = '';
      if (!resp.error) {
        var toSelectingCourseList = _.differenceBy(resp.data, $scope.selectedCourseList, 'id')
        _.forEach(toSelectingCourseList, function (item) {
          item.checked = false
          $scope.toSelectingCourseList.push(item)
        })
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  // 初始化
  function init() {
    queryBuOrgs()
      .then(function () {
        return querySelectedOrg()
      })
      .then(function () {
        return queryTimeSeason()
      })
      .then(function () {
        return querySubject()
      })
      .then(function () {
        return queryGrade()
      })
      .then(function () {
        $scope.querySelectingCourse()
      })
  }
  // 查询已选择的组织
  function querySelectedOrg() {
    return PUBORGSelectedService.query({})
      .$promise.then(function(resp) {
        if (!resp.error) {
          $scope.selectedOrg = resp.data;
          if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {
            $.each($scope.branchList, function(i, b) {
              if (b.id == $scope.selectedOrg.id) {
                $scope.selectedBranch = b;
              }
            });
          } else {
            $uibMsgbox.warn("请选择校区!");
          }
        } else {
          $uibMsgbox.error(resp.message);
        }
      })
  }
  // 查询团队
  function queryBuOrgs() {
    return erp_studentBuOrgsService.queryAll({})
      .$promise.then(function(resp) {
        if (!resp.error) {
          $scope.branchList = resp.data;
          querySelectedOrg();
        }
      })
  }

   /**
   * 查询课程季
   */
  function queryTimeSeason() {
    return erp_timeSeasonService.list({})
      .$promise.then(function(resp) {
        if (!resp.error) {
          $scope.timeSeasonList = resp.data;
        }
      })
  }

    /**
   * 查询科目
   */
  function querySubject() {
    return erp_subjectService.querySelectDatas({
      branch_id: $scope.selectedBranch ? $scope.selectedBranch.id : -1,
      season_id: $scope.selectedTimeSeason ? $scope.selectedTimeSeason.id : -1,
      grade_id: $scope.selectedGrade ? $scope.selectedGrade.id : -1
    }).$promise.then(function(resp) {
      if (!resp.error) {
        $scope.subjectList = resp.data;
      }
    })
  }

  /**
   * 查询年级
   */
  function queryGrade() {
    return erp_gradeService.querySelectDatas({
      branch_id: $scope.selectedBranch ? $scope.selectedBranch.id : -1,
      season_id: $scope.selectedTimeSeason ? $scope.selectedTimeSeason.id : -1
    }).$promise.then(function(resp) {
      if (!resp.error) {
        $scope.gradeList = resp.data;
        if ($scope.student && $scope.student.grade_id) {
          $.each($scope.gradeList, function(i, grade) {
            if (grade.id == $scope.student.grade_id) {
              $scope.selectedGrade = grade;
            }
          })
        }
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }
}
