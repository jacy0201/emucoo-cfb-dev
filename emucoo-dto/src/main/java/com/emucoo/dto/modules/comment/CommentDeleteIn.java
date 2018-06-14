package com.emucoo.dto.modules.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="删除评论")
public class CommentDeleteIn {
	@ApiModelProperty(value="评论Id",name="commentID",required = true)
	private Long commentId;
}
