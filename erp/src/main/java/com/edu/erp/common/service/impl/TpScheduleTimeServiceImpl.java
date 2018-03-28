package com.edu.erp.common.service.impl;

import com.edu.erp.common.service.TpScheduleTimeService;
import com.edu.erp.dao.TPScheduleTimeDao;
import com.edu.erp.model.TPScheduleTime;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "tpScheduleTimeService")
public class TpScheduleTimeServiceImpl implements TpScheduleTimeService {
	
	@Resource(name = "tpScheduleTimeDao")
	private TPScheduleTimeDao tpScheduleTimeDao;

	/**
	 * 分页查询
	 *
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<TPScheduleTime> page(Page<TPScheduleTime> page) throws Exception {
		
		return tpScheduleTimeDao.selectForPage(page);
	}
	
	@Override
	public Page<Map<String, Object>> queryPage(Map<String, Object> paramMap) throws Exception {
		return tpScheduleTimeDao.selectForPage(paramMap);
	}
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPScheduleTime> list(Map<String, Object> param) throws Exception {
		return tpScheduleTimeDao.selectList(param);
	}
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	@Override
	public void toAdd(TPScheduleTime pojo) throws Exception {
		Integer ret = tpScheduleTimeDao.insert(pojo);
		if(ret < 1)
			throw new RuntimeException("toAdd_error");
	}
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	@Override
	public void toUpdate(TPScheduleTime pojo) throws Exception {
		Integer ret = tpScheduleTimeDao.update(pojo);
		if(ret < 1)
			throw new RuntimeException("toUpdate_error");
	}

	/**
	 * 根据IDS字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void toChangeStatus(String ids, Integer status) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", status);
        tpScheduleTimeDao.changeStatus(param);
	}

}
