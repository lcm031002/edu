package com.edu.common.constants;

public interface Constants {
    int LIST_FIRST_INDEX = 0;

    String DEFAULT_ENCODING = "utf-8";

    String YES = "Y";
    String NO = "N";

    String VALID = "valid";
    String INVALID = "invalid";
    String DELETE = "delete";

    String SUCCESS_TEXT = "成功";
    String FAIL_TEXT = "失败";

    interface RespMapKey {
        String ERROR = "error";
        String DATA = "data";
        String MESSAGE = "message";
    }

    /**
     * 编码生成规则前缀序号，定义EncodingSequenceUtil中前缀对应的序号
     */
    interface EncodingPrefixSeq {
        Long CZDJ_PREFIX = 1L; // 1：充值单据
        Long TFDJ_PREFIX = 2L; // 2：退费单据
        Long QKDJ_PREFIX = 3L; // 3：取款单据
        Long ZZDJ_PREFIX = 4L; // 4：转账单据
        Long BJK_PREFIX = 5L; // 5：班级课
        Long YDY_PREFIX = 6L; // 6：一对一
        Long WFD_PREFIX = 7L; // 7：晚辅导
        Long TYPE_FPTT_PREFIX = 8L;// 8：发票抬头
        Long CZ_ZF_PREFIX = 9L; // 9：充值作废
        Long QK_ZF_PREFIX = 10L; // 10：取款作废
        Long DD_ZF_PREFIX = 11L; // 11：订单作废
        Long TF_ZF_PREFIX = 12L; // 12：退费作废
        Long XYBH_PREFIX = 13L;// 13:学员编号
        Long SSA_PREFIX = 14L; // 14:考勤单号
        Long YDY_ATTEND_PREFIX = 15L; // 15:考勤单号
    }

    /**
     * 发票抬头
     */
    interface InvoiceHeading {
        String PERSONAL = "1"; // 个人
        String COMPANY = "2"; // 公司
    }

    /**
     * 订单开票状态
     */
    interface OrderInvoiceStatus {
        Long NOT_MAKE_OUT = 0L; // 未开票
        Long MAKE_OUT = 1L; // 已开票
    }

    interface AccountOperateType {
        String RECHARGE = "recharge"; // 充值
        String TRANSFER = "transfer"; // 转账
        String CLAIM = "claim"; // 理赔
    }

    interface PageInit {
        Integer PAGE_SIZE = 10;
        Integer PAGE_NUM = 1;
    }

    interface DictTypeCode {
        String TEACHER_TYPE = "teacherType";
        String GENDER = "gender";
        String TEACHER_STATUS = "teacherStatus";
        String IS_PART_TIME = "isPartTime";

        String TEACHER_PAGE_TYPE_CODES = "teacherType,gender,teacherStatus,isPartTime";
    }

    interface BusinessType {
        Long BJK = 1L;
        Long GXH = 2L;
        Long WFD = 3L;
    }

    interface OrgType {
        Integer CITY = 2;
        Integer GROUP = 3;
        Integer BRANCH = 4;
    }

    /**
     * 考勤状态
     */
    interface AttendType {
        long INVALID = -1L; // 无效

        long BJK_SET_NULL = 10L; // 班级课置空
        long BJK_HANG_UP = 11L; // 班级课挂起
        long BJK_NORMAL_ATTEND = 12L; // 班级课正常上课

        long YDY_SET_NULL = 20L; // 一对一置空
        long YDY_NORMAL_ATTEND = 21L; // 一对一正常上课
        long YDY_CANCEL_SCHED = 23L; // 一对一排课取消
        long YDY_NORMAL_SCHED = 28L; // 一对一正常排课
        long YDY_CANCEL_ATTEND = 29L; // 一对一考勤作废

        long WFD_SET_NULL = 30L; // 晚辅导置空
        long WFD_NORMAL_ATTEND = 31L; // 晚辅导正常上课
    }

    /**
     * 个性化排课申请类型
     */
    interface SchedApplyType {
        String NEW_APPLY = "1"; // 新单
        String ADD_APPLY = "2"; // 加单
        String CHANGE_APPLY = "3"; // 换单
    }

    /**
     * 个性化排课申请状态
     */
    interface SchedApplyStatus {
        Integer CANCELED = 0; // 已取消
        Integer SUBMIT = 1; // 待匹配
        Integer MATCHING = 2; // 匹配中
        Integer MATCHED = 3; // 已完成
        Integer HAS_YD = 4; // 有压单
        Integer DRAFT = 5; // 草稿
    }

    /**
     * 排课申请审批状态
     */
    interface SchedApplyAuditStatus {
        String REJECT = "0"; // 审批不通过
        String PASS = "1"; // 审批通过
        String AUDITING = "2"; // 审核中
        String WAIT_AUDIT = "3"; // 未审核
    }

    /**
     * 师生匹配状态
     */
    interface StuSchedPlanStatus {
        Integer NOT_SCHEDULED = 1;
        Integer SCHEDULED = 2;
        Integer YA_DAN = 3;
        Integer CANCEL_YA_DAN = 4;
    }

    /**
     * 一对一排课列表类型
     */
    interface SchedApplyListType {
        String APPLY = "apply";
        String MATCH = "match";
        String AUDIT = "audit";
        String HANDLE = "handle";
    }

    /**
     * 订单状态
     */
    interface OrderStatus {
        Long CANCEL = 0L; // 作废
        Long NORMAL = 1L; // 正常
        Long DRAFT = 2L; // 暂存
        Long WAIT_PAY = 3L; // 待缴费
        Long WAIT_AUDIT = 4L; // 审批中
        Long REJECTED = 5L; // 审核不通过
    }

    /**
     * 校区类型
     */
    interface OrgKind {
        int BJK = 1; // 培英精品班
        int YDY = 4; // 个性化
        int JY = 11; // 佳音
        int MT = 12; // 双师
    }

    /**
     * 双师课程类型
     */
    interface MtCourseType {
        String BUKE = "3"; // 在线补课
        String MT = "4"; // 双师
    }

    String DAY_START_TIME = "00:00";
    String DAY_END_TIME = "24:00";

    String[] DATE_PATTERN = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd"};

    String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    String DATE_FORMAT_2 = "yyyyMMddHHmmss";
    String DATE_FORMAT_3 = "yyyy-MM-dd";
    String DATE_FORMAT_4 = "yyyy/MM/dd";
    String DATE_FORMAT_5 = "HH:mm:ss";
    String DATE_FORMAT_6 = "HHmmss";
    String DATE_FORMAT_7 = "HH:mm";

}
