/**
 * Created by Liyong.zhu on 2016/9/14.
 */
'use strict';

/**
 * 学生查询服务
 */
angular.module('ework-ui').factory('erp_studentsService',
		[ '$resource',erp_studentsService ]);


/**
 * 老师主页服务
 */
angular.module('ework-ui').factory('erp_TeacherIndexService',
		[ '$resource', erp_TeacherIndexService ]);

/**
 * 老师查询服务
 */
angular.module('ework-ui').factory('erp_TeacherSearchService',
		[ '$resource', erp_TeacherSearchService ]);
/**
 * 老师列表服务
 */
angular.module('ework-ui').factory('erp_TeacherListService',
		[ '$resource', erp_TeacherListService ]);
/**
 * 考勤教师分组
 */
angular.module('ework-ui').factory('erp_attendanceTeacherGroupService',
		[ '$resource', erp_attendanceTeacherGroupService ]);

/**
 * 学生首页指标查询服务
 */	
angular.module('ework-ui').factory('erp_studentIndexAccountService',
		[ '$resource', erp_studentIndexAccountService ]);

/**
 * 学生首页指标查询服务
 */
angular.module('ework-ui').factory('erp_studentIndexCounselorsService',
		[ '$resource', erp_studentIndexCounselorsService ]);

/**
 * 学生首页-学员积分管理
 */
angular.module('ework-ui').factory('erp_studentIndexIntegralService',
    [ '$resource', erp_studentIndexIntegralService ]);

/**
 * 学生跟踪服务
 */
angular.module('ework-ui').factory('erp_studentTraceInfoService',
		[ '$resource', erp_studentTraceInfoService ]);

/**
 * 学生订单查询服务
 */
angular.module('ework-ui').factory('erp_studentOrdersService',
		[ '$resource', erp_studentOrdersService ]);

/**
 * 学生订单查询服务
 */
angular.module('ework-ui').factory('erp_studentOrdersBJKService',
    [ '$resource', erp_studentOrdersBJKService ]);

/**
 * 学管师我的学员查询服务
 */
angular.module('ework-ui').factory('erp_studentMineService',
	    [ '$resource', erp_studentMineService ]);

/**
 * 学员1对1课程查询
 */
angular.module('ework-ui').factory('erp_studentOrdersYdyService',
    [ '$resource', erp_studentOrdersYdyService ]);

/**
 * 学员晚辅导课程查询服务
 */
angular.module('ework-ui').factory('erp_studentOrdersWFDService',
    [ '$resource', erp_studentOrdersWFDService ]);

/**
 * 学员考勤查询服务
 */
angular.module('ework-ui').factory('erp_studentCourseService',
    [ '$resource', erp_studentCourseService ]);
/**
 * 学生课程表服务
 */
angular.module('ework-ui').factory('erp_studentScheduleService',
	[ '$resource', erp_studentScheduleService ]);
/**
 * 学生订单查询服务
 */
angular.module('ework-ui').factory('erp_studentsCheckService',
		[ '$resource', erp_studentsCheckService ]);

/**
 * 学生就读学校服务
 */
angular.module('ework-ui').factory('erp_studentSchoolService',
		[ '$resource', erp_studentSchoolService ]);

/**
 * 学生所属地区服务
 */
angular.module('ework-ui').factory('erp_studentOrgService',
		[ '$resource', erp_studentOrgService ]);
/**
 * 学员的校区服务
 */
angular.module('ework-ui').factory('erp_studentBuOrgsService',
    [ '$resource', erp_studentBuOrgsService ]);

/**
 * 学生联系方式查询服务
 */
angular.module('ework-ui').factory('erp_studentContactService',
		[ '$resource', erp_studentContactService ]);

/**
 * 学生联系关系服务
 */
angular.module('ework-ui').factory('erp_studentContactRelationService',
		[ '$resource', erp_studentContactRelationService ]);

/**
 * 学生短信服务服务
 */
angular.module('ework-ui').factory('erp_MessageService',
		[ '$resource', erp_MessageService ]);

/**
 * 学生联系人服务
 */
angular.module('ework-ui').factory('erp_studentContactService',
		[ '$resource', erp_studentContactService ]);
/**
 * 学生账户服务
 */
angular.module('ework-ui').factory('erp_studentAccountService',
    [ '$resource', erp_studentAccountService ]);

/**
 * 学员分配服务
 */
angular.module('ework-ui').factory('erp_stuCounselorDistService',
    [ '$resource', erp_stuCounselorDistService])
/**
 * 学管师咨询师服务
 */
angular.module('ework-ui').factory('erp_studentCounselorService',
		[ '$resource', erp_studentCounselorService ]);

/**
 * 员工服务
 */
angular.module('ework-ui').factory('erp_employeeService',
		[ '$resource', erp_employeeService ]);

angular.module('ework-ui').factory('erp_employeeMgrService',
    [ '$resource', erp_employeeMgrService ]);

/**
 * 课程季服务
 */
angular.module('ework-ui').factory('erp_timeSeasonService',
    [ '$resource', erp_timeSeasonService ]);
/**
 * 年级服务
 */
angular.module('ework-ui').factory('erp_gradeService',
    [ '$resource', erp_gradeService ]);

/**
 * 全日制学校服务
 */
angular.module('ework-ui').factory('erp_schoolService',
    [ '$resource', erp_schoolService ]);

/**
 * 科目服务
 */
angular.module('ework-ui').factory('erp_subjectService',
    [ '$resource', erp_subjectService ]);

/**
 * 排课专员服务
 */
angular.module('ework-ui').factory('erp_arrangerService',
    [ '$resource', erp_arrangerService ]);

/**
 * 课程课时服务
 */
angular.module('ework-ui').factory('erp_studentCourseTimesService',
    [ '$resource', erp_studentCourseTimesService ]);

/**
 * 教师服务
 */
angular.module('ework-ui').factory('erp_teacherService',
    [ '$resource', erp_teacherService ]);

/**
 * 学生考勤服务
 */
angular.module('ework-ui').factory('erp_attendanceService',
    [ '$resource', erp_attendanceService ]);

/**
 * 课次考勤服务
 */
angular.module('ework-ui').factory('erp_attendanceCourseTimesService',
    [ '$resource', erp_attendanceCourseTimesService ]);

/**
 * 课程视频
 */
angular.module('ework-ui').factory('erp_attendanceVideoService',
    [ '$resource', erp_attendanceVideoService ]);

/**
 * 考勤历史服务
 */
angular.module('ework-ui').factory('erp_attendanceDetailsService',
    [ '$resource', erp_attendanceDetailsService ]);
/**
 * 考勤补课情况查询服务
 */
angular.module('ework-ui').factory('erp_attendanceMakeupService',
    [ '$resource', erp_attendanceMakeupService ]);

/**
 * 课程试听服务
 */
angular.module('ework-ui').factory('erp_courseListeningService',
    [ '$resource', erp_courseListeningService ]);
/**
 * 课程选择服务
 */
angular.module('ework-ui').factory('erp_courseService',
    [ '$resource', erp_courseService ]);
/**
 * 晚辅导套餐 服务
 */
angular.module('ework-ui').factory('erp_wfdComboService',
	[ '$resource', erp_wfdComboService ]);
/**
 * 双师课程选择服务
 */
angular.module('ework-ui').factory('erp_mtcourseService',
    [ '$resource', erp_mtcourseService ]);

/**
 * 班级课导入服务
 */
angular.module('ework-ui').factory('erp_courseInputService',
    [ '$resource', erp_courseInputService ])

angular.module('ework-ui').factory('erp_courseLadderService',
		[ '$resource', erp_courseLadderService])
/**
 * 课程课次服务
 */
angular.module('ework-ui').factory('erp_courseTimesService',
    [ '$resource', erp_courseTimesService ]);

/**
 * 学员打印相关服务
 */
angular.module('ework-ui').factory('erp_studentPrintService',
    [ '$resource', erp_studentPrintService ]);
/**
 * 公司账户服务
 */
angular.module('ework-ui').factory('erp_companyAccountService',
    [ '$resource', erp_companyAccountService ]);
/**
 * 公司设备服务
 */
angular.module('ework-ui').factory('erp_deviceService',
	[ '$resource', erp_deviceService ]);
/**
 * 学生账户查询
 */
angular.module('ework-ui').factory('erp_studentAccountQueryService',
    [ '$resource', erp_studentAccountQueryService ]);
/**
 * 优惠前置分页查询
 */
angular.module('ework-ui').factory('erp_privilegeCriteriaServicePage',
    [ '$resource', erp_privilegeCriteriaServicePage ]);
/**
 * 优惠前置查询
 */
angular.module('ework-ui').factory('erp_privilegeCriteriaService',
    [ '$resource', erp_privilegeCriteriaService ]);
/**
 * 优惠规则查询
 */
angular.module('ework-ui').factory('erp_privilegeRuleService',
    [ '$resource', erp_privilegeRuleService ]);
/**
 * 优惠规则分页查询
 */
angular.module('ework-ui').factory('erp_privilegeRuleServicePage',
    [ '$resource', erp_privilegeRuleServicePage ]);
/**
 * 优惠券分页查询
 */
angular.module('ework-ui').factory('erp_couponInfoServicePage',
    [ '$resource', erp_couponInfoServicePage ]);
/**
 * 优惠券服务
 */
angular.module('ework-ui').factory('erp_couponInfoService',
    [ '$resource', erp_couponInfoService ]);
/**
 * 优惠券发放服务
 */
angular.module('ework-ui').factory('erp_couponRuleRelService',
    [ '$resource', erp_couponRuleRelService ]);
/**
 * 优惠券服务
 */
angular.module('ework-ui').factory('erp_couponInfoServices',
    [ '$resource', erp_couponInfoServices ]);
/**
 * 优惠活动
 */
angular.module('ework-ui').factory('erp_activityInfoService',
    [ '$resource', erp_activityInfoService ]);
angular.module('ework-ui').factory('erp_activityService',
    [ '$resource', erp_activityService ]);
/**
 * 活动图片
 */
angular.module('ework-ui').factory('erp_activityBannerService',
    [ '$resource', erp_activityBannerService]);
/**
 * 生成优惠券
 */
angular.module('ework-ui').factory('erp_activityGenerateCouponDepotService',
    [ '$resource', erp_activityGenerateCouponDepotService ]);
/**
 * 订单管理服务
 */
angular.module('ework-ui').factory('erp_orderManagerService',
    [ '$resource', erp_orderManagerService ]);


/**
 * 财务/订单单据
 */
angular.module('ework-ui').factory('erp_FinanceOrderService',
	[ '$resource', erp_FinanceOrderService ]);

/**
 * 财务/冻结单据
 */
angular.module('ework-ui').factory('erp_frozenService', 
  [ '$resource', erp_frozenService])

/**
 * 财务/在线支付
 */
angular.module('ework-ui').factory('erp_epayWapService',
	[ '$resource', erp_epayWapService ]);

/**
 * 财务/退费单据
 */
angular.module('ework-ui').factory('erp_refundService',
	[ '$resource', erp_refundService ]);

/**
 * 财务/订单锁定
 */
angular.module('ework-ui').factory('erp_orderFrozenMgrService',
	[ '$resource', erp_orderFrozenMgrService ]);

/**
 * 用户订单
 */
angular.module('ework-ui').factory('erp_orderManagerUserOrdersService',
    [ '$resource', erp_orderManagerUserOrdersService ]);

/**
 * 发票管理
 */
angular.module('ework-ui').factory('erp_InvoiceManagerService',
    [ '$resource', erp_InvoiceManagerService ]);

/**
 * 学员成绩管理
 */
angular.module('ework-ui').factory('erp_studentScoreService',
    [ '$resource', erp_studentScoreService]);

/**
 * 学员订单课程查询
 */
angular.module('ework-ui').factory('erp_studentOrderCourseService',
    [ '$resource', erp_studentOrderCourseService ]);
/**
 * 学员课程1对1排课查询
 */
angular.module('ework-ui').factory('erp_studentCourseSchedulingService',
    [ '$resource', erp_studentCourseSchedulingService ]);
/**
 * 学员1对1排课申请
 */
angular.module('ework-ui').factory('erp_stuCourseSchedApplyYdyService', 
    ['$resource', erp_stuCourseSchedApplyYdyService]);
/**
 * 学员1对1讲义打印
 */
angular.module('ework-ui').factory('erp_stuCourseSchedYdyPrintService', 
    ['$resource', erp_stuCourseSchedYdyPrintService]);
/**
 * 学员订单批改查询
 */
angular.module('ework-ui').factory('erp_orderChangeService',
    [ '$resource', erp_orderChangeService ]);

/**
 * 开票公司服务
 */
angular.module('ework-ui').factory('erp_invoiceCompanyService',
    [ '$resource', erp_invoiceCompanyService ]);

/**
 * 教研组服务
 */
angular.module('ework-ui').factory('erp_teacherGroupService',
    [ '$resource', erp_teacherGroupService ]);

/**
 * 教师临时表服务
 */
angular.module('ework-ui').factory('erp_tmpTeacherInfoService',
    [ '$resource', erp_tmpTeacherInfoService ]);

/**
 * 组织机构服务
 */
angular.module('ework-ui').factory('erp_organizationService',
    [ '$resource', erp_organizationService ]);

/**
 * 教室信息服务
 */
angular.module('ework-ui').factory('erp_roomService',
	[ '$resource', erp_roomService ]);

/**
 * 延课信息服务
 */
angular.module('ework-ui').factory('erp_delayCourseService',
    [ '$resource', erp_delayCourseService ]);

/**
 * 系统通知
 */
angular.module('ework-ui').factory('erp_noticeService',
	[ '$resource', erp_noticeService ]);

/**
 * 双师数据同步核对报表
 */
angular.module('ework-ui').factory('erp_eaiLogService',
    [ '$resource', erp_eaiLogService ]);
    
/**
 * 接口日志管理
 */
angular.module('ework-ui').factory('erp_doubleTeacherSyncService',
	[ '$resource', erp_doubleTeacherSyncService ]);

/**
 * 排号业务
 */
angular.module('ework-ui').factory('erp_sortNumService',
	[ '$resource', erp_sortNumService ]);

/**
 * 线上优惠券管理
 */
angular.module('ework-ui').factory('erp_ebCouponService',
	[ '$resource', erp_ebCouponService ]);

/**
 * 数据字典服务
 */
angular.module('ework-ui').factory('erp_dictService',
	[ '$resource', erp_dictService ]);

/**
 * 排课档期查询服务
 */
angular.module('ework-ui').factory('erp_tpScheduleTimeService',
    [ '$resource', erp_tpScheduleTimeService ]);

/**
 * 学管师耗课分析查询服务
 */
angular.module('ework-ui').factory('erp_StudentManagerService',
    [ '$resource', erp_StudentManagerService ]);

/**
 * 发票跟踪服务
 */
angular.module('ework-ui').factory('erp_invoiceTraceService',
    [ '$resource', erp_invoiceTraceService ]);

/**
 * 摄像机服务
 */
angular.module('ework-ui').factory('erp_cameraService',
    [ '$resource', erp_cameraService ]);

/**
 * 课程视频服务
 */
angular.module('ework-ui').factory('erp_videoService',
    [ '$resource', erp_videoService ]);

/**
 * 角色管理服务
 */
angular.module('ework-ui').factory('erp_RoleService',
    [ '$resource', erp_RoleService ]);

/**
 * 账户服务
 */
angular.module('ework-ui').factory('erp_AccountService',
    [ '$resource', erp_AccountService ]);


function erp_orderChangeService($resource){
    return $resource('/erp/ordermanager/service', {}, {
        changeTransfer : {
            url:'/erp/ordermanager/transfer',
            method : 'POST',
            params : {},
            isArray : false
        },
        changeRefund : {
            url:'/erp/ordermanager/refund',
            method : 'POST',
            params : {},
            isArray : false
        },
        changeFrozen : {
            url:'/erp/ordermanager/frozen',
            method : 'POST',
            params : {},
            isArray : false
        },
        refundLadder : {
            url:'/erp/ordermanager/refundLadder',
            method : 'GET',
            params : {},
            isArray : false
        },
        changeCheck : {
            url:'/erp/ordermanager/changeCheck',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_studentScoreService($resource) {
    return $resource('/erp/studentservice/stuScoreInfo', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        },
        queryDetail: {
            url: '/erp/studentservice/stuScoreInfo/selectOne',
            method: 'GET',
            param: {},
            isArray: false
        },
        post: {
            method: 'POST',
            params: {},
            isArray: false
        },
        put: {
            method: 'PUT',
            params: {},
            isArray: false
        },
        delete: {
            method: 'DELETE',
            params: {},
            isArray: false
        },
        putStatus: {
            url: '/erp/studentservice/stuScoreInfo/changeStatus',
            method: 'PUT',
            params: {},
            isArray: false
        }
    })
}

function erp_studentOrderCourseService($resource){
    return $resource('/erp/studentservice/orderCourse', {}, {
        queryOrderCourse : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
function erp_studentCourseSchedulingService($resource){
    return $resource('/erp/studentservice/scheduling', {}, {
        query: {
            method : 'GET',
            params : {},
            isArray : false
        },
        addYdyOrderCourseSchedulingList: {
            url: '/erp/studentservice/schedulingList',
            method: 'POST',
            params: {},
            isArray: false
        },
        addYdyOrderCourseScheduling: {
            method : 'POST',
            params : {},
            isArray : false
        },
        cancelYdyOrderCourseScheduling: {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        updateYdyOrderCourseScheduling: {
            method : 'PUT',
            params : {},
            isArray : false
        },
        queryConflictScheduling : {
            url:'/erp/studentservice/confict/scheduling',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}
function erp_stuCourseSchedApplyYdyService ($resource) {
    return $resource('/erp/studentservice/schedulingApply', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        },
        getDetail: {
            url: '/erp/studentservice/schedulingApply/toEdit',
            method: 'GET',
            params: {},
            isArray: false
        },
        post: {
            method: 'POST',
            params: {},
            isArray: false
        },
        put: {
            method: 'PUT',
            params: {},
            isArray: false
        },
        delete: {
            method: 'DELETE',
            params: {},
            isArray: false
        },
        putStatus: {
            url: '/erp/studentservice/schedulingApply/changeStatus',
            method: 'PUT',
            params: {},
            isArray: false
        },
        postScore: {
            url: '/erp/studentservice/schedulingApply/stuScore',
            method: 'POST',
            params: {},
            isArray: false
        },
        putScore: {
            url: '/erp/studentservice/schedulingApply/stuScore',
            method: 'PUT',
            params: {},
            isArray: false
        },
        delScore: {
            url: '/erp/studentservice/schedulingApply/stuScore',
            method: 'DELETE',
            params: {},
            isArray: false
        },
        postReq: {
            url: '/erp/studentservice/schedulingApply/stuReq',
            method: 'POST',
            params: {},
            isArray: false
        },
        putReq: {
            url: '/erp/studentservice/schedulingApply/stuReq',
            method: 'PUT',
            params: {},
            isArray: false
        },
        delReq: {
            url: '/erp/studentservice/schedulingApply/stuReq',
            method: 'DELETE',
            params: {},
            isArray: false
        },
        getApplyPlanList: {
            url: '/erp/studentservice/schedulingApply/stuSchedPlan',
            method: 'GET',
            params: {},
            isArray: false
        },
        putApplyPlan: {
            url: '/erp/studentservice/schedulingApply/stuSchedPlan',
            method: 'PUT',
            params: {},
            isArray: false
        },
        putApplyPlanStatus: {
          url: '/erp/studentservice/schedulingApply/stuSchedPlan/changeStatus',
          method: 'PUT',
          params: {},
          isArray: false
        },
        exportScheduleProcessExcel: {
            url: '/erp/studentservice/schedulingDeal/output',
            method: 'GET',
            params: {},
            isArray: false
        },
        putAudit: {
            url: '/erp/studentservice/schedulingApply/audit',
            method: 'PUT',
            params: {},
            isArray: false
        }
    })
} 
function erp_stuCourseSchedYdyPrintService ($resource){
    return $resource('/erp/lecture/print', {}, {
        post: {
            url: '/erp/lecture/print/list',
            method: 'POST',
            params: {},
            isArray: false
        },
        printpost: {
            url: '/erp/lecture/print/preview',
            method: 'POST',
            params: {},
            isArray: false
        },
    })
}
function erp_InvoiceManagerService($resource){
    return $resource('/erp/invoice/service', {}, {
    	query : {
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        post : {
        	method : 'POST',
        	params : {},
        	isArray : false
        },
        put : {
        	method : 'PUT',
        	params : {},
        	isArray : false
        },
        queryByOrder : {
            url: '/erp/ordermanager/invoice/order',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryForApply : {
        	url : '/erp/invoice/apply',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        exportInv : {
        	url : '/erp/invoice/export',
        	method : 'GET',
        	params : {},
        	isArray : false
        }
    });
}

function erp_studentSchoolService($resource) {
	return $resource('/erp/common/dataschool/list', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function erp_studentsService($resource) {
	return $resource('/erp/studentservice/students', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		post : {
			method : 'POST',
			params : {},
			isArray : false
		},
		update : {
			method : 'PUT',
			params : {},
			isArray : false
		},
		addStudent : {
			url : '/erp/studentservice/student/add',
			method : 'POST',
			params : {},
			isArray : false
		},
		synToDouble : {
			url : '/erp/studentservice/synToDouble',
			method : 'POST',
			params : {},
			isArray : false
		},
		resetPasswordDouble : {
			url : '/erp/studentservice/resetPasswordDouble',
			method : 'POST',
			params : {},
			isArray : false
		},
		uploadHeadImg:{
			url : '/erp/studentservice/student/uploadhead',
			method : 'POST',
			params : {},
			isArray : false
		},
        modifyHeadImg:{
            url : '/erp/studentservice/student/modifyPhoto',
            method : 'POST',
            params : {},
            isArray : false
        },
        queryReferenceStudentHt:{
            url : '/erp/studentservice/student/referenceht',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryRecorder:{
            url : '/erp/studentRecorder/listRecorder',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStudentRecordHt:{
            url : '/erp/studentRecorder/service',
            method : 'GET',
            params : {},
            isArray : false
        },
        addRecorder:{
            url : '/erp/studentRecorder/service',
            method : 'POST',
            params : {},
            isArray : false
        },
        queryStudentByNameAndPhone:{
            url : '/erp/studentservice/student/byNameAndPhones',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryByResourceRecId:{
            url : '/gxhcrm/query/queryRescRec',
            method : 'GET',
            params : {},
            isArray : false
        },
        querygradeBycrmGradeId:{
            url : '/gxhcrm/query/querygradeBycrmGradeId',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStudentNameRecorder:{
            url : '/erp/studentRecorder/nameRecorder',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStudentGradeRecorder:{
            url : '/erp/studentRecorder/gradeRecorder',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStudentStatusRecorder:{
            url : '/erp/studentRecorder/statusRecorder',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStudentTrans:{
            url : '/erp/studentservice/student/transAccount',
            method : 'GET',
            params : {},
            isArray : false
        }
	});
}

function erp_TeacherSearchService($resource) {
	return $resource('/erp/teacherservice/page', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		post : {
			method : 'POST',
			params : {},
			isArray : false
		}
	});
}

function erp_attendanceTeacherGroupService($resource) {
	return $resource('/erp/attendancegroup/service', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		post : {
			method : 'POST',
			params : {},
			isArray : false
		},
		put : {
			method : 'PUT',
			params : {},
			isArray : false
		},
		remove : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		queryDetail : {
			url:'/erp/attendancegroup/detail',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function erp_TeacherIndexService($resource) {
	return $resource('/erp/teacherservice/service', {}, {
		query : {
			method : 'GET',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
		post : {
			method : 'POST',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
		put : {
			method : 'PUT',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
		del : {
			method : 'DELETE',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
		toManage : {
			url : '/erp/teacherservice/toManage',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}


function erp_studentIndexAccountService($resource) {
	return $resource('/erp/studentservice/student/indexAccount', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}
function erp_studentIndexCounselorsService($resource) {
	return $resource('/erp/studentservice/student/indexCounselors', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function erp_studentIndexIntegralService($resource) {
    return $resource('/erp/student/integral', {}, {
        queryStudentIntegral : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStudentIntegralFlow : {
            url : '/erp/student/integral_flow',
            method : 'GET',
            params : {},
            isArray : false
        },
        outputStudentIntegralFlow : {
            url : '/erp/student/integral_output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_studentTraceInfoService($resource) {
	return $resource('/erp/studentTraceInfo/service', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
        queryById : {
            url : '/erp/studentTraceInfo/service/selectOne',
            method : 'GET',
            params : {},
            isArray : false
        },
        add: {
            method: 'POST',
            params: {},
            isArray: false
        },
        update: {
            method: 'PUT',
            params: {},
            isArray: false
        },
        exportCourseInfo: {
            url : '/erp/studentTraceInfo/exportCourseInfo',
            method : 'GET',
            params : {},
            isArray : false
        },
        deleteAttachById: {
            url: '/erp/studentTraceInfo/deleteAttach',
            method: 'DELETE',
            params: {},
            isArray: false
        }
	});
}

function erp_studentOrdersService($resource) {
	return $resource('/erp/studentservice/student/orders', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function erp_studentScheduleService($resource) {
	return $resource('/erp/studentservice', {}, {
		queryYDY : {
			method : 'GET',
			params : {},
			url : '/erp/studentservice/scheduleYDY',
			isArray : false
		},
		queryBJK : {
			method : 'GET',
			params : {},
			url : '/erp/studentservice/scheduleBJK',
			isArray : false
		}
	});
}

function erp_studentsCheckService($resource) {
	return $resource('/erp/studentservice/students/check', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		check: {
			method: 'POST',
			params: {},
			isArray: false
		}
	});
}

function erp_studentOrgService($resource){
	return $resource('/erp/dictionary/organization/list', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function erp_studentBuOrgsService($resource){
    return $resource('/erp/dictionary/organization/branchs', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryAll : {
            method : 'GET',
            url:'/erp/dictionary/organization/allbranchs',
            params : {},
            isArray : false
        }
    });
}

function erp_studentContactService($resource){
	return $resource('/erp/studentservice/student/contact/list', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}
function erp_studentContactRelationService($resource){
	return $resource('/erp/studentservice/student/contact/relationList', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}
function erp_studentContactService($resource){
	return $resource('/erp/studentservice/student/contact/service', {}, {
		query : {
			method : 'GET',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
		post : {
			method : 'POST',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
		put : {
			method : 'PUT',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
		del : {
			method : 'DELETE',
			headers: {
				Accept: 'application/json'
			},
			params : {},
			isArray : false
		},
        updateDefaultContact : {
            url : '/erp/studentservice/studentsDefaultContact',
            method : 'put',
            params : {},
            isArray : false
        }
	});
}
function erp_MessageService($resource){
	return $resource('/erp/message/sendMessage/:mobile', {mobile:'@mobile'}, {
		sendMessage: {method: 'GET', headers: {Accept: 'application/json'}}
	});
}

function erp_studentCounselorService($resource){
	return $resource('/erp/studentservice/student/counselor/service', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        }
	});
}
function erp_employeeService($resource){
	return $resource('/erp/employee/service', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		queryList : {
			url : '/erp/employee/service/list',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 员工档案管理
 * @param $resource
 * @returns {*}
 * @constructor
 */
function erp_employeeMgrService($resource){
    return $resource('/erp/employee/employeeservice', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryEmployeeForPage :
            {
                url:'/erp/employee/employeeservice/page',
                method : 'GET',
                params : {},
                isArray : false
            },
        queryEmployeeInfo:{
            url:'/erp/employee/employeeservice/employeeInfo',
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
        queryEmployeeEdu:{
            url:'/erp/employee/employeeservice/employeeInfo/edu',
            method : 'GET',
            params : {},
            isArray : false
        },
        addEmployeeEdu : {
            url:'/erp/employee/employeeservice/employeeInfo/edu',
            method : 'POST',
            params : {},
            isArray : false
        },
        updateEmployeeEdu : {
            url:'/erp/employee/employeeservice/employeeInfo/edu',
            method : 'PUT',
            params : {},
            isArray : false
        },
        deleteEmployeeEdu : {
            url:'/erp/employee/employeeservice/employeeInfo/edu',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        queryEmployeeExp:{
            url:'/erp/employee/employeeservice/employeeInfo/exp',
            method : 'GET',
            params : {},
            isArray : false
        },
        addEmployeeExp : {
            url:'/erp/employee/employeeservice/employeeInfo/exp',
            method : 'POST',
            params : {},
            isArray : false
        },
        updateEmployeeExp : {
            url:'/erp/employee/employeeservice/employeeInfo/exp',
            method : 'PUT',
            params : {},
            isArray : false
        },
        deleteEmployeeExp : {
            url:'/erp/employee/employeeservice/employeeInfo/exp',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        queryEmployeeSum:{
            url:'/erp/employee/employeeservice/employeeInfo/sum',
            method : 'GET',
            params : {},
            isArray : false
        },
        addEmployeeSum : {
            url:'/erp/employee/employeeservice/employeeInfo/sum',
            method : 'POST',
            params : {},
            isArray : false
        },
        updateEmployeeSum : {
            url:'/erp/employee/employeeservice/employeeInfo/sum',
            method : 'PUT',
            params : {},
            isArray : false
        },
        deleteEmployeeSum : {
            url:'/erp/employee/employeeservice/employeeInfo/sum',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        queryEmployeeRew:{
            url:'/erp/employee/employeeservice/employeeInfo/rew',
            method : 'GET',
            params : {},
            isArray : false
        },
        addEmployeeRew : {
            url:'/erp/employee/employeeservice/employeeInfo/rew',
            method : 'POST',
            params : {},
            isArray : false
        },
        updateEmployeeRew : {
            url:'/erp/employee/employeeservice/employeeInfo/rew',
            method : 'PUT',
            params : {},
            isArray : false
        },
        deleteEmployeeRew : {
            url:'/erp/employee/employeeservice/employeeInfo/rew',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        updateEmployeeStatic : {
            url:'/erp/employee/employeeservice/employeeInfo/static',
            method : 'PUT',
            params : {},
            isArray : false
        },
        queryPostByEmpId : {
            url:'/erp/employee/employeeservice/employeeInfo/post',
            method : 'GET',
            params : {},
            isArray : false
        },
        addPost : {
            url:'/erp/employee/employeeservice/employeeInfo/post',
            method : 'POST',
            params : {},
            isArray : false
        },
        removePost : {
            url:'/erp/employee/employeeservice/employeeInfo/post',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        setStatus : {
            url:'/erp/employee/employeeservice/employeeInfo/status',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        modifyHeadImg:{
            url:'/erp/employee/employeeservice/employeeInfo/img',
            method : 'PUT',
            params : {},
            isArray : false
        },
        queryById:{
            url:'/erp/employee/employeeservice/queryById',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_timeSeasonService($resource){
	return $resource('/erp/dictionary/timeSeason/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        querySelectDatas : {
        	url : '/erp/dictionary/timeSeason/toManage',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
		list : {
			url : '/erp/dictionary/timeSeason/list',
			method : 'GET',
			params : {},
			isArray : false
		},
		changeStatus:{
			url: '/erp/dictionary/timeSeason/changeStatus',
			method : 'PUT',
			params : {},
			isArray : false
		}
    });
}

function erp_subjectService($resource){
    return $resource('/erp/dictionary/subject/service', {}, {
        query : {
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
        delete : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        querySelectDatas : {
        	url : '/erp/dictionary/subject/listSubject',
        	method : 'GET',
        	params : {},
        	isArray : false
        }
    });
}

function erp_arrangerService($resource) {
    return $resource('/erp/courseArrangeSp/service', {}, {
        query: {
            method: 'GET',
            params: {
                pageSize: 30,
                currentPage: 1
            },
            isArray: false
        },
        getDetail: {
            url: '/erp/courseArrangeSp/service/selectOne',
            method: 'GET',
            params: {},
            isArray: false
        },
        put: { method: 'PUT', params: {}, isArray: false },
        post: { method: 'POST', params: {}, isArray: false },
        delete: { method: 'DELETE', params: {}, isArray: false }
    });
}

function erp_studentMineService($resource){
    return $resource('/erp/studentservice/student/counselor/page', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        exportExcel : {
            url : '/erp/studentservice/student/counselor/exportExcel',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_studentOrdersBJKService($resource){
    return $resource('/erp/studentservice/student/orders/bjk', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_studentOrdersYdyService($resource){
    return $resource('/erp/studentservice/ydy', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_studentOrdersWFDService($resource){
    return $resource('/erp/studentservice/student/orders/wfd', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
function erp_studentCourseService($resource){
    return $resource('/erp/studentservice/student/course', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryWfd : {
        	url : '/erp/studentservice/student/course/wfd',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
function erp_studentCourseTimesService($resource){
    return $resource('/erp/studentservice/student/courseTimes', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_teacherService($resource){
    return $resource('/erp/teacherservice/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        page:{
            url:'/erp/teacherservice/page',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
function erp_TeacherListService($resource) {
    return $resource('/erp/teacherservice/service', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        },
        uploadImg: {
            url: "/erp/teacherservice/photo",
            method: 'POST',
            params: {},
            isArray: false
        },
        sendInvation: {
            url: "/erp/teacherservice/sendInvation",
            method: "POST",
            params: {},
            isArray: false
        },
        synToDoubleCourse :{
            url: "/erp/teacherservice/synToDoubleCourse",
            method: "POST",
            params: {},
            isArray: false
        },
		changeStatus:{
			url: "/erp/teacherservice/service",
			method: "DELETE",
			params: {},
			isArray: false
		},
		querySubject: {
			url: "/erp/teacherservice/subject",
			method: "GET",
			params: {},
			isArray: false
		},
        checkImportRecord :{
            url: "/erp/teacherservice/checkImportRecord",
            method: "GET",
            params: {},
            isArray: false
        },
      queryList: {
          url: '/erp/teacherservice/list',
          method: 'GET',
          params: {},
          isArray: false
      },
      queryTeacherSched: {
        url: '/erp/teacherservice/teacherSched',
        method: 'GET',
        params: {},
        isArray: false
      },
      queryTeam: {
        url: '/erp/teacherservice/team',
        method: 'GET',
        params: {},
        isArray: false
        }
    });
}
function erp_attendanceService($resource){
    return $resource('/erp/attendanceservice/bjk', {}, {
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        ydyAttend : {
        	url : '/erp/attendanceservice/ydy',
            method : 'POST',
            params : {},
            isArray : false
        },
        ydyAttendPut: {
            url : '/erp/attendance/service',
            method: 'PUT',
            params: {},
            isArray: false
        },
        batchPrint : {
            url :'/erp/attendanceservice/batchPrint',
            method:'GET',
            params: {},
            isArray:false
        }
    });
}

function erp_attendanceCourseTimesService($resource){
    return $resource('/erp/attendance/courseTimes', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        students:{
            url:'/erp/attendance/students',
            method : 'GET',
            params : {},
            isArray : false
        },
        attendanceWfd:{
            url:'/erp/attendance/service/wfd',
            method : 'POST',
            params : {},
            isArray : false
        },
		attendance:{
			url:'/erp/attendance/students',
			method : 'POST',
			params : {},
			isArray : false
		},
        attendanceTeachers:{
            url:'/erp/attendance/teachers',
            method : 'POST',
            params : {},
            isArray : false
        },
        makeup:{
            url:'/erp/attendance/makeup',
            method : 'GET',
            params : {},
            isArray : false
        },
		branchinfo:{
            url:'/erp/attendance/branchinfo',
            method : 'GET',
            params : {},
            isArray : false
        },
		pageForWfd:{
			url:'/erp/attendance/service/wfd',
			method : 'GET',
			params : {},
			isArray : false
		},
        studentsForWfdAttn:{
            url:'/erp/attendance/students/wfd_attn_list',
            method : 'GET',
            params : {},
            isArray : false
        },
        teachersForWfdAttn:{
            url:'/erp/attendance/teachers/wfd_attn_list',
            method : 'GET',
            params : {},
            isArray : false
        },
        teachersGroupForWfdAttn:{
            url:'/erp/attendance/teachers/wfd_attn_group_list',
            method : 'GET',
            params : {},
            isArray : false
        },
        outputExcel :{
            url:'/erp/attendance//courseTimes_attend_bjk/output',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_attendanceVideoService($resource){
    return $resource('/erp/video/queryByVideoNo', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
    })
}
function erp_attendanceDetailsService($resource){
    return $resource('/erp/attendanceservice/bjk/details', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryWfd : {
        	url : '/erp/attendanceservice/wfd',
            method : 'GET',
            params : {},
            isArray : false
        },
        wfdAttn : {
        	url : '/erp/attendanceservice/wfd',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

function erp_attendanceMakeupService($resource){
    return $resource('/erp/attendanceservice/bjk/makeup', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

function erp_courseListeningService($resource){
    return $resource('/erp/courselistening/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
		queryList : {
			url : '/erp/courselistening/all',
			method : 'GET',
			params : {},
			isArray : false
		},
		exportExcel : {
			url : '/erp/courselistening/all_output',
			method : 'GET',
			params : {},
			isArray : false
		}
    });
}

/**
 * 年级服务
 * @param $resource
 * @returns {*}
 */
function erp_gradeService($resource){
    return $resource('/erp/dictionary/grade/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        querySelectDatas : {
        	url:'/erp/dictionary/grade/list',
            method : 'GET',
            params : {},
            isArray : false
        },
        addGrade : {
            method : 'POST',
            params : {},
            isArray : false
        },
        updateGrade : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        delGrade : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        changeStatus : {
        	url:'/erp/dictionary/grade/changeStatus',
            method : 'GET',
            params : {},
            isArray : false
        }
        
    });
}

/**
 * 全日制学校服务
 * @param $resource
 * @returns {*}
 */
function erp_schoolService($resource){
    return $resource('/erp/common/dataschool/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        addSchool : {
            method : 'POST',
            params : {},
            isArray : false
        },
        updateSchool : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        delSchool : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        querySelectDatas : {
        	url:'/erp/common/dataschool/dialog',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryRegionDatas : {
        	url:'/erp/common/region/queryRegionList',
            method : 'GET',
            params : {},
            isArray : false
        },
        changeStatus : {
        	url:'/erp/common/dataschool/changeStatus',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 课程选择服务
 * @param $resource
 * @returns {*}
 */
function erp_courseService($resource){
    return $resource('/erp/coursemanagerment/service', {}, {
        query : {
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
        changeStatus:{
            url:'/erp/coursemanagerment/changeStatus',
            method : 'PUT',
            params : {},
            isArray : false
        },
        querySchedulingAssist:{
            url:'/erp/coursemanagerment/schedulingAssist',
            method : 'GET',
            params : {},
            isArray : false
        },
        updateSchedulingAssist:{
            url:'/erp/coursemanagerment/schedulingAssist',
            method : 'PUT',
            params : {},
            isArray : false
        },
        toAutoCourseScheduling:{
            url:'/erp/coursemanagerment/schedulingAssist',
            method : 'POST',
            params : {},
            isArray : false
        },
        exportExcel:{
            url:'/erp/coursemanagerment/outputExcelForCourse',
            method : 'POST',
            params : {},
            isArray : false
        },
        queryCourseByStudentId:{
            url:'/erp/coursemanagerment/queryCourseByStudentId',
            method : 'GET',
            params : {},
            isArray : false
        },
        querySchedulingTimeAssist:{
            url:'/erp/coursemanagerment/schedulingTimeAssist',
            method : 'GET',
            params : {},
            isArray : false
        },
        updateSchedulingTimeAssist:{
            url:'/erp/coursemanagerment/schedulingTimeAssist',
            method : 'PUT',
            params : {},
            isArray : false
        },
        queryEndTimesByPeriod:{
            url:'/erp/coursemanagerment/queryEndTimesByPeriod',
            method : 'GET',
            params : {},
            isArray : false
        },
        checkAll:{
            url:'/erp/coursemanagerment/checkAll',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryHourLen : {
            url:'/erp/coursemanagerment/queryHourLen',
            method : 'GET',
            params : {},
            isArray : false
    }
    });
}

/**
 * 晚辅导套餐服务
 * @param $resource
 * @returns {*}
 */
function erp_wfdComboService($resource){
	return $resource('/erp/coursemanagerment/package', {}, {
		query : {
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
		remove : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		changeStatus:{
			url:'/erp/coursemanagerment/package/changeStatus',
			method : 'PUT',
			params : {},
			isArray : false
		},
		checkWfdCourse:{
			url:'/erp/coursemanagerment/checkWfdCourse',
			method : 'POST',
			params : {},
			isArray : false
		},
		checkAll: {
			url:'/erp/coursemanagerment/package/inputDataCheck',
			method: 'POST',
			params: {},
			isArray: false
		},
		inputData: {
			url: '/erp/coursemanagerment/package/inputData',
			method: 'POST',
			params: {},
			isArray: false
		},
		outputExcel: {
			url: '/erp/coursemanagerment/package/outputExcel',
			method: 'POST',
			params: {},
			isArray: false
		}
	});
}

/**
 * 双师课程选择服务
 * @param $resource
 * @returns {*}
 */
function erp_mtcourseService($resource){
    return $resource('/erp/coursemanagerment/mtcourse', {}, {
        query : {
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
        changeStatus:{
            url:'/erp/coursemanagerment/mtcourse/changeStatus',
            method : 'PUT',
            params : {},
            isArray : false
        },
        querySchedulingAssist:{
            url:'/erp/coursemanagerment/mtcourse/schedulingAssist',
            method : 'GET',
            params : {},
            isArray : false
        },
        synToDoubleCourse:{
            url:'/erp/coursemanagerment/mtcourse/synToDoubleCourse',
            method : 'GET',
            params : {},
            isArray : false
        },
        selectedCourseList:{
            url:'/erp/coursemanagerment/mtcourse/selectedCourseList',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
/**
 * 班级课导入服务
 */
function erp_courseInputService ($resource) {
  return $resource('/erp/coursemanagerment/inputDataCheck', {}, {
      checkAll: {
        method: 'POST',
        params: {},
        isArray: false
      },
	  checkOne: {
		  url: '/erp/coursemanagerment/inputDataCheckByOne',
		  method: 'POST',
		  params: {},
		  isArray: false
	  },
      inputData: {
        url: '/erp/coursemanagerment/inputData',
        method: 'POST',
        params: {},
        isArray: false
      },
	  outputExcel: {
		  url: '/erp/coursemanagerment/outputExcel',
		  method: 'POST',
		  params: {},
		  isArray: false
	  }
  })
} 

/**
 * 1对1课程阶梯管理
 */
function erp_courseLadderService($resource) {
	return  $resource('/erp/courseladder/service', {}, {
		query : {
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
		remove : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		list:{
			url:'/erp/courseladder/list',
			method : 'GET',
			params : {},
			isArray : false
		},
		adjustLadder:{
			url:'/erp/courseladder/adjustLadder',
			method : 'PUT',
			params : {},
			isArray : false
		},
		changeStatus:{
			url:'/erp/courseladder/changeStatus',
			method : 'PUT',
			params : {},
			isArray : false
		}
	});
}
/**
 * 课程课次服务
 * @param $resource
 * @returns {*}
 */
function erp_courseTimesService($resource){
    return $resource('/erp/coursemanagerment/coursetimes', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        update : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        batchUpdate : {
            url:'/erp/coursemanagerment/coursetimes/batch',
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

/**
 * 学生账户服务
 * @param $resource
 * @returns {*}
 */
function erp_studentAccountService($resource){
    return $resource('/erp/studentaccount/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        queryAccountDynamic : {
    		url:"/erp/studentaccount/all",
            method : 'GET',
            params : {},
            isArray : false
        },
        queryUpdateBaseInfo:{
        	url:"/erp/studentaccount/queryUpdateBaseInfo",
            method : 'GET',
            params : {},
            isArray : false
        },
        del : {
             method : 'DELETE',
             params : {},
             isArray : false
        },
        put : {
             method : 'PUT',
             params : {},
             isArray : false
        }
    });
}

/**
 * 学管师学员分配服务
 */
function erp_stuCounselorDistService ($resource) {
  return $resource('/erp/studentservice/student/counselor/distribute',{}, {
    query: {
      method: 'GET',
      params: {},
      isArray: false
    },
    post: {
      method: 'POST',
      params: {},
      isArray: false
    },
    distributeBatch: {
      url: '/erp/studentservice/student/counselor/distribute/batch',
      method: 'POST',
      params: {},
      isArray: false
    },
    queryDistributeTo: {
      url: '/erp/studentservice/student/counselor/distribute/to',
      method: 'GET',
      params: {},
      isArray: false
    },
    queryDistributeFrom: {
      url: '/erp/studentservice/student/counselor/distribute/from',
      method: 'GET',
      params: {},
      isArray: false
    }
  })
}

/**
 * 学生账户服务
 * @param $resource
 * @returns {*}
 */
function erp_studentAccountQueryService($resource){
    return $resource('/erp/studentaccount/account', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
/**
 * 学员相关的打印查询服务
 * @param $resource
 * @returns {*}
 */
function erp_studentPrintService($resource){
    return $resource('/erp/studentaccount/print', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
/**
 * 公司账户服务
 * @param $resource
 * @returns {*}
 */
function erp_companyAccountService($resource){
    return $resource('/erp/common/companyaccount/service', {}, {
        query : {
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
		delete : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		changeStatus:{
			url:'/erp/common/companyaccount/status',
			method : 'GET',
			params : {},
			isArray : false
		}
    });
}

/**
 * 公司设备服务
 * @param $resource
 * @returns {*}
 */
function erp_deviceService($resource){
	return $resource('/erp/common/device/service', {}, {
		init : {
			url:'/erp/common/device/init',
			method : 'GET',
			params : {},
			isArray : false
		},
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		queryBu : {
            url:'/erp/common/device/bu/service',
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
		delete : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		changeStatus:{
			url:'/erp/common/device/status',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 优惠前置分页服务
 * @param $resource
 * @returns {*}
 */
function erp_privilegeCriteriaServicePage($resource){
    return $resource('/erp/privilegecriteria/servicePage', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 优惠前置服务
 * @param $resource
 * @returns {*}
 */
function erp_privilegeCriteriaService($resource){
    return $resource('/erp/privilegecriteria/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

/**
 * 优惠规则服务
 * @param $resource
 * @returns {*}
 */
function erp_privilegeRuleService($resource){
    return $resource('/erp/privilegerule/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

/**
 * 优惠规则分页服务
 * @param $resource
 * @returns {*}
 */
function erp_privilegeRuleServicePage($resource){
    return $resource('/erp/privilegerule/servicePage', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
/**
 * 优惠券分页服务
 * @param $resource
 * @returns {*}
 */
function erp_couponInfoServicePage($resource){
    return $resource('/erp/couponinfo/servicePage', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
/**
 * 优惠券服务
 * @param $resource
 * @returns {*}
 */
function erp_couponInfoService($resource){
    return $resource('/erp/couponinfo/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}
/**
 * 优惠券发放服务
 * @param $resource
 * @returns {*}
 */
function erp_couponRuleRelService($resource){
    return $resource('/erp/couponrulerel/service', {}, {
        post : {
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 优惠券服务
 * @param $resource
 * @returns {*}
 */
function erp_couponInfoServices($resource){
    return $resource('/erp/couponinfo/services', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 活动Banner图片
 */
function erp_activityBannerService($resource) {
  return $resource('/erp/banner/service', {}, {
    query: {
      method: 'GET',
      params: {},
      isArray: false
    },
    post: {
      method: 'POST',
      params: {},
      isArray: false
    },
    put: {
      method: 'PUT',
      params: {},
      isArray: false
    },
    changeStatus:{
      url:'/erp/banner/changeStatus',
      method : 'PUT',
      params : {},
      isArray : false
    }
  })
  // body...
}
/**
 * 优惠活动
 * @param $resource
 * @returns {*}
 */
function erp_activityInfoService($resource){
    return $resource('/erp/activityInfo/services', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

function erp_activityService($resource) {
  return $resource('/erp/activity/service', {}, {
    query: {
      method: 'GET',
      params: {},
      isArray: false
    },
    post: {
      method: 'POST',
      params: {},
      isArray: false
    },
    put : {
        method : 'PUT',
        params : {},
        isArray : false
    },
    remove : {
        method : 'DELETE',
        params : {},
        isArray : false
    },
    changeStatus:{
        url:'/erp/activity/changeStatus',
        method : 'PUT',
        params : {},
        isArray : false
    }
  })
}

/**
 * 生成优惠券
 * @param $resource
 * @returns {*}
 */
function erp_activityGenerateCouponDepotService($resource){
    return $resource('/erp/activityInfo/generateCouponDepot', {}, {
        post : {
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 订单管理服务
 * @param $resource
 * @returns {*}
 */
function erp_orderManagerService($resource){
    return $resource('/erp/ordermanager/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        pay : {
            url:'/erp/ordermanager/pay',
            method : 'POST',
            params : {},
            isArray : false
        },
        queryOrderChangeCourseTimes : {
            url:'/erp/ordermanager/orderChangeCourseTimes',
            method : 'GET',
            params : {},
            isArray : false
        },
        orderChangeInfo:{
            url:'/erp/ordermanager/orderChangeInfo',
            method : 'GET',
            params : {},
            isArray : false
        },
        orderCourseSurplusCount : {
            url:'/erp/ordermanager/surplusCount',
            method : 'GET',
            params : {},
            isArray : false
        },
        delete : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
		unpay :{
			url:'/erp/ordermanager/unpay',
			method : 'GET',
			params : {},
			isArray : false
		},
		exportUnpayExcel :{
			url:'/erp/ordermanager/exportUnpayExcel',
			method : 'GET',
			params : {},
			isArray : false
		},
        updateOrderInfo :{
            url:'/erp/ordermanager/updateOrderInfo',
            method : 'POST',
            params : {},
            isArray : false
        },
        getPayOnlineQrCode: {
            url: '/erp/qrCode/getQrCode',
            method: 'POST',
            params: {},
            isArray: false
        },
        getPayOnlineQrCodeInfo: {
            url: '/erp/qrCode/getQrCodeResult',
            method: 'POST',
            params: {},
            isArray: false
        },
        getPayOnlineResult: {
            url: '/erp/qrCode/queryBillNoInfo',
            method: 'POST',
            params: {
                billNo: null,
                billDate: null
            },
            isArray: false
        },
        payOnlineRefund: {
            url: '/erp/qrCode/refund',
            method: 'POST',
            params: {},
            isArray: false
        }
    });
}

/**
 * 账务/退费单据服务
 * @param $resource
 * @returns {*}
 */
function erp_refundService($resource){
	return $resource('/erp/ordermanager/refund', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		update : {
			method : 'PUT',
			params : {},
			isArray : false
		},
		delete : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		queryDetail : {
			url:'/erp/ordermanager/refundDetail',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 账务/冻结单据服务
 * @param $resource
 * @returns {*}
 */
function erp_frozenService($resource){
	return $resource('/erp/ordermanager/frozen', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		delete : {
			method : 'DELETE',
			params : {},
			isArray : false
		},
		queryDetail : {
			url:'/erp/ordermanager/frozenDetail',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 账务/订单锁定服务
 * @param $resource
 * @returns {*}
 */
function erp_orderFrozenMgrService($resource){
	return $resource('/erp/orderlocked/service', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		carryForward : {
			method : 'PUT',
			params : {},
			isArray : false
		},
		exportExcel : {
			url : '/erp/orderlocked/export',
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 财务/在线支付
 * @param $resource
 * @returns {*}
 */
function erp_epayWapService($resource){
	return $resource('/erp/epay_wap/service', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		list : {
			url : '/erp/epay_wap/list',
			method : 'GET',
			params : {},
			isArray : false
		},
		queryTeam : {
			url : '/erp/epay_wap/queryTeam',
			method : 'GET',
			params : {},
			isArray : false
		},
		output : {
			url: '/erp/epay_wap/output',
			method: 'POST',
			params: {},
			isArray: false
		}
	});

}

/**
 * 财务/订单单据
 * @param $resource
 * @returns {*}
 */
function erp_FinanceOrderService($resource){
	return $resource('/erp/order/service', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		},
		post : {
			method : 'POST',
			params : {},
			isArray : false
		},
		put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        queryOrderCouseDetail : {
        	url:'/erp/order/detail',
            method : 'GET',
            params : {},
            isArray : false
        },
        lock : {
        	url:'/erp/order/lock',
            method : 'PUT',
            params : {},
            isArray : false
        }
	});

}
function erp_orderManagerUserOrdersService($resource){
    return $resource('/erp/ordermanager/user/myOrders', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_invoiceCompanyService($resource){
	return $resource('/erp/common/invoicecompany/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        list : {
        	url : '/erp/common/invoicecompany/list',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        changeStatus : {
        	url : '/erp/common/invoicecompany/changeStatus',
        	method : 'DELETE',
        	params : {},
        	isArray : false
        }
    });
}

function erp_tmpTeacherInfoService($resource) {
	return $resource('/erp/tmpTeacher/service', {}, {
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        batchImport : {
        	url : '/erp/tmpTeacher/batchImport',
        	method : 'PUT',
        	params : {},
        	isArray : false
        },
        exportExcel : {
        	url : '/erp/tmpTeacher/exportExcel',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        toManage : {
        	url : '/erp/tmpTeacher/toManage',
        	method : 'GET',
        	params : {},
        	isArray : false
        }
	});
}

function erp_teacherGroupService($resource){
	return $resource('/erp/teacherGroup/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryList : {
          url: '/erp/teacherGroup/list',
          method : 'GET',
          params : {},
          isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
            method : 'DELETE',
            params : {},
            isArray : false
        },
        toManage : {
        	url : '/erp/teacherGroup/toManage',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_organizationService($resource){
    return $resource('/erp/dictionary/organization/list', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        teamList : {
        	url : '/erp/dictionary/organization/teamList',
        	method : 'GET',
            params : {},
            isArray : false
        },
        branchList : {
        	url : '/erp/dictionary/organization/branchs',
        	method : 'GET',
            params : {},
            isArray : false
        },
        add : {
        	url : '/erp/dictionary/organization/service',
        	method : 'POST',
            params : {},
            isArray : false
        },
        update : {
        	url : '/erp/dictionary/organization/service',
        	method : 'PUT',
            params : {},
            isArray : false
        },
        queryProductLine : {
        	url : '/erp/dictionary/organization/productLine',
        	method : 'GET',
            params : {},
            isArray : false
        },
        queryDictOrgList : {
        	url : '/erp/dictionary/organization/dictOrgList',
        	method : 'GET',
            params : {},
            isArray : false
        },
        uploadLogo : {
        	url : '/erp/dictionary/organization/uploadLogo',
        	method : 'PUT',
            params : {},
            isArray : false
        },
        deleteLogo : {
        	url : '/erp/dictionary/organization/deleteLogo',
        	method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

function erp_delayCourseService($resource) {
    return $resource('/erp/course/delay/service', {}, {
        listDelayCourse: {
            method: 'GET',
            params: {},
            isArray: false
        },
        listMtDelayCourse: {
            url:'/erp/mtcourse/delay/service',
            method: 'GET',
            params: {},
            isArray: false
        },
        changeSchedulingInfo: {
            url : '/erp/course/delay/changeInfo',
            method: 'GET',
            params: {},
            isArray: false
        },
        listCourseChangeInfo: {
            url : '/erp/course/delay/listChangeInfo',
            method: 'POST',
            params: {},
            isArray: false
        },
        batchDelayCourse:{
            url : '/erp/course/delay/batchDelayCourse',
            method: 'POST',
            params: {},
            isArray: false
        },
        batchDelayMtCourse:{
            url : '/erp/mtcourse/delay/batchDelayCourse',
            method: 'POST',
            params: {},
            isArray: false
        },
        listDelayRecord:{
            url : '/erp/course/delay/record/list',
            method: 'GET',
            params: {},
            isArray: false
        },
        listDelayRecordCourseDetail:{
            url : '/erp/course/delay/record/detail',
            method: 'GET',
            params: {},
            isArray: false
        },
        listDelayCourseSchedulingChangeInfo:{
            url : '/erp/course/delay/record/delayCourseChangeInfo',
            method: 'GET',
            params: {},
            isArray: false
        }

    });
}

function erp_roomService($resource) {
    return $resource('/erp/room/service', {}, {
        listroomArrange : {
                url:'/erp/roomArrange/room/list',
                method:'GET',
                params:{},
                isArray : false
         },
         listroomScheduling : {
                url:'/erp/roomArrange/roomScheduling/list',
                method:'GET',
                params:{},
                isArray : false
         },
        outputExcel : {
            url:'/erp/roomArrange/outputExcel',
            method:'GET',
            params:{},
            isArray : false
        },
        get : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
        	method : 'POST',
            params : {},
            isArray : false
        },
        put : {
        	method : 'PUT',
            params : {},
            isArray : false
        },
        del : {
        	method : 'DELETE',
            params : {},
            isArray : false
        },
		queryRoom : {
			method : 'GET',
			params : {},
			isArray : false,
			url : '/erp/room/room_list'
		},
		queryRoomRel : {
			method : 'GET',
			params : {},
			isArray : false,
			url : '/erp/room/rel'
		},
		saveRoomRel : {
			method : 'POST',
			params : {},
			isArray : false,
			url : '/erp/room/rel'
        },
        untieRoom : {
            method : 'DELETE',
			params : {},
			isArray : false,
			url : '/erp/room/rel'
        },
        saveAllRoom : {
            method : 'POST',
			params : {},
			isArray : false,
			url : '/erp/room/rel/byweekday'
        },
        untieAllRoom : {
            method : 'POST',
			params : {},
			isArray : false,
			url : '/erp/room/rel/delete/byweekday'
        }
    });
}
    
function erp_noticeService($resource) {
    return $resource('/erp/notice/service', {}, {
        queryPage : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryList : {
        	url : '/erp/notice/service/list',
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
        	method : 'POST',
            params : {},
            isArray : false
        },
        addUserNoticeRel : {
        	url : '/erp/notice/service/addUserNoticeRel',
        	method : 'POST',
        	params : {},
        	isArray : false
        },
        put : {
        	method : 'PUT',
            params : {},
            isArray : false
        },
        remove : {
        	method : 'DELETE',
        	params : {},
        	isArray : false
        }
    });
}

function erp_eaiLogService($resource) {
    return $resource('/erp/eaiLog/service', {}, {
    	query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        repeatSendData : {
        	url : '/erp/eaiLog/service/repeatSendData',
        	method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_doubleTeacherSyncService($resource) {
    return $resource('/erp/order/erpMtOrderCourseVerify', {}, {
    	query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        outputExcel: {
            url: '/erp/order/erpMtOrderCourseVerify/outputExcel',
            method: 'GET',
            params: {},
            isArray: false
        }
    });
}

function erp_sortNumService($resource) {
    return $resource('/erp/orderSort/service', {}, {
    	query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        sortNum : {
        	url : '/erp/orderSort/sortNum',
        	method : 'POST',
            params : {},
            isArray : false
        },
        cancelSortNum : {
        	url : '/erp/orderSort/cancelSortNum',
        	method : 'POST',
            params : {},
            isArray : false
        },
        countCourseSortNum : {
        	url : '/erp/orderSort/countCourseSortNum',
        	method : 'GET',
            params : {},
            isArray : false
        },
        countSortNumDetail : {
        	url : '/erp/orderSort/countSortNumDetail',
        	method : 'GET',
            params : {},
            isArray : false
        },
        queryCheckPeople: {
        	url : '/erp/orderSort/queryCheckPeopleList',
        	method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_ebCouponService($resource) {
    return $resource('/erp/ebCoupon/service', {}, {
    	query : {
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
        	url : '/erp/ebCoupon/changeStatus',
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

function erp_dictService($resource) {
    return $resource('/erp/dictionary/service', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        getDictData: {
            url: '/erp/dictionary/service/dictData',
            method: 'GET',
            params: {},
            isArray: false
        }
    });
}

function erp_tpScheduleTimeService($resource) {
    return $resource('/erp/common/tpScheduleTime', {}, {
        queryList : {
            url: '/erp/common/tpScheduleTime/list',
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_StudentManagerService($resource) {
    return $resource('/erp/studentManager/bu', {}, {
        queryStuMgrBu : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStuMgrBranch : {
            url: '/erp/studentManager/branch',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStuMgr : {
            url: '/erp/studentManager/stuMgr',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryStuMgrStudent : {
            url: '/erp/studentManager/student',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryCourseSchedInfo : {
            url: '/erp/studentManager/courseSchedInfo',
            method : 'GET',
            params : {},
            isArray : false
        },
       exportCourseSchedInfo : {
          url: '/erp/studentManager/courseSchedInfo/export',
          method : 'GET',
          params : {},
          isArray : false
       }
    });
}

function erp_invoiceTraceService($resource) {
  return $resource('/erp/invoice/receive', {}, {
    queryList : {
      url: '/erp/invoice/receive/list',
      method : 'GET',
      params : {},
      isArray : false
    },
    receiveInvoice : {
      url: '/erp/invoice/receive/service',
      method : 'POST',
      params : {},
      isArray : false
    }
  });
}
function erp_cameraService($resource) {
    return $resource('/erp/camera/service', {}, {
        listCamera : {
            url:'/erp/camera/list',
            method:'GET',
            params:{},
            isArray : false
        },
        pageCamera : {
            url:'/erp/camera/page',
            method:'GET',
            params:{},
            isArray : false
        },
        get : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

function erp_videoService($resource) {
    return $resource('/erp/video/service', {}, {
        page: {
            method: 'GET',
            params: {},
            isArray: false
        },
        outputExcel: {
            url: '/erp/video/service/outputExcel',
            method: 'GET',
            params: {},
            isArray: false
        }
    });
}

function erp_RoleService($resource){
    return $resource('/erp/hrmSystemSettings/hrmRoleMgr', {}, {
        queryRoleForPage:{
            url:'/erp/hrmSystemSettings/hrmRoleMgr/page',
            method : 'GET',
            params : {},
            isArray : false
        },
        add : {
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove:{
            method : 'DELETE',
            params : {},
            isArray : false
        },

        queryRoleMenu:{
            url:'/erp/hrmSystemSettings/hrmRoleMgr/sub',
            method : 'GET',
            params : {},
            isArray : false
        },

        updateRoleMenu:{
            url:'/erp/hrmSystemSettings/hrmRoleMgr/sub',
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

/**
 * 账户服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function erp_AccountService($resource){
    return $resource('/erp/hrmSystemSettings/hrmAccountMgr', {}, {
        addAccount : {
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove:{
            method : 'DELETE',
            params : {},
            isArray : false
        },
        queryAccountForPage:{
            url:'/erp/hrmSystemSettings/hrmAccountMgr/page',
            method : 'GET',
            params : {},
            isArray : false
        },
        queryRoleWithAccount:{
            url:'/erp/hrmSystemSettings/hrmAccountMgr/queryRoleWithAccount',
            method : 'GET',
            params : {},
            isArray : false
        },
        updateAccountRole : {
            url:'/erp/hrmSystemSettings/hrmAccountMgr/updateAccountRole',
            method : 'PUT',
            params : {},
            isArray : false
        },
        removeRole:{
            url:'/erphrmSystemSettings/hrmAccountMgr/removeAccountRole',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        updateAccountOrg : {
            url:'/erp/hrmSystemSettings/hrmAccountMgr/updateAccountOrg',
            method : 'PUT',
            params : {},
            isArray : false
        },
        removeAccountRoleById : {
            url : '/erp/hrmSystemSettings/hrmAccountMgr/removeAccountRoleById',
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}
