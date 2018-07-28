package com.edu.erp.orders.service;

import com.edu.erp.model.TBankAccount;
import com.edu.erp.model.TFinFee;
import com.edu.erp.model.TFinFeeUse;

/**
 * 资金往来服务
 *
 */
public interface FinFeeService {
	/**
	 * 保存资金往来信息
	 * @param tFinFee
	 * @return
	 * @throws Exception
	 */
	int saveTFinFee(TFinFee tFinFee) throws Exception;
	
	/**
	 * 保存资金用途信息
	 * @param tFinFeeUse
	 * @return
	 * @throws Exception
	 */
	int saveTFinFeeUse(TFinFeeUse tFinFeeUse) throws Exception;
	/**
	 * 保存银行转账账户
	 * @param tBankAccount
	 * @return
	 * @throws Exception
	 */
	int saveTBankAccount(TBankAccount tBankAccount) throws Exception;
}
