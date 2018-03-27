package com.edu.erp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单主表
 * 
 */
public class TOrder extends BaseObject {
	private static final long serialVersionUID = 6296927407699537100L;

	// 订单编号
	private String order_no;
	// 学生
	private Long student_id;
	// 地区
	private Long city_id;
	// 团队
	private Long bu_id;
	// 校区
	private Long branch_id;
	// 销售人员
	private Long agent_id;
	// 业务模式 1：班级课 2：一对一 3：晚辅导'
	private Long business_type;
	// 折扣前总金额
	private Double fee_amount;
	// 折扣后总金额
	private Double actural_amount;
	// 订单审批时间
	private Date audit_date;
	// 订单审批用户
	private Long audit_user;
	private String audit_name;
	// 财务确认时间
	private Date fin_confirm_date;
	// 财务确认用户
	private Long fin_confirm_user;
	// 订单类型 1：原单 2：赠单 3：转班单 4： 续单';
	private Long order_type;
	// 备注
	private String remark;
	// 优惠立减
	private Double immediate_reduce;
	// 订单状态
	private Long order_status;
	// 来源订单ID
	private Long source_order;
	// 订单创建时间
	private Date create_date;
	private String old_id;
	private String invoice_code;
	//新增发票状态 by yecb 2017-03-11
	private Long invoice_status;
	
	private String branch_name;
	private String bu_name;
	private String city_name;
	private String student_name;
	private String agent_name;
	private String student_name_id_encoding;
	private Long resource_rec_id;//线索资源Id

	private TabOrderInfo temporaryOrder;
	private StudentInfo studentInfo;
	private TOrderLock tOrderLock;

	private List<Map<String, Object>> forwardList = new ArrayList<Map<String, Object>>();

	private List<Map<String, Object>> premiumAuditList = new ArrayList<Map<String, Object>>();

	private List<TOrderCourse> orderDetails = new ArrayList<TOrderCourse>();

	private List<Map<String, Object>> unForwardList = new ArrayList<Map<String, Object>>();

	public static enum OrderTypeEnum {
		one(1, "原单"), twp(2, "赠单"), three(3, "转班单"), four(4, "续单");
		private Integer code;
		private String desc;

		private OrderTypeEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
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

	public Long getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}

	public Long getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}

	public Double getFee_amount() {
		return fee_amount;
	}

	public void setFee_amount(Double fee_amount) {
		this.fee_amount = fee_amount;
	}

	public Double getActural_amount() {
		return actural_amount;
	}

	public void setActural_amount(Double actural_amount) {
		this.actural_amount = actural_amount;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public Long getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(Long audit_user) {
		this.audit_user = audit_user;
	}

	public Date getFin_confirm_date() {
		return fin_confirm_date;
	}

	public void setFin_confirm_date(Date fin_confirm_date) {
		this.fin_confirm_date = fin_confirm_date;
	}

	public Long getFin_confirm_user() {
		return fin_confirm_user;
	}

	public void setFin_confirm_user(Long fin_confirm_user) {
		this.fin_confirm_user = fin_confirm_user;
	}

	public Long getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Long order_type) {
		this.order_type = order_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getImmediate_reduce() {
		return immediate_reduce;
	}

	public void setImmediate_reduce(Double immediate_reduce) {
		this.immediate_reduce = immediate_reduce;
	}

	public Long getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Long order_status) {
		this.order_status = order_status;
	}

	public Long getSource_order() {
		return source_order;
	}

	public void setSource_order(Long source_order) {
		this.source_order = source_order;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
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

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getStudent_name_id_encoding() {
		return student_name_id_encoding;
	}

	public void setStudent_name_id_encoding(String student_name_id_encoding) {
		this.student_name_id_encoding = student_name_id_encoding;
	}

	public final List<TOrderCourse> getOrderDetails() {
		return orderDetails;
	}

	public final void setOrderDetails(List<TOrderCourse> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public final TabOrderInfo getTemporaryOrder() {
		return temporaryOrder;
	}

	public final void setTemporaryOrder(TabOrderInfo temporaryOrder) {
		this.temporaryOrder = temporaryOrder;
	}

	public final StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public final void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public final String getAudit_name() {
		return audit_name;
	}

	public final void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}

	public final TOrderLock gettOrderLock() {
		return tOrderLock;
	}

	public final void settOrderLock(TOrderLock tOrderLock) {
		this.tOrderLock = tOrderLock;
	}

	public final List<Map<String, Object>> getPremiumAuditList() {
		return premiumAuditList;
	}

	public final void setPremiumAuditList(
			List<Map<String, Object>> premiumAuditList) {
		this.premiumAuditList = premiumAuditList;
	}

	public final List<Map<String, Object>> getUnForwardList() {
		return unForwardList;
	}

	public final void setUnForwardList(List<Map<String, Object>> unForwardList) {
		this.unForwardList = unForwardList;
	}

	public final List<Map<String, Object>> getForwardList() {
		return forwardList;
	}

	public final void setForwardList(List<Map<String, Object>> forwardList) {
		this.forwardList = forwardList;
	}

	public Long getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(Long invoice_status) {
		this.invoice_status = invoice_status;
	}

	public String getOld_id() {
		return old_id;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}

	public String getInvoice_code() {
		return invoice_code;
	}

	public void setInvoice_code(String invoice_code) {
		this.invoice_code = invoice_code;
	}

	public Long getResource_rec_id() {
		return resource_rec_id;
	}

	public void setResource_rec_id(Long resource_rec_id) {
		this.resource_rec_id = resource_rec_id;
	}

}
