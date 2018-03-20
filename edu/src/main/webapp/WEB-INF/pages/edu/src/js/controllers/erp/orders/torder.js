/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";

// angular.module('ework-ui').controller('erp_torderModalController', [
//     '$rootScope',
//     '$scope',
//     '$log',
//     '$uibModalInstance'
//     'orderCourseList',
//     erp_torderModalController]);

// function erp_torderModalController(
//     $rootScope,
//     $scope,
//     $log,
//     $uibModalInstance,
//     orderCourseList
//     ) {
//     $scope.orderCourseList = orderCourseList;

//     $scope.ok = function() {
//         $uibModalInstance.close();
//     }
// }


angular.module('ework-ui').controller('erp_orderModalController', [
	'$rootScope',
	'$scope',
	'$log',
	 '$uibMsgbox', // 消息提示框服务，其他服务按需引入
	'$uibModalInstance',
	'orderCourseList',
	erp_orderModalController
]);

angular.module('ework-ui').controller('erp_orderCacenlModalController', [
   '$rootScope',
   '$scope',
   '$log',
   '$uibMsgbox',
   '$uibModalInstance',
   'erp_FinanceOrderService',
   'orderId',
   erp_orderCacenlModalController
 ]);

function erp_orderModalController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $uibModalInstance,
    orderCourseList
    ) {
    $scope.orderCourseList = orderCourseList;
    $scope.ok = function () {
        $uibModalInstance.close();
    }
}

function erp_orderCacenlModalController(
	    $rootScope,
	    $scope,
	    $log,
	    $uibMsgbox,
	    $uibModalInstance,
	    erp_FinanceOrderService,
	    orderId
	    ) {
	    $scope.orderId = orderId;
	    $scope.remark='';
	    $scope.ok = function () {
	    	 $scope.del();
	    	$uibModalInstance.close();
	    }

	    // 删除
	    $scope.del= function () {
	    	erp_FinanceOrderService.remove({
	    		remark:$scope.remark ,
	    		orderId : $scope.orderId 
	    	}, function (resp) {
	    		if (!resp.error) {
	               // $scope.query();
	            }else{
	            	$uibMsgbox.error(resp.message);
	            }
	        });
	    };
	   
	}


angular.module('ework-ui').controller('erp_orderController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    '$uibModal',
    'erp_FinanceOrderService',
    'erp_orderManagerService',
    'PUBORGService',
    erp_orderController
    ]);

function erp_orderController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $uibModal,
    erp_FinanceOrderService,
    erp_orderManagerService,
    PUBORGService
  ) {
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索年级名称
    $scope.searchParam = {
    		bu_id: '',
    		order_no: '',
    		branch_id: '',
    		business_type: 1,
    		student_name: ''
    };
    // 团队列表
    $scope.buList = [];
    // 所有校区列表
    $scope.allBranchList = [];
    // 过滤后的校区列表
    $scope.branchList = [];
    // 订单单据详情列表
    $scope.orderCourseList = [];
    $scope.orderId= '';
    //业务模式列表
    $scope.businessTypeList = [{"key" : 1, "value" : "班级课"},
	                   		   {"key" : 2, "value" : "一对一"},
	                   		   {"key" : 3, "value" :"晚辅导"}];
    //订单类型列表
    $scope.orderTypeList = [{
        key: 1, value: '新单'
    }, {
        key: 2, value: '赠单'
    }, {
        key: 3, value: '转班单'
    }, {
        key: 4, value: '续单'
    }]

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
        itemsPerPage: 10,
        onChange: function(){
            $scope.query();
        }
    };

    // 处理【作废订单】按钮点击事件
    $scope.handleCancelOrder = function (id) {
        var r = window.confirm('确定作废选中订单？');
        if (r == true) {
	        $scope.orderId = id;
	        $scope.openCancelModal('lg');
//	        $scope.openModal('lg');
        }
    };

    // 处理【查询订单】按钮点击事件
    $scope.handleQueryOrder = function () {
        $scope.query();
    };

    // 打开取消的模态对话框
    $scope.openCancelModal = function (size) {
        var modalInstance = $uibModal.open({
            templateUrl: 'CancelTorderModal.html',
            controller: 'erp_orderCacenlModalController',
            size: size,
            resolve: {
                orderId: function () {
                    return $scope.orderId;
                }
            }
        })

        modalInstance.result.then(function (result) {
        	 $scope.query();
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        })
    };
    
    // 打开模态对话框
    $scope.openModal = function (size) {
        var modalInstance = $uibModal.open({
            templateUrl: 'erpTorderModal.html',
            controller: 'erp_orderModalController',
            size: size,
            resolve: {
                orderCourseList: function () {
                    return $scope.orderCourseList;
                }
            }
        })
        modalInstance.result.then(function (result) {
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        })
    };

    // 提交
    $scope.add = function () {
    	erp_FinanceOrderService.addOrder($scope.orderDetail, function (resp) {
            $scope.query();
        });
    };

    // 单据详情
    $scope.queryOrderDetail = function (orderId) {
    	erp_FinanceOrderService.queryOrderCouseDetail({
            orderId : orderId
        }, function (resp) {
            if (!resp.error) {
                $scope.orderCourseList = resp.data;
                $scope.openModal('lg');
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    };
   

    // 查询方法
    $scope.query = function () {
    	erp_FinanceOrderService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            bu_id: $scope.searchParam.bu_id,
            business_type: $scope.searchParam.business_type,
            order_no: $scope.searchParam.order_no,
            student_name: $scope.searchParam.student_name,
            branch_id: $scope.searchParam.branch_id,
        }, function (resp) {
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.orderList=resp.data;
                $scope.searchParam.bu_id=resp.bu_id;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    };
    
    $scope.querySelectDatas = function (id) {
    	erp_FinanceOrderService.querySelectDatas({
    		id : id
    	},function(resp) {
            if(!resp.error) {
                $scope.lastOrderList = resp.data;
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    };
    
    $scope.printPage = '';
    $scope.printOrder = function(order){
        $scope.printPage = 'beginPrint';
        erp_orderManagerService.query({
            order_id:order.id,
            orderType:'temporaryOrder'
        },function(resp){
            $scope.openPanel = '';
            if(!resp.error){
                $scope.temporaryOrder = resp.data;
                if($scope.temporaryOrder.business_type == 2) {//1对1
                    CreatePrintPageGxhYdyBb($scope.temporaryOrder);
                } else if($scope.temporaryOrder.business_type == 1) {//班级课
                    if ($scope.temporaryOrder.city_id == 3) {
                        CreatePrintPageXiamen($scope.temporaryOrder);
                    } else {
                        CreatePrintPage($scope.temporaryOrder);
                    }
                } else {
                    CreatePrintPage(resp.data);
                }
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    }
    
    // 业务类型
    $scope.businessType = function (type) {
        return getTypeName($scope.businessTypeList, type);       
    }

    // 订单类型
    $scope.orderType = function (type) {
        return getTypeName($scope.orderTypeList, type);
    }

    // 获取某类型key对应的Value
    function getTypeName(typeArray, type) {
        var text = '未知';
        for (var i = 0; i < typeArray.length; i++) {
            if (type == typeArray[i].key) {
                text = typeArray[i].value
            }
        }
        return text;
    }

    // 打印页面
    //function CreatePrintPage(data) {
    //    //01报班
    //    var strHtml = '<style type="text/css">';
    //    strHtml += 'table { width:660px; font:12px Arial;font-weight: 400; }';
    //    strHtml += 'tr.Line td{ border-top:1px;}';
    //    strHtml += 'tr td{ height:15px; line-height:15px;}';
    //    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    //    strHtml += '</style>';
    //    strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center">';
    //    strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
    //    strHtml += '<tr><td>';
    //
    //    strHtml += '<table border="0" width="660px" align="center">';
    //    strHtml += '<tr align="left" style="font-weight:900;">';
    //    strHtml += '<td colspan="5">业务校区:' + data.branch_name + '	经办人:' + data.employee_name + '</td>';
    //    strHtml += '</tr>';
    //    strHtml += '<tr align="center" style="font-weight:900;">';
    //    strHtml += '<td width="150">学员编号</td>';
    //    strHtml += '<td width="66">学员姓名</td>';
    //    strHtml += '<td width="185">单据编号</td>';
    //    strHtml += '<td width="68">业务类型</td>';
    //    strHtml += '<td width="76">业务日期</td>';
    //    strHtml += '</tr>';
    //    strHtml += '<tr align="center">';
    //    strHtml += '<td>'+data.encoding+'</td>';
    //    strHtml += '<td>'+data.student_name+'</td>';
    //    strHtml += '<td>'+data.encoding+'</td>';
    //    strHtml += '<td>报班</td>';
    //    strHtml += '<td>'+Format("yyyy-MM-dd", new Date(data.create_time))+'</td>';
    //    strHtml += '</tr>';
    //    strHtml += '</table>';
    //
    //    strHtml += '<table border="0" width="660px">';
    //    strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
    //    strHtml += '<td>编号</td>';
    //    strHtml += '<td>课程商品名称</td>';
    //    strHtml += '<td>上课时间</td>';
    //    strHtml += '<td>下课时间</td>';
    //    strHtml += '<td>报班课时</td>';
    //    strHtml += '<td>报班总金额</td>';
    //    strHtml += '</tr>';
    //    for(var i = 0 ; i < data.details.length ; i++){
    //        strHtml += '<tr align="center">';
    //        strHtml += '<td>'+(i+1)+'</td>';
    //        strHtml += '<td>'+data.details[i].course_name+'</td>';
    //        strHtml += '<td>'+data.details[i].start_time+'</td>';
    //        strHtml += '<td>'+data.details[i].end_time+'</td>';
    //        strHtml += '<td>'+data.details[i].course_total_count+'</td>';
    //        strHtml += '<td>￥'+data.details[i].former_sum_price+'</td>';
    //        strHtml += '</tr>';
    //    }
    //
    //    strHtml += '</table>';
    //
    //    strHtml += '<table border="0" width="660px">';
    //    strHtml += '<tr height="15px">';
    //    strHtml += '<td style="font-weight:900;">优惠金额：'+(data.sum_price-data.actual_price)+'</td>';
    //    strHtml += '<td style="font-weight:900;">实际需缴费金额：￥'+data.actual_price+'</td>';
    //    strHtml += '<td style="font-weight:900;">已缴费金额：￥'+data.actual_price+'</td>';
    //    strHtml += '</tr>';
    //    strHtml += '</table>';
    //
    //    strHtml += '<table border="0" width="660px">';
    //    for(var i = 0 ; i < data.payment.details.length ; i++){
    //        strHtml += '<tr height="15px">';
    //        strHtml += '<td style="font-weight:900;">缴费明细：</td>';
    //        strHtml += '<td>'+ convertPayName(data.payment.details[i].payment_way) + '</td>';
    //        strHtml += '<td>￥'+(data.payment.details[i].staffappprem?data.payment.details[i].staffappprem:0)+'</td>';
    //        strHtml += '<td>'+(data.payment.details[i].org_name?data.payment.details[i].org_name:"")+'</td>';
    //        if(data.payment.details[i].device_code){
    //            strHtml += '<td>'+data.payment.details[i].device_code+'</td>';
    //        }
    //        strHtml += '<td>'+(data.payment.details[i].createTime?data.payment.details[i].createTime:"")+'</td>';
    //        strHtml += '</tr>';
    //    }
    //    if(data.bu_id==12){
    //        strHtml += '<tr height="15px"  style="font-weight:900;"><td colspan="6">以上优惠均需报整期课程并消耗完毕，若产生退费/冻结，则优惠取消</td></tr>';
    //    }else{
    //        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    //    }
    //    for(var i = 0 ; i < 9-data.payment.details.length-data.payment.details.length ; i++){
    //        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    //    }
    //    strHtml += '</table></td></tr>';
    //    strHtml += '</table>';
    //    strHtml += '</body>';
    //    KlxxPrint(strHtml);
    //
    //}
    //
    //function CreatePrintPageXiamen(data) {
    //    //01报班
    //    var strHtml = '<style type="text/css">';
    //    strHtml += 'table { width:660px; font:12px Arial;font-weight: 400; }';
    //    strHtml += 'tr.Line td{ border-top:1px;}';
    //    strHtml += 'tr td{ height:15px; line-height:15px;}';
    //    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    //    strHtml += '</style>';
    //    strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center">';
    //    strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
    //    strHtml += '<tr><td>';
    //
    //    strHtml += '<table border="0" width="660px" align="center">';
    //    strHtml += '<tr align="left" style="font-weight:900;">';
    //    strHtml += '<td colspan="5">业务校区:' + data.branch_name +  '	经办人:' + data.employee_name  + '</td>';
    //    strHtml += '</tr>';
    //    strHtml += '<tr height="15px" style="font-weight:900;">';
    //    strHtml += '<td align="center">学员姓名</td>';
    //    strHtml += '<td width="63%" align="left">业务类型</td>';
    //    strHtml += '</tr>';
    //    strHtml += '<tr>';
    //    strHtml += '<td align="center"  >'+data.student_name+'</td>';
    //    strHtml += '<td width="63%"  align="left">报班</td>';
    //    strHtml += '</tr>';
    //    strHtml += '</table>';
    //
    //    strHtml += '<table border="0" width="660px" align="center">';
    //    strHtml += '<tr align="left" height="15px" style="font-weight:900;">';
    //    strHtml += '<td>编号</td>';
    //    strHtml += '<td>报班总金额</td>';
    //    strHtml += '</tr>';
    //    strHtml += '<tr>';
    //    strHtml += '<td>'+1+'</td>';
    //    strHtml += '<td>￥'+data.actual_price+'</td>';
    //    strHtml += '</tr>';
    //    strHtml += '</table>';
    //
    //    strHtml += '<table border="0" width="660px">';
    //    strHtml += '<tr height="15px">';
    //    strHtml += '<td style="font-weight:900;">优惠金额：'+0+'</td>';
    //    strHtml += '<td style="font-weight:900;">实际需缴费金额：￥'+data.actual_price+'</td>';
    //    strHtml += '<td style="font-weight:900;">已缴费金额：￥'+data.actual_price+'</td>';
    //    strHtml += '</tr>';
    //    strHtml += '</table>';
    //
    //    strHtml += '<table border="0" width="660px">';
    //    strHtml += '<tr height="15px">';
    //    strHtml += '<td style="font-weight:900;">缴费明细：</td>';
    //    strHtml += '<td>1</td>';
    //    strHtml += '<td>￥'+data.actual_price+'</td>';
    //    strHtml += '<td>'+data.branch_name+'</td>';
    //    strHtml += '<td>'+ Format("yyyy-MM-dd", new Date(data.create_time))+'</td>';
    //    strHtml += '</tr>';
    //    if(data.bu_id==12){
    //        strHtml += '<tr height="15px"  style="font-weight:900;"><td colspan="6">以上优惠均需报整期课程并消耗完毕，若产生退费/冻结，则优惠取消</td></tr>';
    //    }else{
    //        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    //    }
    //    for(var i = 0 ; i < 9-data.payment.details.length-data.details.length ; i++){
    //        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    //    }
    //    strHtml += '</table></td></tr>';
    //    strHtml += '</table>';
    //    strHtml += '</body>';
    //    KlxxPrint(strHtml);

    //}
    $scope.query();
}