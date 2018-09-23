package com.edu.workflow.modules.index.service;

import com.edu.workflow.modules.index.model.ExtTask;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ExtTaskService
 * @Description: 任务查询服务
 *
 */
public interface ExtTaskService {
	List<ExtTask> queryTaskList(Map<String,Object> obj);
}
