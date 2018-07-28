package com.edu.common.mail;

import java.util.Properties;

import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * @ClassName: SimpleMailFactory
 * @Description: 发送邮件的工程类
 *
 */
public class SimpleMailFactory {

	private static Properties load() {
		SimpleMailConfig.getInstance().load();
		Properties props = new Properties();
		props.put("mail.smtp.host", SimpleMailConfig.getInstance().getHost());
		props.put("mail.smtp.port", SimpleMailConfig.getInstance().getPort());
		props.put("mail.smtp.auth", SimpleMailConfig.getInstance().getAuth());
		return props;
	}

	/**
	 * 功能：取得邮件消息对象的包装器
	 */
	public static SimpleMessageWraper getSimpleMessageWraper() {
		Properties props = SimpleMailFactory.load();
		return new SimpleMessageWraper(props);
	}

	/**
	 * 功能：邮件发送
	 */
	public static void send(SimpleMessageWraper messageWraper) throws Exception {
		MimeMessage mimeMessage = messageWraper.getMimeMessage();
		Transport transport = messageWraper.getSession().getTransport(
				SimpleMailConfig.getInstance().getTransport());

		transport.connect(SimpleMailConfig.getInstance().getHost(),
				SimpleMailConfig.getInstance().getUsername(), SimpleMailConfig
						.getInstance().getPassword());
		
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
	}
}
