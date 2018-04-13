/**
 * Created by Liyong.zhu on 2016/9/30.
 */
"use strict";
angular.module('ework-ui').controller('erp_studentAccountRechargeController', [
  '$rootScope',
  '$scope',
  '$state',
  '$log',
  '$uibMsgbox',
  'erp_companyAccountService',
  'erp_studentAccountService',
  'erp_studentPrintService',
  'erp_studentsService',
  'erp_deviceService',
  erp_studentAccountRechargeController
]);

function erp_studentAccountRechargeController(
  $rootScope,
  $scope,
  $state,
  $log,
  $uibMsgbox,
  erp_companyAccountService,
  erp_studentAccountService,
  erp_studentPrintService,
  erp_studentsService,
  erp_deviceService
) {
  //学员信息
  $scope.student = {};

  $scope.studentId = $("#rootIndex_studentId").val();

  function queryStudentInfo() {
    erp_studentsService.query({
        row_num: 20,
        studentId: $scope.studentId
      },
      function(resp) {
        if (!resp.error && resp.data.length) {
          $scope.student = resp.data[0];
          initial();
        } else {
          $uibMsgbox.alert(resp.message);
        }
      });
  }

  function initial() {
    queryCompanyAccount();
    queryDeviceInfo();
    $('title').text('学员|' + $scope.student.student_name);
  }
  queryStudentInfo();

  function strip(num,precision){
    return +parseFloat(num.toPrecision(precision));
  }

  function sumMoney() {
    $scope.allCzMoney = 0;
    $.each($scope.submitCZList, function(i, cz) {
      $scope.allCzMoney += cz.money;
    });
    $scope.allCzMoney=strip($scope.allCzMoney);
  }
  /* 获取现金充值信息 */
  function genCashin() {
    return {
      studentId: $scope.studentId,
      pay_mode: 1,
      money: $scope.payment.cash_price
    };
  }
  /* 设定新的item序号，并统计该单项的综合 */
  function sumItemsPrice(card_detail) {
    var sum = 0;
    $.each(card_detail, function(i, model) {
      sum += model.staffappprem;
    });
    return strip(sum);
  }

  function removeTransferPrice(id) {
    if (id) {
      if ($scope.payment.transfer_detail) {
        var details = [];
        $.each($scope.payment.transfer_detail, function(i, model) {
          if (model.id != id) {
            details.push(model);
          }
        });
        $scope.payment.transfer_detail = details;
        $scope.payment.transfer_price = sumItemsPrice($scope.payment.transfer_detail);
      }

      var submitCZList = [];
      $.each($scope.submitCZList, function(i, model) {
        if (model.id != id) {
          submitCZList.push(model);
        }
      });
      $scope.submitCZList = submitCZList;
      sumMoney();
    }
  }

  function removeCardPrice(id) {
    if ($scope.payment.card_detail) {
      var card_detail = [];
      $.each($scope.payment.card_detail, function(i, model) {
        if (model.id != id) {
          card_detail.push(model);
        }
      });
      $scope.payment.card_detail = card_detail;
      $scope.payment.card_price = sumItemsPrice($scope.payment.card_detail);

    }

    var submitCZList = [];
    $.each($scope.submitCZList, function(i, model) {
      if (model.id != id) {
        submitCZList.push(model);
      }
    });
    $scope.submitCZList = submitCZList;
    sumMoney();
  }

  /* 提交刷卡信息 */
  function submitCardPrice(cardDetail) {
    
    var param = {
      studentId: $scope.studentId,
      pay_mode: cardDetail.payment_way,
      money: cardDetail.staffappprem,
      stu_card: cardDetail.client_card_no,
      company_card_id: cardDetail.company_card_id,
      remark: cardDetail.extend_column,
      id: cardDetail.id
    };
    if (param.remark != null && param.remark.length > 100) {
      $uibMsgbox.alert("描述信息过长！");
      return -1;
    }
    if (!param.stu_card || param.stu_card.length > 29) {
      $uibMsgbox.alert("请输入正确的卡号！");
      return -1;
    }
    $scope.submitCZList.push(param);
    sumMoney();
    return 1;
  }

  /* 获取pos机的信息 */
  function genPosID(inputCradInfo, account) {
    if (inputCradInfo && account) {
      $.each(account, function(i, it) {
        if (it.id == inputCradInfo.account) {
          inputCradInfo.simple_name = it.simple_name;
          inputCradInfo.pos_id = it.id;
          inputCradInfo.company_card_id = it.company_card_id;
          return it;
        }
      });
    }
  }

  $scope.submitCZList = [];
  $scope.accountList = [];
  $scope.accountTransferList = [];
  $scope.payment = {
    cash_price: '请输入金额',
    card_price: 0,
    transfer_price: 0,
    card_detail: [],
    transfer_detail: []
  }
  $scope.allCzMoney = 0;
  $scope.initialCZ = function() {
    $state.reload();
  }

  /**
   * 添加一条现金充值记录
   */

  var ids = 1;
  /* 保存现金 */
  $scope.addCashItem = function() {
    var param = genCashin();
    // if ($scope.student.active != 1) {
    //   $uibMsgbox.alert("非活跃学员，不能充值！");
    //   return -1;
    // }
    if (!param.money || param.money == '请输入金额') {
      $uibMsgbox.alert("请输入金额！");
      return -1;
    }
    param.money = genFloatByString(param.money);
    if (param.money <= 0 || param.money + '' == 'NaN' || param.money > 1000000) {
      $uibMsgbox.alert("输入的金额不正确！");
      return -1;
    }
    param.id = ++ids;
    $scope.submitCZList.push(param);
    sumMoney();
    return 1;
  };


  $scope.onblurCash = function() {
    if ($scope.payment && $scope.payment.cash_price == '') {
      $scope.payment.cash_price = '请输入金额';
    }
  }
  $scope.onfocusCash = function() {
    if ($scope.payment && $scope.payment.cash_price == '请输入金额') {
      $scope.payment.cash_price = '';
    }
  }
  $scope.removeCzItem = function(czItem) {
    removeTransferPrice(czItem.id);
    removeCardPrice(czItem.id);
  };

  $scope.selectAccountPOS = function(posInfo) {
    $.each($scope.accountListPos, function(idx, pos) {
      if (pos.id == $scope.inputCradInfo.account) {
        $scope.inputCradInfo.account_cmp = pos.account_name;
        return;
      }
    });
  }

  $scope.selectAccountPOS2 = function() {
    $.each($scope.accountTransferList, function(idx, pos) {
      if (pos.ID == $scope.transferPriceData.account) {
        $scope.transferPriceData.account_cmp = pos.ACCOUNT_NAME;
        return;
      }
    });
  };



  /* 刷卡 */
  $scope.skWindowOpen = function() {
    // if ($scope.student.active != 1) {
    //   $uibMsgbox.alert("非活跃学员，不能充值！");
    //   return;
    // }
    $scope.windowOpenType = 'card';
  }
  $scope.windowOpenClose = function() {
    $scope.windowOpenType = undefined;
  }
  $scope.$watch('windowOpenType', function(newValue, oldValue) {
    if (newValue == 'card') {
      $scope.toCardPrice()
    }
  });
  /* 刷卡操作 */
  $scope.toCardPrice = function() {
    $scope.inputCradInfo = {};
    $scope.inputCradInfo.account = $scope.accountListPos[0].id;
    $scope.inputCradInfo.account_cmp = $scope.accountListPos[0].account_name;
    $scope.inputCradPrice = true;

    /** 添加刷卡记录 */
    $scope.addInputCradInfo = function() {
      var str = Format('yyyy-MM-dd hh:mm:ss', new Date());
      var staffappprem = genFloatByString($scope.inputCradInfo.staffappprem);
      if (!staffappprem) {
        $scope.inputCradInfo.staffappprem = 0;
        // 保存失败了
        $uibMsgbox.alert('请输入刷卡金额！');
        return false;
      }
      $scope.inputCradInfo.staffappprem = staffappprem;

      if ($scope.inputCradInfo.staffappprem <= 0) {
        // 保存失败了
        $uibMsgbox.alert('请输入正确的刷卡金额！');
        return false;
      }
      if (!$scope.inputCradInfo.client_card_no) {
        // 保存失败了
        $uibMsgbox.alert('请输入刷卡卡号！');
        return false;
      }
      
      // 获取pos机信息
      genPosID($scope.inputCradInfo, $scope.accountListPos);
      var detailInfo = {
        client_card_no: $scope.inputCradInfo.client_card_no,
        staffappprem: parseFloat($scope.inputCradInfo.staffappprem),
        payment_way: 2,
        createTime: str,
        simple_cmp_name: $scope.inputCradInfo.account_cmp,
        pos_id: $scope.inputCradInfo.pos_id,
        company_card_id: $scope.inputCradInfo.company_card_id,
        account_name: $scope.inputCradInfo.simple_name,
        extend_column: $scope.inputCradInfo.extend_column
      };
      detailInfo.id = ++ids;
      if (submitCardPrice(detailInfo) == -1) {
        return false;
      }
      detailInfo.simple_cmp_name = $scope.inputCradInfo.account_cmp;

      // 增加一条详情
      $scope.payment.card_detail.unshift(detailInfo);

      // 获取单项总计
      $scope.payment.card_price = sumItemsPrice($scope.payment.card_detail);

      $scope.inputCradInfo = {};
      $scope.inputCradInfo.account = $scope.accountListPos[0].id;
      $scope.inputCradInfo.account_cmp = $scope.accountListPos[0].account_name;
      $scope.inputCradPrice = true;
      return true;
    };
      /**
       * 移除刷卡记录
       */
      $scope.removeCardPrice = function(id) {
        removeCardPrice(id);
      };
      $scope.onCloseCardWindow = function () {
        var str = Format('yyyy-MM-dd hh:mm:ss', new Date());
        var staffappprem = genFloatByString($scope.inputCradInfo.staffappprem);
        if (staffappprem || $scope.inputCradInfo.client_card_no) {
          if ($scope.addInputCradInfo()) {
            $scope.windowOpenClose();
          }
        } else {
          $scope.windowOpenClose();
        }
      }
    }
    /* 转账：转入 */
  $scope.zrWindowOpen = function() {
    // if ($scope.student.active != 1) {
    //   $uibMsgbox.alert("非活跃学员，不能充值！");
    //   return;
    // }
    $scope.windowOpenType = 'transfer';
  }

  /* 转账操作 */
  $scope.toTransferPrice = function() {
    $scope.transferPriceData = {};
    // 初始化公司账户信息
    $scope.transferPriceData.account = $scope.accountTransferList[0].ID;
    $scope.transferPriceData.account_cmp = $scope.accountTransferList[0].ACCOUNT_NAME;
    $scope.inputTransferPrice = true;


    // 添加操作
    $scope.addInputTransferPrice = function() {
      var staffappprem = genFloatByString($scope.transferPriceData.staffappprem);
      if (!staffappprem) {
        $scope.transferPriceData.staffappprem = 0;
        // 保存失败了
        $uibMsgbox.alert('请输入转账金额！');
        return;
      }
      $scope.transferPriceData.staffappprem = staffappprem;

      if ($scope.transferPriceData.staffappprem <= 0) {
        // 保存失败了
        $uibMsgbox.alert('请输入正确的转账金额！');
        return;
      }
      if (!$scope.transferPriceData.client_card_no) {
        // 保存失败了
        $uibMsgbox.alert('请输入转账卡号！');
        return;
      }

      var str = Format('yyyy-MM-dd hh:mm:ss', new Date());
      var detailObject = {
        client_card_no: $scope.transferPriceData.client_card_no,
        staffappprem: parseFloat($scope.transferPriceData.staffappprem),
        payment_way: 3,
        createTime: str,
        //              pos_id : scope.transferPriceData.pos_id,
        company_card_id: $scope.transferPriceData.account,
        simple_name: $scope.transferPriceData.simple_name,
        extend_column: $scope.transferPriceData.extend_column
      };
      detailObject.id = ++ids;
      if (submitCardPrice(detailObject) == -1) {
        return;
      }

      // 增加一条详情
      detailObject.simple_cmp_name = $scope.transferPriceData.account_cmp;
      $scope.payment.transfer_detail[$scope.payment.transfer_detail.length] = detailObject;
      // 获取单项总计
      $scope.payment.transfer_price = sumItemsPrice($scope.payment.transfer_detail);

    }

    /* 移除转账信息 */
    $scope.removeTransferPrice = function(id) {
      removeTransferPrice(id);
    }
  }

  function removeTransferPrice(id) {
    if (id) {
      if ($scope.payment.transfer_detail) {
        var details = [];
        $.each($scope.payment.transfer_detail, function(i, model) {
          if (model.id != id) {
            details.push(model);
          }
        });
        $scope.payment.transfer_detail = details;
        $scope.payment.transfer_price = sumItemsPrice($scope.payment.transfer_detail);
      }

      var submitCZList = [];
      $.each($scope.submitCZList, function(i, model) {
        if (model.id != id) {
          submitCZList.push(model);
        }
      });
      $scope.submitCZList = submitCZList;
      sumMoney();
    }
  }
  var printData = [];
  var submitCZBakList = [];
  /**
   * 打印充值
   */
  $scope.printCZ = function() {
    if ($scope.submitCZList && $scope.submitCZList.length > 0) {
      var ob = $scope.submitCZList.pop();
      submitCZBakList.push(ob);
      erp_studentPrintService.query({ dynamicId: ob.dynamic_id, printType: '03' }, function(resp) {
        if(!resp.error) {
          printData.push(resp.data);
          $scope.printCZ();
        }
      });
    } else if (printData.length > 0) {
        var printDataStirng = JSON.stringify(printData);
        var useragent = navigator.userAgent;
        if(useragent.indexOf('Android') > -1 || useragent.indexOf('Adr') > -1) {
          if(location.href.indexOf('klxuexi.org')>0){
            window.open("/printhtml/print_rechargeXiamen.html?printData=" + printDataStirng);
          }else{
            window.open("/klxxedu/printhtml/print_rechargeXiamen.html?printData=" + printDataStirng);
          }
        }
      if(printData[0].rechargeInfo.CITY_ID==3) {
        CreatePrintPage04Xiamen(printData);
      } else {
        CreatePrintPage04(printData);
      }
      for (var i = 0; i < submitCZBakList.length; i++) {
        $scope.submitCZList.push(submitCZBakList[i]);
      }
      submitCZBakList = [];
      printData = [];
    }
  }

  function queryCompanyAccount() {
    // 查询公司账户信息
    erp_companyAccountService.query({p_status:1}, function(resp) {
      if (!resp.error && resp.data && resp.data.length > 0) {
        $scope.accountTransferList = resp.data;
      } else {
        $scope.accountTransferList = [];
        $uibMsgbox.alert(resp.message)
      }
    });
  }

  function queryDeviceInfo() {
    
    erp_deviceService.queryBu({p_status:1}, function(resp) {
      $scope.accountListPos = [];
      if (!resp.error && resp.data && resp.data.length > 0) {
        $.each(resp.data, function(i, model) {
          if (model.account_name) {
            $scope.accountListPos.push(model);
          }
        });
      } else {
        $uibMsgbox.alert(resp.message)
      }
    });
  }

  $scope.CZSubmitALL = function() {
    if ($scope.submitCZList && $scope.submitCZList.length > 0) {

      submitCZ($scope.submitCZList);

    }
  };
  var dynamicIds = [];
  var submitCZ_bak = [];
  /* 充值 */
  function submitCZ(list) {
    if (list != null && list.length > 0) {
      var param = list.pop();
      param.accountOperateType = 'recharge';
      var _waitingModal = $uibMsgbox.waiting('充值中，请稍候...');
      erp_studentAccountService.post(param, function(response) {
        _waitingModal.close();
        if (response.error) {
          $uibMsgbox.alert("保存失败，请联系管理员！message is " + response.message);
        } else {
          if (list != null && list.length > 0) {
            param.dynamic_id = response.data.dynamic_id;
            submitCZ_bak.push(param);
            submitCZ(list);
          } else {
            param.dynamic_id = response.data.dynamic_id;
            submitCZ_bak.push(param);
            $uibMsgbox.confirm({
              content: "充值成功！请选择打印，或者继续充值",
              okText: '打印',
              cancelText: '继续充值',
              callback: function (res) {
                if (res == 'yes') {
                  $.each(submitCZ_bak, function(i, m) {
                    list.push(m);
                  });
                  submitCZ_bak = [];
                  $scope.printCZ()
                } else {
                  $scope.initialCZ()
                }
                $scope.czSummit = true;
              }
            });
          }
          dynamicIds.push(response.data.dynamic_id);
          /*alert("充值成功！");

           // 充值打印内容
           queryPrintRechargeInfo.query({dynamicId:response.data.dynamic_id},function(data){
           CreatePrintPage04(data);
           });*/
        }
      });
    }

  }
}
