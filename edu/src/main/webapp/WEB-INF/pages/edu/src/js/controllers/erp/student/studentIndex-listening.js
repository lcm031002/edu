"use strict";
angular.module('ework-ui')
		.controller(
				'erp_studentListeningController',
				[
                    '$rootScope',
                    '$scope',
                    '$cookieStore',
                    '$log',
                    '$uibMsgbox',
                    'erp_studentContactRelationService',
                    'erp_studentContactService',
                    'erp_courseListeningService',
                    'erp_timeSeasonService',
                    'erp_subjectService',
                    'erp_gradeService',
                    'erp_studentBuOrgsService',
                    'erp_courseService',
                    'erp_courseTimesService',
                    'erp_studentsService',
                    erp_studentListeningController ]);

function erp_studentListeningController(
        $rootScope,
        $scope,
        $cookieStore,
		$log,
        $uibMsgbox,
        erp_studentContactRelationService,
        erp_studentContactService,
        erp_courseListeningService,
        erp_timeSeasonService,
        erp_subjectService,
        erp_gradeService,
        erp_studentBuOrgsService,
        erp_courseService,
        erp_courseTimesService,
        erp_studentsService) {
	// 学员信息
	$scope.student = {};
    $scope.searchParams = {
        startDate: '2015-01-01',
        endDate: getCurrentDate()
    }
    /*初始化_start*/
    $scope.Listening = {}; 					// 试听
    $scope.Listening.Page = {};				// 分页
    $scope.Listening.product_line = 1;		// 产品线
    /*初始化_end*/

    /*切换产品线_start*/
    $scope.changeProduct = function(product_line){
        $scope.Listening.product_line = product_line;
        /*展示数据*/
        $scope.Listening.Page.page = 1;
        $scope.queryListeningResults();
    }
    /*切换产品线_end*/


    /*时间轴_end*/

    /*分页_start*/
    $scope.Listening.Page.page =  1;		/*当前页*/
    $scope.Listening.Page.rows =  6;		/*每页N行*/
    $scope.Listening.Page.total = 0;		/*总记录数*/
    $scope.Listening.Page.totalPage = 0;	/*总页数*/

    $scope.queryListeningResults = function(){
        $scope.isLoading = 'loading...';
        $scope.ListeningResults = [];
        var param = {};

        if(!$scope.studentId){
            $uibMsgbox.alert('未知学生');
            return;
        }
        param.student_id = $scope.studentId;
        param.business_type = 1;
        param.start_date = $scope.searchParams.startDate;
        param.end_date = $scope.searchParams.endDate;
        param.currentPage = $scope.Listening.Page.page;
        param.pageSize = $scope.Listening.Page.rows;
        $scope.isLoading = 'isLoading';
        erp_courseListeningService.query(param,function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.Listening.Page.total = resp.total;
                $scope.Listening.Page.totalPage = resp.totalPage;
                $scope.Listening.Page.currentPage = resp.currentPage;
                $scope.Listening.Page.page = resp.currentPage;
                $scope.Listening.Page.pageSize = resp.pageSize;
                $scope.ListeningResults = resp.data;

                var _rows = $scope.Listening.Page.rows;
                $scope._total = $scope.Listening.Page.total;
                if($scope._total == 0){
                    $scope._begin = 0;
                    $scope._end = 0;
                }else{
                    $scope._begin = ($scope.Listening.Page.page - 1) * _rows + 1;
                    if($scope._total <= _rows || (($scope._begin - 1) + _rows) >= $scope._total){
                        $scope._end = $scope._total;
                    }else{
                        $scope._end = ($scope.Listening.Page.page - 1) * _rows + _rows;
                    }
                }
            }else{
                $uibMsgbox.alert(resp.message);
            }
        })
    }

    $scope.openSelectCourseWindow = function(){
        $('#selectCourseWindow').modal('show');
        $scope.querySelectedCourse();
    }

    $scope.closeSelectCourseWindow = function(){
        $('#selectCourseWindow').modal('hide');
    }

    /*报班*/
    $scope.agree = function(Result){
        window.open('?studentId=' +  $scope.studentId + '#/orders/ordersMgr/ordersMgrOrders', '_blank');
    };


    /**
     * 首页
     */
    $scope.begin = function(){
        $scope.Listening.Page.page = 1;
        $scope.queryListeningResults();
    }

    /**
     * 上一页
     */
    $scope.up = function(){
        if($scope.Listening.Page.page <= 1){
            return;
        }
        $scope.Listening.Page.page = $scope.Listening.Page.page - 1;
        $scope.queryListeningResults();
    }

    /**
     * 下一页
     */
    $scope.down = function(){
        if($scope.Listening.Page.page >= $scope.Listening.Page.totalPage){
            return;
        }
        $scope.Listening.Page.page = $scope.Listening.Page.page + 1;
        $scope.queryListeningResults();
    }

    /**
     * 最后一页
     */
    $scope.end = function(){
        $scope.Listening.Page.page = $scope.Listening.Page.totalPage;
        $scope.queryListeningResults();
    }

    $scope.subjectList = [];
    $scope.selectedSubject = {id:-1};
    $scope.gradeList = [];
    $scope.selectedGrade = {id:-1};
    $scope.selectedTimeSeason = {id:-1};
    $scope.timeSeasonList = [];
    $scope.selectedBranch = {id:-1};

    function queryTimeSeason(){
        erp_timeSeasonService.list({

        },function(resp){
            if(!resp.error){
                $scope.timeSeasonList = resp.data;
                $scope.timeSeasonList.unshift({
                    id:-1,
                    course_season_name:'--全部课程季--'
                });
                $scope.selectedTimeSeason = $scope.timeSeasonList[0];
            }
        })
    }
    function querySubject(){
        erp_subjectService.querySelectDatas({
            branch_id:$scope.selectedBranch.id,
            season_id:$scope.selectedTimeSeason.id,
            grade_id:$scope.selectedGrade.id
        },function(resp){
            if(!resp.error){
                $scope.subjectList = resp.data;
                $scope.subjectList.unshift({
                    id:-1,
                    name:'--全部科目--'
                });
                $scope.selectedSubject = $scope.subjectList[0];
            }
        })
    }
    function queryGrade(){
        erp_gradeService.querySelectDatas({
            branch_id:$scope.selectedBranch.id,
            season_id:$scope.selectedTimeSeason.id
        },function(resp){
            if(!resp.error){
                $scope.gradeList = resp.data;
                $scope.gradeList.unshift({
                    id:-1,
                    grade_name:'--全部年级--'
                });
                $scope.selectedGrade = $scope.gradeList[0];
            }
        })
    }
    function queryBuOrgs(){
        erp_studentBuOrgsService.query({},function(resp){
            if(!resp.error){
                $scope.branchList = resp.data;
                $scope.branchList.unshift({
                    id:-1,
                    org_name:'--全部校区--'
                });
                $scope.selectedBranch = $scope.branchList[0];
            }
        })
    }



    $scope.changeBranch = function(){
        querySubject();
        queryGrade();
    }
    $scope.changeSeason = function(){
        querySubject();
        queryGrade();
    }
    $scope.changeGrade = function(){
        querySubject();
    }

    $scope.pageConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        onChange: function() {
            $scope.querySelectedCourse();
        }
    };

    $scope.querySelectedCourse = function(){
        var param = {
            pageSize: $scope.pageConf.itemsPerPage,
            currentPage: $scope.pageConf.currentPage,
            branch_id:$scope.selectedBranch.id,
            season_id:$scope.selectedTimeSeason.id,
            grade_id:$scope.selectedGrade.id,
            subject_id:$scope.selectedSubject.id,
            business_type:1,
            status:1 //上架课程 
        };
        $scope.isLoading = 'isLoading';
        $scope.toSelectingCourseList = [];
        erp_courseService.query(param,function(resp){
            $scope.isLoading = '';
            if(!resp.error){
                $scope.toSelectingCourseList = resp.data;
                $scope.pageConf.totalItems = resp.total || 0;
            }else{
                $uibMsgbox.alert(resp.message);
            }
        })
    }

    $scope.selectCourseItem = function(course){
        if(!course.courseTimesList){
            course.courseTimesList = [];

            course.isLoadingCourseTime = 'isLoadingCourseTime';
            erp_courseTimesService.query({courseId:course.id},function(resp){
                course.isLoadingCourseTime = '';
                if(!resp.error){
                    course.courseTimesList = resp.data;
                }else{
                    $uibMsgbox.alert(resp.message);
                }
            })
        }
    }

    $scope.postListeningCourseTime = function(course,courseTime){
        var param = {
            course_id:course.id,
            student_id:$scope.studentId,
            course_times:courseTime.course_times,
            course_date:courseTime.course_date
        };

        erp_courseListeningService.post(param,function(resp){
            if(!resp.error){
                $uibMsgbox.success("试听成功！")
                $scope.queryListeningResults();
            }else{
                $uibMsgbox.alert(resp.message);
            }
        })
    }

    $scope.toOpenUpdateListeningPanel = function(Result){
        $('#updateListeningModal').modal('show');
        $scope.selectedUpdateListeningResult = Result;
    }

    $scope.toCloseUpdateListeningPanel = function(){
        $('#updateListeningModal').modal('hide');
        $scope.selectedUpdateListeningResult = undefined;
    }

    $scope.updateListening = function(){
        var param = {
            course_id:$scope.selectedUpdateListeningResult.COURSE_ID,
            student_id:$scope.studentId,
            remark:$scope.selectedUpdateListeningResult.remark,
            id:$scope.selectedUpdateListeningResult.ID,
            pay_status:2
        };

        erp_courseListeningService.put(param,function(resp){
            if(!resp.error){
                $uibMsgbox.alert("已经拒缴!");
                $scope.toCloseUpdateListeningPanel();
                $scope.queryListeningResults();
            }else{
                $uibMsgbox.alert(resp.message);
            }
        })
    }

    $scope.studentId = $("#rootIndex_studentId").val();

    function queryStudentInfo(){
        erp_studentsService.query(
            {
                row_num: 20,
                studentId: $scope.studentId
            },
            function(resp){
                if(!resp.error && resp.data.length){
                    $scope.student = resp.data[0];
                    initial();
                }else{
                    $uibMsgbox.alert(resp.message);
                }
            });
    }
    function initial(){
        /*时间轴_start*/
        var callBack = function() {
            var start_date = $scope.searchParams.startDate;
            var end_date = $scope.searchParams.endDate;
            $scope.Listening.Page.p_start_date = start_date;
            $scope.Listening.Page.p_end_date = end_date;

            $scope.Listening.Page.page = 1;
            $scope.queryListeningResults();
        }
        TimeLine($scope, {
            width : 600
        }, callBack);

        queryTimeSeason();
        querySubject();
        queryGrade();
        queryBuOrgs();

        $('title').text('学员|'+ $scope.student.student_name);
    }

    queryStudentInfo();

}
