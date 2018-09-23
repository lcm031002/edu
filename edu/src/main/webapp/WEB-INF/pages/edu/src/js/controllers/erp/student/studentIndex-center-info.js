"use strict";
angular.module('ework-ui').controller('erp_studentCenterInfoController', [
    '$rootScope',
    '$scope',
    '$state',
    '$cookieStore',
    '$log',
    '$interval',
    '$uibModal',
    '$uibMsgbox',
    'erp_gradeService',
    'erp_studentsService',
    'erp_studentOrgService',
    'erp_studentsCheckService',
    'erp_studentSchoolService',
    'erp_studentContactService',
    'erp_studentIndexCounselorsService',
    'erp_dictService',
    erp_studentCenterInfoController
]);

function erp_studentCenterInfoController(
    $rootScope,
    $scope,
    $state,
    $cookieStore,
    $log,
    $interval,
    $uibModal,
    $uibMsgbox,
    erp_gradeService,
    erp_studentsService,
    erp_studentOrgService,
    erp_studentsCheckService,
    erp_studentSchoolService,
    erp_studentContactService,
    erp_studentIndexCounselorsService,
    erp_dictService
) {
    $scope.tabPanel = 'basicInfo';
    // 学员信息
    $scope.student = {};
    $scope.studentCopy = {};
    $scope.selectedInfo = {
        school: undefined,
        grade: undefined
    }
    $scope.studentIndexCounselors = {};
    $scope.disModifyDetail = false;
    $scope.SexList = [{ value: 1, label: "男" }, { value: 0, label: "女" }];
    $scope.StudentStatusList = [];


    $scope.StudentContactList = [];

    // 查询学生的联系人列表
    function queryStudentContact() {
        erp_studentContactService.query({ student_id: $scope.studentId }, function(resp) {
            if (!resp.error) {
                $scope.StudentContactList = resp.data;
                if (!isEmpty($scope.student.contact_id)) {
                    $scope.student._contact_id = $scope.student.contact_id;
                } else {
                    $scope.student._contact_id = $scope.StudentContactList[0].id;
                }
            }
        });
    }
    //推荐人
    $scope.getRecommender = function(searchInfo) {
            return erp_studentsService.query({
                pageSize: 20,
                currentPage: 1,
                searchInfo: searchInfo,
                searchType: searchInfo
            }).$promise.then(function(resp) {
                _.forEach(resp.data, function(item) {
                    item.studentShortInfo = item.student_name + '【' + item.branch_name + ',' + (item.grade_name || '') + '】'
                });
                return resp.data;
            })
        }
        //报备人
    $scope.getRecorderList = function(searchInfo) {
        return erp_studentsService.queryRecorder({
            nameOrEncoding: searchInfo
        }).$promise.then(function(resp) {
            return resp.data;
        })
    }

    //学员历史推荐人reference_student_ht_modal
    $scope.queryReferenceStudentHt = function(studentId) {
        erp_studentsService.queryReferenceStudentHt({ studentId: studentId }, function(resp) {
            if (!resp.error) {
                //显示推荐人历史框
                $scope.referenceStudentList = resp.data;
                $("#reference_student_ht_modal").modal("show");
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.recorderPaginationConf = {
        currentPage: 1, // 当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function() {
            $scope.queryStudentRecord();
        }
    };

    //查询学员历史报备人信息
    $scope.queryStudentRecord = function() {
            erp_studentsService.queryStudentRecordHt({
                p_studentId: $scope.studentId,
                currentPage: $scope.recorderPaginationConf.currentPage,
                pageSize: $scope.recorderPaginationConf.itemsPerPage
            }, function(resp) {
                if (!resp.error) {
                    //显示推荐人历史框
                    $scope.studentRecorderList = resp.data;
                    $scope.recorderPaginationConf.totalItems = resp.total;
                } else {
                    $uibMsgbox.error(resp.message);
                }
            })
        }
        //添加报备人
    $scope.recorderStartDate = {};
    $scope.addRecorder = function() {
        if (!$scope.recorder || !$scope.recorder.ID) {
            $uibMsgbox.warn("请选择报备人");
            return;
        }
        $uibMsgbox.confirm('确认添加报备人？', function(res) {
            if (res == 'yes') {
                var _modalInstance = $uibMsgbox.waiting('添加中，请稍后');
                erp_studentsService.addRecorder({
                    userId: $scope.recorder.ID,
                    studentId: parseInt($scope.studentId),
                    startDate: $scope.recorderStartDate.val
                }, function(resp) {
                    _modalInstance.close();
                    if (!resp.error) {
                        $scope.recorderStartDate.val = null;
                        $scope.queryStudentRecord();
                    } else {
                        $uibMsgbox.error(resp.message);
                    }
                });
            }
        });
    }

    //查询学员姓名修改历史信息
    $scope.queryStudentNameRecord = function(studentId) {
        erp_studentsService.queryStudentNameRecorder({
            studentId: studentId
        }, function(resp) {
            if (!resp.error) {
                //显示姓名修改历史框
                $scope.studentNameRecorderList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    //查询学员年级修改历史信息
    $scope.queryStudentGradeRecord = function(studentId) {
        erp_studentsService.queryStudentGradeRecorder({
            studentId: studentId,
        }, function(resp) {
            if (!resp.error) {
                //显示姓名修改历史框
                $scope.studentGradeRecorderList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    //查询学员状态修改历史信息
    $scope.queryStudentStatusRecord = function(studentId) {
        erp_studentsService.queryStudentStatusRecorder({
            studentId: studentId,
        }, function(resp) {
            if (!resp.error) {
                //显示状态修改历史框
                $scope.studentStatusRecorderList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    //学员报备人student_recorder_modal
    $scope.showRecorderModal = function() {
            $scope.queryStudentRecord();
            $("#student_recorder_modal").modal("show");
        }
        //学员姓名修改记录name_record_modal
    $scope.showNameRecordModal = function(studentId) {
            $scope.queryStudentNameRecord(studentId);
            $("#name_record_modal").modal("show");
        }
        //学员年级修改记录grade_record_modal
    $scope.showGradeRecordModal = function(studentId) {
            $scope.queryStudentGradeRecord(studentId);
            $("#grade_record_modal").modal("show");
        }
       //学员状态修改记录status_record_modal
    $scope.showStatusRecordModal = function(studentId) {
        $scope.queryStudentStatusRecord(studentId);
        $("#status_record_modal").modal("show");
    }
        // 控制修改或返回
    $scope.actionSwitch = function(action, property_name) {
        if (property_name == 'student_name') {
            if (isEmpty($scope.student._student_name) || $scope.student._student_name == $scope.student.student_name)
                $scope.act_student_name = '返回';
            else
                $scope.act_student_name = '确认';
        } else if (property_name == 'sex') {
            if ($scope.student._sex == -1)
                $scope.act_sex = '返回';
            else
                $scope.act_sex = '确认';
        } else if (property_name == 'phone') {
            if (isEmpty($scope.student._contact_id))
                $scope.act_phone = '返回';
            else
                $scope.act_phone = '确认';
        } else if (property_name == 'email') {
            if (isEmpty($scope.student._email))
                $scope.act_email = '返回';
            else
                $scope.act_email = '确认';
        } else if (property_name == 'qq') {
            if (isEmpty($scope.student._qq))
                $scope.act_qq = '返回';
            else
                $scope.act_qq = '确认';
        }
    };

    // 弹出上传头像框
    $scope.showUploadModal = function(teacher) {
        var modalInstance = $uibModal.open({
            backdrop: 'static',
            templateUrl: 'templates/block/avatar-upload.html',
            controller: 'blocks_avatarUploadController',
            resolve: {
                onUploadImg: function() {
                    return function(base64Img, $uibModalInstance) {
                        erp_studentsService.modifyHeadImg({
                            id: $scope.student.id.toString(),
                            oldPhoto: $scope.student.head_pic,
                            photoBase64: base64Img
                        }, function(resp) {
                            if (!resp.error) {
                                $scope.student.head_pic = resp.url;
                            } else {
                                $uibMsgbox.error(resp.message);
                            }
                            $uibModalInstance.close(base64Img);
                        });
                    }
                }
            }
        });
        modalInstance.result.then(function(result) {}, function() {
            $log.info('DrawModal dismissed at: ' + new Date());
        })
    };

    // 修改学生信息
    $scope.operStudentInfo = function(action, property_name) {
        // {{ }} 和 input 是隐藏和显示关系 _开头的未输入框内容
        if (property_name == 'student_name') {
            $scope.dis_student_name = !$scope.dis_student_name;
            if (action == '返回') {
                $scope.act_student_name = '修改';
                return;
            } else {
                if (isNotEmpty($scope.student._student_name)) {
                    $scope.student.student_name = $scope.student._student_name;
                    $scope.act_student_name = '修改';
                } else {
                    $scope.act_student_name = '返回';
                    $scope.student._student_name = $scope.student.student_name;
                    return;
                }
                if ($scope.dis_student_name) {
                    $scope.act_student_name = '确认';
                    return;
                }
            }
        } else if (property_name == 'sex') {
            $scope.dis_sex = !$scope.dis_sex;
            if (action == '返回') {
                $scope.act_sex = '修改';
                return;
            } else {
                if (isNotEmpty($scope.student._sex) && $scope.student._sex != -1) {
                    $scope.student.sex = $scope.student._sex;
                    $scope.act_sex = '修改';
                } else {
                    $scope.act_sex = '返回';
                    $scope.student._sex = $scope.student.sex;
                    return;
                }
                if ($scope.dis_sex) {
                    $scope.act_sex = '确认';
                    return;
                }
            }
        } else if (property_name == 'birthday') {
            $scope.dis_birthday = !$scope.dis_birthday;
            if (isNotEmpty($('#student_birthday').val()))
                $scope.student.birthday = $('#student_birthday').val();
            else
                return;
        } else if (property_name == 'phone') {
            $scope.dis_phone = !$scope.dis_phone;
            if (action == '返回') {
                $scope.act_phone = '修改';
                return;
            } else {
                if (isNotEmpty($scope.student._contact_id))
                    if (Validator.natural.test($scope.student._contact_id)) {
                        if ($scope.student.contact_id != $scope.student._contact_id) {
                            $scope.student.phone_verify = 0;
                        }
                        // 填充数据
                        $scope.student.contact_id = $scope.student._contact_id;
                        for (var i = 0; i < $scope.StudentContactList.length; i++) {
                            if ($scope.student.contact_id == $scope.StudentContactList[i].id) {
                                $scope.student.phone = $scope.StudentContactList[i].link_phone;
                                $scope.student.relation_name = $scope.StudentContactList[i].relation_name;
                                break;
                            }
                        }
                        $scope.act_phone = '修改';
                    } else {
                        $scope.dis_phone = !$scope.dis_phone;
                        return;
                    }
                else {
                    $scope.act_phone = '返回';
                    $scope.student._phone = $scope.student.contact_id;
                    return;
                }
                if ($scope.dis_phone) {
                    $scope.act_phone = '确认';
                    return;
                }
            }
        } else if (property_name == 'email') {
            $scope.dis_email = !$scope.dis_email;
            if (action == '返回') {
                $scope.act_email = '修改';
                return;
            } else {
                if (isNotEmpty($scope.student._email))
                    if (Validator.email.test($scope.student._email)) {
                        $scope.student.email = $scope.student._email;
                        $scope.act_email = '修改';
                    } else {
                        $scope.dis_email = !$scope.dis_email;
                        return;
                    }
                else {
                    $scope.act_email = '返回';
                    $scope.student._email = $scope.student.email;
                    return;
                }
                if ($scope.dis_email) {
                    $scope.act_email = '确认';
                    return;
                }
            }
        } else if (property_name == 'qq') {
            $scope.dis_qq = !$scope.dis_qq;
            if (action == '返回') {
                $scope.act_qq = '修改';
                return;
            } else {
                if (isNotEmpty($scope.student._qq))
                    if (Validator.qq.test($scope.student._qq)) {
                        $scope.student.qq = $scope.student._qq;
                        $scope.act_qq = '修改';
                    } else {
                        $scope.dis_qq = !$scope.dis_qq;
                        return;
                    }
                else {
                    $scope.act_qq = '返回';
                    $scope.student._qq = $scope.student.qq;
                    return;
                }
                if ($scope.dis_qq) {
                    $scope.act_qq = '确认';
                    return;
                }
            }
        }

        $scope.saveStudent();
    };

    $scope.saveStudent = function(callback) {
        var studentInfo = {};
        studentInfo.id = $scope.student.id;
        studentInfo.head_pic = $scope.student.head_pic;
        studentInfo.student_name = $scope.student.student_name;
        studentInfo.sex = $scope.student.sex;
        studentInfo.birthday = $scope.student.birthday;
        studentInfo.student_status = $scope.student.student_status;
        studentInfo.address = $scope.student.address;
        studentInfo.qq = $scope.student.qq;
        studentInfo.phone = $scope.student.phone;
        studentInfo.contact_id = $scope.student.contact_id;
        studentInfo.relation_name = $scope.student.relation_name;
        studentInfo.email = $scope.student.email;
        studentInfo.grade_id = $scope.student.grade_id;
        studentInfo.attend_school_id = $scope.student.attend_school_id;
        studentInfo.city_id = $scope.student.city_id;
        studentInfo.code = $scope.student.code;
        studentInfo.phone_verify = $scope.student.phone_verify;
        studentInfo.student_id_old = $scope.student.recommender.id;
        //不校验学员姓名是否重复
        //erp_studentsCheckService.check(studentInfo, function(resp) {
        //  if (!resp.error) {
        //  } else {
        //    $uibMsgbox.alert(resp.message);
        //  }
        //});
        erp_studentsService.update(studentInfo, function(result) {
            if (result.error) {
                $uibMsgbox.warn(result.message, function() {
                    window.location = window.location.toString().substring(0, window.location.toString().indexOf(window.location.search)) + '#/students/studentsSearch';
                    // $state.go('studentsSearch', {
                    //     "href": "templates/erp/student/students.html",
                    //     "path": "/students/studentsSearch"
                    // });
                });
            } else {
                $scope.student = result.data;

                for (var i = 0; i < $scope.SexList.length; i++) {
                    if ($scope.SexList[i].value == $scope.student.sex)
                        $scope.student.sex_name = $scope.SexList[i].label;
                }
                for (var i = 0; i < $scope.StudentStatusList.length; i++) {
                    if ($scope.StudentStatusList[i].value == $scope.student.student_status)
                        $scope.student.student_status_name = $scope.StudentStatusList[i].label;
                }

                $scope.student._attend_school = $scope.student.attend_school_name;
                $scope.student._grade = $scope.student.grade_name;

                $rootScope.currentStudent = $scope.student;
                $cookieStore.put(
                    "student",
                    $scope.student);
                $uibMsgbox.alert("修改成功!", function() {
                    if (typeof callback == 'function') {
                        callback($scope.student);
                    }
                });
                initial()
            }
            $scope.saveCtrl = false;
        });
    }

    $scope.getSchools = function(schoolName) {
        return erp_studentSchoolService.query({
            pageSize: 20,
            currentPage: 1,
            school_name: schoolName
        }).$promise.then(function(resp) {
            return resp.data
        })
    }
    $scope.getGrades = function(gradeName) {
        return erp_gradeService.query({
            pageSize: 20, // 每页显示条数
            currentPage: 1, // 要获取的第几页的数据
            p_grade_name: gradeName
        }).$promise.then(function(resp) {
            return resp.data
        });
    }

    $scope.queryStudentGrade = function() {
        erp_gradeService.querySelectDatas({
            date_filter: 1
        }, function(resp) {
            if (!resp.error) {
                $scope.gradeList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    $scope.initStudentInfoDialog = function() {
        $('div#studentInfoDialog #_student_id').val($scope.studentId);
    }

    $scope.updateHead = function() {
        $('div#studentInfoDialog #pic').attr('src',
            genWebContext() + "/common/js/qiniu/upload.jsp");
        $('div#studentInfoDialog').show(0);
    }

    $scope.closeStudentInfoDialog = function() {
        $('div#studentInfoDialog').hide(0);
    }


    $scope.selectPanel = function(tab) {
        $scope.tabPanel = tab;
    };


    $scope.downloadQr = function() {
        window.open($scope.qrSrc + '&isDown=1');
    };

    $scope.studentId = $("#rootIndex_studentId").val();

    function queryStudentInfo() {
        erp_studentsService.query({
                row_num: 20,
                studentId: $scope.studentId
            },
            function(resp) {
                if (!resp.error && resp.data.length) {
                    $scope.student = resp.data[0];
                    if ($scope.student) {
                        $scope.student._attend_school = $scope.student.attend_school_name;
                        $scope.student._grade = $scope.student.grade_name;
                    }
                    initial();
                } else {
                    alert(resp.message);
                }
            });
    }

    function initial() {
        // 显示学生状态
        for (var i = 0; i < $scope.StudentStatusList.length; i++) {
            if ($scope.StudentStatusList[i].value == $scope.student.student_status)
                $scope.student.student_status_name = $scope.StudentStatusList[i].label;
        }
        // 显示性别
        for (var i = 0; i < $scope.SexList.length; i++) {
            if ($scope.SexList[i].value == $scope.student.sex)
                $scope.student.sex_name = $scope.SexList[i].label;
        }

        // 定义下拉框默认项
        $scope.student._sex = $scope.SexList[0].value;
        $scope.student._student_status = $scope.StudentStatusList[0].value;
        queryStudentContact();
        $scope.act_student_name = $scope.act_sex = $scope.act_birthday = $scope.act_phone = $scope.act_email = $scope.act_qq = $scope.act_attend_school = $scope.act_grade = $scope.act_student_status = $scope.act_address = $scope.act_code = $scope.act_city = '修改';

        $scope.studentEncodeEncoding = BASE64.encoder($scope.student.encoding);
        $scope.qrSrc = '/erp/studentservice/student/weChat?encoding=' + $scope.studentEncodeEncoding;
        $('title').text('学员|' + $scope.student.student_name);
        $scope.selectedInfo.school = {
            id: $scope.student.attend_school_id,
            school_name: $scope.student.attend_school_name
        }


        $scope.synToDouble = function() {
            erp_studentsService.synToDouble($scope.student, function(resp) {
                if (!resp.error) {
                    $uibMsgbox.success(resp.message);
                } else {
                    $uibMsgbox.error(resp.message);
                }
            })
        }

        $scope.resetPasswordDouble = function() {
            erp_studentsService.resetPasswordDouble($scope.student, function(resp) {
                if (!resp.error) {
                    $uibMsgbox.success(resp.message);
                } else {
                    $uibMsgbox.error(resp.message);
                }
            })
        }

        $scope.selectedInfo.grade = {
                id: $scope.student.grade_id,
                grade_name: $scope.student.grade_name
            }
            // $scope.searchCity();
    }

    /**
     * 显示修改学生详细信息模块
     * @return {[type]} [description]
     */
    $scope.showModifyDetail = function() {
        angular.copy($scope.student, $scope.studentCopy);
        $scope.studentCopy.recommender = {
            id: $scope.studentCopy.referrals_id,
            studentShortInfo: $scope.studentCopy.referrals
        }
        $scope.disModifyDetail = true;
    }

    $scope.saveModifyDetail = function() {
        $scope.saveCtrl = true;
        $scope.studentCopy.attend_school_id = $scope.selectedInfo.school.id;
        $scope.studentCopy.grade_id = $scope.selectedInfo.grade.id;
        angular.copy($scope.studentCopy, $scope.student);
        $scope.saveStudent(function(student) {
            $scope.disModifyDetail = false;
        });
    }

    $scope.cancelModifyDetail = function() {
        $scope.disModifyDetail = false;
    }

    function queryIndexCounselors() {
        erp_studentIndexCounselorsService.query({
            studentId: $scope.studentId
        }, function(resp) {
            if (!resp.error) {
                $scope.studentIndexCounselors = resp.data;
            }
        });
    }

    $scope.queryStudentStatus = function() {
        erp_dictService.getDictData({ "typeCode": "studentStatus", "needProductLineCdtn": "N" }, function(resp) {
            if (!resp.error) {
                $scope.StudentStatusList = resp.data;
                if ($scope.StudentStatusList && $scope.StudentStatusList.length > 0) {
                    $.each($scope.StudentStatusList, function(idx, studentStatus) {
                        studentStatus.code = Number(studentStatus.code);
                    });
                }
            }
        });
    }

    queryStudentInfo();
    queryIndexCounselors();
    $scope.queryStudentStatus();
    $scope.queryStudentGrade();
}

/**
 * old code
 * 
 */
/*
  $scope.selectSchool = function(school) {
    $scope.act_attend_school = '确认';
    $scope.student._attend_school = school.school_name;
    $scope.student.attend_school = school.id;
    $scope.dis_school = false;
    $scope.schoolResult = [];
  };

  $scope.searchGrade = function() {
    $scope.gradeResult = [];
    $scope.dis_gradeResult = true;
    if (isEmpty($scope.student._grade)) {
      return;
    }
    erp_gradeService.querySelectDatas({
      grade_name: $scope.student._grade
    }, function(resp) {
      $scope.gradeResult = resp.data;
    });
  }
  $scope.selectGrade = function(grade) {
    $scope.act_grade = '确认';
    $scope.student._grade = grade.grade_name;
    $scope.student.grade = grade.id;
    $scope.dis_gradeResult = false;
    $scope.gradeResult = [];
  }

  $scope.searchCity = function() {
    $scope.cityResult = [];
    $scope.dis_region = true;
    var _param = { org_type: 2 };
    erp_studentOrgService.query(_param, function(resp) {
      if (!resp.error) {
        $scope.cityResult = resp.data;
      }
    });
  };

  $scope.selectCity = function(city) {
    $scope.act_city = '确认';
    $scope.student.city = city.id;
    $scope.student._city = city.org_name;
    $scope.dis_region = false;
    $scope.cityResult = [];
  }


  $scope.searchSchool = function() {
    $scope.schoolResult = [];
    $scope.dis_school = true;
    var param = {};
    param.school_name = $(
        '#p_attend_school input[ng-model="student._attend_school"]')
      .val();
    if (param.school_name == null || param.school_name == '') {
      return;
    }
    erp_studentSchoolService.query(param, function(result) {
      if (!result.error) {
        $scope.schoolResult = result.data;
      }
    });
  };

  function initial() {
    // 显示学生状态
    for (var i = 0; i < $scope.StudentStatusList.length; i++) {
      if ($scope.StudentStatusList[i].value == $scope.student.student_status)
        $scope.student.student_status_name = $scope.StudentStatusList[i].label;
    }
    // 显示性别
    for (var i = 0; i < $scope.SexList.length; i++) {
      if ($scope.SexList[i].value == $scope.student.sex)
        $scope.student.sex_name = $scope.SexList[i].label;
    }

    // 定义下拉框默认项
    $scope.student._sex = $scope.SexList[0].value;
    $scope.student._student_status = $scope.StudentStatusList[0].value;
    queryStudentContact();
    $scope.act_student_name = $scope.act_sex = $scope.act_birthday = $scope.act_phone = $scope.act_email = $scope.act_qq = $scope.act_attend_school = $scope.act_grade = $scope.act_student_status = $scope.act_address = $scope.act_code = $scope.act_city = '修改';

    $scope.studentEncodeEncoding = BASE64.encoder($scope.student.encoding);
    $scope.qrSrc = '/erp/studentservice/student/weChat?encoding=' + $scope.studentEncodeEncoding;
    $interval(verifyTime, 1000);
    $('title').text('学员|' + $scope.student.student_name);

    // $scope.searchCity();
  }

 */