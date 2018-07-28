package com.edu.erp.model;


/**
 * 优惠规则课程商品权限
 *
 */
public class PrivilegeCourseRef extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private Long course_id;
	private Long rule_id;
	
	public Long getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
	public Long getRule_id() {
		return rule_id;
	}
	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}

}
