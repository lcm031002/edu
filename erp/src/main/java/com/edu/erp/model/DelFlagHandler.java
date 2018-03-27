package com.edu.erp.model;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 删除标识枚举转换器
 * @author Madison
 * @date 2014-7-29
 */
public class DelFlagHandler extends BaseTypeHandler<DelFlag>
{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, DelFlag parameter, JdbcType jdbcType) throws SQLException 
	{
		ps.setString(i, parameter.getCode());
	}

	@Override
	public DelFlag getNullableResult(ResultSet rs, String columnName) throws SQLException 
	{
		if (rs.wasNull())
        	return null;
        else
        {
        	//根据数据库存储类型决定获取类型
            String value = rs.getString(columnName);
            //根据数据库中的value，定位枚举常量
            return DelFlag.getEnumByCode(value);
        }
	}

	@Override
	public DelFlag getNullableResult(ResultSet rs, int columnIndex) throws SQLException 
	{
		if (rs.wasNull())
        	return null;
        else
        {
        	//根据数据库存储类型决定获取类型
            String value = rs.getString(columnIndex);
            //根据数据库中的value，定位枚举常量
            return DelFlag.getEnumByCode(value);
        }
	}

	@Override
	public DelFlag getNullableResult(CallableStatement cs, int columnIndex) throws SQLException 
	{
		if (cs.wasNull())
        	return null;
        else
        {
        	//根据数据库存储类型决定获取类型
            String value = cs.getString(columnIndex);
            //根据数据库中的value，定位枚举常量
            return DelFlag.getEnumByCode(value);
        }
	}
	
}
