package com.edu.erp.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.common.service.DataSchoolService;
import com.edu.erp.dao.DataSchoolDao;
import com.edu.erp.model.DataSchool;
import com.github.pagehelper.Page;

@Service(value = "dataSchoolService")
public class DataSchoolServiceImpl implements DataSchoolService {
	
	@Resource(name = "dataSchoolDao")
	private DataSchoolDao dataSchoolDao;
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataSchool> page(Page<DataSchool> page) throws Exception {
		return dataSchoolDao.selectForPage(page);
	}
	
	@Override
	public Page<Map<String, Object>> queryPage(Map<String, Object> paramMap) throws Exception {
		return dataSchoolDao.selectForPage(paramMap);
	}
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataSchool> list(Map<String, Object> param) throws Exception {
		return dataSchoolDao.selectList(param);
	}
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	@Override
	public void toAdd(DataSchool pojo) throws Exception {
		Integer ret = dataSchoolDao.insert(pojo);
		if(ret < 1)
			throw new RuntimeException("toAdd_error");
	}
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	@Override
	public void toUpdate(DataSchool pojo) throws Exception {
		Integer ret = dataSchoolDao.update(pojo);
		if(ret < 1)
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
	public void toChangeStatus(String ids, Integer status) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", status);
		dataSchoolDao.changeStatus(param);
	}

}
