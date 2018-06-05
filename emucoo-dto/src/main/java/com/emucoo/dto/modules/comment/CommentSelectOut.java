package com.emucoo.dto.modules.comment;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CommentSelectOut {
	private Long commentID;
	private String commentContent;
	//评论时间
	private Long commentTime;
	//评论人ID
	private Long commentUserID;
	//评论人姓名
	private String commentUserName;
	//评论人头像
	private String commentHeadUrl;
	//评论照片
	private List<ImgUrl> commentImgArr = new ArrayList<ImgUrl>();
	@Data
	public static class ImgUrl {
		private String imgUrl;
	}
}
