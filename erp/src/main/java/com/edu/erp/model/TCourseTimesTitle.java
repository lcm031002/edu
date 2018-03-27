/**  
 * @Title: TOrderCourseTimes.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月19日 下午7:24:20
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;


/**
 * 课次标题信息
 * @author yecb
 *
 */
public class TCourseTimesTitle extends BaseObject {
	
	private static final long serialVersionUID = -3893077962028614112L;

	
	private Long course_id;  // 订单课程ID
  		
    private Long course_times;  // 课次
    
    private String title;//课次标题

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Long getCourse_times() {
		return course_times;
	}

	public void setCourse_times(Long course_times) {
		this.course_times = course_times;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
 
    
}
