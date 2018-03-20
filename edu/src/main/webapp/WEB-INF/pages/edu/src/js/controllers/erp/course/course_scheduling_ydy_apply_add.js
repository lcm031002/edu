angular.module('ework-ui').controller('erp_courseSchedulingYdyApplyAddController', [
  '$scope',
  '$log',
  '$state',
  '$stateParams',
  '$uibModal',
  '$uibMsgbox',
  'erp_studentsService',
  'erp_studentScoreService',
  'erp_stuCourseSchedApplyYdyService',
  erp_courseSchedulingYdyApplyAddController
])

function erp_courseSchedulingYdyApplyAddController (
  $scope,
  $log,
  $state,
  $stateParams,
  $uibModal,
  $uibMsgbox,
  erp_studentsService,
  erp_studentScoreService,
  erp_stuCourseSchedApplyYdyService
) {

  /*************** Begin 双向绑定的Scope数据 **********************************/
  $scope.optype = 'add'
  $scope.curStep = 1
  $scope.steps = [
    {title: '第一步 选择学员'},
    {title: '第二步 学生档案模块'},
    {title: '第三步 课程设计模块'},
    {title: '第四步 申请完成'}
  ]
  // 学生搜索信息
  $scope.stuSearchInfo = {
    searchInfo: ''
  }
  // 已选择的学生
  $scope.selectedStudent = {}
  // 学生列表
  $scope.studentList = []
  $scope.stuGrade = {}
  $scope.totalRequirement = 0 //每周总课程规划节数
  // 排课申请单详细信息
  $scope.apply = {
    id: 38,
    encoding: '', // 申请单号
    applyType: '', // 申请类型, 1-新单， 2-加课单， 3-换单
    beginDate: '', // 开课日期 
    stuScoreList: '',//单科考试成绩
    stuScoreRankingList: '',//综合排名信息
    counselorId: 0, // 咨询师ID
    counselor: '', // 咨询师姓名
    counselorPhone: '', // 咨询师手机
    counselorLine: '', // 咨询师座机
    courseAdminId: 0, // 学管师ID
    courseAdmin: '', // 学管师姓名
    courseAdminPhone: '', // 学管师手机
    courseAdminLine: '', // 学管师座机
    classPlace: '', // 上课地点
    branchEmail: '', // 校区邮箱
    remark: '', // 申请档期-特殊说明
    studentRanking: '', // 学生考试排名
    studentCharacter: '', // 学生性格
    parentInfo: '', // 家长情况
    studentSituation: '', // 学生情况分析
    teacherSpecification: '', // 教师要求说明
    firstClassContent: '', //第一次课内容
    changeTeacherReasons: '', // 换老师原因
    workDirections: '', // 学管师工作方向
    workRemark: '', // 学管师工作备注
    teacherGroupId: 0, // 教研组组长ID
    teacherGroup: '', // 教研组组长姓名
    auditUserId: 0, // 审批人ID
    auditUser: '', // 审批人
    auditDate: '', // 审批时间
    auditStatus: 1, // 审批状态 1-审批通过， 0-审批不通过， 2-审核中， 3-未审核
    studentId: '', // 学生ID
    studentName: '', // 学生姓名
    branchId: 0, // 校区id
    gradeId: 0, // 年级ID
    gradeName: '', // 年级名称
    term: '', // 学期 1-上学期，2-下学期
    examType: '', // 考试类型
    status: 1, // 申请单状态 1-已提交，2-匹配中， 3-已匹配，4-已排课，0-已取消
    stuScoreList: [], // 学生成绩列表
    stuReqList: [], // 课程规划
    schedule: JSON.stringify($scope.stuSchedTimeList),
    stuSchedList: [] // 学生档期
  }
  /*************** End 双向绑定的Scope数据 ***************************/

  // 步骤之间的跳转 
  $scope.nextStep = function (from, to) {
    if (from == 1 && to == 2) {
      var waitingModal = $uibMsgbox.waiting('操作中，请稍候...')
      return erp_stuCourseSchedApplyYdyService.post({
        studentId: $scope.selectedStudent.id
      }).$promise.then(function (resp) {
        waitingModal.close()
        if (!resp.error) {
          _.assignInWith($scope.apply, resp.data, function (objValue, srcValue) {
            return _.isNil(srcValue) ? objValue : srcValue
          })
          $scope.apply.studentName = $scope.selectedStudent.student_name;
          $scope.apply.sexName = $scope.selectedStudent.sex == 1 ? '男' : '女';
          $scope.apply.schoolName = $scope.selectedStudent.attend_school_name;
          $scope.apply.stuGradeName = $scope.selectedStudent.grade_name;
          $scope.apply.beginDate = '';
          $scope.curStep = to
        } else {
          $uibMsgbox.alert(resp.message)
        }
      }).then(function () {
        waitingModal.close()
        erp_studentScoreService.queryDetail({
          studentId: $scope.selectedStudent.id
        }).$promise.then(function (resp) {})
      })
    }
    if (from == 2 && to == 1) {
      if ($scope.optype == 'edit') {
        return false
      }
      return $uibMsgbox.confirm('已经为学生【'+$scope.apply.studentName+'】创建了状态为【草稿】的申请单，返回上一步，将删除该申请单，确定返回？', function (res) {
        if (res == 'yes') {
          erp_stuCourseSchedApplyYdyService.delete({
            id: $scope.apply.id
          }, function (resp) {
            if (!resp.error) {
              $scope.curStep = 1
            } else {
              $uibMsgbox.error(resp.message)
            }
          })
        }
      })
    }
    if (from == 2 && to == 3) {
      return $scope.goToStep3()
    }

    if (from == 3 && to == 4) {
      var apply = getApply($scope.apply)
      if (!$scope.isApplyPlanValid(apply)) {
        return false;
      }
      apply.status = 1
      var waitingModal = $uibMsgbox.waiting('申请单保存中，请稍候...')
      return erp_stuCourseSchedApplyYdyService.put(apply).$promise
        .then(function (resp) {
          waitingModal.close()
          if (!resp.error) {
            $scope.curStep = 4
          } else {
            $uibMsgbox.error(resp.message)
          }
        }, function (resp) {
          waitingModal.close()
          $uibMsgbox.error('请求失败，请联系管理员！' + resp.message)
        })
    }
    $scope.curStep = to
  }
  $scope.isApplyPlanValid = function (apply) {
    
    console.log(JSON.parse(apply.schedule))
    var scheduleList = null
    if (apply.schedule) {
      var _scheduleList = JSON.parse(apply.schedule)
      scheduleList = []
      _.forEach(_scheduleList, function(item) {
        scheduleList.push(item.idles)
      })
      scheduleList = _.flatten(scheduleList)
      if (!_.includes(scheduleList, 'Y')) {
        $uibMsgbox.error('请选择学生申请档期！')
        return false
      }
    }
    if (apply.stuReqList=='') {
      $uibMsgbox.warn('请选择课程规划！')
      return false
    }
    if (!apply.beginDate) {
      $uibMsgbox.error('请输入开课日期！');
      return false;
    }
    if (!apply.classPlace) {
      $uibMsgbox.warn('请输入上课地点！')
      return false
    }
    if (!apply.schedule) {
      $uibMsgbox.warn('请选择学生申请档期！')
      return false
    }
    return true;
  }
  $scope.newApply = function () {
    $state.go('classesScheduleYdyApplyAdd', {}, {
      reload: true
    })
  }
  $scope.goBack = function () {
    $state.go('classesScheduleYdyApply')
  }
  // 进入第三步
  $scope.goToStep3 = function () {
    if (!$scope.apply.applyType) {
      $uibMsgbox.warn('请选择申请类型！')
      return false
    }
    // if (!$scope.apply.studentRanking) {
    //   $uibMsgbox.warn('请输入近期考试排名！')
    //   return false
    // }
    if ($scope.apply.stuScoreList=='') {
      $uibMsgbox.warn('请输入近期考试成绩！')
      return false
    }
    if ($scope.apply.stuScoreRankingList=='') {
      $uibMsgbox.warn('请输入综合排名信息！')
      return false
    }
    if (!$scope.apply.teacherSpecification) {
      $uibMsgbox.warn('请输入老师要求说明！')
      return false
    }
    // if (!$scope.apply.studentCharacter) {
    //   $uibMsgbox.warn('请输入学生性格！')
    //   return false
    // }
    // if (!$scope.apply.parentInfo) {
    //   $uibMsgbox.warn('请输入家长情况！')
    //   return false
    // }
    if (!$scope.apply.studentSituation) {
      $uibMsgbox.warn('请输入学生情况分析！')
      return false
    }

    if (!$scope.apply.workDirections) {
      $uibMsgbox.warn('请输入学管师工作方向！')
      return false
    }
    if ($scope.apply.applyType == '3' && !$scope.apply.changeTeacherReasons) {
      $uibMsgbox.warn('请输入换老师的原因！')
      return false
    }
    // if (!$scope.apply.workRemark) {
    //   $uibMsgbox.warn('请输入备注！')
    //   return false
    // }
    $scope.curStep = 3
    $scope.$broadcast('recalcReqSum');
    return true
    // erp_stuCourseSchedApplyYdyService.put(getApply($scope.apply), function (resp) {
    //   if (!resp.error) {
    //     $scope.curStep = 3
    //   } else {
    //     $uibMsgbox.error(resp.message)
    //   }
    // })
  }
  function getApply (apply) {
    var applyCopy =  _.pick(apply, [
      'id', 'applyType', 'beginDate', 'branchId', 'classPlace', 'branchEmail',
      'remark', 'studentRanking', 'studentCharacter', 'parentInfo', 'studentSituation',
      'teacherSpecification', 'firstClassContent', 'changeTeacherReasons', 'workDirections',
      'workRemark', 'teachGroupId', 'term', 'examType', 'gradeId', 'status', 'stuReqList',
      'schedule',
    ])
    return applyCopy
  }
  // 获取学员列表
  // TODO 后台需要增加剩余课时字段
  $scope.getStudentList = function (studentId) {
    var param = {
      pageSize: 10,
      row_num: 10,
      need_contact: '1',
      searchInfo: $scope.stuSearchInfo.searchInfo,
      searchType: 0
    }
    if(studentId) {
      param.studentId = studentId
    }
    erp_studentsService.query(param, function (resp) {
      if (!resp.error) {
        _.forEach(resp.data, function (stu) {
          if (studentId && stu.id == studentId) {
            $scope.selectedStudent = stu;
            $scope.selectedStudent.checked = true;
            $scope.apply.student = stu;
          } else {
            stu.checked = false
          }
        })
        $scope.studentList = resp.data;
      }else{
        $uibMsgbox.alert(resp.message);
      }
    })
  }
  // 学员选择
  $scope.onStudentCheckedChange = function (student) {
    if (!student.course_schedule_count) {
        return false;
    }
    student.checked = !student.checked
    if (student.checked) {
      $scope.selectedStudent = student
      $scope.apply.student = student
      $scope.stuGrade = {
        id: student.grade_id,
        grade_name: student.grade_name
      }

    } else {
      $scope.selectedStudent = {}
      $scope.apply.student = {}
      $scope.stuGrade = {}
    }
  }
  

  $scope.init = function () {
    if ($state.current.name == 'classesScheduleYdyApplyEdit') {
      if (!$stateParams.id) {
        $uibMsgbox.error('没有相关的申请单信息！', function () {
          $state.go('classesScheduleYdyApply')
        })
        return false
      }

      $scope.optype = 'edit'
      $scope.curStep = 2
      erp_stuCourseSchedApplyYdyService.getDetail({
        id: $stateParams.id
      }, function (resp) {
        if (!resp.error) {
          if (!resp.data) {
            return
          }
          _.assignInWith($scope.apply, resp.data, function (objValue, srcValue) {
            return _.isNil(srcValue) ? objValue : srcValue
          })
          if (resp.data.schedule) {
            $scope.stuSchedTimeList = JSON.parse(resp.data.schedule)
          }
          if (resp.data.stuReqList) {
            $scope.apply.stuReqList = _.sortBy(resp.data.stuReqList, 'seq')
          }
          $scope.getStudentList(resp.data.studentId)
        } else {
          $uibMsgbox.error(resp.message)
        }
      })
    } else {
      $scope.getStudentList($stateParams.studentId)
    }
  }
  $scope.init()
}