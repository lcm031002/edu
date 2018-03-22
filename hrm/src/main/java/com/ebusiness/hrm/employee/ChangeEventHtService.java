package com.ebusiness.hrm.employee;

import java.util.Map;

import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.TabHrmChangeEventHt;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: ChangeEventHtService
 * @Description:TODO 人事异动历史 列表
 * @author:liyj
 * @time:2016年12月29日 上午9:59:25
 */

public interface ChangeEventHtService {
	
	/**
	 * 
	 * @param param
	 * @Description 查询异动历史详细列表
	 * @return
	 * @throws Exception
	 */
	PageInfo<TabHrmChangeEventHt> queryChangeeventHt(Map<String, Object> param,
			PageParam pageParam) throws Exception;
	/**
	 * 查询异动历史列表 单条详细列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> queryeventht(Map<String, Object> param) throws Exception;
}
