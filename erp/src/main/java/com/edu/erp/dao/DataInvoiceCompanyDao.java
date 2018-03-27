package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.DataInvoiceCompany;
import com.github.pagehelper.Page;

@Repository(value = "dataInvoiceCompanyDao")
public interface DataInvoiceCompanyDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<DataInvoiceCompany> selectPage(Map<String, Object> paramMap)
			throws Exception;

	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	List<DataInvoiceCompany> selectList(Map<String, Object> paramMap)
			throws Exception;

	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer save(DataInvoiceCompany dataInvoiceCompany) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer update(DataInvoiceCompany dataInvoiceCompany) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer changeStatus(Map<String, Object> param) throws Exception;

	/**
	 * 新增校区关系
	 * 
	 * @param refs
	 * @return
	 * @throws Exception
	 */
	Integer addOrgRef(List<Map<String, Long>> refs) throws Exception;

	/**
	 * 删除校区关系
	 * 
	 * @param 课程IDS
	 * @return
	 * @throws Exception
	 */
	Integer removeOrgRef(String ids) throws Exception;
}
