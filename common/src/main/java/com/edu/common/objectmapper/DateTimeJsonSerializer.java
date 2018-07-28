package com.edu.common.objectmapper;

import java.io.IOException;
import java.util.Date;

import com.edu.common.util.DateUtil;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 日期时间Json序列化器
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
