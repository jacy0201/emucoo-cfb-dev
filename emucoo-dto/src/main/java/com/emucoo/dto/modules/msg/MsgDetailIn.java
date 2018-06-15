package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sj on 2018/6/13.
 */
@Data
@ApiModel
public class MsgDetailIn {

    @ApiModelProperty(value = "消息id", name = "msgID")
    private Long msgID;
    @ApiModelProperty(value = "消息类别 1：常规任务，2：指派任务：3：改善任务，4：巡店安排，5：工作备忘，6：评论，7:维修任务", name = "workType")
    private Integer workType;
    @ApiModelProperty(value = "消息功能类别 1:创建提醒，2：执行提醒，3：待审提醒，4：超时提醒，5：审核完成提醒，6：抄送提醒，7：评论提醒，8：维修日期确认", name = "functionType")
    private Integer functionType;
}
