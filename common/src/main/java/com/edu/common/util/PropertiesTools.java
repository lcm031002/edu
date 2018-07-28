package com.edu.common.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/***
 * Description : 配置文件读取工具类
 *
 */
public class PropertiesTools {

	private static final Log logger = LogFactory.getLog(PropertiesTools.class);
	//dataMap
	private static final Map<String, String> dataMap = new HashMap<String, String>();
	
	static {
		loadMap();
	}
	
	/**
	 * 加载配置文件config.properties
	 */
	private static void loadMap() {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		Resource res = resourceResolver.getResource("classpath:/properties/config.properties");
		Properties props = null;
		try {
			props = new Properties();
			props.load(res.getInputStream());
		} catch (IOException e) {
			logger.error("Loading MultipleMap properties failed", e);
		}
		if(props != null) {
			Enumeration<?> en = props.propertyNames();
			while(en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = props.getProperty(key);
				dataMap.put(key.trim(), value.trim());
			}
		}
	}
	
	/**
	 * 根据key获取Properties里的value值
	 * @param key值
	 * @return 返回对应的value
	 */
	public static String getPropertiesByKey(String key) {
		return dataMap.get(key);
	}
	
	public static String getDiskDirectory(){
		return getPropertiesByKey("file_root");
	}
}
