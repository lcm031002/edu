/**  
 * @Title: ExtAfterDo.java
 * @Package com.ebusiness.workflow.ext
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年3月7日 下午3:13:47
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.ext;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: ExtAfterDo
 * @Description: 在任务执行完成后执行扩展调用处理
 * @author zhuliyong zly@entstudy.com
 * @date 2015年3月7日 下午3:13:47
 * 
 */
public interface ExtAfterTaskDoLog {
	void log(String businessName, String message, String status,
			HttpServletRequest request);
}
