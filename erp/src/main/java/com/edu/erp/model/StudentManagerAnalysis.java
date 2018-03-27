package com.edu.erp.model;

public class StudentManagerAnalysis {
    private Long buId;
    private String buName;
    private Long branchId;
    private String branchName;
    private Long studentId;
    private String studentEncoding;
    private String studentName;
    private Long studentManagerId;
    private String studentManager;
    private Long attendanceCount;
    private Long surplusCount;
    private Long scheduleCount;
    private Long studentCount;

    public Long getBuId() {
        return buId;
    }

    public void setBuId(Long buId) {
        this.buId = buId;
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentEncoding() {
        return studentEncoding;
    }

    public void setStudentEncoding(String studentEncoding) {
        this.studentEncoding = studentEncoding;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudentManagerId() {
        return studentManagerId;
    }

    public void setStudentManagerId(Long studentManagerId) {
        this.studentManagerId = studentManagerId;
    }

    public String getStudentManager() {
        return studentManager;
    }

    public void setStudentManager(String studentManager) {
        this.studentManager = studentManager;
    }

    public Long getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(Long attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    public Long getSurplusCount() {
        return surplusCount;
    }

    public void setSurplusCount(Long surplusCount) {
        this.surplusCount = surplusCount;
    }

    public Long getScheduleCount() {
        return scheduleCount;
    }

    public void setScheduleCount(Long scheduleCount) {
        this.scheduleCount = scheduleCount;
    }

    public Long getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Long studentCount) {
        this.studentCount = studentCount;
    }
}
