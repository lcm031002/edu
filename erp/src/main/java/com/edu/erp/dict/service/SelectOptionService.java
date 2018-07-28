package com.edu.erp.dict.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.DataCompanyAccount;
import com.edu.erp.model.Grade;
import com.edu.erp.model.TimeSeason;

/**
 * 公用选择
 *
 */
public interface SelectOptionService extends DictionaryService{
	
	/**
	 * 查询年级
	 * 
	 * @return
	 * @throws Exception
	 */
    List<Grade> selectGradeList(Map<String, Object> param) throws Exception;
    
    /**
     * 根据地区查询课程季
     * 
     * @param city_id
     * @return
     * @throws Exception
     */
    List<TimeSeason> selectTimeSeasonList(Long city_id) throws Exception;
    
    /**
	 * 查询公司账户
	 * 
	 * @return
	 * @throws Exception
	 */
    List<DataCompanyAccount> selectCompanyAccountList(Map<String, Object> param) throws Exception;
    
    
}
