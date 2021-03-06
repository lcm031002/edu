angular.module('ework-ui')
    .controller('erp_fileUploadWfdModalController', [
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
        'course',
        erp_fileUploadWfdModalController
    ]);

function erp_fileUploadWfdModalController($rootScope,
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


    $scope.searchParam = {
        bu_id: '',
        order_no: '',
        branch_id: '',
        business_type: 3,
        student_name: ''
    };

    $scope.queryParam = {};

    $scope.handleModalCancel = function () {
        $uibModalInstance.dismiss('cancel');
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
        if(!$scope.courseDetail.unit_price) {
            $uibMsgbox.error("单价必填！");
            return false;
        }
        if(!$scope.courseDetail.start_date) {
            $uibMsgbox.error("开课日期必填！");
            return false;
        }
        if(!$scope.courseDetail.end_date) {
            $uibMsgbox.error("结课日期必填！");
            return false;
        }
        if(!$scope.courseDetail.status) {
            $uibMsgbox.error("状态必填！");
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
            business_type: 3,
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
     * @param selectorValFiled 下拉框值绑定的字段
     * @param selectorShowFiled 下拉框显示label绑定的字段
     * @param selectorList 下拉框名值对集合
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


    querySubject();
    queryGrade();
    queryBuOrgs();
    queryTimeSeason();
}
