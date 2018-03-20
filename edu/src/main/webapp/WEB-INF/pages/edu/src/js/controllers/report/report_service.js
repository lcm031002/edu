'use strict';

/**
 * 报表任务设置
 */
angular.module('ework-ui').factory('report_taskSettingsService',
		[ '$resource', report_taskSettingsService ]);

/**
 * 报表任务运行状态
 */
angular.module('ework-ui').factory('report_taskResultService',
		[ '$resource', report_taskResultService ]);

/**
 * 业绩明细查询服务
 */
angular.module('ework-ui').factory('report_perfDetailsService',
		[ '$resource', report_perfDetailsService ]);

/**
 * 课程顾问绩效查询服务
 */
angular.module('ework-ui').factory('report_orderPerformaceService',
		[ '$resource', report_orderPerformaceService ]);

/**
 * 账户剩余表服务
 */
angular.module('ework-ui').factory('report_accountService',
		[ '$resource', report_accountService ]);

/**
 * 学员剩余课时费用表服务
 */
angular.module('ework-ui').factory('report_surplusAmountFeeService',
    [ '$resource', report_surplusAmountFeeService ]);

/**
 * 考勤消耗表服务
 */
angular.module('ework-ui').factory('report_attendanceReportService',
    [ '$resource', report_attendanceReportService ]);

/**
 * 自动考勤报表服务
 */
angular.module('ework-ui').factory('report_autoAttendanceReportService',
    [ '$resource', report_autoAttendanceReportService ]);

/**
 * 培英班教师工作量表服务
 */
angular.module('ework-ui').factory('report_teacherWorkloadService',
    [ '$resource', report_teacherWorkloadService ]);

/**
 * 账户流水报表
 */
angular.module('ework-ui').factory('report_accountFlowService',
		[ '$resource', report_accountFlowService ]);
/**
 * 充值取现报表
 */
angular.module('ework-ui').factory('report_rechargeCashService',
		[ '$resource', report_rechargeCashService ]);
/**
 * 出纳信息表
 */
angular.module('ework-ui').factory('report_accountCashierService',
		[ '$resource', report_accountCashierService ]);
/**
 * 报冻退转报表
 */
angular.module('ework-ui').factory('report_orderChangeReportService',
		[ '$resource', report_orderChangeReportService ]);
/**
 * 每日业绩汇总报表
 */
angular.module('ework-ui').factory('report_performanceSumService',
		[ '$resource', report_performanceSumService ]);
/**
 * 培英班续报率报表
 */
angular.module('ework-ui').factory('report_renewalRateService',
		[ '$resource', report_renewalRateService ]);
/**
 * 培英班满班率报表
 */
angular.module('ework-ui').factory('report_fullclassRateService',
    [ '$resource', report_fullclassRateService ]);

/**
 * 晚辅导教师考勤表
 */
angular.module('ework-ui').factory('report_wfdTeacherAttendanceService',
		[ '$resource', report_wfdTeacherAttendanceService ]);

/**
 * 月度消耗表服务
 */
angular.module('ework-ui').factory('report_attendanceMonthService',
    [ '$resource', report_attendanceMonthService ]);

/**
 * 月度分类考勤表
 */
angular.module('ework-ui').factory('report_busAttendMonthService',
    [ '$resource', report_busAttendMonthService ]);

/**
 * 在线支付订单明细表
 */
angular.module('ework-ui').factory('report_onLineOrderService',
    [ '$resource', report_onLineOrderService ]);

/**
 * 培英班运营汇总
 */
angular.module('ework-ui').factory('report_bizStatisticsService',
    [ '$resource', report_bizStatisticsService ]);

/**
 * 培英班教研组统计
 */
angular.module('ework-ui').factory('report_teacherGroupAttendanceService',
    [ '$resource', report_teacherGroupAttendanceService]);

/**
 * 班级分析报表
 */
angular.module('ework-ui').factory('report_courseAnalysisService',
    [ '$resource', report_courseAnalysisService]);

/**
 * 个性化学生状态报表
 */
angular.module('ework-ui').factory('report_gxhStudentStatusService',
    [ '$resource', report_gxhStudentStatusService]);

/**
 * 艾伦学员状态报表
 */
angular.module('ework-ui').factory('report_alStudentStatusReportService',
    [ '$resource', report_alStudentStatusReportService ]);

function report_taskSettingsService($resource) {
	return $resource('/report/settings/service', {}, {
        queryList : {
            method : 'GET',
            params : {},
            isArray : false
        },
        add : {
            method : 'POST',
            params : {},
            isArray : false
        },
        update : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        changeStatus : {
        	url : '/report/settings/changeStatus',
        	method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

function report_taskResultService($resource) {
	return $resource('/report/taskrun/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        execute : {
        	url : '/report/taskrun/execute',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

function report_perfDetailsService($resource) {
	return $resource('/report/common/performanceDetails', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryList : {
        	url : '/report/common/performanceDetails/list',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/performanceDetails/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_orderPerformaceService($resource) {
	return $resource('/report/common/order/performance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryList : {
        	url : '/report/common/order/performance/list',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/order/performance/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}


function report_accountService($resource) {
    return $resource('/report/common/account', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        output : {
            url : '/report/common/account/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_surplusAmountFeeService($resource) {
    return $resource('/report/common/surplusamountfee', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        output : {
            url : '/report/common/surplusamountfee_output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_attendanceReportService($resource) {
    return $resource('/report/common/attendance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        output : {
            url : '/report/common/attendance_output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_autoAttendanceReportService($resource) {
    return $resource('/report/common/auto/attendance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        output : {
            url : '/report/common/auto/attendance_output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_teacherWorkloadService($resource) {
    return $resource('/report/bjk/teacherworkload', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        output : {
            url : '/report/bjk/teacherworkload_output',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryOrderStudents : {
            url : '/report/bjk/teacherworkload/order',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryAttendanceStudents : {
            url : '/report/bjk/teacherworkload/attend',
            method : 'GET',
            params : {},
            isArray : false
        },
        outputOrderStudents : {
            url : '/report/bjk/teacherworkload/order/output',
            method : 'GET',
            params : {},
            isArray : false
        },
        outputAttendanceStudents : {
            url : '/report/bjk/teacherworkload/attend/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_attendanceMonthService($resource) {
    return $resource('/report/common/monthattendance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        output : {
            url : '/report/common/monthattendance_output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_busAttendMonthService($resource) {
    return $resource('/report/common/monthbusinessattendance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        output : {
            url : '/report/common/monthbusinessattendance_output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_accountFlowService($resource) {
	return $resource('/report/common/accountFlow', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryList : {
        	url : '/report/common/accountFlow/list',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/accountFlow/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_rechargeCashService($resource) {
	return $resource('/report/common/accountFlow/recharge', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/accountFlow/recharge/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_accountCashierService($resource) {
	return $resource('/report/common/accountFlow/cashier', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/accountFlow/cashier/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
	
	
}

function report_orderChangeReportService($resource) {
	return $resource('/report/common/orderchange', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/orderchange/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });

}

function report_performanceSumService($resource) {
	return $resource('/report/common/performancesum', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/performancesum/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });	
	
	
}

function report_renewalRateService($resource) {
	return $resource('/report/common/renewalRate/sum', {}, {
		queryForSum : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcelForSum : {
        	url : '/report/common/renewalRate/sum/output',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryForLastBase : {
        	url : '/report/common/renewalRate/lastbase',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcelForLastBase : {
        	url : '/report/common/renewalRate/lastbase/output',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryForEstimate : {
        	url : '/report/common/renewalRate/estimate',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcelForEstimate : {
        	url : '/report/common/renewalRate/estimate/output',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryForActual : {
        	url : '/report/common/renewalRate/actual',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcelForActual : {
        	url : '/report/common/renewalRate/actual/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });	
	
}

function report_wfdTeacherAttendanceService($resource) {
	return $resource('/report/wfd/teacher_attendance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/wfd/teacher_attendance/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_onLineOrderService($resource) {
	return $resource('/report/common/onLineOrder', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryList : {
        	url : '/report/common/onLineOrder/list',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
        	url : '/report/common/onLineOrder/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_fullclassRateService($resource) {
    return $resource('/report/common/fullClass/rate', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
            url : '/report/common/fullClass/rate/output',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryLast:{
            url : '/report/common/fullClass/last',
            method : 'GET',
            params : {},
            isArray : false
        },
        exportLastExcel:{
            url : '/report/common/fullClass/last/output',
            method : 'GET',
            params : {},
            isArray : false
        },
    });
}

function report_bizStatisticsService($resource) {
    return $resource('/report/common/order/statistics', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
            url : '/report/common/order/statistics/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_teacherGroupAttendanceService($resource) {
    return $resource('/report/common/teachergroup/attendance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
            url : '/report/common/teachergroup/attendance/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function report_courseAnalysisService($resource) {
  return $resource('/report/bjk/courseAnalysisReport', {}, {
    query: {
      method: 'GET',
      params: {},
      isArray: false
    },
    exportExcel: {
      url: '/report/bjk/courseAnalysisReport/output',
      method: 'GET',
      params: {},
      isArray: false
    }
  });
}

function report_gxhStudentStatusService($resource) {
    return $resource('/report/common/gxh/student/status', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        },
        output: {
            url: '/report/common/gxh/student/status/output',
            method: 'GET',
            params: {},
            isArray: false
        },
        queryBranch: {
            url: '/report/common/gxh/student/status/branch',
            method: 'GET',
            params: {},
            isArray: false
        },
        outputBranchExcel: {
            url: '/report/common/gxh/student/status/branch/output',
            method: 'GET',
            params: {},
            isArray: false
        },
        queryDetail: {
            url: '/report/common/gxh/student/status/detail',
            method: 'GET',
            params: {},
            isArray: false
        },
        outputDetailExcel: {
            url: '/report/common/gxh/student/status/detail/output',
            method: 'GET',
            params: {},
            isArray: false
        }
    });
}
function report_alStudentStatusReportService($resource) {
    return $resource('/report/common/studentStatusReport/queryStudentStatusReport', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        },
        outputExcel: {
            url: '/report/common/studentStatusReport/queryStudentStatusReport/outputExcel',
            method: 'GET',
            params: {},
            isArray: false
        },
        queryDetails: {
            url: '/report/common/studentStatusReport/queryStudentStatusReport/queryReportStudentdetails',
            method: 'GET',
            params: {},
            isArray: false
        }
    });
}