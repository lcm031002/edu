/**
 * Created by shenyan on 2018-3-08.
 *
 * Modify by yans@klxuexi.org
 * 2018-3-09：
 *    - 团队互转
 */
"use strict";
angular
  .module('ework-ui')
  .controller('erp_TeamAccountTransferController', [
    '$rootScope',
    '$scope',
    '$cookieStore',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    'PUBORGSelectedService',
    'erp_studentsService',
    'erp_studentAccountService',
    erp_TeamAccountTransferController
  ]);

function erp_TeamAccountTransferController(
  $rootScope,
  $scope,
  $cookieStore,
  $log,
  $state,
  $uibModal,
  $uibMsgbox,
  PUBORGSelectedService,
  erp_studentsService,
  erp_studentAccountService
) {
  //默认走第一步
  $scope.step = 1;
  // 搜索条件
  $scope.searchInfo = {};
  //学员列表
  $scope.studentList = [];
  //获取传入的学生id
  $scope.studentId = $("#rootIndex_studentId").val() || $state.params.studentId;
  //本人互转为1,其他学员为2
  $scope.studentStatus = 1;
  // 转进账户类型
  $scope.accountTypeInList = [{
    "key" : 1, "value" : "储蓄账户"
  }, {
    "key" : 2, "value" : "冻结账户"
  }];

  // 转入学员信息
  $scope.trasInType = {
    account_type : 1,
    transAmount : '',
    transRemark : ''
  };


  /**
   * 查询学员
   */
  $scope.queryStudents = function() {
    var param ={};
    if($scope.step == 1){
      $scope.studentList = [];
       //默认团队
      PUBORGSelectedService.query({}, function (resp) {
        if (!resp.error) {
          $scope.buId = resp.data.buId;
        } else {
          console.log(resp.message)
        }
      })
    }

    if($scope.studentId){
      if($scope.step == 2){
        //第二步：转入学员特有的搜索条件
        param = {
          transType: $scope.studentStatus,
          stuSearchInfo: $scope.searchInfo.key,
          transOutBuId: $scope.student.buId
        };
        $scope.studentLists = [];
      }
      param.transOutStudentId = $scope.studentId;
    }else{
      $uibMsgbox.error('学生ID为空');
      return false
    }
    
    $scope.isQueryStudent = true;
    erp_studentsService.queryStudentTrans(param,
      function(resp) {
        $scope.isQueryStudent = false;
        if(!resp.error){
          if(resp.data && resp.data.length > 0 && $scope.studentId) {
            if($scope.step == 1){
              $scope.studentList = resp.data;
              _.forEach($scope.studentList, function (arr) {
                if (arr.buId == $scope.buId) {
                  arr.checked = true;
                  $scope.student = arr;
                }
              });
            }else if($scope.step == 2){
              $scope.students = resp.data[0];
              $scope.students.checked = true;
              $scope.studentLists = resp.data;
              $scope.studentLists[0] = $scope.students;
            }
          }else{
            if($scope.step == 1){
              $scope.studentList = resp.data;
            }else{
              $scope.studentLists = resp.data;
            }
            
          }
        }else{
          $uibMsgbox.error(resp.message);
        }
      });
  };

  /**
   * 选择学员
   * @param student
   */
  $scope.checkedStudent = function(student) {
    if ($scope.step == 1) {
      $scope.student = null
      if(student.checked == true){
        student.checked = false;
      }else{
        _.forEach($scope.studentList, function (arr) {
          if (arr.checked == true) {
            arr.checked = false;
          }
          if(arr.buId == student.buId){
            arr.checked = true;
            $scope.student = student;
          }
        })
      }
    } else {
      $scope.students = null
      if(student.checked == true){
        student.checked = false;
      }else{
        _.forEach($scope.studentLists, function (arr) {
          if (arr.checked == true) {
            arr.checked = false;
          }
          if(arr == student){
            arr.checked = true;
            $scope.students = student;
          }
        })
      }
    }
  }


  /**
   * 下一步、上一步
   * @param before
   * @param next
   */
  $scope.nextStep = function(before, next) {
    if(!$scope.student.feeAccount && !$scope.student.frozenAccount 
      && before == 1 && next == 2){
      $uibMsgbox.confirm('储蓄账号和冻结账号不能同时为空')
      return false;
    }
    $scope.step = next;
    if(before == 1 && next == 2){
      // console.log($scope.student)
      $scope.queryStudents();
    }
    if(before == 2 && next == 3){
      // $scope.feeOutAccount = $scope.student.feeAccount;
      // $scope.frozenOutAccount = $scope.student.frozenAccount;
      // $scope.feeInAccount = $scope.students.feeAccount;
      // $scope.frozenInAccount = $scope.students.frozenAccount;
      if(!$scope.student.feeAccount && !$scope.student.frozenAccount){
        $uibMsgbox.confirm('储蓄账号和冻结账号不能同时为空')
        return false;
      }
      $scope.disTrans = false;
      if(!$scope.student.feeAccount){
        $scope.trasInType.account_type = 2 ;
        $scope.disTrans = true;
      }
      if(!$scope.student.frozenAccount){
        $scope.trasInType.account_type = 1 ;
        $scope.disTrans = true;
      }
    }
  }

  /**
   * 本人互转为1,其他学员为2
   * @param changeStatus
   */
  $scope.changeStatus = function(status){
    $scope.studentStatus = status;
    if(status == 1){
      $scope.searchInfo.key = '';
      $scope.queryStudents(); 
    }else{
      $scope.students = null;
      $scope.studentLists = null;
    }
  }

  /**
   * 确认提交
   */
  $scope.confirmTrans = function(){  
    if(!$scope.student){
      $uibMsgbox.confirm('转出账号为空')
      return false;
    }
    if(!$scope.students){
      $uibMsgbox.confirm('转入账号为空')
      return false;
    }
    if(!$scope.trasInType){
      $uibMsgbox.confirm('请选择账户类型')
      return false;
    }
    if(!$scope.trasInType.transAmount){
      $uibMsgbox.confirm('请输入金额')
      return false;
    }
    var param = {
      p_output_student_id : $scope.student.id,
      p_output_bu_id : $scope.student.buId,
      p_output_account_type : $scope.trasInType.account_type,
      p_input_student_id : $scope.students.id,
      p_input_bu_id : $scope.students.buId,
      p_input_account_type : $scope.trasInType.account_type,
      p_transfer_money : $scope.trasInType.transAmount,
      p_remark : $scope.trasInType.transRemark,
      accountOperateType : 'transfer'
    };
    // console.log(param)
    var _waitingModal = $uibMsgbox.waiting('转账中，请稍候....')
    erp_studentAccountService.post(param,function(resp){
      _waitingModal.close();
      if (!resp.error) {
        $uibMsgbox.confirm({
          content:"转账申请提交成功,请等待转出账户团队财务审核!",
          showCancelBtn: false,
          // cancelText: '继续转入',
          okText: '查看详情',
          callback: function(res){
            //跳转账户详情页面
            $state.go('studentAccountQuery');
            // if (res == 'yes') {
            //   $state.go('studentAccountQuery');
            // } else {
            //   $scope.backStep = true;
            //   $scope.student.feeAccount = $scope.feeOutAccount;
            //   $scope.student.frozenAccount = $scope.frozenOutAccount;
            //   $scope.students.feeAccount = $scope.feeInAccount;
            //   $scope.students.frozenAccount = $scope.frozenInAccount;              
            //   $scope.changTransType();
            // }
          }
        })
      } else {
        $uibMsgbox.error(resp.message);
      }
    })
  }

   /**
   * 转账金额效验和处理
   */
  $scope.changeAmount = function(){
    if($scope.trasInType.transAmount === null){
      return false;
    }
    if($scope.trasInType.transAmount === undefined){
      $scope.trasInType.transAmount = 0 ;
      $uibMsgbox.confirm('转入金额不能小于0',function(rs){
        $scope.trasInType.transAmount = null ;
      });
      return false;
    }

    if($scope.trasInType.transAmount<=0){
      $uibMsgbox.confirm('转入金额必须大于0');
      $scope.trasInType.transAmount = null;      
      return false;
    }
    
    if($scope.trasInType.account_type == 1){
      if($scope.trasInType.transAmount > $scope.student.feeAccount){
        $uibMsgbox.confirm('转入金额'+$scope.trasInType.transAmount 
        + '大于转出储蓄账户,请重新输入转入金额(小于'+ $scope.student.feeAccount + ').');
        $scope.trasInType.transAmount = null;
        return false;
      }
      $scope.feeOutAccount = $scope.student.feeAccount - $scope.trasInType.transAmount;      
      $scope.feeInAccount = ($scope.students.feeAccount || 0) + $scope.trasInType.transAmount;        
    }else{
      if($scope.trasInType.transAmount > $scope.student.frozenAccount){
        $uibMsgbox.confirm('转入金额'+$scope.trasInType.transAmount 
        + '大于转出冻结账户,请重新输入转入金额(小于'+ $scope.student.frozenAccount + ').');
        $scope.trasInType.transAmount = null;      
        return false;
      }
      $scope.frozenOutAccount = $scope.student.frozenAccount - $scope.trasInType.transAmount;      
      $scope.frozenInAccount = ($scope.students.frozenAccount || 0) + $scope.trasInType.transAmount;        
    }
  }  

  /**
   * 改变账户类型
   */
  $scope.changTransType = function(){
    $scope.feeOutAccount = null;
    $scope.frozenOutAccount = null;
    $scope.feeInAccount = null;
    $scope.frozenInAccount = null;
    $scope.trasInType.transAmount = null;
  }

  $scope.queryStudents();
}
