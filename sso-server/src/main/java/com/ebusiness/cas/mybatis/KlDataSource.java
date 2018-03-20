package com.ebusiness.cas.mybatis;

import java.util.Properties;

import org.logicalcobwebs.proxool.ProxoolDataSource;

import com.ebusiness.cas.encoder.AESEncoder;

public class KlDataSource extends ProxoolDataSource {
	private static String aesEncodeRule = "klxx_encode_rule";
	private static Properties props = new Properties();

	static {
		try {
			props.load(KlDataSource.class
					.getResourceAsStream("/sso.properties"));
			aesEncodeRule = props.getProperty("aes_encode_rule");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重置数据库用户名为明文
	 */
	public void setUser(String username) {
		super.setUser(username);
		super.setUser(AESEncoder.decode(aesEncodeRule, username));
	}

	/**
	 * 重置数据库密码为明文
	 */
	public void setPassword(String pwd) {
		super.setPassword(pwd); 
		super.setPassword(AESEncoder.decode(aesEncodeRule, pwd));
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
