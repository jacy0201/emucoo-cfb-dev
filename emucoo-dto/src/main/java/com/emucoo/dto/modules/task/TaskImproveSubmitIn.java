package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 改善任务提交
 * 
 * @author zhangxq
 * @date 2018-03-21
 */
@Data
public class TaskImproveSubmitIn {
	private Integer workType;
	private String workID;
	private String subID;
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
