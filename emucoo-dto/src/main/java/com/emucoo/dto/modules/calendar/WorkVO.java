package com.emucoo.dto.modules.calendar;

import lombok.Data;

import java.util.Date;

/**
 * Created by jacy .
 * 任务字段
 */
@Data
public class WorkVO {

    //任务类型
    private String workType;

    //任务描述
    private String workDesc;

    //任务开始时间
    private Date startTime;

    //任务截止时间
    private Date endTime;

    //巡店安排任务id;
    private Integer frontPlanId;

    // 巡店子计划id
    private Integer subPlanId;

    //计划创建人id
    private Integer createUserId;

    //



}
