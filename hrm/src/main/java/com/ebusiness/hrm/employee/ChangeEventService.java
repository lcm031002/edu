package com.ebusiness.hrm.employee;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.TabHrmChangeEvent;
import com.ebusiness.hrm.model.TabHrmEventtype;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: ChangeEventService
 * @Description:实现类
 * @author:liyj
 * @time:2016年12月19日 下午4:52:49
 */
public interface ChangeEventService {
	/**
	 * @Title queryChangeevent
	 * @Description 查询异动列表
	 * @return
	 * @throws Exception
	 */
	PageInfo<TabHrmChangeEvent> queryChangeevent(Map<String, Object> param,
			PageParam pageParam) throws Exception;

	/**
	 * @Description 取消关注
	 * @param 异动id
	 * @return
	 * @throws Exception
	 */
	boolean updateFollow(Map<String, Object> data) throws Exception;

	/**
	 * @Description 人事异动列表查询
	 * @return
	 * @throws Exception
	 */
	List<TabHrmEventtype> queryEventtype() throws Exception;
	/**
	 * @Description 人事异动列表点击详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryChangeeventInfo(Map<String, Object> param) throws Exception;
}
