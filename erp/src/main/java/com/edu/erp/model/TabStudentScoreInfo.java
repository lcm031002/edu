package com.edu.erp.model;

import java.util.Date;
import java.util.List;

public class TabStudentScoreInfo extends BaseObject {
    private static final long serialVersionUID = 1L;
    private Long studentId;
    private String studentName;
    private Date submitDate;
    private Long schoolId;
    private String schoolName;
    private Long gradeId;
    private String gradeName;
    private String term;
    private String termName;
    private String examType;
    private String examTypeName;
    private String remark;
    private String oldId;
    private String statusName;

    List<TabStudentScore> studentScoreList;
    List<TabStudentScoreRanking> studentScoreRankingList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<TabStudentScore> getStudentScoreList() {
        return studentScoreList;
    }

    public void setStudentScoreList(List<TabStudentScore> studentScoreList) {
        this.studentScoreList = studentScoreList;
    }

    public List<TabStudentScoreRanking> getStudentScoreRankingList() {
        return studentScoreRankingList;
    }

    public void setStudentScoreRankingList(List<TabStudentScoreRanking> studentScoreRankingList) {
        this.studentScoreRankingList = studentScoreRankingList;
    }
}
