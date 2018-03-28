package com.edu.erp.common.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.common.qinniu.QiNiuCoreCall;
import com.edu.erp.common.service.QiNiuService;

/**
 * 
 * @author yecb
 *
 */
@Service("qiNiuService")
public class QiNiuServiceImpl implements QiNiuService {

	private static final Logger log = Logger.getLogger(QiNiuServiceImpl.class);
	private QiNiuCoreCall qiNiuCoreCall = QiNiuCoreCall.getInstance();
	
	@Override
	public String uploadImg(String newPhoto, String oldPhoto) throws Exception {
		String fileName = qiNiuCoreCall.genFileName(newPhoto);
		// 上传图片
		qiNiuCoreCall.uploadBase64(newPhoto, fileName);
		String imgUrl = qiNiuCoreCall.do_main + fileName;
		// 删除旧头像
		if (!StringUtils.isEmpty(oldPhoto)) {
			try {
				qiNiuCoreCall.delete(oldPhoto.substring(oldPhoto.indexOf("webERP")));
			} catch (Exception e) {
				log.error("error found,see:", e);
			}
		}
		return imgUrl;
	}

	@Override
	public Boolean isUrl(String photo) throws Exception {
		return photo.startsWith(qiNiuCoreCall.do_main);
	}

	@Override
	public void delImg(String photo) throws Exception {
		qiNiuCoreCall.delete(photo.substring(photo.indexOf("webERP")));
	}

}
