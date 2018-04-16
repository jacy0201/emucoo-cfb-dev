package com.emucoo.common;

import lombok.Data;

@Data
public class PageInfo {
	//每页记录数
	private Long pageSize;
	//当前页数
	private Long pageNumber;
	//总记录数
	private Long totalCount;

	//总页数
	private Long totalPage;

	//数据集
	private Object data;


	
}
