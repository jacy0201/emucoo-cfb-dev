package com.emucoo.dto.modules.calendar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by jacy .
 * 删除行事历
 */
@Data
@ApiModel(value="删除行事历参数")
public class CalendarDelVO {
    //任务 id
    @ApiModelProperty(value="任务id",name="id",required = true, notes="任务 id")
    private String  id;

    //任务类型：1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘
    @ApiModelProperty(value="任务类型",name="workType",required = true ,notes = "1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘")
    private Integer workType;

}
