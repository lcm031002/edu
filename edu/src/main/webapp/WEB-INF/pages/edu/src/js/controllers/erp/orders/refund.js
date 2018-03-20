/**
 * 退费单据
 */
"use strict";
angular.module('ework-ui').controller('erp_refundController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox',
    '$uibModal',
    'erp_refundService',
    erp_refundController
    ]);

function erp_refundController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $uibModal,
    erp_refundService
  ) {
    // 表单操作类型
    $scope.optype = 'detail'; //
    // 搜索的科目名称
    $scope.searchParam = {
        order_no: '',
        encoding: ''
    };
    // 明细列表
    $scope.refundList = [];

    //作废信息
    $scope.refundDeleteInfo = {
        change_id : '',
        remark: ''
    };

    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
     * @param  {Function} onChange      [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        // itemsPerPage: 10,
        // pagesLength: 9,
        // perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.query()
        }
    };

    $scope.paginationBars = [];

    //详情
    $scope.refundDetail = {
        change_id: '',
        courseList: []
    };

    //详情 的分页查询信息
    $scope.refundDetailPaginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        onChange: function(){
            $scope.detailQuery();
        }
    };

    //详情查询
    $scope.detailQuery = function () {
        erp_refundService.queryDetail({
            pageSize: $scope.refundDetailPaginationConf.itemsPerPage,
            currentPage: $scope.refundDetailPaginationConf.currentPage,
            change_id: $scope.refundDetail.change_id
        }, function (resp) {
            if (!resp.error) {
                $scope.refundDetail.courseList = resp.data;
                $scope.refundDetailPaginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message)
            }
        });
    };

    // 处理【详情】按钮点击事件
    $scope.handleDetailRefund = function (refund) {
        $scope.optype = 'detail';
        $scope.refundDetail = {
            change_id: refund.id,
            courseList: []
        };
        $scope.detailQuery();
        $('#erpDetailRefundPanel').modal('show');
    };

    // 处理【作废】按钮点击事件
    $scope.handleDeleteRefund = function (id) {
        $scope.optype = 'delete';
        $scope.refundDeleteInfo = {
            change_id : id,
            remark: ''
        };
        $('#erpDeleteRefundPanel').modal('show');
    };

    // 取款弹出框
    $scope.openDrawModal = function (refund) {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/erp/student/withdrawal.html',
            controller: 'erp_withDrawalController',
            resolve: {
                //目标参数获取 $scope.$resolve.changeNo
                changeNo : function () {
                    return refund.encoding;
                }
            }
        });

        modalInstance.result.then(function (result) {
        	console.log('Modal closed .....')
        	console.log(result)
            $scope.query();
        }, function () {
        	console.log('Modal dismiss ......')
            $log.info('DrawModal dismissed at: ' + new Date());
        })
    };

    // 处理【去取款】按钮点击事件
    $scope.handleDrawingRefund = function (refund) {
        $scope.optype = 'drawing';
        $scope.openDrawModal(refund);
    };
    
    // 处理【标记已取款】按钮点击事件
    $scope.handleDrawRefund = function (id) {
        $scope.optype = 'draw';
        $scope.refundDeleteInfo.change_id = id;
        $uibMsgbox.confirm('您确定要标记为【已取款】吗？', function (result) {
            if(result != 'yes') {
                return;
            }
            $scope.put(id);
        });
    };

    // 处理【打印】按钮点击事件
    $scope.handlePrintRefund = function (refund) {
        $scope.optype = 'print';
        $scope.printPage(refund);
    };

    // 处理【查询】按钮点击事件
    $scope.handleQueryRefund = function () {
        $scope.query();
    };

    // 处理【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        if ($scope.optype == 'detail') {
            $('#erpDetailRefundPanel').modal('hide');
        } else if ($scope.optype == 'delete') {
            $('#erpDeleteRefundPanel').modal('hide');
        }
    };

    // 处理【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
        if ($scope.optype == 'delete') {
            $scope.delete();
        }
    };

    // 修改
    $scope.put = function () {
        erp_refundService.update($scope.refundDeleteInfo.change_id, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success('标记【已取款】成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 作废
    $scope.delete = function () {
        erp_refundService.delete({
            changeId: $scope.refundDeleteInfo.change_id,
            remark: $scope.refundDeleteInfo.remark
        }, function (resp) {
            $('#erpDeleteRefundPanel').modal('hide');
            if (!resp.error) {
                $uibMsgbox.success('作废成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 查询
    $scope.query = function () {
        erp_refundService.query({
            pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            queryOrderString: $scope.searchParam.order_no,
            refundEncoding: $scope.searchParam.encoding,
            studentInfo: $scope.searchParam.student_info
        }, function (resp) {
            if (!resp.error) {
                $scope.refundList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message)
            }
        })
    };

    $scope.printPage = function(refund){
        var strHtml = '<style type="text/css">';
        strHtml += 'table { width:660px; font:12px Arial;font-weight: 400; }';
        strHtml += 'tr.Line td{ border-top:1px;}';
        strHtml += 'tr td{ height:15px; line-height:15px;}';
        strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
        strHtml += '</style>';
        strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center">';
        strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
        strHtml += '<tr><td>';

        strHtml += '<table border="0" width="660px" align="center">';
        strHtml += '<tr align="left" style="font-weight:900;">';
        strHtml += '<td colspan="5">业务校区:' + refund.branch_name + '	                 日期:' + Format("yyyy-MM-dd", new Date(refund.create_time)) + '</td>';
        strHtml += '</tr>';
        strHtml += '<tr height="15px" style="font-weight:900;">';
        strHtml += '<td align="center">学员姓名</td>';
        strHtml += '<td width="63%" align="left">业务类型</td>';
        strHtml += '</tr>';
        strHtml += '<tr>';
        strHtml += '<td align="center"  >'+ refund.student_name + '(' + refund.student_encoding + ')' +'</td>';
        strHtml += '<td width="63%"  align="left">退费</td>';
        strHtml += '</tr>';
        strHtml += '</table>';

        strHtml += '<table border="0" width="660px" align="center">';
        strHtml += '<tr align="left" height="15px" style="font-weight:900;">';
        strHtml += '<td>单据编号</td>';
        strHtml += '<td>退费总金额</td>';
        strHtml += '</tr>';
        strHtml += '<tr>';
        strHtml += '<td>'+ refund.encoding +'</td>';
        strHtml += '<td>￥'+ refund.fee_amount +'</td>';
        strHtml += '</tr>';
        strHtml += '</table>';
        strHtml += '</body>';

        KlxxPrint(strHtml);
    }

    $scope.query();
}
