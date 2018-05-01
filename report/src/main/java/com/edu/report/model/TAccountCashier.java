/**  
 * @Title: TAccountCashier.java
 * @Package com.edu.report.model
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午4:42:40
 * @version KLXX ERPV4.0  
 */
package com.edu.report.model;

/**
 * @ClassName: TAccountCashier
 * @Description: 充值取现表
 * @author ohs@klxuexi.org
 * @date 2017年5月8日 下午4:42:40
 *
 */
public class TAccountCashier {
	
	private Long 	id;

	private Long 	city_id;
	private String  city_name;
	private Long 	bu_id;
	private String bu_name;
	private Long 	branch_id;
	private String  branch_name;
	private Long 	opt_type;
	private String opt_type_name;
	private Long 	 opt_id;
	private String  opt_encoding;
	private Long 	 student_id;
	private String student_name;
	private String student_encoding;
	private Double 	 pay_amount;
	private Double 	 pay_cash;
	private Double 	 pay_card;
	private Double 	 pay_trans;
	private Double  pay_ebprice;
	private Double  pay_onlineprice;
	private Double 	 pay_account;
	private Long 	 input_user;
	private String input_user_name;
	private String input_time;
	private String  card_no;
	private String company_names;
	private String company_account;
	private String remark;
	private String  status;
	private Long 	order_detail_id;
	private String eb_no;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCity_id() {
		return city_id;
	}
	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public Long getBu_id() {
		return bu_id;
	}
	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}
	public String getBu_name() {
		return bu_name;
	}
	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public Long getOpt_type() {
		return opt_type;
	}
	public void setOpt_type(Long opt_type) {
		this.opt_type = opt_type;
	}
	public String getOpt_type_name() {
		return opt_type_name;
	}
	public void setOpt_type_name(String opt_type_name) {
		this.opt_type_name = opt_type_name;
	}
	public Long getOpt_id() {
		return opt_id;
	}
	public void setOpt_id(Long opt_id) {
		this.opt_id = opt_id;
	}
	public String getOpt_encoding() {
		return opt_encoding;
	}
	public void setOpt_encoding(String opt_encoding) {
		this.opt_encoding = opt_encoding;
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
	public Double getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(Double pay_amount) {
		this.pay_amount = pay_amount;
	}
	public Double getPay_cash() {
		return pay_cash;
	}
	public void setPay_cash(Double pay_cash) {
		this.pay_cash = pay_cash;
	}
	public Double getPay_card() {
		return pay_card;
	}
	public void setPay_card(Double pay_card) {
		this.pay_card = pay_card;
	}
	public Double getPay_trans() {
		return pay_trans;
	}
	public void setPay_trans(Double pay_trans) {
		this.pay_trans = pay_trans;
	}
	public Double getPay_account() {
		return pay_account;
	}
	public void setPay_account(Double pay_account) {
		this.pay_account = pay_account;
	}
	public Long getInput_user() {
		return input_user;
	}
	public void setInput_user(Long input_user) {
		this.input_user = input_user;
	}
	public String getInput_user_name() {
		return input_user_name;
	}
	public void setInput_user_name(String input_user_name) {
		this.input_user_name = input_user_name;
	}
	public String getInput_time() {
		return input_time;
	}
	public void setInput_time(String input_time) {
		this.input_time = input_time;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCompany_names() {
		return company_names;
	}
	public void setCompany_names(String company_names) {
		this.company_names = company_names;
	}
	public String getCompany_account() {
		return company_account;
	}
	public void setCompany_account(String company_account) {
		this.company_account = company_account;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(Long order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public Double getPay_ebprice() {
		return pay_ebprice;
	}
	public void setPay_ebprice(Double pay_ebprice) {
		this.pay_ebprice = pay_ebprice;
	}

	public Double getPay_onlineprice() {
		return pay_onlineprice;
	}

	public void setPay_onlineprice(Double pay_onlineprice) {
		this.pay_onlineprice = pay_onlineprice;
	}

	public String getEb_no() {
		return eb_no;
	}

	public void setEb_no(String eb_no) {
		this.eb_no = eb_no;
	}
}
