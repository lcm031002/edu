package com.edu.report.web.common.service.impl;

import java.util.List;

import com.edu.report.dao.UserPermissionDao;
import com.edu.report.web.common.service.UserPermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.report.dao.UserPermissionDao;
import com.edu.report.web.common.service.UserPermissionService;

@Service("userPermissionService")
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	private UserPermissionDao userPermissionDao;
	
	@Override
	public String queryValidBranchInBu(Long buId, Long userId) {
		List<Long> branchs = userPermissionDao.queryValidBranchIdsInBu(buId, userId);
		String join = StringUtils.join(branchs, ",");
		return join;
	}

}
