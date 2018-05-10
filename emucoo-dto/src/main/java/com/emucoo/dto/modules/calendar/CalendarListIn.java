package com.emucoo.dto.modules.calendar;

import lombok.Data;

/**
 * Created by jacy .
 * 行事历列表查询
 */
@Data
public class CalendarListIn {
    //月份
    private String month;
    //用户ID
    private Long userId;
    
}
