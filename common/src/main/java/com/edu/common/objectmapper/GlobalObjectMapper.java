package com.edu.common.objectmapper;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

/**
 * 全局Json对象映射
 */
public class GlobalObjectMapper extends ObjectMapper 
{
	public GlobalObjectMapper() 
	{
		//添加空值全局Json序列化器
		NullValueJsonSerializer nullValueJsonSerializer = new NullValueJsonSerializer();
		this.getSerializerProvider().setNullValueSerializer(nullValueJsonSerializer);
		
		CustomSerializerFactory serializerFactory = new CustomSerializerFactory();
		
		/**
		 * 添加日期类型全局Json序列化器
		 * 注： 1、此只适用Date格式输出为yyyy-MM-dd HH:mm:ss格式
		 * 	   2、如需对Date格式输出为其他格式，则自定义JsonSerializer，例： DateJsonSerializer，
		 *        然后在对应属性的get方法上添加注解即可，例：
		 *        @JsonSerialize(using = DateJsonSerializer.class)
		 *        private Date getBirthDay(){return birthDay;}
		 *     3、Json序列化采用的是就近原则，如果属性上已经有对应的Json序列化器，则将覆盖全局的Json序列化器
		 */
		DateTimeJsonSerializer dateTimeJsonSerializer = new DateTimeJsonSerializer();
		serializerFactory.addGenericMapping(Date.class, dateTimeJsonSerializer);
		
		this.setSerializerFactory(serializerFactory);
	}
}
