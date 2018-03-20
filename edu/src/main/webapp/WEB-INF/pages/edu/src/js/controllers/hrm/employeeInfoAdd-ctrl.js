/**
 * 
 */
angular.module('ework-ui').controller(
		'hrmEmployeeInfoAddCtrl',
		[ '$scope', '$log', '$state', '$cookieStore', '$rootScope',
				'OrgService', 'PostService', 'DictItemService',
				'EmployeeExtService', 'EmployeeManageService',
				'hrmEmployeeService','erp_organizationService', '$uibMsgbox',
			hrmEmployeeInfoAddCtrl ]);

function hrmEmployeeInfoAddCtrl($scope, $log, $state, $cookieStore, $rootScope,
		OrgService, PostService, DictItemService, EmployeeExtService,
		EmployeeManageService, hrmEmployeeService,erp_organizationService,$uibMsgbox) {
	// 员工列表
	$scope.employeeList = {};
	$scope.itemOperateType = '';
	$scope.selectedItem = {};
	$scope.employee = [];
	$scope.edu = {};
	$scope.paginationBars = [];
	$scope.enterTypeList = [];
	// 岗位查询列表
	$scope.postList = [];
	$scope.detailPage = 'templates/hrm/employee/employee_info.html?_='
			+ (new Date()).getTime();

	$scope.employeeId = null;
	$scope.queryType = '';
	$scope.addEmp = true;
	$('title').text('员工添加 | 人力');

	function queryEmployeeInfo() {
		var param = {};
		param.employeeId = $scope.employeeId;
		hrmEmployeeService.query(param, function(resp) {
			if (!resp.error) {
				$scope.selectedEmp = resp.data;
				$('title').text('' + $scope.selectedEmp.employee_name);
				$scope.toQueryItem($scope.selectedEmp);
			} else {
				alert(resp.message);
			}
		})
	}

	/**
	 * 查询数据字典子典所有数据
	 */
	function queryDictSub() {
		var param = {};
		DictItemService.queryDictSubAll(param, function(resp) {
			if (resp.error == false) {
				$scope.cache = resp.data;
				$scope.enterTypeList = $scope.cache.ENTERTYPE;
				$scope.poslevelList = $scope.cache.POSLEVEL;
				$scope.departmentList = $scope.cache.DEPARTMENT;
				// 由于该方法用到$scope.cache,必须放在查询数据字典子项成功之后，否则属于异步查询，会导致toAddEmpPanel()先queryDictSub()执行完，出现数据加载未完成！！！
				toAddEmpPanel();
			}
		});
		OrgService.queryProductLine({}, function(resp) {
			if (!resp.error && resp.data && resp.data.length > 0) {
				$scope.productLineList = resp.data;
			}
		});
	}

	/**
	 * 打开员工档案管理的面板
	 */
	$scope.toQueryItem = function(emp) {
		$scope.addEmp = false;
		// $('#EmpPanel').modal({"show":true,"backdrop":'static'});
		$scope.selectedEmp = emp; // 提取选中的员工id方便查询教育经历 ，工作经历等信息
		// param.id = emp.id;

		getRet(emp);

		$scope.itemOperateType = 'query';

	};

	/**
	 * 查询选中的员工定义表中的动态字段
	 */
	function getRet(emp) {
		var param = {};
		EmployeeExtService.queryField(param, function(resp) {
			if (resp.error == false) {
				$scope.ret = resp.data;
			}
			getEmployeeInfo(emp);
		});
	}

	/**
	 * 查询员工信息表中的基础信息
	 */
	function getEmployeeInfo(emp) {
		var param = {};
		param.id = emp.id;
		EmployeeManageService.query(param, function(resp) {
			if (resp.error == false) {
				$scope.employee = resp.data;
			}
			getSelectedEmp($scope.ret, $scope.employee);
		})
	}

	/**
	 * 查询动态的员工基础信息
	 */
	function getSelectedEmp(ret, employee) {
		$scope.temp = [];
		if (ret && ret.length) {
			for (var i = 0; i < ret.length; i++) {
				var str = ret[i].fieldKey;
				if (angular.isDefined(employee[0][str])) {
					ret[i].fieldKeys = employee[0][str];
				}
			}
			;
			// 再次进行遍历将数据字典子项加入集合
			for (var i = 0; i < ret.length; i++) {
				if (angular.isDenfined(ret[i].fieldKeys)) {
					ret[i].selected = [];
					ret[i].selected = $scope.cache[ret[i].fieldKey];
				}
			}
			$scope.temp = ret;
		}

	}

	/**
	 * 准备添加员工基本信息
	 */
	function toAddEmpPanel() {
		$scope.temp = [];
		var param = {};
		$scope.selectedEmp = {};
		// $scope.addEmp=true;
		EmployeeExtService.queryField(param, function(resp) {
			if (!resp.error) {
				$scope.extField = resp.data;
				if ($scope.extField) {
					for (var i = 0; i < $scope.extField.length; i++) {
						if ($scope.extField[i].fieldType == '数据字典') {
							var str = "";
							str = $scope.extField[i].fieldKey;
							$scope.extField[i].selected = $scope.cache[str];
						}
					}
					$scope.temp = $scope.extField;
					$.each($scope.temp,function(i,n) {
						if(n) {
							n.fieldKeys = n.selected?(n.selected[0]? n.selected[0].code:null):null;
						}
					})
				}
			}
		});
	};

	/**
	 * 将要添加的数据放入对象中，该步骤独立出来是为了请求同步，
	 */
	function addEmpParam() {
		var param = {};
		for (var i = 0; i < $scope.retEmp.length; i++) {
			// 过滤，获取填写过的信息，若字段值为null，MyBatis 插入空值时，需要指定JdbcType，否则报错
			if ($scope.temp[i].fieldKeys != null) {
				var str = $scope.retEmp[i].fieldKey;
				param[str] = $scope.temp[i].fieldKeys;
			}
		}

		// 添加任职信息
		param.work = $scope.selectedEmp;

		var waitingModal = $uibMsgbox.waiting('保存中，请稍候...');
		EmployeeManageService.add(param, function(resp) {
			waitingModal.close();
			if (resp.error == false) {
				$('#EmpPanel').modal('hide');
				$uibMsgbox.confirm("添加成功", function (res) {
					if (res == 'yes') {
						$state.go('hrmEmployee')
					}
				});
				$scope.employeeList[$scope.employeeList.length] = param;
//				queryEmployeeInfo();
			} else {
				$uibMsgbox.confirm("添加失败！失败信息：" + resp.message);
			}
		})
	}

	/**
	 * 再次查询员工定义表中启用的字段信息，获取员工信息的key
	 */
	function queryExtInfo() {
		var param = {};
		EmployeeExtService.queryField(param, function(resp) {
			if (resp.error == false) {
				$scope.retEmp = resp.data;
				addEmpParam();
			}
		});
	}

	/**
	 * 添加员工基础信息
	 */
	$scope.addEmployee = function() {

		if ($scope.selectedEmp.employee_name == ""
				|| $scope.selectedEmp.employee_name == null) {
			$uibMsgbox.confirm("姓名不能为空");
			return;
		}
		if ($scope.selectedEmp.encoding == ""
				|| $scope.selectedEmp.encoding == null) {
			$uibMsgbox.confirm("编码不能为空");
			return;
		}
		if ($scope.selectedEmp.phone == ""
				|| $scope.selectedEmp.phone == null) {
			$uibMsgbox.confirm("手机号不能为空");
			return;
		}
        if(new Date($scope.selectedEmp.entryDate)> new Date()){
            $uibMsgbox.confirm("入职时间不得大于今天");
            return;
        }
		var addFlag = true;
		$.each($scope.temp,function(i,n) {
			if (n.fieldKeys ==undefined || n.fieldKeys == null || n.fieldKeys === "") {
				$uibMsgbox.confirm(n.fieldName + "不能为空");
				addFlag=false;
				return false;
			}
		});
		if(addFlag) {
			queryExtInfo();
		}
	};
	/**
	 * 上传头像
	 */
	$scope.headPic = function() {
		// var selectedEmpId=$scope.selectedEmp.id;
		window.location.href = 'templates/hrm/uploadImage/headPic?selectedEmpId='
				+ $scope.selectedEmp.id;

	}

	// 查询数据字典所有子项
	queryDictSub();

}
