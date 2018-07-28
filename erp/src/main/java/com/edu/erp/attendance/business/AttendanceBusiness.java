package com.edu.erp.attendance.business;

import com.edu.erp.model.BaseObject;

/**
 * @ClassName: AttendanceBusiness
 * @Description: 考勤对象
 *
 */
public class AttendanceBusiness extends BaseObject {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -2463416029361719072L;
	
	private Long schedulingId;
	
	private Long studentId;
	
	private String studentName;
	
	private Long lock_status;
	
	private Long teacherId;
	
	private String remark;
	
	private String order_encoding;
	
	private String courseDate;
	
	private Long attendanceId;
	
	private Long attendType;
	
	//考勤业务类型3表示晚辅导考勤
	private Long business_type;
	//订单课程id
	private Long order_course_id;
	
	public final Long getSchedulingId() {
		return schedulingId;
	}

	public final void setSchedulingId(Long schedulingId) {
		this.schedulingId = schedulingId;
	}

	public final Long getStudentId() {
		return studentId;
	}

	public final void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public final Long getLock_status() {
		return lock_status;
	}

	public final void setLock_status(Long lock_status) {
		this.lock_status = lock_status;
	}

	public final Long getTeacherId() {
		return teacherId;
	}

	public final void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public final String getRemark() {
		return remark;
	}

	public final void setRemark(String remark) {
		this.remark = remark;
	}

	public final String getOrder_encoding() {
		return order_encoding;
	}

	public final void setOrder_encoding(String order_encoding) {
		this.order_encoding = order_encoding;
	}

	public final Long getAttendanceId() {
		return attendanceId;
	}

	public final void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public final Long getAttendType() {
		return attendType;
	}

	public final void setAttendType(Long attendType) {
		this.attendType = attendType;
	}

	public final String getCourseDate() {
		return courseDate;
	}

	public final void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}

	public Long getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}

	public Long getOrder_course_id() {
		return order_course_id;
	}

	public void setOrder_course_id(Long order_course_id) {
		this.order_course_id = order_course_id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
}
