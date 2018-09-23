package com.edu.workflow.common;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.edu.workflow.modules.index.model.ExtTask;
import com.edu.workflow.modules.index.model.ExtTaskVariable;

/**
 * Description : 日期工具类
 *
 */
public final class DataUtil {
	private DataUtil() {
	}

	public static boolean match(Map<String, Object> rs, String exp) {
		if (StringUtils.isEmpty(exp)) {// 如果为空表示匹配到了，保留
			return true;
		}

		for (Iterator<Map.Entry<String, Object>> s = rs.entrySet().iterator(); s
				.hasNext();) {
			Map.Entry<String, Object> r = s.next();
			Object tmpV = r.getValue();
			if (tmpV instanceof Map) {
				Map mo = (Map) tmpV;
				if (match(mo, exp)) {
					return true;
				}
			} else {// 不是map，默认为值都是可以当成字符串的
				if (tmpV == null
						|| (tmpV != null && StringUtils
								.isEmpty(tmpV.toString()))
						|| (tmpV != null && !tmpV.toString().contains(exp))) {
					continue;
				} else {
					return true;
				}
			}

		}
		return false;
	}

	public static boolean match(ExtTask task, String exp) {
		if (StringUtils.isEmpty(exp)) {// 如果为空表示匹配到了，保留
			return true;
		}

		for (Iterator<ExtTaskVariable> s = task.getVariables().iterator(); s
				.hasNext();) {
			ExtTaskVariable r = s.next();
			String valueType = r.getClass_();
			if ("string".equals(valueType)) {
				String string_value = r.getString_value_();
				if (!StringUtils.isEmpty(string_value)
						&& string_value.contains(exp)) {
					return true;
				}
			} else if ("long".equals(valueType)) {
				continue;
			} else if ("blob".equals(valueType)) {
				String text_value_ = r.getText_value_();
				if (!StringUtils.isEmpty(text_value_)
						&& text_value_.contains(exp)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean matchByEntry(Map<String, Object> rs, String key,
			Object value) {
		if (StringUtils.isEmpty(key)) {// 如果为空表示匹配到了，保留
			return true;
		}

		Object tmp1 = rs.get(key);
		if (tmp1 != null && tmp1.toString().equals(value)) {
			return true;
		}

		for (Iterator<Map.Entry<String, Object>> s = rs.entrySet().iterator(); s
				.hasNext();) {
			Map.Entry<String, Object> r = s.next();
			Object tmpV = r.getValue();
			if (tmpV instanceof Map && tmpV != null) {
				Object tmp2 = ((Map) tmpV).get(key);
				if (tmp2 != null && tmp2.toString().equals(value)) {
					return true;
				}
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 如果为null转化为空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String nullToBlank(Object obj) {
		if (obj == null || obj.equals("null")) {
			return "";
		} else {
			return obj.toString();
		}
	}
	/*
	 * public static void main(String[] args) { Map m = new
	 * HashMap<String,Object>(); Map b = new HashMap<String,Object>();
	 * m.put("aaa", "asdfasd"); m.put("bbb", "dafdfa"); m.put("ccc", "adfadf");
	 * m.put("b",b); b.put("qwsada", null); b.put("asdasd", "asdfasdfasd");
	 * b.put("asdfas", "asdfasdfm"); b.put("asdfwe", null); boolean c = match(m,
	 * "m"); System.out.println(c); }
	 */
}
