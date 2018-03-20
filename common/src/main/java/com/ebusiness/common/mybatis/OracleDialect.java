package com.ebusiness.common.mybatis;

/**
 * Oracle方言
 * @author Madison
 * @date 2014-7-31
 */
public class OracleDialect extends Dialect 
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
		
		StringBuilder pageSql = new StringBuilder(sql.length() + 100);
		pageSql.append("SELECT * FROM ( SELECT T.*, ROWNUM rn FROM ( ");
		pageSql.append(sql);
		pageSql.append(" ) T WHERE ROWNUM <= " + (pageSize * currentPage)) ;
		pageSql.append(" ) WHERE RN > " + (pageSize * (currentPage - 1)));
		
		return pageSql.toString();
	}

}
