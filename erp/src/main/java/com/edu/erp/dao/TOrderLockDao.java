/**  
 * @Title: TOrderLockDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月22日 下午3:53:05
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TOrderLock;

/**
 * @ClassName: TOrderLockDao
 * @Description: 查询订单冻结状态
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月22日 下午3:53:05
 * 
 */
@Repository(value = "tOrderLockDao")
public interface TOrderLockDao {
	/**
	 * 
	 * @Title: queryOrderLockStatus
	 * @Description: 查询订单的锁定状态
	 * @param orderId
	 *            订单ID
	 * @return 返回订单的冻结状态
	 * @throws Exception
	 *             设定文件 TOrderLock 返回类型
	 * 
	 */
	TOrderLock queryOrderLockStatus(Long orderId) throws Exception;
	
	/**
	 * 
	 * @Title: insertOrderLockStatus
	 * @Description: 新增订单锁定状态
	 * @param lock 订单锁
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void insertOrderLockStatus(TOrderLock lock) throws Exception;
	
	/**
	 * 
	 * @Title: updateOrderLockStatus
	 * @Description: 修改订单锁状态
	 * @param lock 订单锁
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void updateOrderLockStatus(TOrderLock lock) throws Exception;
	/**
	 * 查询订单锁
	 * @return
	 * @throws Exception
	 */
	List<TOrderLock> queryLockInfoByOrderId(Map<String, Object> param) throws Exception;
	/**
	 * 新增订单锁信息
	 * @param lock
	 * @throws Exception
	 */
	void insertOrderLock(TOrderLock lock) throws Exception;
	/**
	 * 新增订单历史锁信息
	 * @param lock
	 * @throws Exception
	 */
	void insertOrderLockHt(TOrderLock lock) throws Exception;
	/**
	 * 查询剩余课次以及价格
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> countOrderCourse(TOrderLock lock) throws Exception;
	
	/**
	 * 查询锁定订单列表（锁定订单结转用）
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<TOrderLock> selectLockedOrderList(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据课程详情ID查询学生所报课次 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryCourseTimesByOrderDetail(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据 课程Id查询晚辅导的排课
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryCourseScheduling_wfd(Map<String, Object> map) throws Exception;
}
