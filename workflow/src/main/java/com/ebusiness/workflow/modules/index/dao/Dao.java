/**  
 * @Title: Dao.java
 * @Package com.ebusiness.workflow.modules.index.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年11月30日 下午8:03:09
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Dao
 * @Description: 持久层的接口
 * @author zhuliyong zly@entstudy.com
 * @date 2014年11月30日 下午8:03:09
 * 
 */
public interface Dao<T> {
	
	/**
	 * 
	 * @Title: add
	 * @Description: 新增实体
	 * @param t
	 *            实体类
	 * @param @throws Exception 设定文件
	 * @return int 返回类型
	 * @throws Exception
	 */
	Serializable add(T t) throws Exception;

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除实体类
	 * @param t
	 *            实体类
	 * @param @throws Exception 设定文件
	 * @return int 返回类型
	 * @throws Exception
	 */
	void delete(T t) throws Exception;
	void delete(T t,String sql) throws Exception;
	/**
	 * 
	 * @Title: findById
	 * @Description: 通过关键字查询实体类对象
	 * @param type 类型
	 * @param key
	 *            关键字
	 * @return T 返回类型
	 * @throws Exception
	 */
	T findById(Class<T> type,Serializable key) throws Exception;
	
	List<T> list(Class<T> model) throws Exception;
	List<T> list(String sql, Class<T> type, T p) ;

	/**
	 * 
	 * @Title: update
	 * @Description: 更新对象值
	 * @param t
	 *            实体类
	 * @param @throws Exception 设定文件
	 * @return int 返回类型
	 * @throws Exception
	 */
	void update(T t) throws Exception;
	void update(T t, String sql) throws Exception;

}
