package com.edu.common.mybatis;

/**
 * MySql方言
 * @author Madison
 * @date 2014-7-31
 */
public class MySQLDialect extends Dialect
{
	/**
	 * 构造分页SQL
	 * @param 	sql			原始SQL语句
	 * @param 	page		分页对象
	 * @return	String		构造后的分页SQL语句
	 */
	@Override
	public String constructPageSql(String sql, Page<?> page)
	{
		sql = sql.trim();
		
		//当前页数
		int currentPage = page.getCurrentPage();
		//每页显示记录数
		int pageSize = page.getPageSize();
		
		StringBuilder pageSql = new StringBuilder(sql.length() + 20);  
	    pageSql.append(sql);  
	    pageSql.append(" LIMIT " + (pageSize * (currentPage - 1)) + "," + pageSize);
	    
	    return pageSql.toString();  
	}
}
