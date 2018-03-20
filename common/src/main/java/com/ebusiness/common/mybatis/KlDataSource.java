package com.ebusiness.common.mybatis;

import org.apache.commons.lang.StringUtils;
import org.logicalcobwebs.proxool.ProxoolDataSource;

import com.ebusiness.common.util.AESUtils;
import com.ebusiness.common.util.PropertiesTools;

public class KlDataSource extends ProxoolDataSource {
	private static String aesEncodeRule = PropertiesTools
			.getPropertiesByKey("aes_encode_rule");

	static {
		if (StringUtils.isEmpty(aesEncodeRule)) {
			aesEncodeRule = "klxx_encode_rule";
		}
	}

	/**
	 * 重置数据库用户名为明文
	 */
	public void setUser(String username) {
		super.setUser(username);
		super.setUser(AESUtils.decode(aesEncodeRule, username));
	}

	/**
	 * 重置数据库密码为明文
	 */
	public void setPassword(String pwd) {
		super.setPassword(pwd);
		super.setPassword(AESUtils.decode(aesEncodeRule, pwd));
	}
	
	/**
	 * 一个活动连接最大活动时间,默认5分钟
	 */
	@Override
	public long getMaximumActiveTime() {
		return 900000l;
	}
	
	@Override
	public boolean isTestBeforeUse() {
		return true;
	}
	
	@Override
	public boolean isTestAfterUse() {
		return true;
	}
	
	@Override
	public String getHouseKeepingTestSql() {
		return "select 1 from dual";
	}

}
