package com.edu.erp.model;

public class TeacherInfo extends BaseObject {

    // 教师code
    private String encoding;

    // 教师姓名
    private String teacherName;

    // 性别(1=男 2=女)
    private Integer sex;

    // 手机
    private String phone;

    // 员工ID
    private Long employeeId;

    // 微信
    private String wechat;

    // 邮箱
    private String email;

    // 教师身份 1-主讲老师 2-辅导老师-双师  3-普通老师 4-辅导老师-培英
    private Integer teacherType;

    // 头像
    private String photo;

    // 描述
    private String description;

    /**
     * 设置 教师code,对应字段 tab_teacher_info.encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 获取 教师code,对应字段 tab_teacher_info.encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置 教师姓名,对应字段 tab_teacher_info.teacher_name
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * 获取 教师姓名,对应字段 tab_teacher_info.teacher_name
     */
    public String getTeacherName() {
        return this.teacherName;
    }

    /**
     * 设置 性别(1=男 2=女),对应字段 tab_teacher_info.sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取 性别(1=男 2=女),对应字段 tab_teacher_info.sex
     */
    public Integer getSex() {
        return this.sex;
    }

    /**
     * 设置 手机,对应字段 tab_teacher_info.phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取 手机,对应字段 tab_teacher_info.phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置 员工ID,对应字段 tab_teacher_info.employee_id
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 获取 员工ID,对应字段 tab_teacher_info.employee_id
     */
    public Long getEmployeeId() {
        return this.employeeId;
    }

    /**
     * 设置 微信,对应字段 tab_teacher_info.wechat
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * 获取 微信,对应字段 tab_teacher_info.wechat
     */
    public String getWechat() {
        return this.wechat;
    }

    /**
     * 设置 邮箱,对应字段 tab_teacher_info.email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取 邮箱,对应字段 tab_teacher_info.email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 设置 教师身份 1-主讲老师 2-辅导老师-双师  3-普通老师 4-辅导老师-培英,对应字段 tab_teacher_info.teacher_type
     */
    public void setTeacherType(Integer teacherType) {
        this.teacherType = teacherType;
    }

    /**
     * 获取 教师身份 1-主讲老师 2-辅导老师-双师  3-普通老师 4-辅导老师-培英,对应字段 tab_teacher_info.teacher_type
     */
    public Integer getTeacherType() {
        return this.teacherType;
    }

    /**
     * 设置 头像,对应字段 tab_teacher_info.photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 获取 头像,对应字段 tab_teacher_info.photo
     */
    public String getPhoto() {
        return this.photo;
    }

    /**
     * 设置 描述,对应字段 tab_teacher_info.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 tab_teacher_info.description
     */
    public String getDescription() {
        return this.description;
    }
}