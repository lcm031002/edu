/**  
 * @Title: ExtTaskDao.java
 * @Package com.ebusiness.workflow.modules.index.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月15日 下午3:37:46
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.dao;

import java.util.List;

import com.ebusiness.workflow.modules.index.model.ExtTask;

/**
 * @ClassName: ExtTaskDao
 * @Description: 任务列表服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月15日 下午3:37:46
 *
 */
public interface ExtTaskDao  extends Dao<ExtTask>{
	List<ExtTask> queryTaskList(String keyWord);
}
