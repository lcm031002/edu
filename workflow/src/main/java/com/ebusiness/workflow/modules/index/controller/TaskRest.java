/**  
 * @Title: TaskRest.java
 * @Package com.ebusiness.workflow.modules.index.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年11月28日 下午2:13:42
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryComment;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.workflow.cfg.WorkflowCfg;
import com.ebusiness.workflow.common.DataUtil;
import com.ebusiness.workflow.common.DateUtil;
import com.ebusiness.workflow.ext.BeforeCompleteTask;
import com.ebusiness.workflow.ext.ExtAfterTaskDoLog;
import com.ebusiness.workflow.ext.ExtCurrentUserInfo;
import com.ebusiness.workflow.modules.index.model.ExtBusinessEntrust;
import com.ebusiness.workflow.modules.index.model.ExtTask;
import com.ebusiness.workflow.modules.index.service.ExtBusinessEntrustService;
import com.ebusiness.workflow.modules.index.service.ExtTaskService;
import com.ebusiness.workflow.modules.index.service.LocalProcessService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: TaskRest
 * @Description: 工作任务列表，以及任务处理相关的操作
 * @author zhuliyong zly@entstudy.com
 * @date 2014年11月28日 下午2:13:42
 */
@Controller
public class TaskRest {
	private static final Logger log = LoggerFactory.getLogger(TaskRest.class);

	@Resource(name = "taskService")
	private TaskService taskService;

	@Resource(name = "workflowCfg")
	private WorkflowCfg workflowCfg;

	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;

	@Resource(name = "historyService")
	private HistoryService historyService;

	@Resource(name = "processLocalService")
	private ExtBusinessEntrustService businessEntrustService;

	@Resource(name = "processLocalService")
	private LocalProcessService localProcessService;

	@Resource(name = "processEngine")
	private ProcessEngine processEngine;

	@Resource(name = "extTaskService")
	private ExtTaskService extTaskService;

	@RequestMapping(method = RequestMethod.GET, value = "/workflow/task/todo_change", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryHistoryEntrust(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String consignorRole = request.getParameter("consignorRole");
		String consigneeTaskId = request.getParameter("consigneeTaskId");
		List<ExtBusinessEntrust> history = businessEntrustService
				.queryHistoryEntrust(consignorRole,consigneeTaskId);
		queryInfo.put("error", false);
		queryInfo.put("data", history);
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/workflow/task/todo_change", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> addEntrust(@RequestBody Map<String, Object> entrust,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> queryInfo = new HashMap<String, Object>();
		List<ExtBusinessEntrust> list = genEntrusts(entrust);
		String consigneeTaskId = (String) entrust.get("consigneeTaskId");
		Task task = taskService.getTask(consigneeTaskId);
		if (task != null) {
			for (ExtBusinessEntrust businessEntrust : list) {
				businessEntrust.setTaskName(task.getName());
				businessEntrust.setCreateTime(DateUtil
						.getCurrDateOfDate("yyyy-MM-dd hh:ss"));
				taskService.addTaskParticipatingUser(
						businessEntrust.getConsigneeTaskId(),
						businessEntrust.getConsigneeRole(),
						Participation.CANDIDATE);
				taskService.addTaskParticipatingUser(
						businessEntrust.getConsigneeTaskId(),
						businessEntrust.getConsignorRole(),
						Participation.VIEWER);
				businessEntrustService.addBusinessEntrust(businessEntrust);
			}
		}
		queryInfo.put("error", false);
		return queryInfo;
	}

	@SuppressWarnings("unchecked")
	private List<ExtBusinessEntrust> genEntrusts(Map<String, Object> entrust) {
		List<ExtBusinessEntrust> entrustList = new ArrayList<ExtBusinessEntrust>();
		if (!CollectionUtils.isEmpty(entrust)) {
			String beginDate = (String) entrust.get("beginDate");
			String endDate = (String) entrust.get("endDate");
			String consignorRole = (String) entrust.get("consignorRole");
			List<String> employees = (List<String>) entrust.get("employees");
			List<String> employeesName = (List<String>) entrust
					.get("employeesName");

			String consigneeTaskId = (String) entrust.get("consigneeTaskId");
			if (!CollectionUtils.isEmpty(employees)
					&& !CollectionUtils.isEmpty(employeesName)) {
				for (int index = 0; index < employees.size(); index++) {
					Object employee = employees.get(index);
					Object employeeName = employeesName.get(index);
					ExtBusinessEntrust businessEntrust = new ExtBusinessEntrust();
					businessEntrust.setBeginTime(DateUtil.stringToDate(
							beginDate, "yyyy-MM-dd"));
					businessEntrust.setEndTime(DateUtil.stringToDate(endDate,
							"yyyy-MM-dd"));
					businessEntrust.setConsignorRole(consignorRole);
					businessEntrust.setConsigneeTaskId(consigneeTaskId);
					businessEntrust.setConsigneeRole(employee + "");
					businessEntrust.setConsigneeName(employeeName + "");
					businessEntrust.setStatus(1);

					entrustList.add(businessEntrust);
				}
			}

		}

		return entrustList;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/rest/history_entrusts", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> cancelEntrust(@RequestBody ExtBusinessEntrust entrust,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		businessEntrustService.cancelExtBusinessEntrust(entrust);
		queryInfo.put("error", false);
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/workflow/taskinfos", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();

		try {
			request.setCharacterEncoding("UTF-8");
			String searcherName = URLDecoder.decode(StringUtils.defaultIfEmpty(
					request.getParameter("searcherName"), ""));
			Map<String, Object> obj = new HashMap<String, Object>();
			// obj.put("searchName", searcherName);
			List<ExtTask> taskListResult = extTaskService.queryTaskList(obj);
			/*
			 * List<Task> taskList = taskService.createTaskQuery()
			 * .orderDesc("createTime").list(); List<Map<String, Object>>
			 * resultList = WorkflowDataUtil .genTaskResultMap(taskList,
			 * taskService);
			 * 
			 * List<Map<String, Object>> resultFilterList = new
			 * ArrayList<Map<String, Object>>();
			 * 
			 * resultFilterList.addAll(resultList);
			 * 
			 * if (!StringUtils.isEmpty(searcherName)) { for (Map<String,
			 * Object> task : resultList) { if (!DataUtil.match(task,
			 * searcherName)) { resultFilterList.remove(task); } } }
			 */
			if (!StringUtils.isEmpty(searcherName)) {
				Iterator<ExtTask> iterator = taskListResult.iterator();
				while (iterator.hasNext()) {
					ExtTask task = iterator.next();
					if (!DataUtil.match(task, searcherName)) {
						iterator.remove();
					}
				}
			}
			int beginNo = 0;
			int endNo = 20;
			int count = 0;
			List<ExtTask> resultList = new ArrayList<ExtTask>();
			if (!CollectionUtils.isEmpty(taskListResult)) {
				count = taskListResult.size();
				String begin = request.getParameter("beginNo");
				String end = request.getParameter("endNo");

				if (!StringUtils.isEmpty(begin)) {
					beginNo = Integer.parseInt(begin);
				}

				if (!StringUtils.isEmpty(end)) {
					endNo = Integer.parseInt(end);
				}

				if (beginNo > count) {
					beginNo = (int) count;
				}
				if (endNo > count) {
					endNo = (int) count;
				}
				for (int index = beginNo; index < endNo; index++) {
					resultList.add(taskListResult.get(index));
				}
			} else {
				resultList = taskListResult;
			}

			queryInfo.put("error", false);
			queryInfo.put("data", resultList);
			queryInfo.put("totalCount", count);
			queryInfo.put("beginNo", beginNo);
			queryInfo.put("endNo", endNo);
		} catch (Exception e) {
			queryInfo.put("error", false);
			queryInfo.put("message", e.getMessage());
		}
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/workflow/taskinfos/group", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryGroupTasks(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String userName = request.getParameter("userName");
		if (StringUtils.isBlank(userName)) {
			throw new IllegalArgumentException("invalid request");
		}
		List<Task> taskList = taskService.findGroupTasks(userName);
		queryInfo.put("error", false);
		queryInfo.put("data",
				WorkflowDataUtil.genTaskResultMap(taskList, taskService));
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/workflow/taskinfos/personal", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryPersonalTasks(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String userName = request.getParameter("userName");
		if (StringUtils.isBlank(userName)) {
			throw new IllegalArgumentException("invalid request");
		}
		List<Task> taskList = taskService.findPersonalTasks(userName);
		queryInfo.put("error", false);
		queryInfo.put("data",
				WorkflowDataUtil.genTaskResultMap(taskList, taskService));
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/workflow/task/complete", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> completeTask(@RequestBody Map<String, String> task,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String taskId = DataUtil.nullToBlank(task.get("taskId"));
		String outcome = task.get("outcome");
		String remark = task.get("remark");
		//存储到session中 在任务分配中去获取
		request.getSession().setAttribute("outcome", outcome);
		request.getSession().setAttribute("remark", remark);
		
		if (StringUtils.isBlank(taskId)) {
			throw new IllegalArgumentException("invalid request");
		}
		if (!StringUtils.isBlank(remark)) {
			taskService.addTaskComment(taskId, remark);
		}
		Map<String, Object> vaMap = new HashMap<String, Object>();
		if (!StringUtils.isBlank(remark)) {
			vaMap.put("remark", remark);
		} else {
			vaMap.put("remark", "");
		}
		Task taskObj = taskService.getTask(taskId);
		settingsCurrentUser(taskObj, request, response);
		beforeCompleteTask(taskObj, outcome, vaMap);

		StringBuilder messageStr = new StringBuilder();
		messageStr.append("任务ID：");
		messageStr.append(taskObj.getId());
		messageStr.append("，");
		messageStr.append("任务名称：");
		messageStr.append(taskObj.getName());
		messageStr.append("，");
		messageStr.append("处理选择：");
		messageStr.append(outcome);
		messageStr.append("，");
		messageStr.append("备注：");
		messageStr.append(remark);

		if (!StringUtils.isBlank(outcome)) {
			taskService.completeTask(taskId, outcome, vaMap);
		} else {
			taskService.completeTask(taskId, vaMap);
		}
		afterLog("流程审批", messageStr.toString(), "成功", request);

		queryInfo.put("error", false);
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/workflow/task/new", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> startTask(@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMaps = new HashMap<String, Object>();
		if (params.get("processKey") == null) {
			resultMaps.put("error", true);
			resultMaps.put("message", "流程key不能为空！");
			return resultMaps;
		}

		String processKey = params.get("processKey") + "";

		if (StringUtils.isEmpty(processKey)) {
			resultMaps.put("error", true);
			resultMaps.put("message", "流程key不能为空！");
			return resultMaps;
		}

		ProcessInstance processInstance = processEngine.getExecutionService()
				.startProcessInstanceByKey(processKey, params);
		processEngine.getExecutionService().createVariables(
				processInstance.getId(), params, true);
		String processInstanceId = processInstance.getId();
		Map<String,String> data = new HashMap<String,String>();
		data.put("processInstanceId", processInstanceId);
		resultMaps.put("data", data);
		
		
		afterLog("流程定义", "流程创建", "成功", request);

		resultMaps.put("error", false);
		return resultMaps;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/workflow/task/new", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryStartTask(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMaps = new HashMap<String, Object>();
		resultMaps.put("error", false);
		resultMaps.put("message", "工作流正常，可以访问！");
		return resultMaps;
	}

	private void settingsCurrentUser(Task task, HttpServletRequest request,
			HttpServletResponse response) {
		String classname = workflowCfg.getExt_current_user_getter();
		if (!StringUtils.isBlank(classname)) {
			if ("${workflow.ext.ext_current_user_getter}".equals(classname)) {
				classname = "com.ebusiness.workflow.ext.impl.DefaultExtCurrentUserGet";
			}
			try {
				Object userGetter = Class.forName(classname.trim())
						.newInstance();
				if (userGetter instanceof ExtCurrentUserInfo) {
					ExtCurrentUserInfo extCurrentUserInfo = (ExtCurrentUserInfo) userGetter;
					Map<String, Object> userInfo = extCurrentUserInfo
							.genCurrentUserInfo(task, request, response);
					if (!CollectionUtils.isEmpty(userInfo)) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("laststep_user_id", userInfo.get("id"));
						params.put("laststep_username",
								userInfo.get("employeeName"));
						taskService.takeTask(task.getId(),
								userInfo.get("employeeName") + "");
						taskService.setVariables(task.getId(), params);
					}
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void beforeCompleteTask(Task task, String outcome,
			Map<String, Object> params) {
		String classname = workflowCfg.getBefore_complete_task();
		if (!StringUtils.isBlank(classname)) {
			if ("${workflow.ext.before_complete_task}".equals(classname)) {
				return;
			}
			try {
				Object beforeCompleteTask = Class.forName(classname.trim())
						.newInstance();
				if (beforeCompleteTask instanceof BeforeCompleteTask) {
					BeforeCompleteTask completeTask = (BeforeCompleteTask) beforeCompleteTask;
					completeTask.doBefore(task, outcome, params, processEngine);
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/workflow/task/outcomes", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryTaskOutcomes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		try {

			String taskId = request.getParameter("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new IllegalArgumentException("invalid request");
			}
			Task task = taskService.getTask(taskId);
			String activityName = task.getActivityName();
			Set<String> outcomes = taskService.getOutcomes(taskId);
			String processPng = (String) taskService.getVariable(taskId,
					"processDefinition.png");
			String processId = (String) taskService.getVariable(taskId,
					"processDefinition.id");
			String processZip = (String) taskService.getVariable(taskId,
					"processDefinition.zip");

			ActivityCoordinates activityCoordinates = repositoryService
					.getActivityCoordinates(processId, activityName);

			if (StringUtils.isBlank(processPng)
					|| StringUtils.isBlank(processZip)) {
				throw new Exception("error workflow data");
			}
			String deployedPath = workflowCfg.genJPDLSrcDownloadPath() + "/"
					+ processZip.substring(0, processZip.indexOf(".zip"));
			queryInfo.put("error", false);
			queryInfo.put("task",
					WorkflowDataUtil.genTaskResultMap(task, taskService));
			queryInfo.put("data", outcomes);
			queryInfo.put("task_png", deployedPath + "/" + processPng);
			queryInfo.put("activityCoordinates", WorkflowDataUtil
					.genActivityCoordinates(activityCoordinates));

			List<HistoryTask> historyTasks = historyService
					.createHistoryTaskQuery()
					.executionId(task.getExecutionId()).orderDesc("createTime")
					.list();
			queryInfo
					.put("historyTasks", WorkflowDataUtil.genHistoryTasks(
							historyTasks, taskService));
			List<HistoryComment> historyCommentList = taskService
					.getTaskComments(taskId);
			queryInfo.put("historyCommentList",
					WorkflowDataUtil.genTasksRemark(historyCommentList));
		} catch (Exception e) {
			log.error("error found,see:", e);
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
		}
		return queryInfo;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/workflow/task", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryTaskInfos(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		try {

			String taskId = request.getParameter("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new IllegalArgumentException("invalid request");
			}
			Task task = taskService.getTask(taskId);
			String activityName = task.getActivityName();
			Set<String> outcomes = taskService.getOutcomes(taskId);
			String processPng = (String) taskService.getVariable(taskId,
					"processDefinition.png");
			String processId = (String) taskService.getVariable(taskId,
					"processDefinition.id");
			String processZip = (String) taskService.getVariable(taskId,
					"processDefinition.zip");

			ActivityCoordinates activityCoordinates = repositoryService
					.getActivityCoordinates(processId, activityName);

			if (StringUtils.isBlank(processPng)
					|| StringUtils.isBlank(processZip)) {
				throw new Exception("error workflow data");
			}
			String deployedPath = workflowCfg.genJPDLSrcDownloadPath() + "/"
					+ processZip.substring(0, processZip.indexOf(".zip"));
			queryInfo.put("error", false);
			queryInfo.put("task",
					WorkflowDataUtil.genTaskResultMap(task, taskService));
			queryInfo.put("data", outcomes);
			queryInfo.put("task_png", deployedPath + "/" + processPng);
			queryInfo.put("activityCoordinates", WorkflowDataUtil
					.genActivityCoordinates(activityCoordinates));

			List<HistoryTask> historyTasks = historyService
					.createHistoryTaskQuery()
					.executionId(task.getExecutionId()).orderDesc("createTime")
					.list();
			queryInfo
					.put("historyTasks", WorkflowDataUtil.genHistoryTasks(
							historyTasks, taskService));
			List<HistoryComment> historyCommentList = taskService
					.getTaskComments(taskId);
			queryInfo.put("historyCommentList",
					WorkflowDataUtil.genTasksRemark(historyCommentList));
		} catch (Exception e) {
			log.error("error found,see:", e);
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
		}
		return queryInfo;
	}

	private void afterLog(String businessName, String message, String status,
			HttpServletRequest request) {
		String log = workflowCfg.getExt_after_task_do_log();
		if (StringUtils.isNotBlank(log)) {
			Object logExt = null;
			try {
				logExt = Class.forName(log.trim()).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (logExt instanceof ExtAfterTaskDoLog) {
				ExtAfterTaskDoLog taskLog = (ExtAfterTaskDoLog) logExt;
				taskLog.log(businessName, message, status, request);
			}
		}
	}

	protected Integer genIntParameter(String name, HttpServletRequest request) {
		String param = request.getParameter(name);
		Integer longParam = null;
		try {
			if (StringUtils.isNotBlank(param)) {
				longParam = Integer.parseInt(param);
			}
		} catch (Exception e) {
			log.error("error found,see:", e);
		}
		return longParam;
	}
}
