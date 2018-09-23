package com.edu.workflow.modules.index.service;

import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessDefinition;

/**
 * @ClassName: LocalProcessService
 * @Description: TODO(这里用一句话描述这个类的作用)
 *
 */
public interface LocalProcessService {
	/**
	 * 
	 * @Title: queryUserProcess
	 * @Description: 查询业务角色可见的流程定义
	 * @param businessRole
	 *            业务系统角色信息
	 * @return 设定文件
	 * @return List<ProcessDefinition> 返回类型
	 */
	List<ProcessDefinition> queryUserProcess(String businessRole);
}
