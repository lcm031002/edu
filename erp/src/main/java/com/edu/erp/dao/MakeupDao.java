package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.Makeup;

public interface MakeupDao {
	/**
	 * 
	 * @Title: querySchedulingAttendanceMakeupByForCourse
	 * @Description: 查询 某个课程中的某个课次的所有学员的补课信息
	 * @param params
	 *            参数对象
	 * @return 补课预约信息
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Makeup> querySchedulingAttendanceMakeupByForCourse(
            Map<String, Object> params) throws Exception;

	/**
	 * 
	 * @Title: querySchedulingAttendanceMakeupForStu
	 * @Description: 根据学员的id查询该学员的所有补课信息
	 * @param params
	 *            参数对象
	 * @return 补课预约信息
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Makeup> querySchedulingAttendanceMakeupForStu(
            Map<String, Object> params) throws Exception;
}
