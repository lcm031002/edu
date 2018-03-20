/**  
 * @Title: ExtProcessDefDaoImpl.java
 * @Package com.ebusiness.workflow.modules.index.dao.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月2日 下午12:04:16
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.ebusiness.workflow.modules.index.dao.ExtProcessDefDao;
import com.ebusiness.workflow.modules.index.model.ExtProcessDef;

/**
 * @ClassName: ExtProcessDefDaoImpl
 * @Description: 流程定义持久层
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月2日 下午12:04:16
 * 
 */
@Repository(value = "extProcessDefDao")
public class ExtProcessDefDaoImpl extends BaseDao<ExtProcessDef> implements
		ExtProcessDefDao {

}
