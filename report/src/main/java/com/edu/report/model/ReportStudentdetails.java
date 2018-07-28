package com.edu.report.model;

/**
 * 艾伦报表
 *
 */
public class ReportStudentdetails {
     private Long id;
     private Long studentId;
     private String studentEcoding;
     private String studentName;
     private int status;
     private String studentStatus;
     private String changeTime;
     private Long branchId;
     private String branchName;
     private Long counselorId;
     private String counselorName;
     private Long learningmgrId;
     private String learningmgrName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentEcoding() {
        return studentEcoding;
    }

    public void setStudentEcoding(String studentEcoding) {
        this.studentEcoding = studentEcoding;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Long counselorId) {
        this.counselorId = counselorId;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public Long getLearningmgrId() {
        return learningmgrId;
    }

    public void setLearningmgrId(Long learningmgrId) {
        this.learningmgrId = learningmgrId;
    }

    public String getLearningmgrName() {
        return learningmgrName;
    }

    public void setLearningmgrName(String learningmgrName) {
        this.learningmgrName = learningmgrName;
    }
}
