package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MyCourseDao {
	
	/**
	 * 
	 * @Title: searchCourseClassList
	 * @Description: 	查询课程、课次标题
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>     返回类型
	 *
	 */
	List<Map<String,Object>> searchCourseClassList(Map<String, Object> param)throws Exception;
	
	/**
	 * 
	 * @Title: queryCourseDetailList
	 * @Description: 	查询课程信息
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>     返回类型
	 *
	 */
	List<Map<String,Object>> queryCourseDetailList(Map<String, Object> param)throws Exception;
	
	/**
	 * @Title: getTotalByCourseParam
	 * @Description: 	统计课程总数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Integer getTotalByCourseParam(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: countTotalClass
	 * @Description: 	统计课程总课次
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>     返回类型
	 *
	 */
	List<Map<String,Object>> countTotalClass(Map<String, Object> param)throws Exception;
	
	/**
	 * 
	 * @Title: countFinishedClass
	 * @Description: 	统计课程已完成课次
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>     返回类型
	 *
	 */
	List<Map<String,Object>> countFinishedClass(Map<String, Object> param)throws Exception;
	
	/**
	 * 
	 * @Title: queryCourseClassDetailList
	 * @Description: 	根据课程id查询课次信息
	 * @param courseId
	 * @return
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>     返回类型
	 *
	 */
	List<Map<String,Object>> queryCourseClassDetailList(Integer courseId)throws Exception;
	
	/**
	 * 作业绑定课次-根据课程ids查询课次列表
	 */
	List<Map<String,Object>> getCourseTimesList(@Param("courseIds") String[] courseIds)throws Exception;
	
}
