package com.edu.workflow.common;

import com.edu.workflow.modules.index.service.BusinessRoleMappingService;
import com.edu.workflow.modules.index.service.ExtProcessDefService;
import com.edu.workflow.modules.index.service.ProcessRoleDefService;

/**
 * @ClassName: ServiceUtil
 * @Description: 服务工具类
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
