/**  
 * @Title: UserApplicationTaskService.java
 * @Package com.modules.business_center.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年1月22日 下午3:01:50
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.workflow.service;

import java.util.Map;

import com.github.pagehelper.Page;

/**
 * @ClassName: UserApplicationTaskService
 * @Description: 用户任务申请查询
 * @author zhuliyong zly@entstudy.com
 * @date 2015年1月22日 下午3:01:50
 * 
 */
public interface UserTaskService {
	void insertApplication(Map<String, Object> userApplication)
			throws Exception;

	void updateApplication(Map<String, Object> userApplication)
			throws Exception;

	Page<Map<String, Object>> queryApplication(
			Map<String, Object> userApplication) throws Exception;

	int queryLockShenPiStatus(String orderId) throws Exception;

	void deleteApplication(Long applicationId) throws Exception;

	Page<Map<String, Object>> queryBranchApplication(
			Map<String, Object> userApplication) throws Exception;
}
