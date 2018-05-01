/**  
 * @Title: ReportEngine.java
 * @Package com.edu.report
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:07:36
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName: ReportEngine
 * @Description: 报表引擎
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月24日 下午4:07:36
 * 
 */
@Service
public class ReportEngine implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger log = Logger.getLogger(ReportEngine.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org
	 * .springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("begin to load report.");
		}

		// 报表任务配置
		ReportTaskConfig.genInstance().load();

		// 开启报表任务线程池
		ReportTaskThreadPools.genInstance().start();

		if (log.isDebugEnabled()) {
			log.debug("end to load report.");
		}

	}

}
