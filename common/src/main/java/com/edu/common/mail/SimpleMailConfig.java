package com.edu.common.mail;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: SimpleMailConfig
 * @Description: 邮件发送配置
 *
 */
public class SimpleMailConfig {
	private String host;
	private String port;
	private String username;
	private String password;
	private String auth;
	private String transport;

	private static final SimpleMailConfig INSTANCE = new SimpleMailConfig();

	public static SimpleMailConfig getInstance() {
		return INSTANCE;
	}

	public void load() {
		try {
			Properties pr = new Properties();
			InputStream input = SimpleMailConfig.class
					.getResourceAsStream("/properties/mail.properties");
			pr.load(input);
			
			String host = pr.getProperty("mail.send.host");
			String port = pr.getProperty("mail.send.port");
			String username = pr.getProperty("mail.send.username");
			String password = pr.getProperty("mail.send.password");
			String auth = pr.getProperty("mail.send.auth");
			String transport = pr.getProperty("mail.send.transport");
			
			if(StringUtils.isNotBlank(host)){
				setHost(host);
			}
			
			if(StringUtils.isNotBlank(port)){
				setPort(port);
			}
			
			if(StringUtils.isNotBlank(username)){
				setUsername(username);
			}
			
			//TODO加密解密
			if(StringUtils.isNotBlank(password)){
				setPassword(password);
			}
			
			if(StringUtils.isNotBlank(auth)){
				setAuth(auth);
			}
			
			if(StringUtils.isNotBlank(transport)){
				setTransport(transport);
			}
			
		} catch (Exception e) {

		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

}
