package com.emucoo.dto.modules.calendar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="删除工作目标参数")
public class WorkTargetDelVO {
    @ApiModelProperty(value="工作目标Id",name="workTargetId",required = true)
    private Long workTargetId;
}
