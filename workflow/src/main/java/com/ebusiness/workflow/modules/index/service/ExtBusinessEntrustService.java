package com.ebusiness.workflow.modules.index.service;

import java.util.Date;
import java.util.List;

import com.ebusiness.workflow.modules.index.model.ExtBusinessEntrust;

/**
 * @ClassName: ExtBusinessEntrustService
 * @Description: 委托任务列表
 *
 */
public interface ExtBusinessEntrustService {

	ExtBusinessEntrust queryBusinessEntrust(Long id);
	
	void addBusinessEntrust(ExtBusinessEntrust entity);

	void cancelExtBusinessEntrust(ExtBusinessEntrust entity);

	List<ExtBusinessEntrust> queryHistoryEntrust(String consignorRole,String consigneeTaskId);

	List<ExtBusinessEntrust> queryCurrentEntrust(String consigneeRole,
			Date currentTime);
	
}
