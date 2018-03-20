/**  
 * @Title: ServiceUtil.java
 * @Package com.ebusiness.workflow.common
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月1日 下午5:50:25
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.common;

import com.ebusiness.workflow.modules.index.service.BusinessRoleMappingService;
import com.ebusiness.workflow.modules.index.service.ExtProcessDefService;
import com.ebusiness.workflow.modules.index.service.ProcessRoleDefService;

/**
 * @ClassName: ServiceUtil
 * @Description: 服务工具类
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月1日 下午5:50:25
 *
 */
public class ServiceUtil {
	private static final ServiceUtil INSTANCE = new ServiceUtil();
	
	private ServiceUtil(){}
	
	public static ServiceUtil getInstance(){
		return INSTANCE;
	}
	
	private BusinessRoleMappingService businessRoleMappingService;
	
	private ExtProcessDefService extProcessDefService;
	
	private ProcessRoleDefService processRoleDefService;

	public ProcessRoleDefService getProcessRoleDefService() {
		return processRoleDefService;
	}

	public void setProcessRoleDefService(ProcessRoleDefService processRoleDefService) {
		this.processRoleDefService = processRoleDefService;
	}

	public BusinessRoleMappingService getBusinessRoleMappingService() {
		return businessRoleMappingService;
	}

	public void setBusinessRoleMappingService(
			BusinessRoleMappingService businessRoleMappingService) {
		this.businessRoleMappingService = businessRoleMappingService;
	}

	public ExtProcessDefService getExtProcessDefService() {
		return extProcessDefService;
	}

	public void setExtProcessDefService(ExtProcessDefService extProcessDefService) {
		this.extProcessDefService = extProcessDefService;
	}
	
	
}
