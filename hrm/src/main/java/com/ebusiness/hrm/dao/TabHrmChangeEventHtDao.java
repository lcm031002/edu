package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.TabHrmChangeEvent;
import com.ebusiness.hrm.model.TabHrmChangeEventHt;
import com.github.pagehelper.Page;

/**
 * 
 *@ClassName: TabHrmChangeEventHtDao 
 * @Description:TODO 人事异动历史Dao 层
 * @author:liyj
 * @time:2016年12月29日 上午10:08:00
 */
@Repository(value="tabHrmChangeEventHtDao")
public interface TabHrmChangeEventHtDao {

	//查询异动历史信息列表 querychangeeventht
	public Page<TabHrmChangeEventHt> querychangeeventht (Map<String,Object> param) throws Exception ;	
	// 人事异动历史表 记录操作
	Integer insertChangeeventHt(TabHrmChangeEvent thc) throws Exception;
	//查询某条单一的
	List<TabHrmChangeEventHt> queryeventHisotry(Map<String,Object>param) throws Exception;

	List<TabHrmChangeEventHt> queryeventht(Map<String,Object>param) throws Exception;
}
