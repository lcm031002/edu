/**
 * Created by Liyong.zhu on 2017/2/18.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_attendanceBJKStudentsController', [
        '$scope',
        '$log',
        '$uibMsgbox',
        'erp_subjectService',
        'erp_gradeService',
        'erp_courseService',
        'erp_studentBuOrgsService',
        'erp_timeSeasonService',
        'PUBORGSelectedService',
        'erp_attendanceCourseTimesService',
        'erp_teacherService',
        'erp_attendanceMakeupService',
        'EmployeeManageService',
        erp_attendanceBJKStudentsController]);

function erp_attendanceBJKStudentsController(
    $scope,
    $log,
    $uibMsgbox,
    erp_subjectService,
    erp_gradeService,
    erp_courseService,
    erp_studentBuOrgsService,
    erp_timeSeasonService,
    PUBORGSelectedService,
    erp_attendanceCourseTimesService,
    erp_teacherService,
    erp_attendanceMakeupService,
    EmployeeManageService) {
    $scope.isShowSenior = false;
    $scope.businessType = 1;
    $scope.queryParam  = {
        page:1
    };
    $scope.attendTypeList = [
        {
            "name":"置空",
            "id":10
        },{
            "name":"挂起",
            "id":11
        },{
            "name":"正常上课",
            "id":12
        }
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

    $scope.selectQueryAttendType = function(attendType){
        $scope.queryParam.selectedAttendType = attendType;
    }

    $scope.selectAttendType = function(attendType){
        $scope.queryParam.selectedAllAttendType = attendType;
        $.each($scope.selectedStudent,function(i,stu){
            stu.attendType = attendType.id;
            stu.STATUSTYPENAME = attendType.name;
        });
    }

    $scope.isOpenParamSettings = false;
    $scope.openParamSettingsPanel = function(){
        $scope.isOpenParamSettings = true;
        queryCourseParamSettings();
    };
    $scope.closeParamSettingsPanel = function(){
        $scope.isOpenParamSettings = false;
    };

    function queryCourseParamSettings(){
        var param = {};
        param.schedulingId = $scope.schedulingId;
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
                    param.courseId = $scope.courseId;
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
                p.schedulingId = $scope.schedulingId;
            });
            var param = {};
            param.schedulingId = $scope.schedulingId;
            param.schedulingAssistList =   $scope.paramSettingsList;
            $scope.isSubmit = 'saveCourseParamSettings';
            erp_courseService.updateSchedulingTimeAssist(param,function(resp){
                $scope.isSubmit = '';
                if(!resp.error){
                    $uibMsgbox.alert("保存成功！");
                    $scope.closeParamSettingsPanel();
                }else{
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    }

    /**
     *
     * 提交考勤
     *
     */
    $scope.submitAttendance = function(){
        if(!$scope.selectedStudent.length){
            $uibMsgbox.alert("请选择考勤的学员！");
            return;
        }
        var param = {};
        var attendanceList = [];
        $.each($scope.selectedStudent,function(i,stu){
            var student = {};
            student.schedulingId = stu.schedulingId;
            student.studentId = stu.studentId;
            student.studentName = stu.studentName;
            student.lock_status = stu.attShenpiStatus;
            student.teacherId = stu.teacherId;
            student.remark = stu.remark;
            student.order_encoding = stu.orderEncoding;
            student.courseDate = stu.courseDate;
            student.attendanceId = stu.attendanceId;
            student.attendType = stu.attendType;
            attendanceList.push(student);
        });
        param.submitAttendanceList = attendanceList;
        $scope.isSubmitAttendance = 'isSubmitAttendance';
        erp_attendanceCourseTimesService.attendance(param,function(resp){
            $scope.isSubmitAttendance = '';
           if(!resp.error){
               $uibMsgbox.alert("考勤成功！");
           }else{
               $uibMsgbox.error(resp.message);
           }
        });
    }
    $scope.selectedStudent = [];
    $scope.checkedStudent=function(student){
        if(student.checked){
            student.checked = false;
        }else{
            student.checked = true;

        }

        var selectedStudent = [];
        $.each($scope.attendanceStudents,function(i,stu){
            if(stu.checked){
                selectedStudent.push(stu);
            }
        });
        $scope.selectedStudent = selectedStudent;
    }

    $scope.checkedAllStudent=function(){
        var selectedStudent = [];
        $.each($scope.attendanceStudents,function(i,stu){
            if(stu.checked){
                stu.checked = false;
            }else{
                stu.checked = true;
                selectedStudent.push(stu);
            }
        });
        $scope.selectedStudent = selectedStudent;
    }



    $scope.selectTeacher = function(teacher,student){
        student.teacherId = teacher.id;
        student.teacherName = teacher.teacher_name;
        $uibMsgbox.alert("选择成功！");
    }

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
                $uibMsgbox.error(resp.data);
            }
        });
    }

    $scope.changeHourLen = function(paramSetting) {
        if(!paramSetting.courseVal) {
            paramSetting.extandVal1 = null;
            paramSetting.extandVal2 = null;
        }
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
                $uibMsgbox.error(resp.data);
            }
        });
    }

    $scope.showSenior = function(){
        if($scope.isShowSenior){
            $scope.isShowSenior = false;
        }else{
            $scope.isShowSenior = true;
        }
    }






    function initial(){
        $('title').text("班级课考勤 | 快乐学习");
        $scope.courseId = $("#rootIndex_courseId").val();
        $scope.courseTime = $("#rootIndex_courseTime").val();
        $scope.schedulingId = $("#rootIndex_schedulingId").val();

        if($scope.courseId && $scope.courseTime){

            $scope.isQueryCourse = 'isQueryCourse';

            var param = {};
            param.course_id = $scope.courseId;
            param.business_type = 1;
            erp_courseService.query(param,function(resp){

                if(!resp.error&&resp.data&&resp.data.length){
                    $scope.course = resp.data[0];
                    $scope.isQueryCourse = '';
                    $scope.isReady = true;
                }else{
                    $scope.isQueryCourse='courseNotFound';
                }
            });

            var param = {};
            param.courseId = $scope.courseId;
            erp_attendanceCourseTimesService.query(param,function(resp){
                if(!resp.error){
                    $scope.courseTimeAttendanceList = resp.data;
                    if($scope.courseTimeAttendanceList){
                        $.each($scope.courseTimeAttendanceList,function(i,m){
                            if((m.courseTimes+'') == $scope.courseTime){
                                m.checked = true;
                                $scope.selectedCourseTime = m;
                            }
                        });
                    }
                }
            });

            queryStudents();
            queryTeacher();
        }


    }

    function queryStudents(){
        var param = {};
        param.courseId = $scope.courseId;
        param.courseTime = $scope.courseTime;
        param.businessType = $scope.businessType;

        $scope.isQueryAttendanceStudents = true;
        erp_attendanceCourseTimesService.students(param,function(resp){
            $scope.isQueryAttendanceStudents = false;
            if(!resp.error){
                $scope.attendanceStudents = resp.data;
            }
        });
    }


    $scope.outputExcel = function (){
        var param = {};
        param.courseId = $scope.courseId;
        param.courseTime = $scope.courseTime;
        param.businessType = $scope.businessType;

        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        erp_attendanceCourseTimesService.outputExcel(param, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
        ////第五种方法
        //var idTmr;
        //function  getExplorer() {
        //    var explorer = window.navigator.userAgent ;
        //    //ie
        //    if (explorer.indexOf("MSIE") >= 0) {
        //        return 'ie';
        //    }
        //    //firefox
        //    else if (explorer.indexOf("Firefox") >= 0) {
        //        return 'Firefox';
        //    }
        //    //Chrome
        //    else if(explorer.indexOf("Chrome") >= 0){
        //        return 'Chrome';
        //    }
        //    //Opera
        //    else if(explorer.indexOf("Opera") >= 0){
        //        return 'Opera';
        //    }
        //    //Safari
        //    else if(explorer.indexOf("Safari") >= 0){
        //        return 'Safari';
        //    }
        //}
        //function method5(tableid) {
        //    if(getExplorer()=='ie')
        //    {
        //        var curTbl = document.getElementById(tableid);
        //        var oXL = new ActiveXObject("Excel.Application");
        //        var oWB = oXL.Workbooks.Add();
        //        var xlsheet = oWB.Worksheets(1);
        //        var sel = document.body.createTextRange();
        //        sel.moveToElementText(curTbl);
        //        sel.select();
        //        sel.execCommand("Copy");
        //        xlsheet.Paste();
        //        oXL.Visible = true;
        //
        //        try {
        //            var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
        //        } catch (e) {
        //            print("Nested catch caught " + e);
        //        } finally {
        //            oWB.SaveAs(fname);
        //            oWB.Close(savechanges = false);
        //            oXL.Quit();
        //            oXL = null;
        //            idTmr = window.setInterval("Cleanup();", 1);
        //        }
        //
        //    }
        //    else
        //    {
        //        tableToExcel(tableid)
        //    }
        //}
        //function Cleanup() {
        //    window.clearInterval(idTmr);
        //    CollectGarbage();
        //}
        //var tableToExcel = (function() {
        //    var uri = 'data:application/vnd.ms-excel;base64,',
        //        template = '<html><head><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',
        //        base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
        //        format = function(s, c) {
        //            return s.replace(/{(\w+)}/g,
        //                function(m, p) { return c[p]; }) }
        //    return function(table, name) {
        //        if (!table.nodeType) table = document.getElementById(table);
        //        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML};
        //        window.location.href = uri + base64(format(template, ctx));
        //    }
        //})();
        //
        //method5(tableid);
    }

    $scope.toOutputExcel = function(){
        $scope.isOpenStudentsList = 'openStudentsList';
    }

    $scope.closeOutputExcel = function(){
        $scope.isOpenStudentsList = '';
    }

    /*补课——start*/
    $scope.selectAttend={};
    $scope.teach={};
    $scope.extralesson = function(attend){
        $scope.attDetailsResult = [];
        $scope.phone =  attend.PHONE;
        $scope.selectAttend = attend;
        $scope.selectCourse_id = $scope.courseId;
        var param = {};
        param.scheduling_id = attend.schedulingId;
        $scope.openDialog = 'openExtralesson';
        erp_attendanceMakeupService.query(param,function(resp){
            $scope.openDialog = '';
            if(!resp.error){
                //if(resp.data.length==0){
                    //$uibMsgbox.alert("该课程视频未上传，请联系相关人员上传视频~");
                    //return;
               // }else{
                    $scope.openDialog = 'open.dialog.extralesson';
                    $scope.attDetailsLoad = 'loading';
                    $scope.selectAttend = attend;
               // }
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.closeExtralesson = function(){
        $scope.openDialog = '';
    }

    $scope.saveExtralesson = function(){
        if(isEmpty($("#phone").val())){
            $uibMsgbox.alert("手机号码不能为空");
            return;
        }
        if(isEmpty($("#elStartDate").val())){
            $uibMsgbox.alert("开始时间不能为空");
            return;
        }
        if(isEmpty($("#elEndDate").val())){
            $uibMsgbox.alert("截止时间不能为空");
            return;
        }

        var elStartDate = $("#elStartDate").val();
        var elEndDate = $("#elEndDate").val();
        var param = {};
        param.scheduling_id = $scope.selectAttend.schedulingId;
        param.order_course_id = $scope.selectAttend.orderCourseId;
        param.status = '1';
        param.valid_start_date = elStartDate;
        param.valid_end_date = elEndDate;
        param.student_id = $scope.selectAttend.studentId;
        param.phone = $("#phone").val();
        
        $scope.openDialog = 'saveExtralesson';
        erp_attendanceMakeupService.post(param, function(resp){
            $scope.openDialog = '';
            if(!resp.error){
                $uibMsgbox.alert('预约补课完成,预约码为【'+resp.activation_code+'】');
                initial();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

    /*补课——end*/


    initial();
}