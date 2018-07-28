package com.edu.erp.model;

import java.util.List;

/**
 * 排课专员实体
 */
public class CourseArrangeSpecialist extends BaseObject {

    private Long employeeId;
    private String employeeEncoding;
    private String employeeName;
    private Long buId;
    private String buName;

    private List<CourseArrangeSpSubject> courseArrangeSpSubjectList;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEncoding() {
        return employeeEncoding;
    }

    public void setEmployeeEncoding(String employeeEncoding) {
        this.employeeEncoding = employeeEncoding;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

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

    public List<CourseArrangeSpSubject> getCourseArrangeSpSubjectList() {
        return courseArrangeSpSubjectList;
    }

    public void setCourseArrangeSpSubjectList(List<CourseArrangeSpSubject> courseArrangeSpSubjectList) {
        this.courseArrangeSpSubjectList = courseArrangeSpSubjectList;
    }
}
