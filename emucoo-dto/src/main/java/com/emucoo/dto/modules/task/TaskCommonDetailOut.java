package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TaskCommonDetailOut {

	private TaskCommonStatement taskStatement;
	private List<TaskCommonItem> taskItemArr = new ArrayList<TaskCommonItem>();
	private Review allTaskReview;
	private List<Answer> taskReplyArr = new ArrayList<Answer>();

}
