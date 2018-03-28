package com.edu.erp.dict.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.erp.common.service.DataCompanyAccountService;
import org.springframework.stereotype.Service;

import com.edu.erp.dict.service.DataGradeService;
import com.edu.erp.dict.service.SelectOptionService;
import com.edu.erp.dict.service.TimeSeasonService;
import com.edu.erp.model.DataCompanyAccount;
import com.edu.erp.model.Grade;
import com.edu.erp.model.TimeSeason;

@Service("selectOptionService")
public class SelectOptionServiceImpl extends DictionaryServiceImpl implements
		SelectOptionService {
	@Resource(name = "dataGradeService")
	private DataGradeService dataGradeService;

	@Resource(name = "timeSeasonService")
	private TimeSeasonService timeSeasonService;
	
	@Resource(name = "dataCompanyAccountService")
	private DataCompanyAccountService dataCompanyAccountService;

	@Override
	public List<Grade> selectGradeList(Map<String, Object> param)
			throws Exception {
		return dataGradeService.list(param);
	}

	@Override
	public List<TimeSeason> selectTimeSeasonList(Long city_id) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("city_id", city_id);
		return timeSeasonService.queryList(param);
	}

	@Override
	public List<DataCompanyAccount> selectCompanyAccountList(
			Map<String, Object> param) throws Exception {
		return dataCompanyAccountService.queryCompanyAccountList(param);
	}

}
