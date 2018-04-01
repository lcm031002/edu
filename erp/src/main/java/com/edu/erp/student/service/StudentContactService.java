package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentContact;
import com.github.pagehelper.Page;

public interface StudentContactService {

	/**
	 * 查询page
	 * 
	 * @param queryParam 动态参数
	 * @return
	 */
	Page<StudentContact> page(Map<String, Object> queryParam)throws Exception;
	/**
	 * 查询List
	 * 
	 * @param param 动态参数
	 * @return
	 */
	List<StudentContact> list(Map<String, Object> param)throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param studentContact
	 * @throws Exception
	 */
	void toAdd(StudentContact studentContact) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * 
	 * @param studentContact 
	 * @throws Exception
	 */
	void toUpdate(StudentContact studentContact) throws Exception;
	
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
