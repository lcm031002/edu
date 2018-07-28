package com.edu.erp.orders.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.TFinFeeDao;
import com.edu.erp.model.TBankAccount;
import com.edu.erp.model.TFinFee;
import com.edu.erp.model.TFinFeeUse;
import com.edu.erp.orders.service.FinFeeService;

/**
 * 资金业务往来服务
 *
 */
@Service(value = "finFeeService")
public class FinFeeServiceImpl implements FinFeeService {
	private static final Logger log = Logger.getLogger(FinFeeServiceImpl.class);

	@Resource(name = "tFinFeeDao")
	private TFinFeeDao tFinFeeDao;

	@Override
	public int saveTFinFee(TFinFee tFinFee) throws Exception {
		return tFinFeeDao.saveTFinFee(tFinFee);
	}

	@Override
	public int saveTFinFeeUse(TFinFeeUse tFinFeeUse) throws Exception {
		return tFinFeeDao.saveTFinFeeUse(tFinFeeUse);
	}

	@Override
	public int saveTBankAccount(TBankAccount tBankAccount) throws Exception {
		// TODO Auto-generated method stub
		return tFinFeeDao.saveTBankAccount(tBankAccount);
	}





}
