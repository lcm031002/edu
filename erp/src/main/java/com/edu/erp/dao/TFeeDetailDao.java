/**  
 * @Title: TOrderDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月19日 下午6:20:18
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TFee;
import com.edu.erp.model.TFeeDetail;

/**
 * 费用明细DAO
 * @author yecb
 *
 */
@Repository(value = "tFeeDetailDao")
public interface TFeeDetailDao {
	/**
	 * 保存费用明细
	 * @param tFeeDetail
	 * @return
	 * @throws Exception
	 */
	int saveFeeDetail(TFeeDetail tFeeDetail) throws Exception;
	/**
	 * 删除费用明细
	 * @param hashMap
	 * @throws Exception
	 */
	void deleteFeeDetail(HashMap<String, String> hashMap) throws Exception;
	/**
	 * 通过订单ID查询出费用明细表的信息
	 * @param tFeeDetail
	 * @return
	 * @throws Exception
	 */
	List<TFeeDetail> queryFeeDetailByOrderId(TFeeDetail tFeeDetail)  throws Exception;
	/**
	 * 通过批改ID查询出费用明细表的信息
	 * @param tFeeDetail
	 * @return
	 * @throws Exception
	 */
	List<TFeeDetail> queryFeeDetailByChangeId(TFeeDetail tFeeDetail)  throws Exception;
	
	/**
	 * 通过订单ID和费用类型更新费用ID
	 * @param tFeeDetail
	 * @throws Exception
	 */
	void updateFeeIdByFee(TFeeDetail fee)  throws Exception;
	/**
	 * 通过changeId更新费用ID
	 * @param tFeeDetail
	 * @throws Exception
	 */
	void updateFeeIdByFeeChangeId(TFeeDetail fee)  throws Exception;
	/**
	 * 通过批改ID查询出待作废处理的费用明细表的信息
	 * @param tFeeDetail
	 * @return
	 * @throws Exception
	 */
	List<TFeeDetail> queryCancelFeeDetailByChangeId(Map<String, Object> map)  throws Exception;
}
