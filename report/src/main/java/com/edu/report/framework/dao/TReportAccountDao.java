package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportAccount;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TReportAccount;

/**
 * 学员账户余额表DAO
 * @ClassName: TReportAccountDao
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月2日 下午5:28:52
 *
 */
@Repository(value = "tReportAccountDao")
public interface TReportAccountDao {

	List<TReportAccount> queryReport(TReportAccount param) throws Exception;
	
	void deleteAll(Map<String, Object> param) throws Exception;
	
	void insert(Map<String, Object> param) throws Exception;
}
