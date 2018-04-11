package com.emucoo.dto.modules.comment;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CommentAddIn {

	private Integer workType;
	private String workID;
	private String replyContent;
	private List<ImgUrl> replyImgArr = new ArrayList<ImgUrl>();

	@Data
	public static class ImgUrl {
		private String imgUrl;
	}
}
