/**  
 * @Title: TPerformanceDetailsDao.java
 * @Package com.edu.report.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午5:40:24
 * @version KLXX ERPV4.0  
 */
package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TPerformanceDetails;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TPerformanceDetails;
import com.github.pagehelper.Page;

/**
 * @ClassName: TPerformanceDetailsDao
 * @Description: 业绩明细DAO
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 下午5:40:24
 * 
 */
@Repository("performanceDetailsDao")
public interface TPerformanceDetailsDao {
	
	void deleteTaskFlow(Map<String, Object> param)
			throws Exception;
	
	void savePerformanceDetail41and42(Map<String, Object> param)
			throws Exception;
	
	void savePerformanceDetail53(Map<String, Object> param)
			throws Exception;
	
	void savePerformanceDetail51and54(Map<String, Object> param)
			throws Exception;
	
	void savePerformanceDetail52(Map<String, Object> param)
			throws Exception;
	
	void savePerformanceDetail55and54(Map<String, Object> param)
			throws Exception;
	
	void savePerformanceDetailLipei(Map<String, Object> param)
			throws Exception;
	
	/**
     * 查询学员业绩明细
     * @param paramMap 查询条件  团队、校区、学员姓名、学员编号、开始时间、结束时间
     * @return 学员业绩明细
     * @throws Exception
     */
    Page<TPerformanceDetails> selectForPage(Map<String, Object> paramMap) throws Exception;
    
    /**
     * 查询学员业绩明细
     * @param paramMap 查询条件  团队、校区、学员姓名、学员编号、开始时间、结束时间
     * @return 学员业绩明细
     * @throws Exception
     */
    List<TPerformanceDetails> selectForList(Map<String, Object> paramMap) throws Exception;
}
