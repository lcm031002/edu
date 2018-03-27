package com.edu.erp.model;

import java.util.Date;
import java.util.List;

public class TScheduleApplyInfo extends BaseObject {
    private static final long serialVersionUID = 1L;
    private String encoding;
    private String applyType;
    private String applyTypeName;
    private Date beginDate;
    private Long counselorId;
    private String counselor;
    private String counselorPhone;
    private String counselorLine;
    private Long courseAdminId;
    private String courseAdmin;
    private String courseAdminPhone;
    private String courseAdminLine;
    private String classPlace;
    private String branchEmail;
    private String remark;
    private String studentRanking;
    private String studentCharacter;
    private String parentInfo;
    private String studentSituation;
    private String teacherSpecification;
    private String firstClassContent;
    private String changeTeacherReasons;
    private String workDirections;
    private String workRemark;
    private Long teachGroupId;
    private String teachGroup;
    private Long auditUserId;
    private String auditUser;
    private Date auditDate;
    private String auditStatus;
    private String auditStatusName;
    private Long studentId;
    private String studentName;
    private String studentEncoding;
    private String sexName;
    private String schoolName;
    private String stuGradeName;
    private Long branchId;
    private Long gradeId;
    private String gradeName;
    private String term;
    private String termName;
    private String examType;
    private String statusName;
    private Long schedulableSubjectId;
    private String schedulableSubjectName;
    private String subjectName;
    private Integer times;
    private String schedule;
    private String rejectReason;
    private Long courseArrangerId;
    private Long courseArrangerEmpId;
    private String courseArranger;
    private List<TStudentScore> stuScoreList;
    private List<TStudentRequirement> stuReqList;
    private List<TStudentSchedulePlan> stuSchedPlanList;
    
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyTypeName() {
        return applyTypeName;
    }

    public void setApplyTypeName(String applyTypeName) {
        this.applyTypeName = applyTypeName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Long getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Long counselorId) {
        this.counselorId = counselorId;
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor;
    }

    public String getCounselorPhone() {
        return counselorPhone;
    }

    public void setCounselorPhone(String counselorPhone) {
        this.counselorPhone = counselorPhone;
    }

    public String getCounselorLine() {
        return counselorLine;
    }

    public void setCounselorLine(String counselorLine) {
        this.counselorLine = counselorLine;
    }

    public Long getCourseAdminId() {
        return courseAdminId;
    }

    public void setCourseAdminId(Long courseAdminId) {
        this.courseAdminId = courseAdminId;
    }

    public String getCourseAdmin() {
        return courseAdmin;
    }

    public void setCourseAdmin(String courseAdmin) {
        this.courseAdmin = courseAdmin;
    }

    public String getCourseAdminPhone() {
        return courseAdminPhone;
    }

    public void setCourseAdminPhone(String courseAdminPhone) {
        this.courseAdminPhone = courseAdminPhone;
    }

    public String getCourseAdminLine() {
        return courseAdminLine;
    }

    public void setCourseAdminLine(String courseAdminLine) {
        this.courseAdminLine = courseAdminLine;
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace;
    }

    public String getBranchEmail() {
        return branchEmail;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStudentRanking() {
        return studentRanking;
    }

    public void setStudentRanking(String studentRanking) {
        this.studentRanking = studentRanking;
    }

    public String getStudentCharacter() {
        return studentCharacter;
    }

    public void setStudentCharacter(String studentCharacter) {
        this.studentCharacter = studentCharacter;
    }

    public String getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(String parentInfo) {
        this.parentInfo = parentInfo;
    }

    public String getStudentSituation() {
        return studentSituation;
    }

    public void setStudentSituation(String studentSituation) {
        this.studentSituation = studentSituation;
    }

    public String getTeacherSpecification() {
        return teacherSpecification;
    }

    public void setTeacherSpecification(String teacherSpecification) {
        this.teacherSpecification = teacherSpecification;
    }

    public String getFirstClassContent() {
        return firstClassContent;
    }

    public void setFirstClassContent(String firstClassContent) {
        this.firstClassContent = firstClassContent;
    }

    public String getChangeTeacherReasons() {
        return changeTeacherReasons;
    }

    public void setChangeTeacherReasons(String changeTeacherReasons) {
        this.changeTeacherReasons = changeTeacherReasons;
    }

    public String getWorkDirections() {
        return workDirections;
    }

    public void setWorkDirections(String workDirections) {
        this.workDirections = workDirections;
    }

    public String getWorkRemark() {
        return workRemark;
    }

    public void setWorkRemark(String workRemark) {
        this.workRemark = workRemark;
    }

    public Long getTeachGroupId() {
        return teachGroupId;
    }

    public void setTeachGroupId(Long teachGroupId) {
        this.teachGroupId = teachGroupId;
    }

    public String getTeachGroup() {
        return teachGroup;
    }

    public void setTeachGroup(String teachGroup) {
        this.teachGroup = teachGroup;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatusName() {
        return auditStatusName;
    }

    public void setAuditStatusName(String auditStatusName) {
        this.auditStatusName = auditStatusName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEncoding() {
        return studentEncoding;
    }

    public void setStudentEncoding(String studentEncoding) {
        this.studentEncoding = studentEncoding;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStuGradeName() {
        return stuGradeName;
    }

    public void setStuGradeName(String stuGradeName) {
        this.stuGradeName = stuGradeName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getSchedulableSubjectId() {
        return schedulableSubjectId;
    }

    public void setSchedulableSubjectId(Long schedulableSubjectId) {
        this.schedulableSubjectId = schedulableSubjectId;
    }

    public String getSchedulableSubjectName() {
        return schedulableSubjectName;
    }

    public void setSchedulableSubjectName(String schedulableSubjectName) {
        this.schedulableSubjectName = schedulableSubjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
    
    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public Long getCourseArrangerId() {
        return courseArrangerId;
    }

    public void setCourseArrangerId(Long courseArrangerId) {
        this.courseArrangerId = courseArrangerId;
    }

    public Long getCourseArrangerEmpId() {
        return courseArrangerEmpId;
    }

    public void setCourseArrangerEmpId(Long courseArrangerEmpId) {
        this.courseArrangerEmpId = courseArrangerEmpId;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getCourseArranger() {
        return courseArranger;
    }

    public void setCourseArranger(String courseArranger) {
        this.courseArranger = courseArranger;
    }

    public List<TStudentScore> getStuScoreList() {
        return stuScoreList;
    }

    public void setStuScoreList(List<TStudentScore> stuScoreList) {
        this.stuScoreList = stuScoreList;
    }

    public List<TStudentRequirement> getStuReqList() {
        return stuReqList;
    }

    public void setStuReqList(List<TStudentRequirement> stuReqList) {
        this.stuReqList = stuReqList;
    }
    
    public List<TStudentSchedulePlan> getStuSchedPlanList() {
        return stuSchedPlanList;
    }

    public void setStuSchedPlanList(List<TStudentSchedulePlan> stuSchedPlanList) {
        this.stuSchedPlanList = stuSchedPlanList;
    }
}
