package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/6/5.
 */
@Data
@ApiModel
public class MsgListOut {
    @ApiModelProperty(value = "消息列表", name = "msgArray")
    private List<MsgListVo> msgArray;
}
