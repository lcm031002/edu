package com.ebusiness.hrm.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ebusiness.hrm.dao.TabHrmChangeEventDao;
import com.ebusiness.hrm.dao.TabHrmChangeEventHtDao;
import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.TabHrmChangeEvent;
import com.ebusiness.hrm.model.TabHrmChangeEventHt;
import com.ebusiness.hrm.model.TabHrmChangeeventParam;
import com.ebusiness.hrm.model.TabHrmEventtype;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: ChangeEventServiceImpl
 * @Description: 人事异动列表实现
 * @author:liyj
 * @time:2016年12月19日 下午4:53:27
 */
@Service(value = "changeEventService")
public class ChangeEventServiceImpl implements ChangeEventService {

	private static Logger log = Logger.getLogger(ChangeEventServiceImpl.class);
	@Resource(name = "tabHrmChangeEventDao")
	private TabHrmChangeEventDao tabHrmChangeEventDao;

	@Resource(name = "tabHrmChangeEventHtDao")
	private TabHrmChangeEventHtDao tabHrmChangeEventHtDao;
	@Override
	public PageInfo<TabHrmChangeEvent> queryChangeevent(
			Map<String, Object> param, PageParam pageParam) throws Exception {
		Page<TabHrmChangeEvent> list = null;
		PageInfo<TabHrmChangeEvent> page = new PageInfo<TabHrmChangeEvent>();
		try {
			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(pageParam.getPageNum(),
					pageParam.getPageSize());
			list = tabHrmChangeEventDao.querychangeevent(param);
			page = new PageInfo<TabHrmChangeEvent>(list);
		} catch (Exception e) {
			log.error("人事异动查询异常：", e);
		}
		return page;
	}

	@Override
	public boolean updateFollow(Map<String, Object> data) throws Exception {
		int number = tabHrmChangeEventDao.updateFollow(data);
		if (number > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<TabHrmEventtype> queryEventtype() throws Exception {
		return tabHrmChangeEventDao.queryeventtype();
	}

	@Override
	public Map<String, Object> queryChangeeventInfo(Map<String, Object> param) throws Exception {
		List<TabHrmChangeeventParam> list = tabHrmChangeEventDao
				.queryChangeInfo(param);
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			result.put(list.get(i).getParameterKey(), list.get(i)
					.getParameterValue());
		}
		result.put("list", tabHrmChangeEventHtDao.queryeventHisotry(param));
		//查询最新的异动步骤
		TabHrmChangeEvent th=tabHrmChangeEventDao.querystep(param);
		result.put("steps",th.getStep());
		return result;
	}

}
