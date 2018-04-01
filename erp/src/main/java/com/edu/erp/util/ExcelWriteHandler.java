package com.edu.erp.util;

import com.edu.common.util.StringUtil;
import com.edu.erp.model.BaseObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 操作EXCEL
 * 
 * @author wCong
 *
 */
public class ExcelWriteHandler {
	
	private static final Logger log = Logger.getLogger(ExcelWriteHandler.class);
	
	/**
	 * 生成excel文件
	 * @param datas
	 *            数据
	 * @param templateFilePath
	 *            模板文件路径
	 * @param templateFileName
	 *            模板文件名称
	 * @param tempFilePath
	 *            临时文件路径
	 * @param tempFileName
	 *            临时文件名称
	 * @return 输出的Excel路径名称
	 */
	public static String writeDataToExcel(List<? extends Object> datas, String templateFilePath,
		String templateFileName, String tempFilePath, String tempFileName) throws Exception {
		return writeDataToExcel(datas, templateFilePath, templateFileName, tempFilePath, tempFileName, 2, 4);
	}

	/**
	 * 生成excel文件
	 * @param datas
	 *            数据
	 * @param templateFilePath
	 *            模板文件路径
	 * @param templateFileName
	 *            模板文件名称
	 * @param tempFilePath
	 *            临时文件路径
	 * @param tempFileName
	 *            临时文件名称
	 *  @param fieldRow
	 *            字段名定义所在行
	 *  @param writeRow
	 *  		  开始写数据行数
	 * @return 输出的Excel路径名称
	 */
	public static String writeDataToExcel(List<? extends Object> datas, String templateFilePath, 
			String templateFileName, String tempFilePath, String tempFileName, int fieldRow, int writeRow) throws Exception {
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
			File tempalteFile = new File(templateFilePath + File.separator + templateFileName);// 模板文件
			if (tempalteFile.exists() == false) {
				throw new Exception("the template file can not be found!");
			}
			// 临时文件目录创建
			File tempFileDir = new File(tempFilePath);
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
			List<String> headerList = readHeader(sheet, fieldRow);
			if(com.edu.common.util.StringUtil.isEmpty(headerList)) {
				throw new Exception("读取列头失败!");
			}
			CellStyle headerStyle = xlsWorkBook.createCellStyle();
			headerStyle.setFillPattern(CellStyle.ALIGN_FILL);
			headerStyle.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
			headerStyle.setFillBackgroundColor(HSSFColor.BROWN.index);

			CellStyle redCellStyle = xlsWorkBook.createCellStyle();
			redCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			redCellStyle.setFillForegroundColor(HSSFColor.RED.index);

			Cell cell = null;
			//固定EXCEL表头的前四行
			sheet.createFreezePane(0, 1);
			sheet.createFreezePane(0, writeRow);
			// 往Excel里面追加数据
			Iterator<? extends Object> iterator = datas.iterator();
			/*****************
			 * begin iterator data for write data to excel
			 *********************/
			while (iterator.hasNext()) {
				Object rowData = iterator.next();
				String setColor = getFieldValue(rowData, "setColor");
				int cellColumnIndex = 0;// 列索引
				sheet.createRow(writeRow);
				for (String header : headerList) {
					cell = sheet.getRow(writeRow).createCell(cellColumnIndex);
					String value = getFieldValue(rowData, header);
					cell.setCellValue(value);
					if ("true".equals(setColor)) {
						cell.setCellStyle(redCellStyle);
					}
					cellColumnIndex++;
				}
				writeRow++;
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
	private static String getFieldValue(Object obj, String keyName) {
		String fieldValue = "";
		try {
			// 如果对象是Map
			if (obj instanceof Map<?, ?>) {
				Map<String, Object> rowData = (Map<String, Object>) obj;
				fieldValue = getFormatValue(rowData.get(keyName));
			}
			// 如果是一个pojo对象或者是普通的对象时直接返回对象的某个具体属性的值
			else if ((obj instanceof Object) || (obj instanceof BaseObject)) {
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
	private static String getFormatValue(Object c) {
		if (c instanceof java.sql.Timestamp) {
			return new SimpleDateFormat("YYYY/MM/dd HH:mm").format(c);
		}
		if (c instanceof java.sql.Date || c instanceof java.util.Date) {
			return new SimpleDateFormat("YYYY/MM/dd HH:mm").format(c);
		}
		return c == null ? StringUtils.EMPTY : c.toString();
	}

	/**
	 * 读取列头
	 * @param sheet
	 * @return
	 */
	private static List<String> readHeader(Sheet sheet, int fieldRow) {
		List<String> headerList = new ArrayList<String>();
		Row headRow = sheet.getRow(fieldRow);
		Cell cell = null;
		for (int index = headRow.getFirstCellNum(); index < headRow.getLastCellNum(); index++) {
			cell = headRow.getCell(index);
			if (cell != null && !StringUtil.isEmpty(cell.getStringCellValue())) {
				headerList.add(cell.getStringCellValue());
			}

		}
		return headerList;
	}
	
}