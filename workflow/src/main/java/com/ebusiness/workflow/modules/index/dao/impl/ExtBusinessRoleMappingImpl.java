/**  
 * @Title: ExtBusinessrolMappingImpl.java
 * @Package com.ebusiness.workflow.modules.index.dao.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月1日 下午3:03:36
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.ebusiness.workflow.modules.index.dao.ExtBusinessRoleMappingDao;
import com.ebusiness.workflow.modules.index.model.ExtBusinessRoleMapping;

/**
 * @ClassName: ExtBusinessrolMappingImpl
 * @Description: 工作流角色与业务角色映射表
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月1日 下午3:03:36
 */
@Repository(value = "extBusinessrolMappingDao")
public class ExtBusinessRoleMappingImpl extends BaseDao<ExtBusinessRoleMapping>
		implements ExtBusinessRoleMappingDao {

}
