package com.emucoo.dto.modules.calendar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by jacy .
 * 按日期查询行事历列表
 */
@Data
@ApiModel(value="按天查询行事历列表")
public class CalendarListDateIn {
    //日期 2018-04-22
    @ApiModelProperty(value="日期",name="executeDate",required = true,example ="2018-04-22" )
    private String executeDate;
    //用户ID
    @ApiModelProperty(value="用户ID",name="userId",required = true)
    private Long userId;
    
}
