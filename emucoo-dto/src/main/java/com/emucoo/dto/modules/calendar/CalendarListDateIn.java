package com.emucoo.dto.modules.calendar;

import lombok.Data;

/**
 * Created by jacy .
 * 按日期查询行事历列表
 */
@Data
public class CalendarListDateIn {
    //日期 2018-04-22
    private String  executeDate;
    //用户ID
    private Long userId;
    
}
