package com.edu.erp.course_manager.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.CourseScheduling;
import com.edu.erp.model.TCourseRoomRel;
import com.edu.erp.model.TOrderCourseTimes;
import com.github.pagehelper.Page;

public interface CourseSchedulingService {

	/**
	 * 
	 * @Title: queryOne
	 * @Description: 根据id查询排课课次
	 * @param id
	 *            课程课次id
	 * @return 排课信息
	 * @throws Exception
	 *             设定文件 CourseScheduling 返回类型
	 * 
	 */
	CourseScheduling queryOne(Long id) throws Exception;

	int update(CourseScheduling courseScheduling) throws Exception;

	/**
	 * 分页表格
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<CourseScheduling> page(Page<CourseScheduling> page) throws Exception;

	/**
	 * 
	 * @Title: queryCourseScheduling
	 * @Description: 查询课程排课信息
	 * @param condition
	 *            条件
	 * @throws Exception
	 *             设定文件
	 * @return List<CourseScheduling> 返回类型
	 */
	List<CourseScheduling> queryCourseScheduling(CourseScheduling condition)
			throws Exception;

	/**
	 * 
	 * @Title: queryOrderCourseScheduling
	 * @Description: 查询订单课程排课信息
	 * @param orderId
	 *            订单ID
	 * @return 课程排课信息
	 * @throws Exception
	 *             设定文件 List<CourseScheduling> 返回类型
	 * 
	 */
	List<CourseScheduling> queryOrderCourseScheduling(Long orderId)
			throws Exception;

	/**
	 * 新增
	 * 
	 * @param courseScheduling
	 * @param courseScheduling
	 *            校区
	 * @throws Exception
	 */
	void toAdd(CourseScheduling courseScheduling) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * @param param
	 * @throws Exception
	 */
	void toUpdate(Map<String, Object> param) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status) throws Exception;

	/**
	 * 获取需要排课的课程
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Long> getSchedulingCourseID() throws Exception;

	/**
	 * 
	 * @Title: schedulingCourse
	 * @Description: 课程排课
	 * @param courseId
	 *            课程ID
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
//	void schedulingCourse(Long courseId) throws Exception;

	/**
	 * 
	 * @Title: queryCourseSchedulingStudents
	 * @Description: 根据课程id与课次，查询考勤的学员
	 * @param courseId
	 *            ,课程id
	 * @param courseTime
	 *            ,课次序号
	 * 
	 * @return 查询待考勤的学员
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> queryCourseSchedulingStudents(Long courseId,
			Long courseTime) throws Exception;

	/**
	 * 
	 * @Title: querySchedulingAttendanceMakeup
	 * @Description: 查询课程补课预约信息
	 * @param courseId
	 *            课程id
	 * @param courseTime
	 *            课程课次
	 * @param queryInfo
	 * 			  查询参数
	 * @return 返回补课预约信息
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> querySchedulingAttendanceMakeup(Long courseId,
			Long courseTime,String queryInfo) throws Exception;
	/**
	 * 将可报人数，已报人数，待确定人数填充到具体的当前课次信息中
	 * @param courseScheduleList
	 * @throws Exception
	 */
	void fillPeopleCountIntoCourseScheduling(List<CourseScheduling> courseScheduleList) throws Exception;
	
	/**
	 * 更新排课信息
	 * @param courseScheduling
	 * @throws Exception
	 */
	void updateCourseTimes(CourseScheduling courseScheduling) throws Exception;

	/**
	 * 查询待确认订单的排课
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	List<CourseScheduling> queryConfirmCourseScheduling(CourseScheduling condition) throws Exception;
	
	/**
	 * 根据学生查询正式订单是否有购买了对应的课次
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourseTimes> queryCourseSchedulingTimesByStudent(CourseScheduling condition) throws Exception;
	/**
	 * 根据学生课程查询待确定的课次
	 * @param condition
	 * studentId 学生ID
	 * courseId  课程ID
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourseTimes> queryConfirmTimesByCoursesStudent(CourseScheduling condition) throws Exception;
	
	/**
	 * 根据学生课程查询转班的课次
	 * @param condition
	 * studentId 学生ID
	 * courseId  课程ID
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourseTimes> queryTransferTimesByCoursesStudent(CourseScheduling condition) throws Exception;


	/**
	 * 批量修改课程课次服务
	 * @param course_id
	 * @param courseSchedulingDetails
	 * @param create_user TODO
	 */
	void batchUpdateCourseTimesTitle(Long course_id, String courseSchedulingDetails, Long create_user) throws Exception;

	void scheduleCourse (Long courseId,Long updateUser) throws Exception;

	/**
	 * 更新课次时间
	 * @param courseId
	 * @param courseTime
	 * @param courseDate
	 * @param startTime
	 * @param endTime
	 */
	void updateSchedulingTime(Long courseId,int courseTime, String courseDate,  String startTime,  String endTime);
}
