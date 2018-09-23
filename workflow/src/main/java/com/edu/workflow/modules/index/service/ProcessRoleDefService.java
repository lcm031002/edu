package com.edu.workflow.modules.index.service;

import com.edu.workflow.modules.index.model.ExtProcessRoleDef;
import java.util.List;

/**
 * @ClassName: ProcessRoleDefService
 * @Description: 流程节点角色定义服务
 *
 */
public interface ProcessRoleDefService {
	List<ExtProcessRoleDef> queryByKey(String key);
	ExtProcessRoleDef queryOne(String processKey,String processTask);
	void add(ExtProcessRoleDef entity);
	void delete(ExtProcessRoleDef entity);
	void deleteProcessRoleDef(String processKey);
} 
