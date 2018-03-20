/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular.module('ework-ui').controller(
		'erp_teacherGroupController',
		[ '$scope', '$log', '$uibMsgbox', '$uibModal',
				'erp_organizationService', 'erp_teacherGroupService',
				erp_teacherGroupController ]);

function erp_teacherGroupController($scope, $log, $uibMsgbox, $uibModal,
		erp_organizationService, erp_teacherGroupService) {
	// 表单操作类型，添加： add，修改：put
	$scope.optype = 'add'; //

	// 搜索字段
	$scope.searchParam = {
		teach_group_name : ''
	};

	// 教研组列表
	$scope.teacherGroupList = [];

	// 与表单绑定的数据，用于添加和修改
	$scope.teacherGroup = {};
	
	// 当前登录用户所在城市团队列表
	$scope.oriBuList = [];

	// 团队列表
	$scope.buList = [];

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
	 *            perPageOptions [可选，默认]
	 * @param {Function}
	 *            perPageOptions [description]
	 */
	$scope.paginationConf = {
		currentPage : 1, // 当前页
		totalItems : 0,
		onChange : function() {
			$scope.query();
		}
	}

	$scope.paginationBars = [];

	// 处理【添加教研组】按钮点击事件
	$scope.handleAddTeacherGroup = function() {
		$scope.optype = 'add';
		openTeacherGroup();
	}

	// 处理【修改教研组】按钮点击事件
	$scope.handlePutTeacherGroup = function(rowData) {
		$scope.optype = 'put';
		erp_teacherGroupService.toManage({
			id : rowData.id
		}, function(resp) {
			if (!resp.error) {
				$scope.teacherGroup = resp.data;

				erp_organizationService.teamList({city_id : rowData.city_id}, function(resp) {
					if (!resp.error) {
						$scope.buList = resp.data;
						openTeacherGroup();
					}
				});
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	}

	// 打开对话框
	function openTeacherGroup() {
		$uibModal.open({
			resolve : {
				optype : function() {
					return $scope.optype;
				},
				teacherGroup : function() {
					return ($scope.optype == 'add') ? {} : $scope.teacherGroup;
				},
				buList : function() {
					return ($scope.optype == 'add') ? $scope.oriBuList : $scope.buList;
				}
			},
			backdrop: 'static',
			templateUrl : 'templates/block/modal/teacher-group.modal.html',
			controller : 'erp_teacherGroupModalController'
		}).result.then(function(teacherGroup) {
			console.log(teacherGroup)
			if ($scope.optype == 'add') {
				$scope.add(teacherGroup);
			} else {
				$scope.put(teacherGroup);
			}
		}, function() {
		});
	}

	// 处理【删除教研组】按钮点击事件
	$scope.handleDeleteTeacherGroup = function(id) {
		if (window.confirm('确定删除选中教研组？')) {
			$scope.remove(id);
		}
	}

	// 处理【查询教研组】按钮点击事件
	$scope.handleQueryTeacherGroup = function() {
		$scope.query();
	}

	$scope.checkBeforeSave = function() {
		if (!$scope.teacherGroup.teach_group_name) {
			$uibMsgbox.error("教研组名称必填");
			return false;
		}

		if (!$scope.teacherGroup.bu_id) {
			$uibMsgbox.error("团队必填");
			return false;
		}
	}

	// 查询教研组
	$scope.query = function() {
		erp_teacherGroupService.query({
			pageSize : $scope.paginationConf.itemsPerPage,
			currentPage : $scope.paginationConf.currentPage,
			p_teach_group_name : $scope.searchParam.teach_group_name,
		}, function(resp) {
			if (!resp.error) {
				$scope.teacherGroupList = resp.data;
				$scope.paginationConf.totalItems = resp.total;
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	}

	// 添加教研组
	$scope.add = function(teacherGroup) {
		erp_teacherGroupService.post(teacherGroup || $scope.teacherGroup,
				function(resp) {
					if (!resp.error) {
						$uibMsgbox.success("添加成功");
						$scope.query();
						$('#erpTeacherGroupPanel').modal('hide');
					} else {
						$uibMsgbox.error(resp.message);
					}
				});
	}

	// 修改教研组
	$scope.put = function(teacherGroup) {
		erp_teacherGroupService.put(teacherGroup || $scope.teacherGroup,
				function(resp) {
					if (!resp.error) {
						$uibMsgbox.success("修改成功");
						$scope.query();
						$('#erpTeacherGroupPanel').modal('hide');
					} else {
						$uibMsgbox.error(resp.message);
					}
				});
	}

	// 删除教研组
	$scope.remove = function(id) {
		erp_teacherGroupService.remove({
			"id" : id
		}, function(resp) {
			if (!resp.error) {
				$uibMsgbox.success("删除成功");
				$scope.query();
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	}
	
	$scope.initialize = function() {
		$scope.query();
		erp_organizationService.teamList({}, function(resp) {
			if (!resp.error) {
				$scope.oriBuList = resp.data;
				$scope.buList = resp.data;
			}
		});
	}

	$scope.initialize();
}
