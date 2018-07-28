package com.edu.erp.model;

/**
 * 学生姓名修改记录类
 *
 */
public class StudentNameReviseNote {

    private Long id;
    //学生ID
    private  Long studentId;
    //原来学生姓名
    private  String originalStudentName;
    //学生姓名
    private  String studentName;
    //操作人Id
    private  Long operatorId;
    //操作人
    private  String operatorName;
    //修改时间
    private  String updateTime;

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

    public String getOriginalStudentName() {
        return originalStudentName;
    }

    public void setOriginalStudentName(String originalStudentName) {
        this.originalStudentName = originalStudentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
