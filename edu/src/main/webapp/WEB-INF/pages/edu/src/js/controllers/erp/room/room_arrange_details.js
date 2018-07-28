"use strict";
angular.module('ework-ui').controller('erp_roomArrangeDetailsController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_roomService',
    'erp_studentBuOrgsService',
    'PUBORGSelectedService',
    erp_roomArrangeDetailsController
]);

function erp_roomArrangeDetailsController($rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_roomService,
    erp_studentBuOrgsService,
    PUBORGSelectedService) {
    // 返回
    $scope.goBack = function () {
        // window.location.href = '#/orders/classesRoomMgr/classesRoomArranger'
        $state.go('classesRoomArranger')
    }
    // 初始化起止时间
    $scope.searchParams = {
      startDate: moment().startOf('Week').format('YYYY-MM-DD'),
      endDate: moment().endOf('Week').format('YYYY-MM-DD'),
      p_min_date: moment().startOf('month').format('YYYY-MM-DD'),
      p_max_date: moment().endOf('month').add(1,'month').format('YYYY-MM-DD'),
      range: 'lastWeek',
      p_btn_tag: 'hidden'
     };

    $scope.queryRoomScheduling = function queryRoomScheduling(){
        erp_roomService.listroomScheduling({
            roomId:$scope.roomId,
            startDate:$scope.searchParams.startDate,
            endDate:$scope.searchParams.endDate
        },function(resp){
            // console.log(resp)
            if(!resp.error){
                $scope.arrangeList = resp.data;
            }
        });
    }

    // 变更教室
    $scope.currentRoomArrangeRecord = {};
    $scope.showRoomDialog = function(roomArr){
        $scope.currentRoomArrangeRecord = roomArr;
        $scope.selectedRoom = {};
        var params = {
            condition:"空闲",
            startTime:roomArr.startTime,
            endTime:roomArr.endTime,
            branchId:roomArr.branchId,
            startDate:Format('yyyy-MM-dd', new Date(roomArr.courseDate)),
            endDate:Format('yyyy-MM-dd', new Date(roomArr.courseDate)),
        }
        var _uibModalInstance = $uibMsgbox.waiting('处理中，请稍候...');
        erp_roomService.listroomArrange(params, function (resp) {
            if (!resp.error) {
                $scope.emptyRoomList = resp.data;
                $("#roomArrangeChangeRoomDialog").modal("show");
            } else {
                $uibMsgbox.error(resp.message)
            }
            _uibModalInstance.close();
        });
    }

    $scope.changeRoom = function(){
        $uibMsgbox.confirm('确定变更', function (res) {
            if (res == 'yes') {
                if(!$scope.selectedRoom) {
                    $uibMsgbox.alert("请选中教室");
                }
                var params = {
                    course_id:$scope.currentRoomArrangeRecord.courseId,
                    seq:$scope.currentRoomArrangeRecord.courseTime,
                    course_date:Format("yyyyMMdd",new Date(parseInt($scope.currentRoomArrangeRecord.courseDate))),
                    start_time:$scope.currentRoomArrangeRecord.startTime,
                    end_time:$scope.currentRoomArrangeRecord.endTime,
                    room_id:$scope.selectedRoom,
                    branch_id:$scope.currentRoomArrangeRecord.branchId,
                    id:$scope.currentRoomArrangeRecord.courseTimeRoomRef
                }
                var _watingModal = $uibMsgbox.waiting('保存中，请稍候...');
                erp_roomService.saveRoomRel(params, function (resp) {
                    // console.log(resp)
                    _watingModal.close();
                    if (!resp.error) {
                        $uibMsgbox.alert("教室变更成功");
                        $scope.queryRoomScheduling();
                    } else {
                        $uibMsgbox.error(resp.message)
                    }
                    $("#roomArrangeChangeRoomDialog").modal("hide");
                });
            }
        });
    }

    function init() {
        $scope.roomId = $("#rootIndex_roomId").val();
        $scope.searchParams.startDate = Format('yyyy-MM-dd', new Date(parseInt($("#rootIndex_startDate").val())));
        $scope.searchParams.endDate = Format('yyyy-MM-dd', new Date(parseInt($("#rootIndex_endDate").val())));
        $scope.queryRoomScheduling();
    }
    init();

}