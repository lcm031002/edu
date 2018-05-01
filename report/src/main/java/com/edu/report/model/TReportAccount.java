package com.edu.report.model;

import java.math.BigDecimal;

/**
 * 学员账户余额表
 * @ClassName: TReportAccount
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月2日 下午5:22:25
 *
 */
public class TReportAccount {

	private Long id;
	
	private String task_flow; //任务批次，备用，存储的信息是每次执行的任务批次，方便后续查找问题
	
	private Long bu_id; //团队id
	
	private String bu_name; //团队名称

	private Long branch_id; //校区id
	
	private String student_code; //学员编码
	
	private String student_name; //学员姓名
	
	private BigDecimal account; //储值账户
	
	private BigDecimal refund_account; //退费账户
	
	private BigDecimal frozen_account; //冻结账户

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public String getStudent_code() {
		return student_code;
	}

	public void setStudent_code(String student_code) {
		this.student_code = student_code;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getRefund_account() {
		return refund_account;
	}

	public void setRefund_account(BigDecimal refund_account) {
		this.refund_account = refund_account;
	}

	public BigDecimal getFrozen_account() {
		return frozen_account;
	}

	public void setFrozen_account(BigDecimal frozen_account) {
		this.frozen_account = frozen_account;
	}

	public String getTask_flow() {
		return task_flow;
	}

	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}

}
