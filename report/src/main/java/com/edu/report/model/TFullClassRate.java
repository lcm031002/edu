package com.edu.report.model;

/**
 * 满班率报表类
 * @ClassName: TFullClassRate
 * @Description:
 * @author linj linj@klxuexi.org
 * @date 2017年10月17日 下午14:57:59
 *
 */
public class TFullClassRate {
    private Long bu_id;
    private String bu_name;
    private String teacher_name;//教师名称
    private Long grade_id;
    private String grade_name;
    private String subject_name;
    private String group_name;
    private Long season_id;//课程季ID
    private String season_name;//课程季名称
    private Long people_count;
    private Long attendance_people;
    private String actual_rate;//满班率

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public Long getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Long grade_id) {
        this.grade_id = grade_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Long getSeason_id() {
        return season_id;
    }

    public void setSeason_id(Long season_id) {
        this.season_id = season_id;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public Long getPeople_count() {
        return people_count;
    }

    public void setPeople_count(Long people_count) {
        this.people_count = people_count;
    }

    public Long getAttendance_people() {
        return attendance_people;
    }

    public void setAttendance_people(Long attendance_people) {
        this.attendance_people = attendance_people;
    }

    public String getActual_rate() {
        return actual_rate;
    }

    public void setActual_rate(String actual_rate) {
        this.actual_rate = actual_rate;
    }

    public Long getBu_id() {return bu_id;}

    public void setBu_id(Long bu_id) {this.bu_id = bu_id;}

    public String getBu_name() {return bu_name;}

    public void setBu_name(String bu_name) {this.bu_name = bu_name;}
}
