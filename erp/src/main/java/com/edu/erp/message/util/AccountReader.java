package com.edu.erp.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName: AccountReader
 * @Description: 账户信息读取器
 */
public class AccountReader {
	private static final String FILE_CFG = "/properties/messagecenter.properties";
	private static final Properties PROPERTIES = new Properties();
	private static final String CORP_ID = "messagecenter.account.corp_id";
	private static final String CORP_PWD = "messagecenter.account.corp_pwd";
	private static final String CORP_SERVICE = "messagecenter.account.corp_service";
	private static final String EXT = "messagecenter.account.ext";
	private static final String SERVICE = "messagecenter.service";
	private static final AccountReader INSTANCE = new AccountReader();
	private long lasttime = System.currentTimeMillis();
	private AtomicBoolean readed = new AtomicBoolean(false);

	private AccountReader() {
	}

	public static AccountReader getInstance() {
		return INSTANCE;
	}

	private void load() {
		long currentTimeMillis = System.currentTimeMillis();
		if (readed.compareAndSet(false, true)
				|| currentTimeMillis - lasttime >= 10000) {
			InputStream inputStream = AccountReader.class
					.getResourceAsStream(FILE_CFG);
			try {
				AccountReader.PROPERTIES.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			lasttime = currentTimeMillis;
		}
	}

	public String getCORP_ID() {
		load();
		return PROPERTIES.getProperty(CORP_ID);
	}

	public String getCORP_PWD() {
		load();
		return PROPERTIES.getProperty(CORP_PWD);
	}

	public String getCORP_SERVICE() {
		load();
		return PROPERTIES.getProperty(CORP_SERVICE);
	}

	public String getEXT() {
		load();
		return PROPERTIES.getProperty(EXT);
	}

	public String getSERVICE() {
		load();
		return PROPERTIES.getProperty(SERVICE);
	}
}
