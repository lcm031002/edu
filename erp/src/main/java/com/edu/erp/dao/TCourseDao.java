package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

/***
 * Description ：课程商品 DAO 接口
 * 
 * Author ：
 * 
 * Date :
 */
@Repository("tCourseDao")
public interface TCourseDao {

	/***
	 * 保存课程商品信息（2014-09-11）
	 * 
	 * @param tCourse
	 *            课程商品信息对象
	 * @return int 影响行数
	 * @throws Exception
	 *             抛出系统异常
	 */
	int saveTCourse(TCourse tCourse) throws Exception;

	/***
	 * 保存课程商品信息（2014-09-11）
	 * 
	 * @param tCourse
	 *            课程商品信息对象
	 * @return int 影响行数
	 * @throws Exception
	 */
	Integer updateTCourse(TCourse tCourse) throws Exception;

	/**
	 * 
	 * @Description: 查询给定学生可以联报的课程ID信息
	 * 
	 * @param condition
	 *            学生查询对象
	 * 
	 * @param @throws Exception 设定文件
	 * @return List<CourseBusiness> 返回类型
	 */
	List<TCourse> queryCourseByPack(TCourse condition) throws Exception;

	/**
	 * 
	 * @Description: 通过教师ID查询其开设的课程信息
	 * @param tearcherId
	 *            教师ID
	 * @param studentId
	 *            学生ID
	 * @param @throws Exception 设定文件
	 * @return List<OrderDetailBusiness> 返回类型
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
	TCourse queryCourseById(TCourse courseBusiness) throws Exception;

	/**
	 * 
	 * @Title: changeActualPeople
	 * @Description: 修改课程的实际报班人数
	 * @param map
	 *            ：课程ID，course_id
	 * @return
	 * @throws Exception
	 *             设定文件 int 返回类型
	 * 
	 */
	int changeActualPeople(Map<String, Object> map) throws Exception;

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
	 * @Description: 查询存在档期冲突的课程的信息
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

	/**
	 * 
	 * @Title: getDoubleCourseId
	 * @Description: 查询课程绑定的双师课程
	 * @param courseId
	 *            课程ID
	 * @return 双师课程ID
	 * @throws Exception
	 *             设定文件 Long 返回类型
	 * 
	 */
	Long getDoubleCourseId(Long courseId) throws Exception;
	
	/**
	 * 
	 * @Title: changeStatus
	 * @Description: 修改课程状态
	 * @param params 参数对象
	 * courseId：课程id，
	 * status：课程状态
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void changeStatus(Map<String, Object> params) throws Exception;
	
	/**
	 * 
	 * @Title: queryCourseTimesAttendance
	 * @Description: 根据课程ID课次的考勤状态信息
	 * @param courseId 课程id
	 * @return 考勤的课程状态信息
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>    返回类型
	 *
	 */
	List<Map<String,Object>> queryCourseTimesAttendance(Long courseId) throws Exception;
	
	
	/**
	 * 查询课程信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TCourse> selectForPage(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询1对1课程信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TCourse> selectYDYForPage(Map<String, Object> paramMap) throws Exception;
	/**
	 * 排课
	 * @param map
	 * @throws Exception
	 */
	//void schedulingCourse(Map map) throws Exception;
	
	/**
	 * 列表查询课程信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<TCourse> selectList(Map<String, Object> param) throws Exception;
	
	/**
	 * 用于校验记录是否存在，只返回id
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<TCourse> checkExist(Map<String, Object> param) throws Exception;

	Integer toUpdateWfd(TCourse course) throws Exception;

	Integer removeOrgRef(String string) throws Exception;

	Integer insertOrgRef(List<Map<String, Long>> refs);

	Page<Map<String, Object>> selectYdyForPage(Map<String, Object> queryParam)throws Exception;

	Integer updateYdyCourse(TCourse course)throws Exception;

	/**
	 * 根据课程的id查询关联的校区
	 * @param courseIds
	 * @return
	 */
	List<Map<String,Object>> selectBranchByCourseId(Map<String, Object> param)throws Exception;
	
	/**
	 * 查询过期课程
	 * @param param 查询条件
	 * 条件：cur_date 当前日期（yyyy-MM-dd）
	 * @return 过期课程编号列表
	 * @throws Exception
	 */
	List<Long> queryOutCourse(Map<String, Object> param) throws Exception;

	 /**
     * 查询需要导出到excel中的班级课信息
     * 
     * @param queryParam
     * @return
     * @throws Exception
     */
	List<Map<String, Object>> queryListForExcel(Map<String, Object> queryParam);

	List<CourseScheduling> queryCourseSchedulingById(@Param("id") Long id);

	/**
	 * 根据主场课程ID查询课程信息
	 * @param mtId 主场课程ID
	 * @return 课程信息列表
	 * @throws Exception
	 */
	List<TCourse> queryByCourseId(@Param("courseId") Long courseId, @Param("isMtCourse") String isMtCourse) throws Exception;

	/**
	 * 查询课程是否为双师分场
	 * @param id 课程ID
	 * @return
	 * @throws Exception
	 */
	Integer isMtSubCourse(Long id) throws Exception;

	/**
	 * 查询课程是否为双师主场
	 * @param id 课程ID
	 * @return
	 * @throws Exception
	 */
	Integer isMtMainCourse(Long id) throws Exception;

	@Select("select * from t_course where id = #{id,jdbcType=NUMERIC}")
	TCourse getCourseById(Long id);

	/**
	 * 通过校区查询产品线
	 * @param id
	 * @return
	 */
	Long queryProductLine(Long id);

	/**
	 * 查询重复课程
	 * @param
	 * @return
	 */
	int queryRepeatedCourses(TCourse course);

	/**
	 *  根据课程id搜索课时长度
	 * @param courseId
	 * @return
	 */
	TCourse queryHourlenBycourseId(Long courseId);

	/**
	 * 根据课程ID搜索课程课次绑定相关类
	 * @param courseId
	 * @return
	 */
	List<RoomClass> queryRoomClass(Long courseId);

	/*
	 * 通过主场课程ID获取所有分场课程ID
	 * @param mainCourseId 主场课程ID
	 * @return 分场课程ID列表
	 * @throws Exception
	 */
	List<Long> queryAllSubMtCourseId(Long mainCourseId) throws Exception;

	/**
	 * 课程是否存在订单课程(转班的订单没有临时信息)或临时订单课程(审核中的订单)
	 * @param courseId
	 * @return
	 */
	Integer existOrderCourse(Long courseId);

	TCourse selectOneById(Long courseId);

	List<Map<String, Object>> queryCourseIdForVerify(Map<String, Object> paramMap);

	/**
	 * 查询要同步的课程ID
	 * @param paramMap 课程id，包含培英及双师主、分场课程ID
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> querySyncCourseInfoList(Map<String, Object> paramMap) throws Exception;
}
