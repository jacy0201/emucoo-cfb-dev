package com.emucoo.dto.modules.center;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="巡店任务")
public class FrontPlanVO {
    private Long ID;
    private String subID;
    private String taskTitle;
    //1：未执行 2：执行提交 3：执行过期, 4:已审核，5: 审核超时  ( 5 系统待审核)
    private Byte taskStatus;
    //任务类型： 4-巡店安排
    private Integer workType=4;
    private Long shopId;
    private String shopName;
    //实际提醒时间
    private Long actualRemindTime;
    //预计到店时间
    private Long planPreciseTime;
    //实际巡店时间
    private Long actualExecuteTime;

}
