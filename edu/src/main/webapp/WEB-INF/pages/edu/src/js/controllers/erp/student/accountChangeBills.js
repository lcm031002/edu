/**
 * @author zenglw@klxuexi.org 2017/03/13
 */
"use strict";
angular.module('ework-ui').controller(
    'erp_accountChangeBillsController',
    ['$rootScope', '$scope', '$log', '$uibMsgbox', 'erp_studentAccountService', '$uibModal', 'erp_printService',
        erp_accountChangeBillsController]);

function erp_accountChangeBillsController($rootScope, $scope, $log, $uibMsgbox,
                                          erp_studentAccountService, $uibModal, erp_printService) {

    // 搜索参数默认值
    $scope.searchParam = {
        queryStudentString: '',
        queryOrderString: '',
        dynamicType: '-1',
        remark: "",
        // 记录当前更新的账户动态记录的id
        curEditAccountDynamic: {}
    };
    $scope.accountDynamicList = null;
    //pos机
    $scope.POSList = null;
    $scope.cardNum = null;
    // 账户变动类型
    $scope.dynamicTypeList = [{
        "key": '正常单据',
        "value": "-1"
    }, {
        "key": "充值",
        "value": "1"
    }, {
        "key": "理赔",
        "value": "3"
    }, {
        "key": "取款",
        "value": "4"
    },{
        "key": "转账",
        "value": "2"
    },  {
        "key": "充值作废",
        "value": "5"
    }, {
        "key": "理赔作废",
        "value": "6"
    }, {
        "key": "取款作废",
        "value": "7"
    }];

    /**
     * 分页配置
     *
     * @param {Number}
     *            currentPage [当前页面，初始化时默认为1]
     * @param {Number}
     *            totalItems [数据总条数，每次查询时赋值]
     * @param {Number}
     *            itemsPerPage [每页显示条数]
     * @param {Number}
     *            pagesLength [可选，分页栏长度,默认为9]
     * @param {Array}
     *            perPageOptions [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
     * @param {Function}
     *            onChange [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
     */
    $scope.paginationConf = {
        currentPage: 1, // 当前页
        totalItems: 0,
        itemsPerPage: 10,
        onChange: function () {
            $scope.queryAccountDynamic();
        }
    };

    // 作废单据
    $scope.invalidAccountDynamic = function () {

        var waitingModal = $uibMsgbox.waiting('正在处理，请稍候');
        erp_studentAccountService.del({
            dynamicId: $scope.searchParam.curEditAccountDynamic.id,
            remark: $scope.searchParam.remark
        }, function (resp) {
            waitingModal.close();
            if (!resp.error) {
                $uibMsgbox.alert("作废成功");
                $scope.queryAccountDynamic();
            } else {
                $uibMsgbox.alert(resp.message);
            }
        });
        $("#accountChangeBills_invalidAccountDynamicModal").modal(
            'hide');
        $scope.curEditAccountDynamic = {};
    };
    // 修改单据
    $scope.updateAccountDynamic = function () {

        erp_studentAccountService.put({
            id: $scope.searchParam.curEditAccountDynamic.id,
            pay_mode: $scope.searchParam.curEditAccountDynamic.pay_mode,
            account_id: $scope.cardNum.companyAccount,
            card_num: $scope.cardNum.cardNum
        }, function (resp) {
            if (!resp.error) {
                $uibMsgbox.alert(resp.message);
                $scope.queryAccountDynamic();
            } else {
                $uibMsgbox.alert(resp.message);
            }
            $("#accountChangeBills_updateAccountDynamicModal").modal('hide');
            $scope.curEditAccountDynamic = {};
            $scope.POSList = null;
            $scope.cardNum = null;
        });
    };
    // 转账弹出框
    $scope.popTransferModal = function (accountDynamic) {

        var modalInstance = $uibModal.open({
            size: 'xlg',
            template: '<rd-modal>'
            + '       <rd-modal-title>转账</rd-modal-title>'
            + '       <rd-modal-body>'
            + '           <div ng-include="\'templates/erp/student/studentIndex-accountTransfer.html\'"></div>'
            + '       </rd-modal-body>'
            + '       <rd-modal-footer><button class="btn btn-default" ng-click="$dismiss()">关闭</button></rd-modal-footer>'
            + '   </rd-modal>',
            // templateUrl: 'templates/erp/student/studentIndex-accountTransfer.html',
            controller: 'erp_studentAccountTransferController',
            resolve: {
                //目标参数获取 $scope.$resolve.changeNo
                studentId: accountDynamic.student_id,
                buId: accountDynamic.bu_id
            }
        });
    };
    // 取款弹出框
    $scope.openDrawModal = function () {
        var modalInstance = $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/erp/student/withdrawal.html',
            controller: 'erp_withDrawalController',
            resolve: {
                //目标参数获取 $scope.$resolve.changeNo
                changeNo: function () {
                    return null;
                }
            }
        });
        modalInstance.result.then(function (result) {
            $scope.queryAccountDynamic();
            $uibMsgbox.alert("取款成功");
        }, function (reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };
    // 账户变动查询
    $scope.queryAccountDynamic = function () {

        erp_studentAccountService.queryAccountDynamic({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            queryStudentString: $scope.searchParam.queryStudentString,
            queryOrderString: $scope.searchParam.queryOrderString,
            dynamicType: $scope.searchParam.dynamicType
        }, function (resp) {

            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; // 设置总条数
                $scope.accountDynamicList = resp.data;
            } else {
                $uibMsgbox.alert(resp.message);
            }
        });
    };
    //显示作废动户信息模态框
    $scope.showInvalidAccountDynamicModal = function (curEditRecord) {
        $scope.searchParam.curEditAccountDynamic = curEditRecord;
        $("#accountChangeBills_invalidAccountDynamicModal").modal('show');
    };
    //显示修改动户信息模态框
    $scope.showUpdateAccountDynamicModal = function (curEditRecord) {
        $scope.searchParam.curEditAccountDynamic = curEditRecord;

        erp_studentAccountService.queryUpdateBaseInfo({
            id: $scope.searchParam.curEditAccountDynamic.id
        }, function (resp) {
            if (!resp.error) {
                $scope.POSList = resp.POSList;
                $scope.cardNum = resp.cardNum;
                $("#accountChangeBills_updateAccountDynamicModal")
                    .modal('show');
            } else {
                $uibMsgbox.alert(resp.message);
            }
        });
    };

    $scope.queryAccountDynamic();

    $scope.printZZ = function (data) {
        erp_printService.printAccountDynamic({"dynamicId": data.id, "printType": "05"});
    };
    $scope.printQK = function (data) {
        erp_printService.printAccountDynamic({"dynamicId": data.id, "printType": "04"});
    };

    $scope.printLP = function (data) {
        var e = '<style type="text/css">';
        e += "table { width:660px; font:12px Arial; color: #000;font-weight: 400; }";
        e += "tr.Line td{ border-top:1px dashed #000;}";
        e += "tr td{ height:24px; line-height:24px;}";
        e += "h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}";
        e += "</style>";
        e += '<body style="margin:0 auto ;padding-top:350px;text-align:center">';
        e += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
        e += '<tr><td>';
        e += '<table border="0" width="660px" align="center">';
        e += '<tr align="left" style="font-weight:900;">';
        e += '<td colspan="5">业务校区:' + data.branch_name + "\t经办人:"
            + data.operator + "</td>";
        e += "</tr>";
        e += '<tr align="center" style="font-weight:900;">';
        e += '<td width="160">学员编号</td>';
        e += '<td width="66">学员姓名</td>';
        e += '<td width="185">单据编号</td>';
        e += '<td width="68">业务类型</td>';
        e += '<td width="76">业务日期</td>';
        e += "</tr>";
        e += '<tr align="center">';
        e += "<td>" + data.student_encoding + "</td>";
        e += "<td>" + data.student_name + "</td>";
        e += "<td>" + data.encoding + "</td>";
        e += "<td>理赔</td>";
        e += "<td>" + Format("yyyy-MM-dd", new Date(data.input_time)) + "</td>";
        e += "</tr>";
        e += '<tr align="left">';
        e += '<td colspan="7">理赔信息：</td>';
        e += "</tr>";
        e += "</table>";
        e += '<table border="0" width="660px" style="margin-top:15px">';
        e += '<tr height="15px" align="center" style="font-weight:900;">';
        e += "<td>编号</td>";
        e += "<td>理赔金额</td>";
        e += "<td>账户</td>";
        e += "</tr>";
        e += '<tr align="center">';
        e += "<td>1</td>";
        e += "<td>￥" + data.money + "</td>";
        e += "<td>" + data.product_line + "</td>";
        e += "</tr>";
        e += '<tr align="left">';
        e += '<td colspan="3">理赔原因：' + data.remark + "</td>";
        e += "</tr>";
        e += "</table></td></tr>";
        e += "</table>";
        e += "</body>";
        KlxxPrint(e);
    };

    // 打印单据
    $scope.printCZ = function (data) {

        var strHtml = '<style type="text/css">';
        strHtml += 'table { width:660px; font:12px Arial; color: #000;font-weight: 400; }';
        strHtml += 'tr.Line td{ border-top:1px dashed #000;}';
        strHtml += 'tr td{ height:24px; line-height:24px;}';
        strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
        strHtml += '</style>'

        strHtml += '<body style="margin:0 auto ;padding-top:350px;text-align:center">';
        strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
        strHtml += '<tr><td>';

        strHtml += '<table border="0" width="660px" align="center">';
        strHtml += '<tr align="left" style="font-weight:900;">';
        strHtml += '<td colspan="5">业务校区:' + data.branch_name + '	经办人:' + data.operator + '</td>';
        strHtml += '</tr>';
        strHtml += '<tr align="center" style="font-weight:900; ">';
        strHtml += '<td width="150">学员编号</td>';
        strHtml += '<td width="66">学员姓名</td>';
        strHtml += '<td width="185">单据编号</td>';
        strHtml += '<td width="68">业务类型</td>';
        strHtml += '<td width="76">业务日期</td>';
        strHtml += '</tr>';
        strHtml += '<tr align="center">';
        strHtml += '<td>' + data.student_encoding + '</td>';
        strHtml += '<td>' + data.student_name + '</td>';
        strHtml += '<td>' + data.encoding + '</td>';
        strHtml += '<td>充值</td>';
        strHtml += '<td>' + Format("yyyy-MM-dd", new Date(data.input_time))  + '</td>';
        strHtml += '</tr>';
        strHtml += '<tr align="left" >';
        strHtml += '<td colspan="7">充值信息：</td>';
        strHtml += '</tr>';
        strHtml += '</table>';

        strHtml += '<table border="0" width="660px" style="margin-top:15px">';
        strHtml += '<tr height="15px" align="center" style="font-weight:900; >';
        strHtml += '<td>编号</td>';
        strHtml += '<td>操作账户</td>';
        strHtml += '<td>方式</td>';
        strHtml += '<td>操作金额</td>';
        strHtml += '<td>备注</td>';
        strHtml += '</tr>';
        strHtml += '<tr align="center" >';
        strHtml += '<td>1</td>';
        strHtml += '<td>' + data.product_line + '</td>';
        strHtml += '<td>' + data.pay_mode_id + '</td>';
        strHtml += '<td>￥' + data.money + '</td>';
        strHtml += '<td>' + data.remark + '</td>';
        strHtml += '</tr>';
        strHtml += '</table></td></tr>';
        strHtml += '</table>';
        strHtml += '</body>';
        KlxxPrint(strHtml);
    };
    $scope.print = function (data) {
        switch (data.dynamic_type_name) {
            case "充值":
                $scope.printCZ(data);
                break;
            case "理赔":
                $scope.printLP(data);
                break;
            case "转账":
                $scope.printZZ(data);
                break;
            case "取款":
                $scope.printQK(data);
                break;
            default:
                $uibMsgbox.alert("该类型的账户单据不支持打印");
        }
    };
}
