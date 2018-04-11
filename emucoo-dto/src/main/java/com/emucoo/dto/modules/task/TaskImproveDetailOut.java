package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TaskImproveDetailOut {

	private TaskImproveStatement taskStatement;
	private TaskImproveSubmit taskSubmit;
	private Review taskReview;
	private List<Answer> taskReplyArr = new ArrayList<Answer>();

}
