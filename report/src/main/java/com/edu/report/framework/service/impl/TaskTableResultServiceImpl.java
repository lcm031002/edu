package com.edu.report.framework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.TaskTableResultDao;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TTaskTableResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.report.dao.TaskTableResultDao;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TTaskTableResult;

/**
 * @ClassName: TaskTableResultServiceImpl
 * @Description: 查询任务依赖表数据更新状态服务
 *
 */
@Service(value = "taskTableResultService")
public class TaskTableResultServiceImpl implements TaskTableResultService {

	@Resource(name = "taskTableResultDao")
	private TaskTableResultDao taskTableResultDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.framework.service.TaskTableResultService#
	 * queryTaskFlowTaskTableResult(java.lang.String, java.lang.String)
	 */
	@Override
	public TTaskTableResult queryTaskFlowTaskTableResult(String taskTable,
                                                         String taskFlow) throws Exception {
		Assert.notNull(taskTable);
		Assert.notNull(taskFlow);
		TTaskTableResult queryParam = new TTaskTableResult();
		queryParam.setTaskTable(taskTable);
		queryParam.setTaskFlow(taskFlow);

		return taskTableResultDao.queryTaskFlowTaskTableResult(queryParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.framework.service.TaskTableResultService#
	 * queryTaskTableMaxCurrentVal(java.lang.String)
	 */
	@Override
	public TTaskTableResult queryTaskTableMaxCurrentVal(String taskTable)
			throws Exception {
		Assert.notNull(taskTable);
		TTaskTableResult queryParam = new TTaskTableResult();
		queryParam.setTaskTable(taskTable);
		List<TTaskTableResult> taskTableResultList = taskTableResultDao
				.queryTaskTableMaxCurrentVal(queryParam);
		if (!CollectionUtils.isEmpty(taskTableResultList)) {
			return taskTableResultList.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.framework.service.TaskTableResultService#
	 * saveTaskTableResult(com.edu.report.model.TTaskTableResult)
	 */
	@Override
	public void saveTaskTableResult(TTaskTableResult taskTableResult)
			throws Exception {
		Assert.notNull(taskTableResult);
		taskTableResultDao.saveTaskTableResult(taskTableResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edu.report.framework.service.TaskTableResultService#queryMinId
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public TTaskTableResult queryMinId(String taskTable, String taskTableId)
			throws Exception {
		Assert.notNull(taskTable);
		Assert.notNull(taskTableId);
		TTaskTableResult queryParam = new TTaskTableResult();
		queryParam.setTaskTable(taskTable);
		queryParam.setTaskTableId(taskTableId);
		return taskTableResultDao.queryMinId(queryParam);
	}

	@Override
	public void deleteByTaskFlow(String taskFlow) throws Exception {
		this.taskTableResultDao.deleteByTaskFlow(taskFlow);
	}

	/* (non-Javadoc)
	 * @see com.edu.report.framework.service.TaskTableResultService#queryCurrentValTaskFlows(java.lang.String)
	 */
	@Override
	public List<TTaskTableResult> queryCurrentValTaskFlows(String taskId)
			throws Exception {
		Assert.notNull(taskId);
		Map<String,Object> queryMaps = new HashMap<String,Object>();
		queryMaps.put("taskId", taskId);
		return this.taskTableResultDao.queryCurrentValTaskFlows(queryMaps);
	}
	
	/**
     * 查询昨天处理的任务，凌晨再重新处理一次，防止每天0点前处理的数据没有被定时任务执行
     * @return 昨天执行的任务
     * @throws Exception
     */
	@Override
    public List<TTaskTableResult> queryYesterdayTaskFlows() throws Exception {
        return this.taskTableResultDao.queryYesterdayTaskFlows(1);
    }

}
