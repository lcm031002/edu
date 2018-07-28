package com.ebusiness.workflow.ext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.api.task.Task;

/**
 * @ClassName: ExtCurrentUserInfo
 * @Description: 任务处理的时候，设置当前用户的信息
 *
 */
public interface ExtCurrentUserInfo {
	Map<String, Object> genCurrentUserInfo(Task task,
			HttpServletRequest request, HttpServletResponse response);
}
