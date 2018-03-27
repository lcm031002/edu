package com.edu.erp.model;

import java.util.ArrayList;
import java.util.List;

// Generated 2014-9-21 22:52:02 by Hibernate Tools 3.4.0.CR1

/**
 * TabOrderPayCost generated by hbm2java
 */
public class TabOrderPayCost extends BaseObject {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -7867889283958560750L;
	private String encoding;
	private Long orderId;
	private String buyDate;
	private Long studentId;
	private Long sumPrice;
	private Long actualPrice;
	private Long cashPrice;
	private Long cardPrice;
	private Long onlinePrice;
	private Long transferPrice;
	private Long accountPrice;
	private Long unitaryAccount;
	private Long paymentType;
	private Long branchId;
	private String branchName;
	private Long buId;
	private Long areaOrgId;
	private Long lastApprover;
	private Long approveTime;
	private Long beforeOperateBalance;
	private String createUser;
	private String updateUser;
	private String extendColumn;
	private String extendColumn2;
	private Long extendColumn3;
	private Long extendColumn4;
	private Long before_operate_balance;
	private Long frozenAccountPrice;
	private List<TabOrderPayCostDetail> details = new ArrayList<TabOrderPayCostDetail>();

	public Long getBefore_operate_balance() {
		return before_operate_balance;
	}

	public void setBefore_operate_balance(Long before_operate_balance) {
		this.before_operate_balance = before_operate_balance;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Long actualPrice) {
		this.actualPrice = actualPrice;
	}

	public Long getCashPrice() {
		return cashPrice;
	}

	public void setCashPrice(Long cashPrice) {
		this.cashPrice = cashPrice;
	}

	public Long getCardPrice() {
		return cardPrice;
	}

	public void setCardPrice(Long cardPrice) {
		this.cardPrice = cardPrice;
	}

	public Long getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(Long transferPrice) {
		this.transferPrice = transferPrice;
	}

	public Long getAccountPrice() {
		return accountPrice;
	}

	public void setAccountPrice(Long accountPrice) {
		this.accountPrice = accountPrice;
	}

	public Long getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getBuId() {
		return buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Long getAreaOrgId() {
		return areaOrgId;
	}

	public void setAreaOrgId(Long areaOrgId) {
		this.areaOrgId = areaOrgId;
	}

	public Long getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Long approveTime) {
		this.approveTime = approveTime;
	}

	public Long getBeforeOperateBalance() {
		return beforeOperateBalance;
	}

	public void setBeforeOperateBalance(Long beforeOperateBalance) {
		this.beforeOperateBalance = beforeOperateBalance;
	}

	public String getExtendColumn() {
		return extendColumn;
	}

	public void setExtendColumn(String extendColumn) {
		this.extendColumn = extendColumn;
	}

	public String getExtendColumn2() {
		return extendColumn2;
	}

	public void setExtendColumn2(String extendColumn2) {
		this.extendColumn2 = extendColumn2;
	}

	public Long getExtendColumn4() {
		return extendColumn4;
	}

	public void setExtendColumn4(Long extendColumn4) {
		this.extendColumn4 = extendColumn4;
	}

	public Long getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Long sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Long getUnitaryAccount() {
		return unitaryAccount;
	}

	public void setUnitaryAccount(Long unitaryAccount) {
		this.unitaryAccount = unitaryAccount;
	}

	public Long getLastApprover() {
		return lastApprover;
	}

	public void setLastApprover(Long lastApprover) {
		this.lastApprover = lastApprover;
	}

	public Long getExtendColumn3() {
		return extendColumn3;
	}

	public void setExtendColumn3(Long extendColumn3) {
		this.extendColumn3 = extendColumn3;
	}

	public final List<TabOrderPayCostDetail> getDetails() {
		return details;
	}

	public final void setDetails(List<TabOrderPayCostDetail> details) {
		this.details = details;
	}

	public final String getCreateUser() {
		return createUser;
	}

	public final void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public final String getUpdateUser() {
		return updateUser;
	}

	public final void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public final String getBranchName() {
		return branchName;
	}

	public final void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getFrozenAccountPrice() {
		return frozenAccountPrice;
	}

	public void setFrozenAccountPrice(Long frozenAccountPrice) {
		this.frozenAccountPrice = frozenAccountPrice;
	}

	public Long getOnlinePrice() {
		return onlinePrice;
	}

	public void setOnlinePrice(Long onlinePrice) {
		this.onlinePrice = onlinePrice;
	}
}
