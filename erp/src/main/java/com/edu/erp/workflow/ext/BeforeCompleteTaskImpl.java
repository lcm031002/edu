/**  
 * @Title: AfterCompleteTaskImpl.java
 * @Package com.modules.work_flow.handle
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年1月22日 下午9:12:17
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.workflow.ext;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.common.util.DateUtil;
import com.edu.erp.workflow.service.UserTaskService;
import com.edu.workflow.ext.BeforeCompleteTask;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;


public class BeforeCompleteTaskImpl implements BeforeCompleteTask {

	@Override
	public void doBefore(Task task, String outcome, Map<String, Object> params,
			ProcessEngine processEngine) {
		if (task instanceof TaskImpl && null != processEngine) {
			TaskImpl taskImpl = (TaskImpl) task;
			updateStepStatus(taskImpl, outcome, processEngine);
		}
	}

	private void updateStepStatus(TaskImpl task, String outcome,
			ProcessEngine processEngine) {
		TaskService taskService = processEngine.getTaskService();
		Set<String> params = taskService.getVariableNames(task.getId());
		Map<String, Object> vals = taskService.getVariables(task.getId(),
				params);
		String application_id = null == vals.get("application_id") ? null
				: vals.get("application_id") + "";
		if (StringUtils.isNotBlank(application_id)
				&& !"null".equals(application_id)) {
			Map<String, Object> userApplication = new HashMap<String, Object>();
			userApplication.put("ID", application_id);
			userApplication.put("CURRENT_STEP", task.getName());
			userApplication.put("STATUS", 1);
			userApplication.put("CURRENT_STATE", outcome);
			userApplication.put("UPDATETIME",
					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			try {
				UserTaskService userApplicationTaskService = (UserTaskService) ApplicationContextUtil
						.getBean("userTaskService");
				userApplicationTaskService.updateApplication(userApplication);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
