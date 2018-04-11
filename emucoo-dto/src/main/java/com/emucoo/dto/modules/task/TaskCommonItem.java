package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TaskCommonItem {

	private String taskItemID;
	private String taskItemTitle;
	private Integer feedbackText;
	private Integer feedbackImg;
	private String digitalItemName;
	private Integer digitalItemType;
	private String ccPersonNames;
	private List<ImageUrl> taskItemImgArr = new ArrayList<ImageUrl>();

	private TaskSubmit taskSubmit;
	private TaskReview taskReview;

	@Data
	public class TaskSubmit {
		private Long taskSubID;
		private String taskSubHeadUrl;
		private Long taskSubTime;
		private String workText;
		private Double digitalItemValue;
		List<ImageUrl> executeImgArr = new ArrayList<ImageUrl>();
	}

	@Data
	public class TaskReview {
		private String reviewResult;
		private Long reviewID;
		private String reviewOpinion;
		private Long reviewTime;
		private Long auditorID;
		private String auditorName;
		private String auditorHeadUrl;
		List<ImageUrl> reviewImgArr = new ArrayList<ImageUrl>();
	}

}
