package com.ebusiness.hrm.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ebusiness.hrm.dao.TabHrmChangeEventDao;
import com.ebusiness.hrm.dao.TabHrmChangeEventHtDao;
import com.ebusiness.hrm.model.TabHrmChangeEvent;

/**
 * 
 * @ClassName: ChangeEventEmployeeCheckServiceImpl
 * @Description:TODO 人事异动审批流接口实现类
 * @author:liyj
 * @time:2016年12月30日 下午2:15:56
 */
@Service(value = "changeEventEmployeeCheckService")
public class ChangeEventEmployeeCheckServiceImpl implements
		ChangeEventEmployeeCheckService {

	private static final Logger log = Logger
			.getLogger(ChangeEventEmployeeCheckServiceImpl.class);

	@Resource(name = "tabHrmChangeEventDao")
	private TabHrmChangeEventDao tabHrmChangeEventDao;

	@Resource(name = "tabHrmChangeEventHtDao")
	private TabHrmChangeEventHtDao tabHrmChangeEventHtDao;

	@Override
	public boolean insertChangeEvent(Map<String, Object> param)
			throws Exception {
		//查询id
		String taskid=tabHrmChangeEventDao.querytaskid(param);
		if(StringUtils.isBlank(taskid)){
			log.debug("未查询到流程id："+taskid);
			return false;
		}
		param.put("task_id", taskid);
		int number = tabHrmChangeEventDao.insertEvent(param);
		if (number > 0) {
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean insertEventParam(Map<String, Object> param) throws Exception {
		int applyid = tabHrmChangeEventDao.selectChangeId(param);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 便利map中的值 然后存入list中
		Set entrySet = param.entrySet();
		Iterator it = entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry me = (Entry) it.next();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Parameterkey", me.getKey().toString());// KEY
			params.put("Parametervalue", me.getValue().toString());// value
			params.put("applyid", applyid);
			list.add(params);
		}
		int number = tabHrmChangeEventDao.insertEventParam(list);
		if (number > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean insertChangeeventHt(Map<String, Object> param)
			throws Exception {
		TabHrmChangeEvent tabHrmChangeEvent =null;
		if(param.get("event_id")==null){
			// 先去数据库查询 最新的人事异动 数据
			tabHrmChangeEvent	= tabHrmChangeEventDao
					.queryChangeEventid(param);
		}else{
			tabHrmChangeEvent=tabHrmChangeEventDao.querylastchangeInfo(param);
		}
		if(tabHrmChangeEvent.getIs_effect()==null){
			tabHrmChangeEvent.setIs_effect(new Long(0));
		}
		if(tabHrmChangeEvent.getStep()==null){
			tabHrmChangeEvent.setStep(new Long(0));
		}
		int num = tabHrmChangeEventHtDao.insertChangeeventHt(tabHrmChangeEvent);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateChangeEvent(Map<String, Object> param)
			throws Exception {
		int num=(int)param.get("is_effect");
		if(num!=0){
			num=tabHrmChangeEventDao.updateChangeEvent(param);
		}else{
			num=tabHrmChangeEventDao.lastupdateChangeEvent(param);
		}
		if(num>0){
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean insertoutcomeParam(Map<String, Object> param)
			throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 便利map中的值 然后存入list中
		Set entrySet = param.entrySet();
		Iterator it = entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry me = (Entry) it.next();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Parameterkey", me.getKey().toString());// KEY
			params.put("Parametervalue", me.getValue().toString());// value
			params.put("applyid", param.get("event_id"));
			list.add(params);
		}
		int number = tabHrmChangeEventDao.insertEventParam(list);
		if (number > 0) {
			return true;
		}
		return false;
	}

	
}
