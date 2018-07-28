package com.ebusiness.workflow.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.ebusiness.workflow.xml.model.JPDLModel;
import com.ebusiness.workflow.xml.model.JPDLTaskModel;

/**
 * @ClassName: JaxbReadXml
 * @Description: jaxb解析xml
 *
 */
public class JaxbReadXml {

	private static final Logger log = LoggerFactory
			.getLogger(JaxbReadXml.class);

	@SuppressWarnings("unchecked")
	public static <T> T readString(Class<T> clazz, String context)
			throws JAXBException {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			return (T) u.unmarshal(new File(context));
		} catch (JAXBException e) {
			// logger.trace(e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T readConfig(Class<T> clazz, String config,
			Object... arguments) throws IOException, JAXBException {
		InputStream is = null;
		try {
			if (arguments.length > 0) {
				config = MessageFormat.format(config, arguments);
			}
			// logger.trace("read configFileName=" + config);
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			is = new FileInputStream(config);
			return (T) u.unmarshal(is);
		} catch (IOException e) {
			// logger.trace(config, e);
			throw e;
		} catch (JAXBException e) {
			// logger.trace(config, e);
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T readConfigFromStream(InputStream source, URL schema,
			Class<T>... clazz) throws JAXBException, SAXException {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			if (schema != null) {
				SchemaFactory schemaFactory = SchemaFactory
						.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schemaSrc = schemaFactory.newSchema(schema);
				u.setSchema(schemaSrc);
			}
			return (T) u.unmarshal(source);
		} catch (JAXBException e) {
			log.error("error found ,see:", e);
			throw e;
		} catch (SAXException e) {
			log.error("error found ,see:", e);
			throw e;
		}
	}
	
	
	public static JPDLModel readXMLDom4J(InputStream file){
		JPDLModel jpdl = new JPDLModel();
		SAXReader reader = new SAXReader();  
         Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}              
         Element root=document.getRootElement(); 
         if("process".equalsIgnoreCase(root.getName())){
        	 String key = root.attributeValue("key");
        	 String name = root.attributeValue("name");
        	 String version = root.attributeValue("version");
        	 String description = root.attributeValue("description");
        	 jpdl.setKey(key);
        	 jpdl.setName(name);
        	 jpdl.setVersion(version);
        	 jpdl.setDescription(description);
        	 
        	 @SuppressWarnings("unchecked")
			List<Element> taskList = root.elements();
        	 
        	 List<JPDLTaskModel> taskNodeList = new ArrayList<JPDLTaskModel>();
        	 jpdl.setTask(taskNodeList);
        	 for(Element task:taskList){
        		 if("task".equalsIgnoreCase(task.getName())){
        			 String taskName = task.attributeValue("name");
            		 JPDLTaskModel taskNode = new JPDLTaskModel();
            		 taskNode.setName(taskName);
            		 taskNodeList.add(taskNode);
        		 }
        	 }
         }
         return jpdl;
	}
}
