package com.edu.erp.message.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.erp.message.service.MessageRequirementService;
import com.edu.erp.message.util.AccountReader;

/**
 * @ClassName: MessageRequirementServiceImpl
 * @Description: 短信需求发送接口信息
 */
@Service(value = "messageRequirementService")
public class MessageRequirementServiceImpl implements MessageRequirementService {
	private static final Logger log = Logger
			.getLogger(MessageRequirementServiceImpl.class);

	private static Integer TIME_OUT = 3000;

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
	 * @return Map<String,String> 返回类型 result_code: 0#数字:提交成功。数字：提交成功的手机数量
	 *         100:余额不足 101:账号关闭 102:短信内容超过500字或为空或内容编码格式不正确
	 *         103:手机号码超过200个或合法手机号码为空或者与通道类型不匹配 106:用户名不存在 107:密码错误
	 *         108:指定访问ip错误 109:业务不存在或者通道关闭 110:小号不合法
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> sendMessage(String msg_content, String mobile,
			String corp_msg_id) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to send message,msg_content is " + msg_content
					+ ",mobile is " + mobile + ",corp_msg_id is" + corp_msg_id);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String service = AccountReader.getInstance().getSERVICE();
		String corp_id = AccountReader.getInstance().getCORP_ID();
		String corp_pwd = AccountReader.getInstance().getCORP_PWD();
		String corp_service = AccountReader.getInstance().getCORP_SERVICE();
		String ext = AccountReader.getInstance().getEXT();

		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIME_OUT);

		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		HttpPost post = new HttpPost(service);
		post.addHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("corp_id", corp_id));
		params.add(new BasicNameValuePair("corp_pwd", corp_pwd));
		params.add(new BasicNameValuePair("corp_service", corp_service));
		params.add(new BasicNameValuePair("mobile", mobile));
		if (log.isDebugEnabled()) {
			log.debug("send message,corp_id is " + corp_id + ",corp_pwd is "
					+ corp_pwd + ",corp_service is " + corp_service
					+ ",service is " + service);
		}

		params.add(new BasicNameValuePair("msg_content", msg_content));
		params.add(new BasicNameValuePair("corp_msg_id ", corp_msg_id));
		params.add(new BasicNameValuePair("ext ", ext));
		post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		try {
			// 执行
			HttpResponse response = client.execute(post);

			int statusCode = response.getStatusLine().getStatusCode();
			result.put("statusCode", (Integer) statusCode);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String responseText = EntityUtils.toString(entity);
				JSONObject jsonobject = JSONObject.fromObject(responseText);
				log.debug("jsonobject:" + jsonobject);
				Iterator<?> it = jsonobject.keys();
				Map<String, String> jsonResult = new HashMap<String, String>();
				while (it.hasNext()) {
					String key = (String) it.next();
					String value = jsonobject.getString(key);
					jsonResult.put(key, value);
				}
				String code = jsonResult.get("code");
				String msg = jsonResult.get("msg");
				String detail = jsonResult.get("detail");
				if (code != null && code.equals("0")) {
					result.put("error", false);
				} else {
					result.put("error", true);
					if (StringUtils.isNotBlank(msg) && !msg.contains("null")) {
						result.put("errMsg", msg);
					} else if (StringUtils.isNotBlank(detail)) {
						result.put("errMsg", detail);
					} else {
						result.put("errMsg", "短信服务器错误，请联系管理员！");
					}
				}
			} else {
				result.put("error", true);
				result.put("errMsg", "短信服务器错误，请联系管理员！");
				if (log.isDebugEnabled()) {
					log.debug("end to send message.短信服务器错误，请联系管理员！");
				}
			}
		} catch (Exception e) {
			result.put("error", true);
			result.put("errMsg", "短信服务器错误，请联系管理员！");
			if (log.isDebugEnabled()) {
				log.debug("end to send message.短信服务器错误，请联系管理员！");
			}
		} finally {
			post.releaseConnection();
		}

		return result;
	}
}
