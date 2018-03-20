// /**
//  * 教师日历
//  * Created By yans@klxuexi.org 2017-11-17
//  * 
//  */
// angular.module('ework-ui').controller('erp_courseScheduleController',  [
//   '$scope',
//   '$log',
//   '$state',
//   '$uibMsgbox',
//   '$uibModal',
//   'erp_TeacherListService',
//   erp_courseScheduleController
// ])
// function erp_courseScheduleController(
//   $scope,
//   $log,
//   $state,
//   $uibMsgbox,
//   $uibModal,
//   erp_TeacherListService
// ){
//   $scope.groupId = $("#rootIndex_groupId").val();
//   $scope.teacherList = [];
//   $scope.courseList = [];  
//   $scope.teacherDetail = [];
//   $scope.paginationConf = {
//     currentPage: 1, // 当前页
//     totalItems: 0,
//     itemsPerPage: 22,
//     onChange: function() {
//       $scope.queryTeacher();
//     }
//   };  
//   $("[data-toggle=tooltip]").tooltip();
//   $scope.getCourse = function(){
//     erp_TeacherListService
//       .queryTeacherSched({
//         startDate : $scope.curFroms,
//         endDate :  $scope.curTos,
//         teacherId :$scope.teacherDetail.id
//       },
//       function (resp) {
//         if (!resp.error) {
//           var item=resp.data;
//           $scope.space = $('.teacher-calendar-time').width() / 8;
//           for(var i=0;i<item.length;i++){
//             item[i].xAxis = item[i].xAxis * $scope.space;
//            for(var j=i+1;j<item.length;j++){
//             if(item[i].courseDate == item[j].courseDate){
//               // console.log(item[j])            
//               var tag=item[i].yAxis+item[i].courseLen;
//               if(item[j].yAxis<tag && item[j].yAxis>item[i].yAxis){
//                 console.log('dd')  
//                 console.log(item[j])   
//                 item[j].xAxis = item[j].xAxis + 35;
//               }
//             }
//            }
//           }
//           // console.log(resp.data)
//           $scope.courseList = item;
//         }
//       });
//   }

//   $scope.queryTeacher = function () {
//     erp_TeacherListService
//     .query({
//         pageSize:22,
//         teach_group_id:$scope.groupId,
//         search_info: $scope.teacher_name
//       },
//       function (resp) {
//         if (!resp.error) {
//           $scope.teacherList = resp.data;
//           if($scope.teacherDetail==''){
//             $scope.teacherDetail = $scope.teacherList[0]; 
//             $scope.getCourse()
//          }
//         } 
//       });
//   };
//   $scope.queryTeacher();
  
//   $scope.getTeacher = function(arr){
//     // $scope.curWeek();//切换老师重置时间到本周
//     $scope.teacherDetail = arr;
//     $scope.getCourse();
//   }
//   $scope.curFirst;
//   $scope.weekList = [];
//   $scope.year = new Date().getFullYear();
//   $scope.month = (new Date().getMonth()+1);
//   $scope.day = new Date().getDate();
//   $scope.weekDay = [];
//   $scope.formatDate = function(date){             
//     $scope.year = date.getFullYear()+'年';
//     $scope.month = (date.getMonth()+1)+'月';
//     $scope.day = date.getDate()+'日';
//     $scope.weekDay = ['周日','周一','周二','周三','周四','周五','周六'][date.getDay()];  

//     return $scope.weekDay+'('+$scope.month+$scope.day+')';
//   };
//   $scope.changeDate= function(date,n){       
//       date.setDate(date.getDate()+n);        
//       return date;
//   };

//   $scope.getWeek = function(arr){ 
//     var week = arr.getDay() +1;
//     var date = $scope.changeDate(arr, week * -1);
//     $scope.curFirst = new Date(date);
//     var curdata = $scope.curFirst;
//     $scope.curFrom = Format('yyyy年MM月dd日', curdata)
//     $scope.curTo = Format('yyyy年MM月dd日', new Date(curdata.getTime() + 24*6*60*60*1000))
//     $scope.curFroms = Format('yyyy-MM-dd', curdata)
//     $scope.curTos = Format('yyyy-MM-dd', new Date(curdata.getTime() + 24*6*60*60*1000))
//     for (var i = 0; i < 7; i++) {
//       $scope.weekList[i] = $scope.formatDate(i == 0 ? date : $scope.changeDate(date, 1));
//     }   
//     $scope.getCourse();         
//   };             
//   $scope.prevWeek = function(){
//     $scope.getWeek( $scope.changeDate( $scope.curFirst,-5));         
//   };             
//   $scope.nextWeek = function(){                 
//     $scope.getWeek( $scope.changeDate( $scope.curFirst,10));
//   };     
//   $scope.curWeek = function(){                 
//     $scope.getWeek(new Date());
//   };     
//   $scope.getWeek(new Date());
// }