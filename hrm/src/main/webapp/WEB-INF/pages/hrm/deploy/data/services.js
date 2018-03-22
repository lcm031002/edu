/**
 * Created by Liyong.zhu on 2016/4/6.
 */
'use strict';
angular.module('ework-ui').factory('ProjectService', [ '$resource', ProjectService ]);

angular.module('ework-ui').factory('MenusService', [ '$resource', MenusService ]);

angular.module('ework-ui').factory('CompanyService', [ '$resource', CompanyService ]);

angular.module('ework-ui').factory('OrdersService', [ '$resource', OrdersService ]);

angular.module('ework-ui').factory('OrderCoursesService', [ '$resource', OrderCoursesService ]);

angular.module('ework-ui').factory('ERPCourseStudentListService', [ '$resource', ERPCourseStudentListService ]);

angular.module('ework-ui').factory('PUBCourseStudentListService', [ '$resource', PUBCourseStudentListService ]);

//充值单据查询
angular.module('ework-ui').factory('RechargesService', [ '$resource', RechargesService ]);

angular.module('ework-ui').factory('CoursesInfoService', [ '$resource', CoursesInfoService ]);

angular.module('ework-ui').factory('CourseTimesService', [ '$resource', CourseTimesService ]);

angular.module('ework-ui').factory('BranchsService', [ '$resource', BranchsService ]);

angular.module('ework-ui').factory('TeacherListService', [ '$resource', TeacherListService ]);

angular.module('ework-ui').factory('TeacherScheduleService', [ '$resource', TeacherScheduleService ]);

angular.module('ework-ui').factory('StudentListService', [ '$resource', StudentListService ]);

angular.module('ework-ui').factory('StudentScheduleService', [ '$resource', StudentScheduleService ]);

angular.module('ework-ui').factory('TeacherAttendanceService', [ '$resource', TeacherAttendanceService ]);

angular.module('ework-ui').factory('AuditLogService', [ '$resource', AuditLogService ]);

angular.module('ework-ui').factory('DictsService', [ '$resource', DictsService ]);

angular.module('ework-ui').factory('DictTypeItemService', [ '$resource', DictTypeItemService ]);

angular.module('ework-ui').factory('DictItemService', [ '$resource', DictItemService ]);

angular.module('ework-ui').factory('RoleService', [ '$resource', RoleService ]);

angular.module('ework-ui').factory('AccountService', [ '$resource', AccountService ]);

angular.module('ework-ui').factory('BranchTreeService', [ '$resource', BranchTreeService ]);

angular.module('ework-ui').factory('ReportService', [ '$resource', ReportService]);

angular.module('ework-ui').factory('FileUploadService', [ '$resource', FileUploadService ]);

angular.module('ework-ui').factory('FileUploadDataService', [ '$resource', FileUploadDataService ]);

angular.module('ework-ui').factory('OrdersPubService', [ '$resource', OrdersPubService ]);

angular.module('ework-ui').factory('StudentQueryService', [ '$resource', StudentQueryService ]);

angular.module('ework-ui').factory('SubjectService', [ '$resource', SubjectService ]);

angular.module('ework-ui').factory('SeasonService', [ '$resource', SeasonService ]);

angular.module('ework-ui').factory('GradeService', [ '$resource', GradeService ]);

angular.module('ework-ui').factory('CourseService', [ '$resource', CourseService ]);

angular.module('ework-ui').factory('StudentAccountService', [ '$resource', StudentAccountService ]);

angular.module('ework-ui').factory('ProductService', [ '$resource', ProductService ]);

angular.module('ework-ui').factory('PosService', [ '$resource', PosService ]);

angular.module('ework-ui').factory('CompanyAccountService', [ '$resource', CompanyAccountService ]);

angular.module('ework-ui').factory('RefundService', [ '$resource', RefundService ]);

angular.module('ework-ui').factory('StudentCourseTimeStateService', [ '$resource', StudentCourseTimeStateService ]);

angular.module('ework-ui').factory('TransferClassService', [ '$resource', TransferClassService ]);

angular.module('ework-ui').factory('PUBAttendanceCourseService', [ '$resource', PUBAttendanceCourseService ]);

angular.module('ework-ui').factory('PUBAttendanceCourseTimeService', [ '$resource', PUBAttendanceCourseTimeService ]);

angular.module('ework-ui').factory('PUBAttendanceCoursetimeStudentService', [ '$resource', PUBAttendanceCoursetimeStudentService ]);

angular.module('ework-ui').factory('PUBStudentAccountChangeService', [ '$resource', PUBStudentAccountChangeService ]);

angular.module('ework-ui').factory('PUBStudentAccountTransferService', [ '$resource', PUBStudentAccountTransferService ]);

angular.module('ework-ui').factory('PUBStudentAccountWithDrawService', [ '$resource', PUBStudentAccountWithDrawService ]);

angular.module('ework-ui').factory('PUBStudentAccountSettlementOfClaimsService', [ '$resource', PUBStudentAccountSettlementOfClaimsService ]);

angular.module('ework-ui').factory('PUBSuchService', [ '$resource', PUBSuchService ]);

angular.module('ework-ui').factory('PUBExportService', [ '$resource', PUBExportService ]);

angular.module('ework-ui').factory('PUBORGService', [ '$resource', PUBORGService ]);

angular.module('ework-ui').factory('PUBORGSelectedService', [ '$resource', PUBORGSelectedService ]);

angular.module('ework-ui').factory('PUBSystemParamService', [ '$resource', PUBSystemParamService ]);

angular.module('ework-ui').factory('PUBEmployeeService', [ '$resource', PUBEmployeeService ]);

angular.module('ework-ui').factory('PUBAccountService', [ '$resource', PUBAccountService ]);

angular.module('ework-ui').factory('JudgePasswordService', [ '$resource', JudgePasswordService ]);


/**
 * 报表数据查询服务
 * @returns {*}
 * @constructor
 */
function ReportService($resource){
    return $resource('report/:reportType', {reportType:'@reportType'}, {
        query: {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 项目定义信息
 * @param $resource
 * @returns {*}
 * @constructor
 */
function ProjectService($resource){
    return $resource('data/project.json', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 左侧导航菜单
 * @param $resource
 * @returns {*}
 * @constructor
 */
function MenusService($resource){
    return $resource('common/rightservice', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 公司信息服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function CompanyService($resource){
    return $resource('dic/queryCompanyDatas', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        update :{
        	url:'system/organization/homeSchoolAreas',
            method : 'PUT',
            params : {},
            isArray : false
        },
        queryAreas:{
        	url:'system/organization/queryAreas',
        	method : 'GET',
            params : {},
            isArray : false
        },
        
        querySubOrg:{
        	url:'system/organization/querySubOrg',
        	method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * ERP订单查询
 * @param $resource
 * @returns {*}
 * @constructor
 */
function OrdersService($resource){
    return $resource('order/page', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post :{
            method : 'POST',
            params : {},
            isArray : false
        },
        comments:{
        	url:'order/comments',
        	 params : {},
        	 method:'POST',
             isArray : false
        },
        sync:{
        	 url:'order/sync',
        	 params : {},
        	 method:'POST',
             isArray : false
        },
        //导出数据
        exports:{
        	 url:'excels/output/:modelId',
        	 params : {modelId:'orders_01'},
        	 method:'GET',
             isArray : false
        }
    });
}

/**
 * 获取订单详单信息
 * @param $resource
 * @returns {*}
 * @constructor
 */
function OrderCoursesService($resource){
    return $resource('order/queryOrderCourses', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}


/**
 * 获取ERP系统的课程学生信息列表
 * @param $resource
 * @returns {*}
 * @constructor
 */
function ERPCourseStudentListService($resource){
    return $resource('order/queryStudentsDetail', {}, {
        query : {
            method : 'GET',
            params : {type:1},
            isArray : false
        }
    });
}
/**
 * 获取外账系统的课程学生信息列表
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBCourseStudentListService($resource){
    return $resource('order/queryStudents', {}, {
        query : {
            method : 'GET',
            params : {type:0},
            isArray : false
        }
    });
}

/**
 * 查询ERP的充值单据信息，包括充值，取款，转账，理赔单据
 * @param $resource
 * @returns {*}
 * @constructor
 */
function RechargesService($resource){
    return $resource('order/rechargePage', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        //充值单批注
        post :{
        	url:'order/rechargeComments',
            method : 'POST',
            params : {},
            isArray : false
        },
        //充值单定时
        rechargeTiming:{
        	url:'order/orderRechargeTiming',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 查询课程信息
 * @param $resource
 * @returns {*}
 * @constructor
 */
function CoursesInfoService($resource){
    return $resource('course/page', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        saveCourse:{
             url:'course/save',
        	 method : 'PUT',
             params : {},
             isArray : false	
        	
        }
    });
}

/**
 * 查询课次信息
 * @param $resource
 * @returns {*}
 * @constructor
 */
function CourseTimesService($resource){
    return $resource('course/queryScheduling', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 校区查询
 * @param $resource
 * @returns {*}
 * @constructor
 */
function BranchsService($resource){
    return $resource('dic/querySchoolAreaDatas', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 老师查询
 * @param $resource
 * @returns {*}
 * @constructor
 */
function TeacherListService($resource){
    return $resource('teacher/page', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 老师日程查询
 * @param $resource
 * @returns {*}
 * @constructor
 */
function TeacherScheduleService($resource){
    return $resource('course/queryTeacherSchedule', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 查询学生信息列表
 * @param $resource
 * @returns {*}
 * @constructor
 */
function StudentListService($resource){
    return $resource('course/queryStudentsByCourse', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryPostData:{
        	url:'course/queryStudentSchdules',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 查询单个学生的日程安排的情况
 * @param $resource
 * @returns {*}
 * @constructor
 */
function StudentScheduleService($resource){
    return $resource('course/queryStudentSchdules', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 教师考勤消耗内账外账配比
 * @param $resource
 * @returns {*}
 * @constructor
 */
function TeacherAttendanceService($resource){
    return $resource('teacher/spentCourseTimesPage', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 业务日志查询
 * @param $resource
 * @returns {*}
 * @constructor
 */
function AuditLogService($resource){
    return $resource('log/queryOperaterLogs', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 字典定义
 * @param $resource
 * @returns {*}
 * @constructor
 */
function DictsService($resource){
    return $resource('dic/queryDicTypes', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 字典类型服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function DictTypeItemService($resource){
    return $resource('dict/hrm/DictTypeDatas', {}, {
        add : {
        	url:'dict/hrm/addDictTypeData',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}


/**
 * 字典子项服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function DictItemService($resource){
    return $resource('dic/queryDicDatas', {}, {
        add : {
        	url:'dic/addDicData',
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            url:'dic/updateDicData',
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove:{
        	url:'dic/deleteDicData',
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

/**
 * 角色服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
/**
 * 角色服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function RoleService($resource){
    return $resource('system/privilege/role/page', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        add : {
        	url:'system/privilege/role/save',
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
        	url:'system/privilege/role/update',
            method : 'PUT',
            params : {},
            isArray : false
        },
        queryRoleWithAccount:
        {
        	url:'system/privilege/role/queryRoleWithAccount',
            method : 'GET',
            params : {},
            isArray : false
        },
        delete:{
        	url:'system/privilege/role/forbidden',
            method : 'DELETE',
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
function AccountService($resource){
    return $resource('system/privilege/account/page', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        add : {
        	url:'system/privilege/account/save',
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
        	url:'system/privilege/account/update',
            method : 'PUT',
            params : {},
            isArray : false
        },
        delete:{
        	url:'system/privilege/account/forbidden',
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}



/**
 * 查询校区与公司的归属关系
 * @param $resource
 * @returns {*}
 * @constructor
 */
function BranchTreeService($resource){
    return $resource('data/common/branchTree.json', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        queryTree:{
        	 url:'dic/querySchoolTreeModel',
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
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

/**
 * 文件上传服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function FileUploadService($resource){
    return $resource('data/common/fileUpload.json', {}, {
        query:{
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
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

FileUploadService.uploadService = "excels/inputview/input";

/**
 * 文件上传数据服务
 * @returns {Object}
 * @constructor
 */
function FileUploadDataService($resource){
    return $resource('data/common/fileUploadDatas.json', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        add : {
        	url:'excels/inputview/add/:modelId',
            method : 'POST',
            params : {modelId:'@modelId'},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

/**
 * 对公订单服务
 * query:特殊参数（如果有studentId，则只查询该学员对应的对公报班单据）
 * @param $resource
 * @returns {*}
 * @constructor
 */
function OrdersPubService($resource){
    return $resource('order/orderBJKPage', {}, {
        add :{
        	url:'buissness/bookClass',
            method : 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            params : {},
            isArray : false
        },
        delete :{
        	url:'buissness/invliadBookClassOrder',
            method : 'DELETE',
            params : {},
            isArray : false
        },
        query : {
        	url:'buissness/queryConfilctCourseTimes',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        queryDetails:{
        	url:'buissness/queryOrderDetails/:orderId',
        	method : 'GET',
        	params : {},
        	isArray : false
        }
        
    });
}


/**
 * 学员信息列表
 * @param $resource
 * @returns {*}
 * @constructor
 */
function StudentQueryService($resource){
    return $resource('buissness/queryStudents', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 科目服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function SubjectService($resource){
    return $resource('buissness/queryCourseSubjects', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 课程季服务
 * @returns {Object}
 * @constructor
 */
function SeasonService($resource){
    return $resource('buissness/queryCourseSeasons', {}, {
        query : {
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
 * @constructor
 */
function GradeService($resource){
    return $resource('buissness/queryCourseGrades', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 课程服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function CourseService($resource){
    return $resource('course/queryCourses', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryCourseTimes : {
        	url:'course/queryCourseTimes/:courseId',
            method : 'GET',
            params : {courseId:'@courseId'},
            isArray : false
        },
        get:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 学员账户查询服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function StudentAccountService($resource){
    return $resource('data/pub/student-account.json', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function StudentAccountService($resource){
    return $resource('buissness/queryStudentAccount/:studentId', {studentId:'@studentId'}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 产品线服务
 * @returns {Object}
 * @constructor
 */
function ProductService($resource){
    return $resource('buissness/queryProductLines', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * pos服务
 * @returns {Object}
 * @constructor
 */
function PosService($resource){
    return $resource('buissness/queryPos', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 公司账号服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function CompanyAccountService($resource){
    return $resource('buissness/queryCompanyAccounts', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 退费服务
 * @param $resource
 * @constructor
 */
function RefundService($resource){
    return $resource('order/orderRefundPage', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        },
        post:{
        	url:'buissness/refundFees',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 学生课程课次状态信息
 * @param $resource
 * @returns {*}
 * @constructor
 */
function StudentCourseTimeStateService($resource){
    return $resource('buissness/queryStudentCourseTimes/:studentId/:courseId/:orderCourseId',
    		{studentId:'@studentId',courseId:'@courseId',orderCourseId:'orderCourseId'}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 对公转班服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function TransferClassService($resource){
    return $resource('order/orderTransferPage', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
        	url:'buissness/transferClass',
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 对公考勤课程服务
 * @returns {Object}
 * @constructor
 */
function PUBAttendanceCourseService($resource){
    return $resource('query/course/queryAllCourse/:busiType', {busiType:'@busiType'}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 课程课次考勤信息服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBAttendanceCourseTimeService($resource){
    return $resource('query/course/QueryAllTimes/:courseId', {courseId:'@courseId'}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 对公课程课时考勤学员服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBAttendanceCoursetimeStudentService($resource){
    return $resource('', {schedulingId:'@schedulingId'}, {
        query : {
        	url:'query/course/queryCourseTimeStudents/:schedulingId',
            method : 'GET',
            params : {},
            isArray : false
        },
        update:{
        	url:'attendance/batchAttendance',
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

/**
 * 学员账户变动流水
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBStudentAccountChangeService($resource){
    return $resource('', {}, {
        query : {
        	url:'account/query/dynamic',
            method : 'GET',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        saveForCash:{
        	url:'account/recharge/cash',
            method : 'POST',
            params : {},
            isArray : false
        },
        saveForCard:{
        	url:'account/recharge/card',
            method : 'POST',
            params : {},
            isArray : false
        },
        saveForTransfer:{
        	url:'account/recharge/transfer',
            method : 'POST',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}
/**
 * 对公账户学员转账服务
 * @param $resource
 * @constructor
 */
function PUBStudentAccountTransferService($resource){
    return $resource('data/pub/student_account_change.json', {}, {
        post:{
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 对公账户学员取款服务
 * @param $resource
 * @constructor
 */
function PUBStudentAccountWithDrawService($resource){
    return $resource('account/save/dynamic', {}, {
        withDraw:{
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 对公理赔服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBStudentAccountSettlementOfClaimsService($resource){
    return $resource('data/pub/student_account_change.json', {}, {
        post:{
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}
/**
 * 外账点名表数据服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBSuchService($resource){
    return $resource('bussiness/queryAttendances', {}, {
        query:{
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 文件导出服务：参数1，模板ID
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBExportService($resource){
    return $resource('data/pub/BSsuch-mgr.json', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 外账系统组织结构服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBORGService($resource){
    return $resource('common/orgservice', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        put:{
            method : 'PUT',
            params : {
                id:'@id'
            },
            isArray : false
        }
    });
}

/**
 * 查询当前选中的组织节点
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBORGSelectedService($resource){
    return $resource('common/orgservice/selected', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 查询系统参数
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PUBSystemParamService($resource){
    return $resource('common/system_param', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 员工个人信息服务
 * @returns {Object}
 * @constructor
 */
function PUBEmployeeService($resource){
    return $resource('common/employeeservice', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        put:{
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

/**
 * 个人账户服务
 * @returns {Object}
 * @constructor
 */
function PUBAccountService($resource){
    return $resource('common/accountservice', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        put:{
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}
/**
 * 判断输入的密码是否正确
 * @param $resource
 * @returns
 */
function JudgePasswordService($resource){
	  return $resource('common/accountservice/judgePassword', {}, {
	        query:{
	            method : 'GET',
	            params : {},
	            isArray : false
	        }
	    });
}
