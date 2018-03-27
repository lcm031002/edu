package com.edu.erp.dao;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TBankAccount;
import com.edu.erp.model.TFinFee;
import com.edu.erp.model.TFinFeeUse;

/**
 * 资金往来DAO
 * @author yecb
 *
 */
@Repository(value = "tFinFeeDao")
public interface TFinFeeDao {
	/**
	 * 保存资金往来信息
	 * @param TFinFee
	 * @return
	 * @throws Exception
	 */
	int saveTFinFee(TFinFee tFinFee) throws Exception;
	
	/**
	 * 保存资金用途信息
	 * @param TFinFee
	 * @return
	 * @throws Exception
	 */
	int saveTFinFeeUse(TFinFeeUse tFinFeeUse) throws Exception;
	
	/**
	 * 保存银行转账账户
	 * @param TBankAccount
	 * @return
	 * @throws Exception
	 */
	int saveTBankAccount(TBankAccount tBankAccount) throws Exception;
}
