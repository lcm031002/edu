/**  
 * @Title: MessageRequirementService.java
 * @Package com.ebusiness.messagecenter
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年1月7日 下午2:55:27
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.message.service;

import java.util.Map;

/**
 * 
 * @ClassName: MessageRequirementService
 * @Description: 短信需求发送接口信息
 * @author zhuliyong zly@entstudy.com
 * @date 2015年1月7日 下午2:55:27
 * 
 */
public interface MessageRequirementService {
	/**
	 * 
	 * @Title: sendMessage
	 * @Description: 短信发送接口
	 * @param msg_content
	 *            短信内容 短信内容长度不超过500个汉字，每个英文或阿拉伯字符也算1个汉字
	 * @param mobile
	 *            下发目的手机号码 多个号码之间用英文逗号隔开，且一次群发总号码数不能超过200个
	 * @param corp_msg_id
	 *            用户发送短信时自己定义的短信id，用于区分状态报告。可为空。英文和数字的组合，总长度不能超过50个字符
	 * @return Map<String,String> 返回类型
	 * @throws Exception
	 */
	Map<String, Object> sendMessage(String msg_content, String mobile,
			String corp_msg_id) throws Exception;
}
