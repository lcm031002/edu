/**
 * Created by Liyong.zhu on 2016/9/18.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_StudentIndexController', [
        '$rootScope',
        '$scope',
        '$log',
        '$state',
        '$uibMsgbox',
        'erp_studentIndexAccountService',
        'erp_studentIndexCounselorsService',
        'erp_studentsService',
        'erp_MessageService',
        'erp_studentContactService',
        erp_StudentIndexController]);

function erp_StudentIndexController(
    $rootScope,
    $scope,
    $log,
    $state,
    $uibMsgbox,
    erp_studentIndexAccountService,
    erp_studentIndexCounselorsService,
    erp_studentsService,
    erp_MessageService,
    erp_studentContactService) {
    //学员信息
    // debugger
    $scope.student = {};
    //学员账户信息
    $scope.studentIndexAccount = {};
    //学员咨询学管
    $scope.studentIndexCounselors = {};
    //学员报班记录查询类型
    $scope.queryHistoryType = 0;

    $scope.studentId = $("#rootIndex_studentId").val();

    $scope.queryStudentInfo = function (){
        erp_studentsService.query(
            {
                row_num: 20,
                studentId: $scope.studentId
            },
            function(resp){
                if(!resp.error && resp.data.length){
                    $scope.student = resp.data[0];
                    initial();
                }
            });
    }

    $scope.queryStudentInfo();

    function initial(){
        queryIndexAccount();
        queryIndexCounselors();
        $('title').text($scope.student.student_name +" | 快乐学习");
        $scope._d = (new Date()).getTime();
        $scope.studentOrdersUrl = 'templates/erp/student/studentIndex-order.html?_='+ $scope._d;
        $scope.studentOrderBJKUrl = 'templates/erp/student/studentIndex-order-bjk.html?_='+ $scope._d;
        $scope.studentOrderYDYUrl = 'templates/erp/student/studentIndex-order-ydy.html?_='+ $scope._d;
        $scope.studentOrderWFDUrl = 'templates/erp/student/studentIndex-order-wfd.html?_='+ $scope._d;
    }

    $scope.changeOrderType = function(type){
        $scope.queryHistoryType = type;
    }

    function queryIndexAccount(){
        erp_studentIndexAccountService.query({
            studentId:$scope.studentId
        },function(resp){
            if(!resp.error){
                $scope.studentIndexAccount = resp.data;
            }
        })
    }

    function queryIndexCounselors(){
        erp_studentIndexCounselorsService.query({
            studentId:$scope.studentId
        },function(resp){
            if(!resp.error){
                $scope.studentIndexCounselors = resp.data;
            }
        });
    }

    $scope.gotoBasicInfo = function () {
        window.location.href = '#/studentMgr/studentMgrBasicInfo'
    }

    $scope.onStudentSelect = function (stu) {
        window.location.href = '?studentId=' + stu.id +'#/studentMgr/studentMgrIndex'
    }

    /* 验证手机_start */
    $scope.inputPhoneCode = null;
    $scope.originPhoneCode = null;
    $scope.timer = 0;
    $scope.showVerifyPhone = function() {
        if (!$scope.student.phone) {
            alert('请填写手机号码');
            return;
        }
        $uibMsgbox.confirm('确认发送短信验证码？',function(res) {
            if(res == 'yes') {
                $("#studentIndex_validPhoneModal").modal('show');
                $scope.sendMessage();
            }
        });
    }
    //发送短信验证码
    $scope.sendMessage = function() {
        // 发送验证码
        var _uibModalInstance = $uibMsgbox.waiting('正在发送短信，请稍候...');
        erp_MessageService.sendMessage({
            mobile: $scope.student.phone
        }, function (resp) {
            _uibModalInstance.close();
            if (resp.error == true || !resp.rsp_verify_code) {
                alert('发送失败_' + resp.errMsg);
                return;
            } else {
                $scope.originPhoneCode = resp.rsp_verify_code;
            }
        });
    };
    //等待60秒后发送短信验证码
    $scope.waitAndSendMessage = function(){
        $scope.timer = 60;
        var intervalHandler = setInterval(function(){
            $scope.$apply(function(){
                $scope.timer--;
                if($scope.timer <0) {
                    $scope.timer =0;
                    clearInterval(intervalHandler);
                }
            })
        },1000);
        $scope.sendMessage();
    };
    //关闭短信验证弹出框
    $scope.cancleValidPhone = function() {
        $scope.originPhoneCode = null;
        $scope.inputPhoneCode = null;
        $("#studentIndex_validPhoneModal").modal('hide');
    }
    //短信模态框隐藏事件
    $("#studentIndex_validPhoneModal").on('hidden.bs.modal', function (e) {
        $scope.cancleValidPhone();
    })
    //验证短信
    $scope.validPhone = function() {
        if($scope.originPhoneCode == $scope.inputPhoneCode) {
            //更新学员信息中的phone_verify
            erp_studentContactService.updateDefaultContact({
                id:$scope.student.id,
                phone_verify:1
            },function(resp) {
                if (!resp.error) {
                    $uibMsgbox.alert("设置成功！");
                    $scope.queryStudentInfo();
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
            $scope.cancleValidPhone();
        } else {
            $uibMsgbox.warn("短信验证码错误");
        }
    }
    /* 验证手机_end */
}