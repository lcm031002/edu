/**
 * Created by Liyong.zhu on 2016/5/31.
 */

angular.module('ework-ui')
    .controller('StudentListCtrl', ['$scope', '$log','$state','PUBCourseStudentListService','ERPCourseStudentListService',StudentListCtrl]);

function StudentListCtrl($scope, $log,$state,PUBCourseStudentListService,ERPCourseStudentListService) {

    $scope.ERPCourseStudentList = [];

    $scope.PUBCourseStudentList = [];
    $scope.$parent.$watch('currentCourse.courseId', function(value){changeCourse()});
    
    function changeCourse(){
    if($scope.currentCourse.courseId==null)
    		return;
        queryERPCourseStudentList();
        queryPUBCourseStudentList();
    }



    /**
     * 查询ERP内账的学生的信息
     */
    function queryERPCourseStudentList(){
        var param = {};
        param.courseId = $scope.currentCourse.courseId;
        param.type=1;
        $log.log("id===="+$scope.$parent.currentCourse.courseId);
        $scope.ERPCourseStudentQuery = 'isLoading...';
        $scope.ERPCourseStudentList = [];
        ERPCourseStudentListService.get(param,function(resp){
            if(resp.error=='false'||resp.error==false){
                $scope.ERPCourseStudentList = resp.data;
            }
            $scope.ERPCourseStudentQuery = '';
        })
    }

    /**
     * 查询对公系统的学生信息
     */
    function queryPUBCourseStudentList(){
        var param = {};
        param.courseId = $scope.$parent.currentCourse.courseId;
        param.type=0;
        $scope.PUBCourseStudentList = [];
        $scope.PUBCourseStudentQuery = 'isLoading...';
        $log.log("id===="+$scope.$parent.currentCourse.courseId);
        PUBCourseStudentListService.get(param,function(resp){
            if(resp.error == 'false'||resp.error==false){
                $scope.PUBCourseStudentList = resp.data;
            }
            $scope.PUBCourseStudentQuery = '';
        })
    }



}