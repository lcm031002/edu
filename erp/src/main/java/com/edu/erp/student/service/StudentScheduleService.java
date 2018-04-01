package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TAttendance;

public interface StudentScheduleService {

	/**
	 * 查询 1对1 课程表
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<TAttendance> queryYDYSchedule(Map<String, Object> queryParam) throws Exception;
	
	/**
	 * 查询 班级课 的 课程表
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<TAttendance> queryBJKSchedule(Map<String, Object> queryParam) throws Exception;

	/**
	 * 查询订单课程的课时长度
	 * @param orderCourseId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	int queryScheduleCourseTimes(Long orderCourseId,String startTime,String endTime) throws Exception;


}
