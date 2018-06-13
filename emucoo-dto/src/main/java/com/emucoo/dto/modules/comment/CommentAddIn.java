package com.emucoo.dto.modules.comment;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="添加评论/回复")
public class CommentAddIn {
	//评论Id
	@ApiModelProperty(value="评论Id",name="commentID",notes = "仅添加回复时传此参数")
	private Long commentID;
	//1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：工作备忘，6：评论
	@ApiModelProperty(value="评论类型",name="commentID",required = true,notes = "1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：工作备忘，6：评论,7：维修任务")
	private Integer workType;
	private String workID;
	private String subID;
	//维修任务ID
	private Long repairID;
	//workType为4时需传 reportID
	@ApiModelProperty(value="报告Id",name="reportID",notes = "workType=4 巡店任务评论传此参数")
	private Long reportID;
	@ApiModelProperty(value="评论内容",name="content")
	private String content;
	@ApiModelProperty(value="评论照片",name="commentImgArr")
	private List<ImgUrl> commentImgArr = new ArrayList<>();
	@Data
	public static class ImgUrl {
		private String imgUrl;
	}
}
