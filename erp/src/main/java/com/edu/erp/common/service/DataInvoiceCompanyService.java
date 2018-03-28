package com.edu.erp.common.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.DataInvoiceCompany;
import com.github.pagehelper.Page;

public interface DataInvoiceCompanyService {
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<DataInvoiceCompany> queryPage(Map<String, Object> paramMap)
			throws Exception;

	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 查询条件
	 * @return
	 * @throws Exception
	 */
	List<DataInvoiceCompany> queryList(Map<String, Object> param) throws Exception;

	/**
	 * 新增开票公司
	 * 
	 * @param dataInvoiceCompany 开票公司信息
	 * @throws Exception
	 */
	void save(DataInvoiceCompany dataInvoiceCompany) throws Exception;

	/**
	 * 根据ID开票公司信息
	 * 
	 * @param dataInvoiceCompany 开票公司数据
	 * @throws Exception
	 */
	void update(DataInvoiceCompany dataInvoiceCompany) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids 开票公司编号
	 * @param status 状态
	 * @throws Exception
	 */
	void changeStatus(String ids, Integer status) throws Exception;

}
