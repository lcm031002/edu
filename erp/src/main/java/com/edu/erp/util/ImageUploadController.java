package com.edu.erp.util;

import com.edu.common.qinniu.QiNiuCoreCall;

public class ImageUploadController extends BaseController {

	private QiNiuCoreCall qiNiuCoreCall = QiNiuCoreCall.getInstance();

	/**
	 * 将Base64格式的图片上传到七牛云
	 * 
	 * @param photoBase64
	 * @return 七牛云上的图片的全路径
	 * @throws Exception
	 */
	public String uploadBase64Image(String photoBase64) throws Exception {
		String fileName = qiNiuCoreCall.genFileName(photoBase64);
		// 上传头像
		qiNiuCoreCall.uploadBase64(photoBase64, fileName);
		return qiNiuCoreCall.do_main + fileName;
	}
}
