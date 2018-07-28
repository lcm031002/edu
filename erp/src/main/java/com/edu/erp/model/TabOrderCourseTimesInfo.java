package com.edu.erp.model;


/**
 * @ClassName: TabOrderCourseTimesInfo
 * @Description: 班级课选课表
 * 
 */
public class TabOrderCourseTimesInfo extends BaseObject {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 6692263028512382115L;
	private Long student_id;
	private Long order_id;
	private Long order_detail_id;
	private Long course_times;

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(Long order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public Long getCourse_times() {
		return course_times;
	}

	public void setCourse_times(Long course_times) {
		this.course_times = course_times;
	}

}
