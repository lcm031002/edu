package com.edu.erp.promotion.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.PrivilegeCriteriaDao;
import com.edu.erp.model.PrivilegeCriteria;
import com.edu.erp.promotion.service.PrivilegeCriteriaService;
import com.github.pagehelper.Page;

@Service(value = "privilegeCriteriaService")
public class PrivilegeCriteriaServiceImpl implements PrivilegeCriteriaService {
	
	@Resource(name = "privilegeCriteriaDao")
	private PrivilegeCriteriaDao privilegeCriteriaDao;
	
	/**
	 * 分页查询
	 * 
	 * @param page 动态参数 
	 * @return
	 */
	@Override
	public Page<PrivilegeCriteria> queryPrivilegeCriteriaForPage(Map<String, Object> page) throws Exception{
		return privilegeCriteriaDao.queryPrivilegeCriteriaForPage(page);
	}
	
	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public PrivilegeCriteria queryPrivilegeCriteriaForOne(Long id) throws Exception {
		return privilegeCriteriaDao.queryPrivilegeCriteriaForOne(id);
	}
	

	/**
	 * 新增
	 * 
	 * @param PrivilegeCriteria
	 * @throws Exception
	 */
	@Override
	public void addPrivilegeCriteria(PrivilegeCriteria PrivilegeCriteria) throws Exception {
		 Assert.notNull(PrivilegeCriteria.getRule_id(),"优惠规则不允许为空");
		 Assert.notNull(PrivilegeCriteria.getName(),"优惠前置名称不允许为空");
		Integer r = privilegeCriteriaDao.insert(PrivilegeCriteria);
		if(r < 1){
			throw new Exception();	
		}
	}
	
	/**
	 * 更新
	 * 
	 * @param PrivilegeCriteria
	 * @throws Exception
	 */
	@Override
	public void updatePrivilegeCriteria(PrivilegeCriteria PrivilegeCriteria) throws Exception{
		Integer r = privilegeCriteriaDao.update(PrivilegeCriteria);
		if(r < 1){
			throw new Exception();	
		}
	}
	
	/**
	 * 根据ID改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void changePrivilegeCriteriaStatus(String ids, Integer status)
			throws Exception {
		String[] idArr = ids.split(",");
		List<PrivilegeCriteria> PrivilegeCriterias = new ArrayList<PrivilegeCriteria>(idArr.length);
		for(String id : idArr){
			if(id.trim().length() == 0)
				continue;
			PrivilegeCriteria instance = new PrivilegeCriteria();
			instance.setId(Long.parseLong(id));
			instance.setStatus(status);
			PrivilegeCriterias.add(instance);
		}
		privilegeCriteriaDao.changePrivilegeCriteriaStatus(PrivilegeCriterias);
	}
	
	@Override
	public void deletePrivilegeCriteria(String ids) throws Exception {
		// 将数据ID字符串转换为List
		List<String> idList = new ArrayList<String>();
		String[] idArray = ids.split(",");
		idList = Arrays.asList(idArray);
		privilegeCriteriaDao.deleteByIds(idList);
	}
		
}
