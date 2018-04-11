package com.emucoo.dto.modules.task;

import lombok.Data;

@Data
public class TaskCommonItemVo {

	private Long taskItemID;
	private String taskItemTitle;
	private Integer feedbackText;
	private Integer feedbackImg;
	private String digitalItemName;
	private Integer digitalItemType;
	private String itemImgUrls;

	// TaskSubmit
	private Long taskSubID;
	private String taskSubHeadUrl;
	private Long taskSubTime;
	private String workText;
	private Double digitalItemValue;
	private String imageUrls;

	// TaskReview
	private String reviewResult;
	private Long reviewID;
	private String reviewOpinion;
	private Long reviewTime;
	private Long auditorID;
	private String auditorName;
	private String auditorHeadUrl;
	private String imgUrls;

}
