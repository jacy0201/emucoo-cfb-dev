package com.emucoo.dto.modules.calendar;

import lombok.Data;

/**
 * Created by jacy .
 * 按月份查询行事历列表
 */
@Data
public class CalendarListMonthIn {
    //月份 201803
    private String month;
    //用户ID
    private Long userId;
    
}
