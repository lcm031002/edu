"use strict";
angular.module('ework-ui').controller(
		'crmPerformanceCtrl',
		[ '$scope', '$interval', 'crm_BranchsVisibleService',
				'crm_BranchPerformanceService', crmPerformanceCtrl ]);

function crmPerformanceCtrl($scope, $interval, crm_BranchsVisibleService,
		crm_BranchPerformanceService) {
	$scope.isPerformanceLoading = 'loading...';
	$scope.currnentBranch = {};
	$scope.branchsVisible = [];
	$scope.dataSets = [];
	

	var branchPerformance = {
		"fillColor" : "rgba(135, 206, 250,1)",
		"strokeColor" : "rgba(135, 206, 250,1)",
		"pointColor" : "rgba(255,179,63,1)",
		"pointStrokeColor" : "#fff",
		"pointHighlightFill" : "#fff",
		"pointHighlightStroke" : "rgba(255,179,63,1)",
		"data" : []
	};

	var personalPerformance = {
		"fillColor" : "rgba(255, 127, 80,1)",
		"strokeColor" : "rgba(255, 127, 80,1)",
		"pointColor" : "rgba(84,171,26,1)",
		"pointStrokeColor" : "#fff",
		"pointHighlightFill" : "#fff",
		"pointHighlightStroke" : "rgba(84,171,26,1)",
		"data" : []
	};

	var lineChartData = {};

	function createLine() {
		var ctx = document.getElementById("Performance").getContext("2d");
		var barChart = new Chart(ctx).Bar(lineChartData, {
			scaleSteps: 20, //y轴刻度的个数

			responsive : true,
			 //是否绘制柱状条的边框
            barShowStroke: false,
            //柱状条边框的宽度
            barStrokeWidth: 2,
            //柱状条组之间的间距(过大或过小会出现重叠偏移错位的效果，请控制合理数值)
            barValueSpacing: 150,
            //每组柱状条组中柱状条之间的间距
            barDatasetSpacing: 1
		});
		
		$scope.isPerformanceLoading = '';
		console.log(barChart.generateLegend());
	}

	function createLineChart() {
		$scope.isPerformanceLoading = 'loading...';
		// 延迟加载
		var stop = $interval(function() {
			if (document.getElementById("Performance")) {
				createLine();
				if (angular.isDefined(stop)) {
					$interval.cancel(stop);
					stop = undefined;
				}
				$scope.isPerformanceLoading = '';
			}
		}, 100);
	}

	$scope.fresh = function() {
		createLineChart();
	}
	/**
	 * 查询当前的可见校区
	 */
	function queryBranchsVisibleService() {
		crm_BranchsVisibleService.query({}, function(resp) {
			if (!resp.error) {
				$scope.branchsVisible = resp.branchs;
				if ($scope.branchsVisible) {
					$.each($scope.branchsVisible, function(i, mm) {
						mm.name = mm.org_name;
					})
				}
				if ($scope.branchsVisible && $scope.branchsVisible.length > 1) {
					$scope.currnentBranch = $scope.branchsVisible[0];
				} else if ($scope.branchsVisible
						&& $scope.branchsVisible.length == 1) {
					$scope.currnentBranch = $scope.branchsVisible[0];
				}
				queryBranchPerformance($scope.currnentBranch);
			}
		})
	}
	queryBranchsVisibleService();

	$scope.changeBranch = function() {
		queryBranchPerformance();
	}
	/**
	 * 查询给定校区的业绩信息
	 * 
	 * @param branch
	 */
	function queryBranchPerformance(branch) {
		$scope.isPerformanceLoading = 'loading...';
		var val = $(".selectBranchsPerformance").val();
		crm_BranchPerformanceService.query({
			branchId : null == branch ? $scope.branchsVisible[val].id
					: branch.id
		}, function(resp) {
			$scope.isPerformanceLoading = '';
			branchPerformance.data = [];
			var dayLabels = [];
			$scope.productLineAmount = '';
			$scope.productLineName = "业务线总业绩:";
			$scope.schoolAmount = '0';
			$scope.schoolName = resp.branchName;
			personalPerformance.data = [];
			if (!resp.error && resp.data) {
				for (var i = 0; i < resp.data.length; i++) {
					branchPerformance.data.push(resp.data[i].schoolAmount);
					personalPerformance.data.push(resp.data[i].personalAmount);
					if(resp.data[i].personalAmount != 0) {
//						resp.dayLabels[i] = resp.data[i].cnselorName+" "+resp.dayLabels[i];
						dayLabels.push(resp.data[i].cnselorName);
					}
					
					$scope.productLineAmount= resp.data[i].productLineAmount;
					$scope.schoolAmount= resp.data[i].schoolAmount;
				}
				if(resp.data== null || resp.data.length == 0){
					branchPerformance.data.push(0);
					personalPerformance.data.push(0);
					dayLabels.push('');
				}
				branchPerformance.label = resp.branchName;
				personalPerformance.label = "个人业绩";
				lineChartData.datasets = [ branchPerformance,
					personalPerformance ];
				lineChartData.labels = dayLabels;
				$scope.dataSets = lineChartData.datasets;
				$scope.labels = dayLabels;
				createLineChart();
			}

		})
	}

};

