package com.ebusiness.workflow.modules.index.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ebusiness.workflow.modules.index.dao.ExtTaskDao;
import com.ebusiness.workflow.modules.index.model.ExtTask;
import com.ebusiness.workflow.modules.index.service.ExtTaskService;

/**
 * @ClassName: ExtTaskServiceImpl
 * @Description: 任务查询服务
 *
 */
@Repository(value = "extTaskService")
@Transactional
public class ExtTaskServiceImpl implements ExtTaskService {

	@Resource(name = "extTaskDao")
	private ExtTaskDao extTaskDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.workflow.modules.index.service.ExtTaskService#queryTaskList
	 * (java.util.Map)
	 */
	@Override
	public List<ExtTask> queryTaskList(Map<String, Object> obj) {
		Assert.notNull(obj);
		
		String searchName = null;
		if (obj.get("searchName") != null) {
			searchName = obj.get("searchName") + "";
		}

		return extTaskDao.queryTaskList(searchName);
	}

}
