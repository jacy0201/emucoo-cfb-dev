package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TaskCommonAuditIn {

	private Integer workType;
	private String workID;
	private String subID;
	private List<AuditTaskItem> reviewArr = new ArrayList<AuditTaskItem>();

	@Data
	public static class AuditTaskItem {
		private Long taskItemID;
		private Integer reviewResult;
		private String reviewOpinion;
		private List<ImageUrl> reviewImgArr = new ArrayList<ImageUrl>();
	}
}
