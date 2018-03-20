angular.module('ework-ui').controller('erp_studentIntegralController', [
	'$rootScope',
	'$scope',
	'$log',
	'$state',
	'$uibMsgbox',
	'erp_studentIndexIntegralService',
	erp_studentIntegralController
]);

function erp_studentIntegralController(
	$rootScope,
	$scope,
	$log,
	$state,
	$uibMsgbox,
	erp_studentIndexIntegralService
) {
	// 学员ID
	$scope.studentId = $("#rootIndex_studentId").val();
	
	// 查询条件
	$scope.searchParams = {
		p_start_date: '',
		p_end_date: '',
		p_account_id: -1,
		p_student_id: $("#rootIndex_studentId").val()
	};

	// 分页配置
	$scope.pageConf = {
		currentPage: 1,
		totalItems: 0,
		itemsPerPage: 10,
		onChange: function () {
			$scope.queryDetail()
		}	
	};

	// 积分校区列表容器样式表
  $scope.integralContainerStyle = {
      "width": "auto"
  }	;

  // 积分校区列表
	$scope.integralList = [];

	// 重置各校区积分的container的宽度
	$scope.resetIntegraContainerWidth = function () {
		$scope.integralContainerStyle.width =
			$scope.integralList.length * 220 + 'px';
	};

	// 查询所有校区的流水
	$scope.handleQueryAllDetail = function () {
		$scope.searchParams.p_account_id = -1;
		//$scope.queryDetail();
		$scope.integralDetailList = [];
		$scope.pageConf.totalItems = 0;
	};

	// 查询指定校区的积分流水
	$scope.handleQueryBranchDetail = function (detail) {
		$scope.searchParams.p_account_id = detail.id;
		$scope.queryDetail();
	};

	// 查询积分流水
	$scope.queryDetail = function () {
		if(!$scope.searchParams.p_account_id || $scope.searchParams.p_account_id < 0) {
			$uibMsgbox.alert("只能查询校区积分流水，请选定校区！");
			return;
		}
		var params = angular.extend($scope.searchParams, {
			currentPage: $scope.pageConf.currentPage,
			pageSize: $scope.pageConf.itemsPerPage
		});

		var _uibModalInstance = $uibMsgbox.waiting('数据加载中，请稍候...');
		// 查询列表,成功后，初始化分页总条数
		erp_studentIndexIntegralService.queryStudentIntegralFlow(params, function (resp) {
			_uibModalInstance.close();
			if (!resp.error) {
				$scope.integralDetailList = resp.data;
				$scope.pageConf.totalItems = resp.total || 0;
			} else {
				$uibMsgbox.error(resp.message)
			}
		});
	};

	// 导出积分流水
	$scope.outputDetail = function () {
		if(!$scope.searchParams.p_account_id || $scope.searchParams.p_account_id < 0) {
			$uibMsgbox.alert("只能导出校区积分流水，请选定校区！");
			return;
		}
		var params = angular.extend($scope.searchParams, {});

		var _uibModalInstance = $uibMsgbox.waiting('正在导出数据，请稍候...');
		// 查询列表,成功后，初始化分页总条数
		erp_studentIndexIntegralService.outputStudentIntegralFlow(params, function (resp) {
			_uibModalInstance.close();
			if (!resp.error) {
				window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	};

	// 查询学员积分
	$scope.queryIntegral = function () {
		erp_studentIndexIntegralService.queryStudentIntegral($scope.searchParams, function (resp) {
			if (!resp.error) {
				$scope.integralList = resp.data;
				// 总账户
				var totalAcount = {
					integral : 0,
					money : 0
				};
				if($scope.integralList && $scope.integralList.length > 0) {
					_.each($scope.integralList, function (item) {
						totalAcount.integral += item.crrent_integral || 0;
						totalAcount.money += item.attend_amount || 0;
					});
				}
				$scope.totalAcount = totalAcount;

				// 查询到结果调用以下方法调整积分管理的宽度
				$scope.resetIntegraContainerWidth();
			} else {
				$uibMsgbox.error(resp.message)
			}
		});
	};

	// 初始化查询
	$scope.queryIntegral();
}