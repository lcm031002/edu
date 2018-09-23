package com.edu.erp.model;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

//资源记录日志
public class GcOrder extends BaseObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//资源记录ID
	private Long resource_rec_id;
	//订单编号
	private String order_no;
	//订单金额
	private Double fee_amount;
	//订单课时
	private Integer course_count;
	//主要内容备注
	private String remark;
	//报班时间
	private Date order_time;
	
	
	public Long getResource_rec_id() {
		return resource_rec_id;
	}
	public void setResource_rec_id(Long resource_rec_id) {
		this.resource_rec_id = resource_rec_id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	
	public Double getFee_amount() {
		return fee_amount;
	}
	public void setFee_amount(Double fee_amount) {
		this.fee_amount = fee_amount;
	}
	public Integer getCourse_count() {
		return course_count;
	}
	public void setCourse_count(Integer course_count) {
		this.course_count = course_count;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(!StringUtils.isEmpty(getOrder_no())){
			sb.append("报班单号:");
			sb.append(getOrder_no());
			sb.append(",");
		}
		if(null != getFee_amount()){
			sb.append("报班金额:");
			sb.append(getFee_amount());
			sb.append(",");
		}
		if(null != getCourse_count()){
			sb.append("报班课时:");
			sb.append(getCourse_count());
			sb.append(",");
		}
		if(null != getOrder_time()){
			sb.append("报班日期:");
			sb.append(DateFormatUtils.format(getOrder_time(), "yyyy-MM-dd"));
		}
		return sb.toString();
	}
}
