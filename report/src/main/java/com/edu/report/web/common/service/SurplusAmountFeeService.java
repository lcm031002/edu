package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TSurplusAmountFee;
import com.edu.report.model.TSurplusAmountFee;

/**
 * @ClassName: SurplusAmountFeeService
 * @Description: 剩余课时费用表
 *
 */
public interface SurplusAmountFeeService {

	List<TSurplusAmountFee> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(Map<String, Object> param) throws Exception;
}
