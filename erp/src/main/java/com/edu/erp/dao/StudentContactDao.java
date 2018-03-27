package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentContact;
import com.github.pagehelper.Page;

public interface StudentContactDao {
	
	/**
	 * 
	 * @Title: selectForPage
	 * @Description: 学生联系人分页查询
	 * @param queryParam
	 * @return
	 * @throws Exception    设定文件
	 * Page<StudentContact>    返回类型
	 *
	 */
	Page<StudentContact> selectForPage(Map<String, Object> queryParam)throws Exception;
	/**
	 * 查询List
	 * 
	 * @param param 动态参数
	 * @return
	 */
	List<StudentContact> selectList(Map<String, Object> param)throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param studentContact
	 * @throws Exception
	 * @return
	 */
	Integer insert(StudentContact studentContact) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * 
	 * @param studentContact 
	 * @throws Exception
	 * @return
	 */
	Integer update(StudentContact studentContact) throws Exception;
	
	/**
	 * 根据ID修改状态
	 * 
	 * @param param (ids, status)
	 * @throws Exception
	 * @return
	 */
	Integer changeStatus(Map<String, Object> param) throws Exception;
	
	/**
	 * 更新学员默认联系方式及联系方式校验状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Integer updateDefaultPhone(Map<String, Object> param) throws Exception;
	
}
