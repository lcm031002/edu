package com.edu.erp.course_manager.business;

import com.edu.erp.model.StudentInfo;

import java.util.List;

/**
 * Created by zenglw on 2017/12/20.
 */
public class CourseStudentRelationship {
    private Long courseId;
    private Long studentId;
    private String studentEncoding;
    private String studentName;
    private Integer studentStatus;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public Integer getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(Integer studentStatus) {
        this.studentStatus = studentStatus;
    }
}
