package com.edu.erp.model;

import java.util.ArrayList;
import java.util.List;

/***
 * Description : POJO
 *
 */
public class TabOrderInfo extends BaseObject {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -6254707450691217612L;

	private Long v3_mark;

	private String saveType;

	private String old_id; // V3中的主键

	private Double immediate_reduce; // 立减金额

	private Long is_course_pack;// 是否联报课程

	private String encoding; // 订单编码

	private Long student_id; // 学生编号

	private String student_name;// 学生姓名

	private String student_encoding;// 学员编码

	private Long business_type; // 业务类型

	private Long start_status; // 0：未确认 1：已确认

	private Long sum_price; // 总金额

	private Long actual_price; // 实际付款金额

	private Long order_type; // 1：原单 2：赠单 3：转班单 4:续单

	private Long source_order; // 如果是赠单 此处指主报班单 如果是转班单 此处指转出报班单

	private Long return_premium_status; // 1：退费 0：未退费

	private Long is_extend_order; // 1：续单 0：原单

	private Long branch_id; // 业务校区编号

	private String branch_name;// 业务校区名称

	private String employee_name;// 制单人、经办人

	private Long bu_id; // 团队编号

	private String bu_name;// 团队

	private Long city_id; // 地区编号

	private Long rule_id; // 规则编号

	private Integer approved;

	private String approver_name;

	private String invoice_head; // 发票抬头

	private String invoice_code; // 发票税务局登记号

	private Long invoice_type; // 发票类别 1：个人 2：公司

	private Long invoice_money; // 发票金额

	private String invoice_date; // 发票日期2014-12-3

	private String invoice_description; // 发票描述

	private Long valid_status; // 1：有效 0：无效

	private Long check_status; // 1：未审核 2：审核中 3：已通过 4 ：未通过

	private Long last_approver; // 最后审批人编号

	private String approve_time; // 审批实际

	private String remark; // 备注

	private String extend_column;

	private String extend_column2;

	private Long extend_column3;

	private Long extend_column4;

	private Long pay_status;

	private Long lock_status;

	private Long invoice_status;
	
	private Long order_status;
	
	private Long on_line;

	private String billNo;

	private String qrCodeId;

	private String onlinepay_date;
	
	/**
	 * 资源信息记录表ID
	 */
	private Long resource_rec_id;

	private List<OrderCouponRel> coupon_rels = new ArrayList<OrderCouponRel>();
	private List<TabOrderInfoDetail> details = new ArrayList<TabOrderInfoDetail>();
	private TabOrderPayCost payment = new TabOrderPayCost();
	private StudentInfo studentInfo = new StudentInfo();
	private PrivilegeRule privilegeRule = new PrivilegeRule();

	public Long getActual_price() {
		return actual_price;
	}

	public String getApprove_time() {
		return approve_time;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public Long getBu_id() {
		return bu_id;
	}

	public Long getBusiness_type() {
		return business_type;
	}

	public Long getCheck_status() {
		return check_status;
	}

	public final Long getInvoice_status() {
		return invoice_status;
	}

	public final void setInvoice_status(Long invoice_status) {
		this.invoice_status = invoice_status;
	}

	public Long getCity_id() {
		return city_id;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getExtend_column() {
		return extend_column;
	}

	public String getExtend_column2() {
		return extend_column2;
	}

	public final Long getLock_status() {
		return lock_status;
	}

	public final void setLock_status(Long lock_status) {
		this.lock_status = lock_status;
	}

	public final String getStudent_name() {
		return student_name;
	}

	public final void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public Long getExtend_column3() {
		return extend_column3;
	}

	public Long getExtend_column4() {
		return extend_column4;
	}

	public Double getImmediate_reduce() {
		return immediate_reduce;
	}

	public String getInvoice_code() {
		return invoice_code;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public String getInvoice_description() {
		return invoice_description;
	}

	public String getInvoice_head() {
		return invoice_head;
	}

	public Long getInvoice_money() {
		return invoice_money;
	}

	public Long getInvoice_type() {
		return invoice_type;
	}

	public Long getIs_course_pack() {
		return is_course_pack;
	}

	public Long getIs_extend_order() {
		return is_extend_order;
	}

	public Long getLast_approver() {
		return last_approver;
	}

	public String getOld_id() {
		return old_id;
	}

	public Long getOrder_type() {
		return order_type;
	}

	public Long getPay_status() {
		return pay_status;
	}

	public String getRemark() {
		return remark;
	}

	public Long getReturn_premium_status() {
		return return_premium_status;
	}

	public Long getRule_id() {
		return rule_id;
	}

	public Long getSource_order() {
		return source_order;
	}

	public Long getStart_status() {
		return start_status;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public Long getSum_price() {
		return sum_price;
	}

	public Long getV3_mark() {
		return v3_mark;
	}

	public Long getValid_status() {
		return valid_status;
	}

	public void setActual_price(Long actual_price) {
		this.actual_price = actual_price;
	}

	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}

	public void setCheck_status(Long check_status) {
		this.check_status = check_status;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setExtend_column(String extend_column) {
		this.extend_column = extend_column;
	}

	public void setExtend_column2(String extend_column2) {
		this.extend_column2 = extend_column2;
	}

	public void setExtend_column3(Long extend_column3) {
		this.extend_column3 = extend_column3;
	}

	public void setExtend_column4(Long extend_column4) {
		this.extend_column4 = extend_column4;
	}

	public void setImmediate_reduce(Double immediate_reduce) {
		this.immediate_reduce = immediate_reduce;
	}

	public void setInvoice_code(String invoice_code) {
		this.invoice_code = invoice_code;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public void setInvoice_description(String invoice_description) {
		this.invoice_description = invoice_description;
	}

	public void setInvoice_head(String invoice_head) {
		this.invoice_head = invoice_head;
	}

	public void setInvoice_money(Long invoice_money) {
		this.invoice_money = invoice_money;
	}

	public void setInvoice_type(Long invoice_type) {
		this.invoice_type = invoice_type;
	}

	public void setIs_course_pack(Long is_course_pack) {
		this.is_course_pack = is_course_pack;
	}

	public void setIs_extend_order(Long is_extend_order) {
		this.is_extend_order = is_extend_order;
	}

	public void setLast_approver(Long last_approver) {
		this.last_approver = last_approver;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}

	public void setOrder_type(Long order_type) {
		this.order_type = order_type;
	}

	public void setPay_status(Long pay_status) {
		this.pay_status = pay_status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setReturn_premium_status(Long return_premium_status) {
		this.return_premium_status = return_premium_status;
	}

	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}

	public void setSource_order(Long source_order) {
		this.source_order = source_order;
	}

	public void setStart_status(Long start_status) {
		this.start_status = start_status;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public void setSum_price(Long sum_price) {
		this.sum_price = sum_price;
	}

	public void setV3_mark(Long v3_mark) {
		this.v3_mark = v3_mark;
	}

	public void setValid_status(Long valid_status) {
		this.valid_status = valid_status;
	}

	public Integer getApproved() {
		return approved;
	}

	public void setApproved(Integer approved) {
		this.approved = approved;
	}

	public final List<OrderCouponRel> getCoupon_rels() {
		return coupon_rels;
	}

	public final void setCoupon_rels(List<OrderCouponRel> coupon_rels) {
		this.coupon_rels = coupon_rels;
	}

	public final List<TabOrderInfoDetail> getDetails() {
		return details;
	}

	public final void setDetails(List<TabOrderInfoDetail> details) {
		this.details = details;
	}

	public final TabOrderPayCost getPayment() {
		return payment;
	}

	public final void setPayment(TabOrderPayCost payment) {
		this.payment = payment;
	}

	public final String getSaveType() {
		return saveType;
	}

	public final void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public final StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public final void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public final PrivilegeRule getPrivilegeRule() {
		return privilegeRule;
	}

	public final void setPrivilegeRule(PrivilegeRule privilegeRule) {
		this.privilegeRule = privilegeRule;
	}

	public final String getStudent_encoding() {
		return student_encoding;
	}

	public final void setStudent_encoding(String student_encoding) {
		this.student_encoding = student_encoding;
	}

	public final String getBranch_name() {
		return branch_name;
	}

	public final void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public final String getEmployee_name() {
		return employee_name;
	}

	public final void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public final String getBu_name() {
		return bu_name;
	}

	public final void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public final String getApprover_name() {
		return approver_name;
	}

	public final void setApprover_name(String approver_name) {
		this.approver_name = approver_name;
	}

    public Long getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Long order_status) {
        this.order_status = order_status;
    }

	public Long getOn_line() {
		return on_line;
	}

	public void setOn_line(Long on_line) {
		this.on_line = on_line;
	}
	public Long getResource_rec_id() {
		return resource_rec_id;
	}

	public void setResource_rec_id(Long resource_rec_id) {
		this.resource_rec_id = resource_rec_id;
	}

	public String getQrCodeId() {
		return qrCodeId;
	}

	public void setQrCodeId(String qrCodeId) {
		this.qrCodeId = qrCodeId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getOnlinepay_date() {
		return onlinepay_date;
	}

	public void setOnlinepay_date(String onlinepay_date) {
		this.onlinepay_date = onlinepay_date;
	}
}