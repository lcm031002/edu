"use strict";
angular.module('ework-ui').controller('erp_NewTeacherController', [
    '$rootScope',
    '$scope',
    '$state',
    '$uibMsgbox',
    'erp_employeeService',
    'erp_studentOrgService',
    'erp_TeacherIndexService',
    'erp_subjectService',
    erp_NewTeacherController
]);

function erp_NewTeacherController(
    $rootScope,
    $scope,
    $state,
    $uibMsgbox,
    erp_employeeService,
    erp_studentOrgService,
    erp_TeacherIndexService,
    erp_subjectService
) {

    $scope.newTeacher = {
        status: 1,
        sex: '1',
        is_pluralistic: 4,
        teacher_type: 0
    };

    $scope.statusList = [];
    $scope.genderList = [];
    $scope.partTimeList = [];
    $scope.teacherTypeList = [];
    //$scope.cityList = [];
    $scope.buList = [];
    $scope.subjectList = [];
    $scope.selSubjectList = [];
    $scope.subjectSearchInfo = '';
    $scope.selSubjectList = [];
    $scope.selTeamList = [];
    $scope.teamSearchInfo = '';
    $scope.selTeamList = [];

    // 选中关联员工输入框，清空输入框内容
    $scope.onEmployeeNameFocus = function() {
        if ($scope.newTeacher.employee_name == '请输入员工姓名') {
            $scope.newTeacher.employee_name = '';
        }
        $scope.inputing = true;
    };

    // 鼠标离开关联员工控件处理事件
    $scope.onEmployeeNameBlur = function() {
        if (!$scope.newTeacher.employee_name) {
            $scope.newTeacher.employee_name = '请输入员工姓名';
        }
        $scope.inputing = false;
    };


    // 关联员工输入框，名称变化则重新查询员工信息
    $scope.onEmployeeNameChange = function() {
        $scope.isDown = 'loading';
        $scope.searchResult = [];
        erp_employeeService.query({
                row_num: 10,
                currentPage: 1,
                pageSize: 10,
                employee_name: $scope.newTeacher.employee_name
            },
            function(resp) {
                $scope.isDown = '';
                if (!resp.error) {
                    $scope.searchResult = resp.data;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    }

    $scope.changeBu = function(){
        $scope.selSubjectList = [];
        erp_subjectService.querySelectDatas({
            bu_id:$scope.newTeacher.bu_id
        },function(resp){
            if(!resp.error){
                $scope.subjectList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    };
    // 从转入学员控件的查询结果中选择一条数据触发该事件
    $scope.selectEmployee = function(employee) {
        console.log(employee)
        $scope.newTeacher.employee_id = employee.id;
        $scope.newTeacher.teacher_name
            = $scope.newTeacher.nickname
            = $scope.newTeacher.employee_name
            = employee.employee_name;

        $scope.searchResult = [];
    };

    $scope.checkBeforeSave = function() {
        //if (!$scope.newTeacher.employee_name||$scope.newTeacher.employee_name == '请输入员工姓名') {
        //    $uibMsgbox.error('关联员工必填');
        //    return false;
       // }

        if (!$scope.newTeacher.encoding) {
            $uibMsgbox.error('教师编码必填');
            return false;
        }

        if (!$scope.newTeacher.teacher_name) {
            $uibMsgbox.error('教师姓名必填');
            return false;
        }

        if (!$scope.newTeacher.teacher_type) {
            $uibMsgbox.error('教师身份必填');
            return false;
        }

        if (!$scope.newTeacher.status) {
            $uibMsgbox.error('教师状态必填');
            return false;
        }

        if (!$scope.newTeacher.is_pluralistic  != 0 && $scope.newTeacher.is_pluralistic  != 1) {
            $uibMsgbox.error('是否兼职必填');
            return false;
        }

        if (!$scope.newTeacher.sex) {
            $uibMsgbox.error('教师性别必填');
            return false;
        }

        if (!$scope.newTeacher.phone) {
            $uibMsgbox.error('教师联系方式必填');
            return false;
        }

        if ($scope.newTeacher.email && !(Validator.email.test($scope.newTeacher.email))) {
            $uibMsgbox.error('邮箱格式不正确');
            return false;
        }

        if ($scope.selSubjectList.length <= 0) {
            $uibMsgbox.error('科目必填');
            return false;
        }

        if($scope.selTeamList<=0){
            $uibMsgbox.error('团队必填');
            return false;
        }
        return true;
    }

    $scope.removeSubject = function (subject) {
        subject.checked = false;
        _.remove($scope.selSubjectList, subject);
    }

    $scope.removeTeam = function (team) {
        team.checked = false;
        _.remove($scope.selTeamList, team);
    }

    $scope.handleSubjectChange = function (subject) {
        if (subject.checked && !_.some($scope.selSubjectList, subject)) {
            $scope.selSubjectList.push(subject)
        } else {
            _.remove($scope.selSubjectList, subject);
        }
    }


    $scope.handleTeamChange = function (team) {
        if (team.checked && !_.some($scope.selTeamList, team)) {
            $scope.selTeamList.push(team)
        } else {
            _.remove($scope.selTeamList, team);
        }
    }

    $scope.clearAll = function(){
        $state.reload();
    }

    $scope.saveTeacher = function() {
        if ($scope.checkBeforeSave()) {
            var teacherForSave = {};
            angular.copy($scope.newTeacher, teacherForSave);
            var subject = teacherForSave.subject = [];
            _.forEach($scope.selSubjectList, function(item) {
                teacherForSave.subject.push(item.id)
            })
            if (subject) {
                teacherForSave.subject = subject.join(",");
            }
            var team = teacherForSave.team = [];
            _.forEach($scope.selTeamList, function(item) {
                teacherForSave.team.push(item.buId)
            })
            if (team) {
                teacherForSave.team = team.join(",");
            }

            erp_TeacherIndexService.post(teacherForSave, function(resp) {
                if (!resp.error) {
                    $uibMsgbox.success('教师添加成功');
                    // $scope.newTeacher = {};
                    $scope.clearAll();
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    };

  $scope.initialize = function() {
    erp_TeacherIndexService.toManage({}, function(resp) {
      if (!resp.error) {
        $scope.teacherTypeList = resp.teacherType;
        $scope.statusList = resp.teacherStatus;
        $scope.genderList = resp.gender;
        $scope.partTimeList = resp.isPartTime;
        //$scope.cityList = resp.cityList;
        $scope.buList =  resp.buList;
        $scope.subjectList =  resp.subjectList;
        //$scope.newTeacher.city_id = resp.city_id;
        // $scope.buList = $.map(resp.buList,function(n,i) {
        //     if(n.parent_id == resp.city_id) return n;
        // });
        $scope.newTeacher.bu_id = resp.bu_id;
        $scope.newTeacher.teacher_type = resp.teacherType[3].code;
        $scope.newTeacher.status = resp.teacherStatus[1].code;
        $scope.newTeacher.is_pluralistic = resp.isPartTime[1].code;
      }
    });
  }

  $scope.initialize();
}
