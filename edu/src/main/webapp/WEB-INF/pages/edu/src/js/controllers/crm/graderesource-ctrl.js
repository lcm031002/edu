"use strict";
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

angular.module('ework-ui').controller(
		'crmGradeResourceCtrl',
		[ '$scope', 'crm_GradeResourceService', 'crm_BranchsVisibleService','crm_isSchoolAdminService',
            crmGradeResourceCtrl ]);

function crmGradeResourceCtrl($scope, crm_GradeResourceService, crm_BranchsVisibleService,crm_isSchoolAdminService) {
	
	$scope.params = {};

	$scope.grade_headers = [];

	var flag = true;

	$scope.paramdate = new Date().format("yyyy年MM月");

	$scope.query = function() {
		// console.log($scope.gradeBranch);
		$scope.params.branch_id = $scope.gradeBranch.id;
		$scope.params.p_start_date = $('#cdt_start_date').val();
		crm_GradeResourceService.query(
						$scope.params,
						function(data) {
							if (data.dataList) {
								var grades = [];
								var model_header;
								var tmp;
								// 一列数据，统计数据是个数组
								var model = {
									counts : []
								};
								$(data.dataList).each(function(i, m) {
													if (!tmp) {// 初始化数据
														tmp = m.GRTYPE;
														model.grtype = m.GRTYPE;
													}
													if (tmp != m.GRTYPE) {// 当类型发生改变
														grades.push(model);// 保存上一列数据
														model = {
															counts : []
														};// 创建新的一列
														tmp = m.grtype;
														model.grtype = m.GRTYPE;
														flag = false;
													}
													model.counts.push({
														count : m.count
													});// 保存指定列的统计数据
													if (flag) {
														model_header = {};// 设置表头
														model_header.grade_name = m.GRADE_NAME;
														$scope.grade_headers
																.push(model_header);
													}
													if (i + 1 == data.dataList.length) {
														grades.push(model);
													}
												});

								$scope.items = grades;

								/* 合计_start */
								$scope.grade_headers.push({
									grade_name : '合计'
								});
								// 合计行
								for (var i = 0; i < $scope.items.length; i++) {
									var row = $scope.items[i];
									var total = 0;
									for (var j = 0; j < row.counts.length - 1; j++) {
										total += row.counts[j].count;
									}
									row.counts.push({
										count : total
									});
								}
								// 合计列
								model = {
									counts : [],
									grtype : '合计'
								};
								var allTotal = 0;
								for (var i = 0; i < $scope.grade_headers.length - 1; i++) {
									var total = 0;
									for (var j = 0; j < $scope.items.length; j++) {
										for (var h = 0; h < $scope.items[j].counts.length; h++) {
											if (i == h) {
												total += $scope.items[j].counts[h].count;
												break;
											}
										}
									}
									allTotal = allTotal + total;
									model.counts.push({
										count : total
									});
								}
								model.counts.push({
									count : allTotal
								});
								$scope.items.push(model);
								/* 合计_end */
							}
						}, function(err) {
							alert('请联系管理员！错误：' + err.data);
						});
	}
	

	$scope.refresh = function() {
		$scope.grade_headers = [];
		$scope.items = [];
		flag = true;
		$scope.query();
	}

	$scope.excel = function() {
		var param = "";
		for ( var p in $scope.searchParam) {
			if (!$scope.searchParam[p]) {
				param += p + "=" + $scope.searchParam[p] + "&";
			}
		}
		if (confirm("确定要导出?")) {
			location.href = contextPath
					+ "/gxhcrm/home/gradeResource/exp/toExp?"
					+ encodeURI(encodeURI(param));
		}
	};
	
	$scope.gradeBranchsVisible = [];
	var all_school = {
			'id':"",
			'name':"全部",
			'org_name':"全部"
	};
	/**
	 * 查询当前的可见校区
	 */
	crm_BranchsVisibleService.query({}, function(resp) {
		if (!resp.error) {
//			$scope.gradeBranchsVisible = resp.branchs;
			if ($scope.gradeBranchsVisible) {
				$.each(resp.branchs, function(i, mm) {
					mm.name = mm.org_name;
					$scope.gradeBranchsVisible.push(mm);
				});
				$scope.gradeBranch = $scope.gradeBranchsVisible[0];
				$scope.query();
			}
		}
	});
	
//	crm_isSchoolAdminService.query({}, function(res) {
//		$scope.isSchoolAdmin=res.flag;
//		if($scope.isSchoolAdmin) {
//			$scope.gradeBranchsVisible.unshift(all_school);
//		}
//		if($scope.gradeBranchsVisible.length >1) {
//			$scope.gradeBranch = $scope.gradeBranchsVisible[0].id;
//		}
//		$('#cdt_branch_id').val($scope.gradeBranch);
//		console.log($('#cdt_branch_id').val());
//		$scope.query();
//	});

	$scope.changeBranch2 = function() {
		$scope.refresh();
	}

	$scope.monthSelect = function() {
		$scope.refresh();
	}
}