package com.edu.erp.model;

import java.util.Date;


/**
 * 优惠券
 *
 */
public class CouponRuleRel extends BaseObject{

	private static final long serialVersionUID = 1L;
	
	// 编号
	private Long encoding;
	// 优惠规则ID
	private Long rule_id;
	// 学生ID
	private String student_id;
	//经办人
	private Long operater;
	// 经办时间
	private Date operater_time;
	//经办人
	private Long auditor;
	// 经办时间
	private Date auditor_time;
    // 备注
	private String remark;
	
	private String study_ids;
	
	public Long getEncoding() {
		return encoding;
	}
	public void setEncoding(Long encoding) {
		this.encoding = encoding;
	}

	public Long getRule_id() {
		return rule_id;
	}
	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public Long getOperater() {
		return operater;
	}
	public void setOperater(Long operater) {
		this.operater = operater;
	}
	public Date getOperater_time() {
		return operater_time;
	}
	public void setOperater_time(Date operater_time) {
		this.operater_time = operater_time;
	}
	public Long getAuditor() {
		return auditor;
	}
	public void setAuditor(Long auditor) {
		this.auditor = auditor;
	}
	public Date getAuditor_time() {
		return auditor_time;
	}
	public void setAuditor_time(Date auditor_time) {
		this.auditor_time = auditor_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStudy_ids() {
		return study_ids;
	}
	public void setStudy_ids(String study_ids) {
		this.study_ids = study_ids;
	}
	
}
