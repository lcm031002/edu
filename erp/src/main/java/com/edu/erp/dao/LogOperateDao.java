/**  
 * @Title: LogOperateDao.java
 * @Package com.modules.log_operate.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年3月3日 下午4:50:12
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @ClassName: LogOperateDao
 * @Description: 操作日志dao层
 * @author zhuliyong zly@entstudy.com
 * @date 2015年3月3日 下午4:50:12
 * 
 */
@Repository("logOperateDao")
public interface LogOperateDao {
	/**
	 * 
	 * @Title: insertBySQLID
	 * 
	 * @Description: 使用row，执行给定的sqlID的insert动作
	 * 
	 * @param row
	 *            行数据
	 * @throws Exception
	 *             设定文件
	 * @return int 返回类型
	 */
	int insertBySQLID(Map<String, Object> row) throws Exception;
}
