package com.edu.report.model;

/**
 * @ClassName: TAccountRechargeCash
 * @Description: 充值取现表
 *
 */
public class TAccountRechargeCash {
	
	private Long 	id;
	private String 	opt_encoding;
	private String 	calc_date;
	private Long  	opt_type;
	private String  opt_name;
	private Long  	bu_id;
	private String  bu_name;
	private Long  	branch_id;
	private String  branch_name;
	private String  zhuanru;
	private Long  	student_id;
	private String  student_encoding;
	private String  student_name ;
	private Double 	fee_amount;
	private Double 	qukuan;
	private Double 	pay_cash;
	private Double	pay_card;
	private Double 	pay_trans;
	private String  student_bankcard;
	private String 	company_bankcard;
	private Long 	status;
	private String 	status_name;
	private String 	remark; //备注
	private String 	task_flow; //任务批次，备用，存储的信息是每次执行的任务批次，方便后续查找问题
	private String  creatMaker;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpt_encoding() {
		return opt_encoding;
	}
	public void setOpt_encoding(String opt_encoding) {
		this.opt_encoding = opt_encoding;
	}
	public String getCalc_date() {
		return calc_date;
	}
	public void setCalc_date(String calc_date) {
		this.calc_date = calc_date;
	}
	public Long getOpt_type() {
		return opt_type;
	}
	public void setOpt_type(Long opt_type) {
		this.opt_type = opt_type;
	}
	public String getOpt_name() {
		return opt_name;
	}
	public void setOpt_name(String opt_name) {
		this.opt_name = opt_name;
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
	public String getZhuanru() {
		return zhuanru;
	}
	public void setZhuanru(String zhuanru) {
		this.zhuanru = zhuanru;
	}
	public Long getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}
	public String getStudent_encoding() {
		return student_encoding;
	}
	public void setStudent_encoding(String student_encoding) {
		this.student_encoding = student_encoding;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public Double getFee_amount() {
		return fee_amount;
	}
	public void setFee_amount(Double fee_amount) {
		this.fee_amount = fee_amount;
	}
	public Double getQukuan() {
		return qukuan;
	}
	public void setQukuan(Double qukuan) {
		this.qukuan = qukuan;
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
	public String getStudent_bankcard() {
		return student_bankcard;
	}
	public void setStudent_bankcard(String student_bankcard) {
		this.student_bankcard = student_bankcard;
	}
	public String getCompany_bankcard() {
		return company_bankcard;
	}
	public void setCompany_bankcard(String company_bankcard) {
		this.company_bankcard = company_bankcard;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTask_flow() {
		return task_flow;
	}
	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}
	public String getCreatMaker() {return creatMaker;}
	public void setCreatMaker(String creatMaker) {this.creatMaker = creatMaker;}
}
