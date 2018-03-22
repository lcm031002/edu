/**
 * Created by Liyong.zhu on 2016/6/4.
 */
angular.module('ework-ui')
    .controller('TeacherAttendanceCtrl', [
        '$scope',
        '$log',
        '$state',
        'TeacherAttendanceService',
        TeacherAttendanceCtrl]);

function TeacherAttendanceCtrl(
        $scope,
        $log,
        $state,
        TeacherAttendanceService
    ){
    $scope.teacherAttendanceList = [];

    function queryTeacherAttendance(){

        TeacherAttendanceService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.teacherAttendanceList = resp.data;
            }
        })
    };

    queryTeacherAttendance();
}