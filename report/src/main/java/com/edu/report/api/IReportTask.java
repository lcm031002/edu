package com.edu.report.api;

/**
 * @ClassName: IReportTask
 * @Description: 报班任务接口
 *
 */
public interface IReportTask {
    String COMPLETED = "completed";
    
	public void run(String taskId,String taskFlow,Long pageSize,String startId) throws Exception;
}
