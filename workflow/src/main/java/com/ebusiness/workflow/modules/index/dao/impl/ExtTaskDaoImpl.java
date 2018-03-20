/**  
 * @Title: ExtTaskDaoImpl.java
 * @Package com.ebusiness.workflow.modules.index.dao.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月15日 下午3:38:55
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ebusiness.workflow.modules.index.dao.ExtTaskDao;
import com.ebusiness.workflow.modules.index.model.ExtTask;

/**
 * @ClassName: ExtTaskDaoImpl
 * @Description: 任务列表服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月15日 下午3:38:55
 * 
 */
@Repository(value = "extTaskDao")
public class ExtTaskDaoImpl extends BaseDao<ExtTask> implements ExtTaskDao {
	private static final Logger logger = LoggerFactory
			.getLogger(ExtTaskDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.workflow.modules.index.dao.Dao#add(java.lang.Object)
	 */
	@Override
	public Serializable add(ExtTask t) throws Exception {
		throw new IllegalArgumentException("method is not allowed.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.workflow.modules.index.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(ExtTask t) throws Exception {
		throw new IllegalArgumentException("method is not allowed.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.workflow.modules.index.dao.Dao#delete(java.lang.Object,
	 * java.lang.String)
	 */
	@Override
	public void delete(ExtTask t, String sql) throws Exception {
		throw new IllegalArgumentException("method is not allowed.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.workflow.modules.index.dao.Dao#findById(java.lang.Class,
	 * java.io.Serializable)
	 */
	@Override
	public ExtTask findById(Class<ExtTask> type, Serializable key)
			throws Exception {
		try {
			ExtTask t = (ExtTask) getSession().get(type, key);
			return t;
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.workflow.modules.index.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public void update(ExtTask t) throws Exception {
		throw new IllegalArgumentException("method is not allowed.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.workflow.modules.index.dao.Dao#update(java.lang.Object,
	 * java.lang.String)
	 */
	@Override
	public void update(ExtTask t, String sql) throws Exception {
		throw new IllegalArgumentException("method is not allowed.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.workflow.modules.index.dao.ExtTaskDao#queryTaskList(java
	 * .lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExtTask> queryTaskList(String keyWord) {
		String hql = null;
		if (!StringUtils.isEmpty(keyWord)) {
			hql = "from ExtTask task where task.name_ like :name_ or task.assignee_ like :assignee_ order by dbid_ desc";
		} else {
			hql = "from ExtTask task  order by dbid_ desc";
		}
		Query query = getSession().createQuery(hql);

		if (!StringUtils.isEmpty(keyWord)) {
			query.setString("name_", "%" + keyWord + "%");
			query.setString("assignee_", "%" + keyWord + "%");
		}
		return query.list();
	}

}
