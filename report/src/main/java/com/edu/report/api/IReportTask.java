/**  
 * @Title: IReportTask.java
 * @Package com.edu.report.api
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:39:32
 * @version KLXX ERPV4.0  
 */
package com.edu.report.api;

/**
 * @ClassName: IReportTask
 * @Description: 报班任务接口
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月24日 下午4:39:32
 * 
 */
public interface IReportTask {
    String COMPLETED = "completed";
    
	public void run(String taskId,String taskFlow,Long pageSize,String startId) throws Exception;
}
