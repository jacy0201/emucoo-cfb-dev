package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 改善任务审核
 * 
 * @author zhangxq
 * @date 2018-03-21
 */
@Data
public class TaskImproveAuditIn {
	private Integer workType;
	private String workID;
	private String subID;
	private Integer reviewResult;
	private String reviewOpinion;
	private List<ImageUrl> reviewImgArr = new ArrayList<ImageUrl>();

}
