package com.edu.erp.model;

public class TabStudentScore extends BaseObject {
    private static final long serialVersionUID = 1L;
    private Long studentScoreInfoId;
    private String subjectName;
    private Long subjectId;
    private Double score;
    private Integer fullMark;
    private String progress;
    private Integer ranking;
    private Integer classRanking;
    private Long teacherId;
    private String teacherName;
    private String remark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getStudentScoreInfoId() {
        return studentScoreInfoId;
    }

    public void setStudentScoreInfoId(Long studentScoreInfoId) {
        this.studentScoreInfoId = studentScoreInfoId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getFullMark() {
        return fullMark;
    }

    public void setFullMark(Integer fullMark) {
        this.fullMark = fullMark;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getClassRanking() {
        return classRanking;
    }

    public void setClassRanking(Integer classRanking) {
        this.classRanking = classRanking;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
