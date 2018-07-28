package com.edu.report.framework;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.edu.report.framework.tasks.thread.ReportTaskManager;

/**
 * @ClassName: ReportTaskThreadPools
 * @Description: 报表任务线程池
 *
 */
public class ReportTaskThreadPools {
	
	
	private static final Logger log = Logger.getLogger(ReportTaskThreadPools.class);
	
	private static final ReportTaskThreadPools INSTANCE = new ReportTaskThreadPools();
	
	private static final ScheduledExecutorService taskManager = Executors
			.newScheduledThreadPool(1);
	
	private ReportTaskThreadPools(){}
	
	public static final ReportTaskThreadPools genInstance(){
		return INSTANCE;
	}
	
	public void start(){
		if(log.isDebugEnabled()){
			log.debug("begin to start Thread Pool.");
		}
		
		taskManager.scheduleAtFixedRate(new ReportTaskManager(), 1,
				30, TimeUnit.SECONDS);
		
		if(log.isDebugEnabled()){
			log.debug("end to start Thread Pool.");
		}
	}
}
