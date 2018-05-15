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

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TFee;

/**
 * 费用总表DAO
 * @author yecb
 *
 */
@Repository(value = "tFeeDao")
public interface TFeeDao {
	/**
	 * 保存费用总表
	 * @param fee
	 * @return
	 * @throws Exception
	 */
	int saveFee(TFee fee) throws Exception;
	/**
	 * 删除费用总表
	 * @param hashMap
	 * @throws Exception
	 */
	void deleteFee(HashMap<String, String> hashMap) throws Exception;

	/**
	 * 通过订单ID和订单类型更新-》订单编号
	 * @param hashMap
	 * @throws Exception
	 */
	void updateFeeEncoderIdByOrderId(HashMap<String, String> hashMap) throws Exception;
	/**
	 * 通过订单ID和订单类型更新->费用状态，完成时间
	 * @param hashMap
	 * @throws Exception
	 */
	void updateFeeStatusByOrderId(HashMap<String, Object> hashMap) throws Exception;
	/**
	 * 通过订单ID和订单类型查询费用的总额
	 * @param hashMap
	 * @throws Exception
	 */
	TFee queryFeeAmountByOrderId(HashMap<String, String> hashMap) throws Exception;
	/**
	 * 通过操作编码和操作类型查询->费用的总额
	 * @param hashMap  operate_type;operate_no
	 * @throws Exception
	 */
	TFee queryFeeAmountByChangeId(Map<String, Object> hashMap) throws Exception;
	/**
	 * 通过订单据ID->费用状态，完成时间
	 * @param hashMap
	 * @throws Exception
	 */
	void updateFeeStatusByEncoderId(HashMap<String, Object> hashMap) throws Exception;
	/**
	 * 通过操作编码和操作类型更新-》订单编号
	 * @param hashMap
	 * @throws Exception
	 */
	void updateFeeEncoderIdByChangeId(Map<String, Object> hashMap) throws Exception;
	/**
	 * 查询费用信息
	 * @param hashMap
	 * changeId
	 * operateType
	 * @return
	 * @throws Exception
	 */
	List<TFee> queryCancelFeeByChangeId(Map<String, Object> hashMap) throws Exception;

	TFee queryFeeByOrderIdAndFeeType(@Param("orderId") Long orderId, @Param("feeType") Integer feeType);
}
