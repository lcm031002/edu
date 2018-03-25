/**
 * Created by Liyong.zhu on 2017/2/12.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_orderChangeTransferController', [
        '$scope',
        '$log',
        '$q',
        '$state',
        '$uibMsgbox',
        'erp_studentsService',
        'erp_studentOrderCourseService',
        'erp_orderManagerService',
        'erp_courseService',
        'erp_studentBuOrgsService',
        'erp_gradeService',
        'erp_subjectService',
        'erp_timeSeasonService',
        'erp_courseTimesService',
        'erp_orderChangeService',
        erp_orderChangeTransferController]);

function erp_orderChangeTransferController(
    $scope,
    $log,
    $q,
    $state,
    $uibMsgbox,
    erp_studentsService,
    erp_studentOrderCourseService,
    erp_orderManagerService,
    erp_courseService,
    erp_studentBuOrgsService,
    erp_gradeService,
    erp_subjectService,
    erp_timeSeasonService,
    erp_courseTimesService,
    erp_orderChangeService) {

    //学员信息
    $scope.student = undefined;
    $scope.studentList = [];
    $scope.businessType = 1;
    $scope.transferOutTab='bjk';
    $scope.productLine = 1; // 产品线 1-培英精品班 2-个性化 11-佳音
    
    $scope.orderBJK = undefined;
    $scope.orderYDY = undefined;
    //转入信息
    $scope.transferIn = {};
    $scope.transferIn.branch_id = undefined;
    $scope.transferIn.selectedBranch = {id : -1};
    $scope.transferIn.selectedTimeSeason = {id : -1};
    $scope.transferIn.selectedGrade = {id : -1};
    $scope.transferIn.selectedSubject = {id : -1};
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
        onChange: function(){
            $scope.query();
        }
    };
    
    $scope.reloadPage = function() {
		location.reload();
	};

    /**
     * 下一步、上一步
     * @param before
     * @param next
     */
    $scope.nextStep = function(before,next) {
        if (next == 2 && before == 1) { //转出
            if ($scope.productLine == 2) {
                $scope.businessType = 2;
                $scope.transferOutTab = 'ydy';
            }
            $scope.queryCourse();
        }
        if (next == 3 && before == 2) { //转入
            if($scope.businessType == 1) {
                queryBuOrgs().then(function () {
                    return queryTimeSeason();
                }).then(function () {
                    return queryGrade();
                }).then(function () {
                    return querySubject();
                }).then(function () {
                    $scope.querySelectingCourse();
                });
            } else if($scope.businessType == 2) {
                $scope.queryTransInBranchList();
                $scope.transferIn.branch_id = $scope.selectedTransferOutCourse.branch_id;
            }
        }
        $scope.step = next;
    };


    /**
     * 选择学员
     * @param student
     */
    $scope.checkedStudent = function(student) {
        if(student.checked){
            student.checked = false;
            $scope.student = undefined;
            $scope.orderBJK = undefined;
            $scope.orderYDY = undefined;
            $scope.selectedTransferOutCourse = undefined;
            $scope.transferOutCourseTimesList = [];
            $scope.productLine = 1;
        }else{
            student.checked = true;
            $scope.student = student;
            $scope.productLine = student.product_line;

            if($scope.curStudent && $scope.curStudent.id != student.id){
                $scope.curStudent.checked = false;
            }
        }
        $scope.curStudent = $scope.student;
    };

    $scope.studentQueryInfo = {};

    function queryStudents() {
        var param = {};
        if($scope.studentId){
            param.studentId = $scope.studentId;
        }
        param.pageSize = 10;
        param.searchInfo = $scope.studentQueryInfo.searchInfo;
        $scope.isQueryStudent = 'isQueryStudent';
        $scope.student = undefined;
        $scope.curStudent = $scope.student;
        $scope.studentList = [];
        erp_studentsService.query(
            param,
            function(resp){
                $scope.isQueryStudent = '';
                if(!resp.error){
                    $scope.studentList = [];
                    if(resp.data && resp.data.length > 0) {
                        $scope.student = resp.data[0];
                        $scope.productLine = $scope.student.product_line;
                        if ($scope.studentId) {
                            $scope.student.checked = true;
                            $scope.curStudent = $scope.student;
                            $scope.studentList.push($scope.student);
                        } else {
                            $scope.student = null;
                            $scope.studentList = resp.data;
                        }
                    }else{
                        $scope.studentList = resp.data;
                    }
                }else{
                    $uibMsgbox.alert(resp.message);
                }
            });
    }

    $scope.queryCourse =  function (){
        if($scope.businessType == 1 && $scope.orderBJK || $scope.businessType == 2 && $scope.orderYDY) {
            return;
        }

        var param = {
            studentId : $scope.curStudent.id,
            businessType:$scope.businessType
        };

        if ($scope.businessType == 1) {
            $scope.isQueryBjk = 'isQueryBjk';
        } else if ($scope.businessType == 2) {
            $scope.isQueryYdy = 'isQueryYdy';
        }

        erp_studentOrderCourseService.queryOrderCourse(param,function(resp){

            $scope.isQueryBjk = '';
            $scope.isQueryYdy = '';

            if(!resp.error){
                if ($scope.businessType == 1) {
                    $scope.orderBJK = resp.data;
                    if($scope.orderDetailId){
                        $.each($scope.orderBJK,function(i,r){
                            if(r.id == $scope.orderDetailId){
                                $scope.checkTransferOutCourse($scope.orderBJK,r);

                            }
                        })
                    }
                } else if ($scope.businessType == 2) {
                    $scope.orderYDY = resp.data;
                    if($scope.orderDetailId){
                        $.each($scope.orderYDY,function(i,r){
                            if(r.id == $scope.orderDetailId){
                                $scope.checkTransferOutCourse($scope.orderYDY,r);
                            }
                        });
                    }
                }
            }else{
                $uibMsgbox.alert(resp.message);

            }
        });

    };

    $scope.selectTransferOutCourseTab = function(key){
        if($scope.selectedTransferOutCourse) {
            $uibMsgbox.warn("已选定转班课程，不可切换页签！");
            return;
        }
        $scope.transferOutTab = key;
        if(key=='bjk'){
            $scope.businessType = 1;
        }else if(key=='ydy'){
            $scope.businessType = 2;
        }
        $scope.queryCourse();
    };
    $scope.selectedTransferOutCourse = undefined;

    $scope.checkTransferOutCourse = function(courseList,course){
        $.each(courseList,function(i,cs){
            if(cs.ID != course.ID){
                cs.checked = undefined;
                $scope.selectedTransferOutCourse = undefined;
            }
        });
        $scope.transferOutCourseTimesList = [];
        if(course.checked){
            course.checked = false;
            $scope.selectedTransferOutCourse = undefined;
            delete course.transferOutCount;
        }else{
            course.checked = true;
            $scope.selectedTransferOutCourse = course;
            // 班级课，才查询
            if($scope.businessType == 1) {
                queryOrderChangeTimesInfo();
            }
        }

    };

    function queryOrderChangeTimesInfo(){
        var param = {};
        param.orderDetailId = $scope.selectedTransferOutCourse.id;
        $scope.isLoadingCourseTimesPanel = 'isLoadingCourseTimesPanel';
        $scope.selectedTransferOutCourse.orderChangeCourseTimes = undefined;
        erp_orderManagerService.orderCourseSurplusCount(param,function(resp){
            $scope.isLoadingCourseTimesPanel = '';
            if(!resp.error){
                $scope.selectedTransferOutCourse.orderCourseSurplusCount = resp.data;
            }else{
                $uibMsgbox.alert(resp.message);
            }
        });
    }
    $scope.transferOutCourseTimesList = [];
    $scope.checkTransferOutCourseTimes =  function(attendance){
        if(attendance.checked){
            attendance.checked = false;
        }else{
            attendance.checked = true;
        }
        $scope.transferOutCourseTimesList = [];
        $.each($scope.selectedTransferOutCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.checked){
                $scope.transferOutCourseTimesList.push(tfoCourse);
            }
        });
    };

    $scope.checkAllTransferOutCourseTimes = function(){
        $scope.transferOutCourseTimesList = [];
        $.each($scope.selectedTransferOutCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.ATTEND_TYPE==10||!tfoCourse.ATTEND_TYPE){
                tfoCourse.checked = true;
                $scope.transferOutCourseTimesList.push(tfoCourse);
            }
        });
    };

    $scope.unCheckAllTransferOutCourseTimes = function(){
        $.each($scope.selectedTransferOutCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.ATTEND_TYPE==10||!tfoCourse.ATTEND_TYPE){
                tfoCourse.checked = false;
            }
        });
        $scope.transferOutCourseTimesList = [];
    };

    /**
     * 1对1 ，转班课时录入
     * @param course
     */
    $scope.inputTransferOutCourseCount = function(course){
        try{
            course.transferOutCount = parseInt(course.transferOutCount);
        }catch(e){
            course.transferOutCount = -1;
        }
        // 控制“下一步”的可点击
        $scope.transferOutCourseTimesList = [];
        if(course.transferOutCount && course.transferOutCount > 0 && course.transferOutCount <= course.course_schedule_count){
            for(var i = 0;i < course.transferOutCount; i++) {
                $scope.transferOutCourseTimesList.push(i+1);
            }
        } else if (course.transferOutCount == 0 || (course.transferOutCount && course.transferOutCount > course.course_schedule_count)) {
            $uibMsgbox.warn("输入的转班课时必须为正整数且不能大于剩余可排课时！");
        }
    };

    /**
     * 查询课程
     */
    $scope.querySelectingCourse = function(){
        var param = {
            currentPage: $scope.paginationConf.currentPage,
            pageSize: $scope.paginationConf.itemsPerPage,
            branch_id:$scope.transferIn.selectedBranch.id,
            season_id:$scope.transferIn.selectedTimeSeason?$scope.transferIn.selectedTimeSeason.id:-1,
            grade_id:$scope.transferIn.selectedGrade?$scope.transferIn.selectedGrade.id:-1,
            subject_id:$scope.transferIn.selectedSubject?$scope.transferIn.selectedSubject.id:-1,
            business_type:$scope.businessType,
            search_info:$("#courseSearchInfo").val(),
            unit_price: $scope.selectedTransferOutCourse.former_unit_price,
            status:1
        };
        $scope.isQuerySelectingCourse = 'isQuerySelectingCourse';
        $scope.toSelectingCourseList = [];
        erp_courseService.query(param,function(resp){
            $scope.isQuerySelectingCourse = '';
            if(!resp.error){
                $scope.toSelectingCourseList = resp.data;
                $scope.paginationConf.totalItems = resp.total;
                $scope.selectedTransferInCourse = undefined;
            }else{
                $uibMsgbox.alert(resp.message);
            }
        })
    };

    $scope.checkTransferInCourse = function(toSelectingCourseList,course){
        if(course.checked){
            course.checked = false;
            $scope.selectedTransferInCourse = undefined;
            $scope.transferInCourseTimesList = [];
        }else{
            course.checked = true;
            if($scope.selectedTransferInCourse&&$scope.selectedTransferInCourse.id!=course.id){
                $scope.selectedTransferInCourse.checked = false;
            }
            $scope.selectedTransferInCourse = course;
            $scope.transferInCourseTimesList = [];
            $scope.queryTransferInCourseTimes();
        }
    };
    $scope.transferInCourseTimesList = [];
    /**
     * 打开课次面板
     * @param course
     */
    $scope.queryTransferInCourseTimes = function(){
        var param = {};
        param.courseId = $scope.selectedTransferInCourse.id;
        param.studentId = $scope.student.id;
        $scope.isLoadingCourseTimesPanel = 'isLoadingCourseTimesPanel';
        erp_courseTimesService.query(param,function(resp){
            $scope.isLoadingCourseTimesPanel = '';
            if(!resp.error){
                $scope.selectedTransferInCourse.courseSchedulingList = resp.data;
                doSelectedCourseOfTransferIn($scope.transferOutCourseTimesList || []);
            }
        })
    };

    $scope.checkedTransferInCourseTimes = function(courseTime){
        if(courseTime.checked){
            courseTime.checked = false;
        }else{
            if($scope.transferInCourseTimesList.length == $scope.transferOutCourseTimesList.length){
                $uibMsgbox.alert("已经转入全部"+$scope.transferInCourseTimesList.length+"课时！");
                courseTime.checked = false;
                return false;
            }
            courseTime.checked = true;
        }

        $scope.transferInCourseTimesList  = [];
        $.each($scope.selectedTransferInCourse.courseSchedulingList,function(i,node){
            if(node.checked){
                $scope.transferInCourseTimesList.push(node);
            }
        });
    };

    /**
     * 查询课程季
     */
    function queryTimeSeason(){
        var deferred = $q.defer();
        erp_timeSeasonService.list({
            product_line:$scope.transferIn.selectedBranch?$scope.transferIn.selectedBranch.product_line:-1
        },function(resp){
            if(!resp.error){
                $scope.timeSeasonList = resp.data;
                $scope.transferIn.selectedTimeSeason = undefined;
                if($scope.selectedTransferOutCourse && $scope.selectedTransferOutCourse.season_id){
                    $.each($scope.timeSeasonList,function(i, season){
                        if(season.id == $scope.selectedTransferOutCourse.season_id){
                            $scope.transferIn.selectedTimeSeason = season;
                        }
                    })
                }
                deferred.resolve();

            }
        });
        return deferred.promise;
    }

    /**
     * 查询科目
     */
    function querySubject(){
        var deferred = $q.defer();
        erp_subjectService.querySelectDatas({
            branch_id:$scope.transferIn.selectedBranch?$scope.transferIn.selectedBranch.id:-1,
            season_id:$scope.transferIn.selectedTimeSeason?$scope.transferIn.selectedTimeSeason.id:-1,
            grade_id:$scope.transferIn.selectedGrade?$scope.transferIn.selectedGrade.id:-1
        },function(resp){
            if(!resp.error){
                $scope.subjectList = resp.data;
                $scope.transferIn.selectedSubject = undefined;
                if($scope.selectedTransferOutCourse && $scope.selectedTransferOutCourse.subject_id){
                    $.each($scope.subjectList,function(i, subject){
                        if(subject.id == $scope.selectedTransferOutCourse.subject_id){
                            $scope.transferIn.selectedSubject = subject;
                        }
                    });
                }
                deferred.resolve();
            }
        });
        return deferred.promise;
    }

    /**
     * 查询年级
     */
    function queryGrade(){
        var deferred = $q.defer();
        erp_gradeService.querySelectDatas({
            branch_id:$scope.transferIn.selectedBranch?$scope.transferIn.selectedBranch.id:-1,
            season_id:$scope.transferIn.selectedTimeSeason?$scope.transferIn.selectedTimeSeason.id:-1
        },function(resp){
            if(!resp.error){
                $scope.gradeList = resp.data;
                $scope.transferIn.selectedGrade = undefined;
                if($scope.student && $scope.student.grade_id){
                    $.each($scope.gradeList,function(i,grade){
                        if(grade.id == $scope.student.grade_id){
                            $scope.transferIn.selectedGrade = grade;
                        }
                    })
                }
                deferred.resolve();
            }
        });
        return deferred.promise;
    }

    /**
     * 查询校区
     */
    function queryBuOrgs(){
        var deferred = $q.defer();
        erp_studentBuOrgsService.queryAll({},function(resp){
            if(!resp.error){
                $scope.branchList = resp.data;
                if($scope.branchList){
                    $.each($scope.branchList,function(i,branch){
                        if(branch.id == $scope.selectedTransferOutCourse.branch_id){
                            $scope.transferIn.selectedBranch = branch;
                        }
                    });
                }
                deferred.resolve();
            }
        });
        return deferred.promise;
    }

    /**
     * 查询1对1可转入校区
     */
    $scope.queryTransInBranchList = function(){
        var param = {
            course_id:$scope.selectedTransferOutCourse.course_id,
            business_type:2  //1对1课程
        };
        erp_courseService.query(param,function(resp){
            if(!resp.error){
                $scope.transInfBranchList = [];
                if(resp.data && resp.data.length > 0 && resp.data[0].branchInfos) {
                    $scope.transInBranchList =  resp.data[0].branchInfos;
                }
            }
        })
    };

    /**
     * 选择转入校区
     * @param branch
     */
    $scope.selectTransInBranch = function(){
        var branch = _.find($scope.transInBranchList, {id: $scope.transferIn.branch_id})
        $scope.transferIn.branch_name = branch.org_name
    };

    /**
     * 选择校区
     * @param branch
     */
    $scope.selectBranch = function(branch){
        $scope.transferIn.selectedBranch = branch;
        queryGrade().then(function () {
            return querySubject();
        }).then(function () {
            $scope.querySelectingCourse();
        });
    };

    /**
     * 选择课程季
     * @param season
     */
    $scope.selectSeason = function(season){
        $scope.transferIn.selectedTimeSeason = season;
        queryGrade().then(function () {
            return querySubject();
        }).then(function () {
            $scope.querySelectingCourse();
        });
    };

    /**
     * 选择年级
     * @param grade
     */
    $scope.selectGrade = function(grade){
        $scope.transferIn.selectedGrade = grade;
        querySubject().then(function () {
            $scope.querySelectingCourse();
        });
    };

    /**
     * 选择科目
     * @param subject
     */
    $scope.selectSubject = function(subject){
        $scope.transferIn.selectedSubject = subject;
        $scope.querySelectingCourse();
    };
    $scope.remark = "";
    /**
     * 提交转班
     */
    $scope.submitTransfer = function(){
        if($scope.businessType == 1){
            var param = {};
            param.businessType = 1;
            param.studentId = $scope.student.id;
            param.orderDetailId = $scope.selectedTransferOutCourse.id;
            param.transferOutCount = $scope.transferOutCourseTimesList.length;
            var courseTimes = [];
            $.each( $scope.transferOutCourseTimesList,function(i,ct){
                courseTimes.push(ct.TIMES);
            });
            param.transferOutCourseTimes = courseTimes.join(",");
            param.transferOutCourseTimesList=$scope.transferOutCourseTimesList;

            param.transferInCourseId = $scope.selectedTransferInCourse.id;
            param.transferInBranchId = $scope.selectedTransferInCourse.branch_id;
            param.transferInCount = $scope.transferInCourseTimesList.length;
            courseTimes = [];
            $.each( $scope.transferInCourseTimesList,function(i,ct){
                courseTimes.push(ct.course_times);
            });
            param.transferInCourseTimes = courseTimes.join(",");
            param.transferInCourseTimesList=$scope.transferInCourseTimesList;
            param.remark = $scope.selectedTransferOutCourse.remark;
            var waitingModal = $uibMsgbox.waiting('正在处理，请稍候');
            // $scope.isSubmitTransfer = 'isSubmitTransfer';
            erp_orderChangeService.changeTransfer(param,function(resp){
                waitingModal.close();
                if(!resp.error){
                    $uibMsgbox.confirm({
                        content:'转班成功，可以查看详情，或者继续转班',
                        cancelText: '继续转班',
                        okText: '查看详情',
                        callback: function (res) {
                            if (res == 'yes') {
                                //window.location.href = '?studentId=' + param.studentId + '#/studentMgr/studentMgrIndex';
                                window.location.href = '?studentId=' + param.studentId + '&orderId=' + $scope.selectedTransferOutCourse.order_id + '#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
                            } else {
                                //$state.reload();
                                window.location.href="?_t=" + Math.random() + "#/orders/orderChangeTransfer";
                            }
                        }
                    });
                }else{
                    uibMsgbox.alert(resp.message);
                }
            });
        }else if($scope.businessType == 2){
            var param = {};
            param.businessType = 2;
            param.studentId = $scope.student.id;
            param.orderDetailId = $scope.selectedTransferOutCourse.id;
            param.transferOutCount = $scope.selectedTransferOutCourse.transferOutCount;
            var courseTimes = [];
            $.each( $scope.transferOutCourseTimesList,function(i,ct){
                courseTimes.push(ct);
            });
            param.transferOutCourseTimes = courseTimes.join(",");

            param.transferInCourseId = $scope.selectedTransferOutCourse.course_id;
            param.transferInBranchId = $scope.transferIn.branch_id;
            param.transferInCount = param.transferOutCount;
            param.transferInCourseTimes = param.transferOutCourseTimes
            param.remark = $scope.selectedTransferOutCourse.remark;

            var waitingModal = $uibMsgbox.waiting('正在处理，请稍候');
            // $scope.isSubmitTransfer = 'isSubmitTransfer';
            erp_orderChangeService.changeTransfer(param,function(resp){
                waitingModal.close();
                if(!resp.error){
                    $uibMsgbox.confirm({
                        content:'转班成功，可以查看详情，或者继续转班',
                        cancelText: '继续转班',
                        okText: '查看详情',
                        callback: function (res) {
                            if (res == 'yes') {
                                //window.location.href = '?studentId=' + param.studentId + '#/studentMgr/studentMgrIndex';
                                window.location.href = '?studentId=' + param.studentId + '&orderId=' + $scope.selectedTransferOutCourse.order_id + '#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
                            } else {
                                //$state.reload();
                                window.location.href="?_t=" + Math.random() + "#/orders/orderChangeTransfer";
                            }
                        }
                    })
                }else{
                   $uibMsgbox.alert(resp.message);
                }
            });
}
};

    function initial(){
        $('title').text('转班 | 厝边素高');
        $scope.studentId = $("#rootIndex_studentId").val();
        $scope.step = 1;

        $scope.queryStudents = queryStudents;
        $scope.studentId = $("#rootIndex_studentId").val();
        queryStudents();

        $scope.orderDetailId = $("#rootIndex_orderDetailId").val();

    }

    initial();

    /**
     * 根据转出课时的数目在转入课程课时默认匹配上的相应的且规则就是升序
     *
     **/
    function doSelectedCourseOfTransferIn(items) {
        if (!$scope.selectedTransferInCourse.courseSchedulingList) return;
        if (!items.length || !angular.isArray($scope.selectedTransferInCourse.courseSchedulingList)) return;
        if ($scope.transferInCourseTimesList.length) return;

        // if ($scope.selectedTransferInCourse.courseSchedulingList.length >= items.length) {
        //     angular.forEach($scope.selectedTransferInCourse.courseSchedulingList, function(course, i, array) {
        //         // 需要判断是否已报名
        //         if ($scope.transferInCourseTimesList.length < items.length && !course.is_ordered) {
        //             course.checked = true;
        //             $scope.transferInCourseTimesList.push(course);
        //         }
        //     })
        // }

        // 转入课程统计
        var tempTransferInCourseTimesList = [];
        var findTransferInCourseTime = false; //是否找到转入课次

        angular.forEach(items, function(transferOutItem) {
            findTransferInCourseTime = false;
            // 优先查找相同的课次
            angular.forEach($scope.selectedTransferInCourse.courseSchedulingList, function(transferInItem) {
                // 未找到 且 当前课次未被选中 且  学生未报班
                if(findTransferInCourseTime == false && !transferInItem.checked && (!transferInItem.is_ordered ) ) {
                    // 同个课次
                    if(transferInItem.course_times == transferOutItem.TIMES ) {
                        transferInItem.checked = true;
                        tempTransferInCourseTimesList.push(transferInItem);
                        findTransferInCourseTime = true;
                    }
                }
            });
            // 没有符合条件的相同课次，则从第一个课次开始匹配
            if(findTransferInCourseTime == false) {
                angular.forEach($scope.selectedTransferInCourse.courseSchedulingList, function(transferInItem) {
                    // 未找到 且 当前课次未被选中 且  学生未报班
                    if(findTransferInCourseTime == false && !transferInItem.checked && (!transferInItem.is_ordered ) ) {
                        transferInItem.checked = true;
                        tempTransferInCourseTimesList.push(transferInItem);
                        findTransferInCourseTime = true;
                    }
                });
            }
        });

        $scope.transferInCourseTimesList = tempTransferInCourseTimesList;

        if($scope.transferInCourseTimesList.length < items.length) {
            $uibMsgbox.alert("选中课程，没有足够的可选课次！");
        }
    }


}