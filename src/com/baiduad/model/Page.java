package com.baiduad.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Page<T> {
	private static final int DEFAULT_PAGE_SIZE = 15;

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private int start = 0; // 当前页第一条数据在List中的位置,从0开始

	private int page = 1; // 当前页

	private int totalPage = 0; // 总计有多少页

	private int totalCount = 0; // 总记录数
	
	private List<T> list = new ArrayList<T>();

	// //////////////
	// 构造函数
	public Page() {
	}

	public Page(Integer page) {
		if(page!=null)
		  this.page = page;
	}

	// ///////////////

	public void setPage(int page) {
		if (page > 0) {
			start = (page - 1) * pageSize;
			this.page = page;
		}
	}

	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Page setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	// 此位置根据计算得到
	protected void setStart() {
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		totalPage = (int) Math.ceil((totalCount + pageSize - 1) / pageSize);
		start = (page - 1) * pageSize;
	}

	// 总页面数根据总数计算得到
	protected void setTotalPage() {

	}

	public int getTotalPage() {
		return totalPage;
	}

	// /////////////
	// 获取上一页页数
	public int getLastPage() {
		if (hasLastPage()) {
			return page - 1;
		}
		return page;
	}

	public int getNextPage() {
		if (hasNextPage()) {
			return page + 1;
		}
		return page;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return page < totalPage;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasLastPage() {
		return page > 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
	
	

}
