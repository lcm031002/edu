package com.edu.common.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class ERPConstants {

	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_2 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_3 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_4 = "yyyy/MM/dd";
	public static final String DATE_FORMAT_5 = "HH:mm:ss";
	public static final String DATE_FORMAT_6 = "HHmmss";
	public static final String DATE_FORMAT_7 = "HH:mm";
	
	
	public static final String FILE_ROOT = PropertiesTools.getDiskDirectory();
	public static final String REPORT_ROOT =  FILE_ROOT + "report" + File.separator;
	public static final Integer EXCEL_ROW_SIZE = StringUtils.isEmpty(PropertiesTools.getPropertiesByKey("excel_row_size")) 
													|| !NumberUtils.isNumber(PropertiesTools.getPropertiesByKey("excel_row_size")) 
												? 20000 : Integer.parseInt(PropertiesTools.getPropertiesByKey("excel_row_size"));
	
	
	//session.key
	public static final String SESSION_USER = "user"; 				// 当前登陆用户
	public static final String SESSION_ORG = "org"; 				// 组织机构
	public static final String SESSION_CITY = "city"; 				// 地区
	public static final String SESSION_BU = "bu";					// 团队
	public static final String SESSION_BRANCH = "branch";			// 校区
	
	// 数据字典类型
	public static final String TYPE_KM = "TYPE_KM"; 				// 科目
	public static final String TYPE_KCMB = "TYPE_KCMB"; 			// 课程目标
	public static final String TYPE_KCMX = "TYPE_KCMX"; 			// 课程模型
	public static final String TYPE_CP = "TYPE_CP"; 				// 产品
	public static final String TYPE_XXLX = "TYPE_XXLX"; 			// 学校类型
	public static final String TYPE_XSKQ = "TYPE_XSKQ"; 			// 学生考勤状态
	public static final String TYPE_LSKQ = "TYPE_LSKQ"; 			// 老师考勤状态
	public static final String TYPE_FPTT = "TYPE_FPTT"; 			// 发票抬头
	
	
	// 异常页面
	public static final String REDIRECT_ERROR = "redirect:/web/error.do";
	
	public static final boolean OS_WINDOWS = "\\".equals(File.separator) ? true : false;
	public static final boolean OS_LINUX = "/".equals(File.separator) ? true : false;
	
	public static final String VERSION = StringUtils.isEmpty(PropertiesTools.getPropertiesByKey("version")) ? "201511111111" : PropertiesTools.getPropertiesByKey("version");
}
