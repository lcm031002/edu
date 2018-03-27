/**  
 * @Title: SimpleMessageWraper.java
 * @Package com.mail
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年4月18日 下午12:03:40
 * @version KLXX ERPV4.0  
 */
package com.edu.common.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @ClassName: SimpleMessageWraper
 * @Description: 邮件信息对象
 * @author zhuliyong zly@entstudy.com
 * @date 2015年4月18日 下午12:03:40
 * 
 */
public class SimpleMessageWraper {
	private Session session;
	private MimeMessage mimeMessage;
	private Multipart multipart = new MimeMultipart();

	public Session getSession() {
		return session;
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	private String arrayToString(String[] array) {
		String s = "";
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (s == "") {
					s = array[i].trim();
				} else {
					s += "," + array[i].trim();
				}
			}
		}
		return s;
	}

	public SimpleMessageWraper(Properties props) {
		session = Session.getInstance(props);
		session.setDebug(false);

		mimeMessage = new MimeMessage(session);
	}

	public void setFrom(String from) throws MessagingException {
		mimeMessage.setFrom(new InternetAddress(from));
		mimeMessage.saveChanges();
	}

	public void setTo(String[] toArray) throws MessagingException {
		String s = arrayToString(toArray);
		Address[] addresses = InternetAddress.parse(s);
		mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
		mimeMessage.saveChanges();
	}

	public void setCc(String[] ccArray) throws MessagingException {
		String s = arrayToString(ccArray);
		Address[] addresses = InternetAddress.parse(s);
		mimeMessage.setRecipients(Message.RecipientType.CC, addresses);
		mimeMessage.saveChanges();
	}

	public void setSubject(String subject) throws MessagingException {
		mimeMessage.setSubject(subject);
		mimeMessage.saveChanges();
	}

	public void setSentDate(Date date) throws MessagingException {
		mimeMessage.setSentDate(date);
		mimeMessage.saveChanges();
	}

	public void setText(String text) throws MessagingException {
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setText(text);
		multipart.addBodyPart(mimeBodyPart);
		mimeMessage.setContent(multipart);
		mimeMessage.saveChanges();
	}

	public void addAttactment(File file) throws MessagingException,
			UnsupportedEncodingException {
		if (file == null)
			return;

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setFileName(MimeUtility.encodeText(file.getName(), "GBK",
				"B")); // 对邮件头采用base64方式编码
		mimeBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
		multipart.addBodyPart(mimeBodyPart);
		mimeMessage.setContent(multipart);
		mimeMessage.saveChanges();
	}
}
