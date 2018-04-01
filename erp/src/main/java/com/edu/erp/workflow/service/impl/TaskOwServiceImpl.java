package com.edu.erp.workflow.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.TaskOwServiceDao;
import com.edu.erp.workflow.business.MapType;
import com.edu.erp.workflow.business.TaskBusiness;
import com.edu.erp.workflow.service.TaskOwService;
import com.github.pagehelper.Page;

@Service("taskOwService")
public class TaskOwServiceImpl implements TaskOwService {

	@Resource(name = "taskOwServiceDao")
	private TaskOwServiceDao taskServiceOwDao;
	
	@Override
	public List<TaskBusiness> findGroupTasksWithCdts(String userId,
			Map<String, Object> params) throws Exception {
		try {
			return taskServiceOwDao.findGroupTasksWithCdts(userId, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public long countGroupTasksWithCdts(String userId,
			Map<String, Object> params) throws Exception {
		try {
			return taskServiceOwDao.countGroupTasksWithCdts(userId, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<TaskBusiness> findTasksWithBranch(Map<String, Object> params)
			throws Exception {
		try {
			return taskServiceOwDao.findTasksWithBranch(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Page<TaskBusiness> findBranchTaskPage(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("branch_id"));
		
		return taskServiceOwDao.findBranchTasks(param);
	}

	public Page<TaskBusiness> findTasks(Map<String, Object> param)
			throws Exception {
		return taskServiceOwDao.findTasks(param);
	}

	@Override
	public List<MapType> findVariables(Map<String, Object> param)
			throws Exception {
		return taskServiceOwDao.findVariables(param);
	}

    @Override
    public TaskBusiness findTaskByOrderId(Map<String, Object> param) throws Exception {
        return taskServiceOwDao.findTaskByOrderId(param);
    }

}
