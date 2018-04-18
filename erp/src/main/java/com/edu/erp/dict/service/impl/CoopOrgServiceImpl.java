package com.edu.erp.dict.service.impl;

import com.edu.erp.dao.CoopOrgDao;
import com.edu.erp.dict.service.CoopOrgService;
import com.edu.erp.model.CoopOrg;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("coopOrgService")
public class CoopOrgServiceImpl implements CoopOrgService {

    @Resource(name = "coopOrgDao")
    private CoopOrgDao coopOrgDao;

    @Override
    public Page<CoopOrg> selectForPage(Map<String, Object> paramMap) {
        return this.coopOrgDao.selectForPage(paramMap);
    }

    @Override
    public List<CoopOrg> selectList(Map<String, Object> paramMap) {
        return this.coopOrgDao.selectList(paramMap);
    }

    @Override
    public Integer insert(CoopOrg coopOrg) {
        return this.coopOrgDao.insert(coopOrg);
    }

    @Override
    public Integer update(CoopOrg coopOrg) {
        return this.coopOrgDao.update(coopOrg);
    }

    @Override
    public Integer changeStatus(Map<String, Object> paramMap) {
        return this.coopOrgDao.changeStatus(paramMap);
    }
}
