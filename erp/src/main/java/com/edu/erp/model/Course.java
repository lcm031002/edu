package com.edu.erp.model;

import java.math.BigDecimal;
import java.util.Date;

public class Course extends BaseObject {

    // 课程编号
    private String courseNo;

    // 课程名称
    private String courseName;

    // 业务模型  1：班级课  2：一对一 3：晚辅导
    private Integer businessType;

    // 课程目标
    private Integer target;

    // 所属校区
    private Long branchId;

    // 课程季
    private Long seasonId;

    // 年级Id
    private Long gradeId;

    // 科目
    private Long subjectId;

    // 教师ID
    private Long teacherId;

    // 课时单价
    private BigDecimal unitPrice;

    // 课程总价
    private BigDecimal sumPrice;

    // 课时数量
    private Integer courseCount;

    // 课程状态  0：删除   1：上架    2：下架

    // 销售类型 1：正价 2：促销 3：赠送
    private Integer productType;

    // 上架时间:同审批通过时间
    private String validatyDate;

    // 下架日期
    private String invalidityDate;

    // 开课日期
    private String startDate;

    // 结课日期
    private String endDate;

    // 上课时间
    private String startTime;

    // 下课时间
    private String endTime;

    // 课时长度(分钟)
    private BigDecimal hourLen;

    // 剩余课时
    private Integer courseSurplus;

    // 计划上课人数
    private Integer peopleCount;

    // 实际上课人数
    private Integer actualPeopleCount;

    // 上课周期(1,3,5)
    private String attendClassPeriod;

    // 描述
    private String description;

    // 进行状态 1：未开始  2：进行中  3：已结束
    private Integer proceedStatus;

    private Integer isTextbooks;

    private Integer numTextbooks;

    // 1:长期班 2：短期班
    private Integer shortOrLong;

    private Long assteacherId;

    // 业绩归属类型     1:代办校区；2：课程校区
    private Integer performanceBelongType;

    /**
     * 设置 课程编号,对应字段 t_course.course_no
     */
    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    /**
     * 获取 课程编号,对应字段 t_course.course_no
     */
    public String getCourseNo() {
        return this.courseNo;
    }

    /**
     * 设置 课程名称,对应字段 t_course.course_name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 获取 课程名称,对应字段 t_course.course_name
     */
    public String getCourseName() {
        return this.courseName;
    }

    /**
     * 设置 业务模型  1：班级课  2：一对一 3：晚辅导,对应字段 t_course.business_type
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取 业务模型  1：班级课  2：一对一 3：晚辅导,对应字段 t_course.business_type
     */
    public Integer getBusinessType() {
        return this.businessType;
    }

    /**
     * 设置 课程目标,对应字段 t_course.target
     */
    public void setTarget(Integer target) {
        this.target = target;
    }

    /**
     * 获取 课程目标,对应字段 t_course.target
     */
    public Integer getTarget() {
        return this.target;
    }

    /**
     * 设置 所属校区,对应字段 t_course.branch_id
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * 获取 所属校区,对应字段 t_course.branch_id
     */
    public Long getBranchId() {
        return this.branchId;
    }

    /**
     * 设置 课程季,对应字段 t_course.season_id
     */
    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    /**
     * 获取 课程季,对应字段 t_course.season_id
     */
    public Long getSeasonId() {
        return this.seasonId;
    }

    /**
     * 设置 年级Id,对应字段 t_course.grade_id
     */
    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * 获取 年级Id,对应字段 t_course.grade_id
     */
    public Long getGradeId() {
        return this.gradeId;
    }

    /**
     * 设置 科目,对应字段 t_course.subject_id
     */
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * 获取 科目,对应字段 t_course.subject_id
     */
    public Long getSubjectId() {
        return this.subjectId;
    }

    /**
     * 设置 教师ID,对应字段 t_course.teacher_id
     */
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取 教师ID,对应字段 t_course.teacher_id
     */
    public Long getTeacherId() {
        return this.teacherId;
    }

    /**
     * 设置 课时单价,对应字段 t_course.unit_price
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取 课时单价,对应字段 t_course.unit_price
     */
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * 设置 课程总价,对应字段 t_course.sum_price
     */
    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    /**
     * 获取 课程总价,对应字段 t_course.sum_price
     */
    public BigDecimal getSumPrice() {
        return this.sumPrice;
    }

    /**
     * 设置 课时数量,对应字段 t_course.course_count
     */
    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    /**
     * 获取 课时数量,对应字段 t_course.course_count
     */
    public Integer getCourseCount() {
        return this.courseCount;
    }

    /**
     * 设置 销售类型 1：正价 2：促销 3：赠送,对应字段 t_course.product_type
     */
    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    /**
     * 获取 销售类型 1：正价 2：促销 3：赠送,对应字段 t_course.product_type
     */
    public Integer getProductType() {
        return this.productType;
    }

    /**
     * 设置 上架时间:同审批通过时间,对应字段 t_course.validaty_date
     */
    public void setValidatyDate(String validatyDate) {
        this.validatyDate = validatyDate;
    }

    /**
     * 获取 上架时间:同审批通过时间,对应字段 t_course.validaty_date
     */
    public String getValidatyDate() {
        return this.validatyDate;
    }

    /**
     * 设置 下架日期,对应字段 t_course.invalidity_date
     */
    public void setInvalidityDate(String invalidityDate) {
        this.invalidityDate = invalidityDate;
    }

    /**
     * 获取 下架日期,对应字段 t_course.invalidity_date
     */
    public String getInvalidityDate() {
        return this.invalidityDate;
    }

    /**
     * 设置 开课日期,对应字段 t_course.start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取 开课日期,对应字段 t_course.start_date
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * 设置 结课日期,对应字段 t_course.end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取 结课日期,对应字段 t_course.end_date
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * 设置 上课时间,对应字段 t_course.start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 上课时间,对应字段 t_course.start_time
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 设置 下课时间,对应字段 t_course.end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 下课时间,对应字段 t_course.end_time
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 设置 课时长度(分钟),对应字段 t_course.hour_len
     */
    public void setHourLen(BigDecimal hourLen) {
        this.hourLen = hourLen;
    }

    /**
     * 获取 课时长度(分钟),对应字段 t_course.hour_len
     */
    public BigDecimal getHourLen() {
        return this.hourLen;
    }

    /**
     * 设置 剩余课时,对应字段 t_course.course_surplus
     */
    public void setCourseSurplus(Integer courseSurplus) {
        this.courseSurplus = courseSurplus;
    }

    /**
     * 获取 剩余课时,对应字段 t_course.course_surplus
     */
    public Integer getCourseSurplus() {
        return this.courseSurplus;
    }

    /**
     * 设置 计划上课人数,对应字段 t_course.people_count
     */
    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    /**
     * 获取 计划上课人数,对应字段 t_course.people_count
     */
    public Integer getPeopleCount() {
        return this.peopleCount;
    }

    /**
     * 设置 实际上课人数,对应字段 t_course.actual_people_count
     */
    public void setActualPeopleCount(Integer actualPeopleCount) {
        this.actualPeopleCount = actualPeopleCount;
    }

    /**
     * 获取 实际上课人数,对应字段 t_course.actual_people_count
     */
    public Integer getActualPeopleCount() {
        return this.actualPeopleCount;
    }

    /**
     * 设置 上课周期(1,3,5),对应字段 t_course.attend_class_period
     */
    public void setAttendClassPeriod(String attendClassPeriod) {
        this.attendClassPeriod = attendClassPeriod;
    }

    /**
     * 获取 上课周期(1,3,5),对应字段 t_course.attend_class_period
     */
    public String getAttendClassPeriod() {
        return this.attendClassPeriod;
    }

    /**
     * 设置 描述,对应字段 t_course.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 t_course.description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置 进行状态 1：未开始  2：进行中  3：已结束,对应字段 t_course.proceed_status
     */
    public void setProceedStatus(Integer proceedStatus) {
        this.proceedStatus = proceedStatus;
    }

    /**
     * 获取 进行状态 1：未开始  2：进行中  3：已结束,对应字段 t_course.proceed_status
     */
    public Integer getProceedStatus() {
        return this.proceedStatus;
    }

    public void setIsTextbooks(Integer isTextbooks) {
        this.isTextbooks = isTextbooks;
    }

    public Integer getIsTextbooks() {
        return this.isTextbooks;
    }

    public void setNumTextbooks(Integer numTextbooks) {
        this.numTextbooks = numTextbooks;
    }

    public Integer getNumTextbooks() {
        return this.numTextbooks;
    }

    /**
     * 设置 1:长期班 2：短期班,对应字段 t_course.short_or_long
     */
    public void setShortOrLong(Integer shortOrLong) {
        this.shortOrLong = shortOrLong;
    }

    /**
     * 获取 1:长期班 2：短期班,对应字段 t_course.short_or_long
     */
    public Integer getShortOrLong() {
        return this.shortOrLong;
    }

    public void setAssteacherId(Long assteacherId) {
        this.assteacherId = assteacherId;
    }

    public Long getAssteacherId() {
        return this.assteacherId;
    }

    /**
     * 设置 业绩归属类型     1:代办校区；2：课程校区,对应字段 t_course.performance_belong_type
     */
    public void setPerformanceBelongType(Integer performanceBelongType) {
        this.performanceBelongType = performanceBelongType;
    }

    /**
     * 获取 业绩归属类型     1:代办校区；2：课程校区,对应字段 t_course.performance_belong_type
     */
    public Integer getPerformanceBelongType() {
        return this.performanceBelongType;
    }
}