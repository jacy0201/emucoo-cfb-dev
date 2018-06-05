package com.emucoo.dto.modules.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询评论/回复")
public class CommentSelectIn {
	//1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：工作备忘，6：评论
	@ApiModelProperty(value="评论类型",name="workType",required = true,notes = "1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：工作备忘，6：评论")
	private Integer workType;
	private String workID;
	private String subID;
	@ApiModelProperty(value="报告Id",name="reportID",notes = "workType=4 巡店任务评论传此参数")
	//如果 workType=4 需传reportID
	private Long reportID;
	@ApiModelProperty(value="评论ID",name="commentID",notes = "workType=6 查询回复传此参数")
	private Long commentID;
}
