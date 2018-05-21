package com.emucoo.dto.modules.calendar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by jacy .
 * 按月份查询行事历列表
 */
@Data
@ApiModel(value="按月份查询行事历列表")
public class CalendarListMonthIn {
    //月份 201803
    @ApiModelProperty(value="月份",name="month",required = true,example ="201803" )
    private String month;
    //用户ID
    @ApiModelProperty(value="用户ID",name="userId",required = true)
    private Long userId;
    
}
