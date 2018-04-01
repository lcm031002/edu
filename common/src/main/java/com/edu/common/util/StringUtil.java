package com.edu.common.util;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Description : String 工具类
 * 
 * Author : junli.zhang
 * 
 * Date	: 2014-08-21
 */
public class StringUtil {

	public static Pattern CHINA_MOBILE = Pattern.compile("1(?:3[4-9]|47|5[0-27-9]|8[2378])\\d{8}");

	public static Pattern CHINA_UNICOM = Pattern.compile("1(?:3[0-2]|45|5[56]|86)\\d{8}");

	public static Pattern CHINA_TELECOM = Pattern.compile("1(?:33|53|8[09])\\d{8}");

	public static final String REGEX_MOBILE = "^0?1(?:3[0-9]|4[57]|5[0-35-9]|8[0236789])\\d{8}$";
	
	public static final String REGEX_PHONE = "^(0[0-9]{2,3})?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$|(^400[0-9]{7}$)";
	
	public static String replaceAmp(String str) {
		if (!isEmpty(str))
			return str.replaceAll("&amp;", "&").replaceAll("&", "&amp;");
		else
			return "";
	}

	public static String replaceAll(String str) {
		if (!isEmpty(str))
			return str.replaceAll("\\&", "&amp;").replaceAll("\\$", "&#36;")
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
		else
			return "";
	}

	public static String reverseAll(String str) {
		return isEmpty(str) ? "" : str.replaceAll("&amp;", "\\&").replaceAll(
				"&#36;", "\\$").replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&apos;", "'").replaceAll("&quot;", "\"")
				.replaceAll("&nbsp;", "");
	}
	
	/**
	 * 判断字符串是否为空
	 *
	 * @param value
	 * @return true or false
	 */
	public static boolean isEmpty(String value) {
		return (value == null || "".equals(value.trim()) || "null".equals(value.trim()));
	}

	/**
	 * 判断整形是否为null或0
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Integer value) {
		return (value == null || value == 0);
	}

	/**
	 * 判断List是否为空
	 *
	 * @param list
	 * @return true or false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(List list) {
		if (list == null || list.size() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 判断Set是否为空
	 *
	 * @param set
	 * @return true or false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Set set) {
		if (set == null || set.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断Map是否为空
	 *
	 * @param map
	 * @return true or false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Map map) {
		if (map == null || map.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断Map是否为空
	 *
	 * @param value
	 * @return true or false
	 */
	public static boolean isEmpty(Object Value) {
		if (Value == null || Value.equals("null") || Value.equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 判断数组是否为空
	 *
	 * @param value
	 * @return true or false
	 */
	public static boolean isEmpty(Object[] Value) {
		if (Value == null || Value.length == 0)
			return true;
		else
			return false;
	}

	/**
	 * 根据指定的正则表达式校验字符串
	 *
	 * @param reg
	 *            正则表达式
	 * @param string
	 *            拼配的字符串
	 * @return
	 */
	public static boolean startCheck(String reg, String string) {
		boolean tem = false;

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);

		tem = matcher.matches();
		return tem;
	}

	/**
	 * 检验正整数
	 *
	 * @param str
	 *            检验内容
	 * @param length
	 *            指定长度
	 * @return
	 */
	public static boolean checkPositive(String str, int min, int max) {
		String reg = "[0-9]{" + min + "," + max + "}$";
		return startCheck(reg, str);
	}

	/**
	 * 检验整数,适用于正整数、负整数、0，负整数不能以-0开头, 正整数不能以0开头
	 *
	 * */
	public static boolean checkNr(String nr) {
		String reg = "^(-?)[0-9]+\\d*|0";
		return startCheck(reg, nr);
	}

	/**
	 * 手机号码验证,11位 13 号段0-9 14 号段 5,7 15 号段除4以外 18 号段 6, 7, 8, 9
	 * */
	public static boolean checkCellPhone(String cellPhoneNr) {
		return startCheck(REGEX_MOBILE, cellPhoneNr);
	}
	
	public static boolean checkTel(String phone) {
		return startCheck(REGEX_PHONE, phone);
	}

	/**
	 * 检查邮政编码(中国),6位，第一位必须是非0开头，其他5位数字为0-9
	 * */
	public static boolean checkPostcode(String postCode) {
		String regex = "^[1-9]\\d{5}";
		return startCheck(regex, postCode);
	}

	/**
	 * 检验用户名 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾 用户名有最小长度和最大长度限制，比如用户名必须是4-20位
	 * */
	public static boolean checkUsername(String username, int min, int max) {
		String regex = "[\\w\u4e00-\u9fa5]{" + min + "," + max + "}(?<!_)";
		return startCheck(regex, username);
	}

	/**
	 * 检验用户名 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾 有最小位数限制的用户名，比如：用户名最少为4位字符
	 * */
	public static boolean checkUsername(String username, int min) {
		String regex = "[\\w\u4e00-\u9fa5]){" + min + ",}(?<!_)";
		return startCheck(regex, username);
	}

	/**
	 * 检验用户名 取值范围为a-z,A-Z,0-9,"_",汉字 最少一位字符，最大字符位数无限制，不能以"_"结尾
	 * */
	public static boolean checkUsername(String username) {
		String regex = "[\\w\u4e00-\u9fa5]+(?<!_)";
		return startCheck(regex, username);
	}
	
	/**
	 * 获取十位随机数
	 * @return	String	十位随机数
	 */
	public static String getRandom()
	{
		String retStr = "";
		for(int i = 0; i < 10; i++)
		{
			retStr += new Random().nextInt(10);
		}
		return retStr;
	}
	
	
	  /**  
	  * 按字节截取字符串  
	  *   
	  * @param string  
	  *            原始字符串  
	  * @param append  
	  *            要拼接的字符串
	  * @param count  
	  *            截取位数  
	  * @return 截取后的字符串  
	  * @throws Exception  
	  */  
    public static String substring(String string,String append, int len)   
            throws Exception {   
        // 原始字符不为null，也不是空字符串   
        if (string != null && !"".equals(string)) {   
            // 要截取的字节数大于0，且小于原始字符串的字节数   
            if (len > 0 && len < string.getBytes("GBK").length) {   
                StringBuffer buff = new StringBuffer();   
                char c;   
                for (int i = 0; i < len; i++) {   
                    c = string.charAt(i);   
                    buff.append(c);   
                    if (String.valueOf(c).getBytes("GBK").length > 1) {   
                        // 遇到中文汉字，截取字节总数减1   
                        --len;   
                    }   
                }   
            //   System.out.println(new String(buff.toString().getBytes("GBK"),"UTF-8"));
                if(append!=null&&!append.equals(""))
                	buff.append(append);
                return buff.toString();   
            }   
        }   
        return string;   
    }   
    /**
	 * list 去重复
	 * @param list
	 * @return
	 */
	public static List<?> removeDuplicate(List<?> list) {
	   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
	     for ( int j = list.size() - 1 ; j > i; j -- ) {
	       if (list.get(j).equals(list.get(i))) {
	         list.remove(j);
	       }
	      }
	    }
	  return list;
	}
	
	/***
	 * json格式  ，将空对象 转成"";
	 * @param obj
	 * @return
	 */
	public static Object emptyToString(Object obj){
		if (obj == null || obj.equals("null") || obj.equals(""))
			return "";
		else
			return obj;
	}
	
	/**
	 * 如果为null转化为空字符串
	 * @param obj
	 * @return
	 */
	public static String nullToBlank(Object obj){
	    if (obj == null || obj.equals("null")){
			return "";
	    }else{
		  return obj.toString();	
		}
	}
	
	     public static void main(String[] args) {   
	         // 原始字符串   
	         String s = "我ZWR爱JAVA";   
	         System.out.println("原始字符串：" + s);   
	         try {   
	             System.out.println("截取前1位：" + StringUtil.substring(s,"...",1));   
	             System.out.println("截取前2位：" + StringUtil.substring(s,"...", 2));   
	             System.out.println("截取前4位：" + StringUtil.substring(s,"...", 4));   
	             System.out.println("截取前6位：" + StringUtil.substring(s,"...", 6));   
	             System.out.println("截取前11位：" + StringUtil.substring(s,"...", 11));
	         } catch (Exception e) {   
	             e.printStackTrace();   
	         }   
	     }
	     public static String getRandomString(int length) {   
		        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
		        StringBuffer sb = new StringBuffer();   
		        Random random = new Random();   
		        int range = buffer.length();   
		        for (int i = 0; i < length; i ++) {   
		            sb.append(buffer.charAt(random.nextInt(range)));   
		        }   
		        return sb.toString();   
		    }
}
