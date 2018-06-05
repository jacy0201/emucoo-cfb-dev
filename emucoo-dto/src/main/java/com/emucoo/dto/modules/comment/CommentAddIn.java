package com.emucoo.dto.modules.comment;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CommentAddIn {
	//评论Id
	private Long commentID;
	//1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：评论
	private Integer workType;
	private String workID;
	private String subID;
	//workType为4时需传 reportID
	private Long reportID;
	private String content;
	private List<ImgUrl> commentImgArr = new ArrayList<ImgUrl>();
	@Data
	public static class ImgUrl {
		private String imgUrl;
	}
}
