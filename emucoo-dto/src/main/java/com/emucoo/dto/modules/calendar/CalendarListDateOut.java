package com.emucoo.dto.modules.calendar;

import com.emucoo.dto.modules.task.WorkVo_O;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by jacy .
 * 行事历列表查询结果
 */
@Data
@ApiModel(value="返回数据集")
public class CalendarListDateOut {
    //日期
    @ApiModelProperty(value="日期",name="executeDate",required = true,example ="2018-04-22" )
    private String executeDate;
    //用户ID
    @ApiModelProperty(value="用户ID",name="userId",required = true)
    private Long userId;
    //行事历任务
    private List<WorkVo_O.Work> workArr;
}
