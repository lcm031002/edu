/**  
 * @Title: ExtBusinessEntrustService.java
 * @Package com.ebusiness.workflow.modules.index.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月10日 上午9:58:38
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.service;

import java.util.Date;
import java.util.List;

import com.ebusiness.workflow.modules.index.model.ExtBusinessEntrust;

/**
 * @ClassName: ExtBusinessEntrustService
 * @Description: 委托任务列表
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月10日 上午9:58:38
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
