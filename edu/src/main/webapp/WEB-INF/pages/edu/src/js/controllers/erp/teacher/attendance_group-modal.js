angular.module('ework-ui')
  .controller('erp_attendTeacherGroupModalController', [
    '$scope',
    '$uibModalInstance',
    '$uibMsgbox',
    'optype',
    'branchList',
    'attendTeacherGroup',
    erp_attendTeacherGroupModalController
  ])

function erp_attendTeacherGroupModalController(
  $scope,
  $uibModalInstance,
  $uibMsgbox,
  optype,
  branchList,
  attendTeacherGroup
) {

  $scope.modalSearchParam = {
    status:'1',
    bussinessType:'2'
  };
  $scope.attendTeacherGroup = attendTeacherGroup;
  $scope.optype = optype;
  $scope.branchList = branchList;
  $scope.showLeaderSearchModal = false;
  $scope.leaderList = [];
  $scope.teacherList = [];

  //初始化teacherList
  if(attendTeacherGroup.groupTeacherList && attendTeacherGroup.groupTeacherList.length) {
    $scope.teacherList = $.map(attendTeacherGroup.groupTeacherList,function(n) {
      return n.teacher;
    })
  }
  if(!$scope.attendTeacherGroup.branch_id) {
    $scope.attendTeacherGroup.branch_id = $scope.branchList[0].id
  }

  $scope.insertTeacher = function(teacher) {
    var idx = _.findIndex($scope.teacherList, function(o) {
      return o.id == teacher.id;
    })
    if (idx == -1) {
      $scope.teacherList.push(teacher);
    }
    // console.log($scope.teacherList);
  }

  $scope.removeTeacher = function(teacher) {
    _.remove($scope.teacherList, function(o) {
      return o.id == teacher.id;
    });
  }

  $scope.removeLeader = function(teacher) {
    _.remove($scope.leaderList, function(o) {
      return o.id == teacher.id;
    });
  }

  $scope.ok = function() {
    $uibMsgbox.confirm('确认保存？', function(res) {
      if (res == 'yes') {
        if ($scope.teacherList && $scope.teacherList.length > 0) {
          var teacher_id_arr = [];
          teacher_id_arr = $.map($scope.teacherList,function(n) {
            return n.id;
          });
          $scope.attendTeacherGroup.teacher_ids = teacher_id_arr.join(',');
          console.log($scope.attendTeacherGroup.teacher_ids);
        }
        $uibModalInstance.close($scope.attendTeacherGroup);
      }
    });
  }
}
