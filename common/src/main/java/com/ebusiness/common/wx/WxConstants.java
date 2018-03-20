package com.ebusiness.common.wx;

import org.apache.commons.lang.StringUtils;

import com.ebusiness.common.util.PropertiesTools;




public class WxConstants {

	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_2 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_3 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_4 = "yyyy/MM/dd";
	public static final String DATE_FORMAT_5 = "HH:mm:ss";
	public static final String DATE_FORMAT_6 = "HHmmss";
	public static final String DATE_FORMAT_7 = "HH:mm";
	
	
	public static final String VERSION = StringUtils.isEmpty(PropertiesTools.getPropertiesByKey("version")) ? "201511111111" : PropertiesTools.getPropertiesByKey("version");
	
}
