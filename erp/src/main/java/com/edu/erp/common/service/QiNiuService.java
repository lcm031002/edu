package com.edu.erp.common.service;



/**
 * 七牛上传图片
 * @author yecb
 *
 */
public interface QiNiuService {

	/**
	 * 上传图片
	 * @param newPhoto 新图片的imageBase64
	 * @param oldPhoto 旧图片URL
	 * @return 返回上传后的URL
	 * @throws Exception
	 */
	public String uploadImg(String newPhoto,String oldPhoto)throws Exception;
	/**
	 * 判断是否上传后的URL
	 * @param photo
	 * @return
	 * @throws Exception
	 */
	Boolean isUrl(String photo)throws Exception;
	/**
	 * 删除图片
	 * @param newPhoto
	 * @throws Exception
	 */
	void delImg(String photo)throws Exception;
}
