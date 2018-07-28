package com.edu.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * util spring bean tool 
 *
 */
public class ApplicationContextUtil   implements ApplicationContextAware
{
	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException 
	{
		ApplicationContextUtil.context = context;
	}
	
	/**
	 * 通过Spring里配置文件中的Bean对应的ID获取对象示例
	 * @param 	beanId		Spring里配置文件中的Bean对应的ID
	 * @return	T			对象示例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId)
	{
		return (T) context.getBean(beanId);
	}
}

