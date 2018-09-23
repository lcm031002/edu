package com.edu.workflow.modules.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.edu.workflow.modules.index.dao.ExtBusinessEntrustDao;
import com.edu.workflow.modules.index.model.ExtBusinessEntrust;

/**
 * @ClassName: ExtBusinessEntrustDaoImpl
 * @Description: 任务委托关系表
 */
@Repository(value = "extBusinessEntrustDao")
public class ExtBusinessEntrustDaoImpl extends BaseDao<ExtBusinessEntrust>
		implements ExtBusinessEntrustDao {
	
}
