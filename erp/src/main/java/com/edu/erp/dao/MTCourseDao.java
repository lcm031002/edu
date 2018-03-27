package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCourse;
import com.edu.erp.model.TMoreCourseScheduling;
import com.edu.erp.model.TMoreCourseTeacher;
import com.edu.erp.model.TMoreTeacherCourse;
import com.github.pagehelper.Page;

@Repository("mTCourseDao")
public interface MTCourseDao {
	/**
	 * 查询双师课程
	 * 
	 * @param param
	 *            课程查询参数 queryString
	 * @return 符合条件的双师课程
	 * @throws Exception
	 */
	List<TMoreTeacherCourse> queryMTCoursePage(Page<TMoreTeacherCourse> param)
			throws Exception;

	/**
	 * 查询双师课程
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> selectForPage(Map<String, Object> paramMap) throws Exception;
	/**
	 * 
	 * 通过双师课程ID查询双师课程绑定的课程商品
	 * 
	 * @param courseId
	 *            双师课程ID
	 * @return 返回双师课程绑定的课程商品
	 * @throws Exception
	 */
	TCourse selectedPage(Long courseId) throws Exception;
	
	/**
	 * 
	 * 通过双师课程ID查询双师课程
	 * 
	 * @param courseId
	 *            双师课程ID
	 * @return 返回双师课程信息
	 * @throws Exception
	 */
	TMoreTeacherCourse queryMTCourseById(Long courseId) throws Exception;

	/**
	 * 
	 * 添加双师课程
	 * 
	 * @param mTCourse
	 *            双师课程
	 * @throws Exception
	 *             异常信息
	 */
	void addMoreTeacherCourse(TMoreTeacherCourse mTCourse) throws Exception;

	/**
	 * 课程课次安排
	 * 
	 * @param courseScheduling
	 *            课程安排
	 * @throws Exception
	 */
	void addMoreCourseScheduling(TMoreCourseScheduling courseScheduling)
			throws Exception;

	/**
	 * 查询双师课程的排课信息
	 * 
	 * @param moreCourseId
	 *            双师课程ID
	 * @return 返回排课信息
	 * @throws Exception
	 */
	List<TMoreCourseScheduling> queryMTCourseScheduling(Long moreCourseId)
			throws Exception;

	/**
	 * 删除双师课程排课信息
	 * 
	 * @param moreCourseId
	 *            双师课程ID
	 * @throws Exception
	 *             异常信息
	 */
	void deleteMoreCourseScheduling(Long moreCourseId) throws Exception;

	/**
	 * 双师课程教师
	 * 
	 * @param moreCourseTeacher
	 *            双师课程教师
	 * @throws Exception
	 */
	void addMoreCourseTeacher(TMoreCourseTeacher moreCourseTeacher)
			throws Exception;

	/**
	 * 
	 * @param moreCourseTeacher
	 *            参数1：双师课程，moreCourseId，双师课程课次ID，moreCourseTimeId
	 * @return 返回双师课程课次的辅导老师列表的信息
	 * @throws Exception
	 */
	List<TMoreCourseTeacher> queryMoreCourseTeacher(
            TMoreCourseTeacher moreCourseTeacher) throws Exception;

	/**
	 * 删除双师课程教师关联信息
	 * 
	 * @param moreCourseId
	 *            双师课程ID
	 * 
	 * @throws Exception
	 */
	void deleteMoreCourseTeacher(Long moreCourseId) throws Exception;
	/**
	 * 删除双师课程教师关联信息
	 * 
	 * @param moreCourseId
	 *            双师课程ID
	 * 
	 * @throws Exception
	 */
	void updateTCourse(Long moreCourseId) throws Exception;

	/**
	 * 双师课程修改
	 * 
	 * @param mTCourse
	 *            双师课程
	 * @throws Exception
	 *             异常信息
	 */
	void updateMoreTeacherCourse(TMoreTeacherCourse mTCourse) throws Exception;

	/**
	 * 
	 * @Title: queryMTCourseCourseList
	 * @Description: 查询双师课程绑定的课程
	 * @param moreCourseId
	 *            双师课程ID
	 * @return 返回课程列表
	 * @throws Exception
	 *             设定文件 List<TCourse> 返回类型
	 * 
	 */
	List<TCourse> queryMTCourseCourseList(Long moreCourseId) throws Exception;

	/**
	 * 
	 * @Title: updateTCourseMTCourse
	 * @Description: 绑定双师课程
	 * @param tcourse
	 *            课程对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void updateTCourseMTCourse(TCourse tcourse) throws Exception;

	void changeStatus(@Param("ids") String ids, @Param("status") String status, @Param("updateUser") Long updateUser)throws Exception;
	
	 /**
     * 查询
     * @param doubleCourseId
     * @return
     */
	List<Long> queryTeachersByDoubleCourseId(Long doubleCourseId);
	/**
	 * 查询助教老师ID
	 * @param doubleCourseId
	 * @return
	 * @throws Exception
	 */
	List<Long> queryAssTeachersByDoubleCourseId(Long doubleCourseId) throws Exception;

	/**
	 * 更新课程同步双师系统结果
	 * @param paramMap 同步结果
	 * @throws Exception
	 */
	void updateSendInfo(Map<String, Object> paramMap) throws Exception;
}
