package com.edu.erp.model;

/**
 * 学生年级修改记录类
 *
 */
public class StudentGradeReviseNote {
    //学生ID
    private Long studentId;
    //原来学生年级
    private String originalStudentGrade;
    //学生年级
    private String studentGrade ;
    //操作人id
    private Long operatorId;
    //操作人姓名
    private String operatorName;
    //修改时间
    private String updateTime;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getOriginalStudentGrade() {
        return originalStudentGrade;
    }

    public void setOriginalStudentGrade(String originalStudentGrade) {
        this.originalStudentGrade = originalStudentGrade;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
