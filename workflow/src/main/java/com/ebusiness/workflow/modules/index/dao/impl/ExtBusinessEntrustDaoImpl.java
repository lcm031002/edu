/**  
 * @Title: ExtBusinessEntrustDaoImpl.java
 * @Package com.ebusiness.workflow.modules.index.dao.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月1日 下午3:00:39
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.ebusiness.workflow.modules.index.dao.ExtBusinessEntrustDao;
import com.ebusiness.workflow.modules.index.model.ExtBusinessEntrust;

/**
 * @ClassName: ExtBusinessEntrustDaoImpl
 * @Description: 任务委托关系表
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月1日 下午3:00:39
 */
@Repository(value = "extBusinessEntrustDao")
public class ExtBusinessEntrustDaoImpl extends BaseDao<ExtBusinessEntrust>
		implements ExtBusinessEntrustDao {
	
}
