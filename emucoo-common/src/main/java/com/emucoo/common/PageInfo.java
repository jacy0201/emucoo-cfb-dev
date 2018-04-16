package com.emucoo.common;

import lombok.Data;

@Data
public class PageInfo {
	//每页记录数
	private Integer pageSize;
	//当前页数
	private Integer pageNumber;
	//总记录数
	private Integer totalCount;

	//总页数
	private Integer totalPage;

	//数据集
	private Object data;
}
