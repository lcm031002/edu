package com.edu.report.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excel单元格合并策略,merge方法为默认实现，如果规则和merge不一样，请Override"merge"方法
 * @author zenglw
 *
 */
public class ExcelMergePerformanceDetailStrategy extends ExcelMergeStrategy {
	
	public ExcelMergePerformanceDetailStrategy(String compareColName,Integer[] columnIndexs) {
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
	@Override
	public void merge(Sheet sheet,String colName,String value,int rowNumber) {
		//如果单据编号相同则进行合并opt_encoding
		if("fee_type_name".equals(colName)){
			// 设置除去开始行以外的合并行指定的列值为空，才可以合并
			if(sheet.getRow(rowNumber).getCell(20).getStringCellValue().equals("预结转费用"))
				sheet.getRow(rowNumber).getCell(19).setCellValue(0);
		}
		if(rowNumber == -1) {
			//表示遍历文件结束
		} else if(!(compareColName.equals(colName))) {
			return;
		}
		Object prevOrderNumber = this.dataStore.get("prevOrderNumber");
		
		if(null != prevOrderNumber) {//存在上一个单据编号
			
			if(!prevOrderNumber.equals(value)) {//当前传入的单据编号和上一次保存的值不相等，则进行单元格合并
				// 获取开始合并行和截止行
				int startRow = (Integer)this.dataStore.get("startRow"); // 开始合并行
				int endRow = (Integer)this.dataStore.get("endRow"); // 结束合并行
				if(endRow>startRow) {
					// 设置除去开始行以外的合并行指定的列值为空，才可以合并
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
