package com.edu.workflow.modules.index.service;

import com.edu.workflow.modules.index.model.ExtBusinessRoleMapping;
import java.util.List;

/**
 * @ClassName: BusinessRoleMappingService
 * @Description: 业务角色与工作流角色的映射关系
 *
 */
public interface BusinessRoleMappingService {
	void add(ExtBusinessRoleMapping entity);

	void update(ExtBusinessRoleMapping entity);

	void delete(ExtBusinessRoleMapping entity);
	
	void deleteMappingsByProcessKey(ExtBusinessRoleMapping entity);
	
	ExtBusinessRoleMapping queryOne(Long id);
	
	List<ExtBusinessRoleMapping> queryByProcessKey(ExtBusinessRoleMapping entity);

	List<ExtBusinessRoleMapping> queryRoleMappings(ExtBusinessRoleMapping entity);
}
