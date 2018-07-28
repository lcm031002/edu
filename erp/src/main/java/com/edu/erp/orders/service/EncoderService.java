package com.edu.erp.orders.service;

import com.edu.erp.model.TEncoder;

/**
 * 业务单据服务
 *
 */
public interface EncoderService {
	/**
	 * 保存业务单据
	 * @param TEncoder
	 * @return
	 * @throws Exception
	 */
	int saveTEncoder(TEncoder tEncoder) throws Exception;
	
	/**
	 * 查询业务单据信息
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

	void updateEncoderNo(TEncoder tEncoder) throws Exception;
}
