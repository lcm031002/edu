package com.edu.workflow.modules.index.dao;

import com.edu.workflow.modules.index.model.ExtTask;
import java.util.List;

/**
 * @ClassName: ExtTaskDao
 * @Description: 任务列表服务
 *
 */
public interface ExtTaskDao  extends Dao<ExtTask>{
	List<ExtTask> queryTaskList(String keyWord);
}
