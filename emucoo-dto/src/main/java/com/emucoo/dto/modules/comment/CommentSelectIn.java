package com.emucoo.dto.modules.comment;

import lombok.Data;

@Data
public class CommentSelectIn {
	
	private Integer workType;
	private Long workID;
	
	private Long pageNum;
	private Long pageSize;
}
