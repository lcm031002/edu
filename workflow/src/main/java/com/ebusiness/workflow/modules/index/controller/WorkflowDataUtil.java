package com.ebusiness.workflow.modules.index.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.history.HistoryComment;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.springframework.util.CollectionUtils;

/**
 * @ClassName: Util
 * @Description: 工具类
 *
 */
public class WorkflowDataUtil {

	public static List<Map<String, Object>> genResultMap(
			List<ProcessDefinition> processDef) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (ProcessDefinition processDefinition : processDef) {
			Map<String, Object> def = new HashMap<String, Object>();
			def.put("id", processDefinition.getId());
			def.put("key", processDefinition.getKey());
			def.put("name", processDefinition.getName());
			def.put("version", processDefinition.getVersion());
			def.put("deploymentid", processDefinition.getDeploymentId());
			def.put("description", processDefinition.getDescription());
			def.put("imageResourceName",
					processDefinition.getImageResourceName());
			def.put("suspended", processDefinition.isSuspended());
			result.add(def);
		}
		return result;
	}

	public static List<Map<String, Object>> genTaskResultMap(
			List<Task> taskList, TaskService taskService) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Task task : taskList) {
			result.add(genTaskResultMap(task, taskService));
		}
		return result;
	}

	public static List<Map<String, Object>> genHistoryTaskResultMap(
			List<HistoryTask> taskList, ProcessEngine processEngine)
			throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (HistoryTask task : taskList) {
			result.add(genHistoryTaskResultMap(task, processEngine));
		}
		return result;
	}

	public static Map<String, Object> genHistoryTaskResultMap(HistoryTask task,
			ProcessEngine processEngine) throws Exception {
		Map<String, Object> def = new HashMap<String, Object>();
		def.put("id", task.getId());
		def.put("assignee", task.getAssignee());
		def.put("createTime", task.getCreateTime());
		def.put("endTime", task.getEndTime());
		def.put("execution", task.getExecutionId());
		def.put("state", task.getState());

		HistoryTaskInstanceImpl historyTaskInstance = getHistoryTaskInstanceByTaskId(
				task.getId(), processEngine);
		def.put("activityName", historyTaskInstance.getActivityName());
		def.put("name", historyTaskInstance.getActivityName());
		def.put("transitionName", historyTaskInstance.getTransitionName());
		HistoryProcessInstanceImpl historyProcessInstanceImpl = getHistoryDefinitionByTaskId(
				task.getId(), processEngine);
		String defID = historyProcessInstanceImpl.getProcessDefinitionId();
		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionId(defID).uniqueResult();
		def.put("processKey", processDefinition.getKey());
		def.put("processName", processDefinition.getName());
		
		//查询当前状态
		Execution execution = processEngine.getExecutionService()
				.findExecutionById(task.getExecutionId());
		if (null != execution) {
			Execution instance = execution.getProcessInstance();
			if (instance instanceof ExecutionImpl) {
				def.put("currrntActivityName",
						((ExecutionImpl) instance).getActivityName());
			}
		}
		return def;
	}

	public static HistoryTaskInstanceImpl getHistoryTaskInstanceByTaskId(
			final String taskId, ProcessEngine processEngine) {
		return processEngine.execute(new Command<HistoryTaskInstanceImpl>() {
			private static final long serialVersionUID = 1L;

			@Override
			public HistoryTaskInstanceImpl execute(Environment environment)
					throws Exception {
				Session session = environment.get(Session.class);
				StringBuilder hql = new StringBuilder();
				hql.append("select hti from ").append(
						HistoryTaskInstanceImpl.class.getName());
				hql.append(" as hti ");
				hql.append("where hti.historyTask.dbid = :taskDbid");
				return (HistoryTaskInstanceImpl) session
						.createQuery(hql.toString())
						.setLong("taskDbid", Long.valueOf(taskId))
						.uniqueResult();
			}
		});
	}

	public static HistoryProcessInstanceImpl getHistoryDefinitionByTaskId(
			final String taskId, ProcessEngine processEngine) {
		return processEngine.execute(new Command<HistoryProcessInstanceImpl>() {
			private static final long serialVersionUID = 1L;

			@Override
			public HistoryProcessInstanceImpl execute(Environment environment)
					throws Exception {
				Session session = environment.get(Session.class);
				StringBuilder hql = new StringBuilder();
				hql.append("select hti.historyProcessInstance from ").append(
						HistoryTaskInstanceImpl.class.getName());
				hql.append(" as hti ");
				hql.append("where hti.historyTask.dbid = :taskDbid");
				return (HistoryProcessInstanceImpl) session
						.createQuery(hql.toString())
						.setLong("taskDbid", Long.valueOf(taskId))
						.uniqueResult();
			}
		});
	}

	public static Map<String, Object> genTaskResultMap(Task task,
			TaskService taskService) throws Exception {
		Map<String, Object> def = new HashMap<String, Object>();
		def.put("id", task.getId());
		def.put("name", task.getName());
		def.put("assignee", task.getAssignee());
		def.put("description", task.getDescription());
		def.put("duedate", task.getDuedate());
		def.put("createTime", task.getCreateTime());
		def.put("progress", task.getProgress());
		def.put("priority", task.getPriority());
		def.put("execution", task.getExecutionId());

		Set<String> keys = taskService.getVariableNames(task.getId());
		Map<String, Object> params = taskService.getVariables(task.getId(),
				keys);
		String key = (String) params.get("processDefinition.key");
		def.put("processKey", key);
		String name = (String) params.get("processDefinition.name");
		def.put("processName", name);
		String allAssignee = (String) params.get("allAssignee");
		def.put("allAssignee", allAssignee);
		String workbenchURL = (String) params.get("workbenchURL");
		def.put("workbenchURL", workbenchURL);
		String remark = (String) params.get("remark");
		def.put("remark", remark);
		def.put("extData", params);
		return def;
	}

	public static Map<String, Object> genActivityCoordinates(
			ActivityCoordinates activityCoordinates) {
		int x = activityCoordinates.getX();
		int y = activityCoordinates.getY();
		int width = activityCoordinates.getWidth();
		int height = activityCoordinates.getHeight();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", x);
		map.put("y", y);
		map.put("width", width);
		map.put("height", height);
		return map;
	}

	public static List<Map<String, Object>> genHistoryTasks(
			List<HistoryTask> historyTasks, TaskService taskService) {
		List<Map<String, Object>> historys = new ArrayList<Map<String, Object>>();

		for (HistoryTask historyTask : historyTasks) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", historyTask.getId());
			map.put("outcome", historyTask.getOutcome());
			map.put("assignee", historyTask.getAssignee());
			map.put("createTime", historyTask.getCreateTime());
			map.put("state", historyTask.getState());
			List<HistoryComment> historyComments = taskService
					.getTaskComments(historyTask.getId());
			if (!CollectionUtils.isEmpty(historyComments)) {
				map.put("remark", historyComments.get(0).getMessage());
			}
			historys.add(map);
		}
		return historys;
	}

	public static List<Map<String, Object>> genTasksRemark(
			List<HistoryComment> historyComments) {
		List<Map<String, Object>> historys = new ArrayList<Map<String, Object>>();

		for (HistoryComment historyComment : historyComments) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", historyComment.getId());
			map.put("userId", historyComment.getUserId());
			map.put("message", historyComment.getMessage());
			map.put("createTime", historyComment.getTime());
			historys.add(map);
		}
		return historys;
	}

	public static List<Map<String, Object>> genProcessInstance(
			List<ProcessInstance> processInstanceList) {
		List<Map<String, Object>> historys = new ArrayList<Map<String, Object>>();

		for (ProcessInstance processInstance : processInstanceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", processInstance.getId());
			map.put("name", processInstance.getName());
			map.put("state", processInstance.getState());
			historys.add(map);
		}
		return historys;
	}
}
