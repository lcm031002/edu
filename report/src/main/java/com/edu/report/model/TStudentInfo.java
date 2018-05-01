/**  
 * @Title: TStudentInfo.java
 * @Package com.edu.report.model
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年11月16日 下午4:42:40
 * @version KLXX ERPV5.0
 */
package com.edu.report.model;

/**
 * @ClassName: TStudentInfo
 * @Description: 学生信息
 * @author ohs@klxuexi.org
 * @date 2017年11月16日 下午4:42:40
 *
 */
public class TStudentInfo {
	
	private String encoding;
	private String  student_name;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
}
