/**  
 * @Title: LocalTaskService.java
 * @Package com.ebusiness.workflow.modules.index.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月1日 下午4:04:20
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.service;

import java.util.List;

import org.jbpm.api.task.Task;

/**
 * @ClassName: LocalTaskService
 * @Description: 通过业务角色进行业务处理
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月1日 下午4:04:20
 * 
 */
public interface LocalTaskService {
	List<Task> queryUserTasks(String businessRole);
}
