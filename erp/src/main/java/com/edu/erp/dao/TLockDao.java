/**  
 * @Title: TLockDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月12日 下午3:43:47
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TLock;

/**
 * @ClassName: TLockDao
 * @Description: 查询锁
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月12日 下午3:43:47
 * 
 */
@Repository(value = "tLockDao")
public interface TLockDao {
	/**
	 * 
	 * @Title: queryTLockOrderInfo
	 * @Description: 查询业务锁
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>    返回类型
	 *
	 */
	List<Map<String, Object>> queryTLockOrderInfo(Map<String, Object> param)
			throws Exception;
	/**
	 * 行级独占锁
	 * @param param  order_id ：订单ID
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryLockOrderId(Map<String, Object> param) throws Exception;
	
	/**
	 * 判断当前订单是否被锁定
	 * @param param  order_id :订单id;lock_type:批改类型
	 * @return
	 * @throws Exception
	 */
	int queryLockOrderFlag(Map<String, Object> param) throws Exception;
	/**
	 * 是否有课程被锁定
	 * @param param change_id
	 * @return
	 * @throws Exception
	 */
	int checkCourseLock(Map<String, Object> param) throws Exception;
	/**
	 * 是否有有课次被锁定
	 * @param param change_id
	 * @return
	 * @throws Exception
	 */
	int checkCourseTimesLock(Map<String, Object> param) throws Exception;
	/**
	 * 加锁，锁课程
	 * @return
	 * @throws Exception
	 */
	int  saveLock(TLock tLock) throws Exception;
	
	void releaseLock(TLock tLock) throws Exception;

	/**
	 * 查询课程锁信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> queryCourseLock(Map<String, Object> param) throws Exception;
}
