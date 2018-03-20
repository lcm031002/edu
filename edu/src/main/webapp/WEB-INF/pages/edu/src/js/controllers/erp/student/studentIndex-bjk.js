/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_StudentBjkController', [
        '$rootScope',
        '$scope',
        '$cookieStore',
        '$log',
        'erp_timeSeasonService',
        'erp_subjectService',
        'erp_studentOrdersBJKService',
        'erp_studentsService',
        erp_StudentBjkController]);

function erp_StudentBjkController(
    $rootScope,
    $scope,
    $cookieStore,
    $log,
    erp_timeSeasonService,
    erp_subjectService,
    erp_studentOrdersBJKService,
    erp_studentsService) {
    //学员信息
    $scope.student = {};

    $scope.isLoading = '';
    $scope.seasons = [];
    $scope.selectedSeason = {id:-1};
    $scope.selectedSubject ={id:-1};
    $scope.selectSeason = function(){
        queryBJK();
    };
    $scope.selectSubject = function(){
        // $scope.selectedSubject = subject;
        queryBJK();
    };

    function queryTimeSeason(){
        erp_timeSeasonService.list({},function(resp){
            if(!resp.error){
                $scope.seasons =  resp.data;
                if($scope.seasons){
                    $scope.seasons.unshift({
                        id:-1,
                        course_season_name:'全部'
                    });
                    $scope.selectedSeason = $scope.seasons[0];
                }

            }
        });
    }

    function querySubject(){
        erp_subjectService.querySelectDatas({},function(resp){
            if(!resp.error){
                $scope.subjects =  resp.data;
                if($scope.subjects){
                    $scope.subjects.unshift({
                        id:-1,
                        name:'全部'
                    });
                    $scope.selectedSubject = $scope.subjects[0];
                }

            }
        });
    }


    function queryBJK(){
        var param = {
                season:$scope.selectedSeason.id,
                subject:$scope.selectedSubject.id,
                studentId:$scope.studentId
        };
        $scope.isLoading='isLoading';
        erp_studentOrdersBJKService.query(param,function(resp){
            $scope.isLoading='';
            if(!resp.error){
                $scope.orderBJK = resp.data;
            }else{
                alert(resp.message);
            }
        });
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
                    alert(resp.message);
                }
            });
    }
    function initial(){
        queryTimeSeason();
        querySubject();
        queryBJK();
        $('title').text('学员|'+ $scope.student.student_name);
    }

    queryStudentInfo();

}