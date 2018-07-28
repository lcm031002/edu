package com.ebusiness.workflow.ext;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: ExtAfterDo
 * @Description: 在任务执行完成后执行扩展调用处理
 *
 */
public interface ExtAfterTaskDoLog {
	void log(String businessName, String message, String status,
			HttpServletRequest request);
}
