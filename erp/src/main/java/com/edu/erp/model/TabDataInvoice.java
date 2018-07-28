package com.edu.erp.model;

import java.util.Date;

/**
 * @ClassName: TabDataInvoice
 * @Description: 发票模型
 *
 */
public class TabDataInvoice extends BaseObject {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 8179137871958287262L;

	private String encoding; // 发票编号

	private Long orderId; // 订单编号

	private Long studentId; // 学生编号

	private Long branchId; // 校区编号

	private String heading; // 发票抬头编号

	private String companyName; // 开票公司名称

	private String taxNum; // 税务发票号

	private String invoiceExplain; // 开票需求说明

	private Long requiredMoney; // 申开金额

	private Long actualMoney; // 实开金额

	private String invoiceCode; // 发票代码

	private Long invoiceCompamy; // 发票单位编号

	private String invoiceCompamyName; // 发票单位名称

	private String description; // 描述

	private String oldId;

	private Date applyDate; // 申请日期

	private Long applyUser; // 申请人编号

	private String applyUserName; // 申请人名称

	private Date invoiceDate; // 开票日期

	private Long invoiceUser; // 开票人编号

	private String invoiceUserName; // 开票人名称
	
	private Long fees;

	private String orderEncoding; // 报班单号

	private Long orderMoney; // 订单金额

	private Long money; // 已开金额

	private Long premiumAmount;

	private String studentEncoding; // 学生单号

	private String studentName; // 学生名称

	private String branchName; // 校区名称

	private String statusName; // 状态名称

	private String headingName; // 发票抬头名称

	private Integer receiveStatus; // 发票领取状态

	private String receiveStatusName; // 发票领取状态名称

	public static enum StatusEnum {
		MAKEOUT(1, "已开票"), RECYCLECANCEL(2, "已回收作废"), NOTRECYCLECANCEL(3, "未回收作废"), APPLY(
				4, "申请中"), REJECT(5, "拒绝");

		private Integer code;
		private String desc;

		private StatusEnum(Integer code, String desc) {
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

	public final String getEncoding() {
		return encoding;
	}

	public final void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public final Long getOrderId() {
		return orderId;
	}

	public final void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public final Long getStudentId() {
		return studentId;
	}

	public final void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public final Long getBranchId() {
		return branchId;
	}

	public final void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public final String getHeading() {
		return heading;
	}

	public final void setHeading(String heading) {
		this.heading = heading;
	}

	public final String getCompanyName() {
		return companyName;
	}

	public final void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public final String getTaxNum() {
		return taxNum;
	}

	public final void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public final String getInvoiceExplain() {
		return invoiceExplain;
	}

	public final void setInvoiceExplain(String invoiceExplain) {
		this.invoiceExplain = invoiceExplain;
	}

	public final Long getRequiredMoney() {
		return requiredMoney;
	}

	public final void setRequiredMoney(Long requiredMoney) {
		this.requiredMoney = requiredMoney;
	}

	public final Long getActualMoney() {
		return actualMoney;
	}

	public final void setActualMoney(Long actualMoney) {
		this.actualMoney = actualMoney;
	}

	public final String getInvoiceCode() {
		return invoiceCode;
	}

	public final void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public final Long getInvoiceCompamy() {
		return invoiceCompamy;
	}

	public final void setInvoiceCompamy(Long invoiceCompamy) {
		this.invoiceCompamy = invoiceCompamy;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final String getOldId() {
		return oldId;
	}

	public final void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public final Date getApplyDate() {
		return applyDate;
	}

	public final void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public final Long getApplyUser() {
		return applyUser;
	}

	public final void setApplyUser(Long applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public final Date getInvoiceDate() {
		return invoiceDate;
	}

	public final void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public final Long getInvoiceUser() {
		return invoiceUser;
	}

	public final void setInvoiceUser(Long invoiceUser) {
		this.invoiceUser = invoiceUser;
	}

	public String getInvoiceUserName() {
		return invoiceUserName;
	}

	public void setInvoiceUserName(String invoiceUserName) {
		this.invoiceUserName = invoiceUserName;
	}

	public final Long getFees() {
		return fees;
	}

	public final void setFees(Long fees) {
		this.fees = fees;
	}

	public final String getOrderEncoding() {
		return orderEncoding;
	}

	public final void setOrderEncoding(String orderEncoding) {
		this.orderEncoding = orderEncoding;
	}

	public final String getInvoiceCompamyName() {
		return invoiceCompamyName;
	}

	public final void setInvoiceCompamyName(String invoiceCompamyName) {
		this.invoiceCompamyName = invoiceCompamyName;
	}

	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}

	public final Long getMoney() {
		return money;
	}

	public final void setMoney(Long money) {
		this.money = money;
	}

	public final String getStudentEncoding() {
		return studentEncoding;
	}

	public final void setStudentEncoding(String studentEncoding) {
		this.studentEncoding = studentEncoding;
	}

	public final String getStudentName() {
		return studentName;
	}

	public final void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public final String getBranchName() {
		return branchName;
	}

	public final void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public final Long getPremiumAmount() {
		return premiumAmount;
	}

	public final void setPremiumAmount(Long premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getHeadingName() {
		return headingName;
	}

	public void setHeadingName(String headingName) {
		this.headingName = headingName;
	}

	public Integer getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(Integer receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getReceiveStatusName() {
		return receiveStatusName;
	}

	public void setReceiveStatusName(String receiveStatusName) {
		this.receiveStatusName = receiveStatusName;
	}
}
