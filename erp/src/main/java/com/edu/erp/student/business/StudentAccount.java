package com.edu.erp.student.business;

/**
 * 学员账户信息
 */
public class StudentAccount {
    private Long id;
    private Long buId;
    private String buName;
    private Long studentId;
    private String studentEncoding;
    private String studentName;
    private Long schoolId;
    private String schoolName;
    private Long gradeId;
    private String gradeName;
    private Double feeAccount;
    private Double frozenAccount;
    private Double refundAccount;
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
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

    public Long getStudentId() {
        return studentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public Double getFeeAccount() {
        return feeAccount;
    }

    public void setFeeAccount(Double feeAccount) {
        this.feeAccount = feeAccount;
    }

    public Double getFrozenAccount() {
        return frozenAccount;
    }

    public void setFrozenAccount(Double frozenAccount) {
        this.frozenAccount = frozenAccount;
    }

    public Double getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(Double refundAccount) {
        this.refundAccount = refundAccount;
    }
}
