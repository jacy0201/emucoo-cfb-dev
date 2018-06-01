package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sj on 2018/5/30.
 */
@Data
@ApiModel(value = "消息不同模块数组")
public class MsgSummaryModuleVo {

    @ApiModelProperty(value = "消息模块类别", name = "moduleType", notes = "1:创建提醒，2：执行提醒，3：待审提醒，4：超时提醒，5：审核完成提醒，6：评论提醒")
    Integer moduleType;
    String msgContent;
    Integer msgCount;
    @ApiModelProperty(value = "最新的消息发送时间戳", name = "msgSendTimeStamp")
    Long msgSendTimeStamp;
}
