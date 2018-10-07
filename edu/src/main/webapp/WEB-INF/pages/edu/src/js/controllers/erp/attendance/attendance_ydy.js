/**
 * Created by Liyong.zhu on 2016/9/30.
 * Modify by Baiqb@klxuexi.org 2017-08-25
 */
"use strict";
angular.module('ework-ui').controller('erp_attendanceYdyController', [
	'$rootScope',
	'$scope',
	'$uibModal',
	'$uibMsgbox',
	'edu_accountService',
	'erp_studentCounselorService',
	'erp_studentCourseSchedulingService',
	'erp_attendanceService',
	'erp_dictService',
	erp_attendanceYdyController
]);

function erp_attendanceYdyController(
	$rootScope,
	$scope,
	$uibModal,
	$uibMsgbox,
    edu_accountService,
	erp_studentCounselorService,
	erp_studentCourseSchedulingService,
	erp_attendanceService,
	erp_dictService
) {
	// 考勤类型【考勤】
	$scope.attendTypeList = []
	// 考勤类型【排课取消】
	$scope.attendTypeCancelList = []
    // 排课状态
	$scope.attendTypeList = [
		{ "key": null, "value": "全部"},
		{ "key": 28, "value": "未考勤" },
		{ "key": 21, "value": "正常上课" },
		{ "key": 23, "value": "排课取消" },
		{ "key": 29, "value": "考勤作废" }
	];

	$scope.attendance = {
		start_date: getCurrentDate(),
		end_date: getCurrentDate(),
		start_time: '07:00',
		end_time: '23:00',
		attend_type: null
	};
	$scope.attendanceListLoading = false;
	$scope.attendanceList = [];

	$scope.checkAllFlag = false;

	$scope.queryStudentSchedulings = function () {
		$scope.attendList = []
		$scope.attendanceListLoading = true;
		var attendType = null; // 正常排课
		if ($scope.attendance.attend_type) {
			attendType = $scope.attendance.attend_type;
		}
		erp_studentCourseSchedulingService.query({
			counselor_id: $rootScope.curAccount.employeeId,
			start_date: $scope.attendance.start_date,
			end_date: $scope.attendance.end_date,
			start_time: $scope.attendance.start_time,
			end_time: $scope.attendance.end_time,
			attend_type: attendType,
			student_info: $scope.attendance.student_info
		}, function (resp) {
			$scope.attendanceListLoading = false;
			$scope.token = resp.token;
			_.forEach(resp.data, function (item) {
				item.optype="saved"
			})
			if (!resp.error) {
				$scope.attendanceList = resp.data;
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	}
	/**
	 * 批量操作
	 * @param operate 操作类型 21-考勤 23-排课取消
	 */
	$scope.handleBatchAttendance = function (operate) {
		var attendList = _.cloneDeep($scope.getSelections());
		$scope.handleAttend(attendList, operate);

	}
    /**批量打印
	 *
     * @param operate
     */
    $scope.morePrint=function() {
        var attendList = _.cloneDeep($scope.getSelections());
        if (!attendList || attendList.length == 0|| attendList.length == 1) {
            $uibMsgbox.error("请至少选择二条记录");
            return false;
        }
        else{
            var useragent = navigator.userAgent;
            if(useragent.indexOf('Android') > -1 || useragent.indexOf('Adr') > -1)  {
                var attendanceListIds=[];
                for (var i=0;i<attendList.length;i++){
                    attendanceListIds.push(attendList[i].id);
                }
                if(location.href.indexOf('klxuexi.org')>0){
				window.open("/printhtml/print_ydy.html?attendanceListIds=" + attendanceListIds.join(','));}
				else{
                window.open("/edu/printhtml/print_ydy.html?attendanceListIds=" + attendanceListIds.join(','));
				}
			} else {
                $scope.printMoreYdyAttendance(attendList);
            }
        }

    }
	/**
	 * 在考勤之前先对批量操作的数据进行校验
	 */
	$scope.checkBeforeAttend = function (originAttendList, operate) {
		var attendList = _.cloneDeep(originAttendList)
		if (!attendList || attendList.length == 0) {
			$uibMsgbox.error("请至少选择一条记录");
			return false;
		}

		var idList = [];
		$.each(attendList, function (idx, attend) {
			if (attend.attend_type == operate) {
				idList.push(attend.id);
			} else {
				attend.attend_type = operate;
			}
		});
		if (operate == 21) {
			var futureDaysList = []
			_.forEach(attendList, function (item) {
				if(moment(item.course_date, "YYYYMMDD") > moment()) {
					futureDaysList.push(item.id)
				}
			})
			if (futureDaysList.length > 0) {
				var errMsg = '考勤单号[' + futureDaysList.join(',') + '还未到上课日期，不能进行考勤！';
				return $uibMsgbox.error(errMsg);
			}
		}
		if (idList.length > 0) {
			var errMsg = '考勤单号[' + idList.join(',') + ((operate == 21) ? ']已正常上课,不能再次考勤！' : ']已排课取消，不能再次取消！');
			$uibMsgbox.error(errMsg);
			return false;
		}

		return true;
	}

	// 考勤置空
	$scope.cancelAttendance = function (attend) {
		if (attend.attend_type != 21) {
			$uibMsgbox.error("该记录未考勤，不能置空");
			return;
		}

		attend.attend_type = 20;
		$scope.handleAttend([attend], 20);
	}
	// 排课取消
	$scope.cancelScheduing = function (attend) {
		if (attend.attend_type == 23) {
			$uibMsgbox.error("该记录已排课取消，不能再次排课取消");
			return;
		}

		attend.attend_type = 23;
		$scope.handleAttend([attend], 23);
	}
	
	function openAttendModal (recordList, operate) {
		return $uibModal.open({
			templateUrl: 'batchAttendYdy.html',
			resolve: {
				subAttendTypeCancelList: function () {
					return $scope.subAttendTypeCancelList
				},
				subAttendTypeList: function () {
					return $scope.subAttendTypeList
				},
				attendList: function () {
					return recordList
				},
				operate: function () {
					return operate
				},

			},
			size: 'xlg',
			controller: ['$scope', 'subAttendTypeList', 'subAttendTypeCancelList','attendList', 'operate', function (
				$scope, subAttendTypeList, subAttendTypeCancelList, attendList, operate
			) {
				// 考勤时，默认考勤类型为 正常上课（code: 21）
				// 排课取消时，默认考勤类型为 排课取消（code: 23）
				var defaultAttendType = operate == '21' ? '21':'23'
				_.forEach(attendList, function (item) {
					item.sub_attend_type = defaultAttendType
				})
				$scope.attendList = attendList
				$scope.operate = operate
				$scope.subAttendTypeList = subAttendTypeList
				$scope.subAttendTypeCancelList = subAttendTypeCancelList
				var titleMap = {
					20: '置空',
					21: '考勤',
					23: '排课取消',
					29: '考勤作废'
				}
				$scope.title = titleMap[operate]
				$scope.onSave = function () {
					$scope.$close($scope.attendList)
				}
			}]
		})
	}

	// 处理考勤
	$scope.handleAttend = function (attendList, operate) {
		if (!$scope.checkBeforeAttend(attendList, operate)) {
			return false;
		}
		openAttendModal(attendList, operate).result.then(function () {
			_.forEach(attendList, function (item) {
				if (item.copyInfo) {delete item.copyInfo}
				item.attend_type = operate
			});
            var _waitingModal = $uibMsgbox.waiting('操作中，请稍候...');
			erp_attendanceService.ydyAttend({"token" : $scope.token, "attendList" : attendList}, function (resp) {
                _waitingModal.close();
				if (!resp.error) {
					var msgMap = {
						20: '置空成功',
						21: '考勤成功',
						23: '排课取消成功',
						29: '考勤作废成功'
					}
					$uibMsgbox.alert(msgMap[operate]);
					$scope.queryStudentSchedulings();
				} else {
					$uibMsgbox.error(resp.message);
				}
			});
		}, function () {
			$scope.queryStudentSchedulings();
		})
	}

	// 获取所选择的数据
	$scope.getSelections = function () {
		var selections = [];
		if ($scope.attendanceList && $scope.attendanceList.length > 0) {
			$.each($scope.attendanceList, function (idx, attendance) {
				if (attendance.selectFlag) {
					selections.push(attendance);
				}
			});
		}
		return selections;
	}
	// 处理全选事件
	$scope.onCheckAll = function () {
		if ($scope.attendanceList && $scope.attendanceList.length > 0) {
			$.each($scope.attendanceList, function (idx, attendance) {
				attendance.selectFlag = attendance.attend_type != 28 ? false : !!$scope.checkAllFlag;
			});
		}
	}

	$scope.getAttendTypes = function () {
		erp_dictService.getDictData({
			typeCode: 'ydyAttendType',
			subTypeCode: 'cancelSchedule'
		}).$promise.then(function (resp) {
			$scope.subAttendTypeCancelList = _.sortBy(resp.data, 'code')
		})

		erp_dictService.getDictData({
			typeCode: 'ydyAttendType',
			subTypeCode: 'attend'
		}).$promise.then(function (resp) {
			$scope.subAttendTypeList = _.sortBy(resp.data, 'code')
		})
	}
	
	// 考勤记录表信息修改
	$scope.editAttendRecord = function (record) {
		record.optype="edit"
		record.copyInfo = {
			sub_attend_type: record.sub_attend_type,
			remark: record.remark
		}
	}
	// 考勤记录表信息修改保存
	$scope.saveAttendRecord = function (record) {
		record.optype="saved"
		erp_attendanceService.ydyAttendPut(_.pick(record, ['id', 'sub_attend_type','remark']), function (resp) {
			if (!resp.error) {
				$uibMsgbox.alert('修改成功');
			} else {
				$uibMsgbox.error(resp.message);
			}
		});
	}
	// 考勤记录表信息修改取消
	$scope.cancelAttendRecord = function (record) {
		record.optype="saved"
		record.sub_attend_type = record.copyInfo.sub_attend_type
		record.remark = record.copyInfo.remark
	}

    // 打印一对一考勤信息
    $scope.printYdyAttendance = function(attendance) {
        $scope.printPage = 'beginPrint';
        erp_attendanceService.batchPrint({
            attendanceListIds: attendance.id,
        }, function (resp) {
            if (!resp.error) {
            	var attendanceListId = attendance.id;
                var useragent = navigator.userAgent;
            	if(useragent.indexOf('Android') > -1 || useragent.indexOf('Adr') > -1){
            		if(location.href.indexOf('klxuexi.org')>0){
                    window.open("/printhtml/print_ydy.html?attendanceListIds=" + attendanceListId);}
                    else{
                    window.open("/edu/printhtml/print_ydy.html?attendanceListIds=" + attendanceListId);
					}
				}else {
            		CreatePrintPageYdyAttendance(resp.data);
            	}
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }
    //批量打印考勤信息

    $scope.printMoreYdyAttendance = function(attendanceList) {
        $scope.printPage = 'beginPrint';
        var attendanceListIds=[];
        for (var i=0;i<attendanceList.length;i++){
            attendanceListIds.push(attendanceList[i].id);
        }
        erp_attendanceService.batchPrint({
            attendanceListIds:attendanceListIds.join(',')
        }, function (resp) {
            if (!resp.error) {
                CreatePrintPageMoreYdyAttendance(resp.data);
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }




            $scope.init = function () {
                // 获取考勤类型
                $scope.getAttendTypes()
				// 获取考勤列表
                if (!$rootScope.curAccount || !$rootScope.curAccount.employeeId) {
                    edu_accountService.query({}, function (resp) {
                        if (!resp.error) {
							$rootScope.curAccount = resp.data;
							$scope.queryStudentSchedulings();
                        } else {
                            $uibMsgbox.error('获取员工帐号信息失败：' + resp.message);
                        }
                    })
				}else{
					$scope.queryStudentSchedulings();
				}
            }
            $scope.init()

        }
