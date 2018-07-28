package com.edu.cas.client.common.right.impl;

import java.util.List;

import javax.annotation.Resource;

import com.edu.cas.client.common.right.RightService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.cas.client.common.model.Permission;
import com.edu.cas.client.common.right.RightService;
import com.edu.cas.client.dao.PermissionDao;

/**
 * @ClassName: RightServiceImpl
 * @Description: 用户权限查询服务
 *
 */
@Service(value = "rightService")
public class RightServiceImpl implements RightService {
	private static final Logger log = Logger.getLogger(RightServiceImpl.class);
	
	@Resource(name = "permissionDao")
	private PermissionDao permissionDao;
	
	/* (non-Javadoc)
	 * @see com.edu.cas.client.common.right.RightService#queryUserRightModel(java.lang.Long)
	 */
	public List<Permission> queryUserRightModel(Long userId) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("begin to query user right.");
		}
		Permission queryObject = new Permission();
		queryObject.setUserId(userId);
		
		List<Permission> permissionList = permissionDao.queryByUserId(queryObject);
		
		if(log.isDebugEnabled()){
			log.debug("end to query user right.");
		}
		return permissionList;
	}


}
