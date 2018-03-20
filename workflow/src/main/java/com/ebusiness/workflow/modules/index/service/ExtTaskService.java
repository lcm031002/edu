/**  
 * @Title: ExtTaskService.java
 * @Package com.ebusiness.workflow.modules.index.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月15日 下午3:48:08
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.service;

import java.util.List;
import java.util.Map;

import com.ebusiness.workflow.modules.index.model.ExtTask;

/**
 * @ClassName: ExtTaskService
 * @Description: 任务查询服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月15日 下午3:48:08
 *
 */
public interface ExtTaskService {
	List<ExtTask> queryTaskList(Map<String,Object> obj);
}
