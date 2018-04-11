package com.emucoo.dto.modules.comment;

import lombok.Data;

@Data
public class CommentDeleteIn {
	private Integer workType;
	private Long workID;
	private Long replyID;
	
}
