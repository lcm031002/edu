/**  
 * @Title: WorkflowCfg.java
 * @Package com.ebusiness.workflow.cfg
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年11月24日 下午6:22:52
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.cfg;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: WorkflowCfg
 * @Description: 工作流相关的配置文件信息
 * @author zhuliyong zly@entstudy.com
 * @date 2014年11月24日 下午6:22:52
 * 
 */
public class WorkflowCfg {
	private static final String FILE_PATH = "/workflow/web/wffile";
	private static final String FILE_PATH_DEPLOY = "/workflow/web/deploy";
	private static final String FILE_PATH_DOWNLOAD_DEPLOY = "/workflow/web/deploy";

	private String jpdl_zip_dir;

	private String deployed_dir;

	private String deployed_download_dir;
	
	private String ext_query_business_role_url;
	
	private String ext_current_user_getter;
	
	private String before_complete_task;
	
	private String ext_after_task_do_log;

	public String getJpdl_zip_dir() {
		return jpdl_zip_dir;
	}

	public void setJpdl_zip_dir(String jpdl_zip_dir) {
		this.jpdl_zip_dir = jpdl_zip_dir;
	}

	public String getDeployed_dir() {
		return deployed_dir;
	}

	public void setDeployed_dir(String deployed_dir) {
		this.deployed_dir = deployed_dir;
	}

	public String getDeployed_download_dir() {
		return deployed_download_dir;
	}

	public void setDeployed_download_dir(String deployed_download_dir) {
		this.deployed_download_dir = deployed_download_dir;
	}

	public String genJPDLSrcPath() {
		//TODO
		return StringUtils.isBlank(jpdl_zip_dir) ? this.getClass()
				.getResource(FILE_PATH).getPath() : jpdl_zip_dir.trim();
	}

	public String genJPDLDeployPath() {
		//TODO
		return StringUtils.isBlank(deployed_dir) ? this.getClass()
				.getResource(FILE_PATH_DEPLOY).getPath() : deployed_dir.trim();
	}

	public String genJPDLSrcDownloadPath() {
		//TODO
		return StringUtils.isBlank(deployed_download_dir) ? this.getClass()
				.getResource(FILE_PATH_DOWNLOAD_DEPLOY).getPath()
				: deployed_download_dir.trim();
	}

	public String getExt_query_business_role_url() {
		return ext_query_business_role_url;
	}

	public void setExt_query_business_role_url(String ext_query_business_role_url) {
		this.ext_query_business_role_url = ext_query_business_role_url;
	}

	public String getExt_current_user_getter() {
		return ext_current_user_getter;
	}

	public void setExt_current_user_getter(String ext_current_user_getter) {
		this.ext_current_user_getter = ext_current_user_getter;
	}

	public String getBefore_complete_task() {
		return before_complete_task;
	}

	public void setBefore_complete_task(String before_complete_task) {
		this.before_complete_task = before_complete_task;
	}

	public String getExt_after_task_do_log() {
		return ext_after_task_do_log;
	}

	public void setExt_after_task_do_log(String ext_after_task_do_log) {
		this.ext_after_task_do_log = ext_after_task_do_log;
	}
	
	
}
