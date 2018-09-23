package com.edu.workflow.modules.assign;

import com.edu.workflow.common.ServiceUtil;
import com.edu.workflow.modules.index.model.ExtBusinessRoleMapping;
import com.edu.workflow.modules.index.model.ExtProcessDef;
import com.edu.workflow.modules.index.model.ExtProcessRoleDef;
import java.util.Set;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.util.CollectionUtils;

/**
 * @ClassName: AssignTask
 * @Description: 默认任务安排
 *
 */
public class DefaultAssignTask implements AssignmentHandler {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 241558028137607676L;

	@Override
	public void assign(Assignable assignable, OpenExecution execution)
			throws Exception {
		if (assignable instanceof Task && execution instanceof ExecutionImpl) {
			Task task = (Task) assignable;
			String taskName = task.getName();
			ExecutionImpl executionImpl = (ExecutionImpl) execution;
			ProcessDefinitionImpl processDefinition = executionImpl
					.getProcessDefinition();
			String processKey = processDefinition.getKey();
			String deployId = processDefinition.getDeploymentId();
			ExtProcessDef processDef = ServiceUtil.getInstance()
					.getExtProcessDefService().queryOne(deployId);

			ExtProcessRoleDef extProcessRoleDef = ServiceUtil.getInstance()
					.getProcessRoleDefService().queryOne(processKey, taskName);

			// 查询关联的角色信息
			Set<ExtBusinessRoleMapping> roleMappings = extProcessRoleDef.getMappings();
			StringBuilder allAssignee = new StringBuilder();
			// 安排任务处理人
			if (!CollectionUtils.isEmpty(roleMappings)) {
				ExtBusinessRoleMapping[] arrays = new ExtBusinessRoleMapping[roleMappings.size()];
				roleMappings.toArray(arrays);
				for (int index = 0; index < arrays.length; index++) {
					ExtBusinessRoleMapping map = arrays[index];
					assignable.addCandidateUser(map.getBusinessRoleId());
					if (index > 0) {
						allAssignee.append(",");
					}
					allAssignee.append(map.getBusinessRole());

				}

			}
			if (task instanceof TaskImpl) {
				TaskImpl taskImpl = (TaskImpl) task;
				taskImpl.setVariable("processDefinition.key",
						processDefinition.getKey());
				taskImpl.setVariable("processDefinition.name",
						processDefinition.getName());
				taskImpl.setVariable("processDefinition.id",
						processDefinition.getId());
				taskImpl.setVariable("processDefinition.png",
						processDef.getProcessPng());
				taskImpl.setVariable("processDefinition.zip",
						processDef.getProcessZip());
				taskImpl.setVariable("allAssignee", allAssignee.toString());
			}
		}
	}

}
