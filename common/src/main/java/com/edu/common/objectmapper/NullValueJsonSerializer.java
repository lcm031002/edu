package com.edu.common.objectmapper;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 空值Json序列化器
 */
public class NullValueJsonSerializer extends JsonSerializer<Object> 
{
	@Override
	public void serialize(Object object, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException 
	{
		generator.writeString("");
	}
}
