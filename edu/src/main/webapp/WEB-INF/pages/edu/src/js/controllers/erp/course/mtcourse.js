"use strict";
angular.module('ework-ui').controller('erp_mtCourseController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    '$state',
    '$uibModal',
    'erp_mtcourseService',
    'erp_subjectService',
    'erp_gradeService',
    'erp_studentBuOrgsService',
    'erp_TeacherSearchService',
    'erp_timeSeasonService',
    'PUBORGSelectedService',
    'erp_teacherService',
    erp_mtCourseController
]);

function erp_mtCourseController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $state,
    $uibModal,
    erp_mtcourseService,
    erp_subjectService,
    erp_gradeService,
    erp_studentBuOrgsService,
    erp_TeacherSearchService,
    erp_timeSeasonService,
    PUBORGSelectedService,
    erp_teacherService
) {
    $scope.allCheckedFlag = false;
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索课程名称
    $scope.searchParam = {
        course_Name: ''
    };
    $scope.queryParam = {};
    //状态列表
    $scope.statusList = [{ "id": 1, "name": "上架" },
        { "id": 2, "name": "下架" }
    ];

    // 课程列表
    $scope.courseList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.courseDetail = {
        id: '',
        course_name: ''
    };

    // 课程类型
    $scope.courseTypeList = [{ "id": 1, "name": "线上+线下课程" }, { "id": 2, "name": "线下双师课程" }, { "id": 3, "name": "线上补课课程" }, { "id": 4, "name": "暑期双师课程" }];
    // 同步状态
    $scope.sendStatusList = [{ "id": 0, "name": "未同步" }, { "id": 1, "name": "同步成功" }, { "id": 2, "name": "同步失败" }];
    // 运营类型
    $scope.operationTypeList = [{ "id": "0", "name": "直营" }, { "id": "1", "name": "直营+加盟" }];

    $scope.onAllCheckedChange = function() {
        _.forEach($scope.courseList, function(course) {
            course.selectedFlag = $scope.allCheckedFlag;
        })
    }

    $scope.onCourseCheckedChange = function(course) {
        $scope.allCheckedFlag = _.every($scope.courseList, { selectedFlag: true });
    }

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

    $scope.handleImportClass = function() {
        // 跳转到班级课导入页面
        $state.go('ordersMgrCourseInput', {
            path: '/orders/ordersMgr/ordersMgrCourseInput',
            href: 'templates/erp/course/fileUpload_bjk.html'
        })
    }

    /**
     * 查询校区
     */
    function queryBranchOrgs() {
        erp_studentBuOrgsService.query({}, function(resp) {
            if (!resp.error) {
                var data = resp.data;
                if (data && data.length) {
                    $scope.branchList = resp.data;
                    //将全部option添加到branchList
                    $scope.branchList.unshift({
                        id: -1,
                        org_name: "-- 全部 --"
                    });
                }
                //查询选中的组织，并为校区设置默认值
                querySelectedOrg();
            }
        })
    }

    /**
     * 获取组织结构中选中的组织
     */
    function querySelectedOrg() {
        PUBORGSelectedService.query({}, function(resp) {
            if (!resp.error) {
                $scope.selectedOrg = resp.data;
                if ($scope.selectedOrg && $scope.selectedOrg.id && ($scope.selectedOrg.type == "4" || $scope.selectedOrg.type == "3")) {
                    if ($scope.selectedOrg.type == "4") {
                        $.each($scope.branchList, function(i, b) {
                            if (b.id == $scope.selectedOrg.id) {
                                $scope.searchParam.branch_id = b.id;
                            }
                        });
                    }
                } else {
                    $uibMsgbox.warn("请选择团队或校区");
                }
                //界面初始化查询
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    /**
     * 查询科目
     */
    function querySubject() {
        erp_subjectService.querySelectDatas({
            branch_id: $scope.searchParam.branch_id ? $scope.searchParam.branch_id : -1,
            season_id: $scope.searchParam.season_id ? $scope.searchParam.season_id : -1,
            grade_id: $scope.searchParam.grade_id ? $scope.searchParam.selectedCourse.id : -1
        }, function(resp) {
            if (!resp.error) {
                $scope.subjectList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    /**
     * 查询课程
     */
    function queryGrade() {
        erp_gradeService.querySelectDatas({
            branch_id: $scope.searchParam.branch_id ? $scope.searchParam.branch_id : -1,
            season_id: $scope.searchParam.season_id ? $scope.searchParam.season_id : -1,
        }, function(resp) {
            if (!resp.error) {
                $scope.gradeList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    /**
     * 查询课程季
     */
    function queryTimeSeason() {
        erp_timeSeasonService.list({}, function(resp) {
            if (!resp.error) {
                $scope.timeSeasonList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    /**
     * 选择课程
     * @param grade
     */
    $scope.selectCourse = function(grade) {
        $scope.queryParam.selectedCourse = grade;
        querySubject();
    }

    /**
     * 选择科目
     * @param subject
     */
    $scope.selectSubject = function(subject) {
        $scope.queryParam.selectedSubject = subject;
    }



    //获取所有选中的id
    function getSelectedIds() {
        var ids = "";
        if (!_.some($scope.courseList, { selectedFlag: true })) {
            $uibMsgbox.alert('请选择课程！')
            return "";
        }
        _.forEach($scope.courseList, function(course) {
            if (course.selectedFlag) {
                ids += "," + course.id;
            }
        })
        return ids.substring(1);
    }

    // 处理【添加课程】按钮点击事件
    $scope.handleAddCourse = function() {
        $scope.optype = 'add';
        $scope.resetForm();
        // $('#erpMTCoursePanel').modal('show');
        $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'templates/block/modal/course-mtcourse.modal.html',
            controller: 'erp_mtCourseModalController',
            scope: $scope
        }).result.then(function(detail) {
            $scope.add();
        }, function() {});
    };

    // 处理【查询排课】按钮点击事件
    $scope.queryCourseScheduing = function(id) {
        if (id == "" || id == undefined || id == null) {
            $uibMsgbox.alert('课程包中需要绑定主场课程！');
            return;
        }
        window.location.href = "?courseId=" + id + "#/orders/classesScheduleCourse";
    };

    // 处理【修改课程】按钮点击事件
    $scope.handlePutCourse = function(course) {
        $scope.optype = 'put';
        $scope.courseDetail = course;
        //查找出匹配的课程
        $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'templates/block/modal/course-mtcourse.modal.html',
            controller: 'erp_mtCourseModalController',
            scope: $scope
        }).result.then(function(detail) {
            $scope.put();
        }, function() {});
    };

    // 处理【查询课程】按钮点击事件
    $scope.handleQueryCourse = function() {
        $scope.query();
    };
    // 处理【上架课程】按钮点击事件
    $scope.handleUpCourse = function(status) {
        var ids = getSelectedIds();
        $scope.changeStatus(ids, status, '确定上架所选课程？');
    };
    // 处理【下架课程】按钮点击事件
    $scope.handleDownCourse = function(status) {
        var ids = getSelectedIds();
        $scope.changeStatus(ids, status, '确定下架所选课程？');
    };
    // 处理【删除课程】按钮点击事件
    $scope.handleDelCourse = function(status) {
        var ids = getSelectedIds();
        $scope.changeStatus(ids, status, '确定删除所选课程？');
    };
    // 处理【同步叮当】按钮点击事件
    $scope.handleImportClass = function() {
        var ids = getSelectedIds();
        $scope.synToDoubleCourse(ids, null, '确定同步所选课程到叮当？');
        // $scope.query();
    };
    // 处理【自动排课】按钮点击事件
    $scope.handleAutoSchedule = function() {
        var ids = getSelectedIds();
        $scope.changeStatus(ids, status);
    };

    // 处理课程表单【取消】按钮点击事件
    $scope.handleModalCancel = function() {
        $('#erpMTCoursePanel').modal('hide');
    };

    // 处理课程表单【确认】按钮点击事件
    $scope.handleModalConfirm = function() {
        if ($scope.optype == 'add') {
            $scope.add();
        } else {
            $scope.put();
        }
        $('#erpMTCoursePanel').modal('hide');
    };

    // 添加
    $scope.add = function() {
        var waitingModal = $uibMsgbox.waiting('操作中，请稍候...')
        erp_mtcourseService.add($scope.courseDetail, function(resp) {
            waitingModal.close();
            if (!resp.error) {
                if (resp.message) {
                    $uibMsgbox.success(resp.message);
                }
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 修改
    $scope.put = function() {
        var waitingModal = $uibMsgbox.waiting('操作中，请稍候...')
        erp_mtcourseService.update($scope.courseDetail, function(resp) {
            waitingModal.close();
            if (!resp.error) {
                if (resp.message) {
                    $uibMsgbox.success(resp.message);
                }
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 查询方法
    $scope.query = function() {
        erp_mtcourseService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            mtcourse_type: $scope.searchParam.mtcourse_type,
            status: $scope.searchParam.status,
            course_name: $scope.searchParam.course_name,
            teacher_id: $scope.searchParam.teacher_id,
            branch_id: $scope.searchParam.branch_id
        }, function(resp) {
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.courseList = resp.data;
                $scope.onCourseCheckedChange()
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 同步双师
    $scope.synToDoubleCourse = function(ids) {
        if (ids) {
            erp_mtcourseService.synToDoubleCourse({ "doubleCourseIds": ids }, function(resp) {
                if (!resp.error) {
                    if (resp.message) {
                        $uibMsgbox.success(resp.message);
                    }
                    $scope.query();
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    };

    $scope.querySelectDatas = function(id) {
        erp_mtcourseService.querySelectDatas({
            id: id
        }, function(resp) {
            if (!resp.error) {
                $scope.lastCourseList = resp.data;
            }
        });
    };


    /*
     * 修改状态
     * @param flag true-生效 false-无效
     */
    $scope.changeStatus = function(ids, flag, msg) {
        if (ids) {
            if (msg) {
                $uibMsgbox.confirm(msg, function(res) {
                    if (res == 'yes') {
                        erp_mtcourseService.changeStatus({ "ids": ids, "status": flag }, function(resp) {
                            if (!resp.error) {
                                $uibMsgbox.alert("操作成功!");
                                $scope.query();
                            } else {
                                $uibMsgbox.error(resp.message)
                            }
                        });
                    }
                })
            } else {
                erp_mtcourseService.changeStatus({ "ids": ids, "status": flag }, function(resp) {
                    if (!resp.error) {
                        $uibMsgbox.alert("操作成功!");
                        $scope.query();
                    } else {
                        $uibMsgbox.error(resp.message)
                    }
                });
            }
        }
    }

    $scope.onStatusChange = function(course) {
        $scope.changeStatus(course.id, course.status);
    }


    // 重置表单
    $scope.resetForm = function() {
        $scope.courseDetail = {
            status: 1 //课程状态，默认：1 上架
        };
    };

    function queryTeacher() {
        var param = {};
        if ($scope.queryParam.search_info) {
            param.search_info = $scope.queryParam.search_info;
        }
        $scope.isLoadingTeacherList = 'isLoadingTeacherList';
        $scope.queryParam.teacherList = [];
        erp_teacherService.page(param, function(resp) {
            $scope.isLoadingTeacherList = '';
            if (!resp.error) {
                $scope.queryParam.teacherList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.changeSearchInfo = function() {
        queryTeacher();
    }

    $scope.selectCourseTeacher = function(teacher) {
        $scope.courseDetail.teacher_id = teacher.id;
        $scope.courseDetail.teacher_code = teacher.encoding;
        $scope.courseDetail.teacher_name = teacher.teacher_name;
    }


    // 业务类型
    $scope.courseType = function(type) {
            return getTypeName($scope.courseTypeList, type);
        }
        // 同步状态
    $scope.statusType = function(type) {
        return getTypeName($scope.sendStatusList, type);
    }

    // 获取某类型key对应的Value
    function getTypeName(typeArray, type) {
        var text = '';
        for (var i = 0; i < typeArray.length; i++) {
            if (type == typeArray[i].id) {
                text = typeArray[i].name;
            }
        }
        return text;
    }

    querySubject();
    queryGrade();
    queryBranchOrgs();
    queryTimeSeason();
    queryTeacher();

}