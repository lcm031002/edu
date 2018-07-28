package com.ebusiness.workflow.modules.index.dao;

import java.util.List;

import com.ebusiness.workflow.modules.index.model.ExtTask;

/**
 * @ClassName: ExtTaskDao
 * @Description: 任务列表服务
 *
 */
public interface ExtTaskDao  extends Dao<ExtTask>{
	List<ExtTask> queryTaskList(String keyWord);
}
