package com.emucoo.dto.modules.calendar;

import lombok.Data;

/**
 * Created by jacy .
 * 删除行事历
 */
@Data
public class CalendarDelVO {
    //任务 id
    private String  id;

    //任务类型：1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘
    private Integer workType;

}
