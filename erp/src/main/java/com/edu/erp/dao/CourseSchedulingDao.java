package com.edu.erp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.edu.erp.model.AttendanceAuxiliaryData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.CourseScheduling;
import com.edu.erp.model.TCourseTimesTitle;
import com.edu.erp.model.TOrderCourseTimes;
import com.github.pagehelper.Page;

@Repository(value = "courseSchedulingDao")
public interface CourseSchedulingDao {

	/**
	 * 分页表格
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<CourseScheduling> page(Page<CourseScheduling> page) throws Exception;

	int update(CourseScheduling courseScheduling);

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
	 * @throws Exception
	 */
	Integer toAdd(CourseScheduling courseScheduling) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * @param param
	 * @throws Exception
	 */
	Integer toUpdate(Map<String, Object> param) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param param
	 * @throws Exception
	 */
	Integer toChangeStatus(Map<String, Object> param) throws Exception;

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
	 * @param map
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	//void schedulingCourse(Map<String, Object> map) throws Exception;

	void deleteSchedulingCourse(Map<String, Object> map) throws Exception;

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

	/**
	 * 
	 * @Title: queryCourseSchedulingStudents
	 * @Description: 根据课程id与课次，查询考勤的学员
	 * @param params
	 *            courseId,课程id courseTime,课次序号
	 * 
	 * @return 查询待考勤的学员
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> queryCourseSchedulingStudents(
            Map<String, Object> params) throws Exception;

	/**
	 * 
	 * @Title: querySchedulingAttendanceMakeup
	 * @Description: 查询课程补课预约信息
	 * @param params
	 *            参数对象
	 * @return 补课预约信息
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> querySchedulingAttendanceMakeup(Map<String, Object> params) throws Exception;
	/**
	 * 查询课程课次标题信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<TCourseTimesTitle> queryCourseTimeTitleInfo(Map<String, Object> params) throws Exception;
	/**
	 * 新增课程课次标题信息
	 * @param tCourseTimesTitle
	 * @return
	 * @throws Exception
	 */
	int addCourseTimeTitleInfo(TCourseTimesTitle tCourseTimesTitle) throws Exception;
	/**
	 * 更新课程课次标题信息
	 * @param tCourseTimesTitle
	 * @return
	 * @throws Exception
	 */
	int updateCourseTimeTitleInfo(TCourseTimesTitle tCourseTimesTitle) throws Exception;
	
	/**
	 * 双师课程排课信息查询
	 * @param params
	 * @return 排课信息
	 * @throws Exception
	 */
	List<CourseScheduling> queryMultiTchCourseSched(Map<String, Object> params) throws Exception;

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
	 * 批量更新课次标题
	 * @param params
	 */
	void batchUpdateCourseTimesTitle(@Param("lists") ArrayList<TCourseTimesTitle> params);

	Integer queryValidCourseTimeByStudentAndSchedualing(@Param("studentId") Long studentId, @Param("schedualingId") Long schedualingId);

    /**
     * 当前课次在退费审批中
     * @param studentId
     * @param schedualingId
     * @return
     */
	Integer validIsRefund(@Param("studentId") Long studentId, @Param("schedualingId") Long schedualingId);

	AttendanceAuxiliaryData queryAttendanceSchedualingInfo(@Param("studentId") Long studentId, @Param("schedualingId") Long schedualingId);

	@Update("update t_course_scheduling set attended = #{attendStatus,jdbcType=VARCHAR},update_time=sysdate,update_user=#{updateUser,jdbcType=NUMERIC} where id = #{schedualingId,jdbcType=NUMERIC}")
	Integer updateAttendStatus(@Param("schedualingId") Long schedualingId, @Param("attendStatus") String attendStatus,
                               @Param("updateUser") Long updateUser);

	/**
	 * 双师主场课次标题调整，同步更新所有分场课次标题
	 * @param mainCourseId
	 * @throws Exception
	 */
	void batchUpdateSubCourseTimesTitle(Long mainCourseId) throws Exception;

	/**
	 * 双师主场课次排课信息调整，同步更新双师分场
	 * @param courseScheduling 排课信息
	 * @throws Exception
	 */
	void updateSubCourseScheduling(CourseScheduling courseScheduling) throws Exception;

	/**
	 * 双师主场课次标题调整，同步更新分场相应课次标题
	 * @param courseScheduling
	 * @throws Exception
	 */
	void updateSubCourseTimesTitle(CourseScheduling courseScheduling) throws Exception;

	@Select("select * from t_course_scheduling where course_id = #{courseId} and valid_status = 1")
	List<CourseScheduling> queryCourseSchedulingByCourseId(@Param("courseId") Long courseId);

	/**
	 * 查找班级课程已经发生考勤的课次
	 * @param courseId 班级课课程ID
	 * @return
	 */
	@Select("select distinct cs.* from t_course_scheduling cs join t_attendance ta on cs.id = ta.scheduling_id and ta.attend_type in(11,12) and ifnull(ta.for_quit,0) != 1 where course_id = #{courseId}")
	List<CourseScheduling> queryCourseSchedulingAttended(@Param("courseId") Long courseId);

	/**
	 * 删除课次标题
	 * @param courseId
	 * @param courseTime
	 */
	@Delete("delete from t_course_times_title where course_id = #{courseId} and course_times = #{courseTime}")
	void deleteCourseTimeTitle(@Param("courseId") Long courseId, @Param("courseTime") Long courseTime);

	/**
	 * 删除排课信息
	 * @param ids
	 */
	@Delete("delete from t_course_scheduling where id in (${ids})")
	void batchDeleteCoruseScheduling(@Param("ids") String ids);

	Integer insert(CourseScheduling courseScheduling);

	void updateCouseScheduling(CourseScheduling courseScheduling);

	/**
	 * 获取教师排课重复数
	 * @param courseId 课程ID
	 * @return
	 * @throws Exception
	 */
	Integer queryDuplicateTeacherSched(Long courseId) throws Exception;

	@Update("update t_course_scheduling set course_date = #{courseDate},start_time = #{startTime},end_time = #{endTime} where course_id = #{courseId} and course_times = #{courseTime}")
	void updateSchedulingTime(@Param("courseId") Long courseId, @Param("courseTime") int courseTime, @Param("courseDate") String courseDate, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
