package com.ebusiness.workflow.modules.index.service;

import java.util.List;
import java.util.Map;

import com.ebusiness.workflow.modules.index.model.ExtTask;

/**
 * @ClassName: ExtTaskService
 * @Description: 任务查询服务
 *
 */
public interface ExtTaskService {
	List<ExtTask> queryTaskList(Map<String,Object> obj);
}
