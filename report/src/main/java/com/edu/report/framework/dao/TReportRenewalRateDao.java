package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;

/**
 * 学员账户余额表DAO
 * @ClassName: TReportRenewalRateDao
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月2日 下午5:28:52
 *
 */
@Repository(value = "tReportRenewalRateDao")
public interface TReportRenewalRateDao {

	List<TReportRenewalRateSum> queryForSum(Map<String, Object> param) throws Exception;
	
	List<TReportRenewalRateDetail> queryForLastBase(Map<String, Object> param) throws Exception;
	
	List<TReportRenewalRateDetail> queryForEstimate(Map<String, Object> param) throws Exception;
	
	List<TReportRenewalRateDetail> queryForActual(Map<String, Object> param) throws Exception;
	
}
