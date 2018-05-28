package com.emucoo.dto.modules.calendar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询工作目标参数")
public class WorkTargetQueryVO {
    @ApiModelProperty(value="月份",name="month",required = true,example ="2018-05" )
    private String month;
    @ApiModelProperty(value="用户id",name="userID",required = true)
    private Long userID;

}
