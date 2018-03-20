"use strict";
angular.module('ework-ui').controller('erp_TeacherSearchController', [
  '$scope',
  '$uibMsgbox',
  '$uibModal',
  '$log',
  'PUBORGService',
  'erp_organizationService',
  'erp_TeacherListService',
   erp_TeacherSearchController
]);
  function erp_TeacherSearchController($scope, $uibMsgbox, $uibModal, $log,PUBORGService,erp_organizationService,
    erp_TeacherListService) {
    $scope.teacherList = [];
    // 批量选中标识
    $scope.selectAllFlag = false;
    $scope.searchParam={};
    $scope.searchParam.p_bu_id = -1;
    $scope.paginationConf = {
      currentPage: 1, // 当前页
      totalItems: 0,
      itemsPerPage: 10,
      onChange: function() {
        $scope.queryTeacher();
      }
    };
    $scope.buList = [];

    $scope.queryTeacher = function() {
      var _uibModalInstance = $uibMsgbox.waiting('加载中，请稍候...');
      $scope.teacherList = [];
      erp_TeacherListService
        .query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            search_info: $scope.teacher_name,
            org_id : $scope.searchParam.p_bu_id
          },
          function(resp) {
            if (!resp.error) {
              _uibModalInstance.close();
              $scope.paginationConf.totalItems = resp.total || 0; // 设置总条数
              $scope.teacherList = resp.data;
            } else {
              $uibMsgbox
                .alert(resp.message);
            }
            $scope.selectAllFlag = false;
          });
    };
    // 多选
    $scope.$watch(
      'selectAllFlag',
      function(newValue, oldValue) {
        var i;
        if (newValue == true) {

          for (i in $scope.teacherList) {

            $scope.teacherList[i].selectFlag = true;
          }
        } else {

          for (i in $scope.teacherList) {

            $scope.teacherList[i].selectFlag = false;
          }
        }
      });
      // 获取所有选中的id
    $scope.getSelectedIds = function() {
      var selectFlag = null;
      var i = null;
      var syncTeacherIds = "";
      for (i in $scope.teacherList) {
        selectFlag = $scope.teacherList[i].selectFlag;
        if (selectFlag) {
          // 拼接需要同步到叮当的教师id
          syncTeacherIds += ("," + $scope.teacherList[i].id);
        }
      }
      return syncTeacherIds.substring(1);
    };
    // 获取所有选中的id和教师姓名
    $scope.getSelectedIdAndTeacherInfos = function() {
        var selectFlag = null;
        var i = null;
        var syncTeacherIdAndTeacherInfos = "";
        for (i in $scope.teacherList) {
          selectFlag = $scope.teacherList[i].selectFlag;
          if (selectFlag) {
            // 拼接需要同步到叮当的教师id
            syncTeacherIdAndTeacherInfos += ("," + $scope.teacherList[i].id + "&" + $scope.teacherList[i].teacher_name + "@" + $scope.teacherList[i].encoding);
          }
        }
        return syncTeacherIdAndTeacherInfos.substring(1);
      }
      // 同步到叮当
    $scope.syncDingDang = function() {
        var syncTeacherIdAndTeacherInfos = $scope.getSelectedIdAndTeacherInfos();
        // 同步到叮当
        if (syncTeacherIdAndTeacherInfos) {
          erp_TeacherListService.synToDoubleCourse({
            teacherIdAndTeacherInfos: syncTeacherIdAndTeacherInfos
          }, function(resp) {
            // 刷新数据
            $scope.queryTeacher();
            if (!resp.error) {
              if (resp.fail) {
                $uibMsgbox.error("同步失败的教师有：" + resp.fail);
              } else {
                $uibMsgbox.alert("同步成功");
              }
            } else {
              $uibMsgbox.alert(resp.message);
            }
          });
        } else {
          $uibMsgbox.warn("未选中教师");
        }
      }
      // 推送工作平台邀请码
    $scope.sendInvation = function() {
      var invationTeacherIds = $scope.getSelectedIds();
      // 开始推送工作平台邀请码
      if (invationTeacherIds) {
        erp_TeacherListService.sendInvation({
          teacherIds: invationTeacherIds
        }, function(resp) {
          if (!resp.error) {
            // 刷新数据
            $scope.queryTeacher();
            if (resp.fail) {
              $uibMsgbox.error("推送失败的教师有：" + resp.fail);
            } else {
              $uibMsgbox.success("全部推送成功");
            }
          } else {
            $uibMsgbox.alert(resp.message);
          }
        });
      } else {
        $uibMsgbox.warn("未选中教师");
      }
    };
    // 改变上架下架状态
    //$scope.changeStatus = function(teacher) {
    //  var options = (teacher.status == 1 ? "enable" : "disable");
    //  teacher.status = teacher.status == 1 ? 2 : 1;
    //  $uibMsgbox.confirm('确认改变状态？', function(res) {
    //    if (res == 'yes') {
    //      erp_TeacherListService.changeStatus({
    //        teacherIds: teacher.id,
    //        status: options
    //      }, function(resp) {
    //        if (!resp.error) {
    //          $uibMsgbox.success("操作成功");
    //          $scope.queryTeacher();
    //        } else {
    //          $uibMsgbox.alert(resp.message);
    //        }
    //      });
    //    }
    //  });
    //};
    // 弹出上传头像框
    $scope.showUploadModal = function(teacher) {
      teacher.photo = teacher.photo || '';
      var modalInstance = $uibModal
        .open({
          backdrop: 'static',
          templateUrl: 'templates/block/avatar-upload.html',
          controller: 'blocks_avatarUploadController',
          resolve: {
            onUploadImg: function() {
              return function(result, $uibModalInstance) {
                erp_TeacherListService.uploadImg({
                  teacherId: teacher.id.toString(),
                  oldPhoto: teacher.photo.toString(),
                  photoBase64: result
                }, function(resp) {
                  if (!resp.error) {
                    $uibModalInstance.close();
                    $scope.queryTeacher();
                  } else {
                    $uibMsgbox.alert(resp.message);
                  }
                });
              }
            }
          }
        });
      modalInstance.result.then(function(result) {

        if (result) {
          $scope.queryTeacher();
        }
      }, function() {
        $log.info('DrawModal dismissed at: ' + new Date());
      })
    };

    // 修改教师弹出框
    $scope.shopUpdateModal = function(teacher) {
      var updateTeacher = {};
      angular.copy(teacher, updateTeacher);
      var modalInstance = $uibModal
        .open({
          size: 'xlg',
          backdrop: 'static',
          templateUrl: 'updateTeacherModalInstance.html',
          controller: 'updateTeacherModalInstanceController',
          resolve: {
            // 目标参数获取
            // $scope.$resolve.changeNo
            params: function() {
              return {
                teacher: updateTeacher
              }
            }
          }
        });
      modalInstance.result.then(function(result) {
        $scope.queryTeacher();
        $uibMsgbox.alert("操作成功");
      }, function(reason) {
        $log.info('DrawModal dismissed at: ' + new Date());
      });
    }
    $scope.initPage = function() {
          PUBORGService.queryBu({}, function(resp) {
              if (!resp.error) {
                  $scope.buList = resp.data;
                  $scope.buList.unshift({
                    buId: -1,
                    text: "-- 全部 --"
                  });
              }
          });

      }
    $scope.initPage();
    $scope.queryTeacher();
  }


// 修改教师信息
angular.module('ework-ui').controller(
  'updateTeacherModalInstanceController', ['$rootScope', '$scope', '$state', '$uibMsgbox',
    'erp_TeacherIndexService', 'erp_employeeService',
    'erp_studentOrgService', 'erp_TeacherListService','erp_subjectService',
    '$uibModalInstance', 'params',
    updateTeacherModalInstanceController
  ]);

function updateTeacherModalInstanceController($rootScope, $scope, $state,
  $uibMsgbox, erp_TeacherIndexService, erp_employeeService,
  erp_studentOrgService, erp_TeacherListService,erp_subjectService, $uibModalInstance,
  params) {

  $scope.statusList = [];
  $scope.genderList = [];
  $scope.partTimeList = [];
  $scope.teacherTypeList = [];
  //$scope.cityList = [];
  $scope.buList = [];
  $scope.subjectList = [];
  $scope.selSubjectList = [];
  $scope.subjectSearchInfo = '';
  $scope.selTeamList = [];
  $scope.updateTeacher = {
    id: params.teacher.id,
    employee_id: params.teacher.employee_id,
    employee_name: params.teacher.employee_name,
    encoding: params.teacher.encoding,
    teacher_name: params.teacher.teacher_name,
    nickname: params.teacher.nickname,
    teacher_age: params.teacher.teacher_age,
    seniority: params.teacher.seniority,
    teacher_type: params.teacher.teacher_type,
    status: params.teacher.status,
    sex: params.teacher.sex,
    phone: params.teacher.phone,
    is_pluralistic: params.teacher.is_pluralistic,
    bu_id: params.teacher.bu_id,
    description: params.teacher.description,
    email: params.teacher.email,
    old_id: params.teacher.old_id
  };

  // 选中关联员工输入框，清空输入框内容
  $scope.onEmployeeNameFocus = function() {
    if ($scope.updateTeacher.employee_name == '请输入员工姓名') {
      $scope.updateTeacher.employee_name = '';
    }
    $scope.inputing = true;
  };

  // 鼠标离开关联员工控件处理事件
  $scope.onEmployeeNameBlur = function() {
    if (!$scope.updateTeacher.employee_name) {
      $scope.updateTeacher.employee_name = '请输入员工姓名';
    }
    $scope.inputing = false;
  };

  // 关联员工输入框，名称变化则重新查询员工信息
  $scope.onEmployeeNameChange = function() {
    $scope.isDown = 'loading';
    $scope.searchResult = [];
    erp_employeeService.query({
      row_num: 10,
      pageSize: 10,
      currentPage: 1,
      employee_name: $scope.updateTeacher.employee_name
    }, function(resp) {
      $scope.isDown = '';
      if (!resp.error) {
        $scope.searchResult = resp.data;
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  }

  // 从转入学员控件的查询结果中选择一条数据触发该事件
  $scope.selectEmployee = function(employee) {
    $scope.updateTeacher.employee_id = employee.id;
    $scope.updateTeacher.employee_name = employee.employee_name;
    $scope.searchResult = [];
  };

  $scope.checkBeforeSave = function() {
    if (!$scope.updateTeacher.employee_name||$scope.updateTeacher.employee_name == '请输入员工姓名') {
      $uibMsgbox.error('关联员工必填');
      return false;
    }

    if (!$scope.updateTeacher.encoding) {
      $uibMsgbox.error('教师编码必填');
      return false;
    }

    if (!$scope.updateTeacher.teacher_name) {
      $uibMsgbox.error('教师姓名必填');
      return false;
    }

    if (!$scope.updateTeacher.teacher_type) {
      $uibMsgbox.error('教师身份必填');
      return false;
    }

    if (!$scope.updateTeacher.status && $scope.updateTeacher.status!=0) {
      $uibMsgbox.error('教师状态必填');
      return false;
    }
      if ($scope.updateTeacher.is_pluralistic  != 0 && $scope.updateTeacher.is_pluralistic  != 1) {
          $uibMsgbox.error('是否兼职必填');
          return false;
      }

    if (!$scope.updateTeacher.sex) {
      $uibMsgbox.error('教师性别必填');
      return false;
    }

    if (!$scope.updateTeacher.phone) {
      $uibMsgbox.error('教师电话必填');
      return false;
    }
    
    if ($scope.updateTeacher.email && !(Validator.email.test($scope.updateTeacher.email))) {
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
    _.remove($scope.selSubjectList, subject);
    _.forEach($scope.subjectList,  function(arr) {
      if(arr.name == subject.name){
        arr.checked = false;
      }
    });
  }

  $scope.removeTeam = function (team) {
        _.remove($scope.selTeamList, team);
        _.forEach($scope.buList,  function(arr) {
          if(arr.org_name == team.org_name){
            arr.checked = false;
          }
        });
    }
  $scope.handleSubjectChange = function (subject) {
    if (subject.checked && !_.some($scope.selSubjectList, subject)) {
      $scope.selSubjectList.push(subject)
    } else {
      _.remove($scope.selSubjectList, function(n){
        return n.name == subject.name;
      });
    }
  }

    $scope.handleTeamChange = function (team) {
        if (team.checked && !_.some($scope.selTeamList, team)) {
            $scope.selTeamList.push(team)
        } else {
          _.remove($scope.selTeamList, function(n){
            return n.org_name == team.org_name;
          });
        }
    }
  /**
   * 模态框确认
   */
  $scope.putTeacher = function() {

    if ($scope.checkBeforeSave()) {

      var teacherForUpdate = {};
      angular.copy($scope.updateTeacher, teacherForUpdate);
        var subject = teacherForUpdate.subject = [];
        _.forEach($scope.selSubjectList, function(item) {
            teacherForUpdate.subject.push(item.id)
        })
        if (subject) {
            teacherForUpdate.subject = subject.join(",");
        }
        var team = teacherForUpdate.team = [];
        _.forEach($scope.selTeamList, function(item) {
            teacherForUpdate.team.push(item.buId)
        })
        if (team) {
            teacherForUpdate.team = team.join(",");
        }
      erp_TeacherIndexService.put(teacherForUpdate, function(resp) {
        if (!resp.error) {
          $uibModalInstance.close("success");
        } else {
          $uibMsgbox.error(resp.message);
        }
      });
    }
  };

  /**
   * 模态框取消
   */
  $scope.handleModalCancel = function() {
    $uibModalInstance.dismiss('cancel');
  }

  $scope.initialize = function() {
    // 通过教师id查询教师关联的科目
    erp_TeacherListService.querySubject({
      teacherId: $scope.updateTeacher.id
    }, function(resp) {
      if (!resp.error) {
        $scope.selSubjectList = resp.data;
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
    //通过教师id查询教师关联的团队
    erp_TeacherListService.queryTeam({
      teacherId: $scope.updateTeacher.id
    }, function (resp) {
      if (!resp.error) {
        $scope.selTeamList = resp.data;
      } else {
        $uibMsgbox.error(resp.message);
      }
    });

    // $scope.changeBu = function () {
    //   $scope.selSubjectList = [];
    //   erp_subjectService.querySelectDatas({
    //     bu_id: $scope.updateTeacher.bu_id
    //   }, function (resp) {
    //     if (!resp.error) {
    //       $scope.subjectList = resp.data;
    //     } else {
    //       $uibMsgbox.error(resp.message);
    //     }
    //   });
    // };
    erp_TeacherIndexService.toManage({}, function(resp) {
      if (!resp.error) {
        $.each(resp.teacherType, function(i, n) {
          n.code = parseInt(n.code);
        })
        $scope.teacherTypeList = resp.teacherType;
        $.each(resp.teacherStatus, function(i, n) {
          n.code = parseInt(n.code);
        })
        $scope.statusList = resp.teacherStatus;
        $.each(resp.gender, function(i, n) {
          n.code = parseInt(n.code);
        })
        $scope.genderList = resp.gender;
        $.each(resp.isPartTime, function(i, n) {
          n.code = parseInt(n.code);
        })
        $scope.partTimeList = resp.isPartTime;
        $scope.buList = resp.buList;
        $scope.subjectList = resp.subjectList;

        _.forEach($scope.buList, function (item) {
          _.forEach($scope.selTeamList, function (value) {
            if (item.org_name == value.org_name) {
              item.checked = true;
            }
          });
        })
    
        _.forEach($scope.subjectList, function (item) {
          _.forEach($scope.selSubjectList, function (value) {
            if (item.name == value.name) {
              item.checked = true;
            }
          });
        })
        //$scope.cityList = resp.cityList;
        // $scope.buList = $.map(resp.buList,function(n,i) {
        //   if(n.parent_id == resp.city_id) return n;
        // });
        //$scope.subjectList = resp.subjectList;
        //$scope.updateTeacher.city_id = resp.city_id;
        //$scope.updateTeacher.bu_id = resp.bu_id;
      } else {
        $uibMsgbox.error(resp.message);
      }

      // erp_subjectService.querySelectDatas({
      //     bu_id:$scope.updateTeacher.bu_id
      // },function(resp){
      //     if(!resp.error){
      //       $scope.subjectList = resp.data;
      //       _.forEach($scope.subjectList, function (item) {
      //         _.forEach($scope.selSubjectList, function (value) {
      //           if (item.name == value.name) {
      //             item.checked = true;
      //           }
      //         });
      //       })
      //     }else{
      //         $uibMsgbox.error(resp.message);
      //     }
      // });
    });
  }

  $scope.initialize();
}
