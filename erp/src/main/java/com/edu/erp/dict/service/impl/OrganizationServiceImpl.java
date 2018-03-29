package com.edu.erp.dict.service.impl;

import com.edu.erp.dict.service.OrganizationExtendService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.qinniu.QiNiuCoreCall;
import com.edu.erp.dao.OrganizationDao;
import com.edu.erp.dict.service.OrganizationService;
import com.edu.erp.model.OrganizationInfo;

@Service(value = "organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	@Resource(name = "organizationDao")
	private OrganizationDao organizationDao;

	@Resource(name = "organizationExtendService")
	private OrganizationExtendService organizationExtendService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edu.erp.dict.service.OrganizationService#queryOrganizationInfo
	 * (com.edu.erp.model.OrganizationInfo)
	 */
	@Override
	public List<OrganizationInfo> queryOrganizationInfo(OrganizationInfo orgInfo)
			throws Exception {
		return organizationDao.selectList(orgInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edu.erp.dict.service.OrganizationService#list(java.util.Map)
	 */
	@Override
	public List<OrganizationInfo> list(Map<String, Object> param)
			throws Exception {
		return organizationDao.queryList(param);
	}

	/*
	 * (non-Javadoc)
	 * @see com.edu.erp.dict.service.OrganizationService#queryBuBranchs(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<OrganizationInfo> queryBuBranchs(Long userId,Long buId)
			throws Exception {
		Assert.notNull(userId);
		Assert.notNull(buId);
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("parentId", buId);
		
		return organizationDao.queryBuBranchs(param);
	}

    @Override
    public OrganizationInfo selectById(Long id) throws Exception {
        return this.organizationDao.selectById(id);
    }

	/* (non-Javadoc)
	 * @see com.edu.erp.dict.service.OrganizationService#queryBuBranchs(java.lang.Long)
	 */
	@Override
	public List<OrganizationInfo> queryBuBranchs(Long buId) throws Exception {
		Assert.notNull(buId);
		OrganizationInfo queryObj = new OrganizationInfo();
		queryObj.setParent_id(buId);
		return organizationDao.selectList(queryObj);
	}
	
	@Override
    public List<OrganizationInfo> queryBuBranchs(OrganizationInfo orgInfo) throws Exception {
        return organizationDao.selectList(orgInfo);
    }
	
	/**
     * 组织机构新增
     * @param orgInfo 组织机构信息
     * @throws Exception
     */
	@Override
    public void insert(OrganizationInfo orgInfo) throws Exception {
	    this.organizationDao.insert(orgInfo);
		this.organizationExtendService.addByOrgInfo(orgInfo);
	}
    
    /**
     * 组织机构更新
     * @param orgInfo 组织机构信息
     * @throws Exception
     */
	@Override
    public void update(OrganizationInfo orgInfo) throws Exception {
	    this.organizationDao.update(orgInfo);
	    this.organizationExtendService.addByOrgInfo(orgInfo);
	}

    @Override
    public String uploadLogo(Map<String, Object> jsonMap) throws Exception {
        Integer id = (Integer) jsonMap.get("id");
        String logoData = (String) jsonMap.get("logo");
        Assert.notNull(id, "缺少参数");
        Assert.hasText(logoData,"缺少参数");
        
        String logoUrl = QiNiuCoreCall.getInstance().uploadFile(logoData, null);
        jsonMap.put("logo", logoUrl);
        this.organizationDao.updateLogo(jsonMap);
        return logoUrl;
    }
    
    @Override
    public void deleteLogo(Map<String, Object> jsonMap) throws Exception {
        Integer id = (Integer) jsonMap.get("id");
        OrganizationInfo orgInfo = this.selectById(id.longValue());
        if (orgInfo != null && StringUtils.isNotEmpty(orgInfo.getLogo())) {
            jsonMap.put("logo", orgInfo.getLogo());
            this.organizationDao.deleteLogo(jsonMap);
        }
    }

}