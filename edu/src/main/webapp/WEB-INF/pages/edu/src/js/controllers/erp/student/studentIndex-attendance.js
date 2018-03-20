/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular.module('ework-ui').controller('erp_StudentIndexAttendanceController', [
  '$rootScope',
  '$scope',
  '$cookieStore',
  '$uibModal',
  '$uibMsgbox',
  '$log',
  'erp_studentCourseService',
  'erp_studentCourseTimesService',
  'erp_teacherService',
  'EmployeeManageService',
  'erp_courseService',
  'erp_attendanceService',
  'erp_attendanceDetailsService',
  'erp_attendanceMakeupService',
  'erp_studentsService',
  'erp_studentCourseSchedulingService',
  erp_StudentIndexAttendanceController
]);

function erp_StudentIndexAttendanceController(
  $rootScope,
  $scope,
  $cookieStore,
  $uibModal,
  $uibMsgbox,
  $log,
  erp_studentCourseService,
  erp_studentCourseTimesService,
  erp_teacherService,
  EmployeeManageService,
  erp_courseService,
  erp_attendanceService,
  erp_attendanceDetailsService,
  erp_attendanceMakeupService,
  erp_studentsService,
  erp_studentCourseSchedulingService
) {
  //学员信息
  $scope.student = {};
  $scope.businessType = 1;
  $scope.productLine = 1; // 产品线 1-培英精美班 2-个性化 11-佳音

  $scope.courses = [];
  $scope.ydyList = []; // 1对1考勤列表
  $scope.wfdList = []; // 晚辅导考勤列表
  $scope.wfdAttnDetailList = []; // 晚辅导考勤明细列表
  $scope.wfdAttend = {};
  
  $scope.attend = {
		  checkYdyAllFlag : false
  }

  $scope.isLoading = '';
  $('#pre-info-c').tab('show')
  $scope.changeBusinessType = function(bizType) {
    $scope.businessType = bizType;
    $scope.queryStudentCourse();
  }

  $scope.queryStudentCourse = function() {
    var param = {
      p_studentId: $scope.studentId,
      p_businessType: $scope.businessType,
      p_seacherName: $scope.searchInfo
    };
    $scope.isLoading = 'isLoading';
    if ($scope.businessType == 1) {
      $scope.courses = [];
      erp_studentCourseService.query(param, function(resp) {
        $scope.isLoading = '';
        if (!resp.error) {
          $scope.courses = resp.data;
        } else {
          $uibMsgbox.alert(resp.message);
        }
      });
    } else if ($scope.businessType == 2) {
      erp_studentCourseSchedulingService.query({
        student_id: $scope.studentId
      }, function(resp) {
        $scope.isLoading = '';
        if (!resp.error) {
          $scope.ydyList = resp.data;
        } else {
          $uibMsgbox.error(resp.message);
        }
      });
    } else if ($scope.businessType == 3) {
      erp_studentCourseService.queryWfd(param, function(resp) {
        $scope.isLoading = '';
        if (!resp.error) {
          $scope.wfdList = resp.data;
        } else {
          $uibMsgbox.alert(resp.message);
        }
      });
    }
  };

  $scope.showDetail = function(course) {
    if (course.showDetail) {
      course.showDetail = undefined;
    } else {
      course.showDetail = true;
      queryCourseTimes(course);
    }
  }

  function queryCourseTimes(course) {
    var param = {};
    param.courseId = course.ID;
    param.studentId = $scope.studentId;
    course.isLoadingTimes = true;
    erp_studentCourseTimesService.query(param, function(resp) {
      course.isLoadingTimes = false;
      if (!resp.error) {
        course.courseTimes = resp.data;
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

    //高级参数start

    $scope.paramSettingsListOrigin = [
        {   id:null,
            courseId:null,
            courseName : '学管师',
            courseVal:'',
            courseKey:'course_manager',
            courseCfgScope:1,
            courseTime:null,
            extandVal1:null,
            extandVal2:null,
            extandVal3:'',
            extandVal4:'',
            remark:''
        },{
            id:null,
            courseId:null,
            courseName : '中文老师',
            courseVal:'',
            courseKey:'course_tearcher_cn',
            courseCfgScope:1,
            courseTime:null,
            extandVal1:null,
            extandVal2:null,
            extandVal3:'课时长度',
            extandVal4:'',
            remark:''
        },{
            id:null,
            courseId:null,
            courseName : '外文老师',
            courseVal:'',
            courseKey:'course_tearcher_en',
            courseCfgScope:1,
            courseTime:null,
            extandVal1:null,
            extandVal2:null,
            extandVal3:'课时长度',
            extandVal4:'',
            remark:''
        }
    ];

    $scope.paramSettingsList =  _.cloneDeep($scope.paramSettingsListOrigin);
    $scope.queryParam = {};
    $scope.changeSearchInfo = function(paramSetting){
        if(paramSetting.courseKey == 'course_manager') {
            queryEmployeeInfo();
        } else {
            queryTeacher();
        }
    }


    function queryTeacher(){
        var param = {};
        if($scope.queryParam.search_info){
            param.search_info = $scope.queryParam.search_info;
        }
        $scope.isLoadingDataList = 'isLoadingDataList';
        $scope.queryParam.dataList = [];
        erp_teacherService.page(param,function(resp){
            $scope.isLoadingDataList = '';
            if(!resp.error){
                $scope.queryParam.dataList = $.map(resp.data,function(n,i){
                    n.name = n.teacher_name;
                    return n;
                });
            }else{
                $uibMsgbox.alert(resp.data);
            }
        });
    }

    function queryEmployeeInfo(){
        var param = {};
        if($scope.queryParam.search_info){
            param.searchInfo = $scope.queryParam.search_info;
        }
        $scope.isLoadingDataList = 'isLoadingDataList';
        $scope.queryParam.dataList = [];
        EmployeeManageService.queryEmployeeInfo(param,function(resp){
            $scope.isLoadingDataList = '';
            if(!resp.error){
                $scope.queryParam.dataList = $.map(resp.data,function(n,i){
                    n.name = n.EMPLOYEE_NAME;
                    n.encoding = n.ENCODING;
                    n.id = n.ID;
                    return n;
                });
            }else{
                $uibMsgbox.alert(resp.data);
            }
        });
    }

    $scope.isOpenParamSettings = false;
    $scope.openParamSettingsPanel = function(schedulingId,course_id){
        $scope.paramSettingsList = {};
        $scope.currentHighParam =  {
            schedulingId : schedulingId,
            course_id : course_id
        };
        $scope.isOpenParamSettings = true;
        queryCourseParamSettings(schedulingId,course_id);
    };
    $scope.closeParamSettingsPanel = function(){
        $scope.isOpenParamSettings = false;
        $scope.currentHighParam = null;
    };
    function queryCourseParamSettings(schedulingId,course_id){
        var param = {};
        param.schedulingId = schedulingId;
        $scope.isQueryCourseParamSettings = 'isQueryCourseParamSettings';
        //先查询课次级别的参数配置
        erp_courseService.querySchedulingTimeAssist(param,function(resp){
            if(!resp.error){
                var data = resp.data;
                if(data&&data.length){
                    $scope.isQueryCourseParamSettings = '';
                    $scope.paramSettingsList = data;
                }else{
                    //查询课程级别的参数配置
                    var param = {};
                    param.courseId = course_id;
                    erp_courseService.querySchedulingAssist(param,function(resp){
                        $scope.isQueryCourseParamSettings = '';
                        if(!resp.error){
                            var data = resp.data;
                            if(data&&data.length){
                                $scope.paramSettingsList = data;
                            } else {
                                $scope.paramSettingsList =  _.cloneDeep($scope.paramSettingsListOrigin);
                            }
                        }else{
                            $uibMsgbox.error(resp.message);
                        }
                    });
                }
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.selectParam = function(paramSetting,data){
        paramSetting.courseVal = data.id + '';
        paramSetting.courseValName = data.name;
    }


    $scope.saveParamSettings = function(){
        $uibMsgbox.confirm('修改高级参数，不会改变历史考勤记录，如果需要变更历史，请置空后重新考勤，是否继续？', function (res) {

            if (res == 'yes') {
                var isCofirm = true;
                for(var i in $scope.paramSettingsList) {
                    var v = $scope.paramSettingsList[i];
                    if(v.courseKey && v.courseKey == "course_tearcher_en" && v.extandVal2 && v.extandVal2>1) {
                        isCofirm = confirm('口语长度大于1小时，是否继续？');
                    }
                }
                if(isCofirm) {
                    $scope.saveHighParams();
                }
            }
        })
    }
    $scope.saveHighParams = function() {
        if($scope.paramSettingsList&&$scope.paramSettingsList.length){
            $.each($scope.paramSettingsList,function(i,p){
                p.schedulingId = $scope.currentHighParam.schedulingId;
            });
            var param = {};
            param.schedulingId = $scope.currentHighParam.schedulingId;
            param.schedulingAssistList =   $scope.paramSettingsList;
            $scope.isSubmit = 'saveCourseParamSettings';
            erp_courseService.updateSchedulingTimeAssist(param,function(resp){
                $scope.isSubmit = '';
                if(!resp.error){
                    $uibMsgbox.alert("保存成功！");
                    $scope.closeParamSettingsPanel();
                }else{
                    $uibMsgbox.alert(resp.message);
                }
            });
        }
    }

    $scope.changeHourLen = function(paramSetting) {
        if(!paramSetting.courseVal) {
            paramSetting.extandVal1 = null;
            paramSetting.extandVal2 = null;
        }
    }
    //高级参数 end

  //检查所属地区是否属于泉州和合肥
  $scope.checkCity = function(attend) {
    return attend.city_id == 9 || attend.city_id == 7;
  }

  $scope.courseSchedulingTeacherChange = function(attend) {
    attend.teacherList = undefined;
    if (!attend.teacher_name) {
      return;
    }
    attend.isteacherLoading = 'loading...';
    erp_teacherService.query({
      teacher_name: attend.teacher_name,
      status: 1
    }, function(res) {
      attend.isteacherLoading = '';
      if (!res.error) {
        attend.teacherList = res.data;
      } else {
        $uibMsgbox.alert(res.message);
      }
    });
  };

  $scope.changeCourseSchedulingTeacher = function(attend, teacher) {
    attend.teacher_id = teacher.id;
    attend.teacher_name = teacher.teacher_name;
    attend.teacher_encoding = teacher.encoding;
    attend.teacherList = undefined;
  };


  $scope.win_change_student_infoDownListGrade = function() {
    if ($scope.win_change_student_info.grades) {
      $scope.win_change_student_info.show_grades = $scope.win_change_student_info.grades;
    }
  };

  $scope.win_change_student_infoShowGradeSelected = function(grade) {
    $scope.win_change_student_info.GRADE_NAME = grade.GRADE_NAME;
    $scope.win_change_student_info.grade_id = grade.GRADE_ID;
    $scope.win_change_student_info.show_grades = [];
  };

  $scope.changeAttendType = function(attend, attendType) {
    attend.attend_type = attendType;
  }

  /**
   * 考勤保存
   * @param attend
   * @param course
   */
  $scope.saveAttendance = function(attend, course) {
    var attendSubmit = {};
    attendSubmit.attendance_id = attend.attendance_id;
    attendSubmit.attendType = attend.attend_type;
    attendSubmit.schedulingId = attend.SCHEDULINGID;
    attendSubmit.courseDate = attend.course_date;
    attendSubmit.lock_status = attend.lock_status;
    attendSubmit.studentId = $scope.studentId;
    attendSubmit.teacherId = attend.teacher_id;
    attendSubmit.order_encoding = attend.order_encoding;
    attendSubmit.studentName=attend.student_name;
    if (attend.remark) {
      attendSubmit.remark = attend.remark;
      if ((attendSubmit.remark + "").length > 100) {
        attendSubmit.remark = (attendSubmit.remark + "").substring(0, 100);
      }
    }
    erp_attendanceService.post(attendSubmit, function(response) {
      if (!response.error) {
        attend.attend_type = attend.attend_type;
        attend.attend_date = Format('yyyy-MM-dd', new Date());
        attend.oldAttendType = attend.attend_type;
        $uibMsgbox.alert("考勤成功！");
        queryCourseTimes(course);
      } else {
        $uibMsgbox.alert(response.message);
      }
    });
  };

  /*课次考勤明细_start*/
  $scope.showAttentDetail = function(attend, course) {
    $scope.openDialog = 'open.dialog.attendDetails';
    $scope.attDetailsLoad = 'loading';
    $scope.attDetailsResult = [];
    //查询考勤历史数据
    erp_attendanceDetailsService.query({
        student_id: $scope.studentId,
        scheduling_id: attend.SCHEDULINGID
      },
      function(resp) {
        if (!resp.error) {
          if (resp.data) {
            for (var i = 0; i < resp.data.length; i++) {
              resp.data[i].course_times = attend.course_times;
              resp.data[i].course_date = attend.course_date;
              $scope.attDetailsResult.push(resp.data[i]);
            }
          }
        }
        $scope.attDetailsLoad = '';
      });
  }

  $scope.closeAttentDetail = function() {
    $scope.openDialog = '';
  }
  
  $scope.closeExtralesson = function() {
	    $scope.openDialog = '';
  }

  /*补课——start*/
  $scope.selectAttend = {};
  $scope.teach = {};
  $scope.extralesson = function(attend, teach) {
    $scope.attDetailsResult = [];
    $scope.phone = $scope.student.phone;
    $scope.selectAttend = attend;
    $scope.selectCourse_id = teach.course_id;
    var param = {};
    param.scheduling_id = attend.SCHEDULINGID;
    erp_attendanceMakeupService.query(param, function(resp) {
      if (!resp.error) {
        //if (resp.data.length == 0) {
        //  alert("该课程视频未上传，请联系相关人员上传视频~");
        //  return;
        //} else {
          $scope.openDialog = 'open.dialog.extralesson';
          $scope.attDetailsLoad = 'loading';
        //}
      }
    });
  }

  /*晚辅导考勤明细_start*/
  $scope.showWfdAttentDetail = function(wfd) {
    $uibModal.open({
      size: 'lg',
      templateUrl: 'templates/block/modal/student-attendance-wfd.modal.html',
      controller: 'erp_studentWfdAttendModalController',
      resolve: {
        studentId: function () {
          return $scope.studentId
        },
        wfdDetail: function () {
          return wfd
        }
      }
    })
  }

  /*晚辅导考勤_start*/
  $scope.handleWfdAttend = function(attendType) {
      
    }
    /*晚辅导考勤_end*/

  /* 1对1考勤_start */
  /*
   * 批量操作
   * @param operate 操作类型 21-考勤 23-排课取消
   */
  $scope.ydyBatchAttend = function(operate) {
    var attendList = [];
    $.each($scope.ydyList, function(idx, ydy) {
    	if (ydy.selectFlag) {
    		attendList.push(ydy);
    	}
    });
    
    if ($scope.checkBeforeYdyAttend(attendList, operate)) {
      $scope.handleYdyAttend(attendList, operate);
    }
  }
  
  $scope.onYdyCheckAll = function() {
	  if ($scope.ydyList && $scope.ydyList.length > 0) {
			$.each($scope.ydyList, function(idx, ydy) {
				if ($scope.attend.checkYdyAllFlag) {
					ydy.selectFlag = true;
				} else {
					ydy.selectFlag = false;
				}
			});
	  }
  }

  $scope.checkBeforeYdyAttend = function(attendList, operate) {
    if (!attendList || attendList.length == 0) {
      $uibMsgbox.error("请选择考勤记录！");
      return false;
    }

    var idList = [];
    $.each(attendList, function(idx, attend) {
      if (attend.attend_type == operate) {
        idList.push(attend.id);
      } else {
        attend.attend_type = operate;
      }
    });

    if (idList.length > 0) {
      var errMsg = '考勤单号[' + idList.join(',') + ((operate == 21) ? ']已正常上课,不能再次考勤！' : ']已排课取消，不能再次取消！');
      $uibMsgbox.error(errMsg);
      return false;
    }

    return true;
  }

  // 考勤置空
  $scope.cancelYdyAttend = function(attend) {
      if (attend.attend_type != 21) {
        $uibMsgbox.error("该记录未考勤，不能置空");
        return;
      }

      attend.attend_type = 20;
      $scope.handleYdyAttend([attend], 20);
    }
    // 排课取消
  $scope.cancelYdySched = function(attend) {
    if (attend.attend_type == 23) {
      $uibMsgbox.error("该记录已排课取消，不能再次排课取消");
      return;
    }

    attend.attend_type = 23;
    $scope.handleYdyAttend([attend], 23);
  }

  $scope.handleYdyAttend = function(attendList, operate) {
      erp_attendanceService.ydyAttend(attendList, function(resp) {
        if (!resp.error) {
          var msg = (operate == '21') ? '考勤成功' : ((operate == 20) ? '置空成功' : '排课取消成功');
          $uibMsgbox.alert(msg);
          $scope.queryStudentCourse();
        } else {
          $uibMsgbox.error(resp.message);
        }
      });
    }
    /* 1对1考勤_end */

  $scope.saveExtralesson = function() {
    if (isEmpty($("#phone").val())) {
      $uibMsgbox.alert("手机号码不能为空");
      return;
    }
    if (isEmpty($("#elStartDate").val())) {
      $uibMsgbox.alert("开始时间不能为空");
      return;
    }
    if (isEmpty($("#elEndDate").val())) {
      $uibMsgbox.alert("截止时间不能为空");
      return;
    }

    var elStartDate = $("#elStartDate").val();
    var elEndDate = $("#elEndDate").val();
    var param = {};
    param.scheduling_id = $scope.selectAttend.SCHEDULINGID;
    param.order_course_id = $scope.selectAttend.order_detail_id;
    param.status = '1';
    param.valid_start_date = elStartDate;
    param.valid_end_date = elEndDate;
    param.student_id = $scope.studentId;
    param.phone = $("#phone").val();
    $scope.attDetailsLoad = 'loading';
    /*预约校验*/
    erp_attendanceMakeupService.post(param, function(resp) {
      if (!resp.error) {
        $uibMsgbox.alert('预约补课完成,预约码为【' + resp.activation_code + '】', function() {
          $scope.openDialog = '';
      });
      } else {
        $uibMsgbox.alert(resp.message, function() {
          $scope.openDialog = '';
      });
      }
    });
    
  }

  /*补课——end*/

  $scope.studentId = $("#rootIndex_studentId").val();

  function queryStudentInfo() {
    erp_studentsService.query({
        row_num: 20,
        studentId: $scope.studentId
      },
      function(resp) {
        if (!resp.error && resp.data.length) {
          $scope.student = resp.data[0];
          $scope.productLine = resp.data[0].product_line;
          initial();
        } else {
          $uibMsgbox.alert(resp.message);
        }
      });
  }

  function initial() {
    $scope.queryStudentCourse();

    $('title').text('学员|' + $scope.student.student_name);
  }

  queryStudentInfo();
}

/**
 * 晚辅导考勤明细控制器
 */
angular.module('ework-ui').controller('erp_studentWfdAttendModalController', [
  '$rootScope',
  '$scope',
  '$log',
  '$uibModalInstance',
  '$uibMsgbox',
  'studentId',
  'wfdDetail',
  'erp_attendanceDetailsService',
  erp_studentWfdAttendModalController
])

function erp_studentWfdAttendModalController(
  $rootScope,
  $scope,
  $log,
  $uibModalInstance,
  $uibMsgbox,
  studentId,
  wfdDetail,
  erp_attendanceDetailsService
) {
  $scope.wfdAttend = {
    course_date: moment().format('YYYY-MM-DD'),
    remark: ''
  }

  $scope.wfdAttnDetailList = [];
  /**
   * [handleWfdAttend description]
   * @param  {[type]} attendType 30: 置空，31：正常上课
   * @return {[type]}            [description]
   */
  $scope.handleWfdAttend = function (attendType, wfdAttnDetail) {
    erp_attendanceDetailsService.wfdAttn({
        studentId: studentId,
        attendType: attendType,
        counselorId: wfdDetail.COUNSELOR_ID,
        orderCourseId: wfdDetail.ORDER_COURSE_ID,
        courseDate: $scope.wfdAttend.course_date,
        remark: $scope.wfdAttend.remark
      }, function(resp) {
        if (!resp.error) {
          $scope.getWfdAttendDetailList();
        } else {
          $uibMsgbox.alert(resp.message);
        }
      });
  }
  $scope.handleWfdAttendModify = function (attendType, wfdAttnDetail) {
    erp_attendanceDetailsService.wfdAttn({
        studentId: studentId,
        attendType: attendType,
        counselorId: wfdDetail.COUNSELOR_ID,
        orderCourseId: wfdDetail.ORDER_COURSE_ID,
        courseDate: wfdAttnDetail.COURSE_DATE,
        remark: wfdAttnDetail.REMARK || ''
      }, function(resp) {
        if (!resp.error) {
          $scope.getWfdAttendDetailList();
        } else {
          $uibMsgbox.alert(resp.message);
        }
      }); 
  }
  $scope.getWfdAttendDetailList = function(){
    var _uibWaitingModal = $uibMsgbox.waiting('数据加载中，请稍候');
    erp_attendanceDetailsService.queryWfd({
      p_student_id: studentId,
      p_order_id: wfdDetail.ID
    }, function(resp) {
      _uibWaitingModal.close();
      if (!resp.error) {
          $scope.wfdAttnDetailList = resp.data;
      } else {
        $uibMsgbox.alert(resp.message);
      }
    });
  }

  $scope.getWfdAttendDetailList();
}
