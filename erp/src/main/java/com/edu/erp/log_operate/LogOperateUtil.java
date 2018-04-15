/**  
 * @Title: LogOperateUtil.java
 * @Package com.modules.log_operate
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年3月3日 下午5:18:39
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.log_operate;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.log_operate.service.LogOperateService;
import com.sun.jna.platform.win32.Netapi32Util.UserInfo;

/**
 * @ClassName: LogOperateUtil
 * @Description: 记录操作日志
 * @author zhuliyong zly@entstudy.com
 * @date 2015年3月3日 下午5:18:39
 */
public class LogOperateUtil {

	private static final Logger log = Logger.getLogger(LogOperateUtil.class);

	private static final LogOperateUtil INSTANCE = new LogOperateUtil();

	private LogOperateService logOperateService;

	public static LogOperateUtil getInstance() {
		return INSTANCE;
	}

	public void setLogOperateService(LogOperateService logOperateService) {
		this.logOperateService = logOperateService;
	}

	/**
	 * 
	 * @Title: LogOperate
	 * @Description: 记录操作日志
	 * @param businessName
	 *            业务类型
	 * @param detailInfo
	 *            详细信息
	 * @param operater
	 *            操作人信息
	 * @param status
	 *            操作状态
	 * @return void 返回类型
	 * @throws Exception
	 */
	public void LogOperate(String businessName, String detailInfo,
			String operater, String status) {
		Map<String, Object> row = new HashMap<String, Object>();
		try {
			row.put("CREATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));

			row.put("BUSINESSNAME", businessName);
			row.put("DETAILINFO", detailInfo);
			row.put("OPERATER", operater);
			row.put("STATUS", status);
			if (StringUtils.isNotBlank(detailInfo)) {
				if (detailInfo.length() > 200) {
					row.put("DETAILINFO", detailInfo.substring(0, 200));
				}
			}

			logOperateService.insertBySQLID(row);
		} catch (Exception e) {
			log.error("error found:", e);
		}
	}

	public String genUserInfo(HttpServletRequest request) {
		Account account = WebContextUtils.genUser(request);
		UserInfo userInfo = (UserInfo) request.getSession()
				.getAttribute("user");
		StringBuilder operater = new StringBuilder();
		if (userInfo != null) {
			operater.append("用户名：");
			operater.append(account.getUserName());
			operater.append("，");
			operater.append("用户ID：");
			operater.append(account.getId());
		} else {
			operater.append("非法访问,IP：");
			operater.append(request.getRemoteAddr());
		}
		return operater.toString();
	}

	public String genUserInfo(Account account) throws Exception {
		StringBuilder operater = new StringBuilder();
		if (account != null) {
			operater.append("用户名：");
			operater.append(account.getUserName());
			operater.append("，");
			operater.append("用户ID：");
			operater.append(account.getId());
		}else{
			throw new Exception("非法访问！");
		}
		return operater.toString();
	}

	public String subDetailInfo(String detailInfo) {
		return (detailInfo.length() < 200 ? detailInfo.toString() : detailInfo
				.toString().substring(0, 200));
	}

}
