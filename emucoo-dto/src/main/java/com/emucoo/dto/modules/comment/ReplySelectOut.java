package com.emucoo.dto.modules.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value="回复对象")
public class ReplySelectOut {
    private CommentSelectOut.Comment comment =new CommentSelectOut.Comment();
	//回复列表
	@ApiModelProperty(value="被回复的评论对象",name="objectComment")
	private List<ObjectComment> replyList=new ArrayList<>();
	@Data
	public static class ObjectComment{
        @ApiModelProperty(value="评论Id",name="commentID",notes = "仅添加回复时传此参数")
        private Long commentID;
        @ApiModelProperty(value="评论内容",name="commentContent")
        private String commentContent;
        //评论时间
        @ApiModelProperty(value="评论时间",name="commentTime")
        private Long commentTime;
        //评论人ID
        @ApiModelProperty(value="评论人ID",name="commentUserID")
        private Long commentUserID;
        //评论人姓名
        @ApiModelProperty(value="评论人姓名",name="commentUserName")
        private String commentUserName;
        //评论人头像
        @ApiModelProperty(value="评论人头像",name="commentHeadUrl")
        private String commentHeadUrl;
        //评论照片
        @ApiModelProperty(value="评论图片",name="commentImgArr")
        private List<ImgUrl> commentImgArr = new ArrayList<ImgUrl>();
	}

	@Data
	public static class ImgUrl {
		@ApiModelProperty(value="评论图片url",name="imgUrl")
		private String imgUrl;
	}
}
