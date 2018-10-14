/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_courseSchduleChangeController', [
        '$scope',
        '$log',
        '$state',
        '$uibMsgbox',
        'erp_courseService',
        'erp_courseTimesService',
        'erp_teacherService',
        'EmployeeManageService',
        erp_courseSchduleChangeController]);

function erp_courseSchduleChangeController(
    $scope,
    $log,
    $state,
    $uibMsgbox,
    erp_courseService,
    erp_courseTimesService,
    erp_teacherService,
    EmployeeManageService) {
    $scope.businessType = 1;
    $scope.queryParam  = {
        page:1
    };
    $scope.class_period_list = [
        { id : 1,name:'周一'},
        { id : 2,name:'周二'},
        { id : 3,name:'周三'},
        { id : 4,name:'周四'},
        { id : 5,name:'周五'},
        { id : 6,name:'周六'},
        { id : 7,name:'周日'}
    ];
    $scope.user_type_list = [
        { id : 1,name:'全部课次'}
    ];

    $scope.paramSettingsList = [
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

    $scope.course_id = $("#rootIndex_courseId").val();


    /**
     * 查询课程
     */
    $scope.querySelectingCourse = function(){
        var param = {
            branch_id:$scope.queryParam.selectedBranch?$scope.queryParam.selectedBranch.id:-1,
            season_id:$scope.queryParam.selectedTimeSeason?$scope.queryParam.selectedTimeSeason.id:-1,
            grade_id:$scope.queryParam.selectedGrade?$scope.queryParam.selectedGrade.id:-1,
            subject_id:$scope.queryParam.selectedSubject?$scope.queryParam.selectedSubject.id:-1,
            business_type:$scope.businessType,
            search_info:$scope.queryParam.courseSearchInfo
        };
        param.currentPage = $scope.queryParam.currentPage;
        param.status = -1;
        param.pageSize = $scope.queryParam.pageSize;

        $scope.toSelectingCourseList = [];
        if($scope.selectedOrg&&$scope.selectedOrg.id&&$scope.selectedOrg.type=="4"){
        }else{
          $uibMsgbox.warn('您还没选择校区，请选择校区！', function() {
            setTimeout(function() {
              $('.btn-group.sel-org.pull-left').addClass('open');
            }, 300);
          })
            return;
        }
        $scope.isQuerySelectingCourse = 'isQuerySelectingCourse';
        erp_courseService.query(param,function(resp){
            $scope.isQuerySelectingCourse = '';
            if(!resp.error){
                $scope.toSelectingCourseList = resp.data;
                $scope.queryParam.total          = resp.total;
                $scope.queryParam.totalPage     = resp.totalPage;
                $scope.queryParam.pageSize      = resp.pageSize;
                $scope.queryParam.currentPage  = resp.currentPage;
                $scope.queryParam.paginationBars = [];
                for(var index = ($scope.queryParam.page-1) * 10  ;index < $scope.queryParam.totalPage && index < $scope.queryParam.page * 10;index++){
                    $scope.queryParam.paginationBars.push(index);
                }
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }

    /**
     * 修改排课信息
     */
    $scope.updateCourseSchdule = function(){
        $scope.course.start_date = $("#start_date").val();
        $scope.course.end_date = $("#end_date").val();
        $scope.course.start_time = $("#start_time").val();
        $scope.course.end_time = $("#end_time").val();
        var param = {};

        param = $scope.course;
        param.updateType = 'updateType';
        var modalInstance = $uibMsgbox.waiting("数据提交中，请稍候...");
        erp_courseService.update(param,function(resp){
            modalInstance.close();
            if(!resp.error){
                $uibMsgbox.alert("修改成功！");
                $state.reload();
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    function queryCourse(){
        var param = {};
        param.course_id = $scope.course_id;
        param.business_type = 1 ;
        param.status = -1;
        $scope.isLoadingCourse = 'isLoadingCourse';
        erp_courseService.query(param,function(resp){
           if(!resp.error ){
               if(resp.data.length){
                   $scope.course = resp.data[0];
                   $scope.isLoadingCourse = '';
                   queryCourseTimes();
                   initialPeriodList();

                   $scope.isMtCourse = $scope.course.more_teacher_courseid && $scope.course.more_teacher_course_type == 4;
                   $scope.isMainMtCourse = $scope.course.teacher_id == $scope.course.lecturer_id;
               }else{
                   $scope.isLoadingCourse = 'courseLoadingFailed';
                   $scope.courseLoadingFailed = "没有查询到课程!";
               }
           } else{
               $scope.isLoadingCourse = 'courseLoadingFailed';
               $scope.courseLoadingFailed = resp.message;
           }
        });
    }

    function initialPeriodList(){
        if($scope.course&&$scope.course.attend_class_period){
            $.each($scope.class_period_list,function(i,p){
                p.checked = undefined;
            });
            var periodList = $scope.course.attend_class_period.split(",");
            if(periodList.length){
                $.each(periodList,function(i,p){
                    var id = parseInt(p);
                    $.each($scope.class_period_list,function(j,pRecord){
                        if(pRecord.id == id){
                            pRecord.checked = true;
                        }
                    });
                });
            }
        }
    }

    function queryCourseTimes(){
        var param = {};
        param.courseId = $scope.course_id;
        $scope.isQueryCourseTimes = 'isQueryCourseTimes';
        erp_courseTimesService.query(param,function(resp){
            $scope.isQueryCourseTimes = '';
            if(!resp.error){
                $scope.courseTimesList = resp.data;
            } else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    function queryTeacher(){
        var param = {};
        if($scope.queryParam.search_info){
            param.search_info = $scope.queryParam.search_info;
        }
        $scope.isLoadingTeacherList = 'isLoadingTeacherList';
        $scope.queryParam.teacherList = [];
        erp_teacherService.page(param,function(resp){
            $scope.isLoadingTeacherList = '';
            if(!resp.error){
                $scope.queryParam.teacherList = resp.data;
            }else{
                $uibMsgbox.error(resp.data);
            }
        });
    }

    $scope.changeSearchInfo = function(){
        queryTeacher();
    }

    $scope.changeHighParamSearchInfo = function(paramSetting){
        if(paramSetting.courseKey == 'course_manager') {
            queryEmployeeInfo();
        } else {
            queryHighParamTeacher();
        }
    }
    function queryHighParamTeacher(){
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
                alert(resp.data);
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
                alert(resp.data);
            }
        });
    }

    $scope.selectCourseTeacher = function(teacher){
        $scope.course.teacher_id = teacher.id;
        $scope.course.teacher_code = teacher.encoding;
        $scope.course.teacher_name = teacher.teacher_name;
        $uibMsgbox.alert("选择成功！");
    }

    $scope.selectCourseTimeTeacher = function(courseTime,teacher){
        courseTime.teacher_id = teacher.id;
        courseTime.teacher_code = teacher.encoding;
        courseTime.teacher_name = teacher.teacher_name;
        $uibMsgbox.alert("选择成功！");
    }

    $scope.selectParam = function(paramSetting,data){
        paramSetting.courseVal = data.id + '';
        paramSetting.courseValName = data.name;
    }

    $scope.checkPeriod = function(period){
        if(period.checked){
            period.checked = false;
        }else{
            period.checked = true;
        }
        var periodList = [];
        $.each($scope.class_period_list,function(i,p){
            if(p.checked){
                periodList.push(p.id);
            }
        });
        if(periodList.length){
            $scope.course.attend_class_period = periodList.join(",");
        }
    }

    $scope.modifyCourseTime = function(courseTime) {
        courseTime.courseTimeCopy = undefined;
        var copy = angular.copy(courseTime)
        courseTime.courseTimeCopy = copy;
        courseTime.isEdit = true;
    }

    $scope.cancelMofifyCourseTime = function (courseTime) {
        courseTime.isEdit = undefined;
        angular.copy(courseTime.courseTimeCopy, courseTime)
    }

    $scope.saveCourseTime = function(courseTime){
        var course_date = $("#course_date_"+courseTime.id).val();
        var start_time = $("#start_time_"+courseTime.id).val();
        var end_time = $("#end_time_"+courseTime.id).val();
        courseTime.course_date = course_date;
        courseTime.start_time = start_time;
        courseTime.end_time = end_time;
        var param = {};
        param.id = courseTime.id;
        param.title = courseTime.title;
        param.course_date = courseTime.course_date;
        param.start_time = courseTime.start_time;
        param.end_time = courseTime.end_time;
        param.remark = courseTime.remark;
        param.course_times = courseTime.course_times;
        param.teacher_id = courseTime.teacher_id;
        param.assteacher_id = courseTime.assteacher_id;
        param.course_id=$scope.course_id;
        var _waitingModal = $uibMsgbox.waiting('正在提交....')
        erp_courseTimesService.update(param,function(resp){
            _waitingModal.close();
            if(!resp.error){
                $uibMsgbox.alert("保存成功!");
                courseTime.isEdit = undefined;
                //变更结课时间
                var maxDate = 0;
                for(var d in $scope.courseTimesList) {
                    var tmp = parseInt($scope.courseTimesList[d].course_date);
                    if(tmp>maxDate) {
                        maxDate = tmp;
                    }
                }
                maxDate = maxDate+"";
                $scope.course.end_date = maxDate.slice(0,4) + "-" + maxDate.slice(4,6) + "-" + maxDate.slice(6,8);
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }

    $scope.changeStatus = function(status){
        var param = {};
        param.ids = $scope.course.id;
        param.status = status;
        var _waitingModal = $uibMsgbox.waiting('正在提交....')
        erp_courseService.changeStatus(param,function(resp){
            _waitingModal.close();
            if(!resp.error){
                $uibMsgbox.alert("保存成功！");
                $scope.course.status = status;
            } else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    function queryCourseParamSettings(){
        var param = {};
        param.courseId = $scope.course_id;
        $scope.isQueryCourseParamSettings = 'isQueryCourseParamSettings';
        erp_courseService.querySchedulingAssist(param,function(resp){
            $scope.isQueryCourseParamSettings = '';
            if(!resp.error){
                var data = resp.data;
                if(data&&data.length){
                    $scope.paramSettingsList = data;
                }
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.goBack = function(){
        window.open('#/orders/ordersMgr/ordersMgrCourse','_self');
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
                p.courseId = $scope.course.id;
            });
            var param = {};
            param.courseId = $scope.course.id;
            param.schedulingAssistList =   $scope.paramSettingsList;
            var _waitingModal = $uibMsgbox.waiting('正在保存，请稍候...');
            erp_courseService.updateSchedulingAssist(param,function(resp){
                _waitingModal.close();
                if(!resp.error){
                    $uibMsgbox.alert("保存成功！");
                    $scope.closeParamSettingsPanel();
                }else{
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    }

    $scope.isOpenParamSettings = false;
    $scope.openParamSettingsPanel = function(){
        $scope.isOpenParamSettings = true;
        queryCourseParamSettings();
    };
    $scope.closeParamSettingsPanel = function(){
        $scope.isOpenParamSettings = false;
    };

    $scope.changeHourLen = function(paramSetting) {
        if(!paramSetting.courseVal) {
            paramSetting.extandVal1 = null;
            paramSetting.extandVal2 = null;
        }
    }

    if($scope.course_id){
        queryCourse();
        queryTeacher();
    }else{
        $scope.courseLoadingFailed = "没有要排课的课程！";
    }

    $scope.course_scheduling = '走进化学\n反比例函数的基本性质\n空气、氧气\n阅读理解训练营之填空题\nn分子和原\nn原子的结构';
    //批量修改课次标题弹出框
    $scope.batchModifySchedullingModalShow = function() {
        $("#course_scheduling_change_batch_modify_modal").modal('show');
    }
    $scope.batchModifySchedulling = function() {
        if($scope.batchUpdateCourseTimeTitlePlaceholder == $scope.course_scheduling ){
            $uibMsgbox.alert("请输入课次标题！");
            return;
        }
        var _waitingModal = $uibMsgbox.waiting('正在保存，请稍候...');
        erp_courseTimesService.batchUpdate({
                course_id:$scope.course_id,
                courseSchedulingDetails:$scope.course_scheduling
            },function(resp) {
            _waitingModal.close();
            if(!resp.error){
                $("#course_scheduling_change_batch_modify_modal").modal('hide');
                queryCourseTimes();
                $uibMsgbox.alert("保存成功！");
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.printCallNameTable = function(){
                if(location.href.indexOf('klxuexi.org')>0){
            window.open("/printhtml/print_callname.html?courseId=" + $scope.course_id)
                }else{
            window.open("/edu/printhtml/print_callname.html?courseId=" + $scope.course_id)
                }
    }
    $scope.batchUpdateCourseTimeTitlePlaceholder = '走进化学\n反比例函数的基本性质\n空气、氧气\n阅读理解训练营之填空题\nn分子和原\nn原子的结构';
    $scope.focus = function(){
        if($scope.course_scheduling === $scope.batchUpdateCourseTimeTitlePlaceholder){
            $scope.course_scheduling = '';
        }
    };

    $scope.blur = function(){
        if($scope.course_scheduling ===''){
            $scope.course_scheduling = $scope.batchUpdateCourseTimeTitlePlaceholder;
        }
    };
    $scope.resetCourseTeacher = function (courseTime) {
        courseTime.assteacher_name = '';
        courseTime.assteacher_id = 0;
        courseTime.assteacher_code = '';
    }
    $scope.setCourseTeacher = function (courseTime,teacher) {
        courseTime.assteacher_name = teacher.teacher_name;
        courseTime.assteacher_id = teacher.id;
        courseTime.assteacher_code = teacher.encoding;
    };
 }