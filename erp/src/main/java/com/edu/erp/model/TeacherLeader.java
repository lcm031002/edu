package com.edu.erp.model;

public class TeacherLeader extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long teach_group_id;

    private Long employee_id;

    private Long teacher_id;

    private String encoding;

    private String teacher_name;

    private Integer is_pluralistic;

    private String phone;

    public Long getTeach_group_id() {
        return teach_group_id;
    }

    public void setTeach_group_id(Long teach_group_id) {
        this.teach_group_id = teach_group_id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public Integer getIs_pluralistic() {
        return is_pluralistic;
    }

    public void setIs_pluralistic(Integer is_pluralistic) {
        this.is_pluralistic = is_pluralistic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
