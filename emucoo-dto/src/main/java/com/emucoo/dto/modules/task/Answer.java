package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Answer {
	private Integer replyID;
	private String replyContent;
	private Long replyTime;
	private Integer answerID;
	private String answerName;
	private String answerHeadUrl;
	private Integer replyAction;
	private List<ImageUrl> replyImgArr = new ArrayList<ImageUrl>();
}
