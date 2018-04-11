package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 
 * @author zhangxq
 * @date 2018-03-22
 */
@Data
public class TaskImproveStatement {
	private Long backTime;
	private Long chancePointID;
	private String chancePointTitle;
	private Long chancePointSourceID;
	private String chancePointSourceName;
	private Integer chancePointNum;
	private Integer taskStatus;
	private String taskTitle;
	private String taskExplain;
	
	@JsonIgnore
	private String imgUrls;
	
	private List<ImageUrl> taskImgArr = new ArrayList<ImageUrl>();
	private String startDate;
	private String endDate;
	@JsonIgnore
	private Date submitDeadlineDate;
	private Long submitDeadline;
	@JsonIgnore
	private Date reviewDeadlineDate;
	private Long reviewDeadline;
	private Long executorID;
	private String executorName;
	private Long auditorID;
	private String auditorName;
	private String ccPersonNames;
	private Integer taskRepeatType;
	private String taskRepeatValue;
	private Integer taskRank;
	private Integer feedbackText;
	private Integer feedbackImg;
	private String digitalItemName;
	private Integer digitalItemType;

}
