package com.edu.erp.model;

public class TStudentRequirement extends BaseObject {
    private static final long serialVersionUID = 1L;
    private Long applyId;
    private Long subjectId;
    private String subjectName;
    private Integer requirement;
    private Integer seq;
    private Long teachGroupId;
    private String applyType;
    private String teachGroupName;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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

    public Integer getRequirement() {
        return requirement;
    }

    public void setRequirement(Integer requirement) {
        this.requirement = requirement;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getTeachGroupId() {
        return teachGroupId;
    }

    public void setTeachGroupId(Long teachGroupId) {
        this.teachGroupId = teachGroupId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTeachGroupName() {
        return teachGroupName;
    }

    public void setTeachGroupName(String teachGroupName) {
        this.teachGroupName = teachGroupName;
    }
}
