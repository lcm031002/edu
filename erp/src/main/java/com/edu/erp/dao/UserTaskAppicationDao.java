/**  
 * @Title: UserApplicationTaskDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月12日 下午4:42:57
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

/**
 * @ClassName: UserApplicationTaskDao
 * @Description: 工作流应用的dao
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月12日 下午4:42:57
 * 
 */
@Repository(value = "userTaskAppicationDao")
public interface UserTaskAppicationDao {
	void insertApplication(Map<String, Object> userApplication)
			throws Exception;
	void updateApplication(Map<String, Object> userApplication)
			throws Exception;
	void deleteApplication(Long applicationId)
			throws Exception;
	Page<Map<String, Object>> queryApplication(
            Map<String, Object> userApplication) throws Exception;
	List<Map<String, Object>> queryApplicationById(
            Map<String, Object> userApplication) throws Exception;
	
	int queryLockShenPiStatus(Map<String, Object> param) throws Exception;

}