package com.edu.report.framework.cfg;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: ReportCfg
 * @Description: 报表配置文件
 *
 */
@XmlRootElement(name = "report") 
public class ReportCfg  implements Serializable{
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 1091872736548861089L;
	
	@XmlElementWrapper(name = "tables")
	@XmlElement(name = "table") 
	private List<Table> tables;
	
	@XmlElementWrapper(name = "tasks")
	@XmlElement(name = "task")
	private List<Task> tasks;

	public final List<Table> getTables() {
		return tables;
	}

	public final List<Task> getTasks() {
		return tasks;
	}

}
