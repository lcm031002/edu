package com.edu.erp.util;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class DxbCityCfg {
	private static final Log logger = LogFactory.getLog(DxbCityCfg.class);
	
	private Properties props = null;
	
	private long lastModifiedTime = 0;
	
	Resource res = null;
	
	private static final DxbCityCfg INSTANCE = new DxbCityCfg();

	public static DxbCityCfg getInstance() {
		return INSTANCE;
	}
	
	private DxbCityCfg() {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		res = resourceResolver.getResource("classpath:/properties/dxb_city_cfg.properties");
		try {
			lastModifiedTime = res.lastModified();
			
			props = new Properties();
			props.load(res.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("read dxb_city_cfg.properties exception:" + e);
		}
	}
	
	final public String getConfigItem(String name,String defualtVal){
		try {
			long newTime = res.lastModified();
			if(newTime > lastModifiedTime){
				props.clear();
				props.load(res.getInputStream());
			}
			lastModifiedTime = newTime;
			String val = props.getProperty(name);
			
			if(val == null){
				return defualtVal;
			}
		} catch (IOException e) {
			logger.error("getConfigItem exception:" + e);
		}
		return props.getProperty(name);
	}

}
