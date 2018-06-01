package com.emucoo.dto.modules.center;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询任务")
public class TaskQuery {
    @ApiModelProperty(value="月份",name="month",required = true,example ="2018-05" )
    private String month;
}
