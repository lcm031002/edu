package com.edu.workflow.modules.index.service;

import java.util.List;

import org.jbpm.api.task.Task;

/**
 * @ClassName: LocalTaskService
 * @Description: 通过业务角色进行业务处理
 *
 */
public interface LocalTaskService {
	List<Task> queryUserTasks(String businessRole);
}
