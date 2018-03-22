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
import com.ebusiness.hrm.model.TabHrmChangeEventHt;
import com.ebusiness.hrm.model.TabHrmChangeeventParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: ChangeEventHtServiceImpl
 * @Description:TODO 人事异动历史
 * @author:liyj
 * @time:2016年12月29日 上午10:01:10
 */
@Service(value = "changeEventHtService")
public class ChangeEventHtServiceImpl implements ChangeEventHtService {

	private static Logger log = Logger.getLogger(ChangeEventHtServiceImpl.class);
	
	@Resource(name = "tabHrmChangeEventHtDao")
	private TabHrmChangeEventHtDao tabHrmChangeEventHtDao;

	@Resource(name = "tabHrmChangeEventDao")
	private TabHrmChangeEventDao tabHrmChangeEventDao;
	@Override
	public PageInfo<TabHrmChangeEventHt> queryChangeeventHt(
			Map<String, Object> param, PageParam pageParam) throws Exception {
		Page<TabHrmChangeEventHt> list = null;
		PageInfo<TabHrmChangeEventHt> page = new PageInfo<TabHrmChangeEventHt>();
		try {
			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(pageParam.getPageNum(),
					pageParam.getPageSize());
			list = tabHrmChangeEventHtDao.querychangeeventht(param);
			page = new PageInfo<TabHrmChangeEventHt>(list);
		} catch (Exception e) {
			log.error("人事异动查询异常：", e);
		}
		return page;
	}

	@Override
	public Map<String, Object> queryeventht(Map<String, Object> param)
			throws Exception {
		Map<String, Object> result=new HashMap<String, Object>();
		//参数表
		List<TabHrmChangeeventParam> list = tabHrmChangeEventDao
				.queryChangeInfo(param);
		for (int i = 0; i < list.size(); i++) {
			result.put(list.get(i).getParameterKey(), list.get(i)
					.getParameterValue());
		}
		result.put("list", tabHrmChangeEventHtDao.queryeventht(param));
		return result;
	}
}
