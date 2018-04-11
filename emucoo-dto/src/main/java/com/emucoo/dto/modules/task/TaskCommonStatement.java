package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class TaskCommonStatement {

	private Long backTime;
	private Integer taskStatus;
	private String taskTitle;
	private String taskExplain;
	private String startDate;
	private String endDate;
	private Long submitDeadline;
	private Long reviewDeadline;
	private Integer executorID;
	private String executorName;
	private Integer auditorID;
	private String auditorName;
	private String ccPersonNames;
	private Integer taskRepeatType;
	private String taskRepeatValue;
	private Integer taskRank;
	private Integer reviewItemType;
	
	@JsonIgnore
	private String imgUrls;
	
	private List<ImageUrl> taskImgArr = new ArrayList<ImageUrl>();

}
