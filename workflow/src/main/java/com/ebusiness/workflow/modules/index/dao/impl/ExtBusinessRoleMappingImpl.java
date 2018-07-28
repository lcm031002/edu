package com.ebusiness.workflow.modules.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.ebusiness.workflow.modules.index.dao.ExtBusinessRoleMappingDao;
import com.ebusiness.workflow.modules.index.model.ExtBusinessRoleMapping;

/**
 * @ClassName: ExtBusinessrolMappingImpl
 * @Description: 工作流角色与业务角色映射表
 */
@Repository(value = "extBusinessrolMappingDao")
public class ExtBusinessRoleMappingImpl extends BaseDao<ExtBusinessRoleMapping>
		implements ExtBusinessRoleMappingDao {

}
