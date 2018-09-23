package com.edu.workflow.modules.index.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.workflow.cfg.WorkflowCfg;
import com.edu.workflow.ext.ExtAfterTaskDoLog;
import com.edu.workflow.modules.index.model.ExtBusinessRoleMapping;
import com.edu.workflow.modules.index.model.ExtProcessRoleDef;
import com.edu.workflow.modules.index.service.BusinessRoleMappingService;
import com.edu.workflow.modules.index.service.LocalProcessService;
import com.edu.workflow.modules.index.service.ProcessRoleDefService;

/**
 * @ClassName: IdentityRest
 * @Description: 查询用户信息
 *
 */
@Controller
public class IdentityRest {
	@Resource(name = "identityService")
	private IdentityService identityService;

	@Resource(name = "processLocalService")
	private ProcessRoleDefService processRoleDefService;

	@Resource(name = "processLocalService")
	private BusinessRoleMappingService businessRoleMappingService;

	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;

	@Resource(name = "processLocalService")
	private LocalProcessService localProcessService;

	@Resource(name = "workflowCfg")
	private WorkflowCfg workflowCfg;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IdentityRest.class);

	/**
	 * 
	 * @Title: query
	 * @Description: 查询工作流用户
	 * @param request
	 * @param response
	 * @throws Exception
	 *             设定文件
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/rest/identity", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		List<User> userList = identityService.findUsers();
		for (User user : userList) {
			user.getId();
		}

		queryInfo.put("error", false);
		return queryInfo;
	}

	/**
	 * 
	 * @Title: queryRoles
	 * @Description: 提供流程角色定义信息查询服务
	 * @param request
	 * @param response
	 * @throws Exception
	 *             设定文件
	 * @return Map<String,Object> 返回类型
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/workflow/processNodeRole", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryRoles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processKey = request.getParameter("processKey");
		List<ExtProcessRoleDef> reuslt = processRoleDefService
				.queryByKey(processKey);
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		queryInfo.put("error", false);
		queryInfo.put("data", reuslt);
		queryInfo.put("ext_query_business_role_url",
				workflowCfg.getExt_query_business_role_url());
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/workflow/rolemapping", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> addRolesMappings(
			@RequestBody ExtBusinessRoleMapping mappingDefined,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == mappingDefined) {
			LOGGER.error("error request!IP:" + request.getRemoteAddr());
			throw new IllegalArgumentException("ivalid request!");
		}
		Map<String, Object> queryInfo = new HashMap<String, Object>();

		try {
			if (!exist(mappingDefined)) {
				businessRoleMappingService.add(mappingDefined);
			}
			queryInfo.put("error", false);

			StringBuilder messageStr = new StringBuilder();
			messageStr.append("角色分配：");
			messageStr.append(mappingDefined.toString());
			afterLog("流程节点角色分配", messageStr.toString(), "成功", request);
		} catch (Exception e) {
			LOGGER.error("error found,see:", e);
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
		}
		return queryInfo;
	}

	/*
	 * TODO低效
	 */
	private boolean exist(ExtBusinessRoleMapping businessRoleMapping) {
		List<ExtBusinessRoleMapping> result = businessRoleMappingService
				.queryByProcessKey(businessRoleMapping);
		if (!CollectionUtils.isEmpty(result)) {
			for (ExtBusinessRoleMapping extBusinessRoleMapping : result) {
				if (extBusinessRoleMapping.equals(businessRoleMapping)) {
					return true;
				}
			}
		}

		return false;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/workflow/rolemapping", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> updateRolesMappings(
			@RequestBody ExtBusinessRoleMapping mappingDefined,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (mappingDefined == null) {
			LOGGER.error("error request!IP:" + request.getRemoteAddr());
			throw new IllegalArgumentException("ivalid request!");
		}
		businessRoleMappingService.update(mappingDefined);
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		queryInfo.put("error", false);
		StringBuilder messageStr = new StringBuilder();
		messageStr.append("角色分配：");
		messageStr.append(mappingDefined.toString());
		afterLog("流程节点角色修改", messageStr.toString(), "成功", request);
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/workflow/rolemapping", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> deleteRolesMappings(@RequestParam Long id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();

		try {
			if (id == null) {
				LOGGER.error("error request!IP:" + request.getRemoteAddr());
				throw new IllegalArgumentException("ivalid request!");
			}
			ExtBusinessRoleMapping mapping = businessRoleMappingService
					.queryOne(id);
			businessRoleMappingService.delete(mapping);
			queryInfo.put("error", false);
			StringBuilder messageStr = new StringBuilder();
			messageStr.append("流程角色关系：");
			messageStr.append(mapping.toString());
			afterLog("流程节点角色删除", messageStr.toString(), "成功", request);
		} catch (Exception e) {
			LOGGER.error("error found,see:", e);
			StringBuilder messageStr = new StringBuilder();
			messageStr.append("流程角色ID：");
			messageStr.append(id);
			afterLog("流程节点角色删除", messageStr.toString(), "失败", request);
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
		}
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/processdef/roles", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryRolesProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String businessRole = request.getParameter("businessRole");
		if (StringUtils.isBlank(businessRole)) {
			throw new IllegalArgumentException("invalid request");
		}
		Map<String, Object> queryInfo = new HashMap<String, Object>();

		List<ProcessDefinition> defList = localProcessService
				.queryUserProcess(businessRole);
		queryInfo.put("error", false);
		queryInfo.put("data", WorkflowDataUtil.genResultMap(defList));
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
