package com.edu.erp.workflow.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edu.erp.employee.service.EmployeeMgrService;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.erp.model.EmployeeInfo;
import com.edu.erp.workflow.service.UserTaskService;
import com.ebusiness.workflow.common.ServiceUtil;
import com.ebusiness.workflow.modules.assign.DefaultAssignTask;
import com.ebusiness.workflow.modules.index.model.ExtBusinessRoleMapping;
import com.ebusiness.workflow.modules.index.model.ExtProcessDef;
import com.ebusiness.workflow.modules.index.model.ExtProcessRoleDef;

public class OrgBranchAssignTask extends DefaultAssignTask {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -2871982496356363288L;

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
			ExtProcessDef processDef = ServiceUtil
					.getInstance().getExtProcessDefService().queryOne(deployId);

			ExtProcessRoleDef extProcessRoleDef = ServiceUtil
					.getInstance().getProcessRoleDefService()
					.queryOne(processKey, taskName);
			Long branch_id = null;
			String remark = "";
			if (task instanceof TaskImpl) {
				TaskImpl taskImpl = (TaskImpl) task;
				taskImpl.setVariable("processDefinition.key",
						processDefinition.getKey());
				branch_id = (Long) taskImpl.getVariable("branch_id");
				remark = (String) taskImpl.getVariable("remark");
			}
			// 查询关联的角色信息
			Set<ExtBusinessRoleMapping> roleMappings = extProcessRoleDef
					.getMappings();
			StringBuilder allAssignee = new StringBuilder();
			Set<String> allAssigneeSet = new HashSet<String>();
			// 安排任务处理人
			if (!CollectionUtils.isEmpty(roleMappings)) {
				ExtBusinessRoleMapping[] arrays = new ExtBusinessRoleMapping[roleMappings
						.size()];
				roleMappings.toArray(arrays);
				for (int index = 0; index < arrays.length; index++) {
					ExtBusinessRoleMapping map = arrays[index];

					List<EmployeeInfo> users = queryBranchRole(branch_id,
							Long.parseLong(map.getBusinessRoleId()));
					if (!CollectionUtils.isEmpty(users)) {
						for (EmployeeInfo employeeInfo : users) {
							assignable
									.addCandidateUser(map.getBusinessRoleId());
							// 发送短信通知
							sendMessage(
									employeeInfo,
									"[" + processDefinition.getName() + "][任务："
											+ task.getName() + "][任务ID："
											+ task.getId() + "]"
											+ (null != remark ? remark : ""),
									task);
							allAssigneeSet.add(employeeInfo.getEmployee_name());
						}

					}
				}

			}

			if (!CollectionUtils.isEmpty(allAssigneeSet)) {
				Iterator<String> iterator = allAssigneeSet.iterator();
				int index = 0;
				while (iterator.hasNext()) {
					if (index > 0) {
						allAssignee.append(",");
					}
					allAssignee.append(iterator.next());
					index++;
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
				updateStepStatus(taskImpl, allAssignee.toString());
			}
		}
	}

	private List<EmployeeInfo> queryBranchRole(Long branch_id, Long roleId)
			throws Exception {
		EmployeeMgrService employeeInfoService = (EmployeeMgrService) ApplicationContextUtil
				.getBean("employeeMgrService");

		// List<EmployeeInfo> users = employeeInfoService
		// .queryEmployeeInfoByOrgAndRole(branch_id, roleId);
		List<EmployeeInfo> users = new ArrayList<EmployeeInfo>();
		if (roleId != null) {
			//EmployeeInfo user = employeeInfoService.queryEmpInfoById(roleId);
			//users.add(user);
			EmployeeInfo user = employeeInfoService.queryEmpInfoByOrgIdAndId(roleId, branch_id);
			if(user != null)
				users.add(user);
		}
		return users;
	}

	private void sendMessage(EmployeeInfo user, String message, Task task) {
		/*
		 * Map userMap = new HashMap<>(); try {
		 * 
		 * // LPE-20151123--获取用户个人配置 1为开关打开，不是1的不允许发送 if (userMap != null &&
		 * StringUtil.nullToBlank(userMap.get("PARAM_VAL")).equals( "1")) { if
		 * (StringUtils.isNotBlank(user.getPhone())) { try {
		 * MessageRequirementService messageRequirementService =
		 * (MessageRequirementService) ApplicationContextUtil
		 * .getBean("messageRequirementService");
		 * messageRequirementService.sendMessage( "收到订单审批任务，请尽快处理！任务信息：[ERPV4]"
		 * + message + "。", user.getPhone(), "ERPV4WORFLOW" + task.getId()); }
		 * catch (Exception e) { e.printStackTrace(); } } } } catch (Exception
		 * e1) { e1.printStackTrace(); }
		 */
	}

	private void updateStepStatus(TaskImpl task, String allAssignee) throws Exception {
		String application_id = task.getVariable("application_id") + "";
		if (StringUtils.isNotBlank(application_id)
				&& !"null".equals(application_id)) {
			Map<String, Object> userApplication = new HashMap<String, Object>();
			userApplication.put("ID", application_id);
			userApplication.put("CURRENT_STEP", task.getName());
			userApplication.put("STATUS", 1);
			userApplication.put("CURRENT_STATE", "");
			userApplication.put("CURRENT_MAN", allAssignee);
			try {
				UserTaskService userApplicationTaskService = (UserTaskService) ApplicationContextUtil
						.getBean("userTaskService");
				userApplicationTaskService.updateApplication(userApplication);
			} catch (Exception e) {
				throw e;
			}
		}

	}
}
