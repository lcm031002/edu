package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TEDocTemplateTeacherInfo;
import com.edu.erp.model.Teacher;
import com.edu.erp.model.TeacherSubject;
import com.edu.erp.model.TeacherTeamRel;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "teacherInfoDao")
public interface TeacherInfoDao {
	
	/**
	 * 分页查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<Teacher> page(Map<String, Object> param) throws Exception;

	/**
	 * 教师列表查询
	 * @param param 查询条件
	 * @return 教师列表
	 * @throws Exception
	 */
	List<Teacher> selectList(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<Teacher> list(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: queryOrderTeacher
	 * @Description: 查询订单涉及的老师
	 * @param orderId 订单ID 
	 * @return 返回订单涉及的教师信息
	 * @throws Exception    设定文件
	 * List<Teacher>    返回类型
	 *
	 */
	List<Teacher> queryOrderTeacher(Long orderId) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toAdd(Teacher pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toUpdate(Teacher pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param param
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toChangeStatus(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增校区关系
	 * 
	 * @param teacherSubjectList
	 * @return
	 * @throws Exception
	 */
	Integer insertSubjectRef(List<TeacherSubject> teacherSubjectList) throws Exception;
	 
	/**
	 * 删除校区关系
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	Integer toRemoveSubjectRef(String ids) throws Exception;
	
	/**
	 * 
	 * 
	 * @Description: 查询学生报过的教师
	 * 
	 * @param studentId
	 *            学生ID
	 *            
	 * @throws Exception
	 *             设定文件
	 *             
	 * @return List<TeacherBusiness> 返回类型
	 * 
	 * @throws
	 */
	List<Teacher> queryStudentTeachers(Long studentId, Long branchId) throws Exception;
	
	/**
	 * 设置教师图像
	 * 
	 * @param param
	 * @throws Exception
	 */
	Integer toSetPhoto(Map<String, Object> param)throws Exception;
	/**
	 * 设置教师图像
	 * 
	 * @param param
	 * @throws Exception
	 */
	Integer setPhoto(Map<String, Object> param)throws Exception;
	
	
	Teacher queryOne(Long teacher_id) throws Exception;

	int toAddTemplateTeacherInfo(TEDocTemplateTeacherInfo edocCourse)throws Exception;

	void toCheckTemplateTeacherInfo(Map<String, Object> param)throws Exception;

	List<Map<String, Object>> getAsyn(Map<String, Object> params)throws Exception;

	void genInvation(Map<String, Object> params)throws Exception;
	
	List<Map<String, Object>> searchTeacherSubjects(Integer teacherId);

	int isPhoneExisted(Teacher teacher) throws Exception;

	int isEncodingExisted(Teacher teacher) throws Exception;

	/**
	 * 查询老师课程表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> queryCourseSched(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询老师日程安排
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryTeacherSched(Map<String, Object> paramMap) throws Exception;

	/**
	 * 新增教师团队关系
	 * @param teacherTeamRel
	 * @return
	 * @throws Exception
	 */
	Integer insertTeamRef(@Param("teacherTeamRel") TeacherTeamRel teacherTeamRel) throws Exception;

	/**
	 *查询教师关联团队
	 * @param teacherId
	 * @return
	 */
	List<TeacherTeamRel> searchTeacherTeam(Integer teacherId);

	/**
	 * 根据教师ID删除教师关联团队
	 * @param teacherId
	 * @return
	 */
	Integer toRemoveTeamRef(Long teacherId);

	void changeTeacherStatus(Map<String,Object> param);
}
