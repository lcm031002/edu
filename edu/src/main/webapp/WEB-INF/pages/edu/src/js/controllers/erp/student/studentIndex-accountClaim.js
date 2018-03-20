/**
 * Created by Liyong.zhu on 2016/9/30.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_studentAccountClaimController', [
        '$rootScope',
        '$scope',
        '$log',
        '$uibMsgbox',
        'erp_companyAccountService',
        'erp_studentAccountService',
        'erp_studentPrintService',
        'erp_studentsService',
        erp_studentAccountClaimController]);

function erp_studentAccountClaimController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_companyAccountService,
    erp_studentAccountService,
    erp_studentPrintService,
    erp_studentsService) {
    //学员信息
    $scope.student = {};

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
                    $uibMsgbox.confirm(resp.message);
                }
            });
    }
    function initial(){
        $scope.payment = {
            lp_info:{
                student_heading:$scope.student.student_heading,
                student_name:$scope.student.student_name,
                encoding:$scope.student.encoding,
                remark:"",
                student_id:$scope.studentId,
                lp_price:0
            }
        };
        $('title').text('学员|'+ $scope.student.student_name);
    }

    queryStudentInfo();



    /* 理赔提交 */
    $scope.LPSubmit = function() {
        var post = {
            student_id : $scope.payment.lp_info.student_id,
            product_line : 0,
            money : $scope.payment.lp_info.lp_price,
            remark : $scope.payment.lp_info.remark
        };
        post._ = (new Date()).getTime();
        post.money = genFloatByString(post.money);
        if (!post.money || post.money < 0 || post.money > 100000) {
            $uibMsgbox.confirm("理赔失败，金额不正确！");
            return;
        }
        if (post.remark != null && post.remark.length > 100) {
            $uibMsgbox.confirm("理赔失败，描述信息过长！");
            return;
        }
        post.accountOperateType = 'claim';
        var _waitingModal = $uibMsgbox.waiting('理赔申请中，请稍候...');
        erp_studentAccountService.post(post, function(response) {
            _waitingModal.close();
            if (!response.error) {
                $uibMsgbox.confirm("已成功发起理赔审批！");
            }else{
                $uibMsgbox.confirm(response.message);
            }
        });

    };

}