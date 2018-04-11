package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TaskCommonSubmitIn {

	private Integer workType;
	private String workID;
	private String subID;
	private List<TaskItem> taskItemArr = new ArrayList<TaskItem>();

	@Data
	public static class TaskItem {
		private Long taskItemID;
		private String workText;
		private Double digitalItemValue;

		private List<ImgUrl> executeImgArr = new ArrayList<ImgUrl>();

		@Data
		public static class ImgUrl {
			private String imgUrl;
			private long date;
			private String location;
		}
	}
}
