/**  
 * @Title: TCourseSchedulingAssistDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月16日 下午9:36:35
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCourseSchedulingAssist;

/**
 * @ClassName: TCourseSchedulingAssistDao
 * @Description: 查询课程排课参数
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月16日 下午9:36:35
 * 
 */
@Repository(value = "tCourseSchedulingAssistDao")
public interface TCourseSchedulingAssistDao {
	/**
	 * 
	 * @Title: queryCourseSchedulingAssist
	 * @Description: 查询课程排课参数
	 * @param courseId
	 *            课程ID
	 * @return 课程排课参数
	 * @throws Exception
	 *             设定文件 List<TCourseSchedulingAssist> 返回类型
	 * 
	 */
	List<TCourseSchedulingAssist> queryCourseSchedulingAssist(Long courseId)
			throws Exception;
	/**
	 * 
	 * @Title: queryCourseTimeSchedulingAssist
	 * @Description: 查询课程课次参数
	 * @param param courseId,课程id，
	 * 	courseTime，课次序号
	 * @return
	 * @throws Exception    设定文件
	 * List<TCourseSchedulingAssist>    返回类型
	 *
	 */
	List<TCourseSchedulingAssist> queryCourseTimeSchedulingAssist(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 
	 * @Title: add
	 * @Description: 添加配置参数
	 * @param schedulingAssist
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void addCourseSchedulingAssist(TCourseSchedulingAssist schedulingAssist) throws Exception;
	
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除配置参数
	 * @param courseId 课程id
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void deleteCourseSchedulingAssist(Long courseId) throws Exception;

	/**
	 * 根据排课id查询高级参数信息
	 * @param schedulingId
	 * @return
	 */
	List<TCourseSchedulingAssist> queryCourseSchedulingTimeAssist(Long schedulingId);

	/**
	 * 根据排课id删除高级参数信息
	 * @param schedulingId
	 */
	void deleteCourseSchedulingTimeAssist(@Param("schedulingId") Long schedulingId);

	/**
	 * 根据排课id删除高级参数信息
	 * @param schedulingId
	 */
	void batchDeleteCourseSchedulingTimeAssist(@Param("schedulingIds") String schedulingIds);

	/**
	 * 插入课次新的高级参数
	 * @param highParams
	 */
	void insertCourseSchedulingTimeAssist(@Param("highParams") List<TCourseSchedulingAssist> highParams);

}
