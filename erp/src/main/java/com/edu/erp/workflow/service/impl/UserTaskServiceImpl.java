package com.edu.erp.workflow.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.edu.erp.dao.UserTaskAppicationDao;
import com.edu.erp.workflow.service.UserTaskService;
import com.github.pagehelper.Page;

/**
 * @ClassName: UserApplicationTaskServiceImpl
 * @Description: 用户申请查询
 *
 */
@Repository("userTaskService")
public class UserTaskServiceImpl implements
		UserTaskService {
	@Resource(name = "userTaskAppicationDao")
	private UserTaskAppicationDao userTaskAppicationDao;
	
	@Override
	public void insertApplication(Map<String, Object> userApplication)
			throws Exception {
		userTaskAppicationDao.insertApplication(userApplication);
	}

	@Override
	public void updateApplication(Map<String, Object> userApplication)
			throws Exception {
		userTaskAppicationDao.updateApplication(userApplication);
	}

	
	@Override
	public Page<Map<String, Object>> queryApplication(
			Map<String, Object> userApplication) throws Exception {
		Assert.notNull(userApplication);
		Assert.notNull(userApplication.get("APPLICATION_ID"));
		return userTaskAppicationDao.queryApplication(userApplication);
	}
	
	@Override
	public Page<Map<String, Object>> queryBranchApplication(
			Map<String, Object> userApplication) throws Exception {
		Assert.notNull(userApplication);
		return userTaskAppicationDao.queryApplication(userApplication);
	}

	@Override
	public int queryLockShenPiStatus(String orderId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId",orderId);
		
		int row = userTaskAppicationDao.queryLockShenPiStatus(param);
		return row;
	}

	@Override
	public void deleteApplication(Long applicationId) throws Exception {
		Assert.notNull(applicationId);
		userTaskAppicationDao.deleteApplication(applicationId);
	}
}
