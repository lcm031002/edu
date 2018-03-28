/**  
 * @Title: DataCompanyAccountServiceImpl.java
 * @Package com.edu.erp.common.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月20日 下午4:49:09
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.common.service.DataCompanyAccountService;
import com.edu.erp.dao.DataCompanyAccountDao;
import com.edu.erp.model.DataCompanyAccount;
import com.github.pagehelper.Page;

/**
 * @ClassName: DataCompanyAccountServiceImpl
 * @Description: 公司账户服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月20日 下午4:49:09
 * 
 */
@Service("dataCompanyAccountService")
public class DataCompanyAccountServiceImpl implements DataCompanyAccountService {
	private static final Logger log = Logger
			.getLogger(DataCompanyAccountServiceImpl.class);
	
	@Resource(name = "dataCompanyAccountDao")
	private DataCompanyAccountDao dataCompanyAccountDao;
	
	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DataCompanyAccountService#queryCompanyAccountInfo(java.util.Map)
	 */
	@Override
	public Page<DataCompanyAccount> queryPage(
			Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		return dataCompanyAccountDao.selectForPage(param);
	}

	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DataCompanyAccountService#selectCompanyAccountList(java.util.Map)
	 */
	@Override
	public List<DataCompanyAccount> queryCompanyAccountList(
			Map<String, Object> param) throws Exception {
		return dataCompanyAccountDao.selectList(param);
	}

	@Override
	public void save(DataCompanyAccount companyAccount) throws Exception {
		dataCompanyAccountDao.insert(companyAccount);
		
	}

	@Override
	public void deleteById(String strId) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", strId);
		dataCompanyAccountDao.deleteById(param);
		
	}

	@Override
	public void update(DataCompanyAccount companyAccount) throws Exception {
		dataCompanyAccountDao.update(companyAccount);
		
	}
}
