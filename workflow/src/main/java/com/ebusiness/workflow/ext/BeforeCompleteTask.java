package com.ebusiness.workflow.ext;

import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;

/**
 * @ClassName: AfterCompleteTask
 * @Description: 任务执行完毕后，回调
 *
 */
public interface BeforeCompleteTask {
	void doBefore(Task task,String outcome,Map<String,Object> params,ProcessEngine processEngine) ;
}
