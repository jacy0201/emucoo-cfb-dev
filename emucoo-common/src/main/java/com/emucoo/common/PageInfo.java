package com.emucoo.common;

import lombok.Data;

@Data
public class PageInfo {
	
	private Long pageSize;
	private Long pageNumber;
	private Object data;
	
}
