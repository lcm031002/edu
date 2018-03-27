package com.edu.common.qinniu;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import sun.misc.BASE64Decoder;

/**
 * 七牛图片服务器配置
 * 
 * @author wCong
 *
 */
public class QiNiuCoreCall {
    private static final Logger log = Logger.getLogger(QiNiuCoreCall.class);

	public static QiNiuCoreCall getInstance() {
		return new QiNiuCoreCall();
	}

	public final String bucket_name = "erp-v4";
	public final String do_main = "http://7rf32s.com2.z0.glb.qiniucdn.com/"; // 删除img的时候要用

	public final Auth auth = Auth.create("NjPpF1ZAjpvkGDJuf-s8DMCn4fgRdVoooblgTtXN",
			"IbhWtexo5ZGBD221OeKacpjyNZJirc7MrGeah3hr");
	public final BucketManager bucketManager = new BucketManager(auth);

	public final String callbackUrl = "";
	public final String callbackHost = "";

	// 简单上传，使用默认策略
	public String getUpToken0() throws Exception {
		return auth.uploadToken(bucket_name);
	}

	// 覆盖上传
	public String getUpToken1() throws Exception {
		return auth.uploadToken(bucket_name, "");
	}

	// 设置指定上传策略
	public String getUpToken2() throws Exception {
		return auth.uploadToken(bucket_name, "", 3600, new StringMap().put("callbackUrl", "call back url")
				.putNotEmpty("callbackHost", "").put("callbackBody", "key=$(key)&hash=$(etag)"));
	}

	// 设置预处理、去除非限定的策略字段
	public String getUpToken3() throws Exception {
		return auth.uploadToken(bucket_name, "", 3600, new StringMap().putNotEmpty("persistentOps", "")
				.putNotEmpty("persistentNotifyUrl", "").putNotEmpty("persistentPipeline", ""), true);
	}

	/**
	 * 上传文件
	 * 
	 * @param loaclFile
	 * @param upFileName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map upload(File loaclFile, String upFileName) throws Exception {
		Map mp = null;
		UploadManager uploadManager = new UploadManager();
		com.qiniu.http.Response res = uploadManager.put(loaclFile, upFileName, getUpToken0());
		if (res.isOK()) {
			mp = res.jsonToObject(Map.class);
		}
		return mp;
	}

	/**
	 * 上传文件
	 * 
	 * @param loaclFile
	 * @param upFileName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map uploadBase64(String photoBase64, String fileName) throws Exception {
		Map mp = null;
		String imageBase64 = photoBase64.split("base64,")[1];
		String upToken = getUpToken0();
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decoderBytes = decoder.decodeBuffer(imageBase64);
		UploadManager uploadManager = new UploadManager();
		Response res = uploadManager.put(decoderBytes, fileName, upToken);
		if (res.isOK()) {
			mp = res.jsonToObject(Map.class);
		}
		return mp;
	}

	public String uploadFileBase64(String fileData, String fileName) throws Exception {
		String upToken = getUpToken0();
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decoderBytes = decoder.decodeBuffer(fileData);
		UploadManager uploadManager = new UploadManager();
		Response res = uploadManager.put(decoderBytes, fileName, upToken);
		return this.do_main + fileName;
	}

	/**
	 * 生成唯一文件名
	 * 
	 * @param photoBase64
	 * @return
	 */
	public String genFileName(String photoBase64) {
		String imageMime = photoBase64.split("base64,")[0];
		String suffix = null;
		if (imageMime.contains("jpg") ) {
			suffix = ".jpg";
		} else if (imageMime.contains("jpeg")) {
			suffix = ".jpeg";
		} else if (imageMime.contains("bmp")) {
			suffix = ".bmp";
		} else if (imageMime.contains("png")) {
			suffix = ".png";
		} else {
			throw new RuntimeException("不支持的图片类型：" + imageMime);
		}
		String fileName = "webERPv5" + new Date().getTime() + UUID.randomUUID() + suffix;
		return fileName;
	}

	/**
	 * 删除七牛文件
	 * 
	 * @param key
	 * @throws Exception
	 */
	public void delete(String key) throws Exception {
		if (StringUtils.isNotEmpty(key)) {
			bucketManager.delete(bucket_name, key);
		}
	}
	
	/**
	 * 上次文件到七牛服务器
	 * @param photoBase64 新文件内容
	 * @param oldPhoto 旧文件：若有旧文件，则从服务器上删除
	 * @return 新文件在七牛服务器的链接地址
	 * @throws Exception
	 */
	public String uploadFile(String photoBase64, String oldPhoto) throws Exception {
	    QiNiuCoreCall qiNiuCoreCall = getInstance();
	    String fileName = qiNiuCoreCall.genFileName(photoBase64);
        // 上传头像
        qiNiuCoreCall.uploadBase64(photoBase64, fileName);
        // 删除旧头像
        if (StringUtils.isNotEmpty(oldPhoto)) {
            try {
                qiNiuCoreCall.delete(oldPhoto.substring(oldPhoto.indexOf("webERP")));
            } catch (Exception e) {
                log.error("error found,see:", e);
            }
        }
        return this.do_main + fileName;
	}

}
