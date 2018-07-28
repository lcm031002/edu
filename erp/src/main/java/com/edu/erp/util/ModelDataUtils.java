package com.edu.erp.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.edu.common.constants.Constants;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;

/**
 * 数据处理类
 *
 */
public class ModelDataUtils {
	
	
	/**
	 * request.Params转换成request.Attribute
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void transferParaToAttr(ServletRequest request) throws Exception{
		@SuppressWarnings("unchecked")
		Enumeration<String> et = request.getParameterNames();
		while(et.hasMoreElements()){
			String paraName = et.nextElement();
			request.setAttribute(paraName, request.getParameter(paraName));
		}
	}
	
	/**
	 * request.Params转换成Map
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static Map<String, Object> transferParaToMap(ServletRequest request) throws Exception{
		Map<String, Object> dest = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Enumeration<String> et = request.getParameterNames();
		while(et.hasMoreElements()){
			String paraName = et.nextElement();
			String paraValue = request.getParameter(paraName);
			dest.put(paraName, paraValue != null?paraValue.trim():null);
		}
		return dest;
	}
	/**
	 * request.Params转换成Map
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static Map<String, Object> transferParaToMap(ServletRequest request,String encoder) throws Exception{
		Map<String, Object> dest = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Enumeration<String> et = request.getParameterNames();
		while(et.hasMoreElements()){
			String paraName = et.nextElement();
			String paraValue = request.getParameter(paraName);
			dest.put(paraName, paraValue != null?java.net.URLDecoder.decode(paraValue.trim(),encoder):null);
		}
		return dest;
	}
	
	/**
	 * 获取session值
	 *
	 * @param session
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttrFromSession(Class<T> valClass, HttpSession session, String key) throws Exception{
		return (T)session.getAttribute(key);
	}
	
	/**
	 * 获取post参数
	 * 
	 * @param request
	 * @param paramName
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public static <T> T getParamFromReqMap(HttpServletRequest request, String paramName, Class<T> type) throws Exception{
		Map properties  = request.getParameterMap();
		Iterator entries = properties.entrySet().iterator();
		while(entries.hasNext()){ 
			Entry entry = (Entry) entries.next();
			Object key =  entry.getKey();// 前台angular.js post传递参数 以JSON字符串存在于 key中
			if(key != null && key instanceof String){
				 JSONObject jsonObject = JSONObject.fromObject(key);  
			     Map<String, Object> mapJson = JSONObject.fromObject(jsonObject);  
			     for(String k : mapJson.keySet()){
			    	 if(paramName.equals(k)){
			    		 return (T) mapJson.get(k);
			    	 }
			     }
			}
		}
		return null;
	}
	
	/**
	 * springMVC 对JSON转换POJO时 会有个小BUG
	 * 如果JSON存在POJO没有的属性 会报错
	 * 此方法使用反射机制进行过滤 并且转换
	 * 
	 * @param t
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getPojoMatch(Class<T> t, Map<String, Object> paramMap)throws Exception{
		List<String> pojoField = getPojoFieldList(t);
		List<String> removeList = new ArrayList<String>();
		for(String key : paramMap.keySet()){
			boolean filter = true;
			if(StringUtils.isNotEmpty(key)){
				for(String field : pojoField){
					if(key.toLowerCase().equalsIgnoreCase(field.toLowerCase())){
						filter = false;
					}
				}
			}
			if(filter){
				removeList.add(key);
			}
		}
		for(String remove : removeList){
			paramMap.remove(remove);
		}
		// JSon转bean的时候 date类型需要注册格式
		String[] dateFormats = new String[] {
				Constants.DATE_FORMAT_1,
				Constants.DATE_FORMAT_2, 
				Constants.DATE_FORMAT_3, 
				Constants.DATE_FORMAT_4,
				Constants.DATE_FORMAT_5};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats)); 
		return (T)JSONObject.toBean(JSONObject.fromObject(paramMap), t);
	}
	 
	/**
	 * JSON字符串转换成对象
	 * 
	 * @param t
	 * @param str
	 * @param split
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> getPojoMatchList(Class<T> t, String str, String split) throws Exception{
		List<T> list = new ArrayList<T>();
		if(str == null)
			str = "";
		for(String s : str.split(split)){
			if(s.trim().length() == 0)
				continue;
			list.add(getPojoMatch(t, s));
		}
		return list;
	}
	
	/**
	 * JSON字符串转换成对象
	 * 
	 * @param t
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("all") 
	public static <T> T getPojoMatch(Class<T> t, String str) throws Exception{
		String[] dateFormats = new String[] {Constants.DATE_FORMAT_1, Constants.DATE_FORMAT_2, Constants.DATE_FORMAT_3};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats)); 
		if(str == null)
			str = "";
		List<String> pojoField = getPojoFieldList(t);
		List<String> removeList = new ArrayList<String>();
		JSONObject paramJson = JSONObject.fromObject(str.toLowerCase());
		Iterator iterator = paramJson.entrySet().iterator();
		while(iterator.hasNext()){
			Entry obj = (Entry) iterator.next();
			String key = (String) obj.getKey();
			boolean filter = true;
			if(StringUtils.isNotEmpty(key)){
				for(String field : pojoField){
					if(key.toLowerCase().equalsIgnoreCase(field.toLowerCase())){
						filter = false;
					}
				}
			}
			if(filter){
				removeList.add(key);
			}
		}
		for(String remove : removeList){
			paramJson.remove(remove);
		}
		// JSon转bean的时候 date类型需要注册格式
		return (T)JSONObject.toBean(paramJson, t);
	}
	
	/**
	 * 通过递归反射获取对象(及其所有父类)的属性名
	 * 
	 * @param cla
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getPojoFieldList(Class cla){
		List<String> fieldList = new ArrayList<String>();
		Field[] fields = cla.getDeclaredFields(); 
		for(Field field : fields){
			fieldList.add(field.getName());
		}
		Class sup = cla.getSuperclass();
		if(sup == null){
			return fieldList;
		}
		for(String field : getPojoFieldList(sup)){
			fieldList.add(field);
		}
		return fieldList;
	}
	
	/**
	 *	Map.KEY 大写转小写
	 * 
	 * @param maps
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> lowerMapList(List<Map<String, Object>> maps) throws Exception{
		if(maps == null)
			return null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>(maps.size());
		for(Map<String, Object> map : maps){
			resultList.add(lowerMap(map));
		}
		return resultList;
	}
	
	/**
	 * Map.key 大写转小写
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> lowerMap( Map<String, Object> map) throws Exception{
		if(map == null)
			return null;
		Map<String, Object> result = new HashMap<String, Object>();
		for(String key : map.keySet()){
			result.put(key==null?null:key.toLowerCase(), map.get(key));
		}
		return result;
	}
	
	/**
	 * 获取随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) throws Exception{ 
	    StringBuffer buffer = new StringBuffer("0123456789"); 
	    StringBuffer sb = new StringBuffer(); 
	    Random r = new Random(); 
	    int range = buffer.length(); 
	    for (int i = 0; i < length; i ++) { 
	        sb.append(buffer.charAt(r.nextInt(range))); 
	    } 
	    return sb.toString(); 
	}
	
	public static void main(String[] args) {
		try {
			System.err.println(ModelDataUtils.getRandomString(6));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
