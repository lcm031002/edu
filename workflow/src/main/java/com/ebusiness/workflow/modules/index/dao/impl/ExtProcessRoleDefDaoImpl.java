package com.ebusiness.workflow.modules.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.ebusiness.workflow.modules.index.dao.ExtProcessRoleDefDao;
import com.ebusiness.workflow.modules.index.model.ExtProcessRoleDef;

/**
 * @ClassName: ExtProcessroleDefDaoImpl
 * @Description: 流程定义节点查询服务类
 *
 */
@Repository(value = "extProcessRoleDefDao")
public class ExtProcessRoleDefDaoImpl extends BaseDao<ExtProcessRoleDef>
		implements ExtProcessRoleDefDao {

}
