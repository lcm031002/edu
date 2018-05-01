package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TSurplusAmountFee;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TSurplusAmountFee;

/**
 * 剩余课时费用表
 * @ClassName: SurplusAmountFeeDao
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月12日 上午11:25:18
 *
 */
@Repository(value = "surplusAmountFeeDao")
public interface SurplusAmountFeeDao {

	List<TSurplusAmountFee> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteAll(Map<String, Object> param) throws Exception;
	
	void insert(Map<String, Object> param) throws Exception;
}
