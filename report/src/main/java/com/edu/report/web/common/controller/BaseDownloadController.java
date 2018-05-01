package com.edu.report.web.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class BaseDownloadController {

	protected static final Logger log = Logger.getLogger(BaseDownloadController.class);

	/**
	 * 下载临时文件 (下载完就删除)
	 * 
	 * @param courseList
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/downloadTempFile" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public void downloadTempFile(@RequestParam(value = "fileName", required = true) String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 临时文件路径
		String tempFilePath = rootPath + File.separator + "temp";
		// 下载文件
		File downloadFile = new File(tempFilePath, fileName);
		try {
			if (!downloadFile.exists()) {
				throw new Exception("文件已失效，请重新导出！");
			}
			// 下载文件
			downLoad(downloadFile.getAbsolutePath(), request, response);
		} catch (Exception e) {
			log.error("error found,see:", e);
			downloadError(request, response, e);
		} finally {
			// 删除临时文件
			if (downloadFile.exists()) {
				downloadFile.delete();
			}
			deleteRetentionTempFile(new File(tempFilePath));
		}
	}
	
	/**
	 * 删除滞留（超过24小时）文件
	 * @param tempFilePath
	 */
	private void deleteRetentionTempFile(File tempFilePath) {
		if(!tempFilePath.exists() || !tempFilePath.isDirectory()) {
			return;
		}
		File[] files = tempFilePath.listFiles();
		if(files == null || files.length < 1) {
			return;
		}
		long currentTime = System.currentTimeMillis();
		for(File file : files) {
			//超过24小时，则删除
			if(file.isFile() && (currentTime-file.lastModified()) > 24 * 3600 * 1000) {
				file.delete();
			}
			
		}
	}

	/**
	 * 下载文件
	 * @param filePath
	 * @param response
	 * @throws Exception
	 */
	private void downLoad(String filePath, HttpServletRequest request, HttpServletResponse response) throws Exception {  
        File f = new File(filePath);
        if(!f.exists()) {
        	throw new Exception("文件不存在！");
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        OutputStream out = null;
        try {
	        byte[] buf = new byte[1024];  
	        int len = 0;  
	        response.reset(); // 非常重要  
	        // 纯下载方式  
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename="  
	                + new String(f.getName().getBytes("GBK"), "ISO-8859-1"));  
	        out = response.getOutputStream();  
	        while ((len = br.read(buf)) > 0) {
	        	out.write(buf, 0, len);  
	        }
	        out.flush();  
        } finally {
	        IOUtils.closeQuietly(br);
	        IOUtils.closeQuietly(out);
        }
    } 

	/**
	 * 错误提示
	 * 
	 */
	private void downloadError(HttpServletRequest request, HttpServletResponse response, Exception e) {
		OutputStream out = null;
		try {
			response.reset();
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			response.setHeader("content-disposition", "");
			response.setContentType("text/html");
			out = response.getOutputStream();
			out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">".getBytes("UTF-8"));
			out.write("<HTML>".getBytes("UTF-8"));
			out.write("  <HEAD><TITLE>导出</TITLE></HEAD>".getBytes("UTF-8"));
			out.write("  <BODY>".getBytes());
			out.write(("<div style='color:red;'>导出失败...原因:" + e.getMessage() + ".....请联系维护人员!<div>").getBytes("UTF-8"));
			out.write("  </BODY>".getBytes("UTF-8"));
			out.write("</HTML>".getBytes("UTF-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
