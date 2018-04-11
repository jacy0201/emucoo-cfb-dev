package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 改善任务提交信息
 * 
 * @author zhangxq
 * @date 2018-03-22
 */
@Data
public class TaskImproveSubmit {
	private Integer taskSubPerID;
	private String taskSubPerHeadUrl;
	@JsonIgnore
	private Date taskSubTimeDate;
	private Long taskSubTime;
	private String workText;
	@JsonIgnore
	private String imgUrls;
	private List<ImageUrl> executeImgArr = new ArrayList<ImageUrl>();
	private Double digitalItemValue;
}
