package com.edu.erp.dict.service.impl;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.OrganizationExtendDao;
import com.edu.erp.dict.service.OrganizationExtendService;
import com.edu.erp.model.OrganizationExtend;
import com.edu.erp.model.OrganizationInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service("organizationExtendService")
public class OrganizationExtendServiceImpl implements OrganizationExtendService {

    @Resource(name = "organizationExtendDao")
    private OrganizationExtendDao organizationExtendDao;

    @Override
    public void addByOrgInfo(OrganizationInfo orgInfo) throws Exception {
        if (StringUtils.isNotEmpty(orgInfo.getDomain())) {
            String[] domains = orgInfo.getDomain().split(",");
            List<OrganizationExtend> organizationExtendList = new ArrayList<OrganizationExtend>(domains.length);
            OrganizationExtend organizationExtend = null;
            for (String domain : domains) {
                organizationExtend = new OrganizationExtend();
                organizationExtend.setExtendInfo(domain);
                organizationExtend.setExtendType("1");
                organizationExtend.setCreate_user(orgInfo.getCreate_user());
                organizationExtend.setCreate_time(DateUtil.getCurrDateTime());
                organizationExtend.setOrgId(orgInfo.getId());
                organizationExtendList.add(organizationExtend);
            }
            this.organizationExtendDao.deleteByOrgId(orgInfo.getId());
            this.organizationExtendDao.addList(organizationExtendList);
        }
    }
}
