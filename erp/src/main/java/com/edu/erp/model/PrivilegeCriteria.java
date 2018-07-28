package com.edu.erp.model;

/**
 * 优惠前置条件
 *
 */
public class PrivilegeCriteria extends BaseObject{
	private static final long serialVersionUID = 1L;
	
	// 名称
	private String name;
	// 描述
	private String description;
	// 优惠规则
	private Long rule_id;
	// 所属团队
	private Long bu_id;
	// 总金额
	private Long sum_price;
	// 课时数
	private Long sum_hour;
	// 积分
	private Long sum_integral;
	// 年级
	private Long grade_id;
	
	private String grade_name;
	
	private PrivilegeRule privilegeRule;
	//规则名称,用于表格显示,取值于privilegeRule
	private String rule_name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getRule_id() {
		return rule_id;
	}
	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}
	public Long getBu_id() {
		return bu_id;
	}
	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}
	public Long getSum_price() {
		return sum_price;
	}
	public void setSum_price(Long sum_price) {
		this.sum_price = sum_price;
	}
	public Long getSum_hour() {
		return sum_hour;
	}
	public void setSum_hour(Long sum_hour) {
		this.sum_hour = sum_hour;
	}
	public Long getSum_integral() {
		return sum_integral;
	}
	public void setSum_integral(Long sum_integral) {
		this.sum_integral = sum_integral;
	}
	public PrivilegeRule getPrivilegeRule() {
		return privilegeRule;
	}
	public void setPrivilegeRule(PrivilegeRule privilegeRule) {
		this.privilegeRule = privilegeRule;
	}
	public String getRule_name() {
		if(privilegeRule != null)
			return privilegeRule.getRule_name();
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public Long getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
}
