package com.ebusiness.workflow.modules.index.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.RepositoryService;
import org.jbpm.pvm.internal.repository.RepositoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.workflow.cfg.WorkflowCfg;
import com.ebusiness.workflow.common.FileUtil;
import com.ebusiness.workflow.ext.ExtAfterTaskDoLog;
import com.ebusiness.workflow.modules.index.model.ExtProcessDef;
import com.ebusiness.workflow.modules.index.model.ExtProcessRoleDef;
import com.ebusiness.workflow.modules.index.service.ExtProcessDefService;
import com.ebusiness.workflow.modules.index.service.ProcessRoleDefService;
import com.ebusiness.workflow.xml.JaxbReadXml;
import com.ebusiness.workflow.xml.model.JPDLModel;
import com.ebusiness.workflow.xml.model.JPDLTaskModel;

/**
 * @ClassName: ProcessRest
 * @Description: 发布与流程包发布处理相关的服务
 *
 */
@Controller
public class ProcessRest {
	private static final Logger log = LoggerFactory
			.getLogger(ProcessRest.class);

	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;

	@Resource(name = "workflowCfg")
	private WorkflowCfg workflowCfg;

	@Resource(name = "processLocalService")
	private ExtProcessDefService processDefService;

	@Resource(name = "processLocalService")
	private ProcessRoleDefService processRoleDefService;

	@Resource(name = "executionService")
	private ExecutionService executionService;

	/**
	 * 
	 * @Title: query
	 * @Description: 获取给定的目录下的文件信息
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param @throws Exception 设定文件
	 * @return Map<String,Object> 返回类型
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/workflow/processinfo", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> queryInfo = new HashMap<String, Object>();
		List<ExtProcessDef> deployment = processDefService.queryAll();
		queryInfo.put("path", workflowCfg.genJPDLSrcDownloadPath());
		String classpathurl = workflowCfg.genJPDLSrcPath();
		if (StringUtils.isBlank(classpathurl)) {
			throw new IllegalArgumentException("no class url");
		}
		File file = new File(classpathurl);
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		if (file.canRead()) {
			// 如果是目录，则读取目录下的文件
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File fl : files) {
					// 读取zip
					if (fl.getName().endsWith(".zip")) {
						String deployedPath = workflowCfg
								.genJPDLSrcDownloadPath()
								+ "/"
								+ fl.getName().substring(0,
										fl.getName().indexOf(".zip"));
						// zip信息
						Map<String, Object> packageInfo = new HashMap<String, Object>();
						packageInfo.put("file", fl.getName());
						packageInfo.put(
								"name",
								fl.getName().substring(0,
										fl.getName().indexOf(".zip")));
						ExtProcessDef processDef = deployed(fl, deployment);
						if (processDef != null) {
							packageInfo.put("deployed", true);
							packageInfo.put("jpdl", deployedPath + "/"
									+ processDef.getProcessJpdl());
							packageInfo.put("png", deployedPath + "/"
									+ processDef.getProcessPng());
							packageInfo.put("deployId",
									processDef.getProcessDeployId());
							packageInfo
									.put("name", processDef.getProcessName());
							packageInfo.put("key", processDef.getProcessKey());
						} else {
							packageInfo.put("deployed", false);
						}
						fileList.add(packageInfo);
					}
				}
				queryInfo.put("fileList", fileList);
			}
		}
		return queryInfo;
	}

	private ExtProcessDef deployed(File fl, List<ExtProcessDef> deployment) {
		if (CollectionUtils.isEmpty(deployment)) {
			return null;
		} else {
			for (ExtProcessDef deploy : deployment) {
				if (fl.getName().equals(deploy.getProcessZip())
						&& deploy.getProcessState() == 1) {
					return deploy;
				}
			}
			return null;
		}
	}

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
	@RequestMapping(method = RequestMethod.POST, value = "/workflow/processinfo", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> deployProcess(@RequestBody Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> queryInfo = new HashMap<String, Object>();
		String processFileName = null;
		if (param.get("processFile") != null) {
			processFileName = param.get("processFile") + "";
		} else {
			queryInfo.put("error", true);
			queryInfo.put("message", "请选择要发布的流程文件！");
			return queryInfo;
		}

		if (log.isDebugEnabled()) {
			log.debug("begin to deploy process.file is " + processFileName);
		}

		try {

			File processFile = new File(workflowCfg.genJPDLSrcPath() + "/"
					+ processFileName);
			if (!StringUtils.isBlank(processFileName) && processFile.exists()
					&& processFile.getName().endsWith(".zip")) {
				String unzipPath = workflowCfg.genJPDLDeployPath()
						+ "/"
						+ processFileName.substring(0, processFile.getName()
								.indexOf(".zip"));
				// unzip
				FileUtil.unzipFile(unzipPath, processFile);

				if (log.isDebugEnabled()) {
					log.debug("deployed file to path " + unzipPath);
				}

				// create deployed file
				Map<String, String> fileInfo = genProcessDefinition(workflowCfg
						.genJPDLSrcPath() + "/" + processFileName);
				if (log.isDebugEnabled()) {
					log.debug("deployed file info is  " + fileInfo);
				}

				String jpdl = workflowCfg.genJPDLDeployPath()
						+ "/"
						+ processFileName.substring(0, processFile.getName()
								.indexOf(".zip")) + File.separator
						+ fileInfo.get("jpdl");
				File jpdlFile = new File(jpdl);
				if (jpdlFile.exists()) {
					if (log.isDebugEnabled()) {
						log.debug("begin to read jpdl ,see " + jpdl);
					}
					String info = repositoryService.createDeployment()
							.addResourceFromFile(new File(jpdl)).deploy();
					ExtProcessDef processDef = new ExtProcessDef();
					processDef.setProcessZip(processFileName);
					processDefService.delete(processDef);
					if (log.isDebugEnabled()) {
						log.debug("delete old definitions");
					}

					ProcessDefinition processDefinition = repositoryService
							.createProcessDefinitionQuery().deploymentId(info)
							.uniqueResult();
					processDef.setProcessJpdl(fileInfo.get("jpdl"));
					processDef.setProcessPng(fileInfo.get("png"));
					processDef.setProcessState(1);
					processDef.setProcessDeployId(info);
					processDef.setProcessCreateTime(new Date());
					processDef.setProcessKey(processDefinition.getKey());
					processDef.setProcessName(processDefinition.getName());
					addProcessRoleDef(jpdl, processDef);
					
					processDefService.add(processDef);
					if (log.isDebugEnabled()) {
						log.debug("add new definitions");
					}
					
					StringBuilder messageStr = new StringBuilder();
					messageStr.append("流程包：");
					messageStr.append(processFileName);
					afterLog("流程发布", messageStr.toString(), "成功", request);
				}

			} else {
				queryInfo.put("error", true);
				queryInfo.put("message", "");
				StringBuilder messageStr = new StringBuilder();
				messageStr.append("流程包：");
				messageStr.append(processFileName);
				messageStr.append("失败原因：");
				messageStr.append("流程包不存在");
				afterLog("流程发布", messageStr.toString(), "失败", request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder messageStr = new StringBuilder();
			messageStr.append("流程包：");
			messageStr.append(processFileName);
			messageStr.append("，");
			messageStr.append("失败原因：");
			messageStr.append(e);
			afterLog("流程发布", messageStr.toString(), "失败", request);
			throw e;
		}
		return queryInfo;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/workflow/processinfo", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> updateProcess(@RequestBody ExtProcessDef processDef,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> queryInfo = new HashMap<String, Object>();
		if (processDef == null || processDef.getProcessDeployId() == null
				|| processDef.getProcessZip() == null) {
			queryInfo.put("error", true);
			queryInfo.put("message", "请选择要更新的流程！");
			return queryInfo;
		}

		if (log.isDebugEnabled()) {
			log.debug("begin to update process.deployId is "
					+ processDef.getProcessDeployId());
		}

		try {

			File processFile = new File(workflowCfg.genJPDLSrcPath() + "/"
					+ processDef.getProcessZip());
			if (processFile.exists() && processFile.getName().endsWith(".zip")) {
				String unzipPath = workflowCfg.genJPDLDeployPath()
						+ "/"
						+ processDef.getProcessZip().substring(0,
								processFile.getName().indexOf(".zip"));
				// unzip
				FileUtil.unzipFile(unzipPath, processFile);

				if (log.isDebugEnabled()) {
					log.debug("deployed file to path " + unzipPath);
				}

				// create deployed file
				Map<String, String> fileInfo = genProcessDefinition(workflowCfg
						.genJPDLSrcPath() + "/" + processDef.getProcessZip());
				if (log.isDebugEnabled()) {
					log.debug("deployed file info is  " + fileInfo);
				}

				String jpdl = workflowCfg.genJPDLDeployPath()
						+ "/"
						+ processDef.getProcessZip().substring(0,
								processFile.getName().indexOf(".zip"))
						+ File.separator + fileInfo.get("jpdl");
				File jpdlFile = new File(jpdl);
				if (jpdlFile.exists()) {
					if (log.isDebugEnabled()) {
						log.debug("begin to update jpdl ,see " + jpdl);
					}
					repositoryService.deleteDeployment(processDef
							.getProcessDeployId());
					String info = repositoryService.createDeployment()
							.addResourceFromFile(new File(jpdl)).deploy();
					
					ExtProcessDef processDefUpdate = new ExtProcessDef();
					
					processDefUpdate.setProcessDeployId(info);

					/*
					 * RepositoryServiceImpl repositoryServiceImpl =
					 * ((RepositoryServiceImpl) repositoryService);
					 * 
					 * repositoryServiceImpl.updateDeploymentResource(
					 * processDef.getProcessDeployId(), jpdl, new
					 * FileInputStream(jpdlFile));
					 */
					
					processDefUpdate.setProcessZip(processDef.getProcessZip());
					processDefService.delete(processDefUpdate);
					if (log.isDebugEnabled()) {
						log.debug("delete old definitions");
					}

					ProcessDefinition processDefinition = repositoryService
							.createProcessDefinitionQuery().deploymentId(processDefUpdate.getProcessDeployId())
							.uniqueResult();
					processDefUpdate.setProcessJpdl(fileInfo.get("jpdl"));
					processDefUpdate.setProcessPng(fileInfo.get("png"));
					processDefUpdate.setProcessState(1);
					processDefUpdate.setProcessCreateTime(new Date());
					processDefUpdate.setProcessKey(processDefinition.getKey());
					processDefUpdate.setProcessName(processDefinition.getName());
					addProcessRoleDef(jpdl, processDefUpdate);
					
					processDefService.add(processDefUpdate);
					if (log.isDebugEnabled()) {
						log.debug("add new definitions");
					}
					
					StringBuilder messageStr = new StringBuilder();
					messageStr.append("流程包：");
					messageStr.append(processDefUpdate.getProcessZip());
					afterLog("流程发布", messageStr.toString(), "成功", request);

					if (log.isDebugEnabled()) {
						log.debug("end to update jpdl");
					}
				}

			} else {
				queryInfo.put("error", true);
				queryInfo.put("message", "流程包资源文件不存在或文件错误！");
				StringBuilder messageStr = new StringBuilder();
				messageStr.append("流程包：");
				messageStr.append(processDef.getProcessZip());
				messageStr.append("失败原因：");
				messageStr.append("流程包不存在");
				afterLog("流程发布", messageStr.toString(), "失败", request);
			}
		} catch (DuplicateKeyException e) {
			log.error("流程更新失败，see:", e);
			StringBuilder messageStr = new StringBuilder();
			messageStr.append("流程包：");
			messageStr.append(processDef.getProcessZip());
			messageStr.append("，");
			messageStr.append("失败原因：");
			messageStr.append(e);
			afterLog("流程发布", messageStr.toString(), "失败", request);
			queryInfo.put("error", true);
			queryInfo.put("message", "流程包资源文件一样，不允许更新！");
		} catch (Exception e) {
			log.error("流程更新失败，see:", e);
			StringBuilder messageStr = new StringBuilder();
			messageStr.append("流程包：");
			messageStr.append(processDef.getProcessZip());
			messageStr.append("，");
			messageStr.append("失败原因：");
			messageStr.append(e);
			afterLog("流程发布", messageStr.toString(), "失败", request);
			queryInfo.put("error", true);
			queryInfo.put("message", e.getMessage());
		}
		return queryInfo;
	}

	private void addProcessRoleDef(String jpdl, ExtProcessDef processDef)
			throws FileNotFoundException {
		if (log.isDebugEnabled()) {
			log.debug("begin read jpdl tasks.");
		}
		JPDLModel jpdlModel = JaxbReadXml.readXMLDom4J(new FileInputStream(
				new File(jpdl)));
		if (null != jpdlModel && !CollectionUtils.isEmpty(jpdlModel.getTask())) {
			if(processDef.getProcessRoleDefSet()==null){
				processDef.setProcessRoleDefSet(new HashSet<ExtProcessRoleDef>());
			}
			for (JPDLTaskModel taskDef : jpdlModel.getTask()) {
				ExtProcessRoleDef entity = new ExtProcessRoleDef();
				entity.setProcessKey(processDef.getProcessKey());
				entity.setProcessTask(taskDef.getName());
				entity.setRemark("");
				processDef.getProcessRoleDefSet().add(entity);
				//processRoleDefService.add(entity);
			}
			if (log.isDebugEnabled()) {
				log.debug("read jpdl succeed.");
			}
		} else {
			log.error(" read jpdl tasks failed.");
		}

	}

	private Map<String, String> genProcessDefinition(String file)
			throws ZipException, IOException {
		return genProcessDefinition(new File(file));
	}

	private Map<String, String> genProcessDefinition(File file)
			throws ZipException, IOException {
		Map<String, String> dataInfo = new HashMap<String, String>();
		@SuppressWarnings("resource")
		ZipFile zip_file = new ZipFile(file);
		Enumeration<? extends ZipEntry> entries = zip_file.entries();
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = entries.nextElement();
			String name = zipEntry.getName();
			if (name.endsWith(".jpdl.xml")) {
				dataInfo.put("jpdl", name);
			} else if (name.endsWith(".png")) {
				dataInfo.put("png", name);
			}
			System.out.println(name);
		}
		return dataInfo;
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
