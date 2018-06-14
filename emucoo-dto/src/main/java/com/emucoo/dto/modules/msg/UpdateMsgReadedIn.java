package com.emucoo.dto.modules.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/6/13.
 */
@Data
@ApiModel
public class UpdateMsgReadedIn {
    @ApiModelProperty(value = "消息id列表", name = "msgIDs")
    private List<Long> msgIDs;
}
