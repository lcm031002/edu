package com.ebusiness.hrm.model;

import java.io.Serializable;

/**
	 * @ClassName: PageParam
	 * @Description: TODO(这里用一句话描述这个类的作用)
	 * @author WP
	 * @date 
	 * 
	 */
public final class PageParam implements Serializable{
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1818798881714301758L;
	// 当前页
	private int pageNum;
	// 每页的数量
	private int pageSize = 10;
	// 当前页的数量
	private int size;

	public PageParam() {

	}

	public PageParam(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public PageParam(int pageNum, int pageSize, int size, long total, int pages) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.size = size;
		this.total = total;
		this.pages = pages;
	}

	// 总记录数
	private long total;
	// 总页数
	private int pages;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	
}
