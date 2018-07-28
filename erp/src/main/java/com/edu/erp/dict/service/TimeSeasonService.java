package com.edu.erp.dict.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.DataDictionary;
import com.edu.erp.model.TimeSeason;
import com.github.pagehelper.Page;

/**
 * 课程季Service
 * 
 */
public interface TimeSeasonService {
	/**
	 * @param id 课程季ID
	 * @return TimeSeason 课程季对象
	 * @throws Exception
	 */
	TimeSeason queryById(String id) throws Exception;

	Page<TimeSeason> queryPage(Map<String, Object> param)
			throws Exception;
	
	List<TimeSeason> queryList(Map<String, Object> param) throws Exception;
	
	/**
	 * 保存课程季
	 * 
	 * @param timeSeason 课程季对象
	 * @return
	 */
	void save(TimeSeason timeSeason) throws Exception;

	/**
	 * 修改
	 *
	 *            数据字典对象
	 * @return 空
	 * @throws Exception
	 */
	void update(TimeSeason timeSeason) throws Exception;
	
	/**
	 * @param strId 课程季ID，多个用逗号分隔
	 * @return 空
	 * @throws Exception
	 */
	void deleteById(String strId) throws Exception;

	/**
	 * 
	 * @Description: 查询课程季
	 * 
	 * @param businessType
	 *            业务模型
	 * @param city_id
	 *            城市ID
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return List<DictionaryBusiness> 返回类型
	 */
	List<DataDictionary> querySeasons(Long businessType, Long city_id) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @param updateUser
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status, Long updateUser) throws Exception;
}
