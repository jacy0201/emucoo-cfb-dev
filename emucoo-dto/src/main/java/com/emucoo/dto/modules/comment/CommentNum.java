package com.emucoo.dto.modules.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value="评论数量")
public class CommentNum {
	private int  num;
}
