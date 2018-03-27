/**  
 * @Title: StudentIntegralDetails.java
 * @Package com.pojo
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年3月24日 下午2:08:20
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

import java.util.Date;

/**
 * @ClassName: StudentIntegralDetails
 * @Description: 学生积分详情
 * @author zhuliyong zly@entstudy.com
 * @date 2015年3月24日 下午2:08:20
 * 
 */
public class StudentIntegralDetails extends BaseObject {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -5436175699149358219L;

	private Date datetime;

	private Long account_id;

	private Long change_integral;

	private Long crrent_integral;

	private Long after_integral;

	private Long change_type;

	private String change_val;
	
	private Long attend_amount;
	
	private Long last_attend_amount;
	
	private Long change_amount;
	
	private Long branch_id;
	
	/******非DB字段********/
	private String branch_name; //校区名称
	
	public Long getAccount_id() {
		return account_id;
	}

	public Long getAfter_integral() {
		return after_integral;
	}

	public Long getChange_integral() {
		return change_integral;
	}

	public Long getChange_type() {
		return change_type;
	}

	public String getChange_val() {
		return change_val;
	}

	public Long getCrrent_integral() {
		return crrent_integral;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}

	public void setAfter_integral(Long after_integral) {
		this.after_integral = after_integral;
	}

	public void setChange_integral(Long change_integral) {
		this.change_integral = change_integral;
	}

	public void setChange_type(Long change_type) {
		this.change_type = change_type;
	}

	public void setChange_val(String change_val) {
		this.change_val = change_val;
	}

	public void setCrrent_integral(Long crrent_integral) {
		this.crrent_integral = crrent_integral;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Long getAttend_amount() {
		return attend_amount;
	}

	public void setAttend_amount(Long attend_amount) {
		this.attend_amount = attend_amount;
	}

	public Long getLast_attend_amount() {
		return last_attend_amount;
	}

	public void setLast_attend_amount(Long last_attend_amount) {
		this.last_attend_amount = last_attend_amount;
	}

	public Long getChange_amount() {
		return change_amount;
	}

	public void setChange_amount(Long change_amount) {
		this.change_amount = change_amount;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

}
