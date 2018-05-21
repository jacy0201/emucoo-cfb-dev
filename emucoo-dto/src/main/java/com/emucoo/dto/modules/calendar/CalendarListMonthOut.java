package com.emucoo.dto.modules.calendar;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.WorkVo_O;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by jacy .
 * 行事历列表查询结果
 */
@Data
@ApiModel(parent = ParamVo.class)
public class CalendarListMonthOut {
    //月份
    @ApiModelProperty(value="月份",name="month",required = true,example ="201803" )
    private String month;
    //用户ID
    @ApiModelProperty(value="用户ID",name="userId",required = true)
    private Long userId;
    //行事历任务
    private List<WorkVo_O.Work> workArr;
}
