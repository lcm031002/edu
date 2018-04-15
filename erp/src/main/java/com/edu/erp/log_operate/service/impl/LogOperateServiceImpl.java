/**  
 * @Title: LogOperateServiceImpl.java
 * @Package com.modules.log_operate.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年3月3日 下午5:08:57
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.log_operate.service.impl;

import com.github.pagehelper.Page;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.edu.erp.dao.LogOperateDao;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.log_operate.service.LogOperateService;
import org.springframework.util.CollectionUtils;

/**
 * @ClassName: LogOperateServiceImpl
 * @Description: 操作日志类型
 * @author zhuliyong zly@entstudy.com
 * @date 2015年3月3日 下午5:08:57
 * 
 */
@Repository("logOperateService")
public class LogOperateServiceImpl implements LogOperateService {
	
	@Resource(name = "logOperateDao")
	private LogOperateDao logOperateDao;

	public LogOperateServiceImpl(){
		LogOperateUtil.getInstance().setLogOperateService(this);
	}
	

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
	@Override
	public int insertBySQLID(Map<String, Object> row) throws Exception {
		Assert.notNull(row);
		return logOperateDao.insertBySQLID(row);
	}

}
