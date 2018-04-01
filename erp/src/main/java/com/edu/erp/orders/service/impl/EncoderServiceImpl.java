/**  
 * @Title: OrderChangeServiceImpl.java
 * @Package com.ebusiness.erp.orders.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月13日 下午8:52:22
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.EncoderDao;
import com.edu.erp.model.TEncoder;
import com.edu.erp.orders.service.EncoderService;

/**
 * 业务单据服务
 * @author yecb
 *
 */
@Service(value = "encoderService")
public class EncoderServiceImpl implements EncoderService {
	private static final Logger log = Logger.getLogger(EncoderServiceImpl.class);

	@Resource(name = "encoderDao")
	private EncoderDao encoderDao;

	@Override
	public int saveTEncoder(TEncoder tEncoder) throws Exception {
		// TODO Auto-generated method stub
		return encoderDao.saveEncoder(tEncoder);
	}

	@Override
	public TEncoder queryEncoderInfo(TEncoder tEncoder) throws Exception {
		// TODO Auto-generated method stub
		return encoderDao.queryEncoderInfo(tEncoder);
	}

	@Override
	public void updateEncoderStatus(TEncoder tEncoder) throws Exception {
		encoderDao.updateEncoderStatus(tEncoder);
	}

	@Override
	public void updateEncoderById(TEncoder tEncoder) throws Exception {
		encoderDao.updateEncoderById(tEncoder);
		
	}



}
