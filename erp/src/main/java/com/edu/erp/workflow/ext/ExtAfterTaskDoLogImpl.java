package com.edu.erp.workflow.ext;

import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.workflow.ext.ExtAfterTaskDoLog;
import javax.servlet.http.HttpServletRequest;

public class ExtAfterTaskDoLogImpl implements ExtAfterTaskDoLog {

	@Override
	public void log(String businessName, String message, String status,
			HttpServletRequest request) {
		LogOperateUtil.getInstance().LogOperate(businessName, message,
				LogOperateUtil.getInstance().genUserInfo(request), status);
	}

}
