/**  
 * @Title: TaskFlowUtil.java
 * @Package com.edu.report.util
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午4:41:22
 * @version KLXX ERPV4.0  
 */
package com.edu.report.util;

import java.util.Random;

import com.edu.common.util.DateUtil;

/**
 * @ClassName: TaskFlowUtil
 * @Description: 任务批次产生工具类
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 下午4:41:22
 *
 */
public class TaskFlowUtils {
	
	public static String genNextFlow(){
		StringBuffer sb = new StringBuffer();
		sb.append("task");
		sb.append(DateUtil.getCurrDate("yyyyMMddHHmmss"));
		Random r = new Random();
		sb.append(r.nextInt());
		return sb.toString();
	}
	
	public static void main(String[] args){
		System.out.println(TaskFlowUtils.genNextFlow());
	}
}
