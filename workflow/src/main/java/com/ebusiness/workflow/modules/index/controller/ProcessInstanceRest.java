/**  
 * @Title: ProcessInstanceRest.java
 * @Package com.ebusiness.workflow.modules.index.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月12日 下午4:55:10
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.workflow.cfg.WorkflowCfg;
import com.ebusiness.workflow.ext.ExtAfterTaskDoLog;

/**
 * @ClassName: ProcessInstanceRest
 * @Description: 流程实例相关的逻辑处理
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月12日 下午4:55:10
 * 
 */
@Controller
public class ProcessInstanceRest {

	@Resource(name = "executionService")
	private ExecutionService executionService;

	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;

	@Resource(name = "workflowCfg")
	private WorkflowCfg workflowCfg;

	@RequestMapping(method = RequestMethod.GET, value = "/workflow/processInstance", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String deploymentid = request.getParameter("deploymentid");

		if (StringUtils.isBlank(deploymentid)) {
			queryInfo.put("error", true);
			queryInfo.put("message", "error request.");
		} else {

			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery().deploymentId(deploymentid)
					.uniqueResult();
			List<ProcessInstance> processInstanceList = executionService
					.createProcessInstanceQuery()
					.processDefinitionId(processDefinition.getId()).list();

			WorkflowDataUtil.genProcessInstance(processInstanceList);
			queryInfo.put("error", false);
			queryInfo.put("data",
					WorkflowDataUtil.genProcessInstance(processInstanceList));
		}
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/workflow/processInstance", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String processInstanceId = request.getParameter("processInstanceId");
		try{
		if (StringUtils.isBlank(processInstanceId)) {
			queryInfo.put("error", true);
			queryInfo.put("message", "error request.");
		} else {
			executionService.deleteProcessInstance(processInstanceId);
			queryInfo.put("error", false);
			StringBuilder message = new StringBuilder();
			message.append("实例ID：");
			message.append(processInstanceId);
			afterLog("删除实例", message.toString(), "成功", request);
		}
		}catch(Exception e){
			e.printStackTrace();
			StringBuilder message = new StringBuilder();
			message.append("实例ID：");
			message.append(processInstanceId);
			message.append("，");
			message.append("错误信息：");
			message.append(e);
			afterLog("删除实例", message.toString(), "失败", request);
		}
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/workflow/processInstance", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> redo(@RequestBody Map<String, Object> bodyParam,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String processInstanceId = bodyParam.get("processInstanceId") + "";

		try {
			if (StringUtils.isBlank(processInstanceId)) {
				queryInfo.put("error", true);
				queryInfo.put("message", "非法请求！");
			} else {
				ProcessInstance processInstance = executionService
						.createProcessInstanceQuery()
						.processInstanceId(processInstanceId).uniqueResult();
				Set<String> sets = executionService
						.getVariableNames(processInstanceId);
				Map<String, Object> params = executionService.getVariables(
						processInstanceId, sets);
				executionService.signalExecutionById(processInstanceId,
						processInstance.getState(), params);
				queryInfo.put("error", false);
				StringBuilder message = new StringBuilder();
				message.append("实例ID：");
				message.append(processInstanceId);
				afterLog("删除实例", message.toString(), "成功", request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
			StringBuilder message = new StringBuilder();
			message.append("实例ID：");
			message.append(processInstanceId);
			message.append("，");
			message.append("错误信息：");
			message.append(e);
			afterLog("删除实例", message.toString(), "失败", request);
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
}
