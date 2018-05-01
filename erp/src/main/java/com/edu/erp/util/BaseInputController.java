package com.edu.erp.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * 导入的Controller基类
 * 		提供一些共用的方法
 * @ClassName: BaseInputController
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年3月29日 下午5:23:12
 *
 */
public class BaseInputController extends BaseController{
	
	private static final Logger log = Logger.getLogger(BaseInputController.class);

	/**
	 * 创建错误消息
	 * @param field
	 * @param msg
	 * @return
	 */
	protected Map<String, String> createErrorMsg(String field, String msg) {
		Map<String, String> errorInfo = new HashMap<>();
		errorInfo.put("field_name", field);
		errorInfo.put("err_msg", msg);
		return errorInfo;
	}
	
	/**
	 * 下载文件
	 * @param filePath
	 * @param response
	 * @throws Exception
	 */
	protected void downLoad(String filePath, HttpServletResponse response) throws Exception {  
        File f = new File(filePath);
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));  
        byte[] buf = new byte[1024];  
        int len = 0;  
        response.reset(); // 非常重要  
        // 纯下载方式  
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="  
                + new String(f.getName().getBytes("GBK"), "ISO-8859-1"));  
        OutputStream out = response.getOutputStream();  
        while ((len = br.read(buf)) > 0) {
        	out.write(buf, 0, len);  
        }
        out.flush();  
        IOUtils.closeQuietly(br);
        IOUtils.closeQuietly(out);
    } 
}
