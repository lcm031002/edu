package com.edu.erp.model;

/**
 * excel单元格对于的界面显示的单元格数据
 *
 * @param <T>
 */
public class ExcelHtmlCell<T> {
	
	private String displayValue;
	private T holdValue;
	private String checkErrorMessage;
	private String cellName;
	private Integer order = 1;
	
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public String getCheckErrorMessage() {
		return checkErrorMessage;
	}
	public void setCheckErrorMessage(String checkErrorMessage) {
		this.checkErrorMessage = checkErrorMessage;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public T getHoldValue() {
		return holdValue;
	}
	public void setHoldValue(T holdValue) {
		this.holdValue = holdValue;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
}
