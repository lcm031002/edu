/**  
 * @Title: LogOperateService.java
 * @Package com.modules.log_operate.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年3月3日 下午5:08:34
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.log_operate.service;

import com.github.pagehelper.Page;
import java.util.Map;

/**
 * @ClassName: LogOperateService
 * @Description: 操作日志服务
 * @author zhuliyong zly@entstudy.com
 * @date 2015年3月3日 下午5:08:34
 *
 */
public interface LogOperateService {
	/**
	 * 
	 * @Title: insertBySQLID
	 * 
	 * @Description: 使用row，执行给定的sqlID的insert动作
	 * 
	 * @param row
	 *            行数据
	 * @param row
	 *            row
	 * @throws Exception
	 *             设定文件
	 * @return int 返回类型
	 */
	int insertBySQLID(Map<String, Object> row) throws Exception;

}
