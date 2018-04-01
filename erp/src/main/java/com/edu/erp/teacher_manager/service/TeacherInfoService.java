package com.edu.erp.teacher_manager.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.*;
import com.github.pagehelper.Page;

public interface TeacherInfoService {
	
	/**
	 * 分页查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<Teacher> page(Map<String,Object> param) throws Exception;

	/**
	 * 教师列表查询
	 * @param param 查询条件
	 * @return 教师列表
	 * @throws Exception
	 */
	List<Teacher> selectList(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询教师信息
	 * 
	 * @param teacherId 教师ID
	 * @return Teacher：学生信息表
	 * @throws Exception
	 */
	Teacher queryOne(Long teacherId) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<Teacher> list(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @param teacherSubjectList 校区
	 * @throws Exception
	 */
	void toAdd(Teacher pojo, List<TeacherSubject> teacherSubjectList, List<TeacherTeamRel> teacherTeamRelList) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toUpdate(Teacher pojo, List<TeacherSubject> teacherSubjectList,List<TeacherTeamRel> teacherTeamRelList) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status) throws Exception;
	
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
	 * @return List<Teacher> 返回类型
	 * 
	 * @throws
	 */
	List<Teacher> queryStudentTeachers(Long studentId,Long branchId) throws Exception;
	
	/**
	 * 设置教师图像
	 * 
	 * @param teacher_id
	 * @param photo
	 * @throws Exception
	 */
	void toSetPhoto(Long teacher_id, String photo)throws Exception;
	
	/**
	 * 新增班级课文档模板
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	void toAddTemplateTeacherInfo(TEDocTemplateTeacherInfo teacher) throws Exception;
/**
 * 导入教师信息校验
 * @param param
 * @throws Exception
 */
	public void toCheckTemplateTeacherInfo(Map<String, Object> param) throws Exception;

    public List<Map<String, Object>> getAsyn(Map<String, Object> params) throws Exception;

    //返回邀请码生成失败的教师（没有电话号码的教师）
	String genInvation(Map<String, Object> params) throws Exception;
	
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
	 * 上传照片到七牛，并更新数据库
	 * @param json
	 * @throws Exception 
	 */
	void uploadAndSetPhoto(Map<String, String> json) throws Exception;

	/**
	 * 通过教师
	 * @param teacherId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> searchTeacherSubjects(Integer teacherId) throws Exception;

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
	 * 查询教师关联团队
	 * @param teacherId
	 * @return
	 * @throws Exception
	 */
	List<TeacherTeamRel> searchTeacherTeam(Integer teacherId) throws Exception;


}
