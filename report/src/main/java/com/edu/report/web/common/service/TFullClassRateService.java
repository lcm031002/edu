package com.edu.report.web.common.service;


import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;
import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: TFullClassRateService
 * @Description: 满班率API
 *
 */

public interface TFullClassRateService {

	List<TFullClassRate> queryReport(Map<String, Object> param) throws Exception;

//	void updateByTaskFlow(String taskFlow,
//                          Long minOperateNo, Long maxOperateNo) throws Exception;

	List<TFULLClassRateLast> queryDetail(Map<String, Object> param) throws  Exception;

}
