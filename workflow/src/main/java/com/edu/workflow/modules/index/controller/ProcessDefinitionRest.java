package com.edu.workflow.modules.index.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.workflow.cfg.WorkflowCfg;
import com.edu.workflow.common.FileUtil;
import com.edu.workflow.ext.ExtAfterTaskDoLog;
import com.edu.workflow.modules.index.model.ExtProcessDef;
import com.edu.workflow.modules.index.service.ExtProcessDefService;

/**
 * @ClassName: ProcessDefinitionRest
 * @Description: 流程定义信息
 *
 */
@Controller
public class ProcessDefinitionRest {
	private static final Logger log = LoggerFactory
			.getLogger(ProcessDefinitionRest.class);

	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;

	@Resource(name = "executionService")
	private ExecutionService executionService;

	@Resource(name = "processLocalService")
	private ExtProcessDefService processDefService;

	@Resource(name = "workflowCfg")
	private WorkflowCfg workflowCfg;

	/**
	 * 
	 * @Title: query
	 * @Description: 查询流程定义信息
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param @throws Exception 设定文件
	 * @return Map<String,Object> 返回类型
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/workflow/deployedProcessDefinition", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		try {
			List<ProcessDefinition> processDef = repositoryService
					.createProcessDefinitionQuery().list();
			queryInfo.put("error", false);
			queryInfo.put("data", WorkflowDataUtil.genResultMap(processDef));
		} catch (Exception e) {
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
		}
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/workflow/deployedProcessDefinition", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> post(@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String id = (String) params.get("id");
		try {

			if (StringUtils.isBlank(id)) {
				queryInfo.put("error", true);
				queryInfo.put("message", "error request.");
				StringBuilder messageStr = new StringBuilder();
				messageStr.append("启动流程实例：");
				messageStr.append(id);
				afterLog("发起测试流程", messageStr.toString(), "失败", request);
			} else {
				params.remove(id);
				ProcessInstance processInstance = executionService
						.startProcessInstanceById(id);
				queryInfo.put("error", false);
				queryInfo.put("processInstanceId", processInstance.getId());
				StringBuilder messageStr = new StringBuilder();
				messageStr.append("启动流程实例：");
				messageStr.append(id);

				afterLog("发起测试流程", messageStr.toString(), "成功", request);
			}
		} catch (Exception e) {
			e.printStackTrace();

			StringBuilder messageStr = new StringBuilder();
			messageStr.append("启动流程实例：");
			messageStr.append(id);

			messageStr.append("，");
			messageStr.append("错误信息：");
			messageStr.append(e);
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
			afterLog("发起测试流程", messageStr.toString(), "失败", request);
		}
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/workflow/deployedProcessDefinition", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String deploymentid = request.getParameter("deploymentid");
		String deleteType = request.getParameter("deleteType");
		try {
			if (StringUtils.isBlank(deploymentid)) {
				queryInfo.put("error", true);
				queryInfo.put("message", "error request.");
			} else {

				if ("force".equals(deleteType)) {
					// 删除实例
					ProcessDefinition processDefinition = repositoryService
							.createProcessDefinitionQuery()
							.deploymentId(deploymentid).uniqueResult();
					List<ProcessInstance> processInstanceList = executionService
							.createProcessInstanceQuery()
							.processDefinitionId(processDefinition.getId())
							.list();
					for (ProcessInstance processInstance : processInstanceList) {
						try {
							executionService
									.deleteProcessInstance(processInstance
											.getId());
						} catch (Exception e) {
							log.error("error found,see:", e);
							continue;
						}
					}
				}

				// 删除流程定义信息
				repositoryService.deleteDeployment(deploymentid);

				// 查询流程定义信息
				ExtProcessDef extProcessDef = processDefService
						.queryOne(deploymentid);
				deleteDeployedFiles(extProcessDef);
				processDefService.delete(extProcessDef);
				queryInfo.put("error", false);
				/*List<ExtProcessRoleDef> roleDefs = ServiceUtil.getInstance()
						.getProcessRoleDefService()
						.queryByKey(extProcessDef.getProcessKey());
				if(!CollectionUtils.isEmpty(roleDefs)){
					for (ExtProcessRoleDef def : roleDefs) {
						try {
							ServiceUtil.getInstance().getProcessRoleDefService()
									.delete(def);
						} catch (Exception e) {
							log.error("error found,see:", e);
							continue;
						}
					}
				}*/

				StringBuilder messageStr = new StringBuilder();
				messageStr.append("删除流程发布id：");
				messageStr.append(deploymentid);

				afterLog("删除流程", messageStr.toString(), "成功", request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
			StringBuilder messageStr = new StringBuilder();
			messageStr.append("删除流程发布id：");
			messageStr.append(deploymentid);

			messageStr.append("，");
			messageStr.append("错误信息：");
			messageStr.append(e);

			afterLog("删除流程", messageStr.toString(), "失败", request);
		}
		return queryInfo;
	}

	private void deleteDeployedFiles(ExtProcessDef extProcessDef) {
		String zipFile = extProcessDef.getProcessZip();
		String deployedFile = workflowCfg.getDeployed_dir();
		String file_dir = deployedFile + "/"
				+ zipFile.substring(0, zipFile.indexOf(".zip"));
		File file = new File(file_dir);
		FileUtil.deleteFile(file);
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
