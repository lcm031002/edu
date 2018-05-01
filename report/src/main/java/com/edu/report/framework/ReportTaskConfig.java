/**  
 * @Title: ReportTaskConfig.java
 * @Package com.edu.report.framework
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:37:35
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.edu.cas.client.common.right.RightLoader;
import com.edu.report.framework.cfg.ReportCfg;
import com.edu.report.framework.cfg.Table;
import com.edu.report.framework.cfg.Task;
import com.edu.report.util.XMLUtil;

/**
 * @ClassName: ReportTaskConfig
 * @Description: 报表任务配置
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月24日 下午4:37:35
 * 
 */
public class ReportTaskConfig {

	private static final Logger log = Logger.getLogger(ReportTaskConfig.class);

	private static final ReportTaskConfig INSTANCE = new ReportTaskConfig();

	private static final String CFG_XML = "/plugins/report/cfg.xml";

	private ReportCfg cfg = new ReportCfg();
	
	private ReportTaskConfig(){}

	public static ReportTaskConfig genInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * @Title: load
	 * @Description: 初始化加载配置文件 设定文件 void 返回类型
	 * 
	 */
	public void load() {
		if (log.isDebugEnabled()) {
			log.debug("begin to load report cfg.");
		}

		URL jsonFile = RightLoader.class.getResource(CFG_XML);
		if (StringUtils.isEmpty(jsonFile.getFile())) {
			log.error(CFG_XML + " is not found.");
			return;
		}

		ReportCfg reportCfg = (ReportCfg) XMLUtil.convertXmlFileToObject(
				jsonFile, ReportCfg.class, Table.class, Task.class);

		ReportTaskConfig.genInstance().setCfg(reportCfg);

		if (log.isDebugEnabled()) {
			log.debug("end to load report cfg.");
		}
	}

	public final ReportCfg getCfg() {
		return cfg;
	}

	public final void setCfg(ReportCfg cfg) {
		this.cfg = cfg;
	}

	public static void main(String[] args) {
		ReportTaskConfig.genInstance().load();
	}
}
