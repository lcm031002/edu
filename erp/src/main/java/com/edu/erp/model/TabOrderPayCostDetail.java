package com.edu.erp.model;

/**
 * Description ： 缴费单据详情
 *
 */
public class TabOrderPayCostDetail extends BaseObject {

	private static final long serialVersionUID = 433702174224982430L;
	private Long id; // 主键
	private Long order_buy_id; // 缴费单号
	private Long payment_way;
	private String payment_way_str;// 缴费方式 1：现金 2：刷卡 3：转账 4：储值账户 5：一元课程 6：支付宝
									// 7：微信 8：网银，9：冻结账户 10：电商在线支付 11. WEB在线支付
	private String create_time_string;// 创建时间
	private String createusername;// 创建人
	private String org_name;// 组织机构
	private String simple_name; // 设备名称
	private String device_code;// 设备编码
	private Long staffappprem; // 缴费金额
	private String client_card_no; // 客户卡号
	private String eb_no; // 商户订单号
	private String client_name; // 持卡人姓名
	private Long company_card_id; // 公司卡号
	private Long pos_id; // POS机编号
	private String createTime;// 该字段为前后台传输数据用
	private String account_name;// 账户名字
	private String extend_column; // 扩展字段
	private String extend_column2; // 扩展字段2

	private Long extend_column3; // 扩展字段3
	private Long extend_column4; // 扩展字段4

	public String getAccount_name() {
		return account_name;
	}

	public String getClient_card_no() {
		return client_card_no;
	}

	public String getClient_name() {
		return client_name;
	}

	public Long getCompany_card_id() {
		return company_card_id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getExtend_column() {
		return extend_column;
	}

	public String getExtend_column2() {
		return extend_column2;
	}

	public Long getExtend_column3() {
		return extend_column3;
	}

	public Long getExtend_column4() {
		return extend_column4;
	}

	public Long getId() {
		return id;
	}

	public Long getOrder_buy_id() {
		return order_buy_id;
	}

	public Long getPayment_way() {
		return payment_way;
	}

	public Long getPos_id() {
		return pos_id;
	}

	public Long getStaffappprem() {
		return staffappprem;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public void setClient_card_no(String client_card_no) {
		this.client_card_no = client_card_no;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public void setCompany_card_id(Long company_card_id) {
		this.company_card_id = company_card_id;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrder_buy_id(Long order_buy_id) {
		this.order_buy_id = order_buy_id;
	}

	public void setPayment_way(Long payment_way) {
		this.payment_way = payment_way;
	}

	public void setPos_id(Long pos_id) {
		this.pos_id = pos_id;
	}

	public void setStaffappprem(Long staffappprem) {
		this.staffappprem = staffappprem;
	}

	public final String getPayment_way_str() {
		return payment_way_str;
	}

	public final void setPayment_way_str(String payment_way_str) {
		this.payment_way_str = payment_way_str;
	}

	public final String getCreate_time_string() {
		return create_time_string;
	}

	public final void setCreate_time_string(String create_time_string) {
		this.create_time_string = create_time_string;
	}

	public final String getCreateusername() {
		return createusername;
	}

	public final void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	public final String getOrg_name() {
		return org_name;
	}

	public final void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

//	public final String getDevice_name() {
//		return device_name;
//	}
//
//	public final void setDevice_name(String device_name) {
//		this.device_name = device_name;
//	}

	public final String getSimple_name() {
		return simple_name;
	}

	public final void setSimple_name(String simple_name) {
		this.simple_name = simple_name;
	}

	public final String getDevice_code() {
		return device_code;
	}

	public final void setDevice_code(String device_code) {
		this.device_code = device_code;
	}

	public String getEb_no() {
		return eb_no;
	}

	public void setEb_no(String eb_no) {
		this.eb_no = eb_no;
	}
	
	
}
