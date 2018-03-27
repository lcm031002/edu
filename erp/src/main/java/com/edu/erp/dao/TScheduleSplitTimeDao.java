/**  
 * @Title: TPScheduleTimeDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月20日 下午7:22:17
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import com.edu.erp.model.TScheduleSplitTime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

/**
 * @ClassName: TPScheduleTimeDao
 * @Description: 课时拆分记录Dao
 * @author yecb
 * @date 2017年8月28日
 *
 */
@Repository(value = "tScheduleSplitTimeDao")
public interface TScheduleSplitTimeDao {
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer insert(TScheduleSplitTime pojo) throws Exception;

	ArrayList<TScheduleSplitTime> queryTScheduleSplitTimeList(Map<String, Object> paramMap) throws Exception;

}
