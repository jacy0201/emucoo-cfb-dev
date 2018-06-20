package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sj on 2018/6/20.
 */
@Data
@ApiModel
public class UnreadMsgCountOut {
    @ApiModelProperty(value = "是否有未读消息", name = "hasUnreadMsg", example = "false")
    private Boolean hasUnreadMsg = false;
}
