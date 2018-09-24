package com.edu.workflow.ext.impl;

import com.edu.workflow.ext.ExtCurrentUserInfo;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jbpm.api.task.Task;

/**
 * @ClassName: DefaultExtCurrentUserGet
 * @Description: 获取任务处理的当前用户的信息
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