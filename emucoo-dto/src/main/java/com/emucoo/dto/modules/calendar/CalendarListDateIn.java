package com.emucoo.dto.modules.calendar;

import lombok.Data;

import java.util.Date;

/**
 * Created by jacy .
 * 按日期查询行事历列表
 */
@Data
public class CalendarListDateIn {
    //月份 2018-04-22
    private Date  date;
    //用户ID
    private Long userId;
    
}
