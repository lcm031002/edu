package com.edu.erp.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.common.service.DataInvoiceCompanyService;
import com.edu.erp.model.DataInvoiceCompany;
import com.edu.erp.dao.DataInvoiceCompanyDao;
import com.github.pagehelper.Page;

@Service(value = "dataInvoiceCompanyService")
public class DataInvoiceCompanyServiceImpl implements DataInvoiceCompanyService {

	@Resource(name = "dataInvoiceCompanyDao")
	private DataInvoiceCompanyDao dataInvoiceCompanyDao;

	/**
	 * 分页查询
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataInvoiceCompany> queryPage(Map<String, Object> paramMap)
			throws Exception {
		return dataInvoiceCompanyDao.selectPage(paramMap);
	}

	/**
	 * 根据条件查询List<T>
	 *
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataInvoiceCompany> queryList(Map<String, Object> paramMap)
			throws Exception {
		return dataInvoiceCompanyDao.selectList(paramMap);
	}

	/**
	 * 新增
	 *
	 * @throws Exception
	 */
	@Override
	public void save(DataInvoiceCompany dataInvoiceCompany) throws Exception {
		Integer ret = dataInvoiceCompanyDao.save(dataInvoiceCompany);
		if (ret < 1)
			throw new RuntimeException("toAdd_error");
	}

	/**
	 * 根据ID修改
	 *
	 *            部门IDS
	 * @throws Exception
	 */
	@Override
	public void update(DataInvoiceCompany dataInvoiceCompany) throws Exception {
		Integer ret = dataInvoiceCompanyDao.update(dataInvoiceCompany);
		if (ret < 1)
			throw new RuntimeException("toUpdate_error");
	}

	/**
	 * 根据IDS字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void changeStatus(String ids, Integer status) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", status);
		dataInvoiceCompanyDao.changeStatus(param);
	}

}
