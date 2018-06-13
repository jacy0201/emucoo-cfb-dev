package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sj on 2018/6/13.
 */
@Data
@ApiModel(value = "获取消息列表输入参数")
public class MsgListIn {
    @ApiModelProperty(value = "消息功能类别", name = "functionType", notes = "1:创建提醒，2：执行提醒，3：待审提醒，4：超时提醒，5：审核完成提醒，6：抄送提醒，7：评论提醒")
    private Integer functionType;
}
