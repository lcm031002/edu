package com.edu.erp.student.service;

import java.util.Map;

import com.edu.erp.model.EmpOrgPost;
import com.edu.erp.model.StudentCounselorInfo;
import com.github.pagehelper.Page;

public interface StudentCounselorService {

	Page<StudentCounselorInfo> queryStudentListPage(Map<String, Object> queryParam) throws Exception;

	Page<EmpOrgPost> selectForToPage(Map<String, Object> queryParam) throws Exception;

	Page<EmpOrgPost> selectForFromPage(Map<String, Object> queryParam) throws Exception;

	Page<StudentCounselorInfo> page(Map<String, Object> queryParam) throws Exception;

	/**
	 * 批量分配学员
	 * 
	 * @throws Exception
	 */
	void toBatchTransfer(String p_from_conselor_id, String p_to_conselor_id) throws Exception;
	
	/**
	 * 修改学生的归属学管师
	 * 
	 * @throws Exception
	 */
	void toTransfer(String studentConselorId, String newConselorId) throws Exception;

	/**
	 * 查询List
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 */
	Page<StudentCounselorInfo> list(Map<String, Object> param) throws Exception;

	/**
	 * 新增
	 * 
	 * @param studentCounselorInfo
	 * @throws Exception
	 */
	void toAdd(StudentCounselorInfo studentCounselorInfo) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * 
	 * @param studentCounselorInfo
	 * @throws Exception
	 */
	void toUpdate(StudentCounselorInfo studentCounselorInfo) throws Exception;

	/**
	 * 根据ID修改状态
	 * 
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status) throws Exception;

}
