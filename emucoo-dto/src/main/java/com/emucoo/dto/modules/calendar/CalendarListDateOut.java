package com.emucoo.dto.modules.calendar;

import com.emucoo.dto.modules.task.WorkVo_O;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by jacy .
 * 行事历列表查询结果
 */
@Data
public class CalendarListDateOut {
    //日期
    private Date date;
    //用户ID
    private Long userId;
    //行事历任务
    private List<WorkVo_O.Work> workArr;
}
