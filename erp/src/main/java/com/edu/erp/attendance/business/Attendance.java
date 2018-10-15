package com.edu.erp.attendance.business;

import com.edu.erp.model.TAttendance;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Attendance implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "排课表ID")
    private Long schedulingId;

    @ApiModelProperty(value = "学生ID")
    private Long studentId;

    @ApiModelProperty(value = "订单课程ID")
    private Long orderCourseId;

    @ApiModelProperty(value = "考勤人")
    private Long attendUser;

    @ApiModelProperty(value = "考勤日期")
    private Date attendDate;

    @ApiModelProperty(value = "考勤校区ID")
    private Long attendBranchId;

    @ApiModelProperty(value = "考勤金额")
    private Double attendAmount;

    @ApiModelProperty(value = "考勤状态类型")
    private EnumAttendType attendType;

    @ApiModelProperty(value = "学生教师分类标识:T教师,S学生")
    private String tsFlag;

    @ApiModelProperty(value = "是否结转:Y已结转,N未结转")
    private String carried;

    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "上课日期")
    private Long courseDate;

    @ApiModelProperty(value = "")
    private String remark;

    @ApiModelProperty(value = "学管（咨询）ID")
    private Long counselorId;

    @ApiModelProperty(value = "")
    private Long teacherId;

    @ApiModelProperty(value = "退费发起的挂起考勤，1:是，0或null就不是")
    private Long forQuit;

    @ApiModelProperty(value = "上课时间HH:MM")
    private String startTime;

    @ApiModelProperty(value = "下课时间HH:MM")
    private String endTime;

    @ApiModelProperty(value = "科目ID")
    private Long subjectId;

    @ApiModelProperty(value = "1对1教师考勤状态类型")
    private EnumAttendType attendTypeTeacher;

    @ApiModelProperty(value = "")
    private Long courseTimes;

    @ApiModelProperty(value = "考勤状态子类型")
    private EnumAttendType subAttendType;

    @ApiModelProperty(value = "考勤单号")
    private String encoding;

    @ApiModelProperty(value = "审批状态 0：审批不通过 1：审批通过 2：审批中")
    private Integer auditStatus;

    @ApiModelProperty(value = "")
    private Long applyId;

    @ApiModelProperty(value = "高级参数,学管师")
    private Long courseManager;

    @ApiModelProperty(value = "高级参数中教")
    private Long courseTearcherCn;

    @ApiModelProperty(value = "高级参数外教")
    private Long courseTearcherEn;

    @ApiModelProperty(value = "高级参数中教课时长度")
    private Double courseTearcherCnHour;

    @ApiModelProperty(value = "高级参数外教课时长度")
    private Double courseTearcherEnHour;

    @ApiModelProperty(value = "中教口语长度")
    private Double spokenLanguageHour;

    @ApiModelProperty(value = "高级参数中教备注")
    private String courseTearcherCnRemark;

    @ApiModelProperty(value = "高级参数外教备注")
    private String courseTearcherEnRemark;

    @ApiModelProperty(value = "考勤照片")
    private String attendancePic;


    public Attendance(TAttendance a) {
        this.id = a.getId();
        this.applyId = a.getApply_id();
        this.attendAmount = a.getAttend_amount();
        this.attendBranchId = a.getAttend_branch_id();
        this.attendDate = a.getAttend_date();
        this.attendType = EnumAttendType.of(a.getAttend_type());
        this.attendTypeTeacher = EnumAttendType.of(a.getAttend_type_teacher());
        this.attendUser = a.getAttend_user();
        this.auditStatus = a.getAudit_status();
        this.carried = a.getCarried();
        this.counselorId = a.getCounselor_id();
        this.courseDate = a.getCourse_date();
        this.courseManager = a.getCourseManagerAdvancedParam();
        this.courseTearcherCn = a.getChineseTeacherAdvanceParam();
        this.schedulingId = a.getScheduling_id();
        this.studentId = a.getStudent_id();
        this.subAttendType = EnumAttendType.of(a.getSub_attend_type());
        this.remark = a.getRemark();
        this.createTime = a.getCreate_time();
    }
    
    
    public enum EnumAttendType {

        WX(-1L, "无效"),
        BJK_CS(10L, "班级课考勤初始状态"),
        BJK_GQ(11L, "班级课挂起"),
        BJK_ZCSK(12L, "班级课正常上课"),
        YDY_CS(20L, "1对1考勤初始状态"),
        YDY_ZCSK(21L, "1对1正常上课"),
        YDY_WGSK(22L, "1对1违规上课"),
        YDY_QX(23L, "1对1排课取消"),
        YDY_XSKK(24L, "1对1学生旷课老师到"),
        YDY_LSKG(25L, "1对1老师旷工学生到"),
        YDY_SG_LSWD(26L, "1对1学管事故-学生到老师未到"),
        YDY_SG_LSD(27L, "1对1学管事故-学生未到老师到"),
        YDY_WKQ(28L, "1对1未考勤"),
        YDY_ZF(29L, "1对1考勤作废"),
        WFD_CS(30L, "晚辅导考勤初始状态"),
        WFD_ZCSK(31L, "晚辅导正常上课");

        private Long code;
        private String desc;

        EnumAttendType(Long code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static EnumAttendType of(Long code) {
            final EnumAttendType[] values = EnumAttendType.values();
            for (EnumAttendType value : values) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
            return null;
        }

        public Long getCode() {
            return code;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Long schedulingId) {
        this.schedulingId = schedulingId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getOrderCourseId() {
        return orderCourseId;
    }

    public void setOrderCourseId(Long orderCourseId) {
        this.orderCourseId = orderCourseId;
    }

    public Long getAttendUser() {
        return attendUser;
    }

    public void setAttendUser(Long attendUser) {
        this.attendUser = attendUser;
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    public Long getAttendBranchId() {
        return attendBranchId;
    }

    public void setAttendBranchId(Long attendBranchId) {
        this.attendBranchId = attendBranchId;
    }

    public Double getAttendAmount() {
        return attendAmount;
    }

    public void setAttendAmount(Double attendAmount) {
        this.attendAmount = attendAmount;
    }

    public EnumAttendType getAttendType() {
        return attendType;
    }

    public void setAttendType(EnumAttendType attendType) {
        this.attendType = attendType;
    }

    public String getTsFlag() {
        return tsFlag;
    }

    public void setTsFlag(String tsFlag) {
        this.tsFlag = tsFlag;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Long courseDate) {
        this.courseDate = courseDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Long counselorId) {
        this.counselorId = counselorId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getForQuit() {
        return forQuit;
    }

    public void setForQuit(Long forQuit) {
        this.forQuit = forQuit;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public EnumAttendType getAttendTypeTeacher() {
        return attendTypeTeacher;
    }

    public void setAttendTypeTeacher(EnumAttendType attendTypeTeacher) {
        this.attendTypeTeacher = attendTypeTeacher;
    }

    public Long getCourseTimes() {
        return courseTimes;
    }

    public void setCourseTimes(Long courseTimes) {
        this.courseTimes = courseTimes;
    }

    public EnumAttendType getSubAttendType() {
        return subAttendType;
    }

    public void setSubAttendType(EnumAttendType subAttendType) {
        this.subAttendType = subAttendType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getCourseManager() {
        return courseManager;
    }

    public void setCourseManager(Long courseManager) {
        this.courseManager = courseManager;
    }

    public Long getCourseTearcherCn() {
        return courseTearcherCn;
    }

    public void setCourseTearcherCn(Long courseTearcherCn) {
        this.courseTearcherCn = courseTearcherCn;
    }

    public Long getCourseTearcherEn() {
        return courseTearcherEn;
    }

    public void setCourseTearcherEn(Long courseTearcherEn) {
        this.courseTearcherEn = courseTearcherEn;
    }

    public Double getCourseTearcherCnHour() {
        return courseTearcherCnHour;
    }

    public void setCourseTearcherCnHour(Double courseTearcherCnHour) {
        this.courseTearcherCnHour = courseTearcherCnHour;
    }

    public Double getCourseTearcherEnHour() {
        return courseTearcherEnHour;
    }

    public void setCourseTearcherEnHour(Double courseTearcherEnHour) {
        this.courseTearcherEnHour = courseTearcherEnHour;
    }

    public Double getSpokenLanguageHour() {
        return spokenLanguageHour;
    }

    public void setSpokenLanguageHour(Double spokenLanguageHour) {
        this.spokenLanguageHour = spokenLanguageHour;
    }

    public String getCourseTearcherCnRemark() {
        return courseTearcherCnRemark;
    }

    public void setCourseTearcherCnRemark(String courseTearcherCnRemark) {
        this.courseTearcherCnRemark = courseTearcherCnRemark;
    }

    public String getCourseTearcherEnRemark() {
        return courseTearcherEnRemark;
    }

    public void setCourseTearcherEnRemark(String courseTearcherEnRemark) {
        this.courseTearcherEnRemark = courseTearcherEnRemark;
    }

    public String getAttendancePic() {
        return attendancePic;
    }

    public void setAttendancePic(String attendancePic) {
        this.attendancePic = attendancePic;
    }
}