package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TOrderCourseTimes;

/**
 * @ClassName: TOrderCourseTimesDao
 * @Description: 订单详单课次信息
 *
 */
@Repository(value = "tOrderCourseTimesDao")
public interface TOrderCourseTimesDao {
	/**
	 * 
	 * @Title: queryAllTOrderCourseTimes
	 * @Description: 订单下所有的课次的信息
	 * @param orderId 订单ID
	 * @return    设定文件
	 * List<TOrderCourseTimes>    返回类型
	 *
	 */
	List<TOrderCourseTimes> queryAllTOrderCourseTimes(Long orderId);
	
	/**
	 * 
	 * @Title: queryOrderCourseTimes
	 * @Description: 查询课程的课次信息
	 * @param orderDetailId 课程的详单id
	 * @return    设定文件
	 * List<TOrderCourseTimes>    返回类型
	 *
	 */
	List<TOrderCourseTimes> queryOrderCourseTimes(Long orderDetailId);
	
	/**
	 * 
	 * @Title: queryOrderCourseTimes
	 * @Description: 查询课程的课次信息
	 * @param param   (orderDetailId 课程的详单id,Times  课次）
	 * @return    设定文件
	 * List<TOrderCourseTimes>    返回类型
	 *
	 */
	List<TOrderCourseTimes> queryOrderCourseTimesByMap(Map<String, Object> param);
	
	/**
	 * 
	 * @Title: queryOrderCourseTimesInfo
	 * @Description: 查询课程的课次信息
	 * @param orderDetailId 详单id
	 * @return    设定文件
	 * List<Map<String,Object>>    返回类型
	 *
	 */
	List<Map<String, Object>> queryOrderCourseTimesInfo(Long orderDetailId);
	/**
	 * 更新课程课次信息
	 * @param param
	 */
	void updateTOrderCourseTimes(Map<String, Object> param) throws Exception;

	void addByTabOrderId(Long orderId) throws Exception;
}
