package com.edu.report.model;

import java.math.BigDecimal;

/**
 * @ClassName: TAttendanceReport
 * @Description: 考勤消耗表
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月15日 下午2:44:12
 *
 */
public class TeacherGroupAttendanceReport {
	
	private String task_flow; //任务批次

	private Long id;
	
	private String teacher_name; 		//教师姓名

	private String group_name; 		 	//教研组

	private String grade_name; 		 	//年级
	
	private Long course_students; 		//本季人数

	private Long attendance_students; 	//本季出勤人数

	private String attendance_rate; 		//本季出勤率

	private Long last_plan_attendance; 	//上次课应到

	private Long last_actual_attendance; //上次课实到

	private Long curr_plan_attendance;   //本次课应到

	private Long curr_actual_attendance; //上次课实到

	public String getTask_flow() {
		return task_flow;
	}

	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public Long getCourse_students() {
		return course_students;
	}

	public void setCourse_students(Long course_students) {
		this.course_students = course_students;
	}

	public Long getAttendance_students() {
		return attendance_students;
	}

	public void setAttendance_students(Long attendance_students) {
		this.attendance_students = attendance_students;
	}

	public String getAttendance_rate() {	return attendance_rate;	}

	public void setAttendance_rate(String attendance_rate) {	this.attendance_rate = attendance_rate;	}

	public Long getLast_plan_attendance() {
		return last_plan_attendance;
	}

	public void setLast_plan_attendance(Long last_plan_attendance) {
		this.last_plan_attendance = last_plan_attendance;
	}

	public Long getLast_actual_attendance() {
		return last_actual_attendance;
	}

	public void setLast_actual_attendance(Long last_actual_attendance) {
		this.last_actual_attendance = last_actual_attendance;
	}

	public Long getCurr_plan_attendance() {
		return curr_plan_attendance;
	}

	public void setCurr_plan_attendance(Long curr_plan_attendance) {
		this.curr_plan_attendance = curr_plan_attendance;
	}

	public Long getCurr_actual_attendance() {
		return curr_actual_attendance;
	}

	public void setCurr_actual_attendance(Long curr_actual_attendance) {
		this.curr_actual_attendance = curr_actual_attendance;
	}
}
