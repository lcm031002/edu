/**  
 * @Title: SQLUtils.java
 * @Package com.ebusiness.erp.util
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月27日 下午7:44:10
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.util;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: SQLUtils
 * @Description: sql防止注入检查
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月27日 下午7:44:10
 * 
 */
public class SQLUtils {
	public static boolean sql_inj(String str) {
		if(!StringUtils.isNotBlank(str)){
			return false;
		}
		String str2 = str.toUpperCase();
		String inj_str = "'|AND|EXEC|INSERT|SELECT|DELETE|UPDATE|COUNT|*|%|CHR|MID|MASTER|TRUNCATE|CHAR|DECLARE|;|OR|-|+|,";

		// 这里的东西还可以自己添加

		String[] inj_stra = inj_str.split("\\|");

		for (int i = 0; i < inj_stra.length; i++)

		{

			if (str2.indexOf(inj_stra[i]) >= 0)

			{

				return true;

			}

		}
		return false;
	}
}
