package com.edu.erp.dao;

import com.edu.erp.model.OrganizationExtend;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository(value = "organizationExtendDao")
public interface OrganizationExtendDao {
    void addList(List<OrganizationExtend> organizationExtendList) throws Exception;

    void deleteByOrgId(Long orgId) throws Exception;
}
