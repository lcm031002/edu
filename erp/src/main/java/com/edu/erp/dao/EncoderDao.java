/**  
 * @Title: TOrderDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月19日 下午6:20:18
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TEncoder;

/**
 * 业务单据DAO
 * @author yecb
 *
 */
@Repository(value = "encoderDao")
public interface EncoderDao {
	/**
	 * 保存业务单据
	 * @param fee
	 * @return
	 * @throws Exception
	 */
	int saveEncoder(TEncoder tEncoder) throws Exception;
	/**
	 * 查询业务单据
	 * @param tEncoder
	 * @return
	 * @throws Exception
	 */
	TEncoder queryEncoderInfo(TEncoder tEncoder) throws Exception;
	
	/**
	 * 更新业务状态
	 * @param tEncoder
	 * @throws Exception
	 */
	void updateEncoderStatus(TEncoder tEncoder) throws Exception;
	/**
	 * 更新业务状态通过单据ID
	 * @param tEncoder
	 * @throws Exception
	 */
	void updateEncoderById(TEncoder tEncoder) throws Exception;
}
