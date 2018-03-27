/**  
 * @Title: TCOrderCourseDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月19日 下午10:14:56
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TOrderCourseTimes;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCChangeCourse;
import com.edu.erp.model.TCCourseTimes;
import com.edu.erp.model.TCOrderCourse;

/**
 * @ClassName: TCOrderCourseDao
 * @Description: 订单批改课次信息
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月19日 下午10:14:56
 * 
 */
@Repository("tCOrderCourseDao")
public interface TCOrderCourseDao {
	/**
	 * 
	 * @Title: queryOrderCourseChangeTimes
	 * @Description: 查询单一课程的批改课次信息
	 * @param orderCourseId
	 *            课程详情id
	 * @return 返回批改课次信息
	 * @throws Exception
	 *             设定文件 List<TCOrderCourse> 返回类型
	 * 
	 */
	List<TCOrderCourse> queryOrderCourseChangeTimes(Long orderCourseId)
			throws Exception;
	
	/**
	 * 
	 * @Title: queryAllChangeTimes
	 * @Description: 查询批改对应的所有的课次信息
	 * @param changeId 批改ID
	 * @return 返回批改信息
	 * @throws Exception    设定文件
	 * List<TCOrderCourse>    返回类型
	 *
	 */
	List<TCOrderCourse> queryAllChangeTimes(Long changeId)
			throws Exception;
	
	/**
	 * 
	 * @Title: queryAllOrderChangeTimes
	 * @Description: 查询所有订单的批改记录信息
	 * @param orderId 订单ID
	 * @return 批改订单记录
	 * @throws Exception    设定文件
	 * List<TCOrderCourse>    返回类型
	 *
	 */
	List<TCOrderCourse> queryAllOrderChangeTimes(Long orderId)
			throws Exception;
	/**
	 * 查询出课程的总课次与剩余课时信息
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	List<TCOrderCourse> queryTcOrderCourseByChangeId(Long changeId) throws Exception;
	/**
	 * 查询出页面退费课程数据信息
	 * @param queryParam
	 * 
	 * @return
	 * @throws Exception
	 */
	List<TCChangeCourse> queryTCChangeCourse(Map<String, Object> queryParam) throws Exception;
	/**
	 * 保存订单课程
	 * @param tCOrderCourse
	 * @throws Exception
	 */
	void saveTcOrderCourse(TCOrderCourse tCOrderCourse) throws Exception;

    void saveTcOrderCourseTimesByChangeNo(Map<String, Object> paramMap) throws Exception;
    
    Integer queryCourseLockCount(Long orderCourseId) throws Exception;
    
    Integer queryCourseTimesLockCount(Long orderCourseId) throws Exception;
    /**
     * 通过change_id 查询课程课次信息
     * @param orderChangeId
     * @return
     * @throws Exception
     */
    List<TCCourseTimes> queryTcOrderCourseTimesByChangeId(Long orderChangeId) throws Exception;

	List<TOrderCourseTimes> queryOrderCourseTimesByChangeId(Long orderChangeId) throws Exception;

}
