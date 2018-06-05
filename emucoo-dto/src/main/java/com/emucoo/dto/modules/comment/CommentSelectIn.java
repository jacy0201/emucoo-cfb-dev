package com.emucoo.dto.modules.comment;

import lombok.Data;

@Data
public class CommentSelectIn {
	//1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：评论
	private Integer workType;
	private String workID;
	private String subID;
	//如果 workType=4 需传reportID
	private Long reportID;
	//评论id
	private Long commentID;
}
