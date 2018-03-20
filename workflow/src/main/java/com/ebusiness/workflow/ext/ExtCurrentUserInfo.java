/**  
 * @Title: ExtCurrentUserInfo.java
 * @Package com.ebusiness.workflow.ext
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月4日 下午3:21:27
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.ext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.api.task.Task;

/**
 * @ClassName: ExtCurrentUserInfo
 * @Description: 任务处理的时候，设置当前用户的信息
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月4日 下午3:21:27
 * 
 */
public interface ExtCurrentUserInfo {
	Map<String, Object> genCurrentUserInfo(Task task,
			HttpServletRequest request, HttpServletResponse response);
}
