/**  
 * @Title: WorkFlowHelper.java
 * @Package com.modules.work_flow.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月12日 上午11:26:44
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.util;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;

/**
 * @ClassName: WorkFlowHelper
 * @Description: 工作流的工具类
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月12日 上午11:26:44
 * 
 */
public class WorkflowHelper {
	/**
	 * 
	 * @Title: deployed
	 * @Description: 判断给定的工作流key是否可用
	 * @param processEngine
	 *            工作流引擎
	 * @param workflowKey
	 *            工作流key
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isDeployed(ProcessEngine processEngine, String workflowKey) {
		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey(workflowKey).uniqueResult();
		return null != processDefinition && !processDefinition.isSuspended();
	}
}
