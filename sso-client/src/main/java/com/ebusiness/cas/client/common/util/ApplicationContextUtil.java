/**  
 * @Title: ApplicationContextUtil.java
 * @Package com.ebusiness.cas.client.common.util
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 下午12:13:23
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * @ClassName: ApplicationContextUtil
 * @Description: bean上下文工具类
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 下午12:13:23
 *
 */
public class ApplicationContextUtil  implements ApplicationContextAware
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

