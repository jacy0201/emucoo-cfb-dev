package com.emucoo.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 */
@Data
@ApiModel
public class BaseVo {


	@ApiModelProperty(name = "userToken", value = "用户token", example = "000000")
	protected String userToken;

}
