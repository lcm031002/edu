/**  
 * @Title: MailTest.java
 * @Package com.mail
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年4月18日 下午12:38:29
 * @version KLXX ERPV4.0  
 */
package com.edu.common.mail;

/**
 * @ClassName: MailTest
 * @Description: 邮件测试
 * @author zhuliyong zly@entstudy.com
 * @date 2015年4月18日 下午12:38:29
 *
 */
public class MailTest {
	public static void main(String[] args){
		try {
			SimpleMessageWraper simpleMessageWraper = SimpleMailFactory.getSimpleMessageWraper();
			simpleMessageWraper.setFrom("erp@klxuexi.org");  
			simpleMessageWraper.setTo(new String[]{"zlyong@klxuexi.org"});  
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
