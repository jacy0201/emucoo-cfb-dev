package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sj on 2018/6/13.
 */
@Data
public class MsgListVo {
    @ApiModelProperty(value = "消息类别 1：常规任务，2：指派任务：3：改善任务，4：巡店安排，5：工作备忘，6：评论，7:维修任务", name = "workType")
    private Integer workType;
    @ApiModelProperty(value = "消息标题", name = "msgTitle")
    private String msgTitle;
    @ApiModelProperty(value = "发送时间戳", name = "sendTime")
    private Long sendTime;
    @ApiModelProperty(value = "关联的id", name = "unionID")
    private Long unionID;
    @ApiModelProperty(value = "消息id", name = "unionID")
    private Long msgID;
    @ApiModelProperty(value = "是否已读（0：未读，1：已读）", name = "isRead")
    private Boolean isRead;
}
