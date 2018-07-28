package com.ebusiness.workflow.modules.index.service;

import java.util.List;

import com.ebusiness.workflow.modules.index.model.ExtProcessRoleDef;

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
