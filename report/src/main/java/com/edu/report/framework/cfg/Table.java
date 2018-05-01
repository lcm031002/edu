/**  
 * @Title: Table.java
 * @Package com.edu.report.framework.cfg
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午12:05:19
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework.cfg;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @ClassName: Table
 * @Description: 报表依赖的表定义
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 下午12:05:19
 *
 */
@XmlRootElement(name = "table") 
public class Table implements Serializable{
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -5919574667767678829L;
	
	@XmlElement(name = "name") 
	private String name;
	
	@XmlElement(name = "type")  
	private String type;
	
	@XmlElement(name = "key")
	private String key;
	
	@XmlElement(name = "keyPageSize") 
	private String keyPageSize;

	public final String getName() {
		return name;
	}

	public final String getType() {
		return type;
	}

	public final String getKey() {
		return key;
	}

	public final String getKeyPageSize() {
		return keyPageSize;
	}

	@Override
	public String toString() {
		return "Table [name=" + name + ", type=" + type + ", key=" + key
				+ ", keyPageSize=" + keyPageSize + "]";
	}
	
}
