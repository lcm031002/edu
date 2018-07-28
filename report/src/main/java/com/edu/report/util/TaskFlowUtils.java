package com.edu.report.util;

import java.util.Random;

import com.edu.common.util.DateUtil;

/**
 * @ClassName: TaskFlowUtil
 * @Description: 任务批次产生工具类
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
