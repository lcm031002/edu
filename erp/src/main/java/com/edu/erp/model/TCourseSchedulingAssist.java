/**  
 * @Title: TCourseSchedulingAssist.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月16日 下午9:31:08
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

import java.io.Serializable;

/**
 * @ClassName: TCourseSchedulingAssist
 * @Description: 排课参数配置
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月16日 下午9:31:08
 * 
 */
public class TCourseSchedulingAssist implements Serializable{
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 9144751879522533245L;
	private Long id;
	private Long courseId;
	private Long schedulingId;
	private String courseName;
	private String courseVal;
	private String courseKey;
	private Long courseCfgScope;
	private Long courseTime;
	private Double extandVal1;
	private Double extandVal2;
	private String extandVal3;
	private String extandVal4;
	private String remark;
	private String courseValName;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getCourseId() {
		return courseId;
	}

	public final void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public final String getCourseName() {
		return courseName;
	}

	public final void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public final String getCourseKey() {
		return courseKey;
	}

	public final void setCourseKey(String courseKey) {
		this.courseKey = courseKey;
	}

	public final String getCourseVal() {
		return courseVal;
	}

	public final void setCourseVal(String courseVal) {
		this.courseVal = courseVal;
	}

	public final Long getCourseCfgScope() {
		return courseCfgScope;
	}

	public final void setCourseCfgScope(Long courseCfgScope) {
		this.courseCfgScope = courseCfgScope;
	}

	public final Long getCourseTime() {
		return courseTime;
	}

	public final void setCourseTime(Long courseTime) {
		this.courseTime = courseTime;
	}

	public Double getExtandVal1() {
		return extandVal1;
	}

	public void setExtandVal1(Double extandVal1) {
		this.extandVal1 = extandVal1;
	}

	public Double getExtandVal2() {
		return extandVal2;
	}

	public void setExtandVal2(Double extandVal2) {
		this.extandVal2 = extandVal2;
	}

	public final String getExtandVal3() {
		return extandVal3;
	}

	public final void setExtandVal3(String extandVal3) {
		this.extandVal3 = extandVal3;
	}

	public final String getExtandVal4() {
		return extandVal4;
	}

	public final void setExtandVal4(String extandVal4) {
		this.extandVal4 = extandVal4;
	}

	public final String getRemark() {
		return remark;
	}

	public final void setRemark(String remark) {
		this.remark = remark;
	}

	public final String getCourseValName() {
		return courseValName;
	}

	public final void setCourseValName(String courseValName) {
		this.courseValName = courseValName;
	}

	public Long getSchedulingId() {
		return schedulingId;
	}

	public void setSchedulingId(Long schedulingId) {
		this.schedulingId = schedulingId;
	}


}
