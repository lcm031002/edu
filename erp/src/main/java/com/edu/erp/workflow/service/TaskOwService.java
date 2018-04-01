package com.edu.erp.workflow.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.workflow.business.MapType;
import com.edu.erp.workflow.business.TaskBusiness;
import com.github.pagehelper.Page;

public interface TaskOwService {

  /**
   * list of tasks that can be taken by the given user and conditions.
   * Returns an empty list in case no such tasks exist. 
   * @param userId
   * @return
   */
   List<TaskBusiness> findGroupTasksWithCdts(String userId,Map<String,Object> params) throws Exception;
   
   /**
    * count  number of tasks  that can be taken by the given user and conditions.
    * Returns an empty list in case no such tasks exist. 
    * @param userId
    * @return
    */
   long countGroupTasksWithCdts(String userId,Map<String,Object> params) throws Exception;
   
   
   /**
    * 校区任务查询
    * @param params
    * @return
    * @throws Exception
    */
   public List<TaskBusiness> findTasksWithBranch(Map<String, Object> params) throws Exception;
   
   /**
    * 查询校区任务
    * @param params 查询参数
    * @return 返回校区任务
    * @throws Exception
    */
   Page<TaskBusiness> findBranchTaskPage(Map<String,Object> params) throws Exception;

   List<MapType> findVariables(Map<String, Object> param)throws Exception;
   
   Page<TaskBusiness> findTasks(Map<String, Object> param)
			throws Exception;
   
   TaskBusiness findTaskByOrderId(Map<String, Object> param) throws Exception;
}

