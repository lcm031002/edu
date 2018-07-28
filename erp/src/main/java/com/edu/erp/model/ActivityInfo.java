package com.edu.erp.model;


/**
 * 活动信息表
 *
 */
public class ActivityInfo extends BaseObject{
	private static final long serialVersionUID = 1L;
	
	// 活动名称
	private String activity_name;
	// 活动日期
	private String activity_date;
	// 活动人数
	private Long people_count;
	// 优惠规则
	private Long rule_id;
	// 所属团队
	private Long bu_id;
	// 所属校区
	private Long branch_id;
	// 地区
	private Long city_id;
	
	// 地区名称
	private String city_name;
	// 团队名称
	private String bu_name;
	// 校区名称
	private String branch_name;
	// 优惠规则名称
	private String rule_name;
//	优惠券前缀
	private String coupon_prefix;
	
	private String branch_ids;
	
	private String branch_names;
	
	private Integer is_pub;
	
	public String getCoupon_prefix() {
		return coupon_prefix;
	}
	public void setCoupon_prefix(String coupon_prefix) {
		this.coupon_prefix = coupon_prefix;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getActivity_date() {
		return activity_date;
	}
	public void setActivity_date(String activity_date) {
		this.activity_date = activity_date;
	}
	public Long getPeople_count() {
		return people_count;
	}
	public void setPeople_count(Long people_count) {
		this.people_count = people_count;
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
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public Long getCity_id() {
		return city_id;
	}
	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getBu_name() {
		return bu_name;
	}
	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
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
	public Integer getIs_pub() {
		return is_pub;
	}
	public void setIs_pub(Integer is_pub) {
		this.is_pub = is_pub;
	}
}
