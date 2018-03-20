package com.ebusiness.common.objectmapper;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.ebusiness.common.util.DateUtil;

/**
 * 日期时间Json序列化器
 * @author majun
 * @date 2013-12-26
 * 注：此列化器适用yyyy-MM-dd HH:mm:ss格式
 */
public class DateTimeJsonSerializer extends JsonSerializer<Date>
{
	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException 
	{
		generator.writeString(DateUtil.dateToString(date, "yyyy-MM-dd HH:mm:ss"));
	}
}
