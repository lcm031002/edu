package com.edu.erp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.ReflectionUtils;

/**
 * 报表按指定的Excel模板写入数据
 * @ClassName: ReportWriteExcelHandler
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月8日 上午9:53:23
 *
 */
public class ReportWriteExcelHandler {
	
	private static final Logger log = Logger.getLogger(ReportWriteExcelHandler.class);
	
	/**
	 * @title 支持数据类型:HashMap和普通对象
	 *            标题
	 * @param datas
	 *            数据
	 * @param rootPath
	 *            需要合计的列索引
	 * @param templateFileName
	 *            Excel文件基路径
	 * @param tempFileName
	 *            导出的Excel文件名
	 * @return 输出的Excel路径名称
	 */
	public static String writeDataToExcel(List<? extends Object> datas, String rootPath, 
            String templateFileName, String tempFileName) throws Exception {
	    return writeDataToExcel(datas, rootPath, templateFileName, tempFileName, 1);
	}
	
	public static String writeDataToExcel(List<? extends Object> datas, String rootPath, 
			String templateFileName, String tempFileName, int headRowNum) throws Exception {
		FileInputStream input = null;
		FileOutputStream output = null;
		/**
		 * 利用输入输出流读写Excel文件
		 */
		try {
			if (log.isDebugEnabled()) {
				log.debug("begin to writting to excel.............");
			}
			// 复制模板到导出模板
			File tempalteFile = new File(getTemplateFilePath(rootPath) + File.separator + templateFileName);// 模板文件
			if (tempalteFile.exists() == false) {
				throw new Exception("the template file can not be found!");
			}
			// 临时文件目录创建
			File tempFileDir = new File(getTempFilePath(rootPath));
			if(!tempFileDir.exists()) {
				tempFileDir.mkdirs();
			}
			// 导出文件的父级目录
			File outPutFile = new File(tempFileDir, tempFileName);// 输出文件
			// 判断被复制的导出文件是否存在,如果存在就抛出异常不让复制
			if (outPutFile.exists()) {
				throw new Exception("the output file already exist!");
			}
			Files.copy(tempalteFile.toPath(), outPutFile.toPath());
			input = new FileInputStream(outPutFile);
			
			Workbook xlsWorkBook = WorkbookFactory.create(input);
			Sheet sheet = xlsWorkBook.getSheetAt(0);
			List<String> headerList = readHeader(sheet, headRowNum);
			if(CollectionUtils.isEmpty(headerList)) {
				throw new Exception("读取列头失败!");
			}
			CellStyle headerStyle = xlsWorkBook.createCellStyle();
			headerStyle.setFillPattern(CellStyle.ALIGN_FILL);
			headerStyle.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
			headerStyle.setFillBackgroundColor(HSSFColor.BROWN.index);
			Cell cell = null;
			//固定EXCEL表头的第一行
			sheet.createFreezePane(0, headRowNum);
			// 往Excel里面追加数据
			int wirteIndex = headRowNum;
			Iterator<? extends Object> iterator = datas.iterator();
			/*****************
			 * begin iterator data for write data to excel
			 *********************/
			while (iterator.hasNext()) {
				Object rowData = iterator.next();
				int cellColumnIndex = 0;// 列索引
				sheet.createRow(wirteIndex);
				for (String header : headerList) {
					cell = sheet.getRow(wirteIndex).createCell(cellColumnIndex);
					Object value = getFieldValue(rowData, header);
					if(value instanceof String) {
						cell.setCellValue((String)value);
					} else {
						cell.setCellValue((double)value);
					}
					cellColumnIndex++;
				}
				wirteIndex++;
			}
			/***************** end to write *********************/
			output = new FileOutputStream(outPutFile);
			xlsWorkBook.write(output);
			
			return outPutFile.getAbsolutePath();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("write successed.............");
			}
			try {
				if (null != input) {
					input.close();
				}
				if (null != output) {
					output.close();
				}
			} catch (IOException e) {
				log.error("write excel found erros:" + e.getMessage());
				e.printStackTrace();
			}
			log.info("system generator excel file,the file name is:" + tempFileName);
		}
	}
	
	/**
	 * 
	 * @Title: getFieldValue @Description: TODO(获取对象的属性值) @param obj @param
	 * keyName 对象的属性名 @return String 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	private static Object getFieldValue(Object obj, String keyName) {
		Object fieldValue = "";
		try {
			// 如果对象是Map
			if (obj instanceof Map<?, ?>) {
				Map<String, Object> rowData = (Map<String, Object>) obj;
				fieldValue = getFormatValue(rowData.get(keyName));
			}
			// 如果是一个pojo对象或者是普通的对象时直接返回对象的某个具体属性的值
			else if ((obj instanceof Object)) {
				java.lang.reflect.Field field = ReflectionUtils.findField(obj.getClass(), keyName);
				ReflectionUtils.makeAccessible(field);
				Object object = ReflectionUtils.getField(field, obj);
				fieldValue = getFormatValue(object);
			}
		} catch (Exception e) {
			log.error("Not support  for this type...." + obj);
			return StringUtils.EMPTY;
		}
		return fieldValue;
	}
	
	/**
	 * 
	 * @Title: getFormatValue
	 * @Description: TODO(获取格式化后的值,包括格式化日期)
	 * @param @param
	 *            c
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 */
	private static Object getFormatValue(Object c) {
		if (c instanceof java.sql.Timestamp) {
			return new SimpleDateFormat("YYYY/MM/dd HH:mm").format(c);
		}
		if (c instanceof java.sql.Date || c instanceof java.util.Date) {
			return new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(c);
		}
		if (c instanceof BigDecimal) {
			return ((BigDecimal)c).doubleValue();
		}
		if (c instanceof Double) {
			return ((Double)c).doubleValue();
		}
		if (c instanceof Long) {
			return ((Long)c).doubleValue();
		}
		if (c instanceof Integer) {
			return ((Integer)c).doubleValue();
		}
		return c == null ? StringUtils.EMPTY : c.toString();
	}
	
	/**
	 * 读取列头
	 * @param sheet
	 * @return
	 */
	private static List<String> readHeader(Sheet sheet, int headRowNum) {
		List<String> headerList = new ArrayList<String>();
		Row headRow = sheet.getRow(headRowNum);
		Cell cell = null;
		for (int index = headRow.getFirstCellNum(); index < headRow.getLastCellNum(); index++) {
			cell = headRow.getCell(index);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())) {
				headerList.add(cell.getStringCellValue());
			}

		}
		return headerList;
	}
	
	/**
	 * 获取模板文件存放路径
	 * @param rootPath 根目录
	 * @return 模板文件存放路径
	 */
	private static String getTemplateFilePath(String rootPath) {
        return rootPath + File.separator + "template" + File.separator;
	}
	
	private static String getTempFilePath(String rootPath) {
	    return rootPath + File.separator + "temp" + File.separator;
	}
	
}