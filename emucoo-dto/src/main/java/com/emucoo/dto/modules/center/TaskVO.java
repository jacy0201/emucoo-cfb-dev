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
    private Integer taskStatus;
    private Integer taskResult;
    //任务类型：1-常规任务 2-指派任务 3-改善任务  4-巡店安排；5-工作备忘
    private Integer workType;
    private String taskSubHeadUrl;
    private String taskSubName;
}
