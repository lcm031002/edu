"use strict";

angular.module('ework-ui').controller('erp_studentDistributeController', [
  '$rootScope',
  '$scope',
  '$log',
  '$uibMsgbox',
  'erp_stuCounselorDistService',
  erp_studentDistributeController
]);

function erp_studentDistributeController(
  $rootScope,
  $scope,
  $log,
  $uibMsgbox,
  erp_stuCounselorDistService
) {
  $scope.resArray = []
  $scope.counselorFrom = []
  $scope.counselorTo = []
  $scope.students = []
  $scope.chosenCounselorToId = null
  $scope.chosenCounselorToName = ''
  $scope.searchCounselorFrom = ''
  $scope.searchCounselorTo = ''

  $scope.fromPageConf = {
    currentPage: 1, //当前页
    totalItems: 0,
    itemsPerPage: 10,
    showInfos: false,
    onChange: function() {
      $scope.getCounselorFrom()
    }
  };

  $scope.toPageConf = {
    currentPage: 1,
    totalItems: 0,
    itemsPerPage: 10,
    showInfos: false,
    onChange: function() {
      $scope.getCounselorTo()
    }
  }
  
  // 获取可转移出去的学管师
  $scope.queryCounselorFrom = function(counselor) {
    $scope.loadStatues = true;
    erp_stuCounselorDistService.queryDistributeFrom({
      currentPage: 1,
      pageSize: $scope.fromPageConf.itemsPerPage,
      p_emp_name: $scope.searchCounselorFrom
    }, function(resp) {
      $scope.loadStatues = false;
      if (!resp.error) {
        $scope.fromPageConf.totalItems = resp.total
        $scope.counselorFrom = resp.data
        initResArray()
      } else {
        $uibMsgbox.error(resp.message)
      }
    })
  }

  // 获取可转移出去的学管师
  $scope.getCounselorFrom = function(counselor) {
    $scope.loadStatues = true;
    erp_stuCounselorDistService.queryDistributeFrom({
      currentPage: $scope.fromPageConf.currentPage,
      pageSize: $scope.fromPageConf.itemsPerPage,
      p_emp_name: $scope.searchCounselorFrom
    }, function(resp) {
      $scope.loadStatues = false;
      if (!resp.error) {
        $scope.fromPageConf.totalItems = resp.total
        $scope.counselorFrom = resp.data
        initResArray()
      } else {
        $uibMsgbox.error(resp.message)
      }
    })
  }

  // 获取可转移进来的学管师
  $scope.getCounselorTo = function(counselor) {
    $scope.loadSta = true;
    erp_stuCounselorDistService.queryDistributeTo({
      currentPage: $scope.toPageConf.currentPage,
      pageSize: $scope.toPageConf.itemsPerPage,
      p_emp_name: $scope.searchCounselorTo
    }, function(resp) {
      $scope.loadSta = false;
      $scope.toPageConf.totalItems = resp.total
      $scope.counselorTo = resp.data
    })
  }

  // 显示或隐藏老师底下的学生
  $scope.toggleRes = function(item) {
    if (item.isStu) {
      return
    }
    item.showStu = !item.showStu
    if (item.showStu) {
      if (item.students) {
        $scope.showStudents(item)
      } else {
        $scope.getStudents(item)
      }
    } else {
      $scope.hideStudents()
    }

  }

  // 隐藏学生
  $scope.hideStudents = function() {
    for (var i = $scope.resArray.length - 1; i >= 0; i--) {
      if ($scope.resArray[i].showStu) {
        $scope.resArray[i].showStu = false
      }
      if($scope.resArray[i].isStu) {
        console.log($scope.resArray[i])
        $scope.resArray.splice(i, 1)
      }
    }
  }

  // 获取学生
  $scope.getStudents = function (counselor, page) {
    var modalInstance = $uibMsgbox.waiting('数据加载中，请稍候...')
    page = page || 1
    erp_stuCounselorDistService.query({
      currentPage: page,
      pageSize: 999, // TODO 优化页面加载，显示更多按钮
      p_counselor_id: counselor.ID
    }, function(resp){
      modalInstance.close();
      if (!counselor.students || !counselor.students.concat) {
        counselor.students = []
      } 
      if (!resp.error) {
        for (var i = 0; i < resp.data.length; i++) {
          resp.data[i].isStu = true
          counselor.students.push(resp.data[i])
        }
        $scope.showStudents(counselor)
      } else {
        $uibMsgbox.error(resp.message)
      }
    })
  }

  // 显示学生
  $scope.showStudents =function(counselor) {
    $scope.resArray.splice(0, $scope.resArray.length)
    for (var i = 0; i < $scope.counselorFrom.length; i++) {
      $scope.resArray.push($scope.counselorFrom[i])
      if ($scope.counselorFrom[i].emp_id == counselor.emp_id) {
        $scope.resArray = $scope.resArray.concat(counselor.students)
      } else {
        $scope.counselorFrom[i].showStu = false
      }
    }
  }

  // 初始化老师
  function initResArray() {
    $scope.resArray.splice(0, $scope.resArray.length)
    for (var i = 0; i < $scope.counselorFrom.length; i++) {
      $scope.counselorFrom[i].isStu = false
      $scope.counselorFrom[i].showStu = false
      $scope.resArray.push($scope.counselorFrom[i])
    }
  }

  // 选择要转入的老师
  $scope.chooseCounselorTo = function(counselor) {
    $scope.chosenCounselorToId = counselor.emp_id
    $scope.chosenCounselorToName = counselor.emp_name
  }

  // 批量转移学员
  $scope.transferAll = function (counselor) {
    if (!$scope.chosenCounselorToId) {
      $uibMsgbox.warn('请选择要转入的老师！')
      return
    }
    $uibMsgbox.confirm('确定要将<span class="text-danger">【' + counselor.emp_name 
      + '】</span>老师的所有学员分配给<span class="text-danger">【'+ $scope.chosenCounselorToName +'】</span>老师?', function (res) {
      if (res == 'yes') {
        var _modalInstance = $uibMsgbox.waiting('转移中，请稍候...')
        erp_stuCounselorDistService.distributeBatch({
          p_from_conselor_id: '' + counselor.emp_id,
          p_to_conselor_id: '' + $scope.chosenCounselorToId
        }, function (resp) {
          _modalInstance.close()
          if (!resp.error) {
            $uibMsgbox.success('转移成功！')
            $scope.getCounselorFrom()
          } else {
            $uibMsgbox.error(resp.message)
          }
        }) 
      }
    })
  }

  // 转移单个学员
  $scope.transfer = function (student) {
    if (!$scope.chosenCounselorToId) {
      $uibMsgbox.warn('请选择要转入的老师！')
      return
    }
    $uibMsgbox.confirm('确定要将学员<span class="text-danger">【' + student.student_name 
      + '，学号：' + student.encoding + '】</span>分配给<span class="text-danger">【'+ $scope.chosenCounselorToName +'】</span>老师?', function (res) {
      if (res == 'yes') {
        var _modalInstance = $uibMsgbox.waiting('转移中，请稍候...')
        erp_stuCounselorDistService.post({
          new_counselor_id: '' + $scope.chosenCounselorToId,
          stu_counselor_id: '' + student.stu_counselor_id
        }, function (resp) {
          _modalInstance.close();
          if (!resp.error) {
            $uibMsgbox.success('转移成功！')
            $scope.getCounselorFrom()
          } else {
            $uibMsgbox.error(resp.message)
          }
        }) 
      }
    })
  }

  $scope.getCounselorFrom()
  $scope.getCounselorTo()
}
