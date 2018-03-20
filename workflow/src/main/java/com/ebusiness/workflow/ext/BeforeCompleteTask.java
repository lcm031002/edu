/**  
 * @Title: AfterCompleteTask.java
 * @Package com.ebusiness.workflow.ext
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年1月22日 下午9:09:11
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.ext;

import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;

/**
 * @ClassName: AfterCompleteTask
 * @Description: 任务执行完毕后，回调
 * @author zhuliyong zly@entstudy.com
 * @date 2015年1月22日 下午9:09:11
 *
 */
public interface BeforeCompleteTask {
	void doBefore(Task task,String outcome,Map<String,Object> params,ProcessEngine processEngine) ;
}
