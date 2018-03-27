package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.workflow.business.MapType;
import com.edu.erp.workflow.business.TaskBusiness;
import com.github.pagehelper.Page;

@Repository(value = "taskOwServiceDao")
public interface TaskOwServiceDao {

	public List<TaskBusiness> findGroupTasksWithCdts(String userId,
                                                     Map<String, Object> params) throws Exception;

	/**
	 * count number of tasks that can be taken by the given user and conditions.
	 * Returns an empty list in case no such tasks exist.
	 * 
	 * @param userId
	 * @return
	 */
	long countGroupTasksWithCdts(String userId, Map<String, Object> params)
			throws Exception;

	/**
	 * 校区任务列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<TaskBusiness> findTasksWithBranch(Map<String, Object> params)
			throws Exception;

	/**
	 * 查询校区任务
	 * @param page
	 * @param sql_ID
	 * @return
	 * @throws Exception
	 */
	Page<TaskBusiness> findBranchTasks(Map<String, Object> params) throws Exception;

	List<MapType> findVariables(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: findTasks
	 * @Description: 待我审批的任务列表
	 * @param param 查询参数
	 * @return 待我审批的任务
	 * @throws Exception    设定文件
	 * Page<TaskBusiness>    返回类型
	 *
	 */
	Page<TaskBusiness> findTasks(Map<String, Object> param) throws Exception;
	
	TaskBusiness findTaskByOrderId(Map<String, Object> param) throws Exception;
}
