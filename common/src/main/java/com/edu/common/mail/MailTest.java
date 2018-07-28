package com.edu.common.mail;

/**
 * @ClassName: MailTest
 * @Description: 邮件测试
 *
 */
public class MailTest {
	public static void main(String[] args){
		try {
			SimpleMessageWraper simpleMessageWraper = SimpleMailFactory.getSimpleMessageWraper();
			simpleMessageWraper.setFrom("");
			simpleMessageWraper.setTo(new String[]{""});
			simpleMessageWraper.setSubject("ceshi");  
			simpleMessageWraper.setText("邮件内容");  
			//w1.addAttactment(new File("c:\\附件.txt")); 
			SimpleMailFactory.send(simpleMessageWraper);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
