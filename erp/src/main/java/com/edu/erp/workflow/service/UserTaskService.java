package com.edu.erp.workflow.service;

import java.util.Map;

import com.github.pagehelper.Page;

/**
 * @ClassName: UserApplicationTaskService
 * @Description: 用户任务申请查询
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
