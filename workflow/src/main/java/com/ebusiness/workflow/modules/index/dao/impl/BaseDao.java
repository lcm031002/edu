package com.ebusiness.workflow.modules.index.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ebusiness.workflow.modules.index.dao.Dao;

/**
 * @ClassName: BaseDao
 * @Description: 基类
 *
 */
public abstract class BaseDao<T> implements Dao<T> {

	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

	@Resource(name = "sessionFactoryJBPM")
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Serializable add(T t) throws Exception {
		try {
			Serializable result = getSession().save(t);
			return result;
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void delete(T t) throws Exception {
		try {
			getSession().delete(t);
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
	}

	@Override
	@Transactional
	public void delete(T t, String sql) throws Exception {
		try {
			getSession().delete(sql, t);
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public T findById(Class<T> type, Serializable key) throws Exception {
		try {
			T t = (T) getSession().get(type, key);
			return t;
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
		return null;
	}

	public Session getSession() {

		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> list(Class<T> model) throws Exception {
		try {
			Query query = getSession().createQuery(
					"from " + model.getSimpleName());
			List<T> result = query.list();
			return result;
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> list(String sql, Class<T> model, T p) {
		try {
			Query query = getSession().createQuery(sql);
			query.setProperties(p);
			List<T> result = query.list();
			return result;
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void update(T t) throws Exception {
		try {
			getSession().update(t);
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void update(T t, String sql) throws Exception {
		try {
			getSession().update(sql, t);
		} catch (Exception e) {
			logger.error("error found,see:", e);
		}
	}
}
