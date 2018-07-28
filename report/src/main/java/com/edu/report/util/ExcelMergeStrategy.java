package com.edu.report.util;

import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excel单元格合并策略,merge方法为默认实现，如果规则和merge不一样，请Override"merge"方法
 *
 */
public class ExcelMergeStrategy {

	/**
	 * 存放数据的容器（使merge方法能够保存合并状态）
	 */
	protected HashMap<String,Object> dataStore = new HashMap<>(); 
	
	/**
	 * 需要进行行合并的列索引
	 */
	protected Integer[] columnIndexs;
	
	/**
	 * 进行行合并用户比较的列名，如：按照单据编号进行合并，即：如果相连的行，如果单据编号相同，则将指定的列进行合并
	 */
	protected String compareColName;
	
	public ExcelMergeStrategy() {
	}
	
	public ExcelMergeStrategy(String compareColName,Integer[] columnIndexs) {
		this.compareColName = compareColName;
		this.columnIndexs = columnIndexs;
	}
	
	/**
	 * 将相邻行按照compareColName列进行比较，如果值相等则将columnIndexs指定的列进行行合并
	 * @param sheet excel的sheet对象
	 * @param colName excel单元格对于的列名
	 * @param value excel单元格对于的列值
	 * @param rowNumber 当前单元格所在的行号
	 */
	public void merge(Sheet sheet,String colName,String value,int rowNumber) {
		//如果单据编号相同则进行合并opt_encoding
		if(rowNumber == -1) {
			//表示遍历文件结束
		} else if(!compareColName.equals(colName)) {
			return;
		}
		Object prevOrderNumber = this.dataStore.get("prevOrderNumber");
		if(null != prevOrderNumber) {//存在上一个单据编号
			
			boolean equals = prevOrderNumber.equals(value);
			if(!equals) {//当前传入的单据编号和上一次保存的值不相等，则进行单元格合并
				// 获取开始合并行和截止行
				int startRow = (Integer)this.dataStore.get("startRow"); // 开始合并行
				int endRow = (Integer)this.dataStore.get("endRow");; // 结束合并行
				if(endRow>startRow) {
					// 将需要合并的单元格的值设置为空（除去合并的第一行）
					Row rowData = null;
					for (int k = startRow + 1; k <= endRow; k++) {
						rowData = sheet.getRow(k);
						for (Integer columnIndex : columnIndexs) {
							rowData.getCell(columnIndex).setCellValue(0);
						}
					}
					// 合并
					for (Integer columnIndex : columnIndexs) {
						sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, columnIndex, columnIndex));
					}
				}
				//合并后将当前的值保存
				this.dataStore.put("prevOrderNumber", value);
				this.dataStore.put("startRow", rowNumber);
			} 
		} else {
			//将当前的值保存
			this.dataStore.put("prevOrderNumber", value);
			this.dataStore.put("startRow", rowNumber);
		}
		this.dataStore.put("endRow", rowNumber);
	}
}
