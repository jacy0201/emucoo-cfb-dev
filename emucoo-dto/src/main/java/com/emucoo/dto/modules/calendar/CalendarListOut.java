package com.emucoo.dto.modules.calendar;

import lombok.Data;

import java.util.List;

/**
 * Created by jacy .
 * 行事历列表查询结果
 */
@Data
public class CalendarListOut {
    //月份
    private String month;
    //用户ID
    private Long userId;
    //巡店任务
    private List<WorkVO> workList;
}
