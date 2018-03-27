package com.edu.erp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TOrderChange extends BaseObject {
	private static final long serialVersionUID = -6007462335475381440L;
	private Long id;// 主键
	private Long order_id;// 订单id
	private Long change_type;// 批改类型
	private Date apply_time;// 申请时间
	private Long apply_user;// 申请人员
	private Date audit_time;// 审核时间
	private Long audit_user;// 审核人
	private Date fin_confirm_time;  //财务确认时间
	private Long fin_confirm_user; 	//财务确认人员
	private Date validate_time;// 生效时间
	private Long change_status;// 批改状态
	private BigDecimal fee_amount;// 金额
	private String encoding; //编号
	private String change_no; //编号
	private Date input_time;// 录入时间
	private Long input_user;// 录入人员
	private Integer is_draw; //是否取现
	private Long branch_id;  //校区
	
	// 订单编号
	private String order_no;
	private String branch_name;
	private String bu_name;
	private String bu_id;
	private String input_user_name;
	private String student_name_id_encoding;
	private String createUserName;
	private String changeTypeName;
	private String changeStatusName;
	private String courseName;
	private String courseNo;
	private Long orderCourseId;
	private Long courseTimes;
	private Long fee_returns_amount;
	private Double fee_deduction_amount;
	private String remark;
	private String courseTimeString;
	private String apply_time_string;
	private Long total_amount;
	private Long transfer_flag;
	private String student_encoding;
	private String student_name;
	private String student_id;
	private Long invoice_status; //发票状态：1：已开票；0：未开票
	private Long invalid_id;	//作废记录ID
	
	private List<TCOrderCourse> tcOrderCourse = new ArrayList<TCOrderCourse>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getChange_type() {
		return change_type;
	}

	public void setChange_type(Long change_type) {
		this.change_type = change_type;
	}

	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}

	public Date getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}

	public Long getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(Long audit_user) {
		this.audit_user = audit_user;
	}

	public Date getValidate_time() {
		return validate_time;
	}

	public void setValidate_time(Date validate_time) {
		this.validate_time = validate_time;
	}

	public Long getChange_status() {
		return change_status;
	}

	public void setChange_status(Long change_status) {
		this.change_status = change_status;
	}

	public BigDecimal getFee_amount() {
		return fee_amount;
	}

	public Long getApply_user() {
		return apply_user;
	}

	public void setApply_user(Long apply_user) {
		this.apply_user = apply_user;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Date getInput_time() {
		return input_time;
	}

	public void setInput_time(Date input_time) {
		this.input_time = input_time;
	}

	public Long getInput_user() {
		return input_user;
	}

	public void setInput_user(Long input_user) {
		this.input_user = input_user;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getInput_user_name() {
		return input_user_name;
	}

	public void setInput_user_name(String input_user_name) {
		this.input_user_name = input_user_name;
	}

	public void setFee_amount(BigDecimal fee_amount) {
		this.fee_amount = fee_amount;
	}

	public String getStudent_name_id_encoding() {
		return student_name_id_encoding;
	}

	public void setStudent_name_id_encoding(String student_name_id_encoding) {
		this.student_name_id_encoding = student_name_id_encoding;
	}

	public Integer getIs_draw() {
		return is_draw;
	}

	public void setIs_draw(Integer is_draw) {
		this.is_draw = is_draw;
	}

	public final List<TCOrderCourse> getTcOrderCourse() {
		return tcOrderCourse;
	}

	public final void setTcOrderCourse(List<TCOrderCourse> tcOrderCourse) {
		this.tcOrderCourse = tcOrderCourse;
	}

	public final String getCreateUserName() {
		return createUserName;
	}

	public final void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public final String getChangeTypeName() {
		return changeTypeName;
	}

	public final void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	public final String getChangeStatusName() {
		return changeStatusName;
	}

	public final void setChangeStatusName(String changeStatusName) {
		this.changeStatusName = changeStatusName;
	}

	public final String getCourseName() {
		return courseName;
	}

	public final void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public final Long getOrderCourseId() {
		return orderCourseId;
	}

	public final void setOrderCourseId(Long orderCourseId) {
		this.orderCourseId = orderCourseId;
	}

	public final Long getCourseTimes() {
		return courseTimes;
	}

	public final void setCourseTimes(Long courseTimes) {
		this.courseTimes = courseTimes;
	}

	public final Long getFee_returns_amount() {
		return fee_returns_amount;
	}

	public final void setFee_returns_amount(Long fee_returns_amount) {
		this.fee_returns_amount = fee_returns_amount;
	}

	public final Double getFee_deduction_amount() {
		return fee_deduction_amount;
	}

	public final void setFee_deduction_amount(Double fee_deduction_amount) {
		this.fee_deduction_amount = fee_deduction_amount;
	}

	public final String getRemark() {
		return remark;
	}

	public final void setRemark(String remark) {
		this.remark = remark;
	}

	public final String getCourseTimeString() {
		return courseTimeString;
	}

	public final void setCourseTimeString(String courseTimeString) {
		this.courseTimeString = courseTimeString;
	}

	public final String getCourseNo() {
		return courseNo;
	}

	public final void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public final String getApply_time_string() {
		return apply_time_string;
	}

	public final void setApply_time_string(String apply_time_string) {
		this.apply_time_string = apply_time_string;
	}

	public final Long getTotal_amount() {
		return total_amount;
	}

	public final void setTotal_amount(Long total_amount) {
		this.total_amount = total_amount;
	}

	public final Long getTransfer_flag() {
		return transfer_flag;
	}

	public final void setTransfer_flag(Long transfer_flag) {
		this.transfer_flag = transfer_flag;
	}

	public final String getStudent_encoding() {
		return student_encoding;
	}

	public final void setStudent_encoding(String student_encoding) {
		this.student_encoding = student_encoding;
	}

	public final String getStudent_name() {
		return student_name;
	}

	public final void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public Long getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(Long invoice_status) {
		this.invoice_status = invoice_status;
	}

	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getBu_id() {
		return bu_id;
	}

	public void setBu_id(String bu_id) {
		this.bu_id = bu_id;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public Date getFin_confirm_time() {
		return fin_confirm_time;
	}

	public void setFin_confirm_time(Date fin_confirm_time) {
		this.fin_confirm_time = fin_confirm_time;
	}

	public Long getFin_confirm_user() {
		return fin_confirm_user;
	}

	public void setFin_confirm_user(Long fin_confirm_user) {
		this.fin_confirm_user = fin_confirm_user;
	}

	public Long getInvalid_id() {
		return invalid_id;
	}

	public void setInvalid_id(Long invalid_id) {
		this.invalid_id = invalid_id;
	}

	public String getChange_no() {
		return change_no;
	}

	public void setChange_no(String change_no) {
		this.change_no = change_no;
	}
	
}
