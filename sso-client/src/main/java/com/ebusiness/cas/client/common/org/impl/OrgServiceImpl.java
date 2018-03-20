/**  
 * @Title: OrgServiceImpl.java
 * @Package com.ebusiness.cas.client.common.org.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月13日 下午6:27:57
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.common.org.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.ebusiness.cas.client.common.model.OrgModel;
import com.ebusiness.cas.client.common.model.UserPersonalSettings;
import com.ebusiness.cas.client.common.org.OrgService;
import com.ebusiness.cas.client.dao.OrgModelDao;
import com.ebusiness.cas.client.dao.UserPersonalSettingsDao;

/**
 * @ClassName: OrgServiceImpl
 * @Description: 查询用户的组织结构权限
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月13日 下午6:27:57
 * 
 */
@Service(value = "orgService")
public class OrgServiceImpl implements OrgService {

	private static final Logger log = Logger.getLogger(OrgServiceImpl.class);

	@Resource(name = "orgModelDao")
	private OrgModelDao orgModelDao;

	@Resource(name = "userPersonalSettingsDao")
	private UserPersonalSettingsDao userPersonalSettingsDao;

	private static final String PARAM_NAME_ORG = "V5_DEFAULT_ORG";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.cas.client.common.org.OrgService#queryRightOrg()
	 */
	public OrgModel queryRightOrg(Long userId) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to query User Orgs.");
		}
		Assert.notNull(userId);
		OrgModel queryObj = new OrgModel();
		queryObj.setUserId(userId);
		List<OrgModel> queryList = orgModelDao.queryByUserId(queryObj);
		OrgModel ROOT = new OrgModel();
		ROOT.setId(-1L);
		ROOT.setText("快乐学习教育集团");
		ROOT.setChildren(queryList);
		if (log.isDebugEnabled()) {
			log.debug("end to query User Orgs.");
		}
		return ROOT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.cas.client.common.org.OrgService#queryAdminOrgTree()
	 */
	public OrgModel queryAdminOrgTree() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to query User Orgs.");
		}
		List<OrgModel> queryList = orgModelDao.queryAll();
		if (log.isDebugEnabled()) {
			log.debug("end to query User Orgs.");
		}
		if (!CollectionUtils.isEmpty(queryList)) {
			OrgModel root = null;
			Map<String, OrgModel> orgs = new HashMap<String, OrgModel>();
			for (OrgModel org : queryList) {
				if (org.getId().intValue() == 1) {
					root = org;
				}

				if (orgs.get(org.getId() + "") == null) {
					orgs.put(org.getId() + "", org);
				}
			}

			for (OrgModel org : queryList) {
				if (orgs.get(org.getParentId() + "") != null) {
					orgs.get(org.getParentId() + "").getChildren().add(org);
				}
			}
			return root;
		}

		OrgModel ROOT = new OrgModel();
		ROOT.setId(-1L);
		ROOT.setText("快乐学习教育集团");
		if (log.isDebugEnabled()) {
			log.debug("end to query User Orgs,not found orgs.");
		}
		return ROOT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.cas.client.common.org.OrgService#selectedOrg(com.ebusiness
	 * .cas.client.common.model.OrgModel, java.lang.Long)
	 */
	public void selectedOrg(OrgModel org, Long userId) throws Exception {
		Assert.notNull(userId);
		Assert.notNull(org);
		UserPersonalSettings userPersonalSettings = new UserPersonalSettings();
		userPersonalSettings.setParamName(PARAM_NAME_ORG);
		userPersonalSettings.setUserId(userId);
		userPersonalSettingsDao.delete(userPersonalSettings);
		userPersonalSettings.setParamVal(org.getId().toString());
		userPersonalSettingsDao.addSettings(userPersonalSettings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.cas.client.common.org.OrgService#querySelectedOrg(java.
	 * lang.Long)
	 */
	public OrgModel querySelectedOrg(Long userId) throws Exception {
		Assert.notNull(userId);
		UserPersonalSettings userPersonalSettings = new UserPersonalSettings();
		userPersonalSettings.setParamName(PARAM_NAME_ORG);
		userPersonalSettings.setUserId(userId);
		List<OrgModel> settingsOrg = orgModelDao.querySelectedOrg(userPersonalSettings);
		if (!CollectionUtils.isEmpty(settingsOrg)) {
			return settingsOrg.get(0);
		}
		return null;
	}

	public List<OrgModel> queryAdminOrgList() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to query User Orgs.");
		}
		return orgModelDao.queryAll();
	}

	public List<OrgModel> queryOrgBranchList(Long userId) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to query User Orgs.");
		}
		Assert.notNull(userId);
		OrgModel queryObj = new OrgModel();
		queryObj.setUserId(userId);
		return orgModelDao.queryByUserId(queryObj);
	}

	public List<OrgModel> queryOrgBuList(Long userId) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to query User Orgs.");
		}
		Assert.notNull(userId);
		OrgModel queryObj = new OrgModel();
		queryObj.setUserId(userId);
		return orgModelDao.queryOrgBuList(queryObj);
	}

}
