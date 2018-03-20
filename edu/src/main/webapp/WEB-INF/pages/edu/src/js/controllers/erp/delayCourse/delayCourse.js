/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_delayCourseController', [
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
    'erp_delayCourseService',
    erp_delayCourseController
    ]);

function erp_delayCourseController(
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
    erp_delayCourseService
  ) {
    $scope.checkAllFlag = false;

    // 搜索课程名称
    $scope.searchParam = {
        course_Name: '',
        branch_id:-1
    };
    $scope.queryParam = {
        };

    
    // 课程列表
    $scope.courseList = [];

    $scope.selectedCourseList = [];

    function getSelectedCourseList () {
        var courseList = [];
        _.forEach($scope.courseList ,function(course) {
            if (course.selectFlag) {
                courseList.push(course)
            }
        })
        return courseList;
    }
    
    //获取所有选中的id
    function getSelectedIds () {
        var ids = "";
        _.forEach($scope.courseList ,function(course) {
            if (course.selectFlag) {
            	ids+= "," + course.id;
            }
        })
        if (!ids) {
            $uibMsgbox.alert('请选择要操作的课程！');
        }
        return ids.substring(1);
    }

    // 全选事件
    $scope.onCheckAll = function () {
        _.forEach($scope.courseList ,function(course) {
            course.selectFlag = $scope.checkAllFlag;
        })
    }

    // 某一行Checkbox选择事件
    $scope.onCourseChecked = function (course) {
        $scope.checkAllFlag = _.every($scope.courseList, {selectFlag: true});
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

    /**
     * 查询校区
     */
    function queryBuOrgs() {
        erp_studentBuOrgsService.query({}, function (resp) {
            if (!resp.error) {
                var data = resp.data;
                if (data && data.length) {
                    $scope.branchList = resp.data;
                    $scope.modalBranchList = _.cloneDeep(resp.data);
                    //将全部option添加到branchList
                    $scope.branchList.unshift(
                        {
                            id : -1,
                            org_name:"-- 全部 --"
                        }
                    );
                    $scope.searchParam.branchId = -1;
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
        PUBORGSelectedService.query({}, function (resp) {
            if (!resp.error) {
                $scope.selectedOrg = resp.data;
                if ($scope.selectedOrg && $scope.selectedOrg.id && ($scope.selectedOrg.type == "4" || $scope.selectedOrg.type == "3" )) {
                    if($scope.selectedOrg.type == "4") {
                        $.each($scope.branchList, function (i, b) {
                            if (b.id == $scope.selectedOrg.id) {
                                $scope.searchParam.branchId = b.id;
                            }
                        });
                    }
                } else {
                    $uibMsgbox.warn("请选择团队或校区");
                }
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

    /**
     * 选择课程
     * @param grade
     */
    $scope.selectCourse = function(grade){
        $scope.queryParam.selectedCourse = grade;
        querySubject();
    }

    /**
     * 选择科目
     * @param subject
     */
    $scope.selectSubject = function(subject){
        $scope.queryParam.selectedSubject = subject;
    }

    // 查询方法
    $scope.query = function () {
        $scope.loadStatues = true;
        erp_delayCourseService.listDelayCourse({
            branchId:$scope.searchParam.branchId,
            seasonId:$scope.searchParam.seasonId,
            subjectId:$scope.searchParam.subjectId,
            gradeId:$scope.searchParam.gradeId,
            courseSearchInfo:$scope.searchParam.courseSearchInfo,
            teacherSearchInfo:$scope.searchParam.teacherSearchInfo,
            delayCourseDateString:$scope.searchParam.delayCourseDateString
        }, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.courseList=resp.data;
                $scope.onCourseChecked();
                $scope.delayCourseDateString = $scope.searchParam.delayCourseDateString;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    };


    $scope.querySelectDatas = function (id) {
    	erp_courseService.querySelectDatas({
    		id : id
    	},function(resp) {
            if(!resp.error) {
                $scope.lastCourseList = resp.data;
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    };

    querySubject();
    queryGrade();
    queryBuOrgs();
    queryTimeSeason();
    // 当前步骤
    $scope.currentStep = 1;

    // 所有的步骤
    $scope.steps = [{
        title: '第一步：选择课程'
    }, {
        title: '第二步：延课清单'
    }, {
        title: '第三步：延课完成'
    }];

    $scope.back = function(){
        $scope.currentStep = $scope.currentStep-1;
    }

    //查询课次变动
    $scope.querycourseSchedullingChangeInfo = function (courseId,courseTime) {
        $scope.courseSchedullingChangeInfoList = {};
        erp_delayCourseService.changeSchedulingInfo({
            courseId:courseId,
            courseTime:courseTime
        }, function (resp) {
            if (!resp.error) {
                $scope.courseSchedullingChangeInfoList =resp.data;
                $('#delayCourseViewcourseSchedullingChangeInfo').modal('show');
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    //显示变动详情
    $scope.showCourseSchedullingChangeInfo = function(course) {
        $.each($scope.courseChangeInfoList,function(i,n){
            if(n.courseId == course.courseId && course.courseTime ==n.courseTime){
                $scope.courseSchedullingChangeInfoList =n.courseSchedullingChangeInfoList;
                $('#delayCourseViewcourseSchedullingChangeInfo').modal('show');
                return false;
            }
        });
    };
    //制单人
    if (!$rootScope.curEmployee) {
        $rootScope.curEmployee = {}
    }
    $scope.currentPeple = $rootScope.curEmployee.employeeName;
    $scope.$watch('curEmployee', function(newValue) {
        if (newValue && newValue.employeeId) {
          $scope.currentPeple = $rootScope.curEmployee.employeeName;
        }
    })
    $scope.currentDate = Format("yyyy-MM-dd",new Date());
    //跳转到延课清单
    $scope.goDelayCourseInfo = function() {

        $scope.selectedCourseList = getSelectedCourseList();
        if(!$scope.selectedCourseList ||!$scope.selectedCourseList.length) {
            $uibMsgbox.error("请选择延课记录");
            return;
        }
        $scope.selectedCourseChangeInfoList = {};
        var _watingModal = $uibMsgbox.waiting('正在处理，请稍候...');
        erp_delayCourseService.listCourseChangeInfo(
            $.map($scope.selectedCourseList, function (n, i) {
                return {
                    course_id: n.courseId,
                    course_times: n.courseTime
                }
            }) ,
           function (resp) {
                if (!resp.error) {
                    $scope.courseChangeInfoList =resp.data;
                    var courseChangeInfoTmp = null;
                    $.each($scope.selectedCourseList,function(i,n){
                        for (var t in $scope.courseChangeInfoList) {
                            courseChangeInfoTmp = $scope.courseChangeInfoList[t];
                           if(courseChangeInfoTmp.courseId == n.courseId) {
                               n.courseDateBeforeDelay = courseChangeInfoTmp.courseSchedullingChangeInfoList[courseChangeInfoTmp.courseTime-1].courseDateBeforeDelay;
                               n.courseDateAfterDelay = courseChangeInfoTmp.courseSchedullingChangeInfoList[courseChangeInfoTmp.courseTime-1].courseDateAfterDelay;
                               break;
                           }
                        }
                    });
                    $scope.currentStep = 2;
                } else {
                    $uibMsgbox.error(resp.message);
                }
               _watingModal.close();
            }
        );

    }

    //点击延课按钮
    $scope.showReasonInputDialog = function(){
        $('#delayCourseInputReason').modal('show');
    }

    $scope.delayReason = "";
    $scope.delayRecordId = null;//延课单ID
    $scope.delayCourseBatch = function(){
        if(!$scope.delayReason) {
            $uibMsgbox.alert("延课原因必填");
            return;
        }
        $('#delayCourseInputReason').modal('hide');
        $uibMsgbox.confirm('一旦延课后，将不能撤消，是否继续？', function (res) {
            if (res == 'yes') {
                var _watingModal = $uibMsgbox.waiting('正在处理，请稍候...');
                erp_delayCourseService.batchDelayCourse(
                    {
                        delayCourseDate:$scope.delayCourseDateString,
                        reason:$scope.delayReason,
                        delayCourseDetailList : $.map($scope.selectedCourseList, function (n, i) {
                            return {
                                courseId: n.courseId,
                                courseTime: n.courseTime
                            }
                        })
                    },
                    function (resp) {
                        if (!resp.error) {
                            $scope.currentStep = 3;
                            $scope.delayRecordId = resp.data;
                        } else {
                            $uibMsgbox.error(resp.message);
                        }
                        _watingModal.close();
                    }
                );
            }
        })
    }

    $scope.queryDelayReocedCourseDetail = function (id) {
        window.location.href="?recordId="+$scope.delayRecordId+"#/orders/delayrecord/detail";
    };

    $scope.backForDelayCourse = function(){
        $scope.courseList={};
        $scope.currentStep = 1;
    }
}
