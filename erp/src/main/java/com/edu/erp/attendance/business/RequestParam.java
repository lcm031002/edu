/**  
 * @Title: RequestParam.java
 * @Package com.ebusiness.erp.attendance.business
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月21日 下午3:55:58
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.attendance.business;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: RequestParam
 * @Description: 请求参数
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月21日 下午3:55:58
 * 
 */
public class RequestParam implements Serializable{

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -897968237766210588L;
	
	private List<AttendanceBusiness> submitAttendanceList;

	public final List<AttendanceBusiness> getSubmitAttendanceList() {
		return submitAttendanceList;
	}

	public final void setSubmitAttendanceList(
			List<AttendanceBusiness> submitAttendanceList) {
		this.submitAttendanceList = submitAttendanceList;
	}

}
