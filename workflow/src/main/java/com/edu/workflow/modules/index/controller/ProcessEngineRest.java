package com.edu.workflow.modules.index.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ProcessEngineRest
 * @Description: 工作流服务，提供流程起停，任务查询和任务推进等服务
 *
 */
@Controller
public class ProcessEngineRest {
	@Resource(name = "executionService")
	private ExecutionService executionService;

	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;

	@Resource(name = "taskService")
	private TaskService taskService;

	/**
	 * 
	 * @Title: deployProcess
	 * @Description: 发布给定名称的流程
	 * @param request
	 *            参数对象：引用对象
	 *            <p>
	 *            参数名：processFile;
	 *            </p>
	 *            <p>
	 *            格式:流程名称.zip；
	 *            </p>
	 *            <p>
	 *            内容：流程图片.png、*.jpdl.xml
	 *            </p>
	 * @param response
	 * @param @throws Exception 设定文件
	 * @return Map<String,Object> 返回类型
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/rest/processStart/{processKey}", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> startProcess(@PathVariable String processKey,
			@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validKey(processKey);
			ProcessInstance processInstance = executionService
					.startProcessInstanceByKey(processKey, param);
			result.put("error", true);
			result.put("processInstance", genProcessInstance(processInstance));
		} catch (Exception e) {
			result.put("error", false);
			result.put("message", "invalid request!");
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/queryPersonalTasks", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> startProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String roleName = request.getParameter("roleName");
			if (StringUtils.isBlank(roleName)) {
				throw new IllegalArgumentException("Invalid request!");
			}
			List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
			// 获取关联的角色 TODO
			Set<String> allRules = new HashSet<String>();
			TaskQuery taskQuery = taskService.createTaskQuery();
			List<Task> allTask = taskQuery.list();
			if (!CollectionUtils.isEmpty(allTask)) {
				for (Task task : allTask) {
					if (allRules.contains(task.getAssignee())) {
						taskList.add(genTask(task));
					}
				}
			}
			result.put("error", false);
			result.put("data", taskList);
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", "invalid request!");
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/queryTaskVariables/{taskId}", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryVariables(@PathVariable String taskId,
			@RequestBody List<String> paramInfo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(taskId)) {
				throw new IllegalArgumentException("Invalid request!");
			}

			if (!CollectionUtils.isEmpty(paramInfo)) {
				Set<String> variableNames = new HashSet<String>();
				variableNames.addAll(paramInfo);
				Map<String, Object> data = taskService.getVariables(taskId,
						variableNames);
				result.put("data", data);
			}

			result.put("error", false);
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", "invalid request!");
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/rest/taskservice/{path}/{taskId}", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> taskCompleteService(@PathVariable String taskId,
			@PathVariable String path,
			@RequestBody Map<String, Object> paramInfo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(taskId)) {
				throw new IllegalArgumentException("Invalid request!");
			}

			if (!CollectionUtils.isEmpty(paramInfo)) {
				taskService.completeTask(taskId, path, paramInfo);
			} else {
				taskService.completeTask(taskId, path);
			}
			result.put("error", false);
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", "invalid request!");
		}
		return result;
	}

	private Map<String, Object> genTask(Task task) {
		Map<String, Object> taskData = new HashMap<String, Object>();
		taskData.put("id", task.getId());
		taskData.put("name", task.getName());
		taskData.put("progress", task.getProgress());
		taskData.put("description", task.getDescription());
		taskData.put("createtime", task.getCreateTime());
		taskData.put("duedate", task.getDuedate());
		taskData.put("executionId", task.getExecutionId());
		return taskData;
	}

	private Map<String, Object> genProcessInstance(
			ProcessInstance processInstance) {
		Map<String, Object> processInstanceDef = new HashMap<String, Object>();
		processInstanceDef.put("id", processInstance.getId());
		processInstanceDef.put("key", processInstance.getKey());
		processInstanceDef.put("name", processInstance.getName());
		processInstanceDef.put("state", processInstance.getState());

		return processInstanceDef;
	}

	private void validKey(String processKey) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey(processKey);
		if (null == processDefinitionQuery) {
			throw new IllegalArgumentException("invalid request!");
		}
		List<ProcessDefinition> processDefQuery = processDefinitionQuery.list();
		if (CollectionUtils.isEmpty(processDefQuery)) {
			throw new IllegalArgumentException("empty process definitions!");
		}

	}
}
