package com.ebusiness.common.mybatis;

import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * @author Madison
 * @date 2014-7-30
 */
public class Page<T> 
{
	/** 每页显示记录数(默认为20) */
	private int pageSize = 20;
	
	/** 当前页数(默认为1) */
	private int currentPage = 1;
	
	/** 总页数 */
	private int totalPage;
	
	/** 总记录数 */
	private int totalCount;
	
	/** 当前页对应的记录列表 */
	private List<T> resultList;
	
	/** 分页查询条件对应的参数Map */
	private Map<String, Object> paramMap;
	
	/**
	 * 是否进入分页拦截器
	 * 默认true表示进入,若为false表示不进入
	 * @author LYK 
	 * time 2016/7/22
	 */
	private boolean needPageIntercept=Boolean.TRUE;
	
	/**
	 * 计算总页数
	 * @param 	totalCount		总记录数
	 * @param 	pageSize		每页显示记录数
	 * @return	int				总页数
	 */
	public static int computeTotalPage(final int totalCount, final int pageSize)
	{
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
	}
	
	/**
	 * 计算开始页
	 * @param 	currentPage		当前页数
	 * @param 	pageSize		每页显示记录数
	 * @return	int				开始页
	 */
	public static int computeStartPage(final int currentPage, final int pageSize)
	{
		return pageSize * (currentPage - 1);
	}
	
	/**
	 * 计算结束页
	 * @param 	currentPage		当前页数
	 * @param 	pageSize		每页显示记录数
	 * @return	int				结束页
	 */
	public static int computeEndPage(final int currentPage, final int pageSize)
	{
		return pageSize * currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public boolean isNeedPageIntercept() {
		return needPageIntercept;
	}

	public void setNeedPageIntercept(boolean needPageIntercept) {
		this.needPageIntercept = needPageIntercept;
	}
	
	
}
