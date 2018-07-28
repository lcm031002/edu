package com.edu.erp.course_manager.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.edu.erp.model.TCourseSchedulingAssist;

/**
 * @ClassName: SchedulingAssist
 * @Description: 课程高级参数
 *
 */
public class SchedulingAssist implements Serializable{
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 8750232612642180919L;
	private Long courseId;
	private Long schedulingId;
	private List<TCourseSchedulingAssist> schedulingAssistList = new ArrayList<TCourseSchedulingAssist>();
	public final Long getCourseId() {
		return courseId;
	}
	public final void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public final List<TCourseSchedulingAssist> getSchedulingAssistList() {
		return schedulingAssistList;
	}
	public final void setSchedulingAssistList(
			List<TCourseSchedulingAssist> schedulingAssistList) {
		this.schedulingAssistList = schedulingAssistList;
	}

	public Long getSchedulingId() {
		return schedulingId;
	}

	public void setSchedulingId(Long schedulingId) {
		this.schedulingId = schedulingId;
	}
}
