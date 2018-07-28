package com.edu.erp.model;

/**
 * ${教师团队关系表}
 *
 */
public class TeacherTeamRel {
    private  Long id;
    private  Long teacherId;
    private  Long city_id;
    private  String teacherName;
    private  String org_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCity_id() { return city_id; }

    public void setCity_id(Long city_id) { this.city_id = city_id; }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
