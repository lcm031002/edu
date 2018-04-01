package com.edu.erp.course_manager.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.edu.erp.course_manager.business.SchedulingAssist;
import com.edu.erp.model.TCourse;
import com.edu.erp.model.TCourseSchedulingAssist;
import com.edu.erp.model.TempTCourse;
import com.github.pagehelper.Page;

public interface CourseService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TCourse> page(Page<TCourse> page) throws Exception;
	/**
	 * 分页查询课程信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
   Page<TCourse> queryPage(Map<String, Object> param) throws Exception;
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	List<TCourse> list(Map<String, Object> param) throws Exception;

	/**
	 * 新增
	 * 
	 * @param course
	 *            校区
	 * @throws Exception
	 */
	void toAdd(TCourse course) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * @param course
	 * @throws Exception
	 */
	void toUpdate(TCourse course) throws Exception;

	/**
	 * 
	 * @Title: toChangeStatus
	 * @Description: 修改课程的状态
	 * @param ids
	 *            课程ID
	 * @param status
	 *            状态
	 * @param userId
	 *            用户id
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void toChangeStatus(String ids, String status, Long userId)
			throws Exception;

	/**
	 * 根据ids字符串改变是否需要审批状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void toChgIsApprove(String ids, Integer status) throws Exception;

	/**
	 * 改变商品价格
	 * 
	 * @param course_id,unit_price,total_price
	 * @throws Exception
	 */
	void toChangePrice(Long course_id, Double unit_price, Double total_price)
			throws Exception;

	/**
	 * 
	 * 
	 * @Description: 查询给定学生可以报考的联报课程
	 * @param courseBusiness
	 *            学生ID
	 *            联报课程
	 * @throws Exception
	 *             设定文件
	 * @return List<CourseBusiness> 返回类型
	 * @throws
	 */
	List<TCourse> queryCourseByPack(TCourse courseBusiness) throws Exception;

	/**
	 * 
	 * @Description:通过教师查询课程信息
	 * 
	 * @param tearcherId
	 *            教师ID
	 * @param studentId
	 *            学生Id
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return List<CoursePackBusiness> 返回类型
	 */
	List<TCourse> queryCourseByTearcher(Long tearcherId, Long studentId)
			throws Exception;

	/**
	 * 
	 * @Description: 查询该学生可以选择的课程
	 * @param courseCondition
	 *            查询条件
	 * @param @throws Exception 设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
	List<TCourse> queryCourseByBusiness(Map<String, Object> courseCondition)
			throws Exception;

	/**
	 * 课程排课
	 * 
	 * @param courseId
	 * @return error_code : 0正常执行, 其他表示出现异常 error_desc : 异常信息
	 * @throws Exception
	 */
	Map<String, Object> schedulingCourse(Long courseId) throws Exception;

	/**
	 * 删除课程排课信息
	 * 
	 * @param courseId
	 * @return error_code : 0正常执行, 其他表示出现异常 error_desc : 异常信息
	 * @throws Exception
	 */
	Map<String, Object> deleteSchedulingCourse(Long courseId) throws Exception;

	/**
	 * 
	 * @Description: 更新课程人数
	 * 
	 * @param number
	 *            人数
	 * 
	 * @param course_id
	 *            课程id
	 * 
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return int 返回类型
	 * 
	 */
	int updateCoursePeople(int number, Long course_id) throws Exception;

	/**
	 * 
	 * 
	 * @Description: 通过课程信息查询课程
	 * 
	 * @param courseBusiness
	 *            课程信息：ID
	 * @throws Exception
	 *             设定文件
	 * @return CourseBusiness 返回类型
	 */
	TCourse queryCourseByID(TCourse courseBusiness) throws Exception;

	
	Long getDoubleCourseId(Long courseId) throws Exception;
	/**
	 * 
	 * 
	 * @Description: 查询存在档期冲突的课程的信息
	 * 
	 * @param condition
	 *            查询参数
	 * @throws Exception
	 *             设定文件
	 * @return List<CourseBusiness> 返回类型
	 */
	List<TCourse> queryCobnflictCourse(TCourse condition) throws Exception;

	/**
	 * 
	 * 
	 * @Description: 查询学生对应年级和给定校区的课程
	 * 
	 * @param condition
	 *            查询参数
	 * @throws Exception
	 *             设定文件
	 * @return List<CourseBusiness> 返回类型
	 */
	List<TCourse> queryYDYCourse(TCourse condition) throws Exception;

	/**
	 * 
	 * @Title: toChangeActualPeople
	 * @Description: 修改实际参加的人数
	 * @param param
	 *            参数
	 * @throws Exception
	 *             设定文件
	 * @return Integer 返回类型
	 */
	Integer toChangeActualPeople(Map<String, Object> param) throws Exception;

	/**
	 * 获取需要自动下架的课程
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Long> queryOutCourse(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getAsyn(Map<String, Object> params)
			throws Exception;

	/**
	 * 
	 * @Title: queryOrderCourseList
	 * @Description: 查询订单课程信息列表
	 * @param orderId
	 *            订单id
	 * @return 课程列表
	 * @throws Exception
	 *             设定文件 List<TCourse> 返回类型
	 * 
	 */
	List<TCourse> queryOrderCourseList(Long orderId) throws Exception;

	/***
	 * 保存课程商品信息（2014-09-11）
	 * 
	 * @param tCourse
	 *            课程商品信息对象
	 * @return int 影响行数
	 * @throws Exception
	 */
	void updateTCourse(TCourse tCourse) throws Exception;

	/**
	 * 
	 * @Title: updateCourseScheduling
	 * @Description: 更新课程排课信息
	 * @param tCourse
	 *            课程信息
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void updateCourseScheduling(TCourse tCourse) throws Exception;

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
	 * @param courseId
	 *            课程id
	 * @param courseTime
	 *            课次序号
	 * @return 课程排课辅助参数
	 * @throws Exception
	 *             设定文件 List<TCourseSchedulingAssist> 返回类型
	 * 
	 */
	List<TCourseSchedulingAssist> queryCourseTimeSchedulingAssist(
			Long courseId, Long courseTime) throws Exception;

	/**
	 * 
	 * @Title: updateCourseSchedulingAssist
	 * @Description: 修改课程配置参数
	 * @param schedulingAssist
	 *            课程配置参数
	 * @param userId
	 *            用户id
	 * @param courseId
	 *            课程id
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void updateCourseSchedulingAssist(
			List<TCourseSchedulingAssist> schedulingAssist, Long userId,
			Long courseId) throws Exception;

	/**
	 * 
	 * @Title: queryCourseTimesAttendance
	 * @Description: 根据课程ID课次的考勤状态信息
	 * @param courseId
	 *            课程id
	 * @return 考勤的课程状态信息
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> queryCourseTimesAttendance(Long courseId)
			throws Exception;
	
	/**
	 * 修改晚辅导课程
	 * @param course
	 * @throws Exception
	 */
	void toUpdateWfd(TCourse course, String org_ids) throws Exception;
	/**
	 * 新增晚辅导课程
	 * @param course
	 * @param org_ids
	 * @throws Exception
	 */
	void toAdd(TCourse course, String org_ids) throws Exception;
	/**
	 * 新增一对一课程
	 * @param course
	 * @param org_ids TODO
	 * @param org_ids
	 * @throws Exception
	 */
	void toAddYdy(TCourse course, String org_ids) throws Exception;
	/**
	 * 修改一对一课程
	 * @param course
	 * @throws Exception
	 */
	void toUpdateYdy(TCourse course) throws Exception;
	/**
	 * 一对一课程查询
	 * @param queryParam
	 * @return
	 */
	Page<TCourse> queryYdyPage(Map<String, Object> queryParam)throws Exception;
	
	/**
     * 自动下架到期课程
     * 
     */
    void stopOutCourse();
    
    /**
     * 查询需要导出到excel中的班级课信息
     * 
     * @param queryParam
     * @return
     * @throws Exception
     */
	List<Map<String, Object>> queryListForExcel(Map<String, Object> queryParam)throws Exception;

    List<TCourseSchedulingAssist> queryCourseSchedulingTimeAssist(Long schedulingId);

	void updateCourseSchedulingTimeAssist(SchedulingAssist schedulingAssist);


    /**
     * 根据开课日期、上课周期、课时数量自动计算出结课日期
     * @param course
     * @return
     * @throws Exception
     */
    public Date queryEndTimesByPeriod(TCourse course) throws Exception;

	/**
	 * 根据导入的课程信息判断课程是否有重复
	 * @param
	 * @return
	 * @throws Exception
	 */
    int queryRepeatedCourses(TCourse course) throws Exception;

	/**
	 * 根据课程id搜索课时长度
	 * @param courseId
	 * @return
	 */
	TCourse queryHourlenBycourseId(Long courseId);

	/**
	 * 根据上课时间和下课时间自动推算课时长度
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double queryHourLen(String startTime,String endTime) throws Exception;

	public Boolean existOrderCourse(Long courseId);
}
