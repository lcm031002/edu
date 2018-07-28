package com.ebusiness.workflow.modules.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.ebusiness.workflow.modules.index.dao.ExtProcessDefDao;
import com.ebusiness.workflow.modules.index.model.ExtProcessDef;

/**
 * @ClassName: ExtProcessDefDaoImpl
 * @Description: 流程定义持久层
 *
 */
@Repository(value = "extProcessDefDao")
public class ExtProcessDefDaoImpl extends BaseDao<ExtProcessDef> implements
		ExtProcessDefDao {

}
