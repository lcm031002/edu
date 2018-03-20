/**  
 * @Title: DefaultExtCurrentUserGet.java
 * @Package com.ebusiness.workflow.ext
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月4日 下午3:21:06
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.ext.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jbpm.api.task.Task;

import com.ebusiness.workflow.ext.ExtCurrentUserInfo;

/**
 * @ClassName: DefaultExtCurrentUserGet
 * @Description: 获取任务处理的当前用户的信息
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月4日 下午3:21:06
 * 
 */
public class DefaultExtCurrentUserGet implements ExtCurrentUserInfo {

	@Override
	public Map<String, Object> genCurrentUserInfo(Task task,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> userInfoObj = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute("USER_OBJ") != null) {
			Object userInfo = session.getAttribute("USER_OBJ");
			// username
			fillValues(userInfo.getClass(), userInfo, userInfoObj);

		}
		return userInfoObj;
	}

	private void fillValues(Class<?> classType, Object obj,
			Map<String, Object> userInfoObj) {
		Field[] fields = classType.getDeclaredFields();
		for (Field field : fields) {
			try {
				if (field.getName().equals("id")) {
					field.setAccessible(true);
					userInfoObj.put("id", field.get(obj));
				}
				if (field.getName().equals("userName")) {
					field.setAccessible(true);
					userInfoObj.put("userName", field.get(obj));
				}
				if (field.getName().equals("employeeName")) {
					field.setAccessible(true);
					userInfoObj.put("employeeName", field.get(obj));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		if (classType.getGenericSuperclass() != null) {
			Class<?> superClass = classType.getSuperclass();// 父类
			fillValues(superClass, obj, userInfoObj);
		}
	}

}
