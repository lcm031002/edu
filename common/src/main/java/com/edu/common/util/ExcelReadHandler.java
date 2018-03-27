package com.edu.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作EXCEL
 * 
 * @author wCong
 *
 */
public class ExcelReadHandler {
	
	private static final Logger log = Logger.getLogger(ExcelReadHandler.class);
	
	/**
	 * 读取 Excel
	 *	
	 * 
	 * @param input
	 * @throws Exception
	 * @return List<map<列名:列值>>
	 */  
	public static List<Map<String, Object>> readExcel(InputStream input) throws Exception {
		return readExcel(input, null, 5);
	}

	public static List<Map<String, Object>> readExcel(InputStream input, Integer sheetIdx, Integer minRows) throws Exception {
		List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();
		Workbook rwb = null;
		try {
			rwb = WorkbookFactory.create(input);
			// 遍历所有sheet
			Sheet sheet = null;
			if (sheetIdx != null && sheetIdx >= 0) {
				sheet = rwb.getSheetAt(sheetIdx);
				readExcel(sheet, excelList, minRows);
			} else {
				for(int i=0; i < rwb.getNumberOfSheets(); i++){
					sheet = rwb.getSheetAt(i);
					readExcel(sheet, excelList, minRows);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}finally{
			if(input != null)
				input.close();
		}
		return excelList;
	}

	public static void readExcel(Sheet sheet, List<Map<String, Object>> excelList, Integer minRows) throws Exception {
		// 行数
		int rows = sheet.getPhysicalNumberOfRows();
		// 小于5行，当作空的页签
		if(rows < minRows) {
			return;
		}
		// 第三行：编码行
		Row sheetRow = sheet.getRow(2);
		if(sheetRow == null) {
			return;
		}

		Cell cell = null;
		// 列名
		Map<Object, String> colNameMap = new HashMap<Object, String>();
		// 行
		Map<String, Object> rowMap = null;

			// 列数
		int cols = sheetRow.getLastCellNum();
		// 利用二维数组获取单元格数据
		for(int row=0; row<rows; row++){
			boolean isAdd = true;
			// 第 1、2、4行不处理
			if(row < 2 || (minRows == 5 && row == 3))
				continue;

			sheetRow = sheet.getRow(row);
			if(sheetRow == null) {
				break;
			}
			//第一个Cell如果为空，则认为完成了整个页签的读取
			if(sheetRow.getCell(0, Row.RETURN_BLANK_AS_NULL) == null) {
				break;
			}
			// 空值行过滤
			boolean isNuLLRow = true;
			for(int col=0; col<cols; col++){
				cell = sheetRow.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(cell == null) {
					continue;
				}
				Object content = getCellValue(cell);
				if(content != null && StringUtils.isNotEmpty(content.toString())) {
					isNuLLRow = false;
				}
			}
			if(isNuLLRow) {
				continue;
			}

			// 创建行对象
			rowMap = new HashMap<String, Object>(cols);
			// 设置行号
			rowMap.put("row_no", row + 1);
			for(int col=0; col<cols; col++){
				cell = sheetRow.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(cell == null) {
					continue;
				}
				Object content = getCellValue(cell);
				// 第三行的单元格数据为列名
				if(row == 2){
					colNameMap.put(col, content.toString());
					isAdd = false;
					continue;
				}
				String coleName = colNameMap.get(col);
				// 空值列过滤  列名为空的列不作处理
				if(StringUtils.isEmpty(coleName))
					continue;
				// 将列以 列名:列值 的形式  装入行
				rowMap.put(coleName, content);
			}
			if(isAdd) {
				excelList.add(rowMap);
			}
		}
		colNameMap.clear();
	}

	/**
	 * 获取单元格值
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Cell cell) {
		if(cell == null) {
			return null;
		}
		Object value = null;
		switch (cell.getCellType()) {
	      case Cell.CELL_TYPE_NUMERIC: // 数字
	    	  //如果为时间格式的内容
	    	  short format = cell.getCellStyle().getDataFormat();  
	    	  if(format == 14 || format == 20) {
	    		  SimpleDateFormat sdf = null;  
	    		  if(format == 14){  
	    			  //日期  
	    			  sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    		  }else if (format == 20) {  
	    			  //时间  
	    			  sdf = new SimpleDateFormat("HH:mm");  
	    		  }  
	    		  value = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue()));
	    	  } else {
	    		  value = cell.getNumericCellValue();
	    		  double doubleValue = (double) value;
	    		  //value为整数时，去掉小数
	    		  if(Math.round(doubleValue)-doubleValue == 0){
	    			  value = (long)doubleValue;
	    		  }
	    	  }
	          break;
	      case Cell.CELL_TYPE_STRING: // 字符串
	          value = cell.getStringCellValue();
	          break;
	      case Cell.CELL_TYPE_BOOLEAN: // Boolean
	          value = cell.getBooleanCellValue() + "";
	          break;
	      case Cell.CELL_TYPE_FORMULA: // 公式
	          value = cell.getCellFormula() + "";
	          break;
	      case Cell.CELL_TYPE_BLANK: // 空值
	          value = null;
	          break;
	      case Cell.CELL_TYPE_ERROR: // 故障
	          value = "非法字符";
	          break;
	      default:
	          value = "未知类型";
	          break;
		}
		
		return value;
	}
	
	// 测试
	public static void main(String[] args) {
		try {
			// 读Excel
//			InputStream io = new FileInputStream("F:/dowdload/122/111.xls");
//			ExcelHandler.readExcel(io);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}