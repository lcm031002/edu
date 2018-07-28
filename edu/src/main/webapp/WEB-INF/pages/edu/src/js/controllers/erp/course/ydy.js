"use strict";
angular.module('ework-ui').controller('erp_courseYdyController', [
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
    erp_courseYdyController
]);

function erp_courseYdyController($rootScope,
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

    $scope.init = function() {

        $scope.searchParam = { // 搜索条件
            branch_id: -1
        };
        $scope.queryParam = {};

        $scope.selectAllFlag = false; //批量选中标识

        $scope.selectAllFlagForModal = false; //弹出框批量选中标识

        $scope.statusList = [{ "id": 1, "name": "启用" }, { "id": 2, "name": "停用" }]; //状态列表

        $scope.isAllList = [{ "id": 1, "name": "是" }, { "id": 0, "name": "否" }]; //是否应用所有校区

        $scope.courseList = []; // 课程列表

        //销售类型
        $scope.productTypeList = [{
                product_type: 1,
                product_type_name: '正价 '
            },
            {
                product_type: 2,
                product_type_name: '促销 '
            },
            {
                product_type: 3,
                product_type_name: '赠送 '
            }
        ];

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
        queryGrade();
        queryBranchOrgs();
    }


    // 修改
    $scope.updateCourse = function(course) {
        var courseDetail = {
            id: course.id,
            course_no: course.course_no,
            course_name: course.course_name,
            grade_id: course.grade_id,
            unit_price: course.unit_price,
            start_date: course.start_date,
            end_date: course.end_date,
            hour_len: course.hour_len,
            description: course.description,
            business_type: 2,
            branchInfos: course.branchInfos,
            product_type: course.product_type,
            is_all: course.is_all
        };
        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'ydy_modal.html',
            controller: 'erp_ydyModalController',
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
            $uibMsgbox.alert("修改成功");
        }, function(reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };

    $scope.handleImportClass = function() {
            // 跳转到一对一课程导入页面
            $state.go('ordersMgrCourseYDYInput', {
                path: '/orders/ordersMgr/ordersMgrCourseYDYInput',
                href: 'templates/erp/course/fileUpload_ydy.html'
            })
        }
        // 添加
    $scope.addCourse = function() {
        var courseDefault = {};
        courseDefault.branch_id = $scope.searchParam.branch_id;
        courseDefault.start_date = new Date();
        courseDefault.end_date = new Date();
        courseDefault.course_no = '服务端自动生成';
        courseDefault.business_type = 2;
        courseDefault.is_all = $scope.searchParam.is_all;
        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'ydy_modal.html',
            controller: 'erp_ydyModalController',
            scope: $scope,
            resolve: {
                params: function() {
                    return {
                        optype: "添加",
                        courseDetail: courseDefault,
                        selectAllFlagForModal: $scope.selectAllFlagForModal
                    }
                }
            }
        });
        modalInstance.result.then(function(result) {
            $scope.query();
            $uibMsgbox.alert("添加成功");
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
            business_type: 2,
            branch_id: $scope.searchParam.branch_id,
            status: $scope.searchParam.status,
            course_name: $scope.searchParam.course_name,
            grade_id: $scope.searchParam.grade_id
        }, function(resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.courseList = resp.data;
                //拼接校区显示字段
                for (var i in $scope.courseList) {
                    var temp = "";
                    for (var j in $scope.courseList[i].branchInfos) {
                        temp += ("," + $scope.courseList[i].branchInfos[j].org_name);
                    }
                    $scope.courseList[i].branch_names = temp.substr(1);
                }
            } else {
                $uibMsgbox.error(resp.message);
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.courseList = [];
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
            if (confirm("是否确认上架/下架")) {
                erp_courseService.changeStatus({ "ids": ids, "status": status }, function(resp) {
                    if (!resp.error) {
                        $scope.query();
                        $uibMsgbox.alert("操作成功");
                    } else {
                        $uibMsgbox.error(resp.message);
                    }
                });
            }
        }
        /**
         * 批量上架，下架, 删除
         * @param opt 1表示上架，2表示下架 ，0表示删除
         */
    $scope.batchChangeStatus = function(status) {
        var ids = $scope.getSelectedIds();
        if (!ids) {
            $uibMsgbox.error("未选中记录");
            return;
        }
        var status = status.toString();
        if (status == 0) {
            if (confirm("是否确认删除")) {
                erp_courseService.changeStatus({ "ids": ids, "status": status }, function(resp) {
                    if (!resp.error) {
                        $scope.query();
                        $uibMsgbox.alert("操作成功");
                    } else {
                        $uibMsgbox.error(resp.message);
                    }
                });
            }
        } else {
            if (confirm("是否确认上架/下架")) {
                erp_courseService.changeStatus({ "ids": ids, "status": status }, function(resp) {
                    if (!resp.error) {
                        $scope.query();
                        $uibMsgbox.alert("操作成功");
                    } else {
                        $uibMsgbox.error(resp.message);
                    }
                });
            }
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
    $scope.init();
}
angular.module('ework-ui')
    .controller('erp_ydyModalController', [
        '$scope',
        '$uibModalInstance',
        '$uibMsgbox',
        'erp_courseService',
        'params',
        erp_ydyModalController
    ])

function erp_ydyModalController($scope, $uibModalInstance, $uibMsgbox, erp_courseService, params) {
    $scope.optype = params.optype;
    $scope.relationBranchList = [];
    angular.copy($scope.$parent.branchList, $scope.relationBranchList);
    $scope.gradeList = $scope.$parent.gradeList;

    if ($scope.optype == "添加") {
        //设置默认值
        if($scope.gradeList[0]){
            params.courseDetail.grade_id = $scope.gradeList[0].id;
        }
        if (params.courseDetail.branch_id) {
            $.each($scope.relationBranchList, function(i, n) {
                if (params.courseDetail.branch_id == n.id) {
                    n.selectFlag = true;
                }
            });
        }
    }
    $scope.courseDetail = params.courseDetail;
    if ($scope.optype == "修改") {
        //如果是修改操作，遍历已经关联的校区，将校区复选框选上
        if ($scope.courseDetail.branchInfos) {
            for (var i in $scope.courseDetail.branchInfos) {
                for (var j in $scope.relationBranchList) {
                    if ($scope.relationBranchList[j].id == $scope.courseDetail.branchInfos[i].id) {
                        $scope.relationBranchList[j].selectFlag = true;
                    }
                }
            }
        }
    }
    //获取所有选中的id
    $scope.getSelectedIds = function() {
            var selectFlag = null;
            var i = null;
            var relationIds = "";
            for (i in $scope.relationBranchList) {
                selectFlag = $scope.relationBranchList[i].selectFlag;
                if (selectFlag) {
                    relationIds += ("," + $scope.relationBranchList[i].id);
                }
            }
            return relationIds.substring(1);
        }
        /**
         * 模态框取消
         */
    $scope.handleModalCancel = function() {
        $uibModalInstance.dismiss('cancel')
    }
    $scope.switchSelectAll = function(event) {
            for (var i in $scope.relationBranchList) {
                $scope.relationBranchList[i].selectFlag = event.target.checked;
            }
        }
        /**
         * 模态框确认
         */
    $scope.handleModalConfirm = function() {
        if (!checkStartEndTime($scope.courseDetail.start_date, $scope.courseDetail.end_date)) {
            $uibMsgbox.warn("结课日期小于开课日期");
            return;
        }
        if ($scope.courseDetail.unit_price < 0) {
            $uibMsgbox.error("课程单价不允许为负数");
            return;
        }
        $scope.courseDetail.relationIds = $scope.getSelectedIds();
        $scope.courseDetail.branchInfos = [];
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
    }
}