package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.DataDictionary;
import com.edu.erp.model.TimeSeason;
import com.github.pagehelper.Page;

/**
 * 课程季dao
 * 
 * @author lpe
 * @date 2014-8-19
 */
@Repository(value = "timeSeasonDao")
public interface TimeSeasonDao {
	/**
	 * 保存课程季
	 * 
	 * @param timeSeason
	 *            课程季对象
	 * @return
	 * @throws Exception
	 */
	void insert(TimeSeason timeSeason) throws Exception;

	Page<TimeSeason> selectForPage(Map<String, Object> param) throws Exception;

	/**
	 * @param ids
	 *            ID数组
	 * @return 空
	 * @throws Exception
	 */

	void deleteByIds(List<String> ids) throws Exception;

	/**
	 * @param id
	 *            数据ID
	 * @throws Exception
	 */
	TimeSeason selectById(String id) throws Exception;

	/**
	 * 修改
	 * 
	 * @param timeSeason
	 *            课程季对象
	 * @return 空
	 * @throws Exception
	 */
	void update(TimeSeason timeSeason) throws Exception;

	List<TimeSeason> selectList(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @Description: 查询课程季
	 * @param businessType 业务模型
	 * @param city_id 城市ID
	 * @throws Exception
	 *             设定文件
	 *             
	 * @return List<DataDictionary> 返回类型
	 */
	List<DataDictionary> selectSeasons(Long businessType, Long city_id, Long product_line, Long student_id, Long bu_id) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * @param param
	 * @throws Exception
	 */
	void changeStatus(Map<String, Object> param) throws Exception;
}
