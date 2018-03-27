package com.edu.erp.model;


/**
 * 银行转账账户表
 * 
 */
public class TBankAccount extends BaseObject {
	
	private static final long serialVersionUID = 4593241678063422791L;
	// ID
	private Long id;
	//资金ID
	private Long fin_fee_id;
	// 银行代码
	private String bank_code;
	// 账户所有人姓名
	private String account_owner;
	// 账号
	private String account_no;
	// old_id
	private String old_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFin_fee_id() {
		return fin_fee_id;
	}
	public void setFin_fee_id(Long fin_fee_id) {
		this.fin_fee_id = fin_fee_id;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getAccount_owner() {
		return account_owner;
	}
	public void setAccount_owner(String account_owner) {
		this.account_owner = account_owner;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getOld_id() {
		return old_id;
	}
	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}


}
