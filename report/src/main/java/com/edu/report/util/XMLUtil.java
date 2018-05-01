/**  
 * @Title: XMLUtil.java
 * @Package com.edu.report.util
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 上午11:39:30
 * @version KLXX ERPV4.0  
 */
package com.edu.report.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

/**
 * @ClassName: XMLUtil
 * @Description: XML映射工具类
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 上午11:39:30
 * 
 */
public class XMLUtil {
	private static final Logger log = Logger.getLogger(XMLUtil.class);

	/**
	 * 将对象直接转换成String类型的 XML输出
	 * 
	 * @param obj
	 *            待转化为xml的对象
	 * @return xml格式的字符串
	 */
	public static String convertToXml(Object obj) {
		// 创建输出流
		StringWriter sw = new StringWriter();
		try {
			// 利用jdk中自带的转换类实现
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			// 格式化xml输出的格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			// 将对象转换成输出流形式的xml
			marshaller.marshal(obj, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		}
		return sw.toString();
	}

	/**
	 * 将对象根据路径转换成xml文件
	 * 
	 * @param obj
	 *            转换的对象
	 * @param path
	 *            输出文件的路劲
	 * @return
	 */
	public static void convertToXml(Object obj, String path) {
		try {
			// 利用jdk中自带的转换类实现
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			// 格式化xml输出的格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			// 将对象转换成输出流形式的xml
			// 创建输出流
			FileWriter fw = null;
			try {
				fw = new FileWriter(path);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("error found,see:", e);
			}
			marshaller.marshal(obj, fw);
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		}
	}

	/**
	 * 将String类型的xml转换成对象
	 */
	public static Object convertXmlStrToObject(Class<?> clazz, String xmlStr) {
		Object xmlObject = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			// 进行将Xml转成对象的核心接口
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(xmlStr);
			xmlObject = unmarshaller.unmarshal(sr);
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		}
		return xmlObject;
	}

	/**
	 * 将file类型的xml转换成对象
	 */
	public static Object convertXmlFileToObject(Class<?> clazz, String xmlPath) {
		Object xmlObject = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			FileReader fr = null;
			try {
				fr = new FileReader(xmlPath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.error("error found,see:", e);
			}
			xmlObject = unmarshaller.unmarshal(fr);
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		}
		return xmlObject;
	}

	/**
	 * 将file类型的xml转换成对象
	 */
	public static Object convertXmlFileToObject(URL xmlPath,Class... clazz) {
		Object xmlObject = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			File fr = new File(xmlPath.toURI());

			if (fr.exists() && fr.canRead()) {
				xmlObject = unmarshaller.unmarshal(fr);
			} else {
				throw new Exception("file not found.");
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		}
		return xmlObject;
	}
}