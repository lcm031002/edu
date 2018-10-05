package com.edu.erp.model;

import java.util.Date;

/**
 * @ClassName: TAttendance
 * @Description: 考勤信息
 *
 */
public class TAttendance extends BaseObject {
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = -7650224490231045229L;

    private Long scheduling_id;

    private Long student_id;

    private Long order_course_id;

    private Long attend_user;

    private Date attend_date;

    private Long attend_branch_id;

    private Double attend_amount;

    private Long attend_type;

    private String attend_type_name;

    private Long attend_type_teacher;

    private String attend_type_teacher_name;

    private String ts_flag;

    private String carried;

    private Long create_user;
    
    private Long course_times;
    
    private Date create_time;

    private Long update_user;

    private Date update_time;

    private String for_quit;

    private Long course_date;

    private String remark;

    private Long counselor_id;

    private Long teacher_id;

    private Long subject_id;

    private Long erpAttendance_id;

    private Date course_time;

    private String schedulingTeacherName;

    private String teacher_no;

    private String course_no;

    private String course_name;

    private String subject_name;

    private String attendTypeName;

    private String branch_name;

    private String attTeacherName;

    private Long courseTimes;

    private Long courseScheduleCount; // 计划排课课时

    private String attendClassPeriod; // 新增周期排课的周期参数

    private String startDate; // 新增周期排课的开始计划时间

    private String endDate; // 新增周期排课的结束计划时间

    private String orderCourseIds;

    private String scheduleType;

    private String start_time;

    private String end_time;

    private String counselor_name;

    private String student_name;
    
    private String room_code; //教室编码
    
    private String room_name; //教室名称
    
    private Long sub_attend_type; // 考勤子类型

    private String encoding; // 考勤单号

    private Integer audit_status; // 审批状态 0：审批不通过 1：审批通过 2：审批中

    private String grade_name;

    private Long courseManagerAdvancedParam;//学管

    private Long chineseTeacherAdvanceParam;//中教

    private String chineseTeacherAdvanceParamRemark;//中教备注

    private Long foreignTeacherAdvanceParam;//外教

    private String foreignTeacherAdvanceParamRemark;//外教备注

    private Double spokenLanguageHourLength;//口语时长

    private Double chineseTeacherAdvanceParamHourLength;//中教时长

    private Double foreignTeacherAdvanceParamHourLength;//外教时长

    private Long productLine;//产品线

    private Long apply_id;

    private Long attend_ht_id;

    public static enum AttendTypeEnum{
        YJKQ(21l, "已经考勤"),
        ZCPK(28l, "正常排课"),
        QXPK(23l, "取消排课");
        private Long code;
        private String desc;
        private AttendTypeEnum(Long code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Long getCode() {
            return code;
        }
        public void setCode(Long code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static String getDesc(Long code){
            AttendTypeEnum[] vals= AttendTypeEnum.values();
            for(AttendTypeEnum v : vals){
                if(v.getCode()==code){
                    return v.getDesc();
                }
            }
            return "";
        }
    }

    public static enum AuditStatusEnum{
        REJECT(0),
        PASS(1),
        AUDITING(2);
        private Integer value;
        private AuditStatusEnum(Integer value){
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    public Long getScheduling_id() {
        return scheduling_id;
    }

    public void setScheduling_id(Long scheduling_id) {
        this.scheduling_id = scheduling_id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public Long getOrder_course_id() {
        return order_course_id;
    }

    public void setOrder_course_id(Long order_course_id) {
        this.order_course_id = order_course_id;
    }

    public Long getAttend_user() {
        return attend_user;
    }

    public void setAttend_user(Long attend_user) {
        this.attend_user = attend_user;
    }

    public Date getAttend_date() {
        return attend_date;
    }

    public void setAttend_date(Date attend_date) {
        this.attend_date = attend_date;
    }

    public Long getAttend_branch_id() {
        return attend_branch_id;
    }

    public void setAttend_branch_id(Long attend_branch_id) {
        this.attend_branch_id = attend_branch_id;
    }

    public Double getAttend_amount() {
        return attend_amount;
    }

    public void setAttend_amount(Double attend_amount) {
        this.attend_amount = attend_amount;
    }

    public Long getAttend_type() {
        return attend_type;
    }

    public void setAttend_type(Long attend_type) {
        this.attend_type = attend_type;
    }

    public Long getAttend_type_teacher() {
        return attend_type_teacher;
    }

    public void setAttend_type_teacher(Long attend_type_teacher) {
        this.attend_type_teacher = attend_type_teacher;
    }

    public String getTs_flag() {
        return ts_flag;
    }

    public void setTs_flag(String ts_flag) {
        this.ts_flag = ts_flag;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried;
    }

    public Long getCreate_user() {
        return create_user;
    }

    public void setCreate_user(Long create_user) {
        this.create_user = create_user;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Long getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(Long update_user) {
        this.update_user = update_user;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getFor_quit() {
        return for_quit;
    }

    public void setFor_quit(String for_quit) {
        this.for_quit = for_quit;
    }

    public Long getCourse_date() {
        return course_date;
    }

    public void setCourse_date(Long course_date) {
        this.course_date = course_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCounselor_id() {
        return counselor_id;
    }

    public void setCounselor_id(Long counselor_id) {
        this.counselor_id = counselor_id;
    }

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }

    public Long getErpAttendance_id() {
        return erpAttendance_id;
    }

    public void setErpAttendance_id(Long erpAttendance_id) {
        this.erpAttendance_id = erpAttendance_id;
    }

    public Date getCourse_time() {
        return course_time;
    }

    public void setCourse_time(Date course_time) {
        this.course_time = course_time;
    }

    public String getSchedulingTeacherName() {
        return schedulingTeacherName;
    }

    public void setSchedulingTeacherName(String schedulingTeacherName) {
        this.schedulingTeacherName = schedulingTeacherName;
    }

    public String getAttendTypeName() {
        return attendTypeName;
    }

    public void setAttendTypeName(String attendTypeName) {
        this.attendTypeName = attendTypeName;
    }


    public Long getCourse_times() {
        return course_times;
    }

    public void setCourse_times(Long course_times) {
        this.course_times = course_times;
    }

    public String getAttTeacherName() {
        return attTeacherName;
    }

    public void setAttTeacherName(String attTeacherName) {
        this.attTeacherName = attTeacherName;
    }

    public Long getCourseTimes() {
        return courseTimes;
    }

    public void setCourseTimes(Long courseTimes) {
        this.courseTimes = courseTimes;
        setCourse_times(courseTimes);
    }

    public Long getCourseScheduleCount() {
        return courseScheduleCount;
    }

    public void setCourseScheduleCount(Long courseScheduleCount) {
        this.courseScheduleCount = courseScheduleCount;
    }

    public String getAttendClassPeriod() {
        return attendClassPeriod;
    }

    public void setAttendClassPeriod(String attendClassPeriod) {
        this.attendClassPeriod = attendClassPeriod;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrderCourseIds() {
        return orderCourseIds;
    }

    public void setOrderCourseIds(String orderCourseIds) {
        this.orderCourseIds = orderCourseIds;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getTeacher_no() {
        return teacher_no;
    }

    public void setTeacher_no(String teacher_no) {
        this.teacher_no = teacher_no;
    }

    public String getCourse_no() {
        return course_no;
    }

    public void setCourse_no(String course_no) {
        this.course_no = course_no;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCounselor_name() {
        return counselor_name;
    }

    public void setCounselor_name(String counselor_name) {
        this.counselor_name = counselor_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getAttend_type_name() {
        return attend_type_name;
    }

    public void setAttend_type_name(String attend_type_name) {
        this.attend_type_name = attend_type_name;
    }

    public String getAttend_type_teacher_name() {
        return attend_type_teacher_name;
    }

    public void setAttend_type_teacher_name(String attend_type_teacher_name) {
        this.attend_type_teacher_name = attend_type_teacher_name;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Long getSub_attend_type() {
        return sub_attend_type;
    }

    public void setSub_attend_type(Long sub_attend_type) {
        this.sub_attend_type = sub_attend_type;
    }

    public Integer getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(Integer audit_status) {
        this.audit_status = audit_status;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getGrade_name() { return grade_name;}

    public void setGrade_name(String grade_name) {this.grade_name = grade_name;}

    public Long getApply_id() {
        return apply_id;
    }

    public void setApply_id(Long apply_id) {
        this.apply_id = apply_id;
    }

    public Long getCourseManagerAdvancedParam() {
        return courseManagerAdvancedParam;
    }

    public void setCourseManagerAdvancedParam(Long courseManagerAdvancedParam) {
        this.courseManagerAdvancedParam = courseManagerAdvancedParam;
    }

    public Long getChineseTeacherAdvanceParam() {
        return chineseTeacherAdvanceParam;
    }

    public void setChineseTeacherAdvanceParam(Long chineseTeacherAdvanceParam) {
        this.chineseTeacherAdvanceParam = chineseTeacherAdvanceParam;
    }

    public Long getForeignTeacherAdvanceParam() {
        return foreignTeacherAdvanceParam;
    }

    public void setForeignTeacherAdvanceParam(Long foreignTeacherAdvanceParam) {
        this.foreignTeacherAdvanceParam = foreignTeacherAdvanceParam;
    }

    public Double getSpokenLanguageHourLength() {
        return spokenLanguageHourLength;
    }

    public void setSpokenLanguageHourLength(Double spokenLanguageHourLength) {
        this.spokenLanguageHourLength = spokenLanguageHourLength;
    }

    public Double getChineseTeacherAdvanceParamHourLength() {
        return chineseTeacherAdvanceParamHourLength;
    }

    public void setChineseTeacherAdvanceParamHourLength(Double chineseTeacherAdvanceParamHourLength) {
        this.chineseTeacherAdvanceParamHourLength = chineseTeacherAdvanceParamHourLength;
    }

    public Double getForeignTeacherAdvanceParamHourLength() {
        return foreignTeacherAdvanceParamHourLength;
    }

    public void setForeignTeacherAdvanceParamHourLength(Double foreignTeacherAdvanceParamHourLength) {
        this.foreignTeacherAdvanceParamHourLength = foreignTeacherAdvanceParamHourLength;
    }

    public Long getProductLine() {
        return productLine;
    }

    public void setProductLine(Long productLine) {
        this.productLine = productLine;
    }

    public String getChineseTeacherAdvanceParamRemark() {
        return chineseTeacherAdvanceParamRemark;
    }

    public void setChineseTeacherAdvanceParamRemark(String chineseTeacherAdvanceParamRemark) {
        this.chineseTeacherAdvanceParamRemark = chineseTeacherAdvanceParamRemark;
    }

    public String getForeignTeacherAdvanceParamRemark() {
        return foreignTeacherAdvanceParamRemark;
    }

    public void setForeignTeacherAdvanceParamRemark(String foreignTeacherAdvanceParamRemark) {
        this.foreignTeacherAdvanceParamRemark = foreignTeacherAdvanceParamRemark;
    }

    public Long getAttend_ht_id() {
        return attend_ht_id;
    }

    public void setAttend_ht_id(Long attend_ht_id) {
        this.attend_ht_id = attend_ht_id;
    }
}
