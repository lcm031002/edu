package com.edu.erp.course_manager.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zenglw on 2017/12/21.
 */
public class CourseStudentInfo {

    /**课程ID*/
    private Long courseId;
    /**在读学生数*/
    private List<CourseStudentRelationship> inReadingStudents = new ArrayList<>();
    /**报名学生数*/
    private List<CourseStudentRelationship> signUpStudents = new ArrayList<>();

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<CourseStudentRelationship> getInReadingStudents() {
        return inReadingStudents;
    }

    public void setInReadingStudents(List<CourseStudentRelationship> inReadingStudents) {
        this.inReadingStudents = inReadingStudents;
    }

    public List<CourseStudentRelationship> getSignUpStudents() {
        return signUpStudents;
    }

    public void setSignUpStudents(List<CourseStudentRelationship> signUpStudents) {
        this.signUpStudents = signUpStudents;
    }


}
