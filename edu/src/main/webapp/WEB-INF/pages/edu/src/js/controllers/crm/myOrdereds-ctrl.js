/**
 * Created by Liyong.zhu on 2015/12/16.
 */
"use strict";
angular.module('ework-ui').controller(
		'crmMyOrderedsCtrl',
		[ '$scope', 'crm_MyOrderedsService', 'crm_queryDictDataService','crm_branchService','$uibMsgbox',
				crmMyOrderedsCtrl ]);

function crmMyOrderedsCtrl($scope, crm_MyOrderedsService,
		crm_queryDictDataService,crm_branchService,$uibMsgbox) {
	$scope.params = {};
	
	$scope.searchParam = {};
	$scope.pageCallBack = function() {

	};

	$scope.Master = {};

	crm_MyOrderedsService.count({}, function(resp) {
		if (!resp.error) {
			$scope.Master.myOrdereds = parseInt(resp.totalCount);
		}
	});
	
//	agPageObject($scope, crm_MyOrderedsService, $scope.params);
//	$scope.load();
	
	$scope.initDictflag = false;
	
	$scope.initBranchflag = false;

	/* 搜索_start */
	$scope.query = function() {
		$scope.searchParam.p_restypeId = $scope.searchParam.resType.id;
		$scope.searchParam.p_branchId = $scope.searchParam.branch.id;
		$scope.searchParam.page = $scope.currentPage;
		agPageObject($scope, crm_MyOrderedsService, $scope.searchParam);
		$scope.load();
	};
	$scope.onDateRangeChange = function () {
		$scope.query()
	}
	
	// 资源类型
	crm_queryDictDataService.query({
		dictTypeCode : 'ResType'
	}, function(data) {
		$scope.resTypes = [];
		$scope.resTypes = data;

		/* 资源类别_start */
		$scope.resTypesSearch = [];
		$scope.resTypesSearch.push({
			"id" : -1,
			"name" : "全部"
		});
		for (var i = 0; i < data.length; i++) {
			$scope.resTypesSearch.push(data[i]);
		}
		$scope.searchParam.resType = $scope.resTypesSearch[0];
		$scope.initflag = true;
		if($scope.initflag && $scope.initBranchflag) {
			$scope.query();
			
		}
		/* 资源类别_end */
	});
	
	 crm_branchService.query({
         buId:null
     },function(resp){
         if(!resp.error){
             $scope.branchs = [];
             $scope.branchs.push({
            	"id" : -1,
     			"text" : "全部"});
             for(var i=0;i<resp.data.length;i++) {
            	 $scope.branchs.push(resp.data[i]);
             }
             $scope.searchParam.branch = $scope.branchs[0];
             $scope.initBranchflag = true;
             if($scope.initflag && $scope.initBranchflag) {
     			$scope.query();
     		}
         }
     });

	/* 修改删除_start */
	$scope.order = {};
	$scope.formMsg = '';
	$scope.formWait = false;
	$scope.selectItem = {};
	$scope.update = function(item) {
		for (var i = 0; i < $scope.items.length; i++) {
			$scope.items[i].select = '';
		}
		item.select = 'selected';
		$scope.selectItem = item;

		$scope.order = {};
		$scope.formMsg = '';
		$scope.formWait = false;

		$scope.order.rescRecId = $scope.selectItem.rescRecId;
		$scope.order.id = $scope.selectItem.order_id;
		$scope.order.orderNo = $scope.selectItem.order_no;
		$scope.order.feeAmount = $scope.selectItem.fee_amount;
		$scope.order.courseCount = $scope.selectItem.course_count;
		$scope.order.channel = {
			id : $scope.selectItem.channel_id,
			name : $scope.selectItem.channel_name
		};
		$scope.order.orderTime = $scope.selectItem.order_time;
		$scope.order.remark = $scope.selectItem.remark;
		$('#myOrderedUpdate').modal('toggle');
	}

	$scope.submitOrder = function() {
		if ($scope.formWait)
			return;
		if (isEmpty($scope.order.id)) {
			$scope.formMsg = '订单ID不能为空！';
			return;
		}
		if (isEmpty($scope.order.orderNo)) {
			$scope.formMsg = '报班单号不能为空！';
			return;
		}
		if (isEmpty($scope.order.feeAmount)) {
			$scope.formMsg = '订单金额不能为空！！';
			return;
		}
		if (isEmpty($scope.order.courseCount)) {
			$scope.formMsg = '课时数不能为空！';
			return;
		}
		$scope.order.channel_id = $scope.order.channel.id;
		if (isEmpty($scope.order.channel_id)) {
			$scope.formMsg = '来源渠道不能为空！';
			return;
		}
		$scope.order.orderTime = $('#orderTime').val();
		if (isEmpty($scope.order.orderTime)) {
			$scope.formMsg = '报班日期不能为空！';
			return;
		}
		if (isNotEmpty($scope.order.remark)
				&& $scope.order.remark.length > 4000) {
			$scope.formMsg = '备注字数不可以超过4000字！';
			return;
		}
		$scope.formWait = true;
		$scope.formMsg = '正在处理，请等待。。。。';
		crm_MyOrderedsService.update($scope.order, function(ResMap) {
			$scope.formWait = false;
			$scope.formMsg = '';
			if (ResMap.code == 200) {
				$scope.selectItem.order_no = $scope.order.orderNo;
				$scope.selectItem.fee_amount = $scope.order.feeAmount;
				$scope.selectItem.course_count = $scope.order.courseCount;
				$scope.selectItem.channel_id = $scope.order.channel.id;
				$scope.selectItem.channel_name = $scope.order.channel.name;
				$scope.selectItem.order_time = $scope.order.orderTime;
				$scope.selectItem.remark = $scope.order.remark;
				openDialog.myOrderedUpdateToggle();
			} else
				$scope.formMsg = '操作失败:' + ResMap.msg;
		});
	}

	$scope.remove = function(item) {
		if ($scope.formWait)
			return;
		if (!(window.confirm("是否删除[" + item.serial_no + "]?")))
			return;
		$scope.formWait = true;
		crm_MyOrderedsService.remove({
			order_ids : (item.order_id + ''),
			rescRecId : item.rescRecId
		}, function(ResMap) {
			$scope.formWait = false;
			if (ResMap.code == 200) {
				$scope.load();
			} else {
				$uibMsgbox.error('删除失败：' + ResMap.msg);
			}
		});
	}
	/* 修改删除_end */
	
	/* 二次成单_strat */
	$scope.add = function(item) {
		crm_MyOrderedsService.addagain({
			order_ids : (item.order_id + ''),
			rescRecId : item.rescRecId
		}, function(ResMap) {
			if (ResMap.code == 200) {
				$uibMsgbox.success('二次成单成功，请到资源处理查看！');
				$scope.load();
			} else {
				$uibMsgbox.error('二次成单失败：' + ResMap.msg);
			}
		});
	}
	/* 二次成单_end */

	/* 导出_start */
	$scope.exportMyOrder = function() {
		if (confirm("确定要导出?")) {
			var param = "";
			$scope.searchParam.p_restypeId = $scope.searchParam.resType.id;
			$scope.searchParam.p_branchId = $scope.searchParam.branch.id;
			for ( var p in $scope.searchParam) {
				param += p + "="+ $scope.searchParam[p] + "&";
			}
			if (param && param != '') {
				param += "1=1"
			}
			location.href = "/gxhcrm/export/exportMyOrder?"+ encodeURI(encodeURI(param));
		}
	}
	/* 导出_end */

}