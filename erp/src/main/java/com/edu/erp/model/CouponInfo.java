package com.edu.erp.model;


/**
 * 优惠券
 *
 */
public class CouponInfo extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 编号
	private String encoding;
	// 名称
	private String name;
	// 优惠规则ID
	private Long rule_id;
	// 有效期
	private String valid_date;
	// 所属团队
	private Long bu_id;
	// 所属校区
	private Long branch_id;
	// 用于显示
	// 规则名称
	private String rule_name;
	// 地区名称
	private String city_name;
	// 校区名称
	private String branch_name;
	// 团队名称
	private String bu_name;
    //	  优惠券前缀
	private String coupon_prefix;
	// 活动ID
	private Long activity_id;
    // 活动名称
	private String activity_name;
	
	private Integer sort_num;
	
	private String branch_ids;
	
	private String branch_names;
	
	private String couponCount;
	
	private PrivilegeRule privilegeRule = new PrivilegeRule();
	
	public final PrivilegeRule getPrivilegeRule() {
		return privilegeRule;
	}
	public final void setPrivilegeRule(PrivilegeRule privilegeRule) {
		this.privilegeRule = privilegeRule;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRule_id() {
		return rule_id;
	}
	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}
	public String getValid_date() {
		return valid_date;
	}
	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public Long getBu_id() {
		return bu_id;
	}
	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBu_name() {
		return bu_name;
	}
	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getCoupon_prefix() {
		return coupon_prefix;
	}
	public void setCoupon_prefix(String coupon_prefix) {
		this.coupon_prefix = coupon_prefix;
	}
	public Long getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Long activity_id) {
		this.activity_id = activity_id;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public Integer getSort_num() {
		return sort_num;
	}
	public void setSort_num(Integer sort_num) {
		this.sort_num = sort_num;
	}
	public String getBranch_ids() {
		return branch_ids;
	}
	public void setBranch_ids(String branch_ids) {
		this.branch_ids = branch_ids;
	}
	public String getBranch_names() {
		return branch_names;
	}
	public void setBranch_names(String branch_names) {
		this.branch_names = branch_names;
	}
	public String getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(String couponCount) {
		this.couponCount = couponCount;
	}

	
}
