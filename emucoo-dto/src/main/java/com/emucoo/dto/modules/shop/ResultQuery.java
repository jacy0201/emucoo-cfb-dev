package com.emucoo.dto.modules.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResultQuery {
    private Long shopId;
    @ApiModelProperty(value="开始月份",name="startMonth",example ="2018-05" )
    private String startMonth;
    @ApiModelProperty(value="结束月份",name="endMonth",example ="2018-05" )
    private String endMonth;
    private Long dptId;
    private Long formId;
}
