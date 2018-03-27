/**  
 * @Title: TPScheduleTimeDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月20日 下午7:22:17
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import com.edu.erp.model.TPScheduleTime;
import com.edu.erp.model.TPScheduleTime;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: TPScheduleTimeDao
 * @Description: 排课档期Dao
 * @author yecb
 * @date 2017年8月28日
 *
 */
@Repository(value = "tpScheduleTimeDao")
public interface TPScheduleTimeDao {
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TPScheduleTime> selectForPage(Page<TPScheduleTime> page) throws Exception;
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> selectForPage(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<TPScheduleTime> selectList(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer insert(TPScheduleTime pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer update(TPScheduleTime pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer changeStatus(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增排课档期关系
	 * 
	 * @param refs
	 * @return
	 * @throws Exception
	 */
	Integer insertOrgRef(List<Map<String, Long>> refs) throws Exception;

}
