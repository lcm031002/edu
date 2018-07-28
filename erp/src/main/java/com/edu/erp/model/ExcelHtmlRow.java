package com.edu.erp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * excel行数据对于的界面显示的行对象
 *
 */
public class ExcelHtmlRow {
	
	private Integer rowNumber;
	private String checkStatus;//成功，失败
	private List<ExcelHtmlCell> cells;

	public List<ExcelHtmlCell> getCells() {
		cells = cells==null?new ArrayList<ExcelHtmlCell>():cells;
		return cells;
	}

	public void setCells(List<ExcelHtmlCell> cells) {
		this.cells = cells;
	}
	
	public ExcelHtmlCell getCellByCellName(String cellName) {
		for (ExcelHtmlCell excelHtmlCell : cells) {
			if(excelHtmlCell.getCellName().equals(cellName)) {
				return excelHtmlCell;
			}
		}
		return null;
	}
	
	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public void initcheckStatus() {
		for (ExcelHtmlCell excelHtmlCell : cells) {
			if(excelHtmlCell.getCheckErrorMessage() != null) {
				this.checkStatus="失败";
				return;
			}
		}
		this.checkStatus="成功";
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public void sortCellsOfRow() {
		Collections.sort(cells, new Comparator<ExcelHtmlCell>() {
			@Override
			public int compare(ExcelHtmlCell o1, ExcelHtmlCell o2) {
				return o1.getOrder()>o2.getOrder()?1:-1;
			}
		});
	}
}
