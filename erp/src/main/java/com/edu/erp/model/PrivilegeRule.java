package com.edu.erp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠规则
 *
 */
public class PrivilegeRule extends BaseObject {
	public static enum CouponTypeEnum {
		DISCOUNT(1, "折扣优惠"), AMOUNT(2, "优惠金额"), HOUR(3, "每课时优惠");
		private Integer code;
		private String desc;

		private CouponTypeEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	private static final long serialVersionUID = 1L;
	// 规则名称
	private String rule_name;
	// 优惠内容
	private BigDecimal coupon_content;
	// 优惠类型 1：折扣优惠 2 ：优惠金额 3：每课时优惠
	private Integer coupon_type;
	// 开始时间
	private String start_date;
	// 结束时间
	private String end_date;
	// 所属团队
	private Long bu_id;
	//产品线 1-大小班   3-晚自习
	private Integer product_line;
	//剩余课时限制
	private String course_surplus_count;

	private String branch_ids;
	
	private String branch_names;
	
	private List<PrivilegeCriteria> privilegeCriterias = new ArrayList<PrivilegeCriteria>();
	
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

	public String getCourse_surplus_count() {
		return course_surplus_count;
	}

	public void setCourse_surplus_count(String course_surplus_count) {
		this.course_surplus_count = course_surplus_count;
	}

	public Integer getProduct_line() {
		return product_line;
	}

	public void setProduct_line(Integer product_line) {
		this.product_line = product_line;
	}

	// 是否需要审核 0：不需要 1：需要
	
	private Integer is_check;
	//适用人群 1:通用 2：老学员  3:新学员
	private Integer use_scope;

	public Integer getUse_scope() {
		return use_scope;
	}

	public void setUse_scope(Integer use_scope) {
		this.use_scope = use_scope;
	}

	private List<PrivilegeSchoolRef> privilegeSchoolRefs;
	

	public Long getBu_id() {
		return bu_id;
	}


	public BigDecimal getCoupon_content() {
		return coupon_content;
	}

	public Integer getCoupon_type() {
		return coupon_type;
	}

	public String getEnd_date() {
		return end_date;
	}

	public Integer getIs_check() {
		return is_check;
	}

	public List<PrivilegeSchoolRef> getPrivilegeSchoolRefs() {
		return privilegeSchoolRefs;
	}

	public String getRule_name() {
		return rule_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public void setCoupon_content(BigDecimal coupon_content) {
		this.coupon_content = coupon_content;
	}

	public void setCoupon_type(Integer coupon_type) {
		this.coupon_type = coupon_type;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}

	public void setPrivilegeSchoolRefs(
			List<PrivilegeSchoolRef> privilegeSchoolRefs) {
		this.privilegeSchoolRefs = privilegeSchoolRefs;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public final List<PrivilegeCriteria> getPrivilegeCriteria() {
		return privilegeCriterias;
	}

	public final void setPrivilegeCriteria(
			List<PrivilegeCriteria> privilegeCriteriaList) {
		this.privilegeCriterias = privilegeCriteriaList;
	}
	
	
}
