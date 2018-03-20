/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_studentScheduingController', [
    '$rootScope',
    '$scope',
    '$log',
    'erp_subjectService',
    '$uibModal',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    '$filter',
    '$stateParams',
    'uibDateParser',
    'PUBORGSelectedService',
    'erp_tpScheduleTimeService',
    'erp_studentOrderCourseService',
    'erp_studentIndexCounselorsService',
    'erp_studentCourseSchedulingService',
    erp_studentScheduingController
]);

function erp_studentScheduingController(
    $rootScope,
    $scope,
    $log,
    erp_subjectService,
    $uibModal,
    $uibMsgbox,
    $filter,
    $stateParams,
    uibDateParser,
    PUBORGSelectedService,
    erp_tpScheduleTimeService,
    erp_studentOrderCourseService,
    erp_studentIndexCounselorsService,
    erp_studentCourseSchedulingService
) {
    // 学生ID
    $scope.studentId = $("#rootIndex_studentId").val();
    $scope.student = null;
    $scope.studentIndexCounselors = null;

    function queryIndexCounselors() {
        return erp_studentIndexCounselorsService.query({
            studentId: $scope.studentId
        }).$promise.then(function(resp) {
            if (!resp.error) {
                $scope.studentIndexCounselors = resp.data
            } else {
                $uibMsgbox.error(resp.message)
            }
        }, function(resp) {
            $uibMsgbox.error(resp.message)
        });
    }
    $scope.moment = moment
        // 界面展示，list:显示列表，add:显示新增排课
    $scope.pageView = 'list';
    // $scope.pageView = 'add';

    /**
     *  列表页面相关数据 
     */
    // 搜索参数
    $scope.searchInfo = {
        branch_id: '',
        attend_type: null,
        start_date: moment().startOf('month').format('YYYY-MM-DD'),
        end_date: moment().endOf('month').format('YYYY-MM-DD')
    };

    // 排课列表
    $scope.scheduleList = [];

    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
     * @param  {Function} onChange      [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function() {
            $scope.query();
        }
    };

    // 查询参数
    $scope.searchParam = {};

    // 科目列表
    $scope.subjectList = [];

    // 排课状态
    $scope.attendTypeList = [
        { "key": null, "value": "全部" },
        { "key": 28, "value": "未考勤" },
        { "key": 21, "value": "正常上课" },
        { "key": 23, "value": "排课取消" },
        { "key": 29, "value": "考勤作废" }
    ];

    // 与表单绑定的数据，用于添加和修改
    $scope.scheduleDetail = {
        course_date: '',
        attend_type: null,
        scheduleType: '',
        orderCourseIds: '',
        attendClassPeriod: '',
        courseScheduleCount: '',
        startDate: '',
        endDate: '',
        start_time: '',
        end_time: '',
        subject_id: '',
        student_id: '',
        teacher_id: ''
    };


    // 处理【新增排课】按钮点击事件
    $scope.addScheduleCourse = function() {
        if (!$scope.studentIndexCounselors.LEARNINGMGR_ID || !$scope.studentIndexCounselors.LEARNINGMGR_NAME) {
            return $uibMsgbox.error('学员未绑定学管师，无法进行排课，请先绑定学管师！')
        }
        if (!$rootScope.curAccount.employeeId == 0 &&
            !$scope.studentIndexCounselors.LEARNINGMGR_ID == $rootScope.curAccount.employeeId) {
            return $uibMsgbox.error('您不是当前学员的学管师，不能对此学员进行排课！当前学员的学管师为：【' + $scope.studentIndexCounselors.LEARNINGMGR_NAME + '】')
        }
        $scope.initAddPage();
    };

    // 处理【修改排课】按钮点击事件
    $scope.editSchedule = function(schedule) {
        schedule.subjectList = $scope.subjectList;
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/student-scheduling-edit.modal.html',
            controller: 'erp_studentIndexSheduleEditModalController',
            resolve: {
                optype: function() {
                    return 'edit'
                },
                scheduleDetail: function() {
                    return schedule
                },
                studentId: function() {
                    return $scope.studentId
                },
                disabled: function() {
                    return true
                }
            }
        }).result.then(function(detail) {
            $scope.query();
        }, function() {});
    };

    //获取所有选中的id
    function getSelectedIds() {
        var ids = "";
        _.forEach($scope.scheduleList, function(schedule) {
            if (schedule.selectFlag) {
                ids += "," + schedule.id;
            }
        })
        return ids.substring(1);
    }

    function querySelectedOrg() {
        PUBORGSelectedService.query({}, function(resp) {
            if (!resp.error) {
                $scope.selectedOrg = resp.data;
                if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {} else {
                    $uibMsgbox.warn("请选择具体校区之后再进行学员排课!");
                }
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }
    // 全选事件
    $scope.onScheduleCheckAll = function() {
        _.forEach($scope.scheduleList, function(schedule) {
            schedule.selectFlag = $scope.checkAllFlag;
        })
    }

    // 某一行Checkbox选择事件
    $scope.onScheduleChecked = function(schedule) {
        $scope.checkAllFlag = _.every($scope.scheduleList, { selectFlag: true });
    }

    // 处理【取消排课】按钮点击事件
    $scope.cancelScheduleCourse = function(id) {
        var ids = getSelectedIds();
        if (!ids || ids.length < 1) {
            $uibMsgbox.alert('请选定要取消排课的课程！');
            return;
        }
        $uibMsgbox.confirm('确定取消排课？', function(result) {
            if (result != 'yes') {
                return;
            }
            $scope.del(ids);
        });
    };

    // 处理【查询年级】按钮点击事件
    $scope.querySchedule = function() {
        $scope.query();
    };

    // 添加
    $scope.add = function() {
        $scope.scheduleDetail.student_id = $scope.studentId;
        delete $scope.scheduleDetail.teacher_name;
        erp_studentCourseSchedulingService.addYdyOrderCourseScheduling($scope.scheduleDetail, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success(resp.message);
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 修改
    $scope.put = function() {
        delete $scope.scheduleDetail.teacher_name;
        delete $scope.scheduleDetail.subjectList;
        erp_studentCourseSchedulingService.updateYdyOrderCourseScheduling({
            "attend_type": $scope.scheduleDetail.attend_type,
            "id": $scope.scheduleDetail.id,
            "attTeacherName": $scope.scheduleDetail.attTeacherName,
            "teacher_id": $scope.scheduleDetail.teacher_id,
            "subject_id": $scope.scheduleDetail.subject_id
        }, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success(resp.message);
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 删除
    $scope.del = function(id) {
        var waitingModal = $uibMsgbox.waiting('操作中，请稍候...')
        erp_studentCourseSchedulingService.cancelYdyOrderCourseScheduling({
            attend_ids: id
        }, function(resp) {
            waitingModal.close();
            if (!resp.error) {
                $uibMsgbox.success(resp.message);
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    /**
     * 查询科目
     */
    function querySubject() {
        erp_subjectService.querySelectDatas({
            branch_id: $scope.searchParam.branch_id ? $scope.searchParam.branch_id : -1,
            season_id: $scope.searchParam.season_id ? $scope.searchParam.season_id : -1
        }, function(resp) {
            if (!resp.error) {
                $scope.subjectList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    // 查询方法
    $scope.query = function() {
        if ($scope.scheduleDetail.endDate) {
            $scope.searchInfo.end_date = $scope.scheduleDetail.endDate;
        } else {
            if ($scope.scheduleDetail.startDate) {
                $scope.searchInfo.end_date = $scope.scheduleDetail.startDate;
            }
        }
        erp_studentCourseSchedulingService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            student_id: $scope.studentId,
            branch_id: $scope.searchInfo.branch_id,
            attend_type: $scope.searchInfo.attend_type,
            start_date: $scope.searchInfo.start_date,
            end_date: $scope.searchInfo.end_date
        }, function(resp) {
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.scheduleList = resp.data;
                _.forEach($scope.scheduleList, function(item) {
                    var date = uibDateParser.parse('' + item.course_date, 'yyyyMMdd');
                    var dateStr = $filter('date')(date, 'yyyy-MM-dd');
                    item.course_date = dateStr;
                });
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 排课状态
    $scope.attendType = function(type) {
        return getTypeName($scope.attendTypeList, type);
    }

    $scope.insertLeader = function(teacher) {
        $scope.scheduleDetail.teacher_name = teacher.teacher_name;
        $scope.scheduleDetail.teacher_id = teacher.id;
    }

    // 获取某类型key对应的Value
    function getTypeName(typeArray, type) {
        var text = '';
        for (var i = 0; i < typeArray.length; i++) {
            if (type == typeArray[i].key) {
                text = typeArray[i].value;
            }
        }
        return text;
    }

    //-- 新增课程，界面初始化
    $scope.conflictObject = {};


    //-- 已经添加的排课
    $scope.addedScheduleList = [];

    $scope.attendTime = {
        start_time: '',
        end_time: '',
        subject: null,
        defaultDuration: 2 * 60 * 60 * 1000,
        period: null
    }
    $scope.attendPeriod = {
            period: '',
            hour_len: '',
            start_date: '',
            end_date: '',
            subject: ''
        }
        // 返回到列表
    $scope.goBack = function() {
        $scope.pageView = 'list'
        $scope.query();
    }

    // 初始化新增排课页面
    $scope.initAddPage = function() {
            $scope.pageView = 'add';
            $scope.getOrderCourseList();
        }
        // 切换标签页【按时间排课】/【近期排课】
    $scope.selectScheduleType = function(schedule_type) {
            $scope.schedule_type = schedule_type;
        }
        //-- End 新增课程，界面初始化

    //-- 新增课程，报班单相关
    // 新增课程，学员报班单列表(所有)
    $scope.orderCourseList = [];
    $scope.course_surplus_total = 0;
    // 新增课程，学员当前校区的报班单
    $scope.curBranchCourseList = [];
    // 新增课程，学员报班单是否全选
    $scope.orderCourseListCheckedAllFlag = false;
    // 获取学员课程报班单
    $scope.getOrderCourseList = function() {
            var waitingModal = $uibMsgbox.waiting('加载学员报班单中，请稍候...')
            erp_studentOrderCourseService.queryOrderCourse({
                pageSize: 999, // 每页显示条数
                currentPage: 1, // 要获取的第几页的数据
                studentId: $scope.studentId,
                isAllBranch: 1,
                businessType: 2
            }).$promise.then(function(resp) {
                waitingModal.close()
                if (!resp.error) {
                    var arry = _.filter(_.sortBy(resp.data, 'create_time'), function(o) {
                        return o.course_schedule_count > 0;
                    })
                    _.forEach(arry, function(item) {
                        item.available = item.branch_id == $scope.selectedOrg.id
                            // item.disabled = true
                        item.checked = false
                    })
                    $scope.orderCourseList = arry;
                    $scope.curBranchCourseList = _.filter(arry, { branch_id: $scope.selectedOrg.id })
                    if (_.isArray($scope.curBranchCourseList) && $scope.curBranchCourseList.length > 0) {
                        $scope.curBranchCourseList[0].checked = true;
                        $scope.onCourseChecked($scope.curBranchCourseList[0])
                    }
                } else {
                    $uibMsgbox.error(resp.message);
                }
            }, function(resp) {
                waitingModal.close()
            });
        }
        // 学员报班单勾选/取消事件处理
    $scope.onCourseChecked = function(course) {
            if (!_.isArray($scope.curBranchCourseList)) {
                return false
            }
            // for(var i = 0; i < $scope.curBranchCourseList.length; i ++ ) {
            //     var preCourse = $scope.curBranchCourseList[i-1]
            //     var curCourse = $scope.curBranchCourseList[i]
            //     var nextCourse = $scope.curBranchCourseList[i + 1]
            //     if (curCourse.checked) {
            //         curCourse.disabled = !!(nextCourse && nextCourse.checked)
            //     } else {
            //         curCourse.disabled = !!(preCourse && !preCourse.checked)
            //     }
            // }
            $scope.orderCourseListCheckedAllFlag = _.every($scope.curBranchCourseList, { checked: true })
        }
        // 全选/反选课程
    $scope.onCheckAllOrderCourse = function() {
            _.forEach($scope.curBranchCourseList, function(item) {
                item.checked = $scope.orderCourseListCheckedAllFlag
            })
            $scope.onCourseChecked()
        }
        //-- End 新增课程，报班单相关

    //-- 新增课程，时间档期
    $scope.schedule_type = 'time';
    // 时间档期列表
    $scope.tpScheduleTimeList = []
        // 获取时间档期列表
    $scope.getTpScheduleTimeList = function() {
            erp_tpScheduleTimeService.queryList({}, function(resp) {
                if (!resp.error) {
                    _.forEach(resp.data, function(item) {
                        item.checked = false
                    })
                    $scope.tpScheduleTimeList = resp.data
                } else {
                    $uibMsgbox.error(resp.message)
                }
            })
        }
        // 时间档期切换事件处理
    $scope.onTpScheduleTimeChange = function(period) {
            _.forEach($scope.tpScheduleTimeList, function(period) {
                period.checked = false
            })
            $scope.attendTime.period = period
            $scope.attendTime.start_time = period.startTime
            $scope.attendTime.end_time = period.endTime
            $scope.attendTime.defaultDuration = moment(period.endTime, 'HH:mm') -
                moment(period.startTime, 'HH:mm')
            period.checked = true
        }
        // 监听排课开始时间，改变时，结束时间也相应改变
    $scope.$watch('attendTime.start_time', function(newValue, oldValue) {
            $scope.attendTime.end_time = moment(moment(newValue, 'HH:mm') +
                $scope.attendTime.defaultDuration).format('HH:mm')
        })
        //-- End 新增课程，时间档期

    //-- 已选择教师
    $scope.scheduleSelectedTeacher = null
        // 选择老师事件处理
    $scope.onSelectTeacher = function(teacher) {
            $scope.scheduleSelectedTeacher = _.cloneDeep(teacher)
        }
        //-- End 已选择教师 

    //-- 日历相关
    $scope.calendar = {
        yearMonth: moment().format('YYYY年MM月'),
        weeks: getCalendar(moment()),
        currentDate: moment(),
        checkedDays: []
    }

    function getCalendar(curDate, checkedList) {
        // 已经选择的日期
        checkedList = checkedList || []
            // 当前日期
        var curMoment = moment(curDate)
            // 本月第一天
        var curMonthFirstDay = moment(curMoment.startOf('month'))
            // 本月最后一天
        var curMonthLastDay = moment(curMoment.endOf('month'))
            // 本月第一天是一周中的第几天（周日第0天，周一第1天...周六第6天）
        var curMonthFirstDayWeek = curMonthFirstDay.day()
            // 本月最后一天是一周中的第几天
        var curMonthLastDayWeek = curMonthLastDay.day()
            // 当前月日历的第一天（例如本月第一天是周三，那就需要再填充三天）
        var calFirstDay = moment(curMonthFirstDay).subtract((curMonthFirstDayWeek + 6) % 7, 'day')
            // 当前月日历的最后一天
        var calLastDay = moment(curMonthLastDay).add((6 - (curMonthLastDayWeek + 6) % 7), 'day')
            // 当前月日历的所有天数
        var daysArray = []
        var dayPointer = moment(calFirstDay)
        for (; dayPointer < calLastDay; dayPointer.add('day', 1)) {
            // 当前日期是否已经选择
            var isChecked = false
                // 查看当前日期是否在已经选择的日期列表中
            _.forEach(checkedList, function(item) {
                var d = moment(item.value)
                if (d.format('YYYYMMDD') == dayPointer.format('YYYYMMDD')) {
                    isChecked = true
                }
            })
            daysArray.push({
                time: moment(dayPointer),
                date: dayPointer.date(),
                month: dayPointer.month(),
                isChecked: isChecked,
                isCurrentMonth: dayPointer.month() == curDate.month()
            })
        }
        return _.chunk(daysArray, 7)
    }

    function reloadCalendar(calendar) {
        calendar.yearMonth = calendar.currentDate.format('YYYY年MM月')
        calendar.weeks = getCalendar(calendar.currentDate, calendar.checkedDays)
    }
    $scope.calendarToggleCheck = function(day) {
        day.isChecked = !day.isChecked;
        var dayStr = day.time.format('YYYY-MM-DD')
        var checkedDays = $scope.calendar.checkedDays
        if (day.isChecked && !_.some(checkedDays, { value: dayStr })) {
            checkedDays.push({
                key: moment(dayStr, 'YYYY-MM-DD').format('YYYYMMDD'),
                value: dayStr
            })
        }
        if (!day.isChecked) {
            checkedDays.splice(_.findIndex(checkedDays, { value: dayStr }), 1)
        }
    }
    $scope.calendarDecrement = function(calendar, type) {
        calendar.currentDate.subtract(1, type)
        reloadCalendar(calendar)
    }
    $scope.calendarIncrement = function(calendar, type) {
        calendar.currentDate.add(1, type)
        reloadCalendar(calendar)
    }
    $scope.clearAllCalendarChecked = function(calendar) {
        calendar.checkedDays.splice(0, calendar.checkedDays.length)
        reloadCalendar(calendar)
    }
    $scope.setCalendarToCurrenDay = function(calendar) {
        calendar.currentDate = moment()
        reloadCalendar(calendar)
    }
    $scope.deleteCheckDay = function(day) {
            var idx = _.findIndex($scope.calendar.checkedDays, { key: day.key })
            $scope.calendar.checkedDays.splice(idx, 1)
            reloadCalendar($scope.calendar)
        }
        //-- End 日历相关

    //-- 按时间添加排课，确定添加
    // 校验按时间添加排课
    function validAddScheduleByTime() {
        if (getSelectedCourseList($scope.orderCourseList).length <= 0) {
            $uibMsgbox.error('请先选择报班单！')
            return false
        }
        if (!$scope.attendTime.start_time) {
            $uibMsgbox.error('请选择上课时间')
            return false
        }
        if (!$scope.attendTime.end_time) {
            $uibMsgbox.error('请选择下课时间')
            return false
        }
      var attendEndTime = $scope.attendTime.end_time;
      if (attendEndTime == '00:00') {
        attendEndTime = "24:00";
      }
        if (moment($scope.attendTime.start_time, 'HH:mm') - moment(attendEndTime, 'HH:mm') > 0) {
            $uibMsgbox.error('下课时间不能早于上课时间！')
            return false;
        }
        if (!$scope.attendTime.subject) {
            $uibMsgbox.error('请选择科目')
            return false
        }
        if (!$scope.scheduleSelectedTeacher) {
            $uibMsgbox.error('请选择老师')
            return false
        }
        if (!_.isArray($scope.calendar.checkedDays) || $scope.calendar.checkedDays.length <= 0) {
            $uibMsgbox.error('请至少选择一个日期')
            return false
        }
        return true
    }
    // 检测课程是否冲突
    function queryConflictScheduling(
        studentId,
        startTime,
        endTime,
        teacherId,
        checkedDays,
        callback) {
        var waitingModal = $uibMsgbox.waiting('排课校验中，请稍候...')
        var schedulingArry = []
        _.forEach(checkedDays, function(courseDay) {
            schedulingArry.push({
                student_id: studentId,
                start_time: startTime,
                end_time: endTime,
                teacher_id: teacherId,
                course_date: courseDay.key
            })
        })
        return erp_studentCourseSchedulingService.queryConflictScheduling(schedulingArry)
            .$promise.then(function(resp) {
                waitingModal.close()
                if (!resp.error) {
                    callback(resp)
                } else {
                    $uibMsgbox.error(resp.message)
                }
            }, function(resp) {
                $uibMsgbox.error(resp.message)
            });
    }
    // 获取已选择的报班单
    function getSelectedCourseList(courseList) {
        var courseIdList = [];
        _.forEach(courseList, function(course) {
            if (course.checked) {
                courseIdList.push(course.id);
            }
        })
        return courseIdList.join(',');
    }

    $scope.addScheduleByTime = function() {
        if (!validAddScheduleByTime()) {
            return false
        }
        queryConflictScheduling(
            $scope.studentId,
            $scope.attendTime.start_time,
            $scope.attendTime.end_time,
            $scope.scheduleSelectedTeacher.id,
            $scope.calendar.checkedDays,
            function(resp) {
                $scope.conflictObject = resp.data
                $scope.conflictList = []
                _.forIn(resp.data, function(value, key) {
                    $scope.conflictList.push(value[0])
                })
                _.forEach($scope.calendar.checkedDays, function(day) {
                    if (!!$scope.conflictObject[day.key]) {
                        day.cls = "danger"
                    }
                })
                $scope.conflictList = _.orderBy($scope.conflictList, 'course_date')
                var courseIdListStr = getSelectedCourseList($scope.orderCourseList);
                $uibModal.open({
                    backdrop: false,
                    templateUrl: 'newScheduleListByDate.html',
                    size: 'xlg',
                    scope: $scope,
                    controller: ['$scope', function($scope) {
                        $scope.onOk = function() {
                            if ($scope.calendar.checkedDays.length <= 0) {
                                return $uibMsgbox.error('排课列表为空，请选择排课日期！', function() {
                                    $scope.$dismiss();
                                })
                            }
                            var scheduleList = []
                            _.forEach($scope.calendar.checkedDays, function(day) {
                                scheduleList.push({
                                    orderCourseIds: courseIdListStr,
                                    scheduleType: "time",
                                    student_id: $scope.studentId,
                                    start_time: $scope.attendTime.start_time,
                                    end_time: $scope.attendTime.end_time,
                                    startDate: day.value,
                                    subject_id: $scope.attendTime.subject.id,
                                    teacher_id: $scope.scheduleSelectedTeacher.id
                                })
                            })
                            var waitingModal = $uibMsgbox.waiting('排课中，请稍候...')
                            erp_studentCourseSchedulingService.addYdyOrderCourseSchedulingList(
                                scheduleList,
                                function(resp) {
                                    waitingModal.close();
                                    if (!resp.error) {
                                        if (!_.isArray($scope.addedScheduleList)) {
                                            $scope.addedScheduleList = []
                                        }
                                        _.forEach(resp.data, function(item) {
                                            $scope.addedScheduleList.push(item)
                                        })
                                        $uibMsgbox.success(resp.message || '添加排课成功！', function() {
                                            $scope.$close();
                                        });
                                    } else {
                                        $uibMsgbox.error(resp.message);
                                    }
                                });
                        }
                    }]
                }).result.then(function() {
                    $scope.getOrderCourseList();
                    $scope.query();
                    $scope.calendar.checkedDays.splice(0, $scope.calendar.checkedDays.length)
                    $scope.attendTime.period = null
                    $scope.attendTime.start_time = ''
                    $scope.attendTime.end_time = ''
                    reloadCalendar($scope.calendar)
                })
            }
        )
    }

    $scope.addScheduleByPeriod = function() {
            var courseIdListStr = getSelectedCourseList($scope.orderCourseList);
            if (courseIdListStr.length == 0) {
                $uibMsgbox.alert('请选择要操作的课程！');
                return;
            }
            var scheduleDetail = {
                orderCourseIds: courseIdListStr
            }
            scheduleDetail.scheduleType = $scope.schedule_type;
            scheduleDetail.attendClassPeriod = $scope.attendPeriod.period;
            scheduleDetail.courseScheduleCount = $scope.attendPeriod.hour_len;
            scheduleDetail.startDate = $scope.attendPeriod.start_date;
            scheduleDetail.endDate = $scope.attendPeriod.end_date;
            scheduleDetail.start_time = $scope.attendTime.start_time;
            scheduleDetail.end_time = $scope.attendTime.end_time;
            scheduleDetail.teacher_id = $scope.scheduleSelectedTeacher.id;
            scheduleDetail.subject_id = $scope.attendPeriod.subject.id;
            scheduleDetail.student_id = $scope.studentId;
            erp_studentCourseSchedulingService.addYdyOrderCourseScheduling(scheduleDetail, function(resp) {
                if (!resp.error) {
                    $uibMsgbox.success(resp.message);
                    if (!_.isArray($scope.addedScheduleList)) {
                        $scope.addedScheduleList = []
                    }
                    _.forEach(resp.data, function(item) {
                        $scope.addedScheduleList.push(item)
                    })
                    $scope.getOrderCourseList()
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });

        }
        //--End 按时间添加排课，确定添加
    activate();

    //-- 页面加载后初始化页面
    // $scope.initAddPage();
    function activate() {
        $scope.query();
        $scope.getTpScheduleTimeList();
        querySubject();
        querySelectedOrg();
        queryIndexCounselors($scope.studentId).then(function() {
            if ($stateParams.optype == 'newScheduling') {
                $scope.addScheduleCourse();
            }
        })
    }
}