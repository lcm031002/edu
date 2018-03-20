/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_courseSchdulingController', [
        '$scope',
        '$log',
        'erp_subjectService',
        'erp_gradeService',
        'erp_courseService',
        'erp_studentBuOrgsService',
        'erp_timeSeasonService',
        'PUBORGSelectedService',
        erp_courseSchdulingController]);

function erp_courseSchdulingController(
    $scope,
    $log,
    erp_subjectService,
    erp_gradeService,
    erp_courseService,
    erp_studentBuOrgsService,
    erp_timeSeasonService,
    PUBORGSelectedService) {
    $scope.isShowSenior = false;
    $scope.businessType = 1;
    $scope.queryParam  = {
        page:1
    };
    $scope.showSenior = function(){
        if($scope.isShowSenior){
            $scope.isShowSenior = false;
        }else{
            $scope.isShowSenior = true;
        }
    }


    /**
     * 查询科目
     */
    function querySubject(){
        erp_subjectService.querySelectDatas({
            branch_id:$scope.queryParam.selectedBranch?$scope.queryParam.selectedBranch.id:-1,
            season_id:$scope.queryParam.selectedTimeSeason?$scope.queryParam.selectedTimeSeason.id:-1,
            grade_id:$scope.queryParam.selectedGrade?$scope.queryParam.selectedGrade.id:-1
        },function(resp){
            if(!resp.error){
                $scope.subjectList = resp.data;
            }
        })
    }

    /**
     * 查询年级
     */
    function queryGrade(){
        erp_gradeService.querySelectDatas({
            branch_id:$scope.queryParam.selectedBranch?$scope.queryParam.selectedBranch.id:-1,
            season_id:$scope.queryParam.selectedTimeSeason?$scope.queryParam.selectedTimeSeason.id:-1
        },function(resp){
            if(!resp.error){
                $scope.gradeList = resp.data;
                if($scope.student && $scope.student.grade_id){
                    $.each($scope.gradeList,function(i,grade){
                        if(grade.id == $scope.student.grade_id){
                            $scope.queryParam.selectedGrade = grade;
                        }
                    })
                }
            }
        })
    }

    /**
     * 查询校区
     */
    function queryBuOrgs(){
        erp_studentBuOrgsService.query({},function(resp){
            if(!resp.error){
                $scope.branchList = resp.data;
                querySelectedOrg();
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
            }
        })
    }

    /**
     * 选择年级
     * @param grade
     */
    $scope.selectGrade = function(grade){
        $scope.queryParam.selectedGrade = grade;
        querySubject();
        $scope.querySelectingCourse();
    }

    /**
     * 选择科目
     * @param subject
     */
    $scope.selectSubject = function(subject){
        $scope.queryParam.selectedSubject = subject;
        $scope.querySelectingCourse();
    }

    /**
     * 查询课程
     */
    $scope.querySelectingCourse = function(){
        var param = {
            branch_id:$scope.queryParam.selectedBranch?$scope.queryParam.selectedBranch.id:-1,
            season_id:$scope.queryParam.selectedTimeSeason?$scope.queryParam.selectedTimeSeason.id:-1,
            grade_id:$scope.queryParam.selectedGrade?$scope.queryParam.selectedGrade.id:-1,
            subject_id:$scope.queryParam.selectedSubject?$scope.queryParam.selectedSubject.id:-1,
            business_type:$scope.businessType,
            search_info:$scope.queryParam.courseSearchInfo
        };
        param.currentPage = $scope.queryParam.currentPage;
        param.status = -1;
        param.pageSize = $scope.queryParam.pageSize;

        $scope.toSelectingCourseList = [];
        if($scope.selectedOrg&&$scope.selectedOrg.id&&$scope.selectedOrg.type=="4"){
        }else{
            alert("请选择校区!");
            return;
        }
        $scope.isQuerySelectingCourse = 'isQuerySelectingCourse';
        erp_courseService.query(param,function(resp){
            $scope.isQuerySelectingCourse = '';
            if(!resp.error){
                $scope.toSelectingCourseList = resp.data;
                $scope.queryParam.total          = resp.total;
                $scope.queryParam.totalPage     = resp.totalPage;
                $scope.queryParam.pageSize      = resp.pageSize;
                $scope.queryParam.currentPage  = resp.currentPage;
                $scope.queryParam.paginationBars = [];
                for(var index = ($scope.queryParam.page-1) * 10  ;index < $scope.queryParam.totalPage && index < $scope.queryParam.page * 10;index++){
                    $scope.queryParam.paginationBars.push(index);
                }
            }else{
                alert(resp.message);
            }
        })
    }

    /**
     * 分页查询
     * @param currentPage
     */
    $scope.pageQuery = function(currentPage){
        $scope.queryParam.currentPage  = currentPage;

        $scope.querySelectingCourse();
    }
    $scope.firstPage = function(){
        $scope.queryParam.page = 1;
        $scope.queryParam.currentPage  = 1;
        $scope.querySelectingCourse();
    }
    $scope.endPage = function(){

        $scope.queryParam.currentPage  = $scope.queryParam.totalPage;
        $scope.queryParam.page = Math.floor($scope.queryParam.totalPage / 10) + 1;
        $log.log("endPage,page is "+$scope.queryParam.page+",currentPage is "+$scope.queryParam.currentPage);
        $scope.querySelectingCourse();
    }
    $scope.nextPage = function(page){
        $scope.queryParam.page = page;
        $scope.queryParam.currentPage  = (page-1) * 10 + 1;
        $scope.querySelectingCourse();
    }

    function querySelectedOrg(){
        PUBORGSelectedService.query({},function(resp){
            if(!resp.error){
                $scope.selectedOrg = resp.data;
                if($scope.selectedOrg&&$scope.selectedOrg.id&&$scope.selectedOrg.type=="4"){
                    $.each($scope.branchList,function(i,b){
                        if(b.id == $scope.selectedOrg.id){
                            $scope.queryParam.selectedBranch = b;
                        }
                    });
                    $scope.querySelectingCourse();
                }else{
                    alert("请选择校区!");
                }
            }else{
                alert(resp.message);
            }
        })
    }



    $scope.changeStatus = function(status,course){
        var param = {};
        param.ids =course.id;
        param.status = status;
        $scope.isSubmit = 'isSubmit';
        erp_courseService.changeStatus(param,function(resp){
            $scope.isSubmit = '';
            if(!resp.error){
                alert("保存成功！");
                $scope.querySelectingCourse();
            } else{
                alert(resp.message);
            }
        });
    }


    querySubject();
    queryGrade();
    queryBuOrgs();
    queryTimeSeason();
}