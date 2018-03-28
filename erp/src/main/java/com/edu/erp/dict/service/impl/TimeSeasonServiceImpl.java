package com.edu.erp.dict.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.TimeSeasonDao;
import com.edu.erp.dict.service.DataGradeService;
import com.edu.erp.dict.service.TimeSeasonService;
import com.edu.erp.model.DataDictionary;
import com.edu.erp.model.TimeSeason;
import com.github.pagehelper.Page;

@Service("timeSeasonService")
public class TimeSeasonServiceImpl implements TimeSeasonService {

	@Resource(name = "timeSeasonDao")
	private TimeSeasonDao timeSeasonDao;
	
	@Resource(name = "dataGradeService")
	private DataGradeService dataGradeService;

	@Override
	public void save(TimeSeason timeSeason, Long buId) throws Exception {
		timeSeason.setStatus(1);
		timeSeasonDao.insert(timeSeason);
		
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("bu_id", buId);
        data.put("dict_id", timeSeason.getId());
        data.put("dict_type", "tab_time_season");
        dataGradeService.toAddBuRel(data);
	}

	@Override
	public Page<TimeSeason> queryPage(Map<String, Object> paramMap)
			throws Exception {
		return timeSeasonDao.selectForPage(paramMap);
	}

	@Override
	public void deleteById(String strId) throws Exception {
		// 将数据ID字符串转换为List
		List<String> idList = new ArrayList<String>();
		String[] idArray = strId.split(",");
		idList = Arrays.asList(idArray);
		timeSeasonDao.deleteByIds(idList);
	}

	@Override
	public TimeSeason queryById(String id) throws Exception {
		return timeSeasonDao.selectById(id);
	}

	@Override
	public void update(TimeSeason timeSeason) throws Exception {
		timeSeasonDao.update(timeSeason);
	}

	@Override
	public List<TimeSeason> queryList(Map<String, Object> param)
			throws Exception {
		Assert.notNull(param);
		return timeSeasonDao.selectList(param);
	}

	/**
	 * 
	 * @Description: 查询课程季
	 * @param businessType
	 *            业务模型
	 * @param city_id
	 *            城市ID
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return List<DictionaryBusiness> 返回类型
	 */
	@Override
	public List<DataDictionary> querySeasons(Long businessType, Long city_id,
			Long product_line, Long student_id, Long bu_id) throws Exception {
		return this.timeSeasonDao.selectSeasons(businessType, city_id,
				product_line, student_id, bu_id);
	}
	
	@Override
	public void toChangeStatus(String ids, Integer status, Long updateUser) throws Exception {
		Assert.notNull(ids);
		Assert.notNull(status);
		Assert.notNull(updateUser);
		Map<String, Object> param = new HashMap<>();
		param.put("ids", ids);
		param.put("status", status);
		param.put("update_user", updateUser);
		
		timeSeasonDao.changeStatus(param);
	}

}
