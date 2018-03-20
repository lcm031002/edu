/**
 * 
 */
angular.module('ework-ui')
    .controller('hrmEmployeeInfoCtrl', [
        '$scope',
        '$log',
        '$state',
        '$cookieStore',
        '$rootScope',
        'OrgService',
        'PostService',
        'DictItemService',
        'EmployeeExtService',
        'EmployeeManageService',
        'hrmEmployeeService',
        '$uibModal',
        '$uibMsgbox',
        'erp_studentsService',
        hrmEmployeeInfoCtrl
    ]);

function hrmEmployeeInfoCtrl($scope,
    $log,
    $state,
    $cookieStore,
    $rootScope,
    OrgService,
    PostService,
    DictItemService,
    EmployeeExtService,
    EmployeeManageService,
    hrmEmployeeService,
    $uibModal,
    $uibMsgbox,
    erp_studentsService
) {
    //员工列表
    $scope.employeeList = {};
    $scope.itemOperateType = '';
    $scope.selectedItem = {};
    $scope.employee = [];
    $scope.edu = {};
    $scope.paginationBars = [];
    $scope.enterTypeList = [];
    //岗位查询列表
    $scope.postList = [];
    $scope.detailPage = 'templates/hrm/employee/employee_info.html?_=' + (new Date()).getTime();

    $scope.employeeId = null;
    $scope.queryType = '';
    //暂存的订单
    $scope.employeeId = $("#rootIndex_employeeId").val();

    $scope.ret = [];

    // 弹出上传头像框
    $scope.showUploadModal = function(selectedEmp) {
        var modalInstance = $uibModal.open({
            // templateUrl : 'uploadHeadModalInstance.html',
            backdrop: 'static',
            templateUrl: 'templates/block/avatar-upload.html',
            controller: 'blocks_avatarUploadController',
            resolve: {
                onUploadImg: function() {
                    return function(base64Img, $uibModalInstance) {
                        EmployeeManageService.modifyHeadImg({
                            id: $scope.selectedEmp.id.toString(),
                            staff_head: $scope.selectedEmp.staff_head,
                            photoBase64: base64Img
                        }, function(resp) {
                            if (!resp.error) {
                                $scope.selectedEmp.staff_head = resp.url;
                            } else {
                                $uibMsgbox.error(resp.message);
                            }
                            $uibModalInstance.close(base64Img);
                        });
                    }
                }
            }
        });
        modalInstance.result.then(function(result) {}, function() {
            $log.info('DrawModal dismissed at: ' + new Date());
        })
    };

    function queryEmployeeInfo() {
        var param = {};
        param.employeeId = $scope.employeeId;
        hrmEmployeeService.query(param, function(resp) {
            if (!resp.error) {
                $scope.selectedEmp = resp.data;
                if ($scope.selectedEmp.business) {
                    $scope.selectedEmp.business = parseInt($scope.selectedEmp.business);
                    $scope.selectedEmp.dept = parseInt($scope.selectedEmp.dept);
                }
                $('title').text('' + $scope.selectedEmp.employee_name);
                $scope.toQueryItem($scope.selectedEmp);
            } else {
                $uibMsgbox.confirm(resp.message);
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
        //$('#EmpPanel').modal({"show":true,"backdrop":'static'});
        $scope.selectedEmp = emp; //提取选中的员工id方便查询教育经历 ，工作经历等信息
        //param.id = emp.id;

        getRet(emp);

        $scope.itemOperateType = 'query';
        queryEmployeeEdu();
        queryEmployeeExp();
        queryEmployeeSum();
        queryEmployeeRew();
    };

    /**
     * 查询选中的员工定义表中的动态字段
     * 将启用的动态字段全部查出
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
     * 根据id和动态字段查询对应的员工信息，动态字段在后台查出
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
        if (ret) {
            for (var i = 0; i < ret.length; i++) {
                var str = ret[i].fieldKey;
                if (angular.isDefined(employee[0][str])) {
                    ret[i].fieldKeys = employee[0][str];
                }
            };
            //再次进行遍历将数据字典子项加入集合
            for (var i = 0; i < ret.length; i++) {
                if (angular.isDefined(ret[i].fieldKeys)) {
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
    /*$scope.toAddEmpPanel = function(){
		$scope.temp=[];
		var param={};
		$scope.selectedEmp = {};
		$scope.addEmp=true;
	    $('#EmpPanel').modal('show');
	    EmployeeExtService.queryField(param,function(resp){
    		if(resp.error==false){
    			$scope.extField=resp.data;
    		}
    		for(var i=0;i<$scope.extField.length;i++){
    			if($scope.extField[i].dictTypeName!=null){
    				$scope.extField[i].fieldKey=null;
    				$scope.extField[i].selected=$scope.cache[$scope.extField[i].dictTypeName];
    			}else{
    				$scope.extField[i].fieldKey=null;
    			}
    		}
    		$scope.temp=$scope.extField;
    		
    	});
	};*/






    /**
     * 去添加教育经历
     */
    $scope.toAddEdu = function() {
        $scope.eduUpdate = false;
        $scope.selectedEdu = {};
        $("#eduModal").modal({ "show": true, "backdrop": 'static' });
    }

    /**
     * 去修改教育经历
     */
    $scope.toUpdateEdu = function(edu) {
        $scope.eduUpdate = true;
        $("#eduModal").modal("show");
        $scope.selectedEdu = edu;
    }

    /**
     * 去删除教育经历
     */
    $scope.toDeleteEdu = function(edu) {
        $scope.eduDelete = true;
        $scope.selectedEdu = edu;
    }


    /**
     * 去添加工作经历
     */
    $scope.toAddExp = function() {
        $scope.expUpdate = false;
        $scope.selectedExp = {};
        $("#expModal").modal({ "show": true, "backdrop": 'static' });
    }

    /**
     * 去修改工作经历
     */
    $scope.toUpdateExp = function(exp) {
        $scope.expUpdate = true;
        $("#expModal").modal({ "show": true, "backdrop": 'static' });
        $scope.selectedExp = exp;
    }

    /**
     * 去删除工作经历
     */
    $scope.toDeleteExp = function(exp) {
        $scope.expDelete = true;
        $scope.selectedExp = exp;
    }


    /**
     * 去添加工作总结
     */
    $scope.toAddSum = function() {
        $scope.sumUpdate = false;
        $scope.selectedSum = {};
        $("#sumModal").modal({ "show": true, "backdrop": 'static' });
    }

    /**
     * 去修改工作总结
     */
    $scope.toUpdateSum = function(sum) {
        $scope.sumUpdate = true;
        $("#sumModal").modal({ "show": true, "backdrop": 'static' });
        $scope.selectedSum = sum;
    }

    /**
     * 去删除工作总结
     */
    $scope.toDeleteSum = function(sum) {
        $scope.sumDelete = true;
        $scope.selectedSum = sum;
    }


    /**
     * 去添加奖惩信息
     */
    $scope.toAddRew = function() {
        $scope.rewUpdate = false;
        $scope.selectedRew = {};
        $("#rewModal").modal({ "show": true, "backdrop": 'static' });
    }

    /**
     * 去修改奖惩信息
     */
    $scope.toUpdateRew = function(rew) {
        $scope.rewUpdate = true;
        $("#rewModal").modal({ "show": true, "backdrop": 'static' });
        $scope.selectedRew = rew;
    }

    /**
     * 去删除奖惩信息
     */
    $scope.toDeleteRew = function(rew) {
        $scope.rewDelete = true;
        $scope.selectedRew = rew;
    }


    /**
     * 添加员工基础信息
     */
    $scope.addEmployee = function() {
        var param = {};
        /*if($scope.selectedItem.EMPLOYEE_NAME==""||$scope.selectedItem.EMPLOYEE_NAME==null){
        		alert("姓名不能为空");
        		return;
        	}
        if($scope.selectedItem.ENCODING==""||$scope.selectedItem.ENCODING==null){
        	alert("工号不能为空");
        	return;
        }*/
        if($scope.selectedEmp.phone==""||$scope.selectedEmp.phone==null){
        	$uibMsgbox.confirm("手机号不能为空");
        	return;
        }
        queryExtInfo();
        var retEmp = $scope.retEmp;
        for (var i = 0; i < retEmp.length; i++) {
            //过滤，获取填写过的信息，若字段值为null，MyBatis 插入空值时，需要指定JdbcType，否则报错
            if ($scope.temp[i].fieldKey != null) {
                var str = retEmp[i].fieldKey;
                param[str] = $scope.temp[i].fieldKey;
            }
        }
        //添加任职信息
        param.work = $scope.selectedEmp;
        EmployeeManageService.add(param, function(resp) {
            if (resp.error == false) {
                $('#EmpPanel').modal('hide');
                $uibMsgbox.confirm("添加成功");
                $scope.addEmp = false;
                $scope.employeeList[$scope.employeeList.length] = param;
                queryEmployee();
            } else {
                $uibMsgbox.confirm("添加失败！失败信息：" + resp.message);
            }
        })
    };

    /**
     * 修改员工档案
     */
    $scope.updateEmployee = function() {
        /*if($scope.selectedEmp.EMPLOYEE_NAME==""||$scope.selectedEmp.EMPLOYEE_NAME==null){
        		alert("姓名不能为空");
        		return;
        	}
        if($scope.selectedEmp.ENCODING==""||$scope.selectedEmp.ENCODING==null){
        	alert("员工号不能为空");
        	return;
        }*/
        if($scope.selectedEmp.phone==""||$scope.selectedEmp.phone==null){
            $uibMsgbox.confirm("手机号不能为空");
        	return;
        }
        if (new Date($scope.selectedEmp.entryDate) > new Date()) {
            $uibMsgbox.confirm("入职时间不得大于今天");
            return;
        }
        $scope.addEmp = false;
        queryExtInfo();

    };



    /**
     * 将修改的数据放入对象中，该步骤独立出来是为了请求同步
     */
    function updateEmpParam() {
        var param = {};
        for (var i = 0; i < $scope.retEmp.length; i++) {
            var str = $scope.retEmp[i].fieldKey;
            if ($scope.temp[i].fieldKeys != null) {
                $scope.employee[0][str] = $scope.temp[i].fieldKeys;
            }
        }

        $scope.employee[0].id = $scope.selectedEmp.id;
        param = $scope.employee[0];
        EmployeeManageService.updateEmployeeStatic($scope.selectedEmp, function(resp) {
            if (resp.error == false) {
                EmployeeManageService.update(param, function(resp) {
                    if (resp.error == false) {
                        $uibMsgbox.confirm("修改成功", function(res) {
                            if (res == 'yes') {
                                $state.go('hrmEmployee')
                            }
                        });
                        getEmployeeInfo($scope.employee[0]);
                    } else {
                        $uibMsgbox.confirm("修改失败，失败信息：" + resp.message);
                    }
                });
            } else {
                $uibMsgbox.confirm("修改失败，失败信息：" + resp.message);
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
                updateEmpParam();
            }
        });
    }

    /**
     * 添加员工教育经历
     */
    $scope.addEdu = function(selectedEdu) {
        var param = {};
        selectedEdu.employee_id = $scope.selectedEmp.id;
        param = selectedEdu;
        EmployeeManageService.addEmployeeEdu(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("添加员工教育经历成功");
                $("#eduModal").modal("hide");
                queryEmployeeEdu();
            } else {
                $uibMsgbox.confirm("添加失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 修改员工教育经历
     */
    $scope.updateEdu = function(selectedEdu) {
        var param = {};
        param = selectedEdu;
        EmployeeManageService.updateEmployeeEdu(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("修改成功");
                $("#eduModal").modal("hide");
                queryEmployeeEdu();
            } else {
                $uibMsgbox.confirm("修改失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 删除员工教育经历
     */
    $scope.deleteEdu = function() {
        var param = {};
        param.id = $scope.selectedEdu.id;
        EmployeeManageService.deleteEmployeeEdu(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("删除员工教育经历成功");
                $("#DelModal").modal("hide");
                queryEmployeeEdu();
            } else {
                $uibMsgbox.confirm("删除失败：" + resp.message + ",请截图反馈给客服");
            }
            $scope.eduDelete = false;
        })
    }

    /**
     * 查询员工教育经历
     */
    function queryEmployeeEdu() {
        var param = {};
        param.employee_id = $scope.selectedEmp.id;
        EmployeeManageService.queryEmployeeEdu(param, function(resp) {
            if (resp.error == false) {
                $scope.employeeEdu = resp.data;
            }
        })
    }

    /**
     * 查询员工工作经历
     */
    function queryEmployeeExp() {
        var param = {};
        param.employee_id = $scope.selectedEmp.id;
        EmployeeManageService.queryEmployeeExp(param, function(resp) {
            if (resp.error == false) {
                $scope.employeeExp = resp.data;
            }
        })
    }

    /**
     * 添加员工工作经历
     */
    $scope.addExp = function(selectedExp) {
        var param = {};
        selectedExp.employee_id = $scope.selectedEmp.id;
        param = selectedExp;
        EmployeeManageService.addEmployeeExp(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("添加员工工作经历成功");
                $("#expModal").modal("hide");
                queryEmployeeExp();
            } else {
                $uibMsgbox.confirm("添加失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 修改员工工作经历
     */
    $scope.updateExp = function(selectedExp) {
        var param = {};
        param = selectedExp;
        EmployeeManageService.updateEmployeeExp(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("修改成功");
                $("#expModal").modal("hide");
                queryEmployeeExp();
            } else {
                $uibMsgbox.confirm("修改失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 删除员工工作经历
     */
    $scope.deleteExp = function() {
        var param = {};
        param.id = $scope.selectedExp.id;
        EmployeeManageService.deleteEmployeeExp(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("删除员工工作经历成功");
                $("#DelModal").modal("hide");
                queryEmployeeExp();
            } else {
                $uibMsgbox.confirm("删除失败：" + resp.message + ",请截图反馈给客服");
            }
            $scope.expDelete = false;
        })
    }

    /**
     * 查询员工工作总结
     */
    function queryEmployeeSum() {
        var param = {};
        param.employee_id = $scope.selectedEmp.id;
        EmployeeManageService.queryEmployeeSum(param, function(resp) {
            if (resp.error == false) {
                $scope.employeeSum = resp.data;
            }
        })
    }

    /**
     * 添加员工工作总结
     */
    $scope.addSum = function(selectedSum) {
        var param = {};
        selectedSum.employee_id = $scope.selectedEmp.id;
        param = selectedSum;
        param.approval_status = parseInt(param.approval_status);
        EmployeeManageService.addEmployeeSum(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("添加员工工作总结成功");
                $("#sumModal").modal("hide");
                queryEmployeeSum();
            } else {
                $uibMsgbox.confirm("添加失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 修改员工工作总结
     */
    $scope.updateSum = function(selectedSum) {
        var param = {};
        param = selectedSum;
        EmployeeManageService.updateEmployeeSum(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("修改成功");
                $("#sumModal").modal("hide");
                queryEmployeeSum();
            } else {
                $uibMsgbox.confirm("修改失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 删除员工工作总结
     */
    $scope.deleteSum = function() {
        var param = {};
        param.id = $scope.selectedSum.id;
        EmployeeManageService.deleteEmployeeSum(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("删除员工工作总结成功");
                $("#DelModal").modal("hide");
                queryEmployeeSum();
            } else {
                $uibMsgbox.confirm("删除失败：" + resp.message + ",请截图反馈给客服");
            }
            $scope.sumDelete = false;
        })
    }

    /**
     * 添加员工工作总结时查询员工
     */
    $scope.queryEmployeeInfo = function() {
        var searchInfo = $("#employee_search_info").val();
        $scope.onGoingQuery = true;
        var param = {};
        param.searchInfo = searchInfo;
        EmployeeManageService.queryEmployeeInfo(param, function(resp) {
            if (resp.error == false) {
                $scope.employeeListSum = resp.data;
            }
        });
    }

    /**
     * 选择一个员工
     */
    $scope.selectedEmployee = function(employee) {
        //$scope.selectedSum.employeeId=employee.ID;
        $scope.selectedSum.employeeName = employee.EMPLOYEE_NAME;
        $scope.selectedSum.approver = employee.ID;
        $scope.onGoingQuery = false;
        $scope.showQuery = false;
    }

    $scope.showEmployeeQuery = function() {
        $scope.showQuery = true;
    }

    /* $document.on('click', function(event){
	      var element=angular.element(event.target).attr("id");
	    	  $scope.$apply(function() {
	    		  if(element!='showSpan'
	    			  &&element!='selectSpan'
	    		      &&element!='employee_search_info'){
	    	       $scope.onGoingQuery = false;
	    	       $scope.showQuery=false;
	    		  }
	    		  
	    	 });
	      return $document.off('click', event);
	});*/



    /**
     * 查询员工奖惩信息
     */
    function queryEmployeeRew() {
        var param = {};
        param.employee_id = $scope.selectedEmp.id;
        EmployeeManageService.queryEmployeeRew(param, function(resp) {
            if (resp.error == false) {
                $scope.employeeRew = resp.data;
            }
        })
    }

    /**
     * 添加员工奖惩信息
     */
    $scope.addRew = function(selectedRew) {
        var param = {};
        selectedRew.employee_id = $scope.selectedEmp.id;
        param = selectedRew;
        EmployeeManageService.addEmployeeRew(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("添加员工奖惩信息成功");
                $("#rewModal").modal("hide");
                queryEmployeeRew();
            } else {
                $uibMsgbox.confirm("添加失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 修改员工奖惩信息
     */
    $scope.updateRew = function(selectedRew) {
        var param = {};
        param = selectedRew;
        EmployeeManageService.updateEmployeeRew(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("修改成功");
                $("#rewModal").modal("hide");
                queryEmployeeRew();
            } else {
                $uibMsgbox.confirm("修改失败：" + resp.message + ",请截图反馈给客服");
            }
        })
    }

    /**
     * 删除员工奖惩信息
     */
    $scope.deleteRew = function() {
        var param = {};
        param.id = $scope.selectedRew.id;
        EmployeeManageService.deleteEmployeeRew(param, function(resp) {
            if (resp.error == false) {
                $uibMsgbox.confirm("删除员工奖惩信息成功");
                $("#DelModal").modal("hide");
                queryEmployeeRew();
            } else {
                $uibMsgbox.confirm("删除失败：" + resp.message + ",请截图反馈给客服");
            }
            $scope.rewDelete = false;
        })
    }

    /**
     * 上传头像
     */
    $scope.headPic = function() {
        //var selectedEmpId=$scope.selectedEmp.id;
        window.location.href = 'templates/hrm/uploadImage/headPic?selectedEmpId=' + $scope.selectedEmp.id;

    }


    //按照员工id查询岗位信息
    function queryPostByEmpId(emp_id) {
        var param = {};
        $scope.postListToStr = '';
        param.employee_id = emp_id;
        EmployeeManageService.queryPostByEmpId(param, function(resp) {
            if (resp.error == false) {
                $scope.postByEmpList = resp.data;

                for (var i = 0; i < $scope.postByEmpList.length; i++) {
                    $scope.postListToStr += $scope.postByEmpList[i].branchName + ',' + $scope.postByEmpList[i].post_name + "/ ";
                }
            }
        });
    }



    if ($scope.employeeId) {
        //查询当前员工的基本信息
        queryEmployeeInfo();

    }

    queryPostByEmpId($scope.employeeId);
    //查询数据字典所有子项
    queryDictSub();



}