package com.emucoo.dto.modules.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="id 串参数",description="多个id 之间 ,分隔")
public class IdsVo {
    @ApiModelProperty(value="id串",name="ids",example="2,23,34")
    private  String  ids;
}
