package com.edu.erp.model;


public class TAccountDynamic extends BaseObject {
	private static final long serialVersionUID = 1L;
	private String student_info;// 学生信息
	private String encoding;// 单据编码
	private Long student_id;// 学生id
	private String student_name;// 学生姓名
	private String student_encoding;// 学生编码
	private Long bu_id;// 团队id
	private String bu_name;// 所属团队
	private Long branch_id; //校区
	private String branch_name;// 所属校区
	private String operator;// 操作人
	private Long dynamic_type;// 账户变动类型
	private String dynamic_type_name;// 账户变动类型
	private String input_time;// 业务日期
	private Double money;// 金额
	private Double money_fee;// 手续费
	private String status_name;// 动户单据状态（4表示作废）

	private Long pay_mode_id;// 收付费方式主键
	private Long pay_flag;// 收付费
	private String pay_mode;// 收付费方式
	private String remark;// 备注
	
	private String product_line;// 产品线
	private Long account_id;//账户id
	private String card_num;//账户id
	
	private String student_info_entry;//转入用户

	private String in_bu_name; // 转入团队

	private Long account_type;//账户类型

	private Long dynamic_id; //作废关联id

	public Long getBu_id() {
		return bu_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public Long getPay_mode_id() {
		return pay_mode_id;
	}

	public void setPay_mode_id(Long pay_mode_id) {
		this.pay_mode_id = pay_mode_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getStudent_info() {
		return student_info;
	}

	public void setStudent_info(String student_info) {
		this.student_info = student_info;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getStudent_encoding() {
		return student_encoding;
	}

	public void setStudent_encoding(String student_encoding) {
		this.student_encoding = student_encoding;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getDynamic_type() {
		return dynamic_type;
	}

	public void setDynamic_type(Long dynamic_type) {
		this.dynamic_type = dynamic_type;
	}

	public String getDynamic_type_name() {
		return dynamic_type_name;
	}

	public void setDynamic_type_name(String dynamic_type_name) {
		this.dynamic_type_name = dynamic_type_name;
	}

	public String getInput_time() {
		return input_time;
	}

	public void setInput_time(String input_time) {
		this.input_time = input_time;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getMoney_fee() { return money_fee; 	}

	public void setMoney_fee(Double money_fee) { this.money_fee = money_fee;	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getProduct_line() {
		return product_line;
	}

	public void setProduct_line(String product_line) {
		this.product_line = product_line;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	
	public String getStudent_info_entry() {
		return student_info_entry;
	}

	public void setStudent_info_entry(String student_info_entry) {
		this.student_info_entry = student_info_entry;
	}

	public String getIn_bu_name() {
		return in_bu_name;
	}

	public void setIn_bu_name(String in_bu_name) {
		this.in_bu_name = in_bu_name;
	}

	public Long getBranch_id() { return branch_id;	}

	public void setBranch_id(Long branch_id) { this.branch_id = branch_id;	}

	public Long getPay_flag() { return pay_flag;	}

	public void setPay_flag(Long pay_flag) { this.pay_flag = pay_flag; }

	public Long getAccount_type() { return account_type; }

	public void setAccount_type(Long account_type) { this.account_type = account_type; }

	public Long getDynamic_id() { return dynamic_id; 	}

	public void setDynamic_id(Long dynamic_id) { this.dynamic_id = dynamic_id;	}

	@Override
	public String toString() {
		return "TAccountDynamic [id=" + getId() + ", student_info=" + student_info
				+ ", encoding=" + encoding + ", student_id=" + student_id
				+ ", student_name=" + student_name + ", student_encoding="
				+ student_encoding + ", bu_id=" + bu_id + ", bu_name="
				+ bu_name + ", branch_name=" + branch_name + ", city_name="
				+ getCity_name() + ", operator=" + operator + ", dynamic_type="
				+ dynamic_type + ", dynamic_type_name=" + dynamic_type_name
				+ ", input_time=" + input_time + ", money=" + money
				+ ", status_name=" + status_name + ", pay_mode_id="
				+ pay_mode_id + ", pay_mode=" + pay_mode + ", remark=" + remark
				+ ", product_line=" + product_line + ", account_id="
				+ account_id + ", card_num=" + card_num + "]";
	}

}
