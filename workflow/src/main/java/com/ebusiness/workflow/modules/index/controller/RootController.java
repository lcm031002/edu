package com.ebusiness.workflow.modules.index.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: RootController
 * @Description: 根访问控制器
 *
 */
@Controller
@RequestMapping(value = { "/workflow" })
public class RootController {

	@RequestMapping(value = { "/*", "/**", "/web/*", "/web/**" })
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String url = request.getRequestURI();
			String[] pathInfo = url.split("/workflow/");
			String fileName = "";
			if (pathInfo.length == 1
					|| (pathInfo.length == 2 && StringUtils
							.isBlank(pathInfo[1]))) {
				fileName = "/workflow/web/index.html";
			} else {
				fileName = "/workflow/" + pathInfo[1];
			}
			if (fileName.endsWith(".html")) {
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(getResource(fileName));
			} else if (fileName.endsWith(".js")) {
				response.setContentType("application/x-javascript");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type",
						"application/x-javascript;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(getResource(fileName));
			} else if (fileName.endsWith(".css")) {
				response.setContentType("text/css");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/css;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(getResource(fileName));
			} else if (fileName.endsWith(".xml")) {
				response.setContentType("text/xml");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/xml;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(getResource(fileName));
			} else if (fileName.endsWith(".png")) {
				response.setContentType("image/png");
				writeImg(fileName, response, "image/png");
			} else if (fileName.endsWith(".jpg")) {
				response.setContentType("image/jpg");
				writeImg(fileName, response, "image/jpg");
			} else if (fileName.endsWith(".gif")) {
				response.setContentType("image/gif");
				writeImg(fileName, response, "image/gif");
			} else {
				fileName += ".html";
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(getResource(fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void writeImg(String fileName, HttpServletResponse response,
			String contentType) {
		InputStream input = this.getClass().getResourceAsStream(fileName);

		byte data[] = null;
		try {
			data = readInputStream(input);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		/*try {
			System.out.println("fileName is " + fileName);
			input.read(data);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} // 读数据
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}*/
		response.setContentType(contentType); // 设置返回的文件类型
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			os.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

	private byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	private String getResource(String file) throws IOException {
		// 返回读取指定资源的输入流
		InputStream is = this.getClass().getResourceAsStream(file);
		if (null == is) {
			throw new IllegalArgumentException(file);
		}
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader read = new BufferedReader(isr);
		StringBuffer s = new StringBuffer();
		String line = null;
		while ((line = read.readLine()) != null) {
			s.append(line + "\r\n");
		}
		return s.toString();
	}
}
