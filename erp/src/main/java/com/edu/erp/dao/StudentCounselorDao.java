package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.EmpOrgPost;
import com.edu.erp.model.StudentCounselorInfo;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

@Repository(value = "studentCounselorDao")
public interface StudentCounselorDao {

	/**
	 * 查询所有的学管师
	 * 
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	Page<EmpOrgPost> selectForToPage(Map<String, Object> queryParam) throws Exception;

	/**
	 * 查询所有的学管师
	 * 
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	Page<EmpOrgPost> selectForFromPage(Map<String, Object> queryParam) throws Exception;

	/**
	 * 分页查询学管师
	 * 
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	Page<StudentCounselorInfo> selectForPage(Map<String, Object> queryParam) throws Exception;

	/**
	 * 查询List
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 */
	Page<StudentCounselorInfo> selectList(Map<String, Object> param) throws Exception;

	/**
	 * 查询List
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 */
	StudentCounselorInfo selectOne(Map<String, Object> param) throws Exception;

	/**
	 * 新增
	 * 
	 * @param studentCounselorInfo
	 * @throws Exception
	 * @return
	 */
	Integer insert(StudentCounselorInfo studentCounselorInfo) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * 
	 * @param studentCounselorInfo
	 * @throws Exception
	 * @return
	 */
	Integer update(StudentCounselorInfo studentCounselorInfo) throws Exception;

	/**
	 * 根据ID修改状态
	 * 
	 * @param param
	 *            (ids, status)
	 * @throws Exception
	 * @return
	 */
	Integer changeStatus(Map<String, Object> param) throws Exception;

	/**
	 * 把
	 * 
	 * @param param
	 *            (ids, status)
	 * @throws Exception
	 * @return
	 */
	Integer updateInValid(Map<String, Object> param) throws Exception;

	Page<StudentCounselorInfo> queryStudentListPage(Map<String, Object> queryParam) throws Exception;

	List<StudentCounselorInfo> querStudentCounselorInfoByCounselorId(Map<String, Object> param) throws Exception;

	/**
	 * 判断新插入的咨询师（如果是学管师，需要根据branch_id过滤） 是否和数据库中已经存在的学员的咨询师（学管师）的日期交叉
	 * @param param
	 * @return
	 */
	StudentCounselorInfo queryNewest(Map<String, Object> param);

	/**
	 * 将符合条件的所有的学管师和咨询师设置为无效
	 * @param param
	 */
	void inValidAllCounselor(Map<String, Object> param);

}
