"use strict";
angular
    .module('ework-ui')
    .controller('erp_attendanceWfdDetailsController', [
        '$scope',
        '$log',
        '$uibMsgbox',
        '$state',
        'erp_attendanceCourseTimesService',
        erp_attendanceWfdDetailsController]);

function erp_attendanceWfdDetailsController($scope,
                                            $log,
                                            $uibMsgbox,
                                            $state,
                                            erp_attendanceCourseTimesService) {
    $scope.initOrResetStudent = function () {
        $scope.selectAllStudentFlag = false;//学生全选标识
        $scope.studentList = [];//学生列表
        $scope.studentList_attend = [];//已考勤学生列表
        $scope.studentList_no_attend = [];//未考勤学生列表
    };
    $scope.initOrResetTeacher = function () {
        $scope.selectAllTeacherFlag = false;//教师全选标识
        $scope.teacherList = [];//教师列表
        $scope.teacherList_attend = [];//已考勤教师列表
        $scope.teacherList_no_attend = [];//未考勤教师列表
    };
    $scope.initOrResetTeacherGroup = function () {
        $scope.teacherGroupList = [];//教师组列表
        $scope.current_teacher_group = '';//当前选中的教师组
        $scope.initOrResetTeacher();
    };
    $scope.init = function () {
        $scope.selectedTab = 'student';//当前激活的Tab标签页
        $scope.attendance_date = $("#rootIndex_attendance_date").val();//考勤日期
        $scope.current_teacher_group = '';//当前选中的教师组
        $scope.teacher_search_info = "";//教师查询条件
        $scope.student_search_info = "";//学生查询条件
        $scope.initOrResetStudent();
        $scope.initOrResetTeacher();
    };

    /**
     * 全选/取消全选
     * @param type ‘student’表示全选student列表；‘teacher'表示全选teacher列表
     */
    $scope.switchFlag = function (type) {
        var tempArr = [];
        var tempFlag = '';
        var i = 0;
        if (type == 'student') {
            tempArr = $scope.studentList;
            tempFlag = $scope.selectAllStudentFlag;
        } else {
            tempArr = $scope.teacherList;
            tempFlag = $scope.selectAllTeacherFlag;
        }
        if (tempFlag == true) {
            for (i in tempArr) {
                tempArr[i].selectFlag = true;
            }
        } else {
            for (i in tempArr) {
                tempArr[i].selectFlag = false;
            }
        }
    };

    /**
     * 改变标签页，并且加载数据
     * @param tabName 'stduent' 或'teacher'
     */
    $scope.changeTabAndLoadData = function (tabName) {
        $scope.selectedTab = tabName;
        if (tabName == "student") {
            //查询学生列表
            $scope.queryStudents();
        } else {
            //查询教师列表
            $scope.queryTeacherGroups();
        }
    };

    /**
     * 查询学生列表
     */
    $scope.queryStudents = function () {
        $scope.initOrResetStudent();
        erp_attendanceCourseTimesService.studentsForWfdAttn({
                p_attendance_date: $("#rootIndex_attendance_date").val(),
                p_search_info: $scope.student_search_info
            },
            function (resp) {
                if (!resp.error) {
                    $scope.studentList = resp.data;
                    //将studentList中的已考勤学员和未考勤学员分离
                    for (var i in $scope.studentList) {
                        if ($scope.studentList[i].attend_ == 1) {
                            $scope.studentList_attend.push($scope.studentList[i]);
                        } else {
                            $scope.studentList_no_attend.push($scope.studentList[i]);
                        }
                    }
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    };

    /**
     * 查询教师组
     */
    $scope.queryTeacherGroups = function () {
        $scope.initOrResetTeacherGroup();
        erp_attendanceCourseTimesService.teachersGroupForWfdAttn({},
            function (resp) {
                if (!resp.error) {
                    $scope.teacherGroupList = resp.data;
                    //获取教师组后，根据第一个教师组的id查询教师信息
                    $scope.current_teacher_group = resp.data[0].ID;
                    $scope.queryTeachers($scope.current_teacher_group);
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    };

    /**
     * 查询教师列表
     * @param teacher_group_id 教师组ID
     */
    $scope.queryTeachers = function (teacher_group_id) {
        if (teacher_group_id) {
            $scope.current_teacher_group = teacher_group_id;
        }
        $scope.initOrResetTeacher();
        var modalInstance = $uibMsgbox.waiting('正在处理，请稍候...')
        erp_attendanceCourseTimesService.teachersForWfdAttn({
                p_attendance_date: $("#rootIndex_attendance_date").val(),
                p_label_id: $scope.current_teacher_group,
                p_search_info: $scope.teacher_search_info
            },
            function (resp) {
                modalInstance.close();
                if (!resp.error) {
                    $scope.teacherList = resp.data;
                    //将teacherList中的已考勤教师和未考勤教师分离
                    for (var i in $scope.teacherList) {
                        if ($scope.teacherList[i].ATTEND_TYPE == 1) {
                            $scope.teacherList_attend.push($scope.teacherList[i]);
                        } else {
                            $scope.teacherList_no_attend.push($scope.teacherList[i]);
                        }
                    }
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
    };
    /**
     * 考勤学生
     * @param flag 'attendBatch'表示批量考勤；'attend'表示考勤单个学生；'cancel'取消单个学生考勤
     */
    $scope.submitStudentAttend = function (flag, cancleStudent) {
        //构造请求参数
        var requestParam = [];
        //构造请求参数
        if (flag == 'attendBatch') {//批量考勤
            var student = null;
            var i = null;
            for (i in $scope.studentList_no_attend) {
                student = $scope.studentList_no_attend[i];
                if (student.selectFlag == true) {
                    var temp = {};
                    temp.student_id = student.student_id;
                    temp.order_course_id = student.order_detail_id;
                    temp.course_date = $("#rootIndex_attendance_date").val();
                    temp.attend_type = 31;
                    requestParam.push(temp);
                }
            }
        } else {//单个学员考勤
            var temp = {};
            temp.student_id = cancleStudent.student_id;
            temp.order_course_id = cancleStudent.order_detail_id;
            temp.course_date = $("#rootIndex_attendance_date").val();
            if (flag == 'attend') {
                temp.attend_type = 31;
            } else {//flag == 'cancle'
                temp.attend_id = cancleStudent.ATTEND_ID;
                temp.attend_type = 30;
            }
            requestParam.push(temp);
        }
        if (requestParam && requestParam.length > 0) {
            var modalInstance = $uibMsgbox.waiting('正在处理，请稍候...')
            erp_attendanceCourseTimesService.attendanceWfd({
                students: requestParam,
                course_date: $("#rootIndex_attendance_date").val(),
                remark: ''
            }, function (resp) {
                modalInstance.close();
                if (!resp.error) {
                    if (requestParam && requestParam.length == 1 && requestParam[0].attend_type == 30) {
                        var attendDate = new Date(Date.parse(requestParam[0].course_date));
                        var attendYear = attendDate.getFullYear();
                        var attendMonth = attendDate.getMonth();
                        var current = new Date();
                        if (attendYear != current.getFullYear() || attendMonth != current.getMonth()) {
                            $uibMsgbox.alert("跨月考勤置空成功，请查看审批流");
                            $scope.queryStudents();
                        } else {
                            $uibMsgbox.alert("考勤置空成功");
                            $scope.queryStudents();
                        }
                        return;
                    }
                    $uibMsgbox.alert("考勤成功");
                    $scope.queryStudents();
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        } else {
            $uibMsgbox.warn("当前没有选中的记录");
        }
    };
    /**
     * 考勤教师
     * @param flag 'attendBatch'表示批量考勤；'attend'表示考勤单个教师；'cancel'取消单个教师考勤
     */
    $scope.submitTeacherAttend = function (flag, cancleTeacher) {
        //构造请求参数
        var requestParam = [];
        if (flag == 'attendBatch') {//批量考勤
            var teacher = null;
            var i = null;
            for (i in $scope.teacherList_no_attend) {
                teacher = $scope.teacherList_no_attend[i];
                if (teacher.selectFlag == true) {
                    var temp = {};
                    temp.teacher_id = teacher.teacher_id;
                    temp.attend_type = 31;
                    requestParam.push(temp);
                }
            }
        } else {//单个教师考勤
            var temp = {};
            temp.teacher_id = cancleTeacher.teacher_id;
            if (flag == 'attend') {
                temp.attend_type = 31;
            } else {//flag == 'cancle'
                temp.attend_id = cancleTeacher.ATTEND_ID;
                temp.attend_type = 30;
            }
            requestParam.push(temp);
        }
        if (requestParam && requestParam.length > 0) {
            var modalInstance = $uibMsgbox.waiting('正在处理，请稍候...')
            erp_attendanceCourseTimesService.attendanceTeachers({
                teachers: requestParam,
                course_date: $("#rootIndex_attendance_date").val(),
                remark: ''
            }, function (resp) {
                modalInstance.close();
                if (!resp.error) {
                    $scope.queryTeachers();
                    if (requestParam && requestParam.length == 1 && requestParam[0].attend_type == 30) {
                        $uibMsgbox.alert("取消考勤成功");
                    } else {
                        $uibMsgbox.alert("考勤成功");
                    }
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        } else {
            $uibMsgbox.warn("当前没有选中的记录");
        }

    };
    $scope.goSetTeacherGroup = function () {
        $state.go('attendanceGroup', {
            path: '/teachers/attendanceGroup',
            href: 'templates/erp/tearchers/attendanceGroup.html'
        })
    };

    $scope.init();
    $scope.queryStudents(); //初始化加载学生列表
}