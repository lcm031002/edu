package com.edu.erp.model;

import java.util.List;
import org.codehaus.jackson.map.Serializers.Base;

/**
 * 排课专员科目实体
 *
 */
public class CourseArrangeSpSubject extends BaseObject {

    private Long arrangeSpId;
    private Long subjectId;
    private String subjectName;

    private List<CourseArrangeSpGrade> courseArrangeSpGradeList;

    public Long getArrangeSpId() {
        return arrangeSpId;
    }

    public void setArrangeSpId(Long arrangeSpId) {
        this.arrangeSpId = arrangeSpId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<CourseArrangeSpGrade> getCourseArrangeSpGradeList() {
        return courseArrangeSpGradeList;
    }

    public void setCourseArrangeSpGradeList(List<CourseArrangeSpGrade> courseArrangeSpGradeList) {
        this.courseArrangeSpGradeList = courseArrangeSpGradeList;
    }
}
