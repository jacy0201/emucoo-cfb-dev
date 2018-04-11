package com.emucoo.dto.modules.shop;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ShopPlanProgressOut {
	
	private Long replyID;
	private String replyContent;
	private Long replyTime;
	private Long answerID;// 回复人ID
	private String answerName;
	private String answerHeadUrl;
	private String replyAction;// 1不可删，2可删
	
	private List<ImgUrl> replyImgArr = new ArrayList<ImgUrl>();

	@Data
	public static class ImgUrl {
		private String imgUrl;
	}
}
