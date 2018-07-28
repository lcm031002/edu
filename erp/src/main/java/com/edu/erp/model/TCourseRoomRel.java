package com.edu.erp.model;

/**
 * 课程教室关系表
 * @ClassName: TCourseRoomRel
 * @Description: 
 *
 */
public class TCourseRoomRel extends BaseObject{

	private static final long serialVersionUID = 4556484213499153265L;

	private Long course_id;  // 课程ID
    
    private Long seq;  // 课次序号   <- 课程排课表-课次
    
    private Long room_id;  // 教室Id
    
    /** 非DB字段 */
    private String room_code;  //教室编码
    
    private String room_name;  //教室名称
    
    private Long course_scheduling_id;  //课程排课表-Id
    
    private String course_code;  //课程编码
    
    private String course_name;  //课程名称
    
    private Long branch_id;  //校区ID
    
    private String branch_name;  //校区名称
    
    private String teacher_name;  //教师名称
    
    private String grade_name;  //年级名称
    
    private String subject_name;  //科目名称
    
    private Long course_date;    //课程排课表-上课日期
    
    private String start_time;    //课程排课表-上课时间 HH:MM:SS
    
    private String end_time;    //课程排课表-下课时间 HH:MM:SS
    
    private String remark;      //课程排课表-备注
    
    private Boolean isApplyToOther;  //应用到本课程所有未绑定的课次

	private String weekday;//根据排课时间计算出星期几，配合前端展示

	private String videoNo; // 录制任务视频编号

	public TCourseRoomRel() {
	}
	public TCourseRoomRel(TCourseRoomRel tCourseRoomRel) {
		this.setCourse_id(tCourseRoomRel.getCourse_id());
		this.setSeq(Long.valueOf(tCourseRoomRel.getSeq()));
		this.setRoom_id(tCourseRoomRel.getRoom_id());
		this.setBranch_id(tCourseRoomRel.getBranch_id());
		this.setCourse_date(tCourseRoomRel.getCourse_date());
		this.setStart_time(tCourseRoomRel.getStart_time());
		this.setEnd_time(tCourseRoomRel.getEnd_time());
	}


	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getRoom_id() {
		return room_id;
	}

	public void setRoom_id(Long room_id) {
		this.room_id = room_id;
	}

	public String getRoom_code() {
		return room_code;
	}

	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public Long getCourse_scheduling_id() {
		return course_scheduling_id;
	}

	public void setCourse_scheduling_id(Long course_scheduling_id) {
		this.course_scheduling_id = course_scheduling_id;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
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

	public Long getCourse_date() {
		return course_date;
	}

	public void setCourse_date(Long course_date) {
		this.course_date = course_date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public Boolean isApplyToOther() {
		return isApplyToOther;
	}

	public void setApplyToOther(Boolean isApplyToOther) {
		this.isApplyToOther = isApplyToOther;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getVideoNo() {
		return videoNo;
	}

	public void setVideoNo(String videoNo) {
		this.videoNo = videoNo;
	}
}
