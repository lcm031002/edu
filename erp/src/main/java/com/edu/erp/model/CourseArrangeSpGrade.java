package com.edu.erp.model;

/**
 * 排课专员科目年级实体
 *
 */
public class CourseArrangeSpGrade extends BaseObject {

    private Long arrangeSpId;
    private Long arrangeSpSubjectId;
    private Long gradeId;
    private String gradeName;

    public Long getArrangeSpId() {
        return arrangeSpId;
    }

    public void setArrangeSpId(Long arrangeSpId) {
        this.arrangeSpId = arrangeSpId;
    }

    public Long getArrangeSpSubjectId() {
        return arrangeSpSubjectId;
    }

    public void setArrangeSpSubjectId(Long arrangeSpSubjectId) {
        this.arrangeSpSubjectId = arrangeSpSubjectId;
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
}
