package com.emucoo.dto.modules.task;

import lombok.Data;

/**
 * 改善任务详情
 * 
 * @author zhangxq
 * @date 2018-03-21
 */
@Data
public class TaskImproveDetailIn {
	
	private Integer workType;
	private String workID;
	private String subID;

}
