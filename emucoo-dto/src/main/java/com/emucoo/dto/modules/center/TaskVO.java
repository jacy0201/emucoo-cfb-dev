package com.emucoo.dto.modules.center;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="任务对象")
public class TaskVO {
    private Long ID;
    private String workID;
    private String subID;
    private String taskTitle;
    //1：未执行 2：执行提交 3：执行过期, 4:已审核，5: 审核超时  ( 5 系统待审核)
    private Integer taskStatus;
    //1：合格 2：不合格
    private Integer taskResult;
    //任务类型：1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘
    private Integer workType;

    //执行截止时间
    private Long executeDeadline;
    //执行提醒时间
    private Long executeRemindTime;

    //审核截止时间
    private Long auditDeadline;

    //打表人
    private String reporterName;
}
