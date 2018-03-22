package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.ebusiness.hrm.model.TabHrmChangeEvent;
import com.ebusiness.hrm.model.TabHrmChangeeventParam;
import com.ebusiness.hrm.model.TabHrmEventtype;
import com.github.pagehelper.Page;

/**
 * 
 * @ClassName: TabHrmChangeEventDao
 * @Description:人事异动管理
 * @author:Liyj
 * @time:2016年12月19日 下午4:54:54
 */
@Repository(value = "tabHrmChangeEventDao")
public interface TabHrmChangeEventDao {

	// 查询异动信息列表
	public Page<TabHrmChangeEvent> querychangeevent(Map<String, Object> param)
			throws Exception;

	// 关注表的取消关注
	int updateFollow(Map<String, Object> map) throws Exception;

	// 关注表的关注
	int insertFollow(Map<String, Object> map) throws Exception;

	// 查询人事异动列表类型
	List<TabHrmEventtype> queryeventtype() throws Exception;

	// 插入数据
	int insertEvent(Map<String, Object> param) throws Exception;

	int insertEventParam(List<Map<String, Object>> list) throws Exception;

	// 查询任务id
	int selectChangeId(Map<String, Object> param) throws Exception;

	// 查询最新的一条异动历史
	TabHrmChangeEvent queryChangeEventid(Map<String, Object> param)
			throws Exception;

	List<TabHrmChangeeventParam> queryChangeInfo(Map<String, Object> param)
			throws Exception;

	// 修改人事异动信息
	int updateChangeEvent(Map<String, Object> param) throws Exception;

	// 最后一次修改
	int lastupdateChangeEvent(Map<String, Object> param) throws Exception;

	TabHrmChangeEvent querylastchangeInfo(Map<String, Object>param) throws Exception;

	// 查询流程id
	String querytaskid(Map<String, Object> param) throws Exception;
	
	TabHrmChangeEvent querystep(Map<String, Object> param) throws Exception;
	
	TabHrmChangeEvent queryeventht(Map<String, Object> param) throws Exception;
}
