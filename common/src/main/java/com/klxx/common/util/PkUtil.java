package com.klxx.common.util;

import java.util.UUID;

/**
 * 
 * 类名称：PkUtil   
 * 类描述：   
 * 创建人：liuzm   
 * 创建时间：2017-5-11 下午12:31:30        
 * 修改备注：   
 * @version    
 *
 */
public class PkUtil {

	/**
	 * 获取32位唯一的uuid
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
