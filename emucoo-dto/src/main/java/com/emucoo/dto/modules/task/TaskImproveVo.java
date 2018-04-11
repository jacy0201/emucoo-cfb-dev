package com.emucoo.dto.modules.task;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/13 16:26
 */
@Data
public class TaskImproveVo {
	private String submitToken;
	private Long patrolShopArrangeID;
	private Long shopID;
	private Long taskReportID;
	private Long chancePointID;
	private String chancePointTitle;
	private String taskTitle;
	private String taskExplain;
	List<ImageUrl> taskImgArr = Lists.newArrayList();
	private String startDate;
	private String endDate;
	private String submitDeadlineRule;
	List<Executor> executorArray = Lists.newArrayList();
	private Long auditorID;
	private String auditorName;
	List<CCPerson> ccPersonArray = Lists.newArrayList();
	private Integer taskRepeatType;
	private String taskRepeatValue;
	private Integer taskRank;
	private Integer feedbackText;
	private Integer feedbackImg;
	private String digitalItemName;
	private Integer digitalItemType;

	@Data
	public static class Executor {
		private Long executorID;
		private String executorName;
	}

	@Data
	public static class CCPerson {
		private Long ccPersonID;
		private String ccPersonName;
	}

}
