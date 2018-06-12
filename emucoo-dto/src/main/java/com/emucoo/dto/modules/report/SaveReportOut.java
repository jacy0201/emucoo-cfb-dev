package com.emucoo.dto.modules.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sj on 2018/3/31.
 */
@Data
@ApiModel
public class SaveReportOut {
    @ApiModelProperty(value = "±¨¸æid", name = "reportID", example = "1")
    private Long reportID;
}
