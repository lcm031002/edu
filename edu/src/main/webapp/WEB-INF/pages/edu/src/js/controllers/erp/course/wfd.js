"use strict";
angular.module('ework-ui').controller('erp_courseWfdController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_courseService',
    'erp_gradeService',
    'erp_studentBuOrgsService',
    'PUBORGSelectedService',
    'erp_teacherService',
    erp_courseWfdController
]);

function erp_courseWfdController($rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_courseService,
    erp_gradeService,
    erp_studentBuOrgsService,
    PUBORGSelectedService,
    erp_teacherService) {
    // 搜索课程名称
    $scope.searchParam = {
        branch_id: -1
    };
    //批量选中标识
    $scope.selectAllFlag = false;

    //状态列表
    $scope.statusList = [{ "id": 1, "name": "启用" },
        { "id": 2, "name": "停用" }
    ];

    // 课程列表
    $scope.courseList = [];

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
            }
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

    // 修改
    $scope.updateCourse = function(course) {
        var courseDetail = {
            id: course.id,
            course_no: course.course_no,
            course_name: course.course_name,
            branch_id: course.branch_id,
            grade_id: course.grade_id,
            start_date: course.start_date,
            end_date: course.end_date,
            unit_price: course.unit_price,
            business_type: 3,
            description: course.description,
            product_line: course.product_line,
            performance_belong_type: course.performance_belong_type

        };
        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'wfd_modal.html',
            controller: 'erp_wfdModalController',
            scope: $scope,
            resolve: {
                params: function() {
                    return {
                        optype: "修改",
                        courseDetail: courseDetail
                    }
                }
            }
        });
        modalInstance.result.then(function(result) {
            $scope.query();
            $uibMsgbox.alert("操作成功");
        }, function(reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };

    $scope.handleImportClass = function() {
            // 跳转到晚辅导课导入页面
            $state.go('ordersMgrCourseWFDInput', {
                path: '/orders/ordersMgr/ordersMgrCourseWFDInput',
                href: 'templates/erp/course/fileUpload_wfd.html'
            })
        }
        // 添加
    $scope.addCourse = function() {
        var courseDefault = {};
        courseDefault.branch_id = $scope.searchParam.branch_id;
        courseDefault.start_date = new Date();
        courseDefault.end_date = new Date();
        courseDefault.course_no = '服务端自动生成';
        courseDefault.grade_id = 100000182; //默认为晚辅导
        courseDefault.business_type = 3;
        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'wfd_modal.html',
            controller: 'erp_wfdModalController',
            scope: $scope,
            resolve: {
                params: function() {
                    return {
                        optype: "添加",
                        courseDetail: courseDefault
                    }
                }
            }
        });
        modalInstance.result.then(function(result) {
            $scope.query();
            $uibMsgbox.alert("操作成功");
        }, function(reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };

    // 查询方法
    $scope.query = function() {
        $scope.loadStatues = true;
        erp_courseService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            business_type: 3,
            branch_id: $scope.searchParam.branch_id,
            status: $scope.searchParam.status,
            course_name: $scope.searchParam.course_name,
            grade_id: $scope.searchParam.grade_id
        }, function(resp) {
            $scope.loadStatues = false;
            $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
            $scope.courseList = resp.data;
            if (resp.error) {
                $uibMsgbox.error(resp.message);
            }
            $scope.selectAllFlag = false;
        });
    };

    /*
     * 单记录上架下架
     * @param flag true-生效 false-无效
     */
    $scope.onStatusChange = function(course) {
            var ids = course.id.toString();
            var status = course.status.toString();
            course.status = course.status == 1 ? 2 : 1;
            $uibMsgbox.confirm('确认上架/下架？', function(res) {
                if (res == 'yes') {
                    erp_courseService.changeStatus({ "ids": ids, "status": status }, function(resp) {
                        if (!resp.error) {
                            $scope.query();
                            $uibMsgbox.alert("操作成功");
                        } else {
                            $uibMsgbox.error(resp.message);
                        }
                    });
                }
            });
        }
        /**
         * 批量上架，下架, 删除
         * @param opt 1表示上架，2表示下架 ，0表示删除
         */
    $scope.batchChangeStatus = function(status) {
        var ids = $scope.getSelectedIds();
        if (!ids) {
            $uibMsgbox.warn("未选中记录");
            return;
        }
        var status = status.toString();
        if (status == 0) {
            $uibMsgbox.confirm('确认删除？', function(res) {
                if (res == 'yes') {
                    erp_courseService.changeStatus({ "ids": ids, "status": status }, function(resp) {
                        if (!resp.error) {
                            $scope.query();
                            $uibMsgbox.alert("操作成功");
                        } else {
                            $uibMsgbox.error(resp.message);
                        }
                    });
                }
            });
        } else {
            $uibMsgbox.confirm('确认上架/下架？', function(res) {
                if (res == 'yes') {
                    erp_courseService.changeStatus({ "ids": ids, "status": status }, function(resp) {
                        if (!resp.error) {
                            $scope.query();
                            $uibMsgbox.alert("操作成功");
                        } else {
                            $uibMsgbox.error(resp.message);
                        }
                    });
                }
            });
        }
    };

    //多选监控
    $scope.$watch('selectAllFlag', function(newValue, oldValue) {
        var i;
        if (newValue == true) {
            for (i in $scope.courseList) {
                $scope.courseList[i].selectItemFlag = true;
            }
        } else {
            for (i in $scope.courseList) {
                $scope.courseList[i].selectItemFlag = false;
            }
        }
    });
    //获取所有选中的id
    $scope.getSelectedIds = function() {
        var selectItemFlag = null;
        var i = null;
        var syncTeacherIds = "";
        for (i in $scope.courseList) {
            selectItemFlag = $scope.courseList[i].selectItemFlag;
            if (selectItemFlag) {
                //拼接需要同步到叮当的教师id
                syncTeacherIds += ("," + $scope.courseList[i].id);
            }
        }
        return syncTeacherIds.substring(1);
    }

    queryGrade();
    queryBranchOrgs();
}
angular.module('ework-ui')
    .controller('erp_wfdModalController', [
        '$scope',
        '$uibModalInstance',
        '$uibMsgbox',
        'erp_courseService',
        'params',
        erp_wfdModalController
    ])

function erp_wfdModalController($scope, $uibModalInstance, $uibMsgbox, erp_courseService, params) {
    $scope.optype = params.optype;

    $scope.branchList = [];
    angular.copy($scope.$parent.branchList, $scope.branchList);
    $scope.branchList.shift();
    $scope.gradeList = $scope.$parent.gradeList;
    if ($scope.optype == "添加") {
        //如果是添加操作，默认选中第一个校区
        if($scope.branchList[0]){
            params.courseDetail.branch_id = $scope.branchList[0].id;
        }
        if($scope.gradeList[0]){
            params.courseDetail.grade_id = $scope.gradeList[0].id;
        }
    }
    $scope.courseDetail = params.courseDetail;

    $scope.handleModalCancel = function() {
        $uibModalInstance.dismiss('cancel')
    }
    $scope.handleModalConfirm = function() {
        $scope.disabled = true;
        if ($scope.optype == "修改") {
            //更新数据
            erp_courseService.update($scope.courseDetail, function(resp) {
                if (!resp.error) {
                    $uibModalInstance.close("success")
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        } else {
            //添加数据
            erp_courseService.add($scope.courseDetail, function(resp) {
                if (!resp.error) {
                    $uibModalInstance.close("success")
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
        $scope.disabled = false;
    }
}