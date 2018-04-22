"use strict";
angular.module('ework-ui').controller('erp_studentCenterCounselorController', [
  '$scope',
  '$log',
  '$uibModal',
  '$uibMsgbox',
  'erp_studentCounselorService',
  'erp_employeeMgrService',
  'erp_studentsService',
  erp_studentCenterCounselorController
]);

function erp_studentCenterCounselorController(
  $scope,
  $log,
  $uibModal,
  $uibMsgbox,
  erp_studentCounselorService,
  erp_employeeMgrService,
  erp_studentsService
) {
  // 学员ID
  $scope.studentId = $("#rootIndex_studentId").val();
  // 页面模块显示控制
  $scope.viewCtrl = {
    showEditCounselor: false,
    showEditGovern: false
  }
  // 学员信息
  $scope.student = {}
  // 当前咨询师
  $scope.counselor = {
    start_date: '',
    end_date: ''
  }
  // 暂存的咨询师数据
  $scope.counselorCopy = {}
  // 历任咨询师列表
  $scope.historyCounselorList = []
  // 当前学管师
  $scope.govern = {
    start_date: '',
    end_date: ''
  }
  // 暂存的学管师数据
  $scope.governCopy = {}
  // 历任学管师列表
  $scope.historyGovernList = []
  
  // 编辑咨询师
  $scope.showEditCounselor = function () {
    angular.copy($scope.counselor, $scope.counselorCopy);
    $scope.viewCtrl.showEditCounselor = true;
  }

  // 显示历任咨询师
  $scope.showHistoryCounselor = function () {
    openHistoryCounselorModal({
      title: '历任咨询师',
      type: 1,
      studentId: $scope.studentId
    })
  }

  // 取消咨询师修改
  $scope.cancelCounselorEdit = function () {
    angular.copy($scope.counselor, $scope.counselorCopy);
    $scope.viewCtrl.showEditCounselor = false;
  }

  // 保存咨询师修改
  $scope.saveCounselor = function () {
    var start_date = $scope.counselorCopy.start_date;
    var end_date = $scope.counselorCopy.end_date;

    if (isEmpty($scope.counselorCopy.name) || isEmpty($scope.counselorCopy.counselor_id) || isEmpty($scope.counselorCopy.encoding)) {
      $uibMsgbox.alert("咨询师不能为空");
      return;
    }
    if (isEmpty(start_date) || isEmpty(end_date) || !checkStartEndTime(start_date, end_date)) {
      $uibMsgbox.alert("开始日期和结束日期错误");
      return;
    }
    var counselorInfo = {};
    counselorInfo.id = $scope.counselor.id;
    counselorInfo.student_id = $scope.studentId;
    counselorInfo.counselor_type = 1;
    counselorInfo.is_valid = 1;
    counselorInfo.start_date = start_date;
    counselorInfo.end_date = end_date;
    counselorInfo.counselor_id = $scope.counselorCopy.counselor_id;
    counselorInfo.encoding = $scope.counselorCopy.encoding;
    counselorInfo.name = $scope.counselorCopy.name;
    counselorInfo.remark = $scope.counselorCopy.remark;

    var waitModal = $uibMsgbox.waiting('保存中，请稍候...')
    erp_studentCounselorService.post(counselorInfo).$promise.then(function(resp) {
      waitModal.close();
      if (!resp.error) {
        $uibMsgbox.alert("添加成功！");
        angular.copy($scope.counselorCopy, $scope.counselor)
        $scope.viewCtrl.showEditCounselor = false;
        getCounselor($scope.student.id);
      } else {
        $uibMsgbox.alert(resp.message);
      }
    }, function(resp) {
      waitModal.close();
      console.error(resp);
      $uibMsgbox.error('请求失败，请联系管理员！');
    })
    //if(counselorInfo.id && counselorInfo.counselor_id == $scope.counselor.counselor_id) {
    //  erp_studentCounselorService.put(counselorInfo, function(resp) {
    //    if (!resp.error) {
    //      $uibMsgbox.alert("修改成功！");
    //      angular.copy($scope.counselorCopy, $scope.counselor)
    //      $scope.viewCtrl.showEditCounselor = false;
    //      getCounselor($scope.student.id);
    //    } else {
    //      $uibMsgbox.alert(resp.message);
    //    }
    //  })
    //} else {
    //  erp_studentCounselorService.post(counselorInfo, function(resp) {
    //    if (!resp.error) {
    //      $uibMsgbox.alert("修改成功！");
    //      angular.copy($scope.counselorCopy, $scope.counselor)
    //      $scope.viewCtrl.showEditCounselor = false;
    //      getCounselor($scope.student.id);
    //    } else {
    //      $uibMsgbox.alert(resp.message);
    //    }
    //  })
    //}


  }

  // 显示修改学管师对话框
  $scope.showEditGovern = function () {
    angular.copy($scope.govern, $scope.governCopy)
    $scope.viewCtrl.showEditGovern = true;
  }

  // 显示历任学管师
  $scope.showHistoryGovern = function () {
    openHistoryCounselorModal({
      title: '历任学管师',
      type: 2,
      studentId: $scope.studentId
    })
  }

  // 取消学管师修改
  $scope.cancelGovernEdit = function () {
    angular.copy($scope.govern, $scope.governCopy);
    $scope.viewCtrl.showEditGovern = false;
  }

  // 保存学管师修改
  $scope.saveGovern = function () {
    var start_date = $scope.governCopy.start_date;
    var end_date = $scope.governCopy.end_date;
    
    if (isEmpty($scope.governCopy.name) 
      || isEmpty($scope.governCopy.counselor_id) 
      || isEmpty($scope.governCopy.encoding)) {
      $uibMsgbox.alert("学管师不能为空");
      return;
    }
    
    if (isEmpty(start_date) 
      || isEmpty(end_date) 
      || !checkStartEndTime(start_date, end_date)) {
      $uibMsgbox.alert("开始日期和结束日期错误");
      return;
    }
    var governInfo = {};
    governInfo.id = $scope.govern.id;
    governInfo.student_id = $scope.studentId;
    governInfo.counselor_type = 2;
    governInfo.is_valid = 1;
    governInfo.start_date = start_date;
    governInfo.end_date = end_date;
    /*$scope.counselor中ed_开头字段装载入governInfo*/
    governInfo.counselor_id = $scope.governCopy.counselor_id;
    governInfo.encoding = $scope.governCopy.encoding;
    governInfo.name = $scope.governCopy.name;
    governInfo.remark = $scope.governCopy.remark;

    var waitModal = $uibMsgbox.waiting('保存中，请稍候...')
    erp_studentCounselorService.post(governInfo)
      .$promise.then(function(resp) {
        waitModal.close();
        if (!resp.error) {
          $uibMsgbox.alert("添加成功！");
          $scope.govern_action = 1;
          angular.copy($scope.governCopy, $scope.govern)
          $scope.viewCtrl.showEditGovern = false;
          getGovern($scope.student.id);
        } else {
          $uibMsgbox.error(resp.message);
        }
      }, function () {
        waitModal.close()
      })

    //if(governInfo.id && governInfo.counselor_id == $scope.govern.counselor_id) {
    //  erp_studentCounselorService.put(governInfo, function(resp) {//添加
    //    if (!resp.error) {
    //      $uibMsgbox.alert("修改成功！");
    //      $scope.govern_action = 1;
    //      angular.copy($scope.governCopy, $scope.govern)
    //      $scope.viewCtrl.showEditGovern = false;
    //      getGovern($scope.student.id);
    //    } else {
    //      alert(resp.message);
    //    }
    //  });
    //} else {
    //  erp_studentCounselorService.post(governInfo, function(resp) {
    //    if (!resp.error) {
    //      $uibMsgbox.alert("修改成功！");
    //      $scope.govern_action = 1;
    //      angular.copy($scope.governCopy, $scope.govern)
    //      $scope.viewCtrl.showEditGovern = false;
    //      getGovern($scope.student.id);
    //    } else {
    //      alert(resp.message);
    //    }
    //  });
    //}
  }

  $scope.selectCounselor = function() {
    $scope.openTeachersModal('counselor').result.then(function(teacher) {
      var counselor = $scope.counselorCopy;
      counselor.counselor_id = teacher.id;
      counselor.name = teacher.employee_name;
      counselor.encoding = teacher.encoding;
    }, function() {})
  }

  $scope.selectGovern = function() {
    $scope.openTeachersModal('gover').result.then(function(teacher) {
      var govern = $scope.governCopy;
      govern.counselor_id = teacher.id;
      govern.name = teacher.employee_name;
      govern.encoding = teacher.encoding;
    }, function() {})
  }

  $scope.openTeachersModal = function(type) {
    var counselorType = type == 'counselor' ? 1 : 2;
    var modalTitle = (type == 'counselor' ? '咨询师' : '学管师') + '列表';
    return $uibModal.open({
      size: 'lg',
      templateUrl: 'templates/block/modal/employee-list.modal.html',
      controller: 'erp_employeeListModalController',
      resolve: {
        counselorType: function() {
          return counselorType;
        },
        modalTitle: function() {
          return modalTitle;
        }
      }
    })
  }

  init();

  // 界面初始化
  function init() {
    getStudentInfo().then(function(stu){
      if (stu) {
        initStudent(stu)
        getCounselor(stu.id);
        getGovern(stu.id)
      }
    })
  }
  
  //  学管师的结束时间在开始时间上加两年
  $scope.$watch("governCopy.start_date",function(){
	  var date=new Date($scope.governCopy.start_date);
	  date.setFullYear(date.getFullYear() + 2);
	  date.setDate(date.getDate()-1);
	  $scope.governCopy.end_date=date.format("yyyy-MM-dd", date);
  })
  
  //  咨询师的结束时间在开始时间上加两年
    $scope.$watch("counselorCopy.start_date",function(){
	  var date=new Date($scope.counselorCopy.start_date);
	  date.setFullYear(date.getFullYear() + 2);
	  date.setDate(date.getDate()-1);
	  $scope.counselorCopy.end_date=date.format("yyyy-MM-dd", date);
  })

  // 获取学生信息
  function getStudentInfo(stuId) {
    return erp_studentsService.query({studentId: $scope.studentId}).$promise
      .then(function(resp){
        if (!resp.error && resp.data.length) {
          return resp.data[0];
        } else {
          $uibMsgbox.alert(resp.message);
          return false;
        }
      })
  }

  // 初始化学生信息
  function initStudent(stu) {
    $scope.student = stu;
    $('title').text('学员|' + $scope.student.student_name); 
  }

  // 获取当前咨询师
  function getCounselor(stuId) {
    getStuCurCounselor(stuId, 1).then(function(resp) {
      $scope.counselor = resp || {};
      $scope.viewCtrl.showEditCounselor = !resp;
    })
  }

  // 获取当前学管师
  function getGovern(stuId) {
    getStuCurCounselor(stuId, 2).then(function(resp) {
      $scope.govern = resp || {};
      $scope.viewCtrl.showEditGovern = !resp;
    })
  }

  // 获取当前咨询师/学管师
  function getStuCurCounselor(stuId, counselorType) {
    return erp_studentCounselorService.query({
      student_id: stuId,
      counselor_type: counselorType,
      cur_date: moment().format('YYYY-MM-DD')
    }).$promise.then(function(resp) {
      if (!resp.error && resp.data && resp.data.length) {
        return resp.data[0]
      } else {
        return null
      }
    })
  }

  function modalController (scope) {
    console.log(arguments)
  }

  function openHistoryCounselorModal(conf) {
    return $uibModal.open({
      templateUrl: 'employeeList.html',
      size: 'lg',
      resolve: {
        conf: function () {
          return conf
        }
      },
      controller: [
        '$scope',
        'erp_employeeMgrService',
        'conf',
        function (scope, erp_employeeMgrService, conf) {
          scope.pageConf = {
            itemsPerPage: 10,
            totalItems: 0,
            currentPage: 1,
            onChange: function() {
              query();
            }
          }
          scope.employeeList = [];
          scope.conf = conf;
          function query () {
            erp_studentCounselorService.query({
              status: 1,
              pageSize: scope.pageConf.itemsPerPage,
              currentPage: scope.pageConf.currentPage,
              student_id: conf.studentId,
              counselor_type: conf.type
            }).$promise.then(function(resp) {
              if (!resp.error && resp.data && resp.data.length) {
                scope.employeeList = resp.data;
                scope.pageConf.totalItems = resp.total;
              } else {
                scope.employeeList = []
              }
            })
          } 
          query();
        }]
    })
  }
}
