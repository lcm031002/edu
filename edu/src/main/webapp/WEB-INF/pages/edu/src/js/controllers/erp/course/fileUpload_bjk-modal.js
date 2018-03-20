angular.module('ework-ui')
    .controller('erp_fileUploadBjkModalController', [
        '$rootScope',
        '$scope',
        '$uibModalInstance',
        '$uibMsgbox',
        '$filter',
        'uibDateParser',
        'erp_subjectService',
        'erp_gradeService',
        'erp_studentBuOrgsService',
        'erp_timeSeasonService',
        'PUBORGSelectedService',
        'erp_teacherService',
        'erp_courseInputService',
        'erp_organizationService',
        'course',
        erp_fileUploadBjkModalController
    ]);

function erp_fileUploadBjkModalController($rootScope,
                                          $scope,
                                          $uibModalInstance,
                                          $uibMsgbox,
                                          $filter,
                                          uibDateParser,
                                          erp_subjectService,
                                          erp_gradeService,
                                          erp_studentBuOrgsService,
                                          erp_timeSeasonService,
                                          PUBORGSelectedService,
                                          erp_teacherService,
                                          erp_courseInputService,
                                          erp_organizationService,
                                          course) {
    // 初始化课程信息
    $scope.courseDetail = angular.copy(course);
    // 组织架构
    
    // 地区列表
    $scope.areaList = [];
    // 团队列表
    $scope.buList = [];
    // 校区列表
    $scope.branchList = [];

    //$rootScope.$watch('orgRootNode', function (newVal, oldVal) {
    //    initAreaList();
    //    initBuList();
    //    initBranchList();
    //});
    // 年级
    $scope.gradeList = [];
    // 科目
    $scope.subjectList = [];
    // 课程季
    $scope.timeSeasonList = [];
    // 是否补课
    $scope.is_cramclassList = [{"id": 1, "value": "是"}, {"id": 0, "value": "否"}];
    // 是否教材
    $scope.is_textbooksList = [{"id": 1, "value": "是"}, {"id": 0, "value": "否"}];
    // 上架/下架
    $scope.saleList = [{"id": 1, "value": "上架"}, {"id": 2, "value": "下架"}];
    //销售类型
    $scope.productTypeList = [{"id": 1, "value": "正价"}, {"id": 2, "value": "促销"}, {"id": 3, "value": "赠送"}];
    //产品线
    // $scope.productLineList = [
    //     {"id": 1, "value": "大小班"}, {"id": 2, "value": "个性化"},
    //     {"id": 3, "value": "艺考班"}, {"id": 4, "value": "小学部"},
    //     {"id": 5, "value": "中厦合作"}, {"id": 6, "value": "承诺班"},
    //     {"id": 7, "value": "【南昌】小学部"}, {"id": 8, "value": "合作项目"},
    //     {"id": 9, "value": "雅思项目"}
    // ];


    $scope.searchParam = {
        bu_id: '',
        order_no: '',
        branch_id: '',
        business_type: 1,
        student_name: ''
    };

    $scope.queryParam = {};

    $scope.selectCourseTeacher = function(teacher){
        $scope.courseDetail.teacher_id = teacher.id;
        $scope.courseDetail.teacher_code = teacher.encoding;
        $scope.courseDetail.teacher_name = teacher.teacher_name;
        $scope.courseDetail.errorMsgObj.teacher_code = '';
    };

    $scope.selectAssTeacher = function (teacher) {
        $scope.courseDetail.assteacher_name = teacher.teacher_name;
        $scope.courseDetail.assteacher_code = teacher.encoding;
        $scope.courseDetail.assteacher_id = teacher.id;
        $scope.courseDetail.errorMsgObj.assteacher_code = '';
    };

    // 移除辅导老师-培英
    $scope.removeAssTeacher = function () {
        $scope.courseDetail.assteacher_name = null;
        $scope.courseDetail.assteacher_code = null;
        $scope.courseDetail.assteacher_id = null;
        $scope.courseDetail.errorMsgObj.assteacher_code = '';
    };

    $scope.handleModalCancel = function () {
        $uibModalInstance.dismiss($scope.courseDetail);
    };
    $scope.handleModalConfirm = function () {
        if(!$scope.courseDetail.course_no) {
            $uibMsgbox.error("课程编码必填！");
            return false;
        }
        if(!$scope.courseDetail.course_name) {
            $uibMsgbox.error("课程名称必填！");
            return false;
        }
        //if(!$scope.courseDetail.city_id) {
        //    $uibMsgbox.error("地区必填！");
        //    return false;
        //}
        if(!$scope.courseDetail.branch_id) {
            $uibMsgbox.error("校区必填！");
            return false;
        }
        if(!$scope.courseDetail.grade_id) {
            $uibMsgbox.error("年级必填！");
            return false;
        }
        if(!$scope.courseDetail.season_id || $scope.courseDetail.season_id == -1) {
            $uibMsgbox.error("课程季必填！");
            return false;
        }
        if(!$scope.courseDetail.subject_id) {
            $uibMsgbox.error("科目必填！");
            return false;
        }
        if(!$scope.courseDetail.attend_class_period) {
            $uibMsgbox.error("上课周期必填！");
            return false;
        } else {
            var rule =/^([1-7](~\d{2}:\d{2}-\d{2}:\d{2})?,)*([1-7](~\d{2}:\d{2}-\d{2}:\d{2})?)$/;
            var re = new RegExp(rule);
            if (!re.test($scope.courseDetail.attend_class_period)) {
                $uibMsgbox.error("上课周期格式错误！<br>例1：周一、周三，请输入： 1,3; <br>例2：周一 08:00-12:00、周三，请输入：1~08:00-12:00,3");
                return false;
            }
        }
        if(!$scope.courseDetail.start_date) {
            $uibMsgbox.error("开课日期必填！");
            return false;
        }
        if(!$scope.courseDetail.end_date) {
            $uibMsgbox.error("结课日期必填！");
            return false;
        }
        if(!$scope.courseDetail.start_time) {
            $uibMsgbox.error("上课时间必填！");
            return false;
        }
        if(!$scope.courseDetail.end_time) {
            $uibMsgbox.error("下课时间必填！");
            return false;
        }
        if(!$scope.courseDetail.course_count) {
            $uibMsgbox.error("课时数量必填！");
            return false;
        }
        if(!$scope.courseDetail.unit_price) {
            $uibMsgbox.error("课时单价必填！");
            return false;
        }
        if(!$scope.courseDetail.sum_price) {
            $uibMsgbox.error("课程总价必填！");
            return false;
        }
        if(!$scope.courseDetail.teacher_id) {
            $uibMsgbox.error("老师必填！");
            return false;
        }
        if(!$scope.courseDetail.assteacher_id && $scope.courseDetail.assteacher_code) {
            $uibMsgbox.error("辅导老师编号错误！");
            return false;
        }
        // if(!$scope.courseDetail.product_line) {
        //     $uibMsgbox.error("产品线必填！");
        //     return false;
        // }
        if(!$scope.courseDetail.status) {
            $uibMsgbox.error("状态必填！");
            return false;
        }
        if($scope.courseDetail.people_count<0) {
            $uibMsgbox.error("计划上课人数必须为整数！");
            return false;
        }
        for(var i=0; i<$scope.allCourseArray.length; i++) {
            //行号相同
            if($scope.allCourseArray[i].row_no == course.row_no) {
                continue;
            }
            if($scope.courseDetail.course_no == $scope.allCourseArray[i].course_no) {
                $uibMsgbox.error("文档中已存在此课程编码！");
                return false;
            }
        }
        // 系统中课程编码唯一性校验
        var param = {
            business_type: 1,
            course: $scope.courseDetail
        };
        erp_courseInputService.checkOne(param, function (resp) {
            if (!resp.error) {
                angular.copy($scope.courseDetail, course);
                course.validateStatus = 2;
                course.errorMsgObj = {};
                course.errorList = [];
                $uibModalInstance.close(course);
            } else {
                $uibMsgbox.error(resp.message);
            }
        })

    };
    /**
     * 改变下拉框的值，将selectorShowFiled绑定到selectorDetail.valueFiled中
     * @param selectorDetail 下拉框绑定的对象
     * @param selectorList 下拉框名值对集合
     * @param selectorValFiled 下拉框值绑定的字段
     * @param selectorShowFiled 下拉框显示label绑定的字段
     * @param valueFiled 如果存在，则获取集合中选中对象的valueFiled值
     */
    $scope.changeSelector = function(selectorDetail,selectorList,selectorValFiled,selectorShowFiled,valueFiled) {
        selectorDetail.errorMsgObj[selectorShowFiled] = "";
        valueFiled = valueFiled?valueFiled:'value';
        selectorDetail[selectorShowFiled] = _.find(selectorList, {id:selectorDetail[selectorValFiled]})[valueFiled];
    }
    /**
     * 查询科目
     */
    function querySubject(){
        erp_subjectService.querySelectDatas({
            branch_id:$scope.searchParam.branch_id?$scope.searchParam.branch_id:-1,
            season_id:$scope.searchParam.season_id?$scope.searchParam.season_id:-1,
            grade_id:$scope.searchParam.grade_id?$scope.searchParam.selectedCourse.id:-1
        },function(resp){
            if(!resp.error){
                $scope.subjectList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }

    /**
     * 查询年级
     */
    function queryGrade(){
        erp_gradeService.querySelectDatas({
            branch_id:$scope.searchParam.branch_id?$scope.searchParam.branch_id:-1,
            season_id:$scope.searchParam.season_id?$scope.searchParam.season_id:-1,
        },function(resp){
            if(!resp.error){
                $scope.gradeList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }

    function queryBuOrgs() {
        erp_studentBuOrgsService.query({}, function (resp) {
            if (!resp.error) {
                $scope.branchList = resp.data;
                $scope.searchParam.branch_id = $scope.courseDetail.branch_id;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }

    //function querySelectedOrg() {
    //    PUBORGSelectedService.query({}, function (resp) {
    //        if (!resp.error) {
    //            $scope.selectedOrg = resp.data;
    //            if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {
    //                $.each($scope.branchList, function (i, b) {
    //                    if (b.id == $scope.selectedOrg.id) {
    //                        $scope.searchParam.branch_id = b.id;
    //                    }
    //                });
    //            } else {
    //                $uibMsgbox.warn("请选择校区!");
    //            }
    //        } else {
    //            $uibMsgbox.error(resp.message);
    //        }
    //    })
    //}

    /**
     * 查询课程季
     */
    function queryTimeSeason(){
        erp_timeSeasonService.list({
        },function(resp){
            if(!resp.error){
                $scope.timeSeasonList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.performanceBelongList = [
        {
            value:'代办校区',
            id:1
         },{
            value:'课程校区',
            id:2
         },
    ];

    $scope.changeSearchInfo = function(){
        queryTeacher();
    }

    function queryTeacher(){
        var param = {};
        if($scope.queryParam.search_info){
            param.search_info = $scope.queryParam.search_info
        }
        $scope.isLoadingTeacherList = 'isLoadingTeacherList';
        $scope.queryParam.teacherList = [];
        erp_teacherService.page(param,function(resp){
            $scope.isLoadingTeacherList = '';
            if(!resp.error){
                $scope.queryParam.teacherList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    }

    function initAreaList() {
        var node = $rootScope.orgRootNode
        if (node && node.children) {
            $scope.areaList = _.slice(node.children)
        }
    }

    function initBuList() {
        var list = $scope.areaList;
        var bList = [];
        _.forEach(list, function (item) {
            bList = _.concat(bList, item.children);
        })
        $scope.buList = bList
    }

    function initBranchList() {
        var list = $scope.buList;
        var bList = [];
        _.forEach(list, function (item) {
            bList = _.concat(bList, item.children);
        })
        $scope.branchList = bList
    }

    $scope.queryProductLine = function() {
        erp_organizationService.queryProductLine({}, function(resp) {
            if (!resp.error && resp.data && resp.data.length > 0) {
                //产品线
                $scope.productLineList = resp.data;
            }
        })
    }

    querySubject();
    queryGrade();
    queryBuOrgs();
    queryTimeSeason();
    queryTeacher();
    $scope.queryProductLine();
}
