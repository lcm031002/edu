"use strict";
angular.module('ework-ui').controller('erp_courseSettlementController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    '$state',
    '$uibModal',
    'erp_courseService',
    'erp_subjectService',
    'erp_gradeService',
    'erp_studentBuOrgsService',
    'erp_TeacherSearchService',
    'erp_timeSeasonService',
    'PUBORGSelectedService',
    'erp_teacherService',
    'erp_CoopOrgService',
  erp_courseSettlementController
]);

function erp_courseSettlementController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $state,
    $uibModal,
    erp_courseService,
    erp_subjectService,
    erp_gradeService,
    erp_studentBuOrgsService,
    erp_TeacherSearchService,
    erp_timeSeasonService,
    PUBORGSelectedService,
    erp_teacherService,
    erp_CoopOrgService
) {
    $scope.checkAllFlag = false;
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索课程名称
    $scope.searchParam = {
        course_Name: '',
        branch_id: -1
    };
    $scope.queryParam = {};

    //状态列表
    $scope.statusList = [{ "id": 1, "name": "启用" },
        { "id": 2, "name": "停用" }
    ];

    // 课程列表
    $scope.courseList = [];

    $scope.coopOrgList = [];

    $scope.selectedCourseList = []

    // 与表单绑定的数据，用于添加和修改
    $scope.courseDetail = {
        id: '',
        course_name: '',
        encoding: '',
        last_id: '',
        last_encoding: '',
        last_name: '',
        sort: '',
        description: '',
        is_cramclass: 0,
        is_textbooks: 0
    };

    // 是否教材
    $scope.is_textbooksList = [{ "id": 1, "value": "是" }, { "id": 0, "value": "否" }];
    // 是否补课
    $scope.is_cramclassList = [{ "id": 1, "value": "是" }, { "id": 0, "value": "否" }];


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

    function getSelectedCourseList() {
        var courseList = [];
        _.forEach($scope.courseList, function(course) {
            if (course.selectFlag) {
                courseList.push(course)
            }
        })
        return courseList;
    }

    //获取所有选中的id
    function getSelectedIds() {
        var ids = "";
        _.forEach($scope.courseList, function(course) {
            if (course.selectFlag) {
                ids += "," + course.id;
            }
        })
        if (!ids) {
            $uibMsgbox.alert('请选择要操作的课程！');
        }
        return ids.substring(1);
    }

    // 全选事件
    $scope.onCheckAll = function() {
        _.forEach($scope.courseList, function(course) {
            course.selectFlag = $scope.checkAllFlag;
        })
    }

    // 某一行Checkbox选择事件
    $scope.onCourseChecked = function(course) {
        $scope.checkAllFlag = _.every($scope.courseList, { selectFlag: true });
    }

    $scope.handleImportClass = function() {
        // 跳转到班级课导入页面
        $state.go('ordersMgrCourseInput', {
            path: '/orders/ordersMgr/ordersMgrCourseInput',
            href: 'templates/erp/course/fileUpload_bjk.html'
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
     * 查询年级
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
     * 查询校区
     */
    function queryBuOrgs() {
        erp_studentBuOrgsService.query({}, function(resp) {
            if (!resp.error) {
                var data = resp.data;
                if (data && data.length) {
                    $scope.branchList = resp.data;
                    $scope.modalBranchList = _.cloneDeep(resp.data);
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

    // 处理【添加课程】按钮点击事件
    $scope.handleAddCourse = function() {
        $scope.optype = 'add';
        $scope.resetForm();
        //        $('#erpCoursePanel').modal('show');
        $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'templates/block/modal/course-course.modal.html',
            controller: 'erp_courseModalController',
            scope: $scope,
            resolve: {
                saveCourse: function() {
                    return $scope.add;
                }
            }
        }).result.then(function(detail) {
            // $scope.add();
            $scope.query();
        }, function() {});
    };

    $scope.performanceBelongList = [{
        value: '代办校区',
        id: 1
    }, {
        value: '课程校区',
        id: 2
    }, ];

    // 处理【修改课程】按钮点击事件
    $scope.handlePutCourse = function(course) {
        $scope.optype = 'put';
        angular.copy(course, $scope.courseDetail);
        $scope.courseDetail.is_cramclass = 0;
        if ($scope.courseDetail.more_teacher_courseid != null) {
            $scope.courseDetail.is_cramclass = 1;
        }
        //        $("#erpCoursePanel").modal('show');
        $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'templates/block/modal/course-course.modal.html',
            controller: 'erp_courseModalController',
            scope: $scope,
            resolve: {
                saveCourse: function() {
                    return $scope.put
                }
            }
        }).result.then(function(detail) {
            // $scope.put();
            $scope.query();
        }, function() {});
    };

    // 处理【查询课程】按钮点击事件
    $scope.handleQueryCourse = function() {
        $scope.query();
    };
    // 处理【上架课程】按钮点击事件
    $scope.handleUpCourse = function(status) {
        var ids = getSelectedIds();
        $scope.changeStatus(ids, status);
        $scope.query();
    };
    // 处理【下架课程】按钮点击事件
    $scope.handleDownCourse = function(status) {
        var ids = getSelectedIds();
        $scope.changeStatus(ids, status);
        $scope.query();
    };
    // 处理【自动排课】按钮点击事件
    $scope.handleAutoSchedule = function() {
        var ids = getSelectedIds();
        $scope.toAutoCourseScheduling(ids);
    };

    // 处理课程表单【取消】按钮点击事件
    $scope.handleModalCancel = function() {
        $('#erpSystemCommonCoursePanel').modal('hide');
    };

  // 处理【确认】按钮点击事件
  $scope.handleModalConfirm = function () {
    erp_courseService.changeSettlement($scope.courseDetail, function (resp) {
      if (!resp.error) {
        $uibMsgbox.success('修改成功！');
        $scope.query();
      } else {
        $uibMsgbox.error(resp.message);
      }
    })

    $('#erpSystemCommonCoursePanel').modal('hide');
  };

    // 添加
    $scope.add = function(courseDetail) {
        $scope.courseDetail.business_type = 1; //班级课
        return erp_courseService.add(courseDetail || $scope.courseDetail, function(resp) {
            return resp;
        });
    };

    // 修改
    $scope.put = function() {
        return erp_courseService.update($scope.courseDetail, function(resp) {
            return resp
        });
    };

    // 查询方法
    $scope.query = function() {
        $scope.loadStatues = true;
        erp_courseService.queryAll({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            business_type: 1,
            branch_id: $scope.searchParam.branch_id,
            season_id: $scope.searchParam.timeSeason_id,
            status: $scope.searchParam.status,
            course_name: $scope.searchParam.course_name,
            teacher_id: $scope.searchParam.teacher_id,
            assteacher_id: $scope.searchParam.assteacher_id,
            subject_id: $scope.searchParam.subject_id,
            grade_id: $scope.searchParam.grade_id
        }, function(resp) {
            if (!resp.error) {
                $scope.loadStatues = false;                
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.courseList = resp.data;
                $scope.onCourseChecked();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.querySelectDatas = function(id) {
        erp_courseService.querySelectDatas({
            id: id
        }, function(resp) {
            if (!resp.error) {
                $scope.lastCourseList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };


    /*
     * 修改状态
     * @param flag true-生效 false-无效
     */
    $scope.changeStatus = function(id, flag) {
        if (id) {
            erp_courseService.changeStatus({ "ids": id, "status": flag.toString() }, function(resp) {
                if (!resp.error) {
                    $uibMsgbox.alert("操作成功!");
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    }

    $scope.onStatusChange = function(course) {
        $scope.changeStatus(course.id, course.status);
    }


    // 重置表单
    $scope.resetForm = function() {
        $scope.courseDetail = {};
    };

    function queryTeacher() {
        var param = {};
        if ($scope.queryParam.search_info) {
            param.search_info = $scope.queryParam.search_info;
        }
        $scope.isLoadingTeacherList = 'isLoadingTeacherList';
        $scope.queryParam.teacherList = [];
        erp_teacherService.query(param, function(resp) {
            $scope.isLoadingTeacherList = '';
            if (!resp.error) {
                $scope.queryParam.teacherList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

  function queryCoopOrg() {
    erp_CoopOrgService.queryList({}, function(resp) {
      if (!resp.error) {
        $scope.coopOrgList = resp.data;
      } else {
        $uibMsgbox.error(resp.message);
      }
    })
  }

    $scope.changeSearchInfo = function() {
        queryTeacher();
    }

    $scope.selectCourseTeacher = function(teacher) {
        $scope.courseDetail.teacher_id = teacher.id;
        $scope.courseDetail.teacher_code = teacher.encoding;
        $scope.courseDetail.teacher_name = teacher.teacher_name;
    }
    $scope.handleExportExcel = function() {
        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');
        erp_courseService.exportExcel({
            business_type: 1,
            branch_id: $scope.searchParam.branch_id,
            season_id: $scope.searchParam.timeSeason_id,
            status: $scope.searchParam.status,
            course_name: $scope.searchParam.course_name,
            teacher_id: $scope.searchParam.teacher_id,
            assteacher_id: $scope.searchParam.assteacher_id,
            subject_id: $scope.searchParam.subject_id,
            grade_id: $scope.searchParam.grade_id
        }, function(resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载excel
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

  // 处理【修改】按钮点击事件
  $scope.handlePutCourse = function (course) {
    $scope.courseDetail = {
      id: course.id,
      course_name: course.course_name,
      settlement_ratio: course.settlement_ratio
    };
    $("#erpSystemCommonCoursePanel").modal('show');
  };

    querySubject();
    queryGrade();
    queryBuOrgs();
    queryTimeSeason();
    queryTeacher();
    queryCoopOrg();
}