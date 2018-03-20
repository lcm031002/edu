angular.module('ework-ui').controller('erp_teacherExcelInputController', [
  '$rootScope',
  '$scope',
  '$log',
  '$state',
  '$uibMsgbox',
  '$uibModal',
  'erp_TeacherIndexService',
  'erp_subjectService',
  function(
    $rootScope,
    $scope,
    $log,
    $state,
    $uibMsgbox,
    $uibModal,
	erp_TeacherIndexService,
    erp_subjectService
  ) {
    $scope.currentStep = 1;

    $scope.steps = [
      { title: '1. 上传文件' },
      { title: '2. 数据校验' },
      { title: '3. 数据导入' }
    ];
	 $scope.validDisable          = true; // 是否可以开始校验，文件还未上传时，不能点击开始校验
	  $scope.fileUpload            = null; // 待上传的文件
	  $scope.waitingModel          = null; // 等待模态框
	$scope.initImportDataStatus = function() {
		$scope.importData              = []; // 所有数据列表
		$scope.importDataSuccess              = [];
		$scope.importDataFailure              = [];
		$scope.importDataForPageView = [];
		$scope.importDataForView = [];
		$scope.viewDataType = '全部';
		$scope.viewImportedDataType='全部';
	}

    // 分页配置
    $scope.paginationConf = {
		currentPage: 1,
		totalItems: 0,
		itemsPerPage: 10,
		showFirstAndLast: true,
		onChange: function() {
			$scope.genPageData();
		}
    };
	  $scope.genPageData = function(){
		  var startIndex = ($scope.paginationConf.currentPage-1)*$scope.paginationConf.itemsPerPage;
		  var endIndex = $scope.paginationConf.currentPage*$scope.paginationConf.itemsPerPage;
		  $scope.importDataForPageView = [];
		  for(i=startIndex;i<endIndex;i++) {
			  if(!$scope.importDataForView[i]) {
				  break;
			  }
			  $scope.importDataForPageView.push($scope.importDataForView[i]);
		  }
	  };

    // 上传的文件改变事件
    $scope.onFileChange = function(files) {
    	$scope.fileUpload = files[0];
    	$scope.validDisable = !$scope.fileUpload;
    	$scope.$apply();
    }

    // 校验文件
    $scope.handleValidate = function() {
    	$scope.uploadFiles();
    }

	  $scope.filterData = function(){
		if($scope.viewDataType == "全部") {
			$scope.importDataForView = $scope.importData;
		} else if($scope.viewDataType == "成功") {
			$scope.importDataForView = $scope.importDataSuccess;
		} else {
			$scope.importDataForView = $scope.importDataFailure;
		}
		$scope.paginationConf.totalItems = $scope.importDataForView.length || 0; // 设置总条数
		$scope.genPageData();
	  }
	  $scope.filterImportedData = function(){
		if($scope.viewImportedDataType == "全部") {
			$scope.importDataForView = $scope.importData;
		} else if($scope.viewImportedDataType == "已导入") {
			$scope.importDataForView = $scope.importDataSuccess;
		} else {
			$scope.importDataForView = $scope.importDataFailure;
		}
		$scope.paginationConf.totalItems = $scope.importDataForView.length || 0; // 设置总条数
		$scope.genPageData();
	  }
    // 修改数据
    //$scope.handleModifyItem = function(item) {
    //	$uibModal.open({
    //		templateUrl: 'templates/block/modal/teacher-excelInput.modal.html',
    //		controller: 'erp_teacherExcelInputModalController',
    //		resolve: {
    //			teacherDetail: function () {
    //				return {
		//				item:item,
		//				rows:$scope.importData
		//			};
    //			}
    //		}
    //  }).result.then(function(res) {
    //    $scope.proceErrorMsg();
    //  }, function() {
    //  });
    //}

    // 全部导出
    //$scope.exportAll = function(step) {
    //	$scope.outputExcel();
    //}
    //
    //// 错误导出
    //$scope.exportError = function(step) {
    //	if (step == 2) {
    //		$scope.outputExcel(1);
    //	}
    //}

    // 关闭当前页
    $scope.closeCurrentPage = function() {
      history.back();
    }

    // 上传文件
    $scope.uploadFiles = function () {
		$scope.initImportDataStatus();
    	$scope.waitingModal = $uibMsgbox.waiting('正在上传，请稍候...');
    	$.ajaxFileUpload({
    		url: '/erp/teacherservice/inputExcel',
    		secureuri: false,
    		fileElementId: 'fileExcel',
    		dataType: 'json',
    		method: 'post',
    		data: { 
    		},
    		success: function(resp) {
    			// 文件上传完成并且返回校验的数据
    			if ($scope.waitingModal) {
    				$scope.waitingModal.close();
    			}
    			if (!resp.error) {
    				if (resp.data && resp.data.length > 0) {
    					$scope.currentStep = 2;
        				$scope.importData = resp.data;
						$scope.importDataForView = $scope.importData;
    				}
					$scope.paginationConf.totalItems = $scope.importDataForView.length || 0; // 设置总条数
					$scope.genPageData();
					//分类校验成功和校验失败的数据
					for(var i in $scope.importData) {
						if($scope.importData[i].checkStatus == '成功') {
							$scope.importDataSuccess.push($scope.importData[i]);
						}else {
							$scope.importDataFailure.push($scope.importData[i]);
						}
					}
    			} else {
    				$uibMsgbox.warn(resp.message);
    			}
    		},
    		error: function(html, status, e) {
    			if ($scope.waitingModal && $scope.waitingModal.close) {
    				$scope.waitingModal.close();
    			}
    			$uibMsgbox.error('上传失败！' + e)
    		}
    	});
    };

	  $scope.inputData = function(index){
		  var index = index?index:0;
		  var row = $scope.importData[index];
		  if (index >= $scope.importData.length) {//退出递归
			  $scope.currentStep = 3;
			  $('#import-teacher-progress-modal').modal('hide');
			  //分类导入成功和导入失败的数据
			  $scope.importDataSuccess = [];
			  $scope.importDataFailure = [];
			  for(var i in $scope.importData) {
				  if($scope.importData[i].checkStatus == '已导入') {
					  $scope.importDataSuccess.push($scope.importData[i]);
				  }else {
					  $scope.importDataFailure.push($scope.importData[i]);
				  }
			  }
			  return;
		  }
		  $scope.currentImportIndex = index + 1;
		  erp_TeacherIndexService.post({
			  employee_id: _.result(_.find(row.cells,{'cellName':'employee_encoding'}),'holdValue'),
			  encoding: _.result(_.find(row.cells,{'cellName':'teacher_encoding'}),'holdValue'),
			  teacher_name: _.result(_.find(row.cells,{'cellName':'teacher_name'}),'holdValue'),
			  nickname: _.result(_.find(row.cells,{'cellName':'nickname'}),'holdValue'),
			  teacher_age: _.result(_.find(row.cells,{'cellName':'teacher_age'}),'holdValue'),
			  seniority: _.result(_.find(row.cells,{'cellName':'seniority'}),'holdValue'),
			  teacher_type: _.result(_.find(row.cells,{'cellName':'teacher_type'}),'holdValue'),
			  status: _.result(_.find(row.cells,{'cellName':'status'}),'holdValue'),
			  sex: _.result(_.find(row.cells,{'cellName':'sex'}),'holdValue'),
			  phone: _.result(_.find(row.cells,{'cellName':'phone'}),'holdValue'),
			  is_pluralistic: _.result(_.find(row.cells,{'cellName':'is_pluralistic'}),'holdValue'),
			  email: _.result(_.find(row.cells,{'cellName':'email'}),'holdValue'),
			  description:_.result(_.find(row.cells,{'cellName':'description'}),'holdValue'),
			  subject:_.result(_.find(row.cells,{'cellName':'subject_names'}),'holdValue'),
			  bu_id:_.result(_.find(row.cells,{'cellName':'bu'}),'holdValue')
		  }, function(resp) {
			  if (!resp.error) {
				  row.checkStatus = "已导入";
				  $scope.importSuccessItems++;
				  $scope.importSuccessProgress = Number(($scope.importSuccessItems /  $scope.importData.length * 100)).toFixed(2);
			  } else {
				  row.checkStatus = "导入失败";
				  $scope.importFailureItems++;
				  $scope.importFailureProgress = Number(($scope.importFailureItems /  $scope.importData.length * 100)).toFixed(2)
			  }
			  $scope.inputData(++index);
		  });
	  }


    $scope.teacherImport = function() {

		if($scope.importDataFailure.length>0 ) {
			$uibMsgbox.error('存在校验失败的数据，不允许导入');
		} else if($scope.importData.length<=0){
			$uibMsgbox.error('没有数据可以导入');
		} else {
			$uibMsgbox.confirm("即将导入校验成功的数据，共" + $scope.importData.length + "条，确认导入？",function(res){
				if (res == 'yes') {
					$('#import-teacher-progress-modal').modal('show');
					$scope.currentImportIndex    = 1;
					$scope.importSuccessItems = 0;
					$scope.importFailureItems = 0;
					$scope.importSuccessProgress = 0;
					$scope.importFailureProgress = 0;
					$scope.inputData(0);
				}
			});
		}
    }

    // 导出数据
    //$scope.outputExcel = function(handleStatus) {
    //	erp_tmpTeacherInfoService.exportExcel({"batchNo" : $scope.batchNo, "handleStatus" : handleStatus}, function(resp) {
    //		if (!resp.error) {
    //			window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
    //		} else {
    //			$uibMsgbox.error(resp.message);
    //		}
    //	});
    //}
	  // 修改教师弹出框
	  $scope.shopUpdateModal = function(row) {
		  var modalInstance = $uibModal
			  .open({
				  size: 'xlg',
				  templateUrl: 'importTeacherModalInstance.html',
				  controller: 'importTeacherModalInstanceController',
				  resolve: {
					  // 目标参数获取
					  params: function() {
						  return {
							  row: row,
							  rows:$scope.importData
						  }
					  }
				  }});
				  modalInstance.result.then(function(result) {
					  _.remove($scope.importDataFailure,row);
					  var v = _.find($scope.importDataSuccess,row)
					  if(!v) {
						  $scope.importDataSuccess.push(row);
					  }
					  $scope.paginationConf.totalItems = $scope.importDataForView.length || 0; // 设置总条数
				  }, function(reason) {
					  $log.info('DrawModal dismissed at: ' + new Date());
				  });
	  }
}]);

// 修改教师信息
angular.module('ework-ui').controller(
	'importTeacherModalInstanceController', ['$rootScope', '$scope', '$state', '$uibMsgbox',
		'erp_TeacherIndexService', 'erp_employeeService',
		'erp_studentOrgService', 'erp_TeacherListService',
		'$uibModalInstance', 'params','erp_subjectService',
		importTeacherModalInstanceController
	]);

function importTeacherModalInstanceController($rootScope, $scope, $state,
											  $uibMsgbox, erp_TeacherIndexService, erp_employeeService,
											  erp_studentOrgService, erp_TeacherListService, $uibModalInstance,
											  params,erp_subjectService) {
	$scope.rowOrigin = params.row;
	$scope.row = {};
	angular.copy($scope.rowOrigin,$scope.row);
	//绑定到表单的对象
	$scope.bindRow = {
		employee_encoding: _.find($scope.row.cells,{'cellName':'employee_encoding'}),
		teacher_encoding: _.find($scope.row.cells,{'cellName':'teacher_encoding'}),
		teacher_name: _.find($scope.row.cells,{'cellName':'teacher_name'}),
		nickname: _.find($scope.row.cells,{'cellName':'nickname'}),
		teacher_age: _.find($scope.row.cells,{'cellName':'teacher_age'}),
		seniority: _.find($scope.row.cells,{'cellName':'seniority'}),
		teacher_type: _.find($scope.row.cells,{'cellName':'teacher_type'}),
		status: _.find($scope.row.cells,{'cellName':'status'}),
		sex: _.find($scope.row.cells,{'cellName':'sex'}),
		phone: _.find($scope.row.cells,{'cellName':'phone'}),
		is_pluralistic: _.find($scope.row.cells,{'cellName':'is_pluralistic'}),
		bu: _.find($scope.row.cells,{'cellName':'bu'}),
		subject_names: _.find($scope.row.cells,{'cellName':'subject_names'}),
		email: _.find($scope.row.cells,{'cellName':'email'}),
		description: _.find($scope.row.cells,{'cellName':'description'})
	}

	$scope.statusList = [];
	$scope.genderList = [];
	$scope.partTimeList = [];
	$scope.teacherTypeList = [];
	$scope.buList = [];
	$scope.subjectList = [];
	$scope.selSubjectList = [];
	$scope.subjectSearchInfo = '';
	$scope.searchEmployeeInfo = {
		employee_name:''
	};

	// 关联员工输入框，名称变化则重新查询员工信息
	$scope.onEmployeeNameChange = function() {
		$scope.isDown = 'loading';
		$scope.searchResult = [];
		erp_employeeService.query({
			row_num: 10,
			pageSize: 10,
			currentPage: 1,
			employee_name:$scope.searchEmployeeInfo.employee_name
		}, function(resp) {
			$scope.isDown = '';
			if (!resp.error) {
				$scope.searchResult = resp.data;
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	}

	//下拉框值改变事件
	$scope.onChangeForSelector = function(cell,selectorDatas) {
		cell.checkErrorMessage = null;
		cell.displayValue = _.result(_.find(selectorDatas,{"code":cell.holdValue}),'name') ;
	}
	//必填文本框改变事件
	$scope.onChangeForPureNotNull = function(cell) {
		if(cell.holdValue != undefined && cell.holdValue != null && _.trim(cell.holdValue.toString()!= '')) {
			cell.checkErrorMessage = null;
		} else {
			cell.checkErrorMessage = "必填";
		}
		cell.displayValue = cell.holdValue;
	}
	//普通文本框改变事件
	$scope.onChangeForCommonText = function(cell) {
		cell.displayValue = cell.holdValue;
	}

	// 从员工查询控件的查询结果中选择一条数据触发该事件
	$scope.selectEmployee = function(employee) {
		$scope.bindRow.employee_encoding.holdValue = employee.id;
		$scope.bindRow.employee_encoding.displayValue = employee.employee_name;
		$scope.bindRow.employee_encoding.checkErrorMessage = null;
		$("#importEmployeeModifyDropdown").toggleClass("hidden");
	};
	$scope.showDropdown = function() {
		$("#importEmployeeModifyDropdown").toggleClass("hidden");
	}
	//教师编码输入框值改变事件
	$scope.onTeacherEncodingChange= function() {
		//1.查询教师编码在文档中是否已经存在
		var isExist = false;
		$.each(params.rows,function(i,n) {
			if(n.rowNumber != $scope.row.rowNumber) {
				if(_.result(_.find(n.cells,{'cellName':'teacher_encoding'}),'holdValue') == $scope.bindRow.teacher_encoding.holdValue){
					isExist = true;
					return false;
				}
			}
		});
		$scope.bindRow.teacher_encoding.checkErrorMessage = isExist?"教师编码在文档中已经存在":null;
		$scope.bindRow.teacher_encoding.displayValue = $scope.bindRow.teacher_encoding.holdValue;
	}
	//联系方式格式校验
	$scope.onPhoneChange=function() {
		var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
		if(!phoneReg.test($scope.bindRow.phone.holdValue)) {
			$scope.bindRow.phone.checkErrorMessage = '手机号码格式不正确';
		} else {
			//1.查询电话号码在文档中是否已经存在
			var isExist = false;
			$.each(params.rows,function(i,n) {
				if(n.rowNumber != $scope.row.rowNumber) {
					if(_.result(_.find(n.cells,{'cellName':'phone'}),'holdValue') == $scope.bindRow.phone.holdValue){
						isExist = true;
						return false;
					}
				}
			});
			$scope.bindRow.phone.checkErrorMessage = isExist?"电话号码在文档中已经存在":null;
		}
		$scope.bindRow.phone.displayValue = $scope.bindRow.phone.holdValue;
	}

	//修改弹出框打开初始化科目信息
	$scope.initSubject = function(){
		var names = $scope.bindRow.subject_names.displayValue.split(/[,，]/);
		var ids = $scope.bindRow.subject_names.holdValue.split(/[,，]/);
		$.each(ids,function(i,n) {
			var subject = _.find($scope.subjectList,{id:parseInt(n)});
			subject.checked = true;
			$scope.selSubjectList.push(subject);
		})
	}

	//移除科目
	$scope.removeSubject = function (subject) {
		subject.checked = false;
		_.remove($scope.selSubjectList, subject);
		$scope.bindRow.subject_names.checkErrorMessage = $scope.selSubjectList.length<=0? '科目必填':null;
	}

	//选中，反选科目
	$scope.handleSubjectChange = function (subject) {
		if (subject.checked && !_.some($scope.selSubjectList, subject)) {
			$scope.selSubjectList.push(subject);
		} else {
			_.remove($scope.selSubjectList, subject);
		}
		$scope.bindRow.subject_names.checkErrorMessage = $scope.selSubjectList.length<=0? '科目必填':null;
	}

	//服务端校验
	$scope.checkOnServer = function() {
		//1.教师在数据库中是否已经存在
		//2.联系方式在数据库中是否已经存在
		erp_TeacherListService.checkImportRecord({
			teacher_encoding:$scope.bindRow.teacher_encoding.holdValue,
			phone: $scope.bindRow.phone.holdValue
		},function(resp) {
			if(!resp.error) {
				var cells = resp.data.cells;
				$scope.bindRow.teacher_encoding.checkErrorMessage = _.result(_.find(cells,{'cellName':"teacher_encoding"}),'checkErrorMessage');
				$scope.bindRow.phone.checkErrorMessage = _.result(_.find(cells,{'cellName':"phone"}),'checkErrorMessage');
				if( !($scope.bindRow.teacher_encoding.checkErrorMessage || $scope.bindRow.phone.checkErrorMessage)) {
					//1.校验成功，将数据同步到表格
					//-1.格式化科目信息
					var subjectNames = [];
					var subjectIds = [];
					$.each($scope.selSubjectList,function(i,n) {
						subjectNames.push(n.name);
						subjectIds.push(n.id);
					});
					$scope.bindRow.subject_names.displayValue = subjectNames.join(',');
					$scope.bindRow.subject_names.holdValue = subjectIds.join(',');
					//-2.将数据同步到表格
					$scope.rowOrigin.cells = $scope.row.cells;
					$scope.rowOrigin.checkStatus='成功';
					//2.关闭窗口
					$uibModalInstance.close("success");
				} else {
					$scope.rowOrigin.checkStatus='失败';
				}
			} else {
				$uibMsgbox.error(resp.message);
			}
		})
	}

	$scope.checkRow = function() {
		var htmlCheckFlag = 'success';
		$.each($scope.row.cells,function(i,n) {
			if(n.checkErrorMessage) {
				htmlCheckFlag = 'fail';
				return false;
			}
		});
		if(htmlCheckFlag == 'success') {
			//服务端校验
			$scope.checkOnServer();
		}
	}

	/**
	 * 模态框取消
	 */
	$scope.handleModalCancel = function() {
		$uibModalInstance.dismiss('cancel');
	}

    $scope.changeBu = function(){
        $scope.selSubjectList = [];
        erp_subjectService.querySelectDatas({
            bu_id:$scope.bindRow.bu.holdValue
        },function(resp){
            if(!resp.error){
                $scope.subjectList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.initialize = function() {
        erp_TeacherIndexService.toManage({}, function(resp) {
            if (!resp.error) {
                $.each(resp.teacherType, function(i, n) {
                    n.code = parseInt(n.code);
                })
                $scope.teacherTypeList = resp.teacherType;
                $.each(resp.teacherStatus, function(i, n) {
                    n.code = parseInt(n.code);
                })
                $scope.statusList = resp.teacherStatus;
                $.each(resp.gender, function(i, n) {
                    n.code = parseInt(n.code);
                })
                $scope.genderList = resp.gender;
                $.each(resp.isPartTime, function(i, n) {
                    n.code = parseInt(n.code);
                })
                $scope.partTimeList = resp.isPartTime;
                //$scope.cityList = resp.cityList;
                $scope.buList = $.map(resp.buList,function(n,i) {
                    if(n.parent_id == resp.city_id) return n;
                });
                //$scope.subjectList = resp.subjectList;
                //$scope.updateTeacher.city_id = resp.city_id;
                //$scope.updateTeacher.bu_id = resp.bu_id;
            } else {
                $uibMsgbox.error(resp.message);
            }
            erp_subjectService.querySelectDatas({
                bu_id:$scope.bindRow.bu.holdValue
            },function(resp){
                if(!resp.error){
                    $scope.subjectList = resp.data;
                    $scope.initSubject();
                }else{
                    $uibMsgbox.error(resp.message);
                }
            });
        });
    }
	$scope.initialize();
}
